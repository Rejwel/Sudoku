package sudoku.exceptions;

public class SudokuElementConstructorException extends SudokuException {
    public SudokuElementConstructorException(String message) {
        super(message);
    }

    public SudokuElementConstructorException(String message,
                                             Throwable cause) {
        super(message, cause);
    }
}
