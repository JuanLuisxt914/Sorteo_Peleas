
package Reportes;

import javafx.collections.ObservableList;
import modelo.PeleaT;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;


public class RondaDataSource implements JRDataSource{
      private final Object [][] listadoPeleas;
      private int index;   
      
      public RondaDataSource(ObservableList<PeleaT> peleas){
          index = -1;
        listadoPeleas = new Object [peleas.size()][8]; 
        for (int i = 0; i < peleas.size(); i++) {
            listadoPeleas [i][0] = peleas.get(i).getNum();
            listadoPeleas [i][1] = peleas.get(i).getAnillo1();
            listadoPeleas [i][2] = peleas.get(i).getPeso1();
            listadoPeleas [i][3] = peleas.get(i).getPartido1();
            listadoPeleas [i][4] = peleas.get(i).getPartido2();
            listadoPeleas [i][5] = peleas.get(i).getPeso2();
            listadoPeleas [i][6] = peleas.get(i).getAnillo2(); 
            listadoPeleas [i][7] = peleas.get(i).getVs(); 
        }
      }

    @Override
    public boolean next() throws JRException {
        index++;
        return (index < listadoPeleas.length);
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object value = null;
        
        String fieldName = jrf.getName();
        
        switch(fieldName){
            case "num":
                value= listadoPeleas[index][0];
            break;
            case "anillo1":
                value= listadoPeleas[index][1];
            break;
            case "peso1":
                value= listadoPeleas[index][2];
            break;
            case "partido1":
                value= listadoPeleas[index][3];
            break;
            case "partido2":
                value= listadoPeleas[index][4];
            break;
            case "peso2":
                value= listadoPeleas[index][5];
            break;
            case "anillo2":
                value= listadoPeleas[index][6];
            break;
            case "ronda":
                value= listadoPeleas[index][7];
            break;
        }
        return value;
    }
    
    public static JRDataSource getDataSource(ObservableList<PeleaT> peleas){
        return new RondaDataSource(peleas);
    }
    
}
