package com.example.restaurantseminario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.restaurantseminario.utils.DataServer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class EditarMenuOwner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_menu_owner);
        Bundle bundle = getIntent().getExtras();
        String idMenu = bundle.getString("idMenu");
        String name =bundle.getString("Name");
        String precio = bundle.getString("precio");
        String description = bundle.getString("description");
        String getUrlPhotoProducto = bundle.getString("getUrlPhotoProducto");
        Toast.makeText(this, precio, Toast.LENGTH_LONG ).show();

        LodaData(idMenu,name, precio, description, getUrlPhotoProducto );
    }

    private void LodaData(final String idMneu, String name, String precio, final String description, String getUrlPhotoProducto) {

        ImageView imageViewMenu = (ImageView) findViewById(R.id.imageView_menu_edit_photoproducto);
        Glide.with(this)
                .load(getUrlPhotoProducto)
                .into(imageViewMenu);

        final EditText editTextName = (EditText) findViewById(R.id.txt_menu_edit_name);
        editTextName.setText(name);

        final EditText editTextPrecio = (EditText) findViewById(R.id.txt_menu_edit_precio);
        editTextPrecio.setText(precio +"");

        final EditText editTextDescription = (EditText) findViewById(R.id.txt_menu_edit_description);
        editTextDescription.setText(description);


        Button btnGuardarCambios = (Button) findViewById(R.id.btn_editMenu_guardarCambios);
        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(
                        editTextName.getText().toString().length() > 0 &&
                        editTextPrecio.getText().toString().length() > 0 &&
                        editTextDescription.getText().toString().length() > 0
                ){
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    params.add("nombre", editTextName.getText().toString());
                    params.add("precio",editTextPrecio.getText().toString());
                    params.add("descripcion", editTextDescription.getText().toString());
                    client.put(DataServer.HOST_MENU_UPDATE_DATA + idMneu,params, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            final AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                            alert.setMessage("Menu actualizado correctamente");
                            alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            alert.show();
                        }
                    });
                }else {
                    Toast.makeText(v.getContext(), "complete los campos requeridos", Toast.LENGTH_LONG).show();
                }


            }
        });

    }

}