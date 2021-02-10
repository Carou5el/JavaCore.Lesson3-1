package kulkov.JavaCore.Lesson3_1;

import java.util.Arrays;

public class SpiralArray {

    private int xSize;
    private int ySize;
    private int[][] matrix2D;

    public SpiralArray(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.matrix2D = new int[ySize][xSize];
//        this.initMatrix2D();
    }

    public void getMatrix2D()   {

        System.out.println(Arrays.deepToString(matrix2D));
        System.out.printf("matrix2D.length = %d\n", matrix2D.length);
        System.out.printf("matrix2D[0].length = %d\n", matrix2D[0].length);
    }

    /***
     * Метод заполняет 2-мерный массив (прямоугольный, в общем случае) возрастающими числами по спирали.
     */
    public void getInitSpiralArray()    {

        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;

        int borderX0    = 0;
        int borderX     = matrix2D[0].length;
        int borderY0    = 0;
        int borderY     = matrix2D.length;

        int stepCnt = 1;

        /*
            Переменная stepCnt начинается с 1.
            Всего шагов необходимо выполнить (xSize * ySize).
         */
        while(stepCnt <= xSize * ySize) {

            a = borderX0;
            // Обход горизонтально-вправо.
            for(; a < borderX; a++)  {
                /*
                    Заполнение горизонтали, элемент[0][a].
                 */
                matrix2D[borderY0][a] = stepCnt;
                ++stepCnt;
            }

            borderY0++;
            borderX--;
            b = borderY0;

            // Обход вертикально-вниз.
            for(; b < borderY; b++)  {
                /*
                    Заполнение вертикали, элемент[b][borderX].
                 */
                matrix2D[b][borderX] = stepCnt;
                ++stepCnt;
            }
//            borderX--;
            borderY--;
            c = borderX - 1;

            // Обход горизонт-влево.
            for(; c >= borderX0; c--)  {
                /*
                    Заполнене горизонтали, элемент[borderY][c].
                 */
                matrix2D[borderY][c] = stepCnt;
                ++stepCnt;
            }
//            borderY--;
            d = borderY - 1;

            // Обход вертикал-вверх.
            for(; d >= borderY0; d--)  {
                /*
                    Заполнение вертикали, элемент[0][d].
                 */
                matrix2D[d][borderX0] = stepCnt;
                ++stepCnt;
            }
            borderX0++;
        }




    }


}
