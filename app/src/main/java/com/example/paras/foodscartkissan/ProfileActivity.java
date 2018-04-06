package com.example.paras.foodscartkissan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private CircleImageView mProfileView;
    private TextView userName;
    private TextView mobNumber;
    private TextView emailId;
    private Button mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        //String uid = mCurrentUser.getUid();
        //DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("User").child(uid);

        mProfileView = findViewById(R.id.profile_image);
        userName = findViewById(R.id.profile_username);
        mobNumber = findViewById(R.id.mob_number);
        emailId = findViewById(R.id.email_id);
        mButton = findViewById(R.id.editButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(ProfileActivity.this, ProfileEdit.class);
                startActivity(editIntent);
            }
        });

       /* mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

              //  String username = dataSnapshot.child("name").getValue().toString();
                //String mobnumber = dataSnapshot.child("number").getValue().toString();
                //String useremail = dataSnapshot.child("email").getValue().toString();

                //String image = dataSnapshot.child("image").getValue().toString();
                //String thumb_nail = dataSnapshot.child("thumb_image").getValue().toString();

                //userName.setText(username);
                //mobNumber.setText(mobnumber);
                //emailId.setText(useremail);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this,"profile page error",Toast.LENGTH_LONG);

            }
        });

*/

    }
}
