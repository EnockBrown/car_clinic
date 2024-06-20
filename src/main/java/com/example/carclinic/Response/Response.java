package com.example.carclinic.Response;

import com.example.carclinic.Entity.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Response {

    @JsonProperty("Success")
    private boolean success;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("Count")
    private long total;

    @JsonProperty("_meta data")
    private List<Customer> list;

    public Response(boolean success, String message, long total, List<Customer> list) {
        this.success = success;
        this.message = message;
        this.total = total;
        this.list = list;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Customer> getList() {
        return list;
    }

    public void setList(List<Customer> list) {
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
