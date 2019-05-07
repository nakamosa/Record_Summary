package com.example.nakao0411.Record_Summary;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends Activity {
    static {                                 // <--
        System.loadLibrary("opencv_java3");  // <-- この３行を追加
    }
    private Context context;

    private Uri _imageUri;
    static int _tudeId = -1;
    private static String _tvlatitude;
    private static String _tvlongitude;

    private double _latitude = 0;
    private double _longitude = 0;


    private static final int CAMERA_REQUEST = 1888;
    private Camera mCam = null;
    Button button1;
    ImageView imageView;
    ImageView imageView2;
    private Circle_Write CW;
    private Circle_Write2 CW2;
    private Color_Recognision CR;
    private Result_Activity RA;
    int rgb[];
    Bitmap bitmap ;//カメラ画像





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
        CW2 = new Circle_Write2(this);
        CR = new Color_Recognision(this);
        RA = new Result_Activity();
        myApp = (myApp) this.getApplication();
        rgb = myApp.getTestString();
        context = getApplicationContext();



        this.imageView = (ImageView) this.findViewById(R.id.imageView1);
        this.imageView2 = (ImageView) this.findViewById(R.id.imageView2);
        Button photoButton = (Button) this.findViewById(R.id.button1);
        Bitmap setimage = BitmapFactory.decodeResource(getResources(), R.drawable.bluck);
//        imageView.setImageBitmap(setimage);


        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    ActivityCompat.requestPermissions(CameraActivity.this, permissions, 2000);
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

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, _imageUri);

                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        GPSLocationListener locationListener = new GPSLocationListener();
        if (ActivityCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // ACCESS_FINE_LOCATIONの許可を求めるダイアログを表示。その際、リクエストコードを1000に設定。
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(CameraActivity.this, permissions, 1000);
            // onCreate()メソッドを終了。
            return;
        }
        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    //現在地取得
    private class GPSLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            _latitude = location.getLatitude();
            _longitude = location.getLongitude();
            CameraActivity._tvlatitude = String.valueOf(_latitude);
            CameraActivity._tvlongitude = String.valueOf(_longitude);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    }

    //撮影後の処理
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            CameraActivity._tudeId = CameraActivity._tudeId + 1; /* 「_id」を１足してデータベースが更に追加されないように防止 */
            DatabaseHelper helper = new DatabaseHelper(CameraActivity.this);
            SQLiteDatabase db = helper.getWritableDatabase();
            String longitude = CameraActivity._tvlongitude;
            String latitude = CameraActivity._tvlatitude;

            //データベースに現在地登録
            label:
            try {
                if (longitude == null || longitude!= null) {
                    System.out.println("null");
                    break label;
                }
                bitmap =resize(_imageUri,888,510);//カメラ画像
                byte[] byte2 = getBitmapAsByteArray(bitmap);
                String sqlDelete = "DELETE FROM tude WHERE _id = ?";
                SQLiteStatement stmt = db.compileStatement(sqlDelete);
                stmt.bindLong(1, _tudeId);
                stmt.executeUpdateDelete();

                String sqlInsert = "INSERT INTO tude(_id, longitude, latitude,bitmap) VALUES(?, ?, ?,?)";
                stmt = db.compileStatement(sqlInsert);
                stmt.bindLong(1, _tudeId);
                stmt.bindString(2, longitude);
                stmt.bindString(3, latitude);
                stmt.bindBlob(4,byte2);
                stmt.executeInsert();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                db.close();
            }

            //カメラ画像処理
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), _imageUri);//カメラ画像
                bitmap = resize(_imageUri, 1422, 810);//カメラ画像

//                Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.log4);//デバック用
//                bitmap2 = Bitmap.createScaledBitmap(bitmap2, 1422, 810, false);
                Bitmap basa = BitmapFactory.decodeResource(getResources(), R.drawable.bluck2);//透明

                Bitmap bluck2 = BitmapFactory.decodeResource(getResources(), R.drawable.bluck);//黒色の画像
                Bitmap photo2 = CW.Circle_Write2(bitmap, bluck2, basa);//円認識 & 直径測定
                if (myApp.getflag() == 1) {
                    System.out.println("こんにちわ");
                    Intent intent = new Intent(CameraActivity.this, Alert_Activity.class);  //インテントの作成
                    startActivity(intent);

                }
                else {
                    int arclen = CR.Color_Recognision2(bitmap);

                    Intent intent = new Intent(CameraActivity.this, Remake_Activity.class);  //インテントの作成
                    startActivity(intent);
                }
                } catch(IOException e){
                    e.printStackTrace();
                }
            }

        }
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        //PNG, クオリティー100としてbyte配列にデータを格納
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }


    public Bitmap resize(Uri uri, int mMaxWidth, int mMaxHeight) throws IOException {

        Bitmap bitmap = null;
        BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
        decodeOptions.inJustDecodeBounds = true;
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        BitmapFactory.decodeStream(inputStream, null, decodeOptions);
        inputStream.close();
        int actualWidth = decodeOptions.outWidth;
        int actualHeight = decodeOptions.outHeight;

        int desiredWidth = getResizedDimension(mMaxWidth, mMaxHeight,
                actualWidth, actualHeight);
        int desiredHeight = getResizedDimension(mMaxHeight, mMaxWidth,
                actualHeight, actualWidth);

        decodeOptions.inJustDecodeBounds = false;
        decodeOptions.inSampleSize =
                findBestSampleSize(actualWidth, actualHeight, desiredWidth, desiredHeight);

        inputStream = context.getContentResolver().openInputStream(uri);
        Bitmap tempBitmap = BitmapFactory.decodeStream(inputStream, null, decodeOptions);
        inputStream.close();

        if (tempBitmap != null && (tempBitmap.getWidth() > desiredWidth ||
                tempBitmap.getHeight() > desiredHeight)) {
            bitmap = Bitmap.createScaledBitmap(tempBitmap,
                    desiredWidth, desiredHeight, true);
            tempBitmap.recycle();
        } else {
            bitmap = tempBitmap;
        }
        return bitmap;
    }

    private int getResizedDimension(int maxPrimary, int maxSecondary, int actualPrimary,
                                    int actualSecondary) {
        if ((maxPrimary == 0) && (maxSecondary == 0)) {
            return actualPrimary;
        }

        if (maxPrimary == 0) {
            double ratio = (double) maxSecondary / (double) actualSecondary;
            return (int) (actualPrimary * ratio);
        }

        if (maxSecondary == 0) {
            return maxPrimary;
        }

        double ratio = (double) actualSecondary / (double) actualPrimary;
        int resized = maxPrimary;

        if ((resized * ratio) < maxSecondary) {
            resized = (int) (maxSecondary / ratio);
        }
        return resized;
    }

    static int findBestSampleSize(
            int actualWidth, int actualHeight, int desiredWidth, int desiredHeight) {
        double wr = (double) actualWidth / desiredWidth;
        double hr = (double) actualHeight / desiredHeight;
        double ratio = Math.min(wr, hr);
        float n = 1.0f;
        while ((n * 2) <= ratio) {
            n *= 2;
        }
        return (int) n;
    }
}