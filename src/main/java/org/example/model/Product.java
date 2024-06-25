package org.example.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private String id;
    private String name;
    private int qty;
    private String category;
    private double price;
}
