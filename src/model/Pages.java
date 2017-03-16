package model;

import java.util.ArrayList;

public class Pages<T> {
	public static final int PAGE_SIZE = 20;
	
	protected ArrayList< T > listObjects = new ArrayList<>();
	protected int currentPage;
	
	
	public Pages (ArrayList< T > list,int page){
		for(int i=0;i<list.size();i++){
			this.listObjects.add(list.get(i));
		}
		this.currentPage=page;
	}
	
	/**
	 * Returns an ArrayList containing a T page
	 * @param page
	 * @return
	 */
	protected ArrayList< T > getListPage(){
		return listObjects;
	}
	
	public int getNextPage(){
		return currentPage + 1;
	}
	
	public int getPreviousPage(){
		if( currentPage > 0 )
			return currentPage - 1;
		else
			return 0;
	}
	
	public boolean isEmpty() {
		return listObjects.isEmpty();
	}
	
	@Override
	public String toString() {
		return this.getClass()+" [listObjects = " + listObjects + "]";
	}
	
	public void add(T t){
		listObjects.add(t);
	}
}
