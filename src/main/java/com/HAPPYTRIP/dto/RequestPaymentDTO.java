package com.HAPPYTRIP.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RequestPaymentDTO {

    private Long paymentId;
    private String paymentUid;
    private String reservationUid;
    private int price;
    private String name;
    private String phone;


}
