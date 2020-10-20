package com.example.restaurantseminario.menuAdapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.restaurantseminario.FormularioRestaurant;
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
    public View getView(int position, View view, final ViewGroup parent) {
        if(view  == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_list_menu, null);
            TextView txtDescription = view.findViewById(R.id.txt_owner_menu_description);
            txtDescription.setText(LISTDATA.get(position).getDescription());

            ImageView urlImage =  view.findViewById(R.id.imegae_view_owner_menu_image);
            Glide.with(context)
                    .load(LISTDATA.get(position).getUrlPhotoProducto())
                    .into(urlImage);
            TextView txtNombre = (TextView) view.findViewById(R.id.txt_owner_menu_title);
            txtNombre.setText(LISTDATA.get(position).getName());

            TextView txtPrecio = (TextView) view.findViewById(R.id.txt_owner_menu_precio);
            txtPrecio.setText(LISTDATA.get(position).getPrecio().toString());
            Button btnEditar = (Button) view.findViewById(R.id.btn_owner_menu_editar);
            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(parent.getContext(), FormularioRestaurant.class);
                    parent.getContext().startActivity(intent);

                }

            });

        }
        return view;
    }
}
