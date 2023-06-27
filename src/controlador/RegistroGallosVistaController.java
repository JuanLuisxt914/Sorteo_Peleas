package controlador;

import conexiondb.ConexionMySQL;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Evento;
import modelo.Gallo;
import modelo.Partido;

public class RegistroGallosVistaController implements Initializable {

    //Tabla Partido
    @FXML
    private TableView<Partido> tblPartidos;
    @FXML
    private TableColumn<Partido, Integer> colID;
    @FXML
    private TableColumn<Partido, String> colPartido;
    
    //Tabla Partidos Amigos
    @FXML
    private TableView<Partido> tblPartidosAmigos;
    @FXML
    private TableColumn<Partido,Integer> colIdPartidosAmigos;
    @FXML
    private TableColumn<Partido, String> colPartidosAmigos;
    
    //Tabla Gallos
    @FXML
    private TableView<Gallo> tblGallos;
    @FXML
    private TableColumn<Gallo, Integer> colPeso;
    @FXML
    private TableColumn<Gallo, Integer> colAnillo;
    @FXML
    private TableColumn<Gallo, String> colPartidoGallos;
    
    //Formulario Gallos
    @FXML
    private Label lblNombrePartido;
    @FXML
    private TextField txtPeso;
    @FXML
    private TextField txtAnillo;
    @FXML
    private Button btnAgregarGallo;
    @FXML
    private Button btnEliminarGallo;
    @FXML
    private Button btnModificarGallo;
    
    //Formulario Partidos
    @FXML
    private TextField txtPartido;
    @FXML
    private TextField txtIdAmigo;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    
    //Formulario Amigos
    @FXML
    private Button btnAgregarAmigo;
    @FXML
    private Button btnEliminarAmigo;
    
