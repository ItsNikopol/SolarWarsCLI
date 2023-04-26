package org.darkline;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.List;
import javax.swing.*;
import org.darkline.fontapi.FontRender;
public class BreadTerm extends JFrame {
    public int CANVAS_WIDTH  = 640;
    public int CANVAS_HEIGHT = 480;



    private BreadCanvas canvas;
    private BreadIO io = new BreadIO(this);

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
        private int fontW = 8;
        private int fontH = 16;
        private FontRender f = new FontRender("/fonts/font.png","/fonts/glyphs.txt",fontW,fontH);
        public BreadTerm getParentTerminal(){
            return parentTerminal;
        }
        BreadCanvas(BreadTerm parentTerminal){
            this.parentTerminal=parentTerminal;
        }

        private BufferedImage i;




        @Override
        public void paintComponent(Graphics g) {

            i=new BufferedImage(this.getWidth(),this.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D gd = (Graphics2D) i.getGraphics();
            List<Character> displayTextBuffer = io.getDB();
            super.paintComponent(g);
            gd.setBackground(Color.BLACK);
            gd.setColor(Color.BLACK);
            gd.fillRect(0,0,i.getWidth(),i.getHeight());

            int drawingchar=0;

            for (int y = 0; y < (i.getHeight()/fontH); y++ ){
                for (int x = 0; x < (i.getWidth() / fontW); x++) {
                    if(drawingchar< displayTextBuffer.size()){
                        switch(displayTextBuffer.get(drawingchar)) {
                            case '\n':
                                y=y+1;
                                x=-1;
                                break;
                            default:
                                f.drawChar(displayTextBuffer.get(drawingchar), gd, new int[]{x, y});
                        }

                    }else{
                        break;
                    }

                    drawingchar++;
                }
            }
            g.drawImage(i,0,0,null);

        }

    }

}