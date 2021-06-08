package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import modelo.Evento;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class PeleasVistaController implements Initializable {

    @FXML
    private ToggleGroup TipoDeSorteo;
    @FXML
    private Button btnRegistro;
    
    private Evento evento;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void initAtributes(Evento evento){
        this.evento= evento;
        System.out.println("Si entro aqui we");
        System.out.println(evento.getIdEvento());
        System.out.println(evento.getNombrePalenque());
        System.out.println(evento.getFechaEvento());
        System.out.println(evento.getModalidad());
    }
    
    public void closeWindows() {
        try {
            // Cargo la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/MenuVista.fxml"));

            // Cargo el padre
            Parent root = loader.load();

            // Obtengo el controlador
            MenuVistaController controlador = loader.getController();
            
            // Creo la scene y el stage
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            // Asocio el stage con el scene
            stage.setScene(scene);
            stage.show();
            
            // Indico que debe hacer al cerrar
            //stage.setOnCloseRequest(e -> controlador.closeWindows());

            // Ciero la ventana donde estoy
            Stage myStage = (Stage) this.btnRegistro.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(MenuVistaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void IrARegistro(ActionEvent event) {
        
        try {
            // Cargo la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/RegistroGallosVista.fxml"));

            // Cargo el padre
            Parent root = loader.load();

            // Obtengo el controlador
            RegistroGallosVistaController controlador = loader.getController();

            // Creo la scene y el stage
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            // Asocio el stage con el scene
            stage.setScene(scene);
            stage.show();

            // Indico que debe hacer al cerrar
            stage.setOnCloseRequest(e -> controlador.closeWindows());

            // Ciero la ventana donde estoy
            Stage myStage = (Stage) this.btnRegistro.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            //Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
