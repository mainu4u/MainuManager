package eus.mainu.manager;

import android.content.Context;
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

import java.util.ArrayList;

import eus.mainu.manager.adaptadores.AdaptadorValoraciones;
import eus.mainu.manager.datalayer.Valoracion;
import eus.mainu.manager.internet.HttpGetRequest;

public class FragmentoValoraciones extends Fragment {

    private final String TAG = "Valoraciones";

    //Elementos de la vista
    private Context mContext;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    //Array
    ArrayList<Valoracion> arrayValoraciones = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: Fragmento Valoraciones");
        
        mContext = getContext();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            arrayValoraciones = (ArrayList<Valoracion>) bundle.getSerializable("arrayValoraciones");
        }

    }

    //**********************************************************************************************

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_valoraciones, container, false);

        //Cogemos el recycling view
        recyclerView = view.findViewById(R.id.lista_valoraciones);

        //Añadimos una linea debajo de cada objeto
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mContext, new LinearLayoutManager(mContext).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        //Ponemos el SwipeToRefresh
        swipeRefreshLayout = view.findViewById(R.id.swipeValoraciones);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        //Inflamos la vista
        setValoraciones();
        escuchamosSwipe();


        return view;
    }


    //**********************************************************************************************
    //Clase para crear y adaptar la informacion al recycling view
    private void setValoraciones(){
        //Creamos el objeto de la clase adaptador
        AdaptadorValoraciones adapter = new AdaptadorValoraciones(arrayValoraciones, getActivity());

        //Adaptamos el recyclingview
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    //Metodo para definir la accion del swipe to refresh
    private void escuchamosSwipe() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            //Accion que se ejecuta cuando se activa
            @Override
            public void onRefresh() {
                //Creamos otro request porque solo se puede llamar al asynctask una vez
                HttpGetRequest request = new HttpGetRequest();

                if(request.isConnected(mContext)){
                    arrayValoraciones = request.getValoraciones();
                    setValoraciones();
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
