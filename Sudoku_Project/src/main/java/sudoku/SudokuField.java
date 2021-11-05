package sudoku;

import java.util.ArrayList;
import java.util.List;

public class SudokuField {

    public SudokuField(SudokuElement row, SudokuElement col, SudokuElement box, Integer currentRow, Integer currentColumn) {

        this.value = 0;
        this.positionInRow = currentColumn;
        this.positionInCol = currentRow;
        this.positionInBox = countPositionInBox();
        this.row = row;
        this.col = col;
        this.box = box;
        elements.add(row);
        elements.add(col);
        elements.add(box);

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

        if (value >= 0 && value < 9) {
            this.value = value;
            row.setNumberInArray(positionInRow, this);
            col.setNumberInArray(positionInCol, this);
            box.setNumberInArray(positionInBox, this);
        }

        System.out.println("\n");
        for (Integer x :
                row.getArray()) {
            System.out.println(x + " ");
        }
        System.out.println("\n");

//        verifyValueCorrectness();

    }

    public boolean verifyValueCorrectness() {
        boolean isValid = true;
        for (SudokuObserver element : elements) {
            isValid = element.verify();
            if(!isValid) {
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
        int zmiennaWiersz;
        int zmiennaKolumna;
        int counter = 0;

        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                zmiennaWiersz = i;
                zmiennaKolumna = j;
                for (int k = 0; k < 9; k++) {
                    if (k % 3 == 0 && k > 0) {
                        //zmiennaWiersz++;
                        zmiennaWiersz = j;
                        //zmiennaKolumna = j;
                        zmiennaKolumna++;
                    }
                    if (zmiennaWiersz == positionInRow && zmiennaKolumna == positionInCol) {
                        counter = k;
                        return counter;
                    }
                    zmiennaWiersz++;
                    //zmiennaKolumna++;
                }
            }
        }
        return 0;
    }
}
