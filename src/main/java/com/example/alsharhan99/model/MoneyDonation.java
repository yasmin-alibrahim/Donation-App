package com.example.alsharhan99.model;

public class MoneyDonation {
    private String amount;
    private String nameOnCard;
    private String cardNumber;
    private String cvv;
    private String expiryDate;

    public MoneyDonation() {
    }

    public MoneyDonation(String amount, String nameOnCard, String cardNumber, String cvv, String expiryDate) {
        this.amount = amount;
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
