package com.approgramming.coursework.attachment;

import com.approgramming.coursework.offer.Offer;

public class Attachment {
    private Integer attachmentId;
    private Offer offer;
    private Double money;
    private Double firstMoneyAmount;
    private Boolean outOfSchedule;
    private String dateOfEnd;
    private String dateOfStart;

    public Attachment(Integer attachmentId, Offer offer, Double money,
                      Double firstMoneyAmount, Boolean outOfSchedule,
                      String dateOfEnd, String dateOfStart) {
        this.attachmentId = attachmentId;
        this.offer = offer;
        this.money = money;
        this.firstMoneyAmount = firstMoneyAmount;
        this.outOfSchedule = outOfSchedule;
        this.dateOfEnd = dateOfEnd;
        this.dateOfStart = dateOfStart;
    }

    public Attachment() {}

    public Integer getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getFirstMoneyAmount() {
        return firstMoneyAmount;
    }

    public void setFirstMoneyAmount(Double firstMoneyAmount) {
        this.firstMoneyAmount = firstMoneyAmount;
    }

    public Boolean getOutOfSchedule() {
        return outOfSchedule;
    }

    public void setOutOfSchedule(Boolean outOfSchedule) {
        this.outOfSchedule = outOfSchedule;
    }

    public String getDateOfEnd() {
        return dateOfEnd;
    }

    public void setDateOfEnd(String dateOfEnd) {
        this.dateOfEnd = dateOfEnd;
    }

    public String getDateOfStart() {
        return dateOfStart;
    }

    public void setDateOfStart(String dateOfStart) {
        this.dateOfStart = dateOfStart;
    }

    public Double getPercents() {
        return offer.getPercents();
    }

    public void setPercents(Double percents) {
        this.offer.setPercents(percents);
    }

    public Integer getMonthCount() {
        return offer.getMonthCount();
    }

    public void setMonthCount(Integer monthCount) {
        this.offer.setMonthCount(monthCount);
    }

    public String getBankName() {
        return offer.getBankName();
    }

    public void setBankName(String bankName) {
        this.offer.setBankName(bankName);
    }
}
