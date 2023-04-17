import java.util.Locale;
import java.util.ResourceBundle;

public class Main {
    static Locale locale_en_US = new Locale("en", "US");
    static ResourceBundle Strings = ResourceBundle.getBundle("Strings", locale_en_US);
    public static void main(String[] args) {

        IOManager.Out(Strings.getString("Logo"),2);
        IOManager.Out(Strings.getString("Name"),2);
        IOManager.Out(Strings.getString("Modes"),2);
        switch (IOManager.Input(0)){
            case "1" -> Scenario.scenario(60);
            case "2" -> Scenario.scenario(25);
            case "3" -> System.exit(0);
            default -> IOManager.Out(Strings.getString("InvalidInput"),3);
        }
    }
}