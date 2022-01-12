package sudoku.exceptions;

public class ObjectNotInDatabase extends DaoException {
    public ObjectNotInDatabase(String message) {
        super(message);
    }

    public ObjectNotInDatabase(String message, Throwable cause) {
        super(message, cause);
    }
}
