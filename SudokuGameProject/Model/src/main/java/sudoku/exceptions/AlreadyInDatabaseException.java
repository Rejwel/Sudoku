package sudoku.exceptions;

public class AlreadyInDatabaseException extends DaoException {
    public AlreadyInDatabaseException(String message) {
        super(message);
    }

    public AlreadyInDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
