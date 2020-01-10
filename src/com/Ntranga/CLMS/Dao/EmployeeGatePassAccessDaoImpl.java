package com.Ntranga.CLMS.Dao;

import java.math.BigInteger;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.EmployeeGatePassAccessVo;
import com.Ntranga.CLMS.vo.OTExceededWorkersVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.EmployeeGatePassAccess;
import com.Ntranga.core.CLMS.entities.LocationDetails;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@Transactional
@Repository("employeeGatePassAccessDao")
public class EmployeeGatePassAccessDaoImpl implements EmployeeGatePassAccessDao {

	private static Logger log = Logger.getLogger(EmployeeGatePassAccessDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public Integer saveGatePassAccessRecord(EmployeeGatePassAccessVo employeeGatePassAccessVo) {
		// TODO Auto-generated method stub
		Integer id = 0;
		EmployeeGatePassAccess access = null;
		System.out.println("asasa: "+employeeGatePassAccessVo.getEmployeeGatePassAccessId());
		try{
			if(employeeGatePassAccessVo != null && employeeGatePassAccessVo.getEmployeeGatePassAccessId() != null && employeeGatePassAccessVo.getEmployeeGatePassAccessId() > 0){
				access = (EmployeeGatePassAccess) sessionFactory.getCurrentSession().load(EmployeeGatePassAccess.class, employeeGatePassAccessVo.getEmployeeGatePassAccessId());
			}else{
				access = new EmployeeGatePassAccess();
			}
			access.setCustomerDetails(new CustomerDetails(employeeGatePassAccessVo.getCustomerId()));
			access.setCompanyDetails(new CompanyDetails(employeeGatePassAccessVo.getCompanyId()));
			access.setLocationDetails(new LocationDetails(employeeGatePassAccessVo.getLocationId()));
			access.setCountryId(employeeGatePassAccessVo.getCountryId());
			access.setTransactionDate(employeeGatePassAccessVo.getTransactionDate());
			access.setCuttOfHoursORMonth(employeeGatePassAccessVo.getCuttOfHoursORMonth()*60*60);
			access.setModifiedBy(employeeGatePassAccessVo.getModifiedBy());
			access.setModifiedDate(new Date());
			access.setIsActive("Y");
			if(employeeGatePassAccessVo != null && employeeGatePassAccessVo.getEmployeeGatePassAccessId() != null && employeeGatePassAccessVo.getEmployeeGatePassAccessId() > 0){
				id=employeeGatePassAccessVo.getEmployeeGatePassAccessId();
				sessionFactory.getCurrentSession().update(access);
			}else{
				BigInteger sequenceId = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Sequence_Id),0) FROM `Employee_GatePass_Access`  WHERE `Transaction_Date` = '"+DateHelper.convertDateToSQLString(employeeGatePassAccessVo.getTransactionDate())+"' and customer_id='"+employeeGatePassAccessVo.getCustomerId()+"' and company_id = "+employeeGatePassAccessVo.getCompanyId() +" ").list().get(0);
				access.setSequenceId(sequenceId.intValue()+1);	
				access.setCreatedBy(employeeGatePassAccessVo.getModifiedBy());
				access.setCreatedDate(new Date());
				id = (Integer) sessionFactory.getCurrentSession().save(access);
			}
			System.out.println(id);
		}catch(Exception e){
			e.printStackTrace();
			log.error("Error Occured ",e);
		}
		
