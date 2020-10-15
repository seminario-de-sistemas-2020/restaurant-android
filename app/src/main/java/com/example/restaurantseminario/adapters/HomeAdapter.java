package com.example.restaurantseminario.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.restaurantseminario.R;


import java.util.ArrayList;

public class HomeAdapter extends BaseAdapter {

    private ArrayList<StructureDataHome> LISTDATA;
    private Context context;
    public HomeAdapter(ArrayList<StructureDataHome> data, Context context){
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        if( view == null ){
            view =LayoutInflater.from(context).inflate(R.layout.item_lista_home_data, null);
            TextView title = view.findViewById(R.id.txt_title);
            title.setText(LISTDATA.get(position).getNombre());
        }
        return view;
    }
}
