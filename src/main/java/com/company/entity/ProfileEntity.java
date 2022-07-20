package com.company.entity;

import com.company.enums.GeneralRole;
import com.company.enums.GeneralStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class ProfileEntity extends BaseEntity{
    @Column(nullable = false)
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column
    @Enumerated(EnumType.STRING)
    private GeneralRole role;
    @Column
    @Enumerated(EnumType.STRING)
    private GeneralStatus status = GeneralStatus.ACTIVE;
}
