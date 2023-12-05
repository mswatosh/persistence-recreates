package com.example.application;

import jakarta.persistence.Embeddable;

@Embeddable
public class Ship {
    public static enum Size {
        small, large
    }

    public String shipName;

    public Size size;

    Ship() {
        
    }

    Ship(String name, Size size) {
        this.shipName = name;
        this.size = size;
    }

}
