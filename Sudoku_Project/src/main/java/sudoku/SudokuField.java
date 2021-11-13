package sudoku;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SudokuField {

    private PropertyChangeSupport support;
    private int value;
    private final int positionInRow;
    private int positionInCol;
    private int numberOfBox;
    private int positionInBox;

    public SudokuField(int i, int j, int numberOfBox) {
        this.support = new PropertyChangeSupport(this);
        this.value = 0;
        this.positionInRow = j;
        this.positionInCol = i;
        this.numberOfBox = numberOfBox;
        this.positionInBox = getPositionInBox(j,i);
    }

    public void setFieldValue(Integer value) {
        if (value >= 0 && value <= 9 && this.value != value) {
            int oldVal = this.value;
            this.value = value;
            support.firePropertyChange("value", oldVal, (int)value);
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
}
