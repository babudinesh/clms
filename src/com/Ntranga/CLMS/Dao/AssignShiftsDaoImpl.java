package com.Ntranga.CLMS.Dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.AssignShiftPatternDetailsVo;
import com.Ntranga.CLMS.vo.AssignShiftPatternUpdateVo;
import com.Ntranga.CLMS.vo.AssignShiftsVo;
import com.Ntranga.CLMS.vo.PlantVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.AssignShiftPattern;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.DepartmentDetails;
import com.Ntranga.core.CLMS.entities.LocationDetails;
import com.Ntranga.core.CLMS.entities.PlantDetails;
import com.Ntranga.core.CLMS.entities.SectionDetails;
import com.Ntranga.core.CLMS.entities.VendorDetails;
import com.Ntranga.core.CLMS.entities.WorkAreaDetails;
import com.Ntranga.core.CLMS.entities.WorkerDetails;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import common.Logger;

@Transactional
@Repository("assignShiftsDao")
public class AssignShiftsDaoImpl implements AssignShiftsDao  {

	private static Logger log = Logger.getLogger(AssignShiftsDaoImpl.class);
	
	
	@Autowired
	private SessionFactory sessionFactory;



	@Override
	public List<AssignShiftsVo> getWorkerDetailsToGridView(String jsonString) {
		// TODO Auto-generated method stub
		List<AssignShiftsVo> assignShiftsVos = new ArrayList<AssignShiftsVo>();	
		Session session = sessionFactory.getCurrentSession();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(jsonString);
			
			String query = "SELECT  wd.worker_id ,wd.worker_code, CONCAT(wdi.first_name,' ',wdi.last_name) AS Workername,vdi.vendor_name "
						+" FROM worker_details wd  "
						+" INNER JOIN worker_details_info wdi ON(wd.worker_id = wdi.worker_id) " 
						+" INNER JOIN `customer_details_info` cdi ON(wd.customer_id =cdi.customer_id) " 
						+" INNER JOIN `company_details_info` com ON (com.company_id = wd.company_id) " 
						+" INNER JOIN `vendor_details_info` vdi ON(vdi.vendor_id = wd.vendor_id) " 					
						+" WHERE  CONCAT(DATE_FORMAT(wdi.transaction_date, '%Y%m%d'), LPAD(wdi.Sequence_Id, 2, '0')) = "  
						+" ( " 
						+" SELECT MAX(CONCAT(DATE_FORMAT(wdi1.transaction_date, '%Y%m%d'), LPAD(wdi1.Sequence_Id, 2, '0'))) " 
						+" FROM worker_details_info wdi1 " 
						+" WHERE  wdi.`worker_id` = wdi1.`worker_id` "     
						+" AND wdi1.transaction_date <= CURRENT_DATE()    "
						+" ) " 
						+" AND   "
						+" CONCAT(DATE_FORMAT(cdi.transaction_date, '%Y%m%d'), LPAD(cdi.`Customer_Sequence_Id`, 2, '0')) = "  
						+" ( " 
						+" SELECT MAX(CONCAT(DATE_FORMAT(cdi1.transaction_date, '%Y%m%d'), LPAD(cdi1.`Customer_Sequence_Id`, 2, '0'))) " 
						+" FROM customer_details_info cdi1 " 
						+" WHERE cdi.`customer_id` = cdi1.`customer_id` "   
						+" AND cdi1.transaction_date <= CURRENT_DATE() "   
						+" ) " 
						+" AND   "
						+" CONCAT(DATE_FORMAT(com.transaction_date, '%Y%m%d'), LPAD(com.Company_Sequence_Id, 2, '0')) = "  
						+" ( " 
						+" SELECT MAX(CONCAT(DATE_FORMAT(com1.transaction_date, '%Y%m%d'), LPAD(com1.`Company_Sequence_Id`, 2, '0'))) " 
						+" FROM company_details_info com1 " 
						+" WHERE com.`company_id` = com1.`company_id` " 
						+" AND com1.transaction_date <= CURRENT_DATE() "   
						+" ) " 						
						+" AND  " 
						+" CONCAT(DATE_FORMAT(vdi.transaction_date, '%Y%m%d'), LPAD(vdi.Sequence_Id, 2, '0')) = "  
						+" ( " 
						+" SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id, 2, '0'))) " 
						+" FROM vendor_details_info vdi1 " 
						+" WHERE vdi.`vendor_id` = vdi1.`vendor_id` " 
						+" AND vdi1.transaction_date <= CURRENT_DATE() "
						+" )  and wdi.is_active='Y' ";
			
			if( !jo.get("workerCode").toString().equalsIgnoreCase("null") && !jo.get("workerCode").getAsString().equalsIgnoreCase("null")  && !jo.get("workerCode").getAsString().isEmpty()){
				query += " AND wd.worker_code='"+jo.get("workerCode").getAsString()+"'";
			}
			if( !jo.get("workerName").toString().equalsIgnoreCase("null") && !jo.get("workerName").getAsString().equalsIgnoreCase("null")  && !jo.get("workerName").getAsString().isEmpty()){
				query += " AND (wdi.first_name LIKE '%"+jo.get("workerName").getAsString()+"%' OR wdi.last_name LIKE '%"+jo.get("workerName").getAsString()+"%'  OR CONCAT(wdi.first_name,' ',wdi.last_name) LIKE '%"+jo.get("workerName").getAsString()+"%' )";
			}
			if(!(jo.get("customerId").getAsString().equalsIgnoreCase("null")) && !(jo.get("customerId").getAsString().equalsIgnoreCase("null")) && jo.get("customerId").getAsInt() > 0){
				query += " AND  cdi.customer_id="+jo.get("customerId").getAsInt();
            }
			if(!(jo.get("companyId").getAsString().equalsIgnoreCase("null")) && !(jo.get("companyId").getAsString().equalsIgnoreCase("null")) && jo.get("companyId").getAsInt() > 0){
				query += " AND  com.company_id="+jo.get("companyId").getAsInt();
			}			
			if(!(jo.get("vendorId").getAsString().equalsIgnoreCase("null")) && !(jo.get("vendorId").getAsString().equalsIgnoreCase("null")) && jo.get("vendorId").getAsInt() > 0){
				query += " AND  vdi.vendor_id="+jo.get("vendorId").getAsInt();
            }

			List tempList = session.createSQLQuery(query).list();			
			for(Object o : tempList){
				Object[] obj = (Object[]) o;
				AssignShiftsVo shiftsVo = new AssignShiftsVo();	
				shiftsVo.setWorkerId((Integer)obj[0]);
				shiftsVo.setWorkerCode(obj[1]+"");
				shiftsVo.setWorkerName(obj[2]+"");
				shiftsVo.setVendorName(obj[3]+"");				
				assignShiftsVos.add(shiftsVo);
			}	
			
		}catch(Exception e){
			//log.error("Error Occured ",e);
			log.error("Error Occured AssignShiftsDaoImpl method getWorkerDetailsToGridView ",e);
		}		
		return assignShiftsVos;
	}


	@Override
	public List<AssignShiftsVo> getDeptLocPlantDtls(String jsonString) {
		// TODO Auto-generated method stub
		List<AssignShiftsVo>  assignShiftsVos = new ArrayList<AssignShiftsVo>();
		Session session = sessionFactory.getCurrentSession();
		AssignShiftsVo shiftsVo = new AssignShiftsVo();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(jsonString);
			
			String query = " SELECT  wdi.department,wdi.location,location.Location_Name,CASE wdi.department WHEN 0 THEN 'All' ELSE dept.Department_Name END AS dept   FROM  `workrorder_detail` wd  INNER JOIN workorder_detail_info wdi ON (wd.`WorkOrder_id` = wdi.`WorkOrder_id`)  INNER JOIN location_details_info  location  ON( location.Location_Id = wdi.location)   LEFT JOIN department_details_info dept ON (dept.Department_Id = wdi.department AND CONCAT(DATE_FORMAT(dept.transaction_date, '%Y%m%d'), dept.`Department_Sequence_Id`) =  ( SELECT MAX(CONCAT(DATE_FORMAT(dept1.transaction_date,'%Y%m%d'), dept1.`Department_Sequence_Id`)) FROM department_details_info dept1 WHERE  dept.`Department_Id` = dept1.`Department_Id` AND dept1.transaction_date <= CURRENT_DATE()  ))  WHERE    CONCAT(DATE_FORMAT(wdi.transaction_date, '%Y%m%d'), wdi.`Sequence_Id`) =     ( SELECT MAX(CONCAT(DATE_FORMAT(wdi1.transaction_date, '%Y%m%d'), wdi1.`Sequence_Id`))    FROM workorder_detail_info wdi1 WHERE wdi.`WorkOrder_id` = wdi1.`WorkOrder_id`     AND wdi1.transaction_date <= CURRENT_DATE() )   AND CONCAT(DATE_FORMAT(location.transaction_date, '%Y%m%d'), location.`Location_Sequence_Id`) =  ( SELECT MAX(CONCAT(DATE_FORMAT(location1.transaction_date, '%Y%m%d'), location1.`Location_Sequence_Id`))  FROM location_details_info location1  WHERE   location.`Location_Id` = location1.`Location_Id` AND location1.transaction_date <= CURRENT_DATE()  ) ";
			if(!(jo.get("customerId").getAsString().equalsIgnoreCase("null")) && !(jo.get("customerId").getAsString().equalsIgnoreCase("null")) && jo.get("customerId").getAsInt() > 0){
				query += " AND  wd.customer_id="+jo.get("customerId").getAsInt();
            }
			if(!(jo.get("companyId").getAsString().equalsIgnoreCase("null")) && !(jo.get("companyId").getAsString().equalsIgnoreCase("null")) && jo.get("companyId").getAsInt() > 0){
				query += " AND  wd.company_id="+jo.get("companyId").getAsInt();
			}			
			if(!(jo.get("workOrderName").getAsString().equalsIgnoreCase("null")) && !(jo.get("workOrderName").getAsString().equalsIgnoreCase("null")) && jo.get("workOrderName").getAsInt() > 0){
				query += " AND  wd.`WorkOrder_id`="+jo.get("workOrderName").getAsString();
            }
											
		List tempList = session.createSQLQuery(query).list();			
		for(Object o : tempList){
			Object[] obj = (Object[]) o;	
			shiftsVo.setDepartmentId((Integer)obj[0]);
			shiftsVo.setLocationId((Integer)obj[1]);
			shiftsVo.setDepartmentName(obj[3]+"");
			shiftsVo.setLocationName(obj[2]+"");			
		}	
		
		String plantquery = "SELECT plant.`Plant_Id`,info.`Plant_Name` FROM plant_details_info info INNER JOIN plant_details plant ON plant.Plant_Id = info.Plant_Id WHERE CONCAT(DATE_FORMAT(info.transaction_date, '%Y%m%d'), LPAD(info.`Plant_Sequence_Id`, 2, '0')) =  (  SELECT MAX(CONCAT(DATE_FORMAT(info1.transaction_date, '%Y%m%d'), LPAD(info1.`Plant_Sequence_Id`, 2, '0'))) FROM plant_details_info info1 "
				+ " WHERE info.Plant_Id = info1.Plant_Id  AND info1.transaction_date <= CURRENT_DATE()  ) "		
				+ " AND location_id ="+shiftsVo.getLocationId();
		List planttempList = session.createSQLQuery(plantquery).list();
		
		for(Object o : planttempList){
			Object[] obj = (Object[]) o;			
			shiftsVo.getPlantList().add(new SimpleObject((Integer)obj[0], obj[1]+""));		
		}	
		assignShiftsVos.add(shiftsVo);
			
	}catch(Exception e){
		//log.error("Error Occured ",e);
		log.error("Error Occured ",e);
	}	
		return assignShiftsVos;
	}


	@Override
	public Integer saveShiftPatternDetails(AssignShiftPatternDetailsVo assignShiftPatternDetailsVo) {
		// TODO Auto-generated method stub
		//System.out.println("Hiiii");
		Session session = sessionFactory.getCurrentSession();
		Integer id = 0;
		int limit = 31;	
		try{
			/*File file = new File("D:\\test.txt");
			FileWriter fileWriter = new FileWriter(file.getAbsolutePath());
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			System.out.println(assignShiftPatternDetailsVo.getAssignShiftsVo().size());*/
						
			 String daytype = "";
			 Calendar calendar = Calendar.getInstance();
	         calendar.set(Calendar.YEAR,assignShiftPatternDetailsVo.getPatternDate().getYear());
	         calendar.set(Calendar.MONTH,assignShiftPatternDetailsVo.getPatternDate().getMonth()-1);
	         calendar.set(Calendar.DAY_OF_MONTH,assignShiftPatternDetailsVo.getPatternDate().getDay());
	         String weekday = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US).toUpperCase();
	         if(weekday.equalsIgnoreCase("MONDAY")){
	        	 daytype = "+3";			        	 
	         }else if(weekday.equalsIgnoreCase("TUESDAY")){
	        	 daytype = "+2";
	         }else if(weekday.equalsIgnoreCase("WEDNESDAY")){
	        	 daytype = "+1";
	         }else if(weekday.equalsIgnoreCase("FRIDAY")){
	        	 daytype = "+1";
	         }else if(weekday.equalsIgnoreCase("SATURDAY")){
	        	 daytype = "-2";
	         }
	         
	         String insertQueryfields = "", insertQueryfieldValues = "";
			 if(assignShiftPatternDetailsVo.getDepartmentId() != null && assignShiftPatternDetailsVo.getDepartmentId() > 0){
				insertQueryfields += ",Department_Id";
				insertQueryfieldValues += ",'"+assignShiftPatternDetailsVo.getDepartmentId()+"'";
			 }
			 if(assignShiftPatternDetailsVo.getLocationId() != null && assignShiftPatternDetailsVo.getLocationId() > 0){
				insertQueryfields += ",Location_Id";
				insertQueryfieldValues += ",'"+assignShiftPatternDetailsVo.getLocationId()+"'";
			 }	
				
	         List tempList = session.createSQLQuery("SELECT define_pattern_by FROM shift_pattern_details  spd INNER JOIN  shift_pattern_details_info spdi ON (spd.shift_pattern_id = spdi.shift_pattern_id) WHERE  spd.shift_pattern_code='"+assignShiftPatternDetailsVo.getShiftCode()+"'").list();
	         
			for(AssignShiftsVo assignShiftsVo : assignShiftPatternDetailsVo.getAssignShiftsVo()){
				if(assignShiftsVo != null && assignShiftsVo.getSelected() != null && assignShiftsVo.getSelected()){					
					List<AssignShiftPattern> assignShiftPatterns = (List<AssignShiftPattern>) session.createQuery(" from AssignShiftPattern where workerDetails="+assignShiftsVo.getWorkerId()+" AND shiftPatternCode='"+assignShiftPatternDetailsVo.getShiftCode()+"' AND patternStartDate='"+DateHelper.convertDateToSQLDate(assignShiftPatternDetailsVo.getPatternDate())+"'").list();					
					if(assignShiftPatterns.size() > 0){
						AssignShiftPattern assignShiftPattern = assignShiftPatterns.get(0);
						assignShiftPattern.setShiftPatternCode(assignShiftPatternDetailsVo.getShiftCode());
						assignShiftPattern.setCustomerDetails(new CustomerDetails(assignShiftPatternDetailsVo.getCustomerId()));
						assignShiftPattern.setCompanyDetails(new CompanyDetails(assignShiftPatternDetailsVo.getCompanyId()));
						assignShiftPattern.setVendorDetails(new VendorDetails(assignShiftPatternDetailsVo.getVendorId()));
						
						if(assignShiftPatternDetailsVo.getDepartmentId() != null && assignShiftPatternDetailsVo.getDepartmentId() > 0)
							assignShiftPattern.setDepartmentDetails(new DepartmentDetails(assignShiftPatternDetailsVo.getDepartmentId()));
						else
							assignShiftPattern.setDepartmentDetails(null);
						if(assignShiftPatternDetailsVo.getLocationId() != null && assignShiftPatternDetailsVo.getLocationId() > 0)
							assignShiftPattern.setLocationDetails(new LocationDetails(assignShiftPatternDetailsVo.getLocationId()));
						else
							assignShiftPattern.setLocationDetails(null);
						if(assignShiftPatternDetailsVo.getPlantId() != null && assignShiftPatternDetailsVo.getPlantId() > 0)
							assignShiftPattern.setPlantDetails(new PlantDetails(assignShiftPatternDetailsVo.getPlantId()));
						else
							assignShiftPattern.setPlantDetails(null);
						/*if(assignShiftPatternDetailsVo.getSectionId()!= null && assignShiftPatternDetailsVo.getSectionId() > 0)
							assignShiftPattern.setSectionDetails(new SectionDetails(assignShiftPatternDetailsVo.getSectionId()));
						else
							assignShiftPattern.setSectionDetails(null);
						
						//assignShiftPattern.setWorkrorderDetail(new WorkrorderDetail(assignShiftPatternDetailsVo.getWorkOrderId()));
						if(assignShiftPatternDetailsVo.getWorkAreaId()!= null && assignShiftPatternDetailsVo.getWorkAreaId() > 0)
							assignShiftPattern.setWorkAreaDetails(new WorkAreaDetails(assignShiftPatternDetailsVo.getWorkAreaId()));
						else
							assignShiftPattern.setWorkAreaDetails(null);
						*/
						assignShiftPattern.setWorkerDetails(new WorkerDetails(assignShiftsVo.getWorkerId()));
						assignShiftPattern.setIsActive("Y");
						assignShiftPattern.setPatternStartDate(assignShiftPatternDetailsVo.getPatternDate());
						assignShiftPattern.setCreatedBy(assignShiftPatternDetailsVo.getCreatedBy());
						assignShiftPattern.setModifiedBy(assignShiftPatternDetailsVo.getModifiedBy());
						assignShiftPattern.setCreatedDate(new Date());
						assignShiftPattern.setModifiedDate(new Date());	
						session.update(assignShiftPattern);
					}else{
						AssignShiftPattern assignShiftPattern = new AssignShiftPattern();
						assignShiftPattern.setShiftPatternCode(assignShiftPatternDetailsVo.getShiftCode());
						assignShiftPattern.setCustomerDetails(new CustomerDetails(assignShiftPatternDetailsVo.getCustomerId()));
						assignShiftPattern.setCompanyDetails(new CompanyDetails(assignShiftPatternDetailsVo.getCompanyId()));
						assignShiftPattern.setVendorDetails(new VendorDetails(assignShiftPatternDetailsVo.getVendorId()));
						if(assignShiftPatternDetailsVo.getDepartmentId() != null && assignShiftPatternDetailsVo.getDepartmentId() > 0)
							assignShiftPattern.setDepartmentDetails(new DepartmentDetails(assignShiftPatternDetailsVo.getDepartmentId()));
						if(assignShiftPatternDetailsVo.getLocationId() != null && assignShiftPatternDetailsVo.getLocationId() > 0)
							assignShiftPattern.setLocationDetails(new LocationDetails(assignShiftPatternDetailsVo.getLocationId()));
						if(assignShiftPatternDetailsVo.getPlantId() != null && assignShiftPatternDetailsVo.getPlantId() > 0)
							assignShiftPattern.setPlantDetails(new PlantDetails(assignShiftPatternDetailsVo.getPlantId()));
						/*if(assignShiftPatternDetailsVo.getSectionId()!= null && assignShiftPatternDetailsVo.getSectionId() > 0)
							assignShiftPattern.setSectionDetails(new SectionDetails(assignShiftPatternDetailsVo.getSectionId()));
						// assignShiftPattern.setWorkrorderDetail(new WorkrorderDetail(assignShiftPatternDetailsVo.getWorkOrderId()));
						if(assignShiftPatternDetailsVo.getWorkAreaId()!= null && assignShiftPatternDetailsVo.getWorkAreaId() > 0)
							assignShiftPattern.setWorkAreaDetails(new WorkAreaDetails(assignShiftPatternDetailsVo.getWorkAreaId()));*/
						assignShiftPattern.setWorkerDetails(new WorkerDetails(assignShiftsVo.getWorkerId()));
						assignShiftPattern.setIsActive("Y");
						assignShiftPattern.setPatternStartDate(assignShiftPatternDetailsVo.getPatternDate());
						assignShiftPattern.setCreatedBy(assignShiftPatternDetailsVo.getCreatedBy());
						assignShiftPattern.setModifiedBy(assignShiftPatternDetailsVo.getModifiedBy());
						assignShiftPattern.setCreatedDate(new Date());
						assignShiftPattern.setModifiedDate(new Date());					
						session.save(assignShiftPattern);
					}
					String deleteQuery = "";
					if(assignShiftPatternDetailsVo.getMonthYear() != null && assignShiftPatternDetailsVo.getMonthYear().equalsIgnoreCase("N")){
						deleteQuery ="DELETE FROM labor_scheduled_shifts WHERE Emp_Code = (SELECT a.Worker_code FROM worker_details a WHERE a.Worker_id="+assignShiftsVo.getWorkerId()+") AND Business_Date BETWEEN '"+DateHelper.convertDateToSQLDate(assignShiftPatternDetailsVo.getPatternDate())+"' AND LAST_DAY('"+DateHelper.convertDateToSQLDate(assignShiftPatternDetailsVo.getPatternDate())+"') ";
					}else{
						deleteQuery ="DELETE FROM labor_scheduled_shifts WHERE Emp_Code = (SELECT a.Worker_code FROM worker_details a WHERE a.Worker_id="+assignShiftsVo.getWorkerId()+") AND Business_Date BETWEEN '"+DateHelper.convertDateToSQLDate(assignShiftPatternDetailsVo.getPatternDate())+"' AND DATE_ADD('"+DateHelper.convertDateToSQLDate(assignShiftPatternDetailsVo.getPatternDate())+"',INTERVAL (SELECT MAX(spas.Day_Sequence )-1 "
								+" FROM shift_pattern_assign_shifts spas  "
								+"   INNER JOIN shift_pattern_details_info spdi ON (spas.Shift_Pattern_Details_Id = spdi.Shift_Pattern_Details_Id)  "  
								+" INNER JOIN shift_pattern_details spd ON (spdi.Shift_Pattern_Id = spd.Shift_Pattern_Id)  "  
								+" WHERE CONCAT(DATE_FORMAT(spdi.transaction_date, '%Y%m%d'), LPAD(spdi.Shift_Pattern_Sequence_Id, 2, '0')) =  (  " 
								+" SELECT MAX(CONCAT(DATE_FORMAT(spdi1.transaction_date, '%Y%m%d'), LPAD(spdi1.Shift_Pattern_Sequence_Id, 2, '0')))  " 
								+" FROM shift_pattern_details_info spdi1  " 
								+" WHERE spdi.Shift_Pattern_Id = spdi1.Shift_Pattern_Id  AND spdi1.transaction_date <= CURRENT_DATE()  ) AND spd.`Shift_Pattern_Code`= '"+assignShiftPatternDetailsVo.getShiftCode()+"' )  "
								+" DAY) ";
					}
						
					//bufferedWriter.write(deleteQuery);
					session.createSQLQuery(deleteQuery).executeUpdate();

					List workerDetailsQuery = session.createSQLQuery(" SELECT worker_code,CONCAT(first_name,' ',CASE WHEN middle_name IS NULL THEN '' ELSE CONCAT(middle_name,' ') END,last_name) AS workerName, vendor_code,vendor_name FROM worker_details wd INNER JOIN worker_details_info wdi ON (wd.worker_id = wdi.worker_id) INNER JOIN vendor_details vd ON (wd.vendor_id = vd.vendor_id) INNER JOIN vendor_details_info vdi ON (vdi.vendor_id = vd.vendor_id) WHERE CONCAT(DATE_FORMAT(wdi.transaction_date, '%Y%m%d'), LPAD(wdi.Sequence_Id, 2, '0')) = ( SELECT MAX(CONCAT(DATE_FORMAT(wdi1.transaction_date, '%Y%m%d'), LPAD(wdi1.Sequence_Id, 2, '0'))) FROM worker_details_info wdi1 WHERE wdi.worker_id = wdi1.worker_id AND wdi1.transaction_date <= CURRENT_DATE() ) AND CONCAT(DATE_FORMAT(vdi.transaction_date, '%Y%m%d'), LPAD(vdi.Sequence_Id, 2, '0')) = (  SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id, 2, '0'))) FROM vendor_details_info vdi1 WHERE vdi.vendor_id = vdi1.vendor_id AND vdi1.transaction_date <= CURRENT_DATE()  ) AND wd.worker_id = "+assignShiftsVo.getWorkerId()).list();
					Object workerObj = workerDetailsQuery.get(0);
					Object[] workerObjarr = (Object[]) workerObj;
					String insertQuery = "INSERT INTO labor_scheduled_shifts (  ContractorName,code,Shift,Business_Date,Created_date,Modified_Date, Modified_By,Created_By, Emp_Code, Emp_Name"+insertQueryfields+") ";
					
					if(tempList != null && tempList.size() > 0){
						String patternType = (String) tempList.get(0);
						if(patternType != null && patternType.equalsIgnoreCase("Monthly")){
							limit = DateHelper.daysInMonth(Integer.valueOf(DateHelper.convertDateToSQLDate(assignShiftPatternDetailsVo.getPatternDate()).getMonth() ), Integer.valueOf(DateHelper.convertDateToSQLDate(assignShiftPatternDetailsVo.getPatternDate()).getYear()));
							insertQuery += " select ContractorName,code,Shift,Business_Date,NOW(),NOW(),Modified_By,Created_By,Emp_Code,Emp_Name"+insertQueryfieldValues+" FROM "
										+" ( SELECT '"+workerObjarr[3]+"' AS  ContractorName ,'"+workerObjarr[2]+"' as code,shift_code AS Shift, "+assignShiftPatternDetailsVo.getModifiedBy()+" AS Modified_By ,"+assignShiftPatternDetailsVo.getCreatedBy()+" as Created_By,'"+workerObjarr[0]+"' as Emp_Code,'"+workerObjarr[1]+"' as  Emp_Name ,DATE_ADD('"+DateHelper.convertDateToSQLDate(assignShiftPatternDetailsVo.getPatternDate())+"',INTERVAL (@rownum) DAY) AS Business_Date,(@rownum \\:=@rownum+1)  "
									    +" FROM (SELECT shift_code FROM shift_pattern_assign_shifts spas INNER JOIN shift_pattern_details_info spdi ON (spas.Shift_Pattern_Details_Id = spdi.Shift_Pattern_Details_Id)   "
										+" INNER JOIN shift_pattern_details spd ON (spdi.Shift_Pattern_Id = spd.Shift_Pattern_Id)    "
										+" WHERE spd.Shift_Pattern_Code='"+assignShiftPatternDetailsVo.getShiftCode()+"'   AND CONCAT(DATE_FORMAT(spdi.transaction_date, '%Y%m%d'), LPAD(spdi.Shift_Pattern_Sequence_Id, 2, '0')) = ( "
										+" SELECT MAX(CONCAT(DATE_FORMAT(spdi1.transaction_date,'%Y%m%d'),LPAD(spdi1.Shift_Pattern_Sequence_Id,2,'0'))) FROM shift_pattern_details_info spdi1 WHERE spdi.Shift_Pattern_Id = spdi1.Shift_Pattern_Id "  
										+" AND spdi1.transaction_date <= CURRENT_DATE() ) AND day_sequence >= DAYOFWEEK('"+DateHelper.convertDateToSQLDate(assignShiftPatternDetailsVo.getPatternDate())+"')  "
										+" UNION ALL "
										+" SELECT shift_code FROM shift_pattern_assign_shifts spas INNER JOIN shift_pattern_details_info spdi ON (spas.Shift_Pattern_Details_Id = spdi.Shift_Pattern_Details_Id) "  
										+" INNER JOIN shift_pattern_details spd ON (spdi.Shift_Pattern_Id = spd.Shift_Pattern_Id)  "
										+" WHERE spd.Shift_Pattern_Code='"+assignShiftPatternDetailsVo.getShiftCode()+"'  AND CONCAT(DATE_FORMAT(spdi.transaction_date, '%Y%m%d'), LPAD(spdi.Shift_Pattern_Sequence_Id, 2, '0')) = ( "
										+" SELECT MAX(CONCAT(DATE_FORMAT(spdi1.transaction_date,'%Y%m%d'),LPAD(spdi1.Shift_Pattern_Sequence_Id,2,'0')))  FROM "
										+" shift_pattern_details_info spdi1 WHERE spdi.Shift_Pattern_Id = spdi1.Shift_Pattern_Id  AND spdi1.transaction_date <= CURRENT_DATE())  "
										+" AND day_sequence >= DAYOFWEEK('2016-06-01') LIMIT  "+limit+") AS worker1, (SELECT @rownum \\:=0) AS as1 ) AS worker1";
							
						}else if(patternType != null && patternType.equalsIgnoreCase("Bi Weekly")){
							limit = 14;
							insertQuery += " select ContractorName,code,Shift,Business_Date,NOW(),NOW(),Modified_By,Created_By,Emp_Code,Emp_Name"+insertQueryfieldValues+" FROM "
									+" ( SELECT '"+workerObjarr[3]+"' AS  ContractorName ,'"+workerObjarr[2]+"' as code,shift_code AS Shift, "+assignShiftPatternDetailsVo.getModifiedBy()+" AS Modified_By ,"+assignShiftPatternDetailsVo.getCreatedBy()+" as Created_By,'"+workerObjarr[0]+"' as Emp_Code,'"+workerObjarr[1]+"' as  Emp_Name ,DATE_ADD('"+DateHelper.convertDateToSQLDate(assignShiftPatternDetailsVo.getPatternDate())+"',INTERVAL (@rownum) DAY) AS Business_Date,(@rownum \\:=@rownum+1)  "
								    +" FROM (SELECT shift_code FROM shift_pattern_assign_shifts spas INNER JOIN shift_pattern_details_info spdi ON (spas.Shift_Pattern_Details_Id = spdi.Shift_Pattern_Details_Id)   "
									+" INNER JOIN shift_pattern_details spd ON (spdi.Shift_Pattern_Id = spd.Shift_Pattern_Id)    "
									+" WHERE spd.Shift_Pattern_Code='"+assignShiftPatternDetailsVo.getShiftCode()+"'   AND CONCAT(DATE_FORMAT(spdi.transaction_date, '%Y%m%d'), LPAD(spdi.Shift_Pattern_Sequence_Id, 2, '0')) = ( "
									+" SELECT MAX(CONCAT(DATE_FORMAT(spdi1.transaction_date,'%Y%m%d'),LPAD(spdi1.Shift_Pattern_Sequence_Id,2,'0'))) FROM shift_pattern_details_info spdi1 WHERE spdi.Shift_Pattern_Id = spdi1.Shift_Pattern_Id "  
									+" AND spdi1.transaction_date <= CURRENT_DATE() ) AND day_sequence >= DAYOFWEEK('"+DateHelper.convertDateToSQLDate(assignShiftPatternDetailsVo.getPatternDate())+"')  "
									+" UNION ALL "
									+" SELECT shift_code FROM shift_pattern_assign_shifts spas INNER JOIN shift_pattern_details_info spdi ON (spas.Shift_Pattern_Details_Id = spdi.Shift_Pattern_Details_Id) "  
									+" INNER JOIN shift_pattern_details spd ON (spdi.Shift_Pattern_Id = spd.Shift_Pattern_Id)  "
									+" WHERE spd.Shift_Pattern_Code='"+assignShiftPatternDetailsVo.getShiftCode()+"'  AND CONCAT(DATE_FORMAT(spdi.transaction_date, '%Y%m%d'), LPAD(spdi.Shift_Pattern_Sequence_Id, 2, '0')) = ( "
									+" SELECT MAX(CONCAT(DATE_FORMAT(spdi1.transaction_date,'%Y%m%d'),LPAD(spdi1.Shift_Pattern_Sequence_Id,2,'0')))  FROM "
									+" shift_pattern_details_info spdi1 WHERE spdi.Shift_Pattern_Id = spdi1.Shift_Pattern_Id  AND spdi1.transaction_date <= CURRENT_DATE())  "
									+" AND day_sequence >= DAYOFWEEK(DATE_ADD('"+DateHelper.convertDateToSQLDate(assignShiftPatternDetailsVo.getPatternDate())+"', INTERVAL -1 DAY)) "+daytype+"  LIMIT  "+limit+") AS worker1, (SELECT @rownum \\:=0) AS as1 ) AS worker1";
						}else if(patternType != null && patternType.equalsIgnoreCase("Weekly")){
							limit = 7; 
							insertQuery += " select ContractorName,code,Shift,Business_Date,NOW(),NOW(),Modified_By,Created_By,Emp_Code,Emp_Name"+insertQueryfieldValues+" FROM "
									+" ( SELECT '"+workerObjarr[3]+"' AS  ContractorName ,'"+workerObjarr[2]+"' as code,shift_code AS Shift, "+assignShiftPatternDetailsVo.getModifiedBy()+" AS Modified_By ,"+assignShiftPatternDetailsVo.getCreatedBy()+" as Created_By,'"+workerObjarr[0]+"' as Emp_Code,'"+workerObjarr[1]+"' as  Emp_Name ,DATE_ADD('"+DateHelper.convertDateToSQLDate(assignShiftPatternDetailsVo.getPatternDate())+"',INTERVAL (@rownum) DAY) AS Business_Date,(@rownum \\:=@rownum+1)  "
								    +" FROM (SELECT shift_code FROM shift_pattern_assign_shifts spas INNER JOIN shift_pattern_details_info spdi ON (spas.Shift_Pattern_Details_Id = spdi.Shift_Pattern_Details_Id)   "
									+" INNER JOIN shift_pattern_details spd ON (spdi.Shift_Pattern_Id = spd.Shift_Pattern_Id)    "
									+" WHERE spd.Shift_Pattern_Code='"+assignShiftPatternDetailsVo.getShiftCode()+"'   AND CONCAT(DATE_FORMAT(spdi.transaction_date, '%Y%m%d'), LPAD(spdi.Shift_Pattern_Sequence_Id, 2, '0')) = ( "
									+" SELECT MAX(CONCAT(DATE_FORMAT(spdi1.transaction_date,'%Y%m%d'),LPAD(spdi1.Shift_Pattern_Sequence_Id,2,'0'))) FROM shift_pattern_details_info spdi1 WHERE spdi.Shift_Pattern_Id = spdi1.Shift_Pattern_Id "  
									+" AND spdi1.transaction_date <= CURRENT_DATE() ) AND day_sequence >= DAYOFWEEK('"+DateHelper.convertDateToSQLDate(assignShiftPatternDetailsVo.getPatternDate())+"')  "
									+" UNION ALL "
									+" SELECT shift_code FROM shift_pattern_assign_shifts spas INNER JOIN shift_pattern_details_info spdi ON (spas.Shift_Pattern_Details_Id = spdi.Shift_Pattern_Details_Id) "  
									+" INNER JOIN shift_pattern_details spd ON (spdi.Shift_Pattern_Id = spd.Shift_Pattern_Id)  "
									+" WHERE spd.Shift_Pattern_Code='"+assignShiftPatternDetailsVo.getShiftCode()+"'  AND CONCAT(DATE_FORMAT(spdi.transaction_date, '%Y%m%d'), LPAD(spdi.Shift_Pattern_Sequence_Id, 2, '0')) = ( "
									+" SELECT MAX(CONCAT(DATE_FORMAT(spdi1.transaction_date,'%Y%m%d'),LPAD(spdi1.Shift_Pattern_Sequence_Id,2,'0')))  FROM "
									+" shift_pattern_details_info spdi1 WHERE spdi.Shift_Pattern_Id = spdi1.Shift_Pattern_Id  AND spdi1.transaction_date <= CURRENT_DATE())  "
									+" LIMIT  "+limit+") AS worker1, (SELECT @rownum \\:=0) AS as1 ) AS worker1";
							
						}else if(patternType != null && patternType.equalsIgnoreCase("Daily")){
							insertQuery +="SELECT '"+workerObjarr[3]+"' AS  ContractorName ,'"+workerObjarr[2]+"' as code,shift_code AS Shift,'"+DateHelper.convertDateToSQLDate(assignShiftPatternDetailsVo.getPatternDate())+"',NOW(),NOW(), "+assignShiftPatternDetailsVo.getModifiedBy()+" AS Modified_By ,"+assignShiftPatternDetailsVo.getCreatedBy()+" as Created_By,'"+workerObjarr[0]+"' as Emp_Code,'"+workerObjarr[1]+"' as  Emp_Name "+insertQueryfieldValues+" "
									+" FROM shift_pattern_assign_shifts spas " 
									+" INNER JOIN shift_pattern_details_info spdi ON (spas.Shift_Pattern_Details_Id = spdi.Shift_Pattern_Details_Id) "
									+" INNER JOIN shift_pattern_details spd ON (spdi.Shift_Pattern_Id = spd.Shift_Pattern_Id) " 							
									+" WHERE spd.Shift_Pattern_Code='"+assignShiftPatternDetailsVo.getShiftCode()+"' AND "
									+" CONCAT(DATE_FORMAT(spdi.transaction_date, '%Y%m%d'), LPAD(spdi.Shift_Pattern_Sequence_Id, 2, '0')) = "   
									+" ( "  
									+" SELECT MAX(CONCAT(DATE_FORMAT(spdi1.transaction_date, '%Y%m%d'), LPAD(spdi1.Shift_Pattern_Sequence_Id, 2, '0'))) "  
									+" FROM shift_pattern_details_info spdi1 "  
									+" WHERE spdi.Shift_Pattern_Id = spdi1.Shift_Pattern_Id "  
									+" AND spdi1.transaction_date <= CURRENT_DATE() " 
									+" )";
						}
					}					
					//bufferedWriter.write(insertQuery);
					//System.out.println(insertQuery);
					session.createSQLQuery(insertQuery).executeUpdate();
				}
					
			}			
			id = 1;
			//bufferedWriter.close();
			session.flush();
			//System.out.println(id+" :: "+assignShiftPatternDetailsVo.toString());			
		}catch(Exception e){
			id = 0;
			//System.out.println(id+" :: id");
		
			log.error("Error Occured ",e);
		}
		return id;
	}

	public Integer  savelabourShiftDetails(String patternType,String workerCode,String workerName,String vendorCode,String vendorName,AssignShiftPatternDetailsVo assignShiftPatternDetailsVo){
		Integer i = 0;
		try{
			
			
			i=1;
		}catch(Exception e){
			e.printStackTrace();
		}			
		return i;
	}

	public Map<String,List<Object>> monthlyPreiviewDetails(AssignShiftPatternDetailsVo assignShiftPatternDetailsVo ){		
		Map<String,List<Object>>  obj = new HashMap<String,List<Object>>();
		Session session = sessionFactory.getCurrentSession();
		try{
			
			 String  workers = "0";			 
			for(AssignShiftsVo assignShiftsVo : assignShiftPatternDetailsVo.getAssignShiftsVo()){
				if(assignShiftsVo.getSelected() != null && assignShiftsVo.getSelected())
					workers += ","+assignShiftsVo.getWorkerId();
			}
			List<Object> headerList = new  ArrayList<Object>();
			headerList.add("Sl No");
			headerList.add("Worker Name");
			
			String datesListQuery = "SELECT  DAY(DATE_ADD('"+DateHelper.convertDateToSQLString(assignShiftPatternDetailsVo.getPatternDate())+"',INTERVAL (spas.Day_Sequence-1) DAY)),DAY(LAST_DAY('"+DateHelper.convertDateToSQLDate(assignShiftPatternDetailsVo.getPatternDate())+"')) "
			+"  FROM shift_pattern_assign_shifts spas   "
			+"  INNER JOIN shift_pattern_details_info spdi ON (spas.Shift_Pattern_Details_Id = spdi.Shift_Pattern_Details_Id) " 
			+"  INNER JOIN shift_pattern_details spd ON (spdi.Shift_Pattern_Id = spd.Shift_Pattern_Id) "
			+"  WHERE spd.Shift_Pattern_Code='"+assignShiftPatternDetailsVo.getShiftCode()+"' AND " 
			+"  CONCAT(DATE_FORMAT(spdi.transaction_date, '%Y%m%d'), LPAD(spdi.Shift_Pattern_Sequence_Id, 2, '0')) = "    
			+"  ( "   
			+"  SELECT MAX(CONCAT(DATE_FORMAT(spdi1.transaction_date, '%Y%m%d'), LPAD(spdi1.Shift_Pattern_Sequence_Id, 2, '0'))) "   
			+"  FROM shift_pattern_details_info spdi1 "   
			+"  WHERE spdi.Shift_Pattern_Id = spdi1.Shift_Pattern_Id "   
			+"  AND spdi1.transaction_date <= CURRENT_DATE() "  
			+"  ) ";
			Integer noOfDays = 0;
			List datesList = session.createSQLQuery(datesListQuery).list();
			for(int i=0 ; i < datesList.size(); i++){
				Object dateobj = datesList.get(i);
				Object[] obj1 = (Object[]) dateobj;
				if(noOfDays < 1)
				noOfDays = Integer.valueOf(obj1[1]+"");
				
				if( i  < Integer.valueOf(obj1[1]+""))
					headerList.add(obj1[0]);
			}
			
			String contentQuery = "  SELECT wd.`Worker_id` , CONCAT(wd.`Worker_code`, ' - ',IFNULL(wdi.`First_name`,''),' ',IFNULL(wdi.`Last_name`,'') )AS empname, " 
				 +" (SELECT GROUP_CONCAT(shift_code) FROM (SELECT day_sequence, (day_sequence - (DAYOFWEEK('"+DateHelper.convertDateToSQLString(assignShiftPatternDetailsVo.getPatternDate())+"')-1)) AS startday,shift_code,week_name,spdi.shift_pattern_details_id FROM shift_pattern_assign_shifts spas " 
				 +" INNER JOIN  shift_pattern_details_info spdi   ON (  spas.Shift_Pattern_Details_Id = spdi.Shift_Pattern_Details_Id  )  "
				 +" INNER JOIN   shift_pattern_details spd   ON ( spdi.Shift_Pattern_Id = spd.Shift_Pattern_Id  )   "
				 +" WHERE  spd.Shift_Pattern_Code='"+assignShiftPatternDetailsVo.getShiftCode()+"'  "
				 +" AND CONCAT(DATE_FORMAT(spdi.transaction_date, '%Y%m%d'), LPAD(spdi.Shift_Pattern_Sequence_Id, 2, '0')) = ( " 
				 +" SELECT  MAX(CONCAT(DATE_FORMAT(spdi1.transaction_date, '%Y%m%d'), LPAD(spdi1.Shift_Pattern_Sequence_Id, 2,'0')))  "
				 +" FROM shift_pattern_details_info spdi1  "
				 +" WHERE spdi.Shift_Pattern_Id = spdi1.Shift_Pattern_Id  AND spdi1.transaction_date <= CURRENT_DATE()  ) "
				 +" AND day_sequence >= DAYOFWEEK('"+DateHelper.convertDateToSQLString(assignShiftPatternDetailsVo.getPatternDate())+"') "
				 +" UNION ALL "
				 +" SELECT day_sequence, (day_sequence + (DAYOFWEEK('2016-06-01'))) AS startday,shift_code,week_name,spdi.shift_pattern_details_id FROM shift_pattern_assign_shifts spas " 
				 +" INNER JOIN  shift_pattern_details_info spdi   ON (  spas.Shift_Pattern_Details_Id = spdi.Shift_Pattern_Details_Id  ) " 
				 +" INNER JOIN   shift_pattern_details spd   ON ( spdi.Shift_Pattern_Id = spd.Shift_Pattern_Id  ) "  
				 +" WHERE  spd.Shift_Pattern_Code='"+assignShiftPatternDetailsVo.getShiftCode()+"' " 
				 +" AND CONCAT(DATE_FORMAT(spdi.transaction_date, '%Y%m%d'), LPAD(spdi.Shift_Pattern_Sequence_Id, 2, '0')) = ( " 
				 +" SELECT  MAX(CONCAT(DATE_FORMAT(spdi1.transaction_date, '%Y%m%d'), LPAD(spdi1.Shift_Pattern_Sequence_Id, 2,'0'))) "
				 +" FROM shift_pattern_details_info spdi1 " 
				 +" WHERE spdi.Shift_Pattern_Id = spdi1.Shift_Pattern_Id  AND spdi1.transaction_date <= CURRENT_DATE()  )  AND day_sequence >= DAYOFWEEK('2016-06-01')  "
				// +" AND day_sequence >= DAYOFWEEK(DATE_ADD('"+DateHelper.convertDateToSQLString(assignShiftPatternDetailsVo.getPatternDate())+"', INTERVAL -1 DAY)) "+daytype+"  LIMIT "+limit+" "
				 +" )AS m1) AS shifts " 
				 +" FROM `worker_details` wd INNER JOIN `worker_details_info` wdi ON (wd.`Worker_id` = wdi.`Worker_id`) "  
				 +" WHERE "  
				 +" CONCAT(DATE_FORMAT(wdi.transaction_date, '%Y%m%d'), LPAD(wdi.Sequence_Id, 2, '0')) = "      
				 +" ( "     
				 +" SELECT MAX(CONCAT(DATE_FORMAT(wdi1.transaction_date, '%Y%m%d'), LPAD(wdi1.Sequence_Id, 2, '0'))) "     
				 +" FROM worker_details_info wdi1 "     
				 +" WHERE wdi.Worker_id = wdi1.Worker_id "     
				 +" AND wdi1.transaction_date <= CURRENT_DATE() "  
				 +" ) AND wd.`Worker_id` IN ("+workers+") "; 
			List<Object> contentList = new ArrayList<Object>();
			List list = session.createSQLQuery(contentQuery).list();
			for( Object datesObj : list){
				 Object[] datreObj = ( Object[])datesObj;
				 Object[] contobj = new Object[datesList.size()+2];
				 contobj[0] = datreObj[0];
				 contobj[1] = datreObj[1];				 
				 for(int k=0; datreObj[2] != null && k < datreObj[2].toString().split(",").length && k < headerList.size()-2; k++){
					 log.info(datreObj[2].toString()+"  ::  "+k+"  ::  "+noOfDays+" :: "+(k < datreObj[2].toString().split(",").length));					
					       contobj[k+2] = datreObj[2].toString().split(",")[k];
				 }	
				 contentList.add(contobj);
			}						
			obj.put("headers", headerList);
			obj.put("content", contentList);				
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return obj;
	}

	public Map<String,List<Object>> weeklyPreiviewDetails(AssignShiftPatternDetailsVo assignShiftPatternDetailsVo ){		
		Map<String,List<Object>>  obj = new HashMap<String,List<Object>>();
		Session session = sessionFactory.getCurrentSession();
		try{
			
			String  workers = "0";						
			for(AssignShiftsVo assignShiftsVo : assignShiftPatternDetailsVo.getAssignShiftsVo()){
				if(assignShiftsVo.getSelected() != null && assignShiftsVo.getSelected())
					workers += ","+assignShiftsVo.getWorkerId();
			}
			List<Object> headerList = new  ArrayList<Object>();
			headerList.add("Sl No");
			headerList.add("Worker Name");
			
			String datesListQuery = "SELECT  DAY(DATE_ADD('"+DateHelper.convertDateToSQLString(assignShiftPatternDetailsVo.getPatternDate())+"',INTERVAL (spas.Day_Sequence-1) DAY)),DAY(LAST_DAY('"+DateHelper.convertDateToSQLDate(assignShiftPatternDetailsVo.getPatternDate())+"')) "
			+"  FROM shift_pattern_assign_shifts spas   "
			+"  INNER JOIN shift_pattern_details_info spdi ON (spas.Shift_Pattern_Details_Id = spdi.Shift_Pattern_Details_Id) " 
			+"  INNER JOIN shift_pattern_details spd ON (spdi.Shift_Pattern_Id = spd.Shift_Pattern_Id) "
			+"  WHERE spd.Shift_Pattern_Code='"+assignShiftPatternDetailsVo.getShiftCode()+"' AND " 
			+"  CONCAT(DATE_FORMAT(spdi.transaction_date, '%Y%m%d'), LPAD(spdi.Shift_Pattern_Sequence_Id, 2, '0')) = "    
			+"  ( "   
			+"  SELECT MAX(CONCAT(DATE_FORMAT(spdi1.transaction_date, '%Y%m%d'), LPAD(spdi1.Shift_Pattern_Sequence_Id, 2, '0'))) "   
			+"  FROM shift_pattern_details_info spdi1 "   
			+"  WHERE spdi.Shift_Pattern_Id = spdi1.Shift_Pattern_Id "   
			+"  AND spdi1.transaction_date <= CURRENT_DATE() "  
			+"  ) ";
			Integer noOfDays = 0;
			List datesList = session.createSQLQuery(datesListQuery).list();
			for(int i=0 ; i < datesList.size(); i++){
				Object dateobj = datesList.get(i);
				Object[] obj1 = (Object[]) dateobj;
				if(noOfDays < 1)
				noOfDays = Integer.valueOf(obj1[1]+"");
				
				if( i  < Integer.valueOf(obj1[1]+""))
					headerList.add(obj1[0]);
			}
			
			String contentQuery = "  SELECT wd.`Worker_id` , CONCAT(wd.`Worker_code`, ' - ',IFNULL(wdi.`First_name`,''),' ',IFNULL(wdi.`Last_name`,'') )AS empname, " 
				 +" (SELECT GROUP_CONCAT(shift_code) FROM (SELECT day_sequence, (day_sequence - (DAYOFWEEK('"+DateHelper.convertDateToSQLString(assignShiftPatternDetailsVo.getPatternDate())+"')-1)) AS startday,shift_code,week_name,spdi.shift_pattern_details_id FROM shift_pattern_assign_shifts spas " 
				 +" INNER JOIN  shift_pattern_details_info spdi   ON (  spas.Shift_Pattern_Details_Id = spdi.Shift_Pattern_Details_Id  )  "
				 +" INNER JOIN   shift_pattern_details spd   ON ( spdi.Shift_Pattern_Id = spd.Shift_Pattern_Id  )   "
				 +" WHERE  spd.Shift_Pattern_Code='"+assignShiftPatternDetailsVo.getShiftCode()+"'  "
				 +" AND CONCAT(DATE_FORMAT(spdi.transaction_date, '%Y%m%d'), LPAD(spdi.Shift_Pattern_Sequence_Id, 2, '0')) = ( " 
				 +" SELECT  MAX(CONCAT(DATE_FORMAT(spdi1.transaction_date, '%Y%m%d'), LPAD(spdi1.Shift_Pattern_Sequence_Id, 2,'0')))  "
				 +" FROM shift_pattern_details_info spdi1  "
				 +" WHERE spdi.Shift_Pattern_Id = spdi1.Shift_Pattern_Id  AND spdi1.transaction_date <= CURRENT_DATE()  ) "
				 +" AND day_sequence >= DAYOFWEEK('"+DateHelper.convertDateToSQLString(assignShiftPatternDetailsVo.getPatternDate())+"') "
				 +" UNION ALL "
				 +" SELECT day_sequence, (day_sequence + (DAYOFWEEK('"+DateHelper.convertDateToSQLString(assignShiftPatternDetailsVo.getPatternDate())+"'))) AS startday,shift_code,week_name,spdi.shift_pattern_details_id FROM shift_pattern_assign_shifts spas " 
				 +" INNER JOIN  shift_pattern_details_info spdi   ON (  spas.Shift_Pattern_Details_Id = spdi.Shift_Pattern_Details_Id  ) " 
				 +" INNER JOIN   shift_pattern_details spd   ON ( spdi.Shift_Pattern_Id = spd.Shift_Pattern_Id  ) "  
				 +" WHERE  spd.Shift_Pattern_Code='"+assignShiftPatternDetailsVo.getShiftCode()+"' " 
				 +" AND CONCAT(DATE_FORMAT(spdi.transaction_date, '%Y%m%d'), LPAD(spdi.Shift_Pattern_Sequence_Id, 2, '0')) = ( " 
				 +" SELECT  MAX(CONCAT(DATE_FORMAT(spdi1.transaction_date, '%Y%m%d'), LPAD(spdi1.Shift_Pattern_Sequence_Id, 2,'0'))) "
				 +" FROM shift_pattern_details_info spdi1 " 
				 +" WHERE spdi.Shift_Pattern_Id = spdi1.Shift_Pattern_Id  AND spdi1.transaction_date <= CURRENT_DATE()  ) "
				 +" )AS m1) " 
				 +" FROM `worker_details` wd INNER JOIN `worker_details_info` wdi ON (wd.`Worker_id` = wdi.`Worker_id`) "  
				 +" WHERE "  
				 +" CONCAT(DATE_FORMAT(wdi.transaction_date, '%Y%m%d'), LPAD(wdi.Sequence_Id, 2, '0')) = "      
				 +" ( "     
				 +" SELECT MAX(CONCAT(DATE_FORMAT(wdi1.transaction_date, '%Y%m%d'), LPAD(wdi1.Sequence_Id, 2, '0'))) "     
				 +" FROM worker_details_info wdi1 "     
				 +" WHERE wdi.Worker_id = wdi1.Worker_id "     
				 +" AND wdi1.transaction_date <= CURRENT_DATE() "  
				 +" ) AND wd.`Worker_id` IN ("+workers+") "; 
			List<Object> contentList = new ArrayList<Object>();
			List list = session.createSQLQuery(contentQuery).list();
			for( Object datesObj : list){
				 Object[] datreObj = ( Object[])datesObj;
				 Object[] contobj = new Object[datesList.size()+2];
				 contobj[0] = datreObj[0];
				 contobj[1] = datreObj[1];
				 System.out.println(datreObj[2].toString()+" :: "+noOfDays+" ::headerList:: "+headerList.size());
				 for(int k=0; k < datreObj[2].toString().split(",").length && k < headerList.size()-2; k++){
					 System.out.println(datreObj[2].toString()+"  ::  "+k+"  ::  "+noOfDays+" :: "+(k < datreObj[2].toString().split(",").length));					
					       contobj[k+2] = datreObj[2].toString().split(",")[k];
				 }	
				 contentList.add(contobj);
			}						
			obj.put("headers", headerList);
			obj.put("content", contentList);				
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return obj;
	}
	
	@Override
	public Map<String,List<Object>> previewDetails(AssignShiftPatternDetailsVo assignShiftPatternDetailsVo) {
		// TODO Auto-generated method stub
		Map<String,List<Object>>  obj = new HashMap<String,List<Object>>();
		Session session = sessionFactory.getCurrentSession();
		try{
			String workers ="0";
			for(AssignShiftsVo assignShiftsVo : assignShiftPatternDetailsVo.getAssignShiftsVo()){
				if(assignShiftsVo.getSelected() != null && assignShiftsVo.getSelected())
					workers += ","+assignShiftsVo.getWorkerId();
			}
			
			List tempList = session.createSQLQuery("SELECT define_pattern_by FROM shift_pattern_details  spd INNER JOIN  shift_pattern_details_info spdi ON (spd.shift_pattern_id = spdi.shift_pattern_id) WHERE  spd.shift_pattern_code='"+assignShiftPatternDetailsVo.getShiftCode()+"'").list();
			if(tempList != null && tempList.size() > 0){
				String patternType = (String) tempList.get(0);
				if(patternType != null && patternType.equalsIgnoreCase("Monthly")){
					obj = this.monthlyPreiviewDetails(assignShiftPatternDetailsVo);
				}else if(patternType != null && patternType.equalsIgnoreCase("Bi Weekly")){
					obj = this.weeklyPreiviewDetails(assignShiftPatternDetailsVo);
				}else if(patternType != null && patternType.equalsIgnoreCase("Weekly")){
					obj = this.weeklyPreiviewDetails(assignShiftPatternDetailsVo);
				}else if(patternType != null && patternType.equalsIgnoreCase("Daily")){
					List<Object> headerList = new  ArrayList<Object>();
					headerList.add("Sl No");
					headerList.add("Worker Name");
					Calendar calendar = Calendar.getInstance();	
					System.out.println(DateHelper.convertDateToSQLDate(assignShiftPatternDetailsVo.getPatternDate()).toString().split("-")[2]);
					headerList.add(DateHelper.convertDateToSQLDate(assignShiftPatternDetailsVo.getPatternDate()).toString().split("-")[2]);
					
					String contentQuery = "SELECT wd.`Worker_id` ,CONCAT(wd.`Worker_code`,' - ',IFNULL(wdi.`First_name`,''),' ',IFNULL(wdi.`Last_name`,'') )AS empname,( "
					+" SELECT shift_code FROM shift_pattern_assign_shifts spas " 
					+" INNER JOIN shift_pattern_details_info spdi ON (spas.Shift_Pattern_Details_Id = spdi.Shift_Pattern_Details_Id) "   
					+" INNER JOIN shift_pattern_details spd   ON (spdi.Shift_Pattern_Id = spd.Shift_Pattern_Id) "    
					+" WHERE spd.Shift_Pattern_Code='"+assignShiftPatternDetailsVo.getShiftCode()+"' AND CONCAT(DATE_FORMAT(spdi.transaction_date, '%Y%m%d'), LPAD(spdi.Shift_Pattern_Sequence_Id, 2, '0')) = ( "
					+" SELECT MAX(CONCAT(DATE_FORMAT(spdi1.transaction_date,'%Y%m%d'),LPAD(spdi1.Shift_Pattern_Sequence_Id,2,'0')))   FROM shift_pattern_details_info spdi1 "   
					+" WHERE spdi.Shift_Pattern_Id = spdi1.Shift_Pattern_Id  AND spdi1.transaction_date <= CURRENT_DATE()  ) )  AS shifts "
					+" FROM `worker_details` wd INNER JOIN `worker_details_info` wdi ON (wd.`Worker_id` = wdi.`Worker_id`) "  
					+" WHERE CONCAT(DATE_FORMAT(wdi.transaction_date, '%Y%m%d'), LPAD(wdi.Sequence_Id, 2, '0')) =  ( "
					+" SELECT MAX(CONCAT(DATE_FORMAT(wdi1.transaction_date,'%Y%m%d'),LPAD(wdi1.Sequence_Id,2,'0')))  FROM worker_details_info wdi1 "  
					+" WHERE wdi.Worker_id = wdi1.Worker_id  AND wdi1.transaction_date <= CURRENT_DATE() )  AND wd.`Worker_id` IN ("+workers+") ";
					List<Object> contentList = new ArrayList<Object>();
					List list = session.createSQLQuery(contentQuery).list();
					for( Object datesObj : list){
						 Object[] datreObj = ( Object[])datesObj;
						 Object[] contobj = new Object[3];
						 contobj[0] = datreObj[0];
						 contobj[1] = datreObj[1];
						 contobj[2] = datreObj[2];
						 contentList.add(contobj);
					}
					obj.put("headers", headerList);
					obj.put("content", contentList);
				}
			}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return obj;
	}


	@Override
	public List<AssignShiftPatternUpdateVo> getAssignShiftPatternUpdateMasterData(Integer workerId,Integer year,Integer month) {
		// TODO Auto-generated method stub
		List<AssignShiftPatternUpdateVo> assignShiftPatternUpdateVos = new ArrayList<AssignShiftPatternUpdateVo>();
		Session session = sessionFactory.getCurrentSession();
		try{
			String query="SELECT wd.worker_id,wd.customer_id,wd.company_id,date_format(Business_Date,'%d/%m/%Y')as sdate, shift, wsd.`Department_Id`, CONVERT(CASE WHEN Business_Date >= CURRENT_DATE() THEN 0 ELSE 1 END,UNSIGNED INTEGER) AS edit,CASE WHEN wsd.`Department_Id` IS NULL THEN 'All' ELSE info.Department_Name END AS deptName,CONCAT(wsd.Emp_Code,' - ',IFNULL(Emp_Name,'')) AS ename  FROM `labor_scheduled_shifts`  wsd  INNER JOIN worker_details wd ON (wd.worker_code = wsd.emp_code) LEFT JOIN  department_details_info info ON(info.Department_Id = wsd.Department_Id  AND CONCAT(DATE_FORMAT(info.transaction_date, '%Y%m%d'), LPAD(info.`Department_Sequence_Id`, 2, '0')) =  (SELECT MAX(CONCAT(DATE_FORMAT(info1.transaction_date, '%Y%m%d'), LPAD(info1.Department_Sequence_Id, 2, '0')))  FROM department_details_info info1  WHERE info.Department_Id = info1.Department_Id AND info1.transaction_date <= CURRENT_DATE()  )  ) "					
					+ " WHERE wd.Worker_id="+workerId;
			if(year != null && year > 0){
				query +=" AND YEAR(Business_Date) ="+year;
			}
			if(month != null && month > 0){
				query +=" AND MONTH(Business_Date) ="+month;
			}
			query += " ORDER BY `Business_Date` ";
			List tempList = session.createSQLQuery(query).list();
			for(Object o : tempList){
				Object[] obj = (Object[]) o;
				AssignShiftPatternUpdateVo assignShiftPatternUpdateVo = new AssignShiftPatternUpdateVo();
				assignShiftPatternUpdateVo.setWorkerId((Integer)obj[0]);	
				assignShiftPatternUpdateVo.setCustomerId((Integer)obj[1]);
				assignShiftPatternUpdateVo.setCompanyId((Integer)obj[2]);
				assignShiftPatternUpdateVo.setShiftDate(obj[3]+"");
				assignShiftPatternUpdateVo.setShiftCode(obj[4]+"");
				assignShiftPatternUpdateVo.setDepartmentId((Integer)obj[5]);				
				assignShiftPatternUpdateVo.setEdit(Integer.valueOf(obj[6]+""));
				assignShiftPatternUpdateVo.setDepartmentName(obj[7]+"");
				assignShiftPatternUpdateVo.setWorkerCodeName(obj[8]+"");
				assignShiftPatternUpdateVos.add(assignShiftPatternUpdateVo);
			}
			
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		
		return assignShiftPatternUpdateVos;
	}


	@Override
	public Integer saveAssignShiftPatternRecord(AssignShiftPatternUpdateVo assignShiftPatternDetailsVo) {
		// TODO Auto-generated method stub
		Integer id = 0;
		try{
			if(assignShiftPatternDetailsVo.getDepartmentId() != null && assignShiftPatternDetailsVo.getDepartmentId() > 0)
				sessionFactory.getCurrentSession().createSQLQuery(" UPDATE `labor_scheduled_shifts` SET `Shift` = '"+assignShiftPatternDetailsVo.getShiftCode()+"' ,`Department_Id` = '"+assignShiftPatternDetailsVo.getDepartmentId()+"' WHERE date_format(`Business_Date`,'%d/%m/%Y') = '"+assignShiftPatternDetailsVo.getShiftDate()+"' AND `Emp_Code` =( SELECT a.Worker_code FROM worker_details a WHERE a.Worker_id="+assignShiftPatternDetailsVo.getWorkerId()+") ").executeUpdate();
			else
				sessionFactory.getCurrentSession().createSQLQuery(" UPDATE `labor_scheduled_shifts` SET `Shift` = '"+assignShiftPatternDetailsVo.getShiftCode()+"' WHERE date_format(`Business_Date`,'%d/%m/%Y') = '"+assignShiftPatternDetailsVo.getShiftDate()+"' AND `Emp_Code` = ( SELECT a.Worker_code FROM worker_details a WHERE a.Worker_id="+assignShiftPatternDetailsVo.getWorkerId()+")  ").executeUpdate();
		id = 1;
		}catch(Exception e){
			id = 0;
			log.error("Error Occured ",e);
		}
		return id;
	}


	@Override
	public List<SimpleObject> getPatternCodesbyPlantId(PlantVo plantVo) {
		// TODO Auto-generated method stub
		List<SimpleObject> patternCodes = new ArrayList<SimpleObject>();
		Session session = sessionFactory.getCurrentSession();
		try{
			String query = "SELECT child.Shift_Pattern_Id, parent.`Shift_Pattern_Code`,CONCAT(parent.`Shift_Pattern_Code`,' - ', child.`Shift_Pattern_Name`,' - ',child.`Define_Pattern_By`) FROM `shift_pattern_details` parent INNER JOIN `shift_pattern_details_info` child ON (parent.`Shift_Pattern_Id` = child.`Shift_Pattern_Id`) WHERE CONCAT(DATE_FORMAT(child.transaction_date, '%Y%m%d'), LPAD(child.`Shift_Pattern_Sequence_Id`, 2, '0')) = ( SELECT MAX(CONCAT(DATE_FORMAT(child1.transaction_date, '%Y%m%d'), LPAD(child1.`Shift_Pattern_Sequence_Id`, 2, '0'))) FROM shift_pattern_details_info child1 WHERE  child.Shift_Pattern_Id = child1.Shift_Pattern_Id  AND child1.transaction_date <= CURRENT_DATE() )";
			if(plantVo != null && plantVo.getCustomerId() > 0){
				query +=" AND parent.`Customer_Id`="+plantVo.getCustomerId();
			}
			if(plantVo != null && plantVo.getCompanyId() > 0){
				query +=" AND parent.`Company_Id`="+plantVo.getCompanyId();
			}
			if(plantVo != null && plantVo.getLocationId() > 0){
				query +=" AND child.`Location_Id`="+plantVo.getLocationId();
			}
			if(plantVo != null && plantVo.getPlantId() > 0){
				query +=" AND child.`Plant_Id`="+plantVo.getPlantId();
			}
			query +=" AND child.Status='Y' ORDER BY child.`Shift_Pattern_Name`";
			List planttempList = session.createSQLQuery(query).list();
			
			for(Object o : planttempList){
				Object[] obj = (Object[]) o;			
				patternCodes.add(new SimpleObject((Integer)obj[0], obj[1]+"",obj[2]+"" ));		
			}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return patternCodes;
	}
		
	
}
