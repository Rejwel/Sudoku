package sudoku.dao;

import java.io.IOException;
import sudoku.exceptions.CalculationsException;
import sudoku.exceptions.DaoException;


public interface Dao<T> extends AutoCloseable {
    T read() throws DaoException;

    void write(T object) throws DaoException;
}
