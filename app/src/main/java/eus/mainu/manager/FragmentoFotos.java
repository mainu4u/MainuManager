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

import eus.mainu.manager.datalayer.Imagen;
import eus.mainu.manager.internet.HttpGetRequest;

public class FragmentoFotos extends Fragment {

    private final String TAG = "Fotos";

    //Elementos de la vista
    private Context mContext;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    //Array
    ArrayList<Imagen> arrayImagenes = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: Fragmento Imagenes");
        
        mContext = getContext();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            arrayImagenes = (ArrayList<Imagen>) bundle.getSerializable("arrayImagenes");
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
        //setValoraciones();
        escuchamosSwipe();


        return view;
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
                    arrayImagenes = request.getImagenes();
                    //Actualiza vista
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
