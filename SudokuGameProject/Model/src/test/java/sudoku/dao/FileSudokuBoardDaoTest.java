package sudoku.dao;

import org.junit.jupiter.api.Test;
import sudoku.StaticFunctions;
import sudoku.elements.*;
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

        SudokuBoardDaoFactory daoFactory = new SudokuBoardDaoFactory();
        try (Dao<SudokuBoard> dao = daoFactory.getFileDao("test")) {
            dao.write(board);
            SudokuBoard board2 = dao.read();
            assertEquals(board, board2);
            assertEquals(board.hashCode(), board2.hashCode());
        }
    }

    @Test
    void daoWriteExceptionTest() throws Exception {
        SudokuElement SudokuRow = new SudokuRow();

        SudokuBoardDaoFactory daoFactory = new SudokuBoardDaoFactory();

        try(Dao dao = daoFactory.getFileDao("test")) {
            assertThrows(ClassCastException.class, () -> dao.write(SudokuRow));
            assertThrows(ClassNotFoundException.class, () -> dao.write(Class.forName("temp")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void daoReadExceptionTest() throws Exception {
        SudokuBoardDaoFactory daoFactory = new SudokuBoardDaoFactory();

        try(Dao dao = daoFactory.getFileDao("test")) {
            SudokuSolver backtracking = new BacktrackingSudokuSolver();
            SudokuBoard board = new SudokuBoard(backtracking);
            board.solveGame();

            try (RandomAccessFile file = new RandomAccessFile("test", "rw")) {
                file.getChannel().lock();
                assertThrows(RuntimeException.class, () -> dao.read());
                assertThrows(RuntimeException.class, () -> dao.write(board));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}