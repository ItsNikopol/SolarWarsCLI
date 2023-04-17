import java.text.MessageFormat;
import java.util.Arrays;
import java.util.stream.IntStream;

public class StorageManager {
    int[] Storage = new int[9];
    int[] Prices = new int[9];
    int money = 0, debt = 0, savings = 0, capacity = 100;
    boolean gun = false, corplock = false;
    void refreshPrices(int mode){
        Prices[1] = IOManager.rand.nextInt(150,2000);
        Prices[2] = IOManager.rand.nextInt(5000,30000);
        Prices[3] = IOManager.rand.nextInt(10,400);
        Prices[4] = IOManager.rand.nextInt(4000,11000);
        Prices[5] = IOManager.rand.nextInt(3000,13000);
        Prices[6] = IOManager.rand.nextInt(100,3000);
        if (mode == 4){
            Prices[7] = 0;
        }
        else {
            Prices[7] = IOManager.rand.nextInt(2000,15000);
        }
        Prices[8] = IOManager.rand.nextInt(100,2000);
        if (mode == 2){
            Prices[8] = (int) (Prices[7] * 1.2);
        }
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
    }
    void tick(){
        debt *= 1.125;
        savings *= 1.0625;
        corplock = false;
    }
}
