package sudoku.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import sudoku.exceptions.DaoException;


public interface Dao<T> extends AutoCloseable {
    T read() throws DaoException;

    ArrayList<T> readAll() throws DaoException, SQLException;

    void write(T object) throws DaoException, SQLException;
}
