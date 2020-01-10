package com.Ntranga.CLMS.Dao;


import java.math.BigInteger;
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

import com.Ntranga.CLMS.vo.DefineExceptionVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.TimeExceptionRulesVo;
import com.Ntranga.CLMS.vo.TimeExceptionVo;
import com.Ntranga.CLMS.vo.TimeRuleBufferTimeVo;
import com.Ntranga.CLMS.vo.TimeRuleWorkDayStatusVo;
import com.Ntranga.CLMS.vo.TimeRulesDetailsVo;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CompanyDetailsInfo;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.DefineExceptions;
import com.Ntranga.core.CLMS.entities.DivisionDetails;
import com.Ntranga.core.CLMS.entities.DivisionDetailsInfo;
import com.Ntranga.core.CLMS.entities.MCountry;
import com.Ntranga.core.CLMS.entities.TimeExceptionRules;
import com.Ntranga.core.CLMS.entities.TimeExceptions;
import com.Ntranga.core.CLMS.entities.TimeRuleBufferTime;
import com.Ntranga.core.CLMS.entities.TimeRuleWorkDayStatus;
import com.Ntranga.core.CLMS.entities.TimeRulesDetails;
import com.Ntranga.core.CLMS.entities.TimeRulesDetailsInfo;

import common.Logger;

@SuppressWarnings({"unused","unchecked","rawtypes"})
@Transactional
@Repository("timeRulesDao")
public class TimeRulesDaoImpl implements TimeRulesDao  {

