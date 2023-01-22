package com.example.bursary.ui.gallery;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.bursary.FetchUserData;
import com.example.bursary.FirstApplicationFragment;
import com.example.bursary.MainActivity;
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
import com.google.firebase.components.BuildConfig;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.opensooq.supernova.gligar.GligarPicker;

import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    //request codes
    //write external storage request code
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE=1;

    private void requestStoragePermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            new MaterialAlertDialogBuilder(getContext())
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }else{
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }
    }

    private TextView name,phone,email,dob,admNo,course,institution_number,institution,bank_name,bank_account_number,bank_branch,district,division,location,ward,constituency,sub_location,village, yearOfStudy;

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
        reference=database.getReference("requests");

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
        yearOfStudy=view.findViewById(R.id.yearOfStudy);

        // get a reference to the user's application data in the Firebase Realtime Database

        Query query=reference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot ds:snapshot.getChildren()){
                        String name1=ds.child("name").getValue(String.class);
                        String email1=ds.child("email").getValue(String.class);
                        String phone1=ds.child("phone").getValue(String.class);
                        String dob1=ds.child("date").getValue(String.class);
                        String admNo1=ds.child("admNo").getValue(String.class);
                        String course1=ds.child("course").getValue(String.class);
                        String institution1=ds.child("institution").getValue(String.class);
                        String institution_number1=ds.child("institutionPhoneNo").getValue(String.class);
                        String bank_name1=ds.child("bankName").getValue(String.class);
                        String bank_account_number1=ds.child("bankAccountNo").getValue(String.class);
                        String bank_branch1=ds.child("bankBranch").getValue(String.class);
                        String district1=ds.child("district").getValue(String.class);
                        String division1=ds.child("division").getValue(String.class);
                        String location1=ds.child("location").getValue(String.class);
                        String ward1=ds.child("ward").getValue(String.class);
                        String constituency1=ds.child("constituency").getValue(String.class);
                        String sub_location1=ds.child("subLocation").getValue(String.class);
                        String village1=ds.child("village").getValue(String.class);
                        String yearOfStudy1=ds.child("yearOfStudy").getValue(String.class);

                        name.setText(name1);
                        email.setText(email1);
                        phone.setText(phone1);
                        dob.setText(dob1);
                        admNo.setText(admNo1);
                        course.setText(course1);
                        institution.setText(institution1);
                        institution_number.setText(institution_number1);
                        bank_name.setText(bank_name1);
                        bank_account_number.setText(bank_account_number1);
                        bank_branch.setText(bank_branch1);
                        district.setText(district1);
                        division.setText(division1);
                        location.setText(location1);
                        ward.setText(ward1);
                        constituency.setText(constituency1);
                        sub_location.setText(sub_location1);
                        village.setText(village1);
                        yearOfStudy.setText(yearOfStudy1);
                    }
                }
                // else if the user has not applied for a bursary, create a dialog asking them to apply, then send them to FirstApplicationFragment
                else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                    builder.setTitle("Apply for a bursary");
                    builder.setMessage("Looks like you're applying for a bursary for the first time. Click OK to apply");
                    builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // navigate to the FirstApplicationFragment
                            Navigation.findNavController(view).navigate(R.id.nav_firstApplication);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //requesting permission to write to external storage
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestStoragePermission();
        }

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
                                                Upload upload=new Upload(downloadUrls, FirebaseAuth.getInstance().getCurrentUser().getUid(),String.valueOf(System.currentTimeMillis()),genderButton.getText().toString(),"Pending", Calendar.getInstance().getTime().toString(),name.getText().toString(),phone.getText().toString(),email.getText().toString(),dob.getText().toString(),admNo.getText().toString(),course.getText().toString(),institution_number.getText().toString(),institution.getText().toString(),bank_name.getText().toString(),bank_account_number.getText().toString(),bank_branch.getText().toString(),district.getText().toString(),division.getText().toString(),location.getText().toString(),ward.getText().toString(),constituency.getText().toString(),sub_location.getText().toString(),village.getText().toString(),yearOfStudy.getText().toString());
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
                                                                //create pdf and send to email
                                                                createPdf();
                                                            }

                                                            private void createPdf() {
                                                                //creating a pdf document
                                                                PdfDocument document=new PdfDocument();
                                                                //paint
                                                                Paint paint=new Paint();
                                                                //paint.setColor(Color.parseColor("#FFFFFF"));
                                                                //paint.setStyle(Paint.Style.FILL);
                                                                //paint.setTextAlign(Paint.Align.CENTER);
                                                                //paint.setTextSize(50f);
                                                                //paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                                //paint.setAntiAlias(true);
                                                                //paint.setUnderlineText(true);
                                                                //paint.setFakeBoldText(true);
                                                                //paint.setStrikeThruText(true);
                                                                //paint.setShadowLayer(1f,0f,1f,Color.BLUE);
                                                                //paint.setTextSkewX(-0.25f);
                                                                //paint.setTextScaleX(1.5f);
                                                                //paint.setLetterSpacing(0.1f);
                                                                //paint.setSubpixelText(true);
                                                                //paint.setLinearText(true);
                                                                //paint.setElegantTextHeight(true);
                                                                //paint.setDither(true);
                                                                //paint.setFilterBitmap(true);
                                                                //paint.setAlpha(0x80);
                                                                //paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
                                                                //paint.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));
                                                                //paint.setPathEffect(new CornerPathEffect(10));
                                                                //paint.setShader(new LinearGradient(0,0,100,100, Color.RED,Color.BLUE, Shader.TileMode.REPEAT));
                                                                //paint.setColorFilter(new LightingColorFilter(0xFFFF00FF, 0x00000000));
                                                                //paint.setRasterizer(new Rasterizer());

                                                                //creating a page description
                                                                PdfDocument.PageInfo pageInfo=new PdfDocument.PageInfo.Builder(1200,2010,1).create();
                                                                //start a page
                                                                PdfDocument.Page page=document.startPage(pageInfo);
                                                                //drawing something on the page
                                                                Canvas canvas=page.getCanvas();
                                                                //canvas.drawCircle(500,500,200,paint);
                                                                //canvas.drawBitmap(bitmap,0,0,paint);
                                                                //canvas.drawText("Hello World",500,500,paint);
                                                                //canvas.drawLine(100,100,500,500,paint);
                                                                //canvas.drawRoundRect(100,100,500,500,50,50,paint);
                                                                //canvas.drawRect(100,100,500,500,paint);
                                                                //canvas.drawOval(100,100,500,500,paint);

                                                                //drawing the watermark
                                                                Bitmap bitmaps= BitmapFactory.decodeResource(getResources(),R.drawable.img_1);
                                                                //aligning the watermark to the center of the page
                                                                int x=pageInfo.getPageWidth()/2-bitmaps.getWidth()/2;
                                                                int y=pageInfo.getPageHeight()/2-bitmaps.getHeight()/2;
                                                                canvas.drawBitmap(bitmaps,x,y,paint);
                                                                //reducing the opacity of the watermark
                                                                paint.setAlpha(130);

                                                                //drawing the title
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.CENTER);
                                                                paint.setTextSize(50f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Application form",600,100,paint);

                                                                //drawing the logo
                                                                Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.lamulogo);

                                                                //aligning the logo below the title and to the left of the title
                                                                //making the size of the logo smaller
                                                                bitmap=Bitmap.createScaledBitmap(bitmap,200,200,true);
                                                                //aligning the logo below the title and to the right of the title
                                                                canvas.drawBitmap(bitmap,1000,50,paint);

                                                                //drawing the national logo
                                                                Bitmap bitmap1= BitmapFactory.decodeResource(getResources(),R.drawable.img_2);

                                                                //aligning the logo below the title and to the left of the title
                                                                //making the size of the logo smaller
                                                                bitmap1=Bitmap.createScaledBitmap(bitmap1,200,200,true);
                                                                //aligning the logo below the title and to the right of the title
                                                                canvas.drawBitmap(bitmap1,50,50,paint);

                                                                //drawing the title
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.CENTER);
                                                                paint.setTextSize(50f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("County Government of Lamu",600,200,paint);

                                                                //drawing the subheading
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.CENTER);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("University and Collage Bursary Application Form",600,300,paint);
                                                                //underlining the subheading
                                                                canvas.drawLine(100,302,1100,302,paint);

                                                                //drawing the subheading
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.CENTER);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("For the year 2022/2023",600,350,paint);

                                                                //form number
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Form Number: ",50,450,paint);
                                                                //adding a dotted line after the form number
                                                                canvas.drawLine(250,450,500,450,paint);

                                                                //ward
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.RIGHT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Ward: "+ward.getText().toString(),1150,450,paint);

                                                                //drawing the subheading
                                                                //Instructions

                                                                //drawing the subheading
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Part A: Personal Details",50,550,paint);
                                                                //underlining the subheading
                                                                //canvas.drawLine(50,550,390,550,paint);

                                                                //full name
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Full Name: "+name.getText().toString(),50,600,paint);

                                                                //gender
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.RIGHT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("sex :"+genderButton.getText().toString(),1150,600,paint);

                                                                //date of birth
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Date of Birth: "+dob.getText().toString(),50,650,paint);

                                                                //adm number
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Admission Number: "+admNo.getText().toString(),50,700,paint);

                                                                //telephone number
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Telephone Number: "+phone.getText().toString(),50,750,paint);

                                                                //email
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Email: "+email.getText().toString(),50,800,paint);

                                                                //drawing the subheading
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Part B: Institution details",50,850,paint);

                                                                //institution name
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Institution Name: "+institution.getText().toString(),50,900,paint);

                                                                //course
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Course: "+course.getText().toString(),50,950,paint);

                                                                //year of study
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Year of Study: "+yearOfStudy.getText().toString(),50,1000,paint);

                                                                //drawing the subheading
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(25f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Bank Details",50,1050,paint);

                                                                //account name/bank name
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Bank name: "+bank_name.getText().toString(),50,1100,paint);

                                                                //account number
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Account Number: "+bank_account_number.getText().toString(),50,1150,paint);

                                                                //bank name
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Bank branch: "+bank_branch.getText().toString(),50,1200,paint);

                                                                //drawing the subheading
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Part C: Residence",50,1250,paint);

                                                                //district
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("District: "+district.getText().toString(),50,1300,paint);

                                                                //division
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Division: "+division.getText().toString(),50,1350,paint);

                                                                //location
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Location: "+location.getText().toString(),50,1400,paint);

                                                                //ward
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Ward: "+ward.getText().toString(),50,1450,paint);

                                                                //constituency
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Constituency: "+constituency.getText().toString(),50,1500,paint);

                                                                //sub-location
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Sub-location: "+sub_location.getText().toString(),50,1550,paint);

                                                                //village
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Village: "+village.getText().toString(),50,1600,paint);

                                                                //drawing the subheading
                                                                //Declaration
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Part D: Declaration",50,1650,paint);
                                                                //underlining the declaration

                                                                //declaration
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(20f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("I, "+name.getText().toString()+" declare to the best of my knowledge that the information given is true and accurate.",50,1720,paint);

                                                                //parent's name and signature
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Parent's Name: ",50,1775,paint);
                                                                //adding a dotted line after the parent's name
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawLine(300,1775,500,1775,paint);
                                                                //signature
                                                                canvas.drawText("Signature: ",50,1825,paint);
                                                                //adding a dotted line after the signature
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawLine(300,1825,500,1825,paint);
                                                                //date
                                                                canvas.drawText("Date: ",50,1875,paint);
                                                                //adding a dotted line after the date
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawLine(300,1875,500,1875,paint);

                                                                //drawing the subheading
                                                                //committee's recommendation
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Part E: Committee's Recommendation",50,1925,paint);

                                                                //underlining the committee's recommendation
                                                                //paint.setColor(Color.BLACK);
                                                                //paint.setStyle(Paint.Style.FILL);
                                                                //paint.setTextAlign(Paint.Align.LEFT);
                                                                //paint.setTextSize(30f);
                                                                //paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                                //paint.setAntiAlias(true);
                                                                //canvas.drawLine(50,1750,500,1750,paint);

                                                                //next page
                                                                document.finishPage(page);
                                                                //page 2
                                                                PdfDocument.Page page2 = document.startPage(pageInfo);
                                                                Canvas canvas2 = page2.getCanvas();


                                                                //paint.setColor(Color.GRAY);
                                                                //paint.setStyle(Paint.Style.FILL);
                                                                //paint.setTextAlign(Paint.Align.CENTER);
                                                                //paint.setTextSize(100f);
                                                                //paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                                //paint.setAntiAlias(true);
                                                                //canvas2.drawText("CONFIDENTIAL",297,421,paint);

                                                                //committee's recommendation. Bursary approved ( ) Not Approve ( )
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas2.drawText("1. Bursary approved ( ) Not Approve ( )",50,50,paint);
                                                                //chairman's name
                                                                canvas2.drawText("Chairman's Name: ",50,100,paint);
                                                                //adding a dotted line after the chairman's name
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas2.drawLine(300,100,500,100,paint);
                                                                //signature
                                                                canvas2.drawText("Signature: ",50,150,paint);
                                                                //adding a dotted line after the signature
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas2.drawLine(300,150,500,150,paint);
                                                                //date
                                                                canvas2.drawText("Date: ",50,200,paint);
                                                                //adding a dotted line after the date
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas2.drawLine(300,200,500,200,paint);

                                                                //secretary's name
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas2.drawText("Secretary's Name: ",50,250,paint);
                                                                //adding a dotted line after the secretary's name
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas2.drawLine(300,250,500,250,paint);
                                                                //signature
                                                                canvas2.drawText("Signature: ",50,300,paint);
                                                                //adding a dotted line after the signature
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas2.drawLine(300,300,500,300,paint);
                                                                //date
                                                                canvas2.drawText("Date: ",50,350,paint);
                                                                //adding a dotted line after the date
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas2.drawLine(300,350,500,350,paint);

                                                                //official stamp
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas2.drawText("Official Stamp: ",50,400,paint);
                                                                //adding a dotted line after the official stamp
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas2.drawLine(300,400,500,400,paint);



                                                                //saving the pdf
                                                                document.finishPage(page2);

                                                                //saving the pdf after checking for permission using onRequestPermissionsResult

                                                                File file = new File(Environment.getExternalStorageDirectory(), "/"+name.getText().toString()+".pdf");


                                                                try {
                                                                    document.writeTo(new FileOutputStream(file));
                                                                    Toast.makeText(getActivity(), "PDF saved", Toast.LENGTH_SHORT).show();
                                                                } catch (IOException e) {
                                                                    e.printStackTrace();
                                                                    Toast.makeText(getActivity(), "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();

                                                                    //logging the error

                                                                    Log.e("main", "error "+e.toString());
                                                                }

                                                                //closing the document
                                                                document.close();



                                                                //sending the pdf to the email
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