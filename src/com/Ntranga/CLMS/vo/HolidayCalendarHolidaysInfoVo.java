package com.Ntranga.CLMS.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class HolidayCalendarHolidaysInfoVo {

	private int customerId;
	private int companyId;
	private int holidayCalendarDetailsId;
	private int holidayId;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy", timezone="IST")
	private Date fromDate;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy", timezone="IST")
	private Date toDate;
	private String holidayName;
	private int holidayTypeId;
	private String holidayTypeName;
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getHolidayCalendarDetailsId() {
		return holidayCalendarDetailsId;
	}
	public void setHolidayCalendarDetailsId(int holidayCalendarDetailsId) {
		this.holidayCalendarDetailsId = holidayCalendarDetailsId;
	}
	public int getHolidayId() {
		return holidayId;
	}
	public void setHolidayId(int holidayId) {
		this.holidayId = holidayId;
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
	public String getHolidayName() {
		return holidayName;
	}
	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}
	public int getHolidayTypeId() {
		return holidayTypeId;
	}
	public void setHolidayTypeId(int holidayTypeId) {
		this.holidayTypeId = holidayTypeId;
	}
	public String getHolidayTypeName() {
		return holidayTypeName;
	}
	public void setHolidayTypeName(String holidayTypeName) {
		this.holidayTypeName = holidayTypeName;
	}
	
	
}
