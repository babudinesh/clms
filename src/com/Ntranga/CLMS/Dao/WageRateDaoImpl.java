package com.Ntranga.CLMS.Dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WageRateVo;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.JobCodeDetails;
import com.Ntranga.core.CLMS.entities.MCountry;
import com.Ntranga.core.CLMS.entities.VendorDetails;
import com.Ntranga.core.CLMS.entities.WageRateDetails;
import com.Ntranga.core.CLMS.entities.WageRateDetailsInfo;
import com.Ntranga.core.CLMS.entities.WorkrorderDetail;
import common.Logger;

@Transactional
@Repository("wageRateDao")
@SuppressWarnings("rawtypes")
public class WageRateDaoImpl implements WageRateDao {

private static Logger log = Logger.getLogger(WorkAreaDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/*
	 * This method will be used to save/update wage_rate_details, wage_rate_details_info and wage_rate_currency
	 */
	@Override
	public Integer saveWageRate(WageRateVo paramWage) {
		log.info(paramWage+"Entered in saveWageRate()  ::   "+paramWage.toString());

		Session session = sessionFactory.getCurrentSession();
		Integer wageRateDetailsId = 0;
		Integer wageRateId = 0;
		BigInteger wageRateSequenceId = new BigInteger("0");	
		BigInteger listSize = new BigInteger("0");
		
		WageRateDetails wageDetails = new WageRateDetails();
		WageRateDetailsInfo wageDetailsInfo = new WageRateDetailsInfo();

		try{
			
			if(paramWage != null && paramWage.getWageRateId() != null && paramWage.getWageRateId() > 0 ){
				listSize = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(COUNT(`Wage_Rate_Code`),0) FROM `wage_rate_details` parent INNER JOIN `wage_rate_details_info` child ON (parent.`Wage_Rate_Id` = child.`Wage_Rate_Id`) WHERE child.`Customer_Id`="+paramWage.getCustomerId()+" AND child.`Company_Id`="+paramWage.getCompanyId()+" AND child.`Wage_Rate_Id` !="+paramWage.getWageRateId()+" AND parent.`Wage_Rate_Code`='"+ paramWage.getWageRateCode()+"'").list().get(0);
			}else {
				listSize = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(COUNT(`Wage_Rate_Code`),0) FROM `wage_rate_details` parent INNER JOIN `wage_rate_details_info` child ON (parent.`Wage_Rate_Id` = child.`Wage_Rate_Id`) WHERE child.`Customer_Id`="+paramWage.getCustomerId()+" AND child.`Company_Id`="+paramWage.getCompanyId()+" AND parent.`Wage_Rate_Code`='"+paramWage.getWageRateCode()+"'").list().get(0);
			}
			if(listSize.intValue() > 0){				
				wageRateDetailsId = 0;
				return wageRateDetailsId;
			}
			
			// Save or Update wage_rate_details table
			if(paramWage != null && paramWage.getWageRateId() != null && paramWage.getWageRateId() > 0){
				wageDetails = (WageRateDetails) session.load(WageRateDetails.class, paramWage.getWageRateId());
				wageDetails.setCustomerDetails(new CustomerDetails(paramWage.getCustomerId()));
				wageDetails.setCompanyDetails(new CompanyDetails(paramWage.getCompanyId()));
				wageDetails.setWageRateCode(paramWage.getWageRateCode().toUpperCase());
				wageDetails.setModifiedBy(paramWage.getModifiedBy());
				wageDetails.setModifiedDate(new Date());
				session.update(wageDetails);
				wageRateId  = paramWage.getWageRateId();
			}else{
				wageDetails = new WageRateDetails();
				wageDetails.setCustomerDetails(new CustomerDetails(paramWage.getCustomerId()));
				wageDetails.setCompanyDetails(new CompanyDetails(paramWage.getCompanyId()));
				wageDetails.setWageRateCode(paramWage.getWageRateCode().toUpperCase());
				wageDetails.setCreatedBy(paramWage.getCreatedBy());
				wageDetails.setModifiedBy(paramWage.getModifiedBy());
				wageDetails.setCreatedDate(new Date());
				wageDetails.setModifiedDate(new Date());
				wageRateId = (Integer) session.save(wageDetails);
			}
			
			// Save or Update wage_rate_details_info table
			if(paramWage != null && paramWage.getWageRateDetailsId() != null &&  paramWage.getWageRateDetailsId() > 0){
				wageDetailsInfo = (WageRateDetailsInfo) sessionFactory.getCurrentSession().load(WageRateDetailsInfo.class, paramWage.getWageRateDetailsId());
				wageDetailsInfo.setCustomerDetails(new CustomerDetails(paramWage.getCustomerId()));
				wageDetailsInfo.setCompanyDetails(new CompanyDetails(paramWage.getCompanyId()));
				wageDetailsInfo.setVendorDetails(new VendorDetails(paramWage.getVendorId()));
				wageDetailsInfo.setWorkOrderId(paramWage.getWorkOrderId());
				wageDetailsInfo.setWageScaleCode(paramWage.getWageScaleId());
				wageDetailsInfo.setWageRateDetails(new WageRateDetails(wageRateId));
				wageDetailsInfo.setWageRateName(paramWage.getWageRateName());
				//wageDetailsInfo.setWageRateDescription(paramWage.getWageRateDescription());
				wageDetailsInfo.setJobCodeId(paramWage.getJobCodeId());
				wageDetailsInfo.setJobCategory(paramWage.getJobCategory());
				wageDetailsInfo.setJobType(paramWage.getJobType());
				wageDetailsInfo.setMCountry(new MCountry(paramWage.getCountryId()));
				wageDetailsInfo.setStatus(paramWage.getStatus());
				wageDetailsInfo.setTransactionDate(paramWage.getTransactionDate());
				wageDetailsInfo.setBaseRate(paramWage.getBaseRate());
				wageDetailsInfo.setBaseRateCurrencyId(paramWage.getBaseRateCurrencyId());
				wageDetailsInfo.setBaseRateFrequency(paramWage.getBaseRateFrequency());
				wageDetailsInfo.setOvertimeRate(paramWage.getOvertimeRate());
				wageDetailsInfo.setOvertimeRateCurrencyId(paramWage.getOvertimeCurrencyId());
				wageDetailsInfo.setOvertimeFrequency(paramWage.getOvertimeFrequency());
				wageDetailsInfo.setWorkPieceUnit(paramWage.getWorkPieceUnit());
				wageDetailsInfo.setWorkPieceRate(paramWage.getWorkPieceRate());
				wageDetailsInfo.setWorkPieceCurrency(paramWage.getWorkPieceCurrency());
				wageDetailsInfo.setWorkRateMargin(paramWage.getWorkRateMargin());
				wageDetailsInfo.setBaseRateMargin(paramWage.getBaseRateMargin());
				wageDetailsInfo.setOvertimeRateMargin(paramWage.getOvertimeRateMargin());
				wageDetailsInfo.setModifiedBy(paramWage.getModifiedBy());
				wageDetailsInfo.setModifiedDate(new Date());
				session.update(wageDetailsInfo);
				wageRateDetailsId = wageDetailsInfo.getWageRateDetailsId();
			}else{	
				wageDetailsInfo = new WageRateDetailsInfo();
				wageDetailsInfo.setCustomerDetails(new CustomerDetails(paramWage.getCustomerId()));
				wageDetailsInfo.setCompanyDetails(new CompanyDetails(paramWage.getCompanyId()));
				wageDetailsInfo.setWageRateDetails(new WageRateDetails(wageRateId));
				wageDetailsInfo.setVendorDetails(new VendorDetails(paramWage.getVendorId()));
				wageDetailsInfo.setWorkOrderId(paramWage.getWorkOrderId());
				wageDetailsInfo.setWageScaleCode(paramWage.getWageScaleId());
				wageDetailsInfo.setWageRateName(paramWage.getWageRateName());
				//wageDetailsInfo.setWageRateDescription(paramWage.getWageRateDescription());
				wageDetailsInfo.setJobCodeId(paramWage.getJobCodeId());
				wageDetailsInfo.setJobCategory(paramWage.getJobCategory());
				wageDetailsInfo.setJobType(paramWage.getJobType());
				wageDetailsInfo.setMCountry(new MCountry(paramWage.getCountryId()));
				wageDetailsInfo.setStatus(paramWage.getStatus());
				wageDetailsInfo.setTransactionDate(paramWage.getTransactionDate());
				wageDetailsInfo.setBaseRate(paramWage.getBaseRate());
				wageDetailsInfo.setBaseRateCurrencyId(paramWage.getBaseRateCurrencyId());
				wageDetailsInfo.setBaseRateFrequency(paramWage.getBaseRateFrequency());
				wageDetailsInfo.setOvertimeRate(paramWage.getOvertimeRate());				
				wageDetailsInfo.setOvertimeRateCurrencyId(paramWage.getOvertimeCurrencyId());
				wageDetailsInfo.setOvertimeFrequency(paramWage.getOvertimeFrequency());
				wageDetailsInfo.setWorkPieceUnit(paramWage.getWorkPieceUnit());
				wageDetailsInfo.setWorkPieceRate(paramWage.getWorkPieceRate());
				wageDetailsInfo.setWorkPieceCurrency(paramWage.getWorkPieceCurrency());
				wageDetailsInfo.setWorkRateMargin(paramWage.getWorkRateMargin());
				wageDetailsInfo.setBaseRateMargin(paramWage.getBaseRateMargin());
				wageDetailsInfo.setOvertimeRateMargin(paramWage.getOvertimeRateMargin());
				wageDetailsInfo.setCreatedBy(paramWage.getCreatedBy());
				wageDetailsInfo.setModifiedBy(paramWage.getModifiedBy());
				wageDetailsInfo.setCreatedDate(new Date());
				wageDetailsInfo.setModifiedDate(new Date());
				
				if(wageRateId != null && wageRateId > 0){
					wageRateSequenceId  = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Wage_rate_Sequence_Id),0) FROM `wage_rate_details_info` info"
							+ "  WHERE  info.Wage_Rate_Id = "+paramWage.getWageRateId() +" AND info.`Transaction_Date` = '"+DateHelper.convertDateToSQLString(paramWage.getTransactionDate())+"' and info.Customer_Id='"+paramWage.getCustomerId()+"' And info.Company_Id = '"+paramWage.getCompanyId()+"'").list().get(0);
					log.info("Wage Rate sequence Id : "+wageRateSequenceId);
				}	
				wageDetailsInfo.setWageRateSequenceId(wageRateSequenceId.intValue()+1);			
				wageRateDetailsId = (Integer) session.save(wageDetailsInfo);
			}
			session.flush();
		}catch (Exception e) {
			 if(!(session.getTransaction().wasRolledBack())){
				 session.getTransaction().rollback();
			 }
			 log.error("Error Occurred...   ", e);
			 log.info("Exiting from saveWageRate()  ::  wageRateDetailsId =  "+wageRateDetailsId);
		}				
		log.info("Exiting from saveWageRate()  ::  wageRateDetailsId =  "+wageRateDetailsId);
		return wageRateDetailsId;
	}
	
	/*
	 * This method will be used to get wage rate for given  wageRateDetailsId
	 */

	@Override
	public List<WageRateVo> getWageRateById(Integer wageRateDetailsId) {
		log.info("Entered in  getWageRatesListBySearch()  ::   wageRateDetailsId  = "+wageRateDetailsId);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<WageRateVo> returnList = new ArrayList<WageRateVo>();
		WageRateVo wage = new WageRateVo();
		
		String hqlQuery = "SELECT info.Customer_Id, info.Company_Id,info.Wage_Rate_Id, info.Wage_Rate_Name, wage.Wage_Rate_Code, "
							+ "  info.Job_Type, info.Job_Category, info.Job_Code_Id, info.Transaction_Date, "
							+ " info.Status,  info.Country_Id, "
							+ " CAST(info.Base_Rate AS DECIMAL(9,2)) AS Base_Rate, info.Base_Rate_Currency_Id, info.Base_Rate_Frequency, CAST(info.Overtime_Rate AS DECIMAL(9,2)) AS Overtime_Rate, info.Overtime_Currency_Id, info.Overtime_Frequency, "
							+ " cur.Currency AS Base_Rate_Currency , cur1.Currency As Overtime_Rate_Currency,info.vendor_Id, "
							+ " info.`Wage_Scale_Code`,info.`WorkOrder_id`,info.Work_Piece_Unit, CAST(info.Work_Piece_Rate AS DECIMAL(9,2)) AS Work_Piece_Rate, "
							+ " info.Work_Piece_Currency ,cur2.Currency AS workPieceCurrency, info.Work_Rate_Margin,info.Overtime_Rate_Margin,info.Base_Rate_Margin"
							+ " FROM wage_rate_details_info AS info "
							+ " LEFT JOIN wage_rate_details AS wage ON wage.Wage_Rate_Id = info.Wage_Rate_Id " 	
							+ " LEFT JOIN m_currency cur ON info.Base_Rate_Currency_Id = cur.Currency_Id "
							+ " LEFT JOIN m_currency cur1 ON info.Overtime_Currency_Id = cur1.Currency_Id LEFT JOIN m_currency cur2 ON info.Work_Piece_Currency = cur2.Currency_Id "							
							+ " WHERE info.Wage_Rate_Details_Id =  "+wageRateDetailsId;
			
		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){
				for (Object customer : queryList) {
					Object[] obj = (Object[]) customer;
					wage = new WageRateVo();					
					wage.setCustomerId((Integer)obj[0]);
					wage.setCompanyId((Integer)obj[1]);
					wage.setWageRateId((Integer)obj[2]);
					wage.setWageRateName(obj[3]+"");
					wage.setWageRateCode(obj[4]+"");					
					wage.setJobType(obj[5]+"");
					wage.setJobCategory(obj[6]+"");
					wage.setJobCodeId((Integer)obj[7]);
					wage.setTransactionDate((Date)obj[8]);
					wage.setStatus(obj[9]+"");
					wage.setCountryId((Integer)obj[10]);
					if(obj[11] != null){
						wage.setBaseRate(((BigDecimal)obj[11]).doubleValue());
					}
					if(obj[12] != null){
						wage.setBaseRateCurrencyId((Integer)obj[12]);
					}
					if(obj[13] != null){
						wage.setBaseRateFrequency(obj[13]+"");
					}
					if(obj[14] != null){
						wage.setOvertimeRate(((BigDecimal)obj[14]).doubleValue());
					}
					if(obj[15] != null){
						wage.setOvertimeCurrencyId((Integer)obj[15]);
					}
					if(obj[16] != null){
						wage.setOvertimeFrequency(obj[16]+"");
					}
					if(obj[17] != null){
						wage.setOvertimeCurrency(obj[17]+"");
					}
					if(obj[18] != null){
						wage.setBaseRateCurrency(obj[18]+"");
					}
					wage.setVendorId((Integer)obj[19]);
					wage.setWageScaleId(obj[20]+"");
					wage.setWorkOrderId((Integer)obj[21]);
					if(obj[22] != null){
						wage.setWorkPieceUnit(obj[22]+"");
					}
					if(obj[23] != null){
						wage.setWorkPieceRate(((BigDecimal)obj[23]).doubleValue());
					}
					if(obj[24] != null){
						wage.setWorkPieceCurrency(obj[24]+"");
					}
					System.out.println(obj[25]+" :: currency Name work ");
					if(obj[25] != null){
						wage.setWorkPieceCurrencyName(obj[25]+"");
					}
					if(obj[26] != null){
						wage.setWorkRateMargin((Integer)obj[26]);
					}
					if(obj[27] != null){
						wage.setOvertimeRateMargin((Integer)obj[27]);
					}
					if(obj[28] != null){
						wage.setBaseRateMargin((Integer)obj[28]);
					}
					returnList.add(wage);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getWageRatesListBySearch()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getWageRatesListBySearch()  ::   "+returnList);
		return returnList;
	}

	/*
	 * This method will be used to get wage rates List for given customerId, companyId , wage rate code and wage rate name
	 */
	@Override
	public List<WageRateVo> getWageRatesListBySearch(WageRateVo paramWage) {
		log.info("Entered in  getWageRatesListBySearch()  ::   workAreaVo  = "+paramWage);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<WageRateVo> returnList = new ArrayList<WageRateVo>();
		WageRateVo wage = new WageRateVo();
		
		String hqlQuery = "SELECT `Wage_Rate_Details_Id`,`Customer_Name`,`company_name`,`vendor_name`,`Work_Order_Name`,`trDate`,`Status`,`Wage_Scale_Name`,`Wage_Rate_Name`,`Customer_Id`,`Company_Id`,`Vendor_Id`,`WorkOrder_id`,`Wage_Rate_Code`, "
									+ " Customer_Status, Company_Status, Vendor_Status, WageScale_Status, WorkOrder_Status FROM WageRate_Search_View info WHERE Wage_Rate_Details_Id > 0 "
									+ " AND  info.Customer_Status = 'Y' "
									+ " AND  info.Company_Status = 'Y' "
									+ " AND  info.Vendor_Status = 'Validated' "
									+ " AND  info.WageScale_Status = 'Y' " ;
									//+ " AND  info.WorkOrder_Status = 'Y' ";
					
		if(paramWage.getCustomerId() > 0){
			hqlQuery += "  AND  info.Customer_Id = "+paramWage.getCustomerId();
		}
		if(paramWage.getCompanyId() > 0){
			hqlQuery += "  AND  info.Company_Id = "+paramWage.getCompanyId();
		}
		
		if(paramWage.getVendorId() > 0){
			hqlQuery += "  AND  info.vendor_Id = "+paramWage.getVendorId();
		}
		if(paramWage.getWorkOrderId() > 0){
			hqlQuery += "  AND  info.WorkOrder_id = "+paramWage.getWorkOrderId();
		}
		if(paramWage.getWageScaleId() != null && !paramWage.getWageScaleId().isEmpty()){
			hqlQuery += "  AND  info.Wage_Scale_Code = '"+paramWage.getWageScaleId()+"'";
		}
		
		if(paramWage.getWageRateName() != null && !paramWage.getWageRateName().isEmpty()){
			hqlQuery += " AND info.Wage_Rate_Name LIKE '%"+paramWage.getWageRateName()+"%' ";
		}
		
		if(paramWage.getStatus() != null && !paramWage.getStatus().isEmpty()){
			hqlQuery += " AND info.Status = '"+paramWage.getStatus().trim()+"' ";
		}
		
		hqlQuery += " Order By info.Wage_Rate_Name asc";
		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){
				for (Object customer : queryList) {
					Object[] obj = (Object[]) customer;
					wage = new WageRateVo();
					wage.setWageRateDetailsId((Integer)obj[0]);
					wage.setCustomerName((String)obj[1]);
					wage.setCompanyName((String)obj[2]);
					wage.setVendorName((String)obj[3]);
					wage.setWorkOrderName((String)obj[4]);
					wage.setTransDate((String)obj[5]);
					wage.setStatus((obj[6]+"").equalsIgnoreCase("Y")? "Active" : "InActive");
					wage.setWageScaleName((String)obj[7]);
					wage.setWageRateName((String)obj[8]);					
					wage.setCustomerId((Integer)obj[9]);
					wage.setCompanyId((Integer)obj[10]);
					wage.setVendorId((Integer)obj[11]);
					wage.setWorkOrderId((Integer)obj[12]);					
					wage.setWageRateCode((String)obj[13]);					
					returnList.add(wage);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getWageRatesListBySearch()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getWageRatesListBySearch()  ::   "+returnList);
		return returnList;
	}

	/*
	 * This method will be used to get transaction Dates List for given customerId, companyId and wageRateId
	 */
	@Override
	public List<SimpleObject> getTransactionListForWageRate(Integer customerId,Integer companyId, Integer wageRateId) {
		log.info("Entered in getTransactionListForWageRate()  ::   customerId = "+customerId+" , companyId = "+companyId+" , wageRateDetailsId = "+wageRateId);
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List workAreaList = session.createSQLQuery("SELECT Wage_Rate_Details_Id AS id ,CONCAT(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),' - ',Wage_Rate_Sequence_Id) AS name FROM  wage_rate_details_info info  WHERE info.Customer_Id = "+customerId+" AND info.Company_Id = "+companyId+" AND info.Wage_Rate_Id = "+wageRateId+" ORDER BY info.Transaction_Date, info.Wage_Rate_Details_Id asc").list();
			for (Object transDates  : workAreaList) {
				Object[] transaction = (Object[]) transDates;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)transaction[0]);
				object.setName(transaction[1]+"");
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getTransactionListForWageRate()  ::   "+transactionList);
		}
		
		log.info("Exiting from getTransactionListForWageRate()  ::   "+transactionList);
		return transactionList;	
	}

	/*
	 * This method will be used to get Wage rate Currency List for given customer Id, company Id and wageRateId
	 */
	/*@Override
	public List<WageRateCurrencyVo> getWageRateCurrencyList(Integer customerId, Integer companyId, Integer wageRateId) {
		log.info("Entered in getWageRateCurrencyList()  ::   customerId = "+customerId+" , companyId = "+companyId+" , wageRateDetailsId = "+wageRateId);
		
		Session session = sessionFactory.getCurrentSession();
		//List queryList = null;
		List<WageRateCurrencyVo> returnList = new ArrayList<WageRateCurrencyVo>();
		WageRateCurrencyVo wage = new WageRateCurrencyVo();
		
		
		try {				
			List queryList = session.createSQLQuery("SELECT Base_Rate, Base_Rate_Currency_Id, Base_Rate_Frequency, Overtime_Rate, Overtime_Currency_Id, Overtime_Frequency, "
												+ " cur.Currency AS Base_Rate_Currency , cur1.Currency As Overtime_Rate_Currency"
												+ " FROM wage_rate_currency wage"
												+ " LEFT JOIN m_currency cur ON wage.Base_Rate_Currency_Id = cur.Currency_Id "
												+ " LEFT JOIN m_currency cur1 ON wage.Overtime_Currency_Id = cur1.Currency_Id"
												+ " WHERE Wage_Rate_Id = "+wageRateId).list(); 
			//List<WageRateCurrency> queryList = session.createQuery(" FROM WageRateCurrency WHERE Customer_Id = "+ customerId+" AND Company_Id = "+companyId+" AND  Wage_Rate_Id = "+wageRateId).list();
			
			if(queryList.size() > 0){
				for (Object currency : queryList) {
					Object[] obj = (Object[]) currency;
					wage = new WageRateCurrencyVo();
					
					wage.setBaseRate((Integer)obj[0]);
					wage.setBaseRateCurrencyId((Integer)obj[1]);
					wage.setBaseRateFrequency(obj[2]+"");
					wage.setOvertimeRate((Integer)obj[3]);
					wage.setOvertimeCurrencyId((Integer)obj[4]);
					wage.setOvertimeFrequency(obj[5]+"");
					wage.setOvertimeCurrency(obj[6]+"");
					wage.setBaseRateCurrency(obj[7]+"");
					
					returnList.add(wage);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getWageRateCurrencyList()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getWageRateCurrencyList()  ::   "+returnList);
		return returnList;
	}*/


}
