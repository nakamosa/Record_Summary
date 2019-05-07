package com.example.nakao0411.Record_Summary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.security.AccessController.getContext;
import static org.opencv.core.Core.FONT_HERSHEY_SIMPLEX;
import static org.opencv.imgproc.Imgproc.circle;

public class Circle_Write extends Activity {
    private Context context;
    myApp myApp;


    public Circle_Write(Context context) {
        this.context = context;
    }


    Bitmap bluck3;
    Bitmap bluck4;
    Bitmap photo;
    private int[] diame;
    int[] length;
    int[] param;
    int i;
    int arclen2;

    public Bitmap Circle_Write2(Bitmap uri, Bitmap bluck, Bitmap basa) {
        myApp = (myApp) context.getApplicationContext();
        length = myApp.getLength();
        param = myApp.getpar();


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
        Utils.bitmapToMat(bluck4, mat_bluck);

        Mat mat_bluck2 = new Mat();
        Utils.bitmapToMat(bluck4, mat_bluck2);

        Mat mat_bluck3 = new Mat();
        Utils.bitmapToMat(bluck3, mat_bluck3);


        /* */


        Mat gray = new Mat(mat_photo.rows(), mat_photo.cols(), CvType.CV_8SC1);//初期化
        Imgproc.cvtColor(mat_photo, gray, Imgproc.COLOR_RGB2GRAY);//グレースケール化
        Bitmap ji = Bitmap.createBitmap(gray.width(), gray.height(), Bitmap.Config.ARGB_8888);

        System.out.println("①ok");

        Mat m4 = new Mat();
//        Imgproc.threshold(gray, m4, 65, 255, Imgproc.THRESH_BINARY_INV);//二値化
        Imgproc.GaussianBlur(gray, m4, new Size(9,9), 3, 3);
        Utils.matToBitmap(m4, ji);
        System.out.println("②ok");

        Mat circles = new Mat();// 検出した円の情報格納する変数
        //円認識//                                                                                                           65             135
        Imgproc.HoughCircles(m4, circles, Imgproc.CV_HOUGH_GRADIENT, 2, 70, param[0], param[1], 20, 2000);
        System.out.println("③ok");
        Point pt = new Point();

        diame = new int[circles.cols()];

        System.out.println("④ok");


        //       検出した直線上を緑線で塗
        for (i = 0; i < circles.cols(); i++) {


            double data[] = circles.get(0, i);
            if (data == null) break;

            pt.x = data[0];
            pt.y = data[1];
            int rho = (int) data[2];
            circle(mat_bluck, pt, rho, new Scalar(255, 255, 255), 3);//円描画
            circle(mat_bluck2, pt, rho, new Scalar(255, 255, 255), 3);//円描画
            circle(mat_bluck2, pt, 1, new Scalar(255, 255, 255), 9);//円描画


            System.out.println("円(" + i + ")の直径：" + rho * 2);
            arclen2 = rho * 2;
            diame[i] = arclen2;

            String num = String.valueOf(i);
            Imgproc.putText(mat_bluck, num, new Point(pt.x + 2, pt.y + 2), FONT_HERSHEY_SIMPLEX, 2, new Scalar(255, 255, 255), 3);
            Imgproc.putText(mat_bluck2, num, new Point(pt.x + 2, pt.y + 2), FONT_HERSHEY_SIMPLEX, 2, new Scalar(255, 255, 255), 3);

        }
        if(i==0){
            myApp.setflag(1);
        }


        Mat lplp = mat_bluck2;
        Mat lplp2 = mat_bluck;

        //  Bitmap dst に空のBitmapを作成
        Bitmap dst2 = Bitmap.createBitmap(lplp.width(), lplp.height(), Bitmap.Config.ARGB_8888);
        Bitmap dst3 = Bitmap.createBitmap(lplp2.width(), lplp2.height(), Bitmap.Config.ARGB_8888);

//  MatからBitmapに変換
        Utils.matToBitmap(lplp, dst2);
        Utils.matToBitmap(lplp2, dst3);
        myApp.setnum(dst3);
        myApp.setBitmap(photo, dst2);
        myApp.setDiameter(diame);


        return dst2;

    }

}