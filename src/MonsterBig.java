import java.util.Random;
import java.util.Scanner;

public class MonsterBig {
private int x, y;
private final int id;
private String image;
    public MonsterBig(int x, int y, int id, String image){
        this.x = x;
        this.y = y;
        this.image = image;
        this.id = id;
    }
    public MonsterBig(int x, int y, int id){
        this(x, y, id,"\uD83D\uDC7A");
    }

    public String getImage() {
        return image;
}

    public int getId() {
        return id;
    }

    public boolean taskMonster(Random random, Scanner scanner, int difficulty){
        int a = random.nextInt(1000 * difficulty / 5);
        int b = random.nextInt(1000 * difficulty / 5);
        int trueAnswer = a * b;
        System.out.println("Реши пример: " + a + " * " + b + " = ?");
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
