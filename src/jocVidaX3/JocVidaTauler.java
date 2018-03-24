/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jocVidaX3;

import java.util.Scanner;

/**
 *  Esta clase modela un juego de la vida en un tablero de dos 
 * dimensiones.
 * @author juan
 */
class JocVidaTauler {
    
    private String[][] old;
    private String[][] nou;
    private int F;
    private int C;
    private int CE;
    public int borns=0;
    public int deaths=0;
    public int generation=1;

    /**
     * Metodo constructor que tiene en cuenta el número de células 
     * (para una posible futura expansión del programa y que lo 
     * requiera).
     * @param f es el número de filas
     * @param co es el número de columnas
     * @param ce es el número de células
     */
    public JocVidaTauler(int f,int co,int ce) {
        F=f;
        C=co;
        CE=ce;
        old=new String[F][C];
        nou=new String[F][C];
        for (int i = 0; i < F; i++) {
            for (int j = 0; j < C; j++) {
                old[i][j]=" ";
                nou[i][j]=" ";
            }
        }
    }

    /**
     * Metodo constructor sencillo que no tiene en cuenta número de 
     * células.
     * @param f es el número de filas.
     * @param c es el número de columnas.
     */
    public JocVidaTauler(int f, int c) {
        F=f;
        C=c;
        old=new String[F][C];
        nou=new String[F][C];
        for (int i = 0; i < F; i++) {
            for (int j = 0; j < C; j++) {
                old[i][j]=" ";
                nou[i][j]=" ";
            }
        }
    }

    /**
     * Metodo que realiza una jugada. Una jugada consiste en que nazcan y/o 
     * mueran células segun las reglas del juego. Si una casilla esta vacia y 
     * esta rodeada de 3 celulas, entonces nace una célula nueva en esa casilla.
     * Si la casilla esta ocupada por una célula y o bien esta rodeada de 4 o 
     * más celulas o bien de 1 o de ninguna célula, la célula de esa casilla 
     * muere.
     */
    public String jugar() {
        copiar();
        for (int i = 0; i < F; i++) {
            
            for (int j = 0; j < C; j++) {
                int contador=0;
                if (j!=C-1){
                    if (old[i][j+1].equalsIgnoreCase("X")){
                        contador++;
                    }
                }
                if (j!=C-1 && i!=F-1) {
                    if (old[i+1][j+1].equalsIgnoreCase("X")) {
                        contador++;
                    }
                }
                if (i!=F-1){
                    if (old[i+1][j].equalsIgnoreCase("X")) {
                        contador++;
                    }
                }
                if (i!=F-1 && j!=0) {
                    if (old[i+1][j-1].equalsIgnoreCase("X")) {
                        contador++;
                    }
                }
                if (j!=0){
                    if (old[i][j-1].equalsIgnoreCase("X")) {
                        contador++;
                    }
                }
                if (j!=0 && i!=0){
                    if (old[i-1][j-1].equalsIgnoreCase("X")){
                        contador++;
                    }
                }
                if (i!=0) {
                    if (old[i-1][j].equalsIgnoreCase("X")) {
                        contador++;
                    }
                }
                if (i!=0 && j!=C-1) {
                    if (old[i-1][j+1].equalsIgnoreCase("X")) {
                        contador++;
                    }
                }
                if (old[i][j].equalsIgnoreCase(" ") && contador==3) neix(i,j);
                if (old[i][j].equalsIgnoreCase("X") && (contador>=4 || contador<=1)) mort(i,j);    
                
            }
        }
        String tauler=toString();
        generation++;
        return tauler;
    }

    /**
     * Metodo que genera nuevas células en el tablero a partir de 
     * ingresarle al metodo las coordenadas de la casilla donde nacerá 
     * la nueva célula. 
     * @param f es la fila donde se generará la nueva célula.
     * @param c es la columna donde se generará la célula nueva.
     */
    public void neix(int f, int c) {
        nou[f][c]="X";
        borns++;
    }

    /**
     * Metodo que en esta clase devolverá el estado del tableró de juego,
     * con la posición de las células, mostradas como cruces. 
     * @return el tablero.
     */
    @Override
    public String toString(){
        
        String main="";
        for (int i = 0; i < F; i++) {
            for (int j = 0; j < C; j++) {
                main=main+"|"+nou[i][j];
                
            }
            main=main+"|"+"\r\n";
        }
        return main;
    }
        
    public String toStringX(){
        
        String main="";
        for (int i = 0; i < F; i++) {
            for (int j = 0; j < C; j++) {
                main=main+nou[i][j];
        
           
            }
        
        }
        return main;
    }

    /**
     * Copia el contenido del teblero mostrado por pantalla a uno 
     * temporal para poder realizar correctamente la jugada. Antes de 
     * ralizar un movimiento o jugada, se deberia invocar este metodo. De hecho, 
     * por este motivo, este metodo esta insertado en metodo jugar 
     * (el que realiza la jugada). 
     */
    public void copiar() {
        for (int i = 0; i < F; i++) {
            for (int j = 0; j < C; j++) {
                old[i][j]=nou[i][j];
            }
        }
    }

    /**
     * Este metodo mata a la célula de una determinada casilla a partir 
     * de indicar sus coordenadas.
     * @param f es la fila de la casilla correspondiente.
     * @param c es la columna de la casilla correspondiente.
     */
    public void mort(int f, int c) {
        nou[f][c]=" ";
        deaths++;
    }

    /**
     * Comprueba si una casilla esta ocupada o no por una célula.
     * @param f es la fila correspondiente.
     * @param c es la columna correspondiente.
     * @return 
     */
    public boolean ocupado(int f, int c) {
        boolean ocupado;
        if (nou[f][c].equalsIgnoreCase("X")) {
            ocupado=true;
        }else{
            ocupado=false;
        }
        return ocupado;
    }
}
