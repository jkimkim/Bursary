package com.example.bursary;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.bursary.ui.Constants;
import com.example.bursary.ui.gallery.GalleryFragment;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.opensooq.supernova.gligar.GligarPicker;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class FirstApplicationFragment extends Fragment implements  DatePickerDialog.OnDateSetListener{
    //views
    private MaterialButton selectNId,selectFee,selectAdmission,selectStudentId,selectFeesReceipt,gender,apply;
    private ImageView nIdImage,feeImage,admissionImage,studentIdImage,feesReceiptImage;
    private Uri nIdUri,feeUri,admissionUri,studentIdUri,feesReceiptUri;
    List<String> downloadUrls=new ArrayList<>();
    List<Student> studentList=new ArrayList<>();
    //request codes
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_IMAGE_REQUEST2 = 2;
    private static final int PICK_IMAGE_REQUEST3 = 3;
    private static final int PICK_IMAGE_REQUEST4 = 4;
    private static final int PICK_IMAGE_REQUEST5 = 5;
    //write external storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    //write external storage request code
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;
    //read external storage request code
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 2;
    //firebase objects
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private FirebaseStorage storage;
    private FirebaseAuth mAuth;
    private String userId;
    private FirebaseUser user;
    //progress dialog
    private ProgressDialog progressDialog;
    //image uri
    private Uri filePath;
    // google material text input fields
    private TextInputEditText first_name, email, phone_number, date_of_birth, admission_number,institution_name,course_name
            ,institution_phone_number,bank_name,account_number,branch_name,district,division,location,ward
            ,constituency,sub_location,village;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first_application, container, false);
        //initializing views
        selectNId = view.findViewById(R.id.selectNId);
        selectFee = view.findViewById(R.id.selectFee);
        selectAdmission = view.findViewById(R.id.selectAdmission);
        selectStudentId = view.findViewById(R.id.selectStudentId);
        selectFeesReceipt = view.findViewById(R.id.selectFeesReceipt);

        nIdImage = view.findViewById(R.id.nIdImage);
        feeImage = view.findViewById(R.id.feeImage);
        admissionImage = view.findViewById(R.id.admissionImage);
        studentIdImage = view.findViewById(R.id.studentIdImage);
        feesReceiptImage = view.findViewById(R.id.feesReceiptImage);
        first_name = view.findViewById(R.id.first_name);
        email = view.findViewById(R.id.email);
        phone_number = view.findViewById(R.id.phone_number);
        date_of_birth = view.findViewById(R.id.date_of_birth);
        admission_number = view.findViewById(R.id.admission_number);
        institution_name = view.findViewById(R.id.institution_name);
        course_name = view.findViewById(R.id.course_name);
        institution_phone_number = view.findViewById(R.id.institution_phone_number);
        bank_name = view.findViewById(R.id.bank_name);
        account_number = view.findViewById(R.id.account_number);
        branch_name = view.findViewById(R.id.branch_name);
        district = view.findViewById(R.id.district);
        division = view.findViewById(R.id.division);
        location = view.findViewById(R.id.location);
        ward = view.findViewById(R.id.ward);
        constituency = view.findViewById(R.id.constituency);
        sub_location = view.findViewById(R.id.sub_location);
        village = view.findViewById(R.id.village);
        apply = view.findViewById(R.id.apply);
        gender = view.findViewById(R.id.gender);
        //getting the firebase storage reference
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("requests");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userId = user.getUid();
        //attaching listener
        selectNId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        selectFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser2();
            }
        });
        selectAdmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser3();
            }
        });
        selectStudentId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser4();
            }
        });
        selectFeesReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser5();
            }
        });
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] items=new String[]{"Male", "Female", "Others"};
                new MaterialAlertDialogBuilder(getActivity())
                        .setTitle("Select Gender")
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                gender.setText(items[which]);
                            }
                        }).create()
                        .show();
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });
        date_of_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        //checking if user has submitted application and if so, disable the apply button
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("requests");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Upload upload = dataSnapshot.getValue(Upload.class);
                    if (upload.getUserId().equals(userId)) {
                        apply.setEnabled(false);
                        apply.setText("You have already submitted an application");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    private void showDatePickerDialog() {

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date_of_birth.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, 1990, 0, 1);
        datePickerDialog.show();

    }

    private void showFileChooser5() {
        new GligarPicker().withFragment(this).limit(1).requestCode(PICK_IMAGE_REQUEST5).show();
    }
    private void showFileChooser4() {
        new GligarPicker().withFragment(this).limit(1).requestCode(PICK_IMAGE_REQUEST4).show();
    }
    private void showFileChooser3() {
        new GligarPicker().withFragment(this).limit(1).requestCode(PICK_IMAGE_REQUEST3).show();
    }
    private void showFileChooser2() {
        new GligarPicker().withFragment(this).limit(1).requestCode(PICK_IMAGE_REQUEST2).show();
    }
    private void showFileChooser() {
        new GligarPicker().withFragment(FirstApplicationFragment.this).limit(1).requestCode(PICK_IMAGE_REQUEST)
                .show();
    }
    //uploading data and images to firebase
    private void uploadFile() {
        //retrieve values from the edit texts
        String firstName = first_name.getText().toString().trim();
        String Email = email.getText().toString().trim();
        String phoneNumber = phone_number.getText().toString().trim();
        String dateOfBirth = date_of_birth.getText().toString().trim();
        String admissionNumber = admission_number.getText().toString().trim();
        String institutionName = institution_name.getText().toString().trim();
        String courseName = course_name.getText().toString().trim();
        String institutionPhoneNumber = institution_phone_number.getText().toString().trim();
        String bankName = bank_name.getText().toString().trim();
        String accountNumber = account_number.getText().toString().trim();
        String branchName = branch_name.getText().toString().trim();
        String District = district.getText().toString().trim();
        String Division = division.getText().toString().trim();
        String Location = location.getText().toString().trim();
        String Ward = ward.getText().toString().trim();
        String Constituency = constituency.getText().toString().trim();
        String subLocation = sub_location.getText().toString().trim();
        String Village = village.getText().toString().trim();
        //checking if the value is provided
        if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(Email) && !TextUtils.isEmpty(phoneNumber) && !TextUtils.isEmpty(dateOfBirth) && !TextUtils.isEmpty(admissionNumber) && !TextUtils.isEmpty(institutionName) && !TextUtils.isEmpty(courseName) && !TextUtils.isEmpty(institutionPhoneNumber) && !TextUtils.isEmpty(bankName) && !TextUtils.isEmpty(accountNumber) && !TextUtils.isEmpty(branchName) && !TextUtils.isEmpty(District) && !TextUtils.isEmpty(Division) && !TextUtils.isEmpty(Location) && !TextUtils.isEmpty(Ward) && !TextUtils.isEmpty(Constituency) && !TextUtils.isEmpty(subLocation) && !TextUtils.isEmpty(Village)) {
            //if the value is available
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading");
            progressDialog.show();
            //create new object to store the data
            // get a reference to the "Application" child of the Firebase Database
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Applications");
            // get reference to the storage reference
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            // create a list to save the upload tasks
            List<Task<Uri>> uploadTasks = new ArrayList<>();
            //upload images and get their download urls
            if (nIdUri != null){
                //create storage reference to the image
                StorageReference imageRef = mStorageRef.child("images/" + userId + "/nId.jpg");
                //upload the image and add the task to the list
                uploadTasks.add(imageRef.putFile(nIdUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        // Continue with the task to get the download URL
                        return imageRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            downloadUrls.add(downloadUri.toString());
                        }
                    }
                }));
            }
            if (feeUri != null){
                //create storage reference to the image
                StorageReference imageRef = mStorageRef.child("images/" + userId + "/fee.jpg");
                //upload the image and add the task to the list
                uploadTasks.add(imageRef.putFile(feeUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        // Continue with the task to get the download URL
                        return imageRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            downloadUrls.add(downloadUri.toString());
                        }
                    }
                }));
                }
            if (admissionUri != null){
                //create storage reference to the image
                StorageReference imageRef = mStorageRef.child("images/" + userId + "/admission.jpg");
                //upload the image and add the task to the list
                uploadTasks.add(imageRef.putFile(admissionUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        // Continue with the task to get the download URL
                        return imageRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            downloadUrls.add(downloadUri.toString());
                        }
                    }
                }));
            }
            if(studentIdUri !=null){
                //create storage reference to the image
                StorageReference imageRef = mStorageRef.child("images/" + userId + "/studentId.jpg");
                //upload the image and add the task to the list
                uploadTasks.add(imageRef.putFile(studentIdUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        // Continue with the task to get the download URL
                        return imageRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            downloadUrls.add(downloadUri.toString());
                        }
                    }
                }));
            }
            if (feesReceiptUri != null){
                //create storage reference to the image
                StorageReference imageRef = mStorageRef.child("images/" + userId + "/feesReceipt.jpg");
                //upload the image and add the task to the list
                uploadTasks.add(imageRef.putFile(feesReceiptUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        // Continue with the task to get the download URL
                        return imageRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            downloadUrls.add(downloadUri.toString());
                        }
                    }
                }));
            }
            //add download urls to the student object
            final Upload upload = new Upload(downloadUrls, FirebaseAuth.getInstance().getCurrentUser().getUid(),String.valueOf(System.currentTimeMillis()),gender.getText().toString(),"Pending",Calendar.getInstance().getTime().toString(),
                    firstName, phoneNumber, Email, dateOfBirth, admissionNumber, courseName, institutionPhoneNumber, institutionName,
                    bankName, accountNumber, branchName, District, Division, Location, Ward, Constituency, subLocation, Village);

            //use Task.whenAllSuccess to wait for all the tasks to complete
            Tasks.whenAll(uploadTasks).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // save the application to the database
                        mDatabaseRef.push().setValue(upload);
                        // dismiss the progress dialog
                        progressDialog.dismiss();
                        // show a success message
                        new MaterialAlertDialogBuilder(getActivity())
                                .setTitle("Success")
                                .setMessage("Application Submitted Successfully")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
