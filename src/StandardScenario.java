import java.util.Objects;

public class StandardScenario {
    public static void scenario(int days){
        int numberedanswer;
        byte index;
        String answer;
        int available;
        byte pirates = 3;
        byte planet = 1;
        byte fuelreq = 1;
        IOManager.Out("Type [h] to see list of commands.",2);
        StorageManager inv = new StorageManager();
        inv.money = 10000;
        inv.debt = 10000;
        inv.refreshPrices(1);
        while (days>0) {
            IOManager.Out(IOManager.PlanetName(planet),2);
            inv.printTable();
            IOManager.Out(days + " days left.",2);
            IOManager.Out("[Menu] >>> ", 1);  answer = IOManager.StringInput();
            switch (answer) {
                case "b","buy" -> {
                    if (inv.capacity == inv.calculateOccupied()){
                        IOManager.Out("Storage is full.", 3); break;
                    }
                    IOManager.Out("What to buy? [0-7]",2);
                    IOManager.Out("[Buy] >>> ",1); numberedanswer = IOManager.NumberedInput();
                    if (numberedanswer>7) {
                        IOManager.Out("Invalid input.",3); break;
                    }
                    if (inv.Prices[numberedanswer] == 0){
                        IOManager.Out("No one is trading this item here.",3);
                        break;
                    }
                    index = (byte) numberedanswer;
                    available = inv.money/inv.Prices[numberedanswer];
                    IOManager.OutPlus("The price of ",numberedanswer," is "+inv.Prices[numberedanswer]+"$.",2);
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
                case "s","sell" -> {
                    IOManager.Out("What to sell? [0-7]",2);
                    IOManager.Out("[Sell] >>> ",1); numberedanswer = IOManager.NumberedInput();
                    if (numberedanswer>7) {
                        IOManager.Out("Invalid input.",3); break;
                    }
                    index = (byte) numberedanswer;
                    if (inv.Prices[numberedanswer] == 0){
                        IOManager.Out("No one is trading this item here.",3);
                        break;
                    }
                    IOManager.OutPlus("The price of ",numberedanswer," is "+inv.Prices[numberedanswer]+"$.",2);
                    IOManager.Out("You can sell up to "+ inv.Storage[numberedanswer]+".",2);
                    IOManager.Out("How many do you want to sell?",2);
                    IOManager.Out("[Count] >>> ",1);
                    numberedanswer = IOManager.NumberedInput();
                    if (numberedanswer > inv.Storage[index]) {
                        IOManager.OutPlus("You don't have enough ",index," to sell!",3);
                    }
                    else {
                        inv.remove(index,numberedanswer,true);
                    }
                }
                case "w","warp" -> {
                    if (inv.Storage[0]<fuelreq){
                        IOManager.Out("You need at least ["+fuelreq+"] Fuel to warp.",3);
                    }
                    else {
                        IOManager.Out("Where to go? [1-6]",2);
                        IOManager.Out("[Warp] >>> ",1);
                        numberedanswer = IOManager.NumberedInput();
                        if (numberedanswer>6 || numberedanswer == 0) {
                            IOManager.Out("Invalid input.",3);
                            break;
                        }
                        if (numberedanswer == planet) IOManager.Out("You can't warp on the same planet.",3);
                        else {
                            days = (byte) (days - 1);
                            inv.tickVaults();
                            inv.Storage[0] = (short) (inv.Storage[0] - fuelreq);
                            planet = (byte) numberedanswer;
                            inv.refreshPrices(planet);
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
                                        answer = IOManager.StringInput();
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
                                                    IOManager.Out("You lost them.",3);
                                                    break outer;
                                                }
                                            }
                                            default -> IOManager.Out("Invalid input.",3);
                                        }
                                        if (IOManager.rand.nextInt(1,20) <= 2){
                                            inv.clear();
                                            inv.money = (int) (inv.money * 0.2);
                                            IOManager.Out("KABOOOM! Your ship was destroyed.....",2);
                                            IOManager.Out("You see light slowly fading...",3);
                                            IOManager.Out("...",2);
                                            IOManager.Out("You wake up in good shape...",2);
                                            IOManager.Out("...And your ship is empty!",3);
                                        }
                                        else{
                                            IOManager.Out("Pirates missed.",3);
                                        }
                                    }
                                    IOManager.Out("You investigate inside a pirate ship...",3);
                                    index = (byte) IOManager.rand.nextInt(0,7);
                                    numberedanswer = IOManager.rand.nextInt(2,15);
                                    inv.add(index,numberedanswer,false);
                                    IOManager.OutPlus("Inside it, there was ",index,"!",3);
                                    IOManager.Out("You took ["+numberedanswer+"] of it.",3);
                                }
                                case 3 -> {
                                    numberedanswer = IOManager.rand.nextInt(0,7);
                                    IOManager.OutPlus("Underproduction! ",numberedanswer," prices increased!",3);
                                    inv.Storage[numberedanswer] = (short) (inv.Storage[numberedanswer] * 4);
                                }
                                case 4 -> {
                                    numberedanswer = IOManager.rand.nextInt(0,7);
                                    IOManager.OutPlus("Overproduction! ",numberedanswer," prices are lowered.",3);
                                    inv.Storage[numberedanswer] = (short) (inv.Storage[numberedanswer] / 4);
                                }
                            }
                            switch (numberedanswer) {
                                case 5 -> {
                                    if (inv.money>10000) {
                                        IOManager.Out("Do you want to expand storage for 10000$? (+80)",2);
                                        IOManager.Out("[y/n] >>> ",1);
                                        answer = IOManager.StringInput();
                                        if (Objects.equals(answer, "y")) {
                                            inv.capacity = inv.capacity + 80;
                                            fuelreq++;
                                            inv.money = inv.money - 10000;
                                        }
                                    }
                                }
                                case 4 -> {
                                    if (inv.money<10000) break;
                                    if (!inv.gun) {
                                        IOManager.Out("Do you want to buy a gun for 10000$?",2);
                                        IOManager.Out("[y/n] >>> ",1);
                                        answer = IOManager.StringInput();
                                        if (Objects.equals(answer, "y")) {
                                            inv.gun = true;
                                            inv.money = inv.money - 10000;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                case "bk","bank" -> {
                    IOManager.Out("What do you want to do? [d]eposit,[w]ithdraw",2);
                    IOManager.Out("Rate: 1.120/day",2);
                    IOManager.Out("[Bank] >>> ",1); answer = IOManager.StringInput();
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
                case "c","corporation" -> {
                    if (planet != 1) {
                        IOManager.Out("Arrive on the Earth first.",3);
                        break;
                    }
                    IOManager.Out("What do you want to do? [r]epay, [b]orrow",2);
                    IOManager.Out("Rate: 1.160/day",2);
                    IOManager.Out("[Corporation] >>> ",1); answer = IOManager.StringInput();
                    switch (answer){
                        case "r","repay" -> {
                            IOManager.Out("How much you want to repay?",2);
                            IOManager.Out("[Count] >>> ",1);
                            numberedanswer = IOManager.NumberedInput();
                            if (numberedanswer > inv.money) {
                                IOManager.Out("You don't have enough money!",3);}
                            else {
                                inv.money = inv.money - numberedanswer;
                                inv.debt = inv.debt - numberedanswer;
                            }
                        }
                        case "b","borrow" -> {
                            IOManager.Out("How much you want to borrow?",2);
                            IOManager.Out("[Count] >>> ",1);
                            numberedanswer = IOManager.NumberedInput();
                            inv.debt = inv.debt + numberedanswer;
                            inv.money = inv.money + numberedanswer;
                        }
                    }
                }
                case "ff","fastforward" -> {
                    IOManager.Out("Skipping day...",3);
                    inv.refreshPrices(planet);
                    days = (byte) (days - 1);
                    inv.debt = (int) (inv.debt * 1.140);
                    inv.savings = (int) (inv.savings * 1.120);
                }
                case "h","help" -> {
                    IOManager.Out("Basic commands:",2);
                    IOManager.Out("[b] - buy; [s] - sell; [w] - warp; [bk] - bank; [c] - corporation [ff] - fastforward; [h] - help; [q] - quit.",2);
                    IOManager.Out("You can use full command names though. For example [bank] instead of [bk].",3);
                }
                case "q","quit" -> {
                    IOManager.Out("Do you really want to exit? Progress won't be saved. [y/n]",2);
                    IOManager.Out("[Exit] >>> ",1); answer = IOManager.StringInput();
                    if (Objects.equals(answer, "y")){
                        System.exit(0);
                    }
                }
                default -> IOManager.Out("Invalid input.", 3);
            }
        }
        IOManager.Out("Time's out!",3);
        IOManager.Out("You collected "+(inv.money+inv.savings-inv.debt)+"$!",3);
        IOManager.Out("See you next time!",3);
    }
}
