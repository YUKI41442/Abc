package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private String id;
    private String name;
    private String email;
    private String address;
}
