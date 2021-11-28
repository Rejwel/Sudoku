package sudoku.elements;

import org.junit.jupiter.api.Test;
import sudoku.elements.SudokuBoard;
import sudoku.elements.SudokuField;
import sudoku.solver.BacktrackingSudokuSolver;
import sudoku.solver.SudokuSolver;

import static org.junit.jupiter.api.Assertions.*;

class SudokuFieldTest {

    SudokuField field = new SudokuField(0, 0, 0);
    SudokuField field2 = new SudokuField(0, 0, 0);
    SudokuField field3 = new SudokuField(1, 1, 1);

    @Test
    void setValuePositiveTest() {
        field.setFieldValue(1);
        assertEquals(field.getFieldValue(), 1);
    }

    @Test
    void setValueNegativeTest() {
        field.setFieldValue(10);
        assertEquals(field.getFieldValue(), 0);
        field.setFieldValue(-1);
        assertEquals(field.getFieldValue(), 0);
    }

    @Test
    void toStringTest() {
        String str = field.toString();
        assertEquals(str, field.toString());
    }

    @Test
    void equalsThisRowTest() {
        assertTrue(field.equals(field));
    }

    @Test
    void notEqualsRowObjectsTest() {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        assertFalse(field.equals(board));
    }

    @Test
    void equalsTest() {
        assertTrue(field.equals(field2));
    }

    @Test
    void notEqualsTest() {
        assertFalse(field.equals(field3));
    }

    @Test
    void sameHashCodeTest() {
        assertEquals(field.hashCode(), field2.hashCode());
    }

    @Test
    void differentHashCodeTest() {
        assertNotEquals(field.hashCode(), field3.hashCode());
    }

    @Test
    void hashCodeFalseEqualsMustBeFalse() {
        assertNotEquals(field.hashCode(), field3.hashCode());
        assertFalse(field.equals(field3));
    }

    @Test
    void equalsTrueHashCodeMustBeTrue() {
        assertEquals(field.hashCode(), field2.hashCode());
        assertTrue(field.equals(field2));
    }

}