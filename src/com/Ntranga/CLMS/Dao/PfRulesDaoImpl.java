package com.Ntranga.CLMS.Dao;


import java.math.BigInteger;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.TimeRuleBufferTimeVo;
import com.Ntranga.CLMS.vo.TimeRuleWorkDayStatusVo;
import com.Ntranga.CLMS.vo.TimeRulesDetailsVo;
import com.Ntranga.CLMS.vo.WageDeductionVo;
import com.Ntranga.CLMS.vo.WageScaleDetailsVo;
import com.Ntranga.CLMS.vo.WageScaleWorkSkillsVo;
import com.Ntranga.CLMS.vo.WorkerDetailsVo;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.DefinePfrules;
import com.Ntranga.core.CLMS.entities.TimeRuleBufferTime;
import com.Ntranga.core.CLMS.entities.TimeRuleWorkDayStatus;
import com.Ntranga.core.CLMS.entities.TimeRulesDetails;
import com.Ntranga.core.CLMS.entities.TimeRulesDetailsInfo;
import com.Ntranga.core.CLMS.entities.WageDeduction;
import com.Ntranga.core.CLMS.entities.WageScaleDetails;
import com.Ntranga.core.CLMS.entities.WageScaleWorkSkills;

import common.Logger;
@Transactional

@Repository("pfRulesDao")
public class PfRulesDaoImpl implements PfRulesDao  {

