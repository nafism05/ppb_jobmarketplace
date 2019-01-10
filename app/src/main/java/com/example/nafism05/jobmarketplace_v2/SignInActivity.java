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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "Logeeeeeaaaaa";

    TextView fieldEmailSignIn;
    TextView fieldPwdSignIn;
    Button btSignIn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        fieldEmailSignIn = findViewById(R.id.fieldEmailSignIn);
        fieldPwdSignIn = findViewById(R.id.fieldPwdSignIn);

        mAuth = FirebaseAuth.getInstance();
    }

    public void signIn(View view){
        String email = fieldEmailSignIn.getText().toString();
        String password = fieldPwdSignIn.getText().toString();

        signInProcess(email, password);
    }

    private void signInProcess(String email, String password) {

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);

                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            String msg = "";

                            try {
                                throw task.getException();
                            } /*catch(FirebaseAuthWeakPasswordException e) {
                                msg = "password weak";
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                msg = "invalid email";
                            } catch(FirebaseAuthUserCollisionException e) {
                                msg = "user exist";
                            }*/ catch(Exception e) {
                                Log.e(TAG, e.getMessage());
                                msg = e.getMessage();
                            }
                            Toast.makeText(SignInActivity.this, msg,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END sign_in_with_email]
    }

}
