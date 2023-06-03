package com.kodlamaio.invoiceservice.business.rules;

import com.kodlamaio.commonpackage.utils.exceptions.BusinessException;
import com.kodlamaio.invoiceservice.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvoiceBusinessRules {
    private final InvoiceRepository repository;
    public void checkIfEntityExist(UUID id){
        if(repository.findById(id).isEmpty()){
            throw new BusinessException("Invoice not found.");
        }
    }
}