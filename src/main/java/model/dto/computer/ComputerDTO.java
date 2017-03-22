package model.dto.computer;

import model.computer.Computer;

import java.time.format.DateTimeFormatter;

public class ComputerDTO {
    public static final DateTimeFormatter FORMAT_COURT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    protected int id;
    protected String name;
    protected String introduced;
    protected String discontinued;
    protected Integer company_id;
    protected String company_name;

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
            this.company_id = c.getCompany().getId();
            this.company_name = c.getCompany().getName();
        } else {
            this.company_id = null;
            this.company_name = null;
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
        return company_id;
    }

    /**
     * Get Company_name.
     * @return the company name of the computer
     */
    public String getCompanyName() {
        return company_name;
    }
}
