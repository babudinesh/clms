package com.Ntranga.CLMS.vo;

public class DashboardWorkersInfo {
	private String workerType;
	private Integer totalValue;
	private Integer completed;
	private Integer toBeCompleted;
	
	
	
	public String getWorkerType() {
		return workerType;
	}
	
	public void setWorkerType(String workerType) {
		this.workerType = workerType;
	}
	
	public Integer getTotalValue() {
		return totalValue;
	}
	
	public void setTotalValue(Integer totalValue) {
		this.totalValue = totalValue;
	}
	
	public Integer getCompleted() {
		return completed;
	}
	
	public void setCompleted(Integer completed) {
		this.completed = completed;
	}
	
	public Integer getToBeCompleted() {
		return toBeCompleted;
	}
	
	public void setToBeCompleted(Integer toBeCompleted) {
		this.toBeCompleted = toBeCompleted;
	}
	
	
	@Override
	public String toString() {
		return "NewJoineesVo [workerType=" + workerType + ", totalValue="
				+ totalValue + ", completed=" + completed + ", toBeCompleted="
				+ toBeCompleted + "]";
	}
	
	
	
}
