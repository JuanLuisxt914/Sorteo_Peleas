package controlador;

import conexiondb.ConexionMySQL;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Evento;
import modelo.Gallo;
import modelo.Partido;
import modelo.Pelea;

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
        ////////////////////////////////////////////////////////////////////////
        
        partidos = FXCollections.observableArrayList();
        
        

    }    
    
    public void initAtributes(Evento evento){
        this.evento= evento;
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
//            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(true);
            stage.setTitle("Registro");
            stage.show();

             //Indico que debe hacer al cerrar
            stage.setOnCloseRequest(e -> controlador.closeWindows());

             //Ciero la ventana donde estoy
            Stage myStage = (Stage) this.btnRegistro.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            //Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
 
    @FXML
    private void Sortear(ActionEvent event) throws SQLException {
             
        ObtenerPartidos();
        
        if(rbRondas.isSelected()){
            System.out.println("RONDAS ALV");
            
            if (VerificarCantidaGallos()) {
                System.out.println("Todo cool");
                Rondas rondas = ObtenerListaGallosRondas();
                //prueba(rondas);
                
                
                
                
                
                for (int i = 0; i < 100; i++) {
                    ArrayList <Peleas>Peleas = new ArrayList<Peleas>();
                    for (Ronda r: rondas.getRonda()) {
                        Peleas p = PeleasSorteo(r.getGallos());
                        p.setRonda(r.getIdRonda());
                        Peleas.add(p);
                    }
                    int GB=0;
                    for(Peleas g: Peleas)
                        GB=GB+g.getGallosBotados().size();
                    if (GB==0) {
                        
                        System.out.println("PELEAS**************");
                        System.out.println("INTENTOS: "+i);
                        break;
                    }
                    
                    if (i==99) {
                        
                    }
                }               
            } 
        }else{
            System.out.println("\nALEATORIO ALV\n");
            ArrayList<Gallo> gallos = ObtenerListaGallosAleatorio();       
            PeleasSorteo(gallos);
            
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
        int tol=80;
        int c=1;
        for(Integer x: lista1){

            for(Integer y :lista2){
                
                if (!gallos.get(x).isMatch() && !gallos.get(y).isMatch() ) { //Si no tiene match compara el peso
                    
                    if (gallos.get(x).getPeso()<= gallos.get(y).getPeso()+tol && gallos.get(x).getPeso()>= gallos.get(y).getPeso()-tol) {
                        
                        if (gallos.get(x).getIdPartido()!= gallos.get(y).getIdPartido()) {// Verificar los amigos
                            boolean amigos = true;
                            
                            for(Partido p :gallos.get(x).getAmigos()){
                                if(p.getNumPartido()== gallos.get(y).getIdPartido())
                                    amigos = false;                                   
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
    
    public void prueba(Rondas rondas){
        //prueba
        System.out.println("\n Rondas \n");
        for(Ronda x: rondas.getRonda()){
            System.out.println("Ronda: "+x.getIdRonda());
            for(Gallo gx : x.getGallos()){
                System.out.println("id: "+gx.getId());
                System.out.println("anillo: "+gx.getAnillo());
                System.out.println("partido: "+gx.getPartido());
            }
            System.out.println("");
        }
    }
    
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
            Gallo prueba = new Gallo ();
            for(Partido p : partidos){
                numAleatorio= ObtenerNumeroAleatorio(p.getGallos(), modalidad);                
                p.getGallos().get(numAleatorio).setMatch(true);
                p.getGallos().get(numAleatorio).setIdPartido(p.getIdPartido());       
                r.getGallos().add(p.getGallos().get(numAleatorio));
            }            
            rondas.getRonda().add(r);
            numRonda++;      
        }  
        return rondas;
    }
    
    public ArrayList<Gallo> ObtenerListaGallosAleatorio(){
        ArrayList<Gallo>gallos = new ArrayList<Gallo>();
            for(Partido p:partidos){
                for(Gallo g : p.getGallos()){
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
    
    public class Rondas{
        private ArrayList<Ronda> ronda= new ArrayList<Ronda>();

        public Rondas() {
        }

        public ArrayList<Ronda> getRonda() {
            return ronda;
        }

        public void setRonda(ArrayList<Ronda> ronda) {
            this.ronda = ronda;
        }
        
    }
    
    public class Ronda{
        private int idRonda;
        private ArrayList<Gallo> gallos = new ArrayList<Gallo>();

        public Ronda(int idRonda) {
            this.idRonda = idRonda;
        }
        
        public int getIdRonda() {
            return idRonda;
        }

        public void setIdRonda(int idRonda) {
            this.idRonda = idRonda;
        }

        public ArrayList<Gallo> getGallos() {
            return gallos;
        }

        public void setGallos(ArrayList<Gallo> gallos) {
            this.gallos = gallos;
        }      
    }
    
    public class Pelea{
        private int numRonda;
        private int numPelea;
        private Gallo gallo1;
        private Gallo gallo2;

        public Pelea(int numRonda, int numPelea, Gallo gallo1, Gallo gallo2) {
            this.numRonda = numRonda;
            this.numPelea = numPelea;
            this.gallo1 = gallo1;
            this.gallo2 = gallo2;
        }

        public int getNumRonda() {
            return numRonda;
        }

        public void setNumRonda(int numRonda) {
            this.numRonda = numRonda;
        }

        public int getNumPelea() {
            return numPelea;
        }

        public void setNumPelea(int numPelea) {
            this.numPelea = numPelea;
        }

        public Gallo getGallo1() {
            return gallo1;
        }

        public void setGallo1(Gallo gallo1) {
            this.gallo1 = gallo1;
        }

        public Gallo getGallo2() {
            return gallo2;
        }

        public void setGallo2(Gallo gallo2) {
            this.gallo2 = gallo2;
        }    
    }
   
    public class Peleas{
        private int ronda=0;
        private ArrayList<Pelea> peleas;
        private ArrayList<Gallo> gallosBotados;

        public Peleas(ArrayList<Pelea> peleas, ArrayList<Gallo> gallosBotados) {
            this.peleas = peleas;
            this.gallosBotados = gallosBotados;
        }

        public int getRonda() {
            return ronda;
        }

        public void setRonda(int ronda) {
            this.ronda = ronda;
        }

        
        public ArrayList<Pelea> getPeleas() {
            return peleas;
        }

        public void setPeleas(ArrayList<Pelea> peleas) {
            this.peleas = peleas;
        }

        public ArrayList<Gallo> getGallosBotados() {
            return gallosBotados;
        }

        public void setGallosBotados(ArrayList<Gallo> gallosBotados) {
            this.gallosBotados = gallosBotados;
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
