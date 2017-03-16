package main;
import java.sql.SQLException;

import cli.CLIControleur;


public class Main {	
	public static void main( String[] args ) throws ClassNotFoundException, SQLException {
		CLIControleur cli = new CLIControleur();
		cli.displayUserInterface();
	}
}
