package com.example.restaurantseminario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantseminario.utils.DataServer;
import com.example.restaurantseminario.utils.DataUser;
import com.loopj.android.http.*;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        SigninUse();
        SignOut();

        LanzarDirectoAUnActividad();

    }

    public void LanzarDirectoAUnActividad(){
        Intent intent = new Intent(this, BottonNavigstionActivity.class);
        startActivityForResult(intent, 0);
    }



    private void SignOut() {
        Button resgister = (Button) findViewById(R.id.btn_signup);
        resgister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RegisterUser.class);
                startActivityForResult(intent,0);
            }
        });
    }

    private void SigninUse() {
        final Button login = (Button) this.findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "login", Toast.LENGTH_LONG).show();
                EditText email = (EditText) findViewById(R.id.txt_email);
                EditText password = (EditText) findViewById(R.id.txt_password);

                if(password.getText().toString().length()!= 0 && email.getText().toString().length() != 0){
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    params.add("email",email.getText().toString());
                    params.add("password",password.getText().toString());
                    Log.d("email:",email.getText().toString());
                    Log.d("password:",password.getText().toString());
                    Log.d("host:",DataServer.HOST_LOGIN);

                    client.post(DataServer.HOST_LOGIN,params,new JsonHttpResponseHandler(){

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Toast.makeText(MainActivity.this, "antes", Toast.LENGTH_SHORT).show();

                            super.onSuccess(statusCode, headers, response);

                            try {
                                response.getString("message");
                                Toast.makeText(MainActivity.this,response.getString("message") , Toast.LENGTH_SHORT).show();
                                JSONObject dataUser = response.getJSONObject("userData");

                                DataUser.name = dataUser.getString("name");
                                //DataUser.lastName = dataUser.getString("lastName").toString().length()>0? dataUser.getString("lastName"):"";
                                DataUser.email = dataUser.getString("email");
                                DataUser.id = dataUser.getString("_id");
                                DataUser.role = dataUser.getString("role");
                                //DataUser.token = (dataUser.getString("token").length()>0)? dataUser.getString("token"):"";

                                Toast.makeText(MainActivity.this, DataUser.id, Toast.LENGTH_SHORT).show();

                                //ir la segunda activida (BottonNavigationActivity)
                                Intent intent = new Intent(MainActivity.this,BottonNavigstionActivity.class);
                                startActivityForResult(intent,0);

                            }catch (JSONException e){
                                Toast.makeText(MainActivity.this,"error al ingresar" , Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)  {
                            try {
                                errorResponse.getString("error");
                                Toast toast1 = Toast.makeText(getApplicationContext(), errorResponse.getString("error"), Toast.LENGTH_SHORT);
                                toast1.show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                    });

                    //password.setText("");
                }else{
                    Toast.makeText(MainActivity.this, "Complete los campos requeridos", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}