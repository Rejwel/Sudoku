package sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuElementTest {

    SudokuElement row1 = new SudokuRow();
    SudokuElement row2 = new SudokuRow();
    SudokuElement row3 = new SudokuRow();

    SudokuElement col1 = new SudokuColumn();

    SudokuField f0 = new SudokuField(0, 0, 0);
    SudokuField f1 = new SudokuField(1, 0, 0);
    SudokuField f2 = new SudokuField(2, 0, 0);
    SudokuField f3 = new SudokuField(3, 0, 1);
    SudokuField f4 = new SudokuField(4, 0, 1);
    SudokuField f5 = new SudokuField(5, 0, 1);
    SudokuField f6 = new SudokuField(6, 0, 2);
    SudokuField f7 = new SudokuField(7, 0, 2);
    SudokuField f8 = new SudokuField(8, 0, 2);
    SudokuField f9 = new SudokuField(1, 1, 0);

    @BeforeEach
    void init() {
        row1.setNumberInArray(0, f0);
        row1.setNumberInArray(1, f1);
        row1.setNumberInArray(2, f2);
        row1.setNumberInArray(3, f3);
        row1.setNumberInArray(4, f4);
        row1.setNumberInArray(5, f5);
        row1.setNumberInArray(6, f6);
        row1.setNumberInArray(7, f7);
        row1.setNumberInArray(8, f8);

        row2.setNumberInArray(0, f0);
        row2.setNumberInArray(1, f1);
        row2.setNumberInArray(2, f2);
        row2.setNumberInArray(3, f3);
        row2.setNumberInArray(4, f4);
        row2.setNumberInArray(5, f5);
        row2.setNumberInArray(6, f6);
        row2.setNumberInArray(7, f7);
        row2.setNumberInArray(8, f8);

        col1.setNumberInArray(0, f0);
        col1.setNumberInArray(1, f1);
        col1.setNumberInArray(2, f2);
        col1.setNumberInArray(3, f3);
        col1.setNumberInArray(4, f4);
        col1.setNumberInArray(5, f5);
        col1.setNumberInArray(6, f6);
        col1.setNumberInArray(7, f7);
        col1.setNumberInArray(8, f8);

        row3.setNumberInArray(0, f0);
        row3.setNumberInArray(1, f1);
        row3.setNumberInArray(2, f2);
        row3.setNumberInArray(3, f3);
        row3.setNumberInArray(4, f4);
        row3.setNumberInArray(5, f5);
        row3.setNumberInArray(6, f6);
        row3.setNumberInArray(7, f7);
        row3.setNumberInArray(8, f9);
    }

    @Test
    void toStringTest() {
        String str = row1.toString();
        assertEquals(str, row1.toString());
    }

    @Test
    void equalsThisRowTest() {
        assertTrue(row1.equals(row1));
    }

    @Test
    void notEqualsRowObjectsTest() {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        assertFalse(row1.equals(board));
    }

    @Test
    void notEqualsTest() {
        assertFalse(row1.equals(row3));
    }

    @Test
    void sameHashCodeTest() {
        assertEquals(row1.hashCode(), row2.hashCode());
    }

    @Test
    void differentHashCodeTest() {
        assertNotEquals(row1.hashCode(), row3.hashCode());
    }

    @Test
    void hashCodeFalseEqualsMustBeFalse() {
        assertNotEquals(row1.hashCode(), row3.hashCode());
        assertFalse(row1.equals(row3));
    }

    @Test
    void hashCodeTrueEqualsCanBeTrue() {
        assertEquals(row1.hashCode(), row2.hashCode());
        assertTrue(row1.equals(row2));
    }

    @Test
    void hashCodeTrueEqualsCanBeFalse() {
        assertEquals(row1.hashCode(), col1.hashCode());
        assertFalse(row1.equals(col1));
    }

    @Test
    void equalsTrueHashCodeMustBeTrue() {
        assertTrue(row1.equals(row2));
        assertEquals(row1.hashCode(), row2.hashCode());
    }

    @Test
    void equalsFalseHashCodeCanBeFalse() {
        assertFalse(row1.equals(row3));
        assertNotEquals(row1.hashCode(), row3.hashCode());
    }

    @Test
    void equalsFalseHashCodeCanBeTrue() {
        assertFalse(row1.equals(col1));
        assertEquals(row1.hashCode(), col1.hashCode());
    }

}