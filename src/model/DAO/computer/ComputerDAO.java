package model.DAO.computer;

import java.sql.SQLException;

import com.mysql.jdbc.MysqlDataTruncation;

import model.computer.Computer;
import model.Pages;

public interface ComputerDAO {

	Pages<Computer> getAllComputer() throws SQLException, ClassNotFoundException;
	Pages<Computer> getPageComputer(int page) throws SQLException, ClassNotFoundException;
	
	Computer getComputerDetails(int id) throws SQLException, ClassNotFoundException;
	
	Computer createComputer(Computer computer) throws SQLException, MysqlDataTruncation;
	
	Computer updateComputer(Computer computer) throws SQLException, MysqlDataTruncation;
	
	String deleteComputer(int id) throws SQLException;
	
}
