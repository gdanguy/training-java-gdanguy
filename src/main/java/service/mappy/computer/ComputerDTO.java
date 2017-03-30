package service.mappy.computer;

import model.computer.Computer;

import java.time.format.DateTimeFormatter;

public class ComputerDTO {
    public static final DateTimeFormatter FORMAT_COURT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    protected int id;
    protected String name;
    protected String introduced;
    protected String discontinued;
    protected Integer companyId;
    protected String companyName;

    /**
     * Constructor.
     * @param c correspond to a Computer
     */
    public ComputerDTO(Computer c) {
        this.id = c.getId();
        this.name = c.getName();
        if (c.getIntroduced() != null) {
            this.introduced = c.getIntroduced().format(FORMAT_COURT).toString();
        } else {
            this.introduced = null;
        }
        if (c.getDiscontinued() != null) {
            this.discontinued = c.getDiscontinued().format(FORMAT_COURT).toString();
        } else {
            this.discontinued = null;
        }
        if (c.getCompany() != null) {
            this.companyId = c.getCompany().getId();
            this.companyName = c.getCompany().getName();
        } else {
            this.companyId = null;
            this.companyName = null;
        }
    }

    /**
     * Get id.
     * @return the id of the Computer
     */
    public int getId() {
        return id;
    }

    /**
     * Get name.
     * @return the name of the Computer
     */
    public String getName() {
        return name;
    }

    /**
     * Get introduced.
     * @return the LocalDateTime of introduce of the Computer
     */
    public String getIntroduced() {
        return introduced;
    }

    /**
     * Get discontinued.
     * @return the TimeStamp of discontinue of the Computer
     */
    public String getDiscontinued() {
        return discontinued;
    }

    /**
     * Get Company_id.
     * @return the company id of the computer
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * Get Company_name.
     * @return the company name of the computer
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * To String.
     * @return string
     */
    @Override
    public String toString() {
        return "ComputerDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", introduced='" + introduced + '\'' +
                ", discontinued='" + discontinued + '\'' +
                ", companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                '}';
    }

    /**
     * Equals Methode.
     * @param o other object
     * @return true if equals, else false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ComputerDTO that = (ComputerDTO) o;

        if (id != that.id) {
            return false;
        }
        return true;
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
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        return result;
    }
}
