package com.example.nakao0411.Record_Summary;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Result_Activity extends Activity{
    TextView text;
    myApp myApp;
    int[] dimen2 ;
    int tyoku;
    private int number;
    private double
            px;
    Bitmap photo;
    Bitmap bitmap;
    Bitmap[] bit;
    ImageView image1;
    ImageView image2;
    int subVertical;
    int subSide;
    int[] length;
    int i;
    int set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_);
        image1 = findViewById(R.id.imageView1);
        image2 = findViewById(R.id.imageView2);
        Button button = (Button) findViewById(R.id.home);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Result_Activity.this, MainActivity.class);  //インテントの作成
                startActivity(intent);
            }
        });


        myApp = (myApp)this.getApplication();

        bit = myApp.getBitmap();
        px = myApp.getpx();
        length = myApp.getLength();
        subSide = length[1];
        subVertical = length[0];
        photo = bit[0];
        set=0;
        set = myApp.getting();
        if(set == 0){
            bitmap = myApp.getnum();
        }
        else {
            bitmap = bit[1];
        }
        image2.setImageBitmap(photo);
        image1.setImageBitmap(bitmap);
        dimen2 = myApp.getDiameter();
        text = findViewById(R.id.text);
        if(dimen2!=null) {
            for (i = 0; i < dimen2.length; i++) {
                tyoku = (int)(dimen2[i]*px);
                number = number(tyoku);
                text.setText(text.getText() + "\n" + i + "番の丸太" + "\n" + "直径：" + tyoku+"\n"+"木板の個数："+number+"\n");
            }
            System.out.println(i);
        }
    }

    private int number(int data) {
        double circleference = (double) data / 2; // 半径を変数にぶっこむ
        double oneSide = Math.sqrt(2) * circleference; // oneSide に「√２×半径」をぶち込む

        double ratioVertical = oneSide / subVertical; // oneSide を 木板の縦の長さで割ってどれだけ縦に木板が入るか計算
        double ratioSide = oneSide / subSide; // oneSide を 木板の横の長さで割ってどれだけ横に木板が入るか計算

        number = (int) ratioVertical * (int) ratioSide;
        return number;
    }// number に一体どれだけの木板が入るかをぶっこむ。
}
