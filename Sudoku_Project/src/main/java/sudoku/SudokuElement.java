package sudoku;

import java.util.Arrays;
import java.util.List;

public abstract class SudokuElement implements SudokuObserver {

    public SudokuElement() {
        fields = Arrays.asList(new SudokuField[9]);
    }

    public List<SudokuField> getFields() {

        List<SudokuField> listCol = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            SudokuField field = new SudokuField(fields.get(i).getPositionInCol(),
                    fields.get(i).getPositionInRow(),
                    fields.get(i).getNumberOfBox());
            field.setFieldValue(fields.get(i).getFieldValue());
            listCol.set(i, field);
        }
        return listCol;
    }

    private List<SudokuField> fields;

    public boolean verify() {
        return !StaticFunctions.hasDuplicate(fields);
    }

    public void setNumberInArray(Integer pos, SudokuField field) {
        this.fields.set(pos, field);
    }

}
