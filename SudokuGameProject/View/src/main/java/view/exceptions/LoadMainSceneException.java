package view.exceptions;

import sudoku.exceptions.SudokuException;

public class LoadMainSceneException extends SudokuException {
    public LoadMainSceneException(String message) {
        super(message);
    }

    public LoadMainSceneException(String message, Throwable cause) {
        super(message, cause);
    }
}
