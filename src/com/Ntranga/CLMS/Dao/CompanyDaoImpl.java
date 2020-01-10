package com.Ntranga.CLMS.Dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.AddressVo;
import com.Ntranga.CLMS.vo.CompanyDetailsInfoVo;
import com.Ntranga.CLMS.vo.CompanyDetailsVo;
import com.Ntranga.CLMS.vo.CompanyProfileVo;
import com.Ntranga.CLMS.vo.ContactVo;
import com.Ntranga.CLMS.vo.DivisionVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WorkorderDetailInfoVo;
import com.Ntranga.CLMS.vo.WorkorderManpowerDetailsVo;
import com.Ntranga.CLMS.vo.WorkorderResponsibilitiesVo;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyAddress;
import com.Ntranga.core.CLMS.entities.CompanyContact;
import com.Ntranga.core.CLMS.entities.CompanyCurrency;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CompanyDetailsInfo;
import com.Ntranga.core.CLMS.entities.CompanyLanguages;
import com.Ntranga.core.CLMS.entities.CompanyProfile;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.DepartmentDetails;
import com.Ntranga.core.CLMS.entities.DivisionDetails;
import com.Ntranga.core.CLMS.entities.DivisionDetailsInfo;
import com.Ntranga.core.CLMS.entities.LocationDetails;
import com.Ntranga.core.CLMS.entities.MCountry;
import com.Ntranga.core.CLMS.entities.PlantDetails;
import com.Ntranga.core.CLMS.entities.WorkorderDetailInfo;
import com.Ntranga.core.CLMS.entities.WorkorderManpowerDetails;
import com.Ntranga.core.CLMS.entities.WorkorderResponsibilities;
import com.Ntranga.core.CLMS.entities.WorkrorderDetail;

import common.Logger;

@SuppressWarnings({"rawtypes","unchecked"})
@Transactional
@Repository("companyDao")
public class CompanyDaoImpl implements CompanyDao  {

