package com.example.augmentedhighereducationfortruckdrivers;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;

import com.example.augmentedhighereducationfortruckdrivers.Models.Lesson;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;






public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private ListView lv;
    public static boolean firstHomePage = false;
    private ViewGroup viewGroup;
    protected Toolbar toolbar;
    public static List<String> fileList;
    public static String fileName;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);

        FloatingActionButton upload_button = findViewById(R.id.button_upload);
        upload_button.setOnClickListener(this);

        initView();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        toolbar.setTitleTextColor(0xFFFFFFFF);

    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewGroup = (RelativeLayout) findViewById(R.id.container);
        setSupportActionBar(toolbar);

        lv = (ListView)findViewById(R.id.list_view);
        fileList = getFiles();

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fileList);
        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fileName = fileList.get(position);
                fileName = convertFileName(fileName);
                fileName = fileName + ".json";
                System.out.println(fileName);
                Intent intent = new Intent(getApplicationContext(), TeachActivity.class);
                intent.putExtra("FILE_NAME", fileName);
                startActivity(intent);


            }
        });

        // Delete lesson after long button click
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final int which_item = position;
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this lesson? Your progress will be lost")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String fileName = fileList.get(which_item);
                                String withExtension = fileName.replaceAll(" ","_");
                                withExtension = withExtension.toLowerCase();
                                withExtension = withExtension +".json";

                                deleteFile(withExtension);
                                fileList.remove(which_item);
                                arrayAdapter.notifyDataSetChanged();
                                deleteFileDb(fileName);

                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                 return true;
            }
        });
    }


    // Delete document from /users/uid/lessons/
    private void deleteFileDb(String fileName) {
        String uid = mAuth.getCurrentUser().getUid();
        final DocumentReference user = db.collection("users").document(uid);
        final DocumentReference lesson = user.collection("lessons").document(fileName);

        lesson.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot != null) {
                    long score = documentSnapshot.getLong("score");
                    if (score >= 50) {
                        user.update("lessonsComplete", FieldValue.increment(-1));
                    }
                    lesson.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("db", "DocumentSnapshot successfully deleted!");

                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("fail", "Document snapshot did not delete", e);
                                }
                            });
                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_upload) {
            Intent lessonAddIntent = new Intent(this, LessonAddActivity.class);
            this.startActivity(lessonAddIntent);
        }

    }

    // List files from /users/uid/lessons/
    private List<String> getFiles() {
        List<String> resultList = new ArrayList<String>();
        File dir = getFilesDir();
        for (File file : dir.listFiles()) {
            Gson gson = new Gson();
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getAbsolutePath()));
                Lesson testLesson = gson.fromJson(bufferedReader, Lesson.class);
                resultList.add(testLesson.name);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        return resultList;
    }

    public static String convertFileName(String name) {
        String fileName;
        fileName = (name.replaceAll("\\s", "_")).toLowerCase();

        return fileName;
    }


    @Override
    protected void onResume() {
        super.onResume();
        fileList = getFiles();
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            //noinspection RestrictedApi
            m.setOptionalIconsVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.Help:
                Intent helpIntent = new Intent(this, HelpActivity.class);
                this.startActivity(helpIntent);
                break;
            case R.id.logout:
                mAuth.signOut();
                Intent loginIntent = new Intent(this, LoginActivity.class);
                this.startActivity(loginIntent);
                finish();
                break;
            case R.id.progress:
                Intent progressIntent = new Intent(this, ProgressActivity.class);
                this.startActivity(progressIntent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }



}
