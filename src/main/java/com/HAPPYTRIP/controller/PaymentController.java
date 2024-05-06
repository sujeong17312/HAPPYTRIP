package com.HAPPYTRIP.controller;

import com.HAPPYTRIP.domain.Reservation;
import com.HAPPYTRIP.dto.PaymentCallback;
import com.HAPPYTRIP.dto.RequestPaymentDTO;
import com.HAPPYTRIP.service.PaymentService;
import com.HAPPYTRIP.service.ReservationService;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final ReservationService reservationService;


    @GetMapping("/payment")
    public String paymentPage(@RequestParam("reservationId") Long reservationId, Model model) {

        System.out.println("---------------payment check----------------------------------------");

        Reservation reservation = reservationService.getReservation(reservationId);
        RequestPaymentDTO requestPaymentDTO = paymentService.findRequest(reservation.getReservationUid());
        System.out.println(requestPaymentDTO.getPaymentId());

        System.out.println("requsetDto"+requestPaymentDTO);
        model.addAttribute("requestPaymentDTO", requestPaymentDTO);

        return "payment";
    }





    @ResponseBody
    @PostMapping("/payment")
    public ResponseEntity<IamportResponse<Payment>> validationPayment(@RequestBody PaymentCallback request) {
        log.info("request= {} ",request);
        IamportResponse<Payment> iamportResponse = paymentService.paymentByCallback(request);

        log.info("결제 응답={}", iamportResponse.getResponse().toString());

        return new ResponseEntity<>(iamportResponse, HttpStatus.OK);
    }

    @GetMapping("/success-payment")
    public String successPaymentPage() {
        return "success-payment";
    }

    @GetMapping("/fail-payment")
    public String failPaymentPage() {
        return "fail-payment";
    }
}