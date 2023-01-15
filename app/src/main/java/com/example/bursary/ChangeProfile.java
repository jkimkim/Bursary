package com.example.bursary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ChangeProfile extends AppCompatActivity {

    private ImageView imageView;
    FirebaseAuth firebaseAuth;
    private Button select_image_button,upload_button;
    StorageReference storageReference;
    FirebaseUser user;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        imageView = findViewById(R.id.image_view);
        upload_button = findViewById(R.id.upload_button);
        select_image_button = findViewById(R.id.upload_image_button);
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("Profile Images");
        user = firebaseAuth.getCurrentUser();

        Uri uri = user.getPhotoUrl();

        //set image view to user's profile picture from firebase using Picasso
        Picasso.get().load(uri).into(imageView);

        select_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open file explorer to select image
                openFileChooser();
            }
        });
        
        //upload image to firebase storage
        upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        //set title of the activity
        setTitle("Change Profile Picture");
    }

    private void uploadImage() {
        //progress dialog
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        //upload image to firebase storage
        if (imageUri != null){
            //save image to firebase storage with user's id as the name
            StorageReference fileReference = storageReference.child(user.getUid()+"."+getFileExtension(imageUri));

            //upload image to firebase storage
            fileReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        progressDialog.dismiss();
                        //get image url from firebase storage
                        fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                            Uri downloadUrl = uri;
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(downloadUrl)
                                    .build();
                            user.updateProfile(profileUpdates);
                        });
                    })
                    .addOnFailureListener(e -> progressDialog.dismiss())
                    .addOnProgressListener(taskSnapshot -> {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        //if upload is complete, finish activity
                        if (progress == 100){
                            finish();
                        }
                    });
        }
        else {
            progressDialog.dismiss();
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri imageUri) {
        //get file extension of the image
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }

    private void openFileChooser() {
        //open file explorer to select image
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //check if the request code is the same as the one we used to open the file explorer
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            //get the image uri
            imageUri = data.getData();
            //set the image view to the selected image
            imageView.setImageURI(imageUri);
        }
    }
}