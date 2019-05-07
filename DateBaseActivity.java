package com.example.nakao0411.Record_Summary;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;

public class DateBaseActivity extends Activity implements AdapterView.OnItemClickListener {

    private static final int[] photos = {
            R.drawable.loglog,
            R.drawable.log3,
            R.drawable.log,
            R.drawable.log3,
            R.drawable.log
    };

    String[] byteLog;

    private static final String[] date = {"2018/10/05", "1946/08/30", "1985/07/04", "1981/03/13", "1998/06/01"};

    private static final double[] latitude = {35.681167, 34.733985, 33.668805, 34.258762, 34.49075};

    private static final double[] longitude = {139.767052, 136.510232, 135.88986, 136.808279, 136.840387};

    private static final int[] numberLog = {27, 18, 20, 34, 13};

    private static final String[] numberBoard = {
            "4,0,4,4,5,3,5,0,5,0,2,4,3,4,1,0,1,3,0,4,1,4,2,5,2,2",
            "3,2,5,4,5,2,2,1,4,1,1,1,4,0,0,5,5,4",
            "4,3,2,4,1,3,3,2,0,0,3,4,0,1,5,5,0,1,2,4",
            "5,1,5,4,5,3,2,1,3,5,5,4,1,3,5,1,1,2,5,2,5,3,3,0,4,4,5,2,1,5,1,1,4,1",
            "1,0,4,5,0,3,5,4,4,5,5,1,0"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_base);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // ここからは「log～log5」までの画像をbyte配列にして、Stringに変換しています
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.loglog);
        bitmap1 = resize(bitmap1);
        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteLog1 = byteArrayOutputStream.toByteArray();
        String numberLog1 = new String(byteLog1);

        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.log3);
        bitmap2 = resize(bitmap2);
        bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteLog2 = byteArrayOutputStream.toByteArray();
        String numberLog2 = new String(byteLog2);

        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.log);
        bitmap3 = resize(bitmap3);
        bitmap3.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteLog3 = byteArrayOutputStream.toByteArray();
        String numberLog3 = new String(byteLog3);

        Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(), R.drawable.log3);
        bitmap4 = resize(bitmap4);
        bitmap4.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteLog4 = byteArrayOutputStream.toByteArray();
        String numberLog4 = new String(byteLog4);

        Bitmap bitmap5 = BitmapFactory.decodeResource(getResources(), R.drawable.log);
        bitmap5 = resize(bitmap5);
        bitmap5.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteLog5 = byteArrayOutputStream.toByteArray();
        String numberLog5 = new String(byteLog5);

        // ここでString配列の中に、先程作ったStringを全て突っ込んでいます
        byteLog = new String[]{numberLog1, numberLog2, numberLog3, numberLog4, numberLog5};

        DatabaseHelper helper = new DatabaseHelper(DateBaseActivity.this);
        SQLiteDatabase db = helper.getReadableDatabase();

        try {
            SQLiteStatement stmt;

            String sqlDelete = "DELETE FROM baumkuchen";
            stmt = db.compileStatement(sqlDelete);
            stmt.executeUpdateDelete();

            // ここでデータベースに保存する
            for(int ii = 0; ii < 5; ii++) {
                String sqlInsert = "INSERT INTO baumkuchen (id, photo, date, latitude, longitude, numberLog, numberBoard) VALUES (?, ?, ?, ?, ?, ?, ?)";
                stmt = db.compileStatement(sqlInsert);
                stmt.bindLong(1, ii);
                stmt.bindString(2, byteLog[ii]);
                stmt.bindString(3, date[ii]);
                stmt.bindDouble(4, latitude[ii]);
                stmt.bindDouble(5, longitude[ii]);
                stmt.bindLong(6, numberLog[ii]);
                stmt.bindString(7, numberBoard[ii]);

                stmt.executeInsert();
            }
        }
        finally {
            db.close();
        }

        // ここで「ListViewAdaoter.java」を使いレイアウトを構築(ここは見てもらわなくて大丈夫です)
        ListView listView = findViewById(R.id.listView);
        BaseAdapter adapter = new ListViewAdapter(this.getApplicationContext(), R.layout.list, photos, date);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this.getApplicationContext(), SubActivity.class);

        DatabaseHelper databaseHelper = new DatabaseHelper(DateBaseActivity.this);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        try {
            // ここでクリックされた部分を参照してデータベースからデータを引っ張り出してくる。
            String sqlSelect = "SELECT photo, date, latitude, longitude, numberLog, numberBoard FROM baumkuchen WHERE id = " + position;
            Cursor cursor = database.rawQuery(sqlSelect, null);
            startManagingCursor(cursor);

            // 値の初期化
            String byteLogs = "";
            String date = "";
            double latitude = 0;
            double longitude = 0;
            int numberLog = 0;
            String numberBoard = "";

            if (cursor.moveToFirst()){
                do {
                    int index_photo = cursor.getColumnIndex("photo");
                    byteLogs = cursor.getString(index_photo);
                    date = cursor.getString(cursor.getColumnIndex("date"));
                    latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
                    longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
                    numberLog = cursor.getInt(cursor.getColumnIndex("numberLog"));
                    numberBoard = cursor.getString(cursor.getColumnIndex("numberBoard"));
                } while (cursor.moveToNext());
            }

            // 何故か違うアクティビティに値を渡すにはStringでないと無理だったので、Stringに値を直す
            String putLatitude = String.valueOf(latitude);
            String putLongitude = String.valueOf(longitude);
            String putNumberLog = String.valueOf(numberLog);

            // インテントで持っていく値を設定
            String[] numberBoard_array = numberBoard.split(",");
            intent.putExtra("photo", byteLogs);
            intent.putExtra("date", date);
            intent.putExtra("latitude", putLatitude);
            intent.putExtra("longitude", putLongitude);
            intent.putExtra("numberLog", putNumberLog);
            intent.putExtra("numberBoard", numberBoard_array);

            startActivity(intent);

        }finally {
            database.close();
        }
    }

    private Bitmap resize(Bitmap size){
        return Bitmap.createScaledBitmap(size,size.getWidth()/10,size.getHeight()/10,false);
    }
}
