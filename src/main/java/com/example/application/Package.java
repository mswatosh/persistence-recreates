package com.example.application;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Packages6")
public class Package {

    public String description;

    @Id
    public int id;

    public float height;

    public float length;

    public float width;

    public Package() {
    }

    public Package(int id, float length, float width, float height, String description) {
        this.id = id;
        this.length = length;
        this.width = width;
        this.height = height;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Package id=" + id + "; L=" + length + "; W=" + width + "; H=" + height + " " + description;
    }
}

