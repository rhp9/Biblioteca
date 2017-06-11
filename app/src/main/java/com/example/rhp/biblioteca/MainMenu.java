package com.example.rhp.biblioteca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.rhp.biblioteca.Consulta_Libros.ConsultaLibros;
import com.example.rhp.biblioteca.Consulta_Prestamos.ConsultaPrestamos;
import com.example.rhp.biblioteca.Prestamo.IngresarPrestamo;
import com.example.rhp.biblioteca.Regresar_Prestamo.RegresarPrestamo;
import com.example.rhp.biblioteca.Update_Password.UpdatePassword;

public class MainMenu extends AppCompatActivity {
    String mat="";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        final MainMenu that=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getSupportActionBar().setTitle("Main Menu");

        Intent intent = getIntent();
        mat=intent.getStringExtra("matricula");


       ImageButton bCLibros = (ImageButton) findViewById(R.id.bCLibros);

        bCLibros.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View arg0)
            {
                Intent actividad = new Intent(that, ConsultaLibros.class);
                startActivity(actividad);
                Toast.makeText(that,"Consulta de Libros", Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton bPRestados = (ImageButton) findViewById(R.id.bPrestados);

        bPRestados.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0)
            {
                Intent actividad = new Intent(that, IngresarPrestamo.class);
                actividad.putExtra("matricula",mat);
                startActivity(actividad);
                Toast.makeText(that,"Ingresa Prestamos", Toast.LENGTH_SHORT).show();

            }

        });

        ImageButton bCPrestados = (ImageButton) findViewById(R.id.bCPrestados);

        bCPrestados.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0)
            {
                Intent actividad = new Intent(that, ConsultaPrestamos.class);

                actividad.putExtra("matricula",mat);
                startActivity(actividad);
                Toast.makeText(that,"Consulta Prestamos", Toast.LENGTH_SHORT).show();

            }

        });

        ImageButton bRegresar = (ImageButton) findViewById(R.id.bRegresar);

        bRegresar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0)
            {

                Intent actividad = new Intent(that,RegresarPrestamo.class);

                actividad.putExtra("matricula",mat);
                startActivity(actividad);
                Toast.makeText(that,"Regresa Prestamos", Toast.LENGTH_SHORT).show();

            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.changePassword:
                Intent actividad = new Intent(this, UpdatePassword.class);
                actividad.putExtra("matricula",mat);
                startActivity(actividad);
                Toast.makeText(this,"Cambia tu Password", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        return true;
    }
}
