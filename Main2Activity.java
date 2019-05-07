package com.example.nakao0411.Record_Summary;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Main2Activity extends Activity {
    ImageView image3;
    ImageView image23;
    myApp myapp;
    Bitmap[] bitmap;
    Bitmap[] bitmap2;
    Bitmap bit;
    Button button;
    Button button2;
    Circle_Write2 CW2;
    int set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        myapp = (myApp) this.getApplication();
        bitmap = myapp.getBitmap5();
        bitmap2 = myapp.getBitmap();
        CW2 = new Circle_Write2(this);
        image3 = findViewById(R.id.image);
        image23 = findViewById(R.id.image2);
        button = findViewById(R.id.button);
        bit = mixbitmap(bitmap[1],bitmap[0]);
        set=0;
        set = myapp.getting();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, Remake_Activity.class);  //インテントの作成
                startActivity(intent);
            }
        });
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(set == 0){
                    Intent intent = new Intent(Main2Activity.this, Result_Activity.class);  //インテントの作成
                    startActivity(intent);
                }
                else {
                    Bitmap basa = BitmapFactory.decodeResource(getResources(), R.drawable.bluck2);//透明

                    Bitmap bluck2 = BitmapFactory.decodeResource(getResources(), R.drawable.bluck);//黒色の画像
                    Bitmap err2 = BitmapFactory.decodeResource(getResources(), R.drawable.baumu);//エラー画像
                    myapp.setBitmap(bitmap2[0],bit);
                    Bitmap cir =CW2.Circle_Write3(bit,bluck2,err2,basa);
                    Intent intent = new Intent(Main2Activity.this, Result_Activity.class);  //インテントの作成
                    startActivity(intent);
                }

            }
        });


        image23.setImageBitmap(bit);
        image3.setImageBitmap(bitmap2[0]);
//        image23.setImageBitmap(bitmap[0]);
    }

    Bitmap mixbitmap(Bitmap map1,Bitmap map2){
        map2 = Bitmap.createScaledBitmap(map2,map1.getWidth(),map1.getHeight(),false);

        Canvas canvas = new Canvas(map1);
        canvas.drawBitmap(map2,0,0,new Paint());
        return  map1;
    }

}
