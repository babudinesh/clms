package com.Ntranga.CLMS.Dao;


import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.VendorBankDetailsVo;
import com.Ntranga.CLMS.vo.VendorBranchDetailsInfoVo;
import com.Ntranga.CLMS.vo.VendorDetailsVo;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.LocationDetails;
import com.Ntranga.core.CLMS.entities.MPaymentFrequency;
import com.Ntranga.core.CLMS.entities.MPaymentMode;
import com.Ntranga.core.CLMS.entities.MSector;
import com.Ntranga.core.CLMS.entities.MVendorType;
import com.Ntranga.core.CLMS.entities.VendorBankDetails;
import com.Ntranga.core.CLMS.entities.VendorBranchDetailsInfo;
import com.Ntranga.core.CLMS.entities.VendorBranches;
import com.Ntranga.core.CLMS.entities.VendorDetails;
import com.Ntranga.core.CLMS.entities.VendorDetailsInfo;
import com.Ntranga.core.CLMS.entities.VendorIndustrySubindustryDetails;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import common.Logger;
@Transactional
@Repository("vendorDao")
@SuppressWarnings({"rawtypes","unchecked"})
public class VendorDaoImpl implements VendorDao  {

	private static Logger log = Logger.getLogger(VendorDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;


	
	@Override
	public List<SimpleObject> getVendorTypesList() {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> simpleObjects = new ArrayList<SimpleObject>(); 
		try {
			List<MVendorType> tempList =  session.createQuery(" from MVendorType  ORDER BY vendorType asc").list();					
			for(MVendorType mVendorType : tempList){				
				SimpleObject object = new SimpleObject();
				object.setId(mVendorType.getVendorTypeId());
				object.setName(mVendorType.getVendorType());
				simpleObjects.add(object);
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return simpleObjects;
	}
	
	
	@Override
	public List<SimpleObject> getPaymentModesList() {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> simpleObjects = new ArrayList<SimpleObject>(); 
		try {
			List<MPaymentMode> tempList =  session.createQuery(" from MPaymentMode ORDER BY modeOfPayment asc ").list();					
			for(MPaymentMode mPaymentMode : tempList){				
				SimpleObject object = new SimpleObject();
				object.setId(mPaymentMode.getModeOfPaymentId());
				object.setName(mPaymentMode.getModeOfPayment());
				simpleObjects.add(object);
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return simpleObjects;
	}	


	@Override
	public List<SimpleObject> getPaymentFrequencyList() {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> simpleObjects = new ArrayList<SimpleObject>(); 
		try {
			List<MPaymentFrequency> tempList =  session.createQuery("from MPaymentFrequency ORDER BY paymentFrequency asc ").list();					
			for(MPaymentFrequency mPaymentFrequency : tempList){				
				SimpleObject object = new SimpleObject();
				object.setId(mPaymentFrequency.getPaymentFrequnecyId());
				object.setName(mPaymentFrequency.getPaymentFrequency());
				simpleObjects.add(object);
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return simpleObjects;
	}
	
	@Override
	public List<SimpleObject> getSubIndustryListByIndustryId(String industryId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> simpleObjects = new ArrayList<SimpleObject>(); 
		try {
			List<MSector> tempList =  session.createQuery("from MSector where MIndustry = '"+industryId+"' ORDER BY sectorName asc ").list();					
			for(MSector mPaymentFrequency : tempList){				
				SimpleObject object = new SimpleObject();
				object.setId(mPaymentFrequency.getSectorId());
				object.setName(mPaymentFrequency.getSectorName());
				simpleObjects.add(object);
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return simpleObjects;
	}
	
	
	

	
	/* Vendor Details Grid Data*/
	
	//@Override
	public List<VendorDetailsVo> getVendorDetailsList(String customerId,String companyId,String vendorCode,String vendorName,String vendorId,Integer industryId, String status) {
		Session session = sessionFactory.getCurrentSession();
		List<VendorDetailsVo> vendorDetailsVoList = new ArrayList<VendorDetailsVo>();
		System.out.println("customerId::"+customerId+", companyId::"+companyId+", vendorId::"+vendorId+" , vendorCode :: "+vendorName+" , vendorCode :: "+vendorName+" , status :: "+status);
		try {
			

			 String q = "SELECT vdi.Vendor_Details_Info_Id,vd.Vendor_Code,vdi.Vendor_Name, CASE vdi.is_active WHEN 'Y' THEN 'Active' ELSE 'In-Active' END AS STATUS, "+
						" vdi.Transaction_Date, vdi.Registration_Date, ld.Location_Name, vdi.Vendor_Id, vdi.Vendor_Status  "+
						" FROM vendor_details vd LEFT JOIN vendor_details_info vdi  ON(vd.Vendor_Id = vdi.Vendor_Id)  "+
						" LEFT JOIN  Location_Details_Info ld on (vdi.Location_Id = ld.Location_Id "
						+ "AND CONCAT(DATE_FORMAT(ld.transaction_date, '%Y%m%d'), LPAD(ld.Location_Sequence_Id, 2, '0')) =   "+
						" (  "+
						" SELECT MAX(CONCAT(DATE_FORMAT(ldi1.transaction_date, '%Y%m%d'), LPAD(ldi1.Location_Sequence_Id, 2, '0')))  "+
						" FROM location_details_info ldi1  "+
						" WHERE  ld.Location_Id = ldi1.Location_Id     "+
						" AND ldi1.transaction_date <= CURRENT_DATE()    "+
						" ) "
						+ ")  "+
						" WHERE  "+
						" CONCAT(DATE_FORMAT(vdi.transaction_date, '%Y%m%d'), LPAD(vdi.Sequence_Id, 2, '0')) =   "+
						" (  "+
						" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id, 2, '0')))  "+
						" FROM vendor_details_info vdi1  "+
						" WHERE  vdi.vendor_Id = vdi1.`vendor_Id`      "+
						" AND vdi1.transaction_date <= CURRENT_DATE()    "+
						" ) " ;

			/*String q = "SELECT   vdi.Vendor_Details_Info_Id,  vd.Vendor_Code,  vdi.Vendor_Name, "
						+ "  CASE vdi.is_active  WHEN 'Y' THEN 'Active' ELSE 'In-Active' END AS STATUS, "
						+ "    vdi.Transaction_Date, vdi.Registration_Date,  ld.Location_Name, vdi.Vendor_Id, vdi.Vendor_Status  "
						+ " FROM  vendor_details vd,vendor_details_info vdi   "
						+ " LEFT JOIN vendor_industry_subindustry_details isd ON (isd.vendor_id = vdi.Vendor_Id)   "
						+ " WHERE vd.Vendor_Id = vdi.Vendor_Id    AND "
						+ "    CONCAT(DATE_FORMAT(vdi.transaction_date, '%Y%m%d'), LPAD(vdi.Sequence_Id, 2, '0')) =  "
						+ " ( SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id, 2, '0'))) "
						+ " FROM vendor_details_info vdi1 " + " WHERE  vdi.`Vendor_Id` = vdi1.`Vendor_Id`     "
						+ "  AND vdi1.transaction_date <= CURRENT_DATE())  ";
*/

			 
					 if((customerId != null && !customerId.isEmpty() && !customerId.equalsIgnoreCase("undefined") && !customerId.equals("0"))){
						 q +=" and vdi.Customer_Id = '"+customerId+"'";				 
					 }
					 
					 if(companyId != null && !companyId.isEmpty() && !companyId.equalsIgnoreCase("undefined") && !companyId.equals("0")){						 
						 q +=" and vdi.Company_Id = '"+companyId+"'";	
					 } 
					 
					 if(vendorCode != null && !vendorCode.isEmpty() && !vendorCode.equalsIgnoreCase("undefined")){						 
						 q +=" and vd.Vendor_Code LIKE '"+vendorCode+"%'";	
					 }
					 
					 if(vendorName!= null && !vendorName.isEmpty() && !vendorName.equalsIgnoreCase("undefined")){						 
						 q +=" and vdi.Vendor_Name LIKE '"+vendorName+"%'";	
					 }
					 
					 if( (vendorId != null && !vendorId.isEmpty() && !vendorId.equalsIgnoreCase("undefined") && !vendorId.equals("0"))){						 
						 q +=" and vdi.Vendor_Id = '"+vendorId+"'";	
					 }
					 
					 if(industryId != null && industryId >0){
						 q +=" and isd.industry_Id = '"+industryId+"'";				 
					 }
					 /*if( (status != null && !status.isEmpty())){						 
						 q +=" and vdi.is_active = '"+status+"'";	
					 }*/
					 if(status != null && !status.isEmpty()){
						 q += " AND vdi.Vendor_Status = '"+status+"' ";
					 }
					q = q 	+" order by vdi.Vendor_Name ";						
													 
			
			 List tempList = session.createSQLQuery(q).list();			
			for(Object o : tempList){
				Object[] obj = (Object[]) o;
				VendorDetailsVo vendorDetailsVo = new VendorDetailsVo();
				vendorDetailsVo.setVendorDetailsInfoId((Integer)obj[0]);				
				vendorDetailsVo.setVendorCode(obj[1]+"");
				vendorDetailsVo.setVendorName(obj[2]+"");
				vendorDetailsVo.setIsActive(obj[3]+"");
				vendorDetailsVo.setTransactionDate((Date)obj[4]);
				vendorDetailsVo.setRegistrationDate((Date)obj[5]);
				vendorDetailsVo.setLocationName((String)obj[6]);
				vendorDetailsVo.setVendorId((Integer)obj[7]);
				vendorDetailsVo.setVendorStatus((String)obj[8]);
				vendorDetailsVoList.add(vendorDetailsVo);
			}						
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}
		
		
		return vendorDetailsVoList;
	}
	
	
	/* Vendor Details Grid Data End*/

	
	//@Override
	public List<CustomerVo> getCustomerNamesAsDropDown(String customerid) {
		Session session = sessionFactory.getCurrentSession();
		//System.out.println(customerid+":;customerid");
		List<CustomerVo> simpleObjects = new ArrayList<CustomerVo>(); 
		try {
			
			String q = " SELECT a.customer_id,a.customer_Name FROM  customer_details_info a "
						+" WHERE CONCAT(DATE_FORMAT(a.transaction_date, '%Y%m%d'), LPAD(a.Customer_Sequence_Id,2,'0')) =    "
						+" ( SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Customer_Sequence_Id,2,'0')))  "
						+" FROM customer_details_info vdi1   "
						+" WHERE a.customer_id = vdi1.customer_id  "
						+" AND vdi1.transaction_date <= CURRENT_DATE()) AND a.is_active='Y'";
					   
			if(customerid!= null && !customerid.isEmpty() && Integer.parseInt(customerid) > 0){				
				q= q+" and a.Customer_Id= '"+customerid+"' ";
			}			
			q= q+" group by a.Customer_Id order by a.customer_name ";
			
			List tempList =  session.createSQLQuery(q).list();					
			for(Object  o: tempList){
				Object[] obj = (Object[]) o;
				CustomerVo object = new CustomerVo();
				object.setCustomerId((Integer)obj[0]);
				object.setCustomerName(obj[1]+"");
				simpleObjects.add(object);
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return simpleObjects;
	}
	
	
	
	public List<SimpleObject> getComapanyNamesAsDropDown(String customerId,String companyId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> simpleObjects = new ArrayList<SimpleObject>(); 
		try {
			String query = " SELECT a.company_Id,a.company_Name FROM company_details_info  a "
					+" WHERE CONCAT(DATE_FORMAT(a.transaction_date, '%Y%m%d'), LPAD(a.company_Sequence_Id,2,'0')) =  "
					+" ( SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.company_Sequence_Id,2,'0'))) "
					+" FROM company_details_info vdi1   "
					+" WHERE a.company_id = vdi1.company_id  "
					+" AND vdi1.transaction_date <= CURRENT_DATE()) AND a.is_active='Y' "	;
			
			if(customerId!= null && !customerId.isEmpty() && Integer.parseInt(customerId) > 0){		
				query +=" and a.Customer_Id= '"+customerId+"' ";
			}
			if(companyId!= null && !companyId.isEmpty() && Integer.parseInt(companyId) > 0){		
				query +=" and a.Company_Id= '"+companyId+"' ";
			}
			query +=" group by a.Customer_Id,a.company_id   order by a.Company_Name";
			
			
			
			List tempList =  session.createSQLQuery(query).list();					
			for(Object  o: tempList){
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
	
	
	public List<SimpleObject> getVendorNamesAsDropDown(String customerId,String companyId,String vendorId) { 
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> simpleObjects = new ArrayList<SimpleObject>(); 
		try {
			String query = "SELECT a.vendor_id,concat(a.vendor_Name,' (',b.vendor_code,') ') as vname, b.vendor_code FROM vendor_details_info  a "
						+" inner join vendor_details b on a.vendor_id = b.vendor_id "
						+" WHERE CONCAT(DATE_FORMAT(a.transaction_date, '%Y%m%d'), LPAD(a.Sequence_Id,2,'0')) =  "
						+" ( SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id,2,'0'))) "
						+" FROM vendor_details_info vdi1  "
						+" 	WHERE a.vendor_id = vdi1.vendor_id AND a.company_id= vdi1.company_id AND a.customer_id = vdi1.customer_id "
						+" AND vdi1.transaction_date <= CURRENT_DATE())   ";

			if(customerId!= null && !customerId.isEmpty() && Integer.parseInt(customerId) > 0){		
				query +=" and a.Customer_Id= '"+customerId+"' ";
			}
			if(companyId!= null && !companyId.isEmpty() && Integer.parseInt(companyId) > 0){		
				query +=" and a.company_id= '"+companyId+"' ";
			}
			if(vendorId!= null && !vendorId.isEmpty() && Integer.parseInt(vendorId) > 0){		
				query +=" and a.vendor_id= '"+vendorId+"' ";
			}
			
			query+=" order by a.vendor_name ";
			List tempList =  session.createSQLQuery(query).list();
			
			
																	
			for(Object  o: tempList){
				Object[] obj = (Object[]) o;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)obj[0]);
				object.setName(obj[1]+"");
				object.setDesc(obj[2]+"");
				simpleObjects.add(object);
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return simpleObjects;
	}
	

	@Override
	public List<VendorBranchDetailsInfoVo> getVendorBranchGridList(Integer customerId, Integer companyId, Integer vendorId, String vendorCode, String vendorName, String branchCode) {
		Session session = sessionFactory.getCurrentSession();
		List<VendorBranchDetailsInfoVo> branchDetailsInfoVo = new ArrayList<VendorBranchDetailsInfoVo>();
		try {
			String query = "SELECT vendor.`Vendor_Code`,vendorDetailsinfo.`Vendor_Name`,branches.`Branch_Code`,vendorbranchInfo.`Contact_Person`, CASE vendorbranchInfo.`Is_Active` WHEN 'Y' THEN 'Active'  ELSE 'In-Active'  END AS `status`  ,COALESCE(country_Name,'') AS countryName, COALESCE(state_name,'') AS stateName, COALESCE(Location_Name,'') AS LocationName,vendorbranchInfo.`Vendor_Branch_Details_Info_Id`, companydetails.`Company_Name`,customerDetails.`Customer_Name`, vendorbranchInfo.Nature_Of_Work  ";
			/*if(industryId != null && !industryId.isEmpty() && industryId.replace("[", "").replace("]", "").trim().length() > 0){
				query +=" , GROUP_CONCAT( DISTINCT vendorindustry.`Industry_Id`) " ;
			}*/ 
			query +="  FROM `vendor_branch_details_info` vendorbranchInfo  LEFT JOIN m_country country  ON ( vendorbranchInfo.`Country` = country.country_Id )  LEFT JOIN m_state state  ON ( vendorbranchInfo.`state` = state.state_Id )  "
					+ " LEFT  JOIN `vendor_branches` branches  ON (  vendorbranchInfo.`Vendor_Branch_Id` = branches.`Vendor_Branch_Id` )  "
					+ " LEFT JOIN `vendor_details` vendor  ON ( branches.`Vendor_Id` = vendor.`Vendor_Id` )  "
					+ " LEFT JOIN `vendor_details_info` vendorDetailsinfo  ON ( vendor.`Vendor_Id`=vendorDetailsinfo.`Vendor_Id` ) "
					+ " LEFT JOIN `company_details_info` companydetails  ON ( companydetails.`Company_Id`= vendorbranchInfo.`Company_Id` )  "
					+ " LEFT JOIN `customer_details_info` customerDetails  ON (customerDetails.`Customer_Id` = vendorbranchInfo.`Customer_Id`)  ";
			
			/*if(industryId != null && !industryId.isEmpty() && industryId.replace("[", "").replace("]", "").trim().length() > 0){
				query +=" left JOIN `vendor_industry_subindustry_details` vendorindustry  ON ( vendorindustry.`Vendor_Id` = vendorbranchInfo.`Vendor_Id`  AND vendorindustry.`Company_Id` = vendorbranchInfo.`Company_Id`  AND vendorindustry.`Customer_Id` = vendorbranchInfo.`Customer_Id` ) " ;
			}*/
						
			query +=" WHERE CONCAT(DATE_FORMAT(vendorDetailsinfo.Transaction_Date, '%Y%m%d'), vendorDetailsinfo.Sequence_Id) =  (  "
						+"	SELECT MAX(CONCAT(DATE_FORMAT(vdi1.Transaction_Date, '%Y%m%d'), vdi1.Sequence_Id)) "
						+"	FROM vendor_details_info vdi1 "
						+"	WHERE vendorDetailsinfo.vendor_id = vdi1.vendor_id AND vdi1.Transaction_Date <= CURRENT_DATE() "
						+"	) "	 
						+"	AND  CONCAT(DATE_FORMAT(customerDetails.Transaction_Date, '%Y%m%d'), customerDetails.Customer_Sequence_Id) =  "
						+"	( "
						+"	SELECT MAX(CONCAT(DATE_FORMAT(cdi1.Transaction_Date, '%Y%m%d'), cdi1.Customer_Sequence_Id)) "
						+"	FROM customer_details_info cdi1 "
						+"	WHERE customerDetails.customer_id = cdi1.customer_id AND cdi1.Transaction_Date <= CURRENT_DATE() "
						+"	) "
							
						+"	AND  CONCAT(DATE_FORMAT(companydetails.Transaction_Date, '%Y%m%d'), companydetails.Company_Sequence_Id) =  "
						+"	( "
						+"	SELECT MAX(CONCAT(DATE_FORMAT(cdi1.Transaction_Date, '%Y%m%d'), cdi1.Company_Sequence_Id)) "
						+"	FROM company_details_info cdi1 "
						+"	WHERE companydetails.company_id = cdi1.company_id AND cdi1.Transaction_Date <= CURRENT_DATE() ) "
						+ " AND customerDetails.Is_Active = 'Y' "
						+ " AND companyDetails.Is_Active = 'Y' "
						+ " AND vendorDetailsinfo.Vendor_Status = 'Validated' " ;
						//+" AND customerDetails.Customer_Sequence_Id = (SELECT MAX(`Customer_Sequence_Id`)  FROM `customer_details_info` custchild  WHERE custchild.Transaction_Date <= CURRENT_DATE()   AND custchild.`Customer_Id` = vendorbranchInfo.`Customer_Id` ) ";
		
			
		if(Integer.valueOf(customerId)>0){
			query +=" AND vendorbranchInfo.`Customer_Id`="+customerId;
		}
		
		if(Integer.valueOf(companyId)>0){
			query +=" AND vendorbranchInfo.`Company_Id`="+companyId ;
		}
		
		if(vendorCode != null && !vendorCode.isEmpty()){
			query +=" AND vendor.Vendor_Code LIKE '"+vendorCode+"%'" ;
		}
		
		if(vendorName != null && !vendorName.isEmpty()){
			query +=" AND vendorDetailsinfo.Vendor_Name LIKE '"+vendorName+"%'" ;
		}
		
		if(Integer.valueOf(vendorId)>0){
			query +=" AND vendorbranchInfo.`Vendor_Id`="+vendorId ;
		}
		
		/*if(industryId != null && !industryId.isEmpty() && industryId.replace("[", "").replace("]", "").trim().length() > 0){
			query +=" AND vendorindustry.`Industry_Id` IN  ('"+industryId.replace("[", "").replace("]", "")+"') " ;
		}*/			
		if(branchCode != null && !branchCode.isEmpty()){
			query +=" AND vendorbranchInfo.`Branch_Code` like '%"+branchCode+"%' " ;
		}
		 
		query +=" ORDER BY vendor.Vendor_Code asc";
		
		List tempList =  session.createSQLQuery(query).list();
		for(Object  o : tempList){
			Object[] obj = (Object[]) o;
			VendorBranchDetailsInfoVo vo = new VendorBranchDetailsInfoVo();
			vo.setVendorCode(obj[0]+"");
			vo.setVendorName(obj[1]+"");
			vo.setBranchCode(obj[2]+"");
			vo.setContactPerson(obj[3]+"");
			vo.setIsActive(obj[4]+"");			
			vo.setCountryName(obj[5]+"");
			vo.setStateName(obj[6]+"");
			vo.setLocationName((String)obj[7]);
			vo.setVendorBranchDetailsInfoId((Integer)obj[8]);
			vo.setCompanyName(obj[9]+"");	
			vo.setCustomerName(obj[10]+"");	
			vo.setNatureOfWork((String)obj[11]);	
			branchDetailsInfoVo.add(vo);
		}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return branchDetailsInfoVo;
	}


	@Override
	public VendorBranchDetailsInfoVo getVendorBranchDetails(String vendorBranchId) {
		Session session = sessionFactory.getCurrentSession();
		VendorBranchDetailsInfoVo branchDetailsInfoVo = new VendorBranchDetailsInfoVo(); 
		try {
			VendorBranchDetailsInfo info =  (VendorBranchDetailsInfo) session.get(VendorBranchDetailsInfo.class, Integer.valueOf(vendorBranchId));		
			if(info != null){
				branchDetailsInfoVo.setAddress1(info.getAddress1());
				branchDetailsInfoVo.setAddress2(info.getAddress2());
				branchDetailsInfoVo.setAddress3(info.getAddress3());
				branchDetailsInfoVo.setBranchCode(info.getBranchCode());
				branchDetailsInfoVo.setCity(info.getCity());
				branchDetailsInfoVo.setCompanyDetailsId(info.getCompanyDetails().getCompanyId());
				branchDetailsInfoVo.setCustomerDetailsId(info.getCustomerDetails().getCustomerId());
				branchDetailsInfoVo.setVendorDetailsId(info.getVendorDetails().getVendorId());
				branchDetailsInfoVo.setContactPerson(info.getContactPerson());
				branchDetailsInfoVo.setCountry(info.getCountry());
				branchDetailsInfoVo.setDesignation(info.getDesignation());
				branchDetailsInfoVo.setEmail(info.getEmail());
				branchDetailsInfoVo.setEsiAccountNumber(info.getEsiAccountNumber());
				if(info.getEsiRegistrationDate() != null)
					branchDetailsInfoVo.setEsiRegistrationDate(info.getEsiRegistrationDate());
				if(info.getEsiStartDate() != null)
					branchDetailsInfoVo.setEsiStartDate(info.getEsiStartDate());
				branchDetailsInfoVo.setExtension(info.getExtension());
				branchDetailsInfoVo.setFaxNumber(info.getFaxNumber());
				branchDetailsInfoVo.setIsActive(info.getIsActive());
				branchDetailsInfoVo.setIsProfessionalTaxApplicable(info.getIsProfessionalTaxApplicable());
				branchDetailsInfoVo.setLocationName(info.getLocationName());
				branchDetailsInfoVo.setMobile(info.getMobile());
				branchDetailsInfoVo.setPfAccountNumber(info.getPfAccountNumber());
				if(info.getPfRegistrationDate() != null)
					branchDetailsInfoVo.setPfRegistrationDate(info.getPfRegistrationDate());
				if(info.getPfStartDate() != null)
					branchDetailsInfoVo.setPfStartDate(info.getPfStartDate());
				branchDetailsInfoVo.setPfCircle(info.getPfCircle());
				branchDetailsInfoVo.setPfType(info.getPfType());
				branchDetailsInfoVo.setPhoneNumber(info.getPhoneNumber());
				branchDetailsInfoVo.setPincode(info.getPincode());				
				branchDetailsInfoVo.setSequenceId(info.getSequenceId());
				branchDetailsInfoVo.setState(info.getState());
				branchDetailsInfoVo.setTransactionDate(info.getTransactionDate());
				branchDetailsInfoVo.setVendorBranchDetailsInfoId(info.getVendorBranchDetailsInfoId());
				branchDetailsInfoVo.setVendorBranchesId(info.getVendorBranches().getVendorBranchId());
				branchDetailsInfoVo.setBranchCode(info.getVendorBranches().getBranchCode());
				//branchDetailsInfoVo.setWorkType(info.getWorkType());	
				if(info.getNatureOfWork() != null)
					branchDetailsInfoVo.setNatureOfWork(info.getNatureOfWork());
			}else{
				branchDetailsInfoVo = new VendorBranchDetailsInfoVo(); 
			}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return branchDetailsInfoVo;
	}


	@Override
	public Integer saveOrUpdateVendorBranchDetails(VendorBranchDetailsInfoVo vendorBranchDetailsInfoVo) {
		Session session = sessionFactory.getCurrentSession();
		//VendorBranchDetailsInfoVo branchDetailsInfoVo = new VendorBranchDetailsInfoVo(); 
		Integer branchInfoId =0;
		Integer vendorBranchId = 0;
		try {
			VendorBranchDetailsInfo branchInfo =  new VendorBranchDetailsInfo();	
			if(vendorBranchDetailsInfoVo.getVendorBranchesId() != null && vendorBranchDetailsInfoVo.getVendorBranchesId() > 0){
				VendorBranches branches = (VendorBranches) session.get(VendorBranches.class, vendorBranchDetailsInfoVo.getVendorBranchesId());
				branches.setCustomerDetails(new CustomerDetails(vendorBranchDetailsInfoVo.getCustomerDetailsId()));
				branches.setCompanyDetails(new CompanyDetails(vendorBranchDetailsInfoVo.getCompanyDetailsId()));
				branches.setVendorDetails(new VendorDetails(vendorBranchDetailsInfoVo.getVendorDetailsId()));
				branches.setBranchCode(vendorBranchDetailsInfoVo.getBranchCode());
				branches.setIsActive("Y");
				branches.setModifiedBy(vendorBranchDetailsInfoVo.getModifiedBy());
				branches.setModifiedDate(new Date());				
				session.update(branches);
				vendorBranchId = vendorBranchDetailsInfoVo.getVendorBranchesId();
			}else {
				VendorBranches branches = new VendorBranches();
				branches.setCustomerDetails(new CustomerDetails(vendorBranchDetailsInfoVo.getCustomerDetailsId()));
				branches.setCompanyDetails(new CompanyDetails(vendorBranchDetailsInfoVo.getCompanyDetailsId()));
				branches.setVendorDetails(new VendorDetails(vendorBranchDetailsInfoVo.getVendorDetailsId()));
				branches.setBranchCode(vendorBranchDetailsInfoVo.getBranchCode());
				branches.setIsActive("Y");
				branches.setModifiedBy(vendorBranchDetailsInfoVo.getModifiedBy());
				branches.setCreatedBy(vendorBranchDetailsInfoVo.getCreatedBy());
				branches.setModifiedDate(new Date());
				branches.setCreatedDate(new Date());
				vendorBranchId = (Integer) session.save(branches);
			}
						
			if(vendorBranchDetailsInfoVo != null && vendorBranchDetailsInfoVo.getVendorBranchDetailsInfoId() != null && vendorBranchDetailsInfoVo.getVendorBranchDetailsInfoId() > 0){
				branchInfo = (VendorBranchDetailsInfo) session.get(VendorBranchDetailsInfo.class, vendorBranchDetailsInfoVo.getVendorBranchDetailsInfoId());
				branchInfo.setAddress1(vendorBranchDetailsInfoVo.getAddress1());
				branchInfo.setAddress2(vendorBranchDetailsInfoVo.getAddress2());
				branchInfo.setAddress3(vendorBranchDetailsInfoVo.getAddress3());
				branchInfo.setBranchCode(vendorBranchDetailsInfoVo.getBranchCode());
				branchInfo.setCity(vendorBranchDetailsInfoVo.getCity());
				branchInfo.setCompanyDetails(new CompanyDetails( vendorBranchDetailsInfoVo.getCompanyDetailsId()));
				branchInfo.setCustomerDetails( new CustomerDetails(vendorBranchDetailsInfoVo.getCustomerDetailsId()));
				branchInfo.setVendorDetails(new VendorDetails(vendorBranchDetailsInfoVo.getVendorDetailsId()));
				branchInfo.setContactPerson(vendorBranchDetailsInfoVo.getContactPerson());
				branchInfo.setCountry(vendorBranchDetailsInfoVo.getCountry());
				branchInfo.setDesignation(vendorBranchDetailsInfoVo.getDesignation());
				branchInfo.setEmail(vendorBranchDetailsInfoVo.getEmail());
				branchInfo.setEsiAccountNumber(vendorBranchDetailsInfoVo.getEsiAccountNumber());
				branchInfo.setEsiRegistrationDate(vendorBranchDetailsInfoVo.getEsiRegistrationDate());
				branchInfo.setEsiStartDate(vendorBranchDetailsInfoVo.getEsiStartDate());
				branchInfo.setExtension(vendorBranchDetailsInfoVo.getExtension());
				branchInfo.setFaxNumber(vendorBranchDetailsInfoVo.getFaxNumber());
				branchInfo.setIsActive(vendorBranchDetailsInfoVo.getIsActive());
				branchInfo.setIsProfessionalTaxApplicable(vendorBranchDetailsInfoVo.getIsProfessionalTaxApplicable());
				branchInfo.setLocationName(vendorBranchDetailsInfoVo.getLocationName());
				branchInfo.setMobile(vendorBranchDetailsInfoVo.getMobile());
				branchInfo.setPfAccountNumber(vendorBranchDetailsInfoVo.getPfAccountNumber());
				branchInfo.setPfRegistrationDate(vendorBranchDetailsInfoVo.getPfRegistrationDate());
				branchInfo.setPfStartDate(vendorBranchDetailsInfoVo.getPfStartDate());
				branchInfo.setPfCircle(vendorBranchDetailsInfoVo.getPfCircle());
				branchInfo.setPfType(vendorBranchDetailsInfoVo.getPfType());
				branchInfo.setPhoneNumber(vendorBranchDetailsInfoVo.getPhoneNumber());
				branchInfo.setPincode(vendorBranchDetailsInfoVo.getPincode());				
				branchInfo.setSequenceId(vendorBranchDetailsInfoVo.getSequenceId());
				branchInfo.setState(vendorBranchDetailsInfoVo.getState());
				branchInfo.setTransactionDate(vendorBranchDetailsInfoVo.getTransactionDate());				
				branchInfo.setVendorBranches(new VendorBranches(vendorBranchId));
				//branchInfo.setWorkType(vendorBranchDetailsInfoVo.getWorkType());	
				branchInfo.setNatureOfWork(vendorBranchDetailsInfoVo.getNatureOfWork());
				branchInfo.setModifiedBy(vendorBranchDetailsInfoVo.getModifiedBy());				
				branchInfo.setModifiedDate(new Date());
				session.update(branchInfo);
				branchInfoId = vendorBranchDetailsInfoVo.getVendorBranchDetailsInfoId();
			}else{
				branchInfo = new VendorBranchDetailsInfo(); 
				branchInfo.setAddress1(vendorBranchDetailsInfoVo.getAddress1());
				branchInfo.setAddress2(vendorBranchDetailsInfoVo.getAddress2());
				branchInfo.setAddress3(vendorBranchDetailsInfoVo.getAddress3());
				branchInfo.setBranchCode(vendorBranchDetailsInfoVo.getBranchCode());
				branchInfo.setCity(vendorBranchDetailsInfoVo.getCity());
				branchInfo.setCompanyDetails(new CompanyDetails( vendorBranchDetailsInfoVo.getCompanyDetailsId()));
				branchInfo.setCustomerDetails( new CustomerDetails(vendorBranchDetailsInfoVo.getCustomerDetailsId()));
				branchInfo.setVendorDetails(new VendorDetails(vendorBranchDetailsInfoVo.getVendorDetailsId()));
				branchInfo.setContactPerson(vendorBranchDetailsInfoVo.getContactPerson());
				branchInfo.setCountry(vendorBranchDetailsInfoVo.getCountry());
				branchInfo.setDesignation(vendorBranchDetailsInfoVo.getDesignation());
				branchInfo.setEmail(vendorBranchDetailsInfoVo.getEmail());
				branchInfo.setEsiAccountNumber(vendorBranchDetailsInfoVo.getEsiAccountNumber());
				branchInfo.setEsiRegistrationDate(vendorBranchDetailsInfoVo.getEsiRegistrationDate());
				branchInfo.setEsiStartDate(vendorBranchDetailsInfoVo.getEsiStartDate());
				branchInfo.setExtension(vendorBranchDetailsInfoVo.getExtension());
				branchInfo.setFaxNumber(vendorBranchDetailsInfoVo.getFaxNumber());
				branchInfo.setIsActive(vendorBranchDetailsInfoVo.getIsActive());
				branchInfo.setIsProfessionalTaxApplicable(vendorBranchDetailsInfoVo.getIsProfessionalTaxApplicable());
				branchInfo.setLocationName(vendorBranchDetailsInfoVo.getLocationName());
				branchInfo.setMobile(vendorBranchDetailsInfoVo.getMobile());
				branchInfo.setPfAccountNumber(vendorBranchDetailsInfoVo.getPfAccountNumber());
				branchInfo.setPfRegistrationDate(vendorBranchDetailsInfoVo.getPfRegistrationDate());
				branchInfo.setPfStartDate(vendorBranchDetailsInfoVo.getPfStartDate());
				branchInfo.setPfCircle(vendorBranchDetailsInfoVo.getPfCircle());
				branchInfo.setPfType(vendorBranchDetailsInfoVo.getPfType());
				branchInfo.setPhoneNumber(vendorBranchDetailsInfoVo.getPhoneNumber());
				branchInfo.setPincode(vendorBranchDetailsInfoVo.getPincode());	
				BigInteger seq =  (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(`Sequence_Id`),0) FROM `vendor_branch_details_info` info  WHERE  info.`Transaction_Date` ='"+DateHelper.convertDateToSQLString(vendorBranchDetailsInfoVo.getTransactionDate())+"' AND info.Customer_Id="+vendorBranchDetailsInfoVo.getCustomerDetailsId()+" AND info.Company_Id="+vendorBranchDetailsInfoVo.getCompanyDetailsId()+" AND info.vendor_id ="+vendorBranchDetailsInfoVo.getVendorDetailsId()+" AND info.`Vendor_Branch_Id` ="+vendorBranchId).list().get(0);				
				branchInfo.setSequenceId(seq.intValue()+1);
				branchInfo.setState(vendorBranchDetailsInfoVo.getState());
				branchInfo.setTransactionDate(vendorBranchDetailsInfoVo.getTransactionDate());				
				//branchInfo.setWorkType(vendorBranchDetailsInfoVo.getWorkType());
				branchInfo.setNatureOfWork(vendorBranchDetailsInfoVo.getNatureOfWork());
				branchInfo.setVendorBranches(new VendorBranches(vendorBranchId));
				branchInfo.setCreatedBy(vendorBranchDetailsInfoVo.getCreatedBy());
				branchInfo.setCreatedDate(new Date());
				branchInfo.setModifiedBy(vendorBranchDetailsInfoVo.getModifiedBy());			
				branchInfo.setModifiedDate(new Date());
				branchInfoId = (Integer) session.save(branchInfo);
			}
			session.flush();
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		
		return branchInfoId;
	}
	
	@Override
	public int saveVendorDetails(String vendorDetailsJson){
		
		Session session = sessionFactory.getCurrentSession();
		Integer vendorId = null;
		Integer vendorDetailsInfoId = null;
		try{
		JsonParser jsonParser = new JsonParser();
	 	System.out.println("vendorDetailsJson::"+vendorDetailsJson);
		JsonObject jo = (JsonObject) jsonParser.parse(vendorDetailsJson);
		
		VendorDetails vendorDetails = new VendorDetails();
		// System.out.println(jo.get("vendorId")+"vendorId");
		if(jo.get("vendorId") == null || jo.get("vendorId").getAsInt() ==0 ){
			vendorDetails.setCustomerDetails(new CustomerDetails(jo.get("customerId").getAsInt()));
			vendorDetails.setCompanyDetails(new CompanyDetails(jo.get("companyId").getAsInt()));
			vendorDetails.setVendorCode(jo.get("vendorCode").getAsString().toUpperCase());
			vendorDetails.setIsActive(jo.get("isActive").getAsString());
			vendorDetails.setCreatedBy(jo.get("createdBy").getAsInt());
			vendorDetails.setCreatedDate(new Date());
			vendorDetails.setModifiedBy(jo.get("modifiedBy").getAsInt());
			vendorDetails.setModifiedDate(new Date());
			vendorDetails.setVendorStatus("New");
			vendorId = (Integer) session.save(vendorDetails);			
		}else{
			vendorDetails.setVendorId(jo.get("vendorId").getAsInt());
			vendorDetails.setVendorCode(jo.get("vendorCode").getAsString().toUpperCase());
			vendorDetails.setCustomerDetails(new CustomerDetails(jo.get("customerId").getAsInt()));
			vendorDetails.setCompanyDetails(new CompanyDetails(jo.get("companyId").getAsInt()));
			vendorDetails.setIsActive(jo.get("isActive").getAsString());			
			vendorDetails.setModifiedBy(jo.get("modifiedBy").getAsInt());
			vendorDetails.setModifiedDate(new Date());
			vendorDetails.setCreatedDate(new Date());
			vendorId = vendorDetails.getVendorId();
			session.update(vendorDetails);
			
		}
		
		
		VendorDetailsInfo vendorDetailsInfo = new VendorDetailsInfo();		
		vendorDetailsInfo.setVendorDetails(new VendorDetails(vendorId));
		vendorDetailsInfo.setCustomerDetails(new CustomerDetails(jo.get("customerId").getAsInt()));
		vendorDetailsInfo.setCompanyDetails(new CompanyDetails(jo.get("companyId").getAsInt()));
		vendorDetailsInfo.setCompanyCountry(jo.get("countryId").getAsInt());						 
		String dateStr = jo.get("transactionDate").getAsString();
		String registrnDate = jo.get("registrationDate").getAsString();
		String vendorRegDt = jo.get("vendorRegistrationDate").getAsString();
		// System.out.println(dateStr +""+registrnDate);
		
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date transactionDate = null;
		try {
			transactionDate = (Date) formatter.parse(dateStr);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		Date registrationDate = null;
		try {
			registrationDate = (Date) formatter.parse(registrnDate);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		Date vendorRegistrationDate = null;
		try {
			vendorRegistrationDate = (Date) formatter.parse(vendorRegDt);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		System.out.println(transactionDate +""+registrationDate);
		Integer sequenceId = 0;	
		
		if(jo.get("vendorDetailsInfoId")!= null && jo.get("vendorDetailsInfoId").getAsInt()+"" != null && jo.get("vendorDetailsInfoId").getAsInt()+"" != "" && jo.get("vendorDetailsInfoId").getAsInt() >0){
			List tmepList = sessionFactory.getCurrentSession().createSQLQuery("select max(Sequence_Id) from vendor_details_info where Transaction_Date = '"+DateHelper.convertDateToSQLString(transactionDate)+"' and vendor_id = '"+vendorId+"' and vendor_details_info_id = '"+jo.get("vendorDetailsInfoId").getAsInt()+"'").list();
				sequenceId =(Integer) tmepList.get(0);			
			}else{							
				List tmepList = sessionFactory.getCurrentSession().createSQLQuery("select max(Sequence_Id) from vendor_details_info where Transaction_Date = '"+DateHelper.convertDateToSQLString(transactionDate)+"' and vendor_id = '"+vendorId+"'").list();
				if(tmepList.get(0) == null){
					sequenceId =1;	
				}else if(tmepList.get(0) != null ){	
					sequenceId =(Integer)tmepList.get(0);
					sequenceId = sequenceId+1;	
				}
			}
		
		vendorDetailsInfo.setTransactionDate(transactionDate);
		if(jo.get("isActive") != null && !jo.get("isActive").toString().equalsIgnoreCase("null")){
			vendorDetailsInfo.setIsActive(jo.get("isActive").getAsString());
		}
		
		vendorDetailsInfo.setVendorName((jo.get("vendorName").getAsString()));
		vendorDetailsInfo.setVendorTypeId(jo.get("vendorTypeId").getAsInt());
		vendorDetailsInfo.setLocationDetails(new LocationDetails(jo.get("locationId").getAsInt()));
		if(jo.get("telephoneNumber") != null && !jo.get("telephoneNumber").toString().equalsIgnoreCase("null"))
		vendorDetailsInfo.setTelephoneNumber(jo.get("telephoneNumber").getAsString());
		if(jo.get("faxNumber") != null && !jo.get("faxNumber").toString().equalsIgnoreCase("null"))
		vendorDetailsInfo.setFaxNumber(jo.get("faxNumber").getAsString());
		if(jo.get("email") != null && !jo.get("email").toString().equalsIgnoreCase("null"))
		vendorDetailsInfo.setEmail(jo.get("email").getAsString());
		if(jo.get("vendorStatus") != null && !jo.get("vendorStatus").toString().equalsIgnoreCase("null") )
			vendorDetailsInfo.setVendorStatus(jo.get("vendorStatus").getAsString());
		if(jo.get("website") != null && !jo.get("website").toString().equalsIgnoreCase("null"))
		vendorDetailsInfo.setWebsite(jo.get("website").getAsString());
		if(jo.get("modeOfPaymentId") != null && !jo.get("modeOfPaymentId").toString().equalsIgnoreCase("null"))
		vendorDetailsInfo.setModeOfPaymentId(jo.get("modeOfPaymentId").getAsInt());
		if(jo.get("paymentFrequencyId") != null && !jo.get("paymentFrequencyId").toString().equalsIgnoreCase("null"))
		vendorDetailsInfo.setPaymentFrequencyId(jo.get("paymentFrequencyId").getAsInt());
		if(jo.get("companyTypeId") != null && !jo.get("companyTypeId").toString().equalsIgnoreCase("null"))
		vendorDetailsInfo.setCompanyTypeId(jo.get("companyTypeId").getAsInt());
		if(jo.get("registeredUnderId") != null && !jo.get("registeredUnderId").toString().equalsIgnoreCase("null"))
		vendorDetailsInfo.setRegisteredUnderId(jo.get("registeredUnderId").getAsInt());
		if(jo.get("registrationNumber") != null && !jo.get("registrationNumber").toString().equalsIgnoreCase("null"))
		vendorDetailsInfo.setRegistrationNumber(jo.get("registrationNumber").getAsString());
		
		if(jo.get("directorOwnerProprietorName") != null && !jo.get("directorOwnerProprietorName").isJsonNull() && !jo.get("directorOwnerProprietorName").toString().equalsIgnoreCase("null"))
			vendorDetailsInfo.setDirectorOwnerProprietorName(jo.get("directorOwnerProprietorName").getAsString());
		if(jo.get("natureOfWork") != null && !jo.get("natureOfWork").isJsonNull()  && !jo.get("natureOfWork").toString().equalsIgnoreCase("null"))
			vendorDetailsInfo.setNatureOfWork(jo.get("natureOfWork").getAsString());
		
		vendorDetailsInfo.setRegistrationDate(registrationDate);
		if(jo.get("panNumber") != null && !jo.get("panNumber").toString().equalsIgnoreCase("null"))
		vendorDetailsInfo.setPanNumber(jo.get("panNumber").getAsString().toUpperCase());
		if(jo.get("serviceRegistrationNumber") != null && !jo.get("serviceRegistrationNumber").toString().equalsIgnoreCase("null"))
		vendorDetailsInfo.setServiceRegistrationNumber(jo.get("serviceRegistrationNumber").getAsString());
		if(jo.get("vatNumber") != null && !jo.get("vatNumber").toString().equalsIgnoreCase("null"))
		vendorDetailsInfo.setVatNumber(jo.get("vatNumber").getAsString());
		if(jo.get("cstNumber") != null && !jo.get("cstNumber").toString().equalsIgnoreCase("null"))
		vendorDetailsInfo.setCstNumber(jo.get("cstNumber").getAsString());
		if(jo.get("tanNumber") != null && !jo.get("tanNumber").toString().equalsIgnoreCase("null"))
		vendorDetailsInfo.setTanNumber(jo.get("tanNumber").getAsString());
		if(jo.get("lstNumber") != null && !jo.get("lstNumber").toString().equalsIgnoreCase("null"))
		vendorDetailsInfo.setLstNumber(jo.get("lstNumber").getAsString());
		vendorDetailsInfo.setSequenceId(sequenceId);
		if(jo.get("employeeStrength") != null && !jo.get("employeeStrength").toString().equalsIgnoreCase("null"))
		vendorDetailsInfo.setEmployeeStrength(jo.get("employeeStrength").getAsInt());
		if(jo.get("lastYearSalesTurnover") != null && !jo.get("lastYearSalesTurnover").toString().equalsIgnoreCase("null"))
		vendorDetailsInfo.setLastYearSalesTurnover(jo.get("lastYearSalesTurnover").getAsDouble());
		if(jo.get("currencyIn") != null && !jo.get("currencyIn").toString().equalsIgnoreCase("null")){
		vendorDetailsInfo.setCurrencyIn(jo.get("currencyIn").getAsString());
		}
		if(jo.get("securityDepositDetails") != null && !jo.get("securityDepositDetails").toString().equalsIgnoreCase("null"))
		vendorDetailsInfo.setSecurityDepositDetails(jo.get("securityDepositDetails").getAsString());
		if(jo.get("discounts") != null && !jo.get("discounts").toString().equalsIgnoreCase("null"))
		vendorDetailsInfo.setDiscounts(jo.get("discounts").getAsString());
		
		vendorDetailsInfo.setAddress1(jo.get("address1").getAsString());
		if(jo.has("address2")  && !jo.get("address2").isJsonNull() && !jo.get("address2").getAsString().equalsIgnoreCase("null") && !jo.get("address2").getAsString().isEmpty())
//		if(jo.get("address2") != null && !jo.get("address2").toString().equalsIgnoreCase("null"))
		vendorDetailsInfo.setAddress2(jo.get("address2").getAsString());
		if(jo.has("address3")  && !jo.get("address3").isJsonNull() && !jo.get("address3").getAsString().equalsIgnoreCase("null") && !jo.get("address3").getAsString().isEmpty())
		//if(jo.get("address3") != null && !jo.get("address3").toString().equalsIgnoreCase("null"))
		vendorDetailsInfo.setAddress3(jo.get("address3").getAsString());
		vendorDetailsInfo.setCountry(jo.get("country").getAsInt());
		if(jo.get("state") != null && !jo.get("state").toString().equalsIgnoreCase("null") && !jo.get("state").toString().isEmpty())
		vendorDetailsInfo.setState(jo.get("state").getAsInt());
		System.out.println(jo.get("city") != null);
		//System.out.println(jo.get("city").getAsString().equalsIgnoreCase("null"));
		
		if(jo.has("city")  && !jo.get("city").isJsonNull() && !jo.get("city").getAsString().equalsIgnoreCase("null") && !jo.get("city").getAsString().isEmpty())
		//if(jo.get("city") != null && !jo.get("city").getAsString().equalsIgnoreCase("null"))
		vendorDetailsInfo.setCity(jo.get("city").getAsString());
		
		if(jo.has("pincode")  && !jo.get("pincode").isJsonNull() && !jo.get("pincode").getAsString().equalsIgnoreCase("null") && !jo.get("pincode").getAsString().isEmpty())
		//if(jo.get("pincode") != null && !jo.get("pincode").getAsString().equalsIgnoreCase("null"))
			vendorDetailsInfo.setPincode(jo.get("pincode").getAsInt());
		vendorDetailsInfo.setCreatedBy(jo.get("createdBy").getAsInt());
		vendorDetailsInfo.setCreatedDate(new Date());
		vendorDetailsInfo.setModifiedBy(jo.get("modifiedBy").getAsInt());
		vendorDetailsInfo.setModifiedDate(new Date());
		
		if(jo.get("reasonForChange") != null && !jo.get("reasonForChange").isJsonNull()  && !jo.get("reasonForChange").toString().equalsIgnoreCase("null"))
			vendorDetailsInfo.setReasonForStatusChange(jo.get("reasonForChange").getAsString());
		
		if(jo.get("statusDate") != null && !jo.get("statusDate").isJsonNull()  && !jo.get("statusDate").toString().equalsIgnoreCase("null")){
			Date statusDate = null;
			try {
				statusDate = (Date) formatter.parse(jo.get("statusDate").getAsString());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			vendorDetailsInfo.setStatusDate(statusDate);
		}
		vendorDetailsInfo.setVendorRegistrationDate(vendorRegistrationDate);
		
		 
		 if(jo.get("vendorDetailsInfoId")!= null && jo.get("vendorDetailsInfoId").getAsInt()+"" != null && jo.get("vendorDetailsInfoId").getAsInt()+"" != "" && jo.get("vendorDetailsInfoId").getAsInt() >0){
			 vendorDetailsInfo.setVendorDetailsInfoId(jo.get("vendorDetailsInfoId").getAsInt());
				log.info(jo.get("vendorDetailsInfoId").getAsInt()+"::in update");			
				session.update(vendorDetailsInfo);
				vendorDetailsInfoId = vendorDetailsInfo.getVendorDetailsInfoId();
			}else{			
				System.out.println("::in Save");
				 vendorDetailsInfoId =  (Integer) session.save(vendorDetailsInfo);
										
			}
		 
		 
		
		if(jo.get("vendorDetailsInfoId")!= null && jo.get("vendorDetailsInfoId").getAsInt()+"" != null && jo.get("vendorDetailsInfoId").getAsInt()+"" != "" && jo.get("vendorDetailsInfoId").getAsInt() >0){						
			Query q = session.createSQLQuery("delete from vendor_industry_subindustry_details where vendor_Id = '"+vendorId+"' and sequence_Id = '"+sequenceId+"' and Transaction_Date ='"+DateHelper.convertDateToSQLString(transactionDate)+"'");
			q.executeUpdate();
		}
		
		/* Save Industry Sector Details*/
		//CustomerIndustrySectorDetails industrySectorDetails = new CustomerIndustrySectorDetails();
		System.out.println(jo.get("subIndustryIds")+"::subInd");
		String sectorIds ="0";
		if(jo.get("subIndustryIds") != null){
		 sectorIds = jo.get("subIndustryIds").toString().replace("[", "").replace("]", "");
		}
		List tempList = session.createSQLQuery("SELECT `Sector_Id`,`Industry_Id` FROM `m_sector` WHERE `Sector_Id`  IN (" + sectorIds + ") ").list();
		
		for (Object object : tempList) {
			Object[] obj = (Object[]) object;				
			session.save(new VendorIndustrySubindustryDetails(new CustomerDetails(jo.get("customerId").getAsInt()),new CompanyDetails(jo.get("companyId").getAsInt()),new VendorDetails(vendorId),Integer.valueOf(obj[1] + ""),Integer.valueOf(obj[0] + ""),transactionDate,jo.get("createdBy").getAsInt(),new Date(),jo.get("modifiedBy").getAsInt(),new Date(),jo.get("isActive").getAsString(),sequenceId));
			//VendorDetails vendorDetails, int industryId, int subIndustryId, Date transactionDate, int createdBy, Date createdDate, int modifiedBy, Date modifiedDate,String isActive,int sequenceId) {
		}
		
		}catch(Exception e){
			log.error("Error Occured ",e);
			vendorDetailsInfoId = null;
			session.getTransaction().rollback();
		}
		session.flush();		
		return vendorDetailsInfoId;
		
		
		
	}
	
	
	public List<VendorDetailsVo> getVendorDetailsbyId(String vendorInfoId){
		VendorDetailsVo vendorDetailsVo = new VendorDetailsVo();
		List<VendorDetailsVo> vendorList = new ArrayList<VendorDetailsVo>();
		Session session = sessionFactory.getCurrentSession();
		try{
			  String q = " select VI.Vendor_Id,VD.Vendor_Code,VI.Customer_Id,VI.Company_Id,VI.Transaction_Date,VI.Vendor_Name,VI.Vendor_Type_Id,VI.Telephone_Number, "
								+" VI.FAX_Number,VI.Email,VI.Website,VI.Mode_Of_Payment_Id,VI.Payment_Frequency_Id "
								+" ,VI.Company_Type_Id,VI.Registered_Under_Id,VI.Registration_Number,VI.Registration_Date,VI.PAN_Number,VI.Service_Registration_Number, "
								+" VI.VAT_Number,VI.CST_Number,VI.TAN_Number, "
								+" VI.LST_Number,VI.Employee_Strength,VI.Last_Year_Sales_Turnover,VI.Currency_In,VI.Security_Deposit_Details,VI.Discounts, "
								+" VI.Address_1,VI.Address_2,VI.Address_3,VI.Country,VI.State,VI.city,VI.Pincode "
								+" ,concat('[',group_concat(Distinct ISD.Industry_Id),']'),group_concat(distinct ISD.SubIndustry_Id),VI.Vendor_details_info_Id,VI.is_Active,VI.company_country  "
								+" ,mc.country_name,ms.state_name, VI.Location_Id, VI.Vendor_Status,Nature_Of_Work,Director_Owner_Proprietor_Name,"
								+ " VI.Status_Date, VI.Reason_For_Status_Change, VI.Vendor_Registration_Date  "
								+" from vendor_details_info VI  "
								+" left outer join  vendor_industry_subindustry_details ISD  "
								+" on (VI.Vendor_Id = ISD.vendor_id and VI.Transaction_Date = ISD.Transaction_Date and VI.Sequence_Id = ISD.Sequence_Id "
								+" and ISD.Transaction_Date <= current_date() and ISD.Sequence_Id in (select max(x.Sequence_Id)  "
								+" from vendor_industry_subindustry_details x where ISD.vendor_id = x.vendor_id "
								+" and x.Transaction_Date <= current_date()) and ISD.Is_Active = 'Y')  "
								+" inner join vendor_details VD on (VI.vendor_id = VD.vendor_id) "
								+" inner join m_country mc on vi.country = mc.Country_Id "
								+" LEFT OUTER join m_state ms on vi.state = ms.state_id "
								//+" LEFT OUTER join m_city mcy on vi.city = mcy.city_id "
								+" where VI.is_active = 'Y'  "
								+" and Vendor_Details_Info_Id = '"+vendorInfoId+"'  "
								+" group by VI.Customer_Id,VI.Company_Id,VI.Vendor_Id ";
			  
			  
			  
			  
			  Query query = session.createSQLQuery(q);				
				List vendorList1 = query.list();
				for (Object vendor : vendorList1) {
					Object[] obj = (Object[]) vendor;	
					vendorDetailsVo = new VendorDetailsVo();
					vendorDetailsVo.setVendorId((Integer)obj[0]);
					vendorDetailsVo.setVendorCode(obj[1]+"");
					vendorDetailsVo.setCustomerId((Integer)obj[2]);
					vendorDetailsVo.setCompanyId((Integer)obj[3]);
					vendorDetailsVo.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[4]));
					vendorDetailsVo.setVendorName(obj[5]+"");
					vendorDetailsVo.setVendorTypeId((Integer)obj[6]);
					if(obj[7] != null){
					vendorDetailsVo.setTelephoneNumber(obj[7]+"");
					}if(obj[8] != null){
					vendorDetailsVo.setFaxNumber(obj[8]+"");
					}if(obj[9] != null){
					vendorDetailsVo.setEmail(obj[9]+"");
					}if(obj[10] != null){
					vendorDetailsVo.setWebsite(obj[10]+"");
					}if(obj[11] != null){
					vendorDetailsVo.setModeOfPaymentId((Integer)obj[11]);
					}if(obj[12] != null){
					vendorDetailsVo.setPaymentFrequencyId((Integer)obj[12]);
					}if(obj[13] != null){
					vendorDetailsVo.setCompanyTypeId((Integer)obj[13]);
					}if(obj[14] != null){
					vendorDetailsVo.setRegisteredUnderId((Integer)obj[14]);
					}if(obj[15] != null){
					vendorDetailsVo.setRegistrationNumber(obj[15]+"");
					}if(obj[16] != null){
					vendorDetailsVo.setRegistrationDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[16]));
					}if(obj[17] != null){
					vendorDetailsVo.setPanNumber(obj[17]+"");
					}if(obj[18] != null){
					vendorDetailsVo.setServiceRegistrationNumber(obj[18]+"");
					}if(obj[19] != null){
					vendorDetailsVo.setVatNumber(obj[19]+"");
					}if(obj[20] != null){
					vendorDetailsVo.setCstNumber(obj[20]+"");
					}if(obj[21] != null){
					vendorDetailsVo.setTanNumber(obj[21]+"");
					}if(obj[22] != null){
					vendorDetailsVo.setLstNumber(obj[22]+"");
					}if(obj[23] != null){
					vendorDetailsVo.setEmployeeStrength((Integer)obj[23]);
					}if(obj[24] != null){
					vendorDetailsVo.setLastYearSalesTurnover((Double)obj[24]);
					}if(obj[25] != null){
					vendorDetailsVo.setCurrencyIn(obj[25]+"");
					}if(obj[26] != null){
					vendorDetailsVo.setSecurityDepositDetails(obj[26]+"");
					}if(obj[27] != null){
					vendorDetailsVo.setDiscounts(obj[27]+"");
					}if(obj[28] != null){
					vendorDetailsVo.setAddress1(obj[28]+"");
					}if(obj[29] != null){
					vendorDetailsVo.setAddress2(obj[29]+"");
					}if(obj[30] != null){
					vendorDetailsVo.setAddress3(obj[30]+"");
					}if(obj[31] != null){
					vendorDetailsVo.setCountry((Integer)obj[31]);
					}if(obj[32] != null){
					vendorDetailsVo.setState((Integer)obj[32]);
					}if(obj[33] != null){
					vendorDetailsVo.setCity((String)obj[33]);
					}if(obj[34] != null){
					vendorDetailsVo.setPincode((Integer)obj[34]);
					}if(obj[35] != null){
					vendorDetailsVo.setIndustyIds(obj[35]+"");
					}if(obj[36] != null){
					vendorDetailsVo.setSubIndustryIds(obj[36]+"");	
					}if(obj[37] != null){
					vendorDetailsVo.setVendorDetailsInfoId((Integer)obj[37]);	
					}if(obj[38] != null){
					vendorDetailsVo.setIsActive(obj[38]+"");
					}if(obj[39] != null){
						vendorDetailsVo.setCountryId((Integer)obj[39]);
					}if(obj[40] != null){
						vendorDetailsVo.setCountryName(obj[40]+"");
					}if(obj[41] != null){
						vendorDetailsVo.setStateName(obj[41]+"");
					}/*if(obj[42] != null){
						vendorDetailsVo.setCityName(obj[42]+"");
					}*/
					vendorDetailsVo.setLocationId((Integer)obj[42] != null ? (Integer)obj[42] : null);
					vendorDetailsVo.setVendorStatus((String)obj[43]);
					if(obj[44] != null)
						vendorDetailsVo.setNatureOfWork(obj[44]+"");
					if(obj[45] != null)
						vendorDetailsVo.setDirectorOwnerProprietorName(obj[45]+"");
					if(obj[46] != null)
						vendorDetailsVo.setStatusDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[46]));
					if(obj[47] != null)
						vendorDetailsVo.setReasonForChange((String)obj[47]);
					if(obj[48] != null)
						vendorDetailsVo.setVendorRegistrationDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[48]));
					vendorList.add(vendorDetailsVo);
				}
			
		}catch(Exception e){
			log.error("Error Occured ",e);
			
		}
		
		return vendorList;
	}
	
	

	public List<SimpleObject> getTransactionDatesListForEditingVendorDetails(Integer vendorId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List vendorTempList = session.createSQLQuery("select Vendor_Details_Info_Id,concat(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),'-',Sequence_Id) from vendor_details_info  where Vendor_Id = '"+vendorId+"'  and  Is_Active = 'Y'  order by Transaction_Date ,Sequence_Id " ).list();
			for (Object customerObject  : vendorTempList) {
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
	public List<SimpleObject> getTransactionHistoryDatesListForVendorBranchDetails(Integer vendorBranchId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List vendorTempList = session.createSQLQuery("SELECT `Vendor_Branch_Details_Info_Id`,CONCAT(DATE_FORMAT(`Transaction_Date`,'%d/%m/%Y'),' - ',`Sequence_Id`)  AS tdate FROM `vendor_branch_details_info` WHERE `Vendor_Branch_Id`="+vendorBranchId+" ORDER BY `Transaction_Date`,`Sequence_Id`" ).list();
			for (Object customerObject  : vendorTempList) {
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
	
	
/*=============================== Vendor Bank Details Start ====================================*/
	
	@Override
	public List<VendorBankDetailsVo> VendorBankGridDetails(String customerId,String companyId,String vendorCode, String vendorName,String vendorId) {
		Session session = sessionFactory.getCurrentSession();
		List<VendorBankDetailsVo> vendorBankDetailsList = new ArrayList<VendorBankDetailsVo>();
		System.out.println("From VendorBankGridDetails() customerId ::"+customerId+", companyId ::"+companyId.isEmpty()+", vendorCode::"+vendorCode+", vendorName :: "+vendorName);
		try {
			
			 String q = "SELECT vb.Vendor_Bank_Id, vb.bank_name,vb.bank_code, vb.transaction_date,CASE vb.is_Active WHEN 'Y' THEN 'Active'  ELSE 'In-Active'  END AS `status`, vdi.vendor_name,ci.company_name, cdi.customer_name,date_format(vb.transaction_date,'%d/%m/%Y') as strTDate "
						+"	FROM vendor_bank_details vb "
						+"	INNER JOIN vendor_details_info vdi  ON vb.vendor_id=vdi.vendor_id "
						+"	INNER JOIN vendor_details vd ON vdi.vendor_id=vd.vendor_id "
						+"	INNER JOIN customer_details_info cdi ON cdi.customer_id=vb.customer_id "
						+"	INNER JOIN company_details_info ci ON ci.company_id = vb.company_id "
						+"	WHERE  CONCAT(DATE_FORMAT(vdi.transaction_date, '%Y%m%d'), vdi.Sequence_Id) = "
							 
						+"	(  "
						+"	SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), vdi1.Sequence_Id)) "
						+"	FROM vendor_details_info vdi1 "
						+"	WHERE vdi.vendor_id = vdi1.vendor_id AND vdi1.transaction_date <= CURRENT_DATE() "
						+"	) "
							 
						+"	AND  CONCAT(DATE_FORMAT(vb.transaction_date, '%Y%m%d'), vb.Sequence_Id) = "
							 
						+"	( "
						+"	SELECT MAX(CONCAT(DATE_FORMAT(vb1.transaction_date, '%Y%m%d'), vb1.Sequence_Id)) "
						+"	FROM vendor_bank_details vb1 "
						+"	WHERE vb.vendor_id = vb1.vendor_id AND vb1.transaction_date <= CURRENT_DATE() "
						+"	) "
							 
						+"	AND  CONCAT(DATE_FORMAT(cdi.transaction_date, '%Y%m%d'), cdi.Customer_Sequence_Id) =  "
						+"	( "
						+"	SELECT MAX(CONCAT(DATE_FORMAT(cdi1.transaction_date, '%Y%m%d'), cdi1.Customer_Sequence_Id)) "
						+"	FROM customer_details_info cdi1 "
						+"	WHERE cdi.customer_id = cdi1.customer_id AND cdi1.transaction_date <= CURRENT_DATE() "
						+"	) "
							
						+"	AND  CONCAT(DATE_FORMAT(ci.transaction_date, '%Y%m%d'), ci.Company_Sequence_Id) =  "
						+"	( "
						+"	SELECT MAX(CONCAT(DATE_FORMAT(cdi1.transaction_date, '%Y%m%d'), cdi1.Company_Sequence_Id)) "
						+"	FROM company_details_info cdi1 "
						+"	WHERE ci.company_id = cdi1.company_id AND cdi1.transaction_date <= CURRENT_DATE() ) "
						+" AND cdi.Is_Active = 'Y' "
						+" AND ci.Is_Active = 'Y' "
						+" AND vdi.Vendor_Status = 'Validated' ";
			 
			 			System.out.println(!customerId.isEmpty()+"::falseorTrue"+customerId != null);
			 			
					 if(customerId != null && !customerId.isEmpty()){
						 System.out.println(customerId+"::customerId");
						 q +=" and cdi.Customer_Id = '"+customerId+"'";				 
					 } 
					 
					 if(companyId != null && !companyId.isEmpty()  ){						 
						 q +=" and ci.Company_Id = '"+companyId+"' ";	
					 } 
					 
					 if(vendorId != null && !vendorId.isEmpty()  ){						 
						 q +=" and vd.vendor_id  = '"+vendorId+"' ";	
					 } 
					 
					 if(vendorCode != null && !vendorCode.isEmpty() ){						 
						 q +=" and vd.Vendor_Code LIKE '"+vendorCode+"%'";	
					 }
					 
					 if(vendorName != null && !vendorName.isEmpty() ){						 
						 q +=" and vdi.Vendor_Name LIKE '"+vendorName+"%'";	
					 }
					 
					 q +=" ORDER BY vb.Bank_Name asc";
					 
				//	q = q 	+" order by BD.Transaction_Date desc,BD.Sequence_Id desc ";						
								System.out.println("query"+q);					 
			
			 List tempList = session.createSQLQuery(q).list();
			
			for(Object o : tempList){
				Object[] obj = (Object[]) o;
				VendorBankDetailsVo vendorBankDetails = new VendorBankDetailsVo();
				vendorBankDetails.setVendorBankId((Integer)obj[0]);				
				/*vendorBankDetails.setLocationDetails(new LocationDetails((Integer)obj[1]));
				vendorBankDetails.setCustomerDetails(new CustomerDetails((Integer)obj[2]));
				vendorBankDetails.setCompanyDetails(new CompanyDetails((Integer)obj[3]));
				vendorBankDetails.setVendorDetails(new VendorDetails((Integer)obj[4]));	*/
				vendorBankDetails.setBankName(obj[1]+"");
				vendorBankDetails.setBankCode(obj[2]+"");
				vendorBankDetails.setTransactionDate((Date)obj[3]);
				vendorBankDetails.setStrTransactionDate(obj[8]+"");
				vendorBankDetails.setIsActive(obj[4]+"");	
				vendorBankDetails.setVendorName(obj[5]+"");
				vendorBankDetails.setCompanyName(obj[6]+"");
				vendorBankDetails.setCustomerNmae(obj[7]+"");
				vendorBankDetailsList.add(vendorBankDetails);
			}						
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}
		
		
		return vendorBankDetailsList;
	}
	
	@Override
	public Integer saveOrUpdateVendorBankDetails(VendorBankDetailsVo vendorBankDetails) {
		Session session = sessionFactory.getCurrentSession();
		VendorBankDetails bankDetails = new VendorBankDetails(); 
		Integer vendorBankId = 0;
		try {			
			
			bankDetails.setLocationDetails(new LocationDetails(vendorBankDetails.getLocationId()));
			bankDetails.setCustomerDetails(new CustomerDetails(vendorBankDetails.getCustomerId()));
			bankDetails.setCompanyDetails(new CompanyDetails(vendorBankDetails.getCompanyid()));
			bankDetails.setVendorDetails(new VendorDetails(vendorBankDetails.getVendorId()));
			bankDetails.setCompanyCountry(vendorBankDetails.getCountryid());			
			bankDetails.setBankCode(vendorBankDetails.getBankCode());
			
			bankDetails.setBankName(vendorBankDetails.getBankName());
			bankDetails.setTransactionDate(vendorBankDetails.getTransactionDate());
			bankDetails.setSequenceId(vendorBankDetails.getSequenceId());
			bankDetails.setAccountHolderName(vendorBankDetails.getAccountHolderName());
			bankDetails.setAccountNumber(vendorBankDetails.getAccountNumber());
			bankDetails.setBankCity(vendorBankDetails.getBankCity());
			bankDetails.setBranch(vendorBankDetails.getBranch());
			bankDetails.setIfscCode(vendorBankDetails.getIfscCode());
			bankDetails.setMicrCode(vendorBankDetails.getMicrCode());
			bankDetails.setPhoneNumber(vendorBankDetails.getPhoneNumber());
			bankDetails.setAddressLine1(vendorBankDetails.getAddressLine1());
			bankDetails.setAddressLine2(vendorBankDetails.getAddressLine2());
			bankDetails.setAddressLine3(vendorBankDetails.getAddressLine3());
			bankDetails.setCountry(vendorBankDetails.getCountry());
			bankDetails.setState(vendorBankDetails.getState());
			bankDetails.setCity(vendorBankDetails.getCity());
			bankDetails.setPincode(vendorBankDetails.getPincode());
			bankDetails.setCreatedBy(vendorBankDetails.getCreatedBy());
			bankDetails.setCreatedDate(new Date());
			bankDetails.setModifiedBy(vendorBankDetails.getModifiedBy());
			bankDetails.setModifiedDate(new Date());
			bankDetails.setIsActive(vendorBankDetails.getIsActive());
			
			BigInteger SequenceId ;
			if(vendorBankDetails != null &&  vendorBankDetails.getVendorBankId() > 0){	
				// uniqueId = (int) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Sequence_Id),1) FROM `vendor_bank_details` b  WHERE  b.Unique_Id = '"+vendorBankDetails.getUniqueId()+"' AND  b.`Transaction_Date` = '"+DateHelper.convertDateToSQLString(vendorBankDetails.getTransactionDate())+"'").list().get(0);
				bankDetails.setSequenceId(vendorBankDetails.getSequenceId());
				bankDetails.setUniqueId(vendorBankDetails.getUniqueId());
				bankDetails.setVendorBankId(vendorBankDetails.getVendorBankId());
				session.update(bankDetails);
				vendorBankId = bankDetails.getVendorBankId();
			}else  {
				SequenceId = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Sequence_Id),0) FROM `vendor_bank_details` b  WHERE  b.Unique_Id = '"+vendorBankDetails.getUniqueId()+"' AND  b.`Transaction_Date` = '"+DateHelper.convertDateToSQLString(vendorBankDetails.getTransactionDate())+"'").list().get(0);
				if(SequenceId.intValue() >0){
					bankDetails.setSequenceId(SequenceId.intValue()+1);
					bankDetails.setUniqueId(vendorBankDetails.getUniqueId());
				}else{
					bankDetails.setSequenceId(1);
					BigInteger uniqueId = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(unique_id),0) as id FROM vendor_bank_details").list().get(0);
					 bankDetails.setUniqueId(uniqueId.intValue()+1);
				}
				
				vendorBankId = (Integer) session.save(bankDetails);			
			}
			
			
			
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		
		return vendorBankId;
	}
	
	
	@Override
	public List<VendorBankDetailsVo> getVendorBankDetailsbyId(String vendorBankId){
		VendorBankDetails vendorBankDetails = new VendorBankDetails();
		List<VendorBankDetailsVo> vendorList = new ArrayList<VendorBankDetailsVo>();
		Session session = sessionFactory.getCurrentSession();
		try{
			vendorBankDetails = (VendorBankDetails) session.load(VendorBankDetails.class, Integer.parseInt(vendorBankId));
			
			VendorBankDetailsVo bankDetailsVo = new VendorBankDetailsVo();
			bankDetailsVo.setVendorBankId(vendorBankDetails.getVendorBankId());
			bankDetailsVo.setCustomerId(vendorBankDetails.getCustomerDetails().getCustomerId());
			bankDetailsVo.setCompanyid(vendorBankDetails.getCompanyDetails().getCompanyId());
			bankDetailsVo.setLocationId(vendorBankDetails.getLocationDetails().getLocationId());
			bankDetailsVo.setVendorId(vendorBankDetails.getVendorDetails().getVendorId());
			bankDetailsVo.setCountryid(vendorBankDetails.getCompanyCountry());
			bankDetailsVo.setBankCode(vendorBankDetails.getBankCode());	
			bankDetailsVo.setBankName(vendorBankDetails.getBankName());		
			bankDetailsVo.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)vendorBankDetails.getTransactionDate()));
			bankDetailsVo.setIsActive(vendorBankDetails.getIsActive());
			bankDetailsVo.setAccountHolderName(vendorBankDetails.getAccountHolderName());
			bankDetailsVo.setAccountNumber(vendorBankDetails.getAccountNumber());
			bankDetailsVo.setBankCity(vendorBankDetails.getBankCity());
			bankDetailsVo.setBranch(vendorBankDetails.getBranch());
			bankDetailsVo.setMicrCode(vendorBankDetails.getMicrCode());
			bankDetailsVo.setIfscCode(vendorBankDetails.getIfscCode());
			bankDetailsVo.setPhoneNumber(vendorBankDetails.getPhoneNumber());
			bankDetailsVo.setAddressLine1(vendorBankDetails.getAddressLine1());
			bankDetailsVo.setAddressLine2(vendorBankDetails.getAddressLine2());
			bankDetailsVo.setAddressLine3(vendorBankDetails.getAddressLine3());
			bankDetailsVo.setCountry(vendorBankDetails.getCountry());
			bankDetailsVo.setState(vendorBankDetails.getState());
			bankDetailsVo.setCity(vendorBankDetails.getCity());			
			bankDetailsVo.setPincode(vendorBankDetails.getPincode());
			bankDetailsVo.setSequenceId(vendorBankDetails.getSequenceId());
			bankDetailsVo.setUniqueId(vendorBankDetails.getUniqueId());
			bankDetailsVo.setCreatedDate(vendorBankDetails.getCreatedDate());
			bankDetailsVo.setModifiedDate(vendorBankDetails.getModifiedDate());
				
			
			vendorList.add(bankDetailsVo);			
		}catch(Exception e){
			log.error("Error Occured ",e);
			
		}
		
		return vendorList;
	}
	
	
	@Override
	public List<SimpleObject> getTransactionDatesListForEditingVendorBankDetails(Integer uniqueId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List vendorTempList = session.createSQLQuery("select Vendor_Bank_Id,concat(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),'-',Sequence_Id) from vendor_bank_details  where Unique_Id = '"+uniqueId+"' and  Is_Active = 'Y'  order by Transaction_Date ,Sequence_Id " ).list();
			for (Object customerObject  : vendorTempList) {
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
	public List<SimpleObject> getLocationNamesAsDropDown(String customerId, String companyId){
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> simpleObjects = new ArrayList<SimpleObject>(); 
		try {
			List tempList = session.createSQLQuery("select DISTINCT ld.Location_Id,a.Location_Name "
					+ "  from location_details_info a,location_details ld "
					+ "  where a.Transaction_Date = (select max(c.Transaction_Date) from location_details_info c where c.Transaction_Date<= current_date() "
					+ "  and c.Location_Details_Id = a.Location_Details_Id) and " + "  a.Location_Sequence_Id in  "
					+ "  (select max(b.Location_Sequence_Id) from location_details_info b where b.Transaction_Date <= current_date() "
					+ "  and a.Location_Details_Id = b.Location_Details_Id) "
					+ "  and a.Location_Id = ld.Location_Id and ld.customer_Id= '"+customerId+"' and ld.company_Id = '"+companyId+"' 	"
					+ "  group by ld.Customer_Id,ld.company_id,a.Location_Details_Id "
					+ "  order by a.Location_Name asc").list();				
			for(Object  o: tempList){
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
	public boolean validateVendorCode(VendorDetailsVo paramVendor) {
		boolean flag = true;
		
		List returnList = (sessionFactory.getCurrentSession()).createQuery("FROM VendorDetails WHERE customerDetails = "+paramVendor.getCustomerId()+
				  " AND companyDetails = "+paramVendor.getCompanyId()+" AND vendorCode = '"+paramVendor.getVendorCode()+"'").list();
		if(returnList != null && returnList.size() > 0){
			flag = false;
		}else{
			flag = true;
		}
		
		return flag;
		
	}
	
	@Override
	public List<SimpleObject> getCompanyCountrysAsDropDown(String customerId, String companyId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean validateVendorBankCode(VendorBankDetailsVo paramVendor) {
		boolean flag = true;

		List returnList = (sessionFactory.getCurrentSession()).createQuery("FROM VendorBankDetails WHERE customerDetails = "+paramVendor.getCustomerId()+
				  " AND companyDetails = "+paramVendor.getCompanyid()+" AND vendorDetails = '"+paramVendor.getVendorId()+"' and bankCode = '"+paramVendor.getBankCode()+"'").list();
		if(returnList != null && returnList.size() > 0){
			flag = false;
		}else{
			flag = true;
		}
		
		return flag;
	}
	
	
	
	/*=============================== Vendor Bank Details END ====================================*/
	
	
	
	
}
