package sudoku;

import javax.swing.*;
import java.io.*;

public class FileSudokuBoardDao implements Dao<SudokuBoard>,AutoCloseable{

    private String fileName;

    public FileSudokuBoardDao(String fileName) {

        this.fileName = fileName;
    }

    @Override
    public SudokuBoard read() throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(fileName + ".txt"));
        String linia;
        BacktrackingSudokuSolver back = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(back);
        String[] tablica;
        int counter = 0;
        while ((linia = fileReader.readLine()) != null) {
            tablica = linia.split(",");
            for (int i = 0; i < 9; i++) {
                board.set(counter, i, Integer.parseInt(tablica[i]));
            }
            counter++;
        }
        fileReader.close();
        return board;
    }

    @Override
    public void write(SudokuBoard object) throws IOException {
        FileWriter fw = new FileWriter(fileName + ".txt");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                fw.write(object.get(i, j) + ",");
            }
            fw.write("\n");
        }
        fw.close();
    }

    @Override
    public void close() throws Exception {
        
    }
}
