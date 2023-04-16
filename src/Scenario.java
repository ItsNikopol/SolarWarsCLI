import java.util.Objects;

public class Scenario {
    static StorageManager inv = new StorageManager();
    static int numberedanswer, index, available, days;
    static boolean inputproblem;
    static String answer;
    static byte pirates = 3;
    static byte planet = 1;
    static byte fuelreq = 1;
    public static void scenario(int dayscnt){
        days = dayscnt;
        IOManager.Out(Strings.HelpMessage,3);
        inv.money = 10000;
        inv.debt = 10000;
        inv.refreshPrices(1);
        while (days>0) {
            IOManager.Clear();
            IOManager.Out(Strings.PlanetName[planet],2);
            inv.printTable();
            IOManager.Out(Strings.DaysLeft,days,false,2);
            if (inputproblem) {
                IOManager.Out(Strings.InvalidInput,2);
                inputproblem = false;
            }
            switch (IOManager.Input(1)) {
                case "b","buy","1" -> buy();
                case "s","sell","2" -> sell();
                case "bk","bank","3" -> bank();
                case "c","corporation","4" -> corporation();
                case "w","warp","5" -> warp();
                case "ff","fastforward","6" -> {
                    IOManager.Out(Strings.SkipDay,3);
                    inv.refreshPrices(planet);
                    inv.tick();
                    days--;
                }
                case "h","help" -> IOManager.Out(Strings.Help,3);
                case "q","quit" -> {
                    IOManager.Out(Strings.ExitMessage,2);
                    if (Objects.equals(IOManager.Input(6), "Y")){
                        System.exit(0);
                    }
                }
                case "cls" -> inv.clear();
                default -> IOManager.Out(Strings.InvalidInputHelp, 3);
            }
        }
        IOManager.Out("Time's out!",3);
        IOManager.Out("You collected "+(inv.money+inv.savings-inv.debt)+"$!",3);
        IOManager.Out("See you next time!",3);
    }
    static void buy(){
        if (inv.capacity == inv.calculateOccupied()){
            IOManager.Out(Strings.FullStorage, 3); return;
        }
        IOManager.Out(Strings.Buy,2);
        numberedanswer = IOManager.Input(3,1,8);
        if (numberedanswer == -1) {
            inputproblem = true;
            return;
        } else if (inv.Prices[numberedanswer] == 0){
            IOManager.Out(Strings.NotAvailable,3);
            return;
        }
        index = numberedanswer;
        available = inv.money/inv.Prices[numberedanswer];
        IOManager.Out(Strings.Price,numberedanswer,inv.Prices[numberedanswer],2);
        IOManager.Out(Strings.AvailableToBuy,available,false,2);
        IOManager.Out(Strings.HowManyToBuy,2);
        numberedanswer = IOManager.Input(2,0,available);
        if (numberedanswer == -1) {
            inputproblem = true;
        } else if (numberedanswer > available) {
            IOManager.Out(Strings.NotEnoughMoney,numberedanswer,true, 3);
        } else if (numberedanswer > (inv.capacity - inv.calculateOccupied())) {
            IOManager.Out(Strings.NotEnoughStorage,3);
        } else {
            inv.add(index,numberedanswer,true);
        }
    }
    static void sell(){
        IOManager.Out(Strings.Sell,2);
        numberedanswer = IOManager.Input(3,1,8);
        index = numberedanswer;
        if (numberedanswer == -1) {
            inputproblem = true;
            return;
        }
        if (inv.Prices[numberedanswer] == 0){
            IOManager.Out(Strings.NotAvailable,3);
            return;
        }
        IOManager.Out(Strings.Price,numberedanswer,inv.Prices[numberedanswer],2);
        IOManager.Out(Strings.AvailableToSell,inv.Storage[numberedanswer],false,2);
        IOManager.Out(Strings.HowManyToSell,2);
        numberedanswer = IOManager.Input(2,0,inv.Storage[numberedanswer]);
        if (numberedanswer == -1) {
            inputproblem = true;
        }
        else {
            inv.remove(index,numberedanswer,true);
        }
    }
    static void warp(){
        if (inv.Storage[1]<fuelreq){
            IOManager.Out(Strings.NotEnoughFuel,fuelreq,false,3);
            return;
        }
        IOManager.Out(Strings.WhereTo,2);
        numberedanswer = IOManager.Input(4,1,6);
        if (numberedanswer == -1) {
            inputproblem = true;
            return;
        } else if (numberedanswer == planet){
            IOManager.Out(Strings.SamePlanet,3);
            return;
        }
        days--;
        inv.tick();
        inv.Storage[1] -= fuelreq;
        planet = (byte) numberedanswer;
        inv.refreshPrices(planet);
        events();
        switch (numberedanswer) {
            case 5 -> {
                if (inv.money>10000) {
                    IOManager.Out(Strings.ExpandStorage,2);
                    if (Objects.equals(IOManager.Input(6), "Y")) {
                        inv.capacity += 100;
                        fuelreq++;
                        inv.money -= 10000;
                    }
                }
            }
            case 4 -> {
                if (inv.money<10000) break;
                if (!inv.gun) {
                    IOManager.Out(Strings.BuyGun,2);
                    if (Objects.equals(IOManager.Input(6), "Y")) {
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
        IOManager.Out("Rate: 12.5%/day",2);
        answer = IOManager.Input(5);
        switch (answer){
            case "r","repay" -> {
                IOManager.Out("How much you want to repay?",2);
                numberedanswer = IOManager.Input(2,0,inv.debt);
                if (numberedanswer == -1) inputproblem = true;
                else {
                    inv.money -= numberedanswer;
                    inv.debt -= numberedanswer;
                }
            }
            case "b","borrow" -> {
                if (inv.corplock){
                    IOManager.Out(Strings.CorpLock,3);
                    return;
                }
                IOManager.Out("How much you want to borrow?",2);
                numberedanswer = IOManager.Input(2,0,15000);
                inv.debt += numberedanswer;
                inv.money += numberedanswer;
                inv.corplock = true;
            }
        }
    }
    static void bank(){
        IOManager.Out("What do you want to do? [d]eposit,[w]ithdraw",2);
        IOManager.Out("Rate: 6.25%/day",2);
        switch (IOManager.Input(6)){
            case "d","deposit" -> {
                IOManager.Out("How much you want to deposit?",2);
                numberedanswer = IOManager.Input(2,0,inv.money);
                if (numberedanswer == -1) {
                    inputproblem = true;
                } else {
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
                numberedanswer = IOManager.Input(2,0,inv.savings);
                if (numberedanswer == -1) {
                    inputproblem = true;
                } else {
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
        /*
        In order to test events, you can enter event maunally.
        System.out.print("Awaiting debug RNG input (1-4, 5 and more to cancel) > "); numberedanswer = Input(2);
        IOManager.rand.nextInt(1,20)
        */
        switch (IOManager.rand.nextInt(1,20)){
            case 1 -> {
                IOManager.Out(Strings.Wormhole,3);
                days += 3;
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
                    answer = IOManager.Input(5);
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
                index = IOManager.rand.nextInt(1,8);
                numberedanswer = IOManager.rand.nextInt(2,15);
                inv.add(index,numberedanswer,false);
                IOManager.Out(Strings.PiratesLoot,index,numberedanswer,3);
            }
            case 3 -> {
                numberedanswer = IOManager.rand.nextInt(1,8);
                IOManager.Out(Strings.Underproduction,numberedanswer,false,3);
                inv.manipulate(numberedanswer, true);
            }
            case 4 -> {
                numberedanswer = IOManager.rand.nextInt(1,8);
                IOManager.Out(Strings.Overproduction,numberedanswer,false,3);
                inv.manipulate(numberedanswer, false);
            }
            case 5 -> {
                IOManager.Out(Strings.AllDemand,3);
                inv.manipulate(true);
            }
            case 6 -> {
                index = IOManager.rand.nextInt(1,8);
                numberedanswer = IOManager.rand.nextInt(3,5);
                IOManager.Out(Strings.FoundItem,index,false,3);
                inv.add(index,numberedanswer,false);
            }
            case 7 -> {
                IOManager.Out(Strings.SalvageShip,3);
                inv.capacity += 80;
                fuelreq++;
            }
        }
    }
}
