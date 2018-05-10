package eus.mainu.manager.adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import eus.mainu.manager.R;
import eus.mainu.manager.datalayer.Imagen;
import eus.mainu.manager.internet.HttpGetRequest;

public class AdaptadorImagenes extends RecyclerView.Adapter<AdaptadorImagenes.ViewHolder> {

    //Globales
    private final String TAG = "AdaptadorImagenes";

    //Variables
    private Context mContext;
    private ArrayList<Imagen> arrayImagen = new ArrayList<>();

    public AdaptadorImagenes(ArrayList<Imagen> arrayImagen, Context context) {
        this.arrayImagen = arrayImagen;
        mContext = context;
    }

    //Metodo que se utiliza para "Inflar" el contexto
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclingview_imagenes, parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TAG,"onBindViewHolder: called."); //Para debuguear

        holder.nombre.setText(arrayImagen.get(position).getUsuario().getNombre());
        //Ponemos la imagen del usuario circular
        Picasso.with(mContext)
                .load(arrayImagen.get(position).getRuta())
                .fit()
                .centerInside()
                .into(holder.foto);


        holder.aceptar.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HttpGetRequest request = new HttpGetRequest();
                request.aceptar(arrayImagen.get(position));
                holder.aceptar.setVisibility(View.GONE);
                holder.cancelar.setVisibility(View.GONE);

                Log.d(TAG, "onClick: Aceptar");
            }
        }));

        holder.cancelar.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HttpGetRequest request = new HttpGetRequest();
                request.cancelar(arrayImagen.get(position));
                holder.aceptar.setVisibility(View.GONE);
                holder.cancelar.setVisibility(View.GONE);

                Log.d(TAG, "onClick: Cancelar");
            }
        }));

        holder.oficial1.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HttpGetRequest request = new HttpGetRequest();
                request.aceptar(arrayImagen.get(position));
                request.oficial(arrayImagen.get(position));
                holder.aceptar.setVisibility(View.GONE);
                holder.cancelar.setVisibility(View.GONE);
                holder.oficial1.setVisibility(View.GONE);
                holder.oficial2.setVisibility(View.VISIBLE);

                Log.d(TAG, "onClick: Cancelar");
            }
        }));
    }

    //Le dice al adaptador cuantos objetos tenemos en la lista, si devolvemos 0, no muestra ninguno
    @Override
    public int getItemCount() {
        return arrayImagen.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre;
        ImageView foto;
        ImageButton aceptar,cancelar, oficial1,oficial2;

        private ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombreUsuario1);
            foto = itemView.findViewById(R.id.fotoUsuario1);
            aceptar = itemView.findViewById(R.id.botonAceptar1);
            cancelar = itemView.findViewById(R.id.botonCancelar1);
            oficial1 = itemView.findViewById(R.id.botonOficial1);
            oficial2 = itemView.findViewById(R.id.botonOficial2);

        }
    }


}
