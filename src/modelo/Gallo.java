
package modelo;

public class Gallo {
    private int id;
    private int peso;
    private int anillo;
    

    public Gallo(int id, int peso, int anillo) {
        this.id = id;
        this.peso = peso;
        this.anillo = anillo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    
    
    
}
