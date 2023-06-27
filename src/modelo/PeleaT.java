
package modelo;

import java.util.ArrayList;


public class PeleaT{
    private int num;
    private int Peso1;
    private int Anillo1;
    private String Partido1;
    private String vs;
    private String Partido2;
    private int Anillo2;
    private int Peso2;
    private int idPartido1;
    private int idPartido2;
    private ArrayList<Partido> amigosPartido1 = new ArrayList<Partido>();
    private ArrayList<Partido> amigosPartido2 = new ArrayList<Partido>();
    
    public PeleaT(int num, int Peso1, int Anillo1, String Partido1, String vs, String Partido2, int Anillo2, int Peso2) {
        this.num = num;
        this.Peso1 = Peso1;
        this.Anillo1 = Anillo1;
        this.Partido1 = Partido1;
        this.vs = vs;
        this.Partido2 = Partido2;
        this.Anillo2 = Anillo2;
        this.Peso2 = Peso2;
    }

    public int getIdPartido1() {
        return idPartido1;
    }

    public void setIdPartido1(int idPartido1) {
        this.idPartido1 = idPartido1;
    }

    public int getIdPartido2() {
        return idPartido2;
    }

    public void setIdPartido2(int idPartido2) {
        this.idPartido2 = idPartido2;
    }

    
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPeso1() {
        return Peso1;
    }

    public void setPeso1(int Peso1) {
        this.Peso1 = Peso1;
    }

    public int getAnillo1() {
        return Anillo1;
    }

    public void setAnillo1(int Anillo1) {
        this.Anillo1 = Anillo1;
    }

    public String getPartido1() {
        return Partido1;
    }

    public void setPartido1(String Partido1) {
        this.Partido1 = Partido1;
    }

    public String getVs() {
        return vs;
    }

    public void setVs(String vs) {
        this.vs = vs;
    }

    public String getPartido2() {
        return Partido2;
    }

    public void setPartido2(String Partido2) {
        this.Partido2 = Partido2;
    }

    public int getAnillo2() {
        return Anillo2;
    }

    public void setAnillo2(int Anillo2) {
        this.Anillo2 = Anillo2;
    }

    public int getPeso2() {
        return Peso2;
    }

    public void setPeso2(int Peso2) {
        this.Peso2 = Peso2;
    }

    public ArrayList<Partido> getAmigosPartido1() {
        return amigosPartido1;
    }

    public void setAmigosPartido1(ArrayList<Partido> amigosPartido1) {
        this.amigosPartido1 = amigosPartido1;
    }

    public ArrayList<Partido> getAmigosPartido2() {
        return amigosPartido2;
    }

    public void setAmigosPartido2(ArrayList<Partido> amigosPartido2) {
        this.amigosPartido2 = amigosPartido2;
    }
}
