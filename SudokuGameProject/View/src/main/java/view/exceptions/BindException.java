package view.exceptions;

import sudoku.exceptions.SudokuException;

public class BindException extends SudokuException {
    public BindException(String message) {
        super(message);
    }

    public BindException(String message, Throwable cause) {
        super(message, cause);
    }
}
