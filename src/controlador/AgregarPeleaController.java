
package controlador;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.PeleaT;

/**
 * FXML Controller class
 *
 * @author Desarrollo 5
 */
public class AgregarPeleaController  {

    @FXML
    private Button btnSalir;
    @FXML
    private Button btnAgregar;
    @FXML
    private Label lblNumPelea;
    @FXML
    private TextField txtPeso2;
    @FXML
    private TextField txtAnillo2;
    @FXML
    private TextField txtPartido2;
    @FXML
    private TextField txtPartido1;
    @FXML
    private TextField txtAnillo1;
    @FXML
    private TextField txtPeso1;
    
    private ObservableList<PeleaT> peleas;
    private TableView<PeleaT> tblPeleas;
    /**
     * Initializes the controller class.
     */    

    public void initAtributes(ObservableList<PeleaT> peleas,TableView<PeleaT> tblPeleas){
    this.peleas = peleas;
    this.tblPeleas = tblPeleas;
    this.lblNumPelea.setText(peleas.size()+1+"");
    }
    @FXML
    private void Salir(ActionEvent event) {
        salir();
    }

    @FXML
    private void AgregarPelea(ActionEvent event) {
        if (txtPeso1.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Te falto escribir algo");
            alert.showAndWait();
            
        }else if(txtAnillo1.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Te falto escribir algo");
            alert.showAndWait();
            
        }else if(txtPartido1.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Te falto escribir algo");
            alert.showAndWait();
            
        }else if(txtPartido2.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Te falto escribir algo");
            alert.showAndWait();
            
        }else if(txtAnillo2.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Te falto escribir algo");
            alert.showAndWait();
            
        }else if(txtPeso2.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Te falto escribir algo");
            alert.showAndWait();
            
        }else{
            try{
                int num = peleas.size()+1;
                int peso1 = Integer.parseInt(txtPeso1.getText());
                int anillo1 = Integer.parseInt(this.txtAnillo1.getText());
                String partido1 = this.txtPartido1.getText();
                String partido2 = this.txtPartido2.getText();
                int anillo2 = Integer.parseInt(this.txtAnillo2.getText());
                int peso2 = Integer.parseInt(txtPeso2.getText());
                PeleaT pe = new PeleaT(num,peso1,anillo1,partido1,"VS",partido2,anillo2,peso2);
                peleas.add(pe);
                this.tblPeleas.refresh();
                salir();
            }catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Algo llenaste algo mal ");
            alert.showAndWait();
            }          
        } 
    }
    
    public void salir(){
        // Cerrar ventana
        Stage stage = (Stage) this.btnAgregar.getScene().getWindow();
        stage.close();
    }
    
}
