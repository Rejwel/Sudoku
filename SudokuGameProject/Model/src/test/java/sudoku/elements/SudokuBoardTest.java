package sudoku.elements;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sudoku.Repository;
import sudoku.StaticFunctions;
import sudoku.difficulty.Level;
import sudoku.elements.SudokuBoard;
import sudoku.elements.SudokuElement;
import sudoku.elements.SudokuRow;
import sudoku.exceptions.GetSetException;
import sudoku.exceptions.SetLevelException;
import sudoku.exceptions.SolverException;
import sudoku.exceptions.SudokuElementConstructorException;
import sudoku.solver.BacktrackingSudokuSolver;
import sudoku.solver.SudokuSolver;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

class SudokuBoardTest {

    public SudokuBoardTest() {
    }

    @Test
    void solveGameTest() throws SudokuElementConstructorException, SolverException, GetSetException {

        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();

        int[][] startingBoard = StaticFunctions.boardTo2DArray(board);

        StaticFunctions.clearRandomSquaresFromBoard(board);

        board.solveGame();
        int[][] afterSolvingBoard = StaticFunctions.boardTo2DArray(board);


        assertTrue(Arrays.deepEquals(startingBoard, afterSolvingBoard));

    }

    @Test
    void getPositionNegativeTest() throws SudokuElementConstructorException, SolverException {

        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();

        assertThrows(GetSetException.class, () -> board.get(-1, 1));
        assertThrows(GetSetException.class, () -> board.get(0, -1));
        assertThrows(GetSetException.class, () -> board.get(9, 1));
        assertThrows(GetSetException.class, () -> board.get(1, 9));
    }

    @Test
    void getPositionTest() throws SudokuElementConstructorException, GetSetException, SolverException {

        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();

        assertTrue(board.get(0, 0) >= 1 && board.get(0, 0) <= 9);

    }

    @Test
    void setPositionNegativeTest() throws SudokuElementConstructorException, SolverException {

        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();

        assertThrows(GetSetException.class, () -> board.set(-1, 0, 1));
        assertThrows(GetSetException.class, () -> board.set(0, -1, 1));
        assertThrows(GetSetException.class, () -> board.set(9, 0, 1));
        assertThrows(GetSetException.class, () -> board.set(0, 9, 1));
        assertThrows(GetSetException.class, () -> board.set(0, 0, -1));
        assertThrows(GetSetException.class, () ->  board.set(0, 0, 10));
    }

    @Test
    void setPositionTest() throws SudokuElementConstructorException, GetSetException {

        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);

