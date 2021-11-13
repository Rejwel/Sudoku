package sudoku;

import java.util.ArrayList;
import java.util.List;

public class SudokuField {

    public SudokuField(int i, int j, int numberOfBox) {
        this.value = 0;
        this.positionInRow = j;
        this.positionInCol = i;
        this.numberOfBox = numberOfBox;
        this.positionInBox = countPositionInBox(j,i);
    }


    private int value;
    private final int positionInRow;
    private int positionInCol;
    private int numberOfBox;
    private int positionInBox;

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

    public int getPositionInBox() {
        return this.positionInBox;
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

    private Integer countPositionInBox(int positionInRow, int positionInCol) {

        int startingRowNumberPosition = positionInRow - (positionInRow % 3);
        int startingColNumberPosition = positionInCol - (positionInCol % 3);

        int calculatedRowNumberPosition = positionInRow - startingRowNumberPosition;
        int calculatedColNumberPosition = positionInCol - startingColNumberPosition;

        return 3 * calculatedColNumberPosition + calculatedRowNumberPosition;

    }

}
