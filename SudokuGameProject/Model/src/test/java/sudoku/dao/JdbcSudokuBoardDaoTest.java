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
    SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());


    public JdbcSudokuBoardDaoTest() throws Exception {
    }

    @Test
    public void test1() throws Exception {

        board.solveGame();

        db.insertInto(board, "test7");

//        SudokuBoard dbBoard = db.get("test3");

//        StaticFunctions.printBoard(dbBoard);

    }

}
