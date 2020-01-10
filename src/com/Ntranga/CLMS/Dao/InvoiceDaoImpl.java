package com.Ntranga.CLMS.Dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.InvoiceDetailsVo;
import com.Ntranga.CLMS.vo.InvoiceParticularsVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.VendorDetailsVo;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.InvoiceDetails;
import com.Ntranga.core.CLMS.entities.InvoiceParticulars;
import com.Ntranga.core.CLMS.entities.VendorDetails;
import common.Logger;


@SuppressWarnings({"rawtypes"})
@Transactional
@Repository("invoiceDao")
public class InvoiceDaoImpl implements InvoiceDao {

	private static Logger log = Logger.getLogger(InvoiceDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	
	@Override
	public Integer saveInvoice(InvoiceDetailsVo paramInvoice) {
		log.info("Entered in saveInvoice  ::  paramInvoice =  "+paramInvoice);
		
		Session session = sessionFactory.getCurrentSession(); 
		Integer invoiceId = 0;
		InvoiceDetails details = new InvoiceDetails();
		//InvoiceDocuments documents = new InvoiceDocuments();
		InvoiceParticulars particulars = new InvoiceParticulars();
		
		try{
			if(paramInvoice != null && paramInvoice.getInvoiceId() != null && paramInvoice.getInvoiceId() > 0 ){
				details = (InvoiceDetails) session.load(InvoiceDetails.class, paramInvoice.getInvoiceId());
			}
			
			details.setCustomerDetails(new CustomerDetails(paramInvoice.getCustomerId()));
			details.setCompanyDetails(new CompanyDetails(paramInvoice.getCompanyId()));
			details.setVendorDetails(new VendorDetails(paramInvoice.getVendorId()));
			details.setCompanyAddressId(paramInvoice.getCompanyAddressId());
			details.setCompanyContactId(paramInvoice.getCompanyContactId());
			details.setCompanyAddress(paramInvoice.getCompanyAddress());
			details.setCompanyContactNumber(paramInvoice.getCompanyContactNumber());
			details.setVendorAddress(paramInvoice.getVendorAddress());
			details.setVendorContactNumber(paramInvoice.getVendorContactNumber());
			details.setVendorEmail(paramInvoice.getVendorEmail());
			details.setInvoiceNumber(paramInvoice.getInvoiceNumber());
			details.setInvoiceDate(paramInvoice.getInvoiceDate());
			details.setServiceNumber(paramInvoice.getServiceNumber());
			details.setPrNumber(paramInvoice.getPrNumber());
			details.setPoNumber(paramInvoice.getPoNumber());
			details.setStatus(paramInvoice.getStatus());
			details.setServiceTaxNumber(paramInvoice.getServiceTaxNumber());
			details.setPanNumber(paramInvoice.getPanNumber());
			details.setRemarks(paramInvoice.getRemarks());
			details.setSubjectTo(paramInvoice.getSubjectTo());
			details.setComments(paramInvoice.getComments());
			details.setTotalAmount(paramInvoice.getTotalAmount());
			details.setTotalAmountInWords(paramInvoice.getTotalAmountInWords());
			details.setModifiedBy(paramInvoice.getModifiedBy());
			details.setModifiedDate(new Date());
			
			
			
			if(paramInvoice != null && paramInvoice.getInvoiceId() != null && paramInvoice.getInvoiceId() > 0 ){
				session.update(details);
				invoiceId = paramInvoice.getInvoiceId();
			}else{
				details.setCreatedBy(paramInvoice.getCreatedBy());
				details.setCreatedDate(new Date());
				
				invoiceId = (Integer) session.save(details);
			}

			session.flush();
			log.info(" Invoice Id : "+invoiceId);
			
			
			if(paramInvoice.getParticularsList() != null && paramInvoice.getParticularsList().size() > 0){
				if(paramInvoice != null && paramInvoice.getInvoiceId() != null && paramInvoice.getInvoiceId() > 0 ){
					Query q = session.createQuery("DELETE FROM InvoiceParticulars  WHERE  customerDetails = "+paramInvoice.getCustomerId() +" AND companyDetails = "+paramInvoice.getCompanyId() +" AND invoiceId = "+invoiceId);
					q.executeUpdate();
				}
				
				for(InvoiceParticularsVo particularsVo : paramInvoice.getParticularsList()){
					particulars = new InvoiceParticulars();

					particulars.setCustomerDetails(new CustomerDetails(paramInvoice.getCustomerId()));
					particulars.setCompanyDetails(new CompanyDetails(paramInvoice.getCompanyId()));
					particulars.setVendorDetails(new VendorDetails(paramInvoice.getVendorId()));
					particulars.setInvoiceId(invoiceId);
					particulars.setParticulars(particularsVo.getParticulars());
					particulars.setFromDate(particularsVo.getFromDate());
					particulars.setToDate(particularsVo.getToDate());
					particulars.setFrequency(particularsVo.getFrequency());
					particulars.setAmount(particularsVo.getAmount());
					particulars.setQuantity(particularsVo.getQuantity());
					particulars.setCurrencyId(particularsVo.getCurrencyId());	
					particulars.setModifiedBy(paramInvoice.getModifiedBy());
					particulars.setModifiedDate(new Date());
					particulars.setCreatedBy(paramInvoice.getCreatedBy());				
					particulars.setCreatedDate(new Date());
					session.save(particulars);
				}
			}
			
			session.flush();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			log.info("Exiting from saveInvoice()  ::   "+invoiceId);
		}
		log.info("Exiting from saveInvoice()  ::   "+invoiceId);
		return invoiceId;
	}

	/*
	 * This method will be used to search the existing invoices
	 */
	@Override
	public List<InvoiceDetailsVo> getInvoicesListBySearch(InvoiceDetailsVo paramInvoice) {
		log.info("Entered in  getInvoicesListBySearch()  ::   paramInvoice  = "+paramInvoice);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<InvoiceDetailsVo> returnList = new ArrayList<InvoiceDetailsVo>();
		InvoiceDetailsVo invoice = new InvoiceDetailsVo();
		
		String hqlQuery = "SELECT  invoice.Invoice_Number, invoice.Invoice_Date, invoice.Status, invoice.Invoice_Id"
							+ " FROM invoice_details AS invoice "
							+ " LEFT JOIN Customer_View cv ON cv.Customer_Id = invoice.Customer_Id "
							+ " LEFT JOIN Company_View cmpv ON cmpv.Customer_Id = invoice.Customer_Id AND cmpv.Company_Id = invoice.Company_Id  "
							+ " WHERE 1= 1  "
							+ " AND cv.Is_Active='Y' "
							+ " AND cmpv.Is_Active = 'Y' ";
		
		
		if(paramInvoice.getCustomerId() > 0){
			hqlQuery += "  AND  invoice.Customer_Id = "+paramInvoice.getCustomerId();
		}
		
		if(paramInvoice.getCompanyId() != null && paramInvoice.getCompanyId() > 0){
			hqlQuery += "  AND  invoice.Company_Id = "+paramInvoice.getCompanyId();
		}
		
		if(paramInvoice.getVendorId() != null && paramInvoice.getVendorId() > 0){
			hqlQuery += "  AND  invoice.Vendor_Id = "+paramInvoice.getVendorId();
		}
		
		if(paramInvoice.getStatus() != null && !paramInvoice.getStatus().isEmpty()){
			hqlQuery += " AND invoice.Status = '"+paramInvoice.getStatus()+"' ";
		}
		
		if(paramInvoice.getInvoiceNumber() != null && paramInvoice.getInvoiceNumber() > 0){
			hqlQuery += " AND invoice.Invoice_Number LIKE '"+paramInvoice.getInvoiceNumber()+"%' ";
		}
		
		if(paramInvoice.getYear() != null && paramInvoice.getYear() > 0 && paramInvoice.getMonth() != null && paramInvoice.getMonth() > 0 ){
			hqlQuery += " AND YEAR(invoice.Invoice_Date)  = "+paramInvoice.getYear()+" AND MONTH(invoice.Invoice_Date =  "+paramInvoice.getMonth();
		}
		
		if(paramInvoice.getFromDate() != null && paramInvoice.getToDate() != null){
			hqlQuery += " AND invoice.Date_Of_Invoice BETWEEN '"+paramInvoice.getFromDate()+"' AND '"+paramInvoice.getToDate()+"' ";
		}
		
		hqlQuery += " GROUP BY invoice.Invoice_Id Order By invoice.Invoice_Number asc";
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){
				for (Object customer : queryList) {
					Object[] obj = (Object[]) customer;
					invoice = new InvoiceDetailsVo();

					invoice.setInvoiceNumber((Integer)obj[0]);
					invoice.setInvoiceDate((Date)obj[1]);
					invoice.setStatus((String)obj[2]);
					invoice.setInvoiceId((Integer)obj[3]);
					returnList.add(invoice);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getInvoicesListBySearch()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getInvoicesListBySearch()  ::   "+returnList);
		return returnList;
	}

	/*
	 * This method will be used to get the invoice details by invoice id
	 */
	@Override
	public List<InvoiceDetailsVo> getInvoiceById(Integer invoiceId) {
		log.info("Entered in  getInvoicesListBySearch()  ::   Invoice Id  = "+invoiceId);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<InvoiceDetailsVo> returnList = new ArrayList<InvoiceDetailsVo>();
		InvoiceDetailsVo invoice = new InvoiceDetailsVo();
		
		String hqlQuery = "SELECT  invoice.Customer_Id, invoice.Company_Id, invoice.Vendor_Id, invoice.Invoice_Number, invoice.Invoice_Date, invoice.Status, invoice.Service_Number, "
							+ " invoice.PR_Number, invoice.PO_Number, invoice.Service_Tax_Number, invoice.PAN_Number, invoice.Remarks, invoice.Comments, invoice.Subject_To,"
							+ " invoice.Total_Amount, invoice.Total_Amount_In_Words, invoice.Company_Address_Id, invoice.Company_Contact_Id, invoice.Company_Address, invoice.Vendor_Address, "
							+ " invoice.Company_Contact_Number, invoice.Vendor_Contact_Number, invoice.Vendor_Email "
							+ " FROM invoice_details AS invoice "
							+ " LEFT JOIN Customer_View cv ON cv.Customer_Id = invoice.Customer_Id "
							+ " LEFT JOIN Company_View cmpv ON cmpv.Customer_Id = invoice.Customer_Id AND cmpv.Company_Id = invoice.Company_Id  "
							+ " WHERE 1= 1  "
							+ " AND cv.Is_Active='Y' "
							+ " AND cmpv.Is_Active = 'Y' "
							+ " AND invoice.Invoice_Id = "+invoiceId;
		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){
				for (Object customer : queryList) {
					Object[] obj = (Object[]) customer;
					invoice = new InvoiceDetailsVo();
					invoice.setCustomerId((Integer)obj[0]);
					invoice.setCompanyId((Integer)obj[1]);
					invoice.setVendorId((Integer)obj[2]);
					invoice.setInvoiceNumber((Integer)obj[3]);
					invoice.setInvoiceDate((Date)obj[4]);
					invoice.setStatus((String)obj[5]);
					invoice.setServiceNumber((String)obj[6]);
					invoice.setPrNumber((String)obj[7]);
					invoice.setPoNumber((String)obj[8]);
					invoice.setServiceTaxNumber((String)obj[9]);
					invoice.setPanNumber((String)obj[10]);
					invoice.setRemarks((String)obj[11]);
					invoice.setComments((String)obj[12]);
					invoice.setSubjectTo((String)obj[13]);
					invoice.setTotalAmount((String)obj[14]);
					invoice.setTotalAmountInWords((String)obj[15]);
					invoice.setCompanyAddressId((Integer)obj[16]);
					invoice.setCompanyContactId((Integer)obj[17]);
					invoice.setCompanyAddress((String)obj[18]);
					invoice.setVendorAddress((String)obj[19]);
					invoice.setCompanyContactNumber((String)obj[20]);
					invoice.setVendorContactNumber((String)obj[21]);
					invoice.setVendorEmail((String)obj[22]);
					
					invoice.setInvoiceId(invoiceId);
					
					String particularsQuery = "SELECT  particulars.Invoice_Particular_Id, particulars.Particulars, particulars.From_Date, particulars.To_Date, particulars.Frequency, "
							+ " particulars.Rate, particulars.Amount, particulars.Currency_Id, currency.Currency, particulars.Quantity  "
							+ " FROM invoice_particulars AS particulars "
							//+ " LEFT JOIN Customer_View cv ON cv.Customer_Id = particulars.Customer_Id "
							//+ " LEFT JOIN Company_View cmpv ON cmpv.Customer_Id = particulars.Customer_Id AND cmpv.Company_Id = particulars.Company_Id  "
							+ " LEFT JOIN m_currency currency ON currency.Currency_Id = particulars.Currency_Id "
							+ " WHERE 1= 1  "
							+ " AND particulars.Invoice_Id = "+invoiceId;
					List particularsQueryList = session.createSQLQuery(particularsQuery).list();
					List<InvoiceParticularsVo> particularsList = new ArrayList<>();
					
					if(particularsQueryList.size() > 0){
						for (Object particular : particularsQueryList) {
							Object[] partcularObj = (Object[]) particular;
							InvoiceParticularsVo particularVo = new InvoiceParticularsVo();
							particularVo.setInvoiceParticularId((Integer)partcularObj[0]);
							particularVo.setParticulars((String)partcularObj[1]);
							particularVo.setFromDate(DateHelper.convertDate((Date)partcularObj[2]));
							particularVo.setToDate(DateHelper.convertDate((Date)partcularObj[3]));
							particularVo.setFrequency((String)partcularObj[4]);
							particularVo.setRate((String)partcularObj[5]);
							particularVo.setAmount((String)partcularObj[6]);
							particularVo.setCurrencyId((Integer)partcularObj[7]);
							particularVo.setCurrencyName((String)partcularObj[8]);
							particularVo.setQuantity((Integer)partcularObj[9]);
							
							particularsList.add(particularVo);
						}
					}	
						invoice.setParticularsList(particularsList);
					
					returnList.add(invoice);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getInvoiceById()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getInvoiceById()  ::   "+returnList);
		return returnList;
	}

	/*
	 * This method will be used to get the company address based on given customer Id and companyId
	 */
	@Override
	public List<SimpleObject> getCompanyAddressByCompanyId(Integer customerId,Integer companyId) {
		log.info("Entered in getCompanyAddressByCompanyId   ::   customerId = "+customerId+" , CompanyId = "+companyId);
		
		List<SimpleObject> objects = new ArrayList<SimpleObject>();
		try{
			Query query = sessionFactory.getCurrentSession().createSQLQuery(
									"SELECT Address_Id, CONCAT(COALESCE(CASE WHEN ca.address_1 IS NOT NULL THEN CONCAT(ca.address_1,', ') ELSE '' END ,'') , "
														   + " COALESCE(CASE WHEN ca.address_2 IS NOT NULL THEN CONCAT(ca.address_2,', ') ELSE '' END ,''), "
														   + " COALESCE(CASE WHEN ca.address_3 IS NOT NULL THEN CONCAT(ca.address_3,', ') ELSE '' END ,''), "
														   + " COALESCE(CASE WHEN ca.city_name  IS NOT NULL THEN CONCAT(ca.city_name,', ') ELSE '' END ,''), "
														   + " COALESCE(CASE WHEN ca.state_name  IS NOT NULL THEN CONCAT(ca.state_name,', ') ELSE '' END ,'' ), "
														   + " COALESCE(CASE WHEN ca.country_name  IS NOT NULL THEN CONCAT(ca.country_name) ELSE '' END ,'' ),"
														   + " COALESCE(', ',ca.pincode,''),'') AS companyAddr "
									+ " FROM company_addressview ca "
									+ " WHERE 1 = 1 "
											+ " AND Customer_Id = "+customerId
											+ " AND Company_Id = "+companyId);
								
			log.debug("Query  ::  "+query.getQueryString());
			
			List companyAddressList = query.list();
			for (Object company : companyAddressList) {
				Object[] companyArray = (Object[]) company;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)companyArray[0]);
				object.setName((String)companyArray[1]+". ");			
				objects.add(object);
			}		
			
		} catch (Exception e) {
			log.error("Error Occured...  ", e);
			log.info("Exiting from getCompanyAddressByCompanyId ::  "+objects);
		}
		log.info("Exiting from getCompanyAddressByCompanyId ::  "+objects);
		return objects;
		
	}

	/*
	 * This method will be used to get the company contact based on given addressId;
	 */

	@Override
	public List<SimpleObject> getCompanyContactByAddressId(Integer addressId) {
		log.info("Entered in  getCompanyContactByAddressId()  ::   addressId = "+addressId);
		Session session = sessionFactory.getCurrentSession();
		List contactsList = null;
		List<SimpleObject> returnList = new ArrayList<SimpleObject>();
		SimpleObject object = new SimpleObject();
		
		String hqlQuery = "SELECT  Company_Contact_Unique_Id, CASE WHEN contact.Business_Phone_Number IS NOT NULL THEN contact.Business_Phone_Number "
				+ "			ELSE (CASE WHEN contact.Mobile_Number IS NOT NULL THEN contact.Mobile_Number ELSE NULL END) END  AS phoneNumber"
				+ " FROM company_contact AS contact "
				+ " WHERE contact.Company_Address_Id = "+addressId
				+ " AND CONCAT(DATE_FORMAT(contact.Transaction_Date, '%Y%m%d'), contact.Company_Contact_Sequence_Id) =  "
						+ "	( "
						+ "	SELECT MAX(CONCAT(DATE_FORMAT(contact1.Transaction_Date, '%Y%m%d'), contact1.Company_Contact_Sequence_Id)) "
						+ "	FROM company_contact AS contact1 "
						+ "	WHERE contact.Customer_Id = contact1.Customer_Id AND contact.Company_Id = contact1.Company_Id AND contact.Company_Contact_Unique_Id = contact1.Company_Contact_Unique_Id AND contact1.Transaction_Date <= CURRENT_DATE() "
						+ "	) "
				+ " GROUP BY contact.Company_Address_Id ";
		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			contactsList = query.list();

			for (Object company : contactsList) {
				Object[] obj = (Object[]) company;
				object = new SimpleObject();
				
				object.setId((Integer)obj[0]);
				object.setName((String)obj[1]);
				
				returnList.add(object);
			}	
			
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Entered in  getCompanyContactByAddressId()  ::   "+returnList);
		}
		session.flush();
		log.info("Entered in  getCompanyContactByAddressId()  ::   "+returnList);
		return returnList;
	}
	
