package com.Ntranga.CLMS.Dao;


import java.math.BigInteger;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
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
import com.Ntranga.core.CLMS.entities.WageScaleDetails;
import com.Ntranga.core.CLMS.entities.WageScaleWorkSkills;

import common.Logger;
@Transactional

@Repository("wageScaleDao")
public class WageScaleDaoImpl implements WageScaleDao  {

	private static Logger log = Logger.getLogger(WageScaleDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	
	
	@Override
	public List<WageScaleDetailsVo> wageScaleGridDetails(String customerId,String companyId,String Location,String status,String wageScaleCode,String wageScaleNmae) {
		Session session = sessionFactory.getCurrentSession();
		List<WageScaleDetailsVo> wageDetailsList = new ArrayList<WageScaleDetailsVo>();
		System.out.println("customerIdasdf::"+customerId+"::companyId::"+companyId+"::Location::"+Location+":wageScaleCode:"+wageScaleCode+":wageScaleNmae:"+wageScaleNmae);
		try {
			
			 String q = "SELECT DISTINCT cdi.customer_name, com.company_name,  wsd.Transaction_Date, CASE wsd.is_active WHEN 'Y' THEN 'Active' ELSE 'In-Active' END AS STATUS, "+
						"  ldi.Location_Name,wsd.wage_Scale_Code, wsd.wage_Scale_Name,wsd.Wage_Scale_Id "+
						"  FROM wage_scale_details wsd  "+
						"  INNER JOIN customer_details_info cdi ON(wsd.customer_id =cdi.customer_id)  "+
						"  INNER JOIN company_details_info com ON (com.company_id = wsd.company_id)  "+
						"  INNER JOIN location_details_info ldi ON (ldi.Location_Id = wsd.location_Id) "+
						
						"  WHERE  CONCAT(DATE_FORMAT(wsd.transaction_date, '%Y%m%d'), LPAD(wsd.Sequence_Id, 2, '0')) =   "+
						"  (  "+
						"  SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id, 2, '0')))  "+
						"  FROM wage_scale_details vdi1  "+
						"  WHERE  wsd.wage_Scale_Code = vdi1.wage_Scale_Code      "+
						"   AND vdi1.transaction_date <= CURRENT_DATE()    "+
						"  )  "+
						"  AND   "+
						"  CONCAT(DATE_FORMAT(cdi.transaction_date, '%Y%m%d'), LPAD(cdi.Customer_Sequence_Id, 2, '0')) =   "+
						"  (  "+
						"  SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Customer_Sequence_Id, 2, '0')))  "+
						"  FROM customer_details_info vdi1  "+
						"  WHERE cdi.customer_id = vdi1.customer_id    "+
						"   AND vdi1.transaction_date <= CURRENT_DATE()    "+
						"  )  "+
						"  AND   "+
						"  CONCAT(DATE_FORMAT(com.transaction_date, '%Y%m%d'), LPAD(com.Company_Sequence_Id, 2, '0')) =   "+
						"  (  "+
						"  SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Company_Sequence_Id, 2, '0')))  "+
						"  FROM company_details_info vdi1  "+
						"  WHERE com.company_id = vdi1.  company_id  "+
						"   AND vdi1.transaction_date <= CURRENT_DATE()    "+
						"  )  "+
						" AND   "+
						"  CONCAT(DATE_FORMAT(ldi.transaction_date, '%Y%m%d'), LPAD(ldi.Location_Sequence_Id, 2, '0')) =   "+
						"  (  "+
						"  SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Location_Sequence_Id, 2, '0')))  "+
						"  FROM location_details_info vdi1  "+
						"  WHERE ldi.location_Id = vdi1.location_Id  "+
						"  AND vdi1.transaction_date <= CURRENT_DATE()) ";
			 
			 			
			 			
					 if(customerId != null && !customerId.isEmpty()){						
						 q +=" and wsd.Customer_Id = '"+customerId+"'";				 
					 } 
					 if(companyId != null && !companyId.isEmpty()  ){						 
						 q +=" and wsd.Company_Id = '"+companyId+"' ";	
					 } 
					 if(Location != null && !Location.isEmpty() ){						 
						 q +=" and wsd.Location_Id = '"+Location+"'";	
					 }
					 
					 if(status != null && !status.isEmpty()){
						 q +=" and wsd.is_active = '"+status+"'";
					 }
					 
					 if(wageScaleCode != null && !wageScaleCode.isEmpty()){
						 q +=" and wsd.wage_Scale_Code = '"+wageScaleCode+"'";
					 }
					 
					 if(wageScaleNmae != null && !wageScaleNmae.isEmpty()){
						 q +=" and wsd.wage_scale_Name = '"+wageScaleNmae+"'";
					 }
					 
					 
					 q += " ORDER BY wage_Scale_Name asc";
								 
			
			 List tempList = session.createSQLQuery(q).list();
			
			for(Object o : tempList){
				Object[] obj = (Object[]) o;
				WageScaleDetailsVo workerVo = new WageScaleDetailsVo();
				workerVo.setCustomerName(obj[0]+"");
				workerVo.setCompanyName(obj[1]+"");
				workerVo.setTransactionDate((Date)obj[2]);
				workerVo.setIsActive(obj[3]+"");
				workerVo.setLocationName(obj[4]+"");
				workerVo.setWageScaleCode(obj[5]+"");
				workerVo.setWageScaleName(obj[6]+"");							
				workerVo.setWageScaleId((Integer)obj[7]);
				wageDetailsList.add(workerVo);
			}						
		} catch (HibernateException e) {
			log.error("Error Occured ",e);

		}
		
		
		return wageDetailsList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public Integer saveOrUpdateWageScaleDetails(WageScaleDetailsVo wageScaleDetailsVo) {
		Session session = sessionFactory.getCurrentSession();
		Integer wageScaleId =0;
		System.out.println(wageScaleDetailsVo);
		try {
			WageScaleDetails trd = new WageScaleDetails();
			
			trd.setCustomerDetails(new CustomerDetails(wageScaleDetailsVo.getCustomerId()));
			trd.setCompanyDetails(new CompanyDetails(wageScaleDetailsVo.getCompanyId()));
			trd.setLocationId(wageScaleDetailsVo.getLocationId());
			trd.setCountryId(wageScaleDetailsVo.getCountryId());
			trd.setWageScaleCode(wageScaleDetailsVo.getWageScaleCode());
			trd.setWageScaleName(wageScaleDetailsVo.getWageScaleName());
			trd.setTransactionDate(wageScaleDetailsVo.getTransactionDate());
			trd.setIsActive(wageScaleDetailsVo.getIsActive());
			trd.setCreatedBy(wageScaleDetailsVo.getCreatedBy());
			trd.setModifiedBy(wageScaleDetailsVo.getModifiedBy());
			trd.setCreatedDate(new Date());
			trd.setModifiedDate(new Date());
			
			BigInteger SequenceId ;
			if(wageScaleDetailsVo != null &&  wageScaleDetailsVo.getWageScaleId() > 0){	
				trd.setSequenceId(wageScaleDetailsVo.getSequenceId());
				trd.setWageScaleId(wageScaleDetailsVo.getWageScaleId());				
				session.update(trd);
				wageScaleId = trd.getWageScaleId();
			}else  {
				SequenceId = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Sequence_Id),0) FROM wage_scale_details b  WHERE  b.Wage_Scale_Code = '"+wageScaleDetailsVo.getWageScaleCode()+"' AND  b.`Transaction_Date` = '"+DateHelper.convertDateToSQLString(wageScaleDetailsVo.getTransactionDate())+"'").list().get(0);
				if(SequenceId.intValue() >0){
					trd.setSequenceId(SequenceId.intValue()+1);					
				}else{
					trd.setSequenceId(1);				
				}
				
				wageScaleId = (Integer)session.save(trd);			
			}
			
			
			
			
			if(wageScaleDetailsVo.getWageScaleId() != null && wageScaleDetailsVo.getWageScaleId() >0){						
				Query q = session.createSQLQuery("delete from wage_scale_work_skills where wage_Scale_Id = '"+wageScaleDetailsVo.getWageScaleId()+"'");
				q.executeUpdate();
			}
				
			
			for(WageScaleWorkSkillsVo daysVo :wageScaleDetailsVo.getWageScaleWorkSkillList()){
				WageScaleWorkSkills trdInfo = new WageScaleWorkSkills();
				
				trdInfo.setWageScaleDetails(new WageScaleDetails(wageScaleId));
				trdInfo.setCustomerId(wageScaleDetailsVo.getCustomerId());
				trdInfo.setCompanyId(wageScaleDetailsVo.getCompanyId());						
				trdInfo.setIsActive(wageScaleDetailsVo.getIsActive());
				trdInfo.setWorkSkll(daysVo.getWorkSkll());
				trdInfo.setWageFrom(daysVo.getFrom());
				trdInfo.setWageTo(daysVo.getTo());
				trdInfo.setCurrency(daysVo.getCurrency()+"");
				trdInfo.setFrequency(daysVo.getFrequency());
				trdInfo.setCreatedBy(wageScaleDetailsVo.getCreatedBy());
				trdInfo.setModifiedBy(wageScaleDetailsVo.getModifiedBy());
				trdInfo.setCreatedDate(new Date());
				trdInfo.setModifiedDate(new Date());
				session.save(trdInfo);
			}
			
			
		
			
			
		}catch(Exception e){
			
			log.error("Error Occured ",e);
		}
		return wageScaleId;
	}


	
	public List<WageScaleDetailsVo> getDetailsBywageScaleId(Integer wageScaleId) {

		List<WageScaleDetailsVo> wageScaleDetailsList = new ArrayList<WageScaleDetailsVo>();
		Session session = sessionFactory.getCurrentSession();
		try {

			List tempList = session.createSQLQuery("SELECT Wage_Scale_Id,Wage_Scale_Code, Wage_Scale_Name, Transaction_Date, Sequence_Id, Customer_Id, Company_Id, "+ 
									" Country_Id, Location_Id, Is_Active, Created_By, Created_date, Modified_By, Modified_Date "+ 
									" FROM  wage_scale_details  wsd	"+ 								
									" WHERE  Wage_Scale_Id = " + wageScaleId).list();
			for (Object o : tempList) {
				Object[] obj = (Object[]) o;
				WageScaleDetailsVo wageScaleDetailsVo = new WageScaleDetailsVo();
				wageScaleDetailsVo.setWageScaleId((Integer) obj[0]);
				wageScaleDetailsVo.setWageScaleCode((String) obj[1]);
				wageScaleDetailsVo.setWageScaleName((String) obj[2]);
				wageScaleDetailsVo.setTransactionDate((Date) obj[3]);
				wageScaleDetailsVo.setSequenceId((Integer) obj[4]);
				wageScaleDetailsVo.setCustomerId((Integer) obj[5]);
				wageScaleDetailsVo.setCompanyId((Integer) obj[6]);
				wageScaleDetailsVo.setCountryId((Integer) obj[7]);
				wageScaleDetailsVo.setLocationId((Integer) obj[8]);
				wageScaleDetailsVo.setIsActive((obj[9]).toString());
				wageScaleDetailsVo.setCreatedBy((Integer) obj[10]);
				wageScaleDetailsVo.setCreatedDate((Date) obj[11]);
				wageScaleDetailsVo.setModifiedBy((Integer) obj[12]);				
				wageScaleDetailsVo.setModifiedDate((Date) obj[13]);

				List wrkSkillsList = session.createSQLQuery("SELECT wsws.Work_Skll,wsws.wagefrom,	wsws.wageTo,	wsws.Currency as id,wsws.Frequency,mc.Currency as name FROM  wage_scale_work_skills wsws LEFT JOIN m_currency mc ON(mc.Currency_Id = wsws.Currency) INNER JOIN wage_scale_details wsd ON(wsws.wage_Scale_Id = wsd.wage_Scale_Id)    where wsws.wage_scale_id =" + wageScaleId).list();

				for (Object o1 : wrkSkillsList) {
					Object[] obj1 = (Object[]) o1;
					WageScaleWorkSkillsVo workSkillsVo = new WageScaleWorkSkillsVo();
					workSkillsVo.setWorkSkll(obj1[0]+"");					
					workSkillsVo.setFrom(Integer.parseInt(obj1[1]+""));
					workSkillsVo.setTo(Integer.parseInt(obj1[2]+""));
					workSkillsVo.setCurrency(Integer.parseInt(obj1[3]+""));
					workSkillsVo.setFrequency(obj1[4]+"");
					workSkillsVo.setCurrencyName(obj1[5]+"");
					wageScaleDetailsVo.getWageScaleWorkSkillList().add(workSkillsVo);
					
				}

				

				wageScaleDetailsList.add(wageScaleDetailsVo);

			}

		} catch (Exception e) {
			log.error("Error Occured ",e);
		}

		return wageScaleDetailsList;
	}
	
	
	
	
	
	
	@Override
	public List<SimpleObject> getTransactionDatesListForEditingWageScale(String wageScaleCode) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List rulesList = session.createSQLQuery("select wage_Scale_Id,concat(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),'-',Sequence_Id) from wage_scale_details  where Wage_Scale_Code = '"+wageScaleCode+"' and  Is_Active = 'Y'  order by Transaction_Date ,Sequence_Id " ).list();
			for (Object o  : rulesList) {
				Object[] obj = (Object[]) o;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)obj[0]);
				object.setName(obj[1]+"");
				transactionList.add(object);
			}			
		} catch (HibernateException e) {
			log.error("Error Occured ",e);
		}

		return transactionList;

	}
	
	
	
	public List validateWageScaleCode(String wageScaleCode,String customerID,String companyId,Integer wageScaleId){
		Session session = sessionFactory.getCurrentSession();
		Query q = null;
		String message=null;
		List simpleObjectList = new ArrayList<>();
		try{
			if(customerID != null && !customerID.isEmpty() ){
			q =  session.createQuery("from WageScaleDetails where wageScaleCode='"+wageScaleCode.trim()+"' and customerDetails  IN ('"+customerID+"') and companyDetails  IN ('"+companyId+"')");
			}
			
			List tempList = q.list();
			
			SimpleObject object = new SimpleObject();
			if(q.list().size()>0){
				object.setId(1);
				object.setName("WageScale ID already existed");				
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
	
	@Override
	public List<SimpleObject> getWageScaleServices(String customerId, String companyId) {
		// TODO Auto-generated method stub
			
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			String query = "SELECT   `Wage_Scale_Code`,CONCAT(`Wage_Scale_Code`,' - ',`Wage_Scale_Name`) AS sname FROM wage_scale_details wsd WHERE " 
		+" CONCAT(DATE_FORMAT(wsd.transaction_date, '%Y%m%d'), LPAD(wsd.Sequence_Id, 2, '0')) =   "
		+"  (   SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id, 2, '0')))  FROM wage_scale_details vdi1  "
		+"  WHERE  wsd.wage_Scale_Code = vdi1.wage_Scale_Code  AND vdi1.transaction_date <= CURRENT_DATE()  ) AND wsd.Customer_Id ="+customerId+"  AND wsd.Company_Id ="+companyId+" AND wsd.Is_Active='Y' ORDER BY `Wage_Scale_Code` ";
			List rulesList = session.createSQLQuery(query ).list();
			for (Object o  : rulesList) {
				Object[] obj = (Object[]) o;
				SimpleObject object = new SimpleObject();
				object.setName(obj[0]+"");
				object.setDesc(obj[1]+"");
				transactionList.add(object);
			}			
		} catch (HibernateException e) {
			log.error("Error Occured ",e);
		}

		return transactionList;
	}
	
	
	public List<SimpleObject> getcomanyCurrency(Integer companyId){	
		
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> currencyList = new ArrayList<>();
		try {			
			List rulesList = session.createSQLQuery("SELECT default_currency_id,`Currency` FROM company_profile  cp INNER JOIN m_currency mc ON(cp.default_currency_id = mc.`Currency_Id`)  WHERE   company_id = "+companyId).list();
			for (Object o  : rulesList) {
				Object[] obj = (Object[]) o;
				SimpleObject object = new SimpleObject();
				object.setId((int)obj[0]);	
				object.setName(obj[1]+"");				
				currencyList.add(object);
			}			
		} catch (HibernateException e) {
			log.error("Error Occured ",e);
		}
		
		return currencyList;
		
	}
	
}
