package com.Ntranga.CLMS.vo;
// Generated Aug 18, 2016 1:10:14 PM by Hibernate Tools 3.6.0


public class WorkerAdvanceInstallmentDetailsVo implements java.io.Serializable {

	private Integer workerAdvanceInstallmentDetailsId;	
	private Integer workerAdvanceAmountTakenId;
	private String repaymentAmountDate;
	private String repaymentAmount;
	private Integer currencyId;
	
	
	public Integer getWorkerAdvanceInstallmentDetailsId() {
		return workerAdvanceInstallmentDetailsId;
	}
	
	public void setWorkerAdvanceInstallmentDetailsId(Integer workerAdvanceInstallmentDetailsId) {
		this.workerAdvanceInstallmentDetailsId = workerAdvanceInstallmentDetailsId;
	}
	
	public Integer getWorkerAdvanceAmountTakenId() {
		return workerAdvanceAmountTakenId;
	}
	
	public void setWorkerAdvanceAmountTakenId(Integer workerAdvanceAmountTakenId) {
		this.workerAdvanceAmountTakenId = workerAdvanceAmountTakenId;
	}
	
	public String getRepaymentAmountDate() {
		return repaymentAmountDate;
	}
	
	public void setRepaymentAmountDate(String repaymentAmountDate) {
		this.repaymentAmountDate = repaymentAmountDate;
	}
	
	public String getRepaymentAmount() {
		return repaymentAmount;
	}
	
	public void setRepaymentAmount(String repaymentAmount) {
		this.repaymentAmount = repaymentAmount;
	}
	
	public Integer getCurrencyId() {
		return currencyId;
	}
	
	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}

	@Override
	public String toString() {
		return "WorkerAdvanceInstallmentDetailsVo [workerAdvanceInstallmentDetailsId="
				+ workerAdvanceInstallmentDetailsId
				+ ", workerAdvanceAmountTakenId="
				+ workerAdvanceAmountTakenId
				+ ", repaymentAmountDate="
				+ repaymentAmountDate
				+ ", repaymentAmount="
				+ repaymentAmount
				+ ", currencyId="
				+ currencyId + "]";
	}
	
	

}
