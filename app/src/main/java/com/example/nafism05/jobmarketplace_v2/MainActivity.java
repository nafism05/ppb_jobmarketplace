package com.example.nafism05.jobmarketplace_v2;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nafism05.jobmarketplace_v2.adapter.JobRecyclerViewAdapter;
import com.example.nafism05.jobmarketplace_v2.model.Job;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityaaaaaa";

    private RecyclerView recyclerView;
    private JobRecyclerViewAdapter mAdapter;
    private List<Job> jobList = new ArrayList<>();

    TextView emailView;
    Button btSignIn;
    Button btSignOut;
    Button btRegister;
    Button btPostJob;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestoreDB;
    private ListenerRegistration firestoreListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvJobList);

        mAuth = FirebaseAuth.getInstance();
        firestoreDB = FirebaseFirestore.getInstance();

        emailView = findViewById(R.id.emailView);
        btSignIn = findViewById(R.id.btSignIn);
        btSignOut = findViewById(R.id.btSignOut);
        btRegister = findViewById(R.id.btRegister);
        btPostJob = findViewById(R.id.btPostJob);


       /* recyclerView.setHasFixedSize(true);
        mAdapter = new JobRecyclerViewAdapter(jobList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareData();*/

       loadJobList();

        /*firestoreListener = firestoreDB.collection("joblist")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.e(TAG, "Listen failed!", e);
                            return;
                        }

                        List<Job> jobList = new ArrayList<>();

                        for (DocumentSnapshot doc : documentSnapshots) {
                            Job job = doc.toObject(Job.class);
                            job.setId(doc.getId());
                            jobList.add(job);
                        }

                        mAdapter = new JobRecyclerViewAdapter(jobList, getApplicationContext(), firestoreDB);
                        recyclerView.setAdapter(mAdapter);
                    }
                });*/
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }

    public void signOut(View view) {
        mAuth.signOut();
        updateUI(null);
        finish();
        startActivity(getIntent());
    }

    public void showSignInForm(View view){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void showPostJobForm(View view){
        Intent intent = new Intent(this, PostJobActivity.class);
        startActivity(intent);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            emailView.setText(user.getEmail());
            emailView.setVisibility(View.VISIBLE);
            btSignIn.setVisibility(View.GONE);
            btSignOut.setVisibility(View.VISIBLE);
            btPostJob.setVisibility(View.VISIBLE);
        }else{
            emailView.setText("");
            emailView.setVisibility(View.GONE);
            btSignIn.setVisibility(View.VISIBLE);
            btSignOut.setVisibility(View.GONE);
            btRegister.setVisibility(View.VISIBLE);
            btPostJob.setVisibility(View.GONE);
        }
    }

    private void add(){
        Map<String, Object> data = new HashMap<>();
        data.put("title", "Bus Ticket Reservation System");
        data.put("language", "PHP");
        data.put("detail", "we are looking a bus ticket reservation system web base and also with mobile app. user should able to see available but set and book that also ticket master mast able to see reservation details and also able to issue ticket from counter .");

        firestoreDB.collection("joblist")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    private void loadJobList(){
        firestoreDB.collection("joblist")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Job> jobList = new ArrayList<>();

                            for (DocumentSnapshot doc : task.getResult()) {
                                Job job = doc.toObject(Job.class);
                                job.setId(doc.getId());
                                jobList.add(job);
                            }

                            mAdapter = new JobRecyclerViewAdapter(jobList, MainActivity.this, firestoreDB);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(mAdapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void prepareData(){
        Job job = new Job("123", "Web sekolah", "Laravel", "paling lambat besok");
        jobList.add(job);

        job = new Job("123", "Mobile sekolah", "Android", "paling lambat besok");
        jobList.add(job);

        job = new Job("123", "Mobile sekolah", "Android", "paling lambat besok");
        jobList.add(job);

        job = new Job("123", "Mobile sekolah", "Android", "paling lambat besok");
        jobList.add(job);

        job = new Job("123", "Mobile sekolah", "Android", "paling lambat besok");
        jobList.add(job);

        job = new Job("123", "Mobile sekolah", "Android", "paling lambat besok");
        jobList.add(job);

        job = new Job("123", "Mobile sekolah", "Android", "paling lambat besok");
        jobList.add(job);

        job = new Job("123", "Mobile sekolah", "Android", "paling lambat besok");
        jobList.add(job);

        job = new Job("123", "Mobile sekolah", "Android", "paling lambat besok");
        jobList.add(job);

        job = new Job("123", "Mobile sekolah", "Android", "paling lambat besok");
        jobList.add(job);

        job = new Job("123", "Mobile sekolah", "Android", "paling lambat besok");
        jobList.add(job);

        mAdapter.notifyDataSetChanged();

//        Log.d(TAG, "prepare data jalan");
        for (Job str_Agil : jobList)   // using foreach
        {
            Log.e("Agil_Limits - " , str_Agil.getTitle());
        }
    }
}
