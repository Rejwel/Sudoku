package sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BacktrackingSudokuSolverTest {

    @Test
    void solverRowsColumnsSquaresBoardTest() {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtracking);
        sudokuBoard.solveGame();

        for(int i = 0; i < 9; i++)
        {
            SudokuField[] row = sudokuBoard.getSudokuRow(i).getFields();
            SudokuField[] column = sudokuBoard.getSudokuColumn(i).getFields();
            SudokuField[] box = sudokuBoard.getSudokuBox(i).getFields();
            assertFalse(StaticFunctions.hasDuplicate(row));
            assertFalse(StaticFunctions.hasDuplicate(column));
            assertFalse(StaticFunctions.hasDuplicate(box));
        }
    }

    @Test
    void boardGeneratorTest() {

        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtracking);
        sudokuBoard.solveGame();

        boolean isTheSameBoard = true;

        int[][] firstBoard = StaticFunctions.copyBoard(sudokuBoard);
        StaticFunctions.clearBoard(sudokuBoard);
        sudokuBoard.solveGame();

        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(firstBoard[i][j] != sudokuBoard.get(i,j)) {
                    isTheSameBoard = false;
                    break;
                }
            }
            if(!isTheSameBoard) break;
        }

        assertFalse(isTheSameBoard);
    }

}