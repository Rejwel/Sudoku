package sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuFieldTest {

    @Test
    void setValuePositiveTest() {
        SudokuElement row = new SudokuRow();
        SudokuElement col = new SudokuColumn();
        SudokuElement box = new SudokuBox();
        SudokuField field = new SudokuField(row, col, box, 0, 0);
        field.setValue(1);
        assertEquals(field.getValue(), 1);
    }

    @Test
    void setValueNegativeTest() {
        SudokuElement row = new SudokuRow();
        SudokuElement col = new SudokuColumn();
        SudokuElement box = new SudokuBox();
        SudokuField field = new SudokuField(row, col, box, 0, 0);
        field.setValue(10);
        assertEquals(field.getValue(), 0);
        field.setValue(-1);
        assertEquals(field.getValue(), 0);
    }

}