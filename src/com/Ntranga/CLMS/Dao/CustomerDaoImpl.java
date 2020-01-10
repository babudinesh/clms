package com.Ntranga.CLMS.Dao;


import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.AddressVo;
import com.Ntranga.CLMS.vo.ContactVo;
import com.Ntranga.CLMS.vo.CountryVo;
import com.Ntranga.CLMS.vo.CustomerCountriesVo;
import com.Ntranga.CLMS.vo.CustomerDetailsVo;
import com.Ntranga.CLMS.vo.SectorVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CustomerAddress;
import com.Ntranga.core.CLMS.entities.CustomerContact;
import com.Ntranga.core.CLMS.entities.CustomerCountries;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetailsInfo;
import com.Ntranga.core.CLMS.entities.CustomerIndustrySectorDetails;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import common.Logger;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Transactional
@Repository("customerDao")
public class CustomerDaoImpl implements CustomerDao {

	@Autowired
	private SessionFactory sessionFactory;

	/* Get Industries Master List */
	private static Logger log = Logger.getLogger(CustomerDaoImpl.class);
	

	@Override
	public List<AddressVo> getCustomerAddressList(Integer customerId) {
		Session session = sessionFactory.getCurrentSession();
		List<AddressVo> CustomerAddressVoList = new ArrayList<>();
		try {
			
			
			// Query query = session.createSQLQuery("CALL PRO_GET_CUSTOMER_VALID_ADDRESS("+customerId+")");
			Query query = session.createSQLQuery("SELECT `Address_Id`,`Customer_Id`,addr.`Address_Type_Id`,`Address_1`,`Address_2`,`Address_3`,`Country_Name`,`State_Name`,`Pincode`, `Transaction_Date`, addr.`Country`,addr.`State`, addr.`City`,`Address_Type_Name`,Address_Sequence_Id,address_unique_id FROM customer_address addr  INNER JOIN `m_state` s  ON ( s.State_Id=addr.`State` ) INNER JOIN `m_country` c  ON ( c.Country_Id=addr.`Country` ) INNER JOIN `m_address_type` mat ON (mat.`Address_Type_Id` = addr.`Address_Type_Id` )WHERE `Transaction_Date` <= CURRENT_DATE() AND transaction_date = (SELECT MAX(transaction_date) FROM customer_address b WHERE addr.address_unique_id = b.address_unique_id AND addr.customer_id = b.customer_id AND Transaction_Date <= CURRENT_DATE())AND `Address_Sequence_Id` = (SELECT MAX(`Address_Sequence_Id`) FROM customer_address c WHERE addr.address_unique_id = c.address_unique_id AND addr.customer_id = c.customer_id AND c.Transaction_Date = addr.Transaction_Date) and Customer_Id="+customerId+" order by Address_Type_Name");
			System.out.println(query.getQueryString());
			List CustomerAddressList = query.list();
			for (Object customer : CustomerAddressList) {
				Object[] customerArray = (Object[]) customer;
				AddressVo addressVo = new AddressVo();
				addressVo.setAddressId((Integer) customerArray[0]);
				addressVo.setCustomerDetailsId((Integer) customerArray[1]);
				addressVo.setAddressTypeId((Integer) customerArray[2]);
				addressVo.setAddress1(customerArray[3] + "");
				addressVo.setAddress2((customerArray[4] == null || (customerArray[4]+"").equalsIgnoreCase("null") || (customerArray[4]+"").trim().isEmpty()) ? "" : customerArray[4] + "");
				addressVo.setAddress3((customerArray[5] == null || (customerArray[5]+"").equalsIgnoreCase("null") || (customerArray[5]+"").trim().isEmpty()) ? "" : customerArray[5] + "");
				addressVo.setCountry(customerArray[6] + "");
				addressVo.setState(customerArray[7] + "");				
				addressVo.setPincode(customerArray[8] + "");
				addressVo.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)customerArray[9]));
				addressVo.setCountryId((Integer)customerArray[10] );
				addressVo.setStateId( (Integer)customerArray[11] );
				addressVo.setCity(customerArray[12]+"");
				addressVo.setAddressType(customerArray[13]+"" );
				addressVo.setAddressUniqueId((Integer)customerArray[14]);
				
