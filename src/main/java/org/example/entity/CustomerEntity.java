package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity(name = "customer")
@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {
    @Id
    private String id;
    private String name;
    private String email;
    private String address;
}
