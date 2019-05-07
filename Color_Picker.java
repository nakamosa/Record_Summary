package com.example.nakao0411.Record_Summary;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Color_Picker extends Activity {

    private View targetView;
    private ImageView imageView;
    String hex ;
    String hex2 ;
    String hex3;
    int RE ;
    int GE ;
    int BL ;
    int RGB = -1;
    Bitmap bitmap;
    myApp myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color__picker);
        myApp = (myApp) this.getApplication();
        bitmap =myApp.getbitmap2();


        targetView = findViewById(R.id.target);
        imageView = findViewById(R.id.image);
        imageView.setImageBitmap(bitmap);
        Button button = (Button) findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApp.setTestString(RE,GE,BL,RGB);
                Intent intent = new Intent(Color_Picker.this, Setting_Activity.class);  //インテントの作成
                startActivity(intent);
                 }
        });

    }

    public boolean onTouchEvent(MotionEvent event) {
        Bitmap ioio = captureUsingDrawingCache();
        imageView.setImageBitmap(ioio);

        //X軸の取得
        float pointX = event.getX();

        //Y軸の取得
        float pointY = event.getY();


        RGB = ioio.getPixel((int) pointX, (int) pointY);

        rect(RGB);

        return true;
    }

    private Bitmap captureUsingDrawingCache() {

        imageView.buildDrawingCache();
        Bitmap b1 = imageView.getDrawingCache();
        // copy this bitmap otherwise distroying the cache will destroy
        // the bitmap for the referencing drawable and you'll not
        // get the captured view
        Bitmap b = b1.copy(Bitmap.Config.ARGB_8888, false);
        imageView.destroyDrawingCache();

        return b;
    }

    private void rect(int RGB) {
        RE = Color.red(RGB);
        GE = Color.green(RGB);
        BL = Color.blue(RGB);


        hex = Integer.toString(RE, 16);
        hex2 = Integer.toString(GE, 16);
        hex3 = Integer.toString(BL, 16);

        if(RE <=15){
            hex = "0"+hex;
        }
        if( GE<=15){
            hex2 = "0"+hex2;
        }
        if(BL <=15){
            hex3 = "0"+hex3;
        }

        GradientDrawable drawable = new GradientDrawable();

        drawable.mutate();
        drawable.setShape(GradientDrawable.RECTANGLE);// 0がRECTANGLE
        drawable.setCornerRadius(0);
        drawable.setColor(Color.parseColor("#"+hex+hex2+hex3));

        targetView.setBackgroundDrawable(drawable);

    }

}