package sudoku;

public class SudokuBoardDaoFactory {
    public SudokuBoardDaoFactory() {
    }

    Dao<SudokuBoard> getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }
}
