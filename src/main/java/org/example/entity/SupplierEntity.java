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
@Entity(name = "supplier")
@Table(name = "supplier")
public class SupplierEntity {

    @Id
    private String id;
    private String name;
    private String email;
    private String company;


}
