package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "product")
@Table(name = "product")
public class ProductEntity {

    @Id
    private String id;
    private String name;
    private int qty;
    private String category;
    private double price;


}
