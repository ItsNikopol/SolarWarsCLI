import java.util.Objects;

public class Scenario {
    static StorageManager inv = new StorageManager();
    static int value1, value2, available, days;
    static boolean inputproblem;
    static String answer;
    static byte pirates = 3;
    static byte planet = 1;
    public static void scenario(int dayscnt){
        days = dayscnt;
        IOManager.Out(Main.Strings.getString("HelpMessage"),3);
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
            switch (IOManager.Input(2)) {
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
                case "set","settings" -> Main.settings(false);
                case "q","quit" -> {
                    IOManager.Out(Main.Strings.getString("ExitMessage"),2);
                    if (Objects.equals(IOManager.Input(7), "Y")){
                        System.exit(0);
                    }
                }
                default -> IOManager.Out(Main.Strings.getString("InvalidInputHelp"), 3);
            }
        }
		IOManager.Out(Main.Strings.getString("End"), inv.getData(8),false,3);
		
    }
    static void buy(){
        if (inv.getData(1) == inv.calculateOccupied()){
            IOManager.Out(Main.Strings.getString("FullStorage"), 3); return;
        }
        IOManager.Out(Main.Strings.getString("Buy"),2);
        value1 = IOManager.Input(4,1,8);
        if (value1 == -1) {
            inputproblem = true;
            return;
        } else if (inv.getPrice(value1) == 0){
            IOManager.Out(Main.Strings.getString("NotAvailable"),3);
            return;
        }
        value2 = value1;
        available = inv.getData(4)/inv.getPrice(value1);
        IOManager.Out(Main.Strings.getString("Price"), value1,inv.getPrice(value1),2);
        IOManager.Out(Main.Strings.getString("AvailableToBuy"),available,false,2);
        IOManager.Out(Main.Strings.getString("HowManyToBuy"),2);
        value1 = IOManager.Input(3,0,available);
        if (value1 == -1) {
            inputproblem = true;
        } else if (value1 > available) {
            IOManager.Out(Main.Strings.getString("NotEnoughMoney"), value1,true, 3);
        } else if (value1 > (inv.getData(1) - inv.calculateOccupied())) {
            IOManager.Out(Main.Strings.getString("NotEnoughStorage"),3);
        } else {
            inv.add(value2, value1,true);
        }
    }
    static void sell(){
        IOManager.Out(Main.Strings.getString("Sell"),2);
        value1 = IOManager.Input(4,1,8);
        value2 = value1;
        if (value1 == -1) {
            inputproblem = true;
            return;
        }
        if (inv.getPrice(value1) == 0){
            IOManager.Out(Main.Strings.getString("NotAvailable"),3);
            return;
        }
        IOManager.Out(Main.Strings.getString("Price"), value1,inv.getPrice(value1),2);
        IOManager.Out(Main.Strings.getString("AvailableToSell"),inv.getStorage(value1),false,2);
        IOManager.Out(Main.Strings.getString("HowManyToSell"),2);
        value1 = IOManager.Input(3,0,inv.getStorage(value1));
        if (value1 == -1) {
            inputproblem = true;
        }
        else {
            inv.remove(value2, value1,true);
        }
    }
    static void warp(){
        if (inv.getStorage(1)<inv.getData(2)){
            IOManager.Out(Main.Strings.getString("NotEnoughFuel"),inv.getData(2),false,3);
            return;
        }
        IOManager.Out(Main.Strings.getString("WhereTo"),2);
        value1 = IOManager.Input(4,1,6);
        if (value1 == -1) {
            inputproblem = true;
            return;
        } else if (value1 == planet){
            IOManager.Out(Main.Strings.getString("SamePlanet"),3);
            return;
        }
        days--;
        inv.tick();
        inv.remove(1, inv.getData(2), false);
        planet = (byte) value1;
        inv.refreshPrices(planet);
        events();
        pirates = 3;
        switch (value1) {
            case 5 -> {
                if (inv.getData(4)>10000) {
                    IOManager.Out(Main.Strings.getString("ExpandStorage"),2);
                    if (Objects.equals(IOManager.Input(7), "Y")) {
                        inv.modifyShip(1);
                    }
                }
            }
            case 4 -> {
                if (inv.getData(4)<10000) break;
                if (inv.getData(3) == 0) {
                    IOManager.Out(Main.Strings.getString("BuyGun"),2);
                    if (Objects.equals(IOManager.Input(7), "Y")) {
                        inv.modifyShip(2);
                    }
                }
            }
        }
    }
    static void corporation(){
        if (planet != 1) {
            IOManager.Out(Main.Strings.getString("CorpAnotherPlanet"),3);
            return;
        }
        IOManager.Out(Main.Strings.getString("WhatToDo"),2);
		IOManager.Out(Main.Strings.getString("CorpActions"),2);
        IOManager.Out(Main.Strings.getString("CorpRate"),2);
        answer = IOManager.Input(6);
        switch (answer){
            case "r","repay","1" -> {
                IOManager.Out(Main.Strings.getString("Repay"),2);
                value1 = IOManager.Input(3,0,inv.getData(6));
                if (value1 == -1) inputproblem = true;
				else if (value1 >inv.getData(4)) {
					IOManager.Out(Main.Strings.getString("NotEnoughMoney"),3);
				} else {
                    inv.modifyFin(1, value1);
                }
            }
            case "b","borrow","2" -> {
                if (inv.getData(5) == 1){
                    IOManager.Out(Main.Strings.getString("CorpLock"),3);
                    return;
                }
                IOManager.Out(Main.Strings.getString("Borrow"),2);
                value1 = IOManager.Input(3,0,10000);
                inv.modifyFin(2, value1);
            }
        }
    }
    static void bank(){
        IOManager.Out(Main.Strings.getString("WhatToDo"),2);
		IOManager.Out(Main.Strings.getString("BankActions"),2);
        IOManager.Out(Main.Strings.getString("BankRate"),2);
        switch (IOManager.Input(6)){
            case "d","deposit","1" -> {
                IOManager.Out(Main.Strings.getString("Deposit"),2);
                value1 = IOManager.Input(3,0,inv.getData(4));
                if (value1 == -1) {
                    inputproblem = true;
                } else {
                    if (planet != 1) {
                        IOManager.Out(Main.Strings.getString("Charge"), 3);
                        inv.modifyFin(5, value1);
                    }
                    inv.modifyFin(3, value1);
                }
            }
            case "w","withdraw","2" -> {
                IOManager.Out(Main.Strings.getString("Withdraw"),2);
                value1 = IOManager.Input(3,0,inv.getData(7));
                if (value1 == -1) {
                    inputproblem = true;
                } else {
                    if (planet != 1) {
                        IOManager.Out(Main.Strings.getString("Charge"), 3);
                        inv.modifyFin(6, value1);
                    }
                    inv.modifyFin(4, value1);
                }
            }
        }
    }
    static void events(){
        /*
        In order to test events, you can enter event maunally by replacing code in the switch.
        ===
        IOManager.Input(0,1,20);
        ===
        Original code:
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
                    switch (IOManager.Input(6)) {
                        case "a" -> {
                            if (inv.getData(3) == 1) {
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
							IOManager.Out(Main.Strings.getString("PiratesLose"),3);
							break;
						}
						else if (pirates > 0){
							IOManager.Out(Main.Strings.getString("PiratesOnChase"),3);
						}
						if (pirates == 0){
							value2 = IOManager.rand.nextInt(1,8);
							value1 = IOManager.rand.nextInt(3,20);
							inv.add(value2, value1,false);
							IOManager.Out(Main.Strings.getString("PiratesLoot"), value2, value1,3);
						}
					}
                    
                }
            }
            case 3 -> {
                value1 = IOManager.rand.nextInt(1,8);
                IOManager.Out(Main.Strings.getString("Underproduction"), value1,true,3);
                inv.manipulate(value1, true);
            }
            case 4 -> {
                value1 = IOManager.rand.nextInt(1,8);
                IOManager.Out(Main.Strings.getString("Overproduction"), value1,true,3);
                inv.manipulate(value1, false);
            }
            case 5 -> {
                IOManager.Out(Main.Strings.getString("AllDemand"),3);
                inv.manipulate(true);
            }
            case 6 -> {
                value2 = IOManager.rand.nextInt(1,8);
                value1 = IOManager.rand.nextInt(3,5);
                IOManager.Out(Main.Strings.getString("FoundItem"), value2,true,3);
                inv.add(value2, value1,false);
            }
            case 7 -> {
                IOManager.Out(Main.Strings.getString("SalvageShip"),3);
                inv.modifyShip(1);
            }
        }
    }
}
