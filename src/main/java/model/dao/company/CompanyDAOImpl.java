package model.dao.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import model.GenericBuilder;
import model.Page;
import model.company.Company;
import model.dao.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompanyDAOImpl implements CompanyDAO {
    private Logger logger = LoggerFactory.getLogger(CompanyDAOImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;



    /**
     * Get the number of Companies.
     * @return the number of companies in DataBase
     */
    public int count()  {
        logger.info("Count computers");
        Integer cpt = this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM company", Integer.class);
        if (cpt == null) {
            return -1;
        }
        return cpt;
    }

    /**
     * Get a page of companies.
     * @param page who pages is needed
     * @return Page<Company> contains companies wanted
     */
    public Page<Company> getPage(int page) {
        logger.info("Get all companies");
        ArrayList<Company> companies = new ArrayList<>();
        companies = (ArrayList<Company>) this.jdbcTemplate.query(
                "SELECT id, name FROM company LIMIT " + Page.PAGE_SIZE + " OFFSET " + page * Page.PAGE_SIZE,
                new Object[] {},
                new RowMapper<Company>() {
                    public Company mapRow(ResultSet rs, int rowNom) throws SQLException {
                        return GenericBuilder.of(Company::new)
                                .with(Company::setId, rs.getInt("id"))
                                .with(Company::setName, rs.getString("name"))
                                .build();
                    }
                });
        return new Page<Company>(companies, page);
    }

    /**
     * Get a company by id.
     * @param id corresponding to the company wanted
     * @return Company wanted
     */
    public Company get(int id) {
        logger.info("Get all companies");
        return this.jdbcTemplate.queryForObject(
                "SELECT id, name FROM company WHERE id = ?",
                new Object[] {id},
                new RowMapper<Company>() {
                    public Company mapRow(ResultSet rs, int rowNom) throws SQLException {
                        return GenericBuilder.of(Company::new)
                                .with(Company::setId, rs.getInt("id"))
                                .with(Company::setName, rs.getString("name"))
                                .build();
                    }
                });
    }

    /**
     * Return all companies.
     * @return a ArrayList with all companies
     */
    public ArrayList<Company> getAll() {
        logger.info("Get all companies");
        ArrayList<Company> companies = new ArrayList<>();
        companies = (ArrayList<Company>) this.jdbcTemplate.query(
                "SELECT id, name FROM company",
                new Object[] {},
                new RowMapper<Company>() {
                    public Company mapRow(ResultSet rs, int rowNom) throws SQLException {
                        return GenericBuilder.of(Company::new)
                                .with(Company::setId, rs.getInt("id"))
                                .with(Company::setName, rs.getString("name"))
                                .build();
                    }
                });
        return companies;
    }

    /**
     * Create a company.
     * @param c company to create
     * @return the id of the company
     * @throws DAOException if fail
     */
    public int create(Company c) throws DAOException {
        logger.info("Create computer : " + c);
        SimpleJdbcInsert insert =
                new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                        .withTableName("company")
                        .usingGeneratedKeyColumns("id");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", c.getName());
        Number id = insert.executeAndReturnKey(parameterSource);
        if (id == null) {
            logger.error("Error creating Company, bad parameters, " + c.toString());
            throw new DAOException("Error creating Computer, bad Company");
        }
        return id.intValue();
    }

    /**
     * Delete a company and all its computers.
     * @param id the id of the company
     * @return true if succes, false else
     * @throws DAOException if fail
     */
    public boolean delete(int id) throws DAOException {
        logger.info("Delete a company : " + id);
        String sql = "DELETE FROM company where id = ?";
        Object[] params = {id};
        log(params);
        int affectedRows = jdbcTemplate.update(sql, params);
        if (affectedRows == 0) {
            throw new DAOException("Delete Company failed, no rows affected.");
        }
        return true;
    }

    /**
     * Display params of a SQL request.
     * @param params Obejct[]
     */
    private void log(Object[] params) {
        String s = "params = {" + params[0];
        for (int i = 1; i < params.length; i++) {
            s += ", " + params[i];
        }
        s += "}";
        logger.info(s);
    }
}
