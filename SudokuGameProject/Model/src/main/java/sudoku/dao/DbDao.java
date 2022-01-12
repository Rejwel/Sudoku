package sudoku.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import sudoku.exceptions.DaoException;
import sudoku.exceptions.DatabaseConnectionError;
import sudoku.exceptions.DatabaseGeneralError;


public interface DbDao<T> extends AutoCloseable {
    void connect() throws DaoException;

    void createDB() throws SQLException, DaoException;

    int insertInto(T obj, String name) throws DaoException, SQLException;

    T get(String name) throws DaoException, SQLException;

    ArrayList<String> getAll() throws DatabaseConnectionError, DatabaseGeneralError, SQLException;

    void deleteRecord(String name) throws DaoException, SQLException;
}
