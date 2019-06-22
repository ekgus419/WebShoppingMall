package com.dh.webservice.domain;


import lombok.Data;

import javax.persistence.*;

@Entity(name = "ROLE")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private int id;
    @Column(name = "role_name")
    private String roleName;
}