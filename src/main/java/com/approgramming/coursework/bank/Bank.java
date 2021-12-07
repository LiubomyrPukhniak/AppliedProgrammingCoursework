package com.approgramming.coursework.bank;

public class Bank {
    private Integer bankId;
    private String bankName;
    private String email;
    private String password;

    public Bank(Integer bankId, String bankName, String email, String password) {
        this.bankId = bankId;
        this.bankName = bankName;
        this.email = email;
        this.password = password;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
