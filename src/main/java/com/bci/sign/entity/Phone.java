package com.bci.sign.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="Phone")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Phone {

    @Id
    private Long id;
    private Long number;
    private int citycode;
    private String contrycode;
    @ManyToOne
    @JoinColumn(name="userid", nullable=false)
    private UserEntity userid;
}
