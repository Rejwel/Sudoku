package sudoku.elements;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SudokuField implements Serializable, Cloneable, Comparable<SudokuField> {

    private PropertyChangeSupport support;
    private int value;
    private final int positionInRow;
    private final int positionInCol;
    private final int numberOfBox;
    private final int positionInBox;

    public SudokuField(int i, int j, int numberOfBox) {
        this.support = new PropertyChangeSupport(this);
        this.value = 0;
        this.positionInRow = j;
        this.positionInCol = i;
        this.numberOfBox = numberOfBox;
        this.positionInBox = getPositionInBox(j, i);
    }

    public void setFieldValue(Integer value) {
        if (value >= 0 && value <= 9 && this.value != value) {
            int oldVal = this.value;
            this.value = value;
            support.firePropertyChange("value", oldVal, (int) value);
        }
    }

    public void addListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public SudokuField getField() {
        return this;
    }

    public Integer getFieldValue() {
        return value;
    }

    public int getNumberOfBox() {
        return this.numberOfBox;
    }

    public int getPositionInRow() {
        return this.positionInRow;
    }

    public int getPositionInCol() {
        return this.positionInCol;
    }

    private Integer getPositionInBox(int positionInRow, int positionInCol) {

        int startingRowNumberPosition = positionInRow - (positionInRow % 3);
        int startingColNumberPosition = positionInCol - (positionInCol % 3);

        int calculatedRowNumberPosition = positionInRow - startingRowNumberPosition;
        int calculatedColNumberPosition = positionInCol - startingColNumberPosition;

        return 3 * calculatedColNumberPosition + calculatedRowNumberPosition;
    }

    public int getPositionInBox() {
        return this.positionInBox;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .append("positionInRow", positionInRow)
                .append("positionInCol", positionInCol)
                .append("numberOfBox", numberOfBox)
                .append("positionInBox", positionInBox).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SudokuField)) {
            return false;
        }
        SudokuField that = (SudokuField) o;
        return new EqualsBuilder()
                .append(this.value, that.value)
                .append(this.positionInRow, that.positionInRow)
                .append(this.positionInCol, that.positionInCol)
                .append(this.numberOfBox, that.numberOfBox)
                .append(this.positionInBox, that.positionInBox)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(value)
                .append(positionInRow)
                .append(positionInCol)
                .append(numberOfBox)
                .append(positionInBox)
                .toHashCode();
    }

    @Override
    public int compareTo(SudokuField o) {
        if (o == null) {
            throw new NullPointerException("Podany obiekt jest nullem");
        }
        return this.value - o.value;
    }

    @Override
    public SudokuField clone() throws CloneNotSupportedException {
        return (SudokuField) super.clone();
    }
}
