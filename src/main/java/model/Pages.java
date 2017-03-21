package model;

import java.util.ArrayList;

public class Pages<T> {
    public static final int PAGE_SIZE = 20;

    protected ArrayList<T> listObjects = new ArrayList<>();
    protected int currentPage;

    /**
     * Constructor.
     * @param list contains the ArrayList to copy
     * @param page correspond to the currentPage
     */
    public Pages(ArrayList<T> list, int page) {
        for (int i = 0; i < list.size(); i++) {
            this.listObjects.add(list.get(i));
        }
        this.currentPage = page;
    }

    /**
     * Get the ArrayList containing a T page.
     * @return the ArrayList containing a T page
     */
    public ArrayList<T> getListPage() {
        return listObjects;
    }

    /**
     * Get the next page.
     * @return next page
     */
    public int getNextPage() {
        return currentPage + 1;
    }

    /**
     * Get previous page.
     * @return previous page if currentPage > 0 else 0
     */
    public int getPreviousPage() {
        if (currentPage > 0) {
            return currentPage - 1;
        } else {
            return 0;
        }
    }

    /**
     * Get the current page.
     * @return the current page
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * Test if the ArrayList<T> is empty.
     * @return True if Empty, else false
     */
    public boolean isEmpty() {
        return listObjects.isEmpty();
    }

    /**
     * Returns a string representation of the object.
     * @return  a string representation of the object.
     */
    @Override
    public String toString() {
        return this.getClass() + " [listObjects = " + listObjects + "]";
    }

    /**
     * Add a Object in the ArrayList.
     * @param t the object
     */
    public void add(T t) {
        listObjects.add(t);
    }
}
