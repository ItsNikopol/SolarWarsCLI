public class Main {
    public static void main(String[] args) {
        IOManager.Out("   _____       __                                   ",2);
        IOManager.Out("  / ___/____  / /___ _______      ______ ___________",2);
        IOManager.Out("  \\__ \\/ __ \\/ / __ `/ ___/ | /| / / __ `/ ___/ ___/",2);
        IOManager.Out(" ___/ / /_/ / / /_/ / /   | |/ |/ / /_/ / /  (__  ) ",2);
        IOManager.Out("/____/\\____/_/\\__,_/_/    |__/|__/\\__,_/_/  /____/  ",2);
        IOManager.Out("Solarwars v0.8a",2);
        IOManager.Out("[1] Normal Mode - 60 Days",2);
        IOManager.Out("[2] Blitz Mode - 25 Days",2);
        IOManager.Out("[3] Quit",2);
        IOManager.Out("[Select] >>> ",1);
        switch (IOManager.NumberedInput()){
            case 1 -> StandardScenario.scenario(60);
            case 2 -> StandardScenario.scenario(25);
            case 3 -> System.exit(0);
        }
    }
}