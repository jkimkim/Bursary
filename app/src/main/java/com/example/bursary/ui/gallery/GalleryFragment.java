package com.example.bursary.ui.gallery;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
                                                                Bitmap bitmaps= BitmapFactory.decodeResource(getResources(),R.drawable.lamulogo);
                                                                canvas.drawBitmap(bitmaps,0,0,paint);

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
                                                                canvas.drawBitmap(bitmap,50,50,paint);

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

                                                                //drawing the subheading
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.CENTER);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("For the year 2022/2023",600,350,paint);

                                                                //form number
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Form Number: *******",50,450,paint);

                                                                //ward
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.RIGHT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Ward: "+ward.getText().toString(),200,450,paint);

                                                                //drawing the subheading
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Personal Details",50,550,paint);

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
                                                                canvas.drawText("sex :"+genderButton.getText().toString(),200,600,paint);

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
                                                                canvas.drawText("Year of Study: ",50,1000,paint);

                                                                //drawing the subheading
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Part C: Residence",50,1050,paint);

                                                                //district
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("District: "+district.getText().toString(),50,1100,paint);

                                                                //division
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Division: "+division.getText().toString(),50,1150,paint);

                                                                //location
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Location: "+location.getText().toString(),50,1200,paint);

                                                                //ward
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Ward: "+ward.getText().toString(),50,1250,paint);

                                                                //constituency
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Constituency: "+constituency.getText().toString(),50,1300,paint);

                                                                //sub-location
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Sub-location: "+sub_location.getText().toString(),50,1350,paint);

                                                                //village
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Village: "+village.getText().toString(),50,1400,paint);

                                                                //the end of the form
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("End of Form",50,1450,paint);

                                                                //drawing the signature
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Signature: ",50,1500,paint);


                                                                //drawing the date
                                                                paint.setColor(Color.BLACK);
                                                                paint.setStyle(Paint.Style.FILL);
                                                                paint.setTextAlign(Paint.Align.LEFT);
                                                                paint.setTextSize(30f);
                                                                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                                paint.setAntiAlias(true);
                                                                canvas.drawText("Date: ",50,1550,paint);

                                                                //saving the pdf
                                                                document.finishPage(page);
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
                                                                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                                                                emailIntent.setType("text/plain");
                                                                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {email.getText().toString()});
                                                                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Bursary Application formForm");
                                                                emailIntent.putExtra(Intent.EXTRA_TEXT, "Please find attached document");
                                                                emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+file));

                                                                startActivity(Intent.createChooser(emailIntent, "Send email..."));


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