package com.example.carclinic.Service.Impl;

import com.example.carclinic.Entity.Accounts;
import com.example.carclinic.Entity.Customer;
import com.example.carclinic.Mapper.AccountsMapper;
import com.example.carclinic.Mapper.CustomerMapper;
import com.example.carclinic.Repository.AccountsRepository;
import com.example.carclinic.Repository.CustomerRepository;
import com.example.carclinic.Service.IAccountService;
import com.example.carclinic.constatnts.AccountsConstants;
import com.example.carclinic.dto.AccountsDTO;
import com.example.carclinic.dto.CustomerDTO;
import com.example.carclinic.exception.CustomerAlreadyExistsException;
import com.example.carclinic.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private CustomerRepository customerRepository;
    private AccountsRepository accountsRepository;


    @Override
    public void createAccount(CustomerDTO customerDTO) {

        Customer customer= CustomerMapper.mapToCustomer(customerDTO, new Customer());

        //customerRepository.save(customer);

        //checking if their is an already existing customer
        Optional<Customer> optionalCustomer =customerRepository.findByMobileNumber(customerDTO.getMobileNumber());
        if(optionalCustomer.isPresent()){        customer.setCreatedBy("Admin");
        customer.setCreatedAt(LocalDateTime.now());
            throw new CustomerAlreadyExistsException("Customer already exists with given mobile number: "+customerDTO.getMobileNumber());
        }



//        customer.setCreatedBy("Admin");
//        customer.setCreatedAt(LocalDateTime.now());
        Customer savedCustomer= customerRepository.save(customer);

        accountsRepository.save(createNewAccount(savedCustomer));

    }


    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount= new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        Long randomAccountNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccountNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
//        newAccount.setCreatedBy("Admin");
//        newAccount.setCreatedAt(LocalDateTime.now());

        CustomerDTO customerDTO = CustomerMapper.mapToCustomerDTO(customer, new CustomerDTO());
        customerDTO.setAccountDetails(AccountsMapper.mapToAccountsDTO(newAccount, new AccountsDTO()));


        return newAccount;
    }

    @Override
    public CustomerDTO fetchAccounts(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer ", "mobile number ", mobileNumber)
        );

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()-> new ResourceNotFoundException("Account ", " customer id ",customer.getCustomerId().toString())
        );

        CustomerDTO customerDTO = CustomerMapper.mapToCustomerDTO(customer,new CustomerDTO());
        customerDTO.setAccountDetails(AccountsMapper.mapToAccountsDTO(accounts, new AccountsDTO()));
        return customerDTO;
    }



    @Override
    public boolean updateAccount(CustomerDTO customerDTO) {
        boolean isUpdated = false;
        AccountsDTO accountsDTO = customerDTO.getAccountDetails();
        if(accountsDTO !=null){
            Accounts accounts = accountsRepository.findById(accountsDTO.getAccountNumber()).orElseThrow(
                    ()-> new ResourceNotFoundException("Account ", "AccountNUmber ",accountsDTO.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDTO,accounts);
            accounts=accountsRepository.save(accounts);

            //update customer as well
            Long customerId=accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    ()-> new ResourceNotFoundException("Customer ", "customer id ",customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDTO,customer);
            customerRepository.save(customer);

            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {

        Customer customer=customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer ", "mobile number ",mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }


}
