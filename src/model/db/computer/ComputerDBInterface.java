package model.db.computer;

import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.MysqlDataTruncation;

import model.computer.Computer;

public interface ComputerDBInterface {
	ArrayList<Computer> getAllComputer() throws SQLException;
	
	Computer getComputerDetails(int id) throws SQLException;
	
	Computer createComputer(Computer computer) throws SQLException, MysqlDataTruncation;
	
	Computer updateComputer(Computer computer) throws SQLException, MysqlDataTruncation;
	
	String deleteComputer(int id) throws SQLException;
	
}
