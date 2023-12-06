package com.example.application;

import jakarta.persistence.Embeddable;

@Embeddable
public class Ship {
    public static enum Size {
        small, large
    }

    public String name;

    public Size size;

    Ship() {
        
    }

    Ship(String name, Size size) {
        this.name = name;
        this.size = size;
    }

}
