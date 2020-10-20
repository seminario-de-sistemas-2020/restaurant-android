package com.example.restaurantseminario.menuAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.restaurantseminario.R;

import java.util.ArrayList;

public class DashBoardAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<StructureDashboard> LISTDATA;
    public DashBoardAdapter(ArrayList<StructureDashboard> data, Context context){
        LISTDATA = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return LISTDATA.size();
    }

    @Override
    public Object getItem(int position) {
        return LISTDATA.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view  == null){
            view = LayoutInflater.from(context).inflate(R.layout.formulario_registro_restauran, null);
            TextView txtPrueb = view.findViewById(R.id.txt_prueba);
            txtPrueb.setText(LISTDATA.get(position).getName());
        }
        return view;
    }
}
