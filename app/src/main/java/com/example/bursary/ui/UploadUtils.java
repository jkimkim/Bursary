package com.example.bursary.ui;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class UploadUtils {
    String downloadUrl;

    public void uploadIdPhoto(Uri idUri, CompleteListener completeListener) {
        //upload the image to firebase storage
        //get the download url
        //call the completeListener.onComplete(downloadUrl)

        StorageReference storage= FirebaseStorage.getInstance().getReference("userids");
        StorageReference childref=storage.child(new File(idUri.getPath()).getName());
        UploadTask uploadTask=childref.putFile(idUri);

        Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return childref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                downloadUrl=task.getResult().toString();
                completeListener.onComplete(downloadUrl);
            }
        });


    }

public void uploadfee(Uri fee, CompleteListener completeListener) {
    //upload the image to firebase storage
    //get the download url
    //call the completeListener.onComplete(downloadUrl)

    StorageReference storage = FirebaseStorage.getInstance().getReference("userfees");
    StorageReference childref = storage.child(new File(fee.getPath()).getName());
    UploadTask uploadTask = childref.putFile(fee);

    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
        @Override
        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
            return childref.getDownloadUrl();
        }
    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
        @Override
        public void onComplete(@NonNull Task<Uri> task) {
            downloadUrl = task.getResult().toString();
            completeListener.onComplete(downloadUrl);
        }
    });
}

public void uploadCert(Uri fee, CompleteListener completeListener) {
    //upload the image to firebase storage
    //get the download url
    //call the completeListener.onComplete(downloadUrl)

    StorageReference storage = FirebaseStorage.getInstance().getReference("usercerts");
    StorageReference childref = storage.child(new File(fee.getPath()).getName());
    UploadTask uploadTask = childref.putFile(fee);

    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
        @Override
        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
            return childref.getDownloadUrl();
        }
    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
        @Override
        public void onComplete(@NonNull Task<Uri> task) {
            downloadUrl = task.getResult().toString();
            completeListener.onComplete(downloadUrl);
        }
    });
}

public void uploadReport(Uri fee, CompleteListener completeListener) {
    //upload the image to firebase storage
    //get the download url
    //call the completeListener.onComplete(downloadUrl)

    StorageReference storage = FirebaseStorage.getInstance().getReference("userreports");
    StorageReference childref = storage.child(new File(fee.getPath()).getName());
    UploadTask uploadTask = childref.putFile(fee);

    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
        @Override
        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
            return childref.getDownloadUrl();
        }
    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
        @Override
        public void onComplete(@NonNull Task<Uri> task) {
            downloadUrl = task.getResult().toString();
            completeListener.onComplete(downloadUrl);
        }
    });
}

}
