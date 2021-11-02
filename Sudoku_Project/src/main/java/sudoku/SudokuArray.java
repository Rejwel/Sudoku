package sudoku;

public interface SudokuArray {
    boolean verify();
    void setNumberInArray(Integer pos, Integer value);
}