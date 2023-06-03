package com.kodlamaio.invoiceservice.business.abstracts;

import com.kodlamaio.invoiceservice.business.dto.InvoiceGetAllResponse;
import com.kodlamaio.invoiceservice.business.dto.InvoiceGetResponse;
import com.kodlamaio.invoiceservice.entities.Invoice;

import java.util.List;
import java.util.UUID;

public interface InvoiceService {
    void add(Invoice invoice);
    InvoiceGetResponse getById(UUID id);
    List<InvoiceGetAllResponse> getAll();
    void delete(UUID id);
}
