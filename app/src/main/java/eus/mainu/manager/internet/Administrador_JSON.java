package eus.mainu.manager.internet;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import eus.mainu.manager.datalayer.Imagen;
import eus.mainu.manager.datalayer.Plato;
import eus.mainu.manager.datalayer.Report;
import eus.mainu.manager.datalayer.Usuario;
import eus.mainu.manager.datalayer.Valoracion;


/**
 * Created by Manole on 12/03/2018.
 * Clase para administrar los JSON que recibimos de la API
 */

public class Administrador_JSON {

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
                        getDouble(o,"puntuacion"),
                        getString(o,"texto"),
                        getUsuario(o.getJSONObject("usuario")),
                        getString(o,"type")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrayValoracion;
    }

    //Metodo para pedir el listado de bocadillos, todas las operaciones de parseo del mensaje JSON se hacen dentro de el
    ArrayList<Imagen> getImagenes(String result){

        ArrayList<Imagen> arrayImagenes = new ArrayList<>();

        try {
            JSONArray obj = new JSONArray(result);
            for (int i = 0; i < obj.length(); i++){
                JSONObject o = obj.getJSONObject(i);

                //Creamos el bocadillo
                arrayImagenes.add(new Imagen(
                        getInt(o,"id"),
                        getString(o,"url"),
                        getUsuario(o.getJSONObject("usuario")),
                        getString(o,"type")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrayImagenes;
    }

    //Metodo para pedir el listado de bocadillos, todas las operaciones de parseo del mensaje JSON se hacen dentro de el
    public ArrayList<Report> getReports(String result){

        ArrayList<Report> arrayReports = new ArrayList<>();

        try {
            JSONArray obj = new JSONArray(result);
            for (int i = 0; i < obj.length(); i++){
                JSONObject o = obj.getJSONObject(i);

                //Creamos el bocadillo
                arrayReports.add(new Report(
                        getString(o,"nombre"),
                        getString(o,"contenido")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrayReports;
    }

    public ArrayList<Plato> getPlatos(String result){

        ArrayList<Plato> arrayReports = new ArrayList<>();

        try {
            JSONArray obj = new JSONArray(result);
            for (int i = 0; i < obj.length(); i++){
                JSONObject o = obj.getJSONObject(i);

                //Creamos el plato
                arrayReports.add(new Plato(
                        getInt(o,"id"),
                        getString(o,"nombre"),
                        getInt(o,"tipo")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrayReports;
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
            puntuacion = 0;
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
