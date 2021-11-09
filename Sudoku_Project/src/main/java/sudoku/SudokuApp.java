package sudoku;


public class SudokuApp {
    public static void main(String[] args) {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();
        StaticFunctions.printBoard(board);

        System.out.println();
        SudokuElement col = board.getSudokuBox(0);
        for (SudokuField x : col.getFields()) {
            System.out.print(x.getFieldValue() + " ");
        }

        board.set(0,0,1);
        board.set(1,0,2);
        board.set(2,0,3);
        board.set(3,0,4);
        board.set(0,1,2);
        board.set(0,2,3);

        System.out.println();
        System.out.println();
        StaticFunctions.printBoard(board);
        System.out.println();
        for (SudokuField x : col.getFields()) {
            System.out.print(x.getFieldValue() + " ");
        }
        System.out.println();
        col = board.getSudokuBox(0);
        for (SudokuField x : col.getFields()) {
            System.out.print(x.getFieldValue() + " ");
        }
    }
}
