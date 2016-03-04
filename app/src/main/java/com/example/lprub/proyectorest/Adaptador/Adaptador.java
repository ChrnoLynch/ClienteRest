package com.example.lprub.proyectorest.Adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.usuario.proyectorest.R;
import com.example.lprub.proyectorest.clases.Actividad;

import java.util.List;

/**
 * Created by usuario on 03/03/2016.
 */
public class Adaptador extends ArrayAdapter<Actividad> {


    private Context ctx;
    private int res;
    private LayoutInflater lInflator;
    private List<Actividad> valores;

    static class ViewHolder {
        public TextView tv1, tv2;
        public TextView tv3, tv4;
        public TextView tv5, tv6,tvDesc;
    }

    public Adaptador(Context context, int resource, List<Actividad> objects) {
        super(context, resource, objects);
        this.ctx = context;//actividad
        this.res = resource;//layout del item
        this.valores = objects;//lista de valores
        this.lInflator = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    public boolean borrar(int position) {
        try {
            valores.remove(position);
            this.notifyDataSetChanged();
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //1
        ViewHolder gv = new ViewHolder();
        if(convertView==null){
            convertView = lInflator.inflate(res, null);
            TextView tv = (TextView) convertView.findViewById(R.id.tv);
            gv.tv1 = tv;
            tv = (TextView) convertView.findViewById(R.id.tv2);
            gv.tv2 = tv;
            tv = (TextView) convertView.findViewById(R.id.tv3);
            gv.tv3 = tv;
            tv = (TextView) convertView.findViewById(R.id.tv4);
            gv.tv4 = tv;
            tv = (TextView) convertView.findViewById(R.id.tv5);
            gv.tv5 = tv;
            tv = (TextView) convertView.findViewById(R.id.tv6);
            gv.tv6 = tv;
            tv = (TextView) convertView.findViewById(R.id.tvDesc);
            gv.tvDesc = tv;

            convertView.setTag(gv);
        } else {
            gv = (ViewHolder) convertView.getTag();
        }

        gv.tv1.setText("Alumno: "+valores.get(position).getAlumno().replace("|"," "));
        gv.tvDesc.setText("Descripcion: "+valores.get(position).getDescripcion()+"");

        gv.tv2.setText("Lugar inicio: "+valores.get(position).getLugari() + "");
        gv.tv3.setText("Lugar Final: "+valores.get(position).getLugarf() + "");
        gv.tv4.setText("Fecha inicio: "+valores.get(position).getFechai() + "");
        gv.tv5.setText("Fecha fin: "+valores.get(position).getFechaf() + "");
        gv.tv6.setText("Tipo: "+valores.get(position).getTipo() + "");
        return convertView;
    }


}
