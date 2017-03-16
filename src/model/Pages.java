package model;

import java.util.ArrayList;
import java.util.Scanner;

import Service.CLIService;
import configuration.Config;

public abstract class Pages {
	public static final String NEXT_PAGE = "+";
	public static final String PREVIOUS_PAGE = "-";
	
	protected ArrayList<Object> listObject = new ArrayList<>();
	protected int maxPage;
	
	/**
	 * Returns an ArrayList containing an object page
	 * @param page
	 * @return
	 */
	protected ArrayList<Object> getListPage(int page){
		ArrayList<Object> returnListPage = new ArrayList<>();
		for(int i=page*Config.PAGE_SIZE;(i<listObject.size() && i<page*Config.PAGE_SIZE+Config.PAGE_SIZE);i++){
			returnListPage.add(listObject.get(i));
		}
		maxPage = listObject.size()/Config.PAGE_SIZE;
		return returnListPage;
	}

	@Override
	public String toString() {
		return this.getClass()+" [listObject=" + listObject + "]";
	}

	/**
	 * Shows a modifiable range of object to the user
	 * @param s
	 */
	public void display(Scanner s) {
		if( listObject.isEmpty() ){
			System.out.println("No item wanted in the database");
		}else{
			int numPage = 0;
			ArrayList<Object> page = new ArrayList<>();
			boolean quit;
			do{
				quit = true;
				page = getListPage(numPage);
				System.out.println(page);
				String input = CLIService.lireSaisieUtilisateur(s, "Type '"+NEXT_PAGE+"' for next page, '"+PREVIOUS_PAGE+"' for previous page, other for quit");
				if( input.equals(NEXT_PAGE) ){
					numPage++;
					quit = false;
					if( numPage>maxPage )
						numPage=maxPage;
				}else if( input.equals(PREVIOUS_PAGE) ){
					numPage--;
					quit = false;
					if( numPage<0 )
						numPage=0;
				}
			}while( !quit );
		}
	}
}