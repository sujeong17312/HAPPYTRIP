package com.HAPPYTRIP.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirlineDto {
    private Response response;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Header header;
        private Body body;




    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Body {
        private Items items;
        private int numOfRows;
        private int pageNo;
        private int totalCount;



        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Items {
            private List<Airline> item;



        }
    }



}