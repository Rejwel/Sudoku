package sudoku.elements;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import sudoku.exceptions.CalculationsException;
import sudoku.exceptions.CloneException;
import sudoku.exceptions.GetSetException;
import sudoku.exceptions.SolverException;
import sudoku.exceptions.SudokuElementConstructorException;
import sudoku.solver.SudokuSolver;

public class SudokuBoard implements PropertyChangeListener, Serializable, Cloneable {

    private SudokuField[][] board;
    private SudokuSolver solver;
    private List<SudokuElement> sudokuColumns;
    private List<SudokuElement> sudokuRows;
    private List<SudokuElement> sudokuBoxes;
    private static Logger log = Logger.getLogger(SudokuBoard.class.getName());

    public SudokuBoard(SudokuSolver solver) throws SudokuElementConstructorException {
        try {
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
                    board[i][j].addListener(this);
                    setFieldInElement(i, j);
                }
            }
        } catch (Exception e) {
            throw new SudokuElementConstructorException("SudokuElementConstructorException");
        }
    }

    public int get(int x, int y) throws GetSetException {
        if (x > 8 || x < 0 || y > 8 || y < 0) {
            throw new GetSetException("GetSetException");
        }
        return board[x][y].getFieldValue();
    }

    public void set(int x, int y, int value) throws GetSetException {
        if (x > 8 || x < 0 || y > 8 || y < 0 || value < 0 || value > 9) {
            throw new GetSetException("GetSetException");
        }
        this.board[x][y].setFieldValue(value);
    }

    private void setFieldInElement(int x, int y) throws GetSetException {
        try {
            sudokuRows.get(x).setNumberInArray(y, board[x][y].getField());
            sudokuColumns.get(y).setNumberInArray(x, board[x][y].getField());
            sudokuBoxes.get(board[x][y].getNumberOfBox())
                    .setNumberInArray(board[x][y].getPositionInBox(), board[x][y].getField());
        } catch (Exception e) {
            throw new GetSetException("GetSetException");
        }
    }

    public SudokuElement getSudokuColumn(Integer x) throws GetSetException {
        try {
            SudokuElement col = new SudokuColumn();
            for (int i = 0; i < 9; i++) {
                col.setNumberInArray(i, sudokuColumns.get(x).getFields().get(i));
            }
            return col;
        } catch (Exception e) {
            throw new GetSetException("GetSetException");
        }
    }

    public SudokuElement getSudokuRow(Integer y) throws GetSetException {
        try {
            SudokuElement row = new SudokuRow();
            for (int i = 0; i < 9; i++) {
                row.setNumberInArray(i, sudokuRows.get(y).getFields().get(i));
            }
            return row;
        } catch (Exception e) {
            throw new GetSetException("GetSetException");
        }
    }

    public SudokuElement getSudokuBox(Integer x) throws GetSetException {
        try {
            SudokuElement box = new SudokuBox();
            for (int i = 0; i < 9; i++) {
                box.setNumberInArray(sudokuBoxes.get(x).getFields().get(i).getPositionInBox(),
                        sudokuBoxes.get(x).getFields().get(i));
            }
            return box;
        } catch (Exception e) {
            throw new GetSetException("GetSetException");
        }
    }

    public void solveGame() throws SolverException {
        solver.solve(this);
    }

    public boolean checkBoard() throws CalculationsException {
        try {
            boolean isValid;
            for (int i = 0; i < 9; i++) {
                isValid = sudokuColumns.get(i).verify() && sudokuColumns.get(i).getFields().stream().filter(s -> s.getFieldValue() != 0).count() == 9;
                if (!isValid) {
                    return false;
                }
                isValid = sudokuRows.get(i).verify() && sudokuColumns.get(i).getFields().stream().filter(s -> s.getFieldValue() != 0).count() == 9;
                if (!isValid) {
                    return false;
                }
                isValid = sudokuBoxes.get(i).verify() && sudokuColumns.get(i).getFields().stream().filter(s -> s.getFieldValue() != 0).count() == 9;
                if (!isValid) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            throw new CalculationsException("CalculationsException");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
//        try {
//            if (!checkBoard()) {
//                log.debug("Ten element nie pasuje w tym miejscu");
//            }
//        } catch (CalculationsException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("board", board).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SudokuBoard that)) {
            return false;
        }
        return new EqualsBuilder()
                .append(this.board, that.board)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.board)
                .toHashCode();
    }

    @Override
    public SudokuBoard clone() throws CloneNotSupportedException {
        try {
            SudokuBoard clone = (SudokuBoard) super.clone();
            clone.board = new SudokuField[9][9];
            clone.sudokuBoxes = new ArrayList<>(sudokuBoxes);
            clone.sudokuColumns = new ArrayList<>(sudokuColumns);
            clone.sudokuRows = new ArrayList<>(sudokuRows);
            clone.solver = solver.clone();
            for (int i = 0; i < 9; i++) {
                clone.sudokuColumns.set(i, new SudokuColumn());
                clone.sudokuRows.set(i, new SudokuRow());
                clone.sudokuBoxes.set(i, new SudokuBox());
            }
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    clone.board[i][j] = board[i][j].clone();
                    clone.sudokuRows.get(i).setNumberInArray(j, clone.board[i][j].getField());
                    clone.sudokuColumns.get(j).setNumberInArray(i, clone.board[i][j].getField());
                    clone.sudokuBoxes.get(clone.board[i][j].getNumberOfBox())
                            .setNumberInArray(clone.board[i][j].getPositionInBox(), clone.board[i][j].getField());
                }
            }
            return clone;
        } catch (Exception e) {
            throw new CloneException("CloneException");
        }
    }
}
