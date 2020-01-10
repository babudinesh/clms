package com.Ntranga.CLMS.Dao;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.DefineOverTimeControlDetailsInfoVo;
import com.Ntranga.CLMS.vo.OtRoundOffRulesVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WageRatesForOTVo;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.DefineOverTimeControlDetails;
import com.Ntranga.core.CLMS.entities.DefineOverTimeControlDetailsInfo;
import com.Ntranga.core.CLMS.entities.MRegistrationActs;
import com.Ntranga.core.CLMS.entities.OtRoundOffRules;
import com.Ntranga.core.CLMS.entities.WageRatesForOT;

import common.Logger;
@Transactional

@Repository("defineOverTimeControlDetailsDao")
public class DefineOverTimeControlDetailsDaoImpl implements DefineOverTimeControlDetailsDao  {

	private static Logger log = Logger.getLogger(DefineOverTimeControlDetailsDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;



	@Override
	public List<DefineOverTimeControlDetailsInfoVo> getOverTimeDetailsForGrid(DefineOverTimeControlDetailsInfoVo overTimeVo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<DefineOverTimeControlDetailsInfoVo> overTimeVos = new ArrayList<DefineOverTimeControlDetailsInfoVo>(); 
		try {
			String query = "SELECT `Define_OverTime_Details_InfoId`, `OverTime_Control_Name` ,`overTime_Control_Code`,`Company_Name`,customer_name, "
					+" CASE  WHEN info.Is_Active='Y' THEN 'Active' ELSE 'Inactive' END AS bSTATUS "
					+" FROM `define_overtime_control_details_info` info "
					+" INNER JOIN `define_overtime_control_details` dtls ON (info.`Define_OverTime_Details_Id` = dtls.`Define_OverTime_Details_Id`) "
					+" INNER JOIN `customer_details_info` cdi ON(info.customer_id =cdi.customer_id) " 
					+" INNER JOIN `company_details_info` com ON (com.company_id = info.company_id)  "
					+" WHERE "
					+"  CONCAT(DATE_FORMAT(info.`Transaction_Date`, '%Y%m%d'), LPAD(info.Sequence_Id, 2, '0')) = "  
					+" (  "
					+" SELECT MAX(CONCAT(DATE_FORMAT(info1.transaction_date, '%Y%m%d'), LPAD(info1.`Sequence_Id`, 2, '0'))) " 
					+" FROM define_overtime_control_details_info info1  "
					+" WHERE info.`Define_OverTime_Details_Id` = info1.`Define_OverTime_Details_Id` " 
					+" AND info1.transaction_date <= CURRENT_DATE() "   
					+" ) "
					+" AND CONCAT(DATE_FORMAT(cdi.transaction_date, '%Y%m%d'), LPAD(cdi.`Customer_Sequence_Id`, 2, '0')) = "  
					+" (  "
					+" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.`Customer_Sequence_Id`, 2, '0'))) " 
					+" FROM customer_details_info vdi1 " 
					+" WHERE cdi.`Customer_Info_Id` = vdi1.`Customer_Info_Id` "   
					+" AND vdi1.transaction_date <= CURRENT_DATE() "   
					+" ) " 
					+" AND   "
					+" CONCAT(DATE_FORMAT(com.transaction_date, '%Y%m%d'), LPAD(com.Company_Sequence_Id, 2, '0')) = "  
					+" ( " 
					+" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.`Company_Sequence_Id`, 2, '0'))) " 
					+" FROM company_details_info vdi1 " 
					+" WHERE com.`Company_Info_Id` = vdi1.  `Company_Info_Id` " 
					+" AND vdi1.transaction_date <= CURRENT_DATE() "   
					+" )  ";
					
			if(overTimeVo != null && overTimeVo.getCustomerDetailsId() != null && overTimeVo.getCustomerDetailsId() > 0){
				query+= " and info.`Customer_Id`= "+overTimeVo.getCustomerDetailsId(); 
			}
			if(overTimeVo != null && overTimeVo.getCompanyDetailsId() != null && overTimeVo.getCompanyDetailsId() > 0){
				query+= " AND info.`Company_Id` = "+overTimeVo.getCompanyDetailsId();
			}
			if(overTimeVo != null && overTimeVo.getDefineOverTimeDetailsInfoId() != null && overTimeVo.getDefineOverTimeDetailsInfoId() > 0){
				query+= " AND info.`Define_OverTime_Details_InfoId` = "+overTimeVo.getDefineOverTimeDetailsInfoId();
			}
			if(overTimeVo != null && overTimeVo.getIsActive()!= null && !overTimeVo.getIsActive().isEmpty()){
				query+= " AND info.`Is_Active` = '"+overTimeVo.getIsActive()+"'";
			}
			query += " GROUP BY info.`Customer_Id` ORDER BY OverTime_Control_Code asc ";
			System.out.println(query);
					List tempList =  session.createSQLQuery(query).list();
		for(Object object : tempList){	
			Object[] obj = (Object[]) object;
			DefineOverTimeControlDetailsInfoVo controlDetailsInfoVo = new DefineOverTimeControlDetailsInfoVo();
			controlDetailsInfoVo.setDefineOverTimeDetailsInfoId((Integer)obj[0]);
			controlDetailsInfoVo.setOverTimeControlCode(obj[2]+"");
			controlDetailsInfoVo.setCustomerName(obj[4]+"");
			controlDetailsInfoVo.setCompanyName(obj[3]+"");
			controlDetailsInfoVo.setOverTimeControlName(obj[1]+"");			
			controlDetailsInfoVo.setIsActive( (obj[5]+""));
			overTimeVos.add(controlDetailsInfoVo);
		}
	} catch (Exception e) {
		log.error("Error Occured ",e);
	}
	return overTimeVos;				
	}



	@Override
	public Integer saveOrUpdateOverTime(DefineOverTimeControlDetailsInfoVo overTimeVo) {
		// TODO Auto-generated method stub
		DefineOverTimeControlDetailsInfo controlDetailsInfo = new DefineOverTimeControlDetailsInfo();
		DefineOverTimeControlDetails controlDetails = new DefineOverTimeControlDetails();
		Integer detailsId = 0;
		Integer infoId = 0;
		try{
		if(overTimeVo != null && overTimeVo.getDefineOverTimeControlDetailsId() != null && overTimeVo.getDefineOverTimeControlDetailsId() > 0){
			controlDetails = (DefineOverTimeControlDetails) sessionFactory.getCurrentSession().load(DefineOverTimeControlDetails.class, overTimeVo.getDefineOverTimeControlDetailsId());
			detailsId = overTimeVo.getDefineOverTimeControlDetailsId();
		}
		controlDetails.setOverTimeControlCode(overTimeVo.getOverTimeControlCode());
		controlDetails.setIsActive(overTimeVo.getIsActive());
		controlDetails.setCustomerDetails(new CustomerDetails(overTimeVo.getCustomerDetailsId()));
		controlDetails.setCompanyDetails(new CompanyDetails(overTimeVo.getCompanyDetailsId()));
		controlDetails.setModifiedBy(overTimeVo.getModifiedBy());
		controlDetails.setModifiedDate(new Date());		
		if(overTimeVo != null && overTimeVo.getDefineOverTimeControlDetailsId() != null && overTimeVo.getDefineOverTimeControlDetailsId() > 0){
			sessionFactory.getCurrentSession().update(controlDetails);			
		}else{
			controlDetails.setCreatedBy(overTimeVo.getCreatedBy());
			controlDetails.setCreatedDate(new Date());
			detailsId = (Integer)sessionFactory.getCurrentSession().save(controlDetails);
		}
		
		
		
		if(overTimeVo != null && overTimeVo.getDefineOverTimeDetailsInfoId() != null && overTimeVo.getDefineOverTimeDetailsInfoId() > 0){
			controlDetailsInfo = (DefineOverTimeControlDetailsInfo) sessionFactory.getCurrentSession().load(DefineOverTimeControlDetailsInfo.class, overTimeVo.getDefineOverTimeDetailsInfoId());
			infoId = overTimeVo.getDefineOverTimeDetailsInfoId();
		}
		controlDetailsInfo.setCustomerDetails(new CustomerDetails(overTimeVo.getCustomerDetailsId()));
		controlDetailsInfo.setCompanyDetails(new CompanyDetails(overTimeVo.getCompanyDetailsId()));
		controlDetailsInfo.setDefineOverTimeDetailsInfoId(overTimeVo.getDefineOverTimeDetailsInfoId());		
		controlDetailsInfo.setDefineOverTimeControlDetails(new DefineOverTimeControlDetails(detailsId));
		controlDetailsInfo.setCountry(overTimeVo.getCountry());
		controlDetailsInfo.setTransactionDate(overTimeVo.getTransactionDate());
		controlDetailsInfo.setIsActive(overTimeVo.getIsActive());
		controlDetailsInfo.setOverTimeControlName(overTimeVo.getOverTimeControlName());
		controlDetailsInfo.setMinimumOTHoursPerDay(overTimeVo.getMinimumOTHoursPerDay());
		controlDetailsInfo.setConsiderOTHours(overTimeVo.getConsiderOTHours());
		controlDetailsInfo.setOvertimeDurationDay(overTimeVo.getOvertimeDurationDay());
		controlDetailsInfo.setOvertimeDurationWeek(overTimeVo.getOvertimeDurationWeek());
		controlDetailsInfo.setOvertimeDurationMonth(overTimeVo.getOvertimeDurationMonth());
		controlDetailsInfo.setOvertimeDurationQuarter(overTimeVo.getOvertimeDurationQuarter());
		controlDetailsInfo.setOvertimeDurationYear(overTimeVo.getOvertimeDurationYear());
		if(overTimeVo.getAllowOTOnWeeklyOff() != null )
		controlDetailsInfo.setAllowOTOnWeeklyOff(overTimeVo.getAllowOTOnWeeklyOff() == true ? "Y" : "N");
		if(overTimeVo.getAllowOTOnPublicHoliday() != null )
		controlDetailsInfo.setAllowOTOnPublicHoliday(overTimeVo.getAllowOTOnPublicHoliday()== true ? "Y" : "N");
		
		controlDetailsInfo.setModifiedBy(overTimeVo.getModifiedBy());
		controlDetailsInfo.setModifiedDate(new Date());
		if(overTimeVo != null && overTimeVo.getDefineOverTimeDetailsInfoId() != null && overTimeVo.getDefineOverTimeDetailsInfoId() > 0){
			sessionFactory.getCurrentSession().update(controlDetailsInfo);
			sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM `ot_round_off_rules`  WHERE `Define_OverTime_Details_InfoId`="+infoId).executeUpdate();
			sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM `wage_rates_for_ot` WHERE `Define_OverTime_Details_InfoId` = "+infoId).executeUpdate();
			
			
		}else{
			BigInteger sequenceId = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Sequence_Id),0) FROM  `define_overtime_control_details_info` WHERE `Customer_Id`="+overTimeVo.getCustomerDetailsId()+" and `Company_id`="+overTimeVo.getCompanyDetailsId()+" and  `Transaction_Date`='"+DateHelper.convertDateToSQLString(overTimeVo.getTransactionDate())+"'").list().get(0);
			controlDetailsInfo.setSequenceId(sequenceId.intValue()+1);	
			controlDetailsInfo.setCreatedBy(overTimeVo.getCreatedBy());
			controlDetailsInfo.setCreatedDate(new Date());
			infoId = (Integer)sessionFactory.getCurrentSession().save(controlDetailsInfo);
		}
		
		
		for(WageRatesForOTVo otList : overTimeVo.getWageRatesForOT()){
			WageRatesForOT ratesForOT = new WageRatesForOT();
			ratesForOT.setDefineOverTimeControlDetailsInfo(new DefineOverTimeControlDetailsInfo(infoId));
			ratesForOT.setOtRates(otList.getOtRates());
			ratesForOT.setRegularRates(otList.getRegularRates());
			ratesForOT.setWorkedOn(otList.getWorkedOn());
			ratesForOT.setCreatedBy(overTimeVo.getCreatedBy());
			ratesForOT.setCreatedDate(new Date());
			ratesForOT.setModifiedBy(overTimeVo.getModifiedBy());
			ratesForOT.setModifiedDate(new Date());
			sessionFactory.getCurrentSession().save(ratesForOT);
		}
		
		for(OtRoundOffRulesVo rulesVo : overTimeVo.getOtRoundOffRules()){
			OtRoundOffRules offRules = new OtRoundOffRules();
			offRules.setDefineOverTimeControlDetailsInfo(new DefineOverTimeControlDetailsInfo(infoId));
			offRules.setFromMinutes(rulesVo.getFromMinutes());
			offRules.setToMinutes(rulesVo.getToMinutes());
			offRules.setRoundOffToMinutes(rulesVo.getRoundOffToMinutes());
			offRules.setCreatedBy(overTimeVo.getCreatedBy());
			offRules.setCreatedDate(new Date());
			offRules.setModifiedBy(overTimeVo.getModifiedBy());
			offRules.setModifiedDate(new Date());
			sessionFactory.getCurrentSession().save(offRules);
		}
				
		sessionFactory.getCurrentSession().flush();
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return infoId;
	}



