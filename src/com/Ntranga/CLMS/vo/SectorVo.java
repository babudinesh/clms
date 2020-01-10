package com.Ntranga.CLMS.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class SectorVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4211484849144479849L;
	private Integer sectorId;
	private Integer industryId;
    private String sectorName;    
    private Set<Integer> sectorIds;
    private Set<Integer> industryIds;
    private String industryName;
    
    
    
    
    
    
    
    
    
    public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public Set<Integer> getSectorIds() {
		return sectorIds;
	}

	public void setSectorIds(Set<Integer> sectorIds) {
		this.sectorIds = sectorIds;
	}

	public Set<Integer> getIndustryIds() {
		return industryIds;
	}

	public void setIndustryIds(Set<Integer> industryIds) {
		this.industryIds = industryIds;
	}

	public SectorVo(){
    	super();
    }
    
	public SectorVo(Integer sectorId, String sectorName) {
		super();
		this.sectorId = sectorId;
		this.sectorName = sectorName;
	}
	
	public SectorVo(Integer sectorId) {
		super();
		this.sectorId = sectorId;		
	}
	
	public Integer getSectorId() {
		return sectorId;
	}
	public void setSectorId(Integer sectorId) {
		this.sectorId = sectorId;
	}
	public Integer getIndustryId() {
		return industryId;
	}
	public void setIndustryId(Integer industryId) {
		this.industryId = industryId;
	}
	public String getSectorName() {
		return sectorName;
	}
	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}
	  
    
}
