package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.CustomerDao;
import com.Ntranga.CLMS.vo.AddressVo;
import com.Ntranga.CLMS.vo.ContactVo;
import com.Ntranga.CLMS.vo.CountryVo;
import com.Ntranga.CLMS.vo.CustomerCountriesVo;
import com.Ntranga.CLMS.vo.CustomerDetailsVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.core.CLMS.entities.CustomerAddress;
import com.Ntranga.core.CLMS.entities.CustomerContact;
import com.Ntranga.core.CLMS.entities.MContactType;


@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

	
	@Autowired
	private CustomerDao customerDao; 
	
	

	

	@Override
	public List<AddressVo> getCustomerAddressList(Integer customerId) {
		// TODO Auto-generated method stub
		return customerDao.getCustomerAddressList(customerId);
	}

	@Override
	public Integer saveCustomerAddress(CustomerAddress customerAddress) {
		// TODO Auto-generated method stub
		return customerDao.saveCustomerAddress(customerAddress);
	}

	
	@Override
	public List<SimpleObject> getCustomerCurrentAddressRecordsByCustomerId(Integer customerAddressId) {
		// TODO Auto-generated method stub
		return customerDao.getCustomerCurrentAddressRecordsByCustomerId(customerAddressId);
	}

	/*************************** Customer Contact **************************/
	@Override
	public Integer saveCustomerContact(CustomerContact contact) {
		// TODO Auto-generated method stub
		return customerDao.saveCustomerContact(contact);
	}


	@Override
	public List<ContactVo> getCustomerContactsList(Integer customerId) {
		// TODO Auto-generated method stub
		return customerDao.getCustomerContactsList(customerId);
	}



	@Override
	public ContactVo getCustomerContactRecordById(Integer contactId) {
		// TODO Auto-generated method stub
		return customerDao.getCustomerContactRecordById(contactId);
	}
	
	
	public List<SimpleObject> getTransactionListForContact(Integer customerId, Integer contactUniqueId){
		return customerDao.getTransactionListForContact(customerId, contactUniqueId);
	}
	
	/*************************** Customer Contact **************************/

	@Override
	public String getInustrySectorsList(String industryIds) {
		// TODO Auto-generated method stub
		return customerDao.getInustrySectorsList(industryIds);
	}

	@Override
	public Integer saveCustomer(String customerJsonString) {
		// TODO Auto-generated method stub
		return customerDao.saveCustomer(customerJsonString);
	}

	@Override
	public List<CustomerDetailsVo> customerListbyId(Integer CustomerId) {
		// TODO Auto-generated method stub
		return customerDao.customerListbyId(CustomerId);
	}

	@Override
	public List<CustomerDetailsVo> getCustomerDetailsList(String customerId) {
		// TODO Auto-generated method stub
		return customerDao.getCustomerDetailsList(customerId);
	}
	
	@Override
	public List validateCustomerName(String customerName,String customerID){
		return customerDao.validateCustomerName(customerName, customerID);
	}

	@Override
	public List<CountryVo> getCountriesListByCustomerId(String customerID) {
		// TODO Auto-generated method stub
		return customerDao.getCountriesListByCustomerId(customerID);
	}

	@Override
	public List<SimpleObject> getTransactionListForCustomerAddress(Integer customerId,Integer addressUniqueId) {
		// TODO Auto-generated method stub
		return customerDao.getTransactionListForCustomerAddress(customerId, addressUniqueId);
	}
	

	
	
	public List<CustomerCountriesVo> customerCountryList(Integer CustomerId){
		
		return customerDao.customerCountryList(CustomerId);
	}
	
	public List<SimpleObject> getTransactionListForEditingCustomerDetails(Integer customerId){
		return customerDao.getTransactionListForEditingCustomerDetails(customerId);		
	}
	
	public List<CustomerDetailsVo> customerDetailsByCustomerInfoId(Integer customerInfoId){
		return customerDao.customerDetailsByCustomerInfoId(customerInfoId);
		
	}
	
	public List<CustomerCountriesVo> customerCountryListByCustomerInfoId(Integer customerInfoId){
		return customerDao.customerCountryListByCustomerInfoId(customerInfoId);
		
	}

	@Override
	public List<AddressVo> getCustomerAddressRecordById(Integer customerAddressId) {
		// TODO Auto-generated method stub
		return customerDao.getCustomerAddressRecordById(customerAddressId);
	}

	
	public List validateCustomerCode(String customerCode,String customerID){
		return customerDao.validateCustomerCode(customerCode,customerID);
		
	}
	
	public boolean validateCustomerAddress(Integer customerId, Integer addressTypeId){
		return customerDao.validateCustomerAddress(customerId, addressTypeId);
	}
}