// navigate back to the MainActivity
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                })
                                .show();
                    } else {
                        // dismiss the progress dialog
                        progressDialog.dismiss();
// show an error message
                        new MaterialAlertDialogBuilder(getActivity())
                                .setTitle("Error")
                                .setMessage("Failed to submit application, please try again later.")
                                .setPositiveButton("OK", null)
                                .show();
                    }
                }
            });
        }
    }
    //getting the image uri
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode!= AppCompatActivity.RESULT_OK){
            return;
        }

        if (requestCode== PICK_IMAGE_REQUEST){
            String[] idPath = data.getExtras().getStringArray(GligarPicker.IMAGES_RESULT);
            nIdUri=Uri.fromFile(new File(idPath[0]));
            nIdImage.setImageURI(nIdUri);

        }

        if (requestCode==PICK_IMAGE_REQUEST2){
            String[] feePath = data.getExtras().getStringArray(GligarPicker.IMAGES_RESULT);
            feeUri=Uri.fromFile(new File(feePath[0]));
            feeImage.setImageURI(feeUri);


        }
        if (requestCode==PICK_IMAGE_REQUEST3){
            String[] certPath = data.getExtras().getStringArray(GligarPicker.IMAGES_RESULT);
            admissionUri=Uri.fromFile(new File(certPath[0]));
            admissionImage.setImageURI(admissionUri);

        }

        if (requestCode==PICK_IMAGE_REQUEST4){
            String[] reportPath = data.getExtras().getStringArray(GligarPicker.IMAGES_RESULT);
            studentIdUri=Uri.fromFile(new File(reportPath[0]));
            studentIdImage.setImageURI(studentIdUri);

        }

        if (requestCode==PICK_IMAGE_REQUEST5){
            String[] reportPath = data.getExtras().getStringArray(GligarPicker.IMAGES_RESULT);
            feesReceiptUri=Uri.fromFile(new File(reportPath[0]));
            feesReceiptImage.setImageURI(feesReceiptUri);

        }
    }
    //date picker
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
        date_of_birth.setText(selectedDate);
    }
}
