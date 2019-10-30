import java.util.ArrayList;
import java.util.Random;

public class DemoDIYarray {
    public static void main(String[] args) {
        DIYarrayList diyArrayList = new DIYarrayList();
        System.out.println(diyArrayList.size());
        diyArrayList.add(0, new DIYarrayList<>());

        for (int i = 0; i < 30; i++) {
            Random random = new Random();
            diyArrayList.add(random.nextInt());
        }
        System.out.println(diyArrayList.isEmpty());
        diyArrayList.add(19, "some element");
        System.out.println(diyArrayList.data(19));
        diyArrayList.set(20,"la-la-la");
        diyArrayList.forEach((o)-> System.out.println(o));
        System.out.println(diyArrayList.lastIndexOf());
        diyArrayList.clear();
    }
}
