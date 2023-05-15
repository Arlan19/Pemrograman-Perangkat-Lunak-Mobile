package com.allacsta.firebaseloginandregis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class StorageActivity extends AppCompatActivity {

    public static final String TAG = "StorageActivity";

    private ImageView imgView;
    private EditText fileName;
    private Button btnUpload, btnPrev, btnNext, btnDownload;
    private ProgressDialog mProgressDialog;

    private ArrayList<String> listImg;
    private int img_position;

    private StorageReference mStorage;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        imgView = findViewById(R.id.imageView);
        fileName = findViewById(R.id.et_image_name);
        btnUpload = findViewById(R.id.btn_upload);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        btnDownload = findViewById(R.id.btn_download);

        mAuth = FirebaseAuth.getInstance();

        mStorage = FirebaseStorage.getInstance().getReference();

        listImg = new ArrayList<>();

        addFilePath();

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (img_position > 0){
                    img_position = img_position - 1;
                    loadImage();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (img_position < listImg.size() - 1){
                    img_position = img_position + 1;
                    loadImage();
                }
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = fileName.getText().toString().trim();
                if (!fileName.equals("")){

                    Uri uri = Uri.fromFile(new File(listImg.get(img_position)));
                    StorageReference storageReference = mStorage.child("img/"+name+".jpg");
                    storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();
                            while(!downloadUri.isComplete());
                            Uri url = downloadUri.getResult();
                            Toast.makeText(StorageActivity.this, "Upload Succes on Url", Toast.LENGTH_SHORT).show();
//                            if (!downloadUri.isComplete()){
//                                Uri url = downloadUri.getResult();
//                                Toast.makeText(StorageActivity.this, "Upload Succes on Url", Toast.LENGTH_SHORT).show();
//                            };
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(StorageActivity.this, "Upload Failed, Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    fileName.setError("Isi nama file terlebih dahulu");
                }
            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = fileName.getText().toString().trim();
                if (!fileName.equals("")){
                    StorageReference storageRef = mStorage.child("img/"+name+".jpg");

                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(StorageActivity.this).load(uri).into(imgView);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(StorageActivity.this, "Download Failed, Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Download dengan Bytes

//                    final long ONE_MEGABYTE = 1024 * 1024;
//                    storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                        @Override
//                        public void onSuccess(byte[] bytes) {
//                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                            imgView.setImageBitmap(bitmap);
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(StorageActivity.this, "Download Failed, Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });


                }else {
                    fileName.setError("Isi nama file terlebih dahulu");
                }
            }
        });
    }


    private void loadImage() {
        try {
            String path = listImg.get(img_position);
            File file = new File(path,"");
            Bitmap bmp = BitmapFactory.decodeStream(new FileInputStream(file));
//            imgView.setImageResource(R.drawable.black_bg);
            imgView.setImageBitmap(bmp);
        }catch (Exception ex){
            Toast.makeText(this, "Error "+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addFilePath() {

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 1);
        String path = System.getenv("EXTERNAL_STORAGE");
        listImg.add(path+"/Pictures/img1.jpg/");
        listImg.add(path+"/Pictures/img2.jpg/");
        listImg.add(path+"/Pictures/img3.jpg/");
        loadImage();
    }
}