package com.Ntranga.CLMS.Dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.EsiDetailsVo;
import com.Ntranga.CLMS.vo.EsiSlabDetailsVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.EsiDetails;
import com.Ntranga.core.CLMS.entities.EsiSlabDetails;

import common.Logger;

@Transactional
@Repository("esiDao")
public class EsiDaoImpl implements EsiDao  {

	private static Logger log = Logger.getLogger(EsiDaoImpl.class);
	
	
	@Autowired
	private SessionFactory sessionFactory;


	public Integer saveOrUpdateWorkerDetails(EsiDetailsVo esiVo) {
		Session session = sessionFactory.getCurrentSession();
		EsiDetails esiDetails = new EsiDetails(); 
		Integer esiId = 0;
		try {			
				
			
			esiDetails.setCustomerDetails(new CustomerDetails(esiVo.getCustomerId()));
			esiDetails.setCompanyDetails(new CompanyDetails(esiVo.getCompanyId()));
			esiDetails.setCountryId(esiVo.getCountryId());
			esiDetails.setTransactionDate(esiVo.getTransactionDate());
			esiDetails.setIsActive(esiVo.getIsActive());
			esiDetails.setEarningSalaryLimit(esiVo.getEarningSalaryLimit());
			esiDetails.setEmployeeContribution(esiVo.getEmployeeContribution());
			esiDetails.setEmployerContribution(esiVo.getEmployerContribution());		
			BigInteger SequenceId = null;
			BigInteger uniqueId = null;
			
			
			if (esiVo != null && (esiVo.getEsiId() == null || esiVo.getEsiId() == 0)) {

				SequenceId = (BigInteger) sessionFactory.getCurrentSession()
						.createSQLQuery(
								"SELECT COALESCE(MAX(Sequence_Id),0) FROM `Esi_Details` b  WHERE  b.unique_id = '"
										+ esiVo.getUniqueId() + "' AND  b.`Transaction_Date` = '"
										+ DateHelper.convertDateToSQLString(esiVo.getTransactionDate()) + "' and b.customer_id ='"+esiVo.getCustomerId()+"' and b.company_Id = '"+esiVo.getCompanyId()+"'")
						.list().get(0);
				
			
				uniqueId = (BigInteger) sessionFactory.getCurrentSession()
						.createSQLQuery(
								"SELECT COALESCE(MAX(unique_Id),0) FROM `Esi_Details` where customer_id ='"+esiVo.getCustomerId()+"' and company_Id = '"+esiVo.getCompanyId()+"' ")
						.list().get(0);
					if(uniqueId.intValue() > 0){
						esiDetails.setUniqueId(uniqueId.intValue());	
					}else{
						uniqueId = (BigInteger) sessionFactory.getCurrentSession()
								.createSQLQuery(
										"SELECT COALESCE(MAX(unique_Id),0) FROM `Esi_Details`  ")
								.list().get(0);
						esiDetails.setUniqueId(uniqueId.intValue()+1);	
					}
								
				if (SequenceId.intValue() > 0) {
					esiDetails.setSequenceId(SequenceId.intValue() + 1);
					SequenceId = BigInteger.valueOf(esiDetails.getSequenceId());
				} else {
					esiDetails.setSequenceId(1);
					SequenceId = BigInteger.valueOf(esiDetails.getSequenceId());
				}
				esiDetails.setCreatedBy(esiVo.getCreatedBy());
				esiDetails.setCreatedDate(new Date());
				esiDetails.setModifiedBy(esiVo.getModifiedBy());
				esiDetails.setModifiedDate(new Date());
				esiId = (Integer) session.save(esiDetails);

			} else if (esiVo.getEsiId() > 0) {
				esiId = esiVo.getEsiId();		
				esiDetails.setEsiId(esiId);
				esiDetails.setCreatedBy(esiVo.getCreatedBy());
				esiDetails.setCreatedDate(esiVo.getCreatedDate());
				esiDetails.setModifiedBy(esiVo.getModifiedBy());
				esiDetails.setModifiedDate(new Date());
				esiDetails.setUniqueId(esiVo.getUniqueId());
				esiDetails.setSequenceId(esiVo.getSequenceId());
				SequenceId = new BigInteger(esiVo.getSequenceId()+"");
				session.update(esiDetails);
			}
						
			
			if(esiVo.getEsiId() != null && esiVo.getEsiId()  >0){						
				Query q3 = session.createSQLQuery("delete from esi_slab_details where esi_id = '"+esiVo.getEsiId()+"' and Sequence_Id = '"+SequenceId.intValue()+"' and Transaction_Date ='"+DateHelper.convertDateToSQLString(esiVo.getTransactionDate())+"'");
				q3.executeUpdate();
			}
			
			for(EsiSlabDetailsVo slabVo : esiVo.getEsiSlabDetailsList()){
				EsiSlabDetails esiSlabDetails =  new EsiSlabDetails();
				esiSlabDetails.setCustomerDetails(new CustomerDetails(esiVo.getCustomerId()));
				esiSlabDetails.setCompanyDetails(new CompanyDetails(esiVo.getCompanyId()));
				esiSlabDetails.setEsiDetails(new EsiDetails(esiId));
				esiSlabDetails.setTransactionDate(esiVo.getTransactionDate());
				esiSlabDetails.setSequenceId(SequenceId.intValue());				
				esiSlabDetails.setBenefitEndMonth(slabVo.getBenefitEndMonth());
				esiSlabDetails.setBenefitStartMonth(slabVo.getBenefitStartMonth());
				esiSlabDetails.setContributionStartMonth(slabVo.getContributionStartMonth());
				esiSlabDetails.setContributionEndMonth(slabVo.getContributionEndMonth());
				esiSlabDetails.setBenefitEndMonthYear(slabVo.getBenefitEndMonthYear());
				esiSlabDetails.setBenefitStartMonthYear(slabVo.getBenefitStartMonthYear());
				esiSlabDetails.setContributionStartMonthYear(slabVo.getContributionStartMonthYear());
				esiSlabDetails.setContributionEndMonthYear(slabVo.getContributionEndMonthYear());
				esiSlabDetails.setEsiUniqueId(esiDetails.getUniqueId());
				esiSlabDetails.setCreatedBy(esiVo.getCreatedBy());
				esiSlabDetails.setCreatedDate(new Date());
				esiSlabDetails.setModifiedBy(esiVo.getModifiedBy());
				esiSlabDetails.setModifiedDate(new Date());
					session.save(esiSlabDetails);
				
			}
			
			
			
		} catch (Exception e) {
			esiId = 0;
			log.error("Error Occured ",e);
		}
		
		return esiId;
	}
	
	
	


