package sudoku;


import java.io.IOException;

public class SudokuApp {
    public static void main(String[] args) throws IOException {

        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();

        SudokuElement col = board.getSudokuColumn(0);
        System.out.println(col.toString());

        StaticFunctions.printBoard(board);
        //System.out.println(board.toString());
        //System.out.println(col.toString());
        System.out.println(board.hashCode());
        SudokuBoard board1 = new SudokuBoard(backtracking);
        System.out.println(board.equals(board1));
        System.out.println(board1.hashCode());

        SudokuBoardDaoFactory dao = new SudokuBoardDaoFactory();
        //dao.getFileDao("test").write(board);
        StaticFunctions.printBoard((SudokuBoard) dao.getFileDao("test").read());
    }
}
