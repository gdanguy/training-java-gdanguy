package model.db.computer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Main.Config;
import model.computer.Computer;

public class ComputerDB {
	private Connection conn;

	public ComputerDB() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(Config.URL_DB, Config.USER_DB, Config.PASSWORD_DB);

	}

	protected void finalize() {
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (Exception e) {
		}
	}

	public ArrayList<Computer> getComputer() {
		try{
			PreparedStatement s = conn.prepareStatement("SELECT computer.id, computer.name, introduced, discontinued , company_id FROM computer");
			ResultSet r = s.executeQuery();
			ArrayList<Computer> result = new ArrayList<>();
			while(r.next()){
				result.add(new Computer(r.getInt(1),r.getString(2),r.getString(3),r.getString(4),r.getInt(5)));
			}
			return result;
		}catch(SQLException e){
			return null;
		}
		
	}

	public Computer getComputerDetails(int id) {
		try{
			PreparedStatement s = conn.prepareStatement("SELECT computer.id, computer.name, introduced, discontinued , company_id FROM computer where id = ?");
			s.setInt(1, id);
			ResultSet r = s.executeQuery();
			if(r.next()){
				return new Computer(r.getInt(1),r.getString(2),r.getString(3),r.getString(4),r.getInt(5));
			}else{
				return null;
			}
		}catch(SQLException e){
			return null;
		}
	}

	public Computer createComputer(Computer computer) throws SQLException {
		String date;
		boolean introduced = false;
		boolean discontinued = false;
		if( computer.getIntroduced().equals("") ){
			date="null,";
		}else{
			introduced = true;
			date="?,";
		}
		if( computer.getDiscontinued().equals("") ){
			date+="null";
		}else{
			introduced = true;
			date+="?";
		}
		
		PreparedStatement s = conn.prepareStatement(
				"Insert into computer (name,company_id,introduced,discontinued) values (?,?,"+date+")"
				,Statement.RETURN_GENERATED_KEYS);
		int cpt = 1;
		s.setString(cpt++, computer.getName());
		s.setInt(cpt++, computer.getCompany_id());
		if( introduced ){
			s.setString(cpt++, computer.getIntroduced());
		}
		if( discontinued ){
			s.setString(cpt++, computer.getDiscontinued());
		}
		
		int affectedRows = s.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating Computer failed, no rows affected.");
        }

        ResultSet generatedKeys = s.getGeneratedKeys();
        if (generatedKeys.next()) {
            int id = generatedKeys.getInt(1);
            return new Computer(id,computer.getName(),computer.getIntroduced(),computer.getDiscontinued(),computer.getCompany_id());
        }
        else {
            throw new SQLException("Creating Computer failed, no ID obtained.");
        }
	}

	public Computer updateComputer(Computer computer) throws SQLException {
		String date;
		boolean introduced = false;
		boolean discontinued = false;
		if( computer.getIntroduced().equals("") ){
			date="";
		}else{
			introduced = true;
			date=", introduced = ?";
		}
		if( computer.getDiscontinued().equals("") ){
			date+="";
		}else{
			introduced = true;
			date+=", discontinued = ?";
		}
		
		PreparedStatement s = conn.prepareStatement(
				"UPDATE computer SET name = ?, company_id = ? "+date+" WHERE ID = ?"
				,Statement.RETURN_GENERATED_KEYS);
		int cpt = 1;
		s.setString(cpt++, computer.getName());
		s.setInt(cpt++, computer.getCompany_id());
		if( introduced ){
			s.setString(cpt++, computer.getIntroduced());
		}
		if( discontinued ){
			s.setString(cpt++, computer.getDiscontinued());
		}
		s.setInt(cpt++, computer.getId());
		
		int affectedRows = s.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Updating Computer failed, no rows affected.");
        }
        return computer;
	}

	public String deleteComputer(int id) throws SQLException {
		PreparedStatement s = conn.prepareStatement("DELETE FROM computer where id = ?");
		s.setInt(1, id);
		
		int affectedRows = s.executeUpdate();

		if (affectedRows == 0) {
            throw new SQLException("Delete Computer failed, no rows affected.");
        }
        return "Computer "+id+" is deleted";
	}
}
