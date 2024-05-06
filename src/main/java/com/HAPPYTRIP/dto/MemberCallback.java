package com.HAPPYTRIP.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.class)
public class MemberCallback {

    private String name;
    private String email;
    private String address;
}
