package com.example.restaurantseminario.ordenesAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.restaurantseminario.ListaMenusforRestaurant;
import com.example.restaurantseminario.R;
import com.example.restaurantseminario.ui.oredenes.OrdenesFragment;
import com.example.restaurantseminario.utils.DataServer;
import com.example.restaurantseminario.utils.DataUser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.ResourceBundle;

import cz.msebera.android.httpclient.Header;

public class OrdenesAdapter  extends BaseAdapter {

    private ArrayList<StructureDataOrdenes> LISTDATA;
    private Context context;
    public OrdenesAdapter(ArrayList<StructureDataOrdenes> data, Context context){
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
        if(view == null){

             final double precionUnitario = LISTDATA.get(position).getPrecioUnitario();
             final double cantidad = LISTDATA.get(position).getCantidad();
             final String idOrden = LISTDATA.get(position).getIdOrden();


            view = LayoutInflater.from(context).inflate(R.layout.item_list_ordenes, null);
            TextView textViewName = (TextView) view.findViewById(R.id.txt_order_name);
            textViewName.setText(LISTDATA.get(position).getNameMenu());

            TextView textViewPrecio = (TextView)view.findViewById(R.id.txt_order_precio);
            textViewPrecio.setText(String.valueOf(LISTDATA.get(position).getPrecioUnitario()));

            ImageView imageViewFotoProducto = (ImageView) view.findViewById(R.id.imageView_order);
            Glide.with(context)
                    .load(LISTDATA.get(position).getPhotoProducto())
                    .into(imageViewFotoProducto);

            final TextView textViewCntidad = (TextView)view.findViewById(R.id.txt_order_cantidad);
            textViewCntidad.setText(String.valueOf(LISTDATA.get(position).getCantidad()));

            final TextView textViewPrecioTotal = (TextView)view.findViewById(R.id.txt_order_precioTotal);
            textViewPrecioTotal.setText(String.valueOf( cantidad * precionUnitario));

           // final  TextView textViewTOTAL =(TextView) view.findViewById(R.id.txt_orden_TOTAL);

            //seekbar
            SeekBar seekBar = (SeekBar)view.findViewById(R.id.seekBar_order_cantidad);

            seekBar.setProgress(0); //inicial
            seekBar.setMax(50);  // fional
            final View finalView = view;



            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    double PROGRESS = progress+1;
                    textViewCntidad.setText(String.valueOf(PROGRESS));
                    textViewPrecioTotal.setText(String.valueOf( precionUnitario * PROGRESS ));

                    //DataUser.PRECIO_TOTAL = DataUser.PRECIO_TOTAL + precionUnitario*PROGRESS;
                    //Toast.makeText(parent.getContext(), String.valueOf(precionUnitario*progress),Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    int PROGRESS = seekBar.getProgress()+1;
                    final AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    params.add("cantidad", String.valueOf(PROGRESS));
                    client.patch(DataServer.HOST_UPDATE_CANTIDAD_PRODUCTO +idOrden,params,new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            client.get(DataServer.HOST_GET_TOTALPRECIOCANTIDAD + DataUser.id, new JsonHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    super.onSuccess(statusCode, headers, response);
                                    try{
                                        response.getString("prcioTotalCantidad");
                                        DataUser.PRECIO_TOTAL =Double.valueOf(response.getString("prcioTotalCantidad"));
                                    }catch (JSONException e){
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    });

                        //Toast.makeText(finalView.getContext(), "se detubo "+PROGRESS, Toast.LENGTH_LONG).show();

                }
            });



            Button buttonCancelar = (Button)view.findViewById(R.id.btn_order_cancelar);
            buttonCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(parent.getContext(), "Cancelar orden", Toast.LENGTH_LONG).show();
                    String cantidad =(String) textViewCntidad.getText();
                    String total = (String) textViewPrecioTotal.getText();
                    //Toast.makeText(parent.getContext(), idOrden+','+cantidad+","+total, Toast.LENGTH_SHORT).show();

                    AsyncHttpClient client = new AsyncHttpClient();
                    client.patch(DataServer.HOST_CANCELAR_ORDEN+idOrden, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);

                            try {
                                Toast.makeText(parent.getContext(), response.getString("stateOrden"), Toast.LENGTH_SHORT).show();
                                

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
