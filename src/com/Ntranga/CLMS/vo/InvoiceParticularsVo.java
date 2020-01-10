package com.Ntranga.CLMS.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


public class InvoiceParticularsVo {

	private Integer invoiceParticularId;
	private Integer invoiceId;
	private String particulars;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy", timezone="IST")
	private Date fromDate;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy", timezone="IST")
	private Date toDate;
	private String rate;
	private String frequency;
	private String amount;
	private Integer quantity;
	private Integer currencyId;
	
	private Integer createdBy;
	private Date createdDate;
	private Integer modifiedby;
	private Date modifiedDate;
	
	private String currencyName;

	public Integer getInvoiceParticularId() {
		return invoiceParticularId;
	}

	public void setInvoiceParticularId(Integer invoiceParticularId) {
		this.invoiceParticularId = invoiceParticularId;
	}

	public Integer getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getParticulars() {
		return particulars;
	}

	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(Integer modifiedby) {
		this.modifiedby = modifiedby;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	@Override
	public String toString() {
		return "InvoiceParticularsVo [invoiceParticularId=" + invoiceParticularId
				+ ", invoiceId=" + invoiceId + ", particulars=" + particulars
				+ ", fromDate=" + fromDate + ", toDate=" + toDate + ", rate="
				+ rate + ", frequency=" + frequency + ", amount=" + amount
				+ ", quantity=" + quantity + ", currencyId=" + currencyId
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedby=" + modifiedby + ", modifiedDate="
				+ modifiedDate + ", currencyName=" + currencyName + "]";
	}
	
	
	
}
