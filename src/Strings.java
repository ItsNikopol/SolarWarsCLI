public class Strings {
	//Items
    static String[] PlanetName = {"", "Earth", "Mars", "Jupiter", "Saturn", "Neptune", "Pluto"};
    static String[] ProductName = {"Fuel", "Dilithum", "Holos", "Ore", "Meds", "Food", "Weapons", "Water"};
    static String[] InputmodesName = {"Select","Menu","Count","Item","Planet","Action","Y/N"};
	//Main menu
    static String Logo = "   _____       __                                   \n  / ___/____  / /___ _______      ______ ___________\n  \\__ \\/ __ \\/ / __ `/ ___/ | /| / / __ `/ ___/ ___/\n ___/ / /_/ / / /_/ / /   | |/ |/ / /_/ / /  (__  ) \n/____/\\____/_/\\__,_/_/    |__/|__/\\__,_/_/  /____/  ";
    static String Name = "Solarwars v0.10a";
    static String Help = "Game commands:\nBuying:\n[b]/[1] - Buy; [s]/[2] - Sell\nManaging money:\n[bk]/[3] - Bank; [c]/[4] - Corporation\nMoving:\n[w]/[5] - Warp to planet; [ff]/[6] - Skip day\nOther:\n[h] - Help; [q] - Quit from the game.";
    static String Modes = "[1] Normal Mode - 60 Days\n[2] Blitz Mode - 25 Days\n[3] Quit";
	//Actions
    static String DaysLeft = "{0,number,#} days left.";
	static String HelpMessage = "Type [h] to see list of commands.";
    static String SkipDay = "Skipping day...";
	static String ExitMessage = "Do you really want to exit? Progress won't be saved. [y/n]";
    static String InvalidInput = "Invalid Input.";
	static String InvalidInputHelp = "Invalid input.\nYou may want to type [h] to see list of commands.";
	static String FullStorage = "Storage is full.";
    static String NotEnoughStorage = "Not enough storage!";
	static String Buy = "What to buy?";
    static String NotAvailable = "No one is trading this item here.";
    static String Price = "The price of {0} is {1,number,#}$.";
    static String AvailableToBuy = "You can afford {0}.";
    static String AvailableToSell = "You can sell up to {0}.";
    static String HowManyToBuy = "How many do you want to buy?";
    static String HowManyToSell = "How many do you want to sell?";
	static String Sell = "What to sell?";
    static String NotEnoughMoney = "You don't have enough money to afford {0} of these!";
    static String NotEnoughItems = "You don't have enough {0} to sell!";
    static String NotEnoughFuel = "You need at least {0,number,#} fuel to warp.";
    static String WhereTo = "Where to go?";
    static String SamePlanet = "You can't warp on the same planet.";
    static String CorpLock = "The corporation will not loan you any more money today.";
    //Events
    static String ExpandStorage = "Do you want to expand storage for 10000$? (+80)";
    static String BuyGun = "Do you want to buy a gun for 10000$?";
    static String PiratesLose = "You ran out of power, and were captured.\nThe pirates took all your cargo and half of your cash.";
    static String PiratesLoot = "You investigate inside a pirate ship...\nInside it, there was {0}!\nYou took {1,number,#} of it.";
    static String PiratesEscape = "You lost them in a dust cloud.";
    static String Underproduction = "Underproduction! {0} prices increased!";
    static String Overproduction = "Overproduction! {0} on sale.";
    static String AllDemand = "New colony established. All items in demand.";
	static String FoundItem = "You found some {0} on a abandoned ship!";
    static String Wormhole = "WORMHOLE!\nYou stumbled into a wormhole and come out 3 days earlier.";
    static String SalvageShip = "You salvage a Derelict Ship. +80 Bays and Cargo!";
}
