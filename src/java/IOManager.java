
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Random;
import java.util.Scanner;
import java.awt.Window;
import org.darkline.*;

import javax.swing.*;


public class IOManager {
    /*
      dirty static hack
      Let's play a game: "Find-game-window"
    */
    static BreadTerm terminal = ((BreadTerm.BreadCanvas) ((JRootPane)Window.getWindows()[0].getComponents()[0]).getContentPane().getComponents()[0]).getParentTerminal();

    static Scanner in = new Scanner(terminal.getKeyboardStream(),StandardCharsets.UTF_16LE);
    static Random rand = new Random();



    static BreadIO printcli =terminal.getIO();
    static String PlanetName(int planet){
        switch (planet){
            case 1 -> {
                return Main.Strings.getString("Earth");
            }
            case 2 -> {
                return Main.Strings.getString("Mars");
            }
            case 3 -> {
                return Main.Strings.getString("Jupiter");
            }
            case 4 -> {
                return Main.Strings.getString("Saturn");
            }
            case 5 -> {
                return Main.Strings.getString("Neptune");
            }
            case 6 -> {
                return Main.Strings.getString("Pluto");
            }

        }
        return null;
    }
    static String ProductName(int product){
        switch (product){
            case 1 -> {
                return Main.Strings.getString("Fuel");
            }
            case 2 -> {
                return Main.Strings.getString("Dilithum");
            }
            case 3 -> {
                return Main.Strings.getString("Parts");
            }
            case 4 -> {
                return Main.Strings.getString("Ore");
            }
            case 5 -> {
                return Main.Strings.getString("Meds");
            }
            case 6 -> {
                return Main.Strings.getString("Food");
            }
            case 7 -> {
                return Main.Strings.getString("Weapons");
            }
            case 8 -> {
                return Main.Strings.getString("Water");
            }

        }
        return null;
    }
    static String InputModesName(int action){
        switch (action){
            case 1 -> {
                return Main.Strings.getString("Select");
            }
            case 2 -> {
                return Main.Strings.getString("Menu");
            }
            case 3 -> {
                return Main.Strings.getString("Count");
            }
            case 4 -> {
                return Main.Strings.getString("Item");
            }
            case 5 -> {
                return Main.Strings.getString("Planet");
            }
            case 6 -> {
                return Main.Strings.getString("Action");
            }
            case 7 -> {
                return Main.Strings.getString("Y/N");
            }
        }
        return null;
    }
    static void Out(String text, int mode){
        switch (mode){
            case 1 -> printcli.print(text);
            case 2 -> printcli.println(text);
            case 3 -> {
                printcli.print(text+" [Enter]");
                in.skip("\r\n|\r|\n");
                in.nextLine();
            }
        }
    }
    static void Out(String text, int id, boolean idmode, int mode){
        if (idmode) {
            switch (mode) {
                case 1 -> printcli.print(MessageFormat.format(text, ProductName(id)));
                case 2 -> printcli.println(MessageFormat.format(text, ProductName(id)));
                case 3 -> {
                    printcli.print(MessageFormat.format(text + " [Enter]", ProductName(id)));
                    in.skip("\r\n|\r|\n");
                    in.nextLine();
                }
            }
        } else {
            switch (mode) {
                case 1 -> printcli.print(MessageFormat.format(text, id));
                case 2 -> printcli.println(MessageFormat.format(text, id));
                case 3 -> {
                    printcli.print(MessageFormat.format(text + " [Enter]", id));
                    in.skip("\r\n|\r|\n");
                    in.nextLine();
                }
            }
        }
    }
    static void Out(String text, int id, int number, int mode){
        switch (mode){
            case 1 -> printcli.print(MessageFormat.format(text, ProductName(id), number));
            case 2 -> printcli.println(MessageFormat.format(text, ProductName(id), number));
            case 3 -> {
                printcli.print(MessageFormat.format(text+" [Enter]", ProductName(id), number));
                in.skip("\r\n|\r|\n");
                in.nextLine();
            }
        }
    }
    static void Clear(){
        //System.out.print("\033\143");
        printcli.clearScreen();
    }
    static String Input(int request){
        printcli.enableEcho();
        printcli.print("["+InputModesName(request)+"] >> ");
        String next = in.next();
        printcli.disableEcho();
        return next;
    }
    static int Input(int request, int r1, int r2){

        try {
            printcli.print(MessageFormat.format("[{0}][{1,number,#}-{2,number,#}] >> ", InputModesName(request), r1, r2));
            printcli.enableEcho();
            int input = Integer.parseInt(in.next());
            printcli.disableEcho();
            if (input>=r1 && input<=r2) return input;

            return -1;
        } catch (Exception ex){
            printcli.disableEcho();

            return -1;
        }
    }
}
