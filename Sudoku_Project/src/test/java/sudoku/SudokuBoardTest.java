package sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

class SudokuBoardTest {

    // does junit support asserts in loops?

    public SudokuBoardTest() {
    }

    private boolean hasDuplicate(final int[] values) {
        return Arrays.stream(values).distinct().count() != values.length;
    }

    @Test
    void rowsColumnsSquaresBoardTest() {
        SudokuBoard sudokuBoard = new SudokuBoard();
        sudokuBoard.solveGame();
        int[] row = new int[9];

        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                row[j] = sudokuBoard.get(i,j);
            }
            assertFalse(hasDuplicate(row));
        }

        int[] col = new int[9];

        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                col[j] = sudokuBoard.get(j,i);
            }
            assertFalse(hasDuplicate(col));
        }

        int[] square = new int[9];
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
                assertFalse(hasDuplicate(square));
                temp = 0;
            }
        }
    }


    @Test
    void boardGeneratorTest() {

            SudokuBoard sudokuBoard = new SudokuBoard();
            sudokuBoard.solveGame();
            int[][] testBoard1 = new int[9][9];
            int[][] testBoard2 = new int[9][9];

            for(int j = 0; j < 9; j++)
            {
                for(int k = 0; k < 9; k++)
                {
                    testBoard1[j][k] = sudokuBoard.get(j,k);
                }
            }

            sudokuBoard.solveGame();

            for(int j = 0; j < 9; j++)
            {
                for(int k = 0; k < 9; k++)
                {
                    testBoard2[j][k] = sudokuBoard.get(j,k);

                }
            }
            assertFalse(Arrays.deepEquals(testBoard1, testBoard2));
        }

}