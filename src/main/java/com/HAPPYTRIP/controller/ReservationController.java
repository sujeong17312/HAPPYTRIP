
package com.HAPPYTRIP.controller;

import com.HAPPYTRIP.api.Airline;
import com.HAPPYTRIP.api.AirlineApi;
import com.HAPPYTRIP.api.AirlineDto;
import com.HAPPYTRIP.domain.*;
import com.HAPPYTRIP.dto.AirForm;
import com.HAPPYTRIP.dto.ReservationDTO;
import com.HAPPYTRIP.dto.SelectedFlightsDTO;
import com.HAPPYTRIP.service.FlightService;
import com.HAPPYTRIP.service.MemberService;
import com.HAPPYTRIP.service.PaymentService;
import com.HAPPYTRIP.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    private final AirlineApi airlineApi;
    private final FlightService flightService;
    private final ReservationService reservationService;
    private final MemberService memberService;
    private final PaymentService paymentService;


    @PostMapping("/booking")
    public String searchFlights(@Valid AirForm airForm, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()){
            System.out.println("검증 오류 발생:");

            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
            return "home";
        }

        //왕복
        try{

            HashMap<String,String> airMap = new HashMap<>();
            airMap.put("제주", "NAARKPC");
            airMap.put("김해", "NAARKPK");
            airMap.put("김포", "NAARKSS");
            String tripType = airForm.getRoundWay();

            log.info("------------------------");
            log.info(airForm.getArrival() + airForm.getDeparture());
            log.info("airFormPassenger {}", airForm.getPassenger());


            if (tripType != null && tripType.equals("왕복")) {
                String roundWayResponseJson1 = airlineApi.getAirline(airMap.get(airForm.getDeparture()), airMap.get(airForm.getArrival()), airForm.getDepartureDate1(), "KAL");

                String roundWayResponseJson2 = airlineApi.getAirline(airMap.get(airForm.getArrival()), airMap.get(airForm.getDeparture()), airForm.getDepartureDate2(), "KAL");

                ObjectMapper objectMapper1 = new ObjectMapper();

                AirlineDto airlineResponse1 = objectMapper1.readValue(roundWayResponseJson1, AirlineDto.class);
                AirlineDto.Body.Items items1 = airlineResponse1.getResponse().getBody().getItems();

                if(items1 !=null && items1.getItem() !=null){
                    List<Airline> itemList1 = items1.getItem();

                    for (Airline item : itemList1) {

                        String departureTime = item.getDepPlandTime();

                        String arrivalTime = item.getArrPlandTime();

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
                        LocalDateTime localDateTime1 = LocalDateTime.parse(departureTime,formatter);
                        LocalDateTime localDateTime2 = LocalDateTime.parse(arrivalTime,formatter);

                        System.out.println("LocalDateTime: " +localDateTime1);
                        System.out.println("LocalDateTime: " +localDateTime2);
                        System.out.println("Airline Name: " + item.getAirlineNm());
                        System.out.println("Arrival Airport Name: " + item.getArrAirportNm());
                        System.out.println("Arrival Planned Time: " + item.getArrPlandTime());
                        System.out.println("Departure Airport Name: " + item.getDepAirportNm());
                        System.out.println("Departure Planned Time: " + item.getDepPlandTime());
                        System.out.println("Economy Charge: " + item.getEconomyCharge());
                        System.out.println("Prestige Charge: " + item.getPrestigeCharge());
                        System.out.println("Vehicle ID: " + item.getVihicleId());
                        System.out.println();



                        SimpleDateFormat format = new SimpleDateFormat("HHmm");
                        Date dpaTime = format.parse(departureTime.substring(8,12));
                        Date arTime = format.parse(arrivalTime.substring(8,12));
                        long diff = arTime.getTime() - dpaTime.getTime();
                        if (diff < 0) {
                            diff += 24 * 60 * 60 * 1000; // 24시간을 더해줌
                        }
                        long diffMinutes = diff / (60 * 1000);
                        long hours = diffMinutes / 60;
                        long minutes = diffMinutes % 60;
                        String durationTime1 = hours + ":" + minutes;


                        item.setDeplocalDateTime(localDateTime1);
                        item.setArrlocalDateTime(localDateTime2);
                        item.setDuration(durationTime1);
                        item.setPassenger(airForm.getPassenger());


                    }
                    model.addAttribute("itemList1",itemList1);
                }

                ObjectMapper objectMapper2 = new ObjectMapper();

                AirlineDto airlineResponse2 = objectMapper2.readValue(roundWayResponseJson2,AirlineDto.class);
                AirlineDto.Body.Items items2 = airlineResponse2.getResponse().getBody().getItems();
                if(items2 != null && items2.getItem() != null){
                    List<Airline> itemList2 = items2.getItem();

                    for (Airline item : itemList2) {

                        String departureTime = item.getDepPlandTime();
                        String arrivalTime = item.getArrPlandTime();


                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
                        LocalDateTime localDateTime1 = LocalDateTime.parse(departureTime,formatter);
                        LocalDateTime localDateTime2 = LocalDateTime.parse(arrivalTime,formatter);

                        System.out.println("LocalDateTime: "+localDateTime1);
                        System.out.println("LocalDateTime: "+localDateTime2);
                        System.out.println("Airline Name: " + item.getAirlineNm());
                        System.out.println("Arrival Airport Name: " + item.getArrAirportNm());
                        System.out.println("Arrival Planned Time: " + item.getArrPlandTime());
                        System.out.println("Departure Airport Name: " + item.getDepAirportNm());
                        System.out.println("Departure Planned Time: " + item.getDepPlandTime());
                        System.out.println("Economy Charge: " + item.getEconomyCharge());
                        System.out.println("Prestige Charge: " + item.getPrestigeCharge());
                        System.out.println("Vehicle ID: " + item.getVihicleId());
                        System.out.println();


                        SimpleDateFormat format = new SimpleDateFormat("HHmm");
                        Date dpaTime = format.parse(departureTime.substring(8,12));
                        Date arTime = format.parse(arrivalTime.substring(8,12));
                        long diff = arTime.getTime() - dpaTime.getTime();
                        if (diff < 0) {
                            diff += 24 * 60 * 60 * 1000; // 24시간을 더해줌
                        }
                        long diffMinutes = diff / (60 * 1000);
                        long hours = diffMinutes / 60;
                        long minutes = diffMinutes % 60;
                        String durationTime2 = hours + ":" + minutes;

                        item.setDeplocalDateTime(localDateTime1);
                        item.setArrlocalDateTime(localDateTime2);
                        item.setDuration(durationTime2);
                    }

                    model.addAttribute("itemList2",itemList2);
                }
                model.addAttribute("tripType",tripType);


                //편도
            } else {
                String oneWayResponseJson = airlineApi.getAirline(airMap.get(airForm.getDeparture()), airMap.get(airForm.getArrival()), airForm.getDepartureDate1(), "KAL");

                ObjectMapper objectMapper = new ObjectMapper();
                AirlineDto airlineResponse = objectMapper.readValue(oneWayResponseJson, AirlineDto.class);

                AirlineDto.Body.Items items = airlineResponse.getResponse().getBody().getItems();
                if (items != null && items.getItem() != null) {
                    List<Airline> itemList1 = items.getItem();


                    for (Airline item : itemList1) {

                        String departureTime = item.getDepPlandTime();
                        String arrivalTime = item.getArrPlandTime();

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
                        LocalDateTime localDateTime1 = LocalDateTime.parse(departureTime,formatter);
                        LocalDateTime localDateTime2 = LocalDateTime.parse(arrivalTime,formatter);

                        System.out.println("LocalDateTime: "+localDateTime1);
                        System.out.println("LocalDateTime: "+localDateTime2);
                        System.out.println("Airline Name: " + item.getAirlineNm());
                        System.out.println("Arrival Airport Name: " + item.getArrAirportNm());
                        System.out.println("Arrival Planned Time: " + item.getArrPlandTime());
                        System.out.println("Departure Airport Name: " + item.getDepAirportNm());
                        System.out.println("Departure Planned Time: " + item.getDepPlandTime());
                        System.out.println("Economy Charge: " + item.getEconomyCharge());
                        System.out.println("Prestige Charge: " + item.getPrestigeCharge());
                        System.out.println("Vehicle ID: " + item.getVihicleId());
                        System.out.println();


                        SimpleDateFormat format = new SimpleDateFormat("HHmm");
                        Date dpaTime = format.parse(departureTime.substring(8,12));
                        Date arTime = format.parse(arrivalTime.substring(8,12));
                        long diff = arTime.getTime() - dpaTime.getTime();
                        if (diff < 0) {
                            diff += 24 * 60 * 60 * 1000;
                        }
                        long diffMinutes = diff / (60 * 1000);
                        long hours = diffMinutes / 60;
                        long minutes = diffMinutes % 60;
                        String durationTime1 = hours + ":" + minutes;

                        item.setDeplocalDateTime(localDateTime1);
                        item.setArrlocalDateTime(localDateTime2);
                        item.setDuration(durationTime1);
                        item.setPassenger(airForm.getPassenger());
                    }
                    model.addAttribute("itemList1", itemList1);
                } else {
                    System.out.println("No items found in the list.");
                }
            }

        } catch (Exception e){
            System.out.println("2222222222222222");
            e.printStackTrace();
            return "home";
        }

        return "booking";
    }



    @PreAuthorize("isAuthenticated()")
    @PostMapping("/booking/submitFlights")
    public String submitFlights(@RequestBody String jsonString, Model model , HttpSession session) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        SelectedFlightsDTO selectedFlightsDTO = objectMapper.readValue(jsonString, SelectedFlightsDTO.class);
        List<ReservationDTO> selectedFlights1 = selectedFlightsDTO.getSelectedFlights1();
        List<ReservationDTO> selectedFlights2 = selectedFlightsDTO.getSelectedFlights2();
        int passenger=selectedFlightsDTO.getPassenger();

        log.info("-----------------");

        session.setAttribute("selectedFlights1",selectedFlights1);
        session.setAttribute("selectedFlights2",selectedFlights2);
        session.setAttribute("passenger",passenger);
        System.out.println("Session attribute 'selectedFlights1': " + selectedFlights1);
        System.out.println("Session attribute 'selectedFlights2': " + selectedFlights2);
        System.out.println("passenger : "+ passenger);


        return "redirect:/booking/pay";

    }


    @PreAuthorize("isAuthenticated()")
    //선택된 항공권
    @GetMapping("/booking/pay")
    public String showPaymentPage(Model model, HttpSession session) {
        List<ReservationDTO> selectedFlights1 = (List<ReservationDTO>) session.getAttribute("selectedFlights1");
        List<ReservationDTO> selectedFlights2 = (List<ReservationDTO>) session.getAttribute("selectedFlights2");
        int passenger= (int) session.getAttribute("passenger");

        model.addAttribute("selectedFlights1", selectedFlights1);
        model.addAttribute("selectedFlights2", selectedFlights2);
        model.addAttribute("passenger",passenger);

        return "pay";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/booking/pay")
    public String payment(Model model, HttpSession session,Principal principal){
        List<ReservationDTO> selectedFlights1 = (List<ReservationDTO>) session.getAttribute("selectedFlights1");
        List<ReservationDTO> selectedFlights2 = (List<ReservationDTO>)session.getAttribute("selectedFlights2");
        int passenger=(int)session.getAttribute("passenger");

        model.addAttribute("selectedFlights1",selectedFlights1);
        model.addAttribute("selectedFlights2",selectedFlights2);

        System.out.println(passenger);
        log.info("selectedFlights1 = {}",selectedFlights1);
        log.info("selectedFlights2 = {}",selectedFlights2);
        log.info("passenger={} ",passenger);


        Flight flight1 = new Flight();


        String username = principal.getName();
        Optional<Member> optionalMember = memberService.findByUserId(username);
        Member member = optionalMember.get();

        for (ReservationDTO reservationDTO : selectedFlights1) {

            flight1.setFlightNumber(reservationDTO.getFlightNumber());
            flight1.setDeparture(reservationDTO.getDeparture());
            flight1.setArrival(reservationDTO.getArrival());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
            flight1.setDepartureDate(LocalDateTime.parse(reservationDTO.getDepartureTime(),formatter));
            flight1.setArrivalDate(LocalDateTime.parse(reservationDTO.getArrivalTime(),formatter));
            flight1.setAirlineName(reservationDTO.getAirline());
            flight1.setPrice(Integer.parseInt(reservationDTO.getPrice()));
        }
        flightService.saveFlight(flight1);
        Payment payment1 = paymentService.savePayment(flight1.getPrice(),PaymentStatus.READY);
        Reservation reservation1 = reservationService.saveReservation(member,flight1, ReservationStatus.READY,passenger, flight1.getPrice(),payment1);
        Long reservationId1=reservation1.getId();


        if(!selectedFlights2.isEmpty()){
            Flight flight2 = new Flight();

            for (ReservationDTO reservationDTO : selectedFlights2) {
                flight2.setFlightNumber(reservationDTO.getFlightNumber());
                flight2.setDeparture(reservationDTO.getDeparture());
                flight2.setArrival(reservationDTO.getArrival());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
                flight2.setDepartureDate(LocalDateTime.parse(reservationDTO.getDepartureTime(), formatter));
                flight2.setArrivalDate(LocalDateTime.parse(reservationDTO.getArrivalTime(), formatter));
                flight2.setAirlineName(reservationDTO.getAirline());
                flight2.setPrice(Integer.parseInt(reservationDTO.getPrice()));
            }
            flightService.saveFlight(flight2);

            reservationService.saveReservation(member, flight2, ReservationStatus.READY,passenger, flight1.getPrice(),payment1);

        }
        model.addAttribute("reservationId", reservationId1);
        session.setAttribute("reservationId", reservationId1);

        return "redirect:/payment?reservationId=" + reservationId1;

    }




}
