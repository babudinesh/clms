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
import com.Ntranga.core.CLMS.entities.TimeRuleBufferTime;
import com.Ntranga.core.CLMS.entities.TimeRuleWorkDayStatus;
import com.Ntranga.core.CLMS.entities.TimeRulesDetails;
import com.Ntranga.core.CLMS.entities.TimeRulesDetailsInfo;
import com.Ntranga.core.CLMS.entities.WageDeduction;
import com.Ntranga.core.CLMS.entities.WageScaleDetails;
import com.Ntranga.core.CLMS.entities.WageScaleWorkSkills;

import common.Logger;
@Transactional

@Repository("wageDeductionDao")
public class WageDeductionDaoImpl implements WageDeductionDao  {

	private static Logger log = Logger.getLogger(WageDeductionDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	
	
	@Override
	public List<WageDeductionVo> wageDeductionGridDetails(String customerId,String companyId,String Location,String status,String deductionCode) {
		Session session = sessionFactory.getCurrentSession();
		List<WageDeductionVo> wageDetailsList = new ArrayList<WageDeductionVo>();
		System.out.println("customerIdasdf::"+customerId+"::companyId::"+companyId+"::Location::"+Location+":wageScaleCode:"+deductionCode);
		try {
			
			String q = "SELECT DISTINCT cdi.customer_name, com.company_name,  wd.Transaction_Date, "
					+ " CASE wd.is_active WHEN 'Y' THEN 'Active' ELSE 'In-Active' END AS STATUS,  "
					+ " ldi.Location_Name,wd.Deduction_Code, wd.Deduction_Name,wd.Deduction_Id  "
					+ "   FROM wage_deduction wd   "
					+ "   INNER JOIN customer_details_info cdi ON(wd.customer_id =cdi.customer_id)   "
					+ "   INNER JOIN company_details_info com ON (com.company_id = wd.company_id)   "
					+ "   INNER JOIN location_details_info ldi ON (ldi.Location_Id = wd.location_Id)  "
					+ "  WHERE  CONCAT(DATE_FORMAT(wd.transaction_date, '%Y%m%d'), LPAD(wd.Sequence_Id, 2, '0')) =    "
					+ "   (   "
					+ "   SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id, 2, '0')))   "
					+ "   FROM wage_deduction vdi1   " + "   WHERE  wd.Deduction_Code = vdi1.Deduction_Code       "
					+ "    AND vdi1.transaction_date <= CURRENT_DATE()     " + "   )   " + "   AND    "
					+ "   CONCAT(DATE_FORMAT(cdi.transaction_date, '%Y%m%d'), LPAD(cdi.Customer_Sequence_Id, 2, '0')) =    "
					+ "   (   "
					+ "   SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Customer_Sequence_Id, 2, '0')))   "
					+ "   FROM customer_details_info vdi1   " + "   WHERE cdi.customer_id = vdi1.customer_id     "
					+ "    AND vdi1.transaction_date <= CURRENT_DATE()     " + "   )   " + "   AND    "
					+ "   CONCAT(DATE_FORMAT(com.transaction_date, '%Y%m%d'), LPAD(com.Company_Sequence_Id, 2, '0')) =    "
					+ "   (   "
					+ "   SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Company_Sequence_Id, 2, '0')))   "
					+ "   FROM company_details_info vdi1   " + "   WHERE com.company_id = vdi1.  company_id   "
					+ "    AND vdi1.transaction_date <= CURRENT_DATE()     " + "  )   " + "  AND    "
					+ "   CONCAT(DATE_FORMAT(ldi.transaction_date, '%Y%m%d'), LPAD(ldi.Location_Sequence_Id, 2, '0')) =    "
					+ "   (   "
					+ "   SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Location_Sequence_Id, 2, '0')))   "
					+ "  FROM location_details_info vdi1   " + "   WHERE ldi.location_Id = vdi1.location_Id   "
					+ "   AND vdi1.transaction_date <= CURRENT_DATE()) ";
			 
			 			
			 			
					 if(customerId != null && !customerId.isEmpty()){						
						 q +=" and wd.Customer_Id = '"+customerId+"'";				 
					 } 
					 if(companyId != null && !companyId.isEmpty()  ){						 
						 q +=" and wd.Company_Id = '"+companyId+"' ";	
					 } 
					 if(Location != null && !Location.isEmpty() ){						 
						 q +=" and wd.Location_Id = '"+Location+"'";	
					 }
					 
					 if(status != null && !status.isEmpty()){
						 q +=" and wd.is_active = '"+status+"'";
					 }
					 
					 if(deductionCode != null && !deductionCode.isEmpty()){
						 q +=" and wd.Deduction_Code = '"+deductionCode+"'";
					 }
					 
					 
					 
					 q += " ORDER BY Deduction_Name asc";
								 
			
			 List tempList = session.createSQLQuery(q).list();
			
			for(Object o : tempList){
				Object[] obj = (Object[]) o;
				WageDeductionVo workerVo = new WageDeductionVo();
				workerVo.setCustomerName(obj[0]+"");
				workerVo.setCompanyName(obj[1]+"");
				workerVo.setTransactionDate((Date)obj[2]);
				workerVo.setIsActive(obj[3]+"");
				workerVo.setLocationName(obj[4]+"");
				workerVo.setDeductionCode(obj[5]+"");
				workerVo.setDeductionName(obj[6]+"");							
				workerVo.setDeductionId((Integer)obj[7]);
				wageDetailsList.add(workerVo);
			}						
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}
		
		
		return wageDetailsList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public Integer saveOrUpdateWageDeductionDetails(WageDeductionVo wageDeductionVo) {
		Session session = sessionFactory.getCurrentSession();
		Integer deductionId =0;
		System.out.println(wageDeductionVo);
		try {
			
			WageDeduction trd = new WageDeduction();
			
			trd.setCustomerDetails(new CustomerDetails(wageDeductionVo.getCustomerId()));
			trd.setCompanyDetails(new CompanyDetails(wageDeductionVo.getCompanyId()));
			trd.setLocationId(wageDeductionVo.getLocationId());
			trd.setCountryId(wageDeductionVo.getCountryId());
			trd.setDeductionCode(wageDeductionVo.getDeductionCode());
			trd.setDeductionName(wageDeductionVo.getDeductionName());
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
				trd.setDeductionId(wageDeductionVo.getDeductionId());				
				session.update(trd);
				deductionId = trd.getDeductionId();
			}else  {
				SequenceId = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Sequence_Id),0) FROM wage_deduction b  WHERE  b.Deduction_Code = '"+wageDeductionVo.getDeductionCode()+"' AND  b.`Transaction_Date` = '"+DateHelper.convertDateToSQLString(wageDeductionVo.getTransactionDate())+"'").list().get(0);
				if(SequenceId.intValue() >0){
					trd.setSequenceId(SequenceId.intValue()+1);					
				}else{
					trd.setSequenceId(1);				
				}
				trd.setCreatedDate(new Date());
				deductionId = (Integer)session.save(trd);			
			}
					
					
		}catch(Exception e){			
			log.error("Error Occured ",e);
		}
		return deductionId;
	}


	
	public List<WageDeductionVo> getDetailsBywageDeductionId(Integer deductionId) {

		List<WageDeductionVo> wageScaleDetailsList = new ArrayList<WageDeductionVo>();
		Session session = sessionFactory.getCurrentSession();
		try {

			List tempList = session.createSQLQuery("SELECT Deduction_Id,Deduction_Code,Deduction_Name,Transaction_Date,Sequence_Id,Customer_Id,Company_Id,Country_Id,Location_Id, "+ 
													" Fixed_Amount_Or_Percentage,Employer_Amount,Employer_Amount_Type,Employee_Amount,Employee_Amount_Type,Employer_Percentage, "+   
													" Employer_Percentage_Type,Employee_Percentage,Employee_Percentage_Type,Is_Active,Created_Date,  "+  
													" Created_By,Modified_Date,Modified_By	  "+ 
													" FROM  "+ 
													" wage_deduction WHERE Deduction_Id = " + deductionId).list();
			for (Object o : tempList) {
				Object[] obj = (Object[]) o;
				WageDeductionVo deductionVo = new WageDeductionVo();
				deductionVo.setDeductionId((Integer) obj[0]);
				deductionVo.setDeductionCode((String) obj[1]);
				deductionVo.setDeductionName((String) obj[2]);
				deductionVo.setTransactionDate((Date) obj[3]);
				deductionVo.setSequenceId((Integer) obj[4]);
				deductionVo.setCustomerId((Integer) obj[5]);
				deductionVo.setCompanyId((Integer) obj[6]);
				deductionVo.setCountryId((Integer) obj[7]);
				deductionVo.setLocationId((Integer) obj[8]);				
				deductionVo.setFixedAmountOrPercentage((String) obj[9]);
				deductionVo.setEmployerAmount((String) obj[10]);
				if(obj[11] != null && !(obj[11].toString().isEmpty())){
				deductionVo.setEmployerAmountType(Integer.parseInt(obj[11]+""));
				}
				
				deductionVo.setEmployeeAmount((String) obj[12]);
				if(obj[13] != null && !obj[13].toString().isEmpty()){
				deductionVo.setEmployeeAmountType(Integer.parseInt(obj[13]+""));
				}
				deductionVo.setEmployerPercentage((String) obj[14]);
				deductionVo.setEmployerPercentageType((String) obj[15]);
				deductionVo.setEmployeePercentage((String) obj[16]);
				deductionVo.setEmployeePercentageType((String) obj[17]);				
				deductionVo.setIsActive((obj[18]).toString());
				deductionVo.setCreatedDate((Date) obj[19]);
				deductionVo.setCreatedBy((Integer) obj[20]);
				deductionVo.setModifiedDate((Date) obj[21]);
				deductionVo.setModifiedBy((Integer) obj[22]);				
				wageScaleDetailsList.add(deductionVo);

			}

		} catch (Exception e) {
			log.error("Error Occured ",e);
		}

		return wageScaleDetailsList;
	}
	
	
	
	
	
	
	@Override
	public List<SimpleObject> getTransactionDatesListForEditingWageDeduction(String deductionCode) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List rulesList = session.createSQLQuery("select Deduction_Id,concat(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),'-',Sequence_Id) from wage_deduction  where Deduction_Code = '"+deductionCode+"' and  Is_Active = 'Y'  order by Transaction_Date ,Sequence_Id " ).list();
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
	
	
	
	public List validateWageDeductionCode(String deductionCode,String customerID,String companyId,Integer deductionId){
		Session session = sessionFactory.getCurrentSession();
		Query q = null;
		String message=null;
		List simpleObjectList = new ArrayList<>();
		try{
			if(customerID != null && !customerID.isEmpty() ){
			q =  session.createQuery("from WageDeduction where deductionCode='"+deductionCode.trim()+"' and customerDetails  IN ('"+customerID+"') and companyDetails  IN ('"+companyId+"')");
			}
			
			List tempList = q.list();
			
			SimpleObject object = new SimpleObject();
			if(q.list().size()>0){
				object.setId(1);
				object.setName("WageDeduction Code already existed");				
			}else{
				object.setId(0);
				object.setName("Success");
				
			}
			simpleObjectList.add(object);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return simpleObjectList;
	}
	
}
