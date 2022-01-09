package sudoku;

import sudoku.elements.SudokuBoard;
import sudoku.exceptions.SudokuElementConstructorException;
import sudoku.solver.SudokuSolver;

public class Repository {

    private SudokuBoard board;

    public Repository(SudokuSolver solver) throws SudokuElementConstructorException {
        board = new SudokuBoard(solver);
    }

    public SudokuBoard createSudokuBoard() throws CloneNotSupportedException {
        return board.clone();
    }
}
