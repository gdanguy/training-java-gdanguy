package model.DAO.computer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.MysqlDataTruncation;

import configuration.Config;
import model.Pages;
import model.company.Company;
import model.computer.Computer;

public class ComputerDAOImpl implements ComputerDAO{
	private static Logger logger = LoggerFactory.getLogger(ComputerDAOImpl.class);
	private Connection conn;

	/**
	 * Builder
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ComputerDAOImpl() throws ClassNotFoundException, SQLException {
		logger.info("Connection to the database");
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(Config.URL_DB, Config.USER_DB, Config.PASSWORD_DB);
	}

	/**
	 * Close connection
	 */
	protected void finalize() {
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
		}
	}

	/**
	 * This method returns the first page of computers.
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Pages<Computer> getAllComputer() throws SQLException, ClassNotFoundException {
		return getPageComputer(0);
	}
	
	/**
	 * This method returns the page of computers.
	 * @param page
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public Pages<Computer> getPageComputer(int page) throws SQLException, ClassNotFoundException {
		logger.info("Get all computers");
		PreparedStatement s = conn.prepareStatement("SELECT c1.id, c1.name, introduced, discontinued , c2.id, c2.name"
												+ " FROM computer c1"
												+ "	LEFT JOIN company c2 ON c1.company_id = c2.id"
												+ " LIMIT "+Pages.PAGE_SIZE+" OFFSET "+page*Pages.PAGE_SIZE);
		ResultSet r = s.executeQuery();
		ArrayList<Computer> result = new ArrayList<>();
		while( r.next() ){
			LocalDateTime intro = r.getTimestamp("introduced") == null ? null : r.getTimestamp("introduced").toLocalDateTime();
			LocalDateTime disco = r.getTimestamp("discontinued") == null ? null : r.getTimestamp("discontinued").toLocalDateTime();
			result.add(new Computer(r.getInt(1),r.getString(2),intro,disco,new Company(r.getInt(5),r.getString(6))));
		}
		return new Pages<Computer>(result,page);
	}

	/**
	 * This method returns a computer identified by its id.
	 * @param id of the computer
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Computer getComputerDetails(int id) throws SQLException, ClassNotFoundException {
		logger.info("Get computer : "+id);
		PreparedStatement s = conn.prepareStatement("SELECT c1.id, c1.name, introduced, discontinued , c2.id, c2.name"
												+ " FROM computer c1"
												+ " LEFT JOIN company c2 ON c2.id = c1.company_id"
												+ " WHERE c1.id = ?");
		s.setInt(1, id);
		ResultSet r = s.executeQuery();
		if( r.next() ){
			LocalDateTime intro = r.getTimestamp(3) == null ? null : r.getTimestamp(3).toLocalDateTime();
			LocalDateTime disco = r.getTimestamp(4) == null ? null : r.getTimestamp(4).toLocalDateTime();
			System.out.println("r.getInt(1) : "+r.getInt(1));
			System.out.println("r.getString(2) : "+r.getString(2));
			System.out.println("intro : "+intro);
			System.out.println("disco : "+disco);
			System.out.println("r.getInt(5) : "+r.getInt(5));
			System.out.println("r.getString(6) : "+r.getString(6));
			System.out.println("new Company(r.getInt(5),r.getString(6))");
			return new Computer(r.getInt(1),r.getString(2),intro,disco,new Company(r.getInt(5),r.getString(6)));
		}else{
			return null;
		}
	}

	/**
	 * This method adds a computer in the database regardless of its id and returns the computer completed with its id.
	 * @param computer to add in the database
	 * @return the computer inserted
	 * @throws SQLException
	 */
	public Computer createComputer(Computer computer) throws SQLException, MysqlDataTruncation {
		logger.info("Create computer : "+computer);
		PreparedStatement s = conn.prepareStatement(
				"Insert into computer (name,company_id,introduced,discontinued) values (?,?,?,?)"
				,Statement.RETURN_GENERATED_KEYS);
		int cpt = 1;
		s.setString(cpt++, computer.getName());
		s.setInt(cpt++, computer.getCompany().getId());
		Timestamp intro = computer.getIntroduced() == null ? null : Timestamp.valueOf( computer.getIntroduced() );
		s.setTimestamp(cpt++, intro);
	    Timestamp disco = computer.getDiscontinued() == null ? null : Timestamp.valueOf( computer.getDiscontinued() );
		s.setTimestamp(cpt++, disco);
		
		int affectedRows = s.executeUpdate();

        if ( affectedRows == 0 ) {
            throw new SQLException("Creating Computer failed, no rows affected.");
        }

        ResultSet generatedKeys = s.getGeneratedKeys();
        if ( generatedKeys.next() ) {
            int id = generatedKeys.getInt(1);
            return new Computer(id,computer.getName(),computer.getIntroduced(),computer.getDiscontinued(),computer.getCompany());
        }
        else {
            throw new SQLException("Creating Computer failed, no ID obtained.");
        }
	}

	/**
	 * This method finds a computer with has its id and modifies its attributes by those of that passed as parameter.
	 * @param oldComputer with the id of the one to be modified and with its new attributes.
	 * @return
	 * @throws SQLException
	 */
	public Computer updateComputer(Computer oldComputer) throws SQLException, MysqlDataTruncation {
		logger.info("Update a computer : "+oldComputer);
		PreparedStatement s = conn.prepareStatement(
				"UPDATE computer SET name = ?, company_id = ?, introduced = ?, discontinued = ? WHERE ID = ?"
				,Statement.RETURN_GENERATED_KEYS);
		int cpt = 1;
		s.setString(cpt++, oldComputer.getName());
		s.setInt(cpt++, oldComputer.getCompany().getId());
		Timestamp intro = oldComputer.getIntroduced() == null ? null : Timestamp.valueOf( oldComputer.getIntroduced() );
		s.setTimestamp(cpt++, intro);
	    Timestamp disco = oldComputer.getDiscontinued() == null ? null : Timestamp.valueOf( oldComputer.getDiscontinued() );
		s.setTimestamp(cpt++, disco);
		s.setInt(cpt++, oldComputer.getId());
		
		int affectedRows = s.executeUpdate();

        if( affectedRows == 0 ) {
            throw new SQLException("Updating Computer failed, no rows affected.");
        }
        return oldComputer;
	}

	/**
	 * This method removes the computer corresponding to the passed id as a parameter and returns a message confirming whether or not this deletion occurs.
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public String deleteComputer(int id) throws SQLException {
		logger.info("Delete a computer : "+id);
		PreparedStatement s = conn.prepareStatement("DELETE FROM computer where id = ?");
		s.setInt(1, id);
		
		int affectedRows = s.executeUpdate();

		if (affectedRows == 0) {
            throw new SQLException("Delete Computer failed, no rows affected.");
        }
        return "Computer "+id+" is deleted";
	}
}
