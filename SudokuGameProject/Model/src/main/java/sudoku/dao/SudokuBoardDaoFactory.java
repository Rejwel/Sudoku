package sudoku.dao;

import sudoku.elements.SudokuBoard;

public class SudokuBoardDaoFactory {

    public SudokuBoardDaoFactory() {
    }

    public Dao<SudokuBoard> getFileDao(String fileName) throws Exception {
        try (
                Dao<SudokuBoard> dao = new FileSudokuBoardDao(fileName);
        ) {
            return dao;
        }
    }
}
