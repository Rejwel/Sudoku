package sudoku;


public class SudokuApp {
    public static void main(String[] args) {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();
        StaticFunctions.printBoard(board);

        SudokuElement box = board.getSudokuBox(8);
        for (SudokuField x :
                box.getFields()) {
            System.out.println(x.getValue() + " ");
        }

    }
}
