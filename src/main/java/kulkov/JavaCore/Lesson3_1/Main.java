package kulkov.JavaCore.Lesson3_1;

import java.util.Random;
import java.util.Scanner;

public class Main {
    /*
        Переделать проверку победы, чтобы она не была реализована просто набором условий.
        Попробовать переписать логику проверки победы, чтобы она работала для поля 5х5 и количества фишек 4 в линию.

    *** Доработать искусственный интеллект, чтобы он мог блокировать ходы игрока, и пытаться выиграть сам.

        По желанию.
        Наисать метод, который принимает на вход 2 целых числа, например, x и y.
        Внутри метода создаётся целочисленный двумерный массив со сторонами x и y.
        Потом этот массив заполняется последовательно инкрементируемыми числами по спирали (или змейкой).
        То есть, в позиции [0,0] будет 1, в [0,1] - 2 и так далее.
        Потом по достижении правой границы направление заполнения меняется на вертикальное и так далее.
        Так, что последний заполненный элемент будет посередине массива.

     */
    private static char[][] field;
    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = 'O';
    private static final char DOT_EMPTY = '.';
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();
    private static int fieldSizeX = 7;
    private static int fieldSizeY = 7;
    private static int WINNER_CNT = 5;              // Количество символов подряд для победы.

    public static void main(String[] args) {

        SpiralArray spiralArray = new SpiralArray(5, 5);
        spiralArray.getMatrix2D();
        spiralArray.getInitSpiralArray();
        spiralArray.getMatrix2D();

//        spiralArray.getMatrix2D();

//        while (true) {
//            initField();
//            printField();
//
//            while (true) {
//                humanTurn();
//                printField();
//                if (checkGame(DOT_HUMAN, "Human wins!!!")) break;
//                aiTurn();
//                printField();
//                if (checkGame(DOT_AI, "AI win!!!")) break;
//            }
//            System.out.println("Wanna play again?");
//            if (!SCANNER.next().equals("y")) {
//                SCANNER.close();
//                break;
//            }
//        }
    }


    private static void humanTurn() {
        int x;
        int y;

        do {
            System.out.print("Введите координаты хода Х и У от 1 до 3 через пробел ->");
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        } while (!isCellValid(x, y) || !isCellEmpty(x, y));

        field[y][x] = DOT_HUMAN;
    }

    private static void aiTurn() {
        int x;
        int y;
        do {
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        } while (!isCellEmpty(x, y));

        field[y][x] = DOT_AI;
    }

    private static boolean checkGame(char dot, String s) {

        if (checkWin(dot)) {
            System.out.println(s);
            return true;
        }
        if (checkDraw()) {
            System.out.println("Draw!!!");
            return true;
        }
        return false;
    }

    private static boolean checkWin(char c) {

        boolean result = false;

    /*
         Сканируем всё поле до 1-го вхождения переданного символа.
         Когда символ будет найден, вызываем поочерёдно методы проверки "выигрыша".
         Методы проверки "выигрыша" выполняют 4-ре вида проверок:
            1. Линия в горизонталь.
            2. Линия в вертикаль.
            3. Линия в диагональ слева-направо, вниз.
            4. Линия в диагональ справа-налево, вниз.
    */
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                if (field[i][j] == c) {
                    result = (checkHoriz(i, j, c) ||
                            checkVertic(i, j, c) ||
                            checkDiagLeft(i, j, c) ||
                            checkDiagRight(i, j, c));
                }
                if (result) return result;
            }
        }
        return result;
    }

    private static boolean checkHoriz(int yCoord, int xCoord, char checkChar) {

        int checkCnt = 0;

        for (int i = xCoord; i < WINNER_CNT; i++) {
            if (i < fieldSizeX) {                                   // Внутри игрового поля.
                if (field[yCoord][i] == checkChar) {                // Есть проверяемый символ.
                    checkCnt++;
                } else {
                    checkCnt = 0;                                   // "Левый" символ, обнуляем счётчик победы.
                }
            }
        }

        return checkCnt == WINNER_CNT;                              // true - есть победная последовательность.
    }

    private static boolean checkVertic(int yCoord, int xCoord, char checkChar) {

        int checkCnt = 0;

        for (int i = yCoord; i < WINNER_CNT; i++) {
            if (i < fieldSizeY) {                                  // Проверка нахождения внутри игрового поля.
                if (field[i][xCoord] == checkChar) {               // Проверка на символ.
                    checkCnt++;
                } else {
                    checkCnt = 0;                                   // "Левый" символ, обнуляем счётчик победы.
                }
            }
        }
        return checkCnt == WINNER_CNT;                              // true - есть победная последовательность.
    }

    private static boolean checkDiagRight(int yCoord, int xCoord, char checkChar) {

        int checkCnt = 0;

        for (int i = xCoord; i < WINNER_CNT; i++) {
            if (i < fieldSizeY && i < fieldSizeX) {                    // Проверка нахождения внутри игрового поля.
                if (field[i][i] == checkChar) {                        // Проверка на символ.
                    checkCnt++;
                } else {
                    checkCnt = 0;                                       // "Левый" символ, обнуляет счётчик победы.
                }
            }
        }
        return checkCnt == WINNER_CNT;                                  // true - есть победная последовательность.
    }

    private static boolean checkDiagLeft(int yCoord, int xCoord, char checkChar) {

        int checkCnt = 0;
        int j = 0;

        for (int i = yCoord; i < WINNER_CNT; i++) {

            j = -i + (fieldSizeY - 1);
            if (i < fieldSizeY && i < fieldSizeX) {                     // Проверка нахождения внутри игрового поля.
                if (field[i][j] == checkChar) {                         // Проверка на символ.
                    checkCnt++;
                } else {
                    checkCnt = 0;                                       // "Левый" символ, обнуляет счётчик победы.
                }
            }
        }
        return checkCnt == WINNER_CNT;                                  // true - есть победная последовательность.
    }

    private static boolean checkDraw() {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (isCellEmpty(x, y)) return false;
            }
        }
        return true;
    }

    private static void initField() {
        field = new char[fieldSizeY][fieldSizeX];
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                field[y][x] = DOT_EMPTY;
            }
        }
    }

    private static void printField() {
        System.out.print("+");
        for (int i = 0; i < fieldSizeX * 2 + 1; i++)
            System.out.print((i % 2 == 0) ? "-" : i / 2 + 1);
        System.out.println();

        for (int i = 0; i < fieldSizeY; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < fieldSizeX; j++)
                System.out.print(field[i][j] + "|");
            System.out.println();
        }

        for (int i = 0; i <= fieldSizeX * 2 + 1; i++)
            System.out.print("-");
        System.out.println();
    }

    private static boolean isCellEmpty(int x, int y) {
        return field[y][x] == DOT_EMPTY;
    }

    private static boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }
}
