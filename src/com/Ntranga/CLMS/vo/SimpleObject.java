package com.Ntranga.CLMS.vo;

public class SimpleObject {

	private int id;
	private String name;
	private String desc; 

	public SimpleObject(){
		
	}
	
	public SimpleObject(int id,String name){
		this.id=id;
		this.name=name;
	}

	
	public SimpleObject(int id, String name, String desc) {	
		this.id = id;
		this.name = name;
		this.desc = desc;
	}

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
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleObject other = (SimpleObject) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SimpleObject [id=" + id + ", name=" + name + ", desc=" + desc + "]";
	}

	

}
