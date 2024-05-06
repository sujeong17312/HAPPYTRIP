package com.HAPPYTRIP.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AirForm {

    private String roundWay;

    private String oneWay;

    @NotBlank
    private String departure;

    @NotBlank
    private String arrival;

    @NotBlank
    private String departureDate1;

    private String departureDate2;

    @NotNull
    private int passenger;


}
