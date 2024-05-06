package com.HAPPYTRIP.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightInfo {
    private String airlineNm;
    private String arrAirportNm;
    private String arrPlandTime;
    private String depAirportNm;
    private String depPlandTime;
    private int economyCharge;
    private int prestigeCharge;
    private String vihicleId;
}
