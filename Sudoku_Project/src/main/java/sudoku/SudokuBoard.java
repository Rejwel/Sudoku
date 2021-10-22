package sudoku;


public class SudokuBoard {


    public SudokuBoard() {
        this.board = new int[9][9];
        this.solver = new BacktrackingSudokuSolver();
    }

    public SudokuBoard(int[][] board) {
        this.board = board;
        this.solver = new BacktrackingSudokuSolver();
    }

    private int[][] board;
    private SudokuSolver solver;

    public int get(int x, int y) {
        return board[x][y];
    }
    public void set(int x,int y, int value) {
        this.board[x][y] = value;
    }

    public void solveGame() {
        solver.solve(this);
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
