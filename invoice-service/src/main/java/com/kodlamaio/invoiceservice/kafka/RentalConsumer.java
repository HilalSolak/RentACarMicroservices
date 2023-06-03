package com.kodlamaio.invoiceservice.kafka;

import com.kodlamaio.commonpackage.events.rental.InvoiceRentalCreateEvent;
import com.kodlamaio.invoiceservice.business.abstracts.InvoiceService;
import com.kodlamaio.invoiceservice.entities.Invoice;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalConsumer {
    private final InvoiceService service;
    private final ModelMapper mapper;

    @KafkaListener(
            topics =  "rental-created-for-invoice",
            groupId = "rental-create-for-invoice"
    )
    public void consume(InvoiceRentalCreateEvent event){
        service.add(mapper.map(event, Invoice.class));
    }

}
