package com.example.restaurantseminario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class EditarMenuOwner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_menu_owner);
        Bundle bundle = getIntent().getExtras();
        String name =bundle.getString("Name");
        String precio = bundle.getString("precio");
        String description = bundle.getString("description");
        String getUrlPhotoProducto = bundle.getString("getUrlPhotoProducto");
        Toast.makeText(this, precio, Toast.LENGTH_LONG ).show();

        LodaData(name, precio, description, getUrlPhotoProducto );
    }

    private void LodaData(String name, String precio, String description, String getUrlPhotoProducto) {

        ImageView imageViewMenu = (ImageView) findViewById(R.id.imageView_menu_edit_photoproducto);
        Glide.with(this)
                .load(getUrlPhotoProducto)
                .into(imageViewMenu);

        EditText editTextName = (EditText) findViewById(R.id.txt_menu_edit_name);
        editTextName.setText(name);

        EditText editTextPrecio = (EditText) findViewById(R.id.txt_menu_edit_precio);
        editTextPrecio.setText(precio +"");

        EditText editTextDescription = (EditText) findViewById(R.id.txt_menu_edit_description);
        editTextDescription.setText(description);



    }

}