package sudoku;

import org.junit.jupiter.api.Test;
import sudoku.elements.*;
import sudoku.solver.BacktrackingSudokuSolver;
import sudoku.solver.SudokuSolver;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StaticFunctionsTest {

    public StaticFunctionsTest() {
    }

    @Test
    void hasDuplicateTest() {

        SudokuElement row = new SudokuRow();
        SudokuElement col = new SudokuColumn();
        SudokuElement box = new SudokuBox();

        SudokuField f0 = new SudokuField(0, 0, 0);
        SudokuField f1 = new SudokuField(0, 0, 0);
        SudokuField f2 = new SudokuField(0, 0, 0);
        SudokuField f3 = new SudokuField(0, 0, 0);
        SudokuField f4 = new SudokuField(0, 0, 0);
        SudokuField f5 = new SudokuField(0, 0, 0);

        f0.setFieldValue(0);
        f1.setFieldValue(1);
        f2.setFieldValue(2);
        f3.setFieldValue(3);
        f4.setFieldValue(4);


        SudokuField[] firstArray = {f1, f2, f3};
        SudokuField[] secondArray = {f1, f2, f2};
        SudokuField[] thirdArray = {f1, f2, f3, f0, f0, f0, f4};
        SudokuField[] fourthArray = {f1, f2, f3, f3, f0, f0, f4};
        SudokuField[] fifthArray = {f0};


        assertFalse(StaticFunctions.hasDuplicate(List.of(firstArray)));
        assertTrue(StaticFunctions.hasDuplicate(List.of(secondArray)));
        assertFalse(StaticFunctions.hasDuplicate(List.of(thirdArray)));
        assertTrue(StaticFunctions.hasDuplicate(List.of(fourthArray)));
        assertFalse(StaticFunctions.hasDuplicate(List.of(fifthArray)));


    }

    @Test
    void clearBoardTest() {

        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();

        int[][] clearArray = new int[9][9];

        StaticFunctions.clearBoard(board);
        int[][] copiedArray = StaticFunctions.boardTo2DArray(board);

        assertTrue(Arrays.deepEquals(copiedArray, clearArray));

    }

    @Test
    void copyBoardTest() {

        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);

        int[][] tempArray = new int[9][9];
        tempArray[0][0] = 1;


        board.set(0, 0, 1);
        int[][] copiedArray = StaticFunctions.boardTo2DArray(board);

        assertTrue(Arrays.deepEquals(copiedArray, tempArray));

    }

    @Test
    void printBoardTest() {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        StaticFunctions.printBoard(board);
    }

    @Test
    void sudokuElementToArray() throws CloneNotSupportedException {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();

        Integer[] newArray = StaticFunctions.sudokuElementToArray(board.getSudokuRow(0));

        for (Integer i = 0; i < 9; i++) {
            assertEquals(newArray[i], board.getSudokuRow(0).getFields().get(i).getFieldValue());
        }
    }
}