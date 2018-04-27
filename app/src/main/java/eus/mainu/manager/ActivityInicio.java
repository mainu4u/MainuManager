package eus.mainu.manager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;

import eus.mainu.manager.datalayer.Imagen;
import eus.mainu.manager.datalayer.Plato;
import eus.mainu.manager.datalayer.Report;
import eus.mainu.manager.datalayer.Valoracion;
import eus.mainu.manager.internet.HttpGetRequest;

public class ActivityInicio extends AppCompatActivity {

    private final String TAG = "Activity Inicio";
    //Duracion Splash Screen
    private static final long SPLASH_SCREEN_DELAY = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Log.d(TAG, "onCreate: SE HA INICIADO");

        ProgressBar iniciando = findViewById(R.id.iniciando);
        iniciando.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                //Codigo que se ejecuta al de x tiempo
                ArrayList<Valoracion> arrayValoraciones = consigueValoraciones();
                ArrayList<Imagen> arrayImagenes = consigueImagenes();
                ArrayList<Report> arrayReports = consigueReports();
                ArrayList<Plato> arrayPlatos = consiguePlatos();

                //Decimos que queremos navegar a la activity main
                Intent intent = new Intent().setClass(
                        ActivityInicio.this, ActivityMain.class);
                //Le pasamos la informacion que necesita la clase
                intent.putExtra("arrayValoraciones",arrayValoraciones);
                intent.putExtra("arrayImagenes",arrayImagenes);
                intent.putExtra("arrayReports",arrayReports);
                intent.putExtra("arrayPlatos",arrayPlatos);

                //Iniciamos la actividad
                startActivity(intent);
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }

    private ArrayList<Valoracion> consigueValoraciones(){


        HttpGetRequest request = new HttpGetRequest();
        ArrayList<Valoracion> arrayValoraciones = new ArrayList<>();

        if(request.isConnected(this) ) {
            arrayValoraciones = request.getValoraciones();
        }

        Log.d(TAG, "onCreate: Valoraciones conseguidas");

        return arrayValoraciones;

    }

    private ArrayList<Imagen> consigueImagenes(){


        HttpGetRequest request = new HttpGetRequest();
        ArrayList<Imagen> arrayImagenes = new ArrayList<>();

        if(request.isConnected(this) ) {
            arrayImagenes = request.getImagenes();
        }

        Log.d(TAG, "onCreate: Imagenes conseguidas");

        return arrayImagenes;

    }

    private ArrayList<Report> consigueReports(){


        HttpGetRequest request = new HttpGetRequest();
        ArrayList<Report> arrayReports = new ArrayList<>();

        if(request.isConnected(this) ) {
            arrayReports = request.getReports();
        }

        Log.d(TAG, "onCreate: Reports conseguidas");

        return arrayReports;

    }

    private ArrayList<Plato> consiguePlatos(){

        HttpGetRequest request = new HttpGetRequest();
        ArrayList<Plato> arrayPlato = new ArrayList<>();

        if(request.isConnected(this) ) {
            arrayPlato = request.getPlatos();
        }

        Log.d(TAG, "onCreate: Platos conseguidas");

        Collections.sort(arrayPlato, new Comparator<Plato>(){
            @Override
            public int compare(Plato op1, Plato op2){
                if(op1.getTipo() < op2.getTipo())
                    return -1;
                else if(op1.getTipo() > op2.getTipo())
                    return 1;
                else
                    return op1.getNombre().compareToIgnoreCase(op2.getNombre());
            }
        });

        Log.d(TAG, "onCreate: Platos ordenados");


        return arrayPlato;

    }
}
