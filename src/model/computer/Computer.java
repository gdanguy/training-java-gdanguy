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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ( (name == null) ? 0 : name.hashCode() );
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		Computer other = (Computer) obj;
		if ( id != other.id )
			return false;
		return true;
	}

	public int compareTo(Computer c2) {
		if( this.id == c2.getId() )
			return 0;
		else if( this.id < c2.getId() )
			return -1;
		else
			return 1;
	}

}
 
