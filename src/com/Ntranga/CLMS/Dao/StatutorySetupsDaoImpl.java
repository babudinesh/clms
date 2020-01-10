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

import com.Ntranga.CLMS.vo.BonusRulesVo;
import com.Ntranga.CLMS.vo.DefineBudgetBasedOnSkillVo;
import com.Ntranga.CLMS.vo.DefineBudgetDetailsVo;
import com.Ntranga.CLMS.vo.DefineProfessionalTaxInfoVo;
import com.Ntranga.CLMS.vo.DefineProfessionalTaxVo;
import com.Ntranga.CLMS.vo.DefineWorkerGroupVo;
import com.Ntranga.CLMS.vo.LWFRulesInfoVo;
import com.Ntranga.CLMS.vo.LWFRulesVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.BonusRules;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.DefineBudgetBasedOnSkill;
import com.Ntranga.core.CLMS.entities.DefineBudgetDetails;
import com.Ntranga.core.CLMS.entities.DefineBudgetDetailsInfo;
import com.Ntranga.core.CLMS.entities.DefineProfessionalTax;
import com.Ntranga.core.CLMS.entities.DefineProfessionalTaxInfo;
import com.Ntranga.core.CLMS.entities.DefineWorkerGroup;
import com.Ntranga.core.CLMS.entities.LWFRules;
import com.Ntranga.core.CLMS.entities.LWFRulesInfo;
import com.Ntranga.core.CLMS.entities.LocationDetails;
import com.Ntranga.core.CLMS.entities.MCountry;
import com.Ntranga.core.CLMS.entities.MState;
import common.Logger;

@SuppressWarnings({"rawtypes","unused"})
@Transactional
@Repository("statutorySetupsDao")
public class StatutorySetupsDaoImpl implements StatutorySetupsDao {

	private static Logger log = Logger.getLogger(StatutorySetupsDaoImpl.class);
	
	
	@Autowired
	private SessionFactory sessionFactory;


	@Override
	public Integer saveBonusRulesDetails(BonusRulesVo bonusRulesVo) {
		// TODO Auto-generated method stub
		Session session = 	null ; 
		Integer rulesId = 0;
		BigInteger sequenceId ;
		BonusRules rules = null;
		try{
			session = sessionFactory.getCurrentSession();
			if(bonusRulesVo != null && bonusRulesVo.getBonusRulesId() != null && bonusRulesVo.getBonusRulesId() >0 ){
				rules = (BonusRules) session.load(BonusRules.class, bonusRulesVo.getBonusRulesId());
			}else{
				rules = new BonusRules();
			}
			rules.setCustomerDetails(new CustomerDetails(bonusRulesVo.getCustomerDetailsId()));
			rules.setCompanyDetails(new CompanyDetails(bonusRulesVo.getCompanyDetailsId()));
			rules.setTransactionDate(bonusRulesVo.getTransactionDate());
			rules.setIsActive(bonusRulesVo.getIsActive());
			rules.setCountryId(bonusRulesVo.getCountryId());
			rules.setSequenceId(1);
			rules.setSalaryLimit(bonusRulesVo.getSalaryLimit());
			rules.setBonusPercentage(bonusRulesVo.getBonusPercentage());
			rules.setMaxCalculationLimit(bonusRulesVo.getMaxCalculationLimit());
			rules.setModifiedBy(bonusRulesVo.getModifiedBy());
			rules.setModifiedDate(new Date());
			
			if(bonusRulesVo != null && bonusRulesVo.getBonusRulesId() != null && bonusRulesVo.getBonusRulesId() >0 ){
				session.update(rules);
				rulesId = bonusRulesVo.getBonusRulesId();
			}else{
				rules.setCreatedBy(bonusRulesVo.getCreatedBy());				
				rules.setCreatedDate(new Date());				
				sequenceId  = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Sequence_Id),0) FROM Bonus_Rules  WHERE  customer_Id = "+bonusRulesVo.getCustomerDetailsId() +" AND company_id = "+bonusRulesVo.getCompanyDetailsId() +" AND `Transaction_Date` = '"+DateHelper.convertDateToSQLString(bonusRulesVo.getTransactionDate())+"' ").list().get(0);
				rules.setSequenceId(sequenceId.intValue()+1);	
				rulesId = (Integer) session.save(rules);
			}
			
