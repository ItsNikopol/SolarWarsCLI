import java.text.MessageFormat;
import java.util.Arrays;
import java.util.stream.IntStream;

public class StorageManager {
    private final int[] Storage = new int[9];
    private final int[] Prices = new int[9];
    private int money = 10000, debt = 20000, savings = 0, lv = 1;
    private boolean gun = false, corplock = false;
    void refreshPrices(int mode){
        Prices[1] = IOManager.rand.nextInt(150,2000);
        Prices[2] = IOManager.rand.nextInt(5000,30000);
        Prices[3] = IOManager.rand.nextInt(100,5000);
        Prices[4] = IOManager.rand.nextInt(4000,11000);
        Prices[5] = IOManager.rand.nextInt(3000,13000);
        Prices[6] = IOManager.rand.nextInt(10,400);
        if (mode == 4){
            Prices[7] = 0;
        }
        else {
            Prices[7] = IOManager.rand.nextInt(2000,15000);
        }
        Prices[8] = IOManager.rand.nextInt(100,2000);
        Prices[IOManager.rand.nextInt(2,8)] = 0;
        Prices[IOManager.rand.nextInt(2,8)] = 0;
    }
    int calculateOccupied(){
        return Arrays.stream(Storage, 1, 9).sum();
    }
    void printTable(){
        for(int i = 1; i < 9; i++){
            if (Prices[i] == 0){
                System.out.println(MessageFormat.format(
                        "ID{0} [{1}] {2} N/A",
                        i,
                        Storage[i],
                        IOManager.ProductName(i)
                ));
            } else {
                System.out.println(MessageFormat.format(
                        "ID{0} [{1}] {2} {3,number,#}$",
                        i,
                        Storage[i],
                        IOManager.ProductName(i),
                        Prices[i]
                ));
            }
        }
        System.out.print(MessageFormat.format("C: {0,number,#}$ | D: {1,number,#}$ | S: {2,number,#}$ | {3}/{4}", money, debt, savings, calculateOccupied(), getData(1)));
        System.out.println(gun ? " | Gun" : "");
        System.out.println(MessageFormat.format("Sc: {0,number,#} | Lv: {1,number,#}", getData(8), lv));
    }
    void add(int item, int amount, boolean payment){
        Storage[item] += amount;
        if (payment) money -= Prices[item] * amount;
    }
    void remove(int item, int amount, boolean payment){
        Storage[item] -= amount;
        if (payment) money += Prices[item] * amount;
    }
    void manipulate(int item, boolean mode){
        if (mode){
            Prices[item] *= 4;
        } else {
            Prices[item] /= 3;
        }
    }
    void manipulate(boolean mode){
        if (mode){
            for(int i = 1; i < 9; i++){
                Prices[i] *= 2.5;
            }
        } else {
            for(int i = 1; i < 9; i++){
                Prices[i] /= 2.5;
            }
        }
    }
    void clear(){
        IntStream.range(1, 9).forEach(i -> Storage[i] = 0);
        money = (int) (money * 0.2);
    }
    void tick(){
        debt *= 1.125;
        savings *= 1.0625;
        corplock = false;
    }
    int getPrice(int i){
        return Prices[i];
    }
    int getStorage(int i){
        return Storage[i];
    }
    int getData(int mode){
        switch (mode){
            case 1 -> {
                // Capacity.
                return 100*lv;
            }
            case 2 -> {
                // LV, Also acts as fuel requrement.
                return lv;
            }
            case 3 -> {
                // Returns Gun status.
               if (gun){
                   return 1;
                } else {
                   return 0;
               }
            }
            case 4 -> {
                // Money.
                return money;
            }
            case 5 -> {
                // Corporation lock status.
                if (corplock){
                    return 1;
                } else {
                    return 0;
                }
            }
            case 6 -> {
                // Debt
                return debt;
            }
            case 7 -> {
                // Savings
                return savings;
            }
            case 8 -> {
                // Gets current score.
                return money+savings-debt;
            }
        }
        // This SHOULD NOT happen.
        return 0;
    }
    void modifyShip(int mode){
        switch (mode){
            case 1 -> lv++;
            case 2 -> gun = true;
        }
    }
    void modifyFin(int mode, int count){
        switch (mode){
            // Corporation
            case 1 -> {
                money -= count;
                debt -= count;
            }
            case 2 -> {
                money += count;
                debt += count;
                corplock = true;
            }
            // Bank
            case 3 -> {
                money -= count;
                savings += count;
            }
            case 4 -> {
                money += count;
                savings -= count;
            }
            // Off-plan bank
            case 5 -> {
                count = (int) (count*0.7);
                money -= count;
                savings += count;
            }
            case 6 -> {
                count = (int) (count*0.7);
                money += count;
                savings -= count;
            }
        }
    }
}
