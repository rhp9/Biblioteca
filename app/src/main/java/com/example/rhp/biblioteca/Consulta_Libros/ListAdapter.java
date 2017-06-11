package com.example.rhp.biblioteca.Consulta_Libros;

import android.content.Context;

import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.ArrayAdapter;

import android.widget.TextView;

import com.example.rhp.biblioteca.R;

import org.json.JSONException;

import org.json.JSONObject;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<JSONObject>{

    int vg;

    ArrayList<JSONObject> list;

    Context context;

    public ListAdapter(Context context, int vg, int id, ArrayList<JSONObject> list){

        super(context,vg, id,list);

        this.context=context;

        this.vg=vg;

        this.list=list;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(vg, parent, false);

        TextView txtId=(TextView)itemView.findViewById(R.id.nombre);

        //TextView txtName=(TextView)itemView.findViewById(R.id.autor);

       //TextView txtSex=(TextView)itemView.findViewById(R.id.rama);

        try {

            txtId.setText(list.get(position).getString("Nombre"));

            //txtName.setText(list.get(position).getString("Autor"));

           // txtSex.setText(list.get(position).getString("Rama_idRama"));



        } catch (JSONException e) {

            e.printStackTrace();

        }



        return itemView;

    }

}