package com.HAPPYTRIP.service;

import com.HAPPYTRIP.domain.*;
import com.HAPPYTRIP.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    //조회
    public List<Reservation> getList() {
        return reservationRepository.findAll();
    }


    public List<Reservation> getReservationsByMemberId(Long memberId) {
        return reservationRepository.findByMemberId_Id(memberId);
    }

    public Reservation getReservation(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent()) {
            return reservation.get();
        } else {
            throw new RuntimeException("Reservation not found");
        }
    }

    //저장
    public Reservation saveReservation(Member member, Flight flight, ReservationStatus status, int seatCount, int price,Payment payment){
        Reservation reservation=new Reservation();
        reservation.setReservationUid(UUID.randomUUID().toString());
        reservation.setMember(member);
        reservation.setFlight(flight);
        reservation.setSeatCount(seatCount);
        reservation.setStatus(status);
        reservation.setPrice(price);
        reservation.setPayment(payment);

        reservationRepository.save(reservation);

        return reservation;
    }

    //예약 상태 CANCEL로 변경
    public void cancelReservation(Long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isPresent()) {
            Reservation r = optionalReservation.get();
            if (!r.getStatus().equals(ReservationStatus.CANCEL)) {
                r.setStatus(ReservationStatus.CANCEL);
                reservationRepository.save(r);
            } else {
                throw new IllegalStateException("이미 취소된 예약입니다.");
            }
        } else {
            throw new EntityNotFoundException("예약을 찾을 수 없습니다.");
        }
    }
}