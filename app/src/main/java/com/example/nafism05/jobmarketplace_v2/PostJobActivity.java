package com.example.nafism05.jobmarketplace_v2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nafism05.jobmarketplace_v2.model.Job;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class PostJobActivity extends AppCompatActivity {

    private static final String TAG = "PostJobyaaaaaaaaaa";

    TextView fieldTitle;
    TextView fieldLanguage;
    TextView fieldDetail;
    Button btPostJob;

    String id = "";

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestoreDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);

        fieldTitle = findViewById(R.id.fieldTitle);
        fieldLanguage = findViewById(R.id.fieldLanguage);
        fieldDetail = findViewById(R.id.fieldDetail);

        mAuth = FirebaseAuth.getInstance();
        firestoreDB = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("id");

            fieldTitle.setText(bundle.getString("title"));
            fieldLanguage.setText(bundle.getString("language"));
            fieldDetail.setText(bundle.getString("detail"));
        }
    }

    public void postJob(View view){

        FirebaseUser user = mAuth.getCurrentUser();

        String title = fieldTitle.getText().toString();
        String language = fieldLanguage.getText().toString();
        String detail = fieldDetail.getText().toString();
        String user_id = user.getUid();

        if(id.length() > 0){
            editJobProcess(id, title, language, detail, user_id);
        }else{
            postJobProcess(title, language, detail, user_id);
        }


//        postJobProcess2();

    }

    private void postJobProcess(String title, String language, String detail, String user_id){
        Map<String, Object> job = new Job(user_id, title, language, detail).toMap();

        final Intent intent = new Intent(this, MainActivity.class);

        firestoreDB.collection("joblist")
                .add(job)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.e(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        Toast.makeText(getApplicationContext(), "Job has been added!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error adding Note document", e);
                        Toast.makeText(getApplicationContext(), "Job could not be added!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                });

    }

    private void editJobProcess(String id, String title, String language, String detail, String user_id){
        Map<String, Object> job = (new Job(id, user_id, title, language, detail)).toMap();

        final Intent intent = new Intent(this, MainActivity.class);

        firestoreDB.collection("joblist")
                .document(id)
                .set(job)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG, "Note document update successful!");
                        Toast.makeText(getApplicationContext(), "Job has been updated!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error adding Note document", e);
                        Toast.makeText(getApplicationContext(), "Job could not be updated!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                });
    }

    private void postJobProcess2(){
        Toast.makeText(getApplicationContext(), "Note has been added!", Toast.LENGTH_SHORT).show();
    }
}
