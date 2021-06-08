
package modelo;

public class Pelea {
    private int idPelea;
    private Partido partido1;
    private Partido partido2;

    public Pelea(int idPelea, Partido partido1, Partido partido2) {
        this.idPelea = idPelea;
        this.partido1 = partido1;
        this.partido2 = partido2;
    }

    public int getIdPelea() {
        return idPelea;
    }

    public void setIdPelea(int idPelea) {
        this.idPelea = idPelea;
    }

    public Partido getPartido1() {
        return partido1;
    }

    public void setPartido1(Partido partido1) {
        this.partido1 = partido1;
    }

    public Partido getPartido2() {
        return partido2;
    }

    public void setPartido2(Partido partido2) {
        this.partido2 = partido2;
    }
    
    
    
}
