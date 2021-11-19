package sudoku;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class SudokuElement {

    private List<SudokuField> fields;

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

    public boolean verify() {
        return !StaticFunctions.hasDuplicate(fields);
    }

    public void setNumberInArray(Integer pos, SudokuField field) {
        this.fields.set(pos, field);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("fields", fields).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SudokuField)) {
            return false;
        }
        SudokuElement that = (SudokuElement) o;
        return new EqualsBuilder()
                .append(this.fields, that.fields)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.fields)
                .toHashCode();
    }
}
