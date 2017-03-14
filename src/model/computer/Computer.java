package model.computer;

public class Computer {
	protected int id;
	protected String name;
	protected String introduced;
	protected String discontinued;
	protected int company_id;
	
	public Computer(int id, String name, String introduced, String discontinued, int company_id) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company_id = company_id;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getIntroduced() {
		return introduced;
	}
	
	public String getDiscontinued() {
		return discontinued;
	}
	
	public int getCompany_id() {
		return company_id;
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name +"]";
	}
	
	public String toStringDetails() {
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", company_id=" + company_id + "]";
	}


}
 
