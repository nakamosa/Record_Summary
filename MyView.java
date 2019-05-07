package com.example.nakao0411.Record_Summary;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;


public class MyView extends AppCompatImageView {
    private Paint paint;
    private Boolean viewflg;
    private int lr2;
    private int x2;
    private int y2;
    private int i;
    private int[][] list;
    myApp myApp;
    Paint paint2;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        myApp = (myApp) context.getApplicationContext();
//        bit = BitmapFactory.decodeResource(getResources(),R.drawable.maruta_red);
//        bit = bit.copy(Bitmap.Config.ARGB_8888, true);
        paint = new Paint();
        paint2 = new Paint();
        viewflg = true;
        list = new int[10000][4];
    }

    public void showCanvas(boolean flg,int l2,int x,int y){
        viewflg = flg;
        lr2 = l2;


        x2 = x;
        y2 = y;
        // 再描画
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("TestView", " onDraw()");
//        canvas.drawBitmap(bitbit[1],0,0,paint2);
//        Log.d("TestView222", " onDraw(2)");
        if(viewflg){
            Log.d("TestView", " viewflg = true");

            // 背景、半透明]



            // 矩形
            paint.setColor(Color.argb(255, 255,255, 255));
            paint.setStrokeWidth(4);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            // (x1,y1,r,paint) 中心x1座標, 中心y1座標, r半径
            canvas.drawCircle(x2, y2-600, lr2+30, paint);
            paint.setColor(Color.rgb(255,255,255));

            for(int i =0;i<list.length;i++){
                canvas.drawCircle(list[i][0], list[i][1]-600, list[i][2]+30, paint);
            }



        }
        else{
            paint.setColor(Color.argb(255, 255,255, 255));
            canvas.drawCircle(100, 100, lr2+30, paint);
            Log.d("TestView", " viewflg = false");
            i++;
            // 描画クリア
            list[i][0] = x2;
            list[i][1] = y2;
            list[i][2] = lr2;
            x2 =100;
            y2=100;
            paint.setColor(Color.argb(255,255,255,255));
            paint.setStyle(Paint.Style.STROKE);



            for(int i =0;i<list.length;i++){
                canvas.drawCircle(list[i][0], list[i][1]-600, list[i][2]+30, paint);

            }
        }
    }

}