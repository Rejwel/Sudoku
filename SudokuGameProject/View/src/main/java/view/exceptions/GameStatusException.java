package view.exceptions;

import sudoku.exceptions.SudokuException;

public class GameStatusException extends SudokuException {
    public GameStatusException(String message) {
        super(message);
    }

    public GameStatusException(String message, Throwable cause) {
        super(message, cause);
    }
}
