package core.model;

import core.validator.Validateur;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "computer")
public class Computer {
    public static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter FORMAT2 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected int id;
    @Column(name = "name")
    protected String name;
    @Column(name = "introduced")
    protected LocalDateTime introduced;
    @Column(name = "discontinued")
    protected LocalDateTime discontinued;
    @ManyToOne
    @JoinColumn(name = "company_id")
    protected Company company;

    /**
     * Empty constructor.
     */
    public Computer() {

    }

    /**
     * Set id.
     * @param id new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set name.
     * @param name new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set Introduced.
     * @param introduced new introduced
     */
    public void setIntroduced(LocalDateTime introduced) {
        this.introduced = introduced;
    }

    /**
     * Set Introduced.
     * @param introduced String
     */
    public void setIntroduced(String introduced) {
        this.introduced = Validateur.parseString(introduced);
    }

    /**
     * Set Discontinued.
     * @param discontinued new discontinued
     */
    public void setDiscontinued(LocalDateTime discontinued) {
        this.discontinued = discontinued;
    }

    /**
     * Set Discontinued.
     * @param discontinued String
     */
    public void setDiscontinued(String discontinued) {
        this.introduced = Validateur.parseString(discontinued);
    }

    /**
     * Set model.Company.
     * @param company new model.Company
     */
    public void setCompany(Company company) {
        this.company = company;
    }


    /**
     * Get id.
     * @return the id of the model.Computer
     */
    public int getId() {
        return id;
    }

    /**
     * Get name.
     * @return the name of the model.Computer
     */
    public String getName() {
        return name;
    }

    /**
     * Get introduced.
     * @return the LocalDateTime of introduce of the model.Computer
     */
    public LocalDateTime getIntroduced() {
        return introduced;
    }

    /**
     * Get introduced.
     * @return the TimeStamp of introduce of the model.Computer
     */
    public Timestamp getIntroducedTimeStamp() {
        return introduced == null ? null : Timestamp.valueOf(introduced);
    }

    /**
     * Get discontinued.
     * @return the LocalDateTime of discontinue of the model.Computer
     */
    public Timestamp getDiscontinuedTimeStamp() {
        return discontinued == null ? null : Timestamp.valueOf(discontinued);
    }

    /**
     * Get discontinued.
     * @return the TimeStamp of discontinue of the model.Computer
     */
    public LocalDateTime getDiscontinued() {
        return discontinued;
    }

    /**
     * Get model.Company.
     * @return the company of the computer
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Returns a string simple representation of the object.
     * @return a string simple representation of the object.
     */
    @Override
    public String toString() {
        return "model.Computer [id=" + id + ", name=" + name + "]";
    }

    /**
     * Returns a string detailed representation of the object.
     * @return a string detailed representation of the object.
     */
    public String toStringDetails() {
        return "model.Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
                + ", company=" + company + "]";
    }

    /**
     * Equals Methode.
     * @param o other object
     * @return true if equals, else false
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        return this.id == ((Computer) o).getId();
    }

    /**
     * Hash Code.
     * @return hash code
     */
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (introduced != null ? introduced.hashCode() : 0);
        result = 31 * result + (discontinued != null ? discontinued.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        return result;
    }

    /**
     * Convert String to LocalDateTime.
     * @param date in String format
     * @return date in LocalDateTime format
     */
    public static LocalDateTime toLocalDateTime(String date) {
        return LocalDateTime.parse((date + " 00:00:00"), FORMAT);
    }

    /**
     * Convert String to LocalDateTime.
     * @param date in String format2
     * @return date in LocalDateTime format2
     */
    public static LocalDateTime toLocalDateTime2(String date) {
        return LocalDateTime.parse((date + " 00:00:00"), FORMAT2);
    }
}