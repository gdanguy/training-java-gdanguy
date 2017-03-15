package model;

import java.util.ArrayList;

import configuration.Config;

public class Pages {
	private ArrayList<Object> listObject = new ArrayList<>();
	
	public Pages (ArrayList<Object> listObject){
		for(int i=0;i<listObject.size();i++)
			this.listObject.add(listObject.get(i));
	}
	
	public ArrayList<Object> getListPage(int page){
		ArrayList<Object> returnListPage = new ArrayList<>();
		for(int i=page*Config.PAGE_SIZE;i<listObject.size() && i<page*Config.PAGE_SIZE+Config.PAGE_SIZE;i++){
			returnListPage.add(listObject.get(i));
		}
		return returnListPage;
	}
}
