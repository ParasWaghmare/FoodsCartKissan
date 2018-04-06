package com.example.paras.foodscartkissan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileEdit extends AppCompatActivity {

    private CircleImageView editProfileImage;
    private EditText mEditProfileName;
    private EditText mMobileNumber;
    private EditText mEmailId;
    private Button mSaveChanges;
    private  static final int GALLERY_REQUEST=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        mEditProfileName = findViewById(R.id.editProfileName);
        mMobileNumber = findViewById(R.id.editMobileNumber);
        mEmailId = findViewById(R.id.editEmail);

        editProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile_intent = new Intent();
                profile_intent.setAction(Intent.ACTION_GET_CONTENT);
                profile_intent.setType("image/*");
                startActivityForResult(profile_intent, GALLERY_REQUEST);
            }
        });





    }
}
