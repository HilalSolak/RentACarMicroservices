package com.kodlamaio.invoiceservice.business.concretes;

import com.kodlamaio.invoiceservice.business.abstracts.InvoiceService;
import com.kodlamaio.invoiceservice.business.dto.InvoiceGetAllResponse;
import com.kodlamaio.invoiceservice.business.dto.InvoiceGetResponse;
import com.kodlamaio.invoiceservice.business.rules.InvoiceBusinessRules;
import com.kodlamaio.invoiceservice.entities.Invoice;
import com.kodlamaio.invoiceservice.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class InvoiceManager implements InvoiceService {
    private final ModelMapper mapper;
    private final InvoiceRepository repository;
    private final InvoiceBusinessRules rules;



    @Override
    public void add(Invoice invoice) {
        invoice.setId(UUID.randomUUID().toString());
        repository.save(invoice);
    }

    @Override
    public InvoiceGetResponse getById(UUID id) {
        rules.checkIfEntityExist(id);
        return mapper.map(repository.findById(id).get(),InvoiceGetResponse.class);
    }

    @Override
    public List<InvoiceGetAllResponse> getAll() {
        return repository.findAll().stream().map(invoice -> mapper.map(invoice,InvoiceGetAllResponse.class)).toList();
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfEntityExist(id);
        repository.deleteById(id);
    }
}