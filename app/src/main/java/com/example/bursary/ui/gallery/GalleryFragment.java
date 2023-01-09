package com.example.bursary.ui.gallery;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.bursary.FetchUserData;
import com.example.bursary.R;
import com.example.bursary.Upload;
import com.example.bursary.databinding.FragmentGalleryBinding;
import com.example.bursary.ui.CompleteListener;
import com.example.bursary.ui.Constants;
import com.example.bursary.ui.UploadUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.opensooq.supernova.gligar.GligarPicker;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private MaterialButton selectId,selectReport,selectCertificate,selectFees,genderButton,applyButton;
    private ImageView idImage,reportView,certView,feeView;
    private Uri idUri,certUri,feeUri,reportUri;
    List<String> downloadUrls=new ArrayList<>();
    List<Upload> uploadList=new ArrayList<>();

    private TextView name,phone,email,dob,admNo,course,institution_number,institution,bank_name,bank_account_number,bank_branch,district,division,location,ward,constituency,sub_location,village;

    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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

        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Users");

        name=view.findViewById(R.id.name);
        email=view.findViewById(R.id.email);
        phone=view.findViewById(R.id.phone);
        dob=view.findViewById(R.id.dob);
        admNo=view.findViewById(R.id.admNo);
        course=view.findViewById(R.id.course);
        institution=view.findViewById(R.id.institution);
        institution_number=view.findViewById(R.id.institution_number);
        bank_name=view.findViewById(R.id.bank_name);
        bank_account_number=view.findViewById(R.id.bank_account_number);
        bank_branch=view.findViewById(R.id.bank_branch);
        district=view.findViewById(R.id.district);
        division=view.findViewById(R.id.division);
        location=view.findViewById(R.id.location);
        ward=view.findViewById(R.id.ward);
        constituency=view.findViewById(R.id.constituency);
        sub_location=view.findViewById(R.id.sub_location);
        village=view.findViewById(R.id.village);

        Query query=reference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Upload upload=dataSnapshot.getValue(Upload.class);
                    uploadList.add(upload);
                }
                name.setText(uploadList.get(0).getName());
                email.setText(uploadList.get(0).getEmail());
                phone.setText(uploadList.get(0).getPhone());
                dob.setText(uploadList.get(0).getDate());
                admNo.setText(uploadList.get(0).getAdmNo());
                course.setText(uploadList.get(0).getCourse());
                institution.setText(uploadList.get(0).getInstitution());
                institution_number.setText(uploadList.get(0).getInstitutionPhoneNo());
                bank_name.setText(uploadList.get(0).getBankName());
                bank_account_number.setText(uploadList.get(0).getBankAccountNo());
                bank_branch.setText(uploadList.get(0).getBankBranch());
                district.setText(uploadList.get(0).getDistrict());
                division.setText(uploadList.get(0).getDivision());
                location.setText(uploadList.get(0).getLocation());
                ward.setText(uploadList.get(0).getWard());
                constituency.setText(uploadList.get(0).getConstituency());
                sub_location.setText(uploadList.get(0).getSubLocation());
                village.setText(uploadList.get(0).getVillage());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;

    }

    //selecting images

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GligarPicker().withFragment(GalleryFragment.this).limit(1).requestCode(Constants.ID_IMG_REQUEST_CODE)
                        .show();
            }
        });

        selectReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GligarPicker().withFragment(GalleryFragment.this).limit(1).requestCode(Constants.REPORT_IMAGE_REQUEST_CODE)
                        .show();
            }
        });

        selectFees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GligarPicker().withFragment(GalleryFragment.this).limit(1).requestCode(Constants.FEE_IMG_REQUEST_CODE)
                        .show();
            }
        });
        selectCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GligarPicker().withFragment(GalleryFragment.this).limit(1).requestCode(Constants.CERT_IMAGE_REQUEST_CODE)
                        .show();
            }
        });

        genderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] items=new String[]{"Male", "Female", "Others"};
                new MaterialAlertDialogBuilder(getActivity())
                        .setTitle("Select Gender")
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                genderButton.setText(items[which]);
                            }
                        }).create()
                        .show();
            }
        });

        //uploading data to firebase

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idUri==null || certUri==null || feeUri==null || reportUri==null || genderButton.getText().toString().equals(getResources().getString(R.string.select_gender))){
                    Toast.makeText(getActivity(),"Please fill all the data!",Toast.LENGTH_LONG).show();
                    return;
                }
                Dialog progressDialog=new Dialog(getActivity());
                progressDialog.setContentView(R.layout.progressdialog);
                progressDialog.setCancelable(false);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                progressDialog.show();

                //uploading id image
                new UploadUtils().uploadIdPhoto(idUri, new CompleteListener() {
                    @Override
                    public void onComplete(String downLoadUrl) {
                        downloadUrls.add(downLoadUrl);

                        //uploading certificate image
                        new UploadUtils().uploadCert(certUri, new CompleteListener() {
                            @Override
                            public void onComplete(String downLoadUrl1) {
                                downloadUrls.add(downLoadUrl1);

                                //uploading fee image
                                new UploadUtils().uploadfee(feeUri, new CompleteListener() {
                                    @Override
                                    public void onComplete(String downLoadUrl2) {
                                        downloadUrls.add(downLoadUrl2);

                                        //uploading report image
                                        new UploadUtils().uploadReport(reportUri, new CompleteListener() {
                                            @Override
                                            public void onComplete(String downLoadUrl3) {
                                                downloadUrls.add(downLoadUrl3);

                                                //uploading data to firebase
                                                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("requests")
                                                        .push();
                                                Upload upload=new Upload(downloadUrls, FirebaseAuth.getInstance().getCurrentUser().getUid(),String.valueOf(System.currentTimeMillis()),genderButton.getText().toString(),"Pending", Calendar.getInstance().getTime().toString(),name.getText().toString(),phone.getText().toString(),email.getText().toString(),dob.getText().toString(),admNo.getText().toString(),course.getText().toString(),institution_number.getText().toString(),institution.getText().toString(),bank_name.getText().toString(),bank_account_number.getText().toString(),bank_branch.getText().toString(),district.getText().toString(),division.getText().toString(),location.getText().toString(),ward.getText().toString(),constituency.getText().toString(),sub_location.getText().toString(),village.getText().toString());
                                                reference.setValue(upload)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                progressDialog.dismiss();

                                                                Toast.makeText(getActivity(),"Submitted Successfully!",Toast.LENGTH_LONG).show();

                                                                //clearing data
                                                                downloadUrls.clear();
                                                                idUri=null;
                                                                certUri=null;
                                                                feeUri=null;
                                                                reportUri=null;

                                                                //clearing views
                                                                idImage.setImageResource(R.drawable.ic_baseline_image_24);
                                                                certView.setImageResource(R.drawable.ic_baseline_image_24);
                                                                feeView.setImageResource(R.drawable.ic_baseline_image_24);
                                                                reportView.setImageResource(R.drawable.ic_baseline_image_24);
                                                                //navigating to home
                                                                Navigation.findNavController(v).navigate(R.id.nav_home);
                                                                //create pdf
                                                            }
                                                        });
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });

            }
        });
    }

    //getting the image uri

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode!= AppCompatActivity.RESULT_OK){
            return;
        }

        if (requestCode==Constants.ID_IMG_REQUEST_CODE){
            String[] idPath = data.getExtras().getStringArray(GligarPicker.IMAGES_RESULT);
            idUri=Uri.fromFile(new File(idPath[0]));
            idImage.setImageURI(idUri);

        }

        if (requestCode==Constants.FEE_IMG_REQUEST_CODE){
            String[] feePath = data.getExtras().getStringArray(GligarPicker.IMAGES_RESULT);
            feeUri=Uri.fromFile(new File(feePath[0]));
            feeView.setImageURI(feeUri);


        }
        if (requestCode==Constants.CERT_IMAGE_REQUEST_CODE){
            String[] certPath = data.getExtras().getStringArray(GligarPicker.IMAGES_RESULT);
            certUri=Uri.fromFile(new File(certPath[0]));
            certView.setImageURI(certUri);

        }

        if (requestCode==Constants.REPORT_IMAGE_REQUEST_CODE){
            String[] reportPath = data.getExtras().getStringArray(GligarPicker.IMAGES_RESULT);
            reportUri=Uri.fromFile(new File(reportPath[0]));
            reportView.setImageURI(reportUri);

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}