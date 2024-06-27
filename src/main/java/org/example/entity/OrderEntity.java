package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "order_table")
@Table(name = "order_table")
public class OrderEntity {
    @Id

    private String id;
    private String cusId;
    private String status;
    private Date date;
    private double amount;
}
