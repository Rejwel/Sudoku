package sudoku;


import sudoku.dao.Dao;
import sudoku.dao.SudokuBoardDaoFactory;
import sudoku.elements.SudokuBoard;
import sudoku.solver.BacktrackingSudokuSolver;
import sudoku.solver.SudokuSolver;

public class SudokuApp {
    public static void main(String[] args) throws Exception {

        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();

        StaticFunctions.printBoard(board);
        System.out.println("\n");

        SudokuBoardDaoFactory daoFactory = new SudokuBoardDaoFactory();

        try (Dao<SudokuBoard> dao = daoFactory.getFileDao("test")) {
            dao.write(board);
            SudokuBoard board2 = dao.read();
            StaticFunctions.printBoard(board2);
        }
    }
}
