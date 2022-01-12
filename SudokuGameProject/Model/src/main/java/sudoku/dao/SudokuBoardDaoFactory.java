package sudoku.dao;

import org.apache.log4j.Logger;
import sudoku.StaticFunctions;
import sudoku.elements.SudokuBoard;
import sudoku.exceptions.DaoException;

public final class SudokuBoardDaoFactory {

    private static Logger log = Logger.getLogger(StaticFunctions.class.getName());

    private SudokuBoardDaoFactory() {
    }

    public static Dao<SudokuBoard> getFileDao(String fileName) throws Exception {
        try (
            Dao<SudokuBoard> dao = new FileSudokuBoardDao(fileName)
        ) {
            return dao;
        }
    }

    public static DBDao<SudokuBoard> getDatabaseDao() {
        try (
                DBDao<SudokuBoard> dao = new JdbcSudokuBoardDao()
        ) {
            return dao;
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    public static DBDao<SudokuBoard> getDatabaseDao(String boardName) {
        try (
                DBDao<SudokuBoard> dao = new JdbcSudokuBoardDao(boardName)
        ) {
            return dao;
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    public static Dao<SudokuBoard> JdbcSudokuBoardDao() {
        try (
                Dao<SudokuBoard> dao = new JdbcSudokuBoardDao()
        ) {
            return dao;
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }
}
