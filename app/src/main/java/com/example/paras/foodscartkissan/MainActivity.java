package com.example.paras.foodscartkissan;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.FitWindowsFrameLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import io.reactivex.annotations.NonNull;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private RecyclerView mSellList;
    private DatabaseReference mDatabaseMain;
    private DatabaseReference mDatabaseUsr;
    private ShareActionProvider mShareActionProvider;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                if(mAuth.getCurrentUser() == null){
                    Intent startIntent = new Intent(MainActivity.this,StartActivity.class);
                    startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(startIntent );
                }   


            }

        };


        mDatabaseMain = FirebaseDatabase.getInstance().getReference().child("Sell");
        mDatabaseUsr = FirebaseDatabase.getInstance().getReference().child("User");
        mDatabaseUsr.keepSynced(true);
        mDatabaseMain.keepSynced(true);

        mSellList = findViewById(R.id.sell_post);
        mSellList.setHasFixedSize(true);
        mSellList.setLayoutManager(new LinearLayoutManager(this));
        checkUserExist();


    }

    @Override
    protected void onStart() {
        super.onStart();


        mAuth.addAuthStateListener(mAuthListener);
        FirebaseRecyclerAdapter<Sell,SellViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Sell, SellViewHolder>(

                    Sell.class,
                    R.layout.sell_row,
                    SellViewHolder.class,
                    mDatabaseMain

            ) {
                @Override
                protected void populateViewHolder(SellViewHolder viewHolder, Sell model, int position) {

                    viewHolder.setTitle(model.getName());
                    viewHolder.setPrice(model.getPrice());
                    viewHolder.setDesc(model.getDesc());
                    viewHolder.setImage(getApplicationContext(),model.getImage());

                    viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            CharSequence options[] = new CharSequence[]{
                              "yes", "no"
                            };

                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            View view1 = getLayoutInflater().inflate(R.layout.activity_main,null);
                            builder.setTitle("Do you want to buy this?");
                            builder.setItems(options, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    if (position==0){
                                        Toast.makeText(MainActivity.this,"Your buying request has been sent to the seller",Toast.LENGTH_LONG).show();
                                        Intent newIntent = new Intent(MainActivity.this,MainActivity.class);
                                        startActivity(newIntent);

                                    }
                                    if(position==1){
                                        Intent mainIntent = new Intent(MainActivity.this,MainActivity.class);
                                        startActivity(mainIntent);

                                    }
                                }
                            });
                            builder.setView(view1);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });

                }
            };
            mSellList.setAdapter(firebaseRecyclerAdapter);




    }
    private void checkUserExist() {


            if(mAuth.getCurrentUser()!=null) {

                final String user_Id = mAuth.getCurrentUser().getUid();
                mDatabaseUsr.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.hasChild(user_Id)) {

                            Intent setupIntent = new Intent(MainActivity.this, SetupActivity.class);
                            setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(setupIntent);

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
    }






    public static class SellViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public SellViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }
        public void setTitle(String name){

            TextView sell_title = (TextView) mView.findViewById(R.id.itemName);
            sell_title.setText(name);
        }

        public void setPrice(String price){
            TextView sell_price = (TextView) mView.findViewById(R.id.itemPrice);
            sell_price.setText(price);
        }
        public void setDesc(String desc){
            TextView sell_desc = (TextView) mView.findViewById(R.id.itemDesc);
            sell_desc.setText(desc);
        }

        public void setImage(Context ctx,String image){

            ImageView imageView = (ImageView) mView.findViewById(R.id.sellImage);
            Picasso.with(ctx).load(image).into(imageView);

        }




    }

    private void sendToStart() {
        Intent start_intent=new Intent(MainActivity.this,StartActivity.class);
        startActivity(start_intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){

            case R.id.action_add:
                startActivity(new Intent(MainActivity.this,PostActivity.class));
                break;

            case R.id.main_logout:
                    FirebaseAuth.getInstance().signOut();
                    sendToStart();
                    break;

            case R.id.crop_setting:
                    Intent crop_intent = new Intent(MainActivity.this,CropActivity.class);
                    startActivity(crop_intent);
                    break;
            case R.id.wheather_setting:
                    Intent wheather_intent = new Intent(MainActivity.this,WheatherActivity.class);
                    startActivity(wheather_intent);
                    break;
            case R.id.about_setting:
                    Intent vision_intent =  new Intent (MainActivity.this,AboutActivity.class);
                    startActivity(vision_intent);
                    break;
            case R.id.profile_setting:
                    Intent profile_intent = new Intent(MainActivity.this,ProfileActivity.class);
                    startActivity(profile_intent);
                    break;

        }

        return  true;
    }
}
