public class Main {
    public static void main(String[] args) {
        IOManager.Out(Strings.Logo,2);
        IOManager.Out(Strings.Name,2);
        IOManager.Out(Strings.Modes,2);
        switch (IOManager.InputNew(0)){
            case "1" -> Scenario.scenario(60);
            case "2" -> Scenario.scenario(25);
            case "3" -> System.exit(0);
            default -> IOManager.Out(Strings.InvalidInput,3);
        }
    }
}