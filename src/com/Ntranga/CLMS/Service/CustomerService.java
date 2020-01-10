package com.Ntranga.CLMS.Service;

import java.util.List;

import com.Ntranga.CLMS.vo.AddressVo;
import com.Ntranga.CLMS.vo.ContactVo;
import com.Ntranga.CLMS.vo.CountryVo;
import com.Ntranga.CLMS.vo.CustomerCountriesVo;
import com.Ntranga.CLMS.vo.CustomerDetailsVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.core.CLMS.entities.CustomerAddress;
import com.Ntranga.core.CLMS.entities.CustomerContact;


public interface CustomerService {



	public List<AddressVo> getCustomerAddressList(Integer customerId);

	public Integer saveCustomerAddress(CustomerAddress customerAddress);

	public List<AddressVo> getCustomerAddressRecordById(Integer customerAddressId);

	public List<SimpleObject> getCustomerCurrentAddressRecordsByCustomerId(Integer customerAddressId);	

	public List<CountryVo> getCountriesListByCustomerId(String customerID);
	
	public List<SimpleObject> getTransactionListForCustomerAddress(Integer customerId, Integer addressUniqueId);
	
	
	
	/********************* Customer Contact ******************/	

	public Integer saveCustomerContact(CustomerContact contact);	
	
	public List<ContactVo> getCustomerContactsList(Integer customerId);

	public ContactVo getCustomerContactRecordById(Integer contactId);
	
	public List<SimpleObject> getTransactionListForContact(Integer customerId, Integer contactUniqueId);

	/********************* Customer Contact ******************/
	

	public String getInustrySectorsList(String industryIds) ;
	
	public Integer saveCustomer(String  customerJsonString);
	
	public List<CustomerDetailsVo> customerListbyId(Integer CustomerId);

	public List<CustomerDetailsVo> getCustomerDetailsList(String customerId);
	
	public List validateCustomerName(String customerName,String customerID);
	
	
	public List<CustomerCountriesVo> customerCountryList(Integer CustomerId);
	
	public List<SimpleObject> getTransactionListForEditingCustomerDetails(Integer customerId);
	
	public List<CustomerDetailsVo> customerDetailsByCustomerInfoId(Integer customerInfoId);
	
	public List<CustomerCountriesVo> customerCountryListByCustomerInfoId(Integer customerInfoId);
	
	
	public List validateCustomerCode(String customerCode,String customerID);

	
	public boolean validateCustomerAddress(Integer customerId, Integer addressTypeId);
	
	
}
