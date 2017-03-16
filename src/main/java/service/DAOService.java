package service;

import java.sql.SQLException;

import com.mysql.jdbc.MysqlDataTruncation;

import model.Pages;
import model.company.Company;
import model.computer.Computer;

public interface DAOService {
	
	public Pages<?> list(String type) throws ClassNotFoundException, SQLException;
	
	public Pages<?> list(String type, int page) throws ClassNotFoundException, SQLException;
	
	public Pages<Computer> listComputers(int page) throws ClassNotFoundException, SQLException;
	
	public Pages<Company> listCompanies(int page) throws ClassNotFoundException, SQLException;
	
	public String showComputerdetails(int id) throws ClassNotFoundException, SQLException;
	
	public Computer getComputer(int id) throws ClassNotFoundException, SQLException;
	
	public String createComputer(Computer computer) throws ClassNotFoundException, SQLException, NumberFormatException, MysqlDataTruncation;
	
	public String updateComputer(Computer computer) throws ClassNotFoundException, SQLException, NumberFormatException, MysqlDataTruncation;
	
	public String deleteComputer(int id) throws ClassNotFoundException, SQLException;
}
