package sudoku.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import sudoku.elements.SudokuBoard;


public class FileSudokuBoardDao implements Dao<SudokuBoard> {

    private String fileName;

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public SudokuBoard read() {
        try (
                FileInputStream f = new FileInputStream(fileName + ".bin");
                ObjectInputStream o = new ObjectInputStream(f);
        ) {
            SudokuBoard board = (SudokuBoard) o.readObject();
            return board;
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(SudokuBoard object) {
        try (
                FileOutputStream f = new FileOutputStream(fileName + ".bin");
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