		return id;
	}

	@Override
	public EmployeeGatePassAccessVo getGatePassAccessDetails(EmployeeGatePassAccessVo Vo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		EmployeeGatePassAccessVo gatePassAccessVo = new EmployeeGatePassAccessVo();
		try {
			List accessList = session.createSQLQuery("Select `Employee_GatePass_Access_Id`, hour(sec_to_time(`Cutt_Of_Hours_OR_Month`)),`Transaction_Date` from employee_gatepass_access ega where ega.Customer_Id="+Vo.getCustomerId()+" and ega.Company_Id="+Vo.getCompanyId()+" and ega.Country_Id="+Vo.getCountryId()+" and ega.Location_Id="+Vo.getLocationId()+" and CONCAT(DATE_FORMAT(ega.transaction_date, '%Y%m%d'), LPAD(ega.Sequence_Id, 2, '0')) =(SELECT MAX(CONCAT(DATE_FORMAT(ega1.transaction_date, '%Y%m%d'), LPAD(ega1.Sequence_Id, 2, '0'))) FROM employee_gatepass_access ega1 WHERE (ega.customer_id=ega1.customer_id AND ega.company_id=ega1.company_id AND ega.location_id = ega1.location_id AND ega1.transaction_date <= CURRENT_DATE()) GROUP BY location_id)  ").list();						
			
			if(accessList != null && accessList.size() > 0) {
				Object[] obj = (Object[]) accessList.get(0);
				gatePassAccessVo.setEmployeeGatePassAccessId((Integer)obj[0]);
				gatePassAccessVo.setCuttOfHoursORMonth(new BigInteger(obj[1]+"").intValue());
				gatePassAccessVo.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[2]));
			}			
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}

		return gatePassAccessVo;

	}

	@Override
	public List<SimpleObject> getTransactionDatesForHistory(EmployeeGatePassAccessVo Vo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject>  objects = new ArrayList<SimpleObject>();
		try {
			List accessList = session.createSQLQuery("Select `Employee_GatePass_Access_Id`, CONCAT(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),' - ',Sequence_Id) AS name from employee_gatepass_access ega where ega.Customer_Id="+Vo.getCustomerId()+" and ega.Company_Id="+Vo.getCompanyId()+" and ega.Country_Id="+Vo.getCountryId()+" ORDER BY Transaction_Date,Sequence_Id ").list();						
			
			for (Object transDates  : accessList) {				
				Object[] obj = (Object[]) transDates;
				SimpleObject object  = new SimpleObject();
				object.setId((Integer)obj[0]);
				object.setName(obj[1]+"");
				objects.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}

		return objects;

	}

	@Override
	public EmployeeGatePassAccessVo getGatePassAccessRecordById(EmployeeGatePassAccessVo vo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		EmployeeGatePassAccessVo gatePassAccessVo = new EmployeeGatePassAccessVo();
		try {
			List accessList = session.createSQLQuery("Select `Employee_GatePass_Access_Id`, hour(sec_to_time(`Cutt_Of_Hours_OR_Month`)),`Transaction_Date`, `Company_Id`, `Customer_Id`, `Location_Id`, `Country_Id` from employee_gatepass_access where Employee_GatePass_Access_Id="+vo.getEmployeeGatePassAccessId()).list();						
			
			if(accessList != null && accessList.size() > 0) {
				Object[] obj = (Object[]) accessList.get(0);
				gatePassAccessVo.setEmployeeGatePassAccessId((Integer)obj[0]);
				gatePassAccessVo.setCuttOfHoursORMonth(new BigInteger(obj[1]+"").intValue());
				gatePassAccessVo.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[2]));
				gatePassAccessVo.setCustomerId((Integer)obj[4]);
				gatePassAccessVo.setCompanyId((Integer)obj[3]);
				gatePassAccessVo.setCountryId((Integer)obj[6]);
				gatePassAccessVo.setLocationId((Integer)obj[5]);
			}			
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}

		return gatePassAccessVo;

	}

	@Override
	public List<OTExceededWorkersVo> getOTExceededWorkers(String employeeGatePassAccessJsonString) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<OTExceededWorkersVo>  exceededWorkersVos = new ArrayList<OTExceededWorkersVo>() ;
		try {
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(employeeGatePassAccessJsonString);
			
			 List workers = session.createSQLQuery(" SELECT emp,emp_name,`ContractorCode`,`ContractorName`,ega.`Cutt_Of_Hours_OR_Month`,CAST(SEC_TO_TIME(SUM(TIME_TO_SEC(`OTHours`))) AS CHAR) as totalOTHours FROM `labor_time` lt INNER JOIN worker_details wd ON (lt.emp = wd.worker_code) "
						+ " INNER JOIN `employee_gatepass_access` ega ON (ega.`Customer_Id`=wd.customer_id AND ega.`Company_Id` = wd.company_id AND ega.location_id = "+jo.get("locationId").getAsString()+") "
						+ " WHERE CONCAT(DATE_FORMAT(ega.transaction_date, '%Y%m%d'), LPAD(ega.Sequence_Id, 2, '0')) =(SELECT MAX(CONCAT(DATE_FORMAT(ega1.transaction_date, '%Y%m%d'), LPAD(ega1.Sequence_Id, 2, '0'))) FROM employee_gatepass_access ega1 WHERE (ega.customer_id=ega1.customer_id AND ega.company_id=ega1.company_id AND ega.location_id = ega1.location_id AND ega1.transaction_date <= CURRENT_DATE()) GROUP BY location_id)  "
						+ " AND ega.customer_id = "+jo.get("customerId").getAsString()+"  AND ega.company_id ="+jo.get("companyId").getAsString()+"  AND MONTH(`Business_Date`) ="+jo.get("month").getAsString()+"  AND YEAR(`Business_Date`) ="+jo.get("year").getAsString()+"   GROUP BY emp,emp_name HAVING SUM(TIME_TO_SEC(`OTHours`))  >= (ega.`Cutt_Of_Hours_OR_Month`) ").list();						
				
				for (Object worker  : workers) {	
					Object[] obj = (Object[]) worker;
					OTExceededWorkersVo otExceededWorkersVo = new OTExceededWorkersVo();
					otExceededWorkersVo.setEmpCode(obj[0]+"");
					otExceededWorkersVo.setEmpName(obj[1]+"");		
					otExceededWorkersVo.setContractorCode(obj[2]+"");
					otExceededWorkersVo.setContractorName(obj[3]+"");
					otExceededWorkersVo.setTotalOTHours(obj[5]+"");
					exceededWorkersVos.add(otExceededWorkersVo);
				}			
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}
		return exceededWorkersVos;
	}

	
	
}
