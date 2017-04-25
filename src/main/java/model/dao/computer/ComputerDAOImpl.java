package model.dao.computer;

import controller.DashboardServlet;
import model.GenericBuilder;
import model.Page;
import model.company.Company;
import model.computer.Computer;
import model.dao.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ComputerDAOImpl implements ComputerDAO {

    private Logger logger = LoggerFactory.getLogger(ComputerDAOImpl.class);
    @Autowired
    private com.zaxxer.hikari.HikariDataSource dataSource;

    public static final String DELETE_SUCCES = "Computers are deleted";


    /**
     * Get the number of Computer.
     * @return the number of computer in DataBase
     * @throws DAOException if no result
     */
    public int count() throws DAOException {
        logger.info("Count computers");
        Connection conn = null;
        PreparedStatement s = null;
        ResultSet r = null;
        try {
            conn = dataSource.getConnection();
            s = conn.prepareStatement("SELECT COUNT(*) FROM computer");
            r = s.executeQuery();
            int result = -1;
            if (r.next()) {
                result = (r.getInt(1));
            }
            return result;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            Utils.close(conn, s, r);
        }
    }

    /**
     * This method returns the page of computers.
     * @param page corresponds to the page's number to be retrieved
     * @return Page<Computer> corresponds to the page
     * @throws DAOException if SQL request fail
     */
    public Page<Computer> getPage(int page) throws DAOException {
        return getPage(page, Page.PAGE_SIZE, DashboardServlet.ORDER_NULL);
    }

    /**
     * This method returns the page of computers with a sizePage of sizePage.
     * @param page     corresponds to the page's number to be retrieved
     * @param sizePage size of a page
     * @param order order of a page
     * @return Page<Computer> corresponds to the page
     * @throws DAOException if SQL request fail
     */
    public Page<Computer> getPage(int page, int sizePage, String order) throws DAOException {
        logger.info("Get page " + page + ", computers of " + sizePage);
        String orderBy = getOrder(order);
        Connection conn = null;
        PreparedStatement s = null;
        ResultSet r = null;
        try {
            conn = dataSource.getConnection();
            s = conn.prepareStatement("SELECT c1.id, c1.name, introduced, discontinued , c2.id, c2.name"
                    + " FROM computer c1"
                    + " LEFT JOIN company c2 ON c1.company_id = c2.id"
                    + orderBy
                    + " LIMIT " + sizePage + " OFFSET " + page * sizePage);
            r = s.executeQuery();
            ArrayList<Computer> result = new ArrayList<>();
            while (r.next()) {
                result.add(makeComputerWithResultSet(r));
            }
            return new Page<Computer>(result, page, sizePage);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        } finally {
            Utils.close(conn, s, r);
        }
    }

    /**
     * Get the ORDER BY of the request.
     * @param order of the page
     * @return String
     */
    private String getOrder(String order) {
        String result = " ORDER BY ";
        switch (order) {
            default: return "";
            case DashboardServlet.ORDER_NAME_ASC     : return (result + " c1.name ASC");
            case DashboardServlet.ORDER_NAME_DESC    : return (result + " c1.name DESC");
            case DashboardServlet.ORDER_INTRO_ASC    : return (result + " introduced ASC");
            case DashboardServlet.ORDER_INTRO_DESC   : return (result + " introduced DESC");
            case DashboardServlet.ORDER_DISCO_ASC    : return (result + " discontinued ASC");
            case DashboardServlet.ORDER_DISCO_DESC   : return (result + " discontinued DESC");
            case DashboardServlet.ORDER_COMPANY_ASC  : return (result + " c2.name ASC");
            case DashboardServlet.ORDER_COMPANY_DESC : return (result + " c2.name DESC");
        }
    }

    /**
     * This method returns the page of computers with a sizePage of sizePage.
     * @param search word researched
     * @return Page<Computer> corresponds to the page
     * @throws DAOException if SQL request fail
     */
    public Page<Computer> getPage(String search) throws DAOException {
        logger.info("Get Search computers : " + search);
        Connection conn = null;
        PreparedStatement s = null;
        ResultSet r = null;
        try {
            conn = dataSource.getConnection();
            s = conn.prepareStatement("SELECT c1.id, c1.name, introduced, discontinued , c2.id, c2.name"
                    + " FROM computer c1"
                    + " LEFT JOIN company c2 ON c1.company_id = c2.id"
                    + " WHERE c1.name LIKE '%" + search + "%'"
                    + " OR c2.name LIKE '%" + search + "%'");
            r = s.executeQuery();
            ArrayList<Computer> result = new ArrayList<>();
            while (r.next()) {
                result.add(makeComputerWithResultSet(r));
            }
            return new Page<Computer>(result, 0);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        } finally {
            Utils.close(conn, s, r);
        }
    }

    /**
     * This method returns a computer identified by its id.
     * @param id of the computer
     * @return ths Computer wanted
     * @throws DAOException if SQL request fail
     */
    public Computer getDetails(int id) throws DAOException {
        logger.info("Get computer : " + id);
        Connection conn = null;
        PreparedStatement s = null;
        ResultSet r = null;
        try {
            conn = dataSource.getConnection();

            s = conn.prepareStatement("SELECT c1.id, c1.name, introduced, discontinued , c2.id, c2.name"
                    + " FROM computer c1"
                    + " LEFT JOIN company c2 ON c2.id = c1.company_id"
                    + " WHERE c1.id = ?");
            s.setInt(1, id);
            r = s.executeQuery();
            if (r.next()) {
                Computer result = makeComputerWithResultSet(r);
                return result;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        } finally {
            Utils.close(conn, s, r);
        }
    }

    /**
     * Get a Computer with LocalDateTime to Timestamps, column 3 and 4.
     * @param r is the ResultSet of the query
     * @return a Computer
     * @throws DAOException if SQL fail
     */
    private Computer makeComputerWithResultSet(ResultSet r) throws DAOException {
        try {
            LocalDateTime intro = getLocalDateTime(r, 3);
            LocalDateTime disco = getLocalDateTime(r, 4);
            int idCompany = r.getInt(5);
            Computer result = GenericBuilder.of(Computer::new)
                    .with(Computer::setId, r.getInt(1))
                    .with(Computer::setName, r.getString(2))
                    .with(Computer::setIntroduced, intro)
                    .with(Computer::setDiscontinued, disco)
                    .with(Computer::setCompany, idCompany <= 0 ? null : GenericBuilder.of(Company::new)
                            .with(Company::setId, idCompany)
                            .with(Company::setName, r.getString(6))
                            .build())
                    .build();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }

    /**
     * Get LocalDateTime with a Timestamp of a ResultSet.
     * @param r the resultSet
     * @param index the index of the Timestamp
     * @return LocalDateTime or null
     */
    private LocalDateTime getLocalDateTime(ResultSet r, int index) {
        try {
            return r.getTimestamp(index) == null ? null : r.getTimestamp(index).toLocalDateTime();
        } catch (SQLException error) {
            return null;
        }
    }


    /**
     * This method adds a computer in the database regardless of its id and returns the computer completed with its id.
     * @param computer to add in the database
     * @return Computer generated
     * @throws DAOException if SQL fail
     */
    public Computer create(Computer computer) throws DAOException {
        logger.info("Create computer : " + computer);
        Connection conn = null;
        PreparedStatement s = null;
        ResultSet generatedKeys = null;
        try {
            conn = dataSource.getConnection();
            s = conn.prepareStatement(
                    "Insert into computer (name,company_id,introduced,discontinued) values (?,?,?,?)"
                    , Statement.RETURN_GENERATED_KEYS);
            s.setString(1, computer.getName());
            if (computer.getCompany() != null) {
                s.setInt(2, computer.getCompany().getId());
            } else {
                s.setNull(2, Types.INTEGER);
            }
            s.setTimestamp(3, computer.getIntroducedTimeStamp());
            s.setTimestamp(4, computer.getDiscontinuedTimeStamp());

            int affectedRows = s.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating Computer failed, no rows affected.");
            }

            generatedKeys = s.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                Computer result = GenericBuilder.of(Computer::new)
                        .with(Computer::setId, id)
                        .with(Computer::setName, computer.getName())
                        .with(Computer::setIntroduced, computer.getIntroduced())
                        .with(Computer::setDiscontinued, computer.getDiscontinued())
                        .with(Computer::setCompany, computer.getCompany())
                        .build();
                return result;
            } else {
                throw new SQLException("Creating Computer failed, no ID obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        } finally {
            Utils.close(conn, s, generatedKeys);
        }
    }

    /**
     * This method finds a computer with has its id and modifies its attributes by those of that passed as parameter.
     * @param modifiedComputer with the id of the one to be modified and with its new attributes.
     * @return if Computer is updated
     * @throws DAOException if SQL fail
     */
    public boolean update(Computer modifiedComputer) throws DAOException {
        logger.info("Update a computer : " + modifiedComputer);
        Connection conn = null;
        PreparedStatement s = null;
        try {
            conn = dataSource.getConnection();
            s = conn.prepareStatement(
                    "UPDATE computer SET name = ?, company_id = ?, introduced = ?, discontinued = ? WHERE ID = ?"
                    , Statement.RETURN_GENERATED_KEYS);
            s.setString(1, modifiedComputer.getName());
            if (modifiedComputer.getCompany() != null) {
                s.setInt(2, modifiedComputer.getCompany().getId());
            } else {
                s.setNull(2, Types.INTEGER);
            }
            s.setTimestamp(3, modifiedComputer.getIntroducedTimeStamp());
            s.setTimestamp(4, modifiedComputer.getDiscontinuedTimeStamp());
            s.setInt(5, modifiedComputer.getId());

            int affectedRows = s.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating Computer failed, no rows affected.");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        } finally {
            Utils.close(conn, s);
        }
    }

    /**
     * This method removes the computer corresponding to the passed id as a parameter and returns a message confirming whether or not this deletion occurs.
     * @param id of the computer to delete
     * @return if succes : "Computer " + id + " is deleted" or if fail : "Delete Computer failed, no rows affected."
     * @throws DAOException if SQL fail
     */
    public String delete(int id) throws DAOException {
        logger.info("Delete a computer : " + id);
        Connection conn = null;
        PreparedStatement s = null;
        try {
            conn = dataSource.getConnection();
            s = conn.prepareStatement("DELETE FROM computer where id = ?");
            s.setInt(1, id);

            int affectedRows = s.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Delete Computer failed, no rows affected.");
            }
            return "Computer " + id + " is deleted";
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        } finally {
            Utils.close(conn, s);
        }
    }

    /**
     * This method removes computers corresponding to the passed listID as a parameter and returns a message confirming whether or not this deletion occurs.
     * @param listId list of the computer to delete
     * @return if succes : "Computers are deleted", else : "Delete Computer failed, no rows affected."
     * @throws DAOException if SQL fail
     */
    public String delete(List<Integer> listId) throws DAOException {
        logger.info("Delete computers : " + listId);
        Connection conn = null;
        PreparedStatement s = null;
        try {
            conn = dataSource.getConnection();
            String prepareDelete = prepareDelete(listId.size());
            s = conn.prepareStatement("DELETE FROM computer where id in (" + prepareDelete + ")");
            for (int i = 0; i < listId.size(); i++) {
                s.setInt((i + 1), listId.get(i));
            }

            int affectedRows = s.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Delete Computer failed, no rows affected.");
            }
            return DELETE_SUCCES;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        } finally {
            Utils.close(conn, s);
        }
    }

    /**
     * Prepare list of ?.
     * @param listIdSize list.
     * @return .
     */
    private String prepareDelete(int listIdSize) {
        if (listIdSize < 1) {
            return null;
        }
        String result = "?";
        for (int i = 1; i < listIdSize; i++) {
            result += ",?";
        }
        return result;
    }

    /**
     * Delete the last computer added in the DAO.
     * @throws DAOException if delete failed
     */
    public void deleteLast() throws DAOException {
        logger.info("Delete last computer");
        Connection conn = null;
        PreparedStatement s = null;
        try {
            int id = getLastId();
            conn = dataSource.getConnection();
            s = conn.prepareStatement("DELETE FROM computer WHERE id = ?");
            s.setInt(1, id);

            int affectedRows = s.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Delete Computer failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        } finally {
            Utils.close(conn, s);
        }
    }

    /**
     * Get the first computer of the DataBase.
     * @return Computer
     * @throws DAOException if sql failed
     */
    public Computer getFirst() throws DAOException {
        try {
            int id = getFirstId();
            return getDetails(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }

    /**
     * getLastId.
     * @return int
     * @throws SQLException if sql failed
     * @throws DAOException if sql failed
     */
    private int getLastId() throws SQLException, DAOException {
        return getFirstORLastId(true);
    }

    /**
     * getFirstId.
     * @return int
     * @throws SQLException if sql failed
     * @throws DAOException if sql failed
     */
    private int getFirstId() throws SQLException, DAOException {
        return getFirstORLastId(false);
    }

    /**
     * getFirstORLastId.
     * @param last true if you want the last id, false if you want the first
     * @return int
     * @throws DAOException if sql failed
     */
    private int getFirstORLastId(boolean last) throws DAOException {
        String ordre = "";
        if (last) {
            ordre = " DESC ";
        }
        Connection conn = null;
        PreparedStatement s = null;
        ResultSet r = null;
        try {
            conn = dataSource.getConnection();
            s = conn.prepareStatement(
                    "SELECT id FROM computer ORDER BY id" + ordre);
            r = s.executeQuery();
            if (!r.next()) {
                throw new DAOException("No Computer in DataBase");
            }
            int id = r.getInt(1);
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        } finally {
            Utils.close(conn, s, r);
        }
    }

    /**
     * Delete all computer of one company.
     * @param id the id of the company
     * @throws DAOException id SQL bug
     */
    public void deleteIdCompany(int id) throws DAOException {
        logger.info("Delete computer of the company : " + id);
        if (id <= 0) {
            throw new DAOException("id.invalid");
        }
        Connection conn = null;
        PreparedStatement s = null;
        try {
            conn = dataSource.getConnection();
            s = conn.prepareStatement("DELETE FROM computer where company_id = ?");
            s.setInt(1, id);
            s.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        } finally {
            Utils.close(conn, s);
        }
    }

    /**
     * Delete all computer of one company.
     * @param id the id of the company
     * @param conn the connection with transaction
     * @throws DAOException id SQL bug
     */
    public void deleteIdCompany(Connection conn, int id) throws DAOException {
        logger.info("Delete computer of the company : " + id);
        if (conn == null) {
            throw new DAOException("connection.null");
        }
        if (id <= 0) {
            throw new DAOException("id.invalid");
        }
        PreparedStatement s;
        try {
            s = conn.prepareStatement("DELETE FROM computer where company_id = ?");
            s.setInt(1, id);
            s.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }
}
