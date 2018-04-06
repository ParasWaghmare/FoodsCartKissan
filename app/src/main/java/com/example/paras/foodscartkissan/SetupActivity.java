package com.example.paras.foodscartkissan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {

    private CircleImageView mCircleImage;
    private EditText mPersonName;
    private Button mSetupBtn;
    private  static final int GALLERY_REQUEST=1;
    private Uri mSetupImageUri = null ;
    private DatabaseReference mDatabaseUsers;
    private FirebaseAuth mSetupAuth;
    private FirebaseStorage mDatabaseStorage;
    private StorageReference mStorageImage;
    private ProgressDialog mSetupProgress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("User");
        mSetupAuth = FirebaseAuth.getInstance();
        mDatabaseStorage = FirebaseStorage.getInstance();
        mStorageImage   = FirebaseStorage.getInstance().getReference().child("profile_images");
        mSetupProgress = new ProgressDialog(this);



        mCircleImage = findViewById(R.id.personImage);
        mPersonName = findViewById(R.id.personName);
        mSetupBtn = findViewById(R.id.setupBtn);

        mCircleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent gallery_intent = new Intent();
                gallery_intent.setAction(Intent.ACTION_GET_CONTENT);
                gallery_intent.setType("image/*");
                startActivityForResult(gallery_intent, GALLERY_REQUEST);
            }
        });

        mSetupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSetupAccount();
            }
        });


    }

    private void startSetupAccount() {

        final String name = mPersonName.getText().toString().trim();
        final String user_id = mSetupAuth.getCurrentUser().getUid();
        if(!TextUtils.isEmpty(name) && mSetupImageUri != null){
            mSetupProgress.setMessage("Finishing the setup....");
            mSetupProgress.show();

            StorageReference setupFilepath = mStorageImage.child(mSetupImageUri.getLastPathSegment());
            setupFilepath.putFile(mSetupImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String download_uri = taskSnapshot.getDownloadUrl().toString();
                    mDatabaseUsers.child(user_id).child("name").setValue(name);
                    mDatabaseUsers.child(user_id).child("image").setValue(download_uri);
                    mSetupProgress.dismiss();
                    Intent main_Intent = new Intent(SetupActivity.this,MainActivity.class);
                    main_Intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(main_Intent );


                }
            });


        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .start(this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mSetupImageUri = result.getUri();
                mCircleImage.setImageURI(mSetupImageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
            }
        }
    }
}
