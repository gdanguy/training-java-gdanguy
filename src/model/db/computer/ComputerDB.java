package model.db.computer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import configuration.Config;
import model.computer.Computer;

public class ComputerDB {
	private Connection conn;

	/**
	 * Builder
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ComputerDB() throws ClassNotFoundException, SQLException {
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
	 * Cette méthode retourne la liste des ordinateurs.
	 * @return
	 */
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

	/**
	 * This method returns a computer identified by its id.
	 * @param id of the computer
	 * @return
	 */
	public Computer getComputerDetails(int id) {
		try{
			PreparedStatement s = conn.prepareStatement("SELECT computer.id, computer.name, introduced, discontinued , company_id FROM computer where id = ?");
			s.setInt(1, id);
			ResultSet r = s.executeQuery();
			if( r.next() ){
				return new Computer(r.getInt(1),r.getString(2),r.getString(3),r.getString(4),r.getInt(5));
			}else{
				return null;
			}
		}catch(SQLException e){
			return null;
		}
	}

	/**
	 * This method adds a computer in the database regardless of its id and returns the computer completed with its id.
	 * @param computer to add in the database
	 * @return the computer inserted
	 * @throws SQLException
	 */
	public Computer createComputer(Computer computer) throws SQLException {
		String date;
		boolean introduced = false;
		boolean discontinued = false;
		if( computer.getIntroduced().equals("") ){
			date=",null";
		}else{
			introduced = true;
			date=",?";
		}
		if( computer.getDiscontinued().equals("") ){
			date+=",null";
		}else{
			discontinued = true;
			date+=",?";
		}
		
		PreparedStatement s = conn.prepareStatement(
				"Insert into computer (name,company_id,introduced,discontinued) values (?,?"+date+")"
				,Statement.RETURN_GENERATED_KEYS);
		int cpt = 1;
		s.setString(cpt++, computer.getName());
		s.setInt(cpt++, computer.getCompany_id());
		if( introduced ){
			Calendar cal = setDate(computer.getIntroduced());
			s.setTimestamp(cpt++, new java.sql.Timestamp(cal.getTimeInMillis()));
		}
		if( discontinued ){
			Calendar cal = setDate(computer.getDiscontinued());
			s.setTimestamp(cpt++, new java.sql.Timestamp(cal.getTimeInMillis()));
		}
		
		int affectedRows = s.executeUpdate();

        if ( affectedRows == 0 ) {
            throw new SQLException("Creating Computer failed, no rows affected.");
        }

        ResultSet generatedKeys = s.getGeneratedKeys();
        if ( generatedKeys.next() ) {
            int id = generatedKeys.getInt(1);
            return new Computer(id,computer.getName(),computer.getIntroduced(),computer.getDiscontinued(),computer.getCompany_id());
        }
        else {
            throw new SQLException("Creating Computer failed, no ID obtained.");
        }
	}

	/**
	 * This method finds a computer with has its id and modifies its attributes by those of that passed as parameter.
	 * @param computer with the id of the one to be modified and with its new attributes.
	 * @return
	 * @throws SQLException
	 */
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
			discontinued = true;
			date+=", discontinued = ?";
		}
		
		PreparedStatement s = conn.prepareStatement(
				"UPDATE computer SET name = ?, company_id = ? "+date+" WHERE ID = ?"
				,Statement.RETURN_GENERATED_KEYS);
		int cpt = 1;
		s.setString(cpt++, computer.getName());
		s.setInt(cpt++, computer.getCompany_id());
		if( introduced ){
			Calendar cal = setDate(computer.getIntroduced());
			s.setTimestamp(cpt++, new java.sql.Timestamp(cal.getTimeInMillis()));
		}
		if( discontinued ){
			Calendar cal = setDate(computer.getDiscontinued());
			s.setTimestamp(cpt++, new java.sql.Timestamp(cal.getTimeInMillis()));
		}
		s.setInt(cpt++, computer.getId());
		
		int affectedRows = s.executeUpdate();

        if( affectedRows == 0 ) {
            throw new SQLException("Updating Computer failed, no rows affected.");
        }
        return computer;
	}

	/**
	 * This method removes the computer corresponding to the passed id as a parameter and returns a message confirming whether or not this deletion occurs.
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public String deleteComputer(int id) throws SQLException {
		PreparedStatement s = conn.prepareStatement("DELETE FROM computer where id = ?");
		s.setInt(1, id);
		
		int affectedRows = s.executeUpdate();

		if (affectedRows == 0) {
            throw new SQLException("Delete Computer failed, no rows affected.");
        }
        return "Computer "+id+" is deleted";
	}


	private static Calendar setDate(String date) throws NumberFormatException{
		
		String tempo[] = date.split(" ");
		String tempo2[] = tempo[0].split("-");
		String tempo3[] = tempo[1].split(":");
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getDefault());;
		cal.set(Integer.parseInt(tempo2[0]), Integer.parseInt(tempo2[1]), Integer.parseInt(tempo2[2]), Integer.parseInt(tempo3[0]), Integer.parseInt(tempo3[1]), Integer.parseInt(tempo3[2]));
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}
}
