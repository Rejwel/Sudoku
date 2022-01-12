package sudoku.dao;

import org.junit.jupiter.api.Test;
import sudoku.StaticFunctions;
import sudoku.elements.*;
import sudoku.exceptions.DaoException;
import sudoku.solver.BacktrackingSudokuSolver;
import sudoku.solver.SudokuSolver;

import java.io.IOException;
import java.io.RandomAccessFile;

import static org.junit.jupiter.api.Assertions.*;

class FileSudokuBoardDaoTest {

    @Test
    void daoWriteReadTest() throws Exception {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();

        try (Dao<SudokuBoard> dao = SudokuBoardDaoFactory.getFileDao("test")) {
            dao.write(board);
            SudokuBoard board2 = dao.read();
            assertEquals(board, board2);
            assertEquals(board.hashCode(), board2.hashCode());
        }
    }

    @Test
    void daoWriteExceptionTest() {
        SudokuElement SudokuRow = new SudokuRow();

        try(Dao dao = SudokuBoardDaoFactory.getFileDao("test")) {
            assertThrows(ClassCastException.class, () -> dao.write(SudokuRow));
            assertThrows(ClassNotFoundException.class, () -> dao.write(Class.forName("temp")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void daoReadExceptionTest() {

        try(Dao dao = SudokuBoardDaoFactory.getFileDao("test")) {
            SudokuSolver backtracking = new BacktrackingSudokuSolver();
            SudokuBoard board = new SudokuBoard(backtracking);
            board.solveGame();

            try (RandomAccessFile file = new RandomAccessFile("test", "rw")) {
                file.getChannel().lock();
                assertThrows(DaoException.class, () -> dao.read());
                assertThrows(DaoException.class, () -> dao.write(board));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}