/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jocVidaX3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.Timer;
import java.util.TimerTask;

/**
 * It s a game of life in window. This class has the Main method which
 * instanciates the class which simulates the game and the windows.
 *
 * @author Juan Ignacio Avendaño Huergo
 */
public class JocVidaX3 {

    private JFrame finestra;
    private JPanel panel;
    private JButton next;
    private JButton go;
    private ComponentPizarra pizarra;
    private JTextField text;
    private JocVidaTauler jvt;
    private String old;
    private String actual;
    private int celulas = 5;
    private int f = 7;
    private int c = 7;
    private int x = 50 * c;
    private int y = 60 * f;
    private String mensaje;
    //private int borns1=jvt.borns-celulas;
    private JMenuBar menu;
    private JMenuItem ayuda;
    private JMenuItem about;
    private JMenuItem exit;
    private JMenuItem start;
    private JMenuItem custom;
    private JMenuItem a;
    private JMenuItem b;
    private JMenuItem d;
    private JMenuItem statistics;
    private int modelo = 0;
    private JButton automatic;
    private Timer t;
    private JButton stop;
    private JMenuItem random;
    private boolean isAutoRunning = false;

    /**
     * Method that instanciates a JocVidaX3 Class. This method will be the
     * initializer of the program itself.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //celulas=5;
        JocVidaX3 jx = new JocVidaX3();

    }

    /**
     * Constructor method for JocVIdaX2. this will call the composite method for
     * the window and the menu bar, the method which register the controllers
     * and will set general configurations for the window.
     */
    public JocVidaX3() {

        // construim la vista
        finestra = compositeFinestra();

        menu = compositeMenu();

        // registrem els controladors amb la seva vista
        registrarControladores();

        // Configuration for window.
        finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        finestra.setSize(x, y);
        Image imagen = new ImageIcon(getClass().getResource("bacteria.jpg")).getImage();
        finestra.setIconImage(imagen);
        finestra.setLocationByPlatform(true);
        finestra.setVisible(true);

    }

    /**
     * Method which makes the main window and inserts elements to it.
     *
     * @return the Window with the elements inserted and their configurations.
     */
    public JFrame compositeFinestra() {

        //instancing objects in window.
        finestra = new JFrame("JocVidaX3!");
        pizarra = new ComponentPizarra();
        next = new JButton("Continuar");   //? como dimensiono mi boton?
        go = new JButton("Go!");
        go.setForeground(Color.blue);
        panel = new JPanel();
        JLabel label0 = new JLabel("FxC");
        text = new JTextField("7", 3);
        automatic = new JButton("Automático");
        stop = new JButton("Alto!");
        JPanel panel2 = new JPanel();

        //Configure objects
        label0.setHorizontalTextPosition(2);
        next.setEnabled(false);
        automatic.setEnabled(false);
        stop.setEnabled(false);

        //add objects to window.
        panel.add(label0, BorderLayout.WEST);
        panel.add(text, BorderLayout.CENTER);
        panel.add(go, BorderLayout.EAST);
        finestra.add(panel, BorderLayout.NORTH);
        finestra.add(pizarra, BorderLayout.CENTER);
        finestra.add(panel2, BorderLayout.SOUTH);
        panel2.add(next, BorderLayout.WEST);
        panel2.add(automatic, BorderLayout.CENTER);
        panel2.add(stop, BorderLayout.EAST);

        /**
         * etiqueta = new JLabel(); etiqueta.setIcon(new
         * ImageIcon("reniec.jpg")); etiqueta.setBounds(270,10,200,70);
         * add(etiqueta);
         *
         */
        return finestra;
    }

    /**
     * Register controllers for objects in window. This method is like a
     * intermidiate between the internal classes with the commands to be done by
     * the bottons (when we click on it) and the bottons themselfs which were
     * inicialiced in CompositeFinestra().
     */
    public void registrarControladores() {
        next.addActionListener(new ControladorNext());
        go.addActionListener(new ControladorGo());
        start.addActionListener(new ControllerStart());
        ayuda.addActionListener(new ControllerAyuda());
        exit.addActionListener(new ControllerExit());
        about.addActionListener(new controllerAbout());
        custom.addActionListener(new ControllerCustom());
        a.addActionListener(new ControllerA());
        b.addActionListener(new ControllerB());
        d.addActionListener(new ControllerD());
        statistics.addActionListener(new ControllerStatistics());
        automatic.addActionListener(new ControllerAutomatic());
        stop.addActionListener(new ControllerStop());
        random.addActionListener(new ControllerRandom());
    }

