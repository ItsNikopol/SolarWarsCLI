package org.darkline.fontapi;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


public class FontRender {
    private InputStream font;
    private InputStream glyph;

    private int w;
    private int h;
    private BufferedImage fontpng;

    private String fontSymboList;

    public FontRender(String fontpath, String glyphspath, int w, int h){
        this.font = getClass().getResourceAsStream("/fonts/font.png");
        this.glyph = getClass().getResourceAsStream("/fonts/glyphs.txt");
        this.w=w;
        this.h=h;
        try {
            if (font != null && glyph != null) {
                this.fontpng = ImageIO.read(font);

                fontSymboList= new String(glyph.readAllBytes(), StandardCharsets.UTF_8);
            }else {
                IOException e = new FileNotFoundException( "Font not found?");
                throw e;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Font not found? Paths:");
            System.err.println(fontpath);
            System.err.println(glyphspath);
            System.exit(74);
        }
    }


    /**
     * Drawing characters on canvas with fixed font
     * @param c Character (UTF-16LE)
     * @param canvas Graphics2D for drawing
     * @param cursor int array with cursor coordinates: {x, y}
     */
    public void drawChar(char c, Graphics2D canvas, int[] cursor){
        if(fontpng.getWidth()<w || fontpng.getHeight()<h){
            canvas.setBackground(Color.WHITE);
            canvas.setColor(Color.RED);
            canvas.drawString("Unsupported font",8,8);

            throw new RuntimeException();
        }

        int s = fontSymboList.indexOf(c);
        if(s==-1){
            return;
        }
        //System.out.println(s);


        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int pixel;



                if(w*s > fontpng.getWidth() || y > fontpng.getHeight()){

                    return;
                }else {
                    pixel=fontpng.getRGB(w*s+x,y);

                }

                canvas.setColor(new Color(pixel));
                canvas.drawRect(x+(w*cursor[0]),y+(h*cursor[1]),0,0);
                canvas.setColor(Color.black);
            }
        }


    }

}
