package sudoku;


public class SudokuApp {
    public static void main(String[] args) {
        SudokuSolver backtracking = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(backtracking);
        board.solveGame();
        StaticFunctions.printBoard(board);

        SudokuElement list1 = board.getSudokuRow(0);
        System.out.println("Row: ");
        for (Integer x : list1.getArray()) {
            System.out.print(x + " ");

        }
        SudokuElement list2 = board.getSudokuColumn(0);
        System.out.println();
        System.out.println("Column: ");
        for (Integer x : list2.getArray()) {
            System.out.print(x + " ");

        }
        SudokuElement list3 = board.getSudokuBox(0);
        System.out.println();
        System.out.println("Box: ");
        for (Integer x : list3.getArray()) {
            System.out.print(x + " ");

        }
    }
}
