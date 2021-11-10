package sudoku;


import java.util.Arrays;
import java.util.List;

public class SudokuBoard {

    public SudokuBoard(SudokuSolver solver) {

        this.solver = solver;
        this.board = new SudokuField[9][9];
        this.sudokuColumns = Arrays.asList(new SudokuColumn[9]);
        this.sudokuRows = Arrays.asList(new SudokuRow[9]);
        this.sudokuBoxes = Arrays.asList(new SudokuBox[9]);

        for (int i = 0; i < 9; i++) {
            sudokuColumns.set(i, new SudokuColumn());
            sudokuRows.set(i, new SudokuRow());
            sudokuBoxes.set(i, new SudokuBox());
        }

        int boxNumber;
        int startingRowBoxNumber;
        int startingColBoxNumber;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                startingRowBoxNumber = i - (i % 3);
                startingColBoxNumber = j - (j % 3);
                boxNumber = (3 * startingRowBoxNumber + startingColBoxNumber) / 3;
                board[i][j] = new SudokuField(i, j, boxNumber);
            }
        }
    }

    private SudokuField[][] board;

    private List<SudokuElement> sudokuColumns;
    private List<SudokuElement> sudokuRows;
    private List<SudokuElement> sudokuBoxes;

    private SudokuSolver solver;

    public int get(int x, int y) {
        if (x > 8 || x < 0 || y > 8 || y < 0) {
            return -1;
        }
        return board[x][y].getFieldValue();
    }

    private void setValueInElement(int value, int x, int y) {
        if (value >= 0 && value <= 9) {
            sudokuRows.get(x).setNumberInArray(y, board[x][y].getField());
            sudokuColumns.get(y).setNumberInArray(x, board[x][y].getField());
            sudokuBoxes.get(board[x][y].getNumberOfBox())
                    .setNumberInArray(board[x][y].getPositionInBox(), board[x][y].getField());
        }
    }

    public void set(int x, int y, int value) {
        if (x > 8 || x < 0 || y > 8 || y < 0 || value < 0 || value > 9) {
            return;
        }
        setValueInElement(value, x, y);
        this.board[x][y].setFieldValue(value);
    }

    private boolean checkBoard() {
        boolean isValid = true;
        for (int i = 0; i < 9; i++) {
            isValid = sudokuColumns.get(i).verify();
            isValid = sudokuRows.get(i).verify();
            isValid = sudokuBoxes.get(i).verify();
            if (!isValid) {
                return false;
            }
        }
        return true;
    }

    public SudokuElement getSudokuColumn(Integer x) {
        SudokuElement col = new SudokuColumn();
        for (int i = 0; i < 9; i++) {
            col.setNumberInArray(i, sudokuColumns.get(x).getFields().get(i));
        }
        return col;
    }

    public SudokuElement getSudokuRow(Integer y) {
        SudokuElement row = new SudokuRow();
        for (int i = 0; i < 9; i++) {
            row.setNumberInArray(i, sudokuRows.get(y).getFields().get(i));
        }
        return row;
    }

    public SudokuElement getSudokuBox(Integer x) {
        SudokuElement box = new SudokuBox();
        for (int i = 0; i < 9; i++) {
            box.setNumberInArray(sudokuBoxes.get(x).getFields().get(i).getPositionInBox(),
                    sudokuBoxes.get(x).getFields().get(i));
        }
        return box;
    }

    public void solveGame() {
        solver.solve(this);
    }


}
