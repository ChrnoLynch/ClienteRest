package com.example.lprub.proyectorest.Actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.lprub.proyectorest.clases.Profesor;
import com.example.lprub.proyectorest.interfaz.ApiActividades;
import com.example.usuario.proyectorest.R;
import com.example.lprub.proyectorest.clases.Actividad;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class Alta extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    public static final int FECHAINI=0, FECHAFIN=1;
    private String[] arrTipo = new String[3];
    private Spinner spinerTipo;
    private Spinner spinerProf;
    private GregorianCalendar fechaInicio,fechaFin;
    private TextView tvIni,tvFin;
    private String strfechaIni, strfechaFin;
    private EditText edIni,edFin,edDesc;
    private List<String> lista=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota);
        spinerTipo =(Spinner) findViewById(R.id.spTipo);
        spinerProf =(Spinner) findViewById(R.id.spProf);
        arrTipo[0]=this.getString(R.string.excursion);
        arrTipo[1]=this.getString(R.string.extraescolar);
        arrTipo[2]=this.getString(R.string.complementaria);
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, arrTipo);
        spinerTipo.setAdapter(spinnerArrayAdapter);
        spinerTipo.setOnItemSelectedListener(this);
        spinerTipo.setSelection(0);
        consultarProf();
        String[] arrNom={"Pilar","Carmelo","Jaime","Modesto"};
        ArrayAdapter spinnerArrayAdapter2 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, arrNom);
        spinerProf.setAdapter(spinnerArrayAdapter2);
        spinerProf.setOnItemSelectedListener(this);
        spinerProf.setSelection(0);
        fechaInicio =new GregorianCalendar();
        fechaFin=new GregorianCalendar();
        tvIni=(TextView) findViewById(R.id.tvFechaIni);
        tvFin=(TextView) findViewById(R.id.tvFechaFin);
        edIni=(EditText)findViewById(R.id.edLugarInicio);
        edFin=(EditText)findViewById(R.id.edLugarFin);
        edDesc=(EditText)findViewById(R.id.edDesc);
    }
    public void consultarProf() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://ieszv.x10.bz/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        ApiActividades apiActividades = retrofit.create(ApiActividades.class);
        Call<List<Profesor>> call = apiActividades.getProfesores();
        call.enqueue(new Callback<List<Profesor>>() {
            @Override
            public void onResponse(Response<List<Profesor>> response, Retrofit retrofit) {
                for (Profesor a : response.body()) {
                    lista.add(a.getNombre());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case FECHAINI:
                    fechaInicio.setTimeInMillis(data.getExtras().getLong("aux"));
                    Date d= fechaInicio.getTime();
                    String hora=data.getExtras().getString("hora");
                    if(hora.equals("")){
                        hora="00:00";
                    }
                    strfechaIni=dateString(d)+" "+hora;
                    tvIni.setText(strfechaIni);
                    break;
                case FECHAFIN:
                    fechaFin.setTimeInMillis(data.getExtras().getLong("aux"));
                    Date d2=fechaFin.getTime();
                    String hora2=data.getExtras().getString("hora");
                    if(hora2.equals("")){
                        hora2="00:00";
                    }
                    strfechaFin=dateString(d2)+" "+hora2;
                    tvFin.setText(strfechaFin);
                    break;
            }
        }
    }

    public String dateString(Date d){
        return (d.getYear()+(1900)+"-"+(d.getMonth()+1)+"-"+d.getDate());
    }

    public void aceptar(View v){//FILTRAR VACIOS
        int pos= spinerProf.getSelectedItemPosition();
        String tipo= spinerTipo.getSelectedItem().toString();
        String strLugarIni=edIni.getText().toString()
                ,strLugarFin=edFin.getText().toString()
                ,strDesc=edDesc.getText().toString();

        Actividad a=new Actividad("",""+pos,tipo,strfechaIni, strfechaFin,strLugarIni,strLugarFin,strDesc);
        añadirActividad(a);
        setResult(RESULT_OK);
        finish();
    }

    public void añadirActividad(Actividad a) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ieszv.x10.bz/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiActividades apiActividades = retrofit.create(ApiActividades.class);

        final Call<Actividad> call = apiActividades.createActividad(a);
        call.enqueue(new Callback<Actividad>() {
            @Override
            public void onResponse(Response<Actividad> response, Retrofit retrofit) {
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    public void cancelar(View v){
        setResult(RESULT_CANCELED);
        finish();
    }
    public void fechaIni(View v){
        Intent i=new Intent(this,Fecha.class);
        startActivityForResult(i,FECHAINI);
    }
    public void fechaFin(View v){
        Intent i=new Intent(this,Fecha.class);
        startActivityForResult(i,FECHAFIN);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
