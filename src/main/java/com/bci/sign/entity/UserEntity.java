package com.bci.sign.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
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
    @Column(name="email")
    private String email;
    private String password;
    private Date created;
    private Date lastLogin;
    private String token;
    private boolean isActive;
    @OneToMany(targetEntity=Phone.class, mappedBy="userid", fetch= FetchType.EAGER)
    private List<Phone> phones;
}
