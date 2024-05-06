package com.HAPPYTRIP.service;

import com.HAPPYTRIP.domain.Flight;
import com.HAPPYTRIP.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;

    //저장
    public Flight saveFlight(Flight flight){
        return flightRepository.save(flight);
    }
}