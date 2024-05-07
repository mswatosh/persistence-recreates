package com.example.application;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


/**
 * An entity with a generated id value.
 */
@Entity
public class PurchaseOrder {

    public PurchaseOrder() {
    }

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    public UUID id;

    public String purchase;

    public PurchaseOrder(String purchase) {
        this.purchase = purchase;
    }

    @Override
    public String toString() {
        return "UUID: " + id + "   purchase: " + purchase;
    }
}

