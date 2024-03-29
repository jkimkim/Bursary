package com.example.bursary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private DatabaseReference mDatabase;

    //views


    private ImageView banner2;
    private EditText txtName, txtEmail, txtPassword, txtConfirmPassword,
            txtPhone;
    private Button btnRegister;
    ProgressDialog progressDialog;
    private TextView txtConditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        //initializing views

        banner2 = findViewById(R.id.banner2);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        txtPhone = findViewById(R.id.txtPhone);

        txtConditions = findViewById(R.id.txtConditions);
        txtConditions.setOnClickListener(this);

        btnRegister = findViewById(R.id.btnRegister);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        btnRegister.setOnClickListener(this);

        banner2.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:
                registerUser();
                break;
           // case R.id.editTextDate:
             //   com.example.bursary.ui.DatePicker mDatePickerDialogFragment = new com.example.bursary.ui.DatePicker();
               // mDatePickerDialogFragment.show(getSupportFragmentManager(), "Date Picker");
                //break;
                case R.id.txtConditions:
                    termsAndConditions();
                    break;
        }
    }

    //terms and conditions
    private void termsAndConditions() {
        // open TermsActivity
        startActivity(new Intent(SignupActivity.this, TermsActivity.class));
    }

    //register user

    private void registerUser() {
        String name = txtName.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String phone = txtPhone.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        String confirmPassword = txtConfirmPassword.getText().toString().trim();

        //String id = mAuth.getCurrentUser().getUid();

        //Validation

        if (name.isEmpty()) {
            txtName.setError("Name is required");
            txtName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            txtEmail.setError("Email is required");
            txtEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.setError("Please provide valid email");
            txtEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            txtPassword.setError("Password is required");
            txtPassword.requestFocus();
            return;
        }

        if (confirmPassword.isEmpty()) {
            txtConfirmPassword.setError("Confirm Password is required");
            txtConfirmPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            txtPassword.setError("Password must be at least 6 characters");
            txtPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            txtConfirmPassword.setError("Password does not match");
            txtConfirmPassword.requestFocus();
            return;
        }

if (phone.isEmpty()) {
            txtPhone.setError("Phone number is required");
            txtPhone.requestFocus();
            return;
        }

        if (phone.length() < 10) {
            txtPhone.setError("Phone number must be at least 10 characters");
            txtPhone.requestFocus();
            return;
        }


        //progressBar

        progressDialog.show();

        //register the user in firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(name,phone,email);
                                    mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        progressDialog.dismiss();
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        Toast.makeText(SignupActivity.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                        //redirect to login layout

//                                        Intent goToHome = new Intent(SignupActivity.this, MainActivity.class);
//                                        startActivity(goToHome);
//                                        finish();

                                        // Redirect to main activity
                                        startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                        finish();

                                    } else {
                                        Toast.makeText(SignupActivity.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(SignupActivity.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

        //register the user in firebase

    }

    //Date picker

}