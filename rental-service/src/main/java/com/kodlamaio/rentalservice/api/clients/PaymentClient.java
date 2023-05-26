package com.kodlamaio.rentalservice.api.clients;

import com.kodlamaio.commonpackage.utils.dto.ClientResponse;
import com.kodlamaio.commonpackage.utils.dto.CreateRentalPaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@FeignClient(name = "payment-service")
public interface PaymentClient {

    @PutMapping(value = "/api/payments/processRentalPayment")
    ClientResponse processRentalPayment(@RequestBody CreateRentalPaymentRequest request);
}