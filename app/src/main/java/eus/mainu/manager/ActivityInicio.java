package eus.mainu.manager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

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
                consigueDatos();

                //Decimos que queremos navegar a la activity main
                Intent intent = new Intent().setClass(
                        ActivityInicio.this, ActivityMain.class);
                //Le pasamos la informacion que necesita la clase
                //intent.putExtra("Menu",menu);

                //Iniciamos la actividad
                startActivity(intent);
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }

    private void consigueDatos(){

       Log.d(TAG, "onCreate: Datos conseguidos");

    }
}
