package com.Ntranga.CLMS.Dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.ShiftPatternAdditionalRulesVo;
import com.Ntranga.CLMS.vo.ShiftPatternAssignShiftsVo;
import com.Ntranga.CLMS.vo.ShiftPatternVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.LocationDetails;
import com.Ntranga.core.CLMS.entities.MCountry;
import com.Ntranga.core.CLMS.entities.PlantDetails;
import com.Ntranga.core.CLMS.entities.ShiftPatternAdditionalRules;
import com.Ntranga.core.CLMS.entities.ShiftPatternAssignShifts;
import com.Ntranga.core.CLMS.entities.ShiftPatternDetails;
import com.Ntranga.core.CLMS.entities.ShiftPatternDetailsInfo;
import com.Ntranga.core.CLMS.entities.ShiftsDefine;

@SuppressWarnings("rawtypes")
@Transactional
@Repository("shiftPatternDao")
public class ShiftPatternDaoImpl implements ShiftPatternDao {

	private static Logger log = Logger.getLogger(ShiftPatternDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/*
	 * This method will be used to save/ update shift_pattern_details, shift_pattern_details_info, shift_pattern_assign_shifts
	 * 																			and shift_pattern_additional_rules 
	 */
	@Override
	public Integer saveShiftPattern(ShiftPatternVo paramShift) {
		log.info(paramShift.getTransactionDate()+"Entered in saveShiftPattern()  ::   "+paramShift);

		Session session = sessionFactory.getCurrentSession();
		Integer shiftPatternDetailsId = 0;
		Integer shiftPatternId = 0;
		BigInteger shiftPatternSequenceId = new BigInteger("0");	
		
		
		ShiftPatternDetails patternDetails = new ShiftPatternDetails();		
		
		try{									
			if(paramShift != null && paramShift.getShiftPatternId() > 0){
				
				patternDetails = (ShiftPatternDetails) session.load(ShiftPatternDetails.class, paramShift.getShiftPatternId());
				patternDetails.setCustomerDetails(new CustomerDetails(paramShift.getCustomerId()));
				patternDetails.setCompanyDetails(new CompanyDetails(paramShift.getCompanyId()));
				patternDetails.setCountryDetails(new MCountry(paramShift.getCountryId()));
				patternDetails.setShiftPatternCode(paramShift.getShiftPatternCode());
				patternDetails.setModifiedBy(paramShift.getModifiedBy());
				patternDetails.setModifiedDate(new Date());
				session.update(patternDetails);
				shiftPatternId  = paramShift.getShiftPatternId();
			}else{
				
				patternDetails = new ShiftPatternDetails();
				patternDetails.setCustomerDetails(new CustomerDetails(paramShift.getCustomerId()));
				patternDetails.setCompanyDetails(new CompanyDetails(paramShift.getCompanyId()));
				patternDetails.setCountryDetails(new MCountry(paramShift.getCountryId()));
				patternDetails.setShiftPatternCode(paramShift.getShiftPatternCode());
				patternDetails.setCreatedBy(paramShift.getCreatedBy());
				patternDetails.setModifiedBy(paramShift.getModifiedBy());
				patternDetails.setCreatedDate(new Date());
				patternDetails.setModifiedDate(new Date());
				shiftPatternId = (Integer) session.save(patternDetails);
				log.info("Shift Pattern "+shiftPatternId);
			}
						
			//shift_pattern_details_info table
			if(paramShift != null &&  paramShift.getShiftPatternDetailsId() > 0){
				ShiftPatternDetailsInfo patternDetailsInfo = (ShiftPatternDetailsInfo) sessionFactory.getCurrentSession().load(ShiftPatternDetailsInfo.class, paramShift.getShiftPatternDetailsId());
				patternDetailsInfo.setShiftPatternDetails(new ShiftPatternDetails(shiftPatternId));
				patternDetailsInfo.setCustomerDetails(new CustomerDetails(paramShift.getCustomerId()));
				patternDetailsInfo.setCompanyDetails(new CompanyDetails(paramShift.getCompanyId()));
				if(paramShift.getLocationDetailsId() != null && paramShift.getLocationDetailsId() > 0)
					patternDetailsInfo.setLocationDetails(new LocationDetails(paramShift.getLocationDetailsId()));
				else
					patternDetailsInfo.setLocationDetails(null);
				if(paramShift.getPlantDetailsId() != null && paramShift.getPlantDetailsId() > 0)
					patternDetailsInfo.setPlantDetails(new PlantDetails(paramShift.getPlantDetailsId()));
				else
					patternDetailsInfo.setPlantDetails(null);
				patternDetailsInfo.setShiftPatternName(paramShift.getShiftPatternName());
				patternDetailsInfo.setTotalPatternHours(paramShift.getTotalPatternHours());
				patternDetailsInfo.setDefinePatternBy(paramShift.getDefinePatternBy());
				patternDetailsInfo.setHasAdditionalRules(paramShift.isHasAdditionalRules() == true ? "Y" : "N");
				patternDetailsInfo.setStatus(paramShift.getStatus());
				patternDetailsInfo.setTransactionDate(paramShift.getTransactionDate());
				patternDetailsInfo.setModifiedBy(paramShift.getModifiedBy());
				patternDetailsInfo.setModifiedDate(new Date());
				log.info("Shift patternDetailsInfo.getTransactionDate() "+patternDetailsInfo.getTransactionDate());
				session.update(patternDetailsInfo);
				shiftPatternDetailsId = patternDetailsInfo.getShiftPatternDetailsId();
			}else{	
				ShiftPatternDetailsInfo patternDetailsInfo = new ShiftPatternDetailsInfo();
				patternDetailsInfo.setShiftPatternDetails(new ShiftPatternDetails(shiftPatternId));
				patternDetailsInfo.setCustomerDetails(new CustomerDetails(paramShift.getCustomerId()));
				patternDetailsInfo.setCompanyDetails(new CompanyDetails(paramShift.getCompanyId()));
				if(paramShift.getLocationDetailsId() != null && paramShift.getLocationDetailsId() > 0)
					patternDetailsInfo.setLocationDetails(new LocationDetails(paramShift.getLocationDetailsId()));				
				if(paramShift.getPlantDetailsId() != null && paramShift.getPlantDetailsId() > 0)
					patternDetailsInfo.setPlantDetails(new PlantDetails(paramShift.getPlantDetailsId()));				
				patternDetailsInfo.setShiftPatternName(paramShift.getShiftPatternName());
				patternDetailsInfo.setTotalPatternHours(paramShift.getTotalPatternHours());
				patternDetailsInfo.setDefinePatternBy(paramShift.getDefinePatternBy());
				patternDetailsInfo.setHasAdditionalRules(paramShift.isHasAdditionalRules() == true ? "Y" : "N");
				patternDetailsInfo.setStatus(paramShift.getStatus());
				patternDetailsInfo.setTransactionDate(paramShift.getTransactionDate());
				patternDetailsInfo.setCreatedBy(paramShift.getModifiedBy());
				patternDetailsInfo.setModifiedBy(paramShift.getModifiedBy());
				patternDetailsInfo.setCreatedDate(new Date());
				patternDetailsInfo.setModifiedDate(new Date());
				log.info("Shift patternDetailsInfo.getTransactionDate() "+patternDetailsInfo.getTransactionDate());
				if(shiftPatternId != null && shiftPatternId > 0){
					shiftPatternSequenceId  = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Shift_Pattern_Sequence_Id),0) FROM Shift_Pattern_details_info info "
													+ " WHERE  info.Shift_Pattern_Id = "+paramShift.getShiftPatternId() 
													+ " AND info.`Transaction_Date` = '"+DateHelper.convertDateToSQLString(paramShift.getTransactionDate())
													+"' AND info.Customer_Id='"+paramShift.getCustomerId()
													+"' AND info.Company_Id = '"+paramShift.getCompanyId()+"'").list().get(0);
					log.info("Shift PAttern sequence Id : "+shiftPatternSequenceId);
				}	
				patternDetailsInfo.setShiftPatternSequenceId(shiftPatternSequenceId.intValue()+1);			
				shiftPatternDetailsId = (Integer) session.save(patternDetailsInfo);
			}
						
			
			if(paramShift.getShiftPatternDetailsId()+"" != null && paramShift.getShiftPatternDetailsId()+"" != "" && paramShift.getShiftPatternDetailsId() >0 ){						
				Query q = session.createSQLQuery("delete from shift_pattern_assign_shifts where shift_pattern_Details_Id = '"+paramShift.getShiftPatternDetailsId()+"'");
				q.executeUpdate();
			}
			
