package sudoku;


public class SudokuBoard {


    public SudokuBoard(SudokuSolver solver) {
        this.board = new int[9][9];
        this.solver = solver;
    }

    private int[][] board;
    private SudokuSolver solver;

    public int get(int x, int y) {
        if (x > 8 || x < 0 || y > 8 || y < 0) {
            return -1;
        }
        return board[x][y];
    }

    public void set(int x,int y, int value) {
        if (x > 8 || x < 0 || y > 8 || y < 0 || value < 0 || value > 9) {
            return;
        }
        this.board[x][y] = value;
    }

    public void solveGame() {
        solver.solve(this);
    }

}