	public List<EsiDetailsVo> getEsiDetailsByCustomerCompanyIds(Integer customerId,Integer companyId,Integer esiId){		
		List<EsiDetailsVo> esiDetailsList = new ArrayList<EsiDetailsVo>();
		Session session = sessionFactory.getCurrentSession();		
		try{
			
			
			String q =  " SELECT Esi_Id,Customer_Id,Company_Id,Country_Id,Transaction_Date,Sequence_id,unique_Id,Is_Active,Earning_Salary_Limit, "+
					" Employee_Contribution,Employer_Contribution,Created_By,Created_Date"+
					" FROM esi_details ed "+
					" WHERE  ";
			
			
				if(customerId > 0 && companyId > 0){
					q= q +"  CONCAT(DATE_FORMAT(ed.transaction_date, '%Y%m%d'), LPAD(ed.Sequence_Id, 2, '0'))  =	 "+	 
							"  (   "+
							"  SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id, 2, '0')))    "+
							"  FROM esi_details vdi1   "+
							"  WHERE ed.unique_id = vdi1.unique_id AND vdi1.transaction_date <= CURRENT_DATE()   "+
							"  ) AND customer_id = '"+customerId+"'  AND company_id = '"+companyId+"' "   ;
				}else if(esiId > 0){
					q= q +"   esi_id = '"+esiId+"' ";
				}
			
		
			List tempList = session.createSQLQuery(q).list();
			for(Object o:tempList){
				Object[] obj = (Object[])o;
				EsiDetailsVo esiDetails = new EsiDetailsVo();
				esiDetails.setEsiId((Integer)obj[0]);
				esiDetails.setCustomerId((Integer)obj[1]);
				esiDetails.setCompanyId((Integer)obj[2]);
				esiDetails.setCountryId((Integer)obj[3]);
				esiDetails.setTransactionDate((Date)obj[4]);
				esiDetails.setSequenceId((Integer)obj[5]);
				esiDetails.setUniqueId((Integer)obj[6]);				
				esiDetails.setIsActive((String)obj[7]);
				esiDetails.setEarningSalaryLimit((String)obj[8]);
				esiDetails.setEmployeeContribution((String)obj[9]);
				esiDetails.setEmployerContribution((String)obj[10]);
				esiDetails.setCreatedBy((Integer)obj[11]);
				esiDetails.setCreatedDate((Date)obj[12]);
				
				
				String q1 =  " SELECT Esi_Slab_Id,Contribution_Start_Month,Contribution_Start_Month_Year,Contribution_End_Month, "+
						" Contribution_End_Month_Year,Benefit_Start_Month,Benefit_Start_Month_Year,Benefit_End_Month, "+
						" Benefit_End_Month_Year FROM `esi_slab_details` esd  "+
						" WHERE  ";
				
				
				if(customerId > 0 && companyId > 0){
					q1= q1 + " CONCAT(DATE_FORMAT(esd.transaction_date, '%Y%m%d'), LPAD(esd.Sequence_Id, 2, '0'))  = "+		 	 			 
						"  (   "+
						"  SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id, 2, '0')))  "+  
						"  FROM esi_details vdi1   "+
						"  WHERE esd.esi_unique_id = vdi1.unique_id AND vdi1.transaction_date <= CURRENT_DATE()   "+
						"  )  AND esd.esi_unique_id = '"+(Integer)obj[6]+"' AND customer_id = '"+customerId+"'  AND company_id = '"+companyId+"' ";
				}else if(esiId > 0){
					q1= q1 +"   esd.esi_id = '"+esiId+"' ";
				}
												 
		List<EsiSlabDetailsVo> esiSlabList = new ArrayList();
			List tempList1 = session.createSQLQuery(q1).list();
			for(Object o1:tempList1){
				Object[] obj1 = (Object[])o1;
				EsiSlabDetailsVo esiSlabDetails =  new EsiSlabDetailsVo();	
				/*if(obj1[0] != null){
				esiSlabDetails.setEsiSlabId((Integer)obj1[0]);
				}*/if(obj1[1] != null){
				esiSlabDetails.setContributionStartMonth((String)obj1[1]);
				
				}if(obj1[2] != null){
				esiSlabDetails.setContributionStartMonthYear((Integer)obj1[2]);
				
				}if(obj1[3] != null){
					esiSlabDetails.setContributionEndMonth((String)obj1[3]);
				}if(obj1[4] != null){
					esiSlabDetails.setContributionEndMonthYear((Integer)obj1[4]);	
				}if(obj1[5] != null){
					esiSlabDetails.setBenefitStartMonth((String)obj1[5]);
				
				}if(obj1[6] != null){
				esiSlabDetails.setBenefitStartMonthYear((Integer)obj1[6]);
				}if(obj1[7] != null){
					esiSlabDetails.setBenefitEndMonth((String)obj1[7]);
				}if(obj1[8] != null){
					esiSlabDetails.setBenefitEndMonthYear((Integer)obj1[8]);
				}
				esiSlabList.add(esiSlabDetails);
				
			}
			esiDetails.setEsiSlabDetailsList(esiSlabList);
				
				esiDetailsList.add(esiDetails);
			}
			
			
			
			
			
		
		}catch(Exception e){
			log.error("Error Occured ",e);
			
		}
		
		return esiDetailsList;
	}



		
	
	@Override
	public List<SimpleObject> getTransactionHistoryDatesListForESIDetails(int customerId, int companyId) {
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List workerTempList = session.createSQLQuery("SELECT esi_id,CONCAT(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),'-',Sequence_Id) FROM esi_details WHERE customer_id = '"+customerId+"'  AND company_Id = '"+companyId+"'   order by Transaction_Date ,Sequence_Id " ).list();
			for (Object customerObject  : workerTempList) {
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
	
}
