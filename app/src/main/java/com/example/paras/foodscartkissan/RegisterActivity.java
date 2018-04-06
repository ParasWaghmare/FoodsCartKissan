package com.example.paras.foodscartkissan;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout mRegisterEmail;
    private TextInputLayout mRegisterPasswd;
    private  TextInputLayout mRegisterName;
    private Button mRegisterBtn;
    private FirebaseAuth mAuth;
    private ProgressDialog mRegisterDialog;
    private DatabaseReference mDataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isConnected(RegisterActivity.this)) builDialog(RegisterActivity.this).show();
        else{

        }

        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mDataRef = FirebaseDatabase.getInstance().getReference().child("User");



        mRegisterDialog =new ProgressDialog(this);


        mRegisterName = findViewById(R.id.registerName);
        mRegisterEmail = findViewById(R.id.registerEmail);
        mRegisterPasswd = findViewById(R.id.registerPassword);
        mRegisterBtn = findViewById(R.id.registerBtn);

        ConstraintLayout constraintLayout = findViewById(R.id.reg_gradient);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mRegisterEmail.getEditText().getText().toString();
                String password = mRegisterPasswd.getEditText().getText().toString();
                String display_name = mRegisterName.getEditText().getText().toString();
                String Expn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                                +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                                +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                                +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

                if(!TextUtils.isEmpty(display_name) && (!TextUtils.isEmpty(email) && email.matches(Expn) && email.length()>0) && !TextUtils.isEmpty(password)){
                    mRegisterDialog.setTitle("Registering User");
                    mRegisterDialog.setMessage("Please wait while create your account");
                    mRegisterDialog.show();
                    mRegisterDialog.setCanceledOnTouchOutside(false);
                    register_user(display_name,email,password);
                }

            }
        });

    }

    private void register_user(final String display_name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    mRegisterDialog.dismiss();
                    String user_id = mAuth.getCurrentUser().getUid();
                    DatabaseReference current_user_db = mDataRef.child(user_id);
                    current_user_db.child("name").setValue(display_name);
                    current_user_db.child("image").setValue("default");
                    Intent mainIntent = new Intent(RegisterActivity.this,MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainIntent);
                    finish();

                }
                else{
                    mRegisterDialog.hide();
                    Toast.makeText(RegisterActivity.this,"Can't register please check the form detail are filled completely",Toast.LENGTH_LONG).show();

                }

            }
        });
    }
    public boolean isConnected(Context context){

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =cm.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return  true;
             else return false;


        }else
            return false;


    }

    public AlertDialog.Builder builDialog(Context c){


        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Conncetion");
        builder.setMessage("Check  your mobile or wifi connection is active  ");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        return builder;
    }


}
