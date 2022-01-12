package sudoku.dao;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import sudoku.StaticFunctions;
import sudoku.elements.SudokuBoard;
import sudoku.solver.BacktrackingSudokuSolver;

import java.util.ArrayList;

public class JdbcSudokuBoardDaoTest {

    private static Logger log = Logger.getLogger(StaticFunctions.class.getName());

    DBDao<SudokuBoard> db = SudokuBoardDaoFactory.getDatabaseDao();
    SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());


    public JdbcSudokuBoardDaoTest() throws Exception {
    }

    @Test
    public void test1() throws Exception {

//        board.solveGame();
//
//        db.insertInto(board, "asasdd");

        ArrayList<SudokuBoard> dbBoards = db.get("asasdd");

        for (SudokuBoard board :
                dbBoards) {
            StaticFunctions.printBoard(board);
        }

    }

}
