package model.dao.computer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.GenericBuilder;
import model.Page;
import model.dao.DAOException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

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
    public int count() throws DAOException {
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
     * @return Page<Computer> corresponds to the page
     * @throws DAOException if SQL request fail
     */
    public Page<Computer> getPage(int page) throws DAOException {
        return getPage(page, Page.PAGE_SIZE);
    }

    /**
     * This method returns the page of computers with a sizePage of sizePage.
     * @param page corresponds to the page's number to be retrieved
     * @param sizePage size of a page
     * @return Page<Computer> corresponds to the page
     * @throws DAOException if SQL request fail
     */
    public Page<Computer> getPage(int page, int sizePage) throws DAOException {
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
            return new Page<Computer>(result, page, sizePage);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
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
        try {
            conn = Utils.openConnection();
            PreparedStatement s = conn.prepareStatement("SELECT c1.id, c1.name, introduced, discontinued , c2.id, c2.name"
                    + " FROM computer c1"
                    + " LEFT JOIN company c2 ON c1.company_id = c2.id"
                    + " WHERE c1.name LIKE '%" + search + "%'"
                    + " OR c2.name LIKE '%" + search + "%'");
            ResultSet r = s.executeQuery();
            ArrayList<Computer> result = new ArrayList<>();
            while (r.next()) {
                result.add(makeComputerWithResultSet(r));
            }
            r.close();
            s.close();
            Utils.closeConnection(conn);
            return new Page<Computer>(result, 0);
        } catch (SQLException e) {
            e.printStackTrace();
        throw new DAOException(e);
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
        try {
            conn = Utils.openConnection();

            //TODO pourquoi erreur SQL

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
            e.printStackTrace();
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
     * This method adds a computer in the database regardless of its id and returns the computer completed with its id.
     * @param computer to add in the database
     * @return Computer generated
     * @throws DAOException if SQL fail
     */
    public Computer create(Computer computer) throws DAOException {
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
                s.close();
                Utils.closeConnection(conn);
                throw new SQLException("Creating Computer failed, no rows affected.");
            }

            ResultSet generatedKeys = s.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                Computer result = GenericBuilder.of(Computer::new)
                        .with(Computer::setId, id)
                        .with(Computer::setName, computer.getName())
                        .with(Computer::setIntroduced, computer.getIntroduced())
                        .with(Computer::setDiscontinued, computer.getDiscontinued())
                        .with(Computer::setCompany, computer.getCompany())
                        .build();
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
            e.printStackTrace();
            throw new DAOException(e);
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
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
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
            e.printStackTrace();
            throw new DAOException(e);
        }
    }

    /**
     * Delete the last computer added in the DAO.
     * @throws DAOException if delete failed
     */
    public void deleteLast() throws DAOException {
        logger.info("Delete last computer");
        try {
            int id = getLastId();
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
            e.printStackTrace();
            throw new DAOException(e);
        }
    }

    /**
     * Get the first computer of the DataBase.
     * @throws DAOException if sql failed
     * @return Computer
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
    private int getFirstId() throws  SQLException, DAOException {
        return getFirstORLastId(false);
    }

    /**
     * getFirstORLastId.
     * @param last true if you want the last id, false if you want the first
     * @return int
     * @throws SQLException if sql failed
     * @throws DAOException if sql failed
     */
    private int getFirstORLastId(boolean last) throws  SQLException, DAOException {
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
