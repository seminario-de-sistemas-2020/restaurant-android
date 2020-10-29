package com.example.restaurantseminario.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.restaurantseminario.MainActivity;
import com.example.restaurantseminario.R;
import com.example.restaurantseminario.adapters.HomeAdapter;
import com.example.restaurantseminario.adapters.StructureDataHome;
import com.example.restaurantseminario.utils.DataServer;
import com.example.restaurantseminario.utils.DataUser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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
        switch (DataUser.role) {
            case "client":{
                siRoleUser(this.getContext());
                return;
            }
            case "owner":{
                isRoleOwner(this.getContext());
                return;
            }
        }
    }

    private void isRoleOwner( final Context context) {

        final ListView list  = this.getActivity().findViewById(R.id.restaurante_list);
        final ArrayList<StructureDataHome>datos = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(DataServer.HOST_SHOW_IDRESTAURANT_OWNER + DataUser.id, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                            response.getString("message");
                    Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    JSONArray listData = response.getJSONArray("restaurants");

                    if (listData.length()>0){
                        DataUser.yatieneRestaurante = true;
                    }

                    for (int i=0;i<listData.length();i++){
                        JSONObject obj = listData.getJSONObject(i);

                        DataUser.idOwnerRestarant = (String) obj.getString("_id");

                        String nombre = (String)obj.getString("nombre");
                        String id = (String) obj.getString("_id");
                        String idClient = (String) obj.getString("idClient");
                        String nit = (String) obj.getString("nit");
                        String propietario = (String) obj.getString("propietario");
                        String calle = (String) obj.getString("calle");
                        int telefono = (int) obj.getInt("telefono");
                        double lat = (double) obj.getInt("lat");
                        double log = (double) obj.getDouble("log");
                        String logo =(String) obj.getString("logo");
                        String fotoLugar = (String) obj.getString("fotoLugar");

                        StructureDataHome item = new StructureDataHome();
                        item.setNombre(nombre);
                        item.setId(id);
                        item.setIdClient(idClient);
                        item.setNit(nit);
                        item.setPropietario(propietario);
                        item.setCalle(calle);
                        item.setTelefono(telefono);
                        item.setLat(lat);
                        item.setLog(log);
                        item.setLogo(logo);
                        item.setFotoLugar(fotoLugar);

                        datos.add(item);
                    }

                    HomeAdapter adapter = new HomeAdapter(datos,context);
                    list.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

    }



    private void siRoleUser(final Context context) {

        final ListView list  = this.getActivity().findViewById(R.id.restaurante_list);
        final ArrayList<StructureDataHome>datos = new ArrayList<>();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(DataServer.HOST_LIST_ALL_RESTAURANTS,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    response.getString("message");
                    Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    JSONArray listData = response.getJSONArray("restaurants");
                    for (int i=0;i<listData.length();i++){
                        JSONObject obj = listData.getJSONObject(i);
                        String nombre = (String)obj.getString("nombre");
                        String id = (String) obj.getString("_id");
                        String idClient = (String) obj.getString("idClient");
                        String nit = (String) obj.getString("nit");
                        String propietario = (String) obj.getString("propietario");
                        String calle = (String) obj.getString("calle");
                        int telefono = (int) obj.getInt("telefono");
                        double lat = (double) obj.getInt("lat");
                        double log = (double) obj.getDouble("log");
                        String logo =(String) obj.getString("logo");
                        String fotoLugar = (String) obj.getString("fotoLugar");

                        StructureDataHome item = new StructureDataHome();
                        item.setNombre(nombre);
                        item.setId(id);
                        item.setIdClient(idClient);
                        item.setNit(nit);
                        item.setPropietario(propietario);
                        item.setCalle(calle);
                        item.setTelefono(telefono);
                        item.setLat(lat);
                        item.setLog(log);
                        item.setLogo(logo);
                        item.setFotoLugar(fotoLugar);

                        datos.add(item);
                    }

                    HomeAdapter adapter = new HomeAdapter(datos,context);
                    list.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


}