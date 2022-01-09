package sudoku.difficulty;

import java.util.Random;
import sudoku.elements.SudokuBoard;
import sudoku.exceptions.SetLevelException;

public enum Level {
    EASY(10), MEDIUM(25), HARD(50);

    private final int level;

    Level(int i) {
        this.level = i;
    }


    public void removeFieldsFromBoard(SudokuBoard board) throws SetLevelException {
        try {
            int counter = level;
            Random random = new Random();
            while (counter > 0) {
                int x = random.nextInt(9);
                int y = random.nextInt(9);
                if (board.get(x, y) != 0) {
                    board.set(x, y, 0);
                    counter--;
                }
            }
        } catch (Exception e) {
            throw new SetLevelException("SetLevelException");
        }
    }
}
