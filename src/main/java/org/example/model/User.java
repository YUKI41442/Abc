package org.example.model;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String id;
    private String name;
    private String email;
    private String address;
    private String password;


}
