
package modelo;

import conexiondb.ConexionMySQL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Evento {
    private int idEvento;
    private String nombrePalenque;
    private LocalDate fechaEvento;
    private String modalidad;
    private ArrayList<Partido> partidos;

    public Evento(String nombrePalenque, LocalDate fechaEvento, String modalidad) {
        this.nombrePalenque = nombrePalenque;
        this.fechaEvento = fechaEvento;
        this.modalidad = modalidad;
    }

    public Evento(int idEvento, String nombrePalenque, LocalDate fechaEvento, String modalidad) {
        this.idEvento = idEvento;
        this.nombrePalenque = nombrePalenque;
        this.fechaEvento = fechaEvento;
        this.modalidad = modalidad;
    }
    
    

    public Evento(String nombrePalenque) {
        this.nombrePalenque = nombrePalenque;
    }
    

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getNombrePalenque() {
        return nombrePalenque;
    }

    public void setNombrePalenque(String nombrePalenque) {
        this.nombrePalenque = nombrePalenque;
    }

    public LocalDate getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(LocalDate fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public ArrayList<Partido> getPartidos() {
        return partidos;
    }

    public void setPartidos(ArrayList<Partido> partidos) {
        this.partidos = partidos;
    }

    public boolean insertarEvento() throws SQLException{
        // Abro la conexion
        ConexionMySQL conexion = new ConexionMySQL("localhost", "sorteo_de_peleas", "root", "");

        // Sentencia para introducir un servicio
        String SQL = "INSERT INTO eventos (id_evento, nombre_palenque, fecha, modalidad) "
                + "values("
                + "'" + this.getIdEvento() + "', "
                + "'" + this.getNombrePalenque() + "', "
                + "'" + this.getFechaEvento().toString() + "', "
                + "'" + this.getModalidad() + "'"
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
    
    public boolean borrarEvento() throws SQLException{
        // Abro la conexion
        ConexionMySQL conexion = new ConexionMySQL("localhost", "sorteo_de_peleas", "root", "");

        // Sentencia para introducir un servicio
        
        String SQLG = "DELETE FROM gallos WHERE gallos.id_evento ="+this.getIdEvento();
        String SQLA = "DELETE FROM amigos WHERE amigos.id_evento ="+this.getIdEvento();
        String SQLP = "DELETE FROM partidos WHERE partidos.id_evento ="+this.getIdEvento();
        String SQL = "DELETE FROM eventos WHERE eventos.id_evento ="+this.getIdEvento();

        // Devuelvo el numero de filas afectadas
        System.out.println(SQLG);
        System.out.println(SQLA);
        System.out.println(SQLP);
        System.out.println(SQL);
        int filasG = conexion.ejecutarInstruccion(SQLG);
        int filasA = conexion.ejecutarInstruccion(SQLA);
        int filasP = conexion.ejecutarInstruccion(SQLP);
        int filas = conexion.ejecutarInstruccion(SQL);

        conexion.cerrarConexion();

        // Si deuvelve mas de 0, es que hemos borrado registros
        if (filas > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean modificarEvento() throws SQLException{
        // Abro la conexion
        ConexionMySQL conexion = new ConexionMySQL("localhost", "sorteo_de_peleas", "root", "");

        // Sentencia para introducir un servicio
        String SQL = "UPDATE eventos SET "
                + "nombre_palenque = '"+this.getNombrePalenque()+"',"
                +"fecha = '"+this.getFechaEvento().toString()+"',"
                + "modalidad = '"+this.getModalidad()+"' "
                +"WHERE eventos.id_evento = "+ this.getIdEvento();
   

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
