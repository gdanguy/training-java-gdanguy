package model.dao;

import java.sql.SQLException;

public class DAOException extends Exception{
    /**
     * Constructor.
     * @param message message of Exception
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param e Exception
     */
    public DAOException(SQLException e) {
        super(e.getMessage(), e.getCause());
    }
}
