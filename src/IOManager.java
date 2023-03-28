import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Random;
import java.util.Scanner;

public class IOManager {
    static Scanner in = new Scanner(System.in);
    static Random rand = new Random();
    static PrintStream printcli = new PrintStream(System.out, true, StandardCharsets.UTF_8);
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
            case 1 -> printcli.print(MessageFormat.format("{0}{1}{2}", firsthalf, Strings.ProductName[id], secondhalf));
            case 2 -> printcli.println(MessageFormat.format("{0}{1}{2}", firsthalf, Strings.ProductName[id], secondhalf));
            case 3 -> {
                printcli.print(MessageFormat.format("{0}{1}{2} [>]", firsthalf, Strings.ProductName[id], secondhalf));
                in.skip("\r\n|\r|\n");
                in.nextLine();
            }
        }
    }
    static void Clear(){
        System.out.print("\033\143");
    }
    static String Input(){
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