	private static Logger log = Logger.getLogger(CompanyDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;


	/****************************** Company Contact start*********************************/
	
	/*
	 * This method will be used to get uptodate Company address records list based on given customerId and companyId
	 */
	@Override
	public List<SimpleObject> getCompanyCurrentAddressRecordByCompanyId(Integer customerId, Integer companyId) {
		log.info("Entered in getCompanyCurrentAddressRecordByCompanyId   ::   customerId = "+customerId+" , CompanyId = "+companyId);
		
		List<SimpleObject> objects = new ArrayList<SimpleObject>();
		try{
			Query query = sessionFactory.getCurrentSession().createSQLQuery(
									"SELECT Address_Id, Address_Type_Name, Address_1, City, State_Name, Country_Name, Pincode "
									+ " FROM company_address addr "
									//+ " INNER JOIN `m_city` v  ON ( v.City_Id=addr.`City` )  "
									+ " INNER JOIN `m_state` s  ON ( s.State_Id=addr.`State` ) "
									+ " INNER JOIN `m_country` c  ON ( c.Country_Id=addr.`Country` ) "
									+ " INNER JOIN `m_address_type` mat ON (mat.`Address_Type_Id` = addr.`Address_Type_Id` )"
									+ " WHERE "
											+ " CONCAT(DATE_FORMAT(addr.Transaction_Date, '%Y%m%d'), addr.Company_Address_Sequence_Id) =  "
											+ "	( "
											+ "	SELECT MAX(CONCAT(DATE_FORMAT(addr1.Transaction_Date, '%Y%m%d'), addr1.Company_Address_Sequence_Id)) "
											+ "	FROM company_address addr1 "
											+ "	WHERE addr.Customer_Id = addr1.Customer_Id AND addr.Company_Id = addr1.Company_Id AND addr.Address_Id = addr1.Address_Id AND addr1.Transaction_Date <= CURRENT_DATE() "
											+ "	) "
									+ " AND Customer_Id = "+customerId
									+ " AND Company_Id = "+companyId
											//+ " AND Transaction_Date = (SELECT MAX(transaction_date) FROM company_address b WHERE addr.Address_Id = b.Address_Id AND addr.Customer_Id = b.Customer_Id AND  addr.Company_Id = b.Company_Id AND addr.Transaction_Date <= b.Transaction_Date)"
											/*+ " AND `Address_Id` = (SELECT MAX(`Address_Id`) FROM company_address c WHERE addr.Address_Id = c.Address_Id AND addr.Customer_Id = c.Customer_Id AND  addr.Company_Id = c.Company_Id  AND addr.Transaction_Date <= c.Transaction_Date ) "
											+ " AND Customer_Id = "+customerId
											+ " AND Company_Id = "+companyId*/
									+"  GROUP BY Address_Id  ORDER BY Address_Type_Name");

			log.debug("Query  ::  "+query.getQueryString());
			List CustomerAddressList = query.list();
			for (Object customer : CustomerAddressList) {
				Object[] customerArray = (Object[]) customer;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)customerArray[0]);
				object.setName(customerArray[1]+"-"+customerArray[2]+"-"+customerArray[3]+"-"+customerArray[4]+"-"+customerArray[5]+"-"+customerArray[6]+".");			
				objects.add(object);
			}		
		} catch (Exception e) {
			log.error("Error Occured...  ", e);
			log.info("Exiting from getCompanyCurrentAddressRecordByCompanyId ::  "+objects);
		}
		log.info("Exiting from getCompanyCurrentAddressRecordByCompanyId ::  "+objects);
		return objects;
	}
	
	/*
	 * This method will be used to save the company contact record
	 */
	@Override
	public Integer saveCompanyContact(CompanyContact companyContact) {
		log.info("Entered in  saveCompanyContact()  ::   "+companyContact);
		Session session = this.sessionFactory.openSession();//getCurrentSession();
			
		Integer contactId = 0;
		BigInteger contactSequenceId = new BigInteger("0");
		BigInteger uniqueId = new BigInteger("0");
		try {
			session.beginTransaction();	
			if(companyContact != null && companyContact.getCompanyContactId() != null 
									  && companyContact.getCompanyContactId() > 0){
				CompanyContact contact = (CompanyContact) sessionFactory.getCurrentSession().load(CompanyContact.class, companyContact.getCompanyContactId());
				contact.setContactName(companyContact.getContactName());
				contact.setMobileNumber(companyContact.getMobileNumber());
				contact.setBusinessPhoneNumber(companyContact.getBusinessPhoneNumber());
				contact.setExtensionNumber(companyContact.getExtensionNumber());
				contact.setEmailId(companyContact.getEmailId());
				contact.setIsActive(companyContact.getIsActive());
				contact.setCompanyAddressId(companyContact.getCompanyAddressId());
				contact.setCompanyContactUniqueId(companyContact.getCompanyContactUniqueId());
				contact.setTransactionDate(companyContact.getTransactionDate());
				contact.setModifiedBy(companyContact.getModifiedBy());
				contact.setModifiedDate(new Date());
				log.debug("Company contact data before Update  ::  "+contact);
				sessionFactory.getCurrentSession().update(contact);
				contactId = companyContact.getCompanyContactId();
			}else{						
				if(companyContact != null &&  companyContact.getCompanyContactUniqueId() > 0){	
					contactSequenceId  = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Company_Contact_Sequence_Id),0) FROM `company_contact` contact  WHERE  contact.Company_Contact_Unique_Id = "+companyContact.getCompanyContactUniqueId() +" AND contact.`Transaction_Date` = '"+DateHelper.convertDateToSQLString(companyContact.getTransactionDate())+"' AND Customer_Id='"+companyContact.getCustomerDetails().getCustomerId()+"' AND Company_Id='"+companyContact.getCompanyDetails().getCompanyId()+"'").list().get(0);
				}else {
					uniqueId = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Company_Contact_Unique_Id),0) as id FROM company_contact").list().get(0);
					companyContact.setCompanyContactUniqueId(uniqueId.intValue()+1 );	
				}		
							
				companyContact.setCompanyContactSequenceId(contactSequenceId.intValue()+1);	
				log.debug("Company contact data before Save   ::   "+companyContact.toString());
				contactId = (Integer) session.save(companyContact);					
			}
			
			if(!(session.getTransaction().wasCommitted())){
				 session.getTransaction().commit();
			 }
		 }catch (Exception e) {
			 log.error("Error occured ... ",e);
			 if(!(session.getTransaction().wasRolledBack())){
				 session.getTransaction().rollback();
			 }
			 log.info("Exiting from saveCompanyContact()  ::   "+contactId);
		 }
		 log.info("Exiting from saveCompanyContact()  ::   "+contactId);
		 return contactId;
	}
	
	
		
	/*
	 *This method will be used to get all the company contact records based on given customerId and company Id
	 */

	@Override
	public List<ContactVo> getCompanyContactsList(Integer customerId, Integer companyId) {
		log.info("Entered in  getCompanyContactsList()  ::   customerId = "+customerId+" , companyId = "+companyId);
		Session session = sessionFactory.getCurrentSession();
		List contactsList = null;
		List<ContactVo> returnList = new ArrayList<ContactVo>();
		ContactVo contact = new ContactVo();
		
		String hqlQuery = "SELECT contact.Customer_Id, contact.Company_Contact_Id,  contact.Company_Address_Id, "
				+ " contact.Contact_Type_Id, contact.Contact_Name, contact.Contact_Name_Other_Language,"
				+ " contact.Mobile_Number, contact.Business_Phone_Number, contact.Extension_Number, "
				+ " contact.Email_Id, contact.Transaction_Date, contact.Is_Active,  "
				+ " address.Address_1, address.City AS City, state.State_Name AS State,  country.Country_Name AS Country, maddress.Address_Type_Name,"
				+ " mcontact.Contact_Type_Name , contact.Company_Contact_Unique_Id"
				+ " FROM company_contact AS contact "
				+ " LEFT JOIN m_contact_type mcontact ON contact.Contact_Type_Id = mcontact.Contact_Type_Id "
				+ " LEFT JOIN company_address AS address  ON contact.Customer_Id = address.Customer_Id  AND contact.Company_Address_Id = address.Address_Id "
				+ " LEFT JOIN m_address_type AS maddress  ON address.Address_type_Id = maddress.Address_type_Id "
				//+ " LEFT JOIN m_city AS city ON city.City_Id = address.city"
				+ " LEFT JOIN m_state AS state ON state.State_Id = address.State "
				+ " LEFT JOIN m_country AS country ON country.Country_Id = address.Country "
				+ " WHERE contact.Customer_Id = "+customerId
				+ " AND contact.Company_Id = "+companyId
				+ " AND contact.`Transaction_Date` <= CURRENT_DATE() " 
				+ " AND CONCAT(DATE_FORMAT(contact.Transaction_Date, '%Y%m%d'), contact.Company_Contact_Sequence_Id) =  "
						+ "	( "
						+ "	SELECT MAX(CONCAT(DATE_FORMAT(contact1.Transaction_Date, '%Y%m%d'), contact1.Company_Contact_Sequence_Id)) "
						+ "	FROM company_contact AS contact1 "
						+ "	WHERE contact.Customer_Id = contact1.Customer_Id AND contact.Company_Id = contact1.Company_Id AND contact.Company_Contact_Unique_Id = contact1.Company_Contact_Unique_Id AND contact1.Transaction_Date <= CURRENT_DATE() "
						+ "	) "
				+ " GROUP BY contact.Company_Contact_Unique_Id Order By mcontact.Contact_Type_Id asc";
		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			contactsList = query.list();

			for (Object customer : contactsList) {
				Object[] obj = (Object[]) customer;
				contact = new ContactVo();
				//contact.setCompanyId(companyId);
				//contact.setCustomerId((Integer)obj[0]);
				contact.setContactId((Integer)obj[1]);
				contact.setAddressId((Integer)obj[2]);
				contact.setContactTypeId((Integer)obj[3]);
				contact.setContactName((String)obj[4]);
				//contact.setContactNameOtherLanguage((String)obj[5]);
				contact.setMobileNumber((String)obj[6]);
				contact.setBusinessPhoneNumber((String)obj[7]);
				contact.setExtensionNumber((String)obj[8]);
				contact.setEmailId((String)obj[9]);
				contact.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[10]));
				contact.setIsActive(obj[11]+"");
				contact.setAddress((String)(obj[16]+" -"+obj[12]+", "+obj[13]+", "+obj[14]+", "+obj[15]));
				contact.setContactTypeName((String)obj[17]);
				contact.setContactUniqueId((Integer) obj[18]);
				
				returnList.add(contact);
			}	
			
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Entered in  getCompanyContactsList()  ::   "+returnList);
		}
		session.flush();
		log.info("Entered in  getCompanyContactsList()  ::   "+returnList);
		return returnList;
	}
	
	/*
	 * This method will be used to get contact record based on contact Id
	 */
	@Override
	public ContactVo getCompanyContactRecordById(Integer contactId) {
		log.info("Entered in  getCompanyContactRecordById()  ::   contactId = "+contactId);
		Session session = sessionFactory.getCurrentSession();
		ContactVo contact = new ContactVo();
		try {
			String hqlQuery = "SELECT contact.Customer_Id, contact.Company_Contact_Id,  contact.Company_Address_Id, "
							+ " contact.Contact_Type_Id, contact.Contact_Name, contact.Contact_Name_Other_Language,"
							+ " contact.Mobile_Number, contact.Business_Phone_Number, contact.Extension_Number, "
							+ " contact.Email_Id, contact.Transaction_Date, contact.Is_Active,"
							+ " address.Address_1, city.City_Name AS City, state.State_Name AS State,  country.Country_Name AS Country, "
							+ " mcontact.Contact_Type_Name , maddress.Address_Type_Name, contact.Company_Contact_Unique_Id,details.company_id "
							+ " FROM company_contact AS contact "
							+ " LEFT JOIN company_details AS details  ON contact.Customer_Id = details.Customer_Id AND contact.Company_Id = details.Company_Id "
							+ " LEFT JOIN m_contact_type AS mcontact ON  contact.Contact_Type_Id = mContact.Contact_Type_Id "
							+ " LEFT JOIN company_address AS address  ON contact.Company_Id = address.Company_Id  AND contact.Company_Address_Id = address.Address_Id "
							+ " LEFT JOIN m_address_type AS maddress  ON address.Address_Type_Id = maddress.Address_Type_Id "
							+ " LEFT JOIN m_city AS city ON city.City_Id = address.city"
							+ " LEFT JOIN m_state AS state ON state.State_Id = address.State "
							+ " LEFT JOIN m_country AS country ON country.Country_Id = address.Country "
							+ " WHERE  contact.Company_Contact_Id = "+contactId;
			
			Query query = session.createSQLQuery(hqlQuery);
			log.debug("Query ::  "+query.getQueryString());
			
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
				contact.setCompanyId((Integer)obj[19]);
			}
		} catch (Exception e) {
//			log.error("Error Occured ",e);
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getCompanyContactRecordById()  ::   "+contact);
		}
		
		log.info("Exiting from  getCompanyContactRecordById()  ::  "+contact);
		return contact;
	}
	
	/*
	 * This method will be used to get transaction dates list for given customerId, companyId and contactuniqueId
	 */
	@Override
	public List<SimpleObject> getTransactionListForContact(Integer customerId, Integer companyId, Integer contactUniqueId) {
		log.info("Entered in getTransactionListForContact()  ::   customerId = "+customerId+" , companyId = "+companyId+" , contactUniqueId = "+contactUniqueId);
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List contactList = session.createSQLQuery("SELECT Company_Contact_Id AS id ,CONCAT(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),' - ',Company_Contact_Sequence_Id) AS cname FROM  company_contact contact INNER JOIN m_contact_type mcontact ON (contact.Contact_Type_Id = mcontact.Contact_Type_Id) WHERE contact.Customer_Id = "+customerId+" AND Company_Id = "+companyId+" AND Company_Contact_Unique_Id = "+contactUniqueId+"  order by contact.Transaction_Date, contact.Company_Contact_Sequence_Id ").list();
			for (Object transDates  : contactList) {
				Object[] transaction = (Object[]) transDates;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)transaction[0]);
				object.setName(transaction[1]+"");
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getTransactionListForContact()  ::   "+transactionList);
		}
		
		log.info("Exiting from getTransactionListForContact()  ::   "+transactionList);
		return transactionList;

	}

	/***************************  Company Contact End ***************************/

	
	/***************************  Company Address Start ***************************/
	
	/*
	 * This method will be used to save company address
	 */
	@Override
	public Integer saveCompanyAddress(CompanyAddress companyAddress) {
		log.info("Entered in saveCompanyAddress()  ::   "+companyAddress.getAddressId());
		
		Integer companyAdressId = 0;
		Session session = this.sessionFactory.openSession();
		BigInteger adressSequenceId = new BigInteger("0");
		BigInteger uniqueId = new BigInteger("0");
		try {		
			session.beginTransaction();	
			if(companyAddress != null && companyAddress.getCompanyAddressId() != null && companyAddress.getCompanyAddressId() > 0){
				CompanyAddress address = (CompanyAddress) sessionFactory.getCurrentSession().load(CompanyAddress.class, companyAddress.getCompanyAddressId());
				address.setAddress1(companyAddress.getAddress1());
				address.setAddress2(companyAddress.getAddress2());
				address.setAddress3(companyAddress.getAddress3());
				address.setCountry(companyAddress.getCountry());
				address.setState(companyAddress.getState());
				address.setCity(companyAddress.getCity());
				address.setPincode(companyAddress.getPincode());
				address.setIsActive(companyAddress.getIsActive());
				address.setTransactionDate(companyAddress.getTransactionDate());
				address.setModifiedBy(companyAddress.getModifiedBy());
				address.setModifiedDate(new Date());
				log.debug("Company Address before Update ::  "+address);
				sessionFactory.getCurrentSession().update(address);
				companyAdressId = companyAddress.getCompanyAddressId();
			}else{		
				
				if(companyAddress != null && companyAddress.getAddressId() > 0){	
					adressSequenceId  = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Company_Address_Sequence_Id),0) FROM `company_address` addr WHERE  addr.Address_Id = "+companyAddress.getAddressId() +" AND addr.`Transaction_Date` = '"+DateHelper.convertDateToSQLString(companyAddress.getTransactionDate())+"' and Customer_Id='"+companyAddress.getCustomerDetails().getCustomerId()+"' And Company_Id = '"+companyAddress.getCompanyDetails().getCompanyId()+"'").list().get(0);
				}else {
					uniqueId = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Address_Id),0) as id FROM company_address").list().get(0);
					companyAddress.setAddressId( uniqueId.intValue()+1 );
				}		
				
				companyAddress.setCompanyAddressSequenceId(adressSequenceId.intValue()+1);	
				log.debug("Company Address before save ::  "+companyAddress);
				companyAdressId = (Integer) session.save(companyAddress);					
			}
			if(!(session.getTransaction().wasCommitted())){
				 session.getTransaction().commit();
				 session.flush();
			 }
		 }catch (Exception e) {
			 if(!(session.getTransaction().wasRolledBack())){
				 session.getTransaction().rollback();
			 }
			 log.error("Error Occurred...   ", e);
			 log.info("Exiting from saveCompanyAddress()  ::  addressId =  "+companyAdressId);
		}
		log.info("Exiting from saveCompanyAddress()  ::  addressId =  "+companyAdressId);
		return companyAdressId;

	}

	/*
	 * This method will be used to get company address record by given address Id
	 */
	@Override
	public AddressVo getCompanyAddressRecordById(Integer companyAddressId) {
		log.info("Entered in getCompanyAddressRecordById()  ::  companyAddressId = "+companyAddressId);
		Session session = sessionFactory.getCurrentSession();
		AddressVo addressVo = new AddressVo();
		try {
			Query query = session.createSQLQuery(
					"SELECT Company_Address_Id, Customer_Id, addr.Address_Type_Id, Address_1, Address_2, Address_3, "
							+ "	Country_Name, State_Name, City, Pincode, Transaction_Date, addr.Country, addr.State, mat.Address_Type_Name, Is_Active, Address_Id, Company_Id  "
							+ " FROM `company_address` addr INNER JOIN `m_country` mc  ON (mc.`Country_Id` = addr.`Country`) INNER JOIN `m_state` ms  ON (ms.`State_Id`=addr.`State`) "
							//+ " INNER JOIN `m_city` ON (addr.`City`=`City_Id`)"
							+ " INNER JOIN `m_address_type` mat ON (mat.`Address_Type_Id` = addr.`Address_Type_Id` ) WHERE `Company_Address_Id` = '" + companyAddressId
							+ "'");
			log.debug("Query  ::  "+query.getQueryString());
			List companyAddressList = query.list();
			for (Object customer : companyAddressList) {
				Object[] customerArray = (Object[]) customer;				
				addressVo.setAddressId((Integer) customerArray[0]);
				addressVo.setCustomerDetailsId((Integer) customerArray[1]);
				addressVo.setCompanyDetailsId((Integer) customerArray[16]);
				addressVo.setAddressTypeId((Integer) customerArray[2]);
				addressVo.setAddress1(customerArray[3] + "");
				addressVo.setAddress2(customerArray[4]  == null ? "" : customerArray[4] + "");
				addressVo.setAddress3(customerArray[5]  == null ? "" : customerArray[5] + "");
				addressVo.setCountry(customerArray[6] + "");
				addressVo.setState(customerArray[7] + "");
				addressVo.setCity(customerArray[8] + "");
				addressVo.setPincode(customerArray[9] + "");
				addressVo.setCountryId((Integer)customerArray[11]);
				addressVo.setStateId((Integer)customerArray[12]);
				//addressVo.setCityId((Integer)customerArray[13]);
				addressVo.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)customerArray[10]));
				addressVo.setAddressType(customerArray[13]+"" );
				addressVo.setIsActive(customerArray[14]+"");	
				addressVo.setAddressUniqueId( (Integer)customerArray[15]);
			}			
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getCompanyAddressRecordById()  ::  "+addressVo);
		}
		log.info("Exiting from getCompanyAddressRecordById()  ::   "+addressVo);
		return addressVo;
	}

	/*
	 * This method will be used to get list of company address records based on given customerId and companyId
	 */
	@Override
	public List<AddressVo> getCompanyAddressList(Integer customerId, Integer companyId) {
		log.info("Entered in getCompanyAddressList()  ::    customerId =  "+customerId+" , companyId = "+companyId);
		Session session = sessionFactory.getCurrentSession();
		List<AddressVo> cmpAddressVo = new ArrayList<>();
		try {
			Query query = session.createSQLQuery("SELECT `Company_Address_Id`,`Customer_Id`,addr.`Address_Type_Id`,`Address_1`,`Address_2`,`Address_3`,`Country_Name`,`State_Name`,addr.`City`,`Pincode`, `Transaction_Date`, addr.`Country`,addr.`State`, `Address_Type_Name`,Company_Address_Sequence_Id, Address_Id "
											+ " FROM company_address addr "
											//+ " INNER JOIN `m_city` v  ON ( v.City_Id=addr.`City` ) "
											+ " INNER JOIN `m_state` s  ON ( s.State_Id=addr.`State` ) "
											+ " INNER JOIN `m_country` c  ON ( c.Country_Id=addr.`Country` ) "
											+ " INNER JOIN `m_address_type` mat ON (mat.`Address_Type_Id` = addr.`Address_Type_Id` )"
											+ " WHERE  "
											+ " CONCAT(DATE_FORMAT(addr.Transaction_Date, '%Y%m%d'), addr.Company_Address_Sequence_Id) =  "
													+ "	( "
													+ "	SELECT MAX(CONCAT(DATE_FORMAT(addr1.Transaction_Date, '%Y%m%d'), addr1.Company_Address_Sequence_Id)) "
													+ "	FROM company_address addr1 "
													+ "	WHERE addr.Customer_Id = addr1.Customer_Id AND addr.Company_Id = addr1.Company_Id AND addr.Address_Id = addr1.Address_Id AND addr1.Transaction_Date <= CURRENT_DATE() "
													+ "	) "
											+ " AND Customer_Id="+customerId +" AND Company_Id = "+companyId
											+ " ORDER BY mat.Address_Type_Name asc");
			log.debug("Query  ::   "+query.getQueryString());
			List companyAddressList = query.list();
			for (Object company : companyAddressList) {
				Object[] customerArray = (Object[]) company;
				AddressVo addressVo = new AddressVo();
				addressVo.setAddressId((Integer) customerArray[0]);
				addressVo.setCustomerDetailsId((Integer) customerArray[1]);
				addressVo.setAddressTypeId((Integer) customerArray[2]);
				addressVo.setAddress1(customerArray[3] + "");
				addressVo.setAddress2(customerArray[4] == null ? "" : customerArray[4] + "");
				addressVo.setAddress3(customerArray[5] == null ? "" : customerArray[5] + "");
				addressVo.setCountry(customerArray[6] + "");
				addressVo.setState(customerArray[7] + "");
				addressVo.setCity(customerArray[8] + "");
				addressVo.setPincode(customerArray[9] + "");
				addressVo.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)customerArray[10]));
				addressVo.setCountryId((Integer)customerArray[11] );
				addressVo.setStateId( (Integer)customerArray[12] );
				//addressVo.setCityId( (Integer)customerArray[13]);
				addressVo.setAddressType(customerArray[13]+"" );
				addressVo.setAddressUniqueId((Integer)customerArray[15]);
				
				cmpAddressVo.add(addressVo);
			}
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getCompanyAddressList()  ::  "+cmpAddressVo);
		}
		
		log.info("Exiting from getCompanyAddressList()  ::  "+cmpAddressVo);
		return cmpAddressVo;
	}

	/*
	 * This method will be used to get Transaction dates list for address based on given customerId, companyId and addressUniqueId
	 */
	@Override
	public List<SimpleObject> getTransactionListForAddress(Integer customerId,  Integer companyId,  Integer addressUniqueId) {
		log.info("Entered in getTransactionListForAddress()  ::    customerId =  "+customerId+" , companyId = "+companyId+" , addressUniqueId = "+addressUniqueId);

		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List countryListTemp = session.createSQLQuery("  SELECT `Company_Address_Id` AS id ,CONCAT(DATE_FORMAT(`Transaction_Date`,'%d/%m/%Y'),' - ',`Company_Address_Sequence_Id`) AS cname  FROM `company_address` addr INNER JOIN `m_address_type` adrtype ON (addr.`Address_Type_Id`=adrtype.`Address_Type_Id`) WHERE addr.`Customer_Id` ="+customerId+" AND Company_Id = "+companyId+" AND Address_Id="+addressUniqueId+"  ORDER BY addr.Transaction_Date,addr.Company_Address_Sequence_Id ").list();
			for (Object countryObject  : countryListTemp) {
				Object[] obj = (Object[]) countryObject;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)obj[0]);
				object.setName(obj[1]+"");
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getTransactionListForAddress()  ::  "+transactionList);
		}
		log.info("Exiting from getTransactionListForAddress()  ::  "+transactionList);
		return transactionList;

	}
  
	
	/***************************  Company Address End  ***************************/

	/* Company Details Start */
	
	@Override
	public Integer saveCompanyDetails(CompanyDetailsInfoVo companyDetailsInfoVo) {
		String companyJsonString =null;
		System.out.println("companyJsonString::"+companyJsonString);
		Session session = sessionFactory.getCurrentSession();
		Integer companyInfoId = 0;
		Integer companyId = 0;
		BigInteger sequenceId = new BigInteger("0");	
		CompanyDetails companyDetails = new CompanyDetails();
		CompanyDetailsInfo companyDetailsInfo = new CompanyDetailsInfo();
		try{									
			if(companyDetailsInfoVo != null && companyDetailsInfoVo.getCompanyDetailsId() != null && companyDetailsInfoVo.getCompanyDetailsId() > 0){
				companyDetails = (CompanyDetails) session.load(CompanyDetails.class, companyDetailsInfoVo.getCompanyDetailsId());
				companyDetails.setCompanyCode(companyDetailsInfoVo.getCompanyCode().toUpperCase());
				companyDetails.setModifiedBy(companyDetailsInfoVo.getModifiedBy());
				companyDetails.setModifiedDate(new Date());
				session.update(companyDetails);
				companyId  = companyDetailsInfoVo.getCompanyDetailsId();
			}else{
				companyDetails = new CompanyDetails();
				companyDetails.setCustomerDetails(new CustomerDetails(companyDetailsInfoVo.getCustomerDetailsId()));
				companyDetails.setCompanyCode(companyDetailsInfoVo.getCompanyCode().toUpperCase());
				companyDetails.setIsActive("Y");
				companyDetails.setCreatedBy(companyDetailsInfoVo.getCreatedBy());
				companyDetails.setCreatedDate(new Date());
				companyDetails.setModifiedBy(companyDetailsInfoVo.getModifiedBy());
				companyDetails.setModifiedDate(new Date());
				companyId = (Integer) session.save(companyDetails);
			}
			if(companyDetailsInfoVo != null && companyDetailsInfoVo.getCompanyInfoId() != null && companyDetailsInfoVo.getCompanyInfoId() > 0){
				companyDetailsInfo = (CompanyDetailsInfo) sessionFactory.getCurrentSession().load(CompanyDetailsInfo.class, companyDetailsInfoVo.getCompanyInfoId());
				companyDetailsInfo.setCompanyDetails(new CompanyDetails(companyDetailsInfoVo.getCompanyDetailsId()));
				companyDetailsInfo.setCustomerDetails(new CustomerDetails(companyDetailsInfoVo.getCustomerDetailsId()));
				companyDetailsInfo.setCompanyName(companyDetailsInfoVo.getCompanyName());
				companyDetailsInfo.setCountryId(companyDetailsInfoVo.getCountryId());							
				companyDetailsInfo.setTransactionDate(companyDetailsInfoVo.getTransactionDate());
				companyDetailsInfo.setIsActive(companyDetailsInfoVo.getIsActive());
				companyDetailsInfo.setSectorId(companyDetailsInfoVo.getSectorId());	
				companyDetailsInfo.setLegacyId(companyDetailsInfoVo.getLegacyId());			
				companyDetailsInfo.setIndustryId(companyDetailsInfoVo.getIndustryId());
				companyDetailsInfo.setSubIndustryId(companyDetailsInfoVo.getSubIndustryId());	
				companyDetailsInfo.setCorporateIdentityNumber(companyDetailsInfoVo.getCorporateIdentityNumber());
				companyDetailsInfo.setRegistrationActId(companyDetailsInfoVo.getRegistrationActId());
				companyDetailsInfo.setRocRegistrationNumber(companyDetailsInfoVo.getRocRegistrationNumber());
				companyDetailsInfo.setRegistrationDate(companyDetailsInfoVo.getRegistrationDate());
				companyDetailsInfo.setServiceTaxRegistrationNumber(companyDetailsInfoVo.getServiceTaxRegistrationNumber());
				companyDetailsInfo.setTinNumber(companyDetailsInfoVo.getTinNumber());
				companyDetailsInfo.setPanNumber(companyDetailsInfoVo.getPanNumber() != null ? companyDetailsInfoVo.getPanNumber().toUpperCase() : null);
				companyDetailsInfo.setPanRegistrationDate(companyDetailsInfoVo.getPanRegistrationDate());
				companyDetailsInfo.setPfNumber(companyDetailsInfoVo.getPfNumber());			
				companyDetailsInfo.setPfRegistrationDate(companyDetailsInfoVo.getPfRegistrationDate());
				companyDetailsInfo.setPfStartDate(companyDetailsInfoVo.getPfStartDate());
				companyDetailsInfo.setPfTypeId(companyDetailsInfoVo.getPfTypeId());
				companyDetailsInfo.setEsiNumber(companyDetailsInfoVo.getEsiNumber());			
				companyDetailsInfo.setEsiRegistrationDate(companyDetailsInfoVo.getEsiRegistrationDate());
				companyDetailsInfo.setEsiStartDate(companyDetailsInfoVo.getEsiStartDate());				
				companyDetailsInfo.setModifiedBy(companyDetailsInfoVo.getModifiedBy());
				companyDetailsInfo.setModifiedDate(new Date());
				session.update(companyDetailsInfo);
				companyInfoId = companyDetailsInfo.getCompanyInfoId();
			}else{					
				
				companyDetailsInfo.setCompanyDetails(companyDetails);
				companyDetailsInfo.setCustomerDetails(new CustomerDetails(companyDetailsInfoVo.getCustomerDetailsId()));
				companyDetailsInfo.setCompanyName(companyDetailsInfoVo.getCompanyName());
				companyDetailsInfo.setCountryId(companyDetailsInfoVo.getCountryId());							
				companyDetailsInfo.setTransactionDate(companyDetailsInfoVo.getTransactionDate());
				companyDetailsInfo.setIsActive(companyDetailsInfoVo.getIsActive());
				companyDetailsInfo.setSectorId(companyDetailsInfoVo.getSectorId());	
				companyDetailsInfo.setLegacyId(companyDetailsInfoVo.getLegacyId());			
				companyDetailsInfo.setIndustryId(companyDetailsInfoVo.getIndustryId());
				companyDetailsInfo.setSubIndustryId(companyDetailsInfoVo.getSubIndustryId());	
				companyDetailsInfo.setCorporateIdentityNumber(companyDetailsInfoVo.getCorporateIdentityNumber());
				companyDetailsInfo.setRegistrationActId(companyDetailsInfoVo.getRegistrationActId());
				companyDetailsInfo.setRocRegistrationNumber(companyDetailsInfoVo.getRocRegistrationNumber());
				companyDetailsInfo.setRegistrationDate(companyDetailsInfoVo.getRegistrationDate());
				companyDetailsInfo.setServiceTaxRegistrationNumber(companyDetailsInfoVo.getServiceTaxRegistrationNumber());
				companyDetailsInfo.setTinNumber(companyDetailsInfoVo.getTinNumber());
				companyDetailsInfo.setPanNumber(companyDetailsInfoVo.getPanNumber() != null ? companyDetailsInfoVo.getPanNumber().toUpperCase() : null);
				companyDetailsInfo.setPanRegistrationDate(companyDetailsInfoVo.getPanRegistrationDate());
				companyDetailsInfo.setPfNumber(companyDetailsInfoVo.getPfNumber());			
				companyDetailsInfo.setPfRegistrationDate(companyDetailsInfoVo.getPfRegistrationDate());
				companyDetailsInfo.setPfStartDate(companyDetailsInfoVo.getPfStartDate());
				companyDetailsInfo.setPfTypeId(companyDetailsInfoVo.getPfTypeId());
				companyDetailsInfo.setEsiNumber(companyDetailsInfoVo.getEsiNumber());			
				companyDetailsInfo.setEsiRegistrationDate(companyDetailsInfoVo.getEsiRegistrationDate());
				companyDetailsInfo.setEsiStartDate(companyDetailsInfoVo.getEsiStartDate());
				companyDetailsInfo.setCreatedBy(companyDetailsInfoVo.getCreatedBy());
				companyDetailsInfo.setCreatedDate(new Date());
				companyDetailsInfo.setModifiedBy(companyDetailsInfoVo.getModifiedBy());
				companyDetailsInfo.setModifiedDate(new Date());
				//companyDetailsInfo.setUniqueId(companyDetailsInfoVo.getUniqueId());
				//System.out.println(sequenceId+""+companyDetailsInfo.getUniqueId());
				if(companyId != null && companyId > 0)
					// sequenceId  = (BigInteger) session.createSQLQuery("SELECT COALESCE(MAX(`Company_Sequence_Id`),0) AS id FROM `company_details_info`  WHERE  Unique_Id = "+companyDetailsInfoVo.getUniqueId() +" AND `Transaction_Date` = '"+DateHelper.convertDateToSQLString(companyDetailsInfoVo.getTransactionDate())+"' and Customer_Id='"+companyDetailsInfoVo.getCustomerDetailsId()+"'").list().get(0);
					sequenceId  = (BigInteger) session.createSQLQuery("SELECT COALESCE(MAX(`Company_Sequence_Id`),0) AS id FROM `company_details_info`  WHERE  Company_Id = "+companyId+" AND `Transaction_Date` = '"+DateHelper.convertDateToSQLString(companyDetailsInfoVo.getTransactionDate())+"' and Customer_Id='"+companyDetailsInfoVo.getCustomerDetailsId()+"'").list().get(0);
					
				/*}else {					
					BigInteger uniqueId = (BigInteger) session.createSQLQuery("SELECT COALESCE(MAX(`Unique_Id`),0) AS id FROM `company_details_info`").list().get(0);
					companyDetailsInfo.setUniqueId(uniqueId.intValue()+1);					
				}	
				System.out.println(sequenceId+""+companyDetailsInfo.getUniqueId());*/
				companyDetailsInfo.setCompanySequenceId(sequenceId.intValue()+1);			
				companyInfoId = (Integer) session.save(companyDetailsInfo);
			}
			session.flush();
			
		}catch(Exception e){
			log.error("Error Occured ",e);		
		}				
		return companyInfoId;
			
	}
	
	
	// Fetching Customer Details by CustomerId

	@Override
	public List<CompanyDetailsVo> getCompanyDetailsListByCustomerId(int customerId, String countryName, String companyName, String status,Integer companyId) {
		System.out.println("Entered in getCompanyDetailsListByCustomerId  :: customerId  :  "+customerId+", companyName  :  "+companyName+", countryName  : "+countryName+", status : "+status+", companyId : "+companyId);
		Session session = sessionFactory.getCurrentSession();
		List<CompanyDetailsVo> companyDetailsVoList = new ArrayList<>();
		String query = "";
		
		System.out.println(countryName.equalsIgnoreCase("null"));
		try {
			if(companyId == 0){
					 query = "SELECT info.Customer_Id,`Company_Info_Id`,`Company_Code`,Company_Name,`Country_Name`,"
							+ " CASE cd.`Is_Active` WHEN 'Y' THEN 'Active' ELSE 'In-Active'  END AS `status`, cd.Company_Id,  "
							+ " cdi.Customer_Name, customer_code FROM company_details_info info "
							+ " LEFT JOIN `company_details` cd ON (cd.`Company_Id` = info.`Company_Id`) "
							+ " LEFT JOIN `customer_details_info` cdi ON (cdi.`Customer_Id` = info.`Customer_Id`)  "
							+ " LEFT JOIN `customer_details` cds ON (cds.`Customer_Id` = cdi.`Customer_Id`)  "
							+ " LEFT JOIN m_country country ON (country.`Country_Id` = info.`Country_Id`) "
							+ " WHERE cdi.Is_Active = 'Y' "
									+ "	AND  CONCAT(DATE_FORMAT(info.Transaction_Date, '%Y%m%d'), info.Company_Sequence_Id) = ( "
									+"	SELECT MAX(CONCAT(DATE_FORMAT(info1.Transaction_Date, '%Y%m%d'), info1.Company_Sequence_Id)) "
									+"	FROM company_details_info info1 "
									+"	WHERE info.Company_Id = info1.Company_Id AND info1.Transaction_Date <= CURRENT_DATE() "
									+ " ) AND   "      
									+ "(CONCAT(DATE_FORMAT(`cdi`.`Transaction_Date`,'%Y%m%d'),LPAD(`cdi`.`Customer_Sequence_Id`,2,'0')) = (SELECT "
									+ "MAX(CONCAT(DATE_FORMAT(`cdi1`.`Transaction_Date`,'%Y%m%d'),LPAD(`cdi1`.`Customer_Sequence_Id`,2,'0'))) "
									+ " FROM `customer_details_info` `cdi1` "
									+ " WHERE ((`cdi`.`Customer_Id` = `cdi1`.`Customer_Id`) "
									+ " AND (`cdi1`.`Transaction_Date` <= CURDATE())))) "	;		
					 
					 if((countryName != null && !countryName.equalsIgnoreCase("null")) && !(countryName.isEmpty() || countryName.equals(""))){
						query +=" AND country.Country_Name LIKE '"+countryName+"%' ";	
					 }
					 
					 if((companyName != null && !companyName.equalsIgnoreCase(null)) && !(companyName.isEmpty() || companyName.equals(""))){
						query +=" AND info.Company_Name LIKE '"+companyName+"%' ";	
					 }
					 
					 if(status != null && !status.isEmpty())
						query +=" AND info.`Is_Active` = '"+status+"' ";
					
					 if(customerId > 0)	{	
						query +=" AND info.customer_Id="+customerId ;
					 }
					 
					//query += " AND Company_Sequence_Id = (SELECT MAX(Company_Sequence_Id) FROM company_details_info child WHERE child.Transaction_Date = info.transaction_date and child.company_id = cd.company_id) and cd.isDivision = 'NO'";
					query += " GROUP BY cd.Company_Code asc" ;
					
					System.out.println(query);
					List tempList =  session.createSQLQuery(query).list();	
					for(Object y : tempList){
						Object[] objy = (Object[])y;
						CompanyDetailsVo companyDetailsVo = new CompanyDetailsVo();
						companyDetailsVo.setCustomerId((Integer)objy[0]);
						companyDetailsVo.setCompanyId((Integer)objy[1]);
						companyDetailsVo.setCompanyCode(objy[2]+"");
						companyDetailsVo.setCompanyName(objy[3]+"");				
						companyDetailsVo.setCountryName(objy[4]+"");
						companyDetailsVo.setIsActive(objy[5]+"");
						companyDetailsVo.setCmpId((Integer)objy[6]);
						companyDetailsVo.setCustomerName((String)objy[7]);
						companyDetailsVo.setCustomerCode((String)objy[8]);
						companyDetailsVoList.add(companyDetailsVo);
					}
					
					
			}else if(companyId > 0){
				
				
				query = "SELECT info.Customer_Id,`Company_Info_Id`,`Company_Code`,Company_Name,`Country_Name`,"
						+ " CASE cd.`Is_Active` WHEN 'Y' THEN 'Active' ELSE 'In-Active'  END AS `status`, cd.Company_Id ,"
						+ "cd.IsDivision,cd.Division_Company_Id, cdi.Customer_Name, cds.customer_code "
						+ " FROM company_details_info info "
						+ " LEFT JOIN `company_details` cd ON (cd.`Company_Id` = info.`Company_Id`) "
						+ " LEFT JOIN `customer_details_info` cdi ON (cdi.`Customer_Id` = info.`Customer_Id`)  "
						+ " LEFT JOIN `customer_details` cds ON (cds.`Customer_Id` = cdi.`Customer_Id`)  "
						+ " LEFT JOIN m_country country ON (country.`Country_Id` = info.`Country_Id`) "
						+ " WHERE cdi.Is_Active = 'Y' "
								+ "	AND  CONCAT(DATE_FORMAT(info.Transaction_Date, '%Y%m%d'), info.Company_Sequence_Id) = ( "
								+"	SELECT MAX(CONCAT(DATE_FORMAT(info1.Transaction_Date, '%Y%m%d'), info1.Company_Sequence_Id)) "
								+"	FROM company_details_info info1 "
								+"	WHERE info.Company_Id = info1.Company_Id AND info1.Transaction_Date <= CURRENT_DATE() "
								+ " ) AND   "      
								+ "(CONCAT(DATE_FORMAT(`cdi`.`Transaction_Date`,'%Y%m%d'),LPAD(`cdi`.`Customer_Sequence_Id`,2,'0')) = (SELECT "
								+ "MAX(CONCAT(DATE_FORMAT(`cdi1`.`Transaction_Date`,'%Y%m%d'),LPAD(`cdi1`.`Customer_Sequence_Id`,2,'0'))) "
								+ " FROM `customer_details_info` `cdi1` "
								+ " WHERE ((`cdi`.`Customer_Id` = `cdi1`.`Customer_Id`) "
								+ " AND (`cdi1`.`Transaction_Date` <= CURDATE())))) ";
				
				
				if((countryName != null || countryName.equalsIgnoreCase("null")) && !(countryName.isEmpty() || countryName.equals(""))){
					query +=" AND country.Country_Name LIKE '"+countryName+"%' ";
				}
				
				if(companyId >0 )
					query +=" AND info.Company_ID = '"+companyId+"' ";
				
				if(status != null && !status.isEmpty())
					query +=" AND info.`Is_Active` = '"+status+"' ";
				
				if(customerId > 0)		
					query +=" AND info.customer_Id="+customerId ;
				//query += " AND Company_Sequence_Id = (SELECT MAX(Company_Sequence_Id) FROM company_details_info child WHERE child.Transaction_Date = info.transaction_date and child.company_id = cd.company_id)";
				query += " GROUP BY cd.Company_Code asc" ;
				
				List tempList1 =  session.createSQLQuery(query).list();	
				System.out.println(query);
				for(Object o1 : tempList1){
					Object[] obj1 = (Object[]) o1;
					if(obj1[7] != null && obj1[7].toString().equalsIgnoreCase("Yes")){
						
					String	query1 = "SELECT info.Customer_Id,`Company_Info_Id`,`Company_Code`,Company_Name,`Country_Name`, "
								+ " CASE cd.`Is_Active` WHEN 'Y' THEN 'Active' ELSE 'In-Active'  END AS `status`, cd.Company_Id, "
								+ "  cdi.Customer_Name, cds.customer_code "
								+ " FROM company_details_info info "
								+ " LEFT JOIN `company_details` cd ON (cd.`Company_Id` = info.`Company_Id`) "
								+ " LEFT JOIN `customer_details_info` cdi ON (cdi.`Customer_Id` = info.`Customer_Id`)  "
								+ " LEFT JOIN `customer_details` cds ON (cds.`Customer_Id` = cdi.`Customer_Id`)  "
								+ " LEFT JOIN m_country country ON (country.`Country_Id` = info.`Country_Id`) "
								+ " WHERE cdi.Is_Active = 'Y' "
										+ "	AND  CONCAT(DATE_FORMAT(info.Transaction_Date, '%Y%m%d'), info.Company_Sequence_Id) = ( "
										+"	SELECT MAX(CONCAT(DATE_FORMAT(info1.Transaction_Date, '%Y%m%d'), info1.Company_Sequence_Id)) "
										+"	FROM company_details_info info1 "
										+"	WHERE info.Company_Id = info1.Company_Id AND info1.Transaction_Date <= CURRENT_DATE() "
										+ " ) AND   "      
										+ "(CONCAT(DATE_FORMAT(`cdi`.`Transaction_Date`,'%Y%m%d'),LPAD(`cdi`.`Customer_Sequence_Id`,2,'0')) = (SELECT "
										+ "MAX(CONCAT(DATE_FORMAT(`cdi1`.`Transaction_Date`,'%Y%m%d'),LPAD(`cdi1`.`Customer_Sequence_Id`,2,'0'))) "
										+ " FROM `customer_details_info` `cdi1` "
										+ " WHERE ((`cdi`.`Customer_Id` = `cdi1`.`Customer_Id`) "
										+ " AND (`cdi1`.`Transaction_Date` <= CURDATE())))) ";		
					
						if(obj1[8] != null )
							query1 +=" AND info.Company_ID = '"+obj1[8]+"'  AND info.`Is_Active` ='Y' ";
						
						if(customerId > 0)		
							query1 +=" AND info.customer_Id="+customerId ;
						//query1 += " AND Company_Sequence_Id = (SELECT MAX(Company_Sequence_Id) FROM company_details_info child WHERE child.Transaction_Date = info.transaction_date and child.company_id = cd.company_id)";
						query1 += " GROUP BY cd.Company_Code asc" ;
						System.out.println(query1);
						List tempListNew =  session.createSQLQuery(query1).list();	
						
						for(Object a : tempListNew){
							Object[] obja = (Object[]) a;
							CompanyDetailsVo companyDetailsVo = new CompanyDetailsVo();
							companyDetailsVo.setCustomerId((Integer)obja[0]);
							companyDetailsVo.setCompanyId((Integer)obja[1]);
							companyDetailsVo.setCompanyCode(obja[2]+"");
							companyDetailsVo.setCompanyName(obja[3]+"");				
							companyDetailsVo.setCountryName(obja[4]+"");
							companyDetailsVo.setIsActive(obja[5]+"");
							companyDetailsVo.setCmpId((Integer)obja[6]);
							companyDetailsVo.setCustomerName((String)obja[7]);
							companyDetailsVo.setCustomerCode((String)obja[8]);
							companyDetailsVoList.add(companyDetailsVo);
						}
						
					}else{
						
							CompanyDetailsVo companyDetailsVo = new CompanyDetailsVo();
							companyDetailsVo.setCustomerId((Integer)obj1[0]);
							companyDetailsVo.setCompanyId((Integer)obj1[1]);
							companyDetailsVo.setCompanyCode(obj1[2]+"");
							companyDetailsVo.setCompanyName(obj1[3]+"");				
							companyDetailsVo.setCountryName(obj1[4]+"");
							companyDetailsVo.setIsActive(obj1[5]+"");
							companyDetailsVo.setCmpId((Integer)obj1[6]);
							companyDetailsVo.setCustomerName((String)obj1[9]);
							companyDetailsVo.setCustomerCode((String)obj1[10]);
							companyDetailsVoList.add(companyDetailsVo);
						
						
					}
				}
				
			}
					

			
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}
		return companyDetailsVoList;
	}

	@Override
	public CompanyDetailsInfoVo getCompanyDetailsListByCompanyId(int companyInfoId) {
		
		//String query = " SELECT `Company_Info_Id`, info.`Company_Id`, info.`Customer_Id`, `Company_Name`, `Country_Id`, `Company_Sequence_Id`, info.`Transaction_Date`, info.`Is_Active`, `Sector_Id`, `Legacy_Id`, `Industry_Id`, `Sub_Industry_Id`, `Corporate_Identity_Number`, `Registration_Date`, `ROC_Registration_Number`, `Registration_Act_Id`, `Service_Tax_Registration_Number`, `TIN_Number`, `PAN_Number`, `PAN_Registration_Date`, `PF_Number`, `PF_Registration_Date`, `PF_Start_date`, `PF_Type_Id`, `ESI_Number`, `ESI_Registration_Date`, `ESI_Start_Date`,details.`Company_Code` "
		//		+ " FROM `company_details_info`  info INNER JOIN `company_details` details ON (info.`Company_Id` = details.`Company_Id`) WHERE `Company_Info_Id`="+companyInfoId;
		CompanyDetailsInfoVo infoVo = new CompanyDetailsInfoVo();
		CompanyDetailsInfo info = (CompanyDetailsInfo) sessionFactory.getCurrentSession().get(CompanyDetailsInfo.class, companyInfoId);
		try{
		infoVo.setCompanyInfoId(info.getCompanyInfoId());
		infoVo.setCompanyDetailsId(info.getCompanyDetails().getCompanyId());
		infoVo.setCompanyName(info.getCompanyName());
		infoVo.setCompanyCode(info.getCompanyDetails().getCompanyCode());
		infoVo.setCustomerDetailsId(info.getCustomerDetails().getCustomerId());
		infoVo.setCountryId(info.getCountryId());		
		infoVo.setUniqueId(info.getUniqueId());
		infoVo.setCompanySequenceId(info.getCompanySequenceId());
		infoVo.setTransactionDate( DateHelper.convertDate(info.getTransactionDate()));
		infoVo.setIsActive(info.getIsActive());
		infoVo.setSectorId(info.getSectorId());
		infoVo.setLegacyId(info.getLegacyId());
		infoVo.setIndustryId(info.getIndustryId());
		infoVo.setSubIndustryId(info.getSubIndustryId());
		infoVo.setCorporateIdentityNumber(info.getCorporateIdentityNumber());
		if(info.getRegistrationDate() != null)
		infoVo.setRegistrationDate(DateHelper.convertDate(info.getRegistrationDate()));
		infoVo.setRocRegistrationNumber(info.getRocRegistrationNumber());
		infoVo.setRegistrationActId(info.getRegistrationActId());
		infoVo.setServiceTaxRegistrationNumber(info.getServiceTaxRegistrationNumber());
		infoVo.setTinNumber(info.getTinNumber());
		infoVo.setPanNumber(info.getPanNumber());
		if(info.getPanRegistrationDate() != null)
		infoVo.setPanRegistrationDate(DateHelper.convertDate(info.getPanRegistrationDate()));
		infoVo.setPfNumber(info.getPfNumber());
		if(info.getPfRegistrationDate() != null)
		infoVo.setPfRegistrationDate(DateHelper.convertDate(info.getPfRegistrationDate()));
		if(info.getPfStartDate() != null)
		infoVo.setPfStartDate(DateHelper.convertDate(info.getPfStartDate()));
		infoVo.setPfTypeId(info.getPfTypeId());
		infoVo.setEsiNumber(info.getEsiNumber());
		if(info.getEsiRegistrationDate() != null)
		infoVo.setEsiRegistrationDate(DateHelper.convertDate(info.getEsiRegistrationDate()));
		if(info.getEsiStartDate() != null)
		infoVo.setEsiStartDate(DateHelper.convertDate(info.getEsiStartDate()));
		infoVo.setCreatedBy(info.getCreatedBy());
		infoVo.setCreatedDate(DateHelper.convertDate(info.getCreatedDate()));
		infoVo.setModifiedBy(info.getModifiedBy());
		infoVo.setModifiedDate(DateHelper.convertDate(info.getModifiedDate()));
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return infoVo; //
	}

	@Override
	public List<SimpleObject> getCompanyTransactionDates(String customerId, String companyId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> simpleObjects = new ArrayList<SimpleObject>();
		try {
			List tempList =  session.createSQLQuery(" SELECT `Company_Info_Id` AS id, CONCAT(DATE_FORMAT(`Transaction_Date`,'%d/%m/%Y'),' - ',`Company_Sequence_Id`) AS tname FROM `company_details_info` WHERE `Company_Id`='"+companyId+"' and `Customer_Id`='"+customerId+"' order by Transaction_Date, Company_Sequence_Id ").list();			

			for(Object o : tempList){
				Object[] obj = (Object[]) o;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)obj[0]);
				object.setName(obj[1]+"");
				simpleObjects.add(object);
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return simpleObjects;
	}


	
	/* Company Details End */
	

	@Override
	public List<SimpleObject> getCusomerIndustries(Integer customerId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> simpleObjects = new ArrayList<SimpleObject>();
		try {
			List tempList =  session.createSQLQuery(" SELECT industry.`Industry_Id`,industry.`Industry_Name` FROM `customer_industry_sector_details` customer INNER JOIN `m_industry` industry ON ( industry.`Industry_Id`  = customer.`Industry_Id`) WHERE   `Transaction_Date` <= CURRENT_DATE() AND `CustomerIndustry_Sequence_Id` = ( SELECT MAX(`CustomerIndustry_Sequence_Id`) FROM `customer_industry_sector_details` child  WHERE child.`Transaction_Date` = customer.`Transaction_Date` AND customer.`Customer_Id` = child.`Customer_Id` ORDER BY `CustomerIndustry_Sequence_Id` DESC)  AND `Customer_Id` ="+customerId+" GROUP BY customer.`Industry_Id`  ORDER BY industry.Industry_Name").list();					

			for(Object o : tempList){
				Object[] obj = (Object[]) o;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)obj[0]);
				object.setName(obj[1]+"");
				simpleObjects.add(object);
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return simpleObjects;
	}

	@Override
	public List<SimpleObject> getCustomerSectors(Integer customerId, Integer industryId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> simpleObjects = new ArrayList<SimpleObject>();
		try {
			List tempList =  session.createSQLQuery(" SELECT sector.`Sector_Id`,sector.`Sector_Name` FROM `customer_industry_sector_details` customer INNER JOIN `m_sector` sector ON ( sector.`Sector_Id` = customer.`Sector_Id`) WHERE `Transaction_Date` <= CURRENT_DATE() AND  `CustomerIndustry_Sequence_Id` = ( SELECT MAX(`CustomerIndustry_Sequence_Id`) FROM `customer_industry_sector_details` child  WHERE child.`Transaction_Date` = customer.`Transaction_Date` AND customer.`Customer_Id` = child.`Customer_Id` ORDER BY `CustomerIndustry_Sequence_Id` DESC) AND `Customer_Id` ="+customerId+"  AND customer.`Industry_Id` ="+industryId+" GROUP BY customer.`Sector_Id` ORDER BY sector.Sector_Name asc ").list();					

			for(Object o : tempList){
				Object[] obj = (Object[]) o;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)obj[0]);
				object.setName(obj[1]+"");
				simpleObjects.add(object);
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return simpleObjects;
	
	}

	@Override
	public CompanyProfileVo getCompanyProfileByCompanyId(String companyId, String customerId) {		
		CompanyProfileVo profileVo = new CompanyProfileVo(); 
		try {
			List tempList =  sessionFactory.getCurrentSession().createSQLQuery("SELECT cprofile.`Company_Profile_Id`,info.`Company_Id`,info.`Customer_Id`, cprofile.`Sequence_Id`, cprofile.`Is_Active`, `Default_Currency_Id`, `Official_Language_Id`, `Is_Multi_Language`, `Is_Multi_Currency`, `Min_age`, `Max_Age`, `Retire_Age`, `Work_Week_Start_Id`, `Work_Week_End_Id`, `Number_Of_Working_Days`, `Bussiness_Hours_Per_Day`, `Standar_Hours_Per_Week`, `Bussiness_Start_Time`, `Bussiness_End_Time` ,company.`Company_Code`,info.`Company_Name`,country.`Country_Name`,GROUP_CONCAT(DISTINCT `Language_Id`),GROUP_CONCAT(DISTINCT currency.`Currency_Id`), cur.Currency FROM `company_profile` cprofile RIGHT JOIN `company_details` company ON (cprofile.`Company_Id`=company.`Company_Id`) LEFT JOIN `company_details_info` info ON (info.`Company_Id`=company.`Company_Id`) LEFT JOIN `m_country` country ON (country.`Country_Id` = info.`Country_Id`) LEFT JOIN `company_languages` languages ON(languages.`Company_Profile_Id` = cprofile.`Company_Profile_Id`) LEFT JOIN `company_currency` currency ON(currency.`Company_Profile_Id` = cprofile.`Company_Profile_Id`) LEFT JOIN `m_currency` cur ON( cur.`Currency_Id` = cprofile.`Default_Currency_Id` )  WHERE company.`Company_Id` ='"+companyId+"' and company.Customer_Id='"+customerId+"'").list();					
			if(tempList != null && tempList.size() > 0){
				Object[] profile = (Object[]) tempList.get(0);
				profileVo.setCompanyProfileId((Integer)profile[0]);
				profileVo.setCompanyDetailsId((Integer)profile[1]);
				profileVo.setCustomerDetailsId((Integer)profile[2]);
				profileVo.setSequenceId((Integer)profile[3]);
				profileVo.setIsActive((profile[4] == null || profile[4] == "Y") ? "Y" : "N");
				profileVo.setDefaultCurrencyId((Integer)profile[5]);
				profileVo.setOfficialLanguageId((Integer)profile[6]);
				profileVo.setIsMultiLanguage((profile[7] == null || (char)profile[7] == 'Y') ? true : false);
				profileVo.setIsMultiCurrency((profile[8] == null || (char)profile[8] == 'Y') ? true : false);
				profileVo.setMinAge((Integer)profile[9]);
				profileVo.setMaxAge((Integer)profile[10]);
				profileVo.setRetireAge((Integer)profile[11]);
				profileVo.setWorkWeekStartId((Integer)profile[12]);
				profileVo.setWorkWeekEndId((Integer)profile[13]);
				profileVo.setNumberOfWorkingDays((Integer)profile[14]);
				profileVo.setBussinessHoursPerDay((Integer)profile[15]);
				profileVo.setStandarHoursPerWeek((Integer)profile[16]);
				profileVo.setBussinessStartTime(profile[17] == null ? "" : profile[17]+"" );
				profileVo.setBussinessEndTime(profile[18] == null ? "" : profile[18]+"" );
				profileVo.setCompanyCode(profile[19]+"");
				profileVo.setCompanyName(profile[20]+"");
				profileVo.setCountryName(profile[21]+"");
				if(profile[8] != null && profile[23] != null && (char)profile[8] == 'Y' && (profile[23]+"").contains(",")){					
					String val = profile[23]+"";
					 int[] currval = Arrays.stream(val.split(","))
		                .map(String::trim).mapToInt(Integer::parseInt).toArray();
					profileVo.setCurrencys(currval);
				}else if(profile[8] != null && profile[23] != null && (char)profile[8] == 'Y'  &&  !(profile[23]+"").equalsIgnoreCase("null")){
					int[] arr = new int[1];
					arr[0] = (profile[23]+"").equalsIgnoreCase("null") ? 0 : Integer.valueOf(profile[23]+"");
					profileVo.setCurrencys(arr);
				}
				if(profile[7] != null && profile[22] != null && (char)profile[7] == 'Y' && (profile[22]+"").contains(",")){					
					String val = profile[22]+"";
					 int[] currval = Arrays.stream(val.split(","))
		                .map(String::trim).mapToInt(Integer::parseInt).toArray();
					profileVo.setLanguages(currval);
				}else if(profile[7] != null && profile[22] != null && (char)profile[7] == 'Y' &&  !(profile[22]+"").equalsIgnoreCase("null")){
					int[] arr = new int[1];
					arr[0] = (profile[22]+"").equalsIgnoreCase("null") ? 0 : Integer.valueOf(profile[22]+"");
					profileVo.setLanguages(arr);
				}	
				
				profileVo.setDefaultCurrencyName((String)profile[24]);
			}									
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return profileVo;	
	}

	@Override
	public Integer saveCompanyProfile(CompanyProfileVo profileVo) {
		log.info("Entered in  saveCompanyProfile()  ::   "+profileVo);
		Session session = sessionFactory.getCurrentSession();			
		Integer profileId = null;		
		try {
		
			CompanyProfile companyProfile = new CompanyProfile();
			if(profileVo != null && profileVo.getCompanyProfileId() != null && profileVo.getCompanyProfileId() > 0){
				companyProfile = (CompanyProfile) sessionFactory.getCurrentSession().load(CompanyProfile.class, profileVo.getCompanyProfileId());
				companyProfile.setCompanyDetails(new CompanyDetails(profileVo.getCompanyDetailsId()));
				companyProfile.setCustomerDetails(new CustomerDetails(profileVo.getCustomerDetailsId()));
				companyProfile.setSequenceId(profileVo.getSequenceId()+1);
				companyProfile.setIsActive((profileVo.getIsActive() == null || profileVo.getIsActive() == "Y") ? "Y" : "N");
				companyProfile.setDefaultCurrencyId(profileVo.getDefaultCurrencyId());
				companyProfile.setOfficialLanguageId(profileVo.getOfficialLanguageId());
				companyProfile.setIsMultiLanguage(profileVo.getIsMultiLanguage() ? 'Y' : 'N');
				companyProfile.setIsMultiCurrency(profileVo.getIsMultiCurrency() ? 'Y' : 'N');
				companyProfile.setMaxAge(profileVo.getMaxAge());
				companyProfile.setMinAge(profileVo.getMinAge());
				companyProfile.setRetireAge(profileVo.getRetireAge());
				companyProfile.setWorkWeekStartId(profileVo.getWorkWeekStartId());
				companyProfile.setWorkWeekEndId(profileVo.getWorkWeekEndId());
				companyProfile.setNumberOfWorkingDays(profileVo.getNumberOfWorkingDays());
				companyProfile.setBussinessHoursPerDay(profileVo.getBussinessHoursPerDay());
				companyProfile.setStandarHoursPerWeek(profileVo.getStandarHoursPerWeek());
				companyProfile.setBussinessStartTime(profileVo.getBussinessStartTime());
				companyProfile.setBussinessEndTime(profileVo.getBussinessEndTime());
				companyProfile.setModifiedBy(profileVo.getModifiedBy());
				companyProfile.setModifiedDate(new Date());
				sessionFactory.getCurrentSession().update(companyProfile);
				profileId = profileVo.getCompanyProfileId();
			}else{						
				companyProfile.setCompanyDetails(new CompanyDetails(profileVo.getCompanyDetailsId()));
				companyProfile.setCustomerDetails(new CustomerDetails(profileVo.getCustomerDetailsId()));
				companyProfile.setSequenceId(1);
				companyProfile.setIsActive((profileVo.getIsActive() == null || profileVo.getIsActive() == "Y") ? "Y" : "N");
				companyProfile.setDefaultCurrencyId(profileVo.getDefaultCurrencyId());
				companyProfile.setOfficialLanguageId(profileVo.getOfficialLanguageId());
				companyProfile.setIsMultiLanguage(profileVo.getIsMultiLanguage() ? 'Y' : 'N');
				companyProfile.setIsMultiCurrency(profileVo.getIsMultiCurrency() ? 'Y' : 'N');
				companyProfile.setMaxAge(profileVo.getMaxAge());
				companyProfile.setMinAge(profileVo.getMinAge());
				companyProfile.setRetireAge(profileVo.getRetireAge());
				companyProfile.setWorkWeekStartId(profileVo.getWorkWeekStartId());
				companyProfile.setWorkWeekEndId(profileVo.getWorkWeekEndId());
				companyProfile.setNumberOfWorkingDays(profileVo.getNumberOfWorkingDays());
				companyProfile.setBussinessHoursPerDay(profileVo.getBussinessHoursPerDay());
				companyProfile.setStandarHoursPerWeek(profileVo.getStandarHoursPerWeek());
				companyProfile.setBussinessStartTime(profileVo.getBussinessStartTime());
				companyProfile.setBussinessEndTime(profileVo.getBussinessEndTime());
				companyProfile.setModifiedBy(profileVo.getModifiedBy());
				companyProfile.setModifiedDate(new Date());
				companyProfile.setCreatedBy(profileVo.getCreatedBy());
				companyProfile.setCreatedDate(new Date());
				profileId = (Integer) session.save(companyProfile);
			}			
			
			session.createQuery( " Delete from CompanyCurrency where companyProfile ="+profileId).executeUpdate();
			session.createQuery( " Delete from CompanyLanguages where companyProfile ="+profileId).executeUpdate();
		
			if(profileVo.getCurrencys() != null && profileVo.getCurrencys().length > 1){
				for(int currency : profileVo.getCurrencys()){
					session.save(new CompanyCurrency(new CompanyProfile(profileId), Integer.valueOf(currency), 1, "Y", profileVo.getCreatedBy(), new Date(), profileVo.getModifiedBy(),  new Date()));					
				}				
			}else if(profileVo.getCurrencys() != null){
				session.save(new CompanyCurrency(new CompanyProfile(profileId), profileVo.getCurrencys()[0] , 1, "Y", profileVo.getCreatedBy(), new Date(), profileVo.getModifiedBy(),  new Date()));		
			}
			if(profileVo.getLanguages() != null && profileVo.getLanguages().length > 1){
				for(int language : profileVo.getLanguages()){
					session.save(new CompanyLanguages(new CompanyProfile(profileId), Integer.valueOf(language), 1, "Y", profileVo.getCreatedBy(), new Date(), profileVo.getModifiedBy(),  new Date()));					
				}
				
			}else if(profileVo.getLanguages() != null){
				session.save(new CompanyLanguages(new CompanyProfile(profileId), profileVo.getLanguages()[0], 1, "Y", profileVo.getCreatedBy(), new Date(), profileVo.getModifiedBy(),  new Date()));
			}
						
			 session.flush();
			
		 }catch (Exception e) {
			 log.error("Error occured ... ",e);			
		 }
		 return profileId;
	}

	@Override
	public List<WorkorderDetailInfoVo> getCompanyWorkOrderGridData(String customerId, String companyId,
			String locationName, String departmentName, String workOrderCode, String workOrderName, String status) {
		
		
		List<WorkorderDetailInfoVo> workorderDetailInfoVos = new ArrayList<WorkorderDetailInfoVo>();
		try {
			String query = " SELECT  wdi.`WorkOrder_Info_id` ,cdi.customer_name,cmpdi.`Company_Name`,IFNULL(location.Location_Name,''),"
						+ "  CASE WHEN wdi.Department_id = 0 THEN 'ALL' ELSE IFNULL(dept.Department_Name,'') END ,`WorkOrder_Code`,`Work_Order_Name`,CASE wdi.`Is_Active` WHEN 'Y' THEN 'Active' ELSE 'InActive' END AS active "
						+ "  FROM  `workrorder_detail` wd "
						+ "  INNER JOIN workorder_detail_info wdi ON (wd.`WorkOrder_id` = wdi.`WorkOrder_id`) "
						+ "  INNER JOIN customer_details_info cdi ON cdi.customer_id=wd.customer_id  "
						+ "  INNER JOIN company_details_info cmpdi ON cmpdi.`Company_Id`=wd.`Company_Id`  "
						+ "  INNER JOIN location_details_info  location  ON( location.Location_Id = wdi.location_id)   "
						+ " LEFT JOIN (select * from department_details_info dept where CONCAT(DATE_FORMAT(dept.transaction_date, '%Y%m%d'), dept.`Department_Sequence_Id`) =  ("
						+ " SELECT MAX(CONCAT(DATE_FORMAT(dept1.transaction_date, '%Y%m%d'), dept1.`Department_Sequence_Id`)) FROM department_details_info dept1 WHERE dept.`Department_Id` = dept1.`Department_Id` AND dept1.transaction_date <= CURRENT_DATE() )) dept"
						+ " ON ( dept.Department_Id = wdi.Department_id)    "
						+ "  WHERE  " 
						+ "  CONCAT(DATE_FORMAT(wdi.transaction_date, '%Y%m%d'), wdi.`Sequence_Id`) =  "  
						+ "  ( SELECT MAX(CONCAT(DATE_FORMAT(wdi1.transaction_date, '%Y%m%d'), wdi1.`Sequence_Id`))  " 
						+ "  FROM workorder_detail_info wdi1 WHERE wdi.`WorkOrder_id` = wdi1.`WorkOrder_id`  "  
						+ "  AND wdi1.transaction_date <= CURRENT_DATE() )  " 						  
						+ "  AND CONCAT(DATE_FORMAT(cdi.transaction_date, '%Y%m%d'), cdi.Customer_Sequence_Id) =  " 
						+ "  (   "
						+ "  SELECT MAX(CONCAT(DATE_FORMAT(cdi1.transaction_date, '%Y%m%d'), cdi1.Customer_Sequence_Id))  " 
						+ "  FROM customer_details_info cdi1   "
						+ "  WHERE cdi.customer_id = cdi1.customer_id AND cdi1.transaction_date <= CURRENT_DATE()  " 
						+ "  )   "						
						+ "  AND  CONCAT(DATE_FORMAT(cmpdi.transaction_date, '%Y%m%d'), cmpdi.`Company_Sequence_Id`) =  " 
						+ "  (   "
						+ "  SELECT MAX(CONCAT(DATE_FORMAT(cmpdi1.transaction_date, '%Y%m%d'), cmpdi1.`Company_Sequence_Id`))  " 
						+ "  FROM `company_details_info`  cmpdi1  " 
						+ "  WHERE cmpdi.`Company_Id` = cmpdi1.`Company_Id` AND cmpdi1.transaction_date <= CURRENT_DATE()  " 
						+ "  ) AND CONCAT(DATE_FORMAT(location.transaction_date, '%Y%m%d'), location.`Location_Sequence_Id`) =  ( "
						+ "  SELECT    MAX(CONCAT(DATE_FORMAT(location1.transaction_date, '%Y%m%d'), location1.`Location_Sequence_Id`))  FROM "
						+ "  location_details_info location1  WHERE   location.`Location_Id` = location1.`Location_Id` AND location1.transaction_date <= CURRENT_DATE()  )    ";
			
			
						
			if(customerId != null && !customerId.isEmpty() && Integer.valueOf(customerId) > 0){
				query+=" AND wd.Customer_Id="+customerId ;
			}
			if(companyId != null && !companyId.isEmpty() && Integer.valueOf(companyId) > 0){
				query+=" AND  wd.`Company_Id` ="+companyId ;			
			}
			if(locationName != null && !locationName.isEmpty() && Integer.valueOf(locationName) > 0){
				query+=" AND wdi.location_id="+locationName;
			}
			if(departmentName != null && !departmentName.isEmpty() && Integer.valueOf(departmentName) > 0){
				query+="  AND wdi.department_id="+departmentName;
			}
			if(workOrderCode != null && !workOrderCode.isEmpty() ){
				query+=" AND WorkOrder_Code like '%"+workOrderCode+"%'";
			}
			
			if(workOrderName != null && !workOrderName.isEmpty()){
				query+=" AND Work_Order_Name like '%"+workOrderName+"%'";
			}
			
			if(status != null && !status.isEmpty()){
				query+=" AND wdi.Is_Active='"+status+"'";
			}
			query+=" ORDER BY WorkOrder_Code asc";
			
			Session session = sessionFactory.getCurrentSession();
			List tempList =  session.createSQLQuery(query).list();			

			for(Object o : tempList){
				Object[] obj = (Object[]) o;
				WorkorderDetailInfoVo infoVo = new WorkorderDetailInfoVo();
				infoVo.setWorkOrderInfoId((Integer)obj[0]);
				infoVo.setCustomerName(obj[1]+"");
				infoVo.setCompanyName(obj[2]+"");
				infoVo.setLocationName(obj[3]+"");
				infoVo.setDepartmentName(obj[4]+"");
				infoVo.setWorkOrderCode(obj[5]+"");
				infoVo.setWorkOrderName(obj[6]+"");
				infoVo.setIsActive(obj[7]+"");
				
				workorderDetailInfoVos.add(infoVo);
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return workorderDetailInfoVos;
	}

	
	@Override
	public Integer saveCompanyWorkOrder(WorkorderDetailInfoVo workorderDetailInfoVo) {
		Integer workOrderId = 0;
		Integer workOrderInfoId = 0;
		
		WorkrorderDetail workrorderDetail = null;
		WorkorderDetailInfo workorderDetailInfo = null;
		BigInteger listSize = new BigInteger("0");
		try{
			
			if(workorderDetailInfoVo != null && workorderDetailInfoVo.getWorkrorderDetailId() != null && workorderDetailInfoVo.getWorkrorderDetailId() > 0 ){
				listSize = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(COUNT(`WorkOrder_Code`),0) FROM `workrorder_detail` parent INNER JOIN `workorder_detail_info` child ON (parent.`WorkOrder_id` = child.`WorkOrder_id`)   WHERE child.`Customer_Id`="+workorderDetailInfoVo.getCustomerDetailsId()+" AND child.`Company_Id`="+workorderDetailInfoVo.getCompanyDetailsId()+" AND  child.`WorkOrder_id` !="+workorderDetailInfoVo.getWorkrorderDetailId()+" AND parent.`WorkOrder_Code`='"+workorderDetailInfoVo.getWorkOrderCode()+"'").list().get(0);
			}else {
				listSize = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(COUNT(`WorkOrder_Code`),0) FROM `workrorder_detail` parent INNER JOIN `workorder_detail_info` child ON (parent.`WorkOrder_id` = child.`WorkOrder_id`)  WHERE child.`Customer_Id`="+workorderDetailInfoVo.getCustomerDetailsId()+" AND child.`Company_Id`="+workorderDetailInfoVo.getCompanyDetailsId()+" AND  parent.`WorkOrder_Code`='"+workorderDetailInfoVo.getWorkOrderCode()+"'").list().get(0);
			}
			if(listSize.intValue() > 0){				
				workOrderInfoId =-1;
				return workOrderInfoId;
			}
			
			
			if(workorderDetailInfoVo.getWorkrorderDetailId() != null && workorderDetailInfoVo.getWorkrorderDetailId() > 0){
				workrorderDetail = (WorkrorderDetail) sessionFactory.getCurrentSession().load(WorkrorderDetail.class,  workorderDetailInfoVo.getWorkrorderDetailId());
				workrorderDetail.setWorkOrderCode(workorderDetailInfoVo.getWorkOrderCode());
				workrorderDetail.setModifiedBy(workorderDetailInfoVo.getModifiedBy());
				workrorderDetail.setModifiedDate(new Date());
				sessionFactory.getCurrentSession().update(workrorderDetail);
				workOrderId = workorderDetailInfoVo.getWorkrorderDetailId();
			}else{
				workrorderDetail = new WorkrorderDetail();
				workrorderDetail.setWorkOrderCode(workorderDetailInfoVo.getWorkOrderCode());
				workrorderDetail.setCustomerDetails(new CustomerDetails(workorderDetailInfoVo.getCustomerDetailsId()));
				workrorderDetail.setCompanyDetails(new CompanyDetails(workorderDetailInfoVo.getCompanyDetailsId()));
				workrorderDetail.setCreatedBy(workorderDetailInfoVo.getCreatedBy());
				workrorderDetail.setCreatedDate(new Date());
				workrorderDetail.setModifiedBy(workorderDetailInfoVo.getModifiedBy());
				workrorderDetail.setModifiedDate(new Date());
				workOrderId = (Integer) sessionFactory.getCurrentSession().save(workrorderDetail);
			}
			
			if(workorderDetailInfoVo.getWorkOrderInfoId() != null && workorderDetailInfoVo.getWorkOrderInfoId() > 0){
				sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM `workorder_manpower_details` WHERE `WorkOrder_Info_id` = "+workorderDetailInfoVo.getWorkOrderInfoId()).executeUpdate();
				sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM `workorder_responsibilities` WHERE  `WorkOrder_Info_id`="+workorderDetailInfoVo.getWorkOrderInfoId()).executeUpdate();
				
				workorderDetailInfo = (WorkorderDetailInfo) sessionFactory.getCurrentSession().load(WorkorderDetailInfo.class, workorderDetailInfoVo.getWorkOrderInfoId());
				workorderDetailInfo.setCustomerDetails(new CustomerDetails(workorderDetailInfoVo.getCustomerDetailsId()));
				workorderDetailInfo.setCompanyDetails(new CompanyDetails(workorderDetailInfoVo.getCompanyDetailsId()));
				workorderDetailInfo.setWorkrorderDetail(new WorkrorderDetail(workOrderId));
				workorderDetailInfo.setCountry(workorderDetailInfoVo.getCountry());
				workorderDetailInfo.setTransactionDate(workorderDetailInfoVo.getTransactionDate());				
				workorderDetailInfo.setIsActive(workorderDetailInfoVo.getIsActive());
				workorderDetailInfo.setWorkOrderName(workorderDetailInfoVo.getWorkOrderName());
				workorderDetailInfo.setLocationDetails(new LocationDetails(workorderDetailInfoVo.getLocation()));
				workorderDetailInfo.setPlantDetails(new PlantDetails(workorderDetailInfoVo.getPlantId()));
				workorderDetailInfo.setDepartmentDetails(new DepartmentDetails(workorderDetailInfoVo.getDepartment()));	
				workorderDetailInfo.setTotalHeadCount((workorderDetailInfoVo.getTotalHeadCount() != null && workorderDetailInfoVo.getTotalHeadCount() > 0 ? workorderDetailInfoVo.getTotalHeadCount() : 0 ) );
				workorderDetailInfo.setNatureOfWork(workorderDetailInfoVo.getNatureOfWork());
				workorderDetailInfo.setWorkPeriodFrom(workorderDetailInfoVo.getWorkPeriodFrom());
				workorderDetailInfo.setWorkPeriodTo(workorderDetailInfoVo.getWorkPeriodTo());
				workorderDetailInfo.setModifiedBy(workorderDetailInfoVo.getModifiedBy());
				workorderDetailInfo.setModifiedDate(new Date());
				sessionFactory.getCurrentSession().update(workorderDetailInfo);
				workOrderInfoId = workorderDetailInfoVo.getWorkOrderInfoId();
			}else{
				workorderDetailInfo = new WorkorderDetailInfo();
				workorderDetailInfo.setCustomerDetails(new CustomerDetails(workorderDetailInfoVo.getCustomerDetailsId()));
				workorderDetailInfo.setCompanyDetails(new CompanyDetails(workorderDetailInfoVo.getCompanyDetailsId()));
				workorderDetailInfo.setWorkrorderDetail(new WorkrorderDetail(workOrderId));
				workorderDetailInfo.setCountry(workorderDetailInfoVo.getCountry());
				workorderDetailInfo.setTransactionDate(workorderDetailInfoVo.getTransactionDate());
				BigInteger sequenceId  = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(`Sequence_Id`),0) AS id FROM `workorder_detail_info`  WHERE  `WorkOrder_id`= "+workOrderId+" AND Company_Id = "+workorderDetailInfoVo.getCompanyDetailsId()+" AND `Transaction_Date` = '"+DateHelper.convertDateToSQLString(workorderDetailInfoVo.getTransactionDate())+"' and Customer_Id='"+workorderDetailInfoVo.getCustomerDetailsId()+"'").list().get(0);
				workorderDetailInfo.setSequenceId(sequenceId.intValue()+1);
				workorderDetailInfo.setIsActive(workorderDetailInfoVo.getIsActive());
				workorderDetailInfo.setWorkOrderName(workorderDetailInfoVo.getWorkOrderName());
				workorderDetailInfo.setLocationDetails(new LocationDetails(workorderDetailInfoVo.getLocation()));
				workorderDetailInfo.setPlantDetails(new PlantDetails(workorderDetailInfoVo.getPlantId()));
				workorderDetailInfo.setDepartmentDetails(new DepartmentDetails(workorderDetailInfoVo.getDepartment()));
				workorderDetailInfo.setTotalHeadCount((workorderDetailInfoVo.getTotalHeadCount() != null && workorderDetailInfoVo.getTotalHeadCount() > 0 ? workorderDetailInfoVo.getTotalHeadCount() : 0 ) );
				workorderDetailInfo.setNatureOfWork(workorderDetailInfoVo.getNatureOfWork());
				workorderDetailInfo.setWorkPeriodFrom(workorderDetailInfoVo.getWorkPeriodFrom());
				workorderDetailInfo.setWorkPeriodTo(workorderDetailInfoVo.getWorkPeriodTo());
				workorderDetailInfo.setCreatedBy(workorderDetailInfoVo.getCreatedBy());
				workorderDetailInfo.setCreatedDate(new Date());
				workorderDetailInfo.setModifiedBy(workorderDetailInfoVo.getModifiedBy());
				workorderDetailInfo.setModifiedDate(new Date());				
				workOrderInfoId = (Integer) sessionFactory.getCurrentSession().save(workorderDetailInfo);
			}	
			
			
			for(WorkorderManpowerDetailsVo manpowerDetailsVos :  workorderDetailInfoVo.getWorkorderManpowerDetailsVo() ){
				WorkorderManpowerDetails manpowerDetails = new WorkorderManpowerDetails();
				manpowerDetails.setWorkrorderDetail( new WorkrorderDetail(workOrderId));
				manpowerDetails.setWorkorderDetailInfo(new WorkorderDetailInfo(workOrderInfoId));
				manpowerDetails.setWorkSkillId(manpowerDetailsVos.getWorkSkillId());
				manpowerDetails.setJobCodeId(manpowerDetailsVos.getJobCodeId());
				manpowerDetails.setHeadCount(manpowerDetailsVos.getHeadCount());
				manpowerDetails.setFromDate(manpowerDetailsVos.getFromDate());
				manpowerDetails.setToDate(manpowerDetailsVos.getToDate());
				manpowerDetails.setDuration(manpowerDetailsVos.getDuration());
				manpowerDetails.setCreatedBy(workorderDetailInfoVo.getCreatedBy());
				manpowerDetails.setCreatedDate(new Date());
				manpowerDetails.setModifiedBy(workorderDetailInfoVo.getModifiedBy());
				manpowerDetails.setModifiedDate(new Date());
				 sessionFactory.getCurrentSession().save(manpowerDetails);
			}
			
			for(WorkorderResponsibilitiesVo responsibilitiesVo : workorderDetailInfoVo.getWorkorderResponsibilitiesVo() ){
				WorkorderResponsibilities responsibilities = new WorkorderResponsibilities();
				responsibilities.setWorkrorderDetail( new WorkrorderDetail(workOrderId));
				responsibilities.setWorkorderDetailInfo(new WorkorderDetailInfo(workOrderInfoId));
				responsibilities.setCharacteristics(responsibilitiesVo.getCharacteristics());
				responsibilities.setResponsibilityTakenBy(responsibilitiesVo.getResponsibilityTakenBy());
				responsibilities.setCompanyShare(responsibilitiesVo.getCompanyShare());
				responsibilities.setVendorShare(responsibilitiesVo.getVendorShare());
				responsibilities.setAdditionalInfo(responsibilitiesVo.getAdditionalInfo());
				responsibilities.setCreatedBy(workorderDetailInfoVo.getCreatedBy());
				responsibilities.setCreatedDate(new Date());
				responsibilities.setModifiedBy(workorderDetailInfoVo.getModifiedBy());
				responsibilities.setModifiedDate(new Date());				
				sessionFactory.getCurrentSession().save(responsibilities);
			}
			
			sessionFactory.getCurrentSession().flush();
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return workOrderInfoId;
	}

	@Override
	public WorkorderDetailInfoVo getWorkorderDetailInfoByWorkOrderId(String orderId) {
		WorkorderDetailInfoVo workorderDetailInfoVo = new WorkorderDetailInfoVo();
		try{
			WorkorderDetailInfo workOrder= (WorkorderDetailInfo) sessionFactory.getCurrentSession().load(WorkorderDetailInfo.class, Integer.valueOf(orderId));
			workorderDetailInfoVo.setWorkOrderInfoId(workOrder.getWorkOrderInfoId());
			workorderDetailInfoVo.setCustomerDetailsId(workOrder.getCustomerDetails().getCustomerId());
			workorderDetailInfoVo.setCompanyDetailsId(workOrder.getCompanyDetails().getCompanyId());
			workorderDetailInfoVo.setWorkrorderDetailId(workOrder.getWorkrorderDetail().getWorkOrderId());
			workorderDetailInfoVo.setWorkOrderCode(workOrder.getWorkrorderDetail().getWorkOrderCode());
			workorderDetailInfoVo.setCountry(workOrder.getCountry());
			workorderDetailInfoVo.setTransactionDate(workOrder.getTransactionDate());			
			workorderDetailInfoVo.setIsActive(workOrder.getIsActive());
			workorderDetailInfoVo.setWorkOrderName(workOrder.getWorkOrderName());
			workorderDetailInfoVo.setLocation(workOrder.getLocationDetails().getLocationId());
			workorderDetailInfoVo.setDepartment(workOrder.getDepartmentDetails().getDepartmentId());
			workorderDetailInfoVo.setPlantId(workOrder.getPlantDetails().getPlantId());			
			workorderDetailInfoVo.setTotalHeadCount(workOrder.getTotalHeadCount());
			workorderDetailInfoVo.setNatureOfWork(workOrder.getNatureOfWork());
			workorderDetailInfoVo.setWorkPeriodFrom(workOrder.getWorkPeriodFrom());
			workorderDetailInfoVo.setWorkPeriodTo(workOrder.getWorkPeriodTo());
			
			
			List tempList = sessionFactory.getCurrentSession().createSQLQuery("SELECT  man.`Work_Skill_id`,CONCAT(parent.`Job_Code`,' - ', `Job_Name`), man.`Job_Code_id`, man.`Head_Count`, man.`From_Date`, man.`To_Date`, man.`Duration` FROM `workorder_manpower_details` man INNER JOIN `job_code_details` parent ON (man.`Job_Code_id` = parent.`Job_Code_Id` )  INNER JOIN `job_code_details_info` child ON (parent.`Job_Code_Id` = child.`Job_Code_Id`) where child.`Status`='Y'  AND CONCAT(DATE_FORMAT(child.`Transaction_Date`, '%Y%m%d'), child.`Job_Code_Sequence_Id`)  = (SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), vdi1.`Job_Code_Sequence_Id`))  FROM job_code_details_info  vdi1 WHERE parent.Job_Code_Id = vdi1.Job_Code_Id AND vdi1.transaction_date <= CURRENT_DATE()) AND  man.WorkOrder_Info_id="+orderId).list();
			for (Object manpower  : tempList) {
				Object[] obj = (Object[]) manpower;
				WorkorderManpowerDetailsVo manpowerDetailsVos = new WorkorderManpowerDetailsVo();
				manpowerDetailsVos.setWorkSkillId(obj[0]+"");
				manpowerDetailsVos.setJobCode(obj[1]+"");
				manpowerDetailsVos.setJobCodeId((Integer)obj[2]);
				manpowerDetailsVos.setHeadCount((Integer)obj[3]);
				manpowerDetailsVos.setFromDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[4]));
				manpowerDetailsVos.setToDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[5]));
				manpowerDetailsVos.setDuration((Integer)obj[6]);
				workorderDetailInfoVo.getWorkorderManpowerDetailsVo().add(manpowerDetailsVos);
			}
			
			
			List<WorkorderResponsibilities>  WorkorderResponsibilities = sessionFactory.getCurrentSession().createQuery(" from WorkorderResponsibilities where workorderDetailInfo="+orderId).list();
			
			for(WorkorderResponsibilities responsibilities : WorkorderResponsibilities){
				WorkorderResponsibilitiesVo responsibilitiesVo = new WorkorderResponsibilitiesVo();				
				responsibilitiesVo.setCharacteristics(responsibilities.getCharacteristics());
				responsibilitiesVo.setResponsibilityTakenBy(responsibilities.getResponsibilityTakenBy());
				responsibilitiesVo.setCompanyShare(responsibilities.getCompanyShare());
				responsibilitiesVo.setVendorShare(responsibilities.getVendorShare());
				responsibilitiesVo.setAdditionalInfo(responsibilities.getAdditionalInfo());
				workorderDetailInfoVo.getWorkorderResponsibilitiesVo().add(responsibilitiesVo);
			}
			
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return workorderDetailInfoVo;
	}

	@Override
	public List<SimpleObject> getTransactionDatesOfWorkOrderHistory(Integer customerId, Integer companyId,
			Integer workerOrderId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List countryListTemp = session.createSQLQuery(" SELECT `WorkOrder_Info_id` AS id ,CONCAT(DATE_FORMAT(`Transaction_Date`,'%d/%m/%Y'),' - ',`Sequence_Id`) AS cname   FROM `workorder_detail_info`   WHERE `Customer_Id` ="+customerId+" AND Company_Id = "+companyId+" AND `WorkOrder_id`="+workerOrderId+"  ORDER BY Transaction_Date,Sequence_Id ").list();
			for (Object countryObject  : countryListTemp) {
				Object[] obj = (Object[]) countryObject;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)obj[0]);
				object.setName(obj[1]+"");
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getTransactionListForAddress()  ::  "+transactionList);
		}
		log.info("Exiting from getTransactionListForAddress()  ::  "+transactionList);
		return transactionList;

	}

	@Override
	public List<SimpleObject> getWorkOrderAsDropDown(String customerId, String companyId, String vendorId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> workOrderList = new ArrayList<>();
		try {
			String query = " SELECT awo.`WorkOrder_id`,CONCAT(`WorkOrder_Code`,' - ',`Work_Order_Name`) FROM  `associate_work_order` awo INNER JOIN  `workrorder_detail` wd ON (awo.`WorkOrder_id` = wd.`WorkOrder_id`) INNER JOIN `workorder_detail_info` wdi ON (awo.`WorkOrder_id` = wdi.`WorkOrder_id`) "
							+" WHERE  CONCAT(DATE_FORMAT(wdi.transaction_date, '%Y%m%d'), wdi.`Sequence_Id`) =    "
							+" ( SELECT MAX(CONCAT(DATE_FORMAT(wdi1.transaction_date, '%Y%m%d'), wdi1.`Sequence_Id`)) "  
							+" FROM workorder_detail_info wdi1 WHERE wdi.`WorkOrder_id` = wdi1.`WorkOrder_id`   "
							+" AND  awo.`Customer_Id`="+customerId+" AND  awo.`Company_Id`="+companyId+"   AND  awo.`Vendor_Id`="+vendorId
							+" AND wdi1.transaction_date <= CURRENT_DATE() ) ORDER BY  WorkOrder_Code	 ";
			List countryListTemp = session.createSQLQuery(query).list();
			SimpleObject object = new SimpleObject();
			object.setId(0);
			object.setName("All");
			workOrderList.add(object);
			for (Object countryObject  : countryListTemp) {
				Object[] obj = (Object[]) countryObject;
				 object = new SimpleObject();
				object.setId((Integer)obj[0]);
				object.setName(obj[1]+"");
				workOrderList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getWorkOrderAsDropDown()  ::  "+workOrderList);
		}
		log.info("Exiting from getWorkOrderAsDropDown()  ::  "+workOrderList);
		return workOrderList;

	}

	/*
	 * This method will be used to save the division_details and division_details_info
	 */
	@Override
	public Integer saveDivision(DivisionVo paramDivision) {
		log.info("Entered in saveDivision()  ::   "+paramDivision);

		Session session = sessionFactory.getCurrentSession();
		Integer divisionDetailsId = 0;
		Integer divisionId = 0,companyId = 0;
		Integer cid =0,cidInfoId=0;
		BigInteger divisionSequenceId = new BigInteger("0");	
		
		
		DivisionDetails divisionDetails = new DivisionDetails();
		DivisionDetailsInfo divisionDetailsInfo = new DivisionDetailsInfo();
		CompanyDetails companyDetails = new CompanyDetails();
		CompanyDetailsInfo companyDetailsInfo = new CompanyDetailsInfo();
				
		try{									
			if(paramDivision != null && paramDivision.getDivisionId() > 0){
				
				divisionDetails = (DivisionDetails) session.load(DivisionDetails.class, paramDivision.getDivisionId());
				divisionDetails.setCustomerDetails(new CustomerDetails(paramDivision.getCustomerId()));
				divisionDetails.setCompanyDetails(new CompanyDetails(paramDivision.getCompanyId()));
				divisionDetails.setDivisionCode(paramDivision.getDivisionCode().toUpperCase());
				divisionDetails.setModifiedBy(paramDivision.getModifiedBy());
				divisionDetails.setModifiedDate(new Date());
				session.update(divisionDetails);
				divisionId  = paramDivision.getDivisionId();
				
				List companyUpdate = session.createSQLQuery(" SELECT Company_Id FROM Company_Details WHERE Company_Code= '"+paramDivision.getDivisionCode()+"'").list();
				if(companyUpdate != null && companyUpdate.size() > 0){
					companyId = (Integer) companyUpdate.get(0);
				}else{
					companyDetails = new CompanyDetails();
					companyDetails.setCustomerDetails(new CustomerDetails(paramDivision.getCustomerId()));
					companyDetails.setCompanyCode(paramDivision.getDivisionCode().toUpperCase());
					companyDetails.setIsActive("Y");
					companyDetails.setCreatedBy(paramDivision.getCreatedBy());
					companyDetails.setCreatedDate(new Date());
					companyDetails.setModifiedBy(paramDivision.getModifiedBy());
					companyDetails.setModifiedDate(new Date());				
					companyDetails.setIsDivision("YES");
					companyDetails.setDivisionCompanyId(paramDivision.getCompanyId());
					companyId = (Integer) session.save(companyDetails);
				}
			}else{
				
				divisionDetails = new DivisionDetails();
				divisionDetails.setCustomerDetails(new CustomerDetails(paramDivision.getCustomerId()));
				divisionDetails.setCompanyDetails(new CompanyDetails(paramDivision.getCompanyId()));
				divisionDetails.setDivisionCode(paramDivision.getDivisionCode().toUpperCase());
				divisionDetails.setCreatedBy(paramDivision.getCreatedBy());
				divisionDetails.setModifiedBy(paramDivision.getModifiedBy());
				divisionDetails.setCreatedDate(new Date());
				divisionDetails.setModifiedDate(new Date());
				divisionId = (Integer) session.save(divisionDetails);
				
				companyDetails = new CompanyDetails();
				companyDetails.setCustomerDetails(new CustomerDetails(paramDivision.getCustomerId()));
				companyDetails.setCompanyCode(paramDivision.getDivisionCode().toUpperCase());
				companyDetails.setIsActive("Y");
				companyDetails.setCreatedBy(paramDivision.getCreatedBy());
				companyDetails.setCreatedDate(new Date());
				companyDetails.setModifiedBy(paramDivision.getModifiedBy());
				companyDetails.setModifiedDate(new Date());				
				companyDetails.setIsDivision("YES");
				companyDetails.setDivisionCompanyId(paramDivision.getCompanyId());
				companyId = (Integer) session.save(companyDetails);
				
				
			}
			
			session.clear();
			//division details info table
			if(paramDivision != null  && paramDivision.getDivisionDetailsId() > 0){
				divisionDetailsInfo = (DivisionDetailsInfo) sessionFactory.getCurrentSession().load(DivisionDetailsInfo.class, paramDivision.getDivisionDetailsId());
				divisionDetailsInfo.setDivisionDetails(new DivisionDetails(divisionId));
				divisionDetailsInfo.setCustomerDetails(new CustomerDetails(paramDivision.getCustomerId()));
				divisionDetailsInfo.setCompanyDetails(new CompanyDetails(paramDivision.getCompanyId()));
				divisionDetailsInfo.setDivisionName(paramDivision.getDivisionName());
				divisionDetailsInfo.setShortName(paramDivision.getShortName());
				divisionDetailsInfo.setCountry(new MCountry(paramDivision.getCountryId()));
				divisionDetailsInfo.setStatus(paramDivision.getStatus());
				divisionDetailsInfo.setTransactionDate(paramDivision.getTransactionDate());
				divisionDetailsInfo.setModifiedBy(paramDivision.getModifiedBy());
				divisionDetailsInfo.setModifiedDate(new Date());
				session.update(divisionDetailsInfo);
				divisionDetailsId = divisionDetailsInfo.getDivisionDetailsId();
				
				
				
				List s = session.createSQLQuery("SELECT com.company_id,com.Company_Info_Id FROM company_details cd "
						+ " 	INNER JOIN company_details_info com ON (cd.company_id = com.company_id) "
						+ " 		WHERE "
						+ " 		CONCAT(DATE_FORMAT(com.transaction_date, '%Y%m%d'), LPAD(com.Company_Sequence_Id, 2, '0')) = "
						+ " 		 ( "
						+ " 		 SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.`Company_Sequence_Id`, 2, '0'))) "
						+ " 		 FROM company_details_info vdi1 "
						+ " 		 WHERE com.`company_id` = vdi1.  `company_id` "
						+ " 		  AND vdi1.transaction_date <= CURRENT_DATE()  "
						+ " 		 )  AND `IsDivision`= 'YES' AND CD.company_code= '"+paramDivision.getDivisionCode()+"'").list();
				
				for(Object o : s){
					Object[] x = (Object[]) o;
					 cid = (Integer) x[0];
					 cidInfoId = (Integer)x[1];
				}
				
				if(cidInfoId >0){				
				companyDetailsInfo = (CompanyDetailsInfo) sessionFactory.getCurrentSession().load(CompanyDetailsInfo.class, cidInfoId);
				companyDetailsInfo.setCompanyDetails(new CompanyDetails(cid));				
				companyDetailsInfo.setCompanyName(paramDivision.getDivisionName());										
				companyDetailsInfo.setTransactionDate(paramDivision.getTransactionDate());
				companyDetailsInfo.setIsActive(paramDivision.getStatus());							
				companyDetailsInfo.setModifiedBy(paramDivision.getModifiedBy());
				companyDetailsInfo.setModifiedDate(new Date());
				session.update(companyDetailsInfo);
				cidInfoId = companyDetailsInfo.getCompanyInfoId();
				
				}
				
				
				
			}else{	
				divisionDetailsInfo = new DivisionDetailsInfo();
				divisionDetailsInfo.setDivisionDetails(new DivisionDetails(divisionDetails.getDivisionId()));
				divisionDetailsInfo.setCustomerDetails(new CustomerDetails(paramDivision.getCustomerId()));
				divisionDetailsInfo.setCompanyDetails(new CompanyDetails(paramDivision.getCompanyId()));
				divisionDetailsInfo.setDivisionName(paramDivision.getDivisionName());
				divisionDetailsInfo.setShortName(paramDivision.getShortName());
				divisionDetailsInfo.setCountry(new MCountry(paramDivision.getCountryId()));
				divisionDetailsInfo.setStatus(paramDivision.getStatus());
				divisionDetailsInfo.setTransactionDate(paramDivision.getTransactionDate());
				divisionDetailsInfo.setCreatedBy(paramDivision.getCreatedBy());
				divisionDetailsInfo.setModifiedBy(paramDivision.getModifiedBy());
				divisionDetailsInfo.setCreatedDate(new Date());
				divisionDetailsInfo.setModifiedDate(new Date());
				
				if(divisionId != null && divisionId > 0 ){
					divisionSequenceId  = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Division_Sequence_Id),0) FROM division_details_info division"
							+ "  WHERE  division.Division_Id = "+paramDivision.getDivisionId() +" AND division.`Transaction_Date` = '"+DateHelper.convertDateToSQLString(paramDivision.getTransactionDate())+"' and division.Customer_Id='"+paramDivision.getCustomerId()+"' And division.Company_Id = '"+paramDivision.getCompanyId()+"'").list().get(0);
					log.info("Division sequence Id : "+divisionSequenceId);
				}	
				divisionDetailsInfo.setDivisionSequenceId(divisionSequenceId.intValue()+1);			
				divisionDetailsId = (Integer) session.save(divisionDetailsInfo);
				
				
				BigInteger sequenceId = new BigInteger("0");				
				companyDetailsInfo.setCompanyDetails(new CompanyDetails(companyId));
				companyDetailsInfo.setCustomerDetails(new CustomerDetails(paramDivision.getCustomerId()));
				companyDetailsInfo.setCompanyName(paramDivision.getDivisionName());
				companyDetailsInfo.setCountryId(paramDivision.getCountryId());							
				companyDetailsInfo.setTransactionDate(paramDivision.getTransactionDate());
				companyDetailsInfo.setIsActive(paramDivision.getStatus());			
				companyDetailsInfo.setCreatedBy(paramDivision.getCreatedBy());
				companyDetailsInfo.setCreatedDate(new Date());
				companyDetailsInfo.setModifiedBy(paramDivision.getModifiedBy());
				companyDetailsInfo.setModifiedDate(new Date());				
				if(companyId != null && companyId > 0)					
					sequenceId  = (BigInteger) session.createSQLQuery("SELECT COALESCE(MAX(`Company_Sequence_Id`),0) AS id FROM `company_details_info`  WHERE  Company_Id = "+companyId+" AND `Transaction_Date` = '"+DateHelper.convertDateToSQLString(paramDivision.getTransactionDate())+"' and Customer_Id='"+paramDivision.getCustomerId()+"'").list().get(0);
				
				companyDetailsInfo.setCompanySequenceId(sequenceId.intValue()+1);			
				session.save(companyDetailsInfo);
				
			}
			
			session.flush();
		}catch (Exception e) {
			 if(!(session.getTransaction().wasRolledBack())){
				 session.getTransaction().rollback();
			 }
			 log.error("Error Occurred...   ", e);
			 log.info("Exiting from saveDivision()  ::  divisionDetailsId =  "+divisionDetailsId);
		}				
		log.info("Exiting from saveDivision()  ::  divisionDetailsId =  "+divisionDetailsId);
		return divisionDetailsId;
	}

	/*
	 * This method will be used to get the division details by divisionDetailsId
	 */
	@Override
	public List<DivisionVo> getDivisionById(Integer divisionDetailsId) {
		log.info("Entered in  getDivisionById()  ::   divisionDetailsId = "+divisionDetailsId);
		Session session = sessionFactory.getCurrentSession();
		DivisionVo division = new DivisionVo();
		List<DivisionVo> returnList = new ArrayList<DivisionVo>();
		String  hqlQuery = " SELECT info.Customer_Id, info.Company_Id, info.Division_Details_Id, info.Division_Id, division.Division_Code, "
							+ "	info.Division_Name, info.Short_Name, info.Country_Id,"
							+ " info.Transaction_Date, info.Status "							
							+ " FROM division_details_info AS info "
							+ " LEFT JOIN division_details division ON division.Division_Id = info.Division_Id"
							+ " LEFT JOIN company_details AS cmp  ON info.Customer_Id = cmp.Customer_Id  "
																			+ "	AND info.Company_Id = cmp.Company_Id "
							+ " LEFT JOIN company_details_info AS company  ON company.Customer_Id = info.Customer_Id  "
																			+ "	AND company.Company_Id = info.Company_Id "
							+ " LEFT JOIN customer_details AS cus  ON info.Customer_Id = cus.Customer_Id "
							+ " LEFT JOIN customer_details_info AS customer  ON customer.Customer_Id = info.Customer_Id "
							+ " WHERE info.Division_Details_Id = "+divisionDetailsId;
		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			List divisionList = query.list();

			for (Object object : divisionList) {
				Object[] obj = (Object[]) object;
				division = new DivisionVo();
				division.setCustomerId((Integer)obj[0]);
				division.setCompanyId((Integer)obj[1]);
				division.setDivisionDetailsId((Integer)obj[2]);
				division.setDivisionId((Integer)obj[3]);
				division.setDivisionCode((String)obj[4]);
				division.setDivisionName((String)obj[5]);
				division.setShortName((String)obj[6]);
				division.setCountryId((Integer)obj[7]);
				division.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[8]));
				division.setStatus(obj[9]+"");
				
				returnList.add(division);
			}	
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from  getDivisionById()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from  getDivisionById()  ::   "+returnList);
		return returnList;
	}

	/*
	 * This method will be used to get all the division details by customerId, companyId, countryId,
	 * 																			 divisionName and status
	 */
	@Override
	public List<DivisionVo> getDivisionsListBySearch(DivisionVo paramDivision) {
		log.info("Entered in  getDivisionsListBySearch()  ::   divisionVo  = "+paramDivision);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<DivisionVo> returnList = new ArrayList<DivisionVo>();
		DivisionVo division = new DivisionVo();
		
		String hqlQuery = "SELECT company.Company_Name, customer.Customer_Name, info.Division_Id,  "
							+ "	info.Division_Name, info.Transaction_Date, info.Status, info.Division_Details_Id , "
							+ " division.Division_Code, country.Country_Name"
							+ " FROM division_details_info AS info "
							+ " LEFT JOIN division_details division ON division.Division_Id = info.Division_Id "
							+ " LEFT JOIN company_details_info AS company  ON company.Customer_Id = info.Customer_Id  	AND company.Company_Id = info.Company_Id "
																				+ "	AND  CONCAT(DATE_FORMAT(company.Transaction_Date, '%Y%m%d'), company.Company_Sequence_Id) = ( "
																				+"	SELECT MAX(CONCAT(DATE_FORMAT(company1.Transaction_Date, '%Y%m%d'), company1.Company_Sequence_Id)) "
																				+"	FROM company_details_info company1 "
																				+"	WHERE company.Company_Id = company1.Company_Id AND company1.Transaction_Date <= CURRENT_DATE() "
																				+ " ) "					
							+ " LEFT JOIN customer_details_info AS customer  ON customer.Customer_Id = info.Customer_Id  "
																				+ " AND  CONCAT(DATE_FORMAT(customer.Transaction_Date, '%Y%m%d'), customer.Customer_Sequence_Id) =  ( "
																				+"	SELECT MAX(CONCAT(DATE_FORMAT(customer1.Transaction_Date, '%Y%m%d'), customer1.Customer_Sequence_Id)) "
																				+"	FROM customer_details_info customer1 "
																				+"	WHERE customer.Customer_Id = customer1.Customer_Id AND customer1.Transaction_Date <= CURRENT_DATE() "
																				+"	) "
							+ " LEFT JOIN m_country as country ON info.Country_Id = country.Country_Id"
								+ " WHERE info.`Transaction_Date` <= CURRENT_DATE() "
								+ " AND customer.Is_Active = 'Y' "
								+ " AND company.Is_Active = 'Y' "
								
								+ " AND  CONCAT(DATE_FORMAT(info.Transaction_Date, '%Y%m%d'), info.Division_Sequence_Id) =  "
										+"	( "
										+"	SELECT MAX(CONCAT(DATE_FORMAT(info1.Transaction_Date, '%Y%m%d'), info1.Division_Sequence_Id)) "
										+"	FROM division_details_info info1 "
										+"	WHERE info.Customer_Id = info1.Customer_Id AND info.Company_Id = info1.Company_Id AND info.Division_Id = info1.Division_Id AND info1.Transaction_Date <= CURRENT_DATE() "
										+"	) ";
			
		if(paramDivision.getCustomerId() > 0){
			hqlQuery += "  AND  info.Customer_Id = "+paramDivision.getCustomerId();
		}
		
		if(paramDivision.getCompanyId() > 0){
			hqlQuery += "  AND  info.Company_Id = "+paramDivision.getCompanyId();
		}
		
		if(paramDivision.getDivisionName() != null && !paramDivision.getDivisionName().isEmpty()){
			hqlQuery += " AND info.Division_Name LIKE '"+paramDivision.getDivisionName()+"%' ";
		}
		
		if(paramDivision.getDivisionCode() != null && !paramDivision.getDivisionCode().isEmpty()){
			hqlQuery += " AND division.Division_Code = '"+paramDivision.getDivisionCode()+"' ";
		}
		
		if(paramDivision.getStatus() != null && !paramDivision.getStatus().isEmpty()){
			hqlQuery += " AND info.Status LIKE '"+paramDivision.getStatus()+"%' ";
		}
		
		if(paramDivision.getDivisionId() > 0){
			hqlQuery += "  AND  info.Division_Id = "+paramDivision.getDivisionId();
		}
		
		
		hqlQuery += " GROUP BY info.Division_Id Order By info.Division_Name asc";
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){
				for (Object customer : queryList) {
					Object[] obj = (Object[]) customer;
					division = new DivisionVo();
					
					division.setCompanyName((String)obj[0]);
					division.setCustomerName((String)obj[1]);
					division.setDivisionId((Integer)obj[2]);
					division.setDivisionName((String)obj[3]);
					division.setTransactionDate((Date)obj[4]);
					division.setStatus((obj[5]+"").equalsIgnoreCase("Y") ? "Active":"Inactive");
					division.setDivisionDetailsId((Integer)obj[6]);
					division.setDivisionCode((String)obj[7]);
					division.setCountryName((String)obj[8]);
					
					returnList.add(division);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getDivisionsListBySearch()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getDivisionsListBySearch()  ::   "+returnList);
		return returnList;
	}

	/*
	 * This method will be used to get the transaction dates list for given customerId, companyId and divisionUniqueId
	 */
	@Override
	public List<SimpleObject> getTransactionListForDivision(Integer customerId,Integer companyId, Integer divisionId) {
		log.info("Entered in getTransactionListForDivision()  ::   customerId = "+customerId+" , companyId = "+companyId+" , divisionId = "+divisionId);
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List divisionList = session.createSQLQuery("SELECT Division_Details_Id AS id ,CONCAT(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),' - ',Division_Sequence_Id) AS name FROM division_details_info division  WHERE division.Customer_Id = "+customerId+" AND division.Company_Id = "+companyId+" AND division.Division_Id = "+divisionId+" ORDER BY division.Transaction_Date, division.Division_Details_Id asc").list();
			for (Object transDates  : divisionList) {
				Object[] transaction = (Object[]) transDates;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)transaction[0]);
				object.setName(transaction[1]+"");
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getTransactionListForDivision()  ::   "+transactionList);
		}
		
		log.info("Exiting from getTransactionListForDivision()  ::   "+transactionList);
		return transactionList;
	}
	
	/*
	 * This method will be used to validate the company code 
	 */
	@Override
	public Integer validateCompanyCode(String customerId, String companyCode) {
		log.info("Entered in validateCompanyCode()  ::   customerId = "+customerId+" , companyCode = "+companyCode);
		Session session = sessionFactory.getCurrentSession();

		Integer returnString = null;
		try {
			
			List tempList =  session.createSQLQuery("SELECT Company_Id FROM company_details WHERE Company_Code = '"+companyCode+"' AND Customer_Id = "+customerId).list();	
			System.out.println("TempList: "+tempList.size());
			if(tempList != null && tempList.size() > 0){
				returnString = 0;
			}else{
				returnString = 1;
			}
			
		} catch (Exception e) {
			log.error("Error Occured ",e);
			returnString = 1;
		}
		return returnString;
	}

	
	@Override
	public boolean validateCompanyAddress(Integer customerId,Integer companyId, Integer addressTypeId){
		log.info("Entered in validateCompanyAddress  ::  customerId = "+" , companyId = "+companyId+" , addressTypeId = ");
		Session session = sessionFactory.getCurrentSession();
		boolean returnFlag = true;
		try{
			List addressList = session.createQuery(" FROM CompanyAddress WHERE customerDetails = "+customerId +" AND companyDetails = "+companyId+" AND MAddressType = '"+addressTypeId+"' ").list();
			if(addressList != null && addressList.size() > 0){
				returnFlag = false;
			}else{
				returnFlag = true;
			}
		}catch(Exception e){
			returnFlag = false;
			log.error("Exception Occurred...",e);
			log.info("Exiting from validateCompanyAddress with exception :: returnFlag = "+returnFlag);
		}
		log.info("Exiting from validateCompanyAddress :: returnFlag = "+returnFlag);
		return returnFlag;
	}
}
