
package modelo;

import java.util.ArrayList;

public class Peleas{
        private int ronda=0;
        private ArrayList<Pelea> peleas;
        private ArrayList<Gallo> gallosBotados;

        public Peleas(ArrayList<Pelea> peleas, ArrayList<Gallo> gallosBotados) {
            this.peleas = peleas;
            this.gallosBotados = gallosBotados;
        }

        public Peleas() {}

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