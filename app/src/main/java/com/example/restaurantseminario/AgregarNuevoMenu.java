package com.example.restaurantseminario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantseminario.ui.dashboard.DashboardFragment;
import com.example.restaurantseminario.utils.DataServer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class AgregarNuevoMenu extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageViewImg;
    private Button btnoption ,btnsend;
    private String Carpeta_Root="MisImages/";
    private String RUTA_IMAGES=Carpeta_Root+"Mis_Fotos";
    private String path;
    private int CODE_GALLERY= 10;
    private int CODE_CAMERA= 20;
    private Context root;
    private String IDRESTAURANTE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nuevo_menu);
        root = this;


        Bundle bundle = getIntent().getExtras();
        IDRESTAURANTE = bundle.getString("idRestaurante");


        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        checkPermission();
        loadComponents();
        formularioCrearMenu();

    }

    private void formularioCrearMenu() {
        final TextView textViewNombre = (TextView) findViewById(R.id.txt_crearMenu_nombre);
        final TextView textViewPrecio = (TextView) findViewById(R.id.txt_crearMenu_precio);
        final TextView textViewDescription = (TextView) findViewById(R.id.txt_crearMenu_dedscription);


        Button btncrerMenu =(Button) findViewById(R.id.btn_crearMenu_crearmenu);
        btncrerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(
                        textViewNombre.getText().toString().length() > 0 &&
                        textViewPrecio.getText().toString().length() > 0 &&
                        textViewDescription.getText().toString().length() > 0
                ){
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();

                    params.add("nombre", textViewNombre.getText().toString());
                    params.add("precio",textViewPrecio.getText().toString());
                    params.add("descripcion",textViewDescription.getText().toString());

                    client.post(DataServer.HOST_CREAR_NUEVO_MENU + IDRESTAURANTE,params, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            try {
                                JSONObject obj = response.getJSONObject("menuCreated");
                                String idMenu = obj.getString("_id");
                                Toast.makeText(root, idMenu,Toast.LENGTH_LONG).show();
                                enviarImg(idMenu);
                                // vuelave a la actividad anterior
                               Intent intent = new Intent(root, BottonNavigstionActivity.class);
                               startActivity(intent);
                               this.onFinish();

                            } catch (JSONException | FileNotFoundException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }else{
                    Toast.makeText(root,"complete los capos requerido",Toast.LENGTH_LONG).show();
                }
            }
        });
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








    private void loadComponents() {
        imageViewImg = this.findViewById(R.id.imageview_crearMenu_image);
        btnoption=(Button)this.findViewById(R.id.btn_crearMenu_seleccionarImagen);
       // btnsend=(Button) this.findViewById(R.id.btn_crearMenu_crearmenu);
        //btnsend.setOnClickListener(this);
        btnoption.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_crearMenu_seleccionarImagen){
            cargarImagenes();
        }
        //if(v.getId()==R.id.btn_crearMenu_crearmenu) {
          //  try {
             //   enviarImg();
           // } catch (FileNotFoundException e) {
             //   e.printStackTrace();
           // }
       // }
    }

    private void enviarImg(String IDMENU) throws FileNotFoundException {

        if ( imageViewImg.getDrawable() != null) {
            if (path != null) {
                File file = new File(path);
                RequestParams params = new RequestParams();
                params.put("image", file);
                AsyncHttpClient client = new AsyncHttpClient();
                //  if (UserData.ID != null) {
                client.patch( DataServer.HOST_UPLOAD_IMAGE_MENU + IDMENU, params, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        try {

                            Toast.makeText(getApplicationContext(), response.getString("ok"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });



            }
        } else {
            Toast.makeText(this, "No se ha sacado una foto", Toast.LENGTH_LONG).show();
        }
    }
    public void cargarImagenes(){

        final CharSequence[] options= {"Tomar Foto","Cargar de Galeria","Cancelar"};
        final AlertDialog.Builder alertopt= new AlertDialog.Builder(this);
        alertopt.setTitle("Seleccione una Opcion");
        alertopt.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (options[which].equals("Tomar Foto")) {
                    //  Toast.makeText(getApplication(), "camara", Toast.LENGTH_LONG).show();
                    LoadCamera();
                } else {
                    if (options[which].equals("Cargar de Galeria")) {

                        LoadMediaData();


                    } else {
                        dialog.dismiss();

                    }
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
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccionar la aplocacion"), 10);
    }

    private void LoadCamera( ){

        File fileimg =new File(Environment.getExternalStorageDirectory(),RUTA_IMAGES);
        boolean filecreado = fileimg.exists();
        String nameImg="";
        if(filecreado==false) {

            filecreado = fileimg.mkdirs();
        }if(filecreado==true){
            nameImg = "IMG_" +System.currentTimeMillis()/1000+ ".jpg";
        }
        path=Environment.getExternalStorageDirectory()+File.separator+RUTA_IMAGES+File.separator+nameImg;
        File imagen=new File(path);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        startActivityForResult(intent,20);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){

            switch (requestCode){
                case 10:


                    imageViewImg.setImageURI(data.getData());
                    break;

                case 20:
                    MediaScannerConnection.scanFile(this, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String I, Uri uri) {
                            Log.i("Ruta de almacenamiento","PATH:"+path);

                        }
                    });
                    Bitmap bitmap= BitmapFactory.decodeFile(path);
                    imageViewImg.setImageBitmap(bitmap);
                    break;



            }


        }
    }
}