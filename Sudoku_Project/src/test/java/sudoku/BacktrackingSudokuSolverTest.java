package sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BacktrackingSudokuSolverTest {

    @Test
    void solverRowsColumnsSquaresBoardTest() {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtracking);
        sudokuBoard.solveGame();
        Integer[] row = new Integer[9];

        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                row[j] = sudokuBoard.get(i,j);
            }
            assertTrue(StaticFunctions.hasNoDuplicate(row));
        }

        Integer[] col = new Integer[9];

        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                col[j] = sudokuBoard.get(j,i);
            }
            assertTrue(StaticFunctions.hasNoDuplicate(col));
        }

        Integer[] square = new Integer[9];
        int temp = 0;

        for(int i = 0; i <= 6; i+=3)
        {
            for(int j = 0; j <= 6; j+=3)
            {
                for(int k = i; k < i + 3; k++)
                {
                    for(int l = j; l < j + 3; l++)
                    {
                        square[temp++] = sudokuBoard.get(k,l);
                    }
                }
                assertTrue(StaticFunctions.hasNoDuplicate(square));
                temp = 0;
            }
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