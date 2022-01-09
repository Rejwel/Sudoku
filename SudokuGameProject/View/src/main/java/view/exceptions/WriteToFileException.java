package view.exceptions;

import sudoku.exceptions.SudokuException;

public class WriteToFileException extends SudokuException {
    public WriteToFileException(String message) {
        super(message);
    }

    public WriteToFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
