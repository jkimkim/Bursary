package com.example.bursary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.bursary.ui.AdminAdapterDialog;
import com.example.bursary.ui.CompleteListener;
import com.example.bursary.ui.Fetcher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class AdminActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    private AppBarConfiguration mAppBarConfiguration;
    private LinearLayout adminOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        firebaseAuth = FirebaseAuth.getInstance();
        adminOptions = findViewById(R.id.adminSection);
        adminOptions.setOnClickListener(v -> {
            Dialog progressDialog=new Dialog(AdminActivity.this);
            progressDialog.setContentView(R.layout.progressdialog);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.show();

            new Fetcher().fetchApplications(new CompleteListener() {
                @Override
                public void onUploadFetched(List<Upload> uploads) {
                    progressDialog.dismiss();
                    AdminAdapterDialog.showDialog(getSupportFragmentManager(),uploads);
                }
            });
        });
        checkUserStatus();
    }

    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            // user is signed in stay here
            // set email of logged in user
        } else {
            // user not signed in, go to main activity
            startActivity(new Intent(AdminActivity.this, AdminLogin.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        checkUserStatus();
        super.onStart();
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("AdminActivity", "popping backstack");
            fm.popBackStack();
        } else {
            Log.i("AdminActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }

}
