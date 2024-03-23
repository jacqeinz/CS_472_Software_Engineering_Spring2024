package org.eclipse.jakarta.model.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.util.*;
@Entity
public class ReservationDetails implements Serializable {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	private List<Date> list = new ArrayList<Date>();
	private String guest;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Date> getList() {
		return list;
	}
	public void setList(List<Date> list) {
		this.list = list;
	}
	public String getGuest() {
		return guest;
	}
	public void setGuest(String guest) {
		this.guest = guest;
	}

}
