/*
 * RoomReport.java
 */
package org.eclipse.jakarta.model.entity;

// Imports 
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.util.*;
import java.io.Serializable;

/**
 * This class represents the entity RoomReport.
 * 
 * @author Team ABCFG
 */
@Entity
public class RoomReport implements Serializable {
	
	//	
	@Id
	@Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//	
	@Column(name = "Room Status", nullable = false, unique = false)
	private String roomStatus;
	
	//
	@Column(name = "Room Number", nullable = false, unique = false)
	private int roomNumber;
	
	//
	@Column(name = "Bed Size", nullable = false, unique = false)
	private String bedSize;
	
	//
	@Column(name = "Guest Name", nullable = false, unique = false)
	private String guestName; 
	
	//
	@ManyToMany
	private List<String> occupants = new ArrayList<String>();
	
	/**
	 * 
	 */
	public RoomReport() {
    }

	/**
	 * 
	 */
    public RoomReport(String roomStatus, int roomNumber, String bedSize, String guestName) {
        this.roomStatus = roomStatus;
        this.roomNumber = roomNumber;
        this.bedSize = bedSize;
        this.guestName = guestName;
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
    public String getRoomStatus() {
        return roomStatus;
    }

    /**
	 * 
	 */
    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }
    
    /**
	 * 
	 */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
	 * 
	 */
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
	 * 
	 */
    public String getBedSize() {
        return bedSize;
    }

    /**
	 * 
	 */
    public void setBedSize(String bedSize) {
        this.bedSize = bedSize;
    }

    /**
	 * 
	 */
    public String getGuestName() {
        return guestName;
    }

    /**
	 * 
	 */
    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    /**
	 * 
	 */
    public List<String> getOccupants() {
        return occupants;
    }

    /**
	 * 
	 */
    public void setOccupants(List<String> occupants) {
        this.occupants = occupants;
    }
	
    /**
	 * 
	 */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final RoomReport other = (RoomReport) obj;
        return Objects.equals(this.id, other.id);
    }

    /**
	 * 
	 */
    @Override
    public String toString() {
        return "RoomReport{" + "id=" + id + ", roomStatus=" + roomStatus + ", roomNumber=" + roomNumber + ", bedSize=" + bedSize + ", guestName=" + guestName + ", occupants=" + occupants + '}';
    }
}
