package com.kodlamaio.commonpackage.utils.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentRequest {

    @NotBlank(message = "Card Number Can Not Be Null!")
    @Length(min = 16, max = 16, message = "Card Number Must Be 16 Digits")
    private String cardNumber;

    @NotBlank
    @Length(min = 5)
    private String cardHolder;

    @Min(2023)
    private int cardExpirationYear;

    @Max(value = 12)
    @Min(value = 1)
    private int cardExpirationMonth;
    @NotBlank
    @Length(min = 3, max = 3)
    private String cardCvv;
}