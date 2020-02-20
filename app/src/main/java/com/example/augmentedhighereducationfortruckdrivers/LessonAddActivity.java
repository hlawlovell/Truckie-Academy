package com.example.augmentedhighereducationfortruckdrivers;

import android.os.Bundle;


import com.example.augmentedhighereducationfortruckdrivers.Models.Lesson;
import com.example.augmentedhighereducationfortruckdrivers.Models.UserLessonData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LessonAddActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LessonAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseStorage storage;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    private List<String> mDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        storage  = FirebaseStorage.getInstance();
        mDataset = new ArrayList<String>();

        StorageReference listRef = storage.getReference().child("lessons");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.lesson_recyclerview);


        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));


        // Lists all lessons in Firebase Storage using LessonAdapter and RecyclerView
        listRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {

                        for (StorageReference item : listResult.getItems()) {

                            String name = FilenameUtils.removeExtension(item.getName());
                            name = name.replaceAll("_", " ");
                            name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
                            mDataset.add(name);

                        }
                        mAdapter = new LessonAdapter(mDataset);
                        mAdapter.setOnItemClickListener(new LessonAdapter.ClickListener() {
                            @Override
                            public void onItemClick(int position, View v) {
                                downloadLesson(position);
                            }

                        });
                        recyclerView.setAdapter(mAdapter);


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Uh-oh, an error occurred!
                    }
                });



    }

    public static String convertFileName(String name) {
        String fileName;
        fileName = (name.replaceAll("\\s", "_")).toLowerCase();

        return fileName;
    }

    private void downloadLesson(int pos) {
        storage  = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        String fileName = convertFileName(mDataset.get(pos)) + ".json";



        File dir = getFilesDir();
        final File lesson = new File(dir, fileName);

        if (lesson.exists()){
            Toast toast = Toast.makeText(getApplicationContext(), "You already have this lesson", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        StorageReference lessonRef = storageRef.child("lessons/" + fileName);
        lessonRef.getFile(lesson).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Toast toast = Toast.makeText(getApplicationContext(), "Lesson added", Toast.LENGTH_LONG);
                toast.show();
                try {
                    addLessonDb(lesson);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast toast = Toast.makeText(getApplicationContext(), "Error downloading lesson", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }



    // Create document in /users/uid/lessons/lesson/
    private void addLessonDb(File lesson) throws FileNotFoundException {

        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(lesson.getAbsolutePath()));
        Lesson testLesson = gson.fromJson(bufferedReader, Lesson.class);
        String name = testLesson.name;

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        CollectionReference userCol = db.collection("users");

        long score = 0;
        UserLessonData uld = new UserLessonData(new Date(), name, score);
        userCol.document(uid).collection("lessons").document(name).set(uld).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("firestore", "Document written successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("firestore", "Error writing document", e);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
