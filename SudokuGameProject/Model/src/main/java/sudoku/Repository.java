package sudoku;

import sudoku.elements.SudokuBoard;
import sudoku.solver.SudokuSolver;

public final class Repository {

    private SudokuSolver solver;
    private SudokuBoard board;

    private Repository(SudokuSolver solver) {
        this.solver = solver;
        board = new SudokuBoard(solver);
    }

    public SudokuBoard createSudokuBoard() throws CloneNotSupportedException {
        return board.clone();
    }
}
