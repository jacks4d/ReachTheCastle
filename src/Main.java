import java.util.Random;
import java.util.Scanner;
// В этом коде я использовал функции,
//из-за которых были небольшие проблемы с глобальность/локальность переменных,
//поэтому тут небольшой бардак с передачей информации между функциями
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); //Сканер это объект, нужен только один
        Random random = new Random(); // Рандом это объект, (он как сканер) нужен только один
        int step = 0;
        int sizeBoard = 5;
        int[] personCords = new int[]{1, sizeBoard}; //personCords[0] = x, personCords[1] = y
        int personLive = 3; // количество жизней
        int castleX = 1 + random.nextInt(sizeBoard); // Рандомчик зависящий от максимума самого игрового поля
        int castleY = 1; // находится сверху

        int countMonster = sizeBoard * sizeBoard - sizeBoard - 1;

        int[] returningCoords;

        // Пока что строчные записи, сохранил на потом
        String person = "\uD83E\uDDB8 "; // почему оно карточка? 🪪
        String monster = "\uD83D\uDC80";

        String castle = "\uD83C\uDFF0"; // Это эмодзи если что
        String leftBlock = " | ";
        String rightBlock = " |";
        String wall = " + —— + —— + —— + —— + —— + ";

        String[][] board = new String[sizeBoard][sizeBoard]; // игровое поле сделанное массивом


        // Запись пустых строк в каждую клетку игрового поля

        for (int y = 1; y <= sizeBoard; y++) {
            for (int x = 1; x <= sizeBoard; x++) {
                board[y - 1][x - 1] = "  ";
            }
        }
        // запис монстров в рандомные клетки поля
        for (int i = 0; i < countMonster; i++) {
            board[random.nextInt(sizeBoard - 1)][random.nextInt(sizeBoard)] = monster;
        }
        // 1 раз записываем, то где находятся замок и игрок в начале игры на поле
        board[castleY - 1][castleX - 1] = castle;
        board[personCords[1] - 1][personCords[0] - 1] = person;




        // Начало игры

        System.out.println("Привет! Ты готов начать играть в игру? (Напиши: ДА или НЕТ)");

        String answer = scanner.nextLine();
        System.out.println("Ваш ответ:\t" + answer);

        System.out.println("Выбери сложность игры (от 1 до 5):");

        int difficultGame = scanner.nextInt();
        System.out.println("Выбранная сложность:\t" + difficultGame);
        // Бесконечный цикл игры

        while ((personLive > 0) && !(castleX == personCords[0] && castleY == personCords[1])){ // бесконечное повторение игры (то есть шаги будут идти)

            // Отрисовка игрового поля
            drawBoard(sizeBoard, wall, leftBlock, rightBlock, board);
            // Запуск и запись попытки хода

            returningCoords = moveCheck(personCords, scanner);
            if (returningCoords != null){ // если ход правильный
                int x = returningCoords[0];
                int y = returningCoords[1];

                // Запуск и запись самого движения
                boolean isWin = moveTry(personCords, x, y, board, sizeBoard, step, castle, person);
                if (isWin){
                    break;
                }
            }

        }

        // Если цикл закончился (по причине потери жизней)

        if (personLive <= 0) {
            System.out.println("Закончились жизни. Итог: ...");
        }

    }

    public static int[] moveCheck(int[] personCords, Scanner scanner){
        System.out.println(
                "Введите куда будет ходить персонаж (ход возможен только по вертикали и горизонтали на одну клетку)\n" +
                        "(Писать можно отдельно или в 1 строке через пробел)\n" + // оно тут не должно быть, сам написал
                        "Координаты персонажа - (x: " + personCords[0] + ", y: " + personCords[1] + ")" // я так объеденил, но оно не обязательно (через 1 pirntln)
        );

        int x = scanner.nextInt();
        int y = scanner.nextInt();

        if (x == personCords[0] && y == personCords[1]) { // "если то же самое"
            System.out.println("То же место");
            System.out.println("Попробуйте еще раз");
            return null;
        } else if (Math.abs(x - personCords[0]) == 1 || Math.abs(y - personCords[1]) == 1) { // если сдвинулся ТОЛЬКО на 1 в любую сторону
            return new int[]{x, y};
        } else { // вписали что-то не связанное
            System.out.println("Координаты не изменены");
            System.out.println("Попробуйте еще раз");
            return null;
        }
    }


    public static boolean moveTry(int[] personCords, int x, int y, String[][] board, int sizeBoard, int step, String castle, String person){
        boolean isWin;

            if (board[y - 1][x - 1].equals("  ")) { // Если место попытки хода пустое
                board[personCords[1] - 1][personCords[0] - 1] = "  "; // убираем игрока с прошлого места
                board[y - 1][x - 1] = person; // добавляем в новое

                personCords[0] = x;
                personCords[1] = y;
                step++;

                System.out.println(
                                "Ход корректный; новые координаты: "
                                        + personCords[0] + ", " + personCords[1]
                                        + "\nХод номер: " + step
                );
                isWin = false;
            } else if (board[y - 1][x - 1].equals(castle)) {
                System.out.println("Вы прошли игру!");
                isWin = true;
            } else {
                System.out.println("Решите задачу.");
                isWin = false;

            }
            return isWin;

    }
    public static void drawBoard(int sizeBoard, String wall, String leftBlock, String rightBlock, String[][] board){
        for (int y = 1; y <= sizeBoard; y++) {
            System.out.println(wall); // перед каждой строкой клеток (где могут быть игрок, монстр и т.д.) выводим стену горизонтальную
            for (int x = 1; x <= sizeBoard; x++) {
                System.out.print(leftBlock); // выводим сначала левую стену
                System.out.print(board[y - 1][x - 1]); // выводим то что находится в клетке по индексу массива
            }
            System.out.println(rightBlock); // и закрываем правой стеной
        }
        System.out.println(wall); // и в конце горизонтальную стену
    }
}
