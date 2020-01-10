package com.Ntranga.CLMS.vo;

public class MWorkSkillVo {

	private int workAreaSkillId;
	private String workAreaSkillName;
    private boolean isWorkAreaSkill;
    private int workAreaId;
    
    
	public int getWorkAreaSkillId() {
		return workAreaSkillId;
	}
	public void setWorkAreaSkillId(int workAreaSkillId) {
		this.workAreaSkillId = workAreaSkillId;
	}
	public String getWorkAreaSkillName() {
		return workAreaSkillName;
	}
	public void setWorkAreaSkillName(String workAreaSkillName) {
		this.workAreaSkillName = workAreaSkillName;
	}
	public boolean getIsWorkAreaSkill() {
		return isWorkAreaSkill;
	}
	public void setIsWorkAreaSkill(boolean isWorkAreaSkill) {
		this.isWorkAreaSkill = isWorkAreaSkill;
	}
	public int getWorkAreaId() {
		return workAreaId;
	}
	public void setWorkAreaId(int workAreaId) {
		this.workAreaId = workAreaId;
	}
	
	@Override
	public String toString() {
		return "MWorkSkillVo [workAreaSkillId=" + workAreaSkillId
				+ ", workAreaSkillName=" + workAreaSkillName
				+ ", isWorkAreaSkill=" + isWorkAreaSkill + ", workAreaId="
				+ workAreaId + "]";
	}
    
	
    
}
