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
    public static Connection openConnection() throws SQLException {
        logger.info("Open Connection");
        return DS.getConnection();
    }

    /**
     * Close Connection to the database.
     * @param c a Connection open
     * @throws SQLException if SQL fail
     */
    public static void closeConnection(Connection c) throws SQLException {
        logger.info("Disconnection to the database");
        c.close();
    }
}
