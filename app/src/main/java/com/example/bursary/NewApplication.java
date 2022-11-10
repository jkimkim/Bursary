package com.example.bursary;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class NewApplication extends DialogFragment {
    private MaterialButton selectId,selectReport,selectCertificate,selectFees,genderButton,applyButton;
    private ImageView idImage,reportView,certView,feeView;
    private Uri idUri,certUri,feeUri,reportUri;
    List<String> downloadUrls=new ArrayList<>();


    public static void showDialog(FragmentManager manager){
        NewApplication dialog=new NewApplication();
        dialog.show(manager,"New Application");

    }

    @Override

    public void onStart() {
        super.onStart();
        Dialog dialog=getDialog();
        if (dialog!=null){
            int width= ViewGroup.LayoutParams.MATCH_PARENT;
            int height=ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width,height);
            dialog.getWindow().setWindowAnimations(R.style.Theme_Bursary_Slide);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_gallery,container,false);
        selectId=view.findViewById(R.id.selectNId);
        selectCertificate=view.findViewById(R.id.selectBCert);
        selectFees=view.findViewById(R.id.selectFee);
        selectReport=view.findViewById(R.id.selectReport);
        genderButton=view.findViewById(R.id.gender);

        idImage=view.findViewById(R.id.nIdImage);
        reportView=view.findViewById(R.id.reportImage);
        certView=view.findViewById(R.id.bCertImage);
        feeView=view.findViewById(R.id.feeImage);
        applyButton=view.findViewById(R.id.apply);

        return view;

    }

}
