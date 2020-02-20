package com.example.augmentedhighereducationfortruckdrivers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.augmentedhighereducationfortruckdrivers.Models.UserLessonData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Query;


import java.util.ArrayList;
import java.util.List;


// Display user's progress of completed lessons
public class ProgressActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    private List<UserLessonData> lessonsData;
    private int lessonCount;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        lessonCount = 0;
        getSupportActionBar().setHomeButtonEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        lessonsData = new ArrayList<UserLessonData>();
        tv = (TextView) findViewById(R.id.text_view_progress);

        initFirestore();

    }

    // Uses RecyclerView to display lessonsComplete and lesson scores from database
    private void initFirestore() {

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        final ProgressBar pb = (ProgressBar) findViewById(R.id.editText);
        String uid = mAuth.getCurrentUser().getUid();
        final DocumentReference user = mFirestore.collection("users").document(uid);
        CollectionReference lessonCollection = user.collection("lessons");


        lessonCollection.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull  Task<QuerySnapshot> task) {


                        if (task.isSuccessful()) {

                            for (DocumentSnapshot document : task.getResult()) {
                                UserLessonData uld = document.toObject(UserLessonData.class);
                                if (uld != null) {
                                    lessonsData.add(uld);
                                    lessonCount++;
                                }

                            }

                            // Display userLessonData objects using ScoreAdapter
                            final LinearLayoutManager layoutManager = new LinearLayoutManager(ProgressActivity.this);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
                            mAdapter = new ScoreAdapter(lessonsData);
                            recyclerView.setAdapter(mAdapter);

                            user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                    // Display lessons complete in a progressBar as a percentage of total added lessons
                                    if (task.isSuccessful()) {

                                        DocumentSnapshot docSnap = task.getResult();
                                        Long lessonsComplete = docSnap.getLong("lessonsComplete");
                                        String lessonString = String.format("Lessons complete: %d", lessonsComplete);
                                        tv.setText(lessonString);
                                        float percentage = ((lessonsComplete * 100)/lessonCount);
                                        int intPercentage = (int)percentage;

                                        if (intPercentage >= 100) {
                                            intPercentage = 100;
                                            pb.getProgressDrawable().setColorFilter(Color.parseColor("#27D52A"), PorterDuff.Mode.SRC_IN);

                                        }
                                        pb.setProgress(intPercentage);

                                    }
                                }
                            });
                        }

                        else {
                            Log.d("tag", "Error getting documents: ", task.getException());

                        }
                    }
                });




    }
}
