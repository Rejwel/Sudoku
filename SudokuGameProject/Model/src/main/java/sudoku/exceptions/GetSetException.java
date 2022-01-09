package sudoku.exceptions;

public class GetSetException extends SudokuException {
    public GetSetException(String message) {
        super(message);
    }

    public GetSetException(String message,
                           Throwable cause) {
        super(message, cause);
    }
}
