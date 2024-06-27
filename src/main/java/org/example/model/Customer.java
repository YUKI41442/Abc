package org.example.model;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private String id;
    private String name;
    private String email;
    private String address;


}
