package model.dao.computer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.dao.DAOException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import model.Pages;
import model.company.Company;
import model.computer.Computer;
import utils.Utils;

public enum ComputerDAOImpl implements ComputerDAO {
    INSTANCE;

    private Logger logger = LoggerFactory.getLogger(ComputerDAOImpl.class);
    private Connection conn;

    /**
     * Get the number of Computer.
     * @return the number of computer in DataBase
     * @throws DAOException if no result
     */
    public int countComputers() throws DAOException {
        logger.info("Count computers");
        try {
            conn = Utils.openConnection();
            PreparedStatement s = conn.prepareStatement("SELECT COUNT(*) FROM computer");
            ResultSet r = s.executeQuery();
            int result = -1;
            if (r.next()) {
                result = (r.getInt(1));
            }
            r.close();
            s.close();
            Utils.closeConnection(conn);
            return result;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * This method returns the page of computers.
     * @param page corresponds to the page's number to be retrieved
     * @return Pages<Computer> corresponds to the page
     * @throws DAOException if SQL request fail
     */
    public Pages<Computer> getPageComputer(int page) throws DAOException {
        return getPageComputer(page, Pages.PAGE_SIZE);
    }

    /**
     * This method returns the page of computers with a sizePage of sizePage.
     * @param page corresponds to the page's number to be retrieved
     * @param sizePage size of a page
     * @return Pages<Computer> corresponds to the page
     * @throws DAOException if SQL request fail
     */
    public Pages<Computer> getPageComputer(int page, int sizePage) throws DAOException {
        logger.info("Get page " + page + ", computers of " + sizePage);
        try {
            conn = Utils.openConnection();
            PreparedStatement s = conn.prepareStatement("SELECT c1.id, c1.name, introduced, discontinued , c2.id, c2.name"
                    + " FROM computer c1"
                    + " LEFT JOIN company c2 ON c1.company_id = c2.id"
                    + " LIMIT " + sizePage + " OFFSET " + page * sizePage);
            ResultSet r = s.executeQuery();
            ArrayList<Computer> result = new ArrayList<>();
            while (r.next()) {
                result.add(makeComputerWithResultSet(r));
            }
            r.close();
            s.close();
            Utils.closeConnection(conn);
            return new Pages<Computer>(result, page, sizePage);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * This method returns the page of computers with a sizePage of sizePage.
     * @param search word researched
     * @return Pages<Computer> corresponds to the page
     * @throws DAOException if SQL request fail
     */
    public Pages<Computer> getPageComputer(String search) throws DAOException {
        logger.info("Get Search computers : " + search);
        try {
            conn = Utils.openConnection();
            PreparedStatement s = conn.prepareStatement("SELECT c1.id, c1.name, introduced, discontinued , c2.id, c2.name"
                    + " FROM computer c1"
                    + " LEFT JOIN company c2 ON c1.company_id = c2.id"
                    + " WHERE c1.name LIKE '%" + search + "%'");
            ResultSet r = s.executeQuery();
            ArrayList<Computer> result = new ArrayList<>();
            while (r.next()) {
                result.add(makeComputerWithResultSet(r));
            }
            r.close();
            s.close();
            Utils.closeConnection(conn);
            return new Pages<Computer>(result, 0);
        } catch (SQLException e) {
        throw new DAOException(e);
    }
    }

    /**
     * This method returns a computer identified by its id.
     * @param id of the computer
     * @return ths Computer wanted
     * @throws DAOException if SQL request fail
     */
    public Computer getComputerDetails(int id) throws DAOException {
        logger.info("Get computer : " + id);
        try {
            conn = Utils.openConnection();
            PreparedStatement s = conn.prepareStatement("SELECT c1.id, c1.name, introduced, discontinued , c2.id, c2.name"
                    + " FROM computer c1"
                    + " LEFT JOIN company c2 ON c2.id = c1.company_id"
                    + " WHERE c1.id = ?");
            s.setInt(1, id);
            ResultSet r = s.executeQuery();
            if (r.next()) {
                Computer result = makeComputerWithResultSet(r);
                r.close();
                s.close();
                Utils.closeConnection(conn);
                return result;
            } else {
                r.close();
                s.close();
                Utils.closeConnection(conn);
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
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
            LocalDateTime intro = r.getTimestamp(3) == null ? null : r.getTimestamp(3).toLocalDateTime();
            LocalDateTime disco = r.getTimestamp(4) == null ? null : r.getTimestamp(4).toLocalDateTime();
            return new Computer(r.getInt(1), r.getString(2), intro, disco, new Company(r.getInt(5), r.getString(6)));
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }


    /**
     * This method adds a computer in the database regardless of its id and returns the computer completed with its id.
     * @param computer to add in the database
     * @return Computer generated
     * @throws DAOException if SQL fail
     */
    public Computer createComputer(Computer computer) throws DAOException {
        logger.info("Create computer : " + computer);
        try {
            conn = Utils.openConnection();
            PreparedStatement s = conn.prepareStatement(
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
                Utils.closeConnection(conn);
                throw new SQLException("Creating Computer failed, no rows affected.");
            }

            ResultSet generatedKeys = s.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                Computer result = new Computer(id, computer.getName(), computer.getIntroduced(), computer.getDiscontinued(), computer.getCompany());
                generatedKeys.close();
                s.close();
                Utils.closeConnection(conn);
                return result;
            } else {
                generatedKeys.close();
                s.close();
                Utils.closeConnection(conn);
                throw new SQLException("Creating Computer failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * This method finds a computer with has its id and modifies its attributes by those of that passed as parameter.
     * @param modifiedComputer with the id of the one to be modified and with its new attributes.
     * @return Computer updated
     * @throws DAOException if SQL fail
     */
    public Computer updateComputer(Computer modifiedComputer) throws DAOException {
        logger.info("Update a computer : " + modifiedComputer);
        try {
            conn = Utils.openConnection();
            PreparedStatement s = conn.prepareStatement(
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
                s.close();
                Utils.closeConnection(conn);
                throw new SQLException("Updating Computer failed, no rows affected.");
            }
            s.close();
            Utils.closeConnection(conn);
            return modifiedComputer;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * This method removes the computer corresponding to the passed id as a parameter and returns a message confirming whether or not this deletion occurs.
     * @param id of the computer to delete
     * @return if succes : "Computer " + id + " is deleted" or if fail : "Delete Computer failed, no rows affected."
     * @throws DAOException if SQL fail
     */
    public String deleteComputer(int id) throws DAOException {
        logger.info("Delete a computer : " + id);
        try {
            conn = Utils.openConnection();
            PreparedStatement s = conn.prepareStatement("DELETE FROM computer where id = ?");
            s.setInt(1, id);

            int affectedRows = s.executeUpdate();

            if (affectedRows == 0) {
                s.close();
                Utils.closeConnection(conn);
                throw new SQLException("Delete Computer failed, no rows affected.");
            }
            s.close();
            Utils.closeConnection(conn);
            return "Computer " + id + " is deleted";
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Delete the last computer added in the DAO.
     * @throws DAOException if delete failed
     */
    public void deleteLastComputer() throws DAOException {
        logger.info("Delete last computer");
        try {
            int id = getLastComputerId();
            conn = Utils.openConnection();
            PreparedStatement s = conn.prepareStatement("DELETE FROM computer WHERE id = ?");
            s.setInt(1, id);

            int affectedRows = s.executeUpdate();
            if (affectedRows == 0) {
                s.close();
                Utils.closeConnection(conn);
                throw new SQLException("Delete Computer failed, no rows affected.");
            }
            s.close();
            Utils.closeConnection(conn);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Get the first computer of the DataBase
     * @throws DAOException if sql failed
     */
    public Computer getFirstComputer() throws DAOException {
        try {
            int id = getFirstComputerId();
            return getComputerDetails(id);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private int getLastComputerId() throws SQLException, DAOException {
        return getFirstLastComputerId(true);
    }

    private int getFirstComputerId() throws  SQLException, DAOException {
        return getFirstLastComputerId(false);
    }

    private int getFirstLastComputerId(boolean last) throws  SQLException, DAOException {
        String ordre = "";
        if (last) {
            ordre = " DESC ";
        }
        conn = Utils.openConnection();
        PreparedStatement s = conn.prepareStatement(
                "SELECT id FROM computer ORDER BY id" + ordre);
        ResultSet r = s.executeQuery();
        if (!r.next()) {
            throw new DAOException("No Computer in DataBase");
        }
        int id = r.getInt(1);
        r.close();
        s.close();
        Utils.closeConnection(conn);
        return id;
    }
}
