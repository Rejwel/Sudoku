package view.exceptions;

import sudoku.exceptions.SudokuException;

public class CreateGamePaneException extends SudokuException {
    public CreateGamePaneException(String message) {
        super(message);
    }

    public CreateGamePaneException(String message, Throwable cause) {
        super(message, cause);
    }
}
