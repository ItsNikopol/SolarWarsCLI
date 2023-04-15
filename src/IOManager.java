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
    static void Out(String text, int id, boolean idmode, int mode){
        if (idmode) {
            switch (mode) {
                case 1 -> printcli.print(MessageFormat.format(text, Strings.ProductName[id-1]));
                case 2 -> printcli.println(MessageFormat.format(text, Strings.ProductName[id-1]));
                case 3 -> {
                    printcli.print(MessageFormat.format(text + " [>]", Strings.ProductName[id-1]));
                    in.skip("\r\n|\r|\n");
                    in.nextLine();
                }
            }
        } else {
            switch (mode) {
                case 1 -> printcli.print(MessageFormat.format(text, id));
                case 2 -> printcli.println(MessageFormat.format(text, id));
                case 3 -> {
                    printcli.print(MessageFormat.format(text + " [>]", id));
                    in.skip("\r\n|\r|\n");
                    in.nextLine();
                }
            }
        }
    }
    static void Out(String text, int id, int number, int mode){
        switch (mode){
            case 1 -> printcli.print(MessageFormat.format(text, Strings.ProductName[id-1], number));
            case 2 -> printcli.println(MessageFormat.format(text, Strings.ProductName[id-1], number));
            case 3 -> {
                printcli.print(MessageFormat.format(text+" [>]", Strings.ProductName[id-1], number));
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
        try {
            printcli.print(MessageFormat.format("[{0}][{1,number,#}-{2,number,#}] >>> ", Strings.InputmodesName[request], r1, r2));
            int input = Integer.parseInt(in.next());
            if (input>=r1 && input<=r2) return input;
            return -1;
        } catch (Exception ex){
            return -1;
        }
    }
}
