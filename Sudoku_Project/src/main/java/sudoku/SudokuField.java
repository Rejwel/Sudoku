package sudoku;

import java.util.ArrayList;
import java.util.List;

public class SudokuField {

    public SudokuField(SudokuArray row, SudokuArray col, SudokuArray box, Integer x, Integer y) {
        this.value = 0;
        this.positionInRow = x;
        this.positionInCol = y;
        this.positionInBox = countPositionInBox();
        this.row = row;
        this.col = col;
        this.box = box;
        elements.add(this.row);
        elements.add(this.col);
        elements.add(this.box);
    }

    private Integer value;
    private SudokuArray row;
    private SudokuArray col;
    private SudokuArray box;
    private Integer positionInRow;
    private Integer positionInCol;
    private Integer positionInBox;

    private List<SudokuArray> elements = new ArrayList<>(3);

    public void setValue(Integer value) {
        Integer savedValue = this.value;

        if (value >= 0 && value <= 9) {
            row.setNumberInArray(positionInRow, value);
            col.setNumberInArray(positionInCol, value);
            box.setNumberInArray(positionInBox, value);
            this.value = value;
        }

        boolean check = true;
        for (SudokuArray element : elements) {
            check = element.verify();
            if(!check) {
                System.out.println("Wartosc nie pasuje!");
                this.value = savedValue;
                break;
            }
        }
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
                        zmiennaWiersz++;
                        zmiennaKolumna = j;
                    }
                    zmiennaKolumna++;
                    if (zmiennaWiersz == positionInRow && zmiennaKolumna == positionInCol) {
                        counter = k;
                        return counter;
                    }
                }
            }
        }
        return 0;
    }
}
