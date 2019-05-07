package com.example.nakao0411.Record_Summary;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Paint;

public class myApp extends Application {

    private int[]rgb = new int[4];
    private int[]length = new int[4];
    private int[]diame = new int[10000];
    private double px;
    private Bitmap[] bit = new Bitmap[2];
    private Bitmap[] bit2 = new Bitmap[2];
    private double _longitude = 0;
    private double _latitude = 0;
    private Bitmap num;
    private Bitmap bitmap;
    private int set;
    private int flag=0;
    private int[] Par = new int[2];
    SharedPreferences.Editor editor;

    SharedPreferences dataStore ;
    @Override
    public void onCreate() {
        super.onCreate();
        dataStore = getSharedPreferences("Color", MODE_PRIVATE);
        editor = dataStore.edit();
    }

    public void setbitmap2(Bitmap bit){
        bitmap = bit;

    }
    public Bitmap getbitmap2(){
        return bitmap;
    }

    public void setDiameter(int[] dia){
        diame =dia;

    }
    public int[] getDiameter(){
        return diame;
    }

    public void setBitmap(Bitmap photo,Bitmap bitmap){
        bit[0]=photo;
        bit[1]=bitmap;

    }
    public Bitmap[] getBitmap(){
        return bit;
    }

    public void setBitmap5(Bitmap photo,Bitmap bitmap){
        bit2[0]=photo;
        bit2[1]=bitmap;

    }
    public Bitmap[] getBitmap5(){
        return bit2;
    }

    public int[] getTestString() {
        int red = dataStore.getInt("red", 0);
        int green = dataStore.getInt("green", 0);
        int blue = dataStore.getInt("blue", 0);
        int RGB = dataStore.getInt("RGB", 0);

        rgb[0]=red;
        rgb[1]= green;
        rgb[2]=blue;
        rgb[3]=RGB;

        return rgb;
    }

    public void setTestString(int red ,int green,int blue,int RGB) {
        SharedPreferences.Editor editor = dataStore.edit();
        editor.putInt("red",red);
        editor.putInt("green",green);
        editor.putInt("blue",blue);
        editor.putInt("RGB",RGB);
        editor.apply();
    }

    public int[] getLength() {
        int vertical = dataStore.getInt("vertical",0);
        int side = dataStore.getInt("side",0);
        int mkvertical = dataStore.getInt("mkvertical",0);
        int mkside = dataStore.getInt("mkside",0);
        length[0] = vertical;
        length[1] = side;
        length[2] = mkvertical;
        length[3] = mkside;
        return length;

    }

    public void setLength(int log_vertical,int log_side,int mark_vertical,int mark_side) {
        editor.putInt("vertical",log_vertical);
        editor.putInt("side",log_side);
        editor.putInt("mkvertical",mark_vertical);
        editor.putInt("mkside",mark_side);
        editor.apply();
    }

    public double getpx() {
        return px;

    }

    public void setpx(double p) {
        px=p;
    }

    public double getLongitude() { return _longitude; }

    public double getLatitude() { return _latitude; }

    public void setLongitude(double longitude) {
        _longitude = longitude;
    }

    public void setLatitude(double latitude) {
        _latitude = latitude;
    }

    public void setnum(Bitmap num2){
        num = num2;
    }
    public Bitmap getnum(){
        return num;
    }
    public void setting(int set2){
        set = set2;
    }
    public int getting(){
        return set;
    }

    public void setpar(int par1,int par2){
        editor.putInt("param1",par1);
        editor.putInt("param2",par2);
        editor.apply();
    }

    public int[] getpar (){
        int param1 = dataStore.getInt("param1",0);
        int param2 = dataStore.getInt("param2",0);

        Par[0] = param1;
        Par[1] = param2;
        return Par;
    }

    public void setflag(int Flag){ flag = Flag;
    }
    public int getflag(){
        return flag;
    }
}