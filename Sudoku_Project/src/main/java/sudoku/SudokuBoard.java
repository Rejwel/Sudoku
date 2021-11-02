package sudoku;


public class SudokuBoard {

    public SudokuBoard(SudokuSolver solver) {

        this.solver = solver;
        this.board = new SudokuField[9][9];
        this.sudokuColumns = new SudokuColumn[9];
        this.sudokuRows = new SudokuRow[9];
        this.sudokuBoxes = new SudokuBox[9];

        int boxNumber;
        int startingRowBoxNumber;
        int startingColBoxNumber;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                startingRowBoxNumber = i - (i % 3);
                startingColBoxNumber = j - (j % 3);
                boxNumber = ((3 * startingRowBoxNumber + startingColBoxNumber) / 3);
                board[i][j] = new SudokuField(sudokuRows[i], sudokuColumns[j], sudokuBoxes[boxNumber], i, j);
            }
        }
    }

    private SudokuField[][] board;

    private SudokuArray[] sudokuColumns;
    private SudokuArray[] sudokuRows;
    private SudokuArray[] sudokuBoxes;

    private SudokuSolver solver;

    public int get(int x, int y) {
        if (x > 8 || x < 0 || y > 8 || y < 0) {
            return -1;
        }
        return board[x][y].getValue();
    }

    public void set(int x, int y, int value) {
        if (x > 8 || x < 0 || y > 8 || y < 0 || value < 0 || value > 9) {
            return;
        }
        this.board[x][y].setValue(value);
    }

    public SudokuArray getSudokuColumn(Integer x) {
        return sudokuColumns[x];
    }

    public SudokuArray getSudokuRow(Integer y) {
        return sudokuRows[y];
    }

    public SudokuArray getSudokuBox(Integer x) {
        return sudokuBoxes[x];
    }

    public void solveGame() {
        solver.solve(this);
    }
}
