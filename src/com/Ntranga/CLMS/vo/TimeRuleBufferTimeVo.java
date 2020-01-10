package com.Ntranga.CLMS.vo;
// Generated 25 Jul, 2016 12:05:41 PM by Hibernate Tools 3.6.0

public class TimeRuleBufferTimeVo  implements java.io.Serializable {


     
     private String timeEvent;
     private int bufferTime;
     private int exemptedOccurrence;
     private int greaterExemptedOccurrence;
     private String fromHours;
     private String toHours;
     private String penality;
    

    public TimeRuleBufferTimeVo() {
    }



	public String getTimeEvent() {
		return timeEvent;
	}


	public void setTimeEvent(String timeEvent) {
		this.timeEvent = timeEvent;
	}


	public int getBufferTime() {
		return bufferTime;
	}


	public void setBufferTime(int bufferTime) {
		this.bufferTime = bufferTime;
	}



	public int getExemptedOccurrence() {
		return exemptedOccurrence;
	}



	public void setExemptedOccurrence(int exemptedOccurrence) {
		this.exemptedOccurrence = exemptedOccurrence;
	}



	public int getGreaterExemptedOccurrence() {
		return greaterExemptedOccurrence;
	}



	public void setGreaterExemptedOccurrence(int greaterExemptedOccurrence) {
		this.greaterExemptedOccurrence = greaterExemptedOccurrence;
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



	public String getPenality() {
		return penality;
	}



	public void setPenality(String penality) {
		this.penality = penality;
	}



	@Override
	public String toString() {
		return "TimeRuleBufferTimeVo [timeEvent=" + timeEvent + ", bufferTime="
				+ bufferTime + ", exemptedOccurrence=" + exemptedOccurrence
				+ ", greaterExemptedOccurrence=" + greaterExemptedOccurrence
				+ ", fromHours=" + fromHours + ", toHours=" + toHours
				+ ", penality=" + penality + "]";
	}

  



}


