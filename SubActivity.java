package com.example.nakao0411.Record_Summary;

import android.app.Activity;
import android.os.Bundle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class SubActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Intent intent = getIntent();

        // ここでインテントで持ってきた値を変数に収納
        String strPhoto = intent.getStringExtra("photo");
        String date = intent.getStringExtra("date");
        String latitude = intent.getStringExtra("latitude");
        String longitude = intent.getStringExtra("longitude");
        String numberLog = intent.getStringExtra("numberLog");
        String[] numberBoard = intent.getStringArrayExtra("numberBoard");

        // 持ってきたString型の文字列をbyte配列に変換
        byte[] photo = strPhoto.getBytes();

        // 変換したbyte配列から画像を再構築、表示を行う(ここで画像が何故か表示できない)
        Bitmap byteLog = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        ImageView selected_photo = findViewById(R.id.selected_photo);
        selected_photo.setImageBitmap(byteLog);

        // ここ以下関係なし
        TextView tvDate = findViewById(R.id.tvDate);
        tvDate.setText("撮影日時：" + date);
        TextView tvLatitude = findViewById(R.id.tvLatitude);
        tvLatitude.setText("緯度：" + latitude);
        TextView tvLongitude = findViewById(R.id.tvLongitude);
        tvLongitude.setText("経度:" + longitude);
        TextView tvNumberLog = findViewById(R.id.tvNumberLog);
        tvNumberLog.setText("丸太の数：" + numberLog);

        ListView lvNumberBoard = findViewById(R.id.lvNumberBoard);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, numberBoard);
        lvNumberBoard.setAdapter(arrayAdapter);

    }
}