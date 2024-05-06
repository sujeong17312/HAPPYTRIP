package com.HAPPYTRIP.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Payment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="payment_id")
    Long id;


    private int price;
    private String paymentUid;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;


    public void changePaymentBySuccess(String paymentUid, PaymentStatus status) {
        this.paymentUid = paymentUid;
        this.status = status;
    }
}
