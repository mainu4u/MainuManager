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
import eus.mainu.manager.datalayer.Report;
import eus.mainu.manager.internet.HttpGetRequest;

public class AdaptadorReports extends RecyclerView.Adapter<AdaptadorReports.ViewHolder> {

    //Globales
    private final String TAG = "AdaptadorImagenes";

    //Variables
    private Context mContext;
    private ArrayList<Report> arrayReports;

    public AdaptadorReports(ArrayList<Report> arrayReports, Context context) {
        this.arrayReports = arrayReports;
        mContext = context;
    }

    //Metodo que se utiliza para "Inflar" el contexto
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclingview_reports, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TAG,"onBindViewHolder: called."); //Para debuguear

        holder.nombre.setText(arrayReports.get(position).getNombre());
        holder.contenido.setText(arrayReports.get(position).getContenido());

        holder.aceptar.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HttpGetRequest request = new HttpGetRequest();
                request.aceptar(arrayReports.get(position));
                holder.aceptar.setVisibility(View.GONE);

                Log.d(TAG, "onClick: Aceptar");
            }
        }));

    }

    //Le dice al adaptador cuantos objetos tenemos en la lista, si devolvemos 0, no muestra ninguno
    @Override
    public int getItemCount() {
        return arrayReports.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre,contenido;
        ImageButton aceptar;

        private ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombreReport);
            contenido = itemView.findViewById(R.id.contenidoReport);
            aceptar = itemView.findViewById(R.id.botonAceptar2);

        }
    }


}
