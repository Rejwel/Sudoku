package sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuFieldTest {

    @Test
    void setValuePositiveTest() {
        SudokuField field = new SudokuField(0, 0, 0);
        field.setFieldValue(1);
        assertEquals(field.getFieldValue(), 1);
    }

    @Test
    void setValueNegativeTest() {

        SudokuField field = new SudokuField(0, 0, 0);
        field.setFieldValue(10);
        assertEquals(field.getFieldValue(), 0);
        field.setFieldValue(-1);
        assertEquals(field.getFieldValue(), 0);
    }

}