package com.example.nakao0411.Record_Summary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.opencv.core.CvType.CV_8U;

public class Color_Recognision {
    static {                                 // <--
        System.loadLibrary("opencv_java3");  // <-- この３行を追加
    }

    public Color_Recognision(Context context) {
        this.context = context;
    }

    myApp myApp;
    private Context context;
    private int[] length;
    Rect r;

    public int Color_Recognision2(Bitmap photo) {
        myApp = (myApp) context.getApplicationContext();
        length = myApp.getLength();

        Mat mat = new Mat();
        Utils.bitmapToMat(photo, mat);

        Mat src = mat;//入力画像
        Mat dst = Mat.zeros(mat.width(), mat.height(), CV_8U);//初期化
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_RGB2HSV);


        Mat src2 = dst;//HSV画像を代入
        Mat dst2 = Mat.zeros(mat.width(), mat.height(), CV_8U);//初期化
        Core.inRange(src2, new Scalar(160, 50, 50), new Scalar(180, 256, 256), dst2);//赤色

        Bitmap nk = Bitmap.createBitmap(dst2.width(),dst2.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(dst2,nk);
        myApp.setbitmap2(nk);

        List<MatOfPoint> list = new ArrayList<MatOfPoint>();
        Mat hierarchy = Mat.zeros(new Size(dst2.width(), dst2.height()), CvType.CV_8UC1);
        Mat m4 = new Mat();
        Imgproc.threshold(dst2, m4, 127, 255, 0);
        Imgproc.findContours(m4, list, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        if (list.isEmpty()) {
            int i =114514;
            double p = (double) length[2] / i;
            myApp.setpx(p);
            System.out.println("err");
            return i;
        }
        r = Imgproc.boundingRect(list.get(0));
        //大きい矩形を残す
        for (int i = 0; i < list.size(); i++) {
            Rect r2 = Imgproc.boundingRect(list.get(i));
            if (r.height*r.width < r2.height*r.width) {
                r = r2;
            }
        }
        double p = (double) length[2] / r.height;
        System.out.println(r.height+"×"+r.width);
        myApp.setpx(p);
        System.out.println(p);
//        myApp.setBitmap(photo,nk);
        return r.height;


    }

}
