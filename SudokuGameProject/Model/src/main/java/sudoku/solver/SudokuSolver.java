package sudoku.solver;

import java.io.Serializable;
import sudoku.elements.SudokuBoard;

public interface SudokuSolver extends Cloneable, Serializable {
    void solve(SudokuBoard board);

    SudokuSolver clone() throws CloneNotSupportedException;
}
