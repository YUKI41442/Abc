package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    private Integer id;
    private String name;
    private Double price;
    private Integer qty;
    private Double total;

    public Cart(int i, String text, double unitPrice, int qty, double v) {
    }
}
