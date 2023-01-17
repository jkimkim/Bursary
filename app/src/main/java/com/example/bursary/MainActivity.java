package com.example.bursary;



import android.Manifest;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bursary.ui.CompleteListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bursary.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private ImageView imageView;
    private TextView txtname_nav, txtemail_nav;
    List<FetchUserData> fetchUserDataList;
    private ValueEventListener eventListener;
    private String name, email;


    FirebaseAuth firebaseAuth;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
DatabaseReference reference = database.getReference("Users");

//read and write external storage permission
private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE=1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //request permission to write to external storage
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_EXTERNAL_STORAGE_REQUEST_CODE);


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        //imageView = findViewById(R.id.profile_image);
        //txtname_nav = findViewById(R.id.txtname_nav);
        //txtemail_nav = findViewById(R.id.txtemail_nav);


       // DatabaseReference ref = database.getReference("Users").child(user.getUid());
        //ref.addValueEventListener(new ValueEventListener() {
          //  @Override
            //public void onDataChange(@NonNull DataSnapshot snapshot) {
              // List<FetchUserData> fetchUserDataList = new ArrayList<>();
                //for (DataSnapshot ds : snapshot.getChildren()) {
                  //  Log.e("data", ds.toString());
                    //FetchUserData fetchUserData = ds.getValue(FetchUserData.class);
                    //fetchUserDataList.add(fetchUserData);
               // }
                //Log.e("fetchUserDataList", fetchUserDataList.toString());
                //ref.removeEventListener(this);

               // System.out.println(fetchUserDataList);
            //}

            //@Override
            //public void onCancelled(@NonNull DatabaseError error) {

            //}
        //});

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        txtname_nav = binding.navView.getHeaderView(0).findViewById(R.id.txtname_nav);
        txtemail_nav = binding.navView.getHeaderView(0).findViewById(R.id.txtemail_nav);
        imageView = binding.navView.getHeaderView(0).findViewById(R.id.imageView);

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Feature coming soon", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.profileFragment)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    //load user name and email, profile image in navigation drawer

    private void checkUserStatus() {
        //get current user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            //user is signed in stay here
            //show user data in navigation drawer
            showUserData(user);
            //mProfileTv.setText(user.getEmail());
        } else {
            //user not signed in, go to main activity
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }

    private void showUserData(FirebaseUser user) {
        //get user data from firebase database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //get data
                name = "" + snapshot.child("name").getValue();
                email = "" + snapshot.child("email").getValue();
                //set data
                txtname_nav.setText(name);
                txtemail_nav.setText(email);

                //set profile image
                Uri uri = user.getPhotoUrl();
                Glide.with(MainActivity.this).load(uri).into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    //@Override
    //public void onBackPressed() {
      //  new AlertDialog.Builder(this)
        //        .setTitle("Exit?")
          //      .setMessage("Are you sure you want to exit?")
            //    .setNegativeButton(android.R.string.no, null)
              //  .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//
  //                  public void onClick(DialogInterface arg0, int arg1) {
    ///                    MainActivity.super.onBackPressed();
       //             }
         //       }).create().show();

    @Override
    public void onBackPressed(){
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }


    public void onStart() {
        super.onStart();
        checkUserStatus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //handle menu item clicks


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //get item id
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            firebaseAuth.signOut();
            checkUserStatus();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void fetchUsers(CompleteListener listener){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        DatabaseReference ref = database.getReference("Users").child(user.getUid());
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<FetchUserData>fetchUserDataList = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Log.e("data", ds.toString());
                    FetchUserData fetchUserData = ds.getValue(FetchUserData.class);
                    fetchUserDataList.add(fetchUserData);
                }
                Log.e("fetchUserDataList", fetchUserDataList.toString());
                ref.removeEventListener(eventListener);
                listener.onFetchUserDataFetched(fetchUserDataList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        ref.addListenerForSingleValueEvent(eventListener);
    }

}