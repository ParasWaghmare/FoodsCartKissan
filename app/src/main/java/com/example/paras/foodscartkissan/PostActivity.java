package com.example.paras.foodscartkissan;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


import java.util.Random;


public class PostActivity extends AppCompatActivity {
    private EditText mProductName;
    private EditText mPrice;
    private EditText mPostDiscription;
    private Button mSellBtn;
    private static final int GALLERY_REQUEST = 1;
    private StorageReference mStorage;
    private DatabaseReference mDatabaseRefLog;
    private ProgressDialog mProgress;
    private ImageView mImageView;
    private Uri imageUri = null;
    private static final int PERMISSIONS_REQUEST_READ_STORAGE = 100;
    private FirebaseAuth mPostAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSIONS_REQUEST_READ_STORAGE);

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabaseRefLog = FirebaseDatabase.getInstance().getReference().child("Sell");
        mPostAuth = FirebaseAuth.getInstance();
        mCurrentUser = mPostAuth.getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("User").child(mCurrentUser.getUid());

        mProgress = new ProgressDialog(this);

        mImageView = findViewById(R.id.imagePost);
        mProductName = findViewById(R.id.mPostName);
        mPrice = findViewById(R.id.mPostPrice);
        mPostDiscription = findViewById(R.id.mPostDesc);
        mSellBtn = findViewById(R.id.mPostSellBtn);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent gallery_intent = new Intent(Intent.ACTION_GET_CONTENT);
                gallery_intent.setType("image/*");
                startActivityForResult(gallery_intent,GALLERY_REQUEST);


            }
        });

        mSellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sellToPost();
            }
        });
    }

    private void sellToPost() {


        final String name_val = mProductName.getText().toString();
        final String price_val = mPrice.getText().toString();
        final String desc_val = mPostDiscription.getText().toString();
        if (!TextUtils.isEmpty(name_val) && !TextUtils.isEmpty(price_val) && !TextUtils.isEmpty(desc_val) && imageUri !=null){

            mProgress.setMessage("Uploading the post ...");
            mProgress.setCanceledOnTouchOutside(false);
            mProgress.show();

            StorageReference filepath = mStorage.child("sell_image").child(imageUri+".jpg");

            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    final Uri downloadUri = taskSnapshot.getDownloadUrl();
                    final DatabaseReference newSell = mDatabaseRefLog.push();


                    mDatabaseRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            newSell.child("name").setValue(name_val);
                            newSell.child("price").setValue(price_val);
                            newSell.child("desc").setValue(desc_val);
                            newSell.child("image").setValue(downloadUri.toString());
                            newSell.child("uid").setValue(mCurrentUser.getUid());
                            newSell.child("username").setValue(dataSnapshot.child("name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        startActivity(new Intent(PostActivity.this,MainActivity.class));


                                    }
                                }
                            });

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    mProgress.dismiss();

                }
            });
        }




        }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK){

                imageUri = data.getData();
                mImageView.setImageURI(imageUri);

                CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(16,9)
                    .start(this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                imageUri = result.getUri();
                 mImageView.setImageURI(imageUri);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

    private String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(20);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
}
