import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private int step = 0;
    private final int sizeBoardX;
    private final int sizeBoardY;
    private String[][] board;
    private boolean isWin = false;
    private Monster[] monsters;
    final private Map<String, String> icons;


    public Game(int sizeBoardX, int sizeBoardY, int difficulty, Random random, Person person, Castle castle, Map<String, String> icons) {
        this.sizeBoardX = sizeBoardX;
        this.sizeBoardY = sizeBoardY;
        this.icons = icons;

        this.board = new String[this.sizeBoardY][this.sizeBoardX]; // игровое поле сделанное массивом
        for (int y = 0; y < sizeBoardY; y++) {
            for (int x = 0; x < sizeBoardX; x++) {
                this.board[y][x] = "  ";
            }
        }
        // Сначала размещаем замок и игрока
        this.board[castle.getY() - 1][castle.getX() - 1] = castle.getImage();
        this.board[person.getY() - 1][person.getX() - 1] = person.getImage();

// Считаем свободные клетки
        int freeCells = 0;

        for (int y = 0; y < sizeBoardY; y++) {
            for (int x = 0; x < sizeBoardX; x++) {
                if (this.board[y][x].equals("  ")) {
                    freeCells++;
                }
            }
        }

// Количество монстров ограничиваем свободными клетками
        int countMonster = Math.min(
                freeCells,
                sizeBoardX * sizeBoardY * difficulty / 10
        );

        this.monsters = new Monster[countMonster];

        for (int i = 0; i < countMonster; i++) {
            int x;
            int y;


            do {
                x = random.nextInt(sizeBoardX);
                y = random.nextInt(sizeBoardY);
            } while (!this.board[y][x].equals("  "));


            int randomAction = random.nextInt(2);

            switch (randomAction) {
                case 0 -> {
                    this.monsters[i] = new Monster(x + 1, y + 1, icons.get("monsterSmall"));
                }
                case 1 -> {
                    this.monsters[i] = new MonsterBig(x + 1, y + 1, icons.get("monsterBig"));
                }
            }

            this.board[y][x] = this.monsters[i].getImage();
        }

        int countHeart = Math.min(
                freeCells,
                sizeBoardX * sizeBoardY * difficulty / 20
        );


        for (int i = 0; i < countHeart; i++) {
            int x;
            int y;


            do {
                x = random.nextInt(sizeBoardX);
                y = random.nextInt(sizeBoardY);
            } while (!this.board[y][x].equals("  "));


            int randomAction = random.nextInt(2);

            switch (randomAction) {
                case 0 -> {
                    this.board[y][x] = icons.get("heartSmall");
                }
                case 1 -> {
                    this.board[y][x] = icons.get("heartBig");
                }
            }

        }


    }


    void drawBoard() {
        String wallStart = " + ";
        String wallNext = "—— + ";
        for (int y = 0; y < this.sizeBoardY; y++) {
            System.out.println(wallStart + wallNext.repeat(this.sizeBoardX)); // перед каждой строкой клеток (где могут быть игрок, монстр и т.д.) выводим стену горизонтальную
            for (int x = 0; x < this.sizeBoardX; x++) {
                String leftBlock = " | ";
                System.out.print(leftBlock); // выводим сначала левую стену
                System.out.print(this.board[y][x]); // выводим то что находится в клетке по индексу массива
            }
            String rightBlock = " |";
            System.out.println(rightBlock); // и закрываем правой стеной
        }
        System.out.println(wallStart + wallNext.repeat(this.sizeBoardX)); // и в конце горизонтальную стену
    }

    public void moveCheck(Scanner scanner, Random random, Person person, Castle castle, int difficulty) {
        System.out.println(
                "Введите куда будет ходить персонаж (ход возможен только по вертикали и горизонтали на одну клетку)\n" +
                        "(Писать можно отдельно или в 1 строке через пробел)\n" + // оно тут не должно быть, сам написал
                        "Координаты персонажа - (x: " + person.getX() + ", y: " + person.getY() + ")" // я так объеденил, но оно не обязательно (через 1 pirntln)
        );

        int x = scanner.nextInt();
        int y = scanner.nextInt();

        if (x >= 1 && x <= sizeBoardX
                && y >= 1 && y <= sizeBoardY
                && Math.abs(x - person.getX())
                + Math.abs(y - person.getY()) == 1) {
            moveTry(x, y, person, castle, random, scanner, difficulty);
        } else {
            System.out.println("Некорректный ход. Попробуйте ещё раз.");
        }


    }

    public void moveTry(int x, int y, Person person, Castle castle, Random random, Scanner scanner, int difficulty) {
        boolean isWin = false;

        if (this.board[y - 1][x - 1].equals("  ")) { // Если место попытки хода пустое
            this.board[person.getY() - 1][person.getX() - 1] = "  "; // убираем игрока с прошлого места
            this.board[y - 1][x - 1] = person.getImage(); // добавляем в новое

            person.move(x, y);
            this.step++;

            System.out.println(
                    "Ход корректный; новые координаты: "
                            + person.getX() + ", " + person.getY()
                            + "\nХод номер: " + this.step
            );

        } else if (this.board[y - 1][x - 1].equals(castle.getImage())) {
            System.out.println("Вы прошли игру!");
            isWin = true;

        } else if (this.board[y - 1][x - 1].equals(this.icons.get("monsterSmall")) ||
                this.board[y - 1][x - 1].equals(this.icons.get("monsterBig"))) {
            System.out.println("Решите задачу.");
            for (Monster monster : monsters) {
                if (monster.getX() == x && monster.getY() == y) {
                    if (monster.taskMonster(random, scanner, difficulty)) {
                        this.board[person.getY() - 1][person.getX() - 1] = "  ";
                        this.board[y - 1][x - 1] = person.getImage();
                        person.move(x, y);
                        this.step++;
                    } else {
                        person.decHealth();
                    }
                    break;
                }
            }

        } else if (this.board[y - 1][x - 1].equals(this.icons.get("heartSmall"))) {
            this.board[person.getY() - 1][person.getX() - 1] = "  ";
            this.board[y - 1][x - 1] = person.getImage(); // добавляем в новое
            person.incHealth(1);
        } else if (this.board[y - 1][x - 1].equals(this.icons.get("heartBig"))) {
            this.board[person.getY() - 1][person.getX() - 1] = "  ";
            this.board[y - 1][x - 1] = person.getImage(); // добавляем в новое
            person.incHealth(2);
        }

        this.isWin = isWin;

    }

    public boolean getIsWin() {
        return this.isWin;
    }

}
