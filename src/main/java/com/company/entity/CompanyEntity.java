package com.company.entity;

import com.company.enums.GeneralRole;
import com.company.enums.GeneralStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "company")
public class CompanyEntity extends BaseEntity{

    @Column
    private String name;
    @Column
    private String address;
    @Column
    private String contact;
    @Column
    @Enumerated(EnumType.STRING)
    private GeneralRole role;
    @Column
    @Enumerated(EnumType.STRING)
    private GeneralStatus status = GeneralStatus.ACTIVE;

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

}
