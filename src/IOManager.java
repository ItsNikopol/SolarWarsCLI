import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

public class IOManager {
    static Scanner in = new Scanner(System.in);
    static Random rand = new Random();
    static PrintStream printcli = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    /*static class PlanetName{
        String 1 =
    }*/
    static String PlanetName(int planet){
        switch (planet) {
            case 1 -> {
                return "Earth";
            }
            case 2 -> {
                return "Mars";
            }
            case 3 -> {
                return "Jupiter";
            }
            case 4 -> {
                return "Saturn";
            }
            case 5 -> {
                return "Neptune";
            }
            case 6 -> {
                return "Pluto";
            }
            default -> {
                return null;
            }
        }
    }
    static String ProductName(int product){
        switch (product) {
            case 0 -> {return "Fuel";}
            case 1 -> {return "Dilithum";}
            case 2 -> {return "Holos";}
            case 3 -> {return "Ore";}
            case 4 -> {return "Meds";}
            case 5 -> {return "Food";}
            case 6 -> {return "Weapons";}
            case 7 -> {return "Water";}
            default -> {return null;}
        }
    }
    static void Out(String text, int mode){
        switch (mode){
            case 1 -> printcli.print(text);
            case 2 -> printcli.println(text);
            case 3 -> {
                printcli.print(text+" [>]");
                //in.skip("((?<!\\R)\\s)*"); seems like winterm glitches with it
                in.skip("\r\n|\r|\n"); //this is better, but both unix and win 2 in a row glitch with it
                in.nextLine();
            }
        }
    }
    static void OutPlus(String firsthalf, int id, String secondhalf, int mode){
        switch (mode){
            case 1 -> printcli.print(firsthalf+ ProductName(id)+secondhalf);
            case 2 -> printcli.println(firsthalf+ ProductName(id)+secondhalf);
            case 3 -> {
                printcli.print(firsthalf+ProductName(id)+secondhalf+" [>]");
                //in.skip("((?<!\\R)\\s)*");
                in.skip("\r\n|\r|\n");
                in.nextLine();
            }
        }
    }
    static String StringInput(){
        String out;
        try {
            in.skip("((?<!\\R)\\s)*");
            out = in.next();
        }
        catch(Exception ex){
            in.skip("((?<!\\R)\\s)*");
            Out("Invalid input.",2);
            out = "q";
        }
        return out;
    }
    static int NumberedInput(){
        int index;
        try {
            index = in.nextInt();
        }
        catch(Exception ex){
            in.skip("((?<!\\R)\\s)*");
            Out("Invalid input. Answered [0] instead.",2);
            index = 0;
        }
        if (index < 0){
            in.skip("((?<!\\R)\\s)*");
            Out("Invalid input. Answered [0] instead.",2);
            index = 0;
        }
        return index;
    }
}
