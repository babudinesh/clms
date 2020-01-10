package com.Ntranga.CLMS.vo;

import java.io.Serializable;

public class WageRatesForOTVo implements Serializable {

	
	private Integer wageRateForOTId;	
	private String workedOn;
    private String regularRates;
    private String otRates;
    
    
    public Integer getWageRateForOTId() {
		return wageRateForOTId;
	}
	public void setWageRateForOTId(Integer wageRateForOTId) {
		this.wageRateForOTId = wageRateForOTId;
	}
	public String getWorkedOn() {
		return workedOn;
	}
	public void setWorkedOn(String workedOn) {
		this.workedOn = workedOn;
	}
	public String getRegularRates() {
		return regularRates;
	}
	public void setRegularRates(String regularRates) {
		this.regularRates = regularRates;
	}
	public String getOtRates() {
		return otRates;
	}
	public void setOtRates(String otRates) {
		this.otRates = otRates;
	}
    
}
