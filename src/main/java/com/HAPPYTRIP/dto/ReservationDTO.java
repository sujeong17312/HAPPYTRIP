package com.HAPPYTRIP.dto;

import lombok.Data;

@Data
public class ReservationDTO {

    private String airline;
    private String departure;
    private String arrival;
    private String departureTime;
    private String arrivalTime;
    private String duration;
    private String price;
    private String flightNumber;
}