	@Override
	public List<DefineOverTimeControlDetailsInfoVo> getDefineOverTimeDetailsByInfoId(DefineOverTimeControlDetailsInfoVo overTimeVo) {
		// TODO Auto-generated method stub
		DefineOverTimeControlDetailsInfo controlDetailsInfo = new DefineOverTimeControlDetailsInfo();
		List<DefineOverTimeControlDetailsInfoVo> controlDetailsInfos = new ArrayList<DefineOverTimeControlDetailsInfoVo>();
		DefineOverTimeControlDetailsInfoVo controlDetailsInfoVo = new DefineOverTimeControlDetailsInfoVo();
		try{
		if(overTimeVo != null && overTimeVo.getDefineOverTimeDetailsInfoId() != null && overTimeVo.getDefineOverTimeDetailsInfoId() > 0){
			controlDetailsInfo = (DefineOverTimeControlDetailsInfo) sessionFactory.getCurrentSession().load(DefineOverTimeControlDetailsInfo.class, overTimeVo.getDefineOverTimeDetailsInfoId());			
		}
		controlDetailsInfoVo.setDefineOverTimeDetailsInfoId(controlDetailsInfo.getDefineOverTimeDetailsInfoId());
		controlDetailsInfoVo.setCustomerDetailsId(controlDetailsInfo.getCustomerDetails().getCustomerId());
		controlDetailsInfoVo.setCompanyDetailsId(controlDetailsInfo.getCompanyDetails().getCompanyId());
		controlDetailsInfoVo.setDefineOverTimeControlDetailsId(controlDetailsInfo.getDefineOverTimeControlDetails().getDefineOverTimeDetailsId());
		controlDetailsInfoVo.setCountry(controlDetailsInfo.getCountry());
		controlDetailsInfoVo.setTransactionDate(controlDetailsInfo.getTransactionDate());
		controlDetailsInfoVo.setIsActive(controlDetailsInfo.getIsActive());
		controlDetailsInfoVo.setOverTimeControlCode(controlDetailsInfo.getDefineOverTimeControlDetails().getOverTimeControlCode());
		controlDetailsInfoVo.setOverTimeControlName(controlDetailsInfo.getOverTimeControlName());
		controlDetailsInfoVo.setMinimumOTHoursPerDay(controlDetailsInfo.getMinimumOTHoursPerDay());
		controlDetailsInfoVo.setConsiderOTHours(controlDetailsInfo.getConsiderOTHours());
		controlDetailsInfoVo.setOvertimeDurationDay(controlDetailsInfo.getOvertimeDurationDay());
		controlDetailsInfoVo.setOvertimeDurationWeek(controlDetailsInfo.getOvertimeDurationWeek());
		controlDetailsInfoVo.setOvertimeDurationMonth(controlDetailsInfo.getOvertimeDurationMonth());
		controlDetailsInfoVo.setOvertimeDurationQuarter(controlDetailsInfo.getOvertimeDurationQuarter());
		controlDetailsInfoVo.setOvertimeDurationYear(controlDetailsInfo.getOvertimeDurationYear());
		if(controlDetailsInfo.getAllowOTOnWeeklyOff() != null )
		controlDetailsInfoVo.setAllowOTOnWeeklyOff(controlDetailsInfo.getAllowOTOnWeeklyOff().equalsIgnoreCase("Y")? true : false );
		if(controlDetailsInfo.getAllowOTOnPublicHoliday() != null )
		controlDetailsInfoVo.setAllowOTOnPublicHoliday(controlDetailsInfo.getAllowOTOnPublicHoliday().equalsIgnoreCase("Y")? true : false );
		
		List<OtRoundOffRules> roundOffRules = sessionFactory.getCurrentSession().createQuery(" from OtRoundOffRules where defineOverTimeControlDetailsInfo="+controlDetailsInfo.getDefineOverTimeDetailsInfoId()).list();
		for(OtRoundOffRules offRules : roundOffRules){
			OtRoundOffRulesVo offRulesVo = new OtRoundOffRulesVo();
			offRulesVo.setFromMinutes(offRules.getFromMinutes());
			offRulesVo.setToMinutes(offRules.getToMinutes());
			offRulesVo.setRoundOffToMinutes(offRules.getRoundOffToMinutes());
			controlDetailsInfoVo.getOtRoundOffRules().add(offRulesVo);	
		}
		List<WageRatesForOT> wageRatesForOT = sessionFactory.getCurrentSession().createQuery(" from WageRatesForOT where defineOverTimeControlDetailsInfo="+controlDetailsInfo.getDefineOverTimeDetailsInfoId()).list();
		for(WageRatesForOT wageRates : wageRatesForOT){
			WageRatesForOTVo ratesForOTVo = new WageRatesForOTVo();
			ratesForOTVo.setWorkedOn(wageRates.getWorkedOn());
			ratesForOTVo.setRegularRates(wageRates.getRegularRates());
			ratesForOTVo.setOtRates(wageRates.getOtRates());
			controlDetailsInfoVo.getWageRatesForOT().add(ratesForOTVo);	
		}
		
		
		controlDetailsInfos.add(controlDetailsInfoVo);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return controlDetailsInfos;
	}

	@Override
	public List<SimpleObject> getOverTimeNamesByCompanyId(Integer customerDetailsId, Integer companyDetailsId) {
		// TODO Auto-generated method stub
		List<SimpleObject> list = new ArrayList<SimpleObject>();
		try{
			List tempList = sessionFactory.getCurrentSession().createSQLQuery(" SELECT details.`Define_OverTime_Details_Id`, `OverTime_Control_Name`, OverTime_Control_Code FROM `define_overtime_control_details_info` info "
					+ " LEFT JOIN define_overtime_control_details details ON details.`Define_OverTime_Details_Id` = info.`Define_OverTime_Details_Id` "
					+ " WHERE info.Customer_Id= "+customerDetailsId+" AND info.Company_Id ="+companyDetailsId
					+" AND CONCAT(DATE_FORMAT(info.`Transaction_Date`, '%Y%m%d'), LPAD(info.Sequence_Id, 2, '0')) =   "
					+" ( "
					+" SELECT MAX(CONCAT(DATE_FORMAT(info1.transaction_date, '%Y%m%d'), LPAD(info1.`Sequence_Id`, 2, '0'))) "
					+" FROM define_overtime_control_details_info info1 "
					+" WHERE info.`Define_OverTime_Details_Id` = info1.`Define_OverTime_Details_Id` " 
					+" AND info1.transaction_date <= CURRENT_DATE()   "
					+" ) ORDER BY OverTime_Control_Name asc").list();
			for(Object o : tempList){
				Object[] obj = (Object[]) o;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)obj[0]);
				object.setName(obj[1]+"");
				object.setDesc((String)obj[2]);
				list.add(object);
			}
			
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		
		
		return list;
	}



	@Override
	public List<SimpleObject> getTransactionDatesListForOverTimeHistory(DefineOverTimeControlDetailsInfoVo overTimeVo) {
		// TODO Auto-generated method stub
		List<SimpleObject> list = new ArrayList<SimpleObject>();
		try{
			List tempList = sessionFactory.getCurrentSession().createSQLQuery("SELECT `Define_OverTime_Details_InfoId`, CONCAT(DATE_FORMAT(`Transaction_Date`,'%d/%m/%Y'),' - ',`sequence_Id`) AS cname  FROM `define_overtime_control_details_info` WHERE `Customer_Id` ="+overTimeVo.getCustomerDetailsId()+"  AND  `Company_Id` ="+overTimeVo.getCompanyDetailsId()).list();
			for(Object o : tempList){
				Object[] obj = (Object[]) o;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)obj[0]);
				object.setName(obj[1]+"");
				list.add(object);
			}
			
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		
		
		return list;
	}

	
	
}
