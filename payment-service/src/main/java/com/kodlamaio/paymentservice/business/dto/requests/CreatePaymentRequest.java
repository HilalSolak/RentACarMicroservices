package com.kodlamaio.paymentservice.business.dto.requests;

import com.kodlamaio.commonpackage.utils.dto.PaymentRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreatePaymentRequest extends PaymentRequest {

    private double balance;
}
