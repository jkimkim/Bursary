package com.example.bursary;



import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;

import com.example.bursary.ui.CompleteListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
    List<FetchUserData> fetchUserDataList;
    private ValueEventListener eventListener;

    FirebaseAuth firebaseAuth;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
DatabaseReference reference = database.getReference("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();


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

    private void checkUserStatus() {
        //get current user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            //user is signed in stay here
            //set email of logged in user
            //mProfileTv.setText(user.getEmail());
        } else {
            //user not signed in, go to main activity
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
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