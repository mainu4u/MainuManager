package eus.mainu.manager.adaptadores;

import android.content.Context;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import eus.mainu.manager.R;
import eus.mainu.manager.VariablesGlobales;
import eus.mainu.manager.datalayer.Plato;
import eus.mainu.manager.internet.HttpGetRequest;

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
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclingview_platos, parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TAG,"onBindViewHolder: called."); //Para debuguear

    holder.nombre.setText(arrayPlato.get(position).getNombre());
    int tipo = arrayPlato.get(position).getTipo();
    holder.tipo.setText(String.valueOf(tipo));

    if(!arrayPlato.get(position).isSeleccionado()){

        holder.aceptar.setVisibility(View.VISIBLE);
        holder.cancelar.setVisibility(View.GONE);

    } else {

        holder.aceptar.setVisibility(View.GONE);
        holder.cancelar.setVisibility(View.VISIBLE);
    }

    holder.aceptar.setOnClickListener((new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            VariablesGlobales.platos.add(arrayPlato.get(position));
            arrayPlato.get(position).setSeleccionado(true);
            holder.aceptar.setVisibility(View.GONE);
            holder.cancelar.setVisibility(View.VISIBLE);

            Log.d(TAG, "onClick: Seleccionado");
        }
    }));

    holder.cancelar.setOnClickListener((new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            VariablesGlobales.platos.remove(arrayPlato.get(position));
            arrayPlato.get(position).setSeleccionado(false);
            holder.cancelar.setVisibility(View.GONE);
            holder.aceptar.setVisibility(View.VISIBLE);


            Log.d(TAG, "onClick: Seleccionado");
        }
    }));

    }

    //Le dice al adaptador cuantos objetos tenemos en la lista, si devolvemos 0, no muestra ninguno
    @Override
    public int getItemCount() {
        return arrayPlato.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nombre, tipo;
        private ImageButton aceptar, cancelar;
        //RecyclerView layout;

        private ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombrePlato);
            tipo = itemView.findViewById(R.id.tipoPlato);
            aceptar = itemView.findViewById(R.id.botonAceptarPlato);
            cancelar = itemView.findViewById(R.id.botonCancelarPlato);
        }
    }


}
