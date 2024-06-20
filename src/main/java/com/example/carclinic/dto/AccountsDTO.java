package com.example.carclinic.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDTO {

    @NotEmpty(message = "Account Number cannot be null")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private Long accountNumber;

    @NotEmpty(message = "Account Type cannot be Empty | nNull")
    private String accountType;

    @NotEmpty(message = "Branch address cannot be Empty | Null")
    private String branchAddress;
}
