package com.example.restaurantseminario.ui.dashboard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.TrafficStats;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.restaurantseminario.AgregarNuevoMenu;
import com.example.restaurantseminario.FormularioRestaurant;
import com.example.restaurantseminario.R;
import com.example.restaurantseminario.adapters.HomeAdapter;
import com.example.restaurantseminario.adapters.StructureDataHome;
import com.example.restaurantseminario.menuAdapter.DashBoardAdapter;
import com.example.restaurantseminario.menuAdapter.StructureDashboard;
import com.example.restaurantseminario.utils.DataServer;
import com.example.restaurantseminario.utils.DataUser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private boolean yatieneRestaurante = DataUser.yatieneRestaurante;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        switch(DataUser.role){
            case "client":{
                siEsUser();
                break;
            }
            case "owner": {
                siEsOwner();
                break;
            }
        }
    }


    private void siEsOwner() {
       final ListView list  = this.getActivity().findViewById(R.id.hello);
       final ArrayList<StructureDashboard> datos = new ArrayList<>();

        final String IDRESTAURANT= DataUser.idOwnerRestarant;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(DataServer.HOST_LIST_MENU_IDRESTAURANTE + IDRESTAURANT +"&order=asc", new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    response.getString("listMenu");

                    Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    JSONArray listData = response.getJSONArray("listMenu");

                    for (int i=0; i<listData.length();i++){
                        JSONObject obj = listData.getJSONObject(i);

                        String nombre = obj.getString("nombre");
                        String idMenu = obj.getString("_id");
                        String idRestaurant = obj.getString("idRestaurant");
                        String urlImage = obj.getString("fotoProducto");
                        String descripcion = obj.getString("descripcion");
                        double precio =Double.parseDouble(obj.getString("precio"));


                        StructureDashboard item = new StructureDashboard();
                        item.setName(nombre);
                        item.setIdMenu(idMenu);
                        item.setIdRestaurant(idRestaurant);
                        item.setUrlPhotoProducto(urlImage);
                        item.setDescription(descripcion);
                        item.setPrecio(precio);

                        datos.add(item);

                    }


                    DashBoardAdapter adapter = new DashBoardAdapter(datos,getContext());
                    list.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


        //buttons add restaurante and add new menu
        Button btnAddRestaurant = (Button) this.getActivity().findViewById(R.id.btn_add_restaurant);
        btnAddRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(yatieneRestaurante==false){
                    Intent intent = new Intent(getContext(), FormularioRestaurant.class);
                    startActivityForResult(intent,0);
                }
                if(yatieneRestaurante == true){
                   Toast.makeText(getContext(),"Usted ya tiene un restaurante resgistrado",Toast.LENGTH_LONG).show();

                    AlertDialog.Builder alertDialoguedBuilder = new AlertDialog.Builder(getContext());
                    alertDialoguedBuilder.setMessage("Usted ya tiene un restaurante creado");
                    alertDialoguedBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialoguedBuilder.show();

                }


            }
        });

        //button crear nuevo menu
        Button btnCrearMenu = (Button)this.getActivity().findViewById(R.id.btn_lista_add_menu);
        btnCrearMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AgregarNuevoMenu.class);
                intent.putExtra("idRestaurante", IDRESTAURANT);
                startActivityForResult(intent,0);
            }
        });


    }




    private void siEsUser() {

        Button btnAddRestaurant = (Button) this.getActivity().findViewById(R.id.btn_add_restaurant);
        btnAddRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    AlertDialog.Builder alertDialoguedBuilder = new AlertDialog.Builder(getContext());
                    alertDialoguedBuilder.setMessage("Solicite el cambio de rol para agregar un restaurante");
                    alertDialoguedBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialoguedBuilder.show();


            }
        });

        Button btnCrearMenu = (Button)this.getActivity().findViewById(R.id.btn_lista_add_menu);
        btnCrearMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialoguedBuilder = new AlertDialog.Builder(getContext());
                alertDialoguedBuilder.setMessage("Usted no cuenta con el rol necesario para la añadir un menu");
                alertDialoguedBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialoguedBuilder.show();
            }
        });

    }


}