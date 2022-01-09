open module ModelProject {
    requires org.apache.commons.lang3;
    requires java.desktop;
    requires log4j;

    exports sudoku;
    exports sudoku.elements;
    exports sudoku.solver;
    exports sudoku.difficulty;
    exports sudoku.dao;
    exports sudoku.exceptions;
}
