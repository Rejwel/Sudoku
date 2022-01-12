package sudoku.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import sudoku.exceptions.DaoException;


public interface DbDao<T> extends AutoCloseable {
    void connect() throws DaoException;

    void createDB() throws SQLException, DaoException;

    int insertInto(T obj, String name) throws DaoException, SQLException;

    ArrayList<T> get(String name) throws DaoException, SQLException;
}
