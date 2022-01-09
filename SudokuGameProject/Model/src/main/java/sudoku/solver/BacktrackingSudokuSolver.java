package sudoku.solver;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import sudoku.elements.SudokuBoard;
import sudoku.exceptions.SolverException;

public class BacktrackingSudokuSolver implements SudokuSolver,Cloneable {

    @Override
    public void solve(SudokuBoard board) throws SolverException {
        solveBoard(board);
    }

    @Override
    public SudokuSolver clone() throws CloneNotSupportedException {
        return (BacktrackingSudokuSolver) super.clone();
    }

    // algorithm modified from https://www.geeksforgeeks.org/sudoku-backtracking-7/

    private boolean solveBoard(SudokuBoard board) throws SolverException {
        try {
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

            if (full) {
                return true;
            }

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
        } catch (Exception e) {
            throw new SolverException("SolverException");
        }
    }

    private boolean isSafePosition(SudokuBoard board,
                                   int row,
                                   int col,
                                   int num) throws SolverException {

        try {
            for (int i = 0; i < 9; i++) {
                if (board.get(row,i) == num) {
                    return false;
                }
            }

            for (int i = 0; i < 9; i++) {
                if (board.get(i,col) == num) {
                    return false;
                }
            }

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
        } catch (Exception e) {
            throw new SolverException("SolverException");
        }
    }
}
