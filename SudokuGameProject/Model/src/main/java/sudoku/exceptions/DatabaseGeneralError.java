package sudoku.exceptions;

public class DatabaseGeneralError extends DaoException {
    public DatabaseGeneralError(String message) {
        super(message);
    }

    public DatabaseGeneralError(String message, Throwable cause) {
        super(message, cause);
    }
}
