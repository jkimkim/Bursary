package com.example.bursary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText enteremail;
    private Button resetPassBtn;

    ProgressDialog progressDialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        enteremail = (EditText) findViewById(R.id.yourEmail);

        resetPassBtn = (Button) findViewById(R.id.buttonResetPass);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Requesting...");

        mAuth = FirebaseAuth.getInstance();

        resetPassBtn.setOnClickListener(v -> {
            String email = enteremail.getText().toString().trim();
            if (email.isEmpty()) {
                enteremail.setError("Email is required");
                enteremail.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                enteremail.setError("Please provide valid email");
                enteremail.requestFocus();
                return;
            }
            progressDialog.show();
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgotPassword.this, "Check your email to reset your password!", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(ForgotPassword.this, "Try again! Something wrong happened!", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
        });

    }
}