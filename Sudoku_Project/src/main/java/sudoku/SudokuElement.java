package sudoku;

import java.util.Arrays;

public abstract class SudokuElement implements SudokuObserver {

    public SudokuElement() {
        numbers = new SudokuField[9];
    }

    private SudokuField[] numbers;

    public boolean verify() {
        return StaticFunctions.hasNoDuplicate(numbers);
    }

    public void setNumberInArray(Integer pos, SudokuField field) {
        this.numbers[pos] = field;
    }

}
