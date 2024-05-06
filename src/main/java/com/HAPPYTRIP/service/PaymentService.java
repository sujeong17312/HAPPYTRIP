package com.HAPPYTRIP.service;

import com.HAPPYTRIP.domain.PaymentStatus;
import com.HAPPYTRIP.domain.Reservation;
import com.HAPPYTRIP.domain.ReservationStatus;
import com.HAPPYTRIP.dto.PaymentCallback;
import com.HAPPYTRIP.dto.RequestPaymentDTO;
import com.HAPPYTRIP.repository.PaymentRepository;
import com.HAPPYTRIP.repository.ReservationRepository;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;
    private final IamportClient iamportClient;

    public com.HAPPYTRIP.domain.Payment savePayment(int price,  PaymentStatus status){

        com.HAPPYTRIP.domain.Payment payment = new com.HAPPYTRIP.domain.Payment();
        payment.setPrice(price);
        payment.setStatus(status);
        paymentRepository.save(payment);

        return payment;
    }



    public RequestPaymentDTO findRequest(String reservationUid) {
        Reservation reservation = reservationRepository.findReservationAndPaymentAndFlightAndMember(reservationUid);
        int count = reservationRepository.countReservationById(reservation.getPayment().getId());

        RequestPaymentDTO requestPaymentDTO = new RequestPaymentDTO();
        requestPaymentDTO.setPaymentId(reservation.getPayment().getId());
        requestPaymentDTO.setReservationUid(reservation.getReservationUid());
        requestPaymentDTO.setPhone(reservation.getMember().getPhone());
        requestPaymentDTO.setName(reservation.getMember().getName());
        if(count == 1) {
            requestPaymentDTO.setPrice(reservation.getFlight().getPrice() * reservation.getSeatCount());
        }else{
            requestPaymentDTO.setPrice(reservation.getFlight().getPrice() * reservation.getSeatCount()*2);
        }
        return requestPaymentDTO;
    }


    public IamportResponse<Payment> paymentByCallback(PaymentCallback request) {

        System.out.println("확인확인" + request.getPaymentUid() + "  " + request.getReservationUid() + " " + request.getPaymentId());

        try {
            IamportResponse<Payment> iamportResponse = iamportClient.paymentByImpUid(request.getPaymentUid());
            Reservation reservation = reservationRepository.findReservationAndPayment(request.getReservationUid());
            int count=reservationRepository.countReservationById(reservation.getPayment().getId());
            int price=0;
            System.out.println("확인확인1" + reservation);



            // 결제 완료가 아니면
            if(!iamportResponse.getResponse().getStatus().equals("paid")) {
                // 주문, 결제 삭제
                reservationRepository.delete(reservation);
                paymentRepository.delete(reservation.getPayment());

                throw new RuntimeException("결제 미완료");
            }

            // DB에 저장된 결제 금액
            if(count == 1) {
                price = reservation.getPrice() * reservation.getSeatCount();
            }else{
                price = reservation.getPrice() * reservation.getSeatCount() * 2;
            }
            // 실 결제 금액
            int iamportPrice = iamportResponse.getResponse().getAmount().intValue();

            // 결제 금액 검증
            if(iamportPrice != price) {
                reservationRepository.delete(reservation);
                paymentRepository.delete(reservation.getPayment());

                iamportClient.cancelPaymentByImpUid(new CancelData(iamportResponse.getResponse().getImpUid(), true, new BigDecimal(iamportPrice)));

                throw new RuntimeException("결제금액 위변조 의심");
            }


            // 결제 상태 변경
            reservation.getPayment().changePaymentBySuccess(iamportResponse.getResponse().getImpUid(), PaymentStatus.COMPLETION);
            reservationRepository.updateReservationStatusByPaymentId(request.getPaymentId(), ReservationStatus.COMPLETION);

            return iamportResponse;

        } catch (IamportResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //예약 상태 CANCEL로 변경
    public void cancelPayment(Long id) {
        Optional<com.HAPPYTRIP.domain.Payment> optionalPayment = paymentRepository.findById(id);
        if (optionalPayment.isPresent()) {
            com.HAPPYTRIP.domain.Payment p = optionalPayment.get();
            if (!p.getStatus().equals(PaymentStatus.CANCEL)) {
                p.setStatus(PaymentStatus.CANCEL);
                paymentRepository.save(p);
            } else {
                throw new IllegalStateException("이미 취소된 예약입니다.");
            }
        } else {
            throw new EntityNotFoundException("예약을 찾을 수 없습니다.");
        }
    }
}