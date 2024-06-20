package com.example.carclinic.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

@Data
public class CustomerDTO {

    @NotEmpty(message = "Email address cannot be null or empty")
    @Email(message = "Invalid Email address")
    private String email;

    @NotEmpty(message = "Name should not be null or empty")
    @Size(min = 5, max = 50, message = "THe length of name should be between 5 and 30")
    private String name;

    @NotEmpty(message = "Mobile number cannot be Empty |NUll")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    // to return aggregate information of customer with account details
    private AccountsDTO accountDetails;
}