	private static Logger log = Logger.getLogger(TimeRulesDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Integer saveOrUpdateTimeRules(TimeRulesDetailsVo rulesDetailsVo) {
		System.out.println(rulesDetailsVo);
		Session session = sessionFactory.getCurrentSession();
		Integer timeRulesId =0;
		Integer timeRulesInoId = 0;
		try {
			TimeRulesDetails trd = new TimeRulesDetails();
			
			trd.setCustomerId(rulesDetailsVo.getCustomerId());
			trd.setCompanyId(rulesDetailsVo.getCompanyId());
			trd.setCompanyId(rulesDetailsVo.getCompanyId());
			trd.setCountryId(rulesDetailsVo.getCountryId());
			trd.setTimeRuleCode(rulesDetailsVo.getTimeRuleCode());
			
			trd.setCreatedBy(rulesDetailsVo.getCreatedBy());
			trd.setModifiedBy(rulesDetailsVo.getModifiedBy());
			trd.setCreatedDate(new Date());
			trd.setModifiedDate(new Date());
			
			if(rulesDetailsVo != null && rulesDetailsVo.getTimeRulesId() == 0){
				timeRulesId =	(Integer) session.save(trd);				
			}else{
				trd.setTimeRuleId(rulesDetailsVo.getTimeRulesId());
				session.update(trd);	
				timeRulesId = trd.getTimeRuleId();
			}
			
			
			TimeRulesDetailsInfo trdInfo = new TimeRulesDetailsInfo();
			
			/*String hoursDay =  rulesDetailsVo.getHoursDay()!= null  ? DateHelper.getConvertedTime(rulesDetailsVo.getHoursDay()+":00")+"" : null;
			String hoursWeek =  rulesDetailsVo.getHoursWeek() != null  ? DateHelper.getConvertedTime(rulesDetailsVo.getHoursWeek()+":00")+"" : null;
			System.out.println(hoursDay+"::hoursDay::"+hoursWeek);*/
			
			trdInfo.setTimeRulesDetails(new TimeRulesDetails(timeRulesId));
			trdInfo.setCustomerId(rulesDetailsVo.getCustomerId());
			trdInfo.setCompanyId(rulesDetailsVo.getCompanyId());
			trdInfo.setCountryId(rulesDetailsVo.getCountryId());
			trdInfo.setTransactionDate(rulesDetailsVo.getTransactionDate());
			trdInfo.setIsActive(rulesDetailsVo.getIsActive());
			trdInfo.setTimeRulesDescription(rulesDetailsVo.getTimeRulesDescription());
			trdInfo.setWeekStartDay(rulesDetailsVo.getWeekStartDay());
			trdInfo.setWeekEndDay(rulesDetailsVo.getWeekEndDay());
			trdInfo.setFollowFirstInLastOut(rulesDetailsVo.getFollowFirstInLastOut());
			trdInfo.setHoursDay( rulesDetailsVo.getHoursDay());
			trdInfo.setHoursWeek(rulesDetailsVo.getHoursWeek());
			trdInfo.setMinimumDays(rulesDetailsVo.getMinimumDays());
			trdInfo.setMaximumDays(rulesDetailsVo.getMaximumDays());
			trdInfo.setShowWo(rulesDetailsVo.getShowWo());
			trdInfo.setShowPh(rulesDetailsVo.getShowPh());
			trdInfo.setShowLeaves(rulesDetailsVo.getShowLeaves());
			trdInfo.setShowOd(rulesDetailsVo.getShowOd());
			trdInfo.setCreatedBy(rulesDetailsVo.getCreatedBy());
			trdInfo.setModifiedBy(rulesDetailsVo.getModifiedBy());
			trdInfo.setCreatedDate(new Date());
			trdInfo.setModifiedDate(new Date());
			
			
			BigInteger SequenceId ;
			if(rulesDetailsVo != null &&  rulesDetailsVo.getTimeRulesInfoId() > 0){	
				trdInfo.setSequenceId(rulesDetailsVo.getSequenceId());
				trdInfo.setTimeRulesInfoId(rulesDetailsVo.getTimeRulesInfoId());
				SequenceId = BigInteger.valueOf(rulesDetailsVo.getSequenceId());
				session.update(trdInfo);
				timeRulesInoId = rulesDetailsVo.getTimeRulesInfoId();
			}else  {
				SequenceId = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Sequence_Id),0) FROM `time_rules_details_info` b  WHERE  b.Time_Rules_Id = '"+rulesDetailsVo.getTimeRulesId()+"' AND  b.`Transaction_Date` = '"+DateHelper.convertDateToSQLString(rulesDetailsVo.getTransactionDate())+"'").list().get(0);
				if(SequenceId.intValue() >0){
					trdInfo.setSequenceId(SequenceId.intValue()+1);
					SequenceId = BigInteger.valueOf(trdInfo.getSequenceId());
				}else{
					trdInfo.setSequenceId(1);					
					SequenceId = BigInteger.valueOf(trdInfo.getSequenceId());
				}
				
				timeRulesInoId = (Integer)session.save(trdInfo);			
			}
			
			
			if(rulesDetailsVo.getTimeRulesInfoId() != null && rulesDetailsVo.getTimeRulesInfoId() >0){						
				Query q = session.createSQLQuery("delete from time_rule_work_day_status where Time_Rule_Id = '"+rulesDetailsVo.getTimeRulesId()+"' and Sequence_Id = '"+SequenceId.intValue()+"' and Transaction_Date ='"+DateHelper.convertDateToSQLString(rulesDetailsVo.getTransactionDate())+"'");
				q.executeUpdate();
			}
				
			
			for(TimeRuleWorkDayStatusVo daysVo :rulesDetailsVo.getWorkDayStatusList()){
					TimeRuleWorkDayStatus workDay = new TimeRuleWorkDayStatus();
					workDay.setTimeRulesDetails(new TimeRulesDetails(timeRulesId));
					workDay.setCustomerId(rulesDetailsVo.getCustomerId());
					workDay.setCompanyId(rulesDetailsVo.getCompanyId());
					workDay.setFromHours(daysVo.getFromHours());
					workDay.setToHours(daysVo.getToHours());
					workDay.setStatusDescription(daysVo.getStatusDescription());
					workDay.setStatusCode(daysVo.getStatusCode());
					workDay.setCreatedBy(rulesDetailsVo.getCreatedBy());
					workDay.setModifiedBy(rulesDetailsVo.getModifiedBy());
					workDay.setCreatedDate(new Date());
					workDay.setModifiedDate(new Date());
					workDay.setTransactionDate(rulesDetailsVo.getTransactionDate());
					workDay.setSequenceId(SequenceId.intValue());	
					workDay.setIsActive(rulesDetailsVo.getIsActive());
					session.save(workDay);
			}
			
			
			
			if(rulesDetailsVo.getTimeRulesInfoId() != null && rulesDetailsVo.getTimeRulesInfoId() >0){						
				Query q = session.createSQLQuery("delete from time_rule_buffer_time where Time_Rule_Id = '"+rulesDetailsVo.getTimeRulesId()+"' and Sequence_Id = '"+SequenceId.intValue()+"' and Transaction_Date ='"+DateHelper.convertDateToSQLString(rulesDetailsVo.getTransactionDate())+"'");
				q.executeUpdate();
			}
				
			
			for(TimeRuleBufferTimeVo buffTimeVo :rulesDetailsVo.getBufferTimeLsit()){
				TimeRuleBufferTime buffTime = new TimeRuleBufferTime();	
				buffTime.setTimeRulesDetails(new TimeRulesDetails(timeRulesId));
				buffTime.setCustomerId(rulesDetailsVo.getCustomerId());
				buffTime.setCompanyId(rulesDetailsVo.getCompanyId());
				buffTime.setBufferTime(buffTimeVo.getBufferTime());
				buffTime.setTimeEvent(buffTimeVo.getTimeEvent());
				buffTime.setExemptedOccurrence(buffTimeVo.getExemptedOccurrence());
				buffTime.setGreaterExemptedOccurrence(buffTimeVo.getGreaterExemptedOccurrence());
				buffTime.setPenality(buffTimeVo.getPenality());
				buffTime.setFromHours(buffTimeVo.getFromHours());
				buffTime.setToHours(buffTimeVo.getToHours());
				buffTime.setCreatedBy(rulesDetailsVo.getCreatedBy());
				buffTime.setModifiedBy(rulesDetailsVo.getModifiedBy());
				buffTime.setCreatedDate(new Date());
				buffTime.setModifiedDate(new Date());
				buffTime.setTransactionDate(rulesDetailsVo.getTransactionDate());
				buffTime.setSequenceId(SequenceId.intValue());	
				buffTime.setIsActive(rulesDetailsVo.getIsActive());
				
				session.save(buffTime);
			}
			
			
			
		}catch(Exception e){
			
			log.error("Error Occured ",e);
		}
		return timeRulesInoId;
	}


	
	public List<TimeRulesDetailsVo> getDetailsByRuleInfoId(Integer ruleInfoId) {

		List<TimeRulesDetailsVo> ruldDta = new ArrayList<TimeRulesDetailsVo>();
		Session session = sessionFactory.getCurrentSession();
		try {

			List tempList = session
					.createSQLQuery("SELECT tr.`Time_Rule_Id`,tr.`Time_Rule_Code`,tri.Time_Rules_Info_Id,tri.Time_Rules_Id,tri.Customer_Id,tri.Company_Id,tri.Country_Id,tri.Transaction_Date,tri.Sequence_Id, "
							+ " tri.Is_Active,tri.Time_Rules_Description,tri.Week_Start_Day,tri.Week_End_Day,tri.Follow_First_In_Last_Out,tri.Hours_Day,tri.Hours_Week, "
							+ " tri.Minimum_Days,tri.Maximum_Days,tri.Show_WO,tri.Show_PH,tri.Show_Leaves,tri.Show_OD,tri.Created_By,tri.Created_Date,tri.Modified_By, "
							+ " tri.Modified_Date FROM time_rules_details tr "
							+ " INNER JOIN time_rules_details_info tri ON(tr.`Time_Rule_Id` = tri.`Time_Rules_Id`) "
							+ " WHERE  "
							+ " CONCAT(DATE_FORMAT(tri.transaction_date, '%Y%m%d'), LPAD(tri.Sequence_Id,2,'0')) =   "
							+ " 	(   "
							+ " SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id,2,'0'))) "
							+ " FROM time_rules_details_info vdi1  "
							+ " WHERE tri.`Time_Rules_Info_Id` = vdi1.`Time_Rules_Info_Id` "
							+ " AND vdi1.transaction_date <= CURRENT_DATE()) and tri.Time_Rules_Info_Id = "
							+ ruleInfoId)
					.list();
			for (Object o : tempList) {
				Object[] obj = (Object[]) o;
				TimeRulesDetailsVo timeRulesDetailsVo = new TimeRulesDetailsVo();
				timeRulesDetailsVo.setTimeRulesId((Integer) obj[0]);
				timeRulesDetailsVo.setTimeRuleCode(obj[1] + "");
				
				timeRulesDetailsVo.setTimeRulesInfoId((Integer) obj[2]);
				timeRulesDetailsVo.setCustomerId((Integer) obj[4]);
				timeRulesDetailsVo.setCompanyId((Integer) obj[5]);
				timeRulesDetailsVo.setCountryId((Integer) obj[6]);
				timeRulesDetailsVo.setTransactionDate((Date) obj[7]);
				timeRulesDetailsVo.setSequenceId((Integer) obj[8]);
				timeRulesDetailsVo.setIsActive(obj[9] + "");
				timeRulesDetailsVo.setTimeRulesDescription(obj[10] + "");
				timeRulesDetailsVo.setWeekStartDay((String)obj[11]);
				timeRulesDetailsVo.setWeekEndDay((String)obj[12]);
				timeRulesDetailsVo.setFollowFirstInLastOut((String)obj[13]);				
				timeRulesDetailsVo.setHoursDay((Integer)obj[14]);
				timeRulesDetailsVo.setHoursWeek((Integer)obj[15]);
				timeRulesDetailsVo.setMinimumDays((String)obj[16]);
				timeRulesDetailsVo.setMaximumDays((String)obj[17]);
				timeRulesDetailsVo.setShowWo(obj[18] + "");
				timeRulesDetailsVo.setShowPh(obj[19] + "");
				timeRulesDetailsVo.setShowLeaves(obj[20]+ "");
				timeRulesDetailsVo.setShowOd(obj[21] + "");
				timeRulesDetailsVo.setCreatedBy((Integer) obj[22]);
				timeRulesDetailsVo.setModifiedBy((Integer) obj[24]);
				timeRulesDetailsVo.setCreatedDate((Date) obj[23]);
				timeRulesDetailsVo.setModifiedDate((Date) obj[25]);

				List wrkDaysList = session.createSQLQuery(
						"SELECT `From_Hours`,`To_Hours`,`Status_Description`,`Status_Code` FROM time_rules_details_info tr "
								+ " INNER JOIN time_rule_work_day_status tri ON(tr.`Time_Rules_Id` = tri.`Time_Rule_Id`  "
								+ " AND tr.transaction_date = tri.transaction_date AND tr.sequence_id = tri.sequence_id)  "
								+ " where tr.Time_Rules_Info_Id = " + ruleInfoId)
						.list();

				for (Object o1 : wrkDaysList) {
					Object[] obj1 = (Object[]) o1;
					TimeRuleWorkDayStatusVo dayStatusVo = new TimeRuleWorkDayStatusVo();
					dayStatusVo.setFromHours(obj1[0] + "");
					dayStatusVo.setToHours(obj1[1] + "");
					dayStatusVo.setStatusDescription(obj1[2] + "");
					dayStatusVo.setStatusCode(obj1[3] + "");
					timeRulesDetailsVo.getWorkDayStatusList().add(dayStatusVo);

				}

				List bufferList = session
						.createSQLQuery("SELECT `Buffer_Time`,`Time_Event`,Exempted_Occurrence, Greater_Exempted_Occurrence,From_Hours, To_Hours, Penality FROM time_rules_details_info tr "
								+ " INNER JOIN time_rule_buffer_time tri ON(tr.`Time_Rules_Id` = tri.`Time_Rule_Id`  "
								+ " AND tr.transaction_date = tri.transaction_date AND tr.sequence_id = tri.sequence_id)  "
								+ " where tr.Time_Rules_Info_Id = " + ruleInfoId)
						.list();

				for (Object o2 : bufferList) {
					Object[] obj2 = (Object[]) o2;
					TimeRuleBufferTimeVo bufferTimeVo = new TimeRuleBufferTimeVo();
					bufferTimeVo.setBufferTime((Integer) obj2[0]);
					bufferTimeVo.setTimeEvent((String)obj2[1]);
					bufferTimeVo.setExemptedOccurrence((Integer)obj2[2]);
					bufferTimeVo.setGreaterExemptedOccurrence((Integer)obj2[3]);
					bufferTimeVo.setFromHours((String)obj2[4]);
					bufferTimeVo.setToHours((String)obj2[5]);
					bufferTimeVo.setPenality((String)obj2[6]);
					timeRulesDetailsVo.getBufferTimeLsit().add(bufferTimeVo);

				}

				ruldDta.add(timeRulesDetailsVo);

			}

		} catch (Exception e) {
			log.error("Error Occured ",e);
		}

		return ruldDta;
	}
	
	
	public List<SimpleObject> getRuleCodesList(Integer customerId,Integer companyId) {

		List<SimpleObject> ruldDta = new ArrayList<SimpleObject>();
		Session session = sessionFactory.getCurrentSession();
		try {

			List tempList = session
					.createSQLQuery("SELECT tr.`Time_Rule_Code`,tri.Time_Rules_Info_Id, tri.Time_Rules_Description FROM time_rules_details tr "
							+ " INNER JOIN time_rules_details_info tri ON(tr.`Time_Rule_Id` = tri.`Time_Rules_Id`) "
							+ " WHERE  "
							+ " CONCAT(DATE_FORMAT(tri.transaction_date, '%Y%m%d'), LPAD(tri.Sequence_Id,2,'0')) =   "
							+ " 	(   "
							+ " SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id,2,'0'))) "
							+ " FROM time_rules_details_info vdi1  "
							+ " WHERE tri.`Time_Rules_Id` = vdi1. `Time_Rules_Id` "
							+ " AND vdi1.transaction_date <= CURRENT_DATE()) and tri.customer_Id = "
							+ customerId +" and tri.company_Id="+companyId+" ORDER BY tr.`Time_Rule_Code` asc ").list();
			for (Object o : tempList) {
				Object[] obj = (Object[]) o;
				SimpleObject timeRulesDetailsVo = new SimpleObject();				
				timeRulesDetailsVo.setName(obj[0] + "");
				timeRulesDetailsVo.setId((Integer) obj[1]);
				timeRulesDetailsVo.setDesc((String)obj[2]);
				ruldDta.add(timeRulesDetailsVo);

			}

		} catch (Exception e) {
			log.error("Error Occured ",e);
		}

		return ruldDta;
	}
	
	
	
	@Override
	public List<SimpleObject> getTransactionDatesListForEditingTimeRules(Integer timeRulesId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List rulesList = session.createSQLQuery("select Time_Rules_Info_Id,concat(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),'-',Sequence_Id) from time_rules_details_info  where time_Rules_Id = '"+timeRulesId+"' and  Is_Active = 'Y'  order by Transaction_Date ,Sequence_Id " ).list();
			for (Object o  : rulesList) {
				Object[] obj = (Object[]) o;
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
	public Integer saveException(DefineExceptionVo paramException) {
		log.info("Entered in saveException()  ::  paramException =  "+paramException);

		Session session = sessionFactory.getCurrentSession();
		Integer exceptionId = 0;
		BigInteger exceptionSequenceId = new BigInteger("0");	
		BigInteger uniqueId = new BigInteger("0");	
		
		DefineExceptions defineExceptions = new DefineExceptions();
		
				
		try{
					
			defineExceptions.setCustomerDetails(new CustomerDetails(paramException.getCustomerId()));
			defineExceptions.setCompanyDetails(new CompanyDetails(paramException.getCompanyId()));
			defineExceptions.setCountryDetails(new MCountry(paramException.getCountryId()));
			defineExceptions.setExceptionCode(paramException.getExceptionCode().toUpperCase());
			defineExceptions.setExceptionName(paramException.getExceptionName());
			defineExceptions.setTransactionDate(paramException.getTransactionDate());
			defineExceptions.setEffectiveStatus(paramException.getEffectiveStatus());
			defineExceptions.setModifiedBy(paramException.getModifiedBy());
			defineExceptions.setModifiedDate(new Date());						
			
			if(paramException.getExceptionId() != null && paramException.getExceptionId() > 0){
				defineExceptions = (DefineExceptions) session.load(DefineExceptions.class, paramException.getExceptionId());
				session.update(defineExceptions);
				exceptionId  = paramException.getExceptionId();
			}else{
				defineExceptions.setCreatedDate(new Date());
				defineExceptions.setModifiedDate(new Date());
				if(paramException.getExceptionUniqueId() != null && paramException.getExceptionUniqueId() > 0 ){
					exceptionSequenceId  = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Exception_Sequence_Id),0) FROM define_exceptions"
							+ "  WHERE Exception_Unique_Id = "+paramException.getExceptionUniqueId() +" AND `Transaction_Date` = '"+DateHelper.convertDateToSQLString(paramException.getTransactionDate())+"' AND Customer_Id='"+paramException.getCustomerId()+"' AND Company_Id = '"+paramException.getCompanyId()+"'").list().get(0);
					log.info("Exception sequence Id : "+exceptionSequenceId);
					defineExceptions.setExceptionUniqueId( paramException.getExceptionUniqueId());
				}else{
					uniqueId = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Exception_Unique_Id),0) as id FROM define_exceptions").list().get(0);
					defineExceptions.setExceptionUniqueId(uniqueId.intValue()+1 );	
				}	
				defineExceptions.setExceptionSequenceId(exceptionSequenceId.intValue()+1);		
				
				exceptionId = (Integer) session.save(defineExceptions);
				
			}	
			
		}catch (Exception e) {
			 if(!(session.getTransaction().wasRolledBack())){
				 session.getTransaction().rollback();
			 }
			 log.error("Error Occurred...   ", e);
			 log.info("Exiting from saveException()  ::  ExceptionId =  "+exceptionId);
		}				
		log.info("Exiting from saveException()  ::  ExceptionId =  "+exceptionId);
		return exceptionId;
	}



	@Override
	public List<DefineExceptionVo> getExceptionsListBySearch(DefineExceptionVo paramException) {
		log.info("Entered in  getExceptionsListBySearch()  ::   paramException  = "+paramException);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<DefineExceptionVo> returnList = new ArrayList<DefineExceptionVo>();
		DefineExceptionVo exception = new DefineExceptionVo();
		
		String hqlQuery = "SELECT company.Company_Name, customer.Customer_Name, exception.Exception_Unique_Id,  "
							+ "	exception.Exception_Name, exception.Transaction_Date, exception.Effective_Status, exception.Exception_Id , "
							+ " exception.Exception_Code, country.Country_Name"
							+ " FROM define_exceptions AS exception "
							+ " LEFT JOIN customer_view AS customer  ON customer.Customer_Id = exception.Customer_Id  "
							+ " LEFT JOIN company_view AS Company  ON company.Customer_Id = exception.Customer_Id AND company.Company_Id = exception.Company_Id "
							+ " LEFT JOIN m_country as country ON exception.Country_Id = country.Country_Id"
								+ " WHERE 1 = 1 "
								+ " AND customer.Is_Active = 'Y' "
								+ " AND company.Is_Active = 'Y' "
								
								+ " AND  CONCAT(DATE_FORMAT(exception.Transaction_Date, '%Y%m%d'), exception.Exception_Sequence_Id) =  "
										+"	( "
										+"	SELECT MAX(CONCAT(DATE_FORMAT(exception1.Transaction_Date, '%Y%m%d'), exception1.Exception_Sequence_Id)) "
										+"	FROM define_exceptions AS exception1 "
										+"	WHERE exception.Exception_Unique_Id = exception1.Exception_Unique_Id AND exception1.Company_Id = exception1.company_Id AND  exception.Customer_Id = exception1.Customer_Id AND  exception1.Transaction_Date <= CURRENT_DATE() "
										+"	) ";
			
		if(paramException.getCustomerId() > 0){
			hqlQuery += "  AND  exception.Customer_Id = "+paramException.getCustomerId();
		}
		
		if(paramException.getCompanyId() > 0){
			hqlQuery += "  AND  exception.Company_Id = "+paramException.getCompanyId();
		}
		
		if(paramException.getExceptionName() != null && !paramException.getExceptionName().isEmpty()){
			hqlQuery += " AND exception.Exception_Name LIKE '"+paramException.getExceptionName()+"%' ";
		}
		
		if(paramException.getExceptionCode() != null && !paramException.getExceptionCode().isEmpty()){
			hqlQuery += " AND exception.Exception_Code LIKE '"+paramException.getExceptionCode()+"' ";
		}
		
		if(paramException.getEffectiveStatus() != null && !paramException.getEffectiveStatus().isEmpty()){
			hqlQuery += " AND exception.Effective_Status = '"+paramException.getEffectiveStatus()+"' ";
		}
		
		
		hqlQuery += " GROUP BY exception.Exception_Unique_Id Order By exception.Exception_Name asc";
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){
				for (Object customer : queryList) {
					Object[] obj = (Object[]) customer;
					exception = new DefineExceptionVo();
					
					exception.setCompanyName((String)obj[0]);
					exception.setCustomerName((String)obj[1]);
					exception.setExceptionUniqueId((Integer)obj[2]);
					exception.setExceptionName((String)obj[3]);
					exception.setTransactionDate((Date)obj[4]);
					exception.setEffectiveStatus((obj[5]+"").equalsIgnoreCase("Y") ? "Active":"Inactive");
					exception.setExceptionId((Integer)obj[6]);
					exception.setExceptionCode((String)obj[7]);
					exception.setCountryName((String)obj[8]);
					
					returnList.add(exception);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getExceptionsListBySearch()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getExceptionsListBySearch()  ::   "+returnList);
		return returnList;
	}



	@Override
	public List<DefineExceptionVo> getExceptionById(Integer exceptionId) {
		log.info("Entered in  getExceptionById()  ::   exceptionId = "+exceptionId);
		Session session = sessionFactory.getCurrentSession();
		DefineExceptionVo exception = new DefineExceptionVo();
		List<DefineExceptionVo> returnList = new ArrayList<DefineExceptionVo>();
		String  hqlQuery = " SELECT exception.Customer_Id, exception.Company_Id, exception.Exception_Id, exception.Exception_Unique_Id, exception.Exception_Code, "
							+ "	exception.Exception_Name,  exception.Country_Id,"
							+ " exception.Transaction_Date, exception.Effective_Status "							
							+ " FROM define_exceptions AS exception "
							+ " WHERE exception.Exception_Id = "+exceptionId;
		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			List exceptionList = query.list();

			for (Object object : exceptionList) {
				Object[] obj = (Object[]) object;
				exception = new DefineExceptionVo();
				exception.setCustomerId((Integer)obj[0]);
				exception.setCompanyId((Integer)obj[1]);
				exception.setExceptionId((Integer)obj[2]);
				exception.setExceptionUniqueId((Integer)obj[3]);
				exception.setExceptionCode((String)obj[4]);
				exception.setExceptionName((String)obj[5]);
				exception.setCountryId((Integer)obj[6]);
				exception.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[7]));
				exception.setEffectiveStatus((String)obj[8]);
				
				returnList.add(exception);
			}	
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from  getExceptionById()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from  getExceptionById()  ::   "+returnList);
		return returnList;
	}



	@Override
	public List<SimpleObject> getTransactionDatesListForException(Integer customerId, Integer companyId, Integer exceptionUniqueId) {
		log.info("Entered in getTransactionDatesListForException()  ::   customerId = "+customerId+" , companyId = "+companyId+" , exceptionUniqueId = "+exceptionUniqueId);
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List divisionList = session.createSQLQuery("SELECT Exception_Id AS id ,CONCAT(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),' - ',Exception_Sequence_Id) AS name FROM define_exceptions  WHERE Customer_Id = "+customerId+" AND Company_Id = "+companyId+" AND Exception_Unique_Id = "+exceptionUniqueId+" ORDER BY Transaction_Date, Exception_Id asc").list();
			for (Object transDates  : divisionList) {
				Object[] transaction = (Object[]) transDates;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)transaction[0]);
				object.setName(transaction[1]+"");
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getTransactionDatesListForException()  ::   "+transactionList);
		}
		
		log.info("Exiting from getTransactionDatesListForException()  ::   "+transactionList);
		return transactionList;
	}



	@Override
	public Integer validateExceptionCode(DefineExceptionVo paramException) {
		log.info("Entered in  validateExceptionCode()  ::   paramException  = "+paramException);
		
		Session session = sessionFactory.getCurrentSession();
		Integer rows = 0;
		try {				
			rows = session.createQuery(" FROM DefineExceptions WHERE customerDetails = "+paramException.getCustomerId()
													+" AND companyDetails = "+paramException.getCompanyId()
													+" AND exceptionCode = '"+paramException.getExceptionCode()+"' ").getFetchSize();
			System.out.println("From Exception validation: "+rows);
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from validateExceptionCode()  ::   "+rows);
		}
		session.flush();
		log.info("Exiting from validateExceptionCode()  ::   "+rows);
		return rows;
	}

	@Override
	public List<SimpleObject> getExceptionsDropDown(Integer customerId,Integer companyId, Integer countryId) {
		log.info("Entered in  getExceptionsDropDown()  ::   customerId  = "+customerId+", companyId = "+companyId+", countryId = "+countryId);
		
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> returnList = new ArrayList();
		try {				
			List queryList = session.createSQLQuery(" Select Exception_Code, Exception_Name, Exception_Unique_Id FROM define_exceptions exception WHERE Customer_Id = "+customerId
													+" AND Company_Id = "+companyId
													+" AND Country_Id = "+countryId
													+ " AND  CONCAT(DATE_FORMAT(exception.Transaction_Date, '%Y%m%d'), exception.Exception_Sequence_Id) =  "
													+"	( "
													+"	SELECT MAX(CONCAT(DATE_FORMAT(exception1.Transaction_Date, '%Y%m%d'), exception1.Exception_Sequence_Id)) "
													+"	FROM define_exceptions AS exception1 "
													+"	WHERE exception.Exception_Unique_Id = exception1.Exception_Unique_Id AND exception1.Company_Id = exception1.company_Id AND  exception.Customer_Id = exception1.Customer_Id AND  exception1.Transaction_Date <= CURRENT_DATE() "
													+"	) ").list();
			for(Object object : queryList){
				Object[] obj = (Object[])object;
				
				SimpleObject exception = new SimpleObject();
				exception.setId((Integer)obj[2]);
				exception.setName((String)obj[0]);
				exception.setDesc((String)obj[1]);
				
				returnList.add(exception);
			}
			
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getExceptionsDropDown()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getExceptionsDropDown()  ::   "+returnList);
		
		return returnList;
	}


	@Override
	public Integer validateTimeRuleCode(TimeRulesDetailsVo paramTime) {
		log.info("Entered in  validateTimeRuleCode()  ::   paramTime  = "+paramTime);
		
		Session session = sessionFactory.getCurrentSession();
		Integer rows = 0;
		
		try {				
			List queryList = session.createQuery("  FROM TimeRulesDetails WHERE customerId = "+paramTime.getCustomerId()
													+" AND companyId = "+paramTime.getCompanyId()
													+" AND timeRuleCode = '"+paramTime.getTimeRuleCode()+"' ").list();
			System.out.println("From Time Rule validation: "+queryList);
				
			if(queryList == null || queryList.size() == 0 ){
				rows = 0;
			}else{
				rows = queryList.size();
			}
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from validateTimeRuleCode()  ::   "+rows);
		}
		session.flush();
		log.info("Exiting from validateTimeRuleCode()  ::   "+rows);
		return rows;
	}



	@Override
	public List<TimeRulesDetailsVo> getTimeRulesListBySearch(TimeRulesDetailsVo paramTime) {
		log.info("Entered in  getTimeRulesListBySearch()  ::   paramTime  = "+paramTime);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<TimeRulesDetailsVo> returnList = new ArrayList<TimeRulesDetailsVo>();
		TimeRulesDetailsVo timeRule = new TimeRulesDetailsVo();
		
		String hqlQuery = "SELECT company.Company_Name, customer.Customer_Name, info.Time_Rules_Id,  "
							+ "	info.Time_Rules_Description, info.Transaction_Date, info.Is_Active, info.Time_Rules_Info_Id , "
							+ " rules.Time_Rule_Code, country.Country_Name"
							+ " FROM time_rules_details AS rules "
							+ " LEFT JOIN time_rules_details_info AS info ON info.Time_Rules_Id= rules.Time_Rule_Id "
							+ " LEFT JOIN customer_view AS customer  ON customer.Customer_Id = rules.Customer_Id  "
							+ " LEFT JOIN company_view AS Company  ON company.Customer_Id = rules.Customer_Id AND company.Company_Id = rules.Company_Id "
							+ " LEFT JOIN m_country as country ON country.Country_Id = rules.Country_Id"
								+ " WHERE 1 = 1 "
								+ " AND customer.Is_Active = 'Y' "
								+ " AND company.Is_Active = 'Y' "
								
								+ " AND  CONCAT(DATE_FORMAT(info.Transaction_Date, '%Y%m%d'), info.Sequence_Id) =  "
										+"	( "
										+"	SELECT MAX(CONCAT(DATE_FORMAT(info1.Transaction_Date, '%Y%m%d'), info1.Sequence_Id)) "
										+"	FROM time_rules_details_info AS info1 "
										+"	WHERE info.Time_Rules_Id = info1.Time_Rules_Id AND info.Company_Id = info1.company_Id AND  info.Customer_Id = info1.Customer_Id AND  info1.Transaction_Date <= CURRENT_DATE() "
										+"	) ";
			
		if(paramTime.getCustomerId() > 0){
			hqlQuery += "  AND  rules.Customer_Id = "+paramTime.getCustomerId();
		}
		
		if(paramTime.getCompanyId() > 0){
			hqlQuery += "  AND  rules.Company_Id = "+paramTime.getCompanyId();
		}
		
		if(paramTime.getTimeRuleCode() != null && !paramTime.getTimeRuleCode().isEmpty()){
			hqlQuery += " AND rules.Time_Rule_Code LIKE '"+paramTime.getTimeRuleCode()+"' ";
		}
		
		if(paramTime.getIsActive() != null && !paramTime.getIsActive().isEmpty()){
			hqlQuery += " AND info.Is_Active = '"+paramTime.getIsActive()+"' ";
		}
		
		
		hqlQuery += " GROUP BY info.Time_Rules_Id Order By rules.Time_Rule_Code asc";
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){
				for (Object customer : queryList) {
					Object[] obj = (Object[]) customer;
					timeRule = new TimeRulesDetailsVo();
					
					timeRule.setCompanyName((String)obj[0]);
					timeRule.setCustomerName((String)obj[1]);
					timeRule.setTimeRulesId((Integer)obj[2]);
					timeRule.setTimeRulesDescription((String)obj[3]);
					timeRule.setTransactionDate((Date)obj[4]);
					timeRule.setIsActive((obj[5]+"").equalsIgnoreCase("Y") ? "Active":"Inactive");
					timeRule.setTimeRulesInfoId((Integer)obj[6]);
					timeRule.setTimeRuleCode((String)obj[7]);
					timeRule.setCountryName((String)obj[8]);
					
					returnList.add(timeRule);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getTimeRulesListBySearch()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getTimeRulesListBySearch()  ::   "+returnList);
		return returnList;
		
	}



	@Override
	public List<TimeExceptionRulesVo> getTimeExceptionById(TimeExceptionRulesVo paramException) {
		log.info("Entered in  getExceptionById()  ::   exceptionId = "+paramException);
		
		Session session = sessionFactory.getCurrentSession();
		TimeExceptionRulesVo timeRule = new TimeExceptionRulesVo();
		List<TimeExceptionRulesVo> returnList = new ArrayList<TimeExceptionRulesVo>();
		List<TimeExceptionVo> exceptionVoList = new ArrayList<>();
		
		String  exceptionRules = " SELECT rules.Customer_Id, rules.Company_Id, rules.Country_Id, rules.Exception_Rules_Id, rules.Exception_Unique_Id, timeRules.Time_Rule_Code, "
							+ "	rules.Limit_For_Exception_Correction,  rules.Period_Type,"
							+ " rules.Transaction_Date, rules.Number_Of_Exceptions "
							+ " FROM time_exception_rules rules "							
							+ " LEFT JOIN time_rules_details AS timeRules ON rules.Time_Rule_Id = timeRules.Time_Rule_Id  "
							+ " LEFT JOIN time_rules_details_info AS info ON rules.Time_Rules_Info_Id = info.Time_Rules_Info_Id  "
							//+ " LEFT JOIN Customer_View AS time ON rules.Time_Rules_Info_Id = time.Time_Rules_Info_Id  "
							//+ " LEFT JOIN time_rules_details_info AS time ON rules.Time_Rules_Info_Id = time.Time_Rules_Info_Id  "
							+ " WHERE 1 = 1";
		
		if(paramException.getExceptionRulesId() != null && paramException.getExceptionRulesId() > 0){
			exceptionRules += " AND rules.Exception_Rules_Id = "+paramException.getExceptionRulesId();
		}else{
			exceptionRules += " AND  CONCAT(DATE_FORMAT(rules.Transaction_Date, '%Y%m%d'), rules.Exception_Sequence_Id) =  "
								+"	( "
								+"	SELECT MAX(CONCAT(DATE_FORMAT(rules1.Transaction_Date, '%Y%m%d'), rules1.Exception_Sequence_Id)) "
								+"	FROM time_exception_rules AS rules1 "
								+"	WHERE rules.Exception_Unique_Id = rules1.Exception_Unique_Id AND rules.Company_Id = rules1.company_Id AND  rules.Customer_Id = rules1.Customer_Id AND  rules1.Transaction_Date <= CURRENT_DATE() "
								+"	)  "
							+ " AND rules.Time_Rules_Info_Id = "+paramException.getTimeRulesInfoId();
		}
		try {				
			List timeRulesQuery = session.createSQLQuery(exceptionRules).list();
			
			for (Object rule : timeRulesQuery) {
				Object[] obj = (Object[]) rule;
				timeRule = new TimeExceptionRulesVo();
				timeRule.setCustomerId((Integer)obj[0]);
				timeRule.setCompanyId((Integer)obj[1]);
				timeRule.setCountryId((Integer)obj[2]);;
				timeRule.setExceptionRulesId((Integer)obj[3]);
				timeRule.setExceptionUniqueId((Integer)obj[4]);
				timeRule.setTimeRuleCode((String)obj[5]);
				timeRule.setLimitForExceptionCorrection((Integer)obj[6]);
				timeRule.setPeriodType((String)obj[7]);
				timeRule.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[8]));
				timeRule.setNumberOfExceptions((Integer)obj[9]);
				timeRule.setTimeRuleId(paramException.getTimeRuleId());
				timeRule.setTimeRulesInfoId(paramException.getTimeRulesInfoId());
				
				String  exceptions = " SELECT  time.Time_Exception_Id,  exception.Exception_Code, exception.Exception_Name, exception.Exception_Unique_Id  "
						+ " FROM time_exceptions time "							
						+ " LEFT JOIN define_exceptions AS exception ON time.Exception_Id = exception.Exception_Unique_Id "
								+ " AND  CONCAT(DATE_FORMAT(exception.Transaction_Date, '%Y%m%d'), exception.Exception_Sequence_Id) =  "
								+"	( "
								+"	SELECT MAX(CONCAT(DATE_FORMAT(exception1.Transaction_Date, '%Y%m%d'), exception1.Exception_Sequence_Id)) "
								+"	FROM define_exceptions AS exception1 "
								+"	WHERE exception.Exception_Unique_Id = exception1.Exception_Unique_Id AND exception.Company_Id = exception1.company_Id AND  exception.Customer_Id = exception1.Customer_Id AND  exception1.Transaction_Date <= CURRENT_DATE() "
								+"	)  "
						+ " WHERE time.Exception_Rules_Id = "+timeRule.getExceptionRulesId();
				
				List exceptionQuery = session.createSQLQuery(exceptions).list();
				
				for(Object exception: exceptionQuery){
					Object[] object = (Object[])exception;
					
					TimeExceptionVo  exceptionVo = new TimeExceptionVo();
					
					exceptionVo.setTimeExceptionId((Integer)object[0]);
					exceptionVo.setExceptionCode((String)object[1]);
					exceptionVo.setExceptionName((String)object[2]);
					exceptionVo.setExceptionId((Integer)object[3]);
					
					exceptionVoList.add(exceptionVo);
					System.out.println(exceptionVo);
			
				}
				
				timeRule.setExceptionsList(exceptionVoList);
				returnList.add(timeRule);
			}	
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from  getExceptionById()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from  getExceptionById()  ::   "+returnList);
		return returnList;
	}



	@Override
	public Integer saveTimeException(TimeExceptionRulesVo paramException) {
		log.info("Entered in saveException()  ::  paramException =  "+paramException);

		Session session = sessionFactory.getCurrentSession();
		Integer exceptionRulesId = 0;
		Integer timeRulesInfoId = paramException.getTimeRulesInfoId();
		BigInteger exceptionSequenceId = new BigInteger("0");	
		BigInteger uniqueId = new BigInteger("0");	
		
		TimeExceptionRules exceptionRules = new TimeExceptionRules();
		TimeExceptions exceptions = new TimeExceptions();
				
		try{
					
			exceptionRules.setCustomerDetails(new CustomerDetails(paramException.getCustomerId()));
			exceptionRules.setCompanyDetails(new CompanyDetails(paramException.getCompanyId()));
			exceptionRules.setCountryDetails(new MCountry(paramException.getCountryId()));
			exceptionRules.setTimeRulesDetails(new TimeRulesDetails(paramException.getTimeRuleId()));
			exceptionRules.setTimeRulesDetailsInfo(new TimeRulesDetailsInfo(paramException.getTimeRulesInfoId()));
			exceptionRules.setTransactionDate(paramException.getTransactionDate());
			exceptionRules.setLimitForExceptionCorrection(paramException.getLimitForExceptionCorrection());
			exceptionRules.setPeriodType(paramException.getPeriodType());
			exceptionRules.setNumberOfExceptions(paramException.getNumberOfExceptions());
			exceptionRules.setModifiedBy(paramException.getModifiedBy());
			exceptionRules.setModifiedDate(new Date());						
			
			if(paramException.getExceptionRulesId() != null && paramException.getExceptionRulesId() > 0){
				exceptionRules = (TimeExceptionRules) session.load(TimeExceptionRules.class, paramException.getExceptionRulesId());
				session.update(exceptionRules);
				exceptionRulesId  = paramException.getExceptionRulesId();
			}else{
				exceptionRules.setCreatedBy(paramException.getCreatedBy());
				exceptionRules.setCreatedDate(new Date());
				if(paramException.getExceptionUniqueId() != null && paramException.getExceptionUniqueId() > 0 ){
					exceptionSequenceId  = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Exception_Sequence_Id),0) FROM time_exception_rules"
							+ "  WHERE Exception_Unique_Id = "+paramException.getExceptionUniqueId() +" AND `Transaction_Date` = '"+DateHelper.convertDateToSQLString(paramException.getTransactionDate())+"' AND Customer_Id='"+paramException.getCustomerId()+"' AND Company_Id = '"+paramException.getCompanyId()+"'").list().get(0);
					log.info("Exception sequence Id : "+exceptionSequenceId);
					exceptionRules.setExceptionUniqueId( paramException.getExceptionUniqueId());
				}else{
					uniqueId = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Exception_Unique_Id),0) as id FROM time_exception_rules").list().get(0);
					exceptionRules.setExceptionUniqueId(uniqueId.intValue()+1 );	
				}	
				exceptionRules.setExceptionSequenceId(exceptionSequenceId.intValue()+1);		
				exceptionRulesId = (Integer) session.save(exceptionRules);
			}	
			session.clear();
			
			if(paramException.getExceptionsList() != null && paramException.getExceptionsList().size() > 0){
				if(paramException.getExceptionRulesId() != null && paramException.getExceptionRulesId() > 0){
					Query q = session.createSQLQuery("DELETE FROM time_exceptions WHERE Exception_Rules_Id = '"+paramException.getExceptionRulesId());
					q.executeUpdate();
				}
				for(TimeExceptionVo exceptionVo : paramException.getExceptionsList()){
					TimeExceptions exception = new TimeExceptions();
					exception.setCustomerDetails(new CustomerDetails(paramException.getCustomerId()));
					exception.setCompanyDetails(new CompanyDetails(paramException.getCompanyId()));
					exception.setCountryDetails(new MCountry(paramException.getCountryId()));
					exception.setTimeExceptionRules(new TimeExceptionRules(exceptionRulesId));
					exception.setExceptionId(exceptionVo.getExceptionId());
					exception.setModifiedBy(paramException.getModifiedBy());
					exception.setModifiedDate(new Date());
					exception.setCreatedBy(paramException.getCreatedBy());
					exception.setCreatedDate(new Date());
					
					session.save(exception);
				}
			}
			
		}catch (Exception e) {
			 if(!(session.getTransaction().wasRolledBack())){
				 session.getTransaction().rollback();
			 }
			 log.error("Error Occurred...   ", e);
			 log.info("Exiting from saveException()  ::  timeRulesInfoId =  "+timeRulesInfoId);
		}				
		log.info("Exiting from saveException()  ::  timeRulesInfoId =  "+timeRulesInfoId);
		return timeRulesInfoId;
	}



	@Override
	public List<SimpleObject> getTransactionDatesListForTimeException(Integer customerId, Integer companyId, Integer exceptionUniqueId) {
		log.info("Entered in getTransactionDatesListForTimeException()  ::   customerId = "+customerId+" , companyId = "+companyId+" , exceptionUniqueId = "+exceptionUniqueId);
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List divisionList = session.createSQLQuery("SELECT Exception_Rules_Id AS id ,CONCAT(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),' - ',Exception_Sequence_Id) AS name FROM time_exception_rules  WHERE Customer_Id = "+customerId+" AND Company_Id = "+companyId+" AND Exception_Unique_Id = "+exceptionUniqueId+" ORDER BY Transaction_Date, Exception_Rules_Id asc").list();
			for (Object transDates  : divisionList) {
				Object[] transaction = (Object[]) transDates;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)transaction[0]);
				object.setName(transaction[1]+"");
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getTransactionDatesListForTimeException()  ::   "+transactionList);
		}
		
		log.info("Exiting from getTransactionDatesListForTimeException()  ::   "+transactionList);
		return transactionList;
	}
	
}
