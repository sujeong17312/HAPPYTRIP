package com.HAPPYTRIP.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Flight_id")
    private Long id;

    private String flightNumber;

    private String departure;

    private String arrival;

    private LocalDateTime departureDate;

    private LocalDateTime arrivalDate;

    private String airlineName;

    private int price;
}
