package sudoku.dao;

import org.apache.log4j.Logger;
import sudoku.StaticFunctions;
import sudoku.elements.SudokuBoard;

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

    public static DbDao<SudokuBoard> getDatabaseDao() {
        try (
                DbDao<SudokuBoard> dao = new JdbcSudokuBoardDao()
        ) {
            return dao;
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    public static DbDao<SudokuBoard> getDatabaseDao(String boardName) {
        try (
                DbDao<SudokuBoard> dao = new JdbcSudokuBoardDao(boardName)
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
