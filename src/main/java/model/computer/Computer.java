package model.computer;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import model.company.Company;

public class Computer {
	protected int id;
	protected String name;
	protected LocalDateTime introduced;
	protected LocalDateTime discontinued;
	protected Company company;
	
	public Computer(int id, String name, LocalDateTime introduced, LocalDateTime discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public LocalDateTime getIntroduced() {
		return introduced;
	}
	
	public Timestamp getIntroducedTimeStamp() {
		return introduced == null ? null : Timestamp.valueOf( introduced );
	}
	
	public Timestamp getDiscontinuedTimeStamp() {
		return discontinued == null ? null : Timestamp.valueOf( discontinued );
	}
	
	public LocalDateTime getDiscontinued() {
		return discontinued;
	}
	
	public Company getCompany() {
		return company;
	}
	
	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name +"]";
	}
	
	public String toStringDetails() {
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", company=" + company + "]";
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
}
 
