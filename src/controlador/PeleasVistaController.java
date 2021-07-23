package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelo.Evento;

public class PeleasVistaController implements Initializable {

    @FXML
    private ToggleGroup TipoDeSorteo;
    @FXML
    private Button btnRegistro;
    
    private Evento evento;
    @FXML
    private TableView<PeleaT> tblPeleas;
    @FXML
    private TableColumn<PeleaT, Integer> num;
    @FXML
    private TableColumn<PeleaT, Integer> peso1;
    @FXML
    private TableColumn<PeleaT, Integer> anillo1;
    @FXML
    private TableColumn<PeleaT, String> partido1;
    @FXML
    private TableColumn<PeleaT, String> vs;
    @FXML
    private TableColumn<PeleaT, String> partido2;
    @FXML
    private TableColumn<PeleaT,Integer> anillo2;
    @FXML
    private TableColumn<PeleaT, Integer> peso2;
    
    private ObservableList<PeleaT> peleas;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        peleas = FXCollections.observableArrayList();
        this.num.setCellValueFactory(new PropertyValueFactory("num"));
        this.peso1.setCellValueFactory(new PropertyValueFactory("Peso1"));
        this.anillo1.setCellValueFactory(new PropertyValueFactory("Anillo1"));
        this.partido1.setCellValueFactory(new PropertyValueFactory("Partido1"));
        this.vs.setCellValueFactory(new PropertyValueFactory("vs"));
        this.partido2.setCellValueFactory(new PropertyValueFactory("Partido2"));
        this.anillo2.setCellValueFactory(new PropertyValueFactory("Anillo2"));
        this.peso2.setCellValueFactory(new PropertyValueFactory("Peso2"));
        
        PeleaT p = new PeleaT(1,2300,21,"JIMENEZ","VS","JIMENEZ2",921,2300);
        peleas.add(p);
        this.tblPeleas.setItems(peleas);
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
            controlador.initAtributes(evento);

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
    
    public class PeleaT{
        private int num;
        private int Peso1;
        private int Anillo1;
        private String Partido1;
        private String vs;
        private String Partido2;
        private int Anillo2;
        private int Peso2;

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
        
    }
    
}
