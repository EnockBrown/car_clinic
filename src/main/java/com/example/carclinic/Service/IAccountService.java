package com.example.carclinic.Service;

import com.example.carclinic.dto.CustomerDTO;

public interface IAccountService {
    void createAccount(CustomerDTO customerDTO);

    CustomerDTO fetchAccounts(String mobileNumber);


    boolean updateAccount(CustomerDTO customerDTO);

    boolean deleteAccount(String mobileNumber);

}
