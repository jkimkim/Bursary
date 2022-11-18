package com.example.bursary.ui;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bursary.R;
import com.example.bursary.Upload;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.List;

public class AdminAdapterDialog extends DialogFragment {
    private RecyclerView recyclerView;
    private AdminAdapter adapter;
    private static List<Upload> uploadList = new ArrayList<>();
    private ValueEventListener rejectListener, acceptListener;
    private MaterialToolbar toolbar;

    public static void showDialog(FragmentManager manager, List<Upload> uploads) {
        AdminAdapterDialog dialog = new AdminAdapterDialog();
        uploadList.clear();
        uploadList.addAll(uploads);
        dialog.show(manager, "Admin Applications");

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_Bursary);

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.Theme_Bursary_Slide);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_view, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new AdminAdapter(getActivity(), uploadList, new ClickListener() {
            @Override
            public void onclick(Upload upload) {
                new MaterialAlertDialogBuilder(getActivity())
                        .setTitle("Pick an action")
                        .setItems(new String[]{"Accept", "Reject"}, (dialog, which) -> {
                            if (which == 0) {
                                accept(upload);
                                dialog.dismiss();
                            } else {
                                reject(upload);
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });

        return view;
    }

    private void accept(Upload upload) {
        Dialog progressDialog = new Dialog(getActivity());
        progressDialog.setContentView(R.layout.progressdialog);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("requests");
        acceptListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("uploadId").getValue().toString().equals(upload.getUploadId())) {
                        DatabaseReference updateRef = reference.child(dataSnapshot.getKey()).child("status");
                        updateRef.setValue("Accepted")
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity(), "Accepted", Toast.LENGTH_LONG).show();
                                        reference.removeEventListener(acceptListener);
                                        getActivity().recreate();
                                    }
                                });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "An Error Occured", Toast.LENGTH_LONG).show();

            }
        };
        reference.addListenerForSingleValueEvent(acceptListener);
    }

    private void reject(Upload upload) {
        Dialog progressDialog = new Dialog(getActivity());
        progressDialog.setContentView(R.layout.progressdialog);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("requests");
        rejectListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("uploadId").getValue().toString().equals(upload.getUploadId())) {
                        DatabaseReference updateRef = reference.child(dataSnapshot.getKey()).child("status");
                        updateRef.setValue("Rejected")
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity(), "Rejected", Toast.LENGTH_LONG).show();
                                        reference.removeEventListener(rejectListener);
                                        getActivity().recreate();
                                    }
                                });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "An Error Occured", Toast.LENGTH_LONG).show();

            }
        };
        reference.addListenerForSingleValueEvent(rejectListener);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (rejectListener != null) {
            FirebaseDatabase.getInstance().getReference("requests").removeEventListener(rejectListener);
        }
        if (acceptListener != null) {
            FirebaseDatabase.getInstance().getReference("requests").removeEventListener(acceptListener);
        }
    }
}
