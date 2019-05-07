package com.example.nakao0411.Record_Summary;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import static org.opencv.core.Core.FONT_HERSHEY_SIMPLEX;
import static org.opencv.imgproc.Imgproc.circle;

public class Circle_Write2 extends Activity {
    private Context context;
    myApp myApp;


    public Circle_Write2(Context context) {
        this.context = context;
    }


    Bitmap bluck3;
    Bitmap bluck4;
    Bitmap photo;
    private int[] diame;
    int[] length;
    int i;
    Bitmap[] bit;

    public Bitmap Circle_Write3(Bitmap uri, Bitmap bluck, Bitmap err, Bitmap basa) {
        myApp = (myApp) context.getApplicationContext();
        length = myApp.getLength();
        bit = myApp.getBitmap();


//        try {
//            photo = resize(uri, 810, 1422);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
        photo = uri;
        bluck3 = Bitmap.createScaledBitmap(bluck, photo.getWidth(), photo.getHeight(), false);//黒色画像をカメラ画像のサイズに合わせる
        bluck4 = Bitmap.createScaledBitmap(basa, photo.getWidth(), photo.getHeight(), false);//黒色画像をカメラ画像のサイズに合わせる

        Mat mat_photo = new Mat();
        Utils.bitmapToMat(photo, mat_photo);

        /*黒色の画像群*/
        Mat mat_bluck = new Mat();
        Utils.bitmapToMat(bluck3, mat_bluck);

        Mat mat_bluck2 = new Mat();
        Utils.bitmapToMat(bluck4, mat_bluck2);

        Mat mat_bluck3 = new Mat();
        Utils.bitmapToMat(bluck3, mat_bluck3);



        Mat gray = new Mat(mat_photo.rows(), mat_photo.cols(), CvType.CV_8SC1);//初期化
        Imgproc.cvtColor(mat_photo, gray, Imgproc.COLOR_RGB2GRAY);//グレースケール化

        System.out.println("①ok");
        System.out.println("②ok");

        Mat circles = new Mat();// 検出した円の情報格納する変数
        //円認識//                                                                                                           65             135
        Imgproc.HoughCircles(gray, circles, Imgproc.CV_HOUGH_GRADIENT, 2, 80, 1, 200, 10 , 1000);
        System.out.println("③ok");
        Point pt = new Point();
        int arclen2 = 1145141919;
        diame = new int[circles.cols()];

        System.out.println("④ok");


        //       検出した直線上を緑線で塗
        for (i = 0; i < circles.cols(); i++) {

            double data[] = circles.get(0, i);
            if (data == null) break;

            pt.x = data[0];
            pt.y = data[1];
            int rho = (int) data[2];
            circle(mat_bluck2, pt, rho, new Scalar(255, 255, 255), 3);//円描画


            System.out.println("円(" + i + ")の直径：" + rho * 2);
            arclen2 = rho * 2;
            diame[i] = arclen2;

            String num = String.valueOf(i);
            Imgproc.putText(mat_bluck2, num, new Point(pt.x + 2, pt.y + 2), FONT_HERSHEY_SIMPLEX, 2, new Scalar(255, 255, 255), 3);
        }


        if (arclen2 == 1145141919) {
            return err;
        }
        Mat lplp = mat_bluck2;

        //  Bitmap dst に空のBitmapを作成
        Bitmap dst2 = Bitmap.createBitmap(lplp.width(), lplp.height(), Bitmap.Config.ARGB_8888);

//  MatからBitmapに変換
        Utils.matToBitmap(lplp, dst2);
        myApp.setBitmap(bit[0], dst2);
        myApp.setDiameter(diame);


        return dst2;

    }

}