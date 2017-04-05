package model.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import model.dao.computer.ComputerDAOImpl;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public enum DAOFactory {
    INSTANCE;
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ComputerDAOImpl.class);
    private static final String CONFIG_FILE = "/db.properties";
    private static final HikariConfig CFP = new HikariConfig(CONFIG_FILE);
    private static final HikariDataSource DS = new HikariDataSource(CFP);
    private ThreadLocal<Connection> cHolder = new ThreadLocal<>();

    /**
     * .
     */
    DAOFactory() {
        if (cHolder == null) {
            cHolder = new ThreadLocal<>();
        }
    }

    /**
     * Get Instance.
     * @return Instance
     */
    public static DAOFactory getInstance() {
        return DAOFactory.INSTANCE;
    }

    /**
     * Connection to the DataBase.
     */
    public void open() {
        //logger.info("Open Connection");
        try {
            Connection c = cHolder.get();
            if (c == null) {
                c = DS.getConnection();
                cHolder.set(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close Connection to the database.
     */
    public void close() {
        //logger.info("Disconnection to the database");
        Connection c = cHolder.get();
        cHolder.remove();
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Start Transaction.
     */
    public void startTransaction() {
        logger.info("Start Transaction");
        try {
            cHolder.get().setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Commit transaction.
     */
    public void commit() {
        try {
            cHolder.get().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Rollback transaction.
     */
    public void roolback() {
        try {
            cHolder.get().rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close Statement.
     * @param s Statement to close
     */
    public void close(Statement s) {
        if (s != null) {
            try {
                s.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Close Statement and ResultSet.
     * @param s statement to close
     * @param r resultset to close
     */
    public void close(Statement s, ResultSet r) {
        if (r != null) {
            try {
                r.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (s != null) {
            try {
                s.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get connection.
     * @return connection
     */
    public Connection get() {
        if (cHolder.get() == null) {
            open();
        }
        return cHolder.get();
    }
}
