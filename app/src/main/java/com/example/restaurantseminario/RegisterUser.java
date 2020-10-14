package com.example.restaurantseminario;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.restaurantseminario.utils.DataServer;
import com.example.restaurantseminario.utils.DataUser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Environment;
import android.preference.DialogPreference;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class RegisterUser extends AppCompatActivity {

    private static final int CODE_GALERY = 10;
    private String path;
    private ImageView imageViewImg;
    private Context root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        root = this;

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        checkPermission();
        RegisterNewUser();


    }





    private void RegisterNewUser() {
        Button resgitarUsuario = (Button) findViewById(R.id.btn_realizar_registro);
        Button subirFotoAvatar = (Button) findViewById(R.id.btn_tomarFoto);

        subirFotoAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(v.getId() == R.id.btn_tomarFoto){
                    Toast.makeText(RegisterUser.this ,"Tomar fotografia", Toast.LENGTH_LONG).show();
                    cargarImagenes();
                }
            }
        });

        resgitarUsuario.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                EditText name = (EditText) findViewById(R.id.txt_nombre);
                EditText lastName = (EditText) findViewById(R.id.txt_apellido);
                EditText phoneNumber = (EditText) findViewById(R.id.txt_celular);
                EditText email = (EditText) findViewById(R.id.txt_email);
                EditText password = (EditText) findViewById(R.id.txt_password);



                if(
                        name.getText().toString().toString().length() > 0 &&
                        lastName.getText().toString().toString().length() > 0 &&
                        phoneNumber.getText().toString().toString().length() > 0 &&
                        email.getText().toString().toString().length() > 0 &&
                        password.getText().toString().toString().length() > 0
                ){
                    Toast.makeText(v.getContext(), "Campos Completos", Toast.LENGTH_LONG).show();
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    params.add("name",name.getText().toString());
                    params.add("lastName",lastName.getText().toString());
                    params.add("phoneNumber",phoneNumber.getText().toString());
                    params.add("email",email.getText().toString());
                    params.add("password",password.getText().toString());
                    //params.add("name",name.getText().toString());
                    client.post(DataServer.HOST_NEW_USER, params, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            try {
                                response.getString("message");
                                Toast.makeText(RegisterUser.this ,response.getString("message"), Toast.LENGTH_LONG).show();
                                JSONObject dataUser = response.getJSONObject("userAdd");
                                    // funcoin que recibe un idUser para guardar la imagen
                                    EnviarFotoAvatar(dataUser.getString("_id"));

                                    // caragar los datos del usuario
                                DataUser.name = dataUser.getString("name");
                                DataUser.lastName = dataUser.getString("lastName");
                                DataUser.id = dataUser.getString("_id");
                                DataUser.role = dataUser.getString("role");
                                DataUser.email = dataUser.getString("email");
                                DataUser.imageAvatar = dataUser.getString("fotoAvatar");
                                DataUser.password = dataUser.getString("password");

                                //una vez cargado los datos el intent se va al home
                                Intent intent = new Intent(root, BottonNavigstionActivity.class);
                                startActivityForResult(intent, 0);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }else{
                    Toast.makeText(v.getContext(), "Complete los campos requeridos", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void EnviarFotoAvatar(final String idUser){

        Toast.makeText(RegisterUser.this, idUser, Toast.LENGTH_LONG).show();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        File file = new File(path);

        try {

            params.put("image", file);
            if (imageViewImg.getDrawable() != null){
                if (path != null){
                    client.patch(DataServer.HOST_IMAGE_AVATAR + idUser,params, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse){
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            try {
                                errorResponse.getString("message");
                                Toast.makeText(RegisterUser.this, errorResponse.getString("message"), Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }else {
                Toast.makeText(getApplicationContext(), "No se ha sacado una foto", Toast.LENGTH_LONG).show();
            }




        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "No se ha sacado una foto", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }




    }



    public void cargarImagenes(){

        final CharSequence[] options= {"Cargar de Galeria","Cancelar"};
        final AlertDialog.Builder alertopt= new AlertDialog.Builder(this);
        alertopt.setTitle("foto de perfil opcional");
       alertopt.setItems(options, new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
                   if (options[which].equals("Cargar de Galeria")) {

                       LoadMediaData();


                   } else {
                       dialog.dismiss();

                   }

           }
       });
        alertopt.show();
    }


    private void LoadMediaData()  {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(imageFileName,  ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        path = image.getAbsolutePath();
        Toast.makeText(RegisterUser.this ,"45555555", Toast.LENGTH_LONG).show();
        Log.d("ruta",path);
        Toast.makeText(RegisterUser.this ,path, Toast.LENGTH_LONG).show();

        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccionar la aplocacion"), 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 10:
                    imageViewImg = (ImageView) findViewById(R.id.imgView_avatar);
                    imageViewImg.setImageURI(data.getData());

            }

        }
    }


    private void checkPermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return ;

        }
        if(this.checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED  || this.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED|| this.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED ){

            return ;
        }else{
            this.requestPermissions( new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},99);
        }
        return ;


    }
}