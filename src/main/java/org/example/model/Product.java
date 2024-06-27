package org.example.model;


import lombok.*;

@Data
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
