import java.text.MessageFormat;
import java.util.Arrays;
import java.util.stream.IntStream;

public class StorageManager {
    int[] Storage = new int[9];
    int[] Prices = new int[9];
    int money = 0, debt = 0, savings = 0, capacity = 80;
    boolean gun = false;
    void refreshPrices(int mode){
        Prices[1] = IOManager.rand.nextInt(100,2000);
        Prices[2] = IOManager.rand.nextInt(5000,30000);
        Prices[3] = IOManager.rand.nextInt(10,300);
        Prices[4] = IOManager.rand.nextInt(4000,11000);
        Prices[5] = IOManager.rand.nextInt(1500,4000);
        Prices[6] = IOManager.rand.nextInt(100,3000);
        if (mode == 4){
            Prices[7] = 0;
        }
        else {
            Prices[7] = IOManager.rand.nextInt(2000,5000);
        }
        Prices[8] = IOManager.rand.nextInt(100,2000);
        if (mode == 2){
            Prices[8] = (int) (Prices[7] * 1.2);
        }
    }
    int calculateOccupied(){
        return Arrays.stream(Storage, 0, 8).sum();
    }
    void printTable(){
        IntStream.range(1, 9).forEach(i ->
                System.out.println(MessageFormat.format(
                                "ID{0} [{1}] {2} {3,number,#}$",
                                i,
                                Storage[i],
                                Strings.ProductName[i-1],
                                Prices[i]
                )));
        System.out.print(MessageFormat.format("C: {0,number,#}$ | D: {1,number,#}$ | S: {2,number,#}$ | {3}/{4}", money, debt, savings, calculateOccupied(), capacity));
        System.out.println(gun ? " | Gun installed." : "");
    }
    void add(int item, int amount, boolean payment){
        Storage[item] += amount;
        if (payment) money -= Prices[item] * amount;
    }
    void remove(int item, int amount, boolean payment){
        Storage[item] -= amount;
        if (payment) money += Prices[item] * amount;
    }
    void clear(){
        IntStream.range(1, 9).forEach(i -> Storage[i] = 0);
    }
    void tickVaults(){
        debt *= 1.160;
        savings *= 1.120;
    }
}
