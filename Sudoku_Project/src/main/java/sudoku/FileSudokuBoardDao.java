package sudoku;

import java.io.*;
import java.sql.SQLOutput;

public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {

    private String fileName;

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public SudokuBoard read() {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName + ".txt"));) {
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
            return board;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(SudokuBoard object) {
        try (FileWriter fw = new FileWriter(fileName + ".txt");) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    fw.write(object.get(i, j) + ",");
                }
                fw.write("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        System.out.println("Zamykanie");
    }
}
