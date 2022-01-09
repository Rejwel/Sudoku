package view.exceptions;

import sudoku.exceptions.SudokuException;

public class ReadFromFileException extends SudokuException {
    public ReadFromFileException(String message) {
        super(message);
    }

    public ReadFromFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
