package Main;
import java.sql.SQLException;
import java.util.Scanner;

import Service.*;

public class Main {

	
	public static void main( String[] args ) throws ClassNotFoundException, SQLException {
		System.out.println("Welcome, type "+Config.HELP+" for the list of commands");
		String action="";
		Scanner s = new Scanner(System.in);
		do{
			String retourUtilisateur = CLIService.lireSaisieUtilisateur(s,"");
			action = CLIService.choixAction(retourUtilisateur,s);
			System.out.println(action);
		}while(! action.equals(Config.QUIT) );
		s.close();
	}
	
}
