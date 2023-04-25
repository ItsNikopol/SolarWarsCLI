package org.darkline;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.function.Predicate;

public class BreadIO extends InputStream implements KeyListener {
    private ArrayList<Integer> keyboardBuffer = new ArrayList<>();
    private ArrayList<Character> displayBuffer = new ArrayList<>();


    public BreadIO(){
        //128 bytes = 64 chars
        keyboardBuffer.ensureCapacity(128);
    }

    //Keyboard logic

    @Override
    public void keyTyped(KeyEvent e) {
        char typed = e.getKeyChar();


        //Converting 16-bit char to 2 bytes

        byte[] tbyte = new String(new char[]{typed}).getBytes(StandardCharsets.UTF_16LE);
        for (byte b:tbyte) {
            keyboardBuffer.add(((int) b) & 0xFF);
            keyboardBuffer.add(-1);
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public int read() throws IOException {
        while (keyboardBuffer.isEmpty()){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //yes, remove operation does return our byte!
        int b = keyboardBuffer.remove(0);


        return b;

    }

    //No streams here, we just implement print
    public void print(char c){
        System.out.print(c);
    }
    public void print(String s){
        for (char c:s.toCharArray()) {
            print(c);
        }
    }
    public void println(char c){
        print(c);
        print('\n');
    }
    public void println(String s){
        print(s);
        print('\n');
    }





}
