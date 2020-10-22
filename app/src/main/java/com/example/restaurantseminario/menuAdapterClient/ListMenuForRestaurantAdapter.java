package com.example.restaurantseminario.menuAdapterClient;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.restaurantseminario.FormularioRestaurant;
import com.example.restaurantseminario.R;
import com.example.restaurantseminario.utils.DataServer;
import com.example.restaurantseminario.utils.DataUser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ListMenuForRestaurantAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<StructureListMenuRestaurant> LISTDATA;
    public ListMenuForRestaurantAdapter(ArrayList<StructureListMenuRestaurant> data, Context context){
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
    public View getView(final int position, View view, final ViewGroup parent) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_list_menu_client, null);
            TextView txtDescription = view.findViewById(R.id.txt_client_menu_description);
            txtDescription.setText(LISTDATA.get(position).getDescription());

            ImageView urlImage =  view.findViewById(R.id.imageView_client_menu_list);
            Glide.with(context)
                    .load(LISTDATA.get(position).getUrlPhotoProducto())
                    .into(urlImage);
            TextView txtNombre = (TextView) view.findViewById(R.id.txt_client_menu_title);
            txtNombre.setText(LISTDATA.get(position).getName().toUpperCase());

            TextView txtPrecio = (TextView) view.findViewById(R.id.txt_client_menu_precio);
            txtPrecio.setText(String.valueOf(LISTDATA.get(position).getPrecio()));
            Button btnEditar = (Button) view.findViewById(R.id.btn_client_menu_agregar);
            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent intent = new Intent(parent.getContext(), FormularioRestaurant.class);
                    //parent.getContext().startActivity(intent);
                    Toast.makeText(parent.getContext(), LISTDATA.get(position).getIdMenu(), Toast.LENGTH_LONG).show();
                    AsyncHttpClient client = new AsyncHttpClient();

                    RequestParams params = new RequestParams();
                    params.add("idMenu",LISTDATA.get(position).getIdMenu().toString());
                    params.add("idRestaurant", LISTDATA.get(position).getIdRestaurant());
                    params.add("nameMenu",LISTDATA.get(position).getName());
                    String precio = String.valueOf(LISTDATA.get(position).getPrecio());
                    params.add("precioUnitario", precio);
                    params.add("urlFotoProducto",LISTDATA.get(position).getUrlPhotoProducto());

                    client.post(DataServer.HOST_ADD_MENU_TEMPORAL+DataUser.id, params ,new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            try {

                                Toast.makeText(context, response.getString("message"),Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }

            });
        }

        return view;
    }
}
