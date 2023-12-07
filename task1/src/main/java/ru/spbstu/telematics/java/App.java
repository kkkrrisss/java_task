package ru.spbstu.telematics.java;
public class App 
{
        public static void main(String[] args) {
            // Задаем первую матрицу
            int[][] matrix1 = {
                {1,2,3},
                {4,5,6},
                {7,8,9}
            };

            // Задаем вторую матрицу
            int[][] matrix2 = {
                {9,8,7},
                {6,5,4},
                {3,2,1}
            };

            // Вызываем метод для сложения матриц
            int[][] resultMatrix = addMatrix(matrix1, matrix2);

            // Проверяем, удалось ли сложить матрицы
            if (resultMatrix != null) {
                // Выводим результат
                System.out.println("Результат сложения:");
                printMatrix(resultMatrix);
            }
            else {
                if (matrix1.length == 0 && matrix2.length == 0){
                    System.out.println("Матрицы пустые");
                }
                else{
                    System.out.println("Невозможно сложить матрицы разной размерности");
                }
            }
        }

        public static int[][] addMatrix(int[][] matrix1, int[][] matrix2) {
            // Проверяем, что матрицы не пустые
            if (matrix1.length == 0 || matrix2.length == 0){
                return null;
            }
            // Проверяем, что размеры матриц совпадают
            if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
                return null; // Возвращаем null в случае несовпадения размеров или пустых матриц
            }
            int rows = matrix1.length;
            int cols = matrix1[0].length;

            int[][] resultMatrix = new int[rows][cols];

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    resultMatrix[i][j] = matrix1[i][j] + matrix2[i][j];
                }
            }

            return resultMatrix;
        }

        //метод для вывода матрицы
        private static void printMatrix(int[][] matrix) {
            for (int[] row : matrix) {
                for (int value : row) {
                    System.out.print(value + " ");
                }
                System.out.println();
            }
            System.out.println();
        }

}
