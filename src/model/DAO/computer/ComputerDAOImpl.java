package model.DAO.computer;

import java.sql.Connection;
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

import model.Pages;
import model.company.Company;
import model.computer.Computer;
import utils.Utils;

public enum ComputerDAOImpl implements ComputerDAO{
	INSTANCE;
	
	private Logger logger = LoggerFactory.getLogger(ComputerDAOImpl.class);
	private Connection conn;

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
		conn = Utils.openConnection();
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
		Utils.closeConnection(conn);
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
		conn = Utils.openConnection();
		PreparedStatement s = conn.prepareStatement("SELECT c1.id, c1.name, introduced, discontinued , c2.id, c2.name"
												+ " FROM computer c1"
												+ " LEFT JOIN company c2 ON c2.id = c1.company_id"
												+ " WHERE c1.id = ?");
		s.setInt(1, id);
		ResultSet r = s.executeQuery();
		if( r.next() ){
			LocalDateTime intro = r.getTimestamp(3) == null ? null : r.getTimestamp(3).toLocalDateTime();
			LocalDateTime disco = r.getTimestamp(4) == null ? null : r.getTimestamp(4).toLocalDateTime();
			
			Computer result = new Computer(r.getInt(1),r.getString(2),intro,disco,new Company(r.getInt(5),r.getString(6)));
			Utils.closeConnection(conn);
			return result;
		}else{
			Utils.closeConnection(conn);
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
		conn = Utils.openConnection();
		PreparedStatement s = conn.prepareStatement(
				"Insert into computer (name,company_id,introduced,discontinued) values (?,?,?,?)"
				,Statement.RETURN_GENERATED_KEYS);
		s.setString(1, computer.getName());
		s.setInt(2, computer.getCompany().getId());
		Timestamp intro = computer.getIntroduced() == null ? null : Timestamp.valueOf( computer.getIntroduced() );
		s.setTimestamp(3, intro);
	    Timestamp disco = computer.getDiscontinued() == null ? null : Timestamp.valueOf( computer.getDiscontinued() );
		s.setTimestamp(4, disco);
		
		int affectedRows = s.executeUpdate();

        if ( affectedRows == 0 ) {
    		Utils.closeConnection(conn);
            throw new SQLException("Creating Computer failed, no rows affected.");
        }

        ResultSet generatedKeys = s.getGeneratedKeys();
        if ( generatedKeys.next() ) {
            int id = generatedKeys.getInt(1);
            Computer result =  new Computer(id,computer.getName(),computer.getIntroduced(),computer.getDiscontinued(),computer.getCompany());
    		Utils.closeConnection(conn);
    		return result;
        }
        else {
    		Utils.closeConnection(conn);
            throw new SQLException("Creating Computer failed, no ID obtained.");
        }
	}

	/**
	 * This method finds a computer with has its id and modifies its attributes by those of that passed as parameter.
	 * @param modifiedComputer with the id of the one to be modified and with its new attributes.
	 * @return
	 * @throws SQLException
	 */
	public Computer updateComputer(Computer modifiedComputer) throws SQLException, MysqlDataTruncation {
		logger.info("Update a computer : "+modifiedComputer);
		conn = Utils.openConnection();
		PreparedStatement s = conn.prepareStatement(
				"UPDATE computer SET name = ?, company_id = ?, introduced = ?, discontinued = ? WHERE ID = ?"
				,Statement.RETURN_GENERATED_KEYS);
		s.setString(1, modifiedComputer.getName());
		s.setInt(2, modifiedComputer.getCompany().getId());
		Timestamp intro = modifiedComputer.getIntroduced()   == null ? null : Timestamp.valueOf( modifiedComputer.getIntroduced() );
		s.setTimestamp(3, intro);
	    Timestamp disco = modifiedComputer.getDiscontinued() == null ? null : Timestamp.valueOf( modifiedComputer.getDiscontinued() );
		s.setTimestamp(4, disco);
		s.setInt(5, modifiedComputer.getId());
		
		int affectedRows = s.executeUpdate();

        if( affectedRows == 0 ) {
    		Utils.closeConnection(conn);
            throw new SQLException("Updating Computer failed, no rows affected.");
        }
		Utils.closeConnection(conn);
        return modifiedComputer;
	}

	/**
	 * This method removes the computer corresponding to the passed id as a parameter and returns a message confirming whether or not this deletion occurs.
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public String deleteComputer(int id) throws SQLException {
		logger.info("Delete a computer : "+id);
		conn = Utils.openConnection();
		PreparedStatement s = conn.prepareStatement("DELETE FROM computer where id = ?");
		s.setInt(1, id);
		
		int affectedRows = s.executeUpdate();

		if (affectedRows == 0) {
    		Utils.closeConnection(conn);
            throw new SQLException("Delete Computer failed, no rows affected.");
        }
		Utils.closeConnection(conn);
        return "Computer "+id+" is deleted";
	}
}
