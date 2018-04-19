package eus.mainu.manager.internet;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import eus.mainu.manager.datalayer.Usuario;
import eus.mainu.manager.datalayer.Valoracion;


/**
 * Created by Manole on 12/03/2018.
 * Clase para administrar los JSON que recibimos de la API
 */

class Administrador_JSON {

    //Metodo para pedir el listado de bocadillos, todas las operaciones de parseo del mensaje JSON se hacen dentro de el
    ArrayList<Valoracion> getValoraciones(String result){

        ArrayList<Valoracion> arrayValoracion = new ArrayList<>();

        try {
            JSONArray obj = new JSONArray(result);
            for (int i = 0; i < obj.length(); i++){
                JSONObject o = obj.getJSONObject(i);

                //Creamos el bocadillo
                arrayValoracion.add(new Valoracion(
                        getInt(o,"id"),
                        getDouble(o,"precio"),
                        getString(o,"texto"),
                        getUsuario(o.getJSONObject("usuario")),
                        getString(o,"type")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrayValoracion;
    }

    //Metodo para leer un INT de un JSONObject
    private int getInt(JSONObject o, String campo) throws JSONException {

        int id = 0;

        if(!o.isNull(campo)) {
            id = o.getInt(campo);
        }

        return id;
    }

    //Metodo para leer un String de un JSONObject
    private String getString(JSONObject o, String campo) throws JSONException {

        String texto = "";

        if(!o.isNull(campo)) {
            texto = o.getString(campo);
        }

        return texto;
    }


    //Metodo para leer la puntuacion de un JSONObject
    private double getDouble(JSONObject o, String nombre) throws JSONException {

        double puntuacion;

        if(!o.isNull(nombre)) {
            puntuacion = o.getDouble(nombre);
        }
        else {
            puntuacion = 3 + (5 - 3)*(new Random()).nextDouble();
        }

        return puntuacion;
    }


    private Usuario getUsuario(JSONObject o) throws JSONException {

        return new Usuario(getInt(o,"id"),
                getString(o,"nombre"),
                getString(o,"foto"),
                getInt(o, "verificado"));
    }






}
