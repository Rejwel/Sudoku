package sudoku.solver;

import java.io.Serializable;
import sudoku.elements.SudokuBoard;

public interface SudokuSolver extends Serializable {
    void solve(SudokuBoard board);
}
