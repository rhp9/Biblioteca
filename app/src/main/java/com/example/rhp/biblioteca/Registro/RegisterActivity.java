package com.example.rhp.biblioteca.Registro;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rhp.biblioteca.LoginActivity;
import com.example.rhp.biblioteca.MainMenu;
import com.example.rhp.biblioteca.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Registro");


         final RegisterActivity that = this;
        final EditText matEt = (EditText) findViewById(R.id.et_mat);
        final EditText nameEt = (EditText) findViewById(R.id.et_name);
        final EditText passEt= (EditText) findViewById(R.id.et_pass);
        Button submitBtn = (Button) findViewById(R.id.btn_submit);
        final RegisterActivity context = this;


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (matEt == null && nameEt == null && passEt == null) {
                    Toast.makeText(getApplicationContext(), "Llena todos los campos ", Toast.LENGTH_LONG).show();
                }
                else {

                    String mat = matEt.getText().toString();
                    String nombre = nameEt.getText().toString();
                    String pass = passEt.getText().toString();

                    postJSON(mat, nombre,pass);

                    Intent actividad = new Intent(that, LoginActivity.class);
                    startActivity(actividad);
                }
            }
        });

    }


    public void postJSON(final String matricula, final String nombre,final String password)
    {
        final RegisterActivity that = this;
        final RequestQueue queue= Volley.newRequestQueue(this);

        String url ="https://quiet-tundra-44981.herokuapp.com/index.php";
        JSONObject params = new JSONObject();

        try {
            params.put("idAlumnos", matricula);
            params.put("Nombre", nombre);
            params.put("Password",password);

        } catch (Exception e) {

        }

// Instantiate the RequestQueue.


        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject result = new JSONObject(response);
                    Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
                    Log.d("Response", result.toString());
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("idAlumnos", matricula);
                params.put("Nombre", nombre);
                params.put("Password",password);

                return params;
            }
        };
        queue.add(sr);
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
}
