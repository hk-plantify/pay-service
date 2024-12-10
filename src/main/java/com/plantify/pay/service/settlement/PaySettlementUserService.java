package com.plantify.pay.service.settlement;

import com.plantify.pay.domain.dto.settlement.PaySettlementUserResponse;
import com.plantify.pay.domain.entity.Pay;
import com.plantify.pay.domain.entity.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PaySettlementUserService {

    Page<PaySettlementUserResponse> getAllPaySettlements(Pageable pageable);
    PaySettlementUserResponse getPaySettlementByTransactionType(TransactionType transactionType);
    void savePaySettlement(Pay pay, TransactionType transactionType, Long amount);
}