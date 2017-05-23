package core.utils;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {
    public static final int PAGE_SIZE = 10;

    protected List<T> listObjects = new ArrayList<>();
    protected int currentPage;
    protected int pageSize = PAGE_SIZE;

    /**
     * Constructor.
     * @param list contains the ArrayList to copy
     * @param page correspond to the currentPage
     */
    public Page(List<T> list, int page) {
        for (int i = 0; i < list.size(); i++) {
            this.listObjects.add(list.get(i));
        }
        this.currentPage = page;
    }

    /**
     * Constructor.
     * @param list     contains the ArrayList to copy
     * @param page     correspond to the currentPage
     * @param pageSize the page size
     */
    public Page(List<T> list, int page, int pageSize) {
        for (int i = 0; i < list.size(); i++) {
            this.listObjects.add(list.get(i));
        }
        this.currentPage = page;
        this.pageSize = pageSize;
    }

    /**
     * Get the ArrayList containing a T page.
     * @return the ArrayList containing a T page
     */
    public List<T> getListPage() {
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
     * Get the page size.
     * @return the page size
     */
    public int getPageSize() {
        return pageSize;
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
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return this.getClass() + " [listObjects = " + listObjects + "]";
    }

    /**
     * Equals Methode.
     * @param o other object
     * @return true if equals, else false
     */
    @Override
    public boolean equals(Object o) {
        Page<T> page = (Page<T>) o;
        if (page.getPageSize() != this.pageSize || page.getCurrentPage() != this.currentPage) {
            return false;
        }
        boolean equals = true;
        int i = 0;
        while (i < this.pageSize && equals) {
            equals = page.getListPage().get(i).equals(this.listObjects.get(i));
            i++;
        }
        return equals;
    }

    /**
     * Hash Code.
     * @return hash code
     */
    @Override
    public int hashCode() {
        int result = listObjects != null ? listObjects.hashCode() : 0;
        result = 31 * result + currentPage;
        result = 31 * result + pageSize;
        return result;
    }

    /**
     * Add a Object in the ArrayList.
     * @param t the object
     */
    public void add(T t) {
        listObjects.add(t);
    }
}
