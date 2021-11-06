package sudoku;

import java.util.ArrayList;
import java.util.List;

public class SudokuField {

    public SudokuField(SudokuElement row,
                       SudokuElement col,
                       SudokuElement box,
                       Integer currentRow,
                       Integer currentColumn) {

        this.value = 0;
        this.positionInRow = currentColumn;
        this.positionInCol = currentRow;
        this.positionInBox = countPositionInBox();
        this.row = row;
        this.col = col;
        this.box = box;
        elements.add(this.row);
        elements.add(this.col);
        elements.add(this.box);

    }

    private int value;
    private SudokuElement row;
    private SudokuElement col;
    private SudokuElement box;
    private Integer positionInRow;
    private Integer positionInCol;
    private Integer positionInBox;

    private List<SudokuObserver> elements = new ArrayList<>(3);

    public void setValue(Integer value) {

        if (value >= 0 && value <= 9) {
            this.value = value;
            row.setNumberInArray(positionInRow, this);
            col.setNumberInArray(positionInCol, this);
            box.setNumberInArray(positionInBox, this);
        }

        checkValueCorrectness();
    }

    private boolean checkValueCorrectness() {
        boolean isValid = true;
        for (SudokuObserver element : elements) {
            isValid = element.verify();
            if (!isValid) {
                System.out.println("Wartosc nie pasuje!");
                break;
            }
        }
        return isValid;
    }

    public Integer getValue() {
        return value;
    }

    private Integer countPositionInBox() {

        int startingRowNumberPosition = this.positionInRow - (this.positionInRow % 3);
        int startingColNumberPosition = this.positionInCol - (this.positionInCol % 3);

        int calculatedRowNumberPosition = this.positionInRow - startingRowNumberPosition;
        int calculatedColNumberPosition = this.positionInCol - startingColNumberPosition;

        return (3 * calculatedColNumberPosition) + calculatedRowNumberPosition;

    }

}
