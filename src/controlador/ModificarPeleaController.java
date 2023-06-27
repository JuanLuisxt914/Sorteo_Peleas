
package controlador;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.PeleaT;

public class ModificarPeleaController {

    @FXML
    private TextField txtPeso1;
    @FXML
    private TextField txtAnillo1;
    @FXML
    private TextField txtPartido1;
    @FXML
    private TextField txtVS;
    @FXML
    private TextField txtPartido2;
    @FXML
    private TextField txtAnillo2;
    @FXML
    private TextField txtPeso2;
    @FXML
    private Label lblNumPelea;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnSalir;
    
    ObservableList<PeleaT> peleas;
    private TableView<PeleaT> tblPeleas;
    PeleaT pelea;
    
    public void initAtributes(ObservableList<PeleaT> peleas, PeleaT pelea,TableView<PeleaT> tblPeleas){
      lblNumPelea.setText(pelea.getNum()+"");
      txtPeso1.setText(pelea.getPeso1()+"");
      txtAnillo1.setText(pelea.getAnillo1()+"");
      txtPartido1.setText(pelea.getPartido1());
      txtVS.setText(pelea.getVs());
      txtPartido2.setText(pelea.getPartido2());
      txtAnillo2.setText(pelea.getAnillo2()+"");
      txtPeso2.setText(pelea.getPeso2()+"");
      this.peleas=peleas;
      this.pelea=pelea;
      this.tblPeleas=tblPeleas;
    }

    @FXML
    private void ModificarPelea(ActionEvent event) {
        
        if (peleas.contains(pelea)) {
            pelea.setPeso1(Integer.parseInt(txtPeso1.getText()));
            pelea.setAnillo1(Integer.parseInt(txtAnillo1.getText()));
            pelea.setPartido1(txtPartido1.getText());
            pelea.setVs(txtVS.getText());
            pelea.setPartido2(txtPartido2.getText());
            pelea.setAnillo2(Integer.parseInt(txtAnillo2.getText()));
            pelea.setPeso2(Integer.parseInt(txtPeso2.getText()));
            this.tblPeleas.refresh();
            salir();
        }else{
            System.out.println("no");
        }
        
        
    }

    @FXML
    private void EliminarPelea(ActionEvent event) {
        if (peleas.contains(pelea)) {
            peleas.remove(pelea);
            salir();
        }
    }

    @FXML
    private void Salir(ActionEvent event) {
        
        salir();
    }
    
    public void salir(){
        // Cerrar ventana
        Stage stage = (Stage) this.btnEliminar.getScene().getWindow();
        stage.close();
    }

    public PeleaT getPelea() {
        return pelea;
    }
    
    
    
            
    
}
