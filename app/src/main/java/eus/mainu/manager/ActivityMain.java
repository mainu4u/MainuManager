package eus.mainu.manager;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import java.util.ArrayList;

import eus.mainu.manager.adaptadores.AdaptadorFragmentos;
import eus.mainu.manager.datalayer.Valoracion;

public class ActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Valoracion> arrayValoraciones = (ArrayList<Valoracion>) getIntent().getSerializableExtra("arrayValoraciones");

        //Referenciamos los objetos
        ViewPager viewPager = findViewById(R.id.contenedor);
        TabLayout tabLayout = findViewById(R.id.pestañas);

        FragmentoValoraciones fValoraciones = new FragmentoValoraciones();
        //Fragment_Bocadillos fBocadillos = new Fragment_Bocadillos();
        //Fragment_Otros fOtros = new Fragment_Otros();
        Bundle bundle = new Bundle();
        bundle.putSerializable("arrayValoraciones", arrayValoraciones);
        fValoraciones.setArguments(bundle);

        //Creamos los fragmentos
        AdaptadorFragmentos adapter = new AdaptadorFragmentos(getSupportFragmentManager());
        adapter.addFragment(fValoraciones); //index 0
        //adapter.addFragment(fBocadillos); //index 1
        //adapter.addFragment(fOtros); //index 2

        //Creamos las tabulaciones
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        //Añadimos 3 iconos
        tabLayout.getTabAt(0).setIcon(R.drawable.logo_blanco);
        //tabLayout.getTabAt(1).setIcon(R.drawable.ic_sandwich);
        //tabLayout.getTabAt(2).setIcon(R.drawable.ic_french_fries);
    }
}
