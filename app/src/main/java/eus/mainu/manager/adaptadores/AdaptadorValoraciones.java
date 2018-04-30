package eus.mainu.manager.adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import eus.mainu.manager.R;
import eus.mainu.manager.datalayer.Valoracion;
import eus.mainu.manager.internet.HttpGetRequest;

public class AdaptadorValoraciones extends RecyclerView.Adapter<AdaptadorValoraciones.ViewHolder> {

    //Globales
    private final String TAG = "Adaptador_Valoraciones";

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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TAG,"onBindViewHolder: called."); //Para debuguear

    holder.nombre.setText(arrayValoraciones.get(position).getUsuario().getNombre());
    holder.comentario.setText(arrayValoraciones.get(position).getComentario());
    holder.estrellas.setRating((float) arrayValoraciones.get(position).getPuntuacion());

        //Ponemos la imagen del usuario circular
        Picasso.with(mContext).load(arrayValoraciones.get(position).getUsuario().getFoto()).fit().into(holder.foto, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap imageBitmap = ((BitmapDrawable) holder.foto.getDrawable()).getBitmap();
                RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(mContext.getResources(), imageBitmap);
                imageDrawable.setCircular(true);
                imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                holder.foto.setImageDrawable(imageDrawable);
            }

            @Override
            public void onError() {
                holder.foto.setImageResource(R.drawable.mainu_logo);
            }
        });


    holder.aceptar.setOnClickListener((new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            HttpGetRequest request = new HttpGetRequest();
            request.aceptar(arrayValoraciones.get(position));
            holder.aceptar.setVisibility(View.GONE);
            holder.cancelar.setVisibility(View.GONE);

            Log.d(TAG, "onClick: Aceptar");
        }
    }));

    holder.cancelar.setOnClickListener((new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            HttpGetRequest request = new HttpGetRequest();
            request.cancelar(arrayValoraciones.get(position));
            holder.aceptar.setVisibility(View.GONE);
            holder.cancelar.setVisibility(View.GONE);

            Log.d(TAG, "onClick: Cancelar");
        }
    }));
    }

    //Le dice al adaptador cuantos objetos tenemos en la lista, si devolvemos 0, no muestra ninguno
    @Override
    public int getItemCount() {
        return arrayValoraciones.size();
    }

    class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {

        TextView nombre, comentario;
        ImageView foto;
        RatingBar estrellas;
        ImageButton aceptar,cancelar;

        private ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombreUsuario);
            comentario = itemView.findViewById(R.id.comentarioUsuario);
            foto = itemView.findViewById(R.id.fotoUsuario);
            estrellas = itemView.findViewById(R.id.puntuacionUsuario);
            aceptar = itemView.findViewById(R.id.botonAceptar);
            cancelar = itemView.findViewById(R.id.botonCancelar);

        }
    }


}
