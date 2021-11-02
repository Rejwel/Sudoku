package sudoku;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class StaticFunctionsTest {

    public StaticFunctionsTest() {
    }

    @Test
    void hasDuplicateTest() {

        Integer[] firstArray = { 1, 2, 3 };
        Integer[] secondArray = { 1, 2, 2 };
        Integer[] thirdArray = { 1, 2, 3, 0, 0, 0 , 4 };
        Integer[] fourthArray = { 1, 2, 3, 3, 0, 0 , 4 };


        assertTrue(StaticFunctions.hasDuplicate(secondArray));
        assertFalse(StaticFunctions.hasDuplicate(firstArray));
        assertFalse(StaticFunctions.hasDuplicate(thirdArray));
        assertTrue(StaticFunctions.hasDuplicate(fourthArray));


    }

    @Test
    void clearBoardTest() {

        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();

        int[][] clearArray = new int[9][9];

        StaticFunctions.clearBoard(board);
        int[][] copiedArray = StaticFunctions.copyBoard(board);

        assertTrue(Arrays.deepEquals(copiedArray, clearArray));

    }

    @Test
    void copyBoardTest() {

        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);

        int[][] tempArray = new int[9][9];
        tempArray[0][0] = 1;


        board.set(0,0,1);
        int[][] copiedArray = StaticFunctions.copyBoard(board);

        assertTrue(Arrays.deepEquals(copiedArray, tempArray));

    }

    @Test
    void printBoardTest() {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        StaticFunctions.printBoard(board);
    }
}