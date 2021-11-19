package sudoku;


public class SudokuApp {
    public static void main(String[] args) {

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

    }
}