			if(shiftPatternId > 0 && paramShift.getPatternAssignList() != null && paramShift.getPatternAssignList().size() > 0){
			
				if(paramShift.getPatternAssignList() != null ){
					for(ShiftPatternAssignShiftsVo object : paramShift.getPatternAssignList()){
						
						ShiftPatternAssignShifts assignShifts = new ShiftPatternAssignShifts();
						assignShifts.setShiftPatternDetailsInfo(new ShiftPatternDetailsInfo(shiftPatternDetailsId));
						assignShifts.setCustomerDetails(new CustomerDetails(paramShift.getCustomerId()));
						assignShifts.setCompanyDetails(new CompanyDetails(paramShift.getCompanyId()));
						assignShifts.setDaySequence(object.getDaySequence());
						assignShifts.setWeekName(object.getWeekName());
						assignShifts.setShiftCode(object.getShiftCode());
						assignShifts.setShiftDefine(new ShiftsDefine(object.getShiftDefineId()));
						assignShifts.setCreatedBy(paramShift.getModifiedBy());
						assignShifts.setModifiedBy(paramShift.getModifiedBy());
						assignShifts.setCreatedDate(new Date());
						assignShifts.setModifiedDate(new Date());						
						session.save(assignShifts);	
						
						/*if(object != null && object.getShiftPatternAssignId() > 0){
							assignShifts = (ShiftPatternAssignShifts) sessionFactory.getCurrentSession().load(ShiftPatternAssignShifts.class, object.getShiftPatternAssignId());
							assignShifts.setShiftPatternDetailsInfo(new ShiftPatternDetailsInfo(shiftPatternDetailsId));
							assignShifts.setCustomerDetails(new CustomerDetails(paramShift.getCustomerId()));
							assignShifts.setCompanyDetails(new CompanyDetails(paramShift.getCompanyId()));
							assignShifts.setDaySequence(object.getDaySequence());
							assignShifts.setShiftCode(object.getShiftCode());
							assignShifts.setShiftDefine(new ShiftsDefine(object.getShiftDefineId()));
							assignShifts.setModifiedBy(paramShift.getModifiedBy());
							assignShifts.setModifiedDate(new Date());
							session.update(assignShifts);
							//shiftPatternDetailsId = assignShifts.getShiftPatternAssignId();
						}else{	
							
						}	*/
					}
				}
			}		
			
