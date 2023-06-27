
package Reportes;

import javafx.collections.ObservableList;
import modelo.Partido;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class PartidosDataSource implements JRDataSource{
    
      private final Object [][] listadoPartidos;
      private int index;   
      
      public PartidosDataSource(ObservableList<Partido> partidos){
          index = -1;
         
        listadoPartidos = new Object [partidos.size()][2];
        for (int i = 0; i < partidos.size(); i++) {
              listadoPartidos[i][0] = partidos.get(i).getNumPartido();
              listadoPartidos[i][1] = partidos.get(i).getNombrePartido();
        }
      }

    @Override
    public boolean next() throws JRException {
        index++;
        return (index < listadoPartidos.length);
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object value = null;
        
        String fieldName = jrf.getName();
        
        switch(fieldName){
            case "num":
                value= listadoPartidos[index][0];
            break;
            case "partido":
                value= listadoPartidos[index][1];
            break;
        }
        return value;
    }
    
    public static JRDataSource getDataSource(ObservableList<Partido> partidos){
        return new PartidosDataSource(partidos);
    }
    
}
