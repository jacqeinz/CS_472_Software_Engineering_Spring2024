/*
 * RoomReportAdmin.java
 */
package org.eclipse.jakarta.model.entity;

// Imports 
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.util.*;
import java.io.Serializable;

/**
 * This class represents the entity RoomReportAdmin.
 * 
 * @author Team ABCFG
 */
@Entity
public class RoomReportAdmin implements Serializable {
	
	//	
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	//
    @Id
    @ManyToOne
    @JoinColumn(name = "idRoomReport", referencedColumnName = "ID")
    private RoomReport roomReport;
    
    //
    @Id
    @ManyToOne
    @JoinColumn(name = "idAdmin", referencedColumnName = "ID")
    private Admin admin;
	
    /**
	 * 
	 */
    public RoomReportAdmin() {
    }

    /**
	 * 
	 */
    public RoomReportAdmin(RoomReport roomReport, Admin admin) {
        this.roomReport = roomReport;
        this.admin = admin;
    }

    /**
	 * 
	 */
    public Long getId() {
        return id;
    }

    /**
	 * 
	 */
    public void setId(Long id) {
        this.id = id;
    }

    /**
	 * 
	 */
    public RoomReport getRoomReport() {
        return roomReport;
    }

    /**
	 * 
	 */
    public void setRoomReport(RoomReport roomReport) {
        this.roomReport = roomReport;
    }

    /**
	 * 
	 */
    public Admin getAdmin() {
        return admin;
    }

    /**
	 * 
	 */
    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    /**
	 * 
	 */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    /**
	 * 
	 */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RoomReportAdmin other = (RoomReportAdmin) obj;
        return Objects.equals(this.id, other.id);
    }

    /**
	 * 
	 */
    @Override
    public String toString() {
        return "RoomReportAdmin{" + "id=" + id + ", roomReport=" + roomReport + ", admin=" + admin + '}';
    }
}
