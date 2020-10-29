package com.example.restaurantseminario.ui.oredenes;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantseminario.R;
import com.example.restaurantseminario.ordenesAdapter.OrdenesAdapter;
import com.example.restaurantseminario.ordenesAdapter.StructureDataOrdenes;
import com.example.restaurantseminario.utils.DataServer;
import com.example.restaurantseminario.utils.DataUser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrdenesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdenesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrdenesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrdenesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrdenesFragment newInstance(String param1, String param2) {
        OrdenesFragment fragment = new OrdenesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_ordenes, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        calculatoPrecioTOTAL();


        Toast.makeText(getContext(), "Hello from fragment ordesdes", Toast.LENGTH_LONG).show();

        final ListView list = this.getActivity().findViewById(R.id.order_list);
        final ArrayList<StructureDataOrdenes> datos = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(DataServer.HOST_LIST_ALL_ORDENES_USER + DataUser.id, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    JSONArray listData = response.getJSONArray("litsOrdenes");
                    //double precioTotal = 0.0;
                    for(int i= 0; i < listData.length(); i++){
                        JSONObject obj = listData.getJSONObject(i);
                        String name = (String) obj.getString("nameMenu");
                        String idOrden = (String) obj.getString("_id");
                        double precioUnitario = Double.parseDouble(obj.getString("precioUnitario"));
                        double cantidad = Double.parseDouble(obj.getString("cantidad"));
                        double cantidadTotal = Double.parseDouble(obj.getString("precio_cantidad_tocal"));
                        String photoProducto = obj.getString("fotoProducto");

                        //precio total que debe el usuario

                        //precioTotal = precioTotal + cantidadTotal;

                        StructureDataOrdenes item = new StructureDataOrdenes();
                        item.setNameMenu(name);
                        item.setIdOrden(idOrden);
                        item.setPrecioUnitario(precioUnitario);
                        item.setCantidad(cantidad);
                        item.setPrecioCantidadTotal(cantidadTotal);
                        item.setPhotoProducto(photoProducto);


                        datos.add(item);
                    }

                    //DataUser.PRECIO_TOTAL = precioTotal;

                    OrdenesAdapter adapter = new OrdenesAdapter(datos, getContext());
                    list.setAdapter(adapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }


        });

        Button btnCalcularprecioTotal = (Button)getActivity().findViewById(R.id.btn_orders_calcular_preciototal);
        btnCalcularprecioTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculatoPrecioTOTAL();
            }
        });



    }

    public void calculatoPrecioTOTAL(){
        final TextView textView_TOTAL = (TextView)this.getActivity().findViewById(R.id.txt_orden_TOTAL);

        AsyncHttpClient client = new AsyncHttpClient();
        //PRECIO TOTAL DEL CLIENTE
        client.get(DataServer.HOST_GET_TOTALPRECIOCANTIDAD+DataUser.id, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try{
                    response.getString("prcioTotalCantidad");
                    //Toast.makeText(getContext(), response.getString("prcioTotalCantidad"), Toast.LENGTH_LONG ).show();
                    textView_TOTAL.setText(String.valueOf(response.getString("prcioTotalCantidad")));
                }catch (JSONException e){
                    e.printStackTrace();
                }


            }
        });
    }
}