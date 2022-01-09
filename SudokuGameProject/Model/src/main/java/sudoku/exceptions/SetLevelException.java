package sudoku.exceptions;

public class SetLevelException extends SudokuException {
    public SetLevelException(String message) {
        super(message);
    }

    public SetLevelException(String message,
                             Throwable cause) {
        super(message, cause);
    }
}
