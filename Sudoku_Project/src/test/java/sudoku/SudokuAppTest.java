package sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuAppTest {

    public SudokuAppTest() {
    }

    @Test
    void sudokuAppTest() {

        boolean hasErrors = false;

        try {
            SudokuApp app = new SudokuApp();
            String[] args = null;
            SudokuApp.main(args);
        } catch (ExceptionInInitializerError e) {
            hasErrors = true;
        }

        assertFalse(hasErrors);
    }
}