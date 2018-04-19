package eus.mainu.manager.datalayer;

import java.io.Serializable;

/**
 * Created by narciso on 2/03/18.
 * Clase que contiene la valoracion de los usuarios
 */

public class Valoracion implements Serializable{

    private int id;
    private double puntuacion;
    private String comentario;
    private Usuario usuario;
    private String tipo;

    public Valoracion(int id, double puntuacion, String comentario, Usuario usuario, String tipo) {
        this.id = id;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.usuario = usuario;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
