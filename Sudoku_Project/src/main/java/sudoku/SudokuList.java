package sudoku;

import java.util.Arrays;
import java.util.List;

public class SudokuList<T> {
    public SudokuList() {
        numbers = Arrays.asList(new Integer[9]);
    }

    private List<Integer> numbers;

    public boolean verify() {
        return StaticFunctions.hasDuplicate(numbers.toArray(new Integer[numbers.toArray().length]));
    }
}
