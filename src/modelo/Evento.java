
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
    private ArrayList<Pelea> peleas;

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

    public ArrayList<Pelea> getPeleas() {
        return peleas;
    }

    public void setPeleas(ArrayList<Pelea> peleas) {
        this.peleas = peleas;
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
        String SQL = "DELETE FROM eventos WHERE eventos.id_evento ="+this.getIdEvento();

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
    
    public boolean modificarEvento() throws SQLException{
        // Abro la conexion
        ConexionMySQL conexion = new ConexionMySQL("localhost", "sorteo_de_peleas", "root", "");

        // Sentencia para introducir un servicio
        String SQL = "UPDATE eventos SET "
                + "nombre_palenque = '"+this.getNombrePalenque()+"',"
                +"fecha = '"+this.getFechaEvento().toString()+"',"
                + "modalidad = '"+this.getModalidad()+"' "
                +"WHERE eventos.id_evento = "+ this.getIdEvento();
        
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
