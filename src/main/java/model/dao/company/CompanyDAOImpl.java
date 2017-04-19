package model.dao.company;

import model.GenericBuilder;
import model.Page;
import model.company.Company;
import model.dao.DAOException;
import model.dao.DAOFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CompanyDAOImpl implements CompanyDAO {

    private Logger logger = LoggerFactory.getLogger(CompanyDAOImpl.class);
    private DAOFactory daoFactory;

    public void setDaoFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * Get the number of Companies.
     * @return the number of companies in DataBase
     * @throws DAOException if no result
     */
    public int count() throws DAOException {
        logger.info("Count computers");
        Connection conn;
        PreparedStatement s = null;
        ResultSet r = null;
        try {
            conn = daoFactory.get();
            s = conn.prepareStatement("SELECT COUNT(*) FROM company");
            r = s.executeQuery();
            int result = -1;
            if (r.next()) {
                result = (r.getInt(1));
            }
            return result;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            daoFactory.close(s, r);
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
        Connection conn;
        PreparedStatement s = null;
        ResultSet r = null;
        try {
            conn = daoFactory.get();
            s = conn.prepareStatement("SELECT id, name FROM company LIMIT " + Page.PAGE_SIZE + " OFFSET " + page * Page.PAGE_SIZE);
            r = s.executeQuery();
            ArrayList<Company> result = new ArrayList<>();
            while (r.next()) {
                result.add(GenericBuilder.of(Company::new)
                        .with(Company::setId, r.getInt(1))
                        .with(Company::setName, r.getString(2))
                        .build());
            }
            return new Page<Company>(result, page);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            daoFactory.close(s, r);
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
        Connection conn;
        PreparedStatement s = null;
        ResultSet r = null;
        try {
            conn = daoFactory.get();
            s = conn.prepareStatement("SELECT id, name FROM company WHERE id = ?");
            s.setInt(1, id);
            Company result = null;
            r = s.executeQuery();
            if (r.next()) {
                result = GenericBuilder.of(Company::new)
                        .with(Company::setId, r.getInt(1))
                        .with(Company::setName, r.getString(2))
                        .build();
            }
            return result;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            daoFactory.close(s, r);
        }
    }

    /**
     * Return all companies.
     * @return a ArrayList with all companies
     * @throws DAOException if SQL fail
     */
    public ArrayList<Company> getAll() throws DAOException {
        logger.info("Get all companies");
        Connection conn;
        PreparedStatement s = null;
        ResultSet r = null;
        try {
            conn = daoFactory.get();
            s = conn.prepareStatement("SELECT id, name FROM company");
            r = s.executeQuery();
            ArrayList<Company> result = new ArrayList<>();
            while (r.next()) {
                result.add(GenericBuilder.of(Company::new)
                        .with(Company::setId, r.getInt(1))
                        .with(Company::setName, r.getString(2))
                        .build());
            }
            return result;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            daoFactory.close(s, r);
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
        Connection conn;
        PreparedStatement s = null;
        try {
            conn = daoFactory.get();
            s = conn.prepareStatement(
                    "Insert into company (name) values (?)"
                    , Statement.RETURN_GENERATED_KEYS);
            s.setString(1, c.getName());

            int affectedRows = s.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating Company failed, no rows affected.");
            }

            ResultSet generatedKeys = s.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                return id;
            } else {
                generatedKeys.close();
                throw new SQLException("Creating Company failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            daoFactory.close(s);
        }
    }

    /**
     * Delete a company and all its computers.
     * @param id the id of the company
     * @return true if succes, false else
     * @throws DAOException if fail
     */
    public boolean delete(int id) throws DAOException {
        logger.info("Delete a company : " + id);
        Connection conn;
        PreparedStatement s = null;
        try {
            conn = daoFactory.get();
            //delete the company
            s = conn.prepareStatement("DELETE FROM company where id = ?");
            s.setInt(1, id);

            int affectedRows = s.executeUpdate();
            s.close();

            if (affectedRows == 0) {
                throw new SQLException("Delete company failed, no rows affected.");
            }
            return true;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            daoFactory.close(s);
        }
    }
}
