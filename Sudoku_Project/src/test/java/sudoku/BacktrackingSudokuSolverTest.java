package sudoku;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BacktrackingSudokuSolverTest {

    @Test
    void solverRowsColumnsSquaresBoardTest() {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtracking);
        sudokuBoard.solveGame();

        for(int i = 0; i < 9; i++)
        {
            List<SudokuField> row = sudokuBoard.getSudokuRow(i).getFields();
            List<SudokuField> column = sudokuBoard.getSudokuColumn(i).getFields();
            List<SudokuField> box = sudokuBoard.getSudokuBox(i).getFields();
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