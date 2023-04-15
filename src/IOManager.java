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
                in.skip("\r\n|\r|\n");
                in.nextLine();
            }
        }
    }
    static void Out(String firsthalf, int id, String secondhalf, int mode){
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
    static String Input(int request){
        printcli.print("["+Strings.InputmodesName[request]+"] >>> ");
        return in.next();
    }
    static int Input(int request, int r1, int r2){
        printcli.print(MessageFormat.format("[{0}][{1,number,#}-{2,number,#}] >>> ", Strings.InputmodesName[request], r1, r2));
        return in.nextInt();
    }
}
