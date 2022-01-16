package sudoku.dao;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sudoku.StaticFunctions;
import sudoku.elements.SudokuBoard;
import sudoku.exceptions.*;
import sudoku.solver.BacktrackingSudokuSolver;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcSudokuBoardDaoTest {

    private static Logger log = Logger.getLogger(StaticFunctions.class.getName());
    DbDao<SudokuBoard> db = SudokuBoardDaoFactory.getDatabaseDao();
    SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());


    public JdbcSudokuBoardDaoTest() throws Exception {
    }

    @Test
    public void objExistInDatabaseTest() throws Exception {
        board.solveGame();

        if (!db.exist("testBoard")) {
            db.insertInto(board, "testBoard");
        }

        Assertions.assertTrue(db.exist("testBoard"));

        db.deleteRecord("testBoard");

        Assertions.assertFalse(db.exist("testBoard"));
    }

    @Test
    public void daoSaveAndReadObjectTest() throws Exception {
        board.solveGame();

        if (db.exist("testBoard")) {
            db.deleteRecord("testBoard");
        }

        Dao<SudokuBoard> dbDao = SudokuBoardDaoFactory.jdbcSudokuBoardDao("testBoard");

        assert dbDao != null;
        dbDao.write(board);

        SudokuBoard dbBoard = dbDao.read();

        Assertions.assertEquals(board, dbBoard);

    }

    @Test
    public void sameObjectInDatabase() throws Exception {

        board.solveGame();

        if (!db.exist("testBoard2")) {
            db.insertInto(board, "testBoard2");
        }

        Assertions.assertThrows(AlreadyInDatabaseException.class, () -> db.insertInto(board, "testBoard2"));
    }

    @Test
    public void objectNotInDatabase() throws SQLException, DaoException {
        if (db.exist("testBoard2")) {
            db.deleteRecord("testBoard2");
        }

        Assertions.assertThrows(ObjectNotInDatabase.class, () -> db.get("testBoard2"));
    }

    @Test
    public void deleteFromDatabaseNoObjectWithThisName() throws SQLException, DaoException {
        if (db.exist("testBoard2")) {
            db.deleteRecord("testBoard2");
        }

        Assertions.assertThrows(ObjectNotInDatabase.class, () -> db.deleteRecord("testBoard2"));
    }

    @Test
    public void insertToDbTest() throws SolverException, SQLException, DaoException {
        board.solveGame();

        if (db.exist("testBoard")) {
            db.deleteRecord("testBoard");
        }

        db.insertInto(board, "testBoard");

        Assertions.assertTrue(db.exist("testBoard"));
    }

    @Test
    public void getFromDbTest() throws SolverException, SQLException, DaoException {
        board.solveGame();

        if (db.exist("testBoard")) {
            db.deleteRecord("testBoard");
        }

        db.insertInto(board, "testBoard");

        SudokuBoard dbBoard = db.get("testBoard");

        Assertions.assertNotNull(dbBoard);
    }

    @Test
    public void deleteFromDbTest() throws SolverException, SQLException, DaoException {
        board.solveGame();

        if (!db.exist("testBoard")) {
            db.insertInto(board, "testBoard");
        }

        db.deleteRecord("testBoard");

        Assertions.assertFalse(db.exist("testBoard"));
    }

}
