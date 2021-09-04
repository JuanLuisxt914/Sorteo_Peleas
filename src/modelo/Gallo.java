
package modelo;

import conexiondb.ConexionMySQL;
import java.sql.SQLException;
import java.util.ArrayList;

public class Gallo {
    private int id;
    private int peso;
    private int anillo;
    private String partido;
    private ArrayList<Partido> amigos = new ArrayList<Partido>();
    private int idPartido =0;
    private boolean match = false;
    

    public Gallo(int peso, int anillo, String partido) {
        this.peso = peso;
        this.anillo = anillo;
        this.partido = partido;
    }

    public Gallo() {
    }

    
    
    public Gallo(int id, int peso, int anillo) {
        this.id = id;
        this.peso = peso;
        this.anillo = anillo;
    }

    public Gallo(int id, int peso, int anillo, String partido) {
        this.id = id;
        this.peso = peso;
        this.anillo = anillo;
        this.partido = partido;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }

    
    public int getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(int idPartido) {
        this.idPartido = idPartido;
    }
    
    

    public ArrayList<Partido> getAmigos() {
        return amigos;
    }

    public void setAmigos(ArrayList<Partido> amigos) {
        this.amigos = amigos;
    }
    

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
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
    
    public boolean insertarGallo(int id_partido, int idEvento) throws SQLException{
        // Abro la conexion
        ConexionMySQL conexion = new ConexionMySQL("localhost", "sorteo_de_peleas", "root", "");
 
        // Sentencia para introducir un servicio
        String SQL = "INSERT INTO gallos (anillo, peso, id_partido, id_evento) "
                + "values("
                + "'" + this.getAnillo() + "', "
                + "'" + this.getPeso()+ "', "
                + "'" + id_partido+ "', "
                + "'"+idEvento+"'"
                + " )";

        // Devuelvo el numero de filas afectadas
        System.out.println(SQL);
        int filas = conexion.ejecutarInstruccion(SQL);

        conexion.cerrarConexion();

        // Si deuvelve mas de 0, es que hemos insertado registros
        if (filas > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean borrarGallo() throws SQLException{
        // Abro la conexion
        ConexionMySQL conexion = new ConexionMySQL("localhost", "sorteo_de_peleas", "root", "");

        // Sentencia para introducir un servicio
        String SQL = "DELETE FROM gallos WHERE gallos.id_gallo ="+this.getId();

        // Devuelvo el numero de filas afectadas
        System.out.println(SQL);
        

        int filas = conexion.ejecutarInstruccion(SQL);

        conexion.cerrarConexion();

        // Si deuvelve mas de 0, es que hemos borrado registros
        if (filas > 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean modificarGallo() throws SQLException{
        // Abro la conexion
        ConexionMySQL conexion = new ConexionMySQL("localhost", "sorteo_de_peleas", "root", "");

        // Sentencia para introducir un servicio
        String SQL = "UPDATE gallos SET "
                + "anillo = '"+this.getAnillo()+"' , "
                + "peso = '"+this.getPeso()+"' "
                +"WHERE gallos.id_gallo = "+ this.getId();

        System.out.println(SQL);
        // Devuelvo el numero de filas afectadas
        int filas = conexion.ejecutarInstruccion(SQL);

        conexion.cerrarConexion();

        // Si deuvelve mas de 0, es que hemos insertado registros
        if (filas > 0) {
            return true;
        } else {
            return false;
        }
    }
    
}
