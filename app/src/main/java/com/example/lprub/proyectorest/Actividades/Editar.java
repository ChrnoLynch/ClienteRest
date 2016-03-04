package com.example.lprub.proyectorest.Actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.usuario.proyectorest.R;
import com.example.lprub.proyectorest.clases.Actividad;
import com.example.lprub.proyectorest.interfaz.ApiActividades;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class Editar extends AppCompatActivity {
    public static final int FECHAINI=0, FECHAFIN=1;
    private String[] arrTipo = new String[3];
    private Spinner spTipo;
    private Spinner spProf;
    private GregorianCalendar fechaIn,fechaFin;
    private TextView tvIni,tvFin;
    private String strfechaIni, strfechaFin;
    private EditText edIni,edFin,edDesc;
    private List<String> lista=new ArrayList<>();
    private Actividad a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota);
        setTitle("Editar");
        Intent i=getIntent();
        Bundle b=i.getExtras();
        a=b.getParcelable("aux");

        /****/
        spTipo=(Spinner) findViewById(R.id.spTipo);
        arrTipo[0]=this.getString(R.string.excursion);
        arrTipo[1]=this.getString(R.string.extraescolar);
        arrTipo[2]=this.getString(R.string.complementaria);
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, arrTipo);
        spTipo.setAdapter(spinnerArrayAdapter);
        switch (a.getTipo()){
            case "Excursion":
                spTipo.setSelection(0);
                break;
            case "Extraescolar":
                spTipo.setSelection(1);
                break;
            case "Complementaria":
                spTipo.setSelection(2);
                break;
        }
        /****/
        spProf=(Spinner) findViewById(R.id.spProf);

        String[] arrNom={"Pilar","Carmelo","Jaime","Modesto"};
        ArrayAdapter spinnerArrayAdapter2 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, arrNom);
        spProf.setAdapter(spinnerArrayAdapter2);
        switch (a.getProfesor()) {
            case "Pilar":
                spProf.setSelection(0);
                break;
            case "Carmelo":
                spProf.setSelection(1);
                break;
            case "Jaime":
                spProf.setSelection(2);
                break;
            case "Modesto":
                spProf.setSelection(3);
                break;
        }
        /*****/
        fechaIn=new GregorianCalendar();
        fechaFin=new GregorianCalendar();

        tvIni=(TextView) findViewById(R.id.tvFechaIni);
        tvIni.setText(a.getFechai());
        tvFin=(TextView) findViewById(R.id.tvFechaFin);
        tvFin.setText(a.getFechaf());
        /*****/
        edIni=(EditText)findViewById(R.id.edLugarInicio);
        edIni.setText(a.getLugari());
        edFin=(EditText)findViewById(R.id.edLugarFin);
        edFin.setText(a.getLugarf());
        edDesc=(EditText)findViewById(R.id.edDesc);
        edDesc.setText(a.getDescripcion());
        strfechaIni=a.getFechai();
        strfechaFin=a.getFechaf();
    }

    public void aceptar(View v){
        int pos=spProf.getSelectedItemPosition();
        String tipo=spTipo.getSelectedItem().toString();
        String strLugarIni=edIni.getText().toString()
                ,strLugarFin=edFin.getText().toString()
                ,strDesc=edDesc.getText().toString();

        a.setProfesor(""+pos);
        a.setTipo(tipo);
        a.setFechai(strfechaIni);
        a.setFechaf(strfechaFin);
        a.setLugari(strLugarIni);
        a.setLugarf(strLugarFin);
        a.setDescripcion(strDesc);

        edita(a);
        setResult(RESULT_OK);
        finish();
    }

    public void edita(Actividad a) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ieszv.x10.bz/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiActividades apiActividades = retrofit.create(ApiActividades.class);

        final Call<Actividad> call = apiActividades.editaActividad(a);
        call.enqueue(new Callback<Actividad>() {
            @Override
            public void onResponse(Response<Actividad> response, Retrofit retrofit) {
            }

            @Override
            public void onFailure(Throwable t) {}
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case FECHAINI:
                    fechaIn.setTimeInMillis(data.getExtras().getLong("aux"));
                    Date d=fechaIn.getTime();
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
}
