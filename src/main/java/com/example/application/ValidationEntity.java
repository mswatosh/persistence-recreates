package com.example.application;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class ValidationEntity {

    @NotNull
	public String name;

	@Id
	public int id; 

    public ValidationEntity() {

    }

    public ValidationEntity(String name, int id) {
        this.name = name;
        this.id = id;
    }

}