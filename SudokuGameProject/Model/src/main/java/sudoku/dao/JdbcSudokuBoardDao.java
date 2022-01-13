package sudoku.dao;

import java.sql.*;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import sudoku.StaticFunctions;
import sudoku.elements.SudokuBoard;
import sudoku.exceptions.*;
import sudoku.solver.BacktrackingSudokuSolver;

public class JdbcSudokuBoardDao implements DbDao<SudokuBoard>, Dao<SudokuBoard>, AutoCloseable {

    private static final String stringDbUrl = "jdbc:derby:boardsDB;create=true";
    private final Logger log = Logger.getLogger(StaticFunctions.class.getName());

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
    public void connect() throws DatabaseConnectionError {
        try {
            conn = DriverManager.getConnection(stringDbUrl);
            conn.setAutoCommit(false);
        } catch (Exception e) {
            log.error(e);
            throw new DatabaseConnectionError("DatabaseConnectionError", e);
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
            conn.commit();
        } catch (SQLException e) {
            if(e.getSQLState().equals("X0Y32")) {
                conn.rollback();
                return;
            }
            log.error(e);
            throw new DatabaseGeneralError("DatabaseGeneralError", e);
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
                            "field_value SMALLINT NOT NULL)");
            conn.commit();
        } catch (SQLException e) {
            if(e.getSQLState().equals("X0Y32")) {
                conn.rollback();
                return;
            }
            log.error(e);
            throw new DatabaseGeneralError("DatabaseGeneralError", e);
        }
    }

    @Override
    public int insertInto(SudokuBoard obj, String name) throws DaoException, SQLException {
        connect();

        String query;

        try {

        if(!boardAlreadyInDatabase(name)) {
            connect();
            query = "INSERT INTO boards (board_name) VALUES (?)";
            try (PreparedStatement preparedStmt = conn.prepareStatement(query)) {
                preparedStmt.setString(1, name);
                preparedStmt.executeUpdate();
                conn.commit();
            }

            int boardDBId = getIdFromName(name);

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    query = "INSERT INTO game_boards (game_board_id, board_x, board_y, field_value) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement preparedStmt = conn.prepareStatement(query)) {
                        preparedStmt.setInt(1, boardDBId);
                        preparedStmt.setInt(2, i);
                        preparedStmt.setInt(3, j);
                        preparedStmt.setInt(4, obj.get(i, j));
                        preparedStmt.executeUpdate();
                        conn.commit();
                    }
                }
            }
            return boardDBId;
        }
            conn.rollback();
            throw new AlreadyInDatabaseException("AlreadyInDatabaseException");
        } catch(AlreadyInDatabaseException | GetSetException e){
            log.error(e);
            conn.rollback();
            throw new AlreadyInDatabaseException("AlreadyInDatabaseException", e);
        }
    }

    @Override
    public SudokuBoard get(String name) throws DaoException, SQLException {
        connect();
        try {
            int boardDBId = getIdFromName(name);
            return getSudokuBoardFromDatabaseId(boardDBId);
        } catch (Exception e) {
            log.error(e);
            conn.rollback();
            throw new DatabaseGeneralError("DatabaseGeneralError", e);
        }
    }

    @Override
    public ArrayList<String> getAll() throws DatabaseConnectionError, DatabaseGeneralError, SQLException {
        connect();

        ArrayList<String> boardNames = new ArrayList<>();
        String query = "SELECT board_name FROM boards";

        try(PreparedStatement boardId = conn.prepareStatement(query)) {
            try(ResultSet rs = boardId.executeQuery()) {
                conn.commit();
                while (rs.next()) {
                    boardNames.add(rs.getString(1));
                }
                return boardNames;
            }
        } catch (Exception e) {
            log.error(e);
            conn.rollback();
            throw new DatabaseGeneralError("DatabaseGeneralError", e);
        }
    }

    @Override
    public Boolean checkIfExsist(String name) throws SQLException, DaoException {
        return boardAlreadyInDatabase(name);
    }

    @Override
    public void deleteRecord(String name) throws DaoException, SQLException {
        connect();

        int boardDBId = getIdFromName(name);

        String query1 = "DELETE FROM boards WHERE board_id = ?";
        String query2 = "DELETE FROM game_boards WHERE game_board_id = ?";
        try(
            PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
            PreparedStatement preparedStmt2 = conn.prepareStatement(query2)
        ) {
            preparedStmt1.setInt(1, boardDBId);
            preparedStmt2.setInt(1, boardDBId);
            preparedStmt2.executeUpdate();
            preparedStmt1.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            log.error(e);
            conn.rollback();
            throw new DatabaseGeneralError("DatabaseGeneralError", e);
        }
    }

    private int getIdFromName(String name) throws DaoException, SQLException {
        connect();

        int boardDBId = -1;
        String query = "SELECT board_id FROM boards WHERE board_name LIKE ?";

        try(PreparedStatement boardId = conn.prepareStatement(query)) {
            boardId.setString(1, name);
            try(ResultSet rs = boardId.executeQuery()) {
                conn.commit();
                while (rs.next()) {
                    boardDBId = rs.getInt(1);
                }

                if (boardDBId == -1) {
                    throw new ObjectNotInDatabase("ObjectNotInDatabase");
                }

                return boardDBId;
            }
        } catch (Exception e) {
            log.error(e);
            conn.rollback();
            throw new DatabaseGeneralError("DatabaseGeneralError", e);
        }
    }

    private Boolean boardAlreadyInDatabase(String name) throws DaoException, SQLException {
        connect();

        int boardDBId = -1;
        String query = "SELECT board_id FROM boards WHERE board_name LIKE ?";

        try(PreparedStatement boardId = conn.prepareStatement(query)) {
            boardId.setString(1, name);
            try(ResultSet rs = boardId.executeQuery()) {
                conn.commit();
                while (rs.next()) {
                    boardDBId = rs.getInt(1);
                }

                if (boardDBId == -1) {
                    conn.rollback();
                    return false;
                }

                return true;
            }
        } catch (Exception e) {
            log.error(e);
            conn.rollback();
            throw new DatabaseGeneralError("DatabaseGeneralError", e);
        }
    }

    private SudokuBoard getSudokuBoardFromDatabaseId(int boardId) throws DaoException, SQLException, SudokuElementConstructorException {
        connect();

        String query = "SELECT * FROM game_boards WHERE game_board_id = ?";
        try(PreparedStatement boards = conn.prepareStatement(query)) {
            boards.setInt(1, boardId);
            SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
            try(ResultSet rs = boards.executeQuery()) {
                conn.commit();
                rs.next();
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        sudokuBoard.set(rs.getInt(2), rs.getInt(3), rs.getInt(4));
                        rs.next();
                    }
                }
                return sudokuBoard;
            } catch (Exception e) {
                log.error(e);
                conn.rollback();
                throw new ObjectNotInDatabase("ObjectNotInDatabase", e);
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
