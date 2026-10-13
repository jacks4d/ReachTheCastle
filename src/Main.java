import java.util.Random;
import java.util.Scanner;
// В этом коде я использовал функции,
//из-за которых были небольшие проблемы с глобальностью/локальностью переменных,
//поэтому тут небольшой бардак с передачей информации между функциями

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); //Сканер это объект, нужен только один
        Random random = new Random(); // Рандом это объект, (он как сканер) нужен только один

        System.out.println("Привет! Ты готов начать играть в игру? (Напиши: ДА или НЕТ)");

        System.out.println("Ваш ответ:\t" + scanner.nextLine());

        System.out.println("Выбери сложность игры (от 1 до 5):");

        int difficultGame = scanner.nextInt();
        scanner.nextLine(); // после nextInt() там остается нажатие энтер что летит дальше, вхолостую убираем его
        System.out.println("Выбранная сложность:\t" + difficultGame);

        System.out.println("Включить кастомизацию? ДА/НЕТ");
        String answer = scanner.nextLine();
        final boolean isCustom = answer.equals("ДА");

        final int[] sizeBoard = setSizeBoard(scanner, isCustom);



        Person person = new Person(random.nextInt(sizeBoard[0]), sizeBoard[1]);
        Castle castle = new Castle(random.nextInt(sizeBoard[0]), 1);
        Game game = new Game(sizeBoard[0], sizeBoard[1], person, castle);



//        int countMonster = sizeBoard * sizeBoard - sizeBoard - 1;







        // Начало игры


        // Бесконечный цикл игры

        while ((person.getLives() > 0) && !(castle.getX() == person.getX() && castle.getY() == person.getY())){ // бесконечное повторение игры (то есть шаги будут идти)

            // Отрисовка игрового поля
            game.drawBoard();
            // Запуск и запись попытки хода
            game.moveCheck(scanner, person, castle);

            if (game.getIsWin()){
                break;
            }


        }

        // Если цикл закончился (по причине потери жизней)

        if (person.getLives() <= 0) {
            System.out.println("Закончились жизни. Итог: ...");
        }

    }
    public static int[] setSizeBoard(Scanner scanner, boolean isCustom){
        if (isCustom){
            System.out.println("Напишите длину и ширину доски\n" +
                    "К примеру:\n" +
                    "5 6 -- (x = 5, y = 6)\n" +
                    "2 -- (x = 2, y = 2");
            String answer = scanner.nextLine();
            if (answer.contains(" ")){
                String[] parts = answer.split(" ");
                return new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
            }
            else {
                return new int[]{Integer.parseInt(answer), Integer.parseInt(answer)};
            }
        } else {
            return new int[]{5, 5}; // пока что так, ПОТОМ СДЕЛАТЬ НАДО СОВМЕСТИМОСТЬ СО СЛОЖНОСТЬЮ
        }

    }


    static boolean taskMonster() {
        Random random = new Random();
        int a = random.nextInt(100);
        int b = random.nextInt(100);
        int trueAnswer = a + b;
        System.out.println("Реши пример: " + a + " + " + b + " = ?");
        Scanner scanner = new Scanner(System.in);
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
