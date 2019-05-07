package com.example.nakao0411.Record_Summary;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.opencv.core.Mat;


public class Set_Length extends Activity{
    private myApp MyApp;
    int _logVertical = 0;
    int _logSide = 0;
    int _markVertical = 0;
    int _markSide = 0;
    int _param1 = 0;
    int _param2 = 0;


    EditText et_Vertical;
    EditText et_Side ;
    EditText mk_Vertical;
    EditText mk_Side;
    EditText pa_1;
    EditText pa_2;

    myApp myApp;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__length);
        myApp =(myApp)this.getApplication();

        int[] length = myApp.getLength();
        int[] param = myApp.getpar();
        et_Vertical = findViewById(R.id.etVertical);
        et_Side = findViewById(R.id.etSide);
        mk_Vertical = findViewById(R.id.markVertical);
        mk_Side = findViewById(R.id.markSide);
        pa_1 = findViewById(R.id.param1);
        pa_2 = findViewById(R.id.param2);

        String et_V = String.valueOf(length[0]);
        String et_S = String.valueOf(length[1]);
        String mk_V = String.valueOf(length[2]);
        String mk_S= String.valueOf(length[3]);
        String par1= String.valueOf(param[0]);
        String par2= String.valueOf(param[1]);


        et_Vertical.setText(et_V);
        et_Side.setText(et_S);
        mk_Vertical.setText(mk_V);
        mk_Side.setText(mk_S);
        pa_1.setText(par1);
        pa_2.setText(par2);


        et_Vertical.setSelection(et_Vertical.getText().length());
        et_Side.setSelection(et_Side.getText().length());
        mk_Vertical.setSelection(mk_Vertical.getText().length());
        mk_Side.setSelection(mk_Side.getText().length());
        pa_1.setSelection(pa_1.getText().length());
        pa_2.setSelection(pa_2.getText().length());



    }

    public void onPhotoButtonClick(View view) {
        MyApp = (myApp) this.getApplication();

        EditText etVertical = findViewById(R.id.etVertical);
        String strVertical = etVertical.getText().toString();
        _logVertical = Integer.parseInt(strVertical);

        EditText etSide = findViewById(R.id.etSide);
        String strSide = etSide.getText().toString();
        _logSide = Integer.parseInt(strSide);

        EditText mkVertical = findViewById(R.id.markVertical);
        String markVertical = mkVertical.getText().toString();
        _markVertical = Integer.parseInt(markVertical);

        EditText mkSide = findViewById(R.id.markSide);
        String markSide = mkSide.getText().toString();
        _markSide = Integer.parseInt(markSide);

        EditText parm1 = findViewById(R.id.param1);
        String Parm1 = parm1.getText().toString();
        _param1 = Integer.parseInt(Parm1);

        EditText parm2 = findViewById(R.id.param2);
        String Parm2 = parm2.getText().toString();
        _param2 = Integer.parseInt(Parm2);

        MyApp.setLength(_logVertical,_logSide,_markVertical,_markSide);
        MyApp.setpar(_param1,_param2);

        Intent intent = new Intent(Set_Length.this, Setting_Activity.class);
        startActivity(intent);
    }


}
