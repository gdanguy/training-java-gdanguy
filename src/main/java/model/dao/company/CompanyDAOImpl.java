package model.dao.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.LoggerFactory;

import model.Pages;
import model.company.Company;
import utils.Utils;

public enum CompanyDAOImpl implements CompanyDAO {
    INSTANCE;

    private org.slf4j.Logger logger = LoggerFactory.getLogger(CompanyDAOImpl.class);
    private Connection conn = null;


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
        Utils.closeConnection(conn);
        return result;
    }

}
