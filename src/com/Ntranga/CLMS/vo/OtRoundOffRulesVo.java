package com.Ntranga.CLMS.vo;

import java.io.Serializable;

public class OtRoundOffRulesVo implements Serializable {

	
	public Integer getOtRoundOffRulesId() {
		return otRoundOffRulesId;
	}
	public void setOtRoundOffRulesId(Integer otRoundOffRulesId) {
		this.otRoundOffRulesId = otRoundOffRulesId;
	}
	public String getFromMinutes() {
		return fromMinutes;
	}
	public void setFromMinutes(String fromMinutes) {
		this.fromMinutes = fromMinutes;
	}
	public String getToMinutes() {
		return toMinutes;
	}
	public void setToMinutes(String toMinutes) {
		this.toMinutes = toMinutes;
	}
	public String getRoundOffToMinutes() {
		return roundOffToMinutes;
	}
	public void setRoundOffToMinutes(String roundOffToMinutes) {
		this.roundOffToMinutes = roundOffToMinutes;
	}
	private Integer otRoundOffRulesId;
	private String fromMinutes;
    private String toMinutes;
    private String roundOffToMinutes;

    
    
    
}
