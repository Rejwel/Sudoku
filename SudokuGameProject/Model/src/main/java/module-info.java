open module ModelProject {
    requires org.apache.commons.lang3;
    requires java.desktop;

    exports sudoku;
    exports sudoku.elements;
    exports sudoku.solver;
    exports sudoku.difficulty;
}
