package model.dao.company;

import model.Page;
import model.company.Company;
import model.dao.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public enum CompanyDAOImpl implements CompanyDAO {
    INSTANCE;

    private Logger logger = LoggerFactory.getLogger(CompanyDAOImpl.class);
    private Connection conn = null;

    /**
     * Get the number of Companies.
     * @return the number of companies in DataBase
     * @throws DAOException if no result
     */
    public int count() throws DAOException {
        logger.info("Count computers");
        try {
            conn = Utils.openConnection();
            PreparedStatement s = conn.prepareStatement("SELECT COUNT(*) FROM company");
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
     * Get a page of companies.
     * @param page who pages is needed
     * @return Page<Company> contains companies wanted
     * @throws DAOException if SQL fails
     */
    public Page<Company> getPage(int page) throws DAOException {
        logger.info("Get all companies");
        try {
            conn = Utils.openConnection();
            PreparedStatement s = conn.prepareStatement("SELECT id, name FROM company LIMIT " + Page.PAGE_SIZE + " OFFSET " + page * Page.PAGE_SIZE);
            ResultSet r = s.executeQuery();
            ArrayList<Company> result = new ArrayList<>();
            while (r.next()) {
                result.add(new Company(r.getInt(1), r.getString(2)));
            }
            r.close();
            s.close();
            Utils.closeConnection(conn);
            return new Page<Company>(result, page);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Get a company by id.
     * @param id corresponding to the company wanted
     * @return Company wanted
     * @throws DAOException if SQL fails
     */
    public Company get(int id) throws DAOException {
        logger.info("Get all companies");
        try {
            conn = Utils.openConnection();
            PreparedStatement s = conn.prepareStatement("SELECT id, name FROM company WHERE id = ?");
            s.setInt(1, id);
            ResultSet r = s.executeQuery();
            Company result = null;
            if (r.next()) {
                result = new Company(r.getInt(1), r.getString(2));
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
     * Return all companies.
     * @return a ArrayList with all companies
     * @throws DAOException if SQL fail
     */
    public ArrayList<Company> getAll() throws DAOException {
        logger.info("Get all companies");
        try {
            conn = Utils.openConnection();
            PreparedStatement s = conn.prepareStatement("SELECT id, name FROM company");
            ResultSet r = s.executeQuery();
            ArrayList<Company> result = new ArrayList<>();
            while (r.next()) {
                result.add(new Company(r.getInt(1), r.getString(2)));
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
     * Create a company.
     * @param c company to create
     * @return the id of the company
     * @throws DAOException if fail
     */
    public int create(Company c) throws DAOException {
        logger.info("Create computer : " + c);
        try {
            conn = Utils.openConnection();
            PreparedStatement s = conn.prepareStatement(
                    "Insert into company (name) values (?)"
                    , Statement.RETURN_GENERATED_KEYS);
            s.setString(1, c.getName());

            int affectedRows = s.executeUpdate();

            if (affectedRows == 0) {
                s.close();
                Utils.closeConnection(conn);
                throw new SQLException("Creating Company failed, no rows affected.");
            }

            ResultSet generatedKeys = s.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                s.close();
                Utils.closeConnection(conn);
                return id;
            } else {
                generatedKeys.close();
                s.close();
                Utils.closeConnection(conn);
                throw new SQLException("Creating Company failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Delete a company and all its computers.
     * @param id the id of the company
     * @return true if succes, false else
     * @throws DAOException if fail
     */
    public boolean delete(int id) throws DAOException {
        //TODO transaction
        logger.info("Delete a company : " + id);
        try {
            conn = Utils.openConnection();
            conn.setAutoCommit(false);
            //Delete all computer of this company
            PreparedStatement s = conn.prepareStatement("DELETE FROM computer where company_id = ?");
            s.setInt(1, id);
            s.executeUpdate();

            //delete the company
            s = conn.prepareStatement("DELETE FROM company where id = ?");
            s.setInt(1, id);

            int affectedRows = s.executeUpdate();

            if (affectedRows == 0) {
                s.close();
                conn.rollback();
                conn.setAutoCommit(true);
                Utils.closeConnection(conn);
                throw new SQLException("Delete company failed, no rows affected.");
            }
            s.close();
            conn.commit();
            conn.setAutoCommit(true);
            Utils.closeConnection(conn);
            return true;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
