package controlador;

import conexiondb.ConexionMySQL;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Evento;

public class MenuVistaController implements Initializable {

    @FXML
    private TableView<Evento> tblEventos;
    @FXML
    private TableColumn<Evento, String> colNombre;
    @FXML
    private TableColumn<LocalDate, String> colFecha;
    @FXML
    private TableColumn<Evento, String> colModalidad;
    @FXML
    private TextField txtPalenque;
    @FXML
    private DatePicker dtpFechaEvento;
    @FXML
    private ComboBox<String> cmbModalidad;
    @FXML
    private Button btnAgregarEvento;
    @FXML
    private Button btnModificarEvento;
    @FXML
    private Button btnIRaEvento;
    private ObservableList<Evento> eventos;
    private ObservableList<Evento> aux;
    @FXML
    private Button btnEliminarEvento;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        eventos = FXCollections.observableArrayList();
        aux = FXCollections.observableArrayList();
        initCombos();
        initTablaEventos();

    }

    public void initTablaEventos() {

        try {

            ConexionMySQL conexion = new ConexionMySQL("localhost", "sorteo_de_peleas", "root", "");

            conexion.ejecutarConsulta("SELECT * FROM eventos");

            ResultSet rs = conexion.getResultSet();

            while (rs.next()) {
                int id = rs.getInt("id_evento");
                String nombre = rs.getString("nombre_palenque");
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                String modalidad = rs.getString("modalidad");

                Evento e = new Evento(id, nombre, fecha, modalidad);
                aux.add(e);
                for (Evento x : eventos) {
                    aux.add(x);
                }
                eventos.clear();
                for (Evento x : aux) {
                    eventos.add(x);
                }
                aux.clear();
            }
            conexion.cerrarConexion();
            this.colNombre.setCellValueFactory(new PropertyValueFactory("nombrePalenque"));
            this.colFecha.setCellValueFactory(new PropertyValueFactory("fechaEvento"));
            this.colModalidad.setCellValueFactory(new PropertyValueFactory("modalidad"));

            this.tblEventos.setItems(eventos);

        } catch (SQLException ex) {
            Logger.getLogger(MenuVistaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void initCombos() {
        ObservableList<String> obs = FXCollections.observableArrayList();
        obs.add("Derbi de 1 Gallo");
        obs.add("Derbi de 2 Gallos");

        this.cmbModalidad.setItems(obs);
    }

    @FXML
    private void AgregarEvento(ActionEvent event) throws SQLException {
        try {
            if (this.txtPalenque.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Valio Barriga");
                alert.setContentText("Ni el Nombre escribiste ");
                alert.showAndWait();
            } else if (this.dtpFechaEvento.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Valio Barriga");
                alert.setContentText("Te falto escribir la fecha we ");
                alert.showAndWait();
            } else if (this.cmbModalidad.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Valio Barriga");
                alert.setContentText("Te falto escoger la modalidad ");
                alert.showAndWait();
            } else {

                String palenque = this.txtPalenque.getText();
                LocalDate fecha = this.dtpFechaEvento.getValue();
                String modalidad = this.cmbModalidad.getValue();
                Evento e = new Evento(palenque, fecha, modalidad);
                if (e.insertarEvento()) {
                    eventos.clear();
                    initTablaEventos();
                    this.txtPalenque.setText("");
                    this.dtpFechaEvento.setValue(null);
                    this.cmbModalidad.setValue("");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Exito ALV");
                    alert.setContentText("Evento agregado con exito ALV ");
                    alert.showAndWait();
                    cambiarVentana(eventos.get(0));
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Exito ALV");
                    alert.setContentText("Algo Valio Madres we ");
                    alert.showAndWait();
                }
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Valio Barriga");
            alert.setContentText("Algo llenaste mal pendejo");
            alert.showAndWait();
        }
    }

    @FXML
    private void ModificarEvento(ActionEvent event) throws SQLException {
        Evento e = this.tblEventos.getSelectionModel().getSelectedItem();
        if (e != null) {
            if (eventos.contains(e)) {
                e.setNombrePalenque(this.txtPalenque.getText());
                e.setModalidad(this.cmbModalidad.getValue());
                e.setFechaEvento(this.dtpFechaEvento.getValue());
                if (e.modificarEvento()) {
                    this.tblEventos.refresh();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Exitooo");
                    alert.setContentText("Evento Modificado con Exito ALV");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Valio Barriga");
                    alert.setContentText("Algo Valio Madres we");
                    alert.showAndWait();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Valio Barriga");
                alert.setContentText("El evento no existe");
                alert.showAndWait();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Valio Barriga");
            alert.setContentText("No seleccionaste ningun evento ALV");
            alert.showAndWait();
        }

    }

    @FXML
    private void Seleccionar(MouseEvent event) {
        Evento e = this.tblEventos.getSelectionModel().getSelectedItem();
        if (e != null) {
            this.txtPalenque.setText(e.getNombrePalenque());
            this.dtpFechaEvento.setValue(e.getFechaEvento());
            this.cmbModalidad.setValue(e.getModalidad());
        }
    }

    @FXML
    private void IrAEvento(ActionEvent event) {
        Evento e = this.tblEventos.getSelectionModel().getSelectedItem();
        if (e != null) {
            cambiarVentana(e);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Valio Barriga");
            alert.setContentText("No Seleccionaste ningun evento ALV");
            alert.showAndWait();
        }

    }

    @FXML
    private void EliminarEvento(ActionEvent event) throws SQLException {
        Evento e = this.tblEventos.getSelectionModel().getSelectedItem();
        if (e != null) {
            if (eventos.contains(e)) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Eliminar");
                alert.setContentText("Estas seguro que deseas eliminarlo??");
                Optional<ButtonType> action = alert.showAndWait();
                if (action.get() == ButtonType.OK) {
                    if (e.borrarEvento()) {
                        eventos.remove(e);
                        this.tblEventos.refresh();
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setHeaderText(null);
                        alert1.setTitle("Exito ALV");
                        alert1.setContentText("El pinche evento fue borrado con exito ALV ");
                        alert1.showAndWait();
                    }
                }
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setHeaderText(null);
                alert1.setTitle("Valio Barriga");
                alert1.setContentText("Algo Valio Madres we");
                alert1.showAndWait();
            }
        } else {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setHeaderText(null);
            alert1.setTitle("Valio Barriga");
            alert1.setContentText("No Seleccionaste ningun evento ALV");
            alert1.showAndWait();
        }

    }

    public void cambiarVentana(Evento ev) {
        try {
            // Cargo la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/PeleasVista.fxml"));

            // Cargo el padre
            Parent root = loader.load();

            // Obtengo el controlador
            PeleasVistaController controlador = loader.getController();
            controlador.initAtributes(ev);

            // Creo la scene y el stage
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            // Asocio el stage con el scene
            stage.setScene(scene);
            stage.show();

            // Indico que debe hacer al cerrar
            stage.setOnCloseRequest(e -> controlador.closeWindows());

            // Ciero la ventana donde estoy
            Stage myStage = (Stage) this.btnIRaEvento.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(PeleasVistaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void DobleClick(MouseEvent event) {
        Evento e = this.tblEventos.getSelectionModel().getSelectedItem();
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            System.out.println("Diste dos click we Calmate ALV");
            cambiarVentana(e);
        }
    }

}