    //Variables Globales
    Evento evento;
    private ObservableList<Partido> partidos;
    private ObservableList<Partido> amigos;
    private ObservableList<Gallo> gallos;
    private ObservableList<Integer> numPartidos;
    private ObservableList<Integer> numAmigos;
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gallos = FXCollections.observableArrayList();
        partidos = FXCollections.observableArrayList();
        amigos = FXCollections.observableArrayList();
        numPartidos = FXCollections.observableArrayList();
        numAmigos = FXCollections.observableArrayList();
   
    }
    
    public void initAtributes(Evento evento){
        this.evento= evento;
        initTablas();
    }
     
    public void initTablas(){
        //Partidos
        try {
            ConexionMySQL conexion = new ConexionMySQL("localhost", "sorteo_de_peleas", "root", "");

            conexion.ejecutarConsulta("SELECT * FROM partidos WHERE id_evento = "+evento.getIdEvento());

            ResultSet rs = conexion.getResultSet();
            
            while (rs.next()) {
                int id = rs.getInt("id_partido");
                int idP = rs.getInt("num_partido");
                String nombre = rs.getString("nombre_partido");
                
                Partido p = new Partido(id,idP, nombre);
                partidos.add(p);
                numPartidos.add(idP);
            }
            conexion.cerrarConexion();
            
            this.colID.setCellValueFactory(new PropertyValueFactory("numPartido"));
            this.colPartido.setCellValueFactory(new PropertyValueFactory("nombrePartido"));
            
            this.tblPartidos.setItems(partidos);      
            
        } catch (SQLException ex) {
            Logger.getLogger(RegistroGallosVistaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Gallos
        
        this.colPeso.setCellValueFactory(new PropertyValueFactory("peso"));
        this.colAnillo.setCellValueFactory(new PropertyValueFactory("anillo"));
        this.colPartidoGallos.setCellValueFactory(new PropertyValueFactory("partido"));
        this.tblGallos.setItems(gallos);
        
        //Amigos
        this.colIdPartidosAmigos.setCellValueFactory(new PropertyValueFactory("numPartido"));
        this.colPartidosAmigos.setCellValueFactory(new PropertyValueFactory("nombrePartido"));

}
    
    public void initTablaGallos(Partido e){
        try {
            gallos.clear();
            this.txtPartido.setText(e.getNombrePartido());
            ConexionMySQL conexion = new ConexionMySQL("localhost", "sorteo_de_peleas", "root", "");

            conexion.ejecutarConsulta("SELECT * FROM gallos WHERE gallos.id_partido = "+e.getIdPartido());

            ResultSet rs = conexion.getResultSet();
            
            if (ObtenerModalidad()==1) {
               int cont=0;
               while(rs.next())
                    cont++;
               
                ConexionMySQL conexion1 = new ConexionMySQL("localhost", "sorteo_de_peleas", "root", "");
                conexion1.ejecutarConsulta("SELECT * FROM gallos WHERE gallos.id_partido = "+e.getIdPartido());
                ResultSet rs1 = conexion1.getResultSet();
               
                if (cont == 1) {
                    
                    while (rs1.next()) {
                        int id = rs1.getInt("id_gallo");
                        int anillo = rs1.getInt("anillo");
                        int peso = rs1.getInt("peso");
                        int idP = rs1.getInt("id_partido");
                        String nombre = e.getNombrePartido();

                        Gallo g = new Gallo(id,peso, anillo,nombre);
                        gallos.add(g);
                        this.tblGallos.setItems(gallos);
                    }    
                }else{
                    
                    cont=1;
                    while (rs1.next()) {
                        int id = rs1.getInt("id_gallo");
                        int anillo = rs1.getInt("anillo");
                        int peso = rs1.getInt("peso");
                        int idP = rs1.getInt("id_partido");
                        String nombre = "E"+cont+" "+e.getNombrePartido();
                        cont++;
                        Gallo g = new Gallo(id,peso, anillo,nombre);
                        gallos.add(g);
                        this.tblGallos.setItems(gallos);
                    }  
                }
               conexion1.cerrarConexion();
            }else{
                while (rs.next()) {
                    int id = rs.getInt("id_gallo");
                    int anillo = rs.getInt("anillo");
                    int peso = rs.getInt("peso");
                    int idP = rs.getInt("id_partido");
                    String nombre = e.getNombrePartido();

                    Gallo g = new Gallo(id,peso, anillo,nombre);
                    gallos.add(g);
                    this.tblGallos.setItems(gallos);
                }    
            }

            
            conexion.cerrarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(RegistroGallosVistaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void initTablaAmigos(Partido e){
        try {
            numAmigos.clear();
            amigos.clear();
            
            ConexionMySQL conexion = new ConexionMySQL("localhost", "sorteo_de_peleas", "root", "");

            conexion.ejecutarConsulta("SELECT * FROM amigos WHERE amigos.id_partido = "+e.getIdPartido());

            ResultSet rs = conexion.getResultSet();

            while (rs.next()) {
                
                int id = rs.getInt("id_amigo");
                int num = rs.getInt("num_amigo");
                String nombre = rs.getString("partido_amigo");
                Partido p = new Partido(id,num,nombre);
                amigos.add(p);
                numAmigos.add(num);
                this.tblPartidosAmigos.setItems(amigos);
            }
            conexion.cerrarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(RegistroGallosVistaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
    
    public void closeWindows() throws SQLException {
        try {
            // Cargo la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/PeleasVista.fxml"));

            // Cargo el padre
            Parent root = loader.load();
            
            // Obtengo el controlador
            PeleasVistaController controlador = loader.getController();
            controlador.initAtributes(evento);

            // Creo la scene y el stage
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            // Asocio el stage con el scene
            stage.setScene(scene);
            stage.setTitle("PELEAS");
            stage.getIcons().add(new Image(this.getClass().getResource("/Reportes/Gallo 1.jpg").toString()));
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
    
    ////////////////// Partidos //////////////////////////////////////////////////
    @FXML
    private void Seleccionar(MouseEvent event) {
        if (partidos.size()!=0) {
            Partido e = this.tblPartidos.getSelectionModel().getSelectedItem();
            
            if (e != null) {
                this.lblNombrePartido.setText(e.getNombrePartido());
                this.txtPeso.setText("");
                this.txtAnillo.setText("");
                initTablaGallos(e);
                initTablaAmigos(e);
            }
        }
        
    }
    @FXML
    private void Guardar(ActionEvent event) {  
        try {
            if (this.txtPartido.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Valio Barriga");
                alert.setContentText("Ni el Nombre escribiste ");
                alert.showAndWait();
            }else{
                int num = 0;
                if (partidos.size()==0) {
                    num = 1; 
                }else{
                    int size = this.numPartidos.size()-1;
                    System.out.println("size "+size);
                    System.out.println("entro en else");
                    num = this.numPartidos.get(size)+1; 
                }
                
                String nombre = this.txtPartido.getText();
                Partido p = new Partido(num,nombre);
                if (p.insertarPartido(evento.getIdEvento())) {
                    partidos.clear();
                    initTablas();
                    tblPartidos.setItems(partidos);
                    this.txtPartido.setText("");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Exito ALV");
                    alert.setContentText("Partido agregado con exito ALV ");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Exito ALV");
                    alert.setContentText("Algo Valio Madres we ");
                    alert.showAndWait();                   
                    }
                }
        } catch (SQLException ex) {
            Logger.getLogger(RegistroGallosVistaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    @FXML
    private void Modificar(ActionEvent event) throws SQLException {
        Partido e = this.tblPartidos.getSelectionModel().getSelectedItem();
        if (e != null) {
            if (partidos.contains(e)) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Modificar");
                alert.setContentText("Estas seguro que deseas Modificarlo??");
                Optional<ButtonType> action = alert.showAndWait();
                if (action.get() == ButtonType.OK) {
                    e.setNombrePartido(this.txtPartido.getText());
                    if (e.modificarPartido()) {
                        this.tblPartidos.refresh();
                        this.lblNombrePartido.setText(e.getNombrePartido());
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setHeaderText(null);
                        alert1.setTitle("Exito ALV");
                        alert1.setContentText("El pinche partido fue Modificado con exito ALV ");
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
    @FXML
    private void Eliminar(ActionEvent event) throws SQLException {
        Partido e = this.tblPartidos.getSelectionModel().getSelectedItem();
        if (e != null) {
            if (partidos.contains(e)) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Eliminar");
                alert.setContentText("Estas seguro que deseas eliminarlo??");
                Optional<ButtonType> action = alert.showAndWait();
                if (action.get() == ButtonType.OK) {
                    if (e.borrarPartido()) {
                        partidos.remove(e);
                        gallos.clear();
                        amigos.clear();
                        this.tblGallos.refresh();
                        this.tblPartidos.refresh();
                        this.tblPartidosAmigos.refresh();
                        this.lblNombrePartido.setText("Selecciona el Partido");
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setHeaderText(null);
                        alert1.setTitle("Exito ALV");
                        alert1.setContentText("El pinche partido fue borrado con exito ALV ");
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
    @FXML
    private void EnterPartido(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            this.btnGuardar.fire(); 
        }  
    }
    
    ////////////////// Gallos //////////////////////////////////////////////////
    @FXML
    private void AgregarGallo(ActionEvent event) throws SQLException {
        int modalidad=ObtenerModalidad();
        
        if (modalidad==1) {
            GuardarGallo();
        }else{
            if (gallos.size()<modalidad) {
                GuardarGallo();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Ya registraste los "+modalidad+ " correspondientes");
                alert.showAndWait();
            }
        }
            
    }
    @FXML
    private void EliminarGallo(ActionEvent event) throws SQLException {
        Gallo g = this.tblGallos.getSelectionModel().getSelectedItem();
        if (g != null) {
            if (gallos.contains(g)) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Eliminar");
                alert.setContentText("Estas seguro que deseas eliminarlo??");
                Optional<ButtonType> action = alert.showAndWait();
                if (action.get() == ButtonType.OK) {
                    if (g.borrarGallo()) {
                        gallos.remove(g);                      
                        this.txtAnillo.setText("");
                        this.txtPeso.setText("");
                        this.tblGallos.refresh();
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setHeaderText(null);
                        alert1.setTitle("Exito ALV");
                        alert1.setContentText("El pinche gallo fue borrado con exito ALV ");
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
            alert1.setContentText("No Seleccionaste ningun Gallo ALV");
            alert1.showAndWait();
        }
        
    }
    @FXML
    private void ModificarGallo(ActionEvent event) throws SQLException {
        try {
            Gallo g = this.tblGallos.getSelectionModel().getSelectedItem();
            if (g != null) {
                if (gallos.contains(g)) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Modificar");
                    alert.setContentText("Estas seguro que deseas Modificarlo??");
                    Optional<ButtonType> action = alert.showAndWait();
                    if (action.get() == ButtonType.OK) {
                        g.setAnillo(Integer.parseInt(this.txtAnillo.getText()));
                        g.setPeso(Integer.parseInt(this.txtPeso.getText()));
                        if (g.modificarGallo()) {
                            this.tblGallos.refresh();
                            this.txtAnillo.setText("");
                            this.txtPeso.setText("");
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            alert1.setHeaderText(null);
                            alert1.setTitle("Exito ALV");
                            alert1.setContentText("El pinche Gallo fue Modificado con exito ALV ");
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
                alert1.setContentText("No Seleccionaste ningun Gallo ALV");
                alert1.showAndWait();
            }
            
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Solo se permiten numeros ");
            alert.showAndWait();
        } 
        
    }
    @FXML
    private void seleccionarGallo(MouseEvent event) {
        if (gallos.size()!=0) {
            Gallo g = this.tblGallos.getSelectionModel().getSelectedItem();
            if (g != null) {
                this.txtPeso.setText(g.getPeso()+"");
                this.txtAnillo.setText(g.getAnillo()+"");   
            }  
        }
            
    }
    @FXML
    private void enterPeso(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            this.txtAnillo.requestFocus(); 
        }
    }
    @FXML
    private void enterAnillo(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            this.btnAgregarGallo.fire(); 
            this.txtPeso.requestFocus();
        }
    }
    
    public void GuardarGallo() throws SQLException{
         try {
            Partido e = this.tblPartidos.getSelectionModel().getSelectedItem();
            if (partidos.size()!=0) {
                if (e != null) {
                    if (this.txtAnillo.getText().equals("") ){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setTitle("Error");
                        alert.setContentText("Te falto escribir el anillo ");
                        alert.showAndWait();
                    }else if (this.txtPeso.getText().equals("") ){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setTitle("Error");
                        alert.setContentText("Te falto escribir el peso ");
                        alert.showAndWait();
                    }else{
                        int anillo = Integer.parseInt(this.txtAnillo.getText());
                        int peso = Integer.parseInt(this.txtPeso.getText());
                        String nombre = e.getNombrePartido();
                        Gallo g= new Gallo(peso,anillo,nombre);
                        if (e.getIdPartido()!=0) {
                            if (g.insertarGallo(e.getIdPartido(),evento.getIdEvento())) {
                                initTablaGallos(e);
                                this.txtAnillo.setText("");
                                this.txtPeso.setText("");
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText(null);
                                alert.setTitle("Exito ALV");
                                alert.setContentText("Gallo agregado con exito ALV ");
                                alert.showAndWait();
                            }else{
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setHeaderText(null);
                                alert.setTitle("Error");
                                alert.setContentText("Algo Valio Madres we ");
                                alert.showAndWait();
                            } 
                        }else{
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText(null);
                            alert.setTitle("Error");
                            alert.setContentText("el id es 0");
                            alert.showAndWait();
                        }
                    }
                }else{
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setHeaderText(null);
                    alert1.setTitle("Valio Barriga");
                    alert1.setContentText("No Tienes ningun Partido seleccionado");
                    alert1.showAndWait();   
                }  
            }else{
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setHeaderText(null);
                alert1.setTitle("Valio Barriga");
                alert1.setContentText("No hay Partidos");
                alert1.showAndWait();   
            }             
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Solo se permiten numeros ");
            alert.showAndWait();
        }
    }
    
    public int ObtenerModalidad(){
        int modalidad=0;
        switch(evento.getModalidad()){
            
            case "Derbi de 1 Gallo" :
                modalidad = 1;
                break;
                
            case "Derbi de 2 Gallos" :
                modalidad = 2;
                break; 
                
            case "Derbi de 3 Gallos" :
                modalidad = 3;
                break; 
                
            case "Derbi de 4 Gallos" :
                modalidad = 4;
                break; 
                
            case "Derbi de 5 Gallos" :
                modalidad = 5;
                break; 
                
            case "Derbi de 6 Gallos" :
                modalidad = 6;
                break; 
                
            case "Derbi de 7 Gallos" :
                modalidad = 7;
                break; 
                
            case "Derbi de 8 Gallos" :
                modalidad = 8;
                break; 
                
            case "Derbi de 9 Gallos" :
                modalidad = 9;
                break;
                
            case "Derbi de 10 Gallos" :
                modalidad = 10;
                break;
        }
        return modalidad;
    }

    ////////////////// Amigos //////////////////////////////////////////////////
    @FXML
    private void AgregarAmigo(ActionEvent event) throws SQLException {
        try {
            Partido e = this.tblPartidos.getSelectionModel().getSelectedItem();
            if (partidos.size()!=0) {
                if (e != null) {
                    if (this.txtIdAmigo.getText().equals("") ){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setTitle("Error");
                        alert.setContentText("No escribiste el id del amigo ");
                        alert.showAndWait();
                    }else{
                        int num = Integer.parseInt(this.txtIdAmigo.getText());
                        if (numPartidos.contains(num)) {
                            int id=0;
                            String nombre="";

                            ConexionMySQL conexion = new ConexionMySQL("localhost", "sorteo_de_peleas", "root", "");

                            conexion.ejecutarConsulta("SELECT * FROM partidos WHERE partidos.num_partido = "+num+
                                    " AND partidos.id_evento = "+this.evento.getIdEvento());

                            ResultSet rs = conexion.getResultSet();

                            while (rs.next()) {

                                id = rs.getInt("id_partido");
                                int numa = rs.getInt("num_partido");
                                nombre = rs.getString("nombre_partido");
                                System.out.println("id: "+id);
                                System.out.println("numA: "+numa);
                                System.out.println("nombre: "+nombre);
                            }

                            conexion.cerrarConexion();

                            if (e.getIdPartido()!=0) {
                                if (num != e.getNumPartido()) {
                                    try {
                                        if (!numAmigos.contains(num)) {
                                             if (e.insertarPartidoAmigo(num, nombre,id,evento.getIdEvento())) {
                                                initTablaAmigos(e);
                                                this.txtIdAmigo.setText("");
                                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                alert.setHeaderText(null);
                                                alert.setTitle("Exito ALV");
                                                alert.setContentText("Amigo agregado con exito ALV ");
                                                alert.showAndWait();
                                            }else{
                                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                                alert.setHeaderText(null);
                                                alert.setTitle("Error");
                                                alert.setContentText("Algo Valio Madres we ");
                                                alert.showAndWait(); 
                                            }
                                        }else{
                                            Alert alert = new Alert(Alert.AlertType.ERROR);
                                            alert.setHeaderText(null);
                                            alert.setTitle("Error");
                                            alert.setContentText("Ya agregaste al partido amigo ");
                                            alert.showAndWait();

                                        }
                                        
                                    } catch (SQLException ex) {
                                        Logger.getLogger(RegistroGallosVistaController.class.getName()).log(Level.SEVERE, null, ex);
                                    }    
                                }else{
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setHeaderText(null);
                                    alert.setTitle("Error");
                                    alert.setContentText("Es el mismo partido ");
                                    alert.showAndWait();  
                                    this.txtIdAmigo.setText("");
                                }

                            }else{
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setHeaderText(null);
                                alert.setTitle("Error");
                                alert.setContentText("el id es 0");
                                alert.showAndWait();
                            }   
                        }else{
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText(null);
                            alert.setTitle("Error");
                            alert.setContentText("No existe el partido");
                            alert.showAndWait();
                            this.txtIdAmigo.setText("");
                        }
                    }
                }else{
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setHeaderText(null);
                    alert1.setTitle("Valio Barriga");
                    alert1.setContentText("No Tienes ningun Partido seleccionado");
                    alert1.showAndWait();   
                }  
            }else{
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setHeaderText(null);
                alert1.setTitle("Valio Barriga");
                alert1.setContentText("No Tienes ningun Partido seleccionado");
                alert1.showAndWait();   
            }             
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Solo se permiten numeros ");
            alert.showAndWait();
        }
        
    }
    @FXML
    private void EliminarAmigo(ActionEvent event) throws SQLException {
        Partido pA = this.tblPartidosAmigos.getSelectionModel().getSelectedItem();
        Partido p = this.tblPartidos.getSelectionModel().getSelectedItem();
        if (pA != null) {
            if (amigos.contains(pA)) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Eliminar");
                alert.setContentText("Estas seguro que deseas eliminarlo??");
                Optional<ButtonType> action = alert.showAndWait();
                if (action.get() == ButtonType.OK) {
                    if (pA.borrarPartidoAmigo(p.getNumPartido(),evento.getIdEvento())) {
                        initTablaAmigos(p);
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setHeaderText(null);
                        alert1.setTitle("Exito ALV");
                        alert1.setContentText("El pinche partido fue borrado con exito ALV ");
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
            alert1.setContentText("No Seleccionaste ningun Gallo ALV");
            alert1.showAndWait();
        }
    }

    @FXML
    private void EnterAmigos(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            this.btnAgregarAmigo.fire(); 
        } 
    }
      
}    