    /**
     * This method Compose the MenuBar adding its elements and items.
     *
     * @return The menuBar with his elements added.
     */
    public JMenuBar compositeMenu() {

        menu = new JMenuBar();
        JMenu archivo = new JMenu("Archivo");
        JMenu model = new JMenu("Modelo");
        ayuda = new JMenuItem("Ayuda");
        exit = new JMenuItem("Salir");
        start = new JMenuItem("Iniciar partida");
        custom = new JMenuItem("Modelo personalizado");
        JMenu select = new JMenu("Elegir modelo");
        a = new JMenuItem("Glider");
        b = new JMenuItem("Boat");
        d = new JMenuItem("Toad");
        random = new JMenuItem("Locura!");
        JMenu help = new JMenu("Ayuda");
        about = new JMenuItem("Acerca de");
        statistics = new JMenuItem("Estadisticas");

        menu.add(archivo);
        archivo.add(start);
        archivo.add(statistics);
        archivo.add(exit);

        menu.add(model);
        model.add(select);
        select.add(a);
        select.add(b);
        select.add(d);
        select.add(random);
        model.add(custom);

        menu.add(help);
        help.add(ayuda);
        help.add(about);

        finestra.setJMenuBar(menu);
        return menu;
    }

    public class ControllerRandom implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            text.setText("28");
            modelo = 4;
            go.doClick();
        }

    }

    public class ControllerStop implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            t.cancel();
            automatic.setEnabled(true);
            stop.setEnabled(false);
            isAutoRunning = false;
        }

    }

    public class ControllerAutomatic implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] speeds = {"Cada 1 segundo", "Cada Medio segundo", "Cada un cuarto de sugundo", "Cada 1 milesima de segundo"};
            Object a = JOptionPane.showInputDialog(null, "Seleccione Velocidad de cambio de generación:", "Velocidad", 3, null, speeds, speeds[1]);
            String b = a.toString();
            t = new Timer();

            TimerTask task0 = new TimerTask() {

                @Override
                public void run() {
                    next.doClick();
                    stop.setEnabled(true);
                    automatic.setEnabled(false);
                    isAutoRunning = true;
                }
            };

            if (b.equalsIgnoreCase(speeds[0])) {
                t.schedule(task0, 0, 1000);
            }
            if (b.equals(speeds[1])) {
                t.schedule(task0, 0, 500);
            }
            if (b.equals(speeds[3])) {
                t.schedule(task0, 0, 1);
            }
            if (b.equals(speeds[2])) {
                t.schedule(task0, 0, 250);
            }
        }
    }

    /**
     * Controller for the botton that starts the game (Go!).
     */
    public class ControladorGo implements ActionListener {

        /**
         * Method that contains the commands to be done by pressing the botton
         * Go!
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            stop.doClick();

            String a = text.getText();
            int b = Integer.parseInt(a);
            if (b > 28) {
                b = 28;
                a = Integer.toString(b);
                text.setText(a);
            }
            if (b < 5) {
                b = 5;
                a = Integer.toString(b);
                text.setText(a);
            }
            f = b;
            c = b;
            int xx = 50 * c;
            int yy = 62 * f;
            jvt = new JocVidaTauler(f, c);

            if (modelo == 0) {
                celulas = 5;
                jvt.neix(1, 2);
                jvt.neix(2, 3);
                jvt.neix(3, 1);
                jvt.neix(3, 2);
                jvt.neix(3, 3);
            }
            if (modelo == 1) {
                celulas = 5;
                jvt.neix(1, 1);
                jvt.neix(1, 2);
                jvt.neix(2, 1);
                jvt.neix(2, 3);
                jvt.neix(3, 2);
            }
            if (modelo == 2) {
                celulas = 6;
                jvt.neix(2, 2);
                jvt.neix(2, 3);
                jvt.neix(2, 4);
                jvt.neix(3, 1);
                jvt.neix(3, 2);
                jvt.neix(3, 3);
            }
            if (modelo == 3) {
                JOptionPane.showMessageDialog(null, "¡En los siguientes pasos solo escriba números!", "Advertencia", 2);
                String zz = JOptionPane.showInputDialog("Escriba número de Células");
                int z = Integer.parseInt(zz);
                celulas = z;
                boolean mal = true;
                //int ffb=-1;
                //int ccb=-1;

                for (int i = 0; i < celulas; i++) {
                    mal=true;
                    while (mal) {

                        String menssagef = "Seleccione fila para célula " + (i + 1);
                        String menssagec = "Seleccione columna para célula " + (i + 1);
                        //while (ffa<0 || ffa>f){
                        ArrayList filas = new ArrayList();
                        for (int j = 0; j < f; j++) {
                            filas.add(j + 1);
                        }
                        Object[] filasa = filas.toArray();
                        Object ff = JOptionPane.showInputDialog(null, menssagef, "Fila", 3, null, filasa, filas.contains(0));
                        String ffa = ff.toString();
                        int ffb = Integer.parseInt(ffa);
                        //if (ffa>f || ffa<0){
                        //JOptionPane.showMessageDialog(null,"Número incorrecto. Pruebe otra vez.","Ups...",0);
                        //}
                        //}
                        //while (cca<0 || cca>=c){
                        ArrayList columnas = new ArrayList();
                        for (int j = 0; j < c; j++) {
                            columnas.add(j + 1);
                        }
                        Object[] columnasa = columnas.toArray();
                        Object cc = JOptionPane.showInputDialog(null, menssagec, "Columna", 3, null, columnasa, columnas.contains(0));
                        String cca = cc.toString();
                        int ccb = Integer.parseInt(cca);

                        if (jvt.ocupado(ffb - 1, ccb - 1)) {
                            JOptionPane.showMessageDialog(null, "Esta posición ya esta ocupada. Pruebe otra vez.", "Ups...", 0);
                        } else {
                            jvt.neix(ffb - 1, ccb - 1);
                            mal = false;
                        }
                        //}
                    }
                }
            }
            if (modelo == 4) {
                
                String[] quantity={"No mucho (200)","Bien (300)"," Muchas (400)"," Extremo (500)"};
                Object objeto1=JOptionPane.showInputDialog(null, "Seleccione modo", "modo", 3, null, quantity, quantity[1]);
                if (objeto1.equals(quantity[0])){
                    celulas=200;
                }
                if (objeto1.equals(quantity[1])){
                    celulas = 300;
                }
                if (objeto1.equals(quantity[2])){
                    celulas = 400;
                }
                if (objeto1.equals(quantity[3])){
                    celulas = 500;
                }
                Random r = new Random();
                for (int i = 0; i < celulas; i++) {
                    jvt.neix(r.nextInt(f), r.nextInt(c));
                }
            }

            next.setEnabled(true);
            automatic.setEnabled(true);
            finestra.setSize(xx, yy);
            //next.doClick();
            pizarra.dibujar(jvt.toStringX(), f, c);

        }
    }

    /**
     * Class modeling what has to do the program by selecting the option start.
     */
    public class ControllerStart implements ActionListener {

        /**
         * Method that contains the commands to be done by pressing the option
         * Start.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            go.doClick();
        }
    }

    /**
     * Class modeling what has to do the program by selecting the option help
     * (ayuda in spanish).
     */
    public class ControllerAyuda implements ActionListener {

        /**
         * Method that contains the commands to be done by pressing the option
         * help.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String mensaje = "Bienvenido al simulador del Juego de la vida de Conway.\n\n"
                    + "Este simulador Tiene un limite de 28 Filas por columnas (FxC) y un mínimo de 7 FxC.\n\n"
                    + "Para probar rapidamente el programa, dele al botón Go! o vaya a Archivo/Nueva Partida. Verá\n que se activará el botón Continuar. Dele a este botón para generar una nueva generación. "
                    + "Verá\n que las células cambían de sitio. En realidad, no son las mismas células, sino las hijas\n de las anteriores. Por eso decimos que esta es una nueva generación.\n\n"
                    + "Seleccione el botón Automático para que el juego avance solo. Para parar el avance automatico, \n"
                    + "seleccione Alto. Si en algun momento desea que la simulación avanze más lento o más rapido, \nsimplemente pare el avance automatico y vuelva a iniciarlo. \n\n"
                    + "Vaya a Archivo(en la barra de menú)/Estadisticas para ver Estadisticas de las células.\n"
                    + "Solo podrá ver estadisticas de la partida durante una partida. Inície una partida antes\n de ver sus estadisticas...\n\n"
                    + "Seleccionando un modelo también se empieza una nueva partida.\n\n"
                    + "Salga del programa en cualquier momento cerrando la ventana o en Archivo/Salir.\n\n"
                    + "El modelo predeterminado es el de Glider. Para cambiarlo, Vaya a modelo/elegir modelo.\n"
                    + "Si desea usar un modelo manual, deberá seleccionar Modelo Personalizado en el menú Modelo.\n\n"
                    + "Consejo divertido: Visite el modelo Locura!";
            JOptionPane.showMessageDialog(null, mensaje, "Ayuda", 3);
        }
    }

    /**
     * Class modeling what has to do the program by selecting the option Exit.
     */
    public class ControllerExit implements ActionListener {

        /**
         * Method that contains the commands to be done by pressing the option
         * Exit
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    /**
     * Class modeling what has to do the program by selecting the option About.
     */
    public class controllerAbout implements ActionListener {

        /**
         * Method that contains the commands to be done by pressing the option
         * about.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Icon gimber = new ImageIcon(getClass().getResource("logo_scg.png"));
            String message = "Hecho por Juan Ignacio Avendaño Huergo para fines academicos.\n Profesor: Josep Guardiola (No es el del barça).\n Escoles Universitaries Gimbernat y Tomàs Cerdà.\n 2014";
            JOptionPane.showMessageDialog(null, message, "Acerca de", 1, gimber);
        }
    }

    /**
     * Class modeling what has to do the program by selecting the option custom.
     */
    public class ControllerCustom implements ActionListener {

        /**
         * Method that contains the commands to be done by pressing the option
         * Custom.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            modelo = 3;
            go.doClick();

        }
    }

    /**
     * Class modeling what has to do the program by selecting the option A
     * (glider).
     */
    public class ControllerA implements ActionListener {

        /**
         * Method that contains the commands to be done by pressing the option a
         * (model Glider).
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            modelo = 0;
            go.doClick();
        }
    }

    /**
     * Class modeling what has to do the program by selecting the option B
     * (Boat).
     */
    public class ControllerB implements ActionListener {

        /**
         * Method that contains the commands to be done by pressing the option b
         * (model Boat).
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            modelo = 1;
            go.doClick();
        }
    }

    /**
     * Class modeling what has to do the program by pressing the option d
     * (Toad). The program has no option c because variable c is already in use.
     */
    public class ControllerD implements ActionListener {

        /**
         * Method that contains the commands to be done by pressing the option d
         * (model Toad).
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            modelo = 2;
            go.doClick();
        }
    }

    /**
     * Class modeling what has to do the program by selecting the option
     * Statistics.
     */
    public class ControllerStatistics implements ActionListener {

        /**
         * Method that contains the commands to be done by pressing the option
         * Statistics.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            if (pizarra.nextClicks == 0) {
                JOptionPane.showMessageDialog(null, "Perdone, pero antes debe iniciar una partida.", "Ups...", 0);
            } else {

                String model = "";
                if (modelo == 0) {
                    model = "Glider";
                }
                if (modelo == 1) {
                    model = "Boat";
                }
                if (modelo == 2) {
                    model = "Toad";
                }
                if (modelo == 3) {
                    model = "Personalizado";
                }
                if (modelo == 4) {
                    model = "Locura!";
                }
                mensaje = "Geneneración " + jvt.generation + "\nMuertas: " + jvt.deaths + "\nNacidas: " + (jvt.borns - celulas) + "\nPoblación inicial: " + celulas + "\nPoblación final/actual: " + (jvt.borns - jvt.deaths) + "\n Modelo: " + model;
                JOptionPane.showMessageDialog(finestra, mensaje, "Estadisticas", 1);
            }
        }
    }

    /**
     * Class modeling what has to do the program by pressing the botton next.
     */
    public class ControladorNext implements ActionListener {

        /**
         * Method that contains the commands to be done by pressing the bottom
         * Next
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            old = jvt.toStringX();
            jvt.jugar();
            pizarra.dibujar(jvt.toStringX(), f, c);
            actual = jvt.toStringX();
            // acabo?
            if (actual.equals(old)) {
                //statistics.doClick();
                t.cancel();
            }
        }
    }
}