			session.flush();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rulesId;
	}


	@Override
	public BonusRulesVo getBonusRulesDetails(BonusRulesVo bonusRulesVo) {
		// TODO Auto-generated method stub
		Session session = null ; 		
		BonusRulesVo rules = null;		
		try{
			session = sessionFactory.getCurrentSession();
			String query = " SELECT `Customer_Id`,`Company_id`, `Country_Id`, `Transaction_Date`, `Sequence_Id`, `Salary_Limit`, `Bonus_Percentage`, `Max_Calculation_Limit`, `Is_Active`, `Bonus_Rules_id` FROM `bonus_rules` br "
					+ " WHERE CONCAT(br.transaction_date, br.sequence_id) =(SELECT MAX(CONCAT(transaction_date, sequence_id)) FROM bonus_rules br1  WHERE (br.Company_id = br1.Company_id AND br.Customer_Id = br1.Customer_Id )GROUP BY Company_id) ";
			
			if(bonusRulesVo != null && bonusRulesVo.getCustomerDetailsId() != null && bonusRulesVo.getCustomerDetailsId() > 0 )
				query += " AND Customer_Id="+bonusRulesVo.getCustomerDetailsId();
			if(bonusRulesVo != null && bonusRulesVo.getCompanyDetailsId() != null && bonusRulesVo.getCompanyDetailsId() > 0 )
				query += " AND Company_id="+bonusRulesVo.getCompanyDetailsId();	
			
			List tempList = session.createSQLQuery(query).list();
			
			
			if(tempList != null && tempList.size() >0 ){
				Object[] obj = (Object[]) tempList.get(0);
				rules = new BonusRulesVo();
				rules.setCustomerDetailsId((Integer)obj[0]);
				rules.setCompanyDetailsId((Integer)obj[1]);
				rules.setCountryId((Integer)obj[2]);
				rules.setTransactionDate((Date)obj[3]);
				rules.setSequenceId((Integer)obj[4]);
				rules.setSalaryLimit((Integer)obj[5]);
				rules.setBonusPercentage((Integer)obj[6]);
				rules.setMaxCalculationLimit((Integer)obj[7]);
				rules.setIsActive(obj[8]+"");
				rules.setBonusRulesId((Integer)obj[9]);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rules;
	}


	@Override
	public List<SimpleObject> getTransactionDatesForBonusHistory(BonusRulesVo bonusRulesVo) {
		// TODO Auto-generated method stub
		List<SimpleObject> objects = new ArrayList<SimpleObject>();
		try{
			
			List tempList = sessionFactory.getCurrentSession().createSQLQuery("SELECT `Bonus_Rules_id` , CONCAT(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),' - ', Sequence_Id) AS tname FROM `bonus_rules`  WHERE `Customer_Id`="+bonusRulesVo.getCustomerDetailsId()+" and `Company_id`="+bonusRulesVo.getCompanyDetailsId()+" and  `Country_Id`="+bonusRulesVo.getCountryId()).list();
			for(Object object : tempList){
				Object[] obj = (Object[]) object;
				objects.add(new SimpleObject((Integer)obj[0],obj[1]+""));
			}
			
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return objects;
	}


	@Override
	public BonusRulesVo getBonusRecordByBonusRulesId(String bonusRulesId) {
		// TODO Auto-generated method stub
		Session session = null ; 		
		BonusRulesVo rules = null;		
		try{
			session = sessionFactory.getCurrentSession();
			String query = " SELECT `Customer_Id`,`Company_id`, `Country_Id`, `Transaction_Date`, `Sequence_Id`, `Salary_Limit`, `Bonus_Percentage`, `Max_Calculation_Limit`, `Is_Active`, `Bonus_Rules_id` FROM `bonus_rules` br "
					+ " WHERE"
					/*+ " CONCAT(br.transaction_date, br.sequence_id) =(SELECT MAX(CONCAT(transaction_date, sequence_id)) FROM bonus_rules br1  WHERE (br.Company_id = br1.Company_id AND br.Customer_Id = br1.Customer_Id ) GROUP BY Company_id) "
					+ " AND "*/
					+ " Bonus_Rules_id="+bonusRulesId;

			List tempList = session.createSQLQuery(query).list();
			
			
			if(tempList != null && tempList.size() >0 ){
				Object[] obj = (Object[]) tempList.get(0);
				rules = new BonusRulesVo();
				rules.setCustomerDetailsId((Integer)obj[0]);
				rules.setCompanyDetailsId((Integer)obj[1]);
				rules.setCountryId((Integer)obj[2]);
				rules.setTransactionDate((Date)obj[3]);
				rules.setSequenceId((Integer)obj[4]);
				rules.setSalaryLimit((Integer)obj[5]);
				rules.setBonusPercentage((Integer)obj[6]);
				rules.setMaxCalculationLimit((Integer)obj[7]);
				rules.setIsActive(obj[8]+"");
				rules.setBonusRulesId((Integer)obj[9]);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rules;
	}

	/**** LWF Rules Start ******/
	
	/*
	 * This method will be used to save the LWFRules
	 */
	@Override
	public Integer saveLWFRules(LWFRulesVo paramRules) {
		log.info("Entered in saveLWFRules  ::  "+paramRules);
		
		Session session = sessionFactory.getCurrentSession(); ; 
		Integer rulesId = 0;
		Integer rulesInfoId = 0;
		BigInteger sequenceId = new BigInteger("0");
		BigInteger uniqueId = new BigInteger("0");
		LWFRules rules = new LWFRules();
		
		try{
			if(paramRules != null && paramRules.getLwfRulesId() != null && paramRules.getLwfRulesId() > 0 ){
				rules = (LWFRules) session.load(LWFRules.class, paramRules.getLwfRulesId());
			}
			
			rules.setCustomerDetails(new CustomerDetails(paramRules.getCustomerId()));
			rules.setCompanyDetails(new CompanyDetails(paramRules.getCompanyId()));
			rules.setCountryDetails(new MCountry(paramRules.getCountryId()));
			rules.setStateDetails(new MState(paramRules.getStateId()));		
			rules.setTransactionDate(paramRules.getTransactionDate());
			rules.setStatus(paramRules.getStatus());
			rules.setDeductionFrequency(paramRules.getDeductionFrequency());
			rules.setModifiedBy(paramRules.getModifiedBy());
			rules.setModifiedDate(new Date());
			
			if(paramRules != null && paramRules.getLwfRulesId() != null && paramRules.getLwfRulesId() > 0 ){
				session.update(rules);
				rulesId = paramRules.getLwfRulesId();
			}else{
				rules.setCreatedBy(paramRules.getCreatedBy());				
				rules.setCreatedDate(new Date());	
				if(paramRules.getLwfUniqueId() != null && paramRules.getLwfUniqueId() > 0){	
					sequenceId  = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(LWF_Sequence_Id),0) FROM lwf_rules  WHERE  Customer_Id = "+paramRules.getCustomerId() +" AND Company_Id = "+paramRules.getCompanyId()+ " AND Country_Id = "+paramRules.getCountryId()+" AND State_Id = "+paramRules.getStateId()+" AND `Transaction_Date` = '"+DateHelper.convertDateToSQLString(paramRules.getTransactionDate())+"' ").list().get(0);
					rules.setLwfUniqueId(paramRules.getLwfUniqueId());
				}else{
					uniqueId = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(LWF_Unique_Id),0) as id FROM lwf_rules").list().get(0);
					rules.setLwfUniqueId(uniqueId.intValue()+1 );	
				}
				rules.setLwfSequenceId(sequenceId.intValue()+1);	
				rulesId = (Integer) session.save(rules);
			}

			session.flush();
			if(paramRules.getLwfRulesList() != null && paramRules.getLwfRulesList().size() > 0){
				if(paramRules != null && paramRules.getLwfRulesId() != null && paramRules.getLwfRulesId() > 0 ){
					Query q = session.createQuery("DELETE FROM LWFRulesInfo  WHERE  customerDetails = "+paramRules.getCustomerId() +" AND companyDetails = "+paramRules.getCompanyId() +" AND lwfRules= "+rulesId+" AND lwfSequenceId = "+rules.getLwfSequenceId());
					int delete = q.executeUpdate();
					System.out.println(delete);
				}
				for(LWFRulesInfoVo lwfRules : paramRules.getLwfRulesList()){
					LWFRulesInfo rulesInfo = new LWFRulesInfo();
					
					rulesInfo.setCustomerDetails(new CustomerDetails(paramRules.getCustomerId()));
					rulesInfo.setCompanyDetails(new CompanyDetails(paramRules.getCompanyId()));
					rulesInfo.setLwfMonth(lwfRules.getLwfMonth());
					rulesInfo.setTransactionDate(paramRules.getTransactionDate());
					rulesInfo.setLwfRules(new LWFRules(rulesId));
					rulesInfo.setSalaryFrom(lwfRules.getSalaryFrom());
					rulesInfo.setSalaryTo(lwfRules.getSalaryTo());
					rulesInfo.setEmployeeShare(lwfRules.getEmployeeShare());
					rulesInfo.setEmployerShare(lwfRules.getEmployerShare());
					rulesInfo.setModifiedBy(paramRules.getModifiedBy());
					rulesInfo.setModifiedDate(new Date());
					rulesInfo.setLwfUniqueId(rules.getLwfUniqueId());		
					rulesInfo.setLwfSequenceId(rules.getLwfSequenceId());
					rulesInfo.setCreatedBy(paramRules.getCreatedBy());				
					rulesInfo.setCreatedDate(new Date());
					rulesInfoId = (Integer) session.save(rulesInfo);
				}
			}
			
			session.flush();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			log.info("Exiting from saveLWFRules()  ::   "+rulesId);
		}
		log.info("Exiting from saveLWFRules()  ::   "+rulesId);
		return rulesId;
	}

	/*
	 * This method will be used to search the existing LWF_Rules by given custoemrId/companyId/countryId/stateId/ All
	 */
	@Override
	public List<LWFRulesVo> getLWFRulesBySearch(LWFRulesVo paramRules) {
		log.info("Entered in  getLWFRulesBySearch()  ::   LWFRulesVo  = "+paramRules);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<LWFRulesVo> returnList = new ArrayList<LWFRulesVo>();
		LWFRulesVo rules = new LWFRulesVo();
		
		String hqlQuery = "SELECT company.Company_Name, customer.Customer_Name, country.country_Name,  state.State_Name, "
							+ "	rules.LWF_Rules_Id "
							+ " FROM lwf_rules AS rules "
							//+ " LEFT JOIN lwf_rules_info info ON info.LWF_Rules_Id = rules.LWF_Rules_Id "
							
							+ " LEFT JOIN customer_details_info AS customer  ON customer.Customer_Id = rules.Customer_Id "
							+ " AND  CONCAT(DATE_FORMAT(customer.Transaction_Date, '%Y%m%d'), customer.Customer_Sequence_Id) =  ( "
							+ "	SELECT MAX(CONCAT(DATE_FORMAT(customer1.Transaction_Date, '%Y%m%d'), customer1.Customer_Sequence_Id)) "
							+ "	FROM customer_details_info customer1 "
							+ "	WHERE customer.Customer_Id = customer1.Customer_Id AND customer1.Transaction_Date <= CURRENT_DATE() "
							+ "	) "
							+ " LEFT JOIN company_details_info AS company  ON company.Customer_Id = rules.Customer_Id  "
																	+ "	AND company.Company_Id = rules.Company_Id "
																	+ "	AND  CONCAT(DATE_FORMAT(company.Transaction_Date, '%Y%m%d'), company.Company_Sequence_Id) = ( "
																	+"	SELECT MAX(CONCAT(DATE_FORMAT(company1.Transaction_Date, '%Y%m%d'), company1.Company_Sequence_Id)) "
																	+"	FROM company_details_info company1 "
																	+"	WHERE company.Company_Id = company1.Company_Id AND company1.Transaction_Date <= CURRENT_DATE() "
																	+ " ) "	
							+ " LEFT JOIN m_country country ON country.Country_Id = rules.Country_Id "
							+ " LEFT JOIN m_state state ON state.State_Id = rules.State_Id "
								+ " WHERE  customer.Is_Active = 'Y' "
								+ " AND company.Is_Active = 'Y' "
								+ " AND  CONCAT(DATE_FORMAT(rules.Transaction_Date, '%Y%m%d'), rules.LWF_Sequence_Id) =  ( "
								+"	SELECT MAX(CONCAT(DATE_FORMAT(rules1.Transaction_Date, '%Y%m%d'), rules1.LWF_Sequence_Id)) "
								+"	FROM lwf_rules rules1 "
								+"	WHERE rules.LWF_Unique_Id = rules1.LWF_Unique_Id AND rules1.Transaction_Date <= CURRENT_DATE() "
								+"	) ";
			
		if(paramRules.getCustomerId() != null && paramRules.getCustomerId() > 0){
			hqlQuery += "  AND  rules.Customer_Id = "+paramRules.getCustomerId();
		}
		
		if(paramRules.getCompanyId() != null && paramRules.getCompanyId() > 0){
			hqlQuery += "  AND  rules.Company_Id = "+paramRules.getCompanyId();
		}
		
		if(paramRules.getCountryId() != null && paramRules.getCountryId() > 0){
			hqlQuery += " AND rules.Country_Id = "+paramRules.getCountryId();
		}
		
		if(paramRules.getStateId() != null && paramRules.getStateId() > 0 ){
			hqlQuery += " AND rules.State_Id = "+paramRules.getStateId();
		}
		
		hqlQuery += " GROUP BY rules.LWF_Rules_Id Order By customer.Customer_Name asc";
		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){
				for (Object customer : queryList) {
					Object[] obj = (Object[]) customer;
					rules = new LWFRulesVo();
					
					rules.setCustomerName((String)obj[0]);
					rules.setCompanyName((String)obj[1]);
					rules.setCountryName((String)obj[2]);
					rules.setStateName((String)obj[3]);
					rules.setLwfRulesId((Integer)obj[4]);
					//rules.setLwfRulesInfoId((Integer)obj[5]);
					
					
					returnList.add(rules);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getLWFRulesBySearch()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getLWFRulesBySearch()  ::   "+returnList);
		return returnList;
	}

	/*
	 * This method will be used to get the LWF Rules by given lwfRuleId
	 */
	@Override
	public List<LWFRulesVo> getLWFRuleByRuleId(Integer lwfRuleId) {
		log.info("Entered in  getLWFRuleByRuleId()  ::   lwfRuleId = "+lwfRuleId);
		Session session = sessionFactory.getCurrentSession();
		LWFRulesVo rule = new LWFRulesVo();
		List<LWFRulesVo> returnList = new ArrayList<LWFRulesVo>();
		String  hqlQuery = " SELECT rules.Customer_Id, rules.Company_Id,  rules.LWF_Rules_Id, rules.Country_Id, "
							+ "	rules.State_Id,  rules.Transaction_Date, rules.Status, rules.Deduction_Frequency, rules.LWF_Unique_Id"
							+ " FROM  lwf_rules rules "
							+ " WHERE 1 = 1 "
							/*+ " AND CONCAT(DATE_FORMAT(rules.Transaction_Date, '%Y%m%d'), rules.LWF_Sequence_Id) =  ( "
							+"	SELECT MAX(CONCAT(DATE_FORMAT(rules1.Transaction_Date, '%Y%m%d'), rules1.LWF_Sequence_Id)) "
							+"	FROM lwf_rules rules1 "
							+"	WHERE rules.LWF_Rules_Id = rules1.LWF_Rules_Id AND rules1.Transaction_Date <= CURRENT_DATE() "
							+"	) "*/
							+ " AND rules.LWF_Rules_Id = "+lwfRuleId;
		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			List lwfList = query.list();

			for (Object object : lwfList) {
				Object[] obj = (Object[]) object;
				rule = new LWFRulesVo();
				rule.setCustomerId((Integer)obj[0]);
				rule.setCompanyId((Integer)obj[1]);
				rule.setLwfRulesId((Integer)obj[2]);
				rule.setCountryId((Integer)obj[3]);
				rule.setStateId((Integer)obj[4]);
				rule.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[5]));
				rule.setStatus((String)obj[6]);
				rule.setDeductionFrequency((String)obj[7]);
				rule.setLwfUniqueId((Integer)obj[8]);
				
				returnList.add(rule);
			}	
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from  getLWFRuleByRuleId()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from  getLWFRuleByRuleId()  ::   "+returnList);
		return returnList;
	}
	
	/*
	 * This method will be used to get the list of lwf rules for a given lwf rule id
	 */
	@Override
	public List<LWFRulesInfoVo> getLWFRulesListByRuleId(Integer lwfRuleId) {
		log.info("Entered in  getLWFRuleByRuleId()  ::   lwfRuleId = "+lwfRuleId);
		Session session = sessionFactory.getCurrentSession();
		LWFRulesInfoVo rule = new LWFRulesInfoVo();
		List<LWFRulesInfoVo> returnList = new ArrayList<LWFRulesInfoVo>();
		String  hqlQuery = " SELECT rules.LWF_Rules_Id, info.LWF_Sequence_Id, info.LWF_Rules_Info_Id, info.LWF_Month, "
							+ " info.Salary_From, info.Salary_To, info.Employee_Contribution_Share, "
							+ " info.Employer_Contribution_Share"							
							+ " FROM  lwf_rules rules"
							+ " LEFT JOIN lwf_rules_info AS info ON rules.LWF_Rules_Id = info.LWF_Rules_Id "
							+ " WHERE 1 = 1 "
							/*+ " AND CONCAT(DATE_FORMAT(rules.Transaction_Date, '%Y%m%d'), rules.LWF_Sequence_Id) =  ( "
							+"	SELECT MAX(CONCAT(DATE_FORMAT(rules1.Transaction_Date, '%Y%m%d'), rules1.LWF_Sequence_Id)) "
							+"	FROM lwf_rules rules1 "
							+"	WHERE rules.LWF_Rules_Id = rules1.LWF_Rules_Id "
							+"	) "*/
							+ " AND info.LWF_Rules_Id = "+lwfRuleId;
		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			List rulesList = query.list();

			for (Object object : rulesList) {
				Object[] obj = (Object[]) object;
				rule = new LWFRulesInfoVo();
				rule.setLwfRulesId((Integer)obj[0]);
				rule.setLwfSequenceId((Integer)obj[1]);
				rule.setLwfRulesInfoId((Integer)obj[2]);
				rule.setLwfMonth((String)obj[3]);
				rule.setSalaryFrom((String)obj[4]);
				rule.setSalaryTo((String)obj[5]);
				rule.setEmployeeShare((String)obj[6]);
				rule.setEmployerShare((String)obj[7]);
				
				
				returnList.add(rule);
			}	
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from  getLWFRulesListByRuleId()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from  getLWFRulesListByRuleId()  ::   "+returnList);
		return returnList;
	}
	
	/*
	 * This method will be used to get transaction dates list of LWF rules with sequence id
	 */
	@Override
	public List<SimpleObject> getTransactionListForLWFRules(Integer customerId,Integer companyId, Integer lwfUniqueId) {
		log.info("Entered in getTransactionListForLWFRules()  ::   customerId = "+customerId+" , companyId = "+companyId+" , lwfUniqueId = "+lwfUniqueId);
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List datesList = session.createSQLQuery("SELECT LWF_Rules_Id AS id ,CONCAT(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),' - ',LWF_Sequence_Id) AS name FROM  lwf_rules info  WHERE info.Customer_Id = "+customerId+" AND info.Company_Id = "+companyId+" AND info.LWF_Unique_Id = "+lwfUniqueId+"  ORDER BY info.Transaction_Date, info.LWF_Sequence_Id asc").list();
			for (Object transDates  : datesList) {
				Object[] transaction = (Object[]) transDates;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)transaction[0]);
				object.setName(transaction[1]+"");
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getTransactionListForLWFRules()  ::   "+transactionList);
		}
		
		log.info("Exiting from getTransactionListForLWFRules()  ::   "+transactionList);
		return transactionList;
	}
