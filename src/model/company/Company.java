package model.company;

public class Company {
	private int id;
	private String name;

	public Company(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
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
		Company other = (Company) obj;
		if ( this.id != other.id )
			return false;
		return true;
	}
	
	public int compareTo(Company c2) {
		if( this.id == c2.getId() )
			return 0;
		else if( this.id < c2.getId() )
			return -1;
		else
			return 1;
	}
	
}
