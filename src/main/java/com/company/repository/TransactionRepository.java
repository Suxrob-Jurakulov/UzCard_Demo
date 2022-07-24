package com.company.repository;

import com.company.entity.TransactionsEntity;
import com.company.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends PagingAndSortingRepository<TransactionsEntity, String> {


    @Query(value = "select t from TransactionsEntity t where t.cardId = ?1")
    Page<TransactionsEntity> getPaginationByCardId(String cardId, Pageable pageable);

    @Query("from TransactionsEntity t where t.card.number = ?1")
    Page<TransactionsEntity> findByCardNum(String cardNum, Pageable pageable);

    @Query(value = "from TransactionsEntity t where t.transfer.companyId = ?1")
    Page<TransactionsEntity> findByCompanyId(String companyId, Pageable pageable);

    Page<TransactionsEntity> findByCardPhone(String cardPhone, Pageable pageable);

    @Query(value = "from TransactionsEntity t where t.cardId = ?1 and t.type = ?2")
    Page<TransactionsEntity> findByCardIdAndType(String cardId, TransactionType type, Pageable pageable);

    @Query(value = "from TransactionsEntity t where t.card.number = ?1 and t.createdDate > ?2")
    List<TransactionsEntity> getLastMonthByCardNum(String cardNum, LocalDateTime minusMonths);
}
