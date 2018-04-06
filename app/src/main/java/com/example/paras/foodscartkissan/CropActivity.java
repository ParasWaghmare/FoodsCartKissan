package com.example.paras.foodscartkissan;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.Toast;

public class CropActivity extends AppCompatActivity {

    private GridLayout mainGrid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        mainGrid = (GridLayout) findViewById(R.id.mainGrid);

       setSingleEvent(mainGrid);
       // setToggleEvent(mainGrid);
    }

    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        int i;
        for (  i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(finalI==0) {

                        Intent intent = new Intent(CropActivity.this, CropWheatActivity.class);
                        intent.putExtra("info", "This is activity from card item index  " + finalI);
                        startActivity(intent);
                    }else if(finalI==1){

                        Intent intent = new Intent(CropActivity.this, CropRiceActivity.class);
                        intent.putExtra("info", "This is activity from card item index  " + finalI);
                        startActivity(intent);


                    }else if(finalI==2){

                        Intent intent = new Intent(CropActivity.this, CropTeaActivity.class);
                        intent.putExtra("info", "This is activity from card item index  " + finalI);
                        startActivity(intent);


                    }else if(finalI==3){

                        Intent intent = new Intent(CropActivity.this, CropCottonActivity.class);
                        intent.putExtra("info", "This is activity from card item index  " + finalI);
                        startActivity(intent);

                    }else if(finalI==4){

                        Intent intent = new Intent(CropActivity.this, CropCoffeeActivity.class);
                        intent.putExtra("info", "This is activity from card item index  " + finalI);
                        startActivity(intent);

                    }else if(finalI==5){

                        Intent intent = new Intent(CropActivity.this, CropSugarcaneActivity.class);
                        intent.putExtra("info", "This is activity from card item index  " + finalI);
                        startActivity(intent);

                    }

                }
            });
        }
    }

  /*  private void setSingleEvent(GridLayout mainGrid) {

        for (int i = 0 ; i<mainGrid.getChildCount() ; i++){

            final  int finalI = i;

            CardView cardView = (CardView) mainGrid.getChildAt(i);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(CropActivity.this,"Clicked at index " +  finalI,Toast.LENGTH_LONG).show();


                }
            });
        }
    }*/
}
