package sudoku;


public class SudokuBoard {

    public SudokuBoard(SudokuSolver solver) {

        this.solver = solver;
        this.board = new SudokuField[9][9];
        this.sudokuColumns = new SudokuColumn[9];
        this.sudokuRows = new SudokuRow[9];
        this.sudokuBoxes = new SudokuBox[9];

        for (int i = 0; i < 9; i++) {
            sudokuColumns[i] = new SudokuColumn();
            sudokuRows[i] = new SudokuRow();
            sudokuBoxes[i] = new SudokuBox();
        }

        int boxNumber;
        int startingRowBoxNumber;
        int startingColBoxNumber;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                startingRowBoxNumber = i - (i % 3);
                startingColBoxNumber = j - (j % 3);
                boxNumber = (3 * startingRowBoxNumber + startingColBoxNumber) / 3;
                board[i][j] = new SudokuField(sudokuRows[i],
                        sudokuColumns[j],
                        sudokuBoxes[boxNumber],
                        i,
                        j);
            }
        }
    }

    private SudokuField[][] board;

    private SudokuElement[] sudokuColumns;
    private SudokuElement[] sudokuRows;
    private SudokuElement[] sudokuBoxes;

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

    private boolean checkBoard() {
        boolean isValid = true;
        for (int i = 0; i < 9; i++) {
            isValid = sudokuColumns[i].verify();
            isValid = sudokuRows[i].verify();
            isValid = sudokuBoxes[i].verify();
            if (!isValid) {
                return false;
            }
        }
        return true;
    }

    public SudokuElement getSudokuColumn(Integer x) {
        return sudokuColumns[x];
    }

    public SudokuElement getSudokuRow(Integer y) {
        return sudokuRows[y];
    }

    public SudokuElement getSudokuBox(Integer x) {
        return sudokuBoxes[x];
    }

    public void solveGame() {
        solver.solve(this);
    }
}
