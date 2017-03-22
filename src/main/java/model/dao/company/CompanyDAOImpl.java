package model.dao.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import model.Pages;
import model.company.Company;
import utils.Utils;

public enum CompanyDAOImpl implements CompanyDAO {
    INSTANCE;

    private Logger logger = LoggerFactory.getLogger(CompanyDAOImpl.class);
    private Connection conn = null;

    /**
     * Get the number of Companies.
     * @return the number of companies in DataBase
     * @throws SQLException if no result
     */
    public int countCompanies() throws SQLException {
        logger.info("Count computers");
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
    }

    /**
     * Get a page of companies.
     * @param page who pages is needed
     * @return Pages<Company> contains companies wanted
     * @throws SQLException if SQL fails
     */
    public Pages<Company> getPageCompanies(int page) throws SQLException {
        logger.info("Get all companies");
        conn = Utils.openConnection();
        PreparedStatement s = conn.prepareStatement("SELECT id, name FROM company LIMIT " + Pages.PAGE_SIZE + " OFFSET " + page * Pages.PAGE_SIZE);
        ResultSet r = s.executeQuery();
        ArrayList<Company> result = new ArrayList<>();
        while (r.next()) {
            result.add(new Company(r.getInt(1), r.getString(2)));
        }
        r.close();
        s.close();
        Utils.closeConnection(conn);
        return new Pages<Company>(result, page);
    }

    /**
     * Get a company by id.
     * @param id corresponding to the company wanted
     * @return Company wanted
     * @throws SQLException if SQL fails
     */
    public Company getCompany(int id) throws SQLException {
        logger.info("Get all companies");
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
    }

    /**
     * Return all companies.
     * @return a ArrayList with all companies
     * @throws SQLException if SQL fail
     */
    public ArrayList<Company> getAllCompany() throws SQLException {
        logger.info("Get all companies");
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
    }

}
