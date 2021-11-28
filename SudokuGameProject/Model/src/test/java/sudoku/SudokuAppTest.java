package sudoku;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SudokuAppTest {

    public SudokuAppTest() {
    }

    @Test
    void sudokuAppTest() {
        try {
            SudokuApp app = new SudokuApp();
            String[] args = null;
            SudokuApp.main(args);
            assertDoesNotThrow(SudokuApp::new);
            assertDoesNotThrow(() -> SudokuApp.main(args));
        } catch (ExceptionInInitializerError | Exception e) {
            e.printStackTrace();
        }
    }
}