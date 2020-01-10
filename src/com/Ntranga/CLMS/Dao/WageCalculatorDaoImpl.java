package com.Ntranga.CLMS.Dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.WageCalculatorSaveVo;
import com.Ntranga.CLMS.vo.WageCalculatorVo;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.VendorDetails;
import com.Ntranga.core.CLMS.entities.WageCalculator;
import com.Ntranga.core.CLMS.entities.WorkerDetails;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import common.Logger;

@Transactional
@Repository("wageCalculatorDao")
public class WageCalculatorDaoImpl implements WageCalculatorDao  {

	private static Logger log = Logger.getLogger(WageCalculatorDaoImpl.class);
	
	
	@Autowired
	private SessionFactory sessionFactory;

	private @Value("#{db['db.schemaName']}")
	String dbSchemaName;

	@Override
	public List<WageCalculatorVo> getWageCalculatorList(String jsonSearchContent) {
		// TODO Auto-generated method stub
		List<WageCalculatorVo> wageCalculatorVoList = new ArrayList<WageCalculatorVo>();	
		Session session = sessionFactory.getCurrentSession();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(jsonSearchContent);
			String schemaName = null;
			if(!jo.get("schema").isJsonNull() && !jo.get("schema").getAsString().isEmpty()){				
				schemaName = jo.get("schema").getAsString();
            }else{
            	schemaName = dbSchemaName;
            }
			
			
			String query = " SELECT wdi.`customer_id`, wdi.`company_id`, wd.`Vendor_Id`,wd.`Worker_id`,emp,emp_name,wjd.`Work_Skill`,jcd.`Job_Code`,COUNT(emp) AS payable_days,SUM(`OTHours`) AS OT_Hours,COALESCE(wc.`Base_Rate_Per_Day` ,wrdi.`Base_Rate`) AS baseRatePerDay,COALESCE(wc.`Over_Time_Rate_Per_Hour` ,`Overtime_Rate`) AS overTimeRatePerHour,"
							+" wc.`Daily_Rate_Per_Day`,wc.`Additional_Allowance`,wc.`One_Time_Earnings`,wc.`Total_Earnings`,wc.`Pf`,wc.`Esi`,wc.`Lwf`,wc.`Pt`,wc.`Advance`,wc.`Damage_Or_Loss`,wc.`Fines`,wc.`Total_Deductions`,wc.`Gross_Salaray`,wc.`Net_Salary` "
							+" FROM "+schemaName+".`labor_time_report` ltr INNER JOIN `worker_details` wd ON (ltr.emp = wd.`Worker_code`) "
							+" INNER JOIN `worker_details_info` wdi ON (wdi.`Worker_id` = wd.`Worker_id`) " 
							+" LEFT JOIN `work_job_details` wjd ON (wjd.`Worker_id` = wd.`Worker_id` AND CONCAT(DATE_FORMAT(wjd.transaction_date, '%Y%m%d'), LPAD(wjd.`Sequence_Id`, 2, '0')) =  ( "
							+" SELECT MAX(CONCAT(DATE_FORMAT(wjd1.transaction_date, '%Y%m%d'), LPAD(wjd1.Sequence_Id, 2, '0'))) FROM `work_job_details` wjd1 "  
							+" WHERE wjd.`Worker_id` = wjd1.`Worker_id` AND wjd1.transaction_date <= CURRENT_DATE() ) ) " 
							
							+" LEFT JOIN `wage_rate_details_info` wrdi ON (wjd.`Vendor_Id` = wrdi.`Vendor_Id` AND wrdi.`Job_Category` =  wjd.`Work_Skill` AND CONCAT(DATE_FORMAT(wrdi.transaction_date, '%Y%m%d'), LPAD(wrdi.`Wage_Rate_Sequence_Id`, 2, '0')) =  ( "
							+" SELECT MAX(CONCAT(DATE_FORMAT(wrdi1.transaction_date, '%Y%m%d'), LPAD(wrdi1.`Wage_Rate_Sequence_Id`, 2, '0'))) FROM `wage_rate_details_info` wrdi1 "  
							+" WHERE wrdi.`Wage_Rate_Id` = wrdi1.`Wage_Rate_Id` AND wrdi1.transaction_date <= CURRENT_DATE() ) ) "
							
							
							+" LEFT JOIN `job_code_details` jcd ON (jcd.`Job_Code_Id` = wjd.`Job_Name`) "
							+" LEFT JOIN wage_calculator wc ON ( wdi.`customer_id`= wc.`Customer_Id` AND wdi.`company_id`=wc.`Company_Id` AND wc.`Vendor_Id`= wrdi.`Vendor_Id` AND wd.`Worker_id` = wc.`Worker_id`  AND YEAR(`Business_Date`)=wc.`At_Year` AND MONTH(`Business_Date`)=wc.`At_Month` AND wc.`Sequence_id` = (SELECT MAX(wc1.`Sequence_id`) FROM `wage_calculator` wc1 WHERE  wc1.`customer_id`= wc.`Customer_Id` AND wc1.`company_id`=wc.`Company_Id` AND wc.`Vendor_Id`= wc1.`Vendor_Id` AND wc1.`Worker_id` = wc.`Worker_id`  AND wc1.`At_Year`=wc.`At_Year` AND wc1.`At_Month`=wc.`At_Month` )) "
							
							+" WHERE CONCAT(DATE_FORMAT(wdi.transaction_date, '%Y%m%d'), LPAD(wdi.`Sequence_Id`, 2, '0')) =  ( "
							+" SELECT MAX(CONCAT(DATE_FORMAT(wdi1.transaction_date, '%Y%m%d'), LPAD(wdi1.Sequence_Id, 2, '0'))) FROM `worker_details_info` wdi1 "  
							+" WHERE wdi.`Worker_id` = wdi1.`Worker_id` AND wdi1.transaction_date <= CURRENT_DATE() ) ";
		
			
			if(!(jo.get("customerId").toString().equalsIgnoreCase("null")) && !(jo.get("customerId").getAsString().equalsIgnoreCase("null")) && jo.get("customerId").getAsInt() > 0){
				query += " AND  wd.`customer_id`="+jo.get("customerId").getAsInt();
            }
			if(!(jo.get("companyId").toString().equalsIgnoreCase("null")) && !(jo.get("companyId").getAsString().equalsIgnoreCase("null")) && jo.get("companyId").getAsInt() > 0){
				query += " AND  wd.`company_id`="+jo.get("companyId").getAsInt();
			}			
			if(!(jo.get("vendorId").toString().equalsIgnoreCase("null")) && !(jo.get("vendorId").getAsString().equalsIgnoreCase("null")) && jo.get("vendorId").getAsInt() > 0){
				query += " AND  wd.`Vendor_Id`="+jo.get("vendorId").getAsInt();
            }
			if(!(jo.get("workerId").toString().equalsIgnoreCase("null")) && !(jo.get("workerId").getAsString().equalsIgnoreCase("null")) && jo.get("workerId").getAsInt() > 0){
				query += " AND  wd.`Worker_id`="+jo.get("workerId").getAsInt();
            }			
			if(!(jo.get("year").toString().equalsIgnoreCase("null")) && !(jo.get("year").getAsString().equalsIgnoreCase("null")) && jo.get("year").getAsInt() > 0  && !(jo.get("month").toString().equalsIgnoreCase("null")) && !(jo.get("month").getAsString().equalsIgnoreCase("null")) && jo.get("month").getAsInt() > 0){
				query += " AND  YEAR(`Business_Date`)="+jo.get("year").getAsInt()+" AND  MONTH(`Business_Date`)="+jo.get("month").getAsInt() ;
            }
			
			query+= " GROUP BY  emp,YEAR(`Business_Date`),MONTH(`Business_Date`) ORDER BY emp,emp_name" ;
			
			List tempList = session.createSQLQuery(query).list();			
			for(Object o : tempList){
				Object[] obj = (Object[]) o;
				WageCalculatorVo calculatorVo = new WageCalculatorVo();	
					calculatorVo.setCustomerDetailsId((Integer)obj[0]  );
				if(obj[1] != null)
					calculatorVo.setCompanyDetailsId((Integer)obj[1]  );
				if(obj[2] != null)
					calculatorVo.setVendorDetailsId((Integer)obj[2]  );
				if(obj[3] != null)
					calculatorVo.setWorkerDetailsId((Integer)obj[3]  );
				if(obj[4] != null)
					calculatorVo.setWorkerCode(obj[4]+"");
				if(obj[5] != null)
					calculatorVo.setWorkerName(obj[5]+"");
				if(obj[6] != null)
					calculatorVo.setWorkerSkill(obj[6]+"");
				if(obj[7] != null)
					calculatorVo.setJobCode(obj[7]+"");
				if(obj[8] != null)
					calculatorVo.setPayableDays(Integer.valueOf(obj[8]+""));
				if(obj[9] != null)
					calculatorVo.setOverTimeHours(Double.valueOf(obj[9]+""));
				if(obj[10] != null)
					calculatorVo.setBaseRatePerDay(Double.valueOf(obj[10]+""));
				if(obj[11] != null)
					calculatorVo.setOverTimeRatePerHour(Double.valueOf(obj[11]+""));
				if(obj[12] != null)
					calculatorVo.setDailyRatePerDay(Double.valueOf(obj[12]+""));
				if(obj[13] != null)
					calculatorVo.setAdditionalAllowance(Double.valueOf(obj[13]+""));
				if(obj[14] != null)
					calculatorVo.setOneTimeEarnings(Double.valueOf(obj[14]+""));
				if(obj[15] != null)
					calculatorVo.setTotalEarnings(Double.valueOf(obj[15]+""));
				if(obj[16] != null)
					calculatorVo.setPf(Double.valueOf(obj[16]+""));
				if(obj[17] != null)
					calculatorVo.setEsi(Double.valueOf(obj[17]+""));
				if(obj[18] != null)
					calculatorVo.setLwf(Double.valueOf(obj[18]+""));
				if(obj[19] != null)
					calculatorVo.setPt(Double.valueOf(obj[19]+""));
				if(obj[20] != null)
					calculatorVo.setAdvance(Double.valueOf(obj[20]+""));
				if(obj[21] != null)
					calculatorVo.setDamageOrLoss(Double.valueOf(obj[21]+""));
				if(obj[22] != null)
					calculatorVo.setFines(Double.valueOf(obj[22]+""));
				if(obj[23] != null)
					calculatorVo.setTotalDeductions(Double.valueOf(obj[23]+""));
				if(obj[24] != null)
					calculatorVo.setGrossSalaray(Double.valueOf(obj[24]+""));
				if(obj[25] != null)
					calculatorVo.setNetSalary(Double.valueOf(obj[25]+""));
				
				wageCalculatorVoList.add(calculatorVo);
			}	
			
		}catch(Exception e){
			e.printStackTrace();
		}		
		return wageCalculatorVoList;
	}


	@Override
	public Integer saveWageCalculator(WageCalculatorSaveVo wageCalculatorSaveVo) {
		// TODO Auto-generated method stub
		Integer id = 0;
		Session session = sessionFactory.getCurrentSession();
		try{
			for(WageCalculatorVo calculatorVo : wageCalculatorSaveVo.getWageCalculatorVo()){
				WageCalculator calculator = new WageCalculator();
				calculator.setCustomerDetails(new CustomerDetails(calculatorVo.getCustomerDetailsId()));
				calculator.setCompanyDetails(new CompanyDetails(calculatorVo.getCompanyDetailsId()));
				calculator.setVendorDetails(new VendorDetails(calculatorVo.getVendorDetailsId()));
				calculator.setWorkerDetails(new WorkerDetails(calculatorVo.getWorkerDetailsId()));
				calculator.setTransactionDate(new Date());				
				calculator.setAtYear(wageCalculatorSaveVo.getYear());
				calculator.setAtMonth(wageCalculatorSaveVo.getMonth());
				calculator.setPayableDays(calculatorVo.getPayableDays());
				calculator.setOverTimeHours(calculatorVo.getOverTimeHours());
				calculator.setDailyRatePerDay(calculatorVo.getDailyRatePerDay());
				calculator.setBaseRatePerDay(calculatorVo.getBaseRatePerDay());
				calculator.setOverTimeRatePerHour(calculatorVo.getOverTimeRatePerHour());
				calculator.setAdditionalAllowance(calculatorVo.getAdditionalAllowance());
				calculator.setOneTimeEarnings(calculatorVo.getOneTimeEarnings());
				calculator.setTotalEarnings(calculatorVo.getTotalEarnings());
				calculator.setPf(calculatorVo.getPf());
				calculator.setEsi(calculatorVo.getEsi());
				calculator.setLwf(calculatorVo.getLwf());
				calculator.setPt(calculatorVo.getPt());
				calculator.setAdvance(calculatorVo.getAdvance());
				calculator.setDamageOrLoss(calculatorVo.getDamageOrLoss());
				calculator.setFines(calculatorVo.getFines());
				calculator.setTotalDeductions(calculatorVo.getTotalDeductions());
				calculator.setGrossSalaray(calculatorVo.getGrossSalaray());
				calculator.setNetSalary(calculatorVo.getNetSalary());
				calculator.setCreatedBy(wageCalculatorSaveVo.getCreatedBy());
				calculator.setCreatedDate(new Date());
				calculator.setModifiedBy(wageCalculatorSaveVo.getModifiedBy());
				calculator.setModifiedDate(new Date());
				List tempList = session.createQuery(" from WageCalculator where atYear="+wageCalculatorSaveVo.getYear()+" and atMonth="+wageCalculatorSaveVo.getMonth()+" AND workerDetails="+calculatorVo.getWorkerDetailsId()+" ").list();				
				calculator.setSequenceId(tempList.size()+1);				
				id = (Integer) session.save(calculator);
				System.out.println(id +" :: WageCalculatorId");
			}
			session.flush();
			id = 1;
		}catch(Exception e){
			id = 0;
			e.printStackTrace();
		}
		return id;
	}


	
}
