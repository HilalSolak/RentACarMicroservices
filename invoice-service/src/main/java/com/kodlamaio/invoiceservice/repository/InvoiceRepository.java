package com.kodlamaio.invoiceservice.repository;

import com.kodlamaio.invoiceservice.entities.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository

public interface InvoiceRepository extends MongoRepository<Invoice, UUID> {
}