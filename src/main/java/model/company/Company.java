package model.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;
    @Column
    private String name;


    /**
     * Empty constructor.
     */
    public Company() {

    }

    /**
     * setter.
     * @param id .
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * setter.
     * @param name .
     */
    public void setName(String name) {
        this.name = name;
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
     * @return a string representation of the object.
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
