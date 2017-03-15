package model.computer;

import java.util.ArrayList;

import model.Pages;

public class PagesComputers extends Pages{
	public PagesComputers(ArrayList<Computer> listObject) {
		for(int i=0;i<listObject.size();i++)
			this.listObject.add(listObject.get(i));
	}
}
