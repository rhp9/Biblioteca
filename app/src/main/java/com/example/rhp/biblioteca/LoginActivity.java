package com.example.rhp.biblioteca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.*;
import com.example.rhp.biblioteca.Consulta_Libros.ConsultaLibros;
import com.example.rhp.biblioteca.Registro.RegisterActivity;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity
{
    String mat,pass;
    public static final String KEY_USERNAME="idAlumnos";
    public static final String KEY_PASSWORD="Password";
    private EditText matricula,password;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        final  LoginActivity that=this;

        Button bLogin = (Button) findViewById(R.id.bLogin);
         matricula = (EditText) findViewById(R.id.matricula);
          password = (EditText) findViewById(R.id.password);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                postJSON();
            }
        });

        Button bRegister = (Button) findViewById(R.id.bRegistro);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent actividad = new Intent(that, RegisterActivity.class);
                startActivity(actividad);
            }
        });

    }


    public void postJSON()
    {
        final LoginActivity that = this;
        final RequestQueue queue=Volley.newRequestQueue(this);

        String url ="https://quiet-tundra-44981.herokuapp.com/Selects/selectLogin.php";
        mat = matricula.getText().toString().trim();
        pass = password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success"))
                        {
                            getSupportActionBar().setTitle("Bienvenido "+mat);
                            Intent intent = new Intent(that, MainMenu.class);
                            intent.putExtra("matricula", mat);
                            startActivity(intent);
                        }else{
                            Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_USERNAME,mat);
                map.put(KEY_PASSWORD,pass);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
