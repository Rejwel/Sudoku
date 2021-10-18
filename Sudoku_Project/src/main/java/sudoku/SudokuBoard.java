package sudoku;

import java.util.Arrays;
import java.util.Random;

public class SudokuBoard {

    public SudokuBoard() {
        fillBoard();
    }

    private final int[][] board = new int[9][9];

    public int getPoint(int x, int y) {
        return board[x][y];
    }

    public void fillBoard() {
        cleanBoard();
        generateBoard();
    }

    private void cleanBoard() {
        for (int[] row: board) {
            Arrays.fill(row, 0);
        }
    }

    // algorithm modified from https://www.geeksforgeeks.org/sudoku-backtracking-7/
    private boolean generateBoard() {
        int row = -1;
        int col = -1;
        boolean full = true;
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board.length; j++) {
                if (board[i][j] == 0) {
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
        Random random = new Random();
        for (int num = 1; num <= this.board.length; num++) {
            int randomGeneratedNum = random.nextInt(9) + 1;
            if (isSafePosition(board, row, col, randomGeneratedNum)) {
                board[row][col] = randomGeneratedNum;
                if (generateBoard()) {
                    return true;
                } else {
                    board[row][col] = 0;
                }
            }
        }
        return false;
    }

    private boolean isSafePosition(int[][] board, int row, int col, int num) {

        // check if num is already in row
        for (int i = 0; i < board.length; i++) {
            if (board[row][i] == num) {
                return false;
            }
        }

        // check if num is already in col
        for (int i = 0; i < board.length; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }

        // check if num is already in box
        int sqrt = (int)Math.sqrt(board.length);
        int boxRowStart = row - row % sqrt;
        int boxColStart = col - col % sqrt;

        for (int r = boxRowStart; r < boxRowStart + sqrt; r++) {
            for (int d = boxColStart; d < boxColStart + sqrt; d++) {
                if (board[r][d] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard(int[][] board, int boardLength) {
        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardLength; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public void printBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(this.board[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
}
