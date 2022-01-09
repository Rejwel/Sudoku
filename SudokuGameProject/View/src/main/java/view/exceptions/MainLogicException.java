package view.exceptions;

import sudoku.exceptions.SudokuException;

public class MainLogicException extends SudokuException {
    public MainLogicException(String message) {
        super(message);
    }

    public MainLogicException(String message, Throwable cause) {
        super(message, cause);
    }
}
