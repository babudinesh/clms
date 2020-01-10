package com.Ntranga.CLMS.Dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.DefineComplianceTypesVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.VendorComplianceVo;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.LocationDetails;
import com.Ntranga.core.CLMS.entities.MCountry;
import com.Ntranga.core.CLMS.entities.MCurrency;
import com.Ntranga.core.CLMS.entities.MState;
import com.Ntranga.core.CLMS.entities.VendorCompliance;
import com.Ntranga.core.CLMS.entities.VendorDetails;
import com.Ntranga.core.CLMS.entities.VendorDetailsInfo;

import common.Logger;

@SuppressWarnings("rawtypes")
@Transactional
@Repository("vendorComplianceDao")
public class VendorComplianceDaoImpl implements VendorComplianceDao {

	private static Logger log = Logger.getLogger(VendorComplianceDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	/*
	 * This method will be used to save or update the Vendor Compliances
	 */
	@Override
	public Integer saveVendorCompliance(VendorComplianceVo paramCompliance) {
		log.info("Entered in saveVendorCompliance()  ::   "+paramCompliance);

		Session session = sessionFactory.getCurrentSession();
		Integer vendorComplianceId = 0;
		BigInteger vendorComplianceSequenceId = new BigInteger("0");	
		
		VendorCompliance vendor = new VendorCompliance();
		//List<VendorComplianceVo> vCompliance = new ArrayList<VendorComplianceVo>();
		
		//VendorDetails details = new VendorDetails();
		try{
			
			if(paramCompliance != null &&  paramCompliance.getVendorComplianceId() > 0){
				vendor = (VendorCompliance) sessionFactory.getCurrentSession().load(VendorCompliance.class, paramCompliance.getVendorComplianceId());
				vendor.setCustomerDetails(new CustomerDetails(paramCompliance.getCustomerId()));
				vendor.setCompanyDetails(new CompanyDetails(paramCompliance.getCompanyId()));
				vendor.setCountryDetails(new MCountry(paramCompliance.getCountryId()));
				vendor.setVendorDetails(new VendorDetails(paramCompliance.getVendorId()));
				vendor.setLocationDetails(new LocationDetails(paramCompliance.getLocationId()));
				vendor.setComplianceUniqueId(paramCompliance.getDefineComplianceTypeId());
				vendor.setTransactionDate(paramCompliance.getTransactionDate());
				vendor.setStatus(paramCompliance.getStatus());
				vendor.setIssueDate(paramCompliance.getIssueDate());
				vendor.setExpiryDate(paramCompliance.getExpiryDate());
				vendor.setRegisteredState(new MState(paramCompliance.getRegisteredState()));
				vendor.setRenewalDate(paramCompliance.getRenewalDate());
				//vendor.setValidityPeriod(paramCompliance.getValidityPeriod());
				//vendor.setPeriodType(paramCompliance.getPeriodType());
				vendor.setTransactionAmount(paramCompliance.getTransactionAmount());
				vendor.setAmountType(new MCurrency(paramCompliance.getAmountType() == 0 ? null : paramCompliance.getAmountType()));
				vendor.setNumberOfWorkersCovered(paramCompliance.getNumberOfWorkersCovered());
				vendor.setDocumentPath(paramCompliance.getPath());
				vendor.setReason(paramCompliance.getReason());
				vendor.setRemarks(paramCompliance.getRemarks());
				//vendor.setMandatory(paramCompliance.getMandatory() == true ? "Y" : "N");
				vendor.setModifiedBy(paramCompliance.getModifiedBy());
				vendor.setModifiedDate(new Date());
				session.update(vendor);
				vendorComplianceId = vendor.getVendorComplianceId();
			}else{	
				vendor = new VendorCompliance();
				vendor.setCustomerDetails(new CustomerDetails(paramCompliance.getCustomerId()));
				vendor.setCompanyDetails(new CompanyDetails(paramCompliance.getCompanyId()));
				vendor.setCountryDetails(new MCountry(paramCompliance.getCountryId()));
				vendor.setVendorDetails(new VendorDetails(paramCompliance.getVendorId()));
				vendor.setLocationDetails(new LocationDetails(paramCompliance.getLocationId()));
				vendor.setComplianceUniqueId(paramCompliance.getDefineComplianceTypeId());
				vendor.setTransactionDate(paramCompliance.getTransactionDate());
				vendor.setStatus(paramCompliance.getStatus());
				vendor.setIssueDate(paramCompliance.getIssueDate());
				vendor.setLicensePolicyNumber(paramCompliance.getLicensePolicyNumber());
				vendor.setExpiryDate(paramCompliance.getExpiryDate());
				vendor.setRegisteredState(new MState(paramCompliance.getRegisteredState()));
				vendor.setRenewalDate(paramCompliance.getRenewalDate());
				vendor.setDocumentPath(paramCompliance.getPath());
				vendor.setReason(paramCompliance.getReason());
				vendor.setTransactionAmount(paramCompliance.getTransactionAmount());
				vendor.setAmountType(new MCurrency(paramCompliance.getAmountType() == 0 ? null : paramCompliance.getAmountType()));
				vendor.setNumberOfWorkersCovered(paramCompliance.getNumberOfWorkersCovered());
				vendor.setRemarks(paramCompliance.getRemarks());
				//vendor.setMandatory(paramCompliance.getMandatory() == true ? "Y" : "N");
				vendor.setCreatedBy(paramCompliance.getCreatedBy());
				vendor.setModifiedBy(paramCompliance.getModifiedBy());
				vendor.setCreatedDate(new Date());
				vendor.setModifiedDate(new Date());
				
				if(paramCompliance != null && paramCompliance.getVendorComplianceUniqueId() > 0){
					vendorComplianceSequenceId  = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Vendor_Compliance_Sequence_Id),0) FROM Vendor_Compliance vendor"
							+ "  WHERE  vendor.Vendor_Compliance_Unique_Id = "+paramCompliance.getVendorComplianceUniqueId() +" AND vendor.`Transaction_Date` = '"+DateHelper.convertDateToSQLString(paramCompliance.getTransactionDate())+"' and vendor.Customer_Id='"+paramCompliance.getCustomerId()+"' And vendor.Company_Id = '"+paramCompliance.getCompanyId()+"'").list().get(0);
					log.info("Vendor Compliance sequence Id : "+vendorComplianceSequenceId);
					vendor.setVendorComplianceUniqueId( paramCompliance.getVendorComplianceUniqueId() );	
				}else {
					BigInteger vendorComplianceUniqueId = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Vendor_Compliance_Unique_Id),0) as id FROM  Vendor_Compliance vendor").list().get(0);
					vendor.setVendorComplianceUniqueId( vendorComplianceUniqueId.intValue()+1 );					
				}
				vendor.setVendorComplianceSequenceId(vendorComplianceSequenceId.intValue()+1);			
				vendorComplianceId = (Integer) session.save(vendor);
			}
			//}
			session.clear();
			
			// To get the latest vendor record
			List vendorDetailsqry  = session.createQuery(" FROM  VendorDetailsInfo vdi WHERE "
					+" CONCAT(DATE_FORMAT(vdi.transactionDate, '%Y%m%d'), LPAD(vdi.sequenceId, 2, '0')) = (SELECT  "
					+" MAX(CONCAT(DATE_FORMAT(vdi1.transactionDate, '%Y%m%d'),   LPAD(vdi1.sequenceId, 2, '0'))) "
					+"	FROM  VendorDetailsInfo vdi1 "
						+" WHERE "
							+" vdi.customerDetails = vdi1.customerDetails "
							+" AND vdi.companyDetails = vdi1.companyDetails "
							+" AND vdi.vendorDetails = vdi1.vendorDetails "
							+" AND vdi1.transactionDate <= CURDATE() ) "
					+ " AND vdi.customerDetails = "+paramCompliance.getCustomerId()
					+ " AND vdi.companyDetails = "+paramCompliance.getCompanyId()
					+ " AND vdi.vendorDetails = "+paramCompliance.getVendorId()).list();
			session.clear();
			
			//To get all the latest compliance types  based on customer_id, company_id and Vendor_id
			String complianceQuery   = " SELECT  compliance.Doccument_Name, venComp.Status, venComp.Customer_Id, venComp.Company_Id, "
									+ " venComp.Vendor_Id, venComp.Expiry_Date, venComp.Compliance_Unique_Id  "
									+ " FROM vendor_compliance venComp "
									+ " LEFT JOIN define_compliance_types compliance ON compliance.Compliance_Unique_Id = venComp.Compliance_Unique_Id "
									+ " WHERE CONCAT(DATE_FORMAT(`venComp`.`Transaction_Date`, '%Y%m%d'), LPAD(`venComp`.`Vendor_Compliance_Sequence_Id`, 2, '0')) = (SELECT  "
											+" MAX(CONCAT(DATE_FORMAT(`venComp1`.`Transaction_Date`, '%Y%m%d'),   LPAD(`venComp1`.`Vendor_Compliance_Sequence_Id`, 2, '0'))) "
											+"	FROM  vendor_compliance venComp1 "
											+" WHERE venComp.`Customer_Id` = `venComp1`.`Customer_Id` "
											+" AND `venComp`.`Company_Id` = `venComp1`.`Company_Id` "
											+" AND `venComp`.`Vendor_Id` = `venComp1`.`Vendor_Id` "
											+" AND `venComp`.`Compliance_Unique_Id` = `venComp1`.`Compliance_Unique_Id` "
											+" AND  venComp.vendor_Compliance_unique_id = venComp1.vendor_Compliance_unique_id "
											+" AND `venComp1`.`Transaction_Date` <= CURDATE() ) "
									+ " AND compliance.Is_Mandatory = 'Y' "
									+ " AND venComp.Customer_Id = "+paramCompliance.getCustomerId()
									+ " AND venComp.Company_Id = "+paramCompliance.getCompanyId()
									+ " AND venComp.Vendor_Id = "+paramCompliance.getVendorId()
									//+" AND venComp.Expiry_Date >= CURDATE() "
									+" GROUP BY venComp.Vendor_Compliance_Unique_id ORDER BY compliance.Doccument_Name,venComp.Status,venComp.Expiry_Date asc";
			
			//To get all the latest compliance types  based on customer_id, company_id and Vendor_id
			List numOfCompliances = session.createSQLQuery(complianceQuery).list();
			List<VendorComplianceVo> complianceTypesList = new ArrayList<VendorComplianceVo>();
			
			if(numOfCompliances != null && numOfCompliances.size() > 0){
				for(Object o : numOfCompliances){
					Object[] obj = (Object[]) o;
					VendorComplianceVo compliance = new VendorComplianceVo();
					compliance.setComplianceName((String)obj[0]);	
					compliance.setStatus((String)(obj[1]));
					compliance.setExpiryDate((Date)(obj[5]));
					compliance.setDefineComplianceTypeId((Integer)(obj[6]));
					complianceTypesList.add(compliance);
				}
			}
			
			//To get all the latest distinct compliance types  based on customer_id, company_id and Vendor_id
			List distinctCompliances  = session.createSQLQuery(" SELECT distinct venComp.Compliance_Unique_Id, venComp.Status "
					+ " FROM vendor_compliance venComp "
					+ " LEFT JOIN define_compliance_types compliance ON compliance.Compliance_Unique_Id = venComp.Compliance_Unique_Id "
					+ " WHERE  CONCAT(DATE_FORMAT(`venComp`.`Transaction_Date`, '%Y%m%d'), LPAD(`venComp`.`Vendor_Compliance_Sequence_Id`, 2, '0')) = (SELECT  "
							+" MAX(CONCAT(DATE_FORMAT(`venComp1`.`Transaction_Date`, '%Y%m%d'),   LPAD(`venComp1`.`Vendor_Compliance_Sequence_Id`, 2, '0'))) "
								+"	FROM  vendor_compliance venComp1 "
								+" WHERE "
								+" venComp.`Customer_Id` = `venComp1`.`Customer_Id` "
								+" AND `venComp`.`Company_Id` = `venComp1`.`Company_Id` "
								+" AND `venComp`.`Vendor_Id` = `venComp1`.`Vendor_Id` "
								+" AND `venComp`.`Compliance_Unique_Id` = `venComp1`.`Compliance_Unique_Id` "
								+" AND  venComp.vendor_Compliance_unique_id = venComp1.vendor_Compliance_unique_id "
								+" AND `venComp1`.`Transaction_Date` <= CURDATE() ) "
					+ " AND compliance.Is_Mandatory = 'Y' "
					+ " AND venComp.Customer_Id = "+paramCompliance.getCustomerId()
					+ " AND venComp.Company_Id = "+paramCompliance.getCompanyId()
					+ " AND venComp.Vendor_Id = "+paramCompliance.getVendorId()
					+ " AND venComp.Status = 'Verified' "
					+" AND venComp.Expiry_Date >= CURDATE() "
					+" GROUP BY venComp.Vendor_Compliance_Unique_id ORDER BY compliance.Doccument_Name asc").list();
			session.clear();
			
			boolean laborLicense = false;
	    	// To get all the latest define_compliance_types records dropdown
	    	String queryList =" SELECT 	`Compliance_Unique_Id`, `Doccument_Name`, `Is_Active`, Is_Mandatory FROM `define_compliance_types` parent "
	    					+ " WHERE CONCAT(DATE_FORMAT(`parent`.`Transaction_Date`, '%Y%m%d'), LPAD(`parent`.`Sequence_Id`, 2, '0')) =   (SELECT MAX(CONCAT(DATE_FORMAT(`child`.`Transaction_Date`, '%Y%m%d'), LPAD(`child`.`Sequence_Id`, 2, '0'))) FROM `define_compliance_types` child WHERE child.`Transaction_Date` <= Current_Date() AND parent.`Customer_Id`= child.`Customer_Id` AND parent.`Company_Id`=child.`Company_Id` ) AND `Company_Id`= "+paramCompliance.getCompanyId()+" AND `Customer_Id`="+paramCompliance.getCustomerId()+" AND Applicable_To = ( 'Vendor' OR 'Both' ) AND parent.Is_Active = 'Y' AND parent.Is_Mandatory = 'Y' ORDER BY Doccument_Name asc";
	    	List compliancesTypes = sessionFactory.getCurrentSession().createSQLQuery(queryList).list();
			
	    	List<DefineComplianceTypesVo> complianceDropDownList = new ArrayList<>();
	    	if(compliancesTypes != null && compliancesTypes.size() > 0){
				for(Object o : compliancesTypes){
					Object[] obj = (Object[]) o;
					DefineComplianceTypesVo compliance = new DefineComplianceTypesVo();
					compliance.setComplianceUniqueId((Integer)obj[0]);
					compliance.setDoccumentName((String)obj[1]);	
					compliance.setIsActive((String)obj[2]);
					compliance.setIsMandatory(true);
					
					if(compliance.getComplianceUniqueId() != null && compliance.getComplianceUniqueId() == 1){
						laborLicense = true;
					}
					complianceDropDownList.add(compliance);
				}
			}
			
	    	System.out.println(laborLicense+"    ::::  Is Labour License Exists");
	    	
			if( vendorDetailsqry != null && vendorDetailsqry.size() > 0 && complianceTypesList != null && complianceTypesList.size() > 0){
				int uniqueCompliance = 0; // if distinct compliances with validity verified 
				int duplicateDocs = 0;// if the duplicate license exists and validity is not expired
				int expiredValid = 0;// if validity is expired
				int savedDocs = 0; // if saved and validity is not expired
				
				
				if(complianceTypesList != null &&  complianceDropDownList != null){
					for(int j = 0; j< complianceDropDownList.size(); j++){
						for(int i = 0 ; i< complianceTypesList.size(); i++){
							// if verified and not expired 
							if(complianceDropDownList.get(j).getDoccumentName().equalsIgnoreCase(complianceTypesList.get(i).getComplianceName()) && complianceTypesList.get(i).getStatus().equalsIgnoreCase("Verified") && complianceTypesList.get(i).getExpiryDate().compareTo(new Date()) >= 0 ){
					    		// For duplicate document name in vendor compliance
					    		if(i > 0 && complianceTypesList.get(i).getComplianceName().equalsIgnoreCase(complianceTypesList.get(i-1).getComplianceName()) && complianceTypesList.get(i).getStatus().equalsIgnoreCase(complianceTypesList.get(i-1).getStatus()) &&  complianceTypesList.get(i-1).getExpiryDate().compareTo(new Date()) >= 0){
					    			duplicateDocs++;
					    		}else{
					    			//count if verified,not expired and unique document
					    			System.out.println();
					    			uniqueCompliance++;
					    		}
					    	}
							// if verified but expired
							else if(complianceTypesList.get(i).getExpiryDate().compareTo(new Date()) < 0  && complianceDropDownList.get(i).getDoccumentName().equalsIgnoreCase(complianceTypesList.get(i).getComplianceName()) && complianceTypesList.get(i).getStatus().equalsIgnoreCase("Verified")  ){
					    		// For duplicate document name in vendor compliance
					    		if(i > 0 && complianceTypesList.get(i).getComplianceName().equalsIgnoreCase(complianceTypesList.get(i-1).getComplianceName()) && complianceTypesList.get(i).getStatus().equalsIgnoreCase(complianceTypesList.get(i-1).getStatus()) && complianceTypesList.get(i-1).getExpiryDate().compareTo(new Date()) < 0){
					    			//do nothing
					    		}else{
					    			expiredValid++;
					    		}
					    	}
							//if saved but not expired
							else if(complianceDropDownList.get(j).getDoccumentName().equalsIgnoreCase(complianceTypesList.get(i).getComplianceName()) && complianceTypesList.get(i).getStatus().equalsIgnoreCase("Saved") && complianceTypesList.get(i).getExpiryDate().compareTo(new Date()) >= 0 ){
					    		// For duplicate document name in vendor compliance
					    		if(i > 0 && complianceTypesList.get(i).getComplianceName().equalsIgnoreCase(complianceTypesList.get(i-1).getComplianceName()) && complianceTypesList.get(i-1).getExpiryDate().compareTo(new Date()) >= 0){
					    			// do nothing
					    		}else{
					    			savedDocs++;
					    		}
					    	}		
						}//inner for loop
					}// outer for loop
				}// inner if
				log.info("uniqueCompliance = "+uniqueCompliance+",  count1 = , "+duplicateDocs+",  expiredValid = "+expiredValid+ ",  savedDocs = "+savedDocs+" @@@@@@@@@@@@@@@@@@@@@@@@@@@");
				
				if(paramCompliance.getTransactionDate().compareTo((((VendorDetailsInfo)vendorDetailsqry.get(0)).getTransactionDate())) > 0){
					((VendorDetailsInfo)vendorDetailsqry.get(0)).setVendorDetailsInfoId(((VendorDetailsInfo)vendorDetailsqry.get(0)).getVendorDetailsInfoId()+1);
				}else{
					((VendorDetailsInfo)vendorDetailsqry.get(0)).setSequenceId(((VendorDetailsInfo)vendorDetailsqry.get(0)).getSequenceId()+1);
				}
				((VendorDetailsInfo)vendorDetailsqry.get(0)).setReasonForStatusChange(null);
				((VendorDetailsInfo)vendorDetailsqry.get(0)).setStatusDate(null);
				
			    if(distinctCompliances.size() >= compliancesTypes.size()){
			    	
			    	
			    	if(laborLicense && (uniqueCompliance == compliancesTypes.size() &&  expiredValid == 0) && savedDocs == 0 && (((VendorDetailsInfo)vendorDetailsqry.get(0)).getVendorStatus().equalsIgnoreCase("New") || ((VendorDetailsInfo)vendorDetailsqry.get(0)).getVendorStatus().equalsIgnoreCase("Pending For Approval"))){
			    		((VendorDetailsInfo)vendorDetailsqry.get(0)).setVendorStatus("Validated");
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setTransactionDate(paramCompliance.getTransactionDate());
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setCreatedDate(new Date());
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setModifiedDate(new Date());
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setCreatedBy(paramCompliance.getCreatedBy());
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setModifiedBy(paramCompliance.getModifiedBy());
				    	session.save((VendorDetailsInfo)vendorDetailsqry.get(0));
			    	}else if(!laborLicense && (uniqueCompliance == compliancesTypes.size() &&  expiredValid == 0) && savedDocs == 0 && (((VendorDetailsInfo)vendorDetailsqry.get(0)).getVendorStatus().equalsIgnoreCase("New") || ((VendorDetailsInfo)vendorDetailsqry.get(0)).getVendorStatus().equalsIgnoreCase("Pending For Approval"))){
			    		((VendorDetailsInfo)vendorDetailsqry.get(0)).setVendorStatus("Pending For Approval");
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setTransactionDate(paramCompliance.getTransactionDate());
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setCreatedDate(new Date());
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setModifiedDate(new Date());
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setCreatedBy(paramCompliance.getCreatedBy());
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setModifiedBy(paramCompliance.getModifiedBy());
				    	session.save((VendorDetailsInfo)vendorDetailsqry.get(0));
			    	}else if(laborLicense && (uniqueCompliance == 0 &&  expiredValid == compliancesTypes.size() ) && savedDocs == 0 && (((VendorDetailsInfo)vendorDetailsqry.get(0)).getVendorStatus().equalsIgnoreCase("New") || ((VendorDetailsInfo)vendorDetailsqry.get(0)).getVendorStatus().equalsIgnoreCase("Pending For Approval"))){
			    		((VendorDetailsInfo)vendorDetailsqry.get(0)).setVendorStatus("Validated");
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setTransactionDate(paramCompliance.getTransactionDate());
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setCreatedDate(new Date());
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setModifiedDate(new Date());
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setCreatedBy(paramCompliance.getCreatedBy());
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setModifiedBy(paramCompliance.getModifiedBy());
				    	session.save((VendorDetailsInfo)vendorDetailsqry.get(0));
			    	}else if(!laborLicense && (uniqueCompliance == 0 &&  expiredValid == compliancesTypes.size() ) && savedDocs == 0 && (((VendorDetailsInfo)vendorDetailsqry.get(0)).getVendorStatus().equalsIgnoreCase("New") || ((VendorDetailsInfo)vendorDetailsqry.get(0)).getVendorStatus().equalsIgnoreCase("Pending For Approval"))){
			    		((VendorDetailsInfo)vendorDetailsqry.get(0)).setVendorStatus("Pending For Approval");
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setTransactionDate(paramCompliance.getTransactionDate());
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setCreatedDate(new Date());
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setModifiedDate(new Date());
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setCreatedBy(paramCompliance.getCreatedBy());
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setModifiedBy(paramCompliance.getModifiedBy());
				    	session.save((VendorDetailsInfo)vendorDetailsqry.get(0));
			    	}else if( uniqueCompliance < compliancesTypes.size() &&  savedDocs > 0 &&  ((VendorDetailsInfo)vendorDetailsqry.get(0)).getVendorStatus().equalsIgnoreCase("Validated")){
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setVendorStatus("Pending For Approval");
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setTransactionDate(paramCompliance.getTransactionDate());
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setCreatedDate(new Date());
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setModifiedDate(new Date());
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setCreatedBy(paramCompliance.getCreatedBy());
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setModifiedBy(paramCompliance.getModifiedBy());
						session.save((VendorDetailsInfo)vendorDetailsqry.get(0));
						
				    }else if(uniqueCompliance < compliancesTypes.size()   && expiredValid != complianceTypesList.size() && savedDocs > 0  &&  ((VendorDetailsInfo)vendorDetailsqry.get(0)).getVendorStatus().equalsIgnoreCase("Validated")){
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setVendorStatus("Pending For Approval");
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setTransactionDate(paramCompliance.getTransactionDate());
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setCreatedDate(new Date());
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setModifiedDate(new Date());
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setCreatedBy(paramCompliance.getCreatedBy());
				    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setModifiedBy(paramCompliance.getModifiedBy());
						session.save((VendorDetailsInfo)vendorDetailsqry.get(0));
						
				   }
			    	
			    }else if( ((VendorDetailsInfo)vendorDetailsqry.get(0)).getVendorStatus().equalsIgnoreCase("Validated") ){
			    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setVendorStatus("Pending For Approval");
			    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setTransactionDate(paramCompliance.getTransactionDate());
			    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setCreatedDate(new Date());
			    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setModifiedDate(new Date());
			    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setCreatedBy(paramCompliance.getCreatedBy());
			    	((VendorDetailsInfo)vendorDetailsqry.get(0)).setModifiedBy(paramCompliance.getModifiedBy());
					session.save((VendorDetailsInfo)vendorDetailsqry.get(0));
			    }
			}
			session.flush();
		}catch (Exception e) {
			 if(!(session.getTransaction().wasRolledBack())){
				 session.getTransaction().rollback();
			 }
			 log.error("Error Occurred...   ", e);
			 log.info("Exiting from saveVendorCompliance()  ::  vendorComplianceId =  "+vendorComplianceId);
		}				
		log.info("Exiting from saveVendorCompliance()  ::  vendorComplianceId =  "+vendorComplianceId);
		return vendorComplianceId;
	}

	
	/*
	 * This method will be used to get the Vendor Compliance record based on given vendorComplianceId
	 */
	@Override
	public List<VendorComplianceVo> getVendorComplianceById(Integer vendorComplianceId) {
		log.info("Entered in  getVendorComplianceById()  ::   vendorComplianceId  = "+vendorComplianceId);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<VendorComplianceVo> returnList = new ArrayList<VendorComplianceVo>();
		VendorComplianceVo vendor = new VendorComplianceVo();
		
		String hqlQuery = "SELECT venComp.Customer_Id, venComp.Company_Id, venComp.Country_Id, venComp.Vendor_Id, venComp.Compliance_Unique_Id, "
							+ " venComp.Transaction_Date, venComp.Status, venComp.Registered_State, venComp.Issue_Date, venComp.Expiry_Date, "
							+ " venComp.Renewal_Date, venComp.License_Policy_Number,  "
							+ " venComp.Transaction_Amount,venComp.Amount_Type, venComp.Number_Of_Workers_Covered, venComp.Remarks, VenComp.Vendor_Compliance_Unique_Id, "
							+ " venComp.Reason, venComp.Document_Path, venComp.Mandatory, vendor.Vendor_Status,venComp.location_Id "
							+ " FROM vendor_compliance venComp "
							+ " LEFT JOIN company_details_info AS company  ON company.Customer_Id = venComp.Customer_Id AND company.Company_Id = venComp.Company_Id "
							+ " LEFT JOIN customer_details_info AS customer  ON customer.Customer_Id = venComp.Customer_Id "
							+ " LEFT JOIN Vendor_Details AS Vendor ON vendor.Vendor_Id = venComp.Vendor_Id"
								+ " WHERE venComp.Vendor_Compliance_Id =  "+vendorComplianceId
								+" Group By Vendor_Compliance_Id";
			
		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){
				for (Object customer : queryList) {
					Object[] obj = (Object[]) customer;
					vendor = new VendorComplianceVo();
					
					vendor.setCustomerId((Integer)obj[0]);
					vendor.setCompanyId((Integer)obj[1]);
					vendor.setCountryId((Integer)obj[2]);
					vendor.setVendorId((Integer)obj[3]);
					vendor.setDefineComplianceTypeId((Integer)obj[4]);
					vendor.setTransactionDate((Date)obj[5]);
					vendor.setStatus(obj[6]+"");
					vendor.setRegisteredState((Integer)obj[7]);
					vendor.setIssueDate((Date)obj[8]);
					vendor.setExpiryDate((Date)obj[9]);
					vendor.setRenewalDate((Date)obj[10]);
					vendor.setLicensePolicyNumber(obj[11]+"");
					
					vendor.setTransactionAmount((double)obj[12]);
					if(obj[13] != null )
					vendor.setAmountType((Integer)obj[13]);
					vendor.setNumberOfWorkersCovered((Integer)obj[14]);
					vendor.setRemarks((String)obj[15]);
					vendor.setVendorComplianceUniqueId((Integer)obj[16]);
					vendor.setReason((String)obj[17]);
					vendor.setPath((String)obj[18]);
					//String path = obj[18]+"").split(Pattern.quote(System.getProperty("file.separator")))[2]
					System.out.println((String)(obj[18]) != null && obj[18].toString() != ""? (String)(obj[18]) : null);
					String[] count =(obj[18] != null && obj[18].toString() != "" && !obj[18].toString().equalsIgnoreCase("null")) ? (String[])(obj[18]+"").split(Pattern.quote(System.getProperty("file.separator"))) : null;
					//System.out.println(count.length);
					if(count != null){
						String[] extension = count[count.length-1].split("\\.(?=[^\\.]+$)");
						vendor.setDocumentPath(count.length > 0  && count[count.length-1].length() > 20 ? count[count.length-1].substring(0, extension[0].length()-20).concat(".").concat(extension[1]) : null);
						//vendor.setDocumentPath(count.length > 0 ? count[count.length-1] : null);
					}
					vendor.setMandatory((boolean)(obj[19]+"").equalsIgnoreCase("Y") ? true: false);
					vendor.setValidated((boolean)(obj[20]+"").equalsIgnoreCase("Validated") ? true: false);
					vendor.setLocationId((Integer)obj[21]);
					returnList.add(vendor);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getVendorComplianceById()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getVendorComplianceById()  ::   "+returnList);
		return returnList;
	}

	/*
	 * This method will be used to get Vendor Compliance records based on given CustomerId, CompanyId, ComplianceType and status
	 */
	@Override
	public List<VendorComplianceVo> getVendorComplianceListBySearch(VendorComplianceVo paramCompliance) {
		log.info("Entered in  getVendorComplianceListBySearch()  ::   VendorComplianceVo  = "+paramCompliance);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<VendorComplianceVo> returnList = new ArrayList<VendorComplianceVo>();
		VendorComplianceVo compliance = new VendorComplianceVo();
		
		String hqlQuery = "SELECT  company.Company_Name, customer.Customer_Name, vendor.Vendor_Name, "
							+ " compliance.Doccument_Name, DATE_FORMAT(venComp.Issue_Date,'%d/%m/%Y'), DATE_FORMAT(venComp.Expiry_Date,'%d/%m/%Y'), DATE_FORMAT(DATE_SUB(venComp.Expiry_Date, INTERVAL 1 DAY),'%d/%m/%Y') AS Due_Date, "
							//+ " compliance.Doccument_Name, venComp.Issue_Date, venComp.Expiry_Date, DATE_FORMAT(DATE_SUB(venComp.Expiry_Date, INTERVAL 1 DAY),'%d/%m-%Y') AS Due_Date, "
							+ " venComp.Vendor_Compliance_Id, venComp.Status, venComp.Number_Of_Workers_Covered  "
							+ " FROM vendor_compliance venComp "
							+ " LEFT JOIN customer_details_info AS customer  ON customer.Customer_Id = venComp.Customer_Id "
														+" AND CONCAT(DATE_FORMAT(customer.transaction_date, '%Y%m%d'), LPAD(customer.customer_Sequence_Id, 2, '0')) = "
														+"  ( "
														+"  SELECT MAX(CONCAT(DATE_FORMAT(vdi1.Transaction_Date, '%Y%m%d'), LPAD(vdi1.customer_Sequence_Id, 2, '0'))) "
														+"  FROM customer_details_info vdi1  "
														+"  WHERE  customer.`customer_id` = vdi1.`customer_id`     "
														+"   AND vdi1.Transaction_Date <= CURRENT_DATE() )  "
							+ " LEFT JOIN company_details_info AS company  ON company.Customer_Id = venComp.Customer_Id AND company.Company_Id = venComp.Company_Id "
														+" AND CONCAT(DATE_FORMAT(company.Transaction_Date, '%Y%m%d'), LPAD(company.company_Sequence_Id, 2, '0')) = "
														+"  ( "
														+"  SELECT MAX(CONCAT(DATE_FORMAT(vdi1.Transaction_Date, '%Y%m%d'), LPAD(vdi1.company_Sequence_Id, 2, '0'))) "
														+"  FROM company_details_info vdi1 "
														+"  WHERE  company.`company_id` = vdi1.`company_id`     "
														+" 	AND vdi1.Transaction_Date <= CURRENT_DATE())   "							
							+ " LEFT JOIN vendor_details_info vendor ON vendor.Vendor_Id = venComp.Vendor_Id "
														+" AND CONCAT(DATE_FORMAT(vendor.Transaction_Date, '%Y%m%d'), LPAD(vendor.Sequence_Id, 2, '0')) = "
														+"  ( "
														+"  SELECT MAX(CONCAT(DATE_FORMAT(vdi1.Transaction_Date, '%Y%m%d'), LPAD(vdi1.Sequence_Id, 2, '0'))) "
														+"  FROM vendor_details_info vdi1 "
														+"  WHERE  vendor.Vendor_Id = vdi1.Vendor_Id     "
														+" 	AND vdi1.Transaction_Date <= CURRENT_DATE())   "								
							//+ " LEFT JOIN vendor_details ven ON ven.Vendor_Id = venComp.Vendor_Id"
							+ " LEFT JOIN define_compliance_types compliance ON compliance.Customer_Id = venComp.Customer_Id AND compliance.Company_Id = venComp.Company_Id AND  compliance.Compliance_Unique_Id = venComp.Compliance_Unique_Id "
								+ " WHERE customer.Is_Active = 'Y'"
										+ " AND company.Is_Active = 'Y' "
										+ " AND CONCAT(DATE_FORMAT(`venComp`.`Transaction_Date`, '%Y%m%d'), LPAD(`venComp`.`Vendor_Compliance_Sequence_Id`, 2, '0')) = (SELECT  "
										+" MAX(CONCAT(DATE_FORMAT(`venComp1`.`Transaction_Date`, '%Y%m%d'),   LPAD(`venComp1`.`Vendor_Compliance_Sequence_Id`, 2, '0'))) "
										+"	FROM  vendor_compliance venComp1 "
											+" WHERE "
												+" venComp.vendor_Compliance_unique_id = venComp1.vendor_Compliance_unique_id "
												+" AND `venComp1`.`Transaction_Date` <= CURDATE() ) ";
		/*if(paramCompliance.getMandatory() == true){
			hqlQuery+= " AND CONCAT(DATE_FORMAT(`venComp`.`Transaction_Date`, '%Y%m%d'), LPAD(`venComp`.`Vendor_Compliance_Sequence_Id`, 2, '0')) = (SELECT  "
					+" MAX(CONCAT(DATE_FORMAT(`venComp1`.`Transaction_Date`, '%Y%m%d'),   LPAD(`venComp1`.`Vendor_Compliance_Sequence_Id`, 2, '0'))) "
					+"	FROM  vendor_compliance venComp1 "
						+" WHERE "
							+" `venComp`.`Customer_Id` = `venComp1`.`Customer_Id` "
							+" AND `venComp`.`Company_Id` = `venComp1`.`Company_Id` "
							+" AND `venComp`.`Vendor_Id` = `venComp1`.`Vendor_Id` "
							+" AND `venComp`.`Define_Compliance_Type_Id` = `venComp1`.`Define_Compliance_Type_Id` "
							+" AND  venComp.vendor_Compliance_unique_id = venComp1.vendor_Compliance_unique_id "
							+" AND `venComp1`.`Transaction_Date` <= CURDATE() ) ";
		}*/
		if(paramCompliance.getCustomerId() > 0){
			hqlQuery += "  AND  venComp.Customer_Id = "+paramCompliance.getCustomerId();
		}
		if(paramCompliance.getCompanyId() > 0){
			hqlQuery += "  AND  venComp.Company_Id = "+paramCompliance.getCompanyId();
		}
		
		if(paramCompliance.getFromDate() != null && paramCompliance.getToDate() == null){
			hqlQuery += " AND venComp.Issue_Date >= '"+DateHelper.convertDateToSQLString(paramCompliance.getFromDate())+"' ";
		}
		if(paramCompliance.getToDate() != null && paramCompliance.getFromDate() == null){
			//hqlQuery += " AND  '"+DateHelper.convertDateToSQLString(paramCompliance.getToDate())+"' between venComp.Issue_Date AND venComp.Expiry_Date";
			hqlQuery += " AND venComp.Issue_Date >= '"+DateHelper.convertDateToSQLString(paramCompliance.getToDate())+"' ";
		}
		
		if(paramCompliance.getFromDate() != null && paramCompliance.getToDate() != null){
			hqlQuery += " AND venComp.Issue_Date >= '"+DateHelper.convertDateToSQLString(paramCompliance.getFromDate())+"' ";
			hqlQuery += " AND venComp.Issue_Date <= '"+DateHelper.convertDateToSQLString(paramCompliance.getToDate())+"' ";
	

		}
		
		if(paramCompliance.getVendorId() > 0){
			hqlQuery += " AND venComp.Vendor_Id = "+paramCompliance.getVendorId();
		}
		
		if(paramCompliance.getDefineComplianceTypeId() != null && paramCompliance.getDefineComplianceTypeId() > 0){
			hqlQuery += " AND venComp.Compliance_Unique_Id = "+paramCompliance.getDefineComplianceTypeId();
		}
		
		if(paramCompliance.getLocationId() != null && paramCompliance.getLocationId() > 0){
			hqlQuery += " AND venComp.Location_Id = "+paramCompliance.getLocationId();
		}
		
		if(paramCompliance.getStatus() != null && !paramCompliance.getStatus().isEmpty()){
			hqlQuery += " AND venComp.Status = '"+paramCompliance.getStatus()+"'";
		}
		
		if(paramCompliance.getVendorStatus() != null && !paramCompliance.getVendorStatus().isEmpty()){
			hqlQuery += " AND vendor.Vendor_Status = '"+paramCompliance.getVendorStatus()+"'";
		}
		
		if(paramCompliance.getTransactionDate() != null ){
			hqlQuery += " AND venComp.Transaction_Date = '"+paramCompliance.getTransactionDate()+"' ";
		}
		hqlQuery += " GROUP BY venComp.Vendor_Compliance_Unique_Id Order By compliance.Doccument_Name asc";
		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){
				for (Object customer : queryList) {
					Object[] obj = (Object[]) customer;
					compliance = new VendorComplianceVo();
				
					compliance.setCompanyName((String)obj[0]);
					compliance.setCustomerName((String)obj[1]);
					compliance.setVendorName((String)obj[2]);
					compliance.setComplianceName((String)obj[3]);
					compliance.setIssueDt((String)obj[4]);
					compliance.setExpiryDt((String)obj[5]);
					compliance.setDueDate((String)obj[6]);
					compliance.setVendorComplianceId((Integer)obj[7]);
					compliance.setStatus((String)(obj[8]));
					compliance.setNumberOfWorkersCovered((Integer)obj[9]);
					returnList.add(compliance);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getVendorComplianceListBySearch()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getVendorComplianceListBySearch()  ::   "+returnList);
		return returnList;
	}

	
	/*
	 * This method will be used to get the transaction dates list for given customerId, company Id and vendorComplianceUniqueId
	 */
	@Override
	public List<SimpleObject> getTransactionListForVendorCompliance(Integer customerId, Integer companyId, Integer vendorComplianceUniqueId) {
		log.info("Entered in getTransactionListForVendorCompliance()  ::   customerId = "+customerId+" , companyId = "+companyId+" , vendorComplianceUniqueId = "+vendorComplianceUniqueId);
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List complianceList = session.createSQLQuery("SELECT Vendor_Compliance_Id AS id ,CONCAT(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),' - ',Vendor_Compliance_Sequence_Id) AS name FROM  Vendor_Compliance vendor  WHERE vendor.Customer_Id = "+customerId+" AND vendor.Company_Id = "+companyId+" AND vendor.Vendor_Compliance_Unique_Id = "+vendorComplianceUniqueId+" ORDER BY vendor.Transaction_Date, vendor.Vendor_Compliance_Sequence_Id asc").list();
			for (Object transDates  : complianceList) {
				Object[] transaction = (Object[]) transDates;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)transaction[0]);
				object.setName(transaction[1]+"");
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getTransactionListForVendorCompliance()  ::   "+transactionList);
		}
		
		log.info("Exiting from getTransactionListForVendorCompliance()  ::   "+transactionList);
		return transactionList;
	}
	
	/*
	 * This method will be used to get the count of number of workers
	 */
	@Override
	public List<SimpleObject> getNumberOfWorkersCovered(Integer customerId, Integer companyId, Integer vendorId) {
		log.info("Entered in getNumberOfWorkersCovered()  ::   customerId = "+customerId+" , companyId = "+companyId+" , vendorId = "+vendorId);
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List workersCount = session.createSQLQuery(" SELECT COALESCE(SUM(workers),0), Doccument_Name FROM (Select venComp.Number_Of_Workers_Covered as workers, comp.Doccument_Name FROM  Vendor_Compliance venComp  LEFT JOIN Define_Compliance_Types comp ON comp.Compliance_Unique_Id = venComp.Compliance_Unique_Id  "
														+ "	WHERE "
														+ "  CONCAT(DATE_FORMAT(`venComp`.`Transaction_Date`, '%Y%m%d'), LPAD(`venComp`.`Vendor_Compliance_Sequence_Id`, 2, '0')) = (SELECT  "
														+" MAX(CONCAT(DATE_FORMAT(`venComp1`.`Transaction_Date`, '%Y%m%d'),   LPAD(`venComp1`.`Vendor_Compliance_Sequence_Id`, 2, '0'))) "
														+"	FROM  vendor_compliance venComp1 "
															+" WHERE "
																+" venComp.vendor_Compliance_unique_id = venComp1.vendor_Compliance_unique_id "
																+" AND `venComp1`.`Transaction_Date` <= CURDATE() ) "
														+ " AND venComp.Customer_Id = "+customerId
														+ " AND venComp.Company_Id = "+companyId
														+ " AND venComp.Vendor_Id = "+vendorId
														+ " AND venComp.Expiry_Date >= Current_Date()"
														+ " AND ( venComp.Compliance_Unique_Id = 1 )"  
														+ " GROUP BY venComp.vendor_Compliance_Unique_Id"
														+ " ORDER BY venComp.vendor_Id, venComp.Compliance_Unique_Id)a").list();
			for (Object sum  : workersCount) {
				Object[] obj = (Object[]) sum;
				SimpleObject object = new SimpleObject();
				object.setId(((BigDecimal)obj[0]).intValue());
				object.setName((String)obj[1]);
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getNumberOfWorkersCovered()  ::   "+transactionList);
		}
		
		log.info("Exiting from getNumberOfWorkersCovered()  ::   "+transactionList);
		return transactionList;
	}
	
	
	
	
	@Override
	public List<SimpleObject> getLocationListByVendor(Integer customerId, Integer companyId, Integer vendorId) {
		log.info("Entered in getLocationListByVendor()  ::   customerId = "+customerId+" , companyId = "+companyId+" , vendorComplianceUniqueId = "+vendorId);
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List complianceList = session.createSQLQuery("SELECT ldi.location_id,ldi.location_name  FROM location_details_info ldi  "+
														" INNER JOIN vendor_details_info vdi ON (ldi.location_id = vdi.location_id)  "+
														" WHERE   "+
														" (CONCAT(DATE_FORMAT(ldi.Transaction_Date,'%Y%m%d'),LPAD(ldi.location_Sequence_Id,2,'0')) = (SELECT  "+
														" MAX(CONCAT(DATE_FORMAT(cdi1.Transaction_Date,'%Y%m%d'),LPAD(cdi1.location_Sequence_Id,2,'0')))  "+
														" FROM location_details_info cdi1  "+
														" WHERE ((ldi.location_id = cdi1.location_id)  "+
														" AND (ldi.Transaction_Date <= CURDATE()))))  "+
														" AND   "+
														" (CONCAT(DATE_FORMAT(vdi.Transaction_Date,'%Y%m%d'),LPAD(vdi.Sequence_Id,2,'0')) = (SELECT  "+
														" MAX(CONCAT(DATE_FORMAT(cdi1.Transaction_Date,'%Y%m%d'),LPAD(cdi1.Sequence_Id,2,'0')))  "+
														" FROM vendor_details_info cdi1  "+
														" WHERE ((vdi.vendor_id = cdi1.vendor_id)  "+
														" AND (vdi.Transaction_Date <= CURDATE())))) and vdi.customer_Id='"+customerId+"' and vdi.company_id = '"+companyId+"' and vdi.vendor_id = '"+vendorId+"'").list();
			for (Object transDates  : complianceList) {
				Object[] transaction = (Object[]) transDates;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)transaction[0]);
				object.setName(transaction[1]+"");
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getLocationListByVendor()  ::   "+transactionList);
		}
		
		log.info("Exiting from getLocationListByVendor()  ::   "+transactionList);
		return transactionList;
	}

}
