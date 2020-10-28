package com.example.restaurantseminario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
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

import com.example.restaurantseminario.utils.DataServer;
import com.example.restaurantseminario.utils.DataUser;
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

public class FormularioRestaurant extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageViewImg;
    private ImageView imageViewLugar;
    private Button btnoption ,btnsend;
    private String Carpeta_Root="MisImages/";
    private String RUTA_IMAGES=Carpeta_Root+"Mis_Fotos";
    private String path;
    private String path1;
    private int CODE_GALLERY= 10;
    private int CODE_CAMERA= 20;

    private int CODE_GALLERY1= 101;
    private int CODE_CAMERA1= 202;
    private Context root;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_restaurant);
        checkPermission();
        root= this;

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        loadDataFormulario();
        loadComponents();


    }

    private void loadDataFormulario() {
        final TextView textViewNombre = (TextView) findViewById(R.id.txt_formularioRestaurant_name);
        final TextView textViewNit = (TextView) findViewById(R.id.txt_formualrioRestaurant_nit);
        final TextView textViewPropietario = (TextView) findViewById(R.id.txt_formularioRestaurant_propietario);
        final TextView textView1Calle = (TextView) findViewById(R.id.txt_formularioRestaurant_calle);
        final TextView textViewTelefono = (TextView) findViewById(R.id.txt_formularioResaturant_telefono);

        Button btnResgistarRestaurant = (Button) findViewById(R.id.btn_formularioRestaurant_registra);
        btnResgistarRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (
                        textViewNombre.getText().toString().toString().length() > 0 &&
                        textViewNit.getText().toString().toString().length() > 0 &&
                        textViewPropietario.getText().toString().toString().length() > 0 &&
                        textView1Calle.getText().toString().toString().length() > 0 &&
                        textViewTelefono.getText().toString().toString().length() > 0
                ) {
                    Toast.makeText(v.getContext(), "datos completos", Toast.LENGTH_SHORT).show();
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    params.add("nombre", textViewNombre.getText().toString());
                    params.add("idcliente", DataUser.id);
                    params.add("nit", textViewNit.getText().toString());
                    params.add("propietario", textViewPropietario.getText().toString());
                    params.add("calle", textView1Calle.getText().toString());
                    params.add("telefono", textViewTelefono.getText().toString());

                    client.post(DataServer.HOST_CREATE_REGISTRAR_RESTAURANT, params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);

                            try {

                                    JSONObject obj = response.getJSONObject("data");
                                    String idNewRestaurante = obj.getString("_id");

                                    enviarImg(idNewRestaurante);

                            } catch (JSONException | FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(root, BottonNavigstionActivity.class);
                            startActivityForResult(intent, 0);
                        }
                    });
                } else {
                    Toast.makeText(v.getContext(), "complete los campos requeridos", Toast.LENGTH_SHORT).show();
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
        imageViewImg = this.findViewById(R.id.imageView_formulariorestaurante_logo);
        imageViewLugar = this.findViewById(R.id.imageview_formularioRestaurante_lugar);
        btnoption=(Button)this.findViewById(R.id.btn_formularioRestaurant_logo);
        btnsend=(Button) this.findViewById(R.id.btn_formularioRestaurant_lugar);
        btnsend.setOnClickListener(this);
        btnoption.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_formularioRestaurant_logo){
            cargarImagenes(10, 20);
        }
        if(v.getId()==R.id.btn_formularioRestaurant_lugar) {
            cargarImagenes(101,202);
        }
    }
    public void onClick(){

    }

    private void enviarImg(String idNewRestaurant) throws FileNotFoundException {

        if ( imageViewImg.getDrawable() != null) {
            if (path != null) {
                File file = new File(path);
                RequestParams params = new RequestParams();
                params.put("image", file);
                AsyncHttpClient client = new AsyncHttpClient();
                //  if (UserData.ID != null) {
                client.post(DataServer.HOST_UPLOAD_LOGO+idNewRestaurant, params, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                        Toast.makeText(getApplicationContext(),"FAIL",Toast.LENGTH_SHORT);
                    }
                });



            }
        } else {
            Toast.makeText(this, "No se ha sacado una foto", Toast.LENGTH_LONG).show();
        }

        if ( imageViewLugar.getDrawable() != null) {
            if (path1 != null) {
                File file = new File(path1);
                RequestParams params = new RequestParams();
                params.put("image", file);
                AsyncHttpClient client = new AsyncHttpClient();
                //  if (UserData.ID != null) {
                client.post(DataServer.HOST_UPLOAD_FOTO_LUGAR+idNewRestaurant, params, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                        Toast.makeText(getApplicationContext(),"FAIL",Toast.LENGTH_SHORT);
                    }
                });



            }
        } else {
            Toast.makeText(this, "No se ha sacado una foto", Toast.LENGTH_LONG).show();
        }
    }

    public void cargarImagenes(final int MEDIA, final int CAMARA){

        final CharSequence[] options= {"Tomar Foto","Cargar de Galeria","Cancelar"};
        final AlertDialog.Builder alertopt= new AlertDialog.Builder(this);
        alertopt.setTitle("Seleccione una Opcion");
        alertopt.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (options[which].equals("Tomar Foto")) {
                    //  Toast.makeText(getApplication(), "camara", Toast.LENGTH_LONG).show();
                    LoadCamera(CAMARA);
                } else {
                    if (options[which].equals("Cargar de Galeria")) {

                        LoadMediaData(MEDIA);


                    } else {
                        dialog.dismiss();

                    }
                }
            }


        });
        alertopt.show();

    }

    private void LoadMediaData(int MEDIA)  {
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
        if(MEDIA==10){
            path = image.getAbsolutePath();
            intent.setType("image/");
            //startActivityForResult(intent.createChooser(intent, "Seleccionar la aplocacion"), 10);
            startActivityForResult(intent.createChooser(intent, "Seleccionar la aplocacion"), MEDIA);
        }
        if(MEDIA==101){
            path1 = image.getAbsolutePath();
            intent.setType("image/");
            //startActivityForResult(intent.createChooser(intent, "Seleccionar la aplocacion"), 10);
            startActivityForResult(intent.createChooser(intent, "Seleccionar la aplocacion"), MEDIA);
        }

    }

    private void LoadCamera(int CAMARA ){

        File fileimg =new File(Environment.getExternalStorageDirectory(),RUTA_IMAGES);
        boolean filecreado = fileimg.exists();
        String nameImg="";
        if(filecreado==false) {

            filecreado = fileimg.mkdirs();
        }if(filecreado==true){
            nameImg = "IMG_" +System.currentTimeMillis()/1000+ ".jpg";
        }
        if(CAMARA == 20){
            path=Environment.getExternalStorageDirectory()+File.separator+RUTA_IMAGES+File.separator+nameImg;

            File imagen=new File(path);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
            //startActivityForResult(intent,20);
            startActivityForResult(intent,CAMARA);
        }
        if(CAMARA==202){
            path1=Environment.getExternalStorageDirectory()+File.separator+RUTA_IMAGES+File.separator+nameImg;

            File imagen=new File(path1);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
            //startActivityForResult(intent,20);
            startActivityForResult(intent,CAMARA);
        }

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
                case 101:


                    imageViewLugar.setImageURI(data.getData());
                    break;

                case 202:
                    MediaScannerConnection.scanFile(this, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String I, Uri uri) {
                            Log.i("Ruta de almacenamiento","PATH:"+path);

                        }
                    });
                    Bitmap bitmap1= BitmapFactory.decodeFile(path1);
                    imageViewLugar.setImageBitmap(bitmap1);
                    break;

            }


        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
