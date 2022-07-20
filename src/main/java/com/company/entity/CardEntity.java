package com.company.entity;

import com.company.enums.GeneralStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "card")
public class CardEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String number;
    @Column(name = "expired_date")
    private LocalDateTime expiredDate;
    @Column
    private String phone;

    @Column
    @Enumerated(EnumType.STRING)
    private GeneralStatus status = GeneralStatus.NOT_ACTIVE;
    @Column
    private Long balance;

    @Column(name = "client_id")
    private String clientId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", updatable = false,insertable = false)
    private ClientEntity client;

    @Column(name = "company_id")
    private String companyId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    private CompanyEntity company;
}
