package service.mappy.computer;

import model.company.Company;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ComputerDTO {
    public static final DateTimeFormatter FORMAT_COURT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    protected int id;
    @NotNull
    protected String name;
    @DateTimeFormat
    protected String introduced;
    @DateTimeFormat
    protected String discontinued;
    protected Integer companyId;
    protected String companyName;

    /**
     * Empty Constructor.
     */
    public ComputerDTO() {
    }

    /**
     * Set id.
     * @param id new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set Name.
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
        if (introduced != null) {
            this.introduced = introduced.format(FORMAT_COURT).toString();
        } else {
            this.introduced = null;
        }
    }

    /**
     * Set Discontinued.
     * @param discontinued new discontinued
     */
    public void setDiscontinued(LocalDateTime discontinued) {
        if (discontinued != null) {
            this.discontinued = discontinued.format(FORMAT_COURT).toString();
        } else {
            this.discontinued = null;
        }
    }

    /**
     * Set CompanyId.
     * @param companyId new CompanyId
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * Set CompanyName.
     * @param companyName new CompanyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Set CompanyId and CompanyName with a Company.
     * @param company to copy
     */
    public void setCompany(Company company) {
        if (company != null) {
            this.companyId = company.getId();
            this.companyName = company.getName();
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
