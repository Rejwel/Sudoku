package sudoku.elements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import sudoku.StaticFunctions;

public abstract class SudokuElement implements Serializable, Cloneable {

    private List<SudokuField> fields;

    public SudokuElement() {
        fields = Arrays.asList(new SudokuField[9]);
    }

    public List<SudokuField> getFields() throws CloneNotSupportedException {

        List<SudokuField> listCol = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            listCol.set(i, fields.get(i).clone());
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
        if (getClass() != o.getClass()) {
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

    @Override
    public SudokuElement clone() throws CloneNotSupportedException {
        SudokuElement clone = (SudokuElement) super.clone();
        clone.fields = new ArrayList<>(fields);
        for (int i = 0; i < 9; i++) {
            clone.fields.set(i, fields.get(i).clone());
        }
        return clone;
    }
}
