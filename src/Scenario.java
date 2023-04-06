import java.util.Objects;

public class Scenario {
    static StorageManager inv = new StorageManager();
    static int numberedanswer, index, available, days = 60;
    static String answer;
    static byte pirates = 3;
    static byte planet = 1;
    static byte fuelreq = 1;
    public static void scenario(int dayscnt){
        days = dayscnt;
        IOManager.Out("Type [h] to see list of commands.",3);
        inv.money = 10000;
        inv.debt = 10000;
        inv.refreshPrices(1);
        while (dayscnt>0) {
            IOManager.Clear();
            IOManager.Out(Strings.PlanetName[planet],2);
            inv.printTable();
            IOManager.Out(dayscnt + " days left.",2);
            switch (IOManager.InputNew(1)) {
                case "b","buy","1" -> buy();
                case "s","sell","2" -> sell();
                case "bk","bank","3" -> bank();
                case "c","corporation","4" -> corporation();
                case "w","warp","5" -> warp();
                case "ff","fastforward","6" -> {
                    IOManager.Out("Skipping day...",3);
                    inv.refreshPrices(planet);
                    inv.tickVaults();
                    dayscnt--;
                }
                case "h","help" -> IOManager.Out(Strings.Help,3);
                case "q","quit" -> {
                    IOManager.Out("Do you really want to exit? Progress won't be saved. [y/n]",2);
                    if (Objects.equals(IOManager.InputNew(6), "y")){
                        System.exit(0);
                    }
                }
                default -> IOManager.Out("Invalid input.\nYou may want to type [h] to see list of commands.", 3);
            }
        }
        IOManager.Out("Time's out!",3);
        IOManager.Out("You collected "+(inv.money+inv.savings-inv.debt)+"$!",3);
        IOManager.Out("See you next time!",3);
    }
    static void buy(){
        if (inv.capacity == inv.calculateOccupied()){
            IOManager.Out("Storage is full.", 3); return;
        }
        IOManager.Out("What to buy? [0-7]",2);
        IOManager.Out("[Buy] >>> ",1); numberedanswer = IOManager.NumberedInput();
        if (numberedanswer>7) {
            IOManager.Out(Strings.InvalidInput,3); return;
        }
        if (inv.Prices[numberedanswer] == 0){
            IOManager.Out("No one is trading this item here.",3);
            return;
        }
        index = numberedanswer;
        available = inv.money/inv.Prices[numberedanswer];
        IOManager.Out("The price of ",numberedanswer," is "+inv.Prices[numberedanswer]+"$.",2);
        IOManager.Out("You can afford "+available+ ".",2);
        IOManager.Out("How many do you want to buy?",2);
        IOManager.Out("[Count] >>> ",1);
        numberedanswer = IOManager.NumberedInput();
        if (numberedanswer > available) {
            IOManager.Out("You don't have enough money to afford "+ numberedanswer + " of these!", 3);
        } else if (numberedanswer > (inv.capacity - inv.calculateOccupied())) {
            IOManager.Out("Not enough storage!",3);
        } else {
            inv.add(index,numberedanswer,true);
        }
    }
    static void sell(){
        IOManager.Out("What to sell? [0-7]",2);
        IOManager.Out("[Sell] >>> ",1); numberedanswer = IOManager.NumberedInput();
        if (numberedanswer>7) {
            IOManager.Out(Strings.InvalidInput,3); return;
        }
        index = numberedanswer;
        if (inv.Prices[numberedanswer] == 0){
            IOManager.Out("No one is trading this item here.",3);
            return;
        }
        IOManager.Out("The price of ",numberedanswer," is "+inv.Prices[numberedanswer]+"$.",2);
        IOManager.Out("You can sell up to "+ inv.Storage[numberedanswer]+".",2);
        IOManager.Out("How many do you want to sell?",2);
        IOManager.Out("[Count] >>> ",1);
        numberedanswer = IOManager.NumberedInput();
        if (numberedanswer > inv.Storage[index]) {
            IOManager.Out("You don't have enough ",index," to sell!",3);
        }
        else {
            inv.remove(index,numberedanswer,true);
        }
    }
    static void warp(){
        if (inv.Storage[0]<fuelreq){
            IOManager.Out("You need at least ["+fuelreq+"] Fuel to warp.",3);
            return;
        }
        IOManager.Out("Where to go? [1-6]",2);
        IOManager.Out("[Warp] >>> ",1);
        numberedanswer = IOManager.NumberedInput();
        if (numberedanswer>6 || numberedanswer == 0) {
            IOManager.Out(Strings.InvalidInput,3);
            return;
        }
        if (numberedanswer == planet){
            IOManager.Out("You can't warp on the same planet.",3);
            return;
        }
        days = (byte) (days - 1);
        inv.tickVaults();
        inv.Storage[0] = (short) (inv.Storage[0] - fuelreq);
        planet = (byte) numberedanswer;
        inv.refreshPrices(planet);
        events();
        switch (numberedanswer) {
            case 5 -> {
                if (inv.money>10000) {
                    IOManager.Out("Do you want to expand storage for 10000$? (+80)",2);
                    if (Objects.equals(IOManager.InputNew(6), "y")) {
                        inv.capacity = inv.capacity + 80;
                        fuelreq++;
                        inv.money -= 10000;
                    }
                }
            }
            case 4 -> {
                if (inv.money<10000) break;
                if (!inv.gun) {
                    IOManager.Out("Do you want to buy a gun for 10000$?",2);
                    if (Objects.equals(IOManager.InputNew(6), "y")) {
                        inv.gun = true;
                        inv.money -= 10000;
                    }
                }
            }
        }
    }
    static void corporation(){
        if (planet != 1) {
            IOManager.Out("Arrive on the Earth first.",3);
            return;
        }
        IOManager.Out("What do you want to do? [r]epay, [b]orrow",2);
        IOManager.Out("Rate: 1.160/day",2);
        IOManager.Out("[Corporation] >>> ",1); answer = IOManager.Input();
        switch (answer){
            case "r","repay" -> {
                IOManager.Out("How much you want to repay?",2);
                IOManager.Out("[Count] >>> ",1);
                numberedanswer = IOManager.NumberedInput();
                if (numberedanswer > inv.money) {
                    IOManager.Out("You don't have enough money!",3);}
                else {
                    inv.money -= numberedanswer;
                    inv.debt -= numberedanswer;
                }
            }
            case "b","borrow" -> {
                IOManager.Out("How much you want to borrow?",2);
                IOManager.Out("[Count] >>> ",1);
                numberedanswer = IOManager.NumberedInput();
                inv.debt += numberedanswer;
                inv.money += numberedanswer;
            }
        }
    }
    static void bank(){
        IOManager.Out("What do you want to do? [d]eposit,[w]ithdraw",2);
        IOManager.Out("Rate: 1.120/day",2);
        IOManager.Out("[Bank] >>> ",1); answer = IOManager.Input();
        switch (answer){
            case "d","deposit" -> {
                IOManager.Out("How much you want to deposit?",2);
                IOManager.Out("[Count] >>> ",1);
                numberedanswer = IOManager.NumberedInput();
                if (numberedanswer > inv.money) {
                    IOManager.Out("You don't have enough money!",3);}
                else {
                    if (planet != 1) {
                        IOManager.Out("Off-planet charge: 30%", 3);
                        inv.savings = (int) ((inv.savings + numberedanswer) * 0.7);
                    }
                    else{
                        inv.savings = inv.savings + numberedanswer;
                    }
                    inv.money = inv.money - numberedanswer;
                }
            }
            case "w","withdraw" -> {
                IOManager.Out("How much you want to withdraw?",2);
                IOManager.Out("[Count] >>> ",1);
                numberedanswer = IOManager.NumberedInput();
                if (numberedanswer > inv.savings) IOManager.Out("You don't have enough money in bank!",3);
                else {
                    if (planet != 1) {
                        IOManager.Out("Off-planet charge: 30%", 3);
                        inv.money = (int) ((inv.money + numberedanswer) * 0.7);
                    }
                    else {
                        inv.money = inv.money + numberedanswer;
                    }
                    inv.savings = inv.savings - numberedanswer;
                }
            }
        }
    }
    static void events(){
        //System.out.print("Awaiting debug RNG input (1-4, 5 and more to cancel) > "); NumberedInput()
        switch (IOManager.rand.nextInt(1,20)){
            case 1 -> {
                IOManager.Out("WORMHOLE!", 2);
                IOManager.Out("Your ship sucked into wormhole and came out 3 days earlier.",3);
                days = (byte) (days + 3);
            }
            case 2 -> {
                IOManager.Out("PIRATES!",2);
                outer:
                while (pirates != 0) {
                    IOManager.Out("Pirates are chasing you! ["+ pirates+"] ships left.",2);
                    IOManager.Out("What to do? ",1);
                    if (inv.gun) {
                        IOManager.Out("[a]ttack/",1);
                    }
                    IOManager.Out("[r]un",2);
                    IOManager.Out("[Battle] >>> ",1);
                    answer = IOManager.Input();
                    switch (answer) {
                        case "a" -> {
                            if (inv.gun) {
                                if (IOManager.rand.nextInt(1,20) > 10) {
                                    pirates--;
                                    IOManager.Out("You destroyed a ship.",3);
                                } else { IOManager.Out("Missed!",3);}
                            } else {
                                IOManager.Out("You don't have a weapon!",3);}
                        }
                        case "r" -> {
                            if (IOManager.rand.nextInt(1,20) > 13) {
                                IOManager.Out(Strings.PiratesEscape,3);
                                break outer;
                            }
                        }
                        default -> IOManager.Out(Strings.InvalidInput,3);
                    }
                    if (IOManager.rand.nextInt(1,20) <= 2){
                        inv.clear();
                        inv.money = (int) (inv.money * 0.2);
                        IOManager.Out(Strings.PiratesLose,3);
                    }
                    else{
                        IOManager.Out("Pirates missed.",3);
                    }
                }
                IOManager.Out("You investigate inside a pirate ship...",3);
                index = IOManager.rand.nextInt(0,7);
                numberedanswer = IOManager.rand.nextInt(2,15);
                inv.add(index,numberedanswer,false);
                IOManager.Out("Inside it, there was ",index,"!",3);
                IOManager.Out("You took ["+numberedanswer+"] of it.",3);
            }
            case 3 -> {
                numberedanswer = IOManager.rand.nextInt(0,7);
                IOManager.Out("Underproduction! ",numberedanswer," prices increased!",3);
                inv.Storage[numberedanswer] = (inv.Storage[numberedanswer] * 4);
            }
            case 4 -> {
                numberedanswer = IOManager.rand.nextInt(0,7);
                IOManager.Out("Overproduction! ",numberedanswer," prices are lowered.",3);
                inv.Storage[numberedanswer] = (inv.Storage[numberedanswer] / 4);
            }
        }
    }
}
