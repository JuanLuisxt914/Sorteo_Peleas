
package modelo;

public class GalloTabla {
    
    private int peso;
    private int anillo;
    private int id;
    private String partido;

    public GalloTabla(int peso, int anillo, int id, String partido) {
        this.peso = peso;
        this.anillo = anillo;
        this.id = id;
        this.partido = partido;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getAnillo() {
        return anillo;
    }

    public void setAnillo(int anillo) {
        this.anillo = anillo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }
    
    
}
