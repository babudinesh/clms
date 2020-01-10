package com.Ntranga.CLMS.vo;

public class AddressTypeVo {

	 private Integer addressTypeId;
     private String addressTypeName;
     
     
 /**
	 * 
	 */
	public AddressTypeVo() {
	
		// TODO Auto-generated constructor stub
	}
	
/*	public AddressTypeVo(MAddressType mAddressType) {
		this.addressTypeName = mAddressType.getAddressTypeName();
	}*/
	
	/**
	 * @return the addressTypeId
	 */
	public Integer getAddressTypeId() {
		return addressTypeId;
	}
	/**
	 * @param addressTypeId the addressTypeId to set
	 */
	public void setAddressTypeId(Integer addressTypeId) {
		this.addressTypeId = addressTypeId;
	}
	/**
	 * @return the addressTypeName
	 */
	public String getAddressTypeName() {
		return addressTypeName;
	}
	/**
	 * @param addressTypeName the addressTypeName to set
	 */
	public void setAddressTypeName(String addressTypeName) {
		this.addressTypeName = addressTypeName;
	}
}
