package com.kodlamaio.paymentservice.business.rules;

import com.kodlamaio.commonpackage.utils.dto.CreateRentalPaymentRequest;
import com.kodlamaio.commonpackage.utils.exceptions.BusinessException;
import com.kodlamaio.paymentservice.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentBusinessRules {
    private final PaymentRepository repository;
    public void checkIfPaymentExists(UUID id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("Payment Not Exists");
        }
    }
    public void     checkIfPaymentIsValid(CreateRentalPaymentRequest request) {

        if (!repository.existsByCardNumberAndCardHolderAndCardExpirationMonthAndCardExpirationYearAndCardCvv(
                request.getCardNumber(),
                request.getCardHolder(),
                request.getCardExpirationMonth(),
                request.getCardExpirationYear(),
                request.getCardCvv()
        )) {
            throw new BusinessException("Not Valid Payment");
        }
    }
    public void checkIfBalanceIsEnough(double balance, double price) {
        if (balance < price) {
            throw new BusinessException("Insufficient Balance");
        }
    }
}