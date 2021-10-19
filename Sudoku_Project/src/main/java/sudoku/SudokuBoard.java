package sudoku;

import java.util.Arrays;
import java.util.Random;

public class SudokuBoard {

    public SudokuBoard() {

    }

    private int[][] board = new int[9][9];

    public int get(int x, int y) {
        return board[x][y];
    }

    public void solveGame() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        solver.solve(this);
    }

    private void cleanBoard() {
        for (int[] row: board) {
            Arrays.fill(row, 0);
        }
    }

    public void set(int x,int y, int value) {
        this.board[x][y] = value;
    }

    public void printBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(get(i,j) + " ");
            }
            System.out.println();
        }
    }
}
