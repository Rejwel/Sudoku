package sudoku;

import java.util.Arrays;
import java.util.List;

public class SudokuBox implements SudokuArray {

    public SudokuBox() {
        numbers = new SudokuField[9];
    }

    private SudokuField[] numbers;

    @Override
    public boolean verify() {
        return StaticFunctions.hasDuplicate(numbers);
    }

    @Override
    public void setNumberInArray(Integer pos, SudokuField field) {
        this.numbers[pos] = field;
    }

    @Override
    public Integer[] getArray() {
        Integer[] temp = new Integer[9];
        for (int i = 0; i < 9; i++) {
            temp[i] = numbers[i].getValue();
        }
        return temp;
    }
}
