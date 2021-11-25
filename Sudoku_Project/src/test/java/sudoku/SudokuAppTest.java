package sudoku;

import org.junit.jupiter.api.Test;

import java.io.IOException;

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
        } catch (ExceptionInInitializerError | IOException e) {
            hasErrors = true;
        }

        assertFalse(hasErrors);
    }
}