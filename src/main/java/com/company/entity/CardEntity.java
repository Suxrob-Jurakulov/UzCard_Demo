package com.company.entity;

import com.company.enums.GeneralStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "card")
public class CardEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    String number;
    @Column(name = "hidden_number")
    String hiddenNumber;
    @Column(name = "expired_date")
    LocalDateTime expiredDate;
    @Column
    String phone;

    @Column
    @Enumerated(EnumType.STRING)
    GeneralStatus status = GeneralStatus.NOT_ACTIVE;
    @Column
    Long balance;

    @Column(name = "client_id")
    String clientId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", updatable = false, insertable = false)
    ClientEntity client;

    @Column(name = "company_id")
    String companyId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    CompanyEntity company;
}
