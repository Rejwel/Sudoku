package view.exceptions;

import sudoku.exceptions.SudokuException;

public class LanguageException extends SudokuException {
    public LanguageException(String message) {
        super(message);
    }

    public LanguageException(String message, Throwable cause) {
        super(message, cause);
    }
}
