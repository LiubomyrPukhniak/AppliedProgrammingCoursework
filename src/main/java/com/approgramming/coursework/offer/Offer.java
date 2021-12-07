package com.approgramming.coursework.offer;

import com.approgramming.coursework.bank.Bank;

public class Offer {
    private Integer monthCount;
    private Double percents;
    private Integer offerId;
    private String bankName;
    private Integer bankId;

    public Offer(Integer monthCount, Double percents, Integer offerId, String bankName, Integer bankId) {
        this.monthCount = monthCount;
        this.percents = percents;
        this.offerId = offerId;
        this.bankName = bankName;
        this.bankId = bankId;
    }

    public Offer() {}

    public Integer getMonthCount() {
        return monthCount;
    }

    public void setMonthCount(Integer monthCount) {
        this.monthCount = monthCount;
    }

    public Double getPercents() {
        return percents;
    }

    public void setPercents(Double percents) {
        this.percents = percents;
    }

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    @Override
    public String toString() {
        return "ID : " + offerId + ", Percents : " + percents +
                ", Month count : " + monthCount + ", Bank Name : " + bankName;
    }
}
