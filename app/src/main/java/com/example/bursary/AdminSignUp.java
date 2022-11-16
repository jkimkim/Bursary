package com.example.bursary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminSignUp extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Button btnRegister;
    private EditText txtName, txtEmail, txtPassword, txtConfirmPassword;
    private ImageView banner2;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Admins");

        banner2 = findViewById(R.id.banner2);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                registerAdmin();
                break;
                case R.id.banner2:
                    startActivity(new Intent(AdminSignUp.this, AdminLogin.class));
                    break;
        }
    }

    private void registerAdmin() {
        String name = txtName.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        String confirmPassword = txtConfirmPassword.getText().toString().trim();

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

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.setError("Please enter a valid email");
            txtEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            txtPassword.setError("Password is required");
            txtPassword.requestFocus();
            return;
        }

        if (password.length() < 8) {
            txtPassword.setError("Minimum length of password should be 8 characters");
            txtPassword.requestFocus();
            return;
        }

        if (confirmPassword.isEmpty()) {
            txtConfirmPassword.setError("Confirm Password is required");
            txtConfirmPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            txtConfirmPassword.setError("Passwords do not match");
            txtConfirmPassword.requestFocus();
            return;
        }
        //progressDialog.show();
        progressDialog.show();

        //register Admin
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Admin admin = new Admin(name, email);
                                    mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(admin).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        FirebaseUser admin = mAuth.getCurrentUser();

                                        Toast.makeText(AdminSignUp.this, "Admin Registered Successfully", Toast.LENGTH_LONG).show();

                                        startActivity(new Intent(AdminSignUp.this, AdminActivity.class));
                                    } else {
                                        progressDialog.dismiss();
                                        //display a failure message
                                        Toast.makeText(AdminSignUp.this, "Registration Failed", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            progressDialog.dismiss();
                            //display a failure message
                        }
                    }
                });
    }
}