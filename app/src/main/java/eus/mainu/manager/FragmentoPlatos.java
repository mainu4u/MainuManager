package eus.mainu.manager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import eus.mainu.manager.adaptadores.AdaptadorPlatos;
import eus.mainu.manager.adaptadores.AdaptadorReports;
import eus.mainu.manager.datalayer.Plato;
import eus.mainu.manager.datalayer.Report;
import eus.mainu.manager.internet.HttpGetRequest;

public class FragmentoPlatos extends Fragment {

    private final String TAG = "Menu";

    //Elementos de la vista
    private Context mContext;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private TextView contador;
    private ImageButton aniadir,enviar;

    //Array
    ArrayList<Plato> arrayPlatos = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: Fragmento Reports");
        
        mContext = getContext();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            arrayPlatos = (ArrayList<Plato>) bundle.getSerializable("arrayPlatos");
        }

    }

    //**********************************************************************************************

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_menu, container, false);

        aniadir = view.findViewById(R.id.botonAniadir);
        enviar = view.findViewById(R.id.botonEnviar);
        contador = view.findViewById(R.id.contador);
        contador.setText("0");

        //Cogemos el recycling view
        recyclerView = view.findViewById(R.id.lista_valoraciones1);

        //Añadimos una linea debajo de cada objeto
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mContext, new LinearLayoutManager(mContext).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        //Ponemos el SwipeToRefresh
        swipeRefreshLayout = view.findViewById(R.id.swipeValoraciones1);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        //Inflamos la vista
        setPlatos();
        escuchamosSwipe();

        //Ponemos los botones
        setAniadir();
        setEnviar();


        return view;
    }

    //**********************************************************************************************

    private void setAniadir(){
        aniadir.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Añadir");

                final View dialogView = View.inflate(mContext, R.layout.caja_alerta, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setView(dialogView);
                builder.setTitle("Nuevo Plato");
                builder.setCancelable(true)

                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Uri uri = Uri.parse("market://details?id=MY_APP_PACKAGE");
                            Intent intent = new Intent (Intent.ACTION_VIEW, uri);
                            startActivity(intent);                          }
                    })

                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    }).show();

            }
        }));

    }

    //**********************************************************************************************

    private void setEnviar(){

        enviar.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Enviar");

            }
        }));

    }

    //**********************************************************************************************

    //Clase para crear y adaptar la informacion al recycling view
    private void setPlatos(){
        //Creamos el objeto de la clase adaptador
        AdaptadorPlatos adapter = new AdaptadorPlatos(arrayPlatos, getActivity());

        //Adaptamos el recyclingview
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    //**********************************************************************************************

    public void setContador(int numero){

        contador.setText(String.valueOf(numero));

    }


    //**********************************************************************************************

    //Metodo para definir la accion del swipe to refresh
    private void escuchamosSwipe() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            //Accion que se ejecuta cuando se activa
            @Override
            public void onRefresh() {
                //Creamos otro request porque solo se puede llamar al asynctask una vez
                HttpGetRequest request = new HttpGetRequest();

                if(request.isConnected(mContext)){
                    arrayPlatos = request.getPlatos();

                    //Ordenamos
                    Collections.sort(arrayPlatos, (Plato s1, Plato s2) ->
                    {
                        return Integer.compare(s2.getTipo(), s1.getTipo());
                    });

                    Collections.reverse(arrayPlatos);

                    Log.d(TAG, "onCreate: Platos ordenados");
                    setPlatos();
                }

                //Esto es para ejecutar un hilo que se encarga de hacer la accion, creo
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);   //Tiempo en ms durante el cual se muestra el icono de refresh
            }
        });
    }

}
