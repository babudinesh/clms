package com.Ntranga.CLMS.Dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.CompanyDocumentsVo;
import com.Ntranga.CLMS.vo.VendorDocumentsVo;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CompanyDocuments;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.VendorDetails;
import com.Ntranga.core.CLMS.entities.VendorDocuments;
import common.Logger;


@SuppressWarnings({"rawtypes"})
@Transactional
@Repository("documentDao")
public class DocumentDaoImpl implements DocumentDao {

	private static Logger log = Logger.getLogger(DocumentDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	
	@Override
	public List<VendorDocumentsVo> getDocumentsListBySearch(VendorDocumentsVo paramDocuments) {
		log.info("Entered in  getDocumentsListBySearch()  ::   paramDocuments  = "+paramDocuments);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<VendorDocumentsVo> returnList = new ArrayList<VendorDocumentsVo>();
		VendorDocumentsVo document = new VendorDocumentsVo();
		
		String hqlQuery = "SELECT  document.Document_Name, document.Document_Date, document.Status, "
									+ " document.Vendor_Document_Id, document.Challan_Number "
							+ " FROM vendor_documents AS document "
							+ " LEFT JOIN Customer_View cv ON cv.Customer_Id = document.Customer_Id "
							+ " LEFT JOIN Company_View cmpv ON cmpv.Customer_Id = document.Customer_Id AND cmpv.Company_Id = document.Company_Id  "
							+ " WHERE 1= 1  "
							+ " AND cv.Is_Active='Y' "
							+ " AND cmpv.Is_Active = 'Y' ";
		
		
		if(paramDocuments.getCustomerId() > 0){
			hqlQuery += "  AND  document.Customer_Id = "+paramDocuments.getCustomerId();
		}
		
		if(paramDocuments.getCompanyId() != null && paramDocuments.getCompanyId() > 0){
			hqlQuery += "  AND  document.Company_Id = "+paramDocuments.getCompanyId();
		}
		
		if(paramDocuments.getVendorId() != null && paramDocuments.getVendorId() > 0){
			hqlQuery += "  AND  document.Vendor_Id = "+paramDocuments.getVendorId();
		}
		
		if(paramDocuments.getStatus() != null && !paramDocuments.getStatus().isEmpty()){
			hqlQuery += " AND document.Status = '"+paramDocuments.getStatus()+"' ";
		}
		
		if(paramDocuments.getDocumentName() != null && !paramDocuments.getDocumentName().isEmpty()){
			hqlQuery += " AND document.Document_Name = '"+paramDocuments.getDocumentName()+"' ";
		}
		
		if(paramDocuments.getYear() != null && paramDocuments.getYear() > 0 && paramDocuments.getMonth() != null && paramDocuments.getMonth() > 0 ){
			hqlQuery += " AND YEAR(document.Document_Date)  = "+paramDocuments.getYear()+" AND MONTH(document.Document_Date =  "+paramDocuments.getMonth();
		}
		
		if(paramDocuments.getFromDate() != null && paramDocuments.getToDate() != null){
			hqlQuery += " AND document.Document_Date BETWEEN '"+paramDocuments.getFromDate()+"' AND '"+paramDocuments.getToDate()+"' ";
		}
		
		hqlQuery += " GROUP BY document.Vendor_Document_Id Order By document.Document_Name asc";
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){
				for (Object doc : queryList) {
					Object[] obj = (Object[]) doc;
					document = new VendorDocumentsVo();

					document.setDocumentName((String)obj[0]);
					document.setDocumentDate((Date)obj[1]);
					document.setStatus((String)obj[2]);
					document.setVendorDocumentId((Integer)obj[3]);
					document.setChallanNumber((Integer)obj[4]);
					returnList.add(document);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getDocumentsListBySearch()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getDocumentsListBySearch()  ::   "+returnList);
		return returnList;
	}
	

	@Override
	public Integer saveVendorDocument(VendorDocumentsVo paramDocuments) {
		log.info("Entered in saveVendorDocument  ::  paramDocuments =  "+paramDocuments);
		
		Session session = sessionFactory.getCurrentSession(); 
		Integer vendorDocumentId = 0;
		VendorDocuments documents = new VendorDocuments();
		
		try{
			if(paramDocuments != null && paramDocuments.getVendorDocumentId() != null && paramDocuments.getVendorDocumentId() > 0 ){
				documents = (VendorDocuments) session.load(VendorDocuments.class, paramDocuments.getVendorDocumentId());
			}
			
			documents.setCustomerDetails(new CustomerDetails(paramDocuments.getCustomerId()));
			documents.setCompanyDetails(new CompanyDetails(paramDocuments.getCompanyId()));
			documents.setVendorDetails(new VendorDetails(paramDocuments.getVendorId()));
			documents.setChallanNumber(paramDocuments.getChallanNumber());
			documents.setDocumentDate(paramDocuments.getDocumentDate());
			documents.setDocumentName(paramDocuments.getDocumentName());
			documents.setDocumentFrequency(paramDocuments.getDocumentFrequency());
			documents.setAmount(paramDocuments.getAmount());
			documents.setCurrencyId(paramDocuments.getCurrencyId());	
			documents.setStatus(paramDocuments.getStatus());
			documents.setHeadCount(paramDocuments.getHeadCount());
			documents.setFilePath(paramDocuments.getFilePath());
			documents.setFileName(paramDocuments.getFileName());
			documents.setModifiedBy(paramDocuments.getModifiedBy());
			documents.setModifiedDate(new Date());
			documents.setComments(paramDocuments.getComments());
			
	
			if(paramDocuments != null && paramDocuments.getVendorDocumentId() != null && paramDocuments.getVendorDocumentId() > 0 ){
				session.update(documents);
				vendorDocumentId = paramDocuments.getVendorDocumentId();
			}else{
				documents.setCreatedBy(paramDocuments.getCreatedBy());
				documents.setCreatedDate(new Date());
				session.save(documents);
				vendorDocumentId = (Integer) session.save(documents);
			}

			session.flush();

			
			log.info(" Vendor Document Id : "+vendorDocumentId);	
		
			session.flush();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			log.info("Exiting from saveVendorDocument()  ::   "+vendorDocumentId);
		}
		log.info("Exiting from saveVendorDocument()  ::   "+vendorDocumentId);
		return vendorDocumentId;
	}

	/*
	 * This method will be used to get the vendor document details by vendorDocumentId
	 */
	@Override
	public List<VendorDocumentsVo> getVendorDocumentById(Integer vendorDocumentId) {
		log.info("Entered in  getVendorDocumentById()  ::   vendorDocumentId  = "+vendorDocumentId);
		
		Session session = sessionFactory.getCurrentSession();
		List<VendorDocumentsVo> documentsList = new ArrayList<>();
		
		try {				
			String documentQuery = "SELECT  document.Vendor_Document_Id, document.Challan_Number, document.Document_Date, document.Document_Name, "
					+ " document.Amount, document.Currency_Id, currency.Currency, document.File_Path, document.File_Name, "
					+ " document.Document_Frequency, document.Head_Count, vv.Vendor_Code, vv.Vendor_Name, vv.Vendor_Id,"
					+ " document.Status, document.Comments, document.Customer_Id, document.Company_Id "
					+ " FROM vendor_documents AS document "
					//+ " LEFT JOIN Customer_View cv ON cv.Customer_Id = document.Customer_Id "
					//+ " LEFT JOIN Company_View cmpv ON cmpv.Customer_Id = document.Customer_Id AND cmpv.Company_Id = document.Company_Id  "
					+ " LEFT JOIN m_currency currency ON currency.Currency_Id = document.Currency_Id "
					+ " LEFT JOIN Vendor_View vv ON vv.Customer_Id = document.Customer_Id AND vv.Company_Id = document.Company_Id AND vv.Vendor_Id = document.Vendor_Id  "
					+ " WHERE 1= 1  "
					//+ " AND cv.Is_Active='Y' "
					//+ " AND cmpv.Is_Active = 'Y' "
					+ " AND document.Vendor_Document_Id = "+vendorDocumentId
					+ " GROUP BY document.Vendor_Document_Id ";
			List documentsQueryList = session.createSQLQuery(documentQuery).list();
			
			if(documentsQueryList.size() > 0){
				for (Object document : documentsQueryList) {
					Object[] documentObj = (Object[]) document;
					VendorDocumentsVo documentVo = new VendorDocumentsVo();
					
					documentVo.setVendorDocumentId((Integer)documentObj[0]);
					documentVo.setChallanNumber((Integer)documentObj[1]);
					documentVo.setDocumentDate(DateHelper.convertDate((Date)documentObj[2]));
					documentVo.setDocumentName((String)documentObj[3]);
					documentVo.setAmount((String)documentObj[4]);
					documentVo.setCurrencyId((Integer)documentObj[5]);
					documentVo.setCurrencyName((String)documentObj[6]);
					documentVo.setFilePath((String)documentObj[7]);
					documentVo.setFileName((String)documentObj[8]);
					documentVo.setDocumentFrequency((String)documentObj[9]);
					documentVo.setHeadCount((Integer)documentObj[10]);
					documentVo.setVendorCode((String)documentObj[11]);
					documentVo.setVendorName((String)documentObj[12]);
					documentVo.setVendorId((Integer)documentObj[13]);
					documentVo.setStatus((String)documentObj[14]);
					documentVo.setComments((String)documentObj[15]);
					documentVo.setCustomerId((Integer)documentObj[16]);
					documentVo.setCompanyId((Integer)documentObj[17]);
					
					documentsList.add(documentVo);
				}
			}	
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getVendorDocumentById()  ::   "+documentsList);
		}
		session.flush();
		log.info("Exiting from getVendorDocumentById()  ::   "+documentsList);
		return documentsList;
	}


	@Override
	public List<CompanyDocumentsVo> getCompanyDocumentsListBySearch(CompanyDocumentsVo paramDocuments) {
		log.info("Entered in  getCompanyDocumentsListBySearch()  ::   paramDocuments  = "+paramDocuments);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<CompanyDocumentsVo> returnList = new ArrayList<CompanyDocumentsVo>();
		CompanyDocumentsVo document = new CompanyDocumentsVo();
		
		String hqlQuery = "SELECT  document.Document_Name, document.Document_Date, document.Status, "
									+ " document.Company_Document_Id, document.Challan_Number "
							+ " FROM company_documents AS document "
							+ " LEFT JOIN Customer_View cv ON cv.Customer_Id = document.Customer_Id "
							+ " LEFT JOIN Company_View cmpv ON cmpv.Customer_Id = document.Customer_Id AND cmpv.Company_Id = document.Company_Id  "
							+ " WHERE 1= 1  "
							+ " AND cv.Is_Active='Y' "
							+ " AND cmpv.Is_Active = 'Y' ";
		
		
		if(paramDocuments.getCustomerId() > 0){
			hqlQuery += "  AND  document.Customer_Id = "+paramDocuments.getCustomerId();
		}
		
		if(paramDocuments.getCompanyId() != null && paramDocuments.getCompanyId() > 0){
			hqlQuery += "  AND  document.Company_Id = "+paramDocuments.getCompanyId();
		}
		
		if(paramDocuments.getStatus() != null && !paramDocuments.getStatus().isEmpty()){
			hqlQuery += " AND document.Status = '"+paramDocuments.getStatus()+"' ";
		}
		
		if(paramDocuments.getDocumentName() != null && !paramDocuments.getDocumentName().isEmpty()){
			hqlQuery += " AND document.Document_Name = '"+paramDocuments.getDocumentName()+"' ";
		}
		
		if(paramDocuments.getYear() != null && paramDocuments.getYear() > 0 && paramDocuments.getMonth() != null && paramDocuments.getMonth() > 0 ){
			hqlQuery += " AND YEAR(document.Document_Date)  = "+paramDocuments.getYear()+" AND MONTH(document.Document_Date =  "+paramDocuments.getMonth();
		}
		
		if(paramDocuments.getFromDate() != null && paramDocuments.getToDate() != null){
			hqlQuery += " AND document.Document_Date BETWEEN '"+paramDocuments.getFromDate()+"' AND '"+paramDocuments.getToDate()+"' ";
		}
		
		hqlQuery += " GROUP BY document.Company_Document_Id Order By document.Document_Name asc";
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){
				for (Object doc : queryList) {
					Object[] obj = (Object[]) doc;
					document = new CompanyDocumentsVo();

					document.setDocumentName((String)obj[0]);
					document.setDocumentDate((Date)obj[1]);
					document.setStatus((String)obj[2]);
					document.setCompanyDocumentId((Integer)obj[3]);
					document.setChallanNumber((Integer)obj[4]);
					returnList.add(document);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getCompanyDocumentsListBySearch()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getCompanyDocumentsListBySearch()  ::   "+returnList);
		return returnList;
	}


	@Override
	public Integer saveCompanyDocument(CompanyDocumentsVo paramDocuments) {
		log.info("Entered in saveCompanyDocument  ::  paramInvoice =  "+paramDocuments);
		
		Session session = sessionFactory.getCurrentSession(); 
		Integer companyDocumentId = 0;
		CompanyDocuments documents = new CompanyDocuments();
		
		try{
			if(paramDocuments != null && paramDocuments.getCompanyDocumentId() != null && paramDocuments.getCompanyDocumentId() > 0 ){
				documents = (CompanyDocuments) session.load(CompanyDocuments.class, paramDocuments.getCompanyDocumentId());
			}
			
			documents.setCustomerDetails(new CustomerDetails(paramDocuments.getCustomerId()));
			documents.setCompanyDetails(new CompanyDetails(paramDocuments.getCompanyId()));
			documents.setLocationId(paramDocuments.getLocationId());
			documents.setChallanNumber(paramDocuments.getChallanNumber());
			documents.setDocumentDate(paramDocuments.getDocumentDate());
			documents.setDocumentName(paramDocuments.getDocumentName());
			documents.setDocumentFrequency(paramDocuments.getDocumentFrequency());
			documents.setAmount(paramDocuments.getAmount());
			documents.setCurrencyId(paramDocuments.getCurrencyId());	
			documents.setStatus(paramDocuments.getStatus());
			documents.setHeadCount(paramDocuments.getHeadCount());
			documents.setFilePath(paramDocuments.getFilePath());
			documents.setFileName(paramDocuments.getFileName());
			documents.setModifiedBy(paramDocuments.getModifiedBy());
			documents.setModifiedDate(new Date());
			documents.setComments(paramDocuments.getComments());
			
	
			if(paramDocuments != null && paramDocuments.getCompanyDocumentId() != null && paramDocuments.getCompanyDocumentId() > 0 ){
				session.update(documents);
				companyDocumentId = paramDocuments.getCompanyDocumentId();
			}else{
				documents.setCreatedBy(paramDocuments.getCreatedBy());
				documents.setCreatedDate(new Date());
				session.save(documents);
				companyDocumentId = (Integer) session.save(documents);
			}

			session.flush();

			
			log.info(" Company Document Id : "+companyDocumentId);	
		
			session.flush();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			log.info("Exiting from saveCompanyDocument()  ::   "+companyDocumentId);
		}
		log.info("Exiting from saveCompanyDocument()  ::   "+companyDocumentId);
		return companyDocumentId;
	}


	@Override
	public List<CompanyDocumentsVo> getCompanyDocumentById(Integer companyDocumentId) {
		log.info("Entered in  getCompanyDocumentById()  ::   companyDocumentId  = "+companyDocumentId);
		
		Session session = sessionFactory.getCurrentSession();
		List<CompanyDocumentsVo> documentsList = new ArrayList<>();
		
		try {				
			String documentQuery = "SELECT  document.Company_Document_Id, document.Challan_Number, document.Document_Date, document.Document_Name, "
					+ " document.Amount, document.Currency_Id, currency.Currency, document.File_Path, document.File_Name, "
					+ " document.Document_Frequency, document.Head_Count, cmpv.Company_Code, cmpv.Company_Name, document.location_Id,"
					+ " document.Status, document.Comments, document.Customer_Id, document.Company_Id "
					+ " FROM company_documents AS document "
					+ " LEFT JOIN Company_View cmpv ON cmpv.Customer_Id = document.Customer_Id AND cmpv.Company_Id = document.Company_Id  "
					+ " LEFT JOIN m_currency currency ON currency.Currency_Id = document.Currency_Id "
					+ " WHERE 1= 1  "
					+ " AND document.Company_Document_Id = "+companyDocumentId
					+ " GROUP BY document.Company_Document_Id ";
			List documentsQueryList = session.createSQLQuery(documentQuery).list();
			
			if(documentsQueryList.size() > 0){
				for (Object document : documentsQueryList) {
					Object[] documentObj = (Object[]) document;
					CompanyDocumentsVo documentVo = new CompanyDocumentsVo();
					
					documentVo.setCompanyDocumentId((Integer)documentObj[0]);
					documentVo.setChallanNumber((Integer)documentObj[1]);
					documentVo.setDocumentDate(DateHelper.convertDate((Date)documentObj[2]));
					documentVo.setDocumentName((String)documentObj[3]);
					documentVo.setAmount((String)documentObj[4]);
					documentVo.setCurrencyId((Integer)documentObj[5]);
					documentVo.setCurrencyName((String)documentObj[6]);
					documentVo.setFilePath((String)documentObj[7]);
					documentVo.setFileName((String)documentObj[8]);
					documentVo.setDocumentFrequency((String)documentObj[9]);
					documentVo.setHeadCount((Integer)documentObj[10]);
					documentVo.setCompanyCode((String)documentObj[11]);
					documentVo.setCompanyName((String)documentObj[12]);
					documentVo.setLocationId((Integer)documentObj[13]);
					documentVo.setStatus((String)documentObj[14]);
					documentVo.setComments((String)documentObj[15]);
					documentVo.setCustomerId((Integer)documentObj[16]);
					documentVo.setCompanyId((Integer)documentObj[17]);
					
					documentsList.add(documentVo);
				}
			}	
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getCompanyDocumentById()  ::   "+documentsList);
		}
		session.flush();
		log.info("Exiting from getCompanyDocumentById()  ::   "+documentsList);
		return documentsList;
	}
}
