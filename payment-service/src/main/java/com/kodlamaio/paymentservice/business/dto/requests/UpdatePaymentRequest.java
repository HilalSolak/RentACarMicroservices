package com.kodlamaio.paymentservice.business.dto.requests;

import com.kodlamaio.commonpackage.utils.dto.PaymentRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdatePaymentRequest extends PaymentRequest {
    private UUID id;
    private double balance;
}
