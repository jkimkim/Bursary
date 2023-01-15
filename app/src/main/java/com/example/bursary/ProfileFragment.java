package com.example.bursary;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bursary.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFragment extends Fragment {

    private  TextView fullName,email,phoneNo,welcome_text;
    private String name,emailId,phone;
    private ImageView profileImage;
    FirebaseAuth fAuth;

        private FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        fullName = view.findViewById(R.id.full_name);
        email = view.findViewById(R.id.email);
        phoneNo = view.findViewById(R.id.phone);
        profileImage = view.findViewById(R.id.profile_image);
        welcome_text = view.findViewById(R.id.welcome_text);
        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();

        if (user != null){
            showUserData(user);
        }

        return view;
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
            }
        });
    }
}