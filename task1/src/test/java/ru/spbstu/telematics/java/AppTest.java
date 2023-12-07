package ru.spbstu.telematics.java;

import static org.junit.Assert.*;
import org.junit.Test;

public class AppTest {

    @Test
    public void testMatrix() {
        // Тест на обычные матрицы
        int[][] matrix1 = {{1, 2},{3, 4}};
        int[][] matrix2 = {{4, 3},{2, 1}};
        int[][] matrix3 = {{5, 5},{5, 5}};
        assertEquals(matrix3, App.addMatrix(matrix1, matrix2));
    }
    @Test
    public void testNegMatrix() {
        // Тест на матрицы с отрицательными значениями
        int[][] negmatrix1 = {{-2, 5},{0, -6}};
        int[][] negmatrix2 = {{-3,-1},{-2, 3}};
        int[][] negmatrix3 = {{-5, 4},{-2, -3}};
        assertEquals(negmatrix3, App.addMatrix(negmatrix1, negmatrix2));
    }
    @Test
    public void testEmptyMatrix() {
        //Тест на пустые матрицы
        int[][] emptymatrix1 = {};
        int[][] emptymatrix2 = {};
        assertEquals(null, App.addMatrix(emptymatrix1, emptymatrix2));
    }
    @Test
    public void testDiffMatrix() {
        // Тест на матрицы разной размерности
        int[][] m1 = {{1,2,3},{4,5,6}};
        int[][] m2 = {{1,2},{3,4}};
        assertEquals(null, App.addMatrix(m1, m2));

    }
}
