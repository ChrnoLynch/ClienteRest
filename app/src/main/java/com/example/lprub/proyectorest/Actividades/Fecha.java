package com.example.lprub.proyectorest.Actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.usuario.proyectorest.R;

import java.util.GregorianCalendar;

public class Fecha extends AppCompatActivity {

    private DatePicker dp;
    private TimePicker tp;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulsa_fecha);
        dp=(DatePicker) findViewById(R.id.datePicker);
        tp=(TimePicker) findViewById(R.id.timePicker);
        i=getIntent();
    }

    public void aceptar(View v){
        int d=dp.getDayOfMonth();
        int m=dp.getMonth();
        int y=dp.getYear();
        GregorianCalendar gc=new GregorianCalendar();
        gc.set(y, m, d, 0, 0, 0);
        long milisecond=gc.getTimeInMillis();
        i.putExtra("aux", milisecond);

        String hora= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            hora = tp.getHour()+":"+tp.getMinute();
        }else{
            hora="";
        }
        i.putExtra("hora",hora);
        setResult(RESULT_OK, i);

        finish();
    }
}
