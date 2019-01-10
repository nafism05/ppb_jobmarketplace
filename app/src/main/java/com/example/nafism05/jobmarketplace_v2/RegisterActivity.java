package com.example.nafism05.jobmarketplace_v2;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "Logeeeeeaaaaa";

    TextView fieldEmail;
    TextView fieldPwd;
    Button btRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fieldEmail = findViewById(R.id.fieldEmail);
        fieldPwd = findViewById(R.id.fieldPwd);

        mAuth = FirebaseAuth.getInstance();
    }

    public void register(View view) {
        String email = fieldEmail.getText().toString();
        String password = fieldPwd.getText().toString();

        registerProcess(email, password);
    }

    private void registerProcess(String email, String password) {

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());

                        String msg = "";

                        try {
                            throw task.getException();
                        } catch(Exception e) {
                            Log.e(TAG, e.getMessage());
                            msg = e.getMessage();
                        }
                        Toast.makeText(RegisterActivity.this, msg,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        // [END create_user_with_email]
    }
}
