package model.company;

public class Company {
    private int id;
    private String name;

    /**
     * Constructor.
     * @param id the id of the Company
     * @param name the name of the Company
     */
    public Company(int id, String name) {
        this.id = id;
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
     * @return  a string representation of the object.
     */
    @Override
    public String toString() {
        return "Company [id=" + id + ", name=" + name + "]";
    }

}
