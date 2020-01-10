package com.Ntranga.CLMS.vo;

public class DefineBudgetBasedOnSkillVo {

	private Integer budgetSkillId;
	private String skillType;
	private Integer headCount;
	private String amount;
	private Integer currencyId;
	
	private String currencyName;

	public Integer getBudgetSkillId() {
		return budgetSkillId;
	}

	public void setBudgetSkillId(Integer budgetSkillId) {
		this.budgetSkillId = budgetSkillId;
	}

	public String getSkillType() {
		return skillType;
	}

	public void setSkillType(String skillType) {
		this.skillType = skillType;
	}

	public Integer getHeadCount() {
		return headCount;
	}

	public void setHeadCount(Integer headCount) {
		this.headCount = headCount;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Integer getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	@Override
	public String toString() {
		return "DefineBudgetBasedOnSkillVo [budgetSkillId=" + budgetSkillId
				+ ", skillType=" + skillType + ", headCount=" + headCount
				+ ", amount=" + amount + ", currencyId=" + currencyId
				+ ", currencyName=" + currencyName + "]";
	}
	
	
}
