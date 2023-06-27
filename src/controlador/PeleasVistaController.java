package controlador;

import Reportes.AleatorioDataSource;
import Reportes.RondaDataSource;
import conexiondb.ConexionMySQL;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.WindowConstants;
import modelo.Evento;
import modelo.Gallo;
import modelo.Partido;
import modelo.Pelea;
import modelo.PeleaT;
import modelo.Peleas;
import modelo.Ronda;
import modelo.Rondas;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class PeleasVistaController implements Initializable {

    @FXML
    private ToggleGroup TipoDeSorteo;
    @FXML
    private Button btnRegistro;   
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
    @FXML
    private Button Sortear;
    @FXML
    private RadioButton rbRondas;
    @FXML
    private RadioButton rbAleatorio;

    private Evento evento;
    private ObservableList<PeleaT> peleas;
    private ObservableList<Partido> partidos;  
    private ObservableList<Gallo> gallosSinPareja;  
    
    private static final DataFormat SERIALIZED_MIME_TYPE_PELEA = new DataFormat("application/x-java-serialized-object-p");
    @FXML
    private TextField txtTolerancia;
    @FXML
    private Button btnImprimir;
    @FXML
    private TableView<Gallo> tblGallos;
    @FXML
    private TableColumn<Gallo, Integer> colR;
    @FXML
    private TableColumn<Gallo, Integer> colPesoGallos;
    @FXML
    private TableColumn<Gallo, String> colPartidoGallos;
    @FXML
    private Button btnModificarPelea;
    @FXML
    private Button btnSortearManual;
    @FXML
    private Button btnOrdenar;
    @FXML
    private Button btnAgregarPelea;

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
        
        
        this.tblPeleas.setItems(peleas);
        PeleaT pt = new PeleaT(05,05,005,"Desarrollo 5","VS"  ,"Software y Consultoria",05,005);
        peleas.add(pt);
        ////////////////////////////////////////////////////////////////////////
        gallosSinPareja = FXCollections.observableArrayList();
        this.colR.setCellValueFactory(new PropertyValueFactory("id"));
        this.colPesoGallos.setCellValueFactory(new PropertyValueFactory("peso"));
        this.colPartidoGallos.setCellValueFactory(new PropertyValueFactory("partido"));
        
        this.tblGallos.setItems(gallosSinPareja);
        

        
        
        partidos = FXCollections.observableArrayList();
        
        tblPeleas.setRowFactory(tv -> {
            TableRow<PeleaT> row = new TableRow<>();


            row.setOnDragDetected(event -> {
                if (! row.isEmpty()) {
                    Integer index = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(SERIALIZED_MIME_TYPE_PELEA, index);
                    db.setContent(cc);
                    event.consume();
                }
            });

            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE_PELEA)) {
                    if (row.getIndex() != ((Integer)db.getContent(SERIALIZED_MIME_TYPE_PELEA)).intValue()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE_PELEA)) {
                    int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE_PELEA);
                    PeleaT draggedPerson = tblPeleas.getItems().remove(draggedIndex);

                    int dropIndex ; 

                    if (row.isEmpty()) {
                        dropIndex = tblPeleas.getItems().size() ;
                    } else {
                        dropIndex = row.getIndex();
                    }

                    
                    tblPeleas.getItems().add(dropIndex, draggedPerson);

                    event.setDropCompleted(true);
                    tblPeleas.getSelectionModel().select(dropIndex);
                    event.consume();

                    for (int i = 0; i < peleas.size(); i++) {
                        peleas.get(i).setNum(i+1);
                        tblPeleas.refresh();
                    }

                }
            });

            return row ;
        });
    }    
    
    public void initAtributes(Evento evento) throws SQLException{
        this.evento= evento;
        ObtenerPartidos();
        if (partidos.size()==0) {
            Gallo g = new Gallo();
            g.setId(5);
            g.setPeso(0000);
            g.setPartido("Sin Gallos");
            gallosSinPareja.add(g);
        }else{
            int c =0;
            for(Partido p:partidos){
                int ce=1;
                for(Gallo g : p.getGallos()){
                    if (p.getGallos().size()!=1 & ObtenerModalidad()== 1) {
                        g.setPartido("E"+ce+" "+p.getNombrePartido());
                        ce++;
                    }
                    c++;
                    g.setId(c);
                    gallosSinPareja.add(g);
                }
            }
        }
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
            stage.setTitle("EVENTOS");
            stage.getIcons().add(new Image(this.getClass().getResource("/Reportes/Gallo 1.jpg").toString()));
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
//            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(true);
            stage.getIcons().add(new Image(this.getClass().getResource("/Reportes/Gallo 1.jpg").toString()));
            stage.setTitle("Registro");
            stage.show();

             //Indico que debe hacer al cerrar
            stage.setOnCloseRequest(e -> {
                try {
                    controlador.closeWindows();
                } catch (SQLException ex) {
                    Logger.getLogger(PeleasVistaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

             //Ciero la ventana donde estoy
            Stage myStage = (Stage) this.btnRegistro.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            //Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
 
    @FXML
    private void Sortear(ActionEvent event) throws SQLException, InterruptedException {
             
        ObtenerPartidos();
        
        if (partidos.size()!=0) {
            if(rbRondas.isSelected()){
                if (VerificarCantidaGallos()) {
                    //prueba(rondas); 
                    ArrayList<Peleas> pf= new ArrayList<Peleas>();
                    ArrayList<Peleas> aux= new ArrayList<Peleas>();
                    int GBContador=0;

                    for (int i = 0; i < 1000; i++) {  
                        Rondas rondas = ObtenerListaGallosRondas();
                        pf.clear();

                        //Se generan las peleas de las 3 rondas
                        for(Ronda r :rondas.getRonda()){
                            Peleas p= PeleasSorteo(r.getGallos());
                            p.setRonda(r.getIdRonda());                     
                            pf.add(p);
                        }
                        //contabilizo los gallos botados
                        int GB=0;
                        for(Peleas pp:pf )
                            GB=GB+pp.getGallosBotados().size();

                        //Pregunto si es par y pregunto la cantidad de gallos botados
                        if (partidos.size()%2==0) {
                            if (GB==0){
                                System.out.println("INTENTOS: "+i);
                                break;
                            } 
                        }else{
                            if (GB==ObtenerModalidad()) {
                                System.out.println("INTENTOS: "+i);
                                break;
                            }
                        }
                        //Preparar todo para el nuevo ciclo
                        // Hacer true todos los gallos    
                        for(Partido pa : partidos)
                            for(Gallo g : pa.getGallos())
                                g.setMatch(false);

                        //Encontrar Sorteo con menos gallos botados
                        if (i==0){
                            aux.addAll(pf);
                            GBContador=GB;
                        } 
                        else{
                            if (GB<=GBContador) {
                                if (GB==GBContador) {

                                    int gallosBotadosActual=0;
                                    for(Peleas pca :pf )
                                        for (Gallo gca : pca.getGallosBotados()) 
                                             gallosBotadosActual = gallosBotadosActual+gca.getPeso();

                                    int gallosBotadosAux=0;
                                    for(Peleas pca :aux )
                                        for (Gallo gca : pca.getGallosBotados()) 
                                             gallosBotadosAux = gallosBotadosAux+gca.getPeso();


                                    if (gallosBotadosActual>gallosBotadosAux) {
                                        System.out.println("Entro aqui");
                                        System.out.println("Actual: "+gallosBotadosActual+"  ----  Aux: "+gallosBotadosAux);
                                        aux.clear();
                                        aux.addAll(pf);
                                        GBContador=GB;
                                    }else{
                                        pf.clear();
                                        pf.addAll(aux);
                                    }

                                }else{
                                    aux.clear();
                                    aux.addAll(pf);
                                    GBContador=GB;
                                }


                            }else{
                                pf.clear();
                                pf.addAll(aux);
                            }
                        } 
                    }
                    //Imprimir
                    peleas.clear();
                    gallosSinPareja.clear();
                    for(Peleas pp:pf ){

                        for(Pelea pe : pp.getPeleas()){
                            
                            String rondT=pp.getRonda()+"";
                            PeleaT pt = new PeleaT(pe.getNumPelea(),pe.getGallo1().getPeso(),pe.getGallo1().getAnillo(),pe.getGallo1().getPartido(),
                            rondT,pe.getGallo2().getPartido(),pe.getGallo2().getAnillo(),pe.getGallo2().getPeso());
                            pt.setAmigosPartido1(pe.getGallo1().getAmigos());
                            pt.setAmigosPartido2(pe.getGallo2().getAmigos());
                            pt.setIdPartido1(pe.getGallo1().getIdPartido());
                            pt.setIdPartido2(pe.getGallo2().getIdPartido());
                            peleas.add(pt);
                        }

                        if (pp.getGallosBotados().size()!=0) {
                            for(Gallo g :pp.getGallosBotados()){
                                String rondT=pp.getRonda()+"";
                                PeleaT pt = new PeleaT(0,g.getPeso(),g.getAnillo(),g.getPartido(),
                                      rondT  ,"",000,0000);
                                peleas.add(pt);
                                g.setId(pp.getRonda());
                                gallosSinPareja.add(g);
                            }
                        }
                    }
                    for (int i = 0; i < peleas.size(); i++) {
                            peleas.get(i).setNum(i+1);
                            tblPeleas.refresh();
                    }
                    
                    if (gallosSinPareja.size()==0) {
                    Gallo g = new Gallo();
                    g.setId(5);
                    g.setPeso(0000);
                    g.setPartido("Sin gallos");
                    gallosSinPareja.add(g);
                    }
                }
                
            }else{// aleatorio /////////////////////////////////////////////////
                Peleas p = new Peleas();
                Peleas aux = new Peleas();

                for (int i = 0; i < 1000; i++) {
                    ArrayList<Gallo> gallos = ObtenerListaGallosAleatorio();       
                    p = PeleasSorteo(gallos);

                    if (gallos.size()%2==0){
                        if (p.getGallosBotados().size()==0) {
                            System.out.println("Intentos: "+i);
                            break;
                        }
                    }else{
                        if (p.getGallosBotados().size()==1) {
                            System.out.println("Intentos: "+i);
                            break;
                        }
                    }    

                    if (i==0)
                        aux = new Peleas(p.getPeleas(),p.getGallosBotados());
                    else{
                        if (p.getGallosBotados().size()<=aux.getGallosBotados().size()) {

                            if (p.getGallosBotados().size()==aux.getGallosBotados().size()) {
                                int gallosBotadosP=0;
                                for(Gallo g: p.getGallosBotados())
                                    gallosBotadosP = gallosBotadosP+g.getPeso();

                                int gallosBotadosAux=0;
                                for(Gallo g: aux.getGallosBotados())
                                    gallosBotadosAux = gallosBotadosAux+g.getPeso();

                                if (gallosBotadosP>gallosBotadosAux) {

                                    aux = new Peleas(p.getPeleas(),p.getGallosBotados());  
                                }else{
                                    p = new Peleas(aux.getPeleas(),aux.getGallosBotados());
                                }
                            }else
                                aux = new Peleas(p.getPeleas(),p.getGallosBotados());

                        }else
                            p = new Peleas(aux.getPeleas(),aux.getGallosBotados());      
                    }    
                }    
                //Imprimir
                peleas.clear();
                gallosSinPareja.clear();
                for(Pelea pe : p.getPeleas()){
//                    pe.getGallo1().getAmigos();
                    PeleaT pt = new PeleaT(pe.getNumPelea(),pe.getGallo1().getPeso(),pe.getGallo1().getAnillo(),pe.getGallo1().getPartido(),
                            "VS",pe.getGallo2().getPartido(),pe.getGallo2().getAnillo(),pe.getGallo2().getPeso());
                    pt.setAmigosPartido1(pe.getGallo1().getAmigos());
                    pt.setAmigosPartido2(pe.getGallo2().getAmigos());
                    pt.setIdPartido1(pe.getGallo1().getIdPartido());
                    pt.setIdPartido2(pe.getGallo2().getIdPartido());
                    peleas.add(pt);
                }
                for(Gallo g :p.getGallosBotados()){
                    PeleaT pt = new PeleaT(0,g.getPeso(),g.getAnillo(),g.getPartido(),
                            "VS","",000,0000);
                    g.setId(g.getAnillo());
                    peleas.add(pt);
                    gallosSinPareja.add(g);
                }   
                for (int i = 0; i < peleas.size(); i++) {
                            peleas.get(i).setNum(i+1);
                            
                            tblPeleas.refresh();
                }
                if (gallosSinPareja.size()==0) {
                    Gallo g = new Gallo();
                    g.setId(5);
                    g.setPeso(0000);
                    g.setPartido("Sin Gallos");
                    gallosSinPareja.add(g);
                }
            }
            
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("No se han registrado partidos");
            alert.showAndWait();
        }
            
        
    }
    
    public void Ordenar(int min){
        if (peleas.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("No se han registrado partidos");
            alert.showAndWait();
            
        }else{
            for (int i = 0; i < peleas.size(); i++) {
                System.out.println("////////////////////////////////////////////");
                System.out.println("Pelea "+(i+1));
                System.out.println(peleas.get(i).getPartido1());
                System.out.println("id partido: "+peleas.get(i).getIdPartido1());
                System.out.println("Amigos");
                for (Partido partidosAmigos: peleas.get(i).getAmigosPartido1()) {
                    System.out.println(partidosAmigos.getNombrePartido()+" id: "+partidosAmigos.getNumPartido());
                }
                System.out.println("");
                System.out.println(peleas.get(i).getPartido2());
                System.out.println("id partido: "+peleas.get(i).getIdPartido2());
                System.out.println("Amigos");
                for (Partido partidosAmigos: peleas.get(i).getAmigosPartido2()) {
                    System.out.println(partidosAmigos.getNombrePartido()+" id: "+partidosAmigos.getNumPartido());
                }
                System.out.println("");
            }
        }
    }

    public Peleas PeleasSorteo(ArrayList<Gallo> gallos){
        
        ArrayList<Integer> lista1 = new ArrayList<Integer>();
        ArrayList<Integer> lista2 = new ArrayList<Integer>();
            
        for(Gallo g: gallos){           
            lista1.add(ObtenerNumeroAleatorioLista(lista1,gallos.size()));
            lista2.add(ObtenerNumeroAleatorioLista(lista2,gallos.size()));
            g.setMatch(false); 
        } 
        
        ArrayList<Pelea> peleas = new ArrayList<Pelea>();
        int tol= Integer.parseInt(this.txtTolerancia.getText());
        int c=1;
        for(Integer x: lista1){
            
            for(Integer y :lista2){
                
                if (!gallos.get(x).isMatch() && !gallos.get(y).isMatch() ) { //Si no tiene match compara el peso
                    
                    if (gallos.get(x).getPeso()<= gallos.get(y).getPeso()+tol && gallos.get(x).getPeso()>= gallos.get(y).getPeso()-tol) {
                        
                        if (gallos.get(x).getIdPartido()!= gallos.get(y).getIdPartido()) {// Verificar los amigos
                            boolean amigos = true;
                            for(Partido p :gallos.get(x).getAmigos()){
                                if(p.getNumPartido()== gallos.get(y).getIdPartido()){
                                    amigos = false;                                  
                                }
                            }
                            if (amigos) {
                                Pelea pe = new Pelea(c,peleas.size()+1,gallos.get(x),gallos.get(y));
                                gallos.get(x).setMatch(true);
                                gallos.get(y).setMatch(true);
                                peleas.add(pe);
                                c++;
                            }   
                        }  
                    }
                }
            }
        }
        ArrayList<Gallo> gallosBotados = new ArrayList<Gallo>();
        for(Gallo gb: gallos)
            if (!gb.isMatch()){
                gallosBotados.add(gb);
            } 
                
        Peleas p = new Peleas(peleas,gallosBotados);
        return p;
        
    }
    
    ////Rondas//////////////////
    
    public boolean VerificarCantidaGallos(){
        boolean V= true; 
        int modalidad= ObtenerModalidad();
        
        if (modalidad!=1) {
            ArrayList<Partido> partidosIncompletos = new ArrayList<Partido>();           
            for(Partido p : partidos)
                if (p.getGallos().size() != modalidad ) 
                    partidosIncompletos.add(p);           
            if (partidosIncompletos.size()!=0){
                V= false;
                System.out.println("Partidos Incompletos");
                for(Partido p : partidosIncompletos)
                    System.out.println(p.getNombrePartido());
            }       
            return V;
        }else{
            return V;
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
    
    public int ObtenerNumeroAleatorioLista(ArrayList<Integer> lista, int size ){
        int numeroAleatorio = (int) (Math.random()*size);
        if (lista.contains(numeroAleatorio)) 
            return ObtenerNumeroAleatorioLista(lista, size);
        else
            return numeroAleatorio;
    }
    
    public  int ObtenerNumeroAleatorio(ArrayList<Gallo> gallos, int size){         
        int numeroAleatorio = (int) (Math.random()*size);
        
        if(gallos.get(numeroAleatorio).isMatch())            
            return ObtenerNumeroAleatorio(gallos, size);    
        else
              return numeroAleatorio;   
    }
    
    public Rondas ObtenerListaGallosRondas(){      
        int modalidad = ObtenerModalidad();
        Rondas rondas = new Rondas();
        int numRonda=1;
        int numAleatorio;
        for (int i = 0; i < modalidad; i++) {
            Ronda r = new Ronda(numRonda);
            
            for(Partido p : partidos){
                numAleatorio= ObtenerNumeroAleatorio(p.getGallos(), modalidad);                
                p.getGallos().get(numAleatorio).setMatch(true);
                p.getGallos().get(numAleatorio).setIdPartido(p.getNumPartido());
                Gallo g=p.getGallos().get(numAleatorio);
                g.setAmigos(p.getAmigos());
                r.getGallos().add(g);
            }
            rondas.getRonda().add(r);
            numRonda++;      
        }
        return rondas;
    }
    
    public ArrayList<Gallo> ObtenerListaGallosAleatorio(){
        ArrayList<Gallo>gallos = new ArrayList<Gallo>();
            for(Partido p:partidos){
                int c =1;
                for(Gallo g : p.getGallos()){
                    
                    if (p.getGallos().size()!=1 & ObtenerModalidad()== 1) {
                        g.setPartido("E"+c+" "+p.getNombrePartido());
                        c++;
                    }
                    g.setAmigos(p.getAmigos());
                    g.setIdPartido(p.getNumPartido());
                    gallos.add(g);
                }
            } 
        return gallos;
    }
    
    public void ObtenerPartidos() throws SQLException{    
        partidos.clear();
        ConexionMySQL conexion = new ConexionMySQL("localhost", "sorteo_de_peleas", "root", "");

        conexion.ejecutarConsulta("SELECT * FROM partidos WHERE id_evento = "+evento.getIdEvento());

        ResultSet rs = conexion.getResultSet();    
        while (rs.next()) {
            int id = rs.getInt("id_partido");
            int idP = rs.getInt("num_partido");
            String nombre = rs.getString("nombre_partido");            
            Partido p = new Partido(id,idP, nombre);
            
            /////////////////////////////////////////////////////////////////////////////////
            ConexionMySQL conexionG = new ConexionMySQL("localhost", "sorteo_de_peleas", "root", "");

            conexionG.ejecutarConsulta("SELECT * FROM gallos WHERE gallos.id_partido = "+id);

            ResultSet rsG = conexionG.getResultSet();

            while (rsG.next()) {
                int idG = rsG.getInt("id_gallo");
                int anilloG = rsG.getInt("anillo");
                int pesoG = rsG.getInt("peso");
                int idPG = rsG.getInt("id_partido");
                
                Gallo g = new Gallo(idG,pesoG, anilloG,nombre);
                p.getGallos().add(g);
            }
            conexionG.cerrarConexion();
            ///////////////////////////////////////////////////////////////////////////////////////////
            ConexionMySQL conexionA = new ConexionMySQL("localhost", "sorteo_de_peleas", "root", "");
            
            conexionA.ejecutarConsulta("SELECT * FROM amigos WHERE amigos.id_partido = "+id);

            ResultSet rsA = conexionA.getResultSet();

            while (rsA.next()) {

                int idA = rsA.getInt("id_amigo");
                int numA= rsA.getInt("num_amigo");
                String nombreA = rsA.getString("partido_amigo");
                Partido pA = new Partido(idA,numA,nombreA);
                p.getAmigos().add(pA);
            }
            
            conexionA.cerrarConexion();

            partidos.add(p);
           
        }
        conexion.cerrarConexion();    
    }

    @FXML
    private void Imprimir(ActionEvent event) {
        try {
                // Cargo la vista
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/ImprimirVista.fxml"));

                // Cargo el padre
                Parent root1 = loader.load();

                // Obtengo el controlador
                ImprimirController controlador = loader.getController();
                int tipoCotejo=1;
                if (rbRondas.isSelected()) 
                    tipoCotejo=2;
                
            
               controlador.initAtributes(ObtenerModalidad(),evento,peleas,partidos,tipoCotejo);

                // Creo la scene y el stage
                Scene scene = new Scene(root1);
                Stage stage = new Stage();

                // Asocio el stage con el scene
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.setResizable(true);
                stage.setTitle("Imprimir");
                stage.getIcons().add(new Image(this.getClass().getResource("/Reportes/Gallo 1.jpg").toString()));
                stage.show();
                tblPeleas.refresh();
                
            } catch (IOException ex) {
                //Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
                
    }

    @FXML
    private void ModificarPelea(ActionEvent event) {
        PeleaT pelea = this.tblPeleas.getSelectionModel().getSelectedItem();
        
        if (pelea!= null) {
             try {
                // Cargo la vista
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/MoficarPelea.fxml"));

                // Cargo el padre
                Parent root1 = loader.load();

                // Obtengo el controlador
                ModificarPeleaController controlador = loader.getController();
                controlador.initAtributes(peleas,pelea,tblPeleas);

                // Creo la scene y el stage
                Scene scene = new Scene(root1);
                Stage stage = new Stage();

                // Asocio el stage con el scene
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.setResizable(true);
                stage.setTitle("MODIFICAR PELEA");
                stage.getIcons().add(new Image(this.getClass().getResource("/Reportes/Gallo 1.jpg").toString()));
                stage.show();
                tblPeleas.refresh();
                
            } catch (IOException ex) {
                //Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }    
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Valio Barriga");
            alert.setContentText("No seleccionaste ninguna pelea ");
            alert.showAndWait();
        }
    }

    @FXML
    private void Refresh(ActionEvent event) {
        this.tblPeleas.refresh();
    }

    @FXML
    private void enterTolerancia(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            this.Sortear.fire();
        }
    }

    @FXML
    private void OrdenarPeleas(ActionEvent event) {
        Ordenar(1);
    }

    @FXML
    private void AgregarPelea(ActionEvent event) {
        
        try {
            // Cargo la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/AgregarPelea.fxml"));

            // Cargo el padre
            Parent root1 = loader.load();

            // Obtengo el controlador
            AgregarPeleaController controlador = loader.getController();
            controlador.initAtributes(peleas, tblPeleas);

            // Creo la scene y el stage
            Scene scene = new Scene(root1);
            Stage stage = new Stage();

            // Asocio el stage con el scene
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("AGREGAR PELEA");
            stage.getIcons().add(new Image(this.getClass().getResource("/Reportes/Gallo 1.jpg").toString()));
            stage.show();
            tblPeleas.refresh();

        } catch (IOException ex) {
            //Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
