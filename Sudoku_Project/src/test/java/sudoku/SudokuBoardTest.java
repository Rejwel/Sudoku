package sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

class SudokuBoardTest {

    public SudokuBoardTest() {
    }

    @Test
    void solveGameTest() {

        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();

        int[][] startingBoard = StaticFunctions.copyBoard(board);

        StaticFunctions.clearRandomSquaresFromBoard(board);

        board.solveGame();
        int[][] afterSolvingBoard = StaticFunctions.copyBoard(board);


        assertTrue(Arrays.deepEquals(startingBoard, afterSolvingBoard));

    }

    @Test
    void getPositionNegativeTest() {

        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();

        assertEquals(-1, board.get(-1, 1));
        assertEquals(-1, board.get(0, -1));
        assertEquals(-1, board.get(9, 1));
        assertEquals(-1, board.get(1, 9));

    }

    @Test
    void getPositionTest() {

        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();

        assertTrue(board.get(0, 0) >= 1 && board.get(0, 0) <= 9);

    }

    @Test
    void setPositionNegativeTest() {

        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();

        int testingNumber = board.get(0, 0);

        board.set(-1, 0, 1);
        assertEquals(testingNumber, board.get(0, 0));

        board.set(0, -1, 1);
        assertEquals(testingNumber, board.get(0, 0));

        board.set(9, 0, 1);
        assertEquals(testingNumber, board.get(0, 0));

        board.set(0, 9, 1);
        assertEquals(testingNumber, board.get(0, 0));

        board.set(0, 0, -1);
        assertEquals(testingNumber, board.get(0, 0));

        board.set(0, 0, 10);
        assertEquals(testingNumber, board.get(0, 0));
    }

    @Test
    void setPositionTest() {

        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);

        board.set(0, 0, 1);
        assertEquals(board.get(0, 0), 1);

    }

    @Test
    void setPositionAbondTheLimitsTest() {

        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);

        int numberOne = board.get(0, 0);
        int numberTwo = board.get(0, 1);

        board.set(0, 0, 10);
        board.set(0, 1, -1);
        assertEquals(board.get(0, 0), numberOne);
        assertEquals(board.get(0, 1), numberTwo);

    }

    @Test
    void verifyTest() {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);

        board.solveGame();

        for (int i = 0; i < 9; i++) {
            assertTrue(board.getSudokuBox(i).verify());
            assertTrue(board.getSudokuColumn(i).verify());
            assertTrue(board.getSudokuRow(i).verify());
        }
    }

    @Test
    void verifyNegativeTest() {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);

        board.solveGame();
        board.set(0, 0, 1);
        board.set(0, 1, 1);

        assertFalse(board.getSudokuBox(0).verify());
    }

    @Test
    void checkBoardPositive() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();
        Method checkBoardMethod = SudokuBoard.class.getDeclaredMethod("checkBoard");
        checkBoardMethod.setAccessible(true);

        assertTrue((Boolean) checkBoardMethod.invoke(board));
    }

    @Test
    void checkBoardNegativeRowTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();
        Method checkBoardMethod = SudokuBoard.class.getDeclaredMethod("checkBoard");
        checkBoardMethod.setAccessible(true);

        assertTrue((Boolean) checkBoardMethod.invoke(board));

        board.set(0,0,1);
        board.set(0,1,1);

        assertFalse((Boolean) checkBoardMethod.invoke(board));
    }

    @Test
    void checkBoardNegativeColTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();
        Method checkBoardMethod = SudokuBoard.class.getDeclaredMethod("checkBoard");
        checkBoardMethod.setAccessible(true);

        assertTrue((Boolean) checkBoardMethod.invoke(board));

        board.set(0,0,1);
        board.set(1,0,1);

        assertFalse((Boolean) checkBoardMethod.invoke(board));
    }

    @Test
    void checkBoardNegativeBoxTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();
        Method checkBoardMethod = SudokuBoard.class.getDeclaredMethod("checkBoard");
        checkBoardMethod.setAccessible(true);

        assertTrue((Boolean) checkBoardMethod.invoke(board));

        board.set(0,0,1);
        board.set(1,1,1);

        assertFalse((Boolean) checkBoardMethod.invoke(board));
    }
}