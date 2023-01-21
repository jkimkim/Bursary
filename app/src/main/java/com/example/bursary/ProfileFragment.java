package com.example.bursary;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bursary.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ProfileFragment extends Fragment {

    private  TextView fullName,email,phoneNo,welcome_text;
    private String name,emailId,phone;
    private ImageView profileImage;
    FloatingActionButton fab;
    FirebaseAuth fAuth;
    FirebaseUser user;
    DatabaseReference reference;
    String userId;
    ProgressDialog pd;


    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        pd = new ProgressDialog(getContext());

        fullName = view.findViewById(R.id.full_name);
        email = view.findViewById(R.id.email);
        phoneNo = view.findViewById(R.id.phone);
        profileImage = view.findViewById(R.id.profile_image);
        //set click on profile image to change profile picture
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open ChangeProfile activity
                startActivity(new Intent(getActivity(), ChangeProfile.class));
            }
        });
        welcome_text = view.findViewById(R.id.welcome_text);
        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();

        if (user != null){
            showUserData(user);
        }

        return view;
    }

    private void showEditProfileDialog() {

        //Edit Profile Picture, Edit cover Photo,Edit Name,Edit Phone
        String options[]={"Edit Email ","Edit Phone "};
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        //set title
        builder.setTitle("Choose Action");
        //set items to dialog
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                //handle dialog item clicks

                if (which==0){
                    pd.setMessage("Updating Email");
                    showNamePhoneUpdateDialogue("Email");

                }
                //edit profile click
                else if (which==1){
                    pd.setMessage("Updating Cover Photo");
                    showNamePhoneUpdateDialogue("Phone");

                }
            }
        });
        //create and show dialog
        builder.create().show();
    }

    private void showNamePhoneUpdateDialogue(String key) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Update "+key);
        //set layout of dialogue
        LinearLayout linearLayout=new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10,10,10,10);
        //add edit text
        EditText editText= new EditText(getActivity());
        editText.setHint("Enter"+key);
        linearLayout.addView(editText);
        builder.setView(linearLayout);
        //add button dialogue to update
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String value =editText.getText().toString().trim();
                if (!TextUtils.isEmpty(value)) {
                    pd.show();
                    HashMap<String, Object> result = new HashMap<>();
                    result.put(key, value);

                    reference.child(userId).updateChildren(result)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void avoid) {
                                    pd.dismiss();
                                    Toast.makeText(getActivity(), "Updated..", Toast.LENGTH_SHORT).show();


                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();

                                    Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                }
                else {
                    Toast.makeText(getActivity(), "please Enter", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //button to cancel
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

            }
        });
        builder.create().show();
    }


    private void showUserData(FirebaseUser user) {
        //extracting data from firebase database
        String uId = user.getUid();

        //extracting data from firebase database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(uId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                name = task.getResult().child("name").getValue(String.class);
                emailId = task.getResult().child("email").getValue(String.class);
                phone = task.getResult().child("phone").getValue(String.class);

                fullName.setText(name);
                email.setText(emailId);
                phoneNo.setText(phone);
                welcome_text.setText("Welcome "+name + "!");

                //set image view to user's profile picture from firebase using Picasso
                Uri uri = user.getPhotoUrl();
                Picasso.get().load(uri).into(profileImage);
            }
        });
    }
}