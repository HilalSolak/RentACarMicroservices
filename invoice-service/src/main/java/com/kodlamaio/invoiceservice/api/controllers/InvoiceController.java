package com.kodlamaio.invoiceservice.api.controllers;

import com.kodlamaio.invoiceservice.business.abstracts.InvoiceService;
import com.kodlamaio.invoiceservice.business.dto.InvoiceGetAllResponse;
import com.kodlamaio.invoiceservice.business.dto.InvoiceGetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/invoices")
public class InvoiceController {
    private final InvoiceService service;
    @GetMapping
    public List<InvoiceGetAllResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public InvoiceGetResponse getById(@PathVariable UUID id) {
        return service.getById(id);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

}
