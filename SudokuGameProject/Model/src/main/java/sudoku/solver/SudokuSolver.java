package sudoku.solver;

import java.io.Serializable;
import sudoku.elements.SudokuBoard;
import sudoku.exceptions.SolverException;

public interface SudokuSolver extends Cloneable, Serializable {
    void solve(SudokuBoard board) throws SolverException;

    SudokuSolver clone() throws CloneNotSupportedException;
}
