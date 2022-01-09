package sudoku.solver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sudoku.StaticFunctions;
import sudoku.elements.SudokuBoard;
import sudoku.elements.SudokuField;
import sudoku.exceptions.GetSetException;
import sudoku.exceptions.SolverException;
import sudoku.exceptions.SudokuElementConstructorException;
import sudoku.solver.BacktrackingSudokuSolver;
import sudoku.solver.SudokuSolver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BacktrackingSudokuSolverTest {

    @Test
    void solverRowsColumnsSquaresBoardTest() throws CloneNotSupportedException, GetSetException, SudokuElementConstructorException, SolverException {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtracking);
        sudokuBoard.solveGame();

        for(int i = 0; i < 9; i++)
        {
            List<SudokuField> row = sudokuBoard.getSudokuRow(i).getFields();
            List<SudokuField> column = sudokuBoard.getSudokuColumn(i).getFields();
            List<SudokuField> box = sudokuBoard.getSudokuBox(i).getFields();
            Assertions.assertFalse(StaticFunctions.hasDuplicate(row));
            assertFalse(StaticFunctions.hasDuplicate(column));
            assertFalse(StaticFunctions.hasDuplicate(box));
        }
    }

    @Test
    void boardGeneratorTest() throws GetSetException, SolverException, SudokuElementConstructorException {

        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtracking);
        sudokuBoard.solveGame();

        boolean isTheSameBoard = true;

        int[][] firstBoard = StaticFunctions.boardTo2DArray(sudokuBoard);
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