        board.set(0, 0, 1);
        assertEquals(board.get(0, 0), 1);

    }

    @Test
    void setPositionAbondTheLimitsTest() throws SudokuElementConstructorException {

        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);

        assertThrows(GetSetException.class, () -> board.set(0, 0, 10));
        assertThrows(GetSetException.class, () -> board.set(0, 1, -1));

    }

    @Test
    void verifyTest() throws CloneNotSupportedException, SudokuElementConstructorException, SolverException, GetSetException {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();

        Repository repo = new Repository(new BacktrackingSudokuSolver());
        SudokuBoard board = repo.createSudokuBoard();

        board.solveGame();

        for (int i = 0; i < 9; i++) {
            assertTrue(board.getSudokuBox(i).verify());
            assertTrue(board.getSudokuColumn(i).verify());
            assertTrue(board.getSudokuRow(i).verify());
        }
    }

    @Test
    void verifyNegativeTest() throws SudokuElementConstructorException, SolverException {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();

        assertThrows(GetSetException.class, () -> board.getSudokuBox(10).verify());
        assertThrows(GetSetException.class, () -> board.getSudokuBox(-1).verify());

        assertThrows(GetSetException.class, () -> board.getSudokuColumn(10).verify());
        assertThrows(GetSetException.class, () -> board.getSudokuColumn(-1).verify());

        assertThrows(GetSetException.class, () -> board.getSudokuRow(10).verify());
        assertThrows(GetSetException.class, () -> board.getSudokuRow(-1).verify());
    }

    @Test
    void checkBoardPositive() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, SudokuElementConstructorException, SolverException {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();
        Method checkBoardMethod = SudokuBoard.class.getDeclaredMethod("checkBoard");
        checkBoardMethod.setAccessible(true);

        assertTrue((Boolean) checkBoardMethod.invoke(board));
    }

    @Test
    void checkBoardNegativeRowTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, SudokuElementConstructorException, SolverException, GetSetException {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();
        Method checkBoardMethod = SudokuBoard.class.getDeclaredMethod("checkBoard");
        checkBoardMethod.setAccessible(true);

        assertTrue((Boolean) checkBoardMethod.invoke(board));

        board.set(0, 0, 1);
        board.set(0, 1, 1);

        assertFalse((Boolean) checkBoardMethod.invoke(board));
    }

    @Test
    void checkBoardNegativeColTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, SudokuElementConstructorException, SolverException, GetSetException {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();
        Method checkBoardMethod = SudokuBoard.class.getDeclaredMethod("checkBoard");
        checkBoardMethod.setAccessible(true);

        assertTrue((Boolean) checkBoardMethod.invoke(board));

        board.set(0, 0, 1);
        board.set(1, 0, 1);

        assertFalse((Boolean) checkBoardMethod.invoke(board));
    }

    @Test
    void checkBoardNegativeBoxTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, SudokuElementConstructorException, SolverException, GetSetException {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();
        Method checkBoardMethod = SudokuBoard.class.getDeclaredMethod("checkBoard");
        checkBoardMethod.setAccessible(true);

        assertTrue((Boolean) checkBoardMethod.invoke(board));

        board.set(0, 0, 1);
        board.set(1, 1, 1);

        assertFalse((Boolean) checkBoardMethod.invoke(board));
    }

    @Test
    void setValueInElementPositive() throws SudokuElementConstructorException, SolverException, GetSetException {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();

        board.set(0, 0, 1);
        assertEquals(board.get(0, 0), 1);
    }

    @Test
    void setValueInElementNegative() throws SudokuElementConstructorException, SolverException {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();

        assertThrows(GetSetException.class, () -> board.set(0, 0, 10));
        assertThrows(GetSetException.class, () -> board.set(1, 1, -1));
    }

    @Test
    void toStringTest() throws SudokuElementConstructorException {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        String str = board.toString();
        assertEquals(str, board.toString());
    }

    @Test
    void equalsThisTest() throws SudokuElementConstructorException {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);

        assertEquals(board, board);
    }

    @Test
    void notEqualsObjectsTest() throws SudokuElementConstructorException {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        SudokuElement row = new SudokuRow();

        assertNotEquals(board, row);
    }

    @Test
    void equalsTest() throws SudokuElementConstructorException {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        SudokuBoard board2 = new SudokuBoard(backtracking);

        assertEquals(board, board2);
    }

    @Test
    void notEqualsTest() throws SudokuElementConstructorException, SolverException {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();
        SudokuBoard board2 = new SudokuBoard(backtracking);
        board2.solveGame();

        assertNotEquals(board, board2);
    }

    @Test
    void sameHashCodeTest() throws SudokuElementConstructorException {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        SudokuBoard board2 = new SudokuBoard(backtracking);

        assertEquals(board.hashCode(), board2.hashCode());
    }

    @Test
    void differentHashCodeTest() throws SudokuElementConstructorException, SolverException {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();
        SudokuBoard board2 = new SudokuBoard(backtracking);
        board2.solveGame();

        assertNotEquals(board.hashCode(), board2.hashCode());
    }

    @Test
    void hashCodeFalseEqualsMustBeFalse() throws SudokuElementConstructorException, SolverException {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();
        SudokuBoard board2 = new SudokuBoard(backtracking);
        board2.solveGame();

        assertNotEquals(board.hashCode(), board2.hashCode());
        assertNotEquals(board, board2);
    }

    @Test
    void equalsTrueHashCodeMustBeTrue() throws SudokuElementConstructorException {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        SudokuBoard board2 = new SudokuBoard(backtracking);

        assertEquals(board, board2);
        assertEquals(board.hashCode(), board2.hashCode());
    }

    @Test
    void copyTest() throws CloneNotSupportedException, SudokuElementConstructorException, SolverException, GetSetException {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();

        SudokuBoard clone = board.clone();
        assertEquals(clone.get(0, 0), board.get(0, 0));
        assertEquals(clone.get(1, 0), board.get(1, 0));
        assertEquals(clone.get(2, 0), board.get(2, 0));

        clone.set(0, 0, 1);
        clone.set(1, 0, 1);
        clone.set(2, 0, 1);
        board.set(0, 0, 9);
        board.set(1, 0, 9);
        board.set(2, 0, 9);

        assertNotEquals(clone.get(0, 0), board.get(0, 0));
        assertNotEquals(clone.get(1, 0), board.get(1, 0));
        assertNotEquals(clone.get(2, 0), board.get(2, 0));


    }
}