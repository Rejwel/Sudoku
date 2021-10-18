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
    void rowsBoardTest() {
        SudokuBoard sudokuBoard = new SudokuBoard();
        int[] row = new int[9];

        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                row[j] = sudokuBoard.getPoint(i,j);
            }
            assertFalse(hasDuplicate(row));
        }
    }

    @Test
    void colsBoardTest() {
        SudokuBoard sudokuBoard = new SudokuBoard();
        int[] col = new int[9];

        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                col[j] = sudokuBoard.getPoint(j,i);
            }
            assertFalse(hasDuplicate(col));
        }
    }

    @Test
    void squaresBoardTest() {
        SudokuBoard sudokuBoard = new SudokuBoard();
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
                        square[temp++] = sudokuBoard.getPoint(k,l);
                    }
                }
                assertFalse(hasDuplicate(square));
                temp = 0;
            }
        }
    }

    @Test
    void boardGeneratorTest() {
        for(int i = 0; i < 999; i++)
        {
            SudokuBoard sudokuBoard = new SudokuBoard();
            int[][] testBoard1 = new int[9][9];
            int[][] testBoard2 = new int[9][9];

            for(int j = 0; j < 9; j++)
            {
                for(int k = 0; k < 9; k++)
                {
                    testBoard1[j][k] = sudokuBoard.getPoint(j,k);
                }
            }

            sudokuBoard.fillBoard();

            for(int j = 0; j < 9; j++)
            {
                for(int k = 0; k < 9; k++)
                {
                    testBoard2[j][k] = sudokuBoard.getPoint(j,k);

                }
            }
            assertFalse(Arrays.deepEquals(testBoard1, testBoard2));
        }
    }
}