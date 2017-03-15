package main;
import java.sql.SQLException;
import java.util.Scanner;

import Service.*;
import configuration.Config;


public class Main {	
	public static void main( String[] args ) throws ClassNotFoundException, SQLException {
		System.out.println("Welcome, type "+Config.HELP+" for the list of commands");
		String result="";
		Scanner s = new Scanner(System.in);
		do{
			String retourUtilisateur = CLIService.lireSaisieUtilisateur(s,"");
			result = CLIService.choixAction(retourUtilisateur,s);
			System.out.println(result);
		}while(! result.equals(Config.QUIT) );
		s.close();
	}
}
