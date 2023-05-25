package com.bci.sign.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Users")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class UserEntity {

    @Id
    private String userid;
    private String name;
    private String email;
    private String password;
    private LocalDateTime created;
    @Column(name = "LASTLOGIN")
    private LocalDateTime lastLogin;
    private String token;
    private boolean isActive;
    @OneToMany(targetEntity=Phone.class, cascade = CascadeType.ALL,  mappedBy="userid")
    @JsonManagedReference
    private List<Phone> phones;
}
