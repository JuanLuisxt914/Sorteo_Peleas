
package modelo;

import java.util.ArrayList;

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