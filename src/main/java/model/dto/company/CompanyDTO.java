package model.dto.company;

import model.company.Company;

public class CompanyDTO {
    private int id;
    private String name;

    /**
     * Constructor.
     * @param c Company
     */
    public CompanyDTO(Company c) {
        this.id = c.getId();
        this.name = c.getName();
    }

    /**
     * Get id.
     * @return the id of the Company
     */
    public int getId() {
        return id;
    }

    /**
     * Get name.
     * @return the name of the Company
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a string representation of the object.
     * @return  a string representation of the object.
     */
    @Override
    public String toString() {
        return "Company [id=" + id + ", name=" + name + "]";
    }

    /**
     * Hash Code.
     * @return hash code
     */
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    /**
     * Equals Methode.
     * @param o other object
     * @return true if equals, else false
     */
    @Override
    public boolean equals(Object o) {
        return this.id == ((Company) o).getId();
    }
}
