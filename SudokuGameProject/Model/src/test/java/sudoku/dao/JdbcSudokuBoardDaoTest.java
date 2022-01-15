package sudoku.dao;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sudoku.StaticFunctions;
import sudoku.elements.SudokuBoard;
import sudoku.exceptions.AlreadyInDatabaseException;
import sudoku.exceptions.DaoException;
import sudoku.exceptions.SolverException;
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
    public void daoSaveAndReadObjectTest() throws Exception {

//        board.solveGame();
//        db.insertInto(board, "testBoard");
//        Dao<SudokuBoard> dbDao = SudokuBoardDaoFactory.JdbcSudokuBoardDao("testBoard");
//
//        dbDao.write(board);
//
//        SudokuBoard dbBoard = dbDao.read();
//
//        Assertions.assertEquals(board, dbBoard);
    }

    @Test
    public void sameObjectInDatabase() throws Exception {

//        board.solveGame();
//
//        db.insertInto(board, "testBoard2");
//
//        Assertions.assertThrows(AlreadyInDatabaseException.class, () -> db.insertInto(board, "testBoard2"));
    }

    @Test
    public void nullInsert() throws SQLException, DaoException {

        db.deleteRecord("testBoard");
        Assertions.assertThrows(NullPointerException.class, () -> db.insertInto(null, "testBoard"));

    }

    @Test
    public void getAllBoardNames() throws SolverException, SQLException, DaoException {
        board.solveGame();

//        db.insertInto(board, "testBoardds");

//        Assertions.assertTrue(db.getAll().size() > 0);
        List<String> boards = new ArrayList<>();
        boards = db.getAll();

        for (String x :
                boards) {
            log.info(x);
        }

    }

}
