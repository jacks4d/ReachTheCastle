import java.util.Random;
import java.util.Scanner;

public class Monster {
    private int x, y;
    private final String image;

    public Monster(int x, int y, String image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }


    public String getImage() {
        return image;
    }

    public boolean taskMonster(Random random, Scanner scanner, int difficulty) {
        int a = random.nextInt(1000 * difficulty / 5);
        int b = random.nextInt(1000 * difficulty / 5);
        int trueAnswer = a + b;
        System.out.println("Реши пример: " + a + " + " + b + " = ?");
        int ans = scanner.nextInt();
        if (trueAnswer == ans) {
            System.out.println("Верно! Ты победил монстра");
            return true;
        } else {
            System.out.println("Ты проиграл эту битву!");
            return false;
        }
    }

    public int getY() {
        return y;
    }


    public int getX() {
        return x;
    }

}
