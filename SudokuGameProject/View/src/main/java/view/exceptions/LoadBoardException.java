package view.exceptions;

import sudoku.exceptions.SudokuException;

public class LoadBoardException extends SudokuException {
    public LoadBoardException(String message) {
        super(message);
    }

    public LoadBoardException(String message, Throwable cause) {
        super(message, cause);
    }
}
