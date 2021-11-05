package sudoku;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class StaticFunctionsTest {

    public StaticFunctionsTest() {
    }

    @Test
    void hasDuplicateTest() {

        SudokuElement row = new SudokuRow();
        SudokuElement col = new SudokuColumn();
        SudokuElement box = new SudokuBox();

        SudokuField f0 = new SudokuField(row,col,box,0,0);
        SudokuField f1 = new SudokuField(row,col,box,0,0);
        SudokuField f2 = new SudokuField(row,col,box,0,0);
        SudokuField f3 = new SudokuField(row,col,box,0,0);
        SudokuField f4 = new SudokuField(row,col,box,0,0);

        f0.setValue(0);
        f1.setValue(1);
        f2.setValue(2);
        f3.setValue(3);
        f4.setValue(4);

        SudokuField[] firstArray = { f1, f2, f3 };
        SudokuField[] secondArray = { f1, f2, f2 };
        SudokuField[] thirdArray = { f1, f2, f3, f0, f0, f0 , f4 };
        SudokuField[] fourthArray = { f1, f2, f3, f3, f0, f0 , f4 };

        assertFalse(StaticFunctions.hasDuplicate(firstArray));
        assertTrue(StaticFunctions.hasDuplicate(secondArray));
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