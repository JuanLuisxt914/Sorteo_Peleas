
package controlador;

import Reportes.AleatorioDataSource;
import Reportes.PartidosDataSource;
import Reportes.RondaDataSource;
import conexiondb.ConexionMySQL;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.swing.WindowConstants;
import modelo.Evento;
import modelo.Partido;
import modelo.PeleaT;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ImprimirController implements Initializable {

    @FXML
    private Button btnListaPartidos;
    @FXML
    private Button btnPeleas;
    @FXML
    private Button btnScore;
    @FXML
    private Button btnPlantilla;
    
    private Evento evento;
    private int numModalidad;
    private int tipoCotejo;
    private ObservableList<PeleaT> peleas;
    private ObservableList<Partido> partidos; 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void initAtributes(int modalidad, Evento evento,ObservableList<PeleaT> peleas, ObservableList<Partido> partidos, int tipoCotejo){
        this.evento= evento;
        this.numModalidad=modalidad;
        this.peleas=peleas;
        this.partidos=partidos;
        this.tipoCotejo=tipoCotejo;
    }
            

    @FXML
    private void ListaPartidos(ActionEvent event) {
        try {
            String desc;
            if (numModalidad==1){
                desc="PELEA RAPIDA";
            }else{
                desc=evento.getModalidad();
            }
            
            TextInputDialog dialog = new TextInputDialog(desc);
            dialog.setTitle("Imprimir");
            dialog.setHeaderText("IMPRIMIR COTEJO");
            dialog.setContentText("Ingresa la descripcion del derbi");
            //.getIcons().add(new Image(this.getClass().getResource("/Reportes/Gallo 1.jpg").toString()));
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            
            // Add a custom icon.
            stage.getIcons().add(new Image(this.getClass().getResource("/Reportes/Gallo 1.jpg").toString()));
            Optional<String> result = dialog.showAndWait();
            
            //Si se presiona cancelar no se imprimira nada
            if (!result.isPresent()) {
                return;
            }
            
            Map Parametros = new HashMap();
            Parametros.put("Palenque", this.evento.getNombrePalenque());
            Parametros.put("Descripcion", result.get());
            Parametros.put("Imagen1", Imagen("1"));
            Parametros.put("Imagen2", Imagen("2"));
            
            JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/ListaPartidos.jasper"));
            JasperPrint jprint = JasperFillManager.fillReport(report, Parametros, PartidosDataSource.getDataSource(partidos));
            
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(ImprimirController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void PeleasCotejadas(ActionEvent event) {
        String desc;
        if (numModalidad==1){
            desc="PELEA RAPIDA";
        }else{
            desc=evento.getModalidad();
        }
                
        TextInputDialog dialog = new TextInputDialog(desc);
        dialog.setTitle("Imprimir");
        dialog.setHeaderText("IMPRIMIR COTEJO");
        dialog.setContentText("Ingresa la descripcion del derbi");
        //.getIcons().add(new Image(this.getClass().getResource("/Reportes/Gallo 1.jpg").toString()));
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();

        // Add a custom icon.
        stage.getIcons().add(new Image(this.getClass().getResource("/Reportes/Gallo 1.jpg").toString()));
        Optional<String> result = dialog.showAndWait();
        
        //Si se presiona cancelar no se imprimira nada
        if (!result.isPresent()) {
            return;
        }
        
        Map Parametros = new HashMap();
        Parametros.put("Palenque", this.evento.getNombrePalenque());
        Parametros.put("Descripcion", result.get());
        Parametros.put("Imagen1", Imagen("1"));
        Parametros.put("Imagen2", Imagen("2"));

        
        if(tipoCotejo == 2){
            try{
                
                JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/Rondas.jasper"));
                JasperPrint jprint = JasperFillManager.fillReport(report, Parametros, RondaDataSource.getDataSource(peleas));

                JasperViewer view = new JasperViewer(jprint, false);
                view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                view.setVisible(true);


            }catch(JRException ex){
                ex.getMessage();
            }
            
        }else{
            try{
                
                JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/Aleatorio.jasper"));
                JasperPrint jprint = JasperFillManager.fillReport(report, Parametros, AleatorioDataSource.getDataSource(peleas));

                JasperViewer view = new JasperViewer(jprint, false);
                view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                view.setVisible(true);


            }catch(JRException ex){
                ex.getMessage();
            }
        }
    }

    @FXML
    private void Score(ActionEvent event) {
        
        try {
            Map Parametros = new HashMap();
            Parametros.put("Palenque", this.evento.getNombrePalenque());
            Parametros.put("Imagen1", Imagen("1"));
            Parametros.put("Imagen2", Imagen("2"));
            
            JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/Score4.jasper"));
            JasperPrint jprint = JasperFillManager.fillReport(report, Parametros, PartidosDataSource.getDataSource(partidos));
            
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(ImprimirController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void Plantilla(ActionEvent event) {
    }
    
    public InputStream Imagen(String imagen){
        InputStream inp = null;
        try {
            
            ConexionMySQL conexion = new ConexionMySQL("localhost", "sorteo_de_peleas", "root", "");
            
            conexion.ejecutarConsulta("SELECT * FROM archivos WHERE id_archivo = "+imagen);

            ResultSet rs = conexion.getResultSet();
            
            while (rs.next()) {
                
                inp=rs.getBinaryStream(3);
            }    
            conexion.cerrarConexion();
            
        } catch (SQLException ex) {
            Logger.getLogger(PeleasVistaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return inp;
    }
    
}
