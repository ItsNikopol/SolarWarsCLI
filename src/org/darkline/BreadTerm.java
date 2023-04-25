package org.darkline;

import java.awt.*;
import java.io.InputStream;
import javax.swing.*;

public class BreadTerm extends JFrame {
    public static final int CANVAS_WIDTH  = 640;
    public static final int CANVAS_HEIGHT = 480;

    private BreadCanvas canvas;
    private BreadIO io = new BreadIO();

    //keyboard interface here
    private InputStream keyboard = io;

    public InputStream getKeyboardStream(){
        return keyboard;
    }
    public BreadIO getIO() {
        return io;
    }


    public BreadTerm() {
        canvas = new BreadCanvas(this);
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        Container cp = getContentPane();
        cp.add(canvas);

        addKeyListener(io);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setTitle("SolarWars");
        setVisible(true);
    }

    public void redraw(){
        canvas.repaint();
    }

    public class BreadCanvas extends JPanel {

        private BreadTerm parentTerminal;
        public BreadTerm getParentTerminal(){
            return parentTerminal;
        }
        BreadCanvas(BreadTerm parentTerminal){
            this.parentTerminal=parentTerminal;
        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.white);


            g.setFont(new Font("Monospaced", Font.PLAIN, 12));
            g.drawString("type here", 10, 20);
        }

    }

}