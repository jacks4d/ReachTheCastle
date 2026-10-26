import java.util.Random;
import java.util.Scanner;

public class MonsterBig extends Monster {

    public MonsterBig(int x, int y, String image) {
        super(x, y, image);
    }

    @Override
    public boolean taskMonster(Random random, Scanner scanner, int difficulty) {
        int a = random.nextInt(1000 * difficulty / 5);
        int b = random.nextInt(1000 * difficulty / 5);
        int z = random.nextInt(1000 * difficulty / 5);
        int trueAnswer = a + b + z;
        System.out.println("Реши пример: " + a + " + " + b + " + " + z + " = ?");
        int ans = scanner.nextInt();
        if (trueAnswer == ans) {
            System.out.println("Верно! Ты победил монстра");
            return true;
        } else {
            System.out.println("Ты проиграл эту битву!");
            return false;
        }
    }


}
