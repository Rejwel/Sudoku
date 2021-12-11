package sudoku.difficulty;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sudoku.Repository;
import sudoku.elements.SudokuBoard;
import sudoku.solver.BacktrackingSudokuSolver;

public class DifficultyTest {

    @Test
    public void difficultyTest() throws CloneNotSupportedException {
        Repository r = new Repository(new BacktrackingSudokuSolver());

        SudokuBoard b1 = r.createSudokuBoard();
        SudokuBoard b2 = r.createSudokuBoard();
        SudokuBoard b3 = r.createSudokuBoard();

        b1.solveGame();
        b2.solveGame();
        b3.solveGame();

        Level.EASY.setDifficulty(b1);
        Level.MEDIUM.setDifficulty(b2);
        Level.HARD.setDifficulty(b3);

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
