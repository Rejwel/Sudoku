package sudoku;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class StaticFunctionsTest {

    public StaticFunctionsTest() {
    }

    @Test
    void hasDuplicateTest() {

        int[] firstArray = { 1, 2, 3 };
        int[] secondArray = { 1, 2, 2 };

        assertTrue(StaticFunctions.hasDuplicate(secondArray));
        assertFalse(StaticFunctions.hasDuplicate(firstArray));

    }

    @Test
    void clearBoardTest() {

        SudokuBoard board = new SudokuBoard();
        board.solveGame();

        int[][] clearArray = new int[9][9];

        StaticFunctions.clearBoard(board);
        int[][] copiedArray = StaticFunctions.copyBoard(board);

        assertTrue(Arrays.deepEquals(copiedArray, clearArray));

    }

    @Test
    void copyBoardTest() {

        SudokuBoard board = new SudokuBoard();

        int[][] tempArray = new int[9][9];
        tempArray[0][0] = 1;


        board.set(0,0,1);
        int[][] copiedArray = StaticFunctions.copyBoard(board);

        assertTrue(Arrays.deepEquals(copiedArray, tempArray));

    }

    @Test
    void printBoardTest() {
        SudokuBoard board = new SudokuBoard();
        StaticFunctions.printBoard(board);
    }
}