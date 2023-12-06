package com.example.application;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class FODCrewMember {

	public String name;

	@Id
	public int crewID; 

	public boolean registered;

}