				CustomerAddressVoList.add(addressVo);
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}

		return CustomerAddressVoList;
	}

	@Override
	public Integer saveCustomerAddress(CustomerAddress customerAddress) {	
		Integer customerAdressId = 0;
		Session session = this.sessionFactory.openSession();
		BigInteger adressSequenceId = new BigInteger("0");
		try {	
			session.beginTransaction();	
			if(customerAddress != null && customerAddress.getAddressId() != null && customerAddress.getAddressId() > 0){
				CustomerAddress address = (CustomerAddress) sessionFactory.getCurrentSession().load(CustomerAddress.class, customerAddress.getAddressId());
				address.setAddress1(customerAddress.getAddress1());
				address.setAddress2(customerAddress.getAddress2());
				address.setAddress3(customerAddress.getAddress3());
				address.setCountry(customerAddress.getCountry());
				address.setState(customerAddress.getState());
				address.setCity(customerAddress.getCity());
				address.setPincode(customerAddress.getPincode());
				address.setIsActive(customerAddress.getIsActive());
				address.setTransactionDate(customerAddress.getTransactionDate());
				address.setModifiedBy(customerAddress.getModifiedBy());
				address.setModifiedDate(customerAddress.getModifiedDate());
				sessionFactory.getCurrentSession().update(address);
				customerAdressId = customerAddress.getAddressId();
			}else{						
						
				if(customerAddress != null && customerAddress.getAddressUniqueId() > 0){	
					adressSequenceId  = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(address_sequence_id),0) FROM `customer_address` addr WHERE  addr.address_unique_id = "+customerAddress.getAddressUniqueId() +" AND addr.`Transaction_Date` = '"+DateHelper.convertDateToSQLString(customerAddress.getTransactionDate())+"' and customer_id='"+customerAddress.getCustomerDetails().getCustomerId()+"'").list().get(0);
				}else {
					BigInteger uniqueId = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Address_Unique_Id),0) as id FROM customer_address").list().get(0);
					customerAddress.setAddressUniqueId( uniqueId.intValue()+1 );					
				}			  				
				customerAddress.setAddressSequenceId(adressSequenceId.intValue()+1);						
				customerAdressId = (Integer) session.save(customerAddress);					
			}
			if(!(session.getTransaction().wasCommitted())){
				 session.getTransaction().commit();
				 session.flush();
			 }
			 }catch (Exception e) {
				 log.error("Error Occured ",e);
				 if(!(session.getTransaction().wasRolledBack())){
					 session.getTransaction().rollback();
				 }
			 }
		 System.out.println(customerAddress.getAddressId()+" ::customerAdressId"+customerAdressId);
		return customerAdressId;

	}

	
	@Override
	public List<AddressVo> getCustomerAddressRecordById(Integer customerAddressId) {
		Session session = sessionFactory.getCurrentSession();
		AddressVo addressVo = new AddressVo();
		List<AddressVo> adressList = new ArrayList<AddressVo>();
		try {
			Query query = session.createSQLQuery(
					"SELECT `Address_Id`,`Customer_Id`,addr.`Address_Type_Id`,`Address_1`,`Address_2`,`Address_3`,`Country_Name`,`State_Name`,`Pincode`, `Transaction_Date`, addr.`Country`,addr.`State`, addr.`City`,mat.`Address_Type_Name`,`Is_Active`,Address_Unique_Id  "
							+ " FROM `customer_address` addr INNER JOIN `m_country` mc  ON (mc.`Country_Id` = addr.`Country`) INNER JOIN `m_state` ms  ON (ms.`State_Id`=addr.`State`) "
							+ " INNER JOIN `m_address_type` mat ON (mat.`Address_Type_Id` = addr.`Address_Type_Id` ) WHERE `Address_Id` = '" + customerAddressId
							+ "'");
			System.out.println(query.getQueryString());
			List CustomerAddressList = query.list();
			for (Object customer : CustomerAddressList) {
				Object[] customerArray = (Object[]) customer;				
				addressVo.setAddressId((Integer) customerArray[0]);
				addressVo.setCustomerDetailsId((Integer) customerArray[1]);
				addressVo.setAddressTypeId((Integer) customerArray[2]);
				addressVo.setAddress1(customerArray[3] + "");
				addressVo.setAddress2((customerArray[4] == null || (customerArray[4]+"").equalsIgnoreCase("null") || (customerArray[4]+"").trim().isEmpty()) ? "" : customerArray[4] + "");
				addressVo.setAddress3((customerArray[5] == null || (customerArray[5]+"").equalsIgnoreCase("null") || (customerArray[5]+"").trim().isEmpty()) ? "" : customerArray[5] + "");				
				addressVo.setCountry(customerArray[6] + "");
				addressVo.setState(customerArray[7] + "");				
				addressVo.setPincode(customerArray[8] + "");				
				addressVo.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)customerArray[9]));
				addressVo.setCountryId((Integer)customerArray[10] );
				addressVo.setStateId( (Integer)customerArray[11] );
				addressVo.setCity(customerArray[12]+"");
				addressVo.setAddressType(customerArray[13]+"" );
				addressVo.setIsActive(customerArray[14]+"");	
				addressVo.setAddressUniqueId( (Integer)customerArray[15]);
				adressList.add(addressVo);
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}

		return adressList;
	}

	
	
	
	
	
	/****************************** Customer Contact start*********************************/
	
	/*
	 * Save the customer contact record
	 */
	@Override
	public Integer saveCustomerContact(CustomerContact customerContact) {
		System.out.println("From saveCustomerContact() ::    "+customerContact.toString());
		Session session = this.sessionFactory.openSession();//getCurrentSession();
			
		Integer contactId = null;
		BigInteger contactSequenceId = new BigInteger("0");
		try {
			session.beginTransaction();	
			if(customerContact != null && customerContact.getContactId() != null && customerContact.getContactId() > 0){
				CustomerContact contact = (CustomerContact) sessionFactory.getCurrentSession().load(CustomerContact.class, customerContact.getContactId());
				contact.setContactName(customerContact.getContactName());
				contact.setMobileNumber(customerContact.getMobileNumber());
				contact.setBusinessPhoneNumber(customerContact.getBusinessPhoneNumber());
				contact.setExtensionNumber(customerContact.getExtensionNumber());
				contact.setEmailId(customerContact.getEmailId());
				contact.setIsActive(customerContact.getIsActive());
				contact.setAddressUniqueId(customerContact.getAddressUniqueId());
				contact.setContactUniqueId(customerContact.getContactUniqueId());
				contact.setTransactionDate(customerContact.getTransactionDate());				
				contact.setModifiedBy(customerContact.getModifiedBy());
				contact.setModifiedDate(new Date());
				sessionFactory.getCurrentSession().update(contact);
				contactId = customerContact.getContactId();
			}else{						
						
				if(customerContact != null &&  customerContact.getContactUniqueId() > 0){	
					contactSequenceId  = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Contact_Sequence_Id),0) FROM `customer_contact` contact  WHERE  contact.Contact_Unique_Id = "+customerContact.getContactUniqueId() +" AND contact.`Transaction_Date` = '"+DateHelper.convertDateToSQLString(customerContact.getTransactionDate())+"' and customer_id='"+customerContact.getCustomerDetails().getCustomerId()+"'").list().get(0);
				}else {
					BigInteger uniqueId = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Contact_Unique_Id),0) as id FROM customer_contact").list().get(0);
					customerContact.setContactUniqueId( uniqueId.intValue()+1 );					
				}			  				
				customerContact.setContactSequenceId(contactSequenceId.intValue()+1);				
				System.out.println(customerContact.toString());
				contactId = (Integer) session.save(customerContact);					
			}
			
			if(!(session.getTransaction().wasCommitted())){
				 session.getTransaction().commit();
			 }
		 }catch (Exception e) {
			 log.error("Error Occured ",e);
			 if(!(session.getTransaction().wasRolledBack())){
				 session.getTransaction().rollback();
			 }
		 }
		 return contactId;
	}
	
	
	@Override
	public List<SimpleObject> getCustomerCurrentAddressRecordsByCustomerId(Integer customerId) {
		List<SimpleObject> objects = new ArrayList<SimpleObject>();
		try{
			Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT address_unique_id,`Address_Type_Name`,`Address_1`,`City`,`State_Name`,`Country_Name`,`Pincode` FROM customer_address addr   INNER JOIN `m_state` s  ON ( s.State_Id=addr.`State` ) INNER JOIN `m_country` c  ON ( c.Country_Id=addr.`Country` ) INNER JOIN `m_address_type` mat ON (mat.`Address_Type_Id` = addr.`Address_Type_Id` )WHERE `Transaction_Date` <= CURRENT_DATE() AND transaction_date = (SELECT MAX(transaction_date) FROM customer_address b WHERE addr.address_unique_id = b.address_unique_id AND addr.customer_id = b.customer_id AND Transaction_Date <= CURRENT_DATE())AND `Address_Sequence_Id` = (SELECT MAX(`Address_Sequence_Id`) FROM customer_address c WHERE addr.address_unique_id = c.address_unique_id AND addr.customer_id = c.customer_id AND Transaction_Date = addr.transaction_date ) and Customer_Id="+customerId);
			System.out.println(query.getQueryString());
			List CustomerAddressList = query.list();
			for (Object customer : CustomerAddressList) {
				Object[] customerArray = (Object[]) customer;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)customerArray[0]);
				System.out.println(customerArray[3]);
				object.setName(customerArray[1]+"-"+customerArray[2]+"-"+customerArray[3]+"-"+customerArray[4]+"-"+customerArray[5]+"-"+customerArray[6]+".");			
				objects.add(object);
			}		
			
	} catch (Exception e) {
		log.error("Error Occured ",e);

	}
		return objects;
	}
	
	
		
	/*
	 * Get all the customer contact records based on given customerId
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ContactVo> getCustomerContactsList(Integer customerId) {
		//System.out.println("From Customer Contact list....  "+customerId);
		Session session = sessionFactory.getCurrentSession();
		//System.out.println("::session::"+session);			
		List contactsList = null;
		List<ContactVo> returnList = new ArrayList<ContactVo>();
		ContactVo contact = new ContactVo();
		
		String hqlQuery = "SELECT contact.Customer_Id, contact.Contact_Id,  contact.Address_Unique_Id, "
				+ " contact.Contact_Type_Id, contact.Contact_Name, contact.Contact_Name_Other_Language,"
				+ " contact.Mobile_Number, contact.Business_Phone_Number, contact.Extension_Number, "
				+ " contact.Email_Id, contact.Transaction_Date, contact.Is_Active,  "
				+ " address.Address_1, address.City AS City, state.State_Name AS State,  country.Country_Name AS Country, maddress.Address_Type_Name,"
				+ " mcontact.Contact_Type_Name , contact.Contact_Unique_Id"
				+ " FROM customer_contact AS contact "
				+ " LEFT JOIN m_contact_type mcontact ON contact.Contact_Type_Id = mcontact.Contact_Type_Id "
				+ " LEFT JOIN customer_address AS address  ON contact.Customer_Id = address.Customer_Id  AND contact.Address_Unique_Id = address.Address_Unique_Id "
				+ " LEFT JOIN m_address_type AS maddress  ON address.Address_type_Id = maddress.Address_type_Id "
				+ " LEFT JOIN m_state AS state ON state.State_Id = address.State "
				+ " LEFT JOIN m_country AS country ON country.Country_Id = address.Country "
				+ " WHERE contact.Customer_Id = "+customerId
				+ " AND contact.`Transaction_Date` <= CURRENT_DATE() " 
		        + " AND contact.Transaction_Date = (SELECT MAX(Transaction_Date) FROM customer_contact customer "
		        							+ "	WHERE contact.Contact_Unique_Id = customer.Contact_Unique_Id "
		        							+ "AND contact.Customer_Id =customer.Customer_Id "
		        							+ "AND Transaction_Date <= CURRENT_DATE()) "						  
				+"  AND Contact_Sequence_Id IN ("
										+ " SELECT Max(Contact_Sequence_Id) FROM customer_contact customer1 "
										+ " WHERE customer1.Customer_Id  = contact.Customer_Id "
										+ " AND customer1.Contact_Unique_Id = contact.Contact_Unique_Id "
										+ " AND customer1.Is_Active = contact.Is_Active "
										+ " AND customer1.Contact_Type_Id = contact.Contact_Type_Id"
										+ " AND customer1.Transaction_Date = contact.transaction_date )";
		try {				
			
				hqlQuery += " GROUP BY contact.Contact_Unique_Id Order By mcontact.Contact_Type_Name ";
					
			SQLQuery query = session.createSQLQuery(hqlQuery);
			contactsList = query.list();

			for (Object customer : contactsList) {
				System.out.println(customer.toString());
				Object[] obj = (Object[]) customer;
				contact = new ContactVo();
	
				contact.setCustomerId((Integer)obj[0]);
				contact.setContactId((Integer)obj[1]);
				contact.setAddressId((Integer)obj[2]);
				contact.setContactTypeId((Integer)obj[3]);
				contact.setContactName((String)obj[4]);
				contact.setContactNameOtherLanguage((String)obj[5]);
				contact.setMobileNumber((String)obj[6]);
				contact.setBusinessPhoneNumber((String)obj[7]);
				contact.setExtensionNumber((String)obj[8]);
				contact.setEmailId((String)obj[9]);
				contact.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[10]));
				contact.setIsActive(obj[11]+"");
				contact.setAddress((String)(obj[16]+"- "+obj[12]+","+obj[13]+", "+obj[14]+", "+obj[15]));
				contact.setContactTypeName((String)obj[17]);
				contact.setContactUniqueId((Integer) obj[18]);
				
				returnList.add(contact);
			}	
			
				
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		session.flush();
		return returnList;
	}
	
	
	
	@Override
	public ContactVo getCustomerContactRecordById(Integer contactId) {
		System.out.println(contactId);
		Session session = sessionFactory.getCurrentSession();
		ContactVo contact = new ContactVo();
		try {
			String hqlQuery = "SELECT contact.Customer_Id, contact.Contact_Id,  contact.Address_Unique_Id, "
					+ " contact.Contact_Type_Id, contact.Contact_Name, contact.Contact_Name_Other_Language,"
					+ " contact.Mobile_Number, contact.Business_Phone_Number, contact.Extension_Number, "
					+ " contact.Email_Id, contact.Transaction_Date, contact.Is_Active,"
					+ " address.Address_1, address.City AS City, state.State_Name AS State,  country.Country_Name AS Country, "
					+ " mcontact.Contact_Type_Name , maddress.Address_Type_Name, contact.Contact_Unique_Id"
					+ " FROM customer_contact AS contact LEFT JOIN customer_details AS details  ON contact.Customer_Id = details.Customer_Id"
					+ " LEFT JOIN m_contact_type AS mcontact ON  contact.Contact_Type_Id = mContact.Contact_Type_Id "
					+ " LEFT JOIN customer_address AS address  ON contact.Customer_Id = address.Customer_Id  AND contact.Address_Unique_Id = address.Address_Unique_Id "
					+ " LEFT JOIN m_address_type AS maddress  ON address.Address_type_Id = maddress.Address_type_Id "
					+ " LEFT JOIN m_state AS state ON state.State_Id = address.State "
					+ " LEFT JOIN m_country AS country ON country.Country_Id = address.Country "
					+ " WHERE  contact.Contact_Id = "+contactId;
			
			Query query = session.createSQLQuery(hqlQuery);
			System.out.println(query.getQueryString());
			List CustomerAddressList = query.list();
			for (Object customer : CustomerAddressList) {
				Object[] obj = (Object[]) customer;		
				contact = new ContactVo();
				contact.setCustomerId((Integer)obj[0]);
				contact.setContactId((Integer)obj[1]);
				contact.setAddressId((Integer)obj[2]);
				contact.setContactTypeId((Integer)obj[3]);
				contact.setContactName((String)obj[4]);
				contact.setContactNameOtherLanguage((String)obj[5]);
				contact.setMobileNumber((String)obj[6]);
				contact.setBusinessPhoneNumber((String)obj[7]);
				contact.setExtensionNumber((String)obj[8]);
				contact.setEmailId((String)obj[9]);
				contact.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[10]));
				contact.setIsActive(obj[11]+"");
				contact.setAddress((String)(obj[12]+"-"+obj[13]+"-"+obj[14]+"-"+obj[15]+"-"+obj[17]));
				contact.setContactTypeName((String)obj[16]);
				contact.setContactUniqueId((Integer)obj[18]);
				
				/*contactVo.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)customerArray[10]));
				contactVo.setCountryId((Integer)customerArray[11] );
				contactVo.setStateId( (Integer)customerArray[12] );
				contactVo.setCityId( (Integer)customerArray[13]);
				contactVo.setAddressType(customerArray[14]+"" );
				contactVo.setIsActive(customerArray[15]+"");*/			
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}

		return contact;
	}
	
	@Override
	public List<SimpleObject> getTransactionListForContact(Integer customerId, Integer contactUniqueId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List contactList = session.createSQLQuery("SELECT `Contact_Id` AS id ,CONCAT(DATE_FORMAT(`Transaction_Date`,'%d/%m/%Y'),' - ',`Contact_Sequence_Id`) AS cname FROM `customer_contact` contact INNER JOIN `m_contact_type` mcontact ON (contact.`contact_Type_Id`=mcontact.`Contact_Type_Id`) WHERE contact.`Customer_Id` ="+customerId+" and contact_Unique_Id="+contactUniqueId).list();
			for (Object countryObject  : contactList) {
				Object[] obj = (Object[]) countryObject;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)obj[0]);
				object.setName(obj[1]+"");
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}

		return transactionList;

	}
	
	/***************************  CustomerContact End ***************************/
	
	/*
	 * 
	 */
	

	@Override
	public String getInustrySectorsList(String industryIds) {
		Session session = sessionFactory.getCurrentSession();

		String jsonString = null;					
		try {		

			 List tempList = session.createSQLQuery(" SELECT `Industry_Name`, `Sector_Id`,`Sector_Name` FROM `m_sector` s INNER JOIN `m_industry`  i ON ( i.`Industry_Id`=s.`Industry_Id`) WHERE i.`Industry_Id` IN ("+industryIds+") ORDER BY `Industry_Name`").list();
			 Map<String, ArrayList<SectorVo>>  sector = new HashMap<String, ArrayList<SectorVo>>();
			for (Object o : tempList) {
				Object[] obj = (Object[]) o;
				if(sector != null && sector.containsKey(obj[0]+"")){				 						
					  sector.get(obj[0]+"").add(new SectorVo(Integer.valueOf(obj[1]+""),obj[2]+""));
				}else{
					ArrayList<SectorVo> arrayList1 = new ArrayList<SectorVo>();
					arrayList1.add(new SectorVo(Integer.valueOf(obj[1]+""),obj[2]+""));
					sector.put(obj[0]+"",arrayList1);
				}
			}
			
			try {
				jsonString = JSONObject.valueToString(sector).toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("Error Occured ",e);
			}
			  System.out.println(jsonString +" ::jsonString");
			
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		session.flush();
		return jsonString;
	}  
	

	@Override
	public Integer saveCustomer(String customerJsonString) {
		Session session = sessionFactory.getCurrentSession();
		Integer customerId = null;
		Integer customerInfoId = null;
		
		try {
			 	JsonParser jsonParser = new JsonParser();
			 	
				JsonObject jo = (JsonObject) jsonParser.parse(customerJsonString);
				CustomerDetails customerDetails = new CustomerDetails(); 
					System.out.println(customerJsonString);
			
					if (jo.get("customerId") == null || jo.get("customerId").getAsInt() == 0) {
						customerDetails.setCreatedBy(jo.get("createdBy").getAsInt());
						customerDetails.setCustomerCode(jo.get("customerCode").getAsString().toUpperCase());
						customerDetails.setCreatedDate(new Date());
						customerDetails.setModifiedBy(jo.get("modifiedBy").getAsInt());
						customerDetails.setModifiedDate(new Date());
						customerId = (Integer) session.save(customerDetails);
		
					} else {
						customerDetails = (CustomerDetails) session.load(CustomerDetails.class, Integer.valueOf(jo.get("customerId").getAsInt()));
						customerDetails.setModifiedBy(jo.get("modifiedBy").getAsInt());  
						customerDetails.setModifiedDate(new Date());
						customerDetails.setCustomerCode(jo.get("customerCode").getAsString().toUpperCase());
						customerId = jo.get("customerId").getAsInt();
						session.update(customerDetails);
					}
					
				/* Save CustomerDetailsInfo*/
				
					CustomerDetailsInfo customerDetailsInfo = new CustomerDetailsInfo();
					String dateStr = jo.get("transactionDate").getAsString();
					
					DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					Date transactionDate = null;
					try {
						transactionDate = (Date) formatter.parse(dateStr);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					Integer sequenceId = 0;	
					
					if(jo.get("customerinfoId")!= null && jo.get("customerinfoId").getAsInt()+"" != null && jo.get("customerinfoId").getAsInt()+"" != "" && jo.get("customerinfoId").getAsInt() >0){
						List tmepList = sessionFactory.getCurrentSession().createSQLQuery("select max(Customer_Sequence_Id) from customer_details_info where Transaction_Date = '"+DateHelper.convertDateToSQLString(transactionDate)+"' and Customer_Id = '"+customerId+"' and Customer_Info_Id = '"+jo.get("customerinfoId").getAsInt()+"'").list();
							sequenceId =(Integer) tmepList.get(0);
						}else{							
							List tmepList = sessionFactory.getCurrentSession().createSQLQuery("select max(Customer_Sequence_Id) from customer_details_info where Transaction_Date = '"+DateHelper.convertDateToSQLString(transactionDate)+"' and Customer_Id = '"+customerId+"'").list();
							if(tmepList.get(0) == null){
								sequenceId =1;							
							}else if(tmepList.get(0) != null ){	
								sequenceId =(Integer)tmepList.get(0);
								sequenceId = sequenceId+1;							
							}
						}
						
					
										
					customerDetailsInfo.setTransactionDate(transactionDate);	
					customerDetailsInfo.setCustomerSequenceId(sequenceId);
					customerDetailsInfo.setCustomerName(jo.get("customerName").getAsString().trim());
					customerDetailsInfo.setCountryId(jo.get("customerCountry").getAsInt());
					if(jo.get("totNumberOfCompanies") != null){
					customerDetailsInfo.setNumberOfCompanies(jo.get("totNumberOfCompanies").getAsInt());
					}					
					customerDetailsInfo.setEntityType(jo.get("entityType").getAsString());
					if( jo.get("isMultinaional") != null && (jo.get("isMultinaional").getAsBoolean()||jo.get("isMultinaional").getAsString().equalsIgnoreCase("yes") )){
						customerDetailsInfo.setIsMultinaional("Yes");	
					}else{
						customerDetailsInfo.setIsMultinaional("No");	
					}
					
					//System.out.println(jo.get("legacyName") +"::legacy");
					//System.out.println(jo.get("legacyName").getAsString() +"::legacy");
					try{						
					customerDetailsInfo.setLegacyName(jo.get("legacyName").getAsString());						
					}catch(Exception e){
						
					}
										
					customerDetailsInfo.setCustomerDetails(new CustomerDetails(customerId));
					customerDetailsInfo.setIsActive(jo.get("isActive").getAsString());					
					customerDetailsInfo.setModifiedBy(jo.get("modifiedBy").getAsInt()); 
					customerDetailsInfo.setModifiedDate(new Date());
					//System.out.println("customerinfoId::"+jo.get("customerinfoId").getAsInt()+":sequenceId:"+sequenceId+":CustomerSeqId:"+c//ustomerDetailsInfo.getCustomerSequenceId());
					if(jo.get("customerinfoId")!= null && jo.get("customerinfoId").getAsInt()+"" != null && jo.get("customerinfoId").getAsInt()+"" != "" && jo.get("customerinfoId").getAsInt() >0){
						customerDetailsInfo.setCustomerInfoId(jo.get("customerinfoId").getAsInt());
						System.out.println(jo.get("customerinfoId").getAsInt()+"::in update");
						customerDetailsInfo.setCreatedDate(new Date());
						session.update(customerDetailsInfo);
						customerInfoId = customerDetailsInfo.getCustomerInfoId();
					}else{	
						customerDetailsInfo.setCreatedBy(jo.get("createdBy").getAsInt()); 
						System.out.println("::in Save");
						customerDetailsInfo.setCreatedDate(new Date());
						customerInfoId = (Integer) session.save(customerDetailsInfo);							
					}
						
					session.flush();
						
						
					if(jo.get("customerinfoId")!= null && jo.get("customerinfoId").getAsInt()+"" != null && jo.get("customerinfoId").getAsInt()+"" != "" && jo.get("customerinfoId").getAsInt() >0){						
						Query q = session.createSQLQuery("delete from customer_industry_sector_details where Customer_Id = '"+customerId+"' and CustomerIndustry_Sequence_Id = '"+sequenceId+"' and Transaction_Date ='"+DateHelper.convertDateToSQLString(transactionDate)+"'");
						q.executeUpdate();
					}
					
					/* Save Industry Sector Details*/
					CustomerIndustrySectorDetails industrySectorDetails = new CustomerIndustrySectorDetails();					
					String sectorIds = jo.get("sectorIds").toString().replace("[", "").replace("]", "").replaceAll("\"", "");
					
					List tempList = session.createSQLQuery("SELECT `Sector_Id`,`Industry_Id` FROM `m_sector` WHERE `Sector_Id`  IN (" + sectorIds + ") ").list();
					System.out.println(tempList.size()+"::tempList size");
					for (Object object : tempList) {
						Object[] obj = (Object[]) object;
						System.out.println(Integer.valueOf(obj[1] + "")+Integer.valueOf(obj[0] + ""));
						session.save(new CustomerIndustrySectorDetails(new CustomerDetails(customerId),Integer.valueOf(obj[1] + ""),Integer.valueOf(obj[0] + ""),transactionDate,1,new Date(),1,new Date(),"Y",sequenceId));
					}
					
					
					/* Save Customer Country Details*/
					
				//	System.out.println("countryList::"+jo.get("countryList").getAsJsonArray());
				
					Integer countryId = 0;
					 String baseName = null;
					 String noOfCompanies = 0+"";
					 Integer languageId = 0;
					 Integer currencyId =0;
					 
					 if(jo.get("customerinfoId")!= null && jo.get("customerinfoId").getAsInt()+"" != null && jo.get("customerinfoId").getAsInt()+"" != "" && jo.get("customerinfoId").getAsInt() >0){						
							Query q1 = session.createSQLQuery("delete from customer_countries where Customer_Id = '"+customerId+"' and Customer_Countries_Sequence_Id = '"+sequenceId+"' and Transaction_Date ='"+DateHelper.convertDateToSQLString(transactionDate)+"'");
							q1.executeUpdate();
						}
					 
					 if(jo.get("customerCountriesList") != null){
						 System.out.println(jo.get("customerCountriesList")+"::country List");
					JsonArray array = jo.get("customerCountriesList").getAsJsonArray();
					//System.out.println(!jo.get("countryId").getAsString().toString().equalsIgnoreCase("null"));
					for (Object o : array) {				
						JsonObject jobj = (JsonObject) o;
											
						if(jobj.get("countryId") != null && !jobj.get("countryId").toString().equalsIgnoreCase("null")){
								countryId = jobj.get("countryId").getAsInt();
								System.out.println(countryId+"::countryId");
						}
							
						
						if(jobj.get("baseCompanyName") != null && !jobj.get("baseCompanyName").toString().equalsIgnoreCase("null")){
								baseName =jobj.get("baseCompanyName").getAsString();
						}
							
						if(jobj.get("noOfCompanies") != null && !jobj.get("noOfCompanies").toString().equalsIgnoreCase("null"))
								 noOfCompanies = jobj.get("noOfCompanies").getAsString();
							
							
						if(jobj.get("languageId") != null && !jobj.get("languageId").toString().equalsIgnoreCase("null"))
								languageId = jobj.get("languageId").getAsInt();
							
							
						if(jobj.get("currencyId") != null && !jobj.get("currencyId").toString().equalsIgnoreCase("null"))
								 currencyId =jobj.get("currencyId").getAsInt() ;
							
							
							
		       			
				       	session.clear();	   
				        session.save(new CustomerCountries(new CustomerDetails(customerId),countryId,sequenceId,transactionDate,"Y",jo.get("createdBy").getAsInt(),new Date(),jo.get("modifiedBy").getAsInt(),new Date(),languageId,currencyId,baseName,noOfCompanies));
				        //public CustomerCountries(CustomerDetails customerDetails, Integer countryId, int customerCountriesSequenceId, Date transactionDate, String isActive, int createdBy, Date createdDate, int modifiedBy, Date modifiedDate, Integer languageId, Integer currencyId, String baseCompanyName, String noOfCompanies) {
				    }
			 }

		} catch (Exception e) {
			log.error("Error Occured ",e);
			session.getTransaction().rollback();
		}
		session.flush();
		session.clear();	
		return customerInfoId;
	}

	@Override
	public List<CustomerDetailsVo> customerListbyId(Integer CustomerId) {
		// TODO Auto-generated method stub
				Session session = sessionFactory.getCurrentSession();
				System.out.println("::session::"+session);			
				CustomerDetails customersList = null;
				List customerList = new ArrayList<>();
				String cutomerLists =null;
				try {				
					List tempList =session.createSQLQuery("select a.customer_id,Customer_Name,a.Country_Id,Entity_Type,Is_Multinaional,Number_Of_Companies, "+
							" a.Transaction_Date,group_concat(distinct cc.Country_Id), group_concat(distinct ind.Industry_Id),group_concat(distinct ind.Sector_Id),a.is_Active,a.Legacy_Name,cds.Customer_Code "+
							" from customer_details_info a left outer join customer_industry_sector_details ind  "+
							" on a.customer_id = ind.customer_id and a.transaction_date = ind.transaction_date and a.customer_sequence_id = ind.CustomerIndustry_Sequence_Id "+
							" left outer join customer_countries cc on a.customer_id = cc.customer_id and a.customer_sequence_id = cc.Customer_Countries_Sequence_Id and a.transaction_date = cc.Transaction_Date "+
							" inner join m_country  mc on (mc.Country_Id = a.Country_Id) inner join customer_details cds on(cds.customer_Id = a.customer_id) "
							+ " where  a.Transaction_Date = (select max(Transaction_Date) from customer_details_info c where a.customer_id = c.customer_id and Transaction_Date <= current_date() ) "+
							" and Customer_Sequence_Id in ( select max(b.Customer_Sequence_Id) from customer_details_info b where b.Transaction_Date = (select max(Transaction_Date) from customer_details_info c where a.customer_id = c.customer_id and c.Transaction_Date <= a.Transaction_Date) "+
							" and a.customer_id = b.customer_id "+
							" and a.Is_Active ='Y' and a.customer_id ="+CustomerId +
							" group by b.customer_id) group by a.customer_id order by a.Transaction_Date desc,Customer_Sequence_Id desc").list();
				
				
					
					for(int i=0; i<tempList.size();i++){
						Object[] obj =  (Object[]) tempList.get(0);
						CustomerDetailsVo customerDetailsVo = new CustomerDetailsVo();
						customerDetailsVo.setCustomerId((Integer)obj[0]);
						customerDetailsVo.setCustomerName(obj[1]+"");
						customerDetailsVo.setCustomerCountry((Integer)obj[2]);
						customerDetailsVo.setEntityType(obj[3]+"");
						customerDetailsVo.setIsMultinaional(obj[4]+"");
						customerDetailsVo.setNumberOfCompanies((Integer)obj[5]);						
						customerDetailsVo.setTransactionDate((Date)obj[6]);
						customerDetailsVo.setMncCountries(obj[7]+"");
						customerDetailsVo.setIndustyIds(obj[8]+"");
						customerDetailsVo.setSectorIds(obj[9]+"");	
						customerDetailsVo.setIsActive(obj[10]+"");
						customerDetailsVo.setLegacyName(obj[11]+"");
						customerDetailsVo.setCustomerCode(obj[12]+"");
						customerList.add(customerDetailsVo);
						
					}
					
				/*	try {
						 cutomerLists = JSONObject.valueToString(customerList).toString();
						System.out.println("cutomerLists::"+cutomerLists);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						log.error("Error Occured ",e);
					}
					*/
				} catch (Exception e) {
					log.error("Error Occured ",e);
					
				}
				
				return customerList;
				
			}
	
	
	
	
	@Override
	public List<CustomerDetailsVo> customerDetailsByCustomerInfoId(Integer customerInfoId) {
		// TODO Auto-generated method stub
				Session session = sessionFactory.getCurrentSession();
				System.out.println("::session::"+session);			
				CustomerDetails customersList = null;
				List customerList = new ArrayList<>();
				String cutomerLists =null;
				try {				
					List tempList =session.createSQLQuery("select cdi.Customer_Id,Customer_Name,Country_Id,Entity_Type,Is_Multinaional,Number_Of_Companies,cdi.Transaction_Date,"+
															" Legacy_Name, concat('[',group_concat(Distinct Industry_Id),']'),group_concat(distinct Sector_Id),cdi.Customer_Info_Id,cdi.Is_Active,cds.Customer_Code "+
															" from customer_details_info  cdi,customer_industry_sector_details isd,customer_details cds "+
															" where cdi.customer_id = isd.customer_id and cds.customer_id = cdi.customer_id"+
															" and cdi.Transaction_Date = isd.Transaction_Date "+
															" and cdi.Customer_Sequence_Id = isd.CustomerIndustry_Sequence_Id "+
															" and Customer_Info_Id ="+customerInfoId).list();
				
				
					
					for(int i=0; i<tempList.size();i++){
						Object[] obj =  (Object[]) tempList.get(0);
						CustomerDetailsVo customerDetailsVo = new CustomerDetailsVo();
						customerDetailsVo.setCustomerId((Integer)obj[0]);
						customerDetailsVo.setCustomerName(obj[1]+"");
						customerDetailsVo.setCustomerCountry((Integer)obj[2]);
						customerDetailsVo.setEntityType(obj[3]+"");
						customerDetailsVo.setIsMultinaional(obj[4]+"");
						if(obj[5] != null){
						customerDetailsVo.setTotNumberOfCompanies((Integer)obj[5]);	
						}
						customerDetailsVo.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[6]));
						if(obj[7] != null){
						customerDetailsVo.setLegacyName(obj[7]+"");
						}
						customerDetailsVo.setIndustyIds(obj[8]+"");
						customerDetailsVo.setSectorIds(obj[9]+"");	
						customerDetailsVo.setCustomerinfoId((Integer)obj[10]);
						customerDetailsVo.setIsActive(obj[11]+"");
						customerDetailsVo.setCustomerCode(obj[12]+"");
						customerList.add(customerDetailsVo);
						
					}
					
					/*try {
						 cutomerLists = JSONObject.valueToString(customerList).toString();
						System.out.println("cutomerLists::"+cutomerLists);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						log.error("Error Occured ",e);
					}*/
					
				} catch (Exception e) {
					log.error("Error Occured ",e);
					
				}
				
				return customerList;
				
			}
	
	
	
	@Override
	public List<CustomerCountriesVo> customerCountryList(Integer customerInfoId) {
		// TODO Auto-generated method stub
				Session session = sessionFactory.getCurrentSession();								
				List customerList = new ArrayList<>();
				String cutomerLists =null;
				try {				
					List tempList = session.createSQLQuery("select cc.Customer_Countries_Id,cc.Country_Id,cc.Language_Id,cc.Currency_Id,"
														 +" cc.No_Of_Companies,mc.Country_Name,mcr.Currency,ml.Language  from customer_details_info DI "
														 +"left outer join customer_countries CC on(DI.customer_Id =CC.customer_Id and DI.Transaction_Date = CC.Transaction_Date "
														 +"	and CC.Customer_Countries_Sequence_Id = DI.Customer_Sequence_Id) "
														 +"	left join m_country mc on (cc.Country_Id = mc.Country_Id) "  
														 +"	left join m_currency mcr on (cc.Currency_Id = mcr.Currency_Id) " 
														 +" left join m_language ml on (cc.Language_Id = ml.Language_Id) " 
														 +"	where DI.Customer_Info_Id ="+customerInfoId+" order by mc.Country_name").list();
					System.out.println(tempList.size()+"::size");
					for(int i=0; i<tempList.size();i++){
						Object[] obj =  (Object[]) tempList.get(i);
						CustomerCountriesVo customerCountry = new CustomerCountriesVo();
						//customerCountry.setIndex(i+1);
						//System.out.println(obj);
						if(obj[0] != null){
							customerCountry.setCustomerCountriesId((Integer)obj[0]);
						}
						if(obj[1] != null){
							customerCountry.setCountryId((Integer)obj[1]);
						}
						if(obj[2] != null){
							customerCountry.setLanguageId((Integer)obj[2]);
						}if(obj[3] != null){
							customerCountry.setCurrencyId((Integer)obj[3]);
						}/*if(obj[4] != null){
						customerCountry.setBaseCompanyName(obj[4]+"");
						}*/if(obj[4] != null){
							customerCountry.setNoOfCompanies((String)obj[4]);
						}if(obj[5] != null){
							customerCountry.setCountryName((String)obj[5]);
						}if(obj[6] != null){
							customerCountry.setCurrencyName((String)obj[6]);
						}if(obj[7] != null){
							customerCountry.setLanguageName((String)obj[7]);
						}
						//System.out.println(customerCountry);
						customerList.add(customerCountry);
					}
					
					/*try {
						// cutomerLists = JSONObject.valueToString(customerList).toString();
						System.out.println("cutomerLists::"+cutomerLists);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						log.error("Error Occured ",e);
					}*/
					
				} catch (Exception e) {
					log.error("Error Occured ",e);
					
				}
				
				return customerList;
				
			}
	
	@Override
	public List<CustomerCountriesVo> customerCountryListByCustomerInfoId(Integer customerInfoId) {
		// TODO Auto-generated method stub
				Session session = sessionFactory.getCurrentSession();
				System.out.println("::session::"+session);			
				CustomerDetails customersList = null;
				List customerList = new ArrayList<>();
				String cutomerLists =null;
				try {				
					List tempList =session.createSQLQuery("select cc.Customer_Countries_Id,cc.Country_Id,cc.Language_Id,cc.Currency_Id,Base_Company_Name,cc.No_Of_Companies,"
														+ " mc.Country_Name,mcr.Currency, ml.Language from customer_details_info  cdi,customer_countries cc "
														+ " ,m_country mc,m_currency mcr,m_language ml where cdi.customer_id = cc.customer_id "
														+ "	and cdi.Transaction_Date = cc.Transaction_Date "
														+ "	and cdi.Customer_Sequence_Id = cc.Customer_Countries_Sequence_Id " 
														+ "	and mc.Country_Id = cc.Country_Id and mcr.Currency_Id = cc.Currency_Id " 
														+ "	and ml.Language_Id=cc.Language_Id " 
														+ "	and Customer_Info_Id = "+customerInfoId+" order by mc.country_name").list();
				
					for(int i=0; i<tempList.size();i++){
						Object[] obj =  (Object[]) tempList.get(i);
						CustomerCountriesVo customerCountry = new CustomerCountriesVo();						
						//customerCountry.setCustomerCountriesId((Integer)obj[0]);
						customerCountry.setCountryId((Integer)obj[1]);
						customerCountry.setLanguageId((Integer)obj[2]);
						customerCountry.setCurrencyId((Integer)obj[3]);
						//customerCountry.setBaseCompanyName(obj[4]+"");
						customerCountry.setNoOfCompanies((String)obj[5]);
						customerCountry.setCountryName((String)obj[6]);
						customerCountry.setCurrencyName((String)obj[7]);
						customerCountry.setLanguageName((String)obj[8]);
						customerList.add(customerCountry);
					}
					
					try {
						// cutomerLists = JSONObject.valueToString(customerList).toString();
						System.out.println("cutomerLists::"+cutomerLists);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						log.error("Error Occured ",e);
					}
					
				} catch (Exception e) {
					log.error("Error Occured ",e);
					
				}
				
				return customerList;
				
			}
	
	
	@Override
	public List<CustomerDetailsVo> getCustomerDetailsList(String customerId) {
		Session session = sessionFactory.getCurrentSession();
		List<CustomerDetailsVo> customerDetailsVos = new ArrayList<>();
		try {			

			String q = " SELECT  Customer_Name,Entity_Type,a.Transaction_Date,mc.Country_Name,Number_Of_Companies,a.customer_id, "
						+" Customer_Code,CASE a.Is_Active WHEN 'Y' THEN 'Active' ELSE 'In-Active'  END AS `status`,a.Customer_Info_Id   FROM customer_details cds "
						+" INNER JOIN customer_details_info a ON(cds.customer_id = a.customer_id) "
						+"  INNER JOIN m_country mc ON(mc.Country_Id = a.Country_Id) "
						+"  WHERE CONCAT(DATE_FORMAT(a.transaction_date, '%Y%m%d'), LPAD(a.Customer_Sequence_Id,2,'0')) =   " 
						+" ( SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Customer_Sequence_Id,2,'0'))) " 
						+" FROM customer_details_info vdi1   "
						+" WHERE a.customer_id = vdi1.customer_id  "
						+" AND vdi1.transaction_date <= CURRENT_DATE()) AND a.is_active='Y'";		

			if(customerId != null &&  !customerId.isEmpty() && Integer.parseInt(customerId) >0){
				q = q+" and a.customer_id = '"+customerId+"' ";
			}			
			q= q+"  group by a.customer_id order by customer_name";
			
			List tempList =  session.createSQLQuery(q).list();			
			CustomerDetailsVo detailsVo = new CustomerDetailsVo();
			int index = 1;
			for(Object o : tempList){
				Object[] obj = (Object[]) o;			
				detailsVo = new CustomerDetailsVo();
				detailsVo.setCustomerName(obj[0]+"");
				detailsVo.setEntityType(obj[1]+"");
				detailsVo.setTransactionDate((Date)obj[2]);
				detailsVo.setCountryName(obj[3]+"");
				detailsVo.setNumberOfCompanies((Integer)obj[4]);
				detailsVo.setCustomerId((Integer)obj[5]);
				detailsVo.setCustomerCode(obj[6]+"");
				detailsVo.setIsActive(obj[7]+"");
				detailsVo.setCustomerinfoId((Integer)obj[8]);
				detailsVo.setIndex(index);
				customerDetailsVos.add(detailsVo);
				index++;
			}			
		
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}		
		return customerDetailsVos;
	}
	
	
	
	
	@Override
	public List validateCustomerName(String customerName,String customerID){
		Session session = sessionFactory.getCurrentSession();
		Query q = null;
		String message=null;
		List simpleObjectList = new ArrayList<>();
		try{
			if(customerID != null && !customerID.isEmpty() ){
			q =  session.createQuery("from CustomerDetailsInfo where customerName='"+customerName.trim()+"' and customerDetails NOT IN ('"+customerID+"')");
			}else{
			q =  session.createQuery("from CustomerDetailsInfo where customerName='"+customerName.trim()+"'");
			}
			
			List tempList = q.list();
			
			SimpleObject object = new SimpleObject();
			if(q.list().size()>0){
				object.setId(1);
				object.setName("Customer Name already existed");				
			}else{
				object.setId(0);
				object.setName("Success");
				
			}
			simpleObjectList.add(object);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return simpleObjectList;
	}
	
	
	
	public List validateCustomerCode(String customerCode,String customerID){
		Session session = sessionFactory.getCurrentSession();
		Query q = null;
		String message=null;
		List simpleObjectList = new ArrayList<>();
		try{
			if(customerID != null && !customerID.isEmpty() && Integer.parseInt(customerID) >0 ){
			q =  session.createQuery("from CustomerDetails where customerCode='"+customerCode.trim()+"' and customerId NOT IN ('"+customerID+"')");
			}else{
			q =  session.createQuery("from CustomerDetails where customerCode='"+customerCode.trim()+"'");
			}
			
			List tempList = q.list();
			
			SimpleObject object = new SimpleObject();
			if(q.list().size()>0){
				object.setId(1);
				object.setName("Customer ID already existed");				
			}else{
				object.setId(0);
				object.setName("Success");
				
			}
			simpleObjectList.add(object);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		
		return simpleObjectList;
	}
	
	
	@Override
	public List<SimpleObject> getTransactionListForEditingCustomerDetails(Integer customerId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List customerTempList = session.createSQLQuery("select Customer_Info_Id,concat(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),'-',Customer_Sequence_Id) from customer_details_info  where Customer_Id = '"+customerId+"' and  Is_Active = 'Y'  order by Transaction_Date , Customer_Sequence_Id" ).list();
			for (Object customerObject  : customerTempList) {
				Object[] obj = (Object[]) customerObject;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)obj[0]);
				object.setName(obj[1]+"");
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}

		return transactionList;

	}

	@Override
	public List<CountryVo> getCountriesListByCustomerId(String customerID) {
		Session session = sessionFactory.getCurrentSession();
		List<CountryVo> countriesList = new ArrayList<>();
		try {
			String query = " SELECT DISTINCT mc.`Country_Id`,mc.`Country_Name`  FROM `customer_countries` cc   INNER JOIN `customer_details_info` customer ON (customer.customer_id = cc.customer_id)  INNER JOIN m_country mc ON (mc.country_id = cc.country_id)  WHERE `Is_Multinaional` = 'Yes'  AND cc.`Transaction_Date` = ( "
						+" SELECT MAX(`Transaction_Date`) FROM customer_countries ccc  WHERE ccc.`Transaction_Date` <= CURRENT_DATE() AND cc.`Customer_Id`=ccc.`Customer_Id` )   AND cc.`Customer_Countries_Sequence_Id` = "
						+" (SELECT MAX(`Customer_Countries_Sequence_Id`) FROM customer_countries ccc  WHERE ccc.`Transaction_Date` = cc.Transaction_Date AND cc.`Customer_Id`=ccc.`Customer_Id`  "
						+" ORDER BY Customer_Countries_Sequence_Id DESC )   AND cc.`Customer_Id` = "+customerID
						+" AND customer.`Transaction_Date` = (SELECT MAX(`Transaction_Date`)   FROM `customer_details_info` info WHERE info.`Transaction_Date` <= CURRENT_DATE()   AND info.`Customer_Id` = customer.`Customer_Id`) " 
						+" AND customer.`Customer_Sequence_Id`=(  SELECT MAX(`Customer_Sequence_Id`) FROM `customer_details_info` info   WHERE customer.`Transaction_Date` = info.`Transaction_Date` AND info.`Customer_Id` = customer.`Customer_Id` ) "
						+" GROUP BY cc.`Country_Id` order by mc.Country_Name";
			List countryListTemp = session.createSQLQuery(query).list();						
			if(countryListTemp == null ||  countryListTemp.size() <= 0)
				countryListTemp = session.createSQLQuery("SELECT  DISTINCT country.`Country_Id`,country.`Country_Name` ,`Is_Multinaional`,customer.customer_id  FROM `customer_details_info` customer INNER JOIN m_country country ON (customer.country_id = country.country_Id )     WHERE customer.`Transaction_Date` = (SELECT MAX(`Transaction_Date`)   FROM `customer_details_info` info   WHERE info.`Transaction_Date` <= CURRENT_DATE()   AND info.`Customer_Id` = customer.`Customer_Id`)   AND customer.`Customer_Sequence_Id`=(  SELECT MAX(`Customer_Sequence_Id`) FROM `customer_details_info` info   WHERE customer.`Transaction_Date` = info.`Transaction_Date` AND info.`Customer_Id` = customer.`Customer_Id` )  AND `Is_Multinaional` = 'No' AND  customer.customer_id ="+customerID).list(); 
			for (Object countryObject  : countryListTemp) {
				Object[] obj = (Object[]) countryObject;
				CountryVo countryVo = new CountryVo();
				countryVo.setCountryId((Integer)obj[0]);
				countryVo.setCountryName(obj[1]+"");
				countriesList.add(countryVo);
			}			
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}

		return countriesList;

	}
	
	
	

	@Override
	public List<SimpleObject> getTransactionListForCustomerAddress(Integer customerId, Integer addressUniqueId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List countryListTemp = session.createSQLQuery("  SELECT `Address_Id` AS id ,CONCAT(DATE_FORMAT(`Transaction_Date`,'%d/%m/%Y'),' - ',`Address_Sequence_Id`) AS cname  FROM `customer_address` addr INNER JOIN `m_address_type` adrtype ON (addr.`Address_Type_Id`=adrtype.`Address_Type_Id`) WHERE addr.`Customer_Id` ="+customerId+" and Address_Unique_Id="+addressUniqueId).list();
			for (Object countryObject  : countryListTemp) {
				Object[] obj = (Object[]) countryObject;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)obj[0]);
				object.setName(obj[1]+"");
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}

		return transactionList;

	}
	
	@Override
	public boolean validateCustomerAddress(Integer customerId, Integer addressTypeId){
		log.info("Entered in validateCustomerAddress  ::  customerId = "+" , addressTypeId = ");
		Session session = sessionFactory.getCurrentSession();
		boolean returnFlag = true;
		try{
			List addressList = session.createQuery(" FROM CustomerAddress WHERE customerDetails = "+customerId +" AND MAddressType = '"+addressTypeId+"' ").list();
			if(addressList != null && addressList.size() > 0){
				returnFlag = false;
			}else{
				returnFlag = true;
			}
		}catch(Exception e){
			returnFlag = false;
			log.error("Exception Occurred...",e);
			log.info("Exiting from validateCustomerAddress with exception :: returnFlag = "+returnFlag);
		}
		log.info("Exiting from validateCustomerAddress :: returnFlag = "+returnFlag);
		return returnFlag;
	}

}
