package com.example.carclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
public class ResponseDTO {

    private Long requestRefId;
    private String statusCode;
    private String statusMsg;
    private  String customerMessage;
    private LocalDateTime timestamp;
}
