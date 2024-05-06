package com.HAPPYTRIP.repository;

import com.HAPPYTRIP.domain.Reservation;
import com.HAPPYTRIP.domain.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    List<Reservation> findByMemberId_Id(Long memberId);

    Optional<Reservation> findById(Long id);

    @Query("SELECT r FROM Reservation r" +
            " LEFT JOIN FETCH r.payment p" +
            " LEFT JOIN FETCH r.flight f" +
            " LEFT JOIN FETCH r.member m" +
            " WHERE r.reservationUid = :reservationUid")
    Reservation findReservationAndPaymentAndFlightAndMember(String reservationUid);


    @Query("select r from Reservation r" +
            " left join fetch r.payment p" +
            " where r.reservationUid= :reservationUid")
   Reservation findReservationAndPayment(String reservationUid);

    List<Reservation> findByMember_UserId(String userId);

    @Modifying
    @Query("update Reservation r set r.status = :status where r.payment.id = :paymentId")
    void updateReservationStatusByPaymentId(@Param("paymentId") Long paymentId, @Param("status") ReservationStatus status);

    @Query("select count(r) from Reservation r where r.payment.id = :paymentId")
    int countReservationById(@Param("paymentId") Long paymentId);
}
