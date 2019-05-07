package com.example.nakao0411.Record_Summary;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity2 extends Activity {
    static {                                 // <--
        System.loadLibrary("opencv_java3");  // <-- この３行を追加
    }

    private Uri _imageUri;



    private static final int CAMERA_REQUEST = 1888;
    private Camera mCam = null;
    Button button1;
    ImageView imageView;
    ImageView imageView2;
    private Circle_Write CW;
    private Color_Recognision CR;
    private Result_Activity RA;
    int rgb[];



    private String filePath;
    File cameraFolder = new File(
            Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM), "Camera");
    myApp myApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        CW = new Circle_Write(this);
        CR = new Color_Recognision(this);
        RA = new Result_Activity();
        myApp = (myApp) this.getApplication();
        rgb = myApp.getTestString();


        this.imageView = (ImageView) this.findViewById(R.id.imageView1);
        this.imageView2 = (ImageView) this.findViewById(R.id.imageView2);
        Button photoButton = (Button) this.findViewById(R.id.button1);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    ActivityCompat.requestPermissions(CameraActivity2.this, permissions, 2000);
                    return;
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

                Date now = new Date(System.currentTimeMillis());

                String nowStr = dateFormat.format(now);

                String fileName = nowStr + ".jpg";

                ContentValues values = new ContentValues();

                values.put(MediaStore.Images.Media.TITLE, fileName);

                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

                ContentResolver resolver = getContentResolver();

                _imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, _imageUri);

                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
 }



    //撮影後の処理
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {


            //カメラ画像処理
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), _imageUri);//カメラ画像
                myApp.setbitmap2(bitmap);

                Intent intent = new Intent(CameraActivity2.this, Color_Picker.class);  //インテントの作成
                startActivity(intent);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

}
