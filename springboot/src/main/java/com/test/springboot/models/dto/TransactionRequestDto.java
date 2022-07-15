package com.test.springboot.models.dto;

public class TransactionRequestDto {

    private Long accountOrigin;
    private Long accountDestiny;
    private Double amount;
    private Long bankId;

    public TransactionRequestDto() {
    }

    public TransactionRequestDto(Long accountOrigin, Long accountDestiny, Double amount, Long bankId) {
        this.accountOrigin = accountOrigin;
        this.accountDestiny = accountDestiny;
        this.amount = amount;
        this.bankId = bankId;
    }

    public Long getAccountOrigin() {
        return accountOrigin;
    }

    public void setAccountOrigin(Long accountOrigin) {
        this.accountOrigin = accountOrigin;
    }

    public Long getAccountDestiny() {
        return accountDestiny;
    }

    public void setAccountDestiny(Long accountDestiny) {
        this.accountDestiny = accountDestiny;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }
}
