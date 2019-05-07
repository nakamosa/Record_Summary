package com.example.nakao0411.Record_Summary;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class Remake_Activity extends Activity implements View.OnTouchListener {

    private boolean showCanvas;
    private MyView myView;
    int l2;
    int x;
    int y;
    SeekBar seek;
    Button button;
    Button button2;
    ImageView imv;
    ImageView imv2;
    Bitmap[] nbi;
    myApp myApp;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remake_);
        myApp = (myApp) this.getApplication();
        imv = findViewById(R.id.imageview5);
        imv2 = findViewById(R.id.imageview6);
        nbi=myApp.getBitmap();
        imv.setImageBitmap(nbi[0]);
        imv2.setImageBitmap(nbi[1]);

        l2 = 50;
        x = 480;
        y = 480;

        Log.d("MainActivity", "onCreate()");
        myView = this.findViewById(R.id.my_view);
        myView.showCanvas(true, l2, x, y);
        myView.setOnTouchListener(this);
        myView.setImageBitmap(nbi[1]);
        showCanvas = true;
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myView.showCanvas(false, l2, x, y);
                x = 100;
                y = 400;
            }
        });
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap map = loadBitmapFromView(myView);
                myApp.setBitmap5(map,nbi[1]);

                Intent intent = new Intent(Remake_Activity.this, Main2Activity.class);  //インテントの作成
                startActivity(intent);
            }
        });

        seek = findViewById(R.id.seek_zoom);
        seek.setMax(150);
        seek.setProgress(l2);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                l2 = progress;
                myView.showCanvas(true, l2, x, y);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {


        x = (int) event.getRawX();
        y = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(myApp.getting()==0){
                myApp.setting(1);}
                myView.showCanvas(true, l2, x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                myView.showCanvas(true, l2, x, y);
                break;
        }
        return true;
    }
    public static Bitmap loadBitmapFromView(ImageView v) {
        Bitmap b = Bitmap.createBitmap( v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;
    }
}
