package service.utils;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by ebiz on 25/04/17.
 */
public class Utils {
    private static Logger logger = LoggerFactory.getLogger(Utils.class);
    private static com.zaxxer.hikari.HikariDataSource dataSource;

    public static void setDataSource(HikariDataSource dataSource) {
        Utils.dataSource = dataSource;
    }

    /**
     * Open a Connection who take care if transaction or not.
     * @return a connection
     */
    public static Connection getConnection() {
        try {
            logger.debug("Getting connection");
            return DataSourceUtils.getConnection(dataSource); //use utils for transactionManager
        } catch (Exception e) {
            logger.error("Error getting connection" + e.getMessage() +  e.getStackTrace());
        }
        return null;
    }



    /**
     * Close ResultSet, Statement and Connection.
     * @param c connection to close
     * @param s statement to close
     * @param r resultSet to close
     */
    public static void close(Connection c, Statement s, ResultSet r) {
        try {
            if (r != null) {
                r.close();
            }
            if (s != null) {
                s.close();
            }
            if (c != null) {
                c.setAutoCommit(true);
                c.close();
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        }
    }

    /**
     * Close ResultSet, Statement.
     * @param c connection to close
     * @param s statement to close
     */
    public static void close(Connection c, Statement s) {
        close(c, s, null);
    }
}
