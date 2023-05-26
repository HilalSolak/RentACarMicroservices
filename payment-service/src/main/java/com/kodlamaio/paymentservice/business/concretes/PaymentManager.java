package com.kodlamaio.paymentservice.business.concretes;

import com.kodlamaio.commonpackage.utils.dto.ClientResponse;
import com.kodlamaio.commonpackage.utils.dto.CreateRentalPaymentRequest;
import com.kodlamaio.commonpackage.utils.exceptions.BusinessException;
import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.paymentservice.business.abstracts.PaymentService;
import com.kodlamaio.paymentservice.business.dto.requests.CreatePaymentRequest;
import com.kodlamaio.paymentservice.business.dto.requests.UpdatePaymentRequest;
import com.kodlamaio.paymentservice.business.dto.responses.CreatePaymentResponse;
import com.kodlamaio.paymentservice.business.dto.responses.GetAllPaymentsResponse;
import com.kodlamaio.paymentservice.business.dto.responses.GetPaymentResponse;
import com.kodlamaio.paymentservice.business.dto.responses.UpdatePaymentResponse;
import com.kodlamaio.paymentservice.business.rules.PaymentBusinessRules;
import com.kodlamaio.paymentservice.entities.Payment;
import com.kodlamaio.paymentservice.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentManager implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final ModelMapperService mapper;
    private final PaymentBusinessRules rules;
    @Override
    public List<GetAllPaymentsResponse> getAll() {
        List<Payment> payments = paymentRepository.findAll();
        List<GetAllPaymentsResponse> response = payments
                .stream()
                .map(payment -> mapper.forResponse().map(payment, GetAllPaymentsResponse.class))
                .toList();
        return response;
    }

    @Override
    public GetPaymentResponse getById(UUID id) {
        rules.checkIfPaymentExists(id);
        Payment payment = paymentRepository.findById(id).orElseThrow();
        GetPaymentResponse response = mapper.forResponse().map(payment, GetPaymentResponse.class);
        return response;
    }

    @Override
    public CreatePaymentResponse add(CreatePaymentRequest request) {
        Payment payment = mapper.forRequest().map(request, Payment.class);
        payment.setId(null);
        paymentRepository.save(payment);
        CreatePaymentResponse response = mapper.forResponse().map(payment, CreatePaymentResponse.class);
        return response;
    }

    @Override
    public UpdatePaymentResponse update(UUID id, UpdatePaymentRequest request) {
        rules.checkIfPaymentExists(id);
        Payment payment = mapper.forRequest().map(request, Payment.class);
        payment.setId(id);
        paymentRepository.save(payment);
        UpdatePaymentResponse response = mapper.forResponse().map(payment, UpdatePaymentResponse.class);
        return response;
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfPaymentExists(id);
        paymentRepository.deleteById(id);
    }

    @Override
    public ClientResponse processRentalPayment(CreateRentalPaymentRequest request) {
        ClientResponse response = new ClientResponse();
        Payment payment = null;
        try{
            payment = paymentRepository.findByCardNumber(request.getCardNumber());
            rules.checkIfPaymentIsValid(request);
            rules.checkIfBalanceIsEnough(payment.getBalance(), request.getPrice());
            response.setSuccess(true);

        }
        catch (BusinessException exception) {
            response.setSuccess(false);
            response.setMessage(exception.getMessage());
        }
        payment.setBalance(payment.getBalance() - request.getPrice());
        paymentRepository.save(payment);
        return response;
    }


}



