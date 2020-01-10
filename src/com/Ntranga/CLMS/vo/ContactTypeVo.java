package com.Ntranga.CLMS.vo;

import com.Ntranga.core.CLMS.entities.MContactType;

public class ContactTypeVo {

	private Integer contactTypeId;
    private String contactTypeName;
    
    
    /**
	 * 
	 */
	public ContactTypeVo() {
		
		// TODO Auto-generated constructor stub
	}
    
	public ContactTypeVo(MContactType mcontactType) {
		
		this.contactTypeName = mcontactType.getContactTypeName();
	}
    
	/**
	 * @return the contactTypeId
	 */
	public Integer getContactTypeId() {
		return contactTypeId;
	}
	/**
	 * @param contactTypeId the contactTypeId to set
	 */
	public void setContactTypeId(Integer contactTypeId) {
		this.contactTypeId = contactTypeId;
	}
	
	/**
	 * @return the contactTypeName
	 */
	public String getContactTypeName() {
		return contactTypeName;
	}
	/**
	 * @param contactTypeName the contactTypeName to set
	 */
	public void setContactTypeName(String contactTypeName) {
		this.contactTypeName = contactTypeName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MContactTypeVo [contactTypeId=" + contactTypeId
				+ ", contactTypeName=" + contactTypeName + "]";
	}
    
}
