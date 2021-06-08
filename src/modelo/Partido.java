
package modelo;

import java.util.ArrayList;

public class Partido {
    private int idPartido;
    private String nombrePartido;
    private ArrayList<Gallo> gallos;
    private ArrayList<Partido> amigos;

    public Partido(int idPartido, String nombrePartido, ArrayList<Gallo> gallos, ArrayList<Partido> amigos) {
        this.idPartido = idPartido;
        this.nombrePartido = nombrePartido;
        this.gallos = gallos;
        this.amigos = amigos;
    }

    public int getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(int idPartido) {
        this.idPartido = idPartido;
    }

    public String getNombrePartido() {
        return nombrePartido;
    }

    public void setNombrePartido(String nombrePartido) {
        this.nombrePartido = nombrePartido;
    }

    public ArrayList<Gallo> getGallos() {
        return gallos;
    }

    public void setGallos(ArrayList<Gallo> gallos) {
        this.gallos = gallos;
    }

    public ArrayList<Partido> getAmigos() {
        return amigos;
    }

    public void setAmigos(ArrayList<Partido> amigos) {
        this.amigos = amigos;
    }
    
}
