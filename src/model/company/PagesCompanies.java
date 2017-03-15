package model.company;

import java.util.ArrayList;

import model.Pages;

public class PagesCompanies extends Pages{
	public PagesCompanies(ArrayList<Company> listObject) {
		for(int i=0;i<listObject.size();i++)
			this.listObject.add(listObject.get(i));
	}
}
