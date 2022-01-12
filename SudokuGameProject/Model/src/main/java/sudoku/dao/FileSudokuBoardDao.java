package sudoku.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import sudoku.elements.SudokuBoard;
import sudoku.exceptions.DaoException;


public class FileSudokuBoardDao implements Dao<SudokuBoard> {

    private String fileName;
    private static Logger log = Logger.getLogger(FileSudokuBoardDao.class.getName());

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public SudokuBoard read() throws DaoException {
        try (
                FileInputStream f = new FileInputStream(fileName);
                ObjectInputStream o = new ObjectInputStream(f)
        ) {
            SudokuBoard board = (SudokuBoard) o.readObject();
            return board;
        } catch (ClassNotFoundException | IOException e) {
            throw new DaoException("DaoException");
        }
    }

    @Override
    public ArrayList<SudokuBoard> readAll() throws DaoException {
        throw new DaoException("DaoException");
    }

    @Override
    public void write(SudokuBoard object) throws DaoException {
        try (
                FileOutputStream f = new FileOutputStream(fileName);
                ObjectOutputStream o = new ObjectOutputStream(f)
        ) {
            o.writeObject(object);
        } catch (IOException e) {
            throw new DaoException("DaoException");
        }
    }

    @Override
    public void close() {
        log.info("Zamykanie");
    }
}
