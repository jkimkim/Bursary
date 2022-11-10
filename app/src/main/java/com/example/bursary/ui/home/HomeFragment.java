package com.example.bursary.ui.home;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bursary.NewApplication;
import com.example.bursary.R;
import com.example.bursary.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private FragmentHomeBinding binding;
    ProgressDialog progressDialog;

    private LinearLayout firstApplication, submittedApplication, myProfile, adminSection;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        firstApplication = view.findViewById(R.id.firstApplication);
        firstApplication.setOnClickListener(this);

        submittedApplication = view.findViewById(R.id.submittedApplication);
        myProfile = view.findViewById(R.id.myProfile);
        adminSection = view.findViewById(R.id.adminSection);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Submitting...");



        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.firstApplication:
                progressDialog.show();
                NewApplication.showDialog(getChildFragmentManager());
                progressDialog.dismiss();
                break;
        }
    }
}