	/*
	 * This method will be used to get vendor address and vendor contact details based on given vendor customerId, companyId and vendorId
	 */
	@Override
	public List<VendorDetailsVo> getVendorAddressContactByVendorId(Integer customerId, Integer companyId, Integer vendorId) {
		log.info("Entered in getVendorAddressContactByVendorId   ::   customerId = "+customerId+" , companyId = "+companyId+", vendorId = "+vendorId);
		
		List<VendorDetailsVo> objects = new ArrayList<VendorDetailsVo>();
		try{
			Query query = sessionFactory.getCurrentSession().createSQLQuery(
									"SELECT  CONCAT(COALESCE(CASE WHEN va.Address_1 IS NOT NULL THEN CONCAT(va.Address_1,', ') ELSE '' END ,'') , "
														   + " COALESCE(CASE WHEN va.Address_2 IS NOT NULL THEN CONCAT(va.Address_2,', ') ELSE '' END ,''), "
														   + " COALESCE(CASE WHEN va.Address_3 IS NOT NULL THEN CONCAT(va.Address_3,', ') ELSE '' END ,''), "
														   + " COALESCE(CASE WHEN va.city_name  IS NOT NULL THEN CONCAT(va.city_name,', ') ELSE '' END ,''), "
														   + " COALESCE(CASE WHEN va.state_name  IS NOT NULL THEN CONCAT(va.state_name,', ') ELSE '' END ,'' ), "
														   + " COALESCE(CASE WHEN va.country_name  IS NOT NULL THEN CONCAT(va.country_name) ELSE '' END ,'' ),"
														   + " COALESCE(', ',va.pincode,''),'') AS Vendor_Address,  va.Telephone_Number, va.Email,  va.Vendor_Name "
									+ " FROM vendor_addressview va "
									+ " WHERE 1 = 1 "
											+ " AND Customer_Id = "+customerId
											+ " AND Company_Id = "+companyId
											+ " AND Vendor_Id = "+vendorId);
								
			log.debug("Query  ::  "+query.getQueryString());
			
			List vendorList = query.list();
			for (Object vendor : vendorList) {
				Object[] vendorArray = (Object[]) vendor;
				VendorDetailsVo vendorVo = new VendorDetailsVo();
				
				vendorVo.setVendorId(vendorId);
				vendorVo.setAddress((String)vendorArray[0]+". ");
				vendorVo.setTelephoneNumber((String)vendorArray[1]);	
				vendorVo.setEmail((String)vendorArray[2]);
				vendorVo.setVendorName((String)vendorArray[3]);
				objects.add(vendorVo);
			}		
			
		} catch (Exception e) {
			log.error("Error Occured...  ", e);
			log.info("Exiting from getVendorAddressContactByVendorId ::  "+objects);
		}
		log.info("Exiting from getVendorAddressContactByVendorId ::  "+objects);
		return objects;
	}

}
