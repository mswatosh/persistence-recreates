package com.example.application;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SimpleCrewMember {

	public String name;

	@Id
	public int crewID; 

}