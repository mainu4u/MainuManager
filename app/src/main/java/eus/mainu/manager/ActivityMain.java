package eus.mainu.manager;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import java.util.ArrayList;

import eus.mainu.manager.adaptadores.AdaptadorFragmentos;
import eus.mainu.manager.datalayer.Imagen;
import eus.mainu.manager.datalayer.Plato;
import eus.mainu.manager.datalayer.Report;
import eus.mainu.manager.datalayer.Valoracion;

public class ActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Leemos los datos
        ArrayList<Valoracion> arrayValoraciones = (ArrayList<Valoracion>) getIntent().getSerializableExtra("arrayValoraciones");
        ArrayList<Imagen> arrayImagenes = (ArrayList<Imagen>) getIntent().getSerializableExtra("arrayImagenes");
        ArrayList<Report> arrayReports = (ArrayList<Report>) getIntent().getSerializableExtra("arrayReports");
        ArrayList<Plato> arrayPlatos = (ArrayList<Plato>) getIntent().getSerializableExtra("arrayPlatos");

        //Referenciamos los objetos
        ViewPager viewPager = findViewById(R.id.contenedor);
        TabLayout tabLayout = findViewById(R.id.pestañas);

        FragmentoPlatos fPlatos = new FragmentoPlatos();
        FragmentoValoraciones fValoraciones = new FragmentoValoraciones();
        FragmentoFotos fFotos = new FragmentoFotos();
        FragmentoReports fReports = new FragmentoReports();

        Bundle bundle = new Bundle();
        bundle.putSerializable("arrayValoraciones", arrayValoraciones);
        fValoraciones.setArguments(bundle);

        bundle = new Bundle();
        bundle.putSerializable("arrayImagenes", arrayImagenes);
        fFotos.setArguments(bundle);

        bundle = new Bundle();
        bundle.putSerializable("arrayReports", arrayReports);
        fReports.setArguments(bundle);

        bundle = new Bundle();
        bundle.putSerializable("arrayPlatos", arrayPlatos);
        fPlatos.setArguments(bundle);

        //Creamos los fragmentos
        AdaptadorFragmentos adapter = new AdaptadorFragmentos(getSupportFragmentManager());
        adapter.addFragment(fPlatos); //index 0
        adapter.addFragment(fValoraciones); //index 1
        adapter.addFragment(fFotos); //index 2
        adapter.addFragment(fReports); //index 3

        //Creamos las tabulaciones
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        //Añadimos 3 iconos
        tabLayout.getTabAt(0).setIcon(R.drawable.change_menu);
        tabLayout.getTabAt(1).setIcon(R.drawable.valoraciones);
        tabLayout.getTabAt(2).setIcon(R.drawable.imagenes);
        tabLayout.getTabAt(3).setIcon(R.drawable.reporte);

        //tabLayout.getTabAt(1).setIcon(R.drawable.ic_sandwich);
        //tabLayout.getTabAt(2).setIcon(R.drawable.ic_french_fries);
    }
}
