package com.approgramming.coursework.customer;

public class Customer {
    private String name;
    private String surname;
    private Integer customerId;
    private String email;
    private String password;
    private Double moneyCount;

    public Customer(String name, String surname, Integer customerId, String email, String password, Double moneyCount) {
        this.name = name;
        this.surname = surname;
        this.customerId = customerId;
        this.email = email;
        this.password = password;
        this.moneyCount = moneyCount;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Double getMoneyCount() {
        return moneyCount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMoneyCount(Double moneyCount) {
        this.moneyCount = moneyCount;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", customerId=" + customerId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", moneyCount=" + moneyCount +
                '}';
    }
}
