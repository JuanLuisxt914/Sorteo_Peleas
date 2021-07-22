package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Evento;
import modelo.Gallo;
import modelo.GalloTabla;
import modelo.Partido;

public class RegistroGallosVistaController implements Initializable {

    @FXML
    private Button btnAgregarGallo;
    @FXML
    private TableView<GalloTabla> tblGallos;
    @FXML
    private TableColumn<GalloTabla, Integer> colPeso;
    @FXML
    private TableColumn<GalloTabla, Integer> colAnillo;
    @FXML
    private TableColumn<GalloTabla, Integer> colID;
    @FXML
    private TableColumn<GalloTabla, String> colPartido;
    @FXML
    private TextField txtPeso;
    @FXML
    private TextField txtAnillo;
    @FXML
    private TextField txtPartido;
    @FXML
    private TextField txtIdAmigo;
    @FXML
    private TableView<Partido> tblAmigos;
    @FXML
    private TableColumn<Partido, Integer> colIdPartido;
    @FXML
    private TableColumn<Partido, String> colPartidoAmigo;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;

    private ArrayList<Partido> partidos;
    private ArrayList<Gallo> gallos;
    private ArrayList<Partido> amigos;

    private ObservableList<GalloTabla> gallosTabla;
    private ObservableList<Partido> partidosTabla;
    private int numGallo = 1;
    @FXML
    private Label lblNumGallo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gallosTabla = FXCollections.observableArrayList();
        initTablaGallos();
        this.txtPeso.requestFocus();
        //colPeso.prefWidthProperty().bind(colPeso.widthProperty());
        
    }
    public void initTablaGallos(){
        this.colPeso.setCellValueFactory(new PropertyValueFactory("peso"));
        this.colAnillo.setCellValueFactory(new PropertyValueFactory("anillo"));
        this.colID.setCellValueFactory(new PropertyValueFactory("id"));
        this.colPartido.setCellValueFactory(new PropertyValueFactory("partido"));

        GalloTabla g = new GalloTabla(2300,40,1,"Jimenez");
        gallosTabla.add(g);
        gallosTabla.add(g);
        gallosTabla.add(g);
        gallosTabla.add(g);
        gallosTabla.add(g);
        gallosTabla.add(g);
        gallosTabla.add(g);
        gallosTabla.add(g);
        gallosTabla.add(g);
        gallosTabla.add(g);
        this.tblGallos.setItems(gallosTabla);
    }
    
    public void initTablaPartidoAmigos(){
        this.colIdPartido.setCellValueFactory(new PropertyValueFactory("idPartido"));
        this.colPartidoAmigo.setCellValueFactory(new PropertyValueFactory("nombrePartido"));

        this.tblAmigos.setItems(partidosTabla);
        
    }

    public void closeWindows() {
        try {
            // Cargo la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/PeleasVista.fxml"));

            // Cargo el padre
            Parent root = loader.load();
            
            // Obtengo el controlador
            PeleasVistaController controlador = loader.getController();

            // Creo la scene y el stage
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            // Asocio el stage con el scene
            stage.setScene(scene);
            stage.show();
            
            // Indico que debe hacer al cerrar
            stage.setOnCloseRequest(e -> controlador.closeWindows());

            // Ciero la ventana donde estoy
            Stage myStage = (Stage) this.btnAgregarGallo.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(MenuVistaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Seleccionar(MouseEvent event) {
    }

    @FXML
    private void AgregarGallo(ActionEvent event) {
        try {
            System.out.println("aqui entra");
            int peso = Integer.parseInt(this.txtPeso.getText());
            int anillo = Integer.parseInt(this.txtAnillo.getText());
            int id = this.gallosTabla.size() + 1;
            String partido = this.txtPartido.getText();
            GalloTabla g = new GalloTabla(peso, anillo, id, partido);
            gallosTabla.add(g);
            this.tblGallos.refresh();
            System.out.println("aqui tmbn");

            if (this.numGallo == 1) {
                numGallo++;
                this.txtPeso.setText("");
                this.txtAnillo.setText("");
                this.lblNumGallo.setText("Gallo 2");
            } else {
                numGallo--;
                this.txtPeso.setText("");
                this.txtAnillo.setText("");
                this.txtPartido.setText("");
                this.lblNumGallo.setText("Gallo 1");

                int idPartido = partidos.size() + 1;

                Partido p = new Partido(idPartido, partido, gallos, amigos);
                partidos.add(p);
                gallos.clear();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Exito");
                alert.setContentText("Partido Agregado con Exito ALV");
                alert.showAndWait();
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Formato Incorrecto");
            alert.showAndWait();
        }
    }
    @FXML
    private void AgregarAmigo(ActionEvent event) {
    }

    @FXML
    private void Guardar(ActionEvent event) {
    }

    @FXML
    private void Modificar(ActionEvent event) {
    }

    @FXML
    private void Eliminar(ActionEvent event) {
    }
}