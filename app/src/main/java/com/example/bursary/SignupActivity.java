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

public class SignupActivity extends AppCompatActivity implements View.OnClickListener , DatePickerDialog.OnDateSetListener{

    private FirebaseAuth mAuth;

    private DatabaseReference mDatabase;

    //views


    private ImageView banner2;
    private EditText txtName, txtEmail, txtPassword, txtConfirmPassword, editTextDate,
            txtPhone,txtAdmNo,txtCourse,institutionPhone,txtInstitution,txtBankName, txtBankAccNo, txtBankBranch,txtDistrict,txtDivision,txtLocation,txtWard,txtConstituency,txtSubLocation,txtVillage;
    private Button btnRegister;
    ProgressDialog progressDialog;

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
        txtAdmNo = findViewById(R.id.txtAdmNo);
        txtCourse = findViewById(R.id.txtCourse);
        institutionPhone = findViewById(R.id.institutionPhone);
        txtInstitution = findViewById(R.id.txtInstitution);
        txtBankName = findViewById(R.id.txtBankName);
        txtBankAccNo = findViewById(R.id.txtBankAccNo);
        txtBankBranch = findViewById(R.id.txtBankBranch);
        txtDistrict = findViewById(R.id.txtDistrict);
        txtDivision = findViewById(R.id.txtDivision);
        txtLocation = findViewById(R.id.txtLocation);
        txtWard = findViewById(R.id.txtWard);
        txtConstituency = findViewById(R.id.txtConstituency);
        txtSubLocation = findViewById(R.id.txtSubLocation);
        txtVillage = findViewById(R.id.txtVillage);

        editTextDate = findViewById(R.id.editTextDate);
        editTextDate.setOnClickListener(this);

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
            case R.id.editTextDate:
                com.example.bursary.ui.DatePicker mDatePickerDialogFragment = new com.example.bursary.ui.DatePicker();
                mDatePickerDialogFragment.show(getSupportFragmentManager(), "Date Picker");
                break;
        }
    }

    //register user

    private void registerUser() {
        String name = txtName.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String phone = txtPhone.getText().toString().trim();
        String admNo = txtAdmNo.getText().toString().trim();
        String course = txtCourse.getText().toString().trim();
        String institution = txtInstitution.getText().toString().trim();
        String institutionPhoneNo = institutionPhone.getText().toString().trim();
        String bankName = txtBankName.getText().toString().trim();
        String bankAccNo = txtBankAccNo.getText().toString().trim();
        String bankBranch = txtBankBranch.getText().toString().trim();
        String district = txtDistrict.getText().toString().trim();
        String division = txtDivision.getText().toString().trim();
        String location = txtLocation.getText().toString().trim();
        String ward = txtWard.getText().toString().trim();
        String constituency = txtConstituency.getText().toString().trim();
        String subLocation = txtSubLocation.getText().toString().trim();
        String village = txtVillage.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        String confirmPassword = txtConfirmPassword.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        String id = mAuth.getCurrentUser().getUid();

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

        if (date.isEmpty()) {
            editTextDate.setError("Date is required");
            editTextDate.requestFocus();
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

        if (admNo.isEmpty()) {
            txtAdmNo.setError("Admission number is required");
            txtAdmNo.requestFocus();
            return;
        }

        if (course.isEmpty()) {
            txtCourse.setError("Course is required");
            txtCourse.requestFocus();
            return;
        }

        if (institution.isEmpty()) {
            txtInstitution.setError("Institution is required");
            txtInstitution.requestFocus();
            return;
        }

        if (institutionPhoneNo.isEmpty()) {
            institutionPhone.setError("Institution phone number is required");
            institutionPhone.requestFocus();
            return;
        }

        if (bankName.isEmpty()) {
            txtBankName.setError("Bank name is required");
            txtBankName.requestFocus();
            return;
        }

        if (bankAccNo.isEmpty()) {
            txtBankAccNo.setError("Bank account number is required");
            txtBankAccNo.requestFocus();
            return;
        }

        if (bankBranch.isEmpty()) {
            txtBankBranch.setError("Bank branch is required");
            txtBankBranch.requestFocus();
            return;
        }

        if (district.isEmpty()) {
            txtDistrict.setError("District is required");
            txtDistrict.requestFocus();
            return;
        }

        if (division.isEmpty()) {
            txtDivision.setError("Division is required");
            txtDivision.requestFocus();
            return;
        }

        if (location.isEmpty()) {
            txtLocation.setError("Location is required");
            txtLocation.requestFocus();
            return;
        }

        if (ward.isEmpty()) {
            txtWard.setError("Ward is required");
            txtWard.requestFocus();
            return;
        }

        if (constituency.isEmpty()) {
            txtConstituency.setError("Constituency is required");
            txtConstituency.requestFocus();
            return;
        }

        if (subLocation.isEmpty()) {
            txtSubLocation.setError("Sub location is required");
            txtSubLocation.requestFocus();
            return;
        }

        if (village.isEmpty()) {
            txtVillage.setError("Village is required");
            txtVillage.requestFocus();
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
                            User user = new User(name,phone,email,date,admNo,course,institutionPhoneNo,institution,bankName,bankAccNo,bankBranch,district,division,location,ward,constituency,subLocation,village,id);
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
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
        editTextDate.setText(selectedDate);
    }
}