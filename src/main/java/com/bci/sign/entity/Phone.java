package com.bci.sign.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long number;
    private int citycode;
    private String contrycode;
    @ManyToOne
    @JoinColumn(name="userid", nullable=false)
    @JsonBackReference
    private UserEntity userid;
}
