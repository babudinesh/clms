package com.Ntranga.CLMS.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WageCalculatorSaveVo implements Serializable {

	private Integer year;
	private Integer month;
	private Integer createdBy;
	private Integer modifiedBy;
	private List<WageCalculatorVo>  wageCalculatorVo = new ArrayList<WageCalculatorVo>();
	
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}	
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Integer getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public List<WageCalculatorVo> getWageCalculatorVo() {
		return wageCalculatorVo;
	}
	public void setWageCalculatorVo(List<WageCalculatorVo> wageCalculatorVo) {
		this.wageCalculatorVo = wageCalculatorVo;
	}
	
	
	
}
