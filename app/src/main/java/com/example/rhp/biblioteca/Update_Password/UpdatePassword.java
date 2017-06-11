package com.example.rhp.biblioteca.Update_Password;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rhp.biblioteca.MainMenu;
import com.example.rhp.biblioteca.R;
import com.example.rhp.biblioteca.Regresar_Prestamo.RegresarPrestamo;
import com.example.rhp.biblioteca.com.google.zxing.integration.android.IntentIntegrator;
import com.example.rhp.biblioteca.com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdatePassword extends AppCompatActivity {

    Button bCambiar;
    EditText etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        final UpdatePassword that=this;
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cambia tu password");

        Intent intent = getIntent();
        final String mat=intent.getStringExtra("matricula");

        bCambiar = (Button)findViewById(R.id.bCambiar);
        etPassword = (EditText)findViewById(R.id.etPassword);

        bCambiar.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View arg0)
            {
                String password= etPassword.getText().toString();
                postJSON(mat,password);
                Toast toast = Toast.makeText(getApplicationContext(), "Password cambiado con Exito", Toast.LENGTH_SHORT);toast.show();
                Intent actividad = new Intent(that, MainMenu.class);
                startActivity(actividad);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void postJSON(final String mat,final String pass)
    {
        final UpdatePassword that = this;
        final RequestQueue queue= Volley.newRequestQueue(this);

        String url ="https://quiet-tundra-44981.herokuapp.com/updates/updatePassword.php";
        JSONObject params = new JSONObject();

        try {
            params.put("idAlumnos", mat);
            params.put("Password", pass);


        } catch (Exception e) {

        }

// Instantiate the RequestQueue.


        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject result = new JSONObject(response);
                    Toast toast = Toast.makeText(getApplicationContext(), "Password cambiado con Exito", Toast.LENGTH_SHORT);toast.show();
                    Log.d("Response", result.toString());

                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response", error.toString());
                Toast toast = Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT);toast.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                params.put("idAlumnos", mat);
                params.put("Password", pass);

                return params;
            }
        };
        queue.add(sr);
    }
}
