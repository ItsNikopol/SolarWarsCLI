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

    int scrollState = 0;
    int maxscrollState =0;
    int manualscrollState=0;


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
        addMouseWheelListener(io);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setTitle("SolarWars");
        setVisible(true);

    }

    public void redraw(){
        canvas.repaint();
    }

    public void setScaleFactor(int sf){
        canvas.setScaleFactor(sf);
    }

    public class BreadCanvas extends JPanel {

        private BreadTerm parentTerminal;
        private int fontW = 8;
        private int fontH = 16;
        private int scaleFactor = 1;

        private int oldWidth= 0;
        private int oldHeight=0;

        private FontRender f = new FontRender("/fonts/font.png","/fonts/glyphs.txt",fontW,fontH);
        public BreadTerm getParentTerminal(){
            return parentTerminal;
        }
        BreadCanvas(BreadTerm parentTerminal){
            this.parentTerminal=parentTerminal;
        }


        private BufferedImage i;

        public void setScaleFactor(int scaleFactor){
            if(scaleFactor >= 1) {
                this.scaleFactor = scaleFactor;
            }
        }


        @Override
        public void paintComponent(Graphics g) {

            if(this.getWidth()!=this.oldWidth||this.getHeight()!=this.oldHeight){
                parentTerminal.scrollState=0;
                parentTerminal.maxscrollState=0;
                parentTerminal.manualscrollState=0;
            }
            this.oldWidth=this.getWidth();
            this.oldHeight=this.getHeight();

            i=new BufferedImage(this.getWidth()/scaleFactor,this.getHeight()/scaleFactor, BufferedImage.TYPE_INT_ARGB);
            Graphics2D gd = (Graphics2D) i.getGraphics();
            List<Character> displayTextBuffer = io.getDB();
            super.paintComponent(g);
            gd.setBackground(Color.BLACK);
            gd.setColor(Color.BLACK);
            gd.fillRect(0,0,i.getWidth(),i.getHeight());

            int drawingchar=0;

            for (int y = parentTerminal.scrollState; y < (i.getHeight()/fontH); y++ ){
                for (int x = 0; x < (i.getWidth() / fontW); x++) {
                    if(drawingchar< displayTextBuffer.size()){
                        switch(displayTextBuffer.get(drawingchar)) {
                            case '\n':
                                y=y+1;
                                x=-1;
                                if(y > (i.getHeight()/fontH)-1){
                                    parentTerminal.maxscrollState=parentTerminal.maxscrollState-1;
                                    parentTerminal.scrollState=parentTerminal.maxscrollState;

                                    //reset everything
                                    gd.fillRect(0,0,i.getWidth(),i.getHeight());
                                    y = parentTerminal.scrollState;
                                    x=Integer.MAX_VALUE;
                                    drawingchar=0;
                                }
                                break;
                            default:
                                f.drawChar(displayTextBuffer.get(drawingchar), gd, new int[]{x, y+parentTerminal.manualscrollState});
                        }

                    }else{
                        break;
                    }

                    drawingchar++;
                }
            }
            g.drawImage(i,0,0,i.getWidth()*scaleFactor,i.getHeight()*scaleFactor,null);


        }

    }

}