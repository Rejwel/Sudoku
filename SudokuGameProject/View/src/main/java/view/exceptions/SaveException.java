package view.exceptions;

import sudoku.exceptions.SudokuException;

public class SaveException extends SudokuException {
    public SaveException(String message) {
        super(message);
    }

    public SaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
