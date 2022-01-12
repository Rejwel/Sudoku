package sudoku.dao;

import java.sql.*;
import org.apache.log4j.Logger;
import sudoku.StaticFunctions;
import sudoku.elements.SudokuBoard;
import sudoku.exceptions.DaoException;
import sudoku.exceptions.SudokuElementConstructorException;
import sudoku.solver.BacktrackingSudokuSolver;


public class JdbcSudokuBoardDao implements DbDao<SudokuBoard>, Dao<SudokuBoard>, AutoCloseable {

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
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(
                    "CREATE TABLE boards ("
                            + "board_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
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
                            "board_x SMALLINT NOT NULL," +
                            "board_y SMALLINT NOT NULL," +
                            "field_value SMALLINT NOT NULL," +
                            "is_disabled BOOLEAN)");
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

        String query;
        int boardDBId;

        if(!boardAlreadyInDatabase(name)) {
            connect();
            query = "INSERT INTO boards (board_name) VALUES (?)";
            try (PreparedStatement preparedStmt = conn.prepareStatement(query)) {
                preparedStmt.setString(1, name);
                preparedStmt.executeUpdate();
            }
        }

        try {
            boardDBId = getIdFromName(name);

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    query = "INSERT INTO game_boards (game_board_id, board_x, board_y, field_value, is_disabled) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement preparedStmt = conn.prepareStatement(query)) {
                        preparedStmt.setInt(1, boardDBId);
                        preparedStmt.setInt(2, i);
                        preparedStmt.setInt(3, j);
                        preparedStmt.setInt(4, obj.get(i, j));
                        preparedStmt.setBoolean(5, false);
                        preparedStmt.executeUpdate();
                    }
                }
            }

            conn.close();
            return boardDBId;
        } catch(Exception e){
            log.error(e);
            conn.close();
            throw new DaoException("DaoException", e);
        }
    }

    @Override
    public SudokuBoard get(String name) throws DaoException, SQLException {
        connect();
        try {
            int boardDBId = getIdFromName(name);
            SudokuBoard board = getSudokuBoardFromDatabaseId(boardDBId);
            conn.close();
            return board;
        } catch (Exception e) {
            log.error(e);
            conn.close();
            throw new DaoException("DaoException", e);
        }
    }

    @Override
    public void deleteRecord(String name) throws DaoException, SQLException {
        connect();

        int boardDBId = getIdFromName(name);

        String query1 = "DELETE FROM boards WHERE board_id LIKE ?";
        String query2 = "DELETE FROM game_boards WHERE game_board_id LIKE ?";
        try(
            PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
            PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
        ) {
            preparedStmt1.setInt(1, boardDBId);
            preparedStmt2.setInt(1, boardDBId);
            preparedStmt1.executeUpdate();
            preparedStmt2.executeUpdate();
            conn.close();
        } catch (Exception e) {
            log.error(e);
            conn.close();
            throw new DaoException("DaoException", e);
        }
    }

    private int getIdFromName(String name) throws DaoException, SQLException {
        connect();

        int boardDBId = -1;
        String query = "SELECT board_id FROM boards WHERE board_name LIKE ?";

        try(PreparedStatement boardId = conn.prepareStatement(query)) {
            boardId.setString(1, name);
            try(ResultSet rs = boardId.executeQuery()) {
                while (rs.next()) {
                    boardDBId = rs.getInt(1);
                }

                if (boardDBId == -1) {
                    conn.close();
                    throw new DaoException("DaoException");
                }

                return boardDBId;
            }
        } catch (Exception e) {
            log.error(e);
            conn.close();
            throw new DaoException("DaoException", e);
        }
    }

    private Boolean boardAlreadyInDatabase(String name) throws DaoException, SQLException {
        connect();

        int boardDBId = -1;
        String query = "SELECT board_id FROM boards WHERE board_name LIKE ?";

        try(PreparedStatement boardId = conn.prepareStatement(query)) {
            boardId.setString(1, name);
            try(ResultSet rs = boardId.executeQuery()) {
                while (rs.next()) {
                    boardDBId = rs.getInt(1);
                }

                if (boardDBId == -1) {
                    conn.close();
                    return false;
                }

                conn.close();
                return true;
            }
        } catch (Exception e) {
            log.error(e);
            conn.close();
            throw new DaoException("DaoException", e);
        }
    }

    private SudokuBoard getSudokuBoardFromDatabaseId(int boardId) throws DaoException, SQLException, SudokuElementConstructorException {
        connect();

        String query = "SELECT * FROM game_boards WHERE game_board_id = ?";
        try(PreparedStatement boards = conn.prepareStatement(query)) {
            boards.setInt(1, boardId);
            SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
            try(ResultSet rs = boards.executeQuery()) {
                rs.next();
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        sudokuBoard.set(rs.getInt(2), rs.getInt(3), rs.getInt(4));
                        rs.next();
                    }
                }
                conn.close();
                return sudokuBoard;
            } catch (Exception e) {
                log.error(e);
                conn.close();
                throw new DaoException("DaoException", e);
            }
        }
    }

    @Override
    public void close() throws Exception {
        conn.close();
    }

    @Override
    public SudokuBoard read() throws DaoException {
        try {
            return get(this.name);
        } catch (Exception e) {
            throw new DaoException("DaoException");
        }
    }

    @Override
    public void write(SudokuBoard object) throws DaoException {
        try {
            insertInto(object, this.name);
        } catch (Exception e) {
            throw new DaoException("DaoException", e);
        }
    }
}
