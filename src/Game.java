import java.util.Random;
import java.util.Scanner;
public class Game {
    private int step = 0;
    private final int sizeBoardX;
    private final int sizeBoardY;
    private String[][] board;
    private boolean isWin = false;

    public Game(int sizeBoardX, int sizeBoardY, Person person, Castle castle){
        this.sizeBoardX = sizeBoardX;
        this.sizeBoardY = sizeBoardY;

        this.board = new String[this.sizeBoardY][this.sizeBoardX]; // игровое поле сделанное массивом
        for (int y = 0; y < sizeBoardY; y++) {
            for (int x = 0; x < sizeBoardX; x++) {
                this.board[y][x] = "  ";
            }
        }
//        for (int i = 0; i < countMonster; i++) {
//            this.board[random.nextInt(sizeBoard - 1)][random.nextInt(sizeBoard)] = monster;
//        }
        this.board[castle.getY() - 1][castle.getX() - 1] = castle.getImage();
        this.board[person.getY() - 1][person.getX() - 1] = person.getImage();

    }


    void drawBoard(){
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
    public void moveCheck(Scanner scanner, Person person, Castle castle) {
        System.out.println(
                "Введите куда будет ходить персонаж (ход возможен только по вертикали и горизонтали на одну клетку)\n" +
                        "(Писать можно отдельно или в 1 строке через пробел)\n" + // оно тут не должно быть, сам написал
                        "Координаты персонажа - (x: " + person.getX() + ", y: " + person.getY() + ")" // я так объеденил, но оно не обязательно (через 1 pirntln)
        );

        int x = scanner.nextInt();
        int y = scanner.nextInt();

        if ((Math.abs(x - person.getX()) == 1 && x > 0 ) || (Math.abs(y - person.getY()) == 1) && y > 0) { // если сдвинулся ТОЛЬКО на 1 в любую сторону
            moveTry(x, y, person, castle);
        } else if (x == person.getX() && y == person.getY()) { // "если то же самое"
            System.out.println("То же место");
            System.out.println("Попробуйте еще раз");
        } else { // вписали что-то не связанное
            System.out.println("Координаты не изменены");
            System.out.println("Попробуйте еще раз");
        }


    }
    public void moveTry(int x, int y, Person person, Castle castle){
        boolean isWin;

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
            isWin = false;
        } else if (this.board[y - 1][x - 1].equals(castle.getImage())) {
            System.out.println("Вы прошли игру!");
            isWin = true;
        } else {
            System.out.println("Решите задачу.");
            isWin = false;

        }
        this.isWin = isWin;

    }
    public boolean getIsWin(){
        return this.isWin;
    }

}
