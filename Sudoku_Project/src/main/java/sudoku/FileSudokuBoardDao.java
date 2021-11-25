package sudoku;

import java.io.*;
import java.sql.SQLOutput;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {

    private String fileName;

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public SudokuBoard read() {
        try (
                FileInputStream f = new FileInputStream(fileName + ".txt");
                ObjectInputStream o = new ObjectInputStream(f);
        ) {
            SudokuBoard board = (SudokuBoard) o.readObject();
            return board;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(SudokuBoard object) {
        try (
                FileOutputStream f = new FileOutputStream(fileName + ".txt");
                ObjectOutputStream o = new ObjectOutputStream(f)
        ) {
            o.writeObject(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        System.out.println("Zamykanie");
    }
}
