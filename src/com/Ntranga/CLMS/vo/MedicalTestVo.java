package com.Ntranga.CLMS.vo;

public class MedicalTestVo {

	private int id;
	private String name;
	private Boolean isPeriodic;
	private Boolean isOnBoard;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getIsPeriodic() {
		return isPeriodic;
	}
	public void setIsPeriodic(Boolean isPeriodic) {
		this.isPeriodic = isPeriodic;
	}
	public Boolean getIsOnBoard() {
		return isOnBoard;
	}
	public void setIsOnBoard(Boolean isOnBoard) {
		this.isOnBoard = isOnBoard;
	}
	
	@Override
	public String toString() {
		return "MedicalTestVo [id=" + id + ", name=" + name + ", isPeriodic="
				+ isPeriodic + ", isOnBoard=" + isOnBoard + ", getId()="
				+ getId() + ", getName()=" + getName() + ", getIsPeriodic()="
				+ getIsPeriodic() + ", getIsOnBoard()=" + getIsOnBoard()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
	
}