			if(paramShift.getShiftPatternDetailsId()+"" != null && paramShift.getShiftPatternDetailsId()+"" != "" && paramShift.getShiftPatternDetailsId() >0 ){						
				Query q = session.createSQLQuery("delete from Shift_Pattern_Additional_Rules where shift_pattern_Details_Id = '"+paramShift.getShiftPatternDetailsId()+"'");
				q.executeUpdate();
			}
			
			if(shiftPatternId > 0 && paramShift.getPatternAdditionalList() != null && paramShift.getPatternAdditionalList().size() > 0){
			
				if(paramShift.getPatternAdditionalList() != null ){
					for(ShiftPatternAdditionalRulesVo object : paramShift.getPatternAdditionalList()){
						//System.out.println(object.getShiftPatternRulesId()+":::id");
						
						ShiftPatternAdditionalRules rules = new ShiftPatternAdditionalRules();
						rules.setShiftPatternDetailsInfo(new ShiftPatternDetailsInfo(shiftPatternDetailsId));
						rules.setCustomerDetails(new CustomerDetails(paramShift.getCustomerId()));
						rules.setCompanyDetails(new CompanyDetails(paramShift.getCompanyId()));
						rules.setShiftOccurrence(object.getShiftOccurrence());
						rules.setShiftWeekDay(object.getShiftWeekDay());
						rules.setShiftCode(object.getShiftCode());
						rules.setShiftDefine(new ShiftsDefine(object.getShiftDefineId()));
						rules.setCreatedBy(paramShift.getModifiedBy());
						rules.setModifiedBy(paramShift.getModifiedBy());
						rules.setCreatedDate(new Date());
						rules.setModifiedDate(new Date());						
						session.save(rules);
						
						/*if(object != null && object.getShiftPatternRulesId() > 0){
							System.out.println(object.getShiftPatternRulesId()+":::id if");
							rules = (ShiftPatternAdditionalRules) sessionFactory.getCurrentSession().load(ShiftPatternAdditionalRules.class, object.getShiftPatternRulesId());
							rules.setShiftPatternDetailsInfo(new ShiftPatternDetailsInfo(shiftPatternDetailsId));
							rules.setCustomerDetails(new CustomerDetails(paramShift.getCustomerId()));
							rules.setCompanyDetails(new CompanyDetails(paramShift.getCompanyId()));
							rules.setShiftOccurrence(object.getShiftOccurrence());
							rules.setShiftWeekDay(object.getShiftWeekDay());
							rules.setShiftCode(object.getShiftCode());
							rules.setShiftDefine(new ShiftsDefine(object.getShiftDefineId()));
							rules.setModifiedBy(paramShift.getModifiedBy());
							rules.setModifiedDate(new Date());
							session.update(rules);
							//shiftPatternDetailsId = assignShifts.getShiftPatternAssignId();
						}else{	
							System.out.println(object.getShiftPatternRulesId()+":::id else");
								
						}*/	
					}
				}
			}
			session.flush();
		}catch (Exception e) {
			 if(!(session.getTransaction().wasRolledBack())){
				 session.getTransaction().rollback();
			 }
			 log.error("Error Occurred...   ", e);
			 log.info("Exiting from saveShiftPattern()  ::  shiftPatternDetailsId =  "+shiftPatternDetailsId);
		}				
		log.info("Exiting from saveShiftPattern()  ::  shiftPatternDetailsId =  "+shiftPatternDetailsId);
		return shiftPatternDetailsId;
	}

	/*
	 * This method will be used to get the Shift pattern record based on given shift Pattern Details Id
	 */
	@Override
	public List<ShiftPatternVo> getShiftPatternById(Integer shiftPatternDetailsId) {
		log.info("Entered in  getShiftPatternById()  ::   shiftPatternDetailsId =  "+shiftPatternDetailsId);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<ShiftPatternVo> returnList = new ArrayList<ShiftPatternVo>();
		ShiftPatternVo shift = new ShiftPatternVo();
		
		String hqlQuery = "SELECT info.Customer_Id, info.Company_Id, info.Shift_Pattern_Id,  info.Shift_Pattern_Name, "
							+ " info.Transaction_Date, info.Status, info.Shift_Pattern_Details_Id , shift.Shift_Pattern_Code, "
							+ " shift.Country_Id, info.Total_Pattern_Hours, "
							+ " CASE WHEN info.Define_Pattern_By = 'Daily' THEN '1' ELSE"
								+ "	CASE WHEN info.Define_Pattern_By = 'Weekly' THEN '7' ELSE "
									+ " CASE WHEN info.Define_Pattern_By = 'Bi Weekly' THEN '14' ELSE"
										+ " CASE WHEN info.Define_Pattern_By = 'Monthly' THEN '31' "
										+ "END "
									+ " END "
								+ " END "
							+ " END , "
							+ " info.Has_Additional_Shift_Rules,location_id,plant_id "
							//+ " assign.Shift_Code, assign.Day_Sequence, "
							//+ " define.Shift_Name, define.OFF_Shift, define.Start_Time, define.End_Time, define.Total_Hours "
							//+ " rule.Shift_Occurence, rule.Shift_Week_Day, rule.Shift_Code "
							+ " FROM shift_pattern_details_info AS info "
							+ " LEFT JOIN shift_pattern_details shift ON shift.Shift_Pattern_Id = info.Shift_Pattern_Id"
							//+ " LEFT JOIN shift_pattern_assign_shifts assign ON info.Shift_Pattern_Details_Id = assign.Shift_Pattern_Details_Id "
							//+ " LEFT JOIN shift_pattern_additional_rules rule ON info.Shift_Pattern_Details_Id = rule.Shift_Pattern_Details_Id "
							//+ " LEFT JOIN shifts_define define ON assign.Shift_Code = define.Shift_Code "
							+ " LEFT JOIN company_details AS cmp  ON info.Customer_Id = cmp.Customer_Id  AND info.Company_Id = cmp.Company_Id "
							+ " LEFT JOIN customer_details AS cus  ON info.Customer_Id = cus.Customer_Id "
							+ " WHERE info.Shift_Pattern_Details_Id = "+shiftPatternDetailsId;
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){
				for (Object customer : queryList) {
					Object[] obj = (Object[]) customer;
					shift = new ShiftPatternVo();
					
					shift.setCustomerId((Integer)obj[0]);
					shift.setCompanyId((Integer)obj[1]);
					shift.setShiftPatternId((Integer)obj[2]);
					shift.setShiftPatternName((String)obj[3]);
					shift.setTransactionDate((Date)obj[4]);
					shift.setStatus(obj[5]+"");
					shift.setShiftPatternDetailsId((Integer)obj[6]);
					shift.setShiftPatternCode(obj[7]+"");
					shift.setCountryId((Integer)obj[8]);
					shift.setTotalPatternHours(obj[9]+"");
					shift.setDefinePatternBy(obj[10]+"");
					shift.setHasAdditionalRules((obj[11]+"").equalsIgnoreCase("Y") ? true : false);
					if(obj[12] != null)
						shift.setLocationDetailsId((Integer)obj[12]);
					else
						shift.setLocationDetailsId(0);
					if(obj[13] != null)
						shift.setPlantDetailsId((Integer)obj[13]);
					else
						shift.setPlantDetailsId(0);
					
					returnList.add(shift);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getShiftPatternById()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getShiftPatternById()  ::   "+returnList);
		return returnList;
	}

	/*
	 * This method will be used to get the list of Shift Pattern records based on given customerId, companyId , shiftPatternCode
	 * 																		and shiftPatternName
	 */
	@Override
	public List<ShiftPatternVo> getShiftPatternListBySearch(ShiftPatternVo paramShift) {
		log.info("Entered in  getShiftPatternListBySearch()  ::   ShiftPatternVo  = "+paramShift);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<ShiftPatternVo> returnList = new ArrayList<ShiftPatternVo>();
		ShiftPatternVo shift = new ShiftPatternVo();
		
		String hqlQuery = "SELECT info.Customer_Id, info.Company_Id, company.Company_Name, customer.Customer_Name, "
							+ "	info.Shift_Pattern_Id,  info.Shift_Pattern_Name, info.Transaction_Date, "
							+ " case when info.Status = 'Y' then 'Active'   else 'In Active' end as status, "
							+ " info.Shift_Pattern_Details_Id , shift.Shift_Pattern_Code ,CASE WHEN info.Location_Id IS NULL THEN 'All' ELSE loc.location_name END AS locname,CASE WHEN info.Plant_Id IS NULL THEN 'All' ELSE plant.plant_name END AS plantname,define_pattern_by  "
							+ " FROM shift_pattern_details_info AS info "
							+ " INNER JOIN shift_pattern_details shift ON shift.Shift_Pattern_Id = info.Shift_Pattern_Id"
							+ " INNER JOIN company_details  cmp  ON info.Customer_Id = cmp.Customer_Id  "
																			+ "	AND info.Company_Id = cmp.Company_Id "
							+ " INNER JOIN company_details_info  company  ON company.Customer_Id = info.Customer_Id  "
																			+ "	AND company.Company_Id = info.Company_Id "
							+ " INNER JOIN customer_details  cus  ON info.Customer_Id = cus.Customer_Id "
							+ " INNER JOIN customer_details_info  customer  ON customer.Customer_Id = info.Customer_Id "
							+ " LEFT JOIN location_details_info loc ON  (loc.Location_Id = info.Location_Id AND "+ 
							" CONCAT(DATE_FORMAT(loc.transaction_date, '%Y%m%d'), LPAD(loc.location_Sequence_Id, 2, '0')) = "+  
							" ( "+  
							" SELECT MAX(CONCAT(DATE_FORMAT(loc1.transaction_date, '%Y%m%d'), LPAD(loc1.location_Sequence_Id, 2, '0'))) "+ 
							" FROM location_details_info loc1 "+ 
							" WHERE  loc.Location_Id = loc1.Location_Id   AND loc1.status='Y' "+  
							" AND loc1.transaction_date <= CURRENT_DATE()   )) "+
							" LEFT JOIN  plant_details_info  plant ON (plant.Plant_Id = info.Plant_Id AND "+ 
							" CONCAT(DATE_FORMAT(plant.transaction_date, '%Y%m%d'), LPAD(plant.plant_Sequence_Id, 2, '0')) = "+  
							" ( "+ 
							" SELECT MAX(CONCAT(DATE_FORMAT(plant1.transaction_date, '%Y%m%d'), LPAD(plant1.plant_Sequence_Id, 2, '0'))) "+ 
							" FROM plant_details_info plant1 "+ 
							" WHERE  plant.Plant_Id = plant1.Plant_Id   AND plant1.status='Y' "+  
							" AND plant1.transaction_date <= CURRENT_DATE()   )) " 
							
							+ " WHERE  CONCAT(DATE_FORMAT(info.transaction_date, '%Y%m%d'), LPAD(info.Shift_Pattern_Sequence_Id, 2, '0')) =    "
							+ " ( " 
							+ " SELECT MAX(CONCAT(DATE_FORMAT(info1.transaction_date, '%Y%m%d'), LPAD(info1.Shift_Pattern_Sequence_Id, 2, '0')))   "
							+ " FROM shift_pattern_details_info info1   "
							+ " WHERE  info.Shift_Pattern_Id = info1.Shift_Pattern_Id   "
							+ " AND info1.transaction_date <= CURRENT_DATE()   ) "
							
							+ " AND CONCAT(DATE_FORMAT(customer.transaction_date, '%Y%m%d'), LPAD(customer.Customer_Sequence_Id, 2, '0')) =    "
							+ " (   "
							+ " SELECT MAX(CONCAT(DATE_FORMAT(customer1.transaction_date, '%Y%m%d'), LPAD(customer1.Customer_Sequence_Id, 2, '0')))   "
							+ " FROM customer_details_info customer1   "
							+ " WHERE  customer.Customer_Id = customer1.Customer_Id   "
							+ " AND customer1.transaction_date <= CURRENT_DATE()   )  "
							 
							+ "  AND CONCAT(DATE_FORMAT(company.transaction_date, '%Y%m%d'), LPAD(company.company_Sequence_Id, 2, '0')) =    "
							+ "  (   "
							+ "  SELECT MAX(CONCAT(DATE_FORMAT(company1.transaction_date, '%Y%m%d'), LPAD(company1.company_Sequence_Id, 2, '0')))   "
							+ "  FROM company_details_info company1   "
							+ "  WHERE  company.Company_Id = company1.Company_Id   "
							+ "  AND company1.transaction_date <= CURRENT_DATE()   )  ";
			
		if(paramShift.getCustomerId() > 0){
			hqlQuery += "  AND  info.Customer_Id = "+paramShift.getCustomerId();
		}
		
		if(paramShift.getCompanyId() > 0){
			hqlQuery += "  AND  info.Company_Id = "+paramShift.getCompanyId();
		}
		if(paramShift.getShiftPatternCode() != null && !paramShift.getShiftPatternCode().isEmpty()){
			hqlQuery += "  AND  shift.Shift_Pattern_Code LIKE '"+paramShift.getShiftPatternCode()+"%' ";
		}
		
		if(paramShift.getShiftPatternName() != null && !paramShift.getShiftPatternName().isEmpty()){
			hqlQuery += " AND info.Shift_Pattern_Name LIKE '"+paramShift.getShiftPatternName()+"%' ";
		}
		
		if(paramShift.getStatus() != null && !paramShift.getStatus().isEmpty()){
			hqlQuery += " AND info.Status LIKE '"+paramShift.getStatus()+"%' ";
		}
		
		hqlQuery += " GROUP BY info.Shift_Pattern_Id Order By shift.Shift_Pattern_code asc";
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){
				for (Object customer : queryList) {
					Object[] obj = (Object[]) customer;
					shift = new ShiftPatternVo();
					
					shift.setCustomerId((Integer)obj[0]);
					shift.setCompanyId((Integer)obj[1]);
					shift.setCompanyName((String)obj[2]);
					shift.setCustomerName((String)obj[3]);
					shift.setShiftPatternId((Integer)obj[4]);
					shift.setShiftPatternName((String)obj[5]);
					shift.setTransactionDate((Date)obj[6]);
					shift.setStatus(obj[7]+"");
					shift.setShiftPatternDetailsId((Integer)obj[8]);
					shift.setShiftPatternCode(obj[9]+"");
					shift.setLocationName(obj[10]+"");
					shift.setPlantName(obj[11]+"");
					shift.setDefinePatternBy(obj[12]+"");
					returnList.add(shift);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getShiftPatternListBySearch()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getShiftPatternListBySearch()  ::   "+returnList);
		return returnList;
	}

	/*
	 * This method will be used to get transaction dates list for shift pattern based on customerId, companyId and shiftPatternId
	 */
	@Override
	public List<SimpleObject> getTransactionListForShiftPattern(Integer customerId, Integer companyId, Integer shiftPatternId) {
		log.info("Entered in getTransactionListForShiftPattern()  ::   customerId = "+customerId+" , companyId = "+companyId+" , shiftPatternId = "+shiftPatternId);
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List patternList = session.createSQLQuery("SELECT Shift_Pattern_Details_Id AS id ,CONCAT(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),' - ',Shift_Pattern_Sequence_Id) AS name FROM  shift_pattern_details_info info  WHERE info.Customer_Id = "+customerId+" AND info.Company_Id = "+companyId+" AND info.Shift_Pattern_Id = "+shiftPatternId+" ORDER BY info.Transaction_Date, info.Shift_Pattern_Details_Id").list();
			for (Object transDates  : patternList) {
				Object[] transaction = (Object[]) transDates;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)transaction[0]);
				object.setName(transaction[1]+"");
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getTransactionListForShiftPattern()  ::   "+transactionList);
		}
		
		log.info("Exiting from getTransactionListForShiftPattern()  ::   "+transactionList);
		return transactionList;
	}

	/*@Override
	public List<ShiftPatternAdditionalRulesVo> getShiftAdditionalRules(Integer customerId, Integer companyId, Integer shiftPatternDetailsId) {
		log.info("Entered in  getShiftAdditionalRules()  ::   shiftPatternDetailsId =  "+shiftPatternDetailsId);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<ShiftPatternAdditionalRulesVo> returnList = new ArrayList<ShiftPatternAdditionalRulesVo>();
		ShiftPatternAdditionalRulesVo shift = new ShiftPatternAdditionalRulesVo();
		
		String hqlQuery = "SELECT  rule.Shift_Pattern_Details_Id , rule.Shift_Occurrence,  rule.Shift_Code,"
								+ " (CASE WHEN rule.Shift_Week_Day = 'MONDAY' THEN '1' ELSE "
									+ "	(CASE WHEN rule.Shift_Week_Day = 'TUESDAY' THEN '2' ELSE "
										+ " (CASE WHEN rule.Shift_Week_Day = 'WEDNESDAY' THEN '3' ELSE  "
											+ " (CASE WHEN rule.Shift_Week_Day = 'THURSDAY' THEN '4' ELSE "
												+ " (CASE WHEN rule.Shift_Week_Day = 'FRIDAY' THEN '5' ELSE  "
													+ " (CASE WHEN rule.Shift_Week_Day = 'SATURDAY' THEN '6' ELSE  "
														+ " (CASE WHEN rule.Shift_Week_Day = 'SUNDAY' THEN '7' ELSE	"
														+ "'0' END)"
													+ "END) "
											+ "	END) "
										+ "END) "
									+ " END) "
								+ " END) "
							+ " END) Shift_Week_Day,"
							+ "	rule.Shift_Pattern_Rules_Id, rule.Shift_Define_Id "
							+ " FROM shift_pattern_details_info AS info "
							+ " LEFT JOIN shift_pattern_details shift ON shift.Shift_Pattern_Id = info.Shift_Pattern_Id"
							+ " LEFT JOIN shift_pattern_additional_rules rule ON info.Shift_Pattern_Details_Id = rule.Shift_Pattern_Details_Id "
							+ " LEFT JOIN shifts_define define ON rule.Shift_Code = define.Shift_Code "
							+ " LEFT JOIN company_details AS cmp  ON info.Customer_Id = cmp.Customer_Id  AND info.Company_Id = cmp.Company_Id "
							+ " LEFT JOIN customer_details AS cus  ON info.Customer_Id = cus.Customer_Id "
							+ " WHERE info.Shift_Pattern_Details_Id = "+shiftPatternDetailsId
							+ " Order By rule.Shift_Code asc ";
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){
				for (Object customer : queryList) {
					Object[] obj = (Object[]) customer;
					shift = new ShiftPatternAdditionalRulesVo();

					//shift.setShiftPatternDetailsId((Integer)obj[0]);
					shift.setShiftOccurrence(obj[1]+"");
					shift.setShiftWeekDay(obj[3]+"");
					shift.setShiftCode(obj[2]+"");
					shift.setShiftPatternRulesId(((Integer)obj[4] == null ? (Integer)0 : (Integer)obj[4] ));
					shift.setShiftDefineId(((Integer)obj[5] == null ? (Integer)0 : (Integer)obj[5] ));
					
					returnList.add(shift);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getShiftAdditionalRules()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getShiftAdditionalRules()  ::   "+returnList);
		return returnList;
	}*/
	
	@Override
	public List<ShiftPatternAdditionalRulesVo> getShiftAdditionalRules(Integer customerId, Integer companyId, Integer shiftPatternDetailsId) {
		log.info("Entered in  getShiftAdditionalRules()  ::   shiftPatternDetailsId =  "+shiftPatternDetailsId);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<ShiftPatternAdditionalRulesVo> returnList = new ArrayList<ShiftPatternAdditionalRulesVo>();
		ShiftPatternAdditionalRulesVo shift = new ShiftPatternAdditionalRulesVo();
		
		String hqlQuery = "SELECT rule.Shift_Pattern_Details_Id , "+
						   " rule.Shift_Occurrence, "+
						   "  rule.Shift_Code, "+
						   "  (CASE  "+
						   "      WHEN rule.Shift_Week_Day = 'MONDAY' THEN '1'  "+
						   "     ELSE  (CASE  "+
						   "          WHEN rule.Shift_Week_Day = 'TUESDAY' THEN '2'  "+
						   "          ELSE  (CASE  "+
						   "              WHEN rule.Shift_Week_Day = 'WEDNESDAY' THEN '3'  "+
						   "               ELSE   (CASE  "+
						   "                   WHEN rule.Shift_Week_Day = 'THURSDAY' THEN '4'  "+
						   "                   ELSE  (CASE  "+
						   "                       WHEN rule.Shift_Week_Day = 'FRIDAY' THEN '5'  "+
						   "                      ELSE   (CASE  "+
						   "                     WHEN rule.Shift_Week_Day = 'SATURDAY' THEN '6'  "+
						   "                         ELSE   (CASE  "+
						   "         WHEN rule.Shift_Week_Day = 'SUNDAY' THEN '7'  "+
						   "                              ELSE '0'  "+
						   "                         END) "+
						   "                     END)   "+
						   "                 END)  "+
						   "             END)   "+
						   "         END)   "+
						   "      END)   "+
						   "   END) Shift_Week_Day, "+
						   "   rule.Shift_Pattern_Rules_Id, "+
						   "    rule.Shift_Define_Id   "+
						   "    FROM shift_pattern_additional_rules  rule WHERE shift_pattern_details_Id = "+shiftPatternDetailsId
						   	+" Order By rule.Shift_Code asc ";
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){
				for (Object customer : queryList) {
					Object[] obj = (Object[]) customer;
					shift = new ShiftPatternAdditionalRulesVo();

					//shift.setShiftPatternDetailsId((Integer)obj[0]);
					shift.setShiftOccurrence(obj[1]+"");
					shift.setShiftWeekDay(obj[3]+"");
					shift.setShiftCode(obj[2]+"");
					shift.setShiftPatternRulesId(((Integer)obj[4] == null ? (Integer)0 : (Integer)obj[4] ));
					shift.setShiftDefineId(((Integer)obj[5] == null ? (Integer)0 : (Integer)obj[5] ));
					
					returnList.add(shift);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getShiftAdditionalRules()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getShiftAdditionalRules()  ::   "+returnList);
		return returnList;
	}

	@Override
	public List<ShiftPatternAssignShiftsVo> getShiftPatternAssignShifts(Integer customerId, Integer companyId, Integer shiftPatternDetailsId) {
		log.info("Entered in  getShiftPatternAssignShifts()  ::   shiftPatternDetailsId =  "+shiftPatternDetailsId);
		
		Session session = sessionFactory.getCurrentSession();		
		List<ShiftPatternAssignShiftsVo> returnList = new ArrayList<ShiftPatternAssignShiftsVo>();
		ShiftPatternAssignShiftsVo shift = new ShiftPatternAssignShiftsVo();
		
		String hqlQuery = "SELECT   assign.Shift_Pattern_Details_Id , assign.Shift_Code, assign.Day_Sequence, "
							+ " define.Shift_Name, define.OFF_Shift, define.Start_Time, define.End_Time, define.Total_Hours, "
							+ " assign.Shift_Pattern_Assign_Id, define.Shift_Define_Id,assign.Week_Name"
							+ " FROM shift_pattern_details_info AS info "
							+ " LEFT JOIN shift_pattern_details shift ON shift.Shift_Pattern_Id = info.Shift_Pattern_Id"
							+ " LEFT JOIN shift_pattern_assign_shifts assign ON info.Shift_Pattern_Details_Id = assign.Shift_Pattern_Details_Id "
							+ " LEFT JOIN shifts_define_view define ON assign.Shift_Code = define.Shift_Code "
							+ " LEFT JOIN company_details AS cmp  ON info.Customer_Id = cmp.Customer_Id  AND info.Company_Id = cmp.Company_Id "
							+ " LEFT JOIN customer_details AS cus  ON info.Customer_Id = cus.Customer_Id "
							+ " WHERE info.Shift_Pattern_Details_Id = "+shiftPatternDetailsId
							+ " ORDER BY assign.Day_Sequence asc ";
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			List queryList = query.list();
			List valueList = new ArrayList<>();
			if(queryList.size() > 0){
				for (Object customer : queryList) {
					Object[] obj = (Object[]) customer;
					shift = new ShiftPatternAssignShiftsVo();
					if(obj[0] != null) {
					//shift.setShiftPatternDetailsId((Integer)obj[0]);
					shift.setShiftCode(obj[1]+"");
					shift.setDaySequence((Integer)obj[2]);
					shift.setShiftName(obj[3]+"");
					shift.setOffShift((obj[4]+"").equalsIgnoreCase("Y") ? true : false);
					shift.setStartTime(obj[5]+"");
					shift.setEndTime(obj[6]+"");
					shift.setTotalHours(obj[7]+"");
					shift.setShiftPatternAssignId((Integer)(obj[8] == null ? 0 : obj[8]));
					shift.setShiftDefineId((Integer)obj[9]);
					shift.setWeekName(obj[10]+"");
					int value = shift.getShiftDefineId();
					valueList.add(value);
					shift.setValue(valueList);
					returnList.add(shift);
					}
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getShiftPatternAssignShifts()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getShiftPatternAssignShifts()  ::   "+returnList);
		return returnList;
	}

	@Override
	public List<SimpleObject> getShiftPatternDropdown(Integer customerId, Integer companyId) {
	log.info("Entered in  getShiftPatternDropdown()  ::   customerId  = "+customerId+", companyId = "+companyId);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<SimpleObject> returnList = new ArrayList<SimpleObject>();
		
		String hqlQuery = "SELECT info.Shift_Pattern_Id,  info.Shift_Pattern_Name,  shift.Shift_Pattern_Code  "
							+ " FROM shift_pattern_details_info AS info "
								+ " INNER JOIN shift_pattern_details shift ON shift.Shift_Pattern_Id = info.Shift_Pattern_Id"
								+ " INNER JOIN customer_view  customer  ON info.Customer_Id = customer.Customer_Id "
								+ " INNER JOIN company_view  company  ON info.Customer_Id = company.Customer_Id  AND info.Company_Id = company.Company_Id "
								+ " LEFT JOIN location_details_info loc ON  (loc.Location_Id = info.Location_Id AND "+ 
										" CONCAT(DATE_FORMAT(loc.transaction_date, '%Y%m%d'), LPAD(loc.location_Sequence_Id, 2, '0')) = "  
										+ " ( "  
										+ " SELECT MAX(CONCAT(DATE_FORMAT(loc1.transaction_date, '%Y%m%d'), LPAD(loc1.location_Sequence_Id, 2, '0'))) " 
										+ " FROM location_details_info loc1 " 
										+ " WHERE  loc.Location_Id = loc1.Location_Id    "  
										+ " AND loc1.transaction_date <= CURRENT_DATE()   )) "
								+ " LEFT JOIN  plant_details_info  plant ON (plant.Plant_Id = info.Plant_Id AND "
										+ " CONCAT(DATE_FORMAT(plant.transaction_date, '%Y%m%d'), LPAD(plant.plant_Sequence_Id, 2, '0')) = "  
										+ " ( " 
										+ " SELECT MAX(CONCAT(DATE_FORMAT(plant1.transaction_date, '%Y%m%d'), LPAD(plant1.plant_Sequence_Id, 2, '0'))) " 
										+ " FROM plant_details_info plant1 " 
										+ " WHERE  plant.Plant_Id = plant1.Plant_Id   AND plant1.status='Y' " 
										+ " AND plant1.transaction_date <= CURRENT_DATE()   )) " 
							+ " WHERE  CONCAT(DATE_FORMAT(info.transaction_date, '%Y%m%d'), LPAD(info.Shift_Pattern_Sequence_Id, 2, '0')) =    "
										+ " ( " 
											+ " SELECT MAX(CONCAT(DATE_FORMAT(info1.transaction_date, '%Y%m%d'), LPAD(info1.Shift_Pattern_Sequence_Id, 2, '0')))   "
											+ " FROM shift_pattern_details_info info1   "
											+ " WHERE  info.Shift_Pattern_Id = info1.Shift_Pattern_Id   "
											+ " AND info1.transaction_date <= CURRENT_DATE()   ) "
								+ " AND  info.Customer_Id = "+customerId
								+ " AND  info.Company_Id = "+companyId
								+ " AND customer.Is_Active = 'Y' "
								+ " AND company.Is_Active = 'Y' "
								+ " AND loc.Status = 'Y' "
								+ " AND plant.status = 'Y' "
								+ " AND info.status = 'Y'"
								+ " GROUP BY info.Shift_Pattern_Id Order By shift.Shift_Pattern_code asc";
		try {				
			queryList = session.createSQLQuery(hqlQuery).list();
			if(queryList.size() > 0){
				for (Object pattern : queryList) {
					Object[] obj = (Object[]) pattern;
					SimpleObject  object = new SimpleObject();
					
					object.setId((Integer)obj[0]);
					object.setName((String)obj[1]);
					object.setDesc((String)obj[2]);
					
					returnList.add(object);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getShiftPatternDropdown()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getShiftPatternDropdown()  ::   "+returnList);
		return returnList;
	}

}
