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

import com.Ntranga.CLMS.vo.RulesForPaymentReleaseVo;
import com.Ntranga.CLMS.vo.RulesForPaymentVerificationVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.MCountry;
import com.Ntranga.core.CLMS.entities.RulesForPaymentRelease;
import com.Ntranga.core.CLMS.entities.RulesForPaymentVerification;

import common.Logger;

@SuppressWarnings({"rawtypes", "unused"})
@Transactional
@Repository("RulesForPaymentDao")
public class RulesForPaymentDaoImpl implements RulesForPaymentDao {

	private static Logger log = Logger.getLogger(StatutorySetupsDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Integer saveRulesForPayment(RulesForPaymentReleaseVo paramRules) {
		log.info("Entered in saveRulesForPayment  ::  paramRules =  "+paramRules);
		
		Session session = sessionFactory.getCurrentSession(); ; 
		Integer rulesForPaymentId = 0;
	//	Integer budgetDetailsId = 0;
		BigInteger sequenceId = new BigInteger("0");
		BigInteger uniqueId = new BigInteger("0");
		RulesForPaymentRelease release = new RulesForPaymentRelease();
		RulesForPaymentVerification verification = new RulesForPaymentVerification();
		
		try{
			if(paramRules != null && paramRules.getRulesForPaymentId() != null && paramRules.getRulesForPaymentId() > 0 ){
				release = (RulesForPaymentRelease) session.load(RulesForPaymentRelease.class, paramRules.getRulesForPaymentId());
			}
			
			release.setCustomerDetails(new CustomerDetails(paramRules.getCustomerId()));
			release.setCompanyDetails(new CompanyDetails(paramRules.getCompanyId()));
			release.setCountryDetails(new MCountry(paramRules.getCountryId()));
			release.setTransactionDate(paramRules.getTransactionDate());
			release.setBeforeVerification(paramRules.getBeforeVerification());
			release.setAfterVerification(paramRules.getAfterVerification());
			release.setLiabilitiesPaid(paramRules.getLiabilitiesPaid());
			release.setPaymentMethod(paramRules.getPaymentMethod());
			release.setPeriod(paramRules.getPeriod());
			release.setPeriodType(paramRules.getPeriodType());
			release.setModifiedBy(paramRules.getModifiedBy());
			release.setModifiedDate(new Date());
			
			
			
			if(paramRules != null && paramRules.getRulesForPaymentId() != null && paramRules.getRulesForPaymentId() > 0 ){
				release.setSequenceId(paramRules.getSequenceId());
				session.update(release);
				rulesForPaymentId = paramRules.getRulesForPaymentId();
			}else{
				release.setCreatedBy(paramRules.getCreatedBy());
				release.setCreatedDate(new Date());
				
				if(paramRules.getUniqueId() != null && paramRules.getUniqueId() > 0 ){
					sequenceId  = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Sequence_Id),0) FROM rules_for_payment_release "
							+ "  WHERE Unique_Id = "+paramRules.getUniqueId() +" AND Transaction_Date = '"+DateHelper.convertDateToSQLString(paramRules.getTransactionDate())+"' AND Customer_Id='"+paramRules.getCustomerId()+"' AND Company_Id = '"+paramRules.getCompanyId()+"'").list().get(0);
					release.setUniqueId(paramRules.getUniqueId());	
				}else{
					uniqueId = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Unique_Id),0) as id FROM rules_for_payment_release ").list().get(0);
					release.setUniqueId(uniqueId.intValue()+1 );	
				}
				release.setSequenceId(sequenceId.intValue()+1);
				rulesForPaymentId = (Integer) session.save(release);
				
				
			}

			session.flush();

			
			log.info(" sequence Id : "+sequenceId+" Rules for Payment Id : "+rulesForPaymentId);
			if(paramRules.getVerificationList() != null && paramRules.getVerificationList().size() > 0){
				if(paramRules != null && paramRules.getRulesForPaymentId() != null && paramRules.getRulesForPaymentId() > 0 ){
					Query q = session.createQuery("DELETE FROM RulesForPaymentVerification  WHERE  customerDetails = "+paramRules.getCustomerId() +" AND companyDetails = "+paramRules.getCompanyId() +" AND RulesForPaymentDetails = "+rulesForPaymentId+" AND sequenceId = "+release.getSequenceId());
					int delete = q.executeUpdate();
					//System.out.println(delete);
				}
				
				for(RulesForPaymentVerificationVo verificationVo : paramRules.getVerificationList()){
					verification = new RulesForPaymentVerification();

					verification.setCustomerDetails(new CustomerDetails(paramRules.getCustomerId()));
					verification.setCompanyDetails(new CompanyDetails(paramRules.getCompanyId()));
					verification.setCountryDetails(new MCountry(paramRules.getCountryId()));
					verification.setRulesForPaymentDetails(new RulesForPaymentRelease(rulesForPaymentId));
					//verification.setSequenceId(verificationVo.getSequenceId());
					verification.setComplianceId(verificationVo.getComplianceUniqueId());
					verification.setConsiderForVerification(verificationVo.getConsiderForVerification() == true ? "Y" : "N");
					verification.setModifiedBy(paramRules.getModifiedBy());
					verification.setModifiedDate(new Date());
					verification.setCreatedBy(paramRules.getCreatedBy());				
					verification.setCreatedDate(new Date());
					session.save(verification);
				}
			}
			
			session.flush();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			log.info("Exiting from saveRulesForPayment()  ::   "+rulesForPaymentId);
		}
		log.info("Exiting from saveRulesForPayment()  ::   "+rulesForPaymentId);
		return rulesForPaymentId;
	}

	@Override
	public List<RulesForPaymentReleaseVo> getRulesForPaymentById(Integer customerId, Integer companyId, Integer rulesForPaymentId) {
		log.info("Entered in  getRulesForPaymentById()  ::   customerId = "+customerId+", CompanyId = "+companyId+", RulesForPaymentId  = "+rulesForPaymentId);
		
		Session session = sessionFactory.getCurrentSession();
		RulesForPaymentReleaseVo paymentVo = new RulesForPaymentReleaseVo();
		List<RulesForPaymentReleaseVo> returnList = new ArrayList<RulesForPaymentReleaseVo>();
		
		String  hqlQuery = " SELECT rule.Customer_Id, rule.Company_Id,  rule.Unique_Id, rule.Country_Id, "
							+ "	rule.Transaction_Date, rule.After_Verification, rule.Before_Verification, rule.Liabilities_Paid, rule.Payment_Method, "
							+ " rule.Period, rule.Period_Type,rule.Sequence_Id, rule.Rules_For_Payment_Id "
							+ " FROM  rules_for_payment_release AS rule"
							+ " WHERE 1 = 1 ";					
		
		if(customerId != null && customerId > 0 ){
			hqlQuery += " AND rule.Customer_Id =  "+customerId;
		}
		
		if(companyId != null && companyId > 0 ){
			hqlQuery += " AND rule.Company_Id = "+companyId;
		}
		
		if(rulesForPaymentId != null && rulesForPaymentId > 0){
			hqlQuery += " AND rule.Rules_For_Payment_Id = "+rulesForPaymentId;
		}else{
			hqlQuery +=   " AND  CONCAT(DATE_FORMAT(rule.Transaction_Date, '%Y%m%d'), rule.Sequence_Id) =  ( "
						+ "	SELECT MAX(CONCAT(DATE_FORMAT(rule1.Transaction_Date, '%Y%m%d'), rule1.Sequence_Id)) "
						+ "	FROM rules_for_payment_release rule1 "
						+ "	WHERE rule.Unique_Id = rule1.Unique_Id AND rule1.Transaction_Date <= CURRENT_DATE() "
						+ "	) ";
		}

		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			List budgetList = query.list();

			for (Object object : budgetList) {
				Object[] obj = (Object[]) object;
				
				paymentVo = new RulesForPaymentReleaseVo();
				
				paymentVo.setCustomerId((Integer)obj[0]);
				paymentVo.setCompanyId((Integer)obj[1]);
				paymentVo.setUniqueId((Integer)obj[2]);
				paymentVo.setCountryId((Integer)obj[3]);
				paymentVo.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[4]));
				paymentVo.setAfterVerification((Integer)obj[5]);
				paymentVo.setBeforeVerification((Integer)obj[6]);
				//workerGroupVo.setWageRateId((Integer)obj[8]);
				paymentVo.setLiabilitiesPaid((Integer)obj[7]);
				paymentVo.setPaymentMethod((String)obj[8]);
				paymentVo.setPeriod((Integer)obj[9]);
				paymentVo.setPeriodType((String)obj[10]);
				paymentVo.setSequenceId((Integer)obj[11]);
				paymentVo.setRulesForPaymentId((Integer)obj[12]);
				
				String  verificationQuery = " SELECT verify.Compliance_Id, compliance.Doccument_Name,  verify.Consider_For_Verification, verify.Payment_Verification_Id  "
						+ " FROM  rules_for_payment_verification AS verify "
						+ " LEFT JOIN define_compliance_types compliance ON compliance.Customer_Id = verify.Customer_Id AND compliance.Company_Id = verify.Company_Id AND  compliance.Compliance_Unique_Id = verify.Compliance_Id "
						+ " WHERE 1 = 1 "
						+ " AND verify.Rules_For_Payment_Id =  "+paymentVo.getRulesForPaymentId()
						+ " GROUP BY Compliance_Unique_Id ";
				
				List verificationList = session.createSQLQuery(verificationQuery).list();
				List<RulesForPaymentVerificationVo> verifyList = new ArrayList<>();
				
				for (Object object1 : verificationList) {
					Object[] obj1 = (Object[]) object1;
					
					RulesForPaymentVerificationVo verificationVo = new RulesForPaymentVerificationVo();
					verificationVo.setComplianceUniqueId((Integer)obj1[0]);
					verificationVo.setDoccumentName((String)obj1[1]);
					verificationVo.setConsiderForVerification(((String)obj1[2]).equalsIgnoreCase("Y") ? true : false);
					verificationVo.setPaymentVerificationId((Integer)obj1[3]);
					
					verifyList.add(verificationVo);
				}
				paymentVo.setVerificationList(verifyList);
				
				returnList.add(paymentVo);
			}	
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from  getRulesForPaymentById()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from  getRulesForPaymentById()  ::   "+returnList);
		return returnList;
	}

	@Override
	public List<SimpleObject> getTransactionListForRulesForPayment(Integer customerId, Integer companyId, Integer uniqueId) {
		log.info("Entered in getTransactionListForRulesForPayment()  ::   customerId = "+customerId+" , companyId = "+companyId+", uniqueId = "+uniqueId);
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List datesList = session.createSQLQuery("SELECT Rules_For_Payment_Id AS id ,CONCAT(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),' - ',Sequence_Id) AS name FROM  rules_for_payment_release info  WHERE info.Customer_Id = "+customerId+" AND info.Company_Id = "+companyId+" AND info.Unique_Id = "+uniqueId+" ORDER BY info.Transaction_Date, info.Sequence_Id asc").list();
			
			for (Object transDates  : datesList) {
				Object[] transaction = (Object[]) transDates;
				
				SimpleObject object = new SimpleObject();
				object.setId((Integer)transaction[0]);
				object.setName((String)transaction[1]);
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getTransactionListForRulesForPayment()  ::   "+transactionList);
		}
		
		log.info("Exiting from getTransactionListForRulesForPayment()  ::   "+transactionList);
		return transactionList;
	}
	
	@Override
	public List<RulesForPaymentVerificationVo> getCompliances(Integer customerId, Integer companyId) {
		List<RulesForPaymentVerificationVo> verificationList = new ArrayList<RulesForPaymentVerificationVo>();
		try{
			String query="SELECT  `Doccument_Name`, Compliance_Unique_Id FROM `define_compliance_types` parent WHERE `Transaction_Date` = (SELECT MAX(`Transaction_Date`) FROM `define_compliance_types` child WHERE child.`Transaction_Date` <= CURRENT_DATE() AND parent.`Customer_Id`= child.`Customer_Id` AND parent.`Company_Id`=child.`Company_Id`) AND `Sequence_Id` = (SELECT MAX(`Sequence_Id`) FROM `define_compliance_types` child WHERE child.`Transaction_Date` =  parent.`Transaction_Date` AND parent.`Customer_Id`= child.`Customer_Id` AND parent.`Company_Id`=child.`Company_Id` ) AND `Company_Id`="+companyId+" AND `Customer_Id`="+customerId
							+ " AND Applicable_To in ( 'Vendor' , 'Both' )"
							+ " AND Is_Active = 'Y'  ORDER BY Doccument_Name asc";
			
			List tempList = sessionFactory.getCurrentSession().createSQLQuery(query).list();
			System.out.println(tempList);
			if(tempList != null && tempList.size() > 0){
				for(Object o : tempList){
					Object[] obj = (Object[]) o;
					RulesForPaymentVerificationVo rules = new RulesForPaymentVerificationVo();
					
					rules.setDoccumentName((String)obj[0]);
					rules.setComplianceUniqueId((Integer)obj[1]);
					rules.setConsiderForVerification(false);
					
					verificationList.add(rules);
				}
			}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}		
		return verificationList;
	}

	@Override
	public List<SimpleObject> getPaymentRulesDocumentNames(Integer customerId, Integer companyId) {
		log.info("Entered in  getPaymentRulesDocumentNames()  ::   customerId = "+customerId+", CompanyId = "+companyId);
		
		Session session = sessionFactory.getCurrentSession();
		
		List<SimpleObject> returnList = new ArrayList<SimpleObject>();
		
		String  verificationQuery = " SELECT verify.Compliance_Id, compliance.Doccument_Name, verify.Payment_Verification_Id  "
				+ " FROM  rules_for_payment_verification AS verify "
				+ " LEFT JOIN define_compliance_types compliance ON compliance.Customer_Id = verify.Customer_Id AND compliance.Company_Id = verify.Company_Id AND  compliance.Compliance_Unique_Id = verify.Compliance_Id "
				+ " LEFT JOIN rules_for_payment_release rule ON rule.Rules_For_Payment_Id = verify.Rules_For_Payment_Id "
				+ " WHERE 1 = 1 "
				+ " AND  CONCAT(DATE_FORMAT(rule.Transaction_Date, '%Y%m%d'), rule.Sequence_Id) =  ( "
				+ "	SELECT MAX(CONCAT(DATE_FORMAT(rule1.Transaction_Date, '%Y%m%d'), rule1.Sequence_Id)) "
				+ "	FROM rules_for_payment_release rule1 "
				+ "	WHERE rule.Unique_Id = rule1.Unique_Id AND rule1.Transaction_Date <= CURRENT_DATE() "
				+ "	)  AND verify.Consider_For_Verification = 'Y' " ;
				
		
		
		if(customerId != null && customerId > 0 ){
			verificationQuery += " AND rule.Customer_Id =  "+customerId;
		}
		
		if(companyId != null && companyId > 0 ){
			verificationQuery += " AND rule.Company_Id = "+companyId;
		}
		
		
		verificationQuery += " GROUP BY Compliance_Unique_Id ";
		
		try {					
			List verificationList = session.createSQLQuery(verificationQuery).list();
			List<RulesForPaymentVerificationVo> verifyList = new ArrayList<>();
			
			for (Object object1 : verificationList) {
				Object[] obj1 = (Object[]) object1;
				
				SimpleObject object = new SimpleObject();
				object.setId((Integer)obj1[2]);
				object.setName((String)obj1[1]);
				//verificationVo.setPaymentVerificationId((Integer)obj1[2]);
				
				returnList.add(object);
			}
				
			
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from  getPaymentRulesDocumentNames()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from  getPaymentRulesDocumentNames()  ::   "+returnList);
		return returnList;
	}

}
