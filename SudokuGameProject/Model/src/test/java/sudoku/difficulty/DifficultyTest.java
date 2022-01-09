package sudoku.difficulty;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sudoku.Repository;
import sudoku.elements.SudokuBoard;
import sudoku.exceptions.GetSetException;
import sudoku.exceptions.SetLevelException;
import sudoku.exceptions.SolverException;
import sudoku.exceptions.SudokuElementConstructorException;
import sudoku.solver.BacktrackingSudokuSolver;

public class DifficultyTest {

    @Test
    public void difficultyTest() throws CloneNotSupportedException, SetLevelException, SolverException, GetSetException, SudokuElementConstructorException {
        Repository r = new Repository(new BacktrackingSudokuSolver());

        SudokuBoard b1 = r.createSudokuBoard();
        SudokuBoard b2 = r.createSudokuBoard();
        SudokuBoard b3 = r.createSudokuBoard();

        b1.solveGame();
        b2.solveGame();
        b3.solveGame();

        Level.EASY.removeFieldsFromBoard(b1);
        Level.MEDIUM.removeFieldsFromBoard(b2);
        Level.HARD.removeFieldsFromBoard(b3);

        Assertions.assertThrows(SetLevelException.class, () -> Level.EASY.removeFieldsFromBoard(null));

        int b1ZerosCounter = 0;
        int b2ZerosCounter = 0;
        int b3ZerosCounter = 0;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(b1.get(i,j) == 0) b1ZerosCounter++;
                if(b2.get(i,j) == 0) b2ZerosCounter++;
                if(b3.get(i,j) == 0) b3ZerosCounter++;
            }
        }

        Assertions.assertEquals(b1ZerosCounter, 10);
        Assertions.assertEquals(b2ZerosCounter, 25);
        Assertions.assertEquals(b3ZerosCounter, 50);
    }
}
