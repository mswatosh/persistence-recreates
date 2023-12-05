package com.example.application;


import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CrewMember {

	public String name;

	public String rank;

	@Id
	public int crewID; 

	@Embedded
	public Ship ship;

}