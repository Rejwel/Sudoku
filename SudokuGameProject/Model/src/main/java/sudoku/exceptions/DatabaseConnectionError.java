package sudoku.exceptions;

public class DatabaseConnectionError extends DaoException {
    public DatabaseConnectionError(String message) {
        super(message);
    }

    public DatabaseConnectionError(String message, Throwable cause) {
        super(message, cause);
    }
}
