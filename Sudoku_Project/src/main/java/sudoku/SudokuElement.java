package sudoku;

public abstract class SudokuElement {

    public SudokuElement() {
        numbers = new SudokuField[9];
    }

    private SudokuField[] numbers;

    public boolean verify() {
        return StaticFunctions.hasDuplicate(numbers);
    }

    public void setNumberInArray(Integer pos, SudokuField field) {
        this.numbers[pos] = field;
    }

    public Integer[] getArray() {
        Integer[] temp = new Integer[9];
        for (int i = 0; i < 9; i++) {
            temp[i] = numbers[i].getValue();
        }
        return temp;
    }
}
