
import com.google.gson.Gson;
import org.darkline.BreadTerm;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ListResourceBundle;
import java.util.Map;

public class Main {
    static Locale locale_en_US = new Locale("en", "US");
    static Locale locale_ru_RU = new Locale("ru", "RU");
    static ResourceBundle Strings = new LocaleAdapter(locale_en_US);
    public static void main(String[] args) {
        Gson gson = new Gson();
        System.out.println();
        BreadTerm b = new BreadTerm();
        System.setIn(b.getIO());


        b.setScaleFactor(1);
        b.redraw();

        changelang();
        menu();
    }

    public static void menu() {
        IOManager.Clear();
        IOManager.Out("Solarwars v0.13a",2);
        IOManager.Out(Strings.getString("Logo"),2);
        IOManager.Out(Strings.getString("Modes"),2);
        switch (IOManager.Input(1)){
            case "1" -> Scenario.scenario(60);
            case "2" -> Scenario.scenario(25);
            case "3" -> settings(true);
            case "5" -> System.exit(0);
            default -> IOManager.Out(Strings.getString("InvalidInput"),3);
        }
    }
    public static void changelang(){
        IOManager.Out(Strings.getString("Language"),2);
        switch (IOManager.Input(1)){
            case "1" -> Strings = new LocaleAdapter(locale_en_US);
            case "2" -> Strings = new LocaleAdapter(locale_ru_RU);
            default -> IOManager.Out(Strings.getString("InvalidInput"),3);
        }
    }
    public static void settings(boolean frommenu){
        IOManager.Clear();
        IOManager.Out(Strings.getString("Settings"),2);
        switch (IOManager.Input(1)){
            case "0" -> {}
            case "1" -> changelang();
            default -> IOManager.Out(Strings.getString("InvalidInput"),3);
        }
        if (frommenu) menu();
    }
}
