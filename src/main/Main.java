package main;
import java.sql.SQLException;
import java.util.Scanner;

import Service.*;
import configuration.Config;


public class Main {	
	public static final Scanner scanner = new Scanner(System.in);
	
	public static void main( String[] args ) throws ClassNotFoundException, SQLException {
		System.out.println("Welcome, type "+Config.HELP+" for the list of commands");
		String result="";
		do{
			String retourUtilisateur = CLIService.lireSaisieUtilisateur("");
			result = CLIService.choixAction(retourUtilisateur);
			System.out.println(result);
		}while(! result.equals(Config.QUIT) );
	}
}
