package com.company.entity;

import com.company.enums.GeneralRole;
import com.company.enums.GeneralStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "client", uniqueConstraints = @UniqueConstraint
        (columnNames = {"passport_series", "passport_number"}))
public class ClientEntity extends BaseEntity{
    @Column(nullable = false)
    private String name;
    @Column
    private String surname;
    @Column(name = "middle_name")
    private String middleName;
    @Column(nullable = false)
    private String phone;
    @Column(name = "passport_series", nullable = false)
    private String passportSeries;
    @Column(name = "passport_number", nullable = false)
    private String passportNumber;
    @Column
    @Enumerated(EnumType.STRING)
    private GeneralStatus status = GeneralStatus.ACTIVE;
}
