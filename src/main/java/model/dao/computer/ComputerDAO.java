package model.dao.computer;

import java.sql.SQLException;

import com.mysql.jdbc.MysqlDataTruncation;

import model.Pages;
import model.computer.Computer;

public interface ComputerDAO  {

	public Pages<Computer> getAllComputer() throws SQLException, ClassNotFoundException;
	public Pages<Computer> getPageComputer(int page) throws SQLException, ClassNotFoundException;
	
	public Computer getComputerDetails(int id) throws SQLException, ClassNotFoundException;
	
	public Computer createComputer(Computer computer) throws SQLException, MysqlDataTruncation;
	
	public Computer updateComputer(Computer computer) throws SQLException, MysqlDataTruncation;
	
	public String deleteComputer(int id) throws SQLException;
	
	public static ComputerDAOImpl getInstance() {
		return ComputerDAOImpl.INSTANCE;
	}
}
