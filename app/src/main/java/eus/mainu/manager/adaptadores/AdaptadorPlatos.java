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
import eus.mainu.manager.datalayer.Plato;

public class AdaptadorPlatos extends RecyclerView.Adapter<AdaptadorPlatos.ViewHolder> {

    //Globales
    private final String TAG = "Adaptador_Platos";

    //Variables
    private Context mContext;
    private ArrayList<Plato> arrayPlato;


    public AdaptadorPlatos(ArrayList<Plato> arrayPlato, Context context) {
        this.arrayPlato = arrayPlato;
        mContext = context;
    }


    //Metodo que se utiliza para "Inflar" el contexto
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclingview_valoraciones, parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TAG,"onBindViewHolder: called."); //Para debuguear

    holder.nombre.setText(arrayPlato.get(position).getNombre());
    holder.tipo.setText(arrayPlato.get(position).getTipo());

    //holder.layout.setOnClickListener();
    }

    //Le dice al adaptador cuantos objetos tenemos en la lista, si devolvemos 0, no muestra ninguno
    @Override
    public int getItemCount() {
        return arrayPlato.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre, tipo;
        RecyclerView layout;

        private ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombrePlato);
            tipo = itemView.findViewById(R.id.nombrePlato);
            layout = itemView.findViewById(R.id.layoutPlato);
        }
    }


}
