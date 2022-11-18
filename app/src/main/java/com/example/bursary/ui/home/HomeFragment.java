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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.bursary.NewApplication;
import com.example.bursary.R;
import com.example.bursary.Upload;
import com.example.bursary.databinding.FragmentHomeBinding;
//import com.example.bursary.ui.AdminAdapterDialog;
import com.example.bursary.ui.CompleteListener;
import com.example.bursary.ui.Fetcher;
import com.example.bursary.ui.MyApplications;
import com.example.bursary.ui.gallery.GalleryFragment;

import java.util.List;

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
        submittedApplication.setOnClickListener(this);

        myProfile = view.findViewById(R.id.myProfile);

        adminSection = view.findViewById(R.id.adminSection);
        adminSection.setOnClickListener(this);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Submitting...");



        return view;

    }

    @Override
    public void onClick(View v) {
        Dialog pDialog = new Dialog(HomeFragment.this.getContext());
        pDialog.setContentView(R.layout.progressdialog);
        pDialog.setCancelable(false);
        pDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        pDialog.show();

        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.firstApplication:
                fragment = new GalleryFragment();
                replaceFragment(fragment);
                pDialog.dismiss();
                break;
            case R.id.submittedApplication:
                pDialog.show();

                new Fetcher().fetchApplications(new CompleteListener() {
                    @Override
                    public void onUploadFetched(List<Upload> uploads) {
                        pDialog.dismiss();
                        MyApplications.showDialog(getChildFragmentManager(), uploads);
                    }
                });

                break;
            //case R.id.adminSection:
              //  pDialog.show();
                //new Fetcher().fetchApplications(new CompleteListener() {
                  //  @Override
                    //public void onUploadFetched(List<Upload> uploads) {
                      //  pDialog.dismiss();
                        //AdminAdapterDialog.showDialog(getChildFragmentManager(), uploads);
                   // }
                //});
        }
    }

    private void replaceFragment(Fragment galleryFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment_content_main, galleryFragment);
        transaction.setReorderingAllowed(true);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}