package sudoku;

import java.util.Arrays;
import java.util.Random;

public final class StaticFunctions {

    private StaticFunctions() {
    }

    public static boolean hasDuplicate(SudokuField[] values) {
        int zeros;
        int zero;
        zeros = zero = 0;
        Integer[] temp = new Integer[values.length];

        for (int i = 0; i < values.length; i++) {
            if (values[i] == null || values[i].getValue().equals(0)) {
                temp[i] = 0;
                zeros++;
                zero = 1;
            } else {
                temp[i] = values[i].getValue();
            }
        }

        return Arrays.stream(temp).distinct().count() - zero != temp.length - zeros;
    }

    public static Integer[] sudokuElementToArray(SudokuElement fields) {
        Integer[] temp = new Integer[fields.getFields().length];

        for (int i = 0; i < fields.getFields().length; i++) {
            temp[i] = fields.getFields()[i].getValue();
        }

        return temp;
    }

    public static void clearBoard(SudokuBoard board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.set(i, j, 0);
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
                boardCopy[i][j] = board.get(i, j);
            }
        }
        return boardCopy;
    }

    public static void printBoard(SudokuBoard board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board.get(i, j) + " ");
            }
            System.out.println();
        }
    }
}
