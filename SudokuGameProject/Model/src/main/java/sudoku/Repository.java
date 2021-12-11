package sudoku;

import sudoku.elements.SudokuBoard;
import sudoku.solver.SudokuSolver;

public class Repository {

    private SudokuBoard board;

    public Repository(SudokuSolver solver) {
        board = new SudokuBoard(solver);
    }

    public SudokuBoard createSudokuBoard() throws CloneNotSupportedException {
        board.solveGame();
        return board.clone();
    }
}
