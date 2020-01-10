package com.Ntranga.CLMS.vo;
// Generated 25 Jul, 2016 12:05:41 PM by Hibernate Tools 3.6.0

public class TimeRuleWorkDayStatusVo  implements java.io.Serializable {


     private Integer dayStatusId;     
     private String fromHours;
     private String toHours;
     private String statusDescription;
     private String statusCode;
    

    public TimeRuleWorkDayStatusVo() {
    }


	public Integer getDayStatusId() {
		return dayStatusId;
	}


	public void setDayStatusId(Integer dayStatusId) {
		this.dayStatusId = dayStatusId;
	}


	public String getFromHours() {
		return fromHours;
	}


	public void setFromHours(String fromHours) {
		this.fromHours = fromHours;
	}


	public String getToHours() {
		return toHours;
	}


	public void setToHours(String toHours) {
		this.toHours = toHours;
	}


	public String getStatusDescription() {
		return statusDescription;
	}


	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}


	public String getStatusCode() {
		return statusCode;
	}


	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

    



}


