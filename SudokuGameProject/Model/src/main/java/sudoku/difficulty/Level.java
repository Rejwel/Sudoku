package sudoku.difficulty;

import java.util.Random;
import sudoku.elements.SudokuBoard;

abstract class Level implements Difficulty {

    private final int counter;

    protected Level(int counter) {
        this.counter = counter;
    }

    @Override
    public void removeFieldsFromBoard(SudokuBoard board) {
        int counter = this.counter;
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
