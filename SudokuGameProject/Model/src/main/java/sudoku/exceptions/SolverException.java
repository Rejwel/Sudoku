package sudoku.exceptions;

public class SolverException extends SudokuException {
    public SolverException(String message) {
        super(message);
    }

    public SolverException(String message,
                           Throwable cause) {
        super(message, cause);
    }
}
