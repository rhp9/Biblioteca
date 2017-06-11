package com.example.rhp.biblioteca.Prestamo;

import android.content.DialogInterface;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rhp.biblioteca.Consulta_Libros.ConsultaLibros;
import com.example.rhp.biblioteca.LoginActivity;
import com.example.rhp.biblioteca.MainMenu;
import com.example.rhp.biblioteca.com.google.zxing.integration.android.IntentIntegrator;
import com.example.rhp.biblioteca.com.google.zxing.integration.android.IntentResult;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.rhp.biblioteca.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class IngresarPrestamo extends AppCompatActivity
{
    private ImageButton scanBtn;
    Button bPrestar;
    private String m_Text = "";
    private EditText codeBar;
    String prestado;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_prestamo);
        final IngresarPrestamo that=this;
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ingresa un Prestamo");

        Intent intent = getIntent();
        final String mat=intent.getStringExtra("matricula");
        //Toast toast = Toast.makeText(getApplicationContext(),mat , Toast.LENGTH_SHORT);toast.show();

        scanBtn = (ImageButton)findViewById(R.id.bCamara);
        bPrestar = (Button)findViewById(R.id.bPrestar);
        //contentTxt = (TextView)findViewById(R.id.scan_content);
        codeBar= (EditText)findViewById(R.id.etCodeBar);

        scanBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View arg0)
            {
                IntentIntegrator scanIntegrator = new IntentIntegrator(that);
                scanIntegrator.initiateScan();
            }
        });

        bPrestar.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View arg0)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(that);
                builder.setTitle("Password Administrador");

                // Set up the input
                final EditText input = new EditText(that);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        if(m_Text.isEmpty())
                        {
                            Toast toast = Toast.makeText(getApplicationContext(), "Por favor ingresa la contraseña", Toast.LENGTH_SHORT);toast.show();
                        }
                        else
                        {
                            if (m_Text.equals("umin")) {
                                String code = codeBar.getText().toString();
                                if (code.isEmpty())
                                {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Por favor ingresa un codigo de barras", Toast.LENGTH_SHORT);toast.show();
                                }
                                else
                                {
                                    checkLibro(code, mat);
                                    Intent actividad = new Intent(that, MainMenu.class);
                                    startActivity(actividad);
                                }
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

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

    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
    //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null)
        {
        //we have a result
            String scanContent = scanningResult.getContents();
           // String scanFormat = scanningResult.getFormatName();

            //formatTxt.setText("FORMAT: " + scanFormat);
            codeBar.setText(scanContent);
        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);toast.show();
        }
    }

    public void postJSON(final String mat, final String codebar)
    {
        final IngresarPrestamo that = this;
        final RequestQueue queue= Volley.newRequestQueue(this);

        String url ="https://quiet-tundra-44981.herokuapp.com/Inserts/insertPrestamo.php";
        JSONObject params = new JSONObject();

        try {
            params.put("Matricula", mat);
            params.put("Codebar", codebar);


        } catch (Exception e) {

        }

// Instantiate the RequestQueue.


        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject result = new JSONObject(response);

                    Log.d("Response", result.toString());
                    Toast toast = Toast.makeText(getApplicationContext(), "El libro fue prestado con Exito", Toast.LENGTH_SHORT);toast.show();
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

                params.put("Matricula", mat);
                params.put("Codebar", codebar);

                return params;
            }
        };
        queue.add(sr);
    }

    public  void checkLibro(final String codebar,final String mat)
    {
        final IngresarPrestamo that = this;


        String url = "https://quiet-tundra-44981.herokuapp.com/Selects/checkLibros.php?id="+codebar;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray("result");
                    JSONObject collegeData = result.getJSONObject(0);

                    prestado = collegeData.getString("Prestado");


                    if(prestado.equals("0"))
                    {


                        postJSON(mat,codebar);
                    }
                    else
                    {
                        Toast toast = Toast.makeText(getApplicationContext(), "El libro no esta disponible", Toast.LENGTH_SHORT);toast.show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(that,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void check(final VolleyCallback callback)
    {

    }
    public interface VolleyCallback{
        void onSuccess(String result);
    }
}