	private static Logger log = Logger.getLogger(PfRulesDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	
		
	@Override
	public Integer saveOrUpdateWageDeductionDetails(WageDeductionVo wageDeductionVo) {
		Session session = sessionFactory.getCurrentSession();
		Integer pfrulesId =0;
		System.out.println(wageDeductionVo);
		try {
			
			DefinePfrules trd = new DefinePfrules();
			
			trd.setCustomerDetails(new CustomerDetails(wageDeductionVo.getCustomerId()));
			trd.setCompanyDetails(new CompanyDetails(wageDeductionVo.getCompanyId()));			
			trd.setCountryId(wageDeductionVo.getCountryId());			
			trd.setTransactionDate(wageDeductionVo.getTransactionDate());
			trd.setIsActive(wageDeductionVo.getIsActive());
			trd.setCreatedBy(wageDeductionVo.getCreatedBy());
			trd.setModifiedBy(wageDeductionVo.getModifiedBy());
			trd.setFixedAmountOrPercentage(wageDeductionVo.getFixedAmountOrPercentage());
			trd.setEmployerAmount(wageDeductionVo.getEmployerAmount());
			trd.setEmployerAmountType(wageDeductionVo.getEmployerAmountType()+"");
			trd.setEmployeeAmount(wageDeductionVo.getEmployeeAmount());
			trd.setEmployeeAmountType(wageDeductionVo.getEmployeeAmountType()+"");
			trd.setEmployerPercentage(wageDeductionVo.getEmployerPercentage());
			trd.setEmployerPercentageType(wageDeductionVo.getEmployerPercentageType());
			trd.setEmployeePercentage(wageDeductionVo.getEmployeePercentage());
			trd.setEmployeePercentageType(wageDeductionVo.getEmployeePercentageType());
			trd.setModifiedDate(new Date());			
			BigInteger SequenceId ;
			if(wageDeductionVo != null &&  wageDeductionVo.getDeductionId() > 0){
				trd.setCreatedDate(wageDeductionVo.getCreatedDate());
				trd.setSequenceId(wageDeductionVo.getSequenceId());
				trd.setPfrulesId(wageDeductionVo.getDeductionId());				
				session.update(trd);
				pfrulesId = trd.getPfrulesId();
			}else  {
				SequenceId = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Sequence_Id),0) FROM Define_Pfrules b  WHERE  b.Customer_Id = '"+wageDeductionVo.getCustomerId()+"' and  b.Company_Id = '"+wageDeductionVo.getCompanyId()+"'  AND  b.`Transaction_Date` = '"+DateHelper.convertDateToSQLString(wageDeductionVo.getTransactionDate())+"'").list().get(0);
				if(SequenceId.intValue() >0){
					trd.setSequenceId(SequenceId.intValue()+1);					
				}else{
					trd.setSequenceId(1);				
				}
				trd.setCreatedDate(new Date());
				pfrulesId = (Integer)session.save(trd);			
			}
					
					
		}catch(Exception e){			
			log.error("Error Occured ",e);
		}
		return pfrulesId;
	}


	
	public List<WageDeductionVo> getDetailsBywageDeductionId(Integer customerId,Integer companyId,Integer pfrulesId) {

		List<WageDeductionVo> wageScaleDetailsList = new ArrayList<WageDeductionVo>();
		Session session = sessionFactory.getCurrentSession();
		try {

			String q = " SELECT pfrules_Id,Transaction_Date,Sequence_Id,Customer_Id,Company_Id,Country_Id,  "
							+ "	 Fixed_Amount_Or_Percentage,Employer_Amount,Employer_Amount_Type,Employee_Amount,Employee_Amount_Type,Employer_Percentage,  "
							+ "		 Employer_Percentage_Type,Employee_Percentage,Employee_Percentage_Type,Is_Active,Created_Date,     "
							+ "		 Created_By,Modified_Date,Modified_By	  FROM define_pfrules  wdi  "
							+ "		WHERE  " ;
			
				if (customerId != null && customerId > 0 && companyId != null && companyId > 0) {
					q = q + " CONCAT(DATE_FORMAT(wdi.Transaction_Date,'%Y%m%d'),LPAD(wdi.Sequence_id,2,'0')) = (SELECT  "
							+ "		MAX(CONCAT(DATE_FORMAT(vdi1.Transaction_Date,'%Y%m%d'),LPAD(vdi1.Sequence_id,2,'0')))  "
							+ "		FROM define_pfrules vdi1  "
							+ "		WHERE (wdi.customer_id = vdi1.customer_id AND wdi.company_id = vdi1.company_id)  "
							+ "		AND (vdi1.Transaction_Date <= CURDATE())) and wdi.customer_id =" + customerId
							+ " and wdi.company_id = " + companyId + " ";
	
				}
							
				
							if(pfrulesId != null && pfrulesId > 0){
								q =q + "  wdi.pfrules_Id ="+pfrulesId+"";
							}
							
							
			List tempList = session.createSQLQuery(q).list();
			for (Object o : tempList) {
				Object[] obj = (Object[]) o;
				WageDeductionVo deductionVo = new WageDeductionVo();
				deductionVo.setDeductionId((Integer) obj[0]);				
				deductionVo.setTransactionDate((Date) obj[1]);
				deductionVo.setSequenceId((Integer) obj[2]);
				deductionVo.setCustomerId((Integer) obj[3]);
				deductionVo.setCompanyId((Integer) obj[4]);
				deductionVo.setCountryId((Integer) obj[5]);						
				deductionVo.setFixedAmountOrPercentage((String) obj[6]);
				deductionVo.setEmployerAmount((String) obj[7]);
				if(obj[8] != null && !(obj[8].toString().isEmpty())){
				deductionVo.setEmployerAmountType(Integer.parseInt(obj[8]+""));
				}
				
				deductionVo.setEmployeeAmount((String) obj[9]);
				if(obj[10] != null && !obj[10].toString().isEmpty()){
				deductionVo.setEmployeeAmountType(Integer.parseInt(obj[10]+""));
				}
				deductionVo.setEmployerPercentage((String) obj[11]);
				deductionVo.setEmployerPercentageType((String) obj[12]);
				deductionVo.setEmployeePercentage((String) obj[13]);
				deductionVo.setEmployeePercentageType((String) obj[14]);				
				deductionVo.setIsActive((obj[15]).toString());
				deductionVo.setCreatedDate((Date) obj[16]);
				deductionVo.setCreatedBy((Integer) obj[17]);
				deductionVo.setModifiedDate((Date) obj[18]);
				deductionVo.setModifiedBy((Integer) obj[19]);				
				wageScaleDetailsList.add(deductionVo);

			}

		} catch (Exception e) {
			log.error("Error Occured ",e);
		}

		return wageScaleDetailsList;
	}
	
	
	
	
	
	
	@Override
	public List<SimpleObject> getTransactionDatesListForEditingWageDeduction(Integer customerId,Integer companyId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List rulesList = session.createSQLQuery("select pfrules_Id,concat(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),'-',Sequence_Id) from define_pfrules  where customer_id = '"+customerId+"' and company_id = '"+companyId+"'  and  Is_Active = 'Y'  order by Transaction_Date ,Sequence_Id " ).list();
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
	
	
	
	
	
}
