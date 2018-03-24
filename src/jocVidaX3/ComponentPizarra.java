 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jocVidaX3;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This class will model a JPanel modified for our purpose. This class will 
 * model the game 's tamble with the cells (células) in the place they must be.
 * @author Juan Ignacio Avendaño Huergo
 */
public class ComponentPizarra extends JPanel {

    private String jvtRecibido = "";
    private int fRecibido = 1;
    private int cRecibido = 1;
    public int nextClicks = 0;
    private int tamX;
    private int tamY;
    private Image bacteria;

    /**
     * Program 's component which paints and will paint table received by the 
     * dibujar()(paint() in spanish) method by String.
     *
     * @param g An object needed for painting.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //this will be shown only when the program was just started and before the game begins. 
        if (nextClicks == 0) {
            Image imagen2=new ImageIcon(getClass().getResource("Imagen2.png")).getImage();
            //g.drawImage(imagen2,30,50,350,200,null);
            if (getWidth()<430){
                g.drawImage(imagen2,5,30,getWidth(),getHeight()-100,null);
            }else{
                g.drawImage(imagen2,0,30,getWidth(),getHeight()-30,null);  
            }

        // this will be shown during the game and will refresh at every move.   
        } else {
            
            Image bacteria = new ImageIcon(getClass().getResource("bacteria.jpg")).getImage();
            tamX = getWidth() / fRecibido;
            tamY = getHeight() / cRecibido;
            pintarGrid(g);

            for (int l = 0; l < jvtRecibido.length(); l++) {

                char a = jvtRecibido.charAt(l);
                String b = Character.toString(a);
                if (b.equalsIgnoreCase("X")){
                    if(tamX>50 || tamY<35){
                        g.drawImage(bacteria, posX(l), posY(l), tamX-tamX/3, tamY-1, null);
                    }else{
                        g.drawImage(bacteria, posX(l), posY(l), tamX-1, tamY-1, null);
                    }      
                }             
            }
        }
    }

    /**
     * Method that tells the program to paint the move. It will receive some 
     * essential data from the JocVidaX2 which the class will save as 
     * attributes. Then the method will call the repaint() method, which will 
     * call the paintComponent for painting.
     * @param JocVida Is the String containing the data of where the cells go.
     * @param f Is the number of rows the game has.
     * @param c is the number of columns the game has.
     */
    public void dibujar(String JocVida, int f, int c) {

        fRecibido = f;
        cRecibido = c;
        jvtRecibido = JocVida;
        nextClicks++;
        repaint();

    }

    /**
     * It calculates the position in the x axis of the window that the cell must
     * be located.
     *
     * @param l is the position that the cell has in a String that contains 
     * the root data for locating it (a integer number).
     * @return The position in the x axis for a cell (which is an integer number).
     */
    public int posX(int l) {

        int x = 0;
        int xx = 0;


        x=l%cRecibido; 
        

        if(tamX>50 || tamY<35){
            xx = ((tamX) * x) + tamX/5;
        }else{
        
        xx = (tamX * x)+1;
        }
        

        return xx;
        
    }

    /**
     * It calculates the position in the y axis of the window that the cell must
     * be located.
     *
     * @param l is the position that the cell has in a String that contains 
     * the root data for locating it (a integer number).
     * @return The position in the y axis for a cell (which is an integer number).
     */
    public int posY(int l) {

        int y = 0;
        int yy = 0;

        y = (l / fRecibido) + 1;
        
        /**
         if (fRecibido>16){
            yx = (tamY) * y - tamY*3/4 ;
            
        }if (fRecibido>25){
            
            yx = (tamY) * y - tamY*99/100 ;
            
        }else{
            yx = (tamY) * y - tamY*99/100 ;
        }
        **/
       
        yy=tamY*y+1-tamY;

        return yy;
    }
    
    /**
     * This method is called by the paintComponent and paints the grid for the 
     * game.
     * @param g An object needed for painting.
     */
    public void pintarGrid(Graphics g) {
        int x = 0;
        int y = 0;

        for (int i = 0; i <= fRecibido; i++) {
            
            if (i==fRecibido) {
                g.drawLine(x, 5, x, getHeight()-5);
            }else{
                g.drawLine(x+5, 5, x+5, getHeight()-5);
            }
            x = x + tamX;
        }
        for (int j = 0; j <= cRecibido; j++) {
            
            if (j==cRecibido) {
                g.drawLine(5, y, getWidth(), y);
            }else{
                g.drawLine(5, y+5, getWidth()-5, y+5);
            }
            y = y + tamY;
        }
    }
}