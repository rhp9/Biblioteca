package com.example.rhp.biblioteca.Consulta_Libros;

import android.app.SearchManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.rhp.biblioteca.R;

import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;

import org.json.JSONException;

import org.json.JSONObject;

import java.util.ArrayList;



import android.app.ProgressDialog;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

public class ConsultaLibros extends AppCompatActivity {
    ListView librosList;
    String url = "https://quiet-tundra-44981.herokuapp.com/Selects/selectLibros.php";
    ProgressDialog dialog;
    final ConsultaLibros that=this;
    String jsonResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_libros);

        getSupportActionBar().setTitle("Consulta Libros");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        librosList = (ListView)findViewById(R.id.listv);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.show();

        //Instantiate the RequestQueue
        RequestQueue queue= Volley.newRequestQueue(that);

        final ArrayList al = new ArrayList();
        //Request a string response from a private URL
        JsonArrayRequest jsonRequest= new JsonArrayRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONArray>() {
                    public void onResponse(JSONArray response) {
                        //Display the first 500 characters of the response String

                       try
                        {
                            jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject person = (JSONObject) response.get(i);

                                String nombre = person.getString("1");
                                String autor = person.getString("2");
                                String rama= person.getString("3");
                                String pres= person.getString("4");
                                String prestado="";


                                if(pres.equals("0"))
                                {
                                    prestado="NO";
                                }
                                else
                                {
                                    prestado="SI";
                                }

                                jsonResponse = "\nNombre: " + nombre + "\n\n" + "Autor: " + autor + "\n\n" + "Rama: " + rama + "\n\n" + "Prestado: " + prestado +"    \n";

                                al.add(jsonResponse);
                            }

                           ArrayAdapter adapter = new ArrayAdapter(that, R.layout.list_layout,R.id.nombre, al);
                            librosList.setAdapter(adapter);

                            dialog.dismiss();
                        }
                        catch(JSONException e)
                        {
                            Toast.makeText(getBaseContext(),"Fallo en la interpretacion del JSON",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                },new Response.ErrorListener(){
            public void onErrorResponse(VolleyError error){
                Log.d("Response","That didn't work");
                error.printStackTrace();
                Toast.makeText(getBaseContext(),"that didnt work",Toast.LENGTH_SHORT).show();
            }
        });
        //Add the request to the RequestQueue
        queue.add(jsonRequest);
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height)
    {
        Bitmap b = Bitmap.createBitmap(width , height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
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
