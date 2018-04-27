package eus.mainu.manager.datalayer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Manole on 16/02/2018.
 * Clase del modelo de datos Plato
 */

public class Plato implements Serializable {

    private int id;
    private String nombre;
    private int tipo;
    private boolean seleccionado;


    public Plato(int id, String nombre, int tipo) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        seleccionado = false;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}