package com.example.restaurantseminario.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.restaurantseminario.R;
import com.example.restaurantseminario.RegisterUser;
import com.example.restaurantseminario.listMenus.listaDeMenus;


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
    public View getView(final int position, View view, final ViewGroup viewGroup) {
        if( view == null ){
            view =LayoutInflater.from(context).inflate(R.layout.item_lista_home_data, null);
            TextView title = view.findViewById(R.id.txt_title);
            title.setText(LISTDATA.get(position).getNombre());
            Button button = view.findViewById(R.id.btn_ver_menu);
            ImageView logoImage = view.findViewById(R.id.img_view_foto_restauant);
            //if(LISTDATA.get(position).getLogo().equals("")){
                Glide.with(context)
                        .load(LISTDATA.get(position).getLogo())
                        .into(logoImage);

            //}
            TextView txtViewNumerPhone = (TextView) view.findViewById(R.id.txt_phone);
            txtViewNumerPhone.setText(LISTDATA.get(position).getTelefono().toString());
            TextView textViewLugar = (TextView) view.findViewById(R.id.txt_calle);
            textViewLugar.setText(LISTDATA.get(position).getCalle());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(v.getContext(), LISTDATA.get(position).getIdClient(), Toast.LENGTH_SHORT).show();

                    //como navegar a otro fragmen o a otra activida
                    Intent intent =new Intent(v.getContext(), RegisterUser.class);
                    //ViewGroup.inflate(intent,0);

                }
            });
        }
        return view;
    }
}
