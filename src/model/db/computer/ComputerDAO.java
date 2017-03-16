package model.db.computer;

import java.sql.SQLException;

import com.mysql.jdbc.MysqlDataTruncation;

import model.computer.Computer;
import model.Pages;

public interface ComputerDAO {

	Pages<Computer> getAllComputer() throws SQLException;
	Pages<Computer> getPageComputer(int page) throws SQLException;
	
	Computer getComputerDetails(int id) throws SQLException;
	
	Computer createComputer(Computer computer) throws SQLException, MysqlDataTruncation;
	
	Computer updateComputer(Computer computer) throws SQLException, MysqlDataTruncation;
	
	String deleteComputer(int id) throws SQLException;
	
}
