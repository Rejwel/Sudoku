package sudoku;

import java.util.ArrayList;
import java.util.List;

public class SudokuField {

    public SudokuField(int i, int j, int numberOfBox) {
        this.value = 0;
        this.positionInRow = j;
        this.positionInCol = i;
        this.numberOfBox = numberOfBox;
    }


    private int value;
    private final int positionInRow;
    private int positionInCol;
    private int numberOfBox;

    public void setFieldValue(Integer value) {
        if (value >= 0 && value <= 9) {
            this.value = value;
        }
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

    public SudokuField getField() {
        return this;
    }

    public Integer getFieldValue() {
        return value;
    }

}
