
package modelo;

import conexiondb.ConexionMySQL;
import java.sql.SQLException;
import java.util.ArrayList;

public class Partido {
    private int idPartido;
    private int numPartido;
    private String nombrePartido;
    private ArrayList<Gallo> gallos;
    private ArrayList<Partido> amigos;

    public Partido( int numPartido, String nombrePartido, ArrayList<Gallo> gallos, ArrayList<Partido> amigos) {
        this.idPartido = idPartido;
        this.numPartido = numPartido;
        this.nombrePartido = nombrePartido;
        this.gallos = gallos;
        this.amigos = amigos;
    }

    public Partido(int idPartido, int numPartido, String nombrePartido, ArrayList<Gallo> gallos, ArrayList<Partido> amigos) {
        this.idPartido = idPartido;
        this.numPartido = numPartido;
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

    public int getNumPartido() {
        return numPartido;
    }

    public void setNumPartido(int numPartido) {
        this.numPartido = numPartido;
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
    public boolean insertarPartido(int id_evento) throws SQLException{
        // Abro la conexion
        ConexionMySQL conexion = new ConexionMySQL("localhost", "sorteo_de_peleas", "root", "");
        
        

        // Sentencia para introducir un servicio
        String SQL = "INSERT INTO partidos (num_partido, nombre_partido, id_evento) "
                + "values("
                + "'" + this.getNumPartido() + "', "
                + "'" + this.getNombrePartido()+ "', "
                + "'" + id_evento+ "' "
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
    
    public boolean borrarPartido() throws SQLException{
        // Abro la conexion
        ConexionMySQL conexion = new ConexionMySQL("localhost", "sorteo_de_peleas", "root", "");

        // Sentencia para introducir un servicio
        String SQL = "DELETE FROM partidos WHERE partidos.id_partido ="+this.getIdPartido();
        String SQLG = "DELETE FROM gallos WHERE gallos.id_partido ="+this.getIdPartido();

        // Devuelvo el numero de filas afectadas
        System.out.println(SQL);
        System.out.println(SQLG);
        
        int filasG = conexion.ejecutarInstruccion(SQLG);

        int filas = conexion.ejecutarInstruccion(SQL);

        conexion.cerrarConexion();

        // Si deuvelve mas de 0, es que hemos borrado registros
        if (filas > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean modificarPartido() throws SQLException{
        // Abro la conexion
        ConexionMySQL conexion = new ConexionMySQL("localhost", "sorteo_de_peleas", "root", "");

        // Sentencia para introducir un servicio
        String SQL = "UPDATE partidos SET "
                + "nombre_partido = '"+this.getNombrePartido()+"'"
                +"WHERE partidos.id_partido = "+ this.getIdPartido();
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
