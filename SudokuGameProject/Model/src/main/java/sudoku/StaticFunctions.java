package sudoku;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import sudoku.elements.SudokuBoard;
import sudoku.elements.SudokuElement;
import sudoku.elements.SudokuField;

public final class StaticFunctions {

    private StaticFunctions() {
    }

    public static boolean hasDuplicate(List<SudokuField> values) {
        int zeros;
        int zero;
        zeros = zero = 0;
        Integer[] temp = new Integer[values.size()];


        for (int i = 0; i < values.size(); i++) {
            if (values.get(i) == null || values.get(i).getFieldValue().equals(0)) {
                temp[i] = 0;
                zeros++;
                zero = 1;
            } else {
                temp[i] = values.get(i).getFieldValue();
            }
        }

        return Arrays.stream(temp).distinct().count() - zero != temp.length - zeros;
    }

    public static Integer[] sudokuElementToArray(SudokuElement fields)
            throws CloneNotSupportedException {
        Integer[] temp = new Integer[fields.getFields().size()];

        for (int i = 0; i < fields.getFields().size(); i++) {
            temp[i] = fields.getFields().get(i).getFieldValue();
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

    public static int[][] boardTo2DArray(SudokuBoard board) {
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
