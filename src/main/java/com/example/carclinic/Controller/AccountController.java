package com.example.carclinic.Controller;

import com.example.carclinic.Entity.Customer;
import com.example.carclinic.Repository.CustomerRepository;
import com.example.carclinic.Response.Response;
import com.example.carclinic.Service.IAccountService;
import com.example.carclinic.constatnts.AccountsConstants;
import com.example.carclinic.dto.CustomerDTO;
import com.example.carclinic.dto.ResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor

@Validated
public class AccountController {

    private IAccountService iAccountService;

    @PostMapping("/creteAccount")
    public ResponseEntity<ResponseDTO> createAccount( @Valid @RequestBody CustomerDTO customerDTO){
        iAccountService.createAccount(customerDTO);
//        System.out.println("Account created");
//        System.out.println("Email "+ customerDTO.getEmail());
//        System.out.println("Name "+ customerDTO.getName());
//        System.out.println("Phone Number "+ customerDTO.getMobileNumber());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(
                        AccountsConstants.requestRefId,
                        AccountsConstants.STATUS_201,
                        AccountsConstants.MESSAGE_200,
                        AccountsConstants.CUSTOMER_MESSAGE,
                        LocalDateTime.now()));
    }

    @GetMapping("/fetchAccountDetails")

    public  ResponseEntity<CustomerDTO> fetchAccountDetails(@RequestParam
                                                                @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                                String mobileNumber){
        CustomerDTO customerDTO = iAccountService.fetchAccounts(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDTO);
    }



    @PutMapping("/updateAccountDetails")
    public ResponseEntity<ResponseDTO> updateAccountDetails( @Valid @RequestBody CustomerDTO customerDTO){
        boolean isUpdated = iAccountService.updateAccount(customerDTO);

        if(isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(
                            AccountsConstants.requestRefId,
                            AccountsConstants.STATUS_201,
                            AccountsConstants.MESSAGE_200,
                            AccountsConstants.CUSTOMER_MESSAGE,
                            LocalDateTime.now()));
        }else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(
                            AccountsConstants.requestRefId,
                            AccountsConstants.STATUS_500,
                            AccountsConstants.MESSAGE_500,
                            AccountsConstants.CUSTOMER_MESSAGE,
                            LocalDateTime.now()));
        }
    }

    @DeleteMapping("/deleteAccount")
    public ResponseEntity<ResponseDTO>deleteAccountDetails(@RequestParam
                                                               @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                               String mobileNumber){
        boolean isDeleted = iAccountService.deleteAccount(mobileNumber);

        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(
                            AccountsConstants.requestRefId,
                            AccountsConstants.STATUS_200,
                            AccountsConstants.STATUS_200,
                            AccountsConstants.CUSTOMER_MESSAGE,
                            LocalDateTime.now()
                    ));
        }else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(
                            AccountsConstants.requestRefId,
                            AccountsConstants.STATUS_500,
                            AccountsConstants.MESSAGE_500,
                            AccountsConstants.CUSTOMER_MESSAGE,
                            LocalDateTime.now()));
        }
    }


    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/getAllCustomers")

    public ResponseEntity<Response> getAllCustomers(@RequestParam(required = false) String mobileNumber) {
        try {
            List<Customer> customers = new ArrayList<>();
            long total_rows = customerRepository.count();
            if (mobileNumber == null) {
                customerRepository.findAll().forEach(customers::add);
            } else {
                //customerRepository.findByMobileNumber(mobileNumber).forEach(customers::add);
                customerRepository.findAll().forEach(customers::add);
            }
            if (customers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return  new ResponseEntity<>(new Response(true,"OK", total_rows, customers),
                    HttpStatus.OK);
            //return new ResponseEntity<>(new Response(true, "OK", total_rows, customers), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
