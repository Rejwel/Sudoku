package sudoku.exceptions;

public class CalculationsException extends SudokuException {
    public CalculationsException(String message) {
        super(message);
    }

    public CalculationsException(String message,
                                 Throwable cause) {
        super(message, cause);
    }
}
