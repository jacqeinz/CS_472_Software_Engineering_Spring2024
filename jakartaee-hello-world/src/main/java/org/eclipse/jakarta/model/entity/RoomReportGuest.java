/*
 * RoomReportGuest.java
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
 * This class represents the entity RoomReportGuest.
 * 
 * @author Team ABCFG
 */
@Entity
public class RoomReportGuest implements Serializable {
	
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
    @JoinColumn(name = "idGuest", referencedColumnName = "ID")
    private Guest guest;

    /**
	 * 
	 */
    public RoomReportGuest() {
    }

    /**
	 * 
	 */
    public RoomReportGuest(RoomReport roomReport, Guest guest) {
        this.roomReport = roomReport;
        this.guest = guest;
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
    public Guest getGuest() {
        return guest;
    }

    /**
	 * 
	 */
    public void setGuest(Guest guest) {
        this.guest = guest;
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
        final RoomReportGuest other = (RoomReportGuest) obj;
        return Objects.equals(this.id, other.id);
    }

    /**
	 * 
	 */
    @Override
    public String toString() {
        return "RoomReportGuest{" + "id=" + id + ", roomReport=" + roomReport + ", guest=" + guest + '}';
    }
}
