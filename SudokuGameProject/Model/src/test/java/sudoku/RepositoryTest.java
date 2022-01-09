package sudoku;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sudoku.elements.SudokuBoard;
import sudoku.exceptions.SolverException;
import sudoku.exceptions.SudokuElementConstructorException;
import sudoku.solver.BacktrackingSudokuSolver;

public class RepositoryTest {

    @Test
    public void repoTest() throws CloneNotSupportedException, SudokuElementConstructorException, SolverException {
        Repository r = new Repository(new BacktrackingSudokuSolver());
        SudokuBoard board = r.createSudokuBoard();
        board.solveGame();
        SudokuBoard board2 = r.createSudokuBoard();

        Assertions.assertNotEquals(board, board2);
    }
}