/***** LWF Rules End ********/
	
	
/***** Professional Tax  Start****/
	/*
	 * This method will be used to save the professional tax 
	 */
	@Override
	public Integer saveProfessionalTax(DefineProfessionalTaxVo paramRules) {
		log.info("Entered in saveProfessionalRules  ::  "+paramRules);
		
		Session session = sessionFactory.getCurrentSession(); ; 
		Integer taxId = 0;
		Integer taxInfoId = 0;
		BigInteger sequenceId = new BigInteger("0");
		BigInteger uniqueId = new BigInteger("0");
		DefineProfessionalTax tax = new DefineProfessionalTax();
		
		try{
			if(paramRules != null && paramRules.getProfessionalTaxId() != null && paramRules.getProfessionalTaxId() > 0 ){
				tax = (DefineProfessionalTax) session.load(DefineProfessionalTax.class, paramRules.getProfessionalTaxId());
			}
			
			tax.setCustomerDetails(new CustomerDetails(paramRules.getCustomerId()));
			tax.setCompanyDetails(new CompanyDetails(paramRules.getCompanyId()));
			tax.setCountryDetails(new MCountry(paramRules.getCountryId()));
			tax.setStateId(paramRules.getStateId());	
			tax.setLocationDetails(new LocationDetails(paramRules.getLocationId()));
			tax.setTransactionDate(paramRules.getTransactionDate());
			tax.setStatus(paramRules.getStatus());
			tax.setFrequency(paramRules.getFrequency());
			tax.setDefineByState(paramRules.getDefineByState() == true ? "Yes" : "No");
			tax.setDefineByStateAndCity(paramRules.getDefineByStateAndCity() == true ? "Yes" : "No");
			tax.setCity(paramRules.getCity());
			tax.setModifiedBy(paramRules.getModifiedBy());
			tax.setModifiedDate(new Date());
			
			if(paramRules != null && paramRules.getProfessionalTaxId() != null && paramRules.getProfessionalTaxId() > 0 ){
				session.update(tax);
				taxId = paramRules.getProfessionalTaxId();
			}else{
				tax.setCreatedBy(paramRules.getCreatedBy());				
				tax.setCreatedDate(new Date());	
				
				if(paramRules.getTaxUniqueId() != null && paramRules.getTaxUniqueId() > 0){	
					sequenceId  = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Tax_Sequence_Id),0) FROM define_professional_tax  WHERE  Customer_Id = "+paramRules.getCustomerId() +" AND Company_Id = "+paramRules.getCompanyId()+" AND Country_Id = "+paramRules.getCountryId()+" AND Location_Id = "+paramRules.getLocationId()+" AND `Transaction_Date` = '"+DateHelper.convertDateToSQLString(paramRules.getTransactionDate())+"' ").list().get(0);
					tax.setTaxUniqueId( paramRules.getTaxUniqueId());
				}else{
					uniqueId = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Tax_Unique_Id),0) as id FROM define_professional_tax").list().get(0);
					tax.setTaxUniqueId(uniqueId.intValue()+1 );	
				}
				tax.setTaxSequenceId(sequenceId.intValue()+1);	
				taxId = (Integer) session.save(tax);
			}

			session.flush();
			
			if(paramRules.getTaxRulesList() != null && paramRules.getTaxRulesList().size() > 0){
				
				if(paramRules != null && paramRules.getProfessionalTaxId() != null && paramRules.getProfessionalTaxId() > 0 ){
					Query q = session.createQuery("DELETE FROM DefineProfessionalTaxInfo  WHERE  customerDetails = "+paramRules.getCustomerId() +" AND companyDetails = "+paramRules.getCompanyId() +" AND taxDetails= "+taxId+" AND taxSequenceId = "+tax.getTaxSequenceId());
					int delete = q.executeUpdate();
					System.out.println(delete);
				}
				session.flush();
				
				for(DefineProfessionalTaxInfoVo taxRules : paramRules.getTaxRulesList()){
					DefineProfessionalTaxInfo rulesInfo = new DefineProfessionalTaxInfo();
					
					rulesInfo.setCustomerDetails(new CustomerDetails(paramRules.getCustomerId()));
					rulesInfo.setCompanyDetails(new CompanyDetails(paramRules.getCompanyId()));
					rulesInfo.setPTMonthBasedOnFrequency(taxRules.getPtMonth());
					rulesInfo.setTaxDetails(new DefineProfessionalTax(taxId));
					rulesInfo.setSlabFrom(taxRules.getSlabFrom());
					rulesInfo.setSlabTo(taxRules.getSlabTo());
					rulesInfo.setAmount(taxRules.getAmount());
					rulesInfo.setPTAmount(taxRules.getPtAmount());
					rulesInfo.setModifiedBy(paramRules.getModifiedBy());
					rulesInfo.setModifiedDate(new Date());
					rulesInfo.setTaxUniqueId(tax.getTaxUniqueId());
					rulesInfo.setTaxSequenceId(sequenceId.intValue()+1);
					rulesInfo.setCreatedBy(paramRules.getCreatedBy());				
					rulesInfo.setCreatedDate(new Date());
					
					taxInfoId = (Integer) session.save(rulesInfo);
				}
			}
			session.flush();
		
		}catch(Exception e){
			taxId = 0;
			e.printStackTrace();
			log.error(e.getMessage());
			log.info("Exception Occurred. Exiting from saveProfessionalRules()  ::   "+taxId);
		}
		
		log.info("Exiting from saveProfessionalRules()  ::   "+taxId);
		return taxId;
	}

	/*
	 * This method will be used to search the existing LWF_Rules by given custoemrId/companyId/countryId/locationId/ All
	 */
	@Override
	public List<DefineProfessionalTaxVo> getProfessionalTaxListBySearch(DefineProfessionalTaxVo paramRules) {
		log.info("Entered in  getProfessionalTaxListBySearch()  ::   DefineProfessionalTaxVo  = "+paramRules);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<DefineProfessionalTaxVo> returnList = new ArrayList<DefineProfessionalTaxVo>();
		DefineProfessionalTaxVo taxRules = new DefineProfessionalTaxVo();
		
		String hqlQuery = "SELECT company.Company_Name, customer.Customer_Name, country.country_Name,  location.Location_Name, "
							+ "	tax.Professional_Tax_Id"
							+ " FROM define_professional_tax AS tax "
							//+ " LEFT JOIN define_professional_tax_info info ON info.Professional_Tax_Id = tax.Professional_Tax_Id "
							
							+ " LEFT JOIN customer_details_info AS customer  ON customer.Customer_Id = tax.Customer_Id "
														+ " AND  CONCAT(DATE_FORMAT(customer.Transaction_Date, '%Y%m%d'), customer.Customer_Sequence_Id) =  ( "
														+ "	SELECT MAX(CONCAT(DATE_FORMAT(customer1.Transaction_Date, '%Y%m%d'), customer1.Customer_Sequence_Id)) "
														+ "	FROM customer_details_info customer1 "
														+ "	WHERE customer.Customer_Id = customer1.Customer_Id AND customer1.Transaction_Date <= CURRENT_DATE() "
														+ "	) "
							+ " LEFT JOIN company_details_info AS company  ON company.Customer_Id = tax.Customer_Id  "
														+ "	AND company.Company_Id = tax.Company_Id "
														+ "	AND  CONCAT(DATE_FORMAT(company.Transaction_Date, '%Y%m%d'), company.Company_Sequence_Id) = ( "
														+"	SELECT MAX(CONCAT(DATE_FORMAT(company1.Transaction_Date, '%Y%m%d'), company1.Company_Sequence_Id)) "
														+"	FROM company_details_info company1 "
														+"	WHERE company.Company_Id = company1.Company_Id AND company1.Transaction_Date <= CURRENT_DATE() "
														+ " ) "	
							+ " LEFT JOIN m_country country ON country.Country_Id = tax.Country_Id "
							+ " LEFT JOIN location_details_info location ON location.Location_Id = tax.Location_Id "
														+ " AND  CONCAT(DATE_FORMAT(location.Transaction_Date, '%Y%m%d'), location.Location_Sequence_Id) =  ( "
														+ "	SELECT MAX(CONCAT(DATE_FORMAT(location1.Transaction_Date, '%Y%m%d'), location1.Location_Sequence_Id)) "
														+ "	FROM location_details_info location1 "
														+ "	WHERE location.Location_Id = location1.Location_Id AND location1.Transaction_Date <= CURRENT_DATE() "
														+ "	) "
								+ " WHERE  customer.Is_Active = 'Y' "
								+ " AND company.Is_Active = 'Y' "
								+ " AND location.Status= 'Y' "
								+ " AND  CONCAT(DATE_FORMAT(tax.Transaction_Date, '%Y%m%d'), tax.Tax_Sequence_Id) =  ( "
								+"	SELECT MAX(CONCAT(DATE_FORMAT(tax1.Transaction_Date, '%Y%m%d'), tax1.Tax_Sequence_Id)) "
								+"	FROM define_professional_tax tax1 "
								+"	WHERE tax.Tax_Unique_Id = tax1.Tax_Unique_Id AND tax1.Transaction_Date <= CURRENT_DATE() "
								+"	) ";
			
		if(paramRules.getCustomerId() != null && paramRules.getCustomerId() > 0){
			hqlQuery += "  AND  tax.Customer_Id = "+paramRules.getCustomerId();
		}
		
		if(paramRules.getCompanyId() != null && paramRules.getCompanyId() > 0){
			hqlQuery += "  AND  tax.Company_Id = "+paramRules.getCompanyId();
		}
		
		if(paramRules.getCountryId() != null && paramRules.getCountryId() > 0){
			hqlQuery += " AND tax.Country_Id = "+paramRules.getCountryId();
		}
		
		if(paramRules.getLocationId() != null && paramRules.getLocationId() > 0 ){
			hqlQuery += " AND tax.Location_Id = "+paramRules.getLocationId();
		}
		
		hqlQuery += " GROUP BY tax.Professional_Tax_Id ORDER BY customer.Customer_Name asc";
		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){
				for (Object customer : queryList) {
					Object[] obj = (Object[]) customer;
					taxRules = new DefineProfessionalTaxVo();
					
					taxRules.setCustomerName((String)obj[0]);
					taxRules.setCompanyName((String)obj[1]);
					taxRules.setCountryName((String)obj[2]);
					taxRules.setLocationName((String)obj[3]);
					taxRules.setProfessionalTaxId((Integer)obj[4]);
					
					returnList.add(taxRules);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getProfessionalTaxListBySearch()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getProfessionalTaxListBySearch()  ::   "+returnList);
		return returnList;
	}

	/*
	 * This method will be used to get the professional tax by given professionalTaxId
	 */
	@Override
	public List<DefineProfessionalTaxInfoVo> getPTRulesListByProfessionalTaxId(Integer professionalTaxId) {
		log.info("Entered in  getPTRulesListByProfessionalTaxId()  ::   professionalTaxId = "+professionalTaxId);
		
		Session session = sessionFactory.getCurrentSession();
		DefineProfessionalTaxInfoVo tax = new DefineProfessionalTaxInfoVo();
		List<DefineProfessionalTaxInfoVo> returnList = new ArrayList<DefineProfessionalTaxInfoVo>();
		
		String  hqlQuery = " SELECT info.Professional_Tax_Id, info.Tax_Sequence_Id, info.Professional_Tax_Info_Id, "
							+ " info.Slab_From, info.Slab_To, info.Amount, info.PT_Amount, "
							+ " info.PT_Month_Based_On_Frequency, info.Tax_Unique_Id "
							+ " FROM define_professional_tax tax "							
							+ " LEFT JOIN define_professional_tax_info AS info ON tax.Professional_Tax_Id = info.Professional_Tax_Id "
								+ " WHERE 1 = 1 "
								/*+ " AND CONCAT(DATE_FORMAT(tax.Transaction_Date, '%Y%m%d'), tax.Tax_Sequence_Id) =  ( "
								+"	SELECT MAX(CONCAT(DATE_FORMAT(tax1.Transaction_Date, '%Y%m%d'), tax1.Tax_Sequence_Id)) "
								+"	FROM define_professional_tax tax1 "
								+"	WHERE tax.Professional_Tax_Id = tax1.Professional_Tax_Id AND tax1.Transaction_Date <= CURRENT_DATE() "
								+"	) "*/
								+ " AND info.Professional_Tax_Id = "+professionalTaxId;
		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			List rulesList = query.list();

			for (Object object : rulesList) {
				Object[] obj = (Object[]) object;
				tax = new DefineProfessionalTaxInfoVo();
				
				tax.setProfessionalTaxId((Integer)obj[0]);
				tax.setTaxSequenceId((Integer)obj[1]);
				tax.setProfessionalTaxInfoId((Integer)obj[2]);
				tax.setSlabFrom((String)obj[3]);
				tax.setSlabTo((String)obj[4]);
				tax.setAmount((String)obj[5]);
				tax.setPtAmount((String)obj[6]);
				tax.setPtMonth((String)obj[7]);
				tax.setTaxUniqueId((Integer)obj[8]);
				
				returnList.add(tax);
			}	
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from  getPTRulesListByProfessionalTaxId()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from  getPTRulesListByProfessionalTaxId()  ::   "+returnList);
		return returnList;
	}
	

	/*
	 * This method will be used to get the list of professional tax rules for a given professionalTaxId
	 */
	@Override
	public List<DefineProfessionalTaxVo> getProfessionalTaxByProfessionalTaxId(Integer professionalTaxId) {
		log.info("Entered in  getProfessionalTaxByProfessionalTaxId()  ::   professionalTaxId = "+professionalTaxId);
		
		Session session = sessionFactory.getCurrentSession();
		DefineProfessionalTaxVo tax = new DefineProfessionalTaxVo();
		List<DefineProfessionalTaxVo> returnList = new ArrayList<DefineProfessionalTaxVo>();
		
		String  hqlQuery = " SELECT tax.Customer_Id, tax.Company_Id,  tax.Professional_Tax_Id, tax.Country_Id, "
							+ "	tax.State_Id,  tax.Transaction_Date, tax.Status, tax.Frequency, tax.Define_By_State, "
							+ " tax.Define_By_State_And_City, tax.City, tax.Location_Id, tax.Tax_unique_Id "
							+ " FROM  define_professional_tax tax"
							+ " WHERE 1 = 1   "
							/*+ " AND CONCAT(DATE_FORMAT(tax.Transaction_Date, '%Y%m%d'), tax.Tax_Sequence_Id) =  ( "
							+"	SELECT MAX(CONCAT(DATE_FORMAT(tax1.Transaction_Date, '%Y%m%d'), tax1.Tax_Sequence_Id)) "
							+"	FROM define_professional_tax tax1 "
							+"	WHERE tax.Professional_Tax_Id = tax1.Professional_Tax_Id AND tax1.Transaction_Date <= CURRENT_DATE() "
							+"	) "*/
							+ " AND tax.Professional_Tax_Id = "+professionalTaxId;
		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			List lwfList = query.list();

			for (Object object : lwfList) {
				Object[] obj = (Object[]) object;
				tax = new DefineProfessionalTaxVo();
				tax.setCustomerId((Integer)obj[0]);
				tax.setCompanyId((Integer)obj[1]);
				tax.setProfessionalTaxId((Integer)obj[2]);
				tax.setCountryId((Integer)obj[3]);
				tax.setStateId((Integer)obj[4]);
				tax.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[5]));
				tax.setStatus((String)obj[6]);
				tax.setFrequency((String)obj[7]);
				tax.setDefineByState(((String)obj[8]).equalsIgnoreCase("Yes") ? true : false);
				tax.setDefineByStateAndCity(((String)obj[9]).equalsIgnoreCase("Yes") ? true : false);
				tax.setCity((String)obj[10]);
				tax.setLocationId((Integer)obj[11]);
				tax.setTaxUniqueId((Integer)obj[12]);
				
				returnList.add(tax);
			}	
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from  getProfessionalTaxByProfessionalTaxId()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from  getProfessionalTaxByProfessionalTaxId()  ::   "+returnList);
		return returnList;
	}

	/*
	 * This method will be used to get transaction dates list of LWF rules with sequence id
	 */
	@Override
	public List<SimpleObject> getTransactionListForProfessionalTax(Integer customerId, Integer companyId, Integer taxUniqueId) {
		log.info("Entered in getTransactionListForProfessionalTax()  ::   customerId = "+customerId+" , companyId = "+companyId+" , professionalTaxId = "+taxUniqueId);
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List datesList = session.createSQLQuery("SELECT Professional_Tax_Id AS id ,CONCAT(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),' - ',Tax_Sequence_Id) AS name FROM  define_professional_tax info  WHERE info.Customer_Id = "+customerId+" AND info.Company_Id = "+companyId+" AND info.Tax_Unique_Id = "+taxUniqueId+" ORDER BY info.Transaction_Date, info.Tax_Sequence_Id asc").list();
			
			for (Object transDates  : datesList) {
				Object[] transaction = (Object[]) transDates;
				
				SimpleObject object = new SimpleObject();
				object.setId((Integer)transaction[0]);
				object.setName(transaction[1]+"");
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getTransactionListForProfessionalTax()  ::   "+transactionList);
		}
		
		log.info("Exiting from getTransactionListForProfessionalTax()  ::   "+transactionList);
		return transactionList;
	}


	/*********** Professional Tax ends ***********/
	
	/*********** Budget Start ************/
	
	/*
	 * This method will be used to get the budget details record based on given budgetDetailsId
	 */
	@Override
	public List<DefineBudgetDetailsVo> getBudgetDetailsByBudgetDetailsId(Integer budgetDetailsId) {
		log.info("Entered in  getBudgetDetailsByCompanyId()  ::   budgetDetailsId = "+budgetDetailsId);
		
		Session session = sessionFactory.getCurrentSession();
		DefineBudgetDetailsVo budgetVo = new DefineBudgetDetailsVo();
		List<DefineBudgetDetailsVo> returnList = new ArrayList<DefineBudgetDetailsVo>();
		List<DefineBudgetBasedOnSkillVo> skillList = new ArrayList<>();
		
		String  hqlQuery = " SELECT budget.Customer_Id, budget.Company_Id,  budget.Budget_Id, budget.Country_Id, "
							+ "	budget.Budget_Code, info.Transaction_Date, info.Budget_Status, info.Approval_Status, "
							+ " info.Budget_Name, info.location_Id, info.Department_Id, info.Has_Calendar_Year, info.Has_Financial_Year, "
							+ " info.Budget_Year, info.Budget_Type, info.Budget_Frequency, info.Has_Regular_Wages, info.Has_Bonus, "
							+ " info.Has_Overtime_Wages, info.Has_Loans, info.Has_Additional_Allowances, info.Has_Others, info.Budget_Sequence_Id, "
							+ " info.Budget_Details_Id  "
							+ " FROM  define_budget_details budget"
							+ " LEFT JOIN define_budget_details_info info ON budget.Budget_Id = info.Budget_Id "
							+ " WHERE 1 = 1 "
							/*+ " AND CONCAT(DATE_FORMAT(info.Transaction_Date, '%Y%m%d'), info.Budget_Sequence_Id) =  ( "
								+"	SELECT MAX(CONCAT(DATE_FORMAT(info1.Transaction_Date, '%Y%m%d'), info1.Budget_Sequence_Id)) "
								+"	FROM define_budget_details_info info1 "
								+"	WHERE info.Budget_Id = info1.Budget_Id AND info1.Transaction_Date <= CURRENT_DATE() "
								+"	) " */
							+ " AND info.Budget_Details_Id =  "+budgetDetailsId;

		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			List budgetList = query.list();

			for (Object object : budgetList) {
				Object[] obj = (Object[]) object;
				budgetVo = new DefineBudgetDetailsVo();
				budgetVo.setCustomerId((Integer)obj[0]);
				budgetVo.setCompanyId((Integer)obj[1]);
				budgetVo.setBudgetId((Integer)obj[2]);
				budgetVo.setCountryId((Integer)obj[3]);
				budgetVo.setBudgetCode((String)obj[4]);
				budgetVo.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[5]));
				budgetVo.setBudgetStatus((String)obj[6]);
				budgetVo.setApprovalStatus((String)obj[7]);
				budgetVo.setBudgetName((String)obj[8]);
				budgetVo.setLocationId((Integer)obj[9]);
				budgetVo.setDepartmentId((Integer)obj[10]);
				budgetVo.setHasCalendarYear(((String)obj[11]).equalsIgnoreCase("Y")? true : false);
				budgetVo.setHasFinancialYear(((String)obj[12]).equalsIgnoreCase("Y")? true : false);
				budgetVo.setConsiderYear(budgetVo.getHasCalendarYear() == true ? "Calendar": budgetVo.getHasFinancialYear() == true ? "Financial":null);
				budgetVo.setBudgetYear((String)obj[13]);
				budgetVo.setBudgetType((String)obj[14]);
				budgetVo.setBudgetFrequency((String)obj[15]);
				budgetVo.setHasRegularWages(((String)obj[16]).equalsIgnoreCase("Y")? true : false);
				budgetVo.setHasBonus(((String)obj[17]).equalsIgnoreCase("Y")? true : false);
				budgetVo.setHasOvertimeWages(((String)obj[18]).equalsIgnoreCase("Y")? true : false);
				budgetVo.setHasLoans(((String)obj[19]).equalsIgnoreCase("Y")? true : false);
				budgetVo.setHasAdditionalAllowance(((String)obj[20]).equalsIgnoreCase("Y")? true : false);
				budgetVo.setHasOther(((String)obj[21]).equalsIgnoreCase("Y")? true : false);
				budgetVo.setBudgetSequenceId((Integer)obj[22]);
				budgetVo.setBudgetDetailsId((Integer)obj[23]);
				
				System.out.println("Budget Vo "+budgetVo);
				
				String budgetSkill = " SELECT skill.Skill_Type, skill.Head_Count, skill.Amount, skill.Currency_Id,  currency.Currency "
						+ " FROM define_budget_based_on_skill skill  "
						+ " LEFT JOIN m_currency currency ON skill.Currency_Id = currency.Currency_Id "
						+ " WHERE  1 = 1 "
						+ " AND skill.Customer_Id = "+budgetVo.getCustomerId()
						+ " AND skill.Company_Id =  "+budgetVo.getCompanyId()
						+ " AND skill.Budget_Details_Id =  "+budgetDetailsId;
				
				SQLQuery skillQuery = session.createSQLQuery(budgetSkill);
				List skillQueryList = skillQuery.list();
				
				for (Object skill : skillQueryList) {
					Object[] obj1 = (Object[]) skill;
					DefineBudgetBasedOnSkillVo skillVo = new DefineBudgetBasedOnSkillVo();
					skillVo.setSkillType((String)obj1[0]);
					skillVo.setHeadCount((Integer)obj1[1]);
					skillVo.setAmount((String)obj1[2]);
					skillVo.setCurrencyId((Integer)obj1[3]);
					skillVo.setCurrencyName((String)obj1[4]);
					
					skillList.add(skillVo);
				}
				
				budgetVo.setBudgetSkillsList(skillList);
				returnList.add(budgetVo);
			}	
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from  getBudgetDetailsByCompanyId()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from  getBudgetDetailsByCompanyId()  ::   "+returnList);
		return returnList;
	}
	
	/*
	 * This method will be used to save the budget details
	 */
	@Override
	public Integer saveBudget(DefineBudgetDetailsVo paramBudget) {
		log.info("Entered in saveBudget  ::  "+paramBudget);
		
		Session session = sessionFactory.getCurrentSession(); ; 
		Integer budgetId = 0;
		Integer budgetDetailsId = 0;
		BigInteger sequenceId = new BigInteger("0");
		BigInteger uniqueId = new BigInteger("0");
		DefineBudgetDetails budget = new DefineBudgetDetails();
		DefineBudgetDetailsInfo budgetDetails = new DefineBudgetDetailsInfo();
		DefineBudgetBasedOnSkill skillBasedBudget = new DefineBudgetBasedOnSkill();
		
		try{
			if(paramBudget != null && paramBudget.getBudgetId() != null && paramBudget.getBudgetId() > 0 ){
				budget = (DefineBudgetDetails) session.load(DefineBudgetDetails.class, paramBudget.getBudgetId());
			}
			
			budget.setCustomerDetails(new CustomerDetails(paramBudget.getCustomerId()));
			budget.setCompanyDetails(new CompanyDetails(paramBudget.getCompanyId()));
			budget.setCountryDetails(new MCountry(paramBudget.getCountryId()));
			budget.setBudgetCode(paramBudget.getBudgetCode().toUpperCase());
			budget.setModifiedBy(paramBudget.getModifiedBy());
			budget.setModifiedDate(new Date());
			
			if(paramBudget != null && paramBudget.getBudgetId() != null && paramBudget.getBudgetId() > 0 ){
				session.update(budget);
				budgetId = paramBudget.getBudgetId();
			}else{
				budget.setCreatedBy(paramBudget.getCreatedBy());				
				budget.setCreatedDate(new Date());	
				budgetId = (Integer) session.save(budget);
			}

			session.flush();
			
			if(paramBudget != null && paramBudget.getBudgetDetailsId() != null && paramBudget.getBudgetDetailsId() > 0 ){
				budgetDetails = (DefineBudgetDetailsInfo) session.load(DefineBudgetDetailsInfo.class, paramBudget.getBudgetDetailsId());
			}
			
			budgetDetails.setCustomerDetails(new CustomerDetails(paramBudget.getCustomerId()));
			budgetDetails.setCompanyDetails(new CompanyDetails(paramBudget.getCompanyId()));
			budgetDetails.setCountryDetails(new MCountry(paramBudget.getCountryId()));
			budgetDetails.setBudgetId(new DefineBudgetDetails(budgetId));
			budgetDetails.setTransactionDate(paramBudget.getTransactionDate());
			budgetDetails.setApprovalStatus(paramBudget.getApprovalStatus());
			budgetDetails.setBudgetStatus(paramBudget.getBudgetStatus());
			budgetDetails.setBudgetName(paramBudget.getBudgetName());
			budgetDetails.setBudgetType(paramBudget.getBudgetType());
			budgetDetails.setBudgetFrequency(paramBudget.getBudgetFrequency());
			budgetDetails.setBudgetYear(paramBudget.getBudgetYear());
			budgetDetails.setLocationId(paramBudget.getLocationId());
			budgetDetails.setDepartmentId(paramBudget.getDepartmentId());
			budgetDetails.setHasCalendarYear(paramBudget.getConsiderYear().equalsIgnoreCase("Calendar") ? "Y":"N");
			budgetDetails.setHasFinancialYear(paramBudget.getConsiderYear().equalsIgnoreCase("Financial") ? "Y" : "N");
			budgetDetails.setHasRegularWages(paramBudget.getHasRegularWages() == true ? "Y" : "N");
			budgetDetails.setHasOvertimeWages(paramBudget.getHasOvertimeWages()== true ? "Y" : "N");
			budgetDetails.setHasLoans(paramBudget.getHasLoans()== true ? "Y" : "N");
			budgetDetails.setHasBonus(paramBudget.getHasBonus()== true ? "Y" : "N");
			budgetDetails.setHasAdditionalAllowances(paramBudget.getHasAdditionalAllowance() == true ? "Y" : "N");
			budgetDetails.setHasOthers(paramBudget.getHasOther() == true ? "Y" : "N");
			budgetDetails.setModifiedBy(paramBudget.getModifiedBy());
			budgetDetails.setModifiedDate(new Date());
			
			
			if(paramBudget != null && paramBudget.getBudgetDetailsId() != null && paramBudget.getBudgetDetailsId() > 0 ){
				session.update(budgetDetails);
				budgetDetailsId = paramBudget.getBudgetDetailsId();
			}else{
				budgetDetails.setCreatedBy(paramBudget.getCreatedBy());				
				budgetDetails.setCreatedDate(new Date());	
				if(budgetId != null && budgetId > 0 ){
					sequenceId  = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Budget_Sequence_Id),0) FROM `define_budget_details_info` budget"
							+ "  WHERE  budget.Budget_Id = "+budgetId +" AND budget.`Transaction_Date` = '"+DateHelper.convertDateToSQLString(paramBudget.getTransactionDate())+"' and budget.Customer_Id='"+paramBudget.getCustomerId()+"' And budget.Company_Id = '"+paramBudget.getCompanyId()+"'").list().get(0);
					
				}	
				budgetDetails.setBudgetSequenceId(sequenceId.intValue()+1);
				budgetDetailsId = (Integer) session.save(budgetDetails);
			}
			
			log.info("Budget sequence Id : "+sequenceId+" Budget Details Id : "+budgetDetailsId);
			if(paramBudget.getBudgetSkillsList() != null && paramBudget.getBudgetSkillsList().size() > 0){
				if(paramBudget != null && paramBudget.getBudgetDetailsId() != null && paramBudget.getBudgetDetailsId() > 0 ){
					Query q = session.createQuery("DELETE FROM DefineBudgetBasedOnSkill  WHERE  customerDetails = "+paramBudget.getCustomerId() +" AND companyDetails = "+paramBudget.getCompanyId() +" AND defineBudgetDetailsInfo = "+budgetDetailsId+" AND  defineBudgetDetails = "+budgetId+" AND budgetSequenceId = "+budgetDetails.getBudgetSequenceId());
					int delete = q.executeUpdate();
					//System.out.println(delete);
				}
				
				for(DefineBudgetBasedOnSkillVo skillVo : paramBudget.getBudgetSkillsList()){
					skillBasedBudget = new DefineBudgetBasedOnSkill();
					//System.out.println("Skill Vo :"+skillVo);
					skillBasedBudget.setCustomerDetails(new CustomerDetails(paramBudget.getCustomerId()));
					skillBasedBudget.setCompanyDetails(new CompanyDetails(paramBudget.getCompanyId()));
					skillBasedBudget.setCountryDetails(new MCountry(paramBudget.getCountryId()));
					skillBasedBudget.setDefineBudgetDetails(new DefineBudgetDetails(budgetId));
					skillBasedBudget.setDefineBudgetDetailsInfo(new DefineBudgetDetailsInfo(budgetDetails.getBudgetDetailsId()));
					skillBasedBudget.setBudgetSequenceId(budgetDetails.getBudgetSequenceId());
					skillBasedBudget.setSkillType(skillVo.getSkillType());
					skillBasedBudget.setHeadCount(skillVo.getHeadCount());
					skillBasedBudget.setAmount(skillVo.getAmount());
					skillBasedBudget.setCurrencyId(skillVo.getCurrencyId());
					skillBasedBudget.setModifiedBy(paramBudget.getModifiedBy());
					skillBasedBudget.setModifiedDate(new Date());
					skillBasedBudget.setCreatedBy(paramBudget.getCreatedBy());				
					skillBasedBudget.setCreatedDate(new Date());
					//System.out.println("Skill :"+skillBasedBudget);
					session.save(skillBasedBudget);
				}
			}
			
			session.flush();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			log.info("Exiting from saveBudget()  ::   "+budgetDetailsId);
		}
		log.info("Exiting from saveBudget()  ::   "+budgetDetailsId);
		return budgetDetailsId;
	}


	/*
	 * This method will be used to get the transaction dates list for budget details record based on given uniqueId
	 */
	@Override
	public List<SimpleObject> getTransactionListForBudget(DefineBudgetDetailsVo paramBudget) {
		log.info("Entered in getTransactionListForBudget()  ::   paramBudget = "+paramBudget);
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List datesList = session.createSQLQuery("SELECT Budget_Details_Id AS id ,CONCAT(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),' - ',Budget_Sequence_Id) AS name FROM  define_budget_details_info info  WHERE info.Customer_Id = "+paramBudget.getCustomerId()+" AND info.Company_Id = "+paramBudget.getCompanyId()+" AND info.Budget_Id = "+paramBudget.getBudgetId()+" ORDER BY info.Transaction_Date, info.Budget_Sequence_Id asc").list();
			
			for (Object transDates  : datesList) {
				Object[] transaction = (Object[]) transDates;
				
				SimpleObject object = new SimpleObject();
				object.setId((Integer)transaction[0]);
				object.setName(transaction[1]+"");
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getTransactionListForBudget()  ::   "+transactionList);
		}
		
		log.info("Exiting from getTransactionListForBudget()  ::   "+transactionList);
		return transactionList;
	}
	
	/*
	 * This method will be used to get the budget details list based on given data
	 */
	@Override
	public List<DefineBudgetDetailsVo> getBudgetListBySearch(DefineBudgetDetailsVo paramBudget){
		log.info("Entered in  getBudgetListBySearch()  ::   paramBudget  = "+paramBudget);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<DefineBudgetDetailsVo> returnList = new ArrayList<DefineBudgetDetailsVo>();
		DefineBudgetDetailsVo budgetVo = new DefineBudgetDetailsVo();
		
		String hqlQuery = "SELECT company.Company_Name, customer.Customer_Name,  location.Location_Name, details.Budget_Code, "
							+ "	info.Budget_Name, info.Budget_Year, info.Approval_Status, info.Budget_Details_Id"
							+ " FROM define_budget_details AS details "
							+ " LEFT JOIN define_budget_details_info info ON info.Budget_Id = details.Budget_Id "
							+ " LEFT JOIN customer_view AS customer  ON customer.Customer_Id = info.Customer_Id "
							+ " LEFT JOIN company_view AS company  ON company.Customer_Id = info.Customer_Id  AND company.Company_Id = info.Company_Id "
							+ " LEFT JOIN location_details_info location ON location.Location_Id = info.Location_Id "
														+ " AND  CONCAT(DATE_FORMAT(location.Transaction_Date, '%Y%m%d'), location.Location_Sequence_Id) =  ( "
														+ "	SELECT MAX(CONCAT(DATE_FORMAT(location1.Transaction_Date, '%Y%m%d'), location1.Location_Sequence_Id)) "
														+ "	FROM location_details_info location1 "
														+ "	WHERE location.Location_Id = location1.Location_Id AND location1.Transaction_Date <= CURRENT_DATE() "
														+ "	) "
								+ " WHERE  customer.Is_Active = 'Y' "
								+ " AND company.Is_Active = 'Y' "
								+ " AND location.Status= 'Y' "
								+ " AND  CONCAT(DATE_FORMAT(info.Transaction_Date, '%Y%m%d'), info.Budget_Sequence_Id) =  ( "
								+"	SELECT MAX(CONCAT(DATE_FORMAT(info1.Transaction_Date, '%Y%m%d'), info1.Budget_Sequence_Id)) "
								+"	FROM define_budget_details_info info1 "
								+"	WHERE info.Budget_Id = info1.Budget_Id AND info1.Transaction_Date <= CURRENT_DATE() "
								+"	) ";
			
		if(paramBudget.getCustomerId() != null && paramBudget.getCustomerId() > 0){
			hqlQuery += "  AND  info.Customer_Id = "+paramBudget.getCustomerId();
		}
		
		if(paramBudget.getCompanyId() != null && paramBudget.getCompanyId() > 0){
			hqlQuery += "  AND  info.Company_Id = "+paramBudget.getCompanyId();
		}
		
		if(paramBudget.getBudgetCode() != null && !paramBudget.getBudgetCode().isEmpty()){
			hqlQuery += "  AND  info.Budget_Code LIKE '"+paramBudget.getBudgetCode()+"%' ";
		}
		
		if(paramBudget.getBudgetName() != null && !paramBudget.getBudgetName().isEmpty()){
			hqlQuery += "  AND  info.Budget_Name LIKE '"+paramBudget.getBudgetName()+"%' ";
		}
		
		if(paramBudget.getBudgetYear() != null && !paramBudget.getBudgetYear().isEmpty()){
			hqlQuery += "  AND  info.Budget_Year = "+paramBudget.getBudgetYear();
		}
		
		if(paramBudget.getApprovalStatus() != null && !paramBudget.getApprovalStatus().isEmpty()){
			hqlQuery += "  AND  info.Approval_Status = '"+paramBudget.getApprovalStatus()+"' ";
		}
		
		hqlQuery += " GROUP BY info.Budget_Id ORDER BY customer.Customer_Name asc";
		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){
				for (Object customer : queryList) {
					Object[] obj = (Object[]) customer;
					budgetVo = new DefineBudgetDetailsVo();
					
					budgetVo.setCustomerName((String)obj[0]);
					budgetVo.setCompanyName((String)obj[1]);
					budgetVo.setLocationName((String)obj[2]);
					budgetVo.setBudgetCode((String)obj[3]);
					budgetVo.setBudgetName((String)obj[4]);
					budgetVo.setBudgetYear((String)obj[5]);
					budgetVo.setApprovalStatus((String)obj[6]);
					budgetVo.setBudgetDetailsId((Integer)obj[7]);
					
					returnList.add(budgetVo);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getBudgetListBySearch()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getBudgetListBySearch()  ::   "+returnList);
		return returnList;
		
	}
	
	/*
	 * This method will be used to validate the budget code and budget year based on given data
	 */
	@Override
	public Integer validateBudgetCodeandYear(Integer customerId, Integer companyId, String budgetCode, String budgetYear){
		log.info("Entered in  validateBudgetCodeandYear()  ::   customerId  = "+customerId+", companyId = "+companyId+", budgetCode = "+budgetCode+", budgetYear = "+budgetYear);
		
		Session session = sessionFactory.getCurrentSession();
		Integer rowCount = 0;
		List queryList = null;
		
		String hqlQuery = "";
			
		if(customerId != null && customerId > 0){
			hqlQuery += "  AND  customerDetails = "+customerId;
		}
		
		if(companyId != null && companyId > 0){
			hqlQuery += "  AND  companyDetails = "+companyId;
		}
		
		if(budgetCode != null && !budgetCode.isEmpty()){
			hqlQuery += "  AND  budgetCode = '"+budgetCode+"' ";
		}
		
		if(budgetYear != null && !budgetYear.isEmpty()){
			hqlQuery += "  AND  budgetYear = "+budgetYear;
		}
		
		hqlQuery += " GROUP BY Budget_Id";
		
		try {	
			if(budgetCode != null && !budgetCode.isEmpty()){
				 queryList = session.createQuery(" FROM DefineBudgetDetails WHERE 1 = 1 "+hqlQuery).list();
			}else if(budgetYear != null && !budgetYear.isEmpty()){
				queryList = session.createQuery(" FROM DefineBudgetDetailsInfo WHERE 1 = 1 "+hqlQuery).list();
			}
			
			if(queryList != null){
				rowCount = queryList.size();
			}
			
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from validateBudgetCodeandYear()  ::   "+rowCount);
		}
		session.flush();
		log.info("Exiting from validateBudgetCodeandYear()  ::   "+rowCount);
		return rowCount;
	}
	/*********** Budget End ***********/


	/*
	 * This method will be used to get the worker group list based on given data
	 */
	@Override
	public List<DefineWorkerGroupVo> getWorkGroupBySearch(DefineWorkerGroupVo paramWorkerGroup) {
		log.info("Entered in  getWorkGroupBySearch()  ::   paramWorkerGroup  = "+paramWorkerGroup);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<DefineWorkerGroupVo> returnList = new ArrayList<DefineWorkerGroupVo>();
		DefineWorkerGroupVo budgetVo = new DefineWorkerGroupVo();
		
		String hqlQuery = "SELECT company.Company_Name, customer.Customer_Name,  country.Country_Name, details.Group_Code, "
							+ "	details.Group_Name,  details.status, details.Worker_Group_Id"
							+ " FROM define_worker_group AS details "
							+ " LEFT JOIN customer_view AS customer  ON customer.Customer_Id = details.Customer_Id "
							+ " LEFT JOIN company_view AS company  ON company.Customer_Id = details.Customer_Id  AND company.Company_Id = details.Company_Id "
							+ " LEFT JOIN m_country AS country ON  details.Country_Id = country.Country_Id"
								+ " WHERE  customer.Is_Active = 'Y' "
								+ " AND company.Is_Active = 'Y' "
								+ " AND  CONCAT(DATE_FORMAT(details.Transaction_Date, '%Y%m%d'), details.Sequence_Id) =  ( "
									+"	SELECT MAX(CONCAT(DATE_FORMAT(details1.Transaction_Date, '%Y%m%d'), details1.Sequence_Id)) "
									+"	FROM define_worker_group AS details1 "
									+"	WHERE details.Unique_Id = details1.Unique_Id AND details1.Transaction_Date <= CURRENT_DATE() "
								+"	) ";
			
		if(paramWorkerGroup.getCustomerId() != null && paramWorkerGroup.getCustomerId() > 0){
			hqlQuery += "  AND  details.Customer_Id = "+paramWorkerGroup.getCustomerId();
		}
		
		if(paramWorkerGroup.getCompanyId() != null && paramWorkerGroup.getCompanyId() > 0){
			hqlQuery += "  AND  details.Company_Id = "+paramWorkerGroup.getCompanyId();
		}
		
		if(paramWorkerGroup.getGroupCode() != null && !paramWorkerGroup.getGroupCode().isEmpty()){
			hqlQuery += "  AND  details.Group_Code LIKE '"+paramWorkerGroup.getGroupCode()+"%' ";
		}
		
		if(paramWorkerGroup.getGroupName() != null && !paramWorkerGroup.getGroupName().isEmpty()){
			hqlQuery += "  AND  details.Group_Name LIKE '"+paramWorkerGroup.getGroupName()+"%' ";
		}
		
		if(paramWorkerGroup.getStatus() != null && !paramWorkerGroup.getStatus().isEmpty()){
			hqlQuery += "  AND  details.Status = '"+paramWorkerGroup.getStatus()+"' ";
		}
		
		hqlQuery += " GROUP BY details.Unique_Id ORDER BY customer.Customer_Name asc";
		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){
				for (Object customer : queryList) {
					Object[] obj = (Object[]) customer;
					budgetVo = new DefineWorkerGroupVo();
					
					budgetVo.setCustomerName((String)obj[0]);
					budgetVo.setCompanyName((String)obj[1]);
					budgetVo.setCountryName((String)obj[2]);
					budgetVo.setGroupCode((String)obj[3]);
					budgetVo.setGroupName((String)obj[4]);
					budgetVo.setStatus(((String)obj[5]).equalsIgnoreCase("Y") ? "Active":"Inactive");
					budgetVo.setWorkerGroupId((Integer)obj[6]);
					
					returnList.add(budgetVo);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getWorkGroupBySearch()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getWorkGroupBySearch()  ::   "+returnList);
		return returnList;
	}

	/*
	 * This method will be used to save the worker group data
	 */
	@Override
	public Integer saveWorkerGroup(DefineWorkerGroupVo paramWorkerGroup) {
		log.info("Entered in saveWorkerGroup  ::  paramWorkerGroup = "+paramWorkerGroup);
		
		Session session = sessionFactory.getCurrentSession(); ; 
		Integer workerGroupId = 0;
		BigInteger sequenceId = new BigInteger("0");
		BigInteger uniqueId = new BigInteger("0");
		DefineWorkerGroup workerGroup = new DefineWorkerGroup();
		
		try{
			
			
			if(paramWorkerGroup != null && paramWorkerGroup.getWorkerGroupId() != null && paramWorkerGroup.getWorkerGroupId() > 0 ){
				workerGroup = (DefineWorkerGroup) session.load(DefineWorkerGroup.class, paramWorkerGroup.getWorkerGroupId());
			}
			
			workerGroup.setCustomerDetails(new CustomerDetails(paramWorkerGroup.getCustomerId()));
			workerGroup.setCompanyDetails(new CompanyDetails(paramWorkerGroup.getCompanyId()));
			workerGroup.setCountryDetails(new MCountry(paramWorkerGroup.getCountryId()));
			workerGroup.setTransactionDate(paramWorkerGroup.getTransactionDate());
			workerGroup.setStatus(paramWorkerGroup.getStatus());
			workerGroup.setGroupName(paramWorkerGroup.getGroupName());
			workerGroup.setGroupCode(paramWorkerGroup.getGroupCode().toUpperCase());
			//workerGroup.setWageRateId(paramWorkerGroup.getWageRateId());
			workerGroup.setHolidayCalendarId(paramWorkerGroup.getHolidayCalendarId());
			workerGroup.setShiftPatternId(paramWorkerGroup.getShiftPatternId());
			workerGroup.setTimeRuleId(paramWorkerGroup.getTimeRuleId());
			workerGroup.setOverTimeRuleId(paramWorkerGroup.getOverTimeRuleId());
			workerGroup.setModifiedBy(paramWorkerGroup.getModifiedBy());
			workerGroup.setModifiedDate(new Date());
			
			
			if(paramWorkerGroup != null && paramWorkerGroup.getWorkerGroupId() != null && paramWorkerGroup.getWorkerGroupId() > 0 ){
				session.update(workerGroup);
				workerGroupId = paramWorkerGroup.getWorkerGroupId();
			}else{
				workerGroup.setCreatedBy(paramWorkerGroup.getCreatedBy());				
				workerGroup.setCreatedDate(new Date());	
				if(paramWorkerGroup.getUniqueId() != null && paramWorkerGroup.getUniqueId() > 0 ){
					sequenceId  = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Sequence_Id),0) FROM define_worker_group "
							+ "  WHERE Unique_Id = "+paramWorkerGroup.getUniqueId() +" AND Transaction_Date = '"+DateHelper.convertDateToSQLString(paramWorkerGroup.getTransactionDate())+"' AND Customer_Id='"+paramWorkerGroup.getCustomerId()+"' AND Company_Id = '"+paramWorkerGroup.getCompanyId()+"'").list().get(0);
					workerGroup.setUniqueId(paramWorkerGroup.getUniqueId());	
				}else{
					uniqueId = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Unique_Id),0) as id FROM define_worker_group ").list().get(0);
					workerGroup.setUniqueId(uniqueId.intValue()+1 );	
				}
				workerGroup.setSequenceId(sequenceId.intValue()+1);
				workerGroupId = (Integer) session.save(workerGroup);
			}
			
			log.info("Sequence Id : "+sequenceId+" Worker Group Id : "+workerGroupId);
			
			
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			log.info("Exiting from saveWorkerGroup()  ::  workerGroupId =  "+workerGroupId);
		}
		log.info("Exiting from saveWorkerGroup()  ::  workerGroupId =  "+workerGroupId);
		return workerGroupId;
	}

	/*
	 * This method will be used to get the worker group details record based on given workerGroupId
	 */
	@Override
	public List<DefineWorkerGroupVo> getWorkerGroupById(Integer workerGroupId) {
		log.info("Entered in  getWorkerGroupById()  ::   workerGroupId = "+workerGroupId);
		
		Session session = sessionFactory.getCurrentSession();
		DefineWorkerGroupVo workerGroupVo = new DefineWorkerGroupVo();
		List<DefineWorkerGroupVo> returnList = new ArrayList<DefineWorkerGroupVo>();
		
		String  hqlQuery = " SELECT groups.Customer_Id, groups.Company_Id,  groups.Unique_Id, groups.Country_Id, "
							+ "	groups.Group_Code, groups.Transaction_Date, groups.Status, groups.Group_Name, "
							+ " groups.Holiday_Calendar_Id, groups.Shift_Pattern_Id, groups.Time_Rule_Id, groups.Over_Time_Rule_Id, "
							+ " groups.Sequence_Id, groups.Worker_Group_Id  "
							+ " FROM  define_worker_group AS groups"
							+ " WHERE 1 = 1 "
							+ " AND groups.Worker_Group_Id =  "+workerGroupId;

		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			List budgetList = query.list();

			for (Object object : budgetList) {
				Object[] obj = (Object[]) object;
				
				workerGroupVo = new DefineWorkerGroupVo();
				
				workerGroupVo.setCustomerId((Integer)obj[0]);
				workerGroupVo.setCompanyId((Integer)obj[1]);
				workerGroupVo.setUniqueId((Integer)obj[2]);
				workerGroupVo.setCountryId((Integer)obj[3]);
				workerGroupVo.setGroupCode((String)obj[4]);
				workerGroupVo.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[5]));
				workerGroupVo.setStatus((String)obj[6]);
				workerGroupVo.setGroupName((String)obj[7]);
				//workerGroupVo.setWageRateId((Integer)obj[8]);
				workerGroupVo.setHolidayCalendarId((Integer)obj[8]);
				workerGroupVo.setShiftPatternId((Integer)obj[9]);
				workerGroupVo.setTimeRuleId((Integer)obj[10]);
				workerGroupVo.setOverTimeRuleId((Integer)obj[11]);
				workerGroupVo.setSequenceId((Integer)obj[12]);
				workerGroupVo.setWorkerGroupId((Integer)obj[13]);
				
				returnList.add(workerGroupVo);
			}	
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from  getWorkerGroupById()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from  getWorkerGroupById()  ::   "+returnList);
		return returnList;
	}


	/*
	 * This method will be used to get the transaction dates list for worker group based on given uniqueId
	 */
	@Override
	public List<SimpleObject> getTransactionDatesListForWorkerGroup(Integer customerId, Integer companyId, Integer uniqueId) {
		log.info("Entered in getTransactionDatesListForWorkerGroup()  ::   customerId = "+customerId+" , companyId = "+companyId+", uniqueId = "+uniqueId);
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List datesList = session.createSQLQuery("SELECT Worker_Group_Id AS id ,CONCAT(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),' - ',Sequence_Id) AS name FROM  define_worker_group info  WHERE info.Customer_Id = "+customerId+" AND info.Company_Id = "+companyId+" AND info.Unique_Id = "+uniqueId+" ORDER BY info.Transaction_Date, info.Sequence_Id asc").list();
			
			for (Object transDates  : datesList) {
				Object[] transaction = (Object[]) transDates;
				
				SimpleObject object = new SimpleObject();
				object.setId((Integer)transaction[0]);
				object.setName(transaction[1]+"");
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getTransactionDatesListForWorkerGroup()  ::   "+transactionList);
		}
		
		log.info("Exiting from getTransactionDatesListForWorkerGroup()  ::   "+transactionList);
		return transactionList;
	}


	/*
	 * This method will be used to validate the worker group code
	 */
	@Override
	public Integer validateWorkerGroupCode(Integer customerId, Integer companyId, String groupCode) {
		log.info("Entered in  validateWorkerGroupCode()  ::   customerId  = "+customerId+", companyId = "+companyId+", groupCode = "+groupCode);
		
		Session session = sessionFactory.getCurrentSession();
		Integer rowCount = 0;
		List queryList = null;
		
		String hqlQuery = "";
			
		if(customerId != null && customerId > 0){
			hqlQuery += "  AND  customerDetails = "+customerId;
		}
		
		if(companyId != null && companyId > 0){
			hqlQuery += "  AND  companyDetails = "+companyId;
		}
		
		if(groupCode != null && !groupCode.isEmpty()){
			hqlQuery += "  AND  groupCode = '"+groupCode+"' ";
		}
		
		hqlQuery += " GROUP BY Unique_Id";
		
		try {	
			
			queryList = session.createQuery(" FROM DefineWorkerGroup WHERE 1 = 1 "+hqlQuery).list();
			
			
			if(queryList != null){
				rowCount = queryList.size();
			}
			
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from validateWorkerGroupCode()  ::   "+rowCount);
		}
		session.flush();
		log.info("Exiting from validateWorkerGroupCode()  ::   "+rowCount);
		return rowCount;
	}

}
