package sudoku;

import java.io.IOException;

public class SudokuBoardDaoFactory {

    public SudokuBoardDaoFactory() {
    }

    Dao<SudokuBoard> getFileDao(String fileName) throws Exception {
        try (
                Dao<SudokuBoard> dao = new FileSudokuBoardDao(fileName);
        ) {
            return dao;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
