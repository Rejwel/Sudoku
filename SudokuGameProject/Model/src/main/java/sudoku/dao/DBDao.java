package sudoku.dao;

import sudoku.exceptions.DaoException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DBDao<T> extends AutoCloseable {
    void connect() throws DaoException;

    void createDB() throws SQLException, DaoException;

    int insertInto(T obj, String name) throws DaoException, SQLException;

    ArrayList<T> get(String name) throws DaoException, SQLException;
}
