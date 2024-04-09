package com.business.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("Admin")
public class Admin extends Account {

    private int employeeId;
    
    
    /**
	 * 
	 */

	/**
	 * 
	 */

	public int getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param isAdmin
	 * @param employeeId
	 */
	public Admin(boolean isAdmin, int employeeId) {
		super();
	
		this.employeeId = employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}


}
