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
        IOManager.Out(Main.Strings.getString("HelpMessage"),3);
        inv.money = 10000;
        inv.debt = 10000;
        inv.refreshPrices(1);
        while (days>0) {
            IOManager.Clear();
            IOManager.Out(IOManager.PlanetName(planet),2);
            inv.printTable();
            IOManager.Out(Main.Strings.getString("DaysLeft"),days,false,2);
            if (inputproblem) {
                IOManager.Out(Main.Strings.getString("InvalidInput"),2);
                inputproblem = false;
            }
            switch (IOManager.Input(1)) {
                case "b","buy","1" -> buy();
                case "s","sell","2" -> sell();
                case "bk","bank","3" -> bank();
                case "c","corporation","4" -> corporation();
                case "w","warp","5" -> warp();
                case "ff","fastforward","6" -> {
                    IOManager.Out(Main.Strings.getString("SkipDay"),3);
                    inv.refreshPrices(planet);
                    inv.tick();
                    days--;
                }
                case "h","help" -> IOManager.Out(Main.Strings.getString("Help"),3);
                case "q","quit" -> {
                    IOManager.Out(Main.Strings.getString("ExitMessage"),2);
                    if (Objects.equals(IOManager.Input(6), "Y")){
                        System.exit(0);
                    }
                }
                default -> IOManager.Out(Main.Strings.getString("InvalidInputHelp"), 3);
            }
        }
		numberedanswer = inv.money+inv.savings-inv.debt;
		IOManager.Out(Main.Strings.getString("End"),numberedanswer,false,3);
		
    }
    static void buy(){
        if (inv.capacity == inv.calculateOccupied()){
            IOManager.Out(Main.Strings.getString("FullStorage"), 3); return;
        }
        IOManager.Out(Main.Strings.getString("Buy"),2);
        numberedanswer = IOManager.Input(3,1,8);
        if (numberedanswer == -1) {
            inputproblem = true;
            return;
        } else if (inv.Prices[numberedanswer] == 0){
            IOManager.Out(Main.Strings.getString("NotAvailable"),3);
            return;
        }
        index = numberedanswer;
        available = inv.money/inv.Prices[numberedanswer];
        IOManager.Out(Main.Strings.getString("Price"),numberedanswer,inv.Prices[numberedanswer],2);
        IOManager.Out(Main.Strings.getString("AvailableToBuy"),available,false,2);
        IOManager.Out(Main.Strings.getString("HowManyToBuy"),2);
        numberedanswer = IOManager.Input(2,0,available);
        if (numberedanswer == -1) {
            inputproblem = true;
        } else if (numberedanswer > available) {
            IOManager.Out(Main.Strings.getString("NotEnoughMoney"),numberedanswer,true, 3);
        } else if (numberedanswer > (inv.capacity - inv.calculateOccupied())) {
            IOManager.Out(Main.Strings.getString("NotEnoughStorage"),3);
        } else {
            inv.add(index,numberedanswer,true);
        }
    }
    static void sell(){
        IOManager.Out(Main.Strings.getString("Sell"),2);
        numberedanswer = IOManager.Input(3,1,8);
        index = numberedanswer;
        if (numberedanswer == -1) {
            inputproblem = true;
            return;
        }
        if (inv.Prices[numberedanswer] == 0){
            IOManager.Out(Main.Strings.getString("NotAvailable"),3);
            return;
        }
        IOManager.Out(Main.Strings.getString("Price"),numberedanswer,inv.Prices[numberedanswer],2);
        IOManager.Out(Main.Strings.getString("AvailableToSell"),inv.Storage[numberedanswer],false,2);
        IOManager.Out(Main.Strings.getString("HowManyToSell"),2);
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
            IOManager.Out(Main.Strings.getString("NotEnoughFuel"),fuelreq,false,3);
            return;
        }
        IOManager.Out(Main.Strings.getString("WhereTo"),2);
        numberedanswer = IOManager.Input(4,1,6);
        if (numberedanswer == -1) {
            inputproblem = true;
            return;
        } else if (numberedanswer == planet){
            IOManager.Out(Main.Strings.getString("SamePlanet"),3);
            return;
        }
        days--;
        inv.tick();
        inv.Storage[1] -= fuelreq;
        planet = (byte) numberedanswer;
        inv.refreshPrices(planet);
        events();
        pirates = 3;
        switch (numberedanswer) {
            case 5 -> {
                if (inv.money>10000) {
                    IOManager.Out(Main.Strings.getString("ExpandStorage"),2);
                    if (Objects.equals(IOManager.Input(6), "Y")) {
                        inv.capacity += 80;
                        fuelreq++;
                        inv.money -= 10000;
                    }
                }
            }
            case 4 -> {
                if (inv.money<10000) break;
                if (!inv.gun) {
                    IOManager.Out(Main.Strings.getString("BuyGun"),2);
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
            IOManager.Out(Main.Strings.getString("Logo"),3);
            return;
        }
        IOManager.Out(Main.Strings.getString("WhatToDo"),2);
		IOManager.Out(Main.Strings.getString("CorpActions"),2);
        IOManager.Out(Main.Strings.getString("CorpRate"),2);
        answer = IOManager.Input(5);
        switch (answer){
            case "r","repay" -> {
                IOManager.Out(Main.Strings.getString("Repay"),2);
                numberedanswer = IOManager.Input(2,0,inv.debt);
                if (numberedanswer == -1) inputproblem = true;
                else {
                    inv.money -= numberedanswer;
                    inv.debt -= numberedanswer;
                }
            }
            case "b","borrow" -> {
                if (inv.corplock){
                    IOManager.Out(Main.Strings.getString("CorpLock"),3);
                    return;
                }
                IOManager.Out(Main.Strings.getString("Borrow"),2);
                numberedanswer = IOManager.Input(2,0,15000);
                inv.debt += numberedanswer;
                inv.money += numberedanswer;
                inv.corplock = true;
            }
        }
    }
    static void bank(){
        IOManager.Out(Main.Strings.getString("WhatToDo"),2);
		IOManager.Out(Main.Strings.getString("BankActions"),2);
        IOManager.Out(Main.Strings.getString("BankRate"),2);
        switch (IOManager.Input(6)){
            case "d","deposit" -> {
                IOManager.Out(Main.Strings.getString("Deposit"),2);
                numberedanswer = IOManager.Input(2,0,inv.money);
                if (numberedanswer == -1) {
                    inputproblem = true;
                } else {
                    if (planet != 1) {
                        IOManager.Out(Main.Strings.getString("Charge"), 3);
                        inv.savings = (int) ((inv.savings + numberedanswer) * 0.7);
                    }
                    else{
                        inv.savings = inv.savings + numberedanswer;
                    }
                    inv.money = inv.money - numberedanswer;
                }
            }
            case "w","withdraw" -> {
                IOManager.Out(Main.Strings.getString("Withdraw"),2);
                numberedanswer = IOManager.Input(2,0,inv.savings);
                if (numberedanswer == -1) {
                    inputproblem = true;
                } else {
                    if (planet != 1) {
                        IOManager.Out(Main.Strings.getString("Charge"), 3);
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
        System.out.println("Awaiting debug RNG input"); numberedanswer = IOManager.Input(0,1,20);
        IOManager.rand.nextInt(1,20)
        */
        switch (IOManager.rand.nextInt(1,20)){
            case 1 -> {
                IOManager.Out(Main.Strings.getString("Wormhole"),3);
                days += 3;
            }
            case 2 -> {
                outer:
                while (pirates != 0) {
					inputproblem = false;
					IOManager.Out(Main.Strings.getString("PiratesChase"),pirates,false,2);
                    IOManager.Out(Main.Strings.getString("WhatToDo"),2);
                    IOManager.Out(Main.Strings.getString("PiratesActions"),2);
                    switch (IOManager.Input(5)) {
                        case "a" -> {
                            if (inv.gun) {
                                if (IOManager.rand.nextInt(1,20) > 10) {
                                    pirates--;
                                    IOManager.Out(Main.Strings.getString("DestroyedShip"),2);
                                } else { IOManager.Out(Main.Strings.getString("Missed"),2);}
                            } else {
                                IOManager.Out(Main.Strings.getString("NoWeapon"),3);}
                        }
                        case "r" -> {
                            if (IOManager.rand.nextInt(1,20) > 13) {
                                IOManager.Out(Main.Strings.getString("PiratesEscape"),3);
                                break outer;
                            }
                        }
                        default -> {
							IOManager.Out(Main.Strings.getString("InvalidInput"),3);
							inputproblem = true;
						}
                    }
					if (!inputproblem){
						if (IOManager.rand.nextInt(1,20) <= 2){
							inv.clear();
							inv.money = (int) (inv.money * 0.2);
							IOManager.Out(Main.Strings.getString("PiratesLose"),3);
							break;
						}
						else if (pirates > 0){
							IOManager.Out(Main.Strings.getString("PiratesOnChase"),3);
						}
						if (pirates == 0){
							index = IOManager.rand.nextInt(1,8);
							numberedanswer = IOManager.rand.nextInt(3,20);
							inv.add(index,numberedanswer,false);
							IOManager.Out(Main.Strings.getString("PiratesLoot"),index,numberedanswer,3);
						}
					}
                    
                }
            }
            case 3 -> {
                numberedanswer = IOManager.rand.nextInt(1,8);
                IOManager.Out(Main.Strings.getString("Underproduction"),numberedanswer,true,3);
                inv.manipulate(numberedanswer, true);
            }
            case 4 -> {
                numberedanswer = IOManager.rand.nextInt(1,8);
                IOManager.Out(Main.Strings.getString("Overproduction"),numberedanswer,true,3);
                inv.manipulate(numberedanswer, false);
            }
            case 5 -> {
                IOManager.Out(Main.Strings.getString("AllDemand"),3);
                inv.manipulate(true);
            }
            case 6 -> {
                index = IOManager.rand.nextInt(1,8);
                numberedanswer = IOManager.rand.nextInt(3,5);
                IOManager.Out(Main.Strings.getString("FoundItem"),index,true,3);
                inv.add(index,numberedanswer,false);
            }
            case 7 -> {
                IOManager.Out(Main.Strings.getString("SalvageShip"),3);
                inv.capacity += 80;
                fuelreq++;
            }
        }
    }
}
