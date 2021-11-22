package sudoku;

public class SudokuBoardDaoFactory {
    public SudokuBoardDaoFactory() {
    }

    Dao getFileDao(String fileName) {
        try(FileSudokuBoardDao dao =  new FileSudokuBoardDao(fileName)){
            return dao;
        } catch (Exception e) {
            throw new RuntimeException("Cos");
        }
    }
}
