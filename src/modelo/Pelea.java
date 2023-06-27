package modelo;

import java.util.ArrayList;

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