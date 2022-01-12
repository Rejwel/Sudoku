package sudoku.dao;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import sudoku.StaticFunctions;
import sudoku.elements.SudokuBoard;
import sudoku.solver.BacktrackingSudokuSolver;

import java.util.ArrayList;

public class JdbcSudokuBoardDaoTest {

    private static Logger log = Logger.getLogger(StaticFunctions.class.getName());

    DbDao<SudokuBoard> db = SudokuBoardDaoFactory.getDatabaseDao();
    Dao<SudokuBoard> dbDao = SudokuBoardDaoFactory.JdbcSudokuBoardDao("testBoard");
    SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());


    public JdbcSudokuBoardDaoTest() throws Exception {
    }

    @Test
    public void test1() throws Exception {

        board.solveGame();
        StaticFunctions.printBoard(board);
//
//        db.insertInto(board, "test2");
//        dbDao.write(board);
//        db.deleteRecord("test3");
//
//        SudokuBoard boardFromDb = dbDao.read();
//
//        StaticFunctions.printBoard(boardFromDb);

//        SudokuBoard dbBoard = db.get("test3");

//        StaticFunctions.printBoard(dbBoard);

    }

}
