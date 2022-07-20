package com.company.entity;

import com.company.enums.TransactionStatus;
import com.company.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class TransactionsEntity extends BaseEntity{

    @Column(name = "card_id")
    private String cardId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", updatable = false, insertable = false)
    private CardEntity card;

    @Column
    private Long amount;
    @Column(name = "transaction_amount")
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(name = "transfer_id")
    private String transferId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transfer_id", updatable = false, insertable = false)
    private TransferEntity transfer;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

}
