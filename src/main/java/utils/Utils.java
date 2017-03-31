package utils;

import java.sql.Connection;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;

import model.dao.computer.ComputerDAOImpl;

public abstract class Utils {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ComputerDAOImpl.class);
    private static final String CONFIG_FILE = "/db.properties";
    private static final HikariConfig CFP = new HikariConfig(CONFIG_FILE);
    private static final HikariDataSource DS = new HikariDataSource(CFP);

    /**
     * Connection to the DataBase.
     * @return a Connection
     * @throws SQLException if SQL fail
     */
    public static Connection open() throws SQLException {
        logger.info("Open Connection");
        Connection conn = DS.getConnection();
        conn.setAutoCommit(true);
        return conn;
    }

    /**
     * Close Connection to the database.
     * @param c a Connection open
     * @throws SQLException if SQL fail
     */
    public static void close(Connection c) throws SQLException {
        logger.info("Disconnection to the database");
        if (c != null) {
            c.close();
        }
    }

    /**
     * RollbacK, SetAutoCommit(true) and Close Connection to the database.
     * @param c a Connection open
     */
    public static void rollbackClose(Connection c) {
        try {
            logger.info("RollBack and Disconnection to the database");
            if (c != null) {
                c.rollback();
                c.setAutoCommit(true);
                c.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    /**
     * Commit, SetAutoCommit(true) and Close Connection to the database.
     * @param c a Connection open
     */
    public static void commitClose(Connection c) {
        try {
            logger.info("Commit and Disconnection to the database");
            if (c != null) {
                c.commit();
                c.setAutoCommit(true);
                c.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
}
