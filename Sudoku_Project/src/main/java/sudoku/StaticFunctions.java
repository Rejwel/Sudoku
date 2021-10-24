package sudoku;

import java.util.Arrays;
import java.util.Random;

public final class StaticFunctions {

    private StaticFunctions() {
    }

    public static boolean hasDuplicate(final int[] values) {
        return Arrays.stream(values).distinct().count() != values.length;
    }

    public static void clearBoard(SudokuBoard board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.set(i,j, 0);
            }
        }
    }

    public static void clearRandomSquaresFromBoard(SudokuBoard board) {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            board.set(random.nextInt(9), random.nextInt(9), 0);
        }
    }

    public static int[][] copyBoard(SudokuBoard board) {
        int[][] boardCopy = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boardCopy[i][j] = board.get(i,j);
            }
        }
        return boardCopy;
    }

    public static void printBoard(SudokuBoard board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board.get(i,j) + " ");
            }
            System.out.println();
        }
    }
}
