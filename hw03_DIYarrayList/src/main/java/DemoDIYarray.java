import java.util.*;

public class DemoDIYarray {
    public static void main(String[] args) {
        DIYarrayList <Integer> diy1 = new DIYarrayList<>(20);
        DIYarrayList diy2 = new DIYarrayList<>(20);
        List list = new DIYarrayList();
        ArrayList a =new ArrayList();

        for (int i = 0; i < 30; i++) {
            Random random = new Random();
            diy1.add(random.nextInt());
            diy2.add(random.nextInt());

        }

        diy2.addAll(diy1);
        Collections.addAll(diy2,diy2.lastIndexOf());
        Collections.copy(diy2,diy1);
        Collections.sort(diy2);
        diy2.forEach((o)-> System.out.println(o));
    }
}
