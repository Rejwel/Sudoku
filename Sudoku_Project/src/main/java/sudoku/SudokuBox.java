package sudoku;

import java.util.Arrays;
import java.util.List;

public class SudokuBox implements SudokuArray {

    public SudokuBox() {
        numbers = new SudokuField[9];
        Arrays.fill(numbers, 0);
    }

    private SudokuField[] numbers;

    @Override
    public boolean verify() {
        return StaticFunctions.hasDuplicate(numbers);
    }

    @Override
    public void setNumberInArray(Integer pos, Integer value) {
        this.numbers[pos].setValue(value);
    }
}
