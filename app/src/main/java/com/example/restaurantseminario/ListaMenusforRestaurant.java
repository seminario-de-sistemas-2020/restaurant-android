package com.example.restaurantseminario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantseminario.menuAdapter.DashBoardAdapter;
import com.example.restaurantseminario.menuAdapter.StructureDashboard;
import com.example.restaurantseminario.menuAdapterClient.ListMenuForRestaurantAdapter;
import com.example.restaurantseminario.menuAdapterClient.StructureListMenuRestaurant;
import com.example.restaurantseminario.utils.DataServer;
import com.example.restaurantseminario.utils.DataUser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ListaMenusforRestaurant extends AppCompatActivity {


    private ListaMenusforRestaurant root = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_menusfor_restaurant);

        Bundle bundle = getIntent().getExtras();
        String idRestaurant = bundle.getString("idRestaurant");

        Toast.makeText(this, idRestaurant,Toast.LENGTH_LONG ).show();

        //TextView txtListMenuPrueba = (TextView) findViewById(R.id.txt_list_menu_id);
        //txtListMenuPrueba.setText(idRestaurant);

        IsUser(idRestaurant);

    }

    private void IsUser(String idRestaurant) {

        final ListView list  = findViewById(R.id.listView_client);
        final ArrayList<StructureListMenuRestaurant> datos = new ArrayList<>();

        final String IDRESTAURANT= idRestaurant;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(DataServer.HOST_LIST_MENU_IDRESTAURANTE + IDRESTAURANT +"&order=asc", new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    response.getString("listMenu");

                    Toast.makeText(root, response.getString("idRestaurant"), Toast.LENGTH_SHORT).show();
                    JSONArray listData = response.getJSONArray("listMenu");
                    for (int i=0; i<listData.length();i++){
                        JSONObject obj = listData.getJSONObject(i);
                        String idMenu = obj.getString("_id");
                        String idRestaurant = obj.getString("idRestaurant");
                        String nombre = obj.getString("nombre");
                        String urlImage = obj.getString("fotoProducto");
                        String descripcion = obj.getString("descripcion");
                        double precio =(double) obj.getDouble("precio");


                        StructureListMenuRestaurant item = new StructureListMenuRestaurant();
                        item.setIdMenu(idMenu);
                        item.setIdRestaurant(idRestaurant);
                        item.setName(nombre);
                        item.setUrlPhotoProducto(urlImage);
                        item.setDescription(descripcion);
                        item.setPrecio(precio);

                        datos.add(item);

                    }


                    ListMenuForRestaurantAdapter adapter = new ListMenuForRestaurantAdapter(datos,root);
                    list.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }

}