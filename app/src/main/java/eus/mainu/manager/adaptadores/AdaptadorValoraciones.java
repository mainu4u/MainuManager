package eus.mainu.manager.adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import eus.mainu.manager.R;
import eus.mainu.manager.datalayer.Valoracion;

public class AdaptadorValoraciones extends RecyclerView.Adapter<AdaptadorValoraciones.ViewHolder> {

    //Globales
    private final String TAG = "Adaptador_Bocadillos";

    //Variables
    private Context mContext;
    private ArrayList<Valoracion> arrayValoraciones;


    public AdaptadorValoraciones(ArrayList<Valoracion> arrayValoraciones, Context context) {
        this.arrayValoraciones = arrayValoraciones;
        mContext = context;
    }


    //Metodo que se utiliza para "Inflar" el contexto
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclingview_valoraciones, parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG,"onBindViewHolder: called."); //Para debuguear

    holder.nombre.setText(arrayValoraciones.get(position).getUsuario().getNombre());


    //Accion que se ejecuta al pulsar en un objeto de la lista
        /*
    holder.bocadillo_layout.setOnClickListener((new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: "+ arrayValoraciones.get(position).getNombre());

            //Decimos que queremos navegar a la clase Elemento
            Intent intent = new Intent(mContext, arrayValoraciones.class);
            //Le pasamos la informacion que necesita la clase
            intent.putExtra("Bocadillo", arrayValoraciones.get(position));
            //Iniciamos la actividad
            mContext.startActivity(intent);

        }
    }));*/
    }

    //Le dice al adaptador cuantos objetos tenemos en la lista, si devolvemos 0, no muestra ninguno
    @Override
    public int getItemCount() {
        return arrayValoraciones.size();
    }

    class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {

        TextView nombre;

        private ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombreUsuario);

        }
    }


}
