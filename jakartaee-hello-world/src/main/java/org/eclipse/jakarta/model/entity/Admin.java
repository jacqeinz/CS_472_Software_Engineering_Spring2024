package org.eclipse.jakarta.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity


public class Admin extends Account {
    private boolean isAdmin;
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
		this.isAdmin = isAdmin;
		this.employeeId = employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
