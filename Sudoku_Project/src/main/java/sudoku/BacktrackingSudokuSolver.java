package sudoku;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BacktrackingSudokuSolver implements SudokuSolver {

    @Override
    public void solve(SudokuBoard board) {
        solveBoard(board);
    }

    // algorithm modified from https://www.geeksforgeeks.org/sudoku-backtracking-7/

    private boolean solveBoard(SudokuBoard board) {
        int row = -1;
        int col = -1;
        boolean full = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.get(i,j) == 0) {
                    row = i;
                    col = j;

                    // We still have some remaining
                    // missing values in Sudoku
                    full = false;
                    break;
                }
            }
            if (!full) {
                break;
            }
        }

        // No empty space left
        if (full) {
            return true;
        }

        // Else for each-row backtrack + random sudoku board generator
        Integer[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        List<Integer> intList = Arrays.asList(numbers);
        Collections.shuffle(intList);
        intList.toArray(numbers);

        for (int num = 0; num < 9; num++) {

            if (isSafePosition(board, row, col, numbers[num])) {
                board.set(row,col,numbers[num]);
                if (solveBoard(board)) {
                    return true;
                } else {
                    board.set(row,col,0);
                }
            }
        }
        return false;
    }

    private boolean isSafePosition(SudokuBoard board, int row, int col, int num) {

        // check if num is already in row
        for (int i = 0; i < 9; i++) {
            if (board.get(row,i) == num) {
                return false;
            }
        }

        // check if num is already in col
        for (int i = 0; i < 9; i++) {
            if (board.get(i,col) == num) {
                return false;
            }
        }

        // check if num is already in box
        int sqrt = (int)Math.sqrt(9);
        int boxRowStart = row - row % sqrt;
        int boxColStart = col - col % sqrt;

        for (int r = boxRowStart; r < boxRowStart + sqrt; r++) {
            for (int d = boxColStart; d < boxColStart + sqrt; d++) {
                if (board.get(r,d) == num) {
                    return false;
                }
            }
        }
        return true;
    }
}
