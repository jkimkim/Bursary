package com.example.bursary.ui;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.bursary.Upload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fetcher {
    private ValueEventListener eventListener, genderListener;

    public void fetchApplications(CompleteListener listener) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("requests");
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<Upload> uploadList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Log.e("data", dataSnapshot.toString());
                    Upload upload = dataSnapshot.getValue(Upload.class);
                    uploadList.add(upload);
                }
                Log.e("uploadlist", uploadList.toString());
                reference.removeEventListener(eventListener);
                listener.onUploadFetched(uploadList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        reference.addListenerForSingleValueEvent(eventListener);
    }
}
