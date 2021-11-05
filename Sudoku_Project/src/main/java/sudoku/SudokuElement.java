package sudoku;

public abstract class SudokuElement implements SudokuObserver {

    public SudokuElement() {
        fields = new SudokuField[9];
    }

    public SudokuField[] getFields() {
        return fields;
    }

    private SudokuField[] fields;

    public boolean verify() {
        return !StaticFunctions.hasDuplicate(fields);
    }

    public void setNumberInArray(Integer pos, SudokuField field) {
        this.fields[pos] = field;
    }

}
