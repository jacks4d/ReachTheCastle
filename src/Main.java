import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();


        System.out.println("Привет! Ты готов начать играть в игру? (Напиши: ДА или НЕТ)");

        String isReady = scanner.nextLine();
        System.out.println("Ваш ответ:\t" + isReady);

        System.out.println("Выбери сложность игры (от 1 до 5):");

        int difficulty = scanner.nextInt();
        scanner.nextLine(); // Забираем перевод строки после числа

        System.out.println("Выбранная сложность:\t" + difficulty);

        System.out.println("Включить кастомизацию? ДА/НЕТ");

        String answer = scanner.nextLine();
        final boolean isCustom = answer.trim().equalsIgnoreCase("ДА");

        final int[] sizeBoard = setSizeBoard(scanner, isCustom, difficulty);

        Map<String, String> icons = new HashMap<>();
        icons.put("monsterSmall", "\uD83D\uDE08");
        icons.put("monsterBig", "\uD83D\uDC7A");
        icons.put("castle", "\uD83C\uDFF0");
        icons.put("player", "\uD83E\uDDB8");
        icons.put("heartSmall", "\u2665\uFE0F");
        icons.put("heartBig", "\u2764\uFE0F\u200D\uD83D\uDD25");

        if (isCustom) {
            setIcons(scanner, icons);
        }


        Person person = new Person(
                random.nextInt(sizeBoard[0]) + 1,
                sizeBoard[1],
                3,
                icons.get("player")
        );
        Castle castle = new Castle(
                random.nextInt(sizeBoard[0]) + 1,
                1,
                icons.get("castle")
        );

        Game game = new Game(
                sizeBoard[0],
                sizeBoard[1],
                difficulty,
                random,
                person,
                castle,
                icons);


        // Начало игры


        // Бесконечный цикл игры

        while ((person.getLives() > 0) && !(castle.getX() == person.getX() && castle.getY() == person.getY())) { // бесконечное повторение игры (то есть шаги будут идти)

            // Отрисовка игрового поля
            game.drawBoard();
            // Запуск и запись попытки хода
            game.moveCheck(scanner, random, person, castle, difficulty);

            if (game.getIsWin()) {
                break;
            }


        }

        // Если цикл закончился (по причине потери жизней)

        if (person.getLives() <= 0) {
            System.out.println("Закончились жизни. Итог: ...");
        }

    }

    public static void setIcons(Scanner scanner, Map<String, String> icons) {
        System.out.println("Настроить иконки? (ДА/НЕТ)");
        String answer = scanner.nextLine();
        if (answer.trim().equalsIgnoreCase("ДА")) {
            System.out.println("Иконку рекомендуется записывать в 1 символ");

            System.out.println("Введите иконку маленького монстра:");
            answer = scanner.nextLine();
            icons.put("monsterSmall", answer);

            System.out.println("Введите иконку большого монстра:");
            answer = scanner.nextLine();
            icons.put("monsterBig", answer);

            System.out.println("Введите иконку замка:");
            answer = scanner.nextLine();
            icons.put("castle", answer);

            System.out.println("Введите иконку игрока:");
            answer = scanner.nextLine();
            icons.put("player", answer);
        }
    }

    public static int[] setSizeBoard(Scanner scanner, boolean isCustom, int difficulty) {
        if (isCustom) {
            System.out.println("Напишите длину и ширину доски\n" +
                    "К примеру:\n" +
                    "5 6 -- (x = 5, y = 6)\n" +
                    "2 -- (x = 2, y = 2");
            String answer = scanner.nextLine();
            if (answer.contains(" ")) {
                String[] parts = answer.split(" ");
                return new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
            } else {
                return new int[]{Integer.parseInt(answer), Integer.parseInt(answer)};
            }
        } else {
            return new int[]{5 * difficulty, 5 * difficulty};
        }

    }
}
