package sudoku.dao;

import org.apache.log4j.Logger;
import sudoku.StaticFunctions;
import sudoku.elements.SudokuBoard;
import sudoku.exceptions.DaoException;
import sudoku.exceptions.GetSetException;
import sudoku.exceptions.SudokuElementConstructorException;
import sudoku.solver.BacktrackingSudokuSolver;

import java.sql.*;
import java.util.ArrayList;

public class JdbcSudokuBoardDao implements DBDao<SudokuBoard>, Dao<SudokuBoard>, AutoCloseable {

    private static final String stringDbUrl = "jdbc:derby:boardsDB;create=true";
    private Logger log = Logger.getLogger(StaticFunctions.class.getName());

    private Connection conn;
    private String name;

    public JdbcSudokuBoardDao() throws DaoException, SQLException {
        System.setProperty("derby.language.sequence.preallocator", "1");
        createDB();
    }


    public JdbcSudokuBoardDao(String name) throws DaoException, SQLException {
        this.name = name;
        System.setProperty("derby.language.sequence.preallocator", "1");
        createDB();
    }

    @Override
    public void createDB() throws DaoException, SQLException {
        createBoardsTable();
        createGameBoardsTable();
    }

    @Override
    public void connect() throws DaoException {
        try {
            conn = DriverManager.getConnection(stringDbUrl);
        } catch (Exception e) {
            log.error(e);
            throw new DaoException("DaoException", e);
        }
    }

    private void createBoardsTable() throws DaoException, SQLException {
        connect();
        try (Statement stmt = conn.createStatement()){
            stmt.executeUpdate(
                    "CREATE TABLE boards (" +
                            "board_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                            "board_name varchar(30)," +
                            "PRIMARY KEY (board_id))");
            conn.close();
        } catch (SQLException e) {
            if(e.getSQLState().equals("X0Y32")) return;
            log.error(e);
            conn.close();
            throw new DaoException("DaoException", e);
        }
    }

    private void createGameBoardsTable() throws DaoException, SQLException {
        connect();
        try (Statement stmt = conn.createStatement()){
            stmt.executeUpdate(
                    "CREATE TABLE game_boards (" +
                            "game_board_id INT REFERENCES boards(board_id), " +
                            "board_saved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                            "board_values varchar(255))");
            conn.close();
        } catch (SQLException e) {
            if(e.getSQLState().equals("X0Y32")) return;
            log.error(e);
            conn.close();
            throw new DaoException("DaoException", e);
        }
    }


    @Override
    public int insertInto(SudokuBoard obj, String name) throws DaoException, SQLException {
        connect();
        try (Statement stmt = conn.createStatement()) {

            PreparedStatement preparedStmt;
            String query;
            int boardDBId = -1;

            query = "SELECT board_id FROM boards WHERE board_name LIKE ?";
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, name);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) {
                boardDBId = rs.getInt(1);
            }

            if(boardDBId == -1) {
                query = "INSERT INTO boards (board_name) VALUES (?)";
                preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString(1, name);
                preparedStmt.executeUpdate();

                query = "SELECT board_id FROM boards WHERE board_name LIKE ?";
                preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString(1, name);
                rs = preparedStmt.executeQuery();
                while (rs.next()) {
                    boardDBId = rs.getInt(1);
                }
            }

            query = "INSERT INTO game_boards (game_board_id, board_values) VALUES (?, ?)";
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, boardDBId);
            preparedStmt.setString(2, StaticFunctions.toDBString(obj));
            preparedStmt.executeUpdate();

            conn.close();
            return boardDBId;
        } catch (Exception e) {
            log.error(e);
            conn.close();
            throw new DaoException("DaoException", e);
        }
    }

    @Override
    public ArrayList<SudokuBoard> get(String name) throws DaoException, SQLException {
        connect();

        int boardDBId = -1;
        String query = "SELECT board_id FROM boards WHERE board_name LIKE ?";

        try (PreparedStatement boardId = conn.prepareStatement(query)) {
            boardId.setString(1, name);
            ResultSet rs = boardId.executeQuery();
            while (rs.next()) {
                boardDBId = rs.getInt(1);
            }

            //TODO: CHANGE THIS TO EXCEPTION OR STH
            if(boardDBId == -1) throw new DaoException("DaoException");

            query = "SELECT * FROM game_boards WHERE game_board_id = ?";
            try(PreparedStatement boards = conn.prepareStatement(query)) {
                boards.setInt(1, boardDBId);

                ArrayList<SudokuBoard> sudokuBoards = new ArrayList<>();

                rs = boards.executeQuery();
                while (rs.next()) {
                    String boardValues = rs.getString(3);
                    sudokuBoards.add(stringToBoard(boardValues));
                }
                return sudokuBoards;
            }
        } catch (Exception e) {
            log.error(e);
            conn.close();
            throw new DaoException("DaoException", e);
        }
    }

    private SudokuBoard stringToBoard(String board) throws SudokuElementConstructorException, GetSetException {
        String[] parts = board.split(",");
        SudokuBoard boardObj = new SudokuBoard(new BacktrackingSudokuSolver());
        int counter = 0;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boardObj.set(i,j, Integer.parseInt(parts[counter++]));
            }
        }

        return boardObj;
    }

    @Override
    public void close() throws Exception {
        conn.close();
    }

    @Override
    public SudokuBoard read() throws DaoException {
        throw new DaoException("DaoException");
    }

    @Override
    public ArrayList<SudokuBoard> readAll() throws DaoException, SQLException {
        try {
            return get(this.name);
        } catch (Exception e) {
            throw new DaoException("DaoException", e);
        }
    }

    @Override
    public void write(SudokuBoard object) throws DaoException, SQLException {
        try {
            insertInto(object, this.name);
        } catch (Exception e) {
            throw new DaoException("DaoException", e);
        }
    }
}
