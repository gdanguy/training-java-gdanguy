package model.dao.computer;

import controller.DashboardController;
import exception.CodeError;
import model.GenericBuilder;
import model.Page;
import model.company.Company;
import model.computer.Computer;
import exception.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ComputerDAOImpl implements ComputerDAO {
    private Logger logger = LoggerFactory.getLogger(ComputerDAOImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final String DELETE_SUCCES = "Computers are deleted";


    /**
     * Get the number of Computer.
     * @return the number of computer in DataBase
     * @throws DAOException if no result
     */
    public int count() throws DAOException {
        logger.info("Count computers");
        Integer cpt = this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM computer", Integer.class);
        if (cpt == null) {
            throw new DAOException(CodeError.COMPUTER_NOT_FOUND);
        }
        return cpt;
    }

    /**
     * This method returns the page of computers.
     * @param page corresponds to the page's number to be retrieved
     * @return Page<Computer> corresponds to the page
     */
    public Page<Computer> getPage(int page) {
        return getPage(page, Page.PAGE_SIZE, DashboardController.ORDER_NULL);
    }

    /**
     * This method returns the page of computers with a sizePage of sizePage.
     * @param page     corresponds to the page's number to be retrieved
     * @param sizePage size of a page
     * @param order order of a page
     * @return Page<Computer> corresponds to the page
     */
    public Page<Computer> getPage(int page, int sizePage, String order) {
        logger.info("Get page " + page + ", computers of " + sizePage);
        String orderBy = getOrder(order);
        ArrayList<Computer> computers = new ArrayList<>();
        computers = (ArrayList<Computer>) this.jdbcTemplate.query(
                "SELECT c1.id as id, c1.name as name, introduced, discontinued , c2.id as company_id, c2.name as company_name"
                        + " FROM computer c1"
                        + " LEFT JOIN company c2 ON c1.company_id = c2.id"
                        + orderBy
                        + " LIMIT " + sizePage + " OFFSET " + page * sizePage,
                new Object[] {},
                new RowMapper<Computer>() {
                    public Computer mapRow(ResultSet rs, int rowNom) throws SQLException {
                        return makeComputerWithResultSet(rs);
                    }
                });
        return new Page<Computer>(computers, page, sizePage);
    }

    /**
     * Get the ORDER BY of the request.
     * @param order of the page
     * @return String
     */
    private String getOrder(String order) {
        String result = " ORDER BY ";
        switch (order) {
            case DashboardController.ORDER_NAME_ASC     : return (result + " name ASC");
            case DashboardController.ORDER_NAME_DESC    : return (result + " name DESC");
            case DashboardController.ORDER_INTRO_ASC    : return (result + " introduced ASC");
            case DashboardController.ORDER_INTRO_DESC   : return (result + " introduced DESC");
            case DashboardController.ORDER_DISCO_ASC    : return (result + " discontinued ASC");
            case DashboardController.ORDER_DISCO_DESC   : return (result + " discontinued DESC");
            case DashboardController.ORDER_COMPANY_ASC  : return (result + " company_name ASC");
            case DashboardController.ORDER_COMPANY_DESC : return (result + " company_name DESC");
            default: return "";
        }
    }

    /**
     * This method returns the page of computers with a sizePage of sizePage.
     * @param search word researched
     * @return Page<Computer> corresponds to the page
     */
    public Page<Computer> getPage(String search) {
        ArrayList<Computer> computers = new ArrayList<>();
        String sql = "SELECT c1.id as id, c1.name as name, introduced, discontinued , c2.id as company_id, c2.name as company_name"
                + " FROM computer c1"
                + " LEFT JOIN company c2 ON c1.company_id = c2.id"
                + " WHERE c1.name LIKE '%" + search + "%'"
                + " OR c2.name LIKE '%" + search + "%'";
        computers = (ArrayList<Computer>) this.jdbcTemplate.query(
                sql, new Object[] {},
                new RowMapper<Computer>() {
                    public Computer mapRow(ResultSet rs, int rowNom) throws SQLException {
                        return makeComputerWithResultSet(rs);
                    }
                });
        return new Page<Computer>(computers, 0);
    }

    /**
     * This method returns a computer identified by its id.
     * @param id of the computer
     * @return ths Computer wanted
     */
    public Computer getDetails(int id) {
        logger.info("Get computer : " + id);
        return this.jdbcTemplate.queryForObject(
                "SELECT c1.id as id, c1.name as name, introduced, discontinued , c2.id as company_id, c2.name as company_name"
                        + " FROM computer c1"
                        + " LEFT JOIN company c2 ON c2.id = c1.company_id"
                        + " WHERE c1.id = ?",
                new Object[] {id},
                new RowMapper<Computer>() {
                    public Computer mapRow(ResultSet rs, int rowNom) throws SQLException {
                        return makeComputerWithResultSet(rs);
                    }
                });
    }

    /**
     * Get a Computer with LocalDateTime to Timestamps, column 3 and 4.
     * @param rs is the ResultSet of the query
     * @return a Computer
     */
    private Computer makeComputerWithResultSet(ResultSet rs) {
        try {
            LocalDateTime intro = getLocalDateTime(rs, "introduced");
            LocalDateTime disco = getLocalDateTime(rs, "discontinued");
            int idCompany = rs.getInt("company_id");
            Computer result = GenericBuilder.of(Computer::new)
                    .with(Computer::setId, rs.getInt("id"))
                    .with(Computer::setName, rs.getString("name"))
                    .with(Computer::setIntroduced, intro)
                    .with(Computer::setDiscontinued, disco)
                    .with(Computer::setCompany, idCompany <= 0 ? null : GenericBuilder.of(Company::new)
                            .with(Company::setId, idCompany)
                            .with(Company::setName, rs.getString("company_name"))
                            .build())
                    .build();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get LocalDateTime with a Timestamp of a ResultSet.
     * @param r the resultSet
     * @param label the label of the Timestamp
     * @return LocalDateTime or null
     */
    private LocalDateTime getLocalDateTime(ResultSet r, String label) {
        try {
            return r.getTimestamp(label) == null ? null : r.getTimestamp(label).toLocalDateTime();
        } catch (SQLException error) {
            return null;
        }
    }


    /**
     * This method adds a computer in the database regardless of its id and returns the computer completed with its id.
     * @param computer to add in the database
     * @return Computer generated
     */
    public Computer create(Computer computer) {
        logger.info("Create computer : " + computer);
        if (computer == null) {
            logger.error("Error creating Computer, computer == null");
            throw new DAOException(CodeError.COMPUTER_IS_NULL);
        }
        SimpleJdbcInsert insert =
                new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                        .withTableName("computer")
                        .usingGeneratedKeyColumns("id");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", computer.getName())
                .addValue("introduced", computer.getIntroducedTimeStamp())
                .addValue("discontinued", computer.getDiscontinuedTimeStamp())
                .addValue("company_id", computer.getCompany() == null ? null : computer.getCompany().getId());
        Number id = insert.executeAndReturnKey(parameterSource);
        if (id == null) {
            logger.error("Error creating Computer, bad parameters, " + computer.toStringDetails());
            throw new DAOException(CodeError.COMPUTER_CREATE_BAD_PARAMETERS);
        }
        int idComputer = id.intValue();
        Computer result = GenericBuilder.of(Computer::new)
                .with(Computer::setId, idComputer)
                .with(Computer::setName, computer.getName())
                .with(Computer::setIntroduced, computer.getIntroduced())
                .with(Computer::setDiscontinued, computer.getDiscontinued())
                .with(Computer::setCompany, computer.getCompany())
                .build();
        return result;
    }

    /**
     * This method finds a computer with has its id and modifies its attributes by those of that passed as parameter.
     * @param modifiedComputer with the id of the one to be modified and with its new attributes.
     * @return if Computer is updated
     */
    public boolean update(Computer modifiedComputer) {
        logger.info("Update a computer : " + modifiedComputer);
        Integer companyId = modifiedComputer.getCompany() != null ? modifiedComputer.getCompany().getId() : null;
        String sql = "UPDATE computer SET name = ?, company_id = ?, introduced = ?, discontinued = ? WHERE id = ?";
        Object[] params = {modifiedComputer.getName(), companyId, modifiedComputer.getIntroducedTimeStamp(), modifiedComputer.getDiscontinuedTimeStamp(), modifiedComputer.getId()};
        log(params);
        int affectedRows = jdbcTemplate.update(sql, params);
        if (affectedRows == 0) {
            throw new DAOException(CodeError.COMPUTER_EDIT);
        }
        return true;
    }

    /**
     * This method removes the computer corresponding to the passed id as a parameter and returns a message confirming whether or not this deletion occurs.
     * @param id of the computer to delete
     * @return if succes : "Computer " + id + " is deleted" or if fail : "Delete Computer failed, no rows affected."
     */
    public String delete(int id) {
        logger.info("Delete a computer : " + id);
        String sql = "DELETE FROM computer where id = ?";
        Object[] params = {id};
        log(params);
        int affectedRows = jdbcTemplate.update(sql, params);
        if (affectedRows == 0) {
            throw new DAOException(CodeError.COMPUTER_DELETE);
        }
        return "Computer " + id + " is deleted";
    }

    /**
     * This method removes computers corresponding to the passed listID as a parameter and returns a message confirming whether or not this deletion occurs.
     * @param listId list of the computer to delete
     * @return if succes : "Computers are deleted", else : "Delete Computer failed, no rows affected."
     */
    public String delete(List<Integer> listId) {
        logger.info("Delete computers : " + listId);
        String prepareDelete = prepareDelete(listId.size());
        String sql = "DELETE FROM computer where id in (" + prepareDelete + ")";
        Object[] params = prepareDelete(listId);
        log(params);
        int affectedRows = jdbcTemplate.update(sql, params);
        if (affectedRows == 0) {
            throw new DAOException(CodeError.COMPUTER_DELETE);
        }
        return DELETE_SUCCES;
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
     * Prepare list of params.
     * @param list list.
     * @return .
     */
    private Object[] prepareDelete(List<Integer> list) {
        int size = list.size();
        if (size < 1) {
            return null;
        }
        Object[] result = new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    /**
     * Delete the last computer added in the DAO.
     */
    public void deleteLast() {
        delete(getLastId());
    }


    /**
     * Delete all computer of one company.
     * @param id the id of the company
     */
    public void deleteIdCompany(int id) {
        logger.info("Delete computer of the company : " + id);
        if (id <= 0) {
            throw new DAOException(CodeError.COMPUTER_COMPANY_ID_INVALID);
        }
        String sql = "DELETE FROM computer where company_id = ?";
        Object[] params = {id};
        log(params);
        jdbcTemplate.update(sql, params);
    }

    /**
     * Get the first computer of the DataBase.
     * @return Computer
     */
    public Computer getFirst() {
        int id = getFirstId();
        return getDetails(id);
    }

    /**
     * getLastId.
     * @return int
     */
    private int getLastId() {
        return getFirstORLastId(true);
    }

    /**
     * getFirstId.
     * @return int
     */
    private int getFirstId() {
        return getFirstORLastId(false);
    }

    /**
     * getFirstORLastId.
     * @param last true if you want the last id, false if you want the first
     * @return int
     */
    private int getFirstORLastId(boolean last) {
        String ordre = "";
        if (last) {
            ordre = " DESC ";
        }
        Integer cpt = this.jdbcTemplate.queryForObject("SELECT id FROM computer ORDER BY id " + ordre + " LIMIT 1", Integer.class);
        if (cpt == null) {
            throw new DAOException(CodeError.COMPUTER_NOT_FOUND);
        }
        return cpt;
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
        logger.debug(s);
    }
}
