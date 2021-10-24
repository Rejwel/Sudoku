package sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

class SudokuBoardTest {

    public SudokuBoardTest() {
    }

    @Test
    void solveGameTest() {

        SudokuBoard board = new SudokuBoard();
        board.solveGame();

        int[][] startingBoard = StaticFunctions.copyBoard(board);

        StaticFunctions.clearRandomSquaresFromBoard(board);

        board.solveGame();
        int[][] afterSolvingBoard = StaticFunctions.copyBoard(board);


        assertTrue(Arrays.deepEquals(startingBoard, afterSolvingBoard));

    }

    @Test
    void getPositionNegativeTest() {

        SudokuBoard board = new SudokuBoard();
        board.solveGame();

        assertEquals(-1, board.get(-1,1));
        assertEquals(-1, board.get(0,-1));
        assertEquals(-1, board.get(9,1));
        assertEquals(-1, board.get(1,9));

    }

    @Test
    void getPositionTest() {

        SudokuBoard board = new SudokuBoard();
        board.solveGame();

        assertTrue(board.get(0,0) >= 1 && board.get(0,0) <= 9);

    }

    @Test
    void setPositionNegativeTest() {

        SudokuBoard board = new SudokuBoard();
        board.solveGame();

        int testingNumber = board.get(0,0);

        board.set(-1,0, 1);
        assertEquals(testingNumber, board.get(0,0));

        board.set(0,-1, 1);
        assertEquals(testingNumber, board.get(0,0));

        board.set(9,0, 1);
        assertEquals(testingNumber, board.get(0,0));

        board.set(0,9, 1);
        assertEquals(testingNumber, board.get(0,0));

        board.set(0,0, -1);
        assertEquals(testingNumber, board.get(0,0));

        board.set(0,0, 10);
        assertEquals(testingNumber, board.get(0,0));
    }

    @Test
    void setPositionTest() {

        SudokuBoard board = new SudokuBoard();

        board.set(0,0, 1);
        assertEquals(board.get(0,0), 1);

    }

}