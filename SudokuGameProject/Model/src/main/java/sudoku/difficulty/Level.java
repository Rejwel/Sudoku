package sudoku.difficulty;

import java.util.Random;
import sudoku.elements.SudokuBoard;

public enum Level {
    EASY, MEDIUM, HARD;

    public void setDifficulty(SudokuBoard board) {
        switch (this) {
            case EASY -> removeFieldsFromBoard(board, 10);
            case MEDIUM -> removeFieldsFromBoard(board, 25);
            case HARD -> removeFieldsFromBoard(board, 50);
            default -> throw new RuntimeException("Blad w wybieraniu trudnosci poziomu");
        }
    }

    private void removeFieldsFromBoard(SudokuBoard board, int fieldsToDelete) {
        int counter = fieldsToDelete;
        Random random = new Random();
        while (counter > 0) {
            int x = random.nextInt(9);
            int y = random.nextInt(9);
            if (board.get(x, y) != 0) {
                board.set(random.nextInt(9), random.nextInt(9), 0);
                counter--;
            }
        }
    }
}
