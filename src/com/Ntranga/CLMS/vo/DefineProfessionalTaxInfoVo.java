package com.Ntranga.CLMS.vo;

public class DefineProfessionalTaxInfoVo {

	private String slabFrom;
	private String slabTo;
	private String amount;
	private String ptAmount;
	private Integer professionalTaxInfoId;
	private Integer professionalTaxId;
	private Integer taxSequenceId;
	private Integer taxUniqueId;
	private String ptMonth;
	
	
	public String getSlabFrom() {
		return slabFrom;
	}

	public void setSlabFrom(String slabFrom) {
		this.slabFrom = slabFrom;
	}

	public String getSlabTo() {
		return slabTo;
	}

	public void setSlabTo(String slabTo) {
		this.slabTo = slabTo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPtAmount() {
		return ptAmount;
	}

	public void setPtAmount(String ptAmount) {
		this.ptAmount = ptAmount;
	}
	
	public Integer getProfessionalTaxInfoId() {
		return professionalTaxInfoId;
	}

	public void setProfessionalTaxInfoId(Integer professionalTaxInfoId) {
		this.professionalTaxInfoId = professionalTaxInfoId;
	}

	public Integer getProfessionalTaxId() {
		return professionalTaxId;
	}

	public void setProfessionalTaxId(Integer professionalTaxId) {
		this.professionalTaxId = professionalTaxId;
	}

	public Integer getTaxSequenceId() {
		return taxSequenceId;
	}

	public void setTaxSequenceId(Integer taxSequenceId) {
		this.taxSequenceId = taxSequenceId;
	}

	public Integer getTaxUniqueId() {
		return taxUniqueId;
	}

	public void setTaxUniqueId(Integer taxUniqueId) {
		this.taxUniqueId = taxUniqueId;
	}

	public String getPtMonth() {
		return ptMonth;
	}

	public void setPtMonth(String pTMonthBasedOnFrequency) {
		ptMonth = pTMonthBasedOnFrequency;
	}

	@Override
	public String toString() {
		return "DefineProfessionalTaxInfoVo [slabFrom=" + slabFrom
				+ ", slabTo=" + slabTo + ", amount=" + amount + ", ptAmount="
				+ ptAmount + ", professionalTaxInfoId=" + professionalTaxInfoId
				+ ", professionalTaxId=" + professionalTaxId
				+ ", taxSequenceId=" + taxSequenceId + ", taxUniqueId="
				+ taxUniqueId + ", ptMonth=" + ptMonth + "]";
	}
	
	
}
