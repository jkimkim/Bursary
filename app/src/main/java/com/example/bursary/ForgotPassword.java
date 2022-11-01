package com.example.bursary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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



    }
}