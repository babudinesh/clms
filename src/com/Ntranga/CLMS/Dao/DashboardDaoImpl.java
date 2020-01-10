package com.Ntranga.CLMS.Dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.DashboardChartVo;
import com.Ntranga.CLMS.vo.DashboardComplianceStatusVo;
import com.Ntranga.CLMS.vo.DashboardVo;
import com.Ntranga.CLMS.vo.DashboardWorkersInfoVo;
import com.Ntranga.CLMS.vo.DashboardWorkersTypeVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.core.DateHelper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import common.Logger;

@Transactional
@Repository("dashboardDao")
@SuppressWarnings({"unused","unchecked","rawtypes"})
public class DashboardDaoImpl implements DashboardDao {

	@Autowired
	private SessionFactory sessionFactory;

	private static Logger log = Logger.getLogger(DashboardDaoImpl.class);
	
/*
 * Old Dashboard Code
 */
	@Override
	public Map<String, List> getDashboardDetails(String dashboardJsonString) {
		// TODO Auto-generated method stub
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		try{
			System.out.println(dashboardJsonString+" :::  dashboardJsonString");
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(dashboardJsonString);		
			
			String searchQuery = "";
			
			
			if(jo.get("customerId")!= null && !jo.get("customerId").toString().equalsIgnoreCase("null") && jo.get("customerId").getAsInt() > 0){
				searchQuery += " AND v.customer_id='"+jo.get("customerId").getAsString()+"' ";
			}
			if(jo.get("companyId")!= null && !jo.get("companyId").toString().equalsIgnoreCase("null") && jo.get("companyId").getAsInt() > 0){
				searchQuery += " AND v.company_id='"+jo.get("companyId").getAsString()+"' ";
			}
			if(jo.get("vendorId")!= null && !jo.get("vendorId").toString().equalsIgnoreCase("null") && jo.get("vendorId").getAsInt() > 0 ){
				searchQuery += " AND v.vendor_id='"+jo.get("vendorId").getAsString()+"' ";
			}
			/*if(jo.get("workerId")!= null && !jo.get("workerId").toString().equalsIgnoreCase("null") && jo.get("workerId").getAsInt() > 0){
				searchQuery += " AND wd.worker_id='"+jo.get("workerId").getAsString()+"' ";
			}*/
					
			
			String attendanceQuery = " SELECT shift, COUNT(shift) FROM labor_time_report ltr INNER JOIN worker_details v ON (ltr.emp  = v.worker_code ) WHERE business_date = CURRENT_DATE() ";
			attendanceQuery += searchQuery+"  GROUP BY shift ";
			List attendanceTempList = sessionFactory.getCurrentSession().createSQLQuery(attendanceQuery).list();
			List attendanceList = new ArrayList<>();
			
			for(Object Obj : attendanceTempList){
				Object [] objarray = (Object[]) Obj;
				attendanceList.add(new SimpleObject( new BigInteger(objarray[1]+"").intValue(), objarray[0]+""));
			}
			masterInfoMap.put("attendanceList", attendanceList);
			
			
			
			
			String vendorQuery = " SELECT vendor_name,vendor_details_info_id FROM vendor_details_info v WHERE vendor_status RLIKE 'New|Pending For Approval' AND CONCAT(DATE_FORMAT(v.transaction_date, '%Y%m%d'), LPAD(v.Sequence_Id, 2, '0')) = (SELECT MAX(CONCAT(DATE_FORMAT(vdi.transaction_date, '%Y%m%d'), LPAD(vdi.Sequence_Id, 2, '0'))) FROM vendor_details_info vdi WHERE (v.vendor_id=vdi.vendor_id AND vdi.transaction_date <= CURRENT_DATE()) GROUP BY vdi.vendor_id) ";
			vendorQuery += searchQuery+"  GROUP BY vendor_id ";
			List vendorTempList = sessionFactory.getCurrentSession().createSQLQuery(vendorQuery).list();
			List vendorList = new ArrayList<>();
			
			for(Object Obj : vendorTempList){
				Object [] objarray = (Object[]) Obj;
				vendorList.add(new SimpleObject( new BigInteger(objarray[1]+"").intValue(), objarray[0]+""));
			}	
			masterInfoMap.put("vendorList", vendorList);
			
			String workerCountQuery = " SELECT SUM(NOT ISNULL(date_of_joining)) AS joining, SUM(NOT ISNULL(date_of_leaving)) AS leaving FROM worker_details v INNER JOIN worker_details_info wdi ON (v.worker_id = wdi.worker_id) WHERE CONCAT(wdi.transaction_date, wdi.sequence_id) =(SELECT MAX(CONCAT(transaction_date, sequence_id)) FROM worker_details_info vdi WHERE wdi.worker_id = vdi.worker_id GROUP BY worker_id) AND DATE_FORMAT( Date_Of_Joining,'%m-%Y') = DATE_FORMAT( CURRENT_DATE,'%m-%Y') ";
			workerCountQuery += searchQuery;
			List workerCountTempList = sessionFactory.getCurrentSession().createSQLQuery(workerCountQuery).list();
			List workerCountList = new ArrayList<>();
			
			for(Object Obj : workerCountTempList){
				Object [] objarray = (Object[]) Obj;
				workerCountList.add(new SimpleObject( new BigInteger(objarray[0]+"").intValue(), objarray[1]+"", objarray[1]+""));
			}	
			masterInfoMap.put("workerCountList", workerCountList);
			
			
			String notificationQuery = " SELECT CONCAT(vendor_name,' ',COALESCE(vendor_status,''),' on ', COALESCE(DATE_FORMAT(status_date,'%d/%m/%Y'),'')) as name,vendor_details_info_id as id FROM vendor_details_info v WHERE vendor_status RLIKE 'Debarred|Terminated' AND CONCAT(DATE_FORMAT(v.transaction_date, '%Y%m%d'), LPAD(v.Sequence_Id, 2, '0')) = (SELECT MAX(CONCAT(DATE_FORMAT(vdi.transaction_date, '%Y%m%d'), LPAD(vdi.Sequence_Id, 2, '0'))) FROM vendor_details_info vdi WHERE (v.vendor_id=vdi.vendor_id AND vdi.transaction_date <= CURRENT_DATE()) GROUP BY vdi.vendor_id) AND DATE_FORMAT( status_date,'%m-%Y') = DATE_FORMAT( CURRENT_DATE,'%m-%Y') ";
			notificationQuery += searchQuery+"  GROUP BY vendor_id UNION ALL ";
			notificationQuery += " SELECT CONCAT(first_name,' ',COALESCE(reason_for_status_change,''),' on ', COALESCE(DATE_FORMAT(date_of_leaving,'%d/%m/%Y'),'')) as name,worker_info_id as id FROM worker_details v INNER JOIN worker_details_info wdi ON (v.worker_id = wdi.worker_id) WHERE reason_for_status_change RLIKE 'Debarred|Terminated' AND CONCAT(wdi.transaction_date, wdi.sequence_id) =(SELECT MAX(CONCAT(transaction_date, sequence_id)) FROM worker_details_info vdi WHERE wdi.worker_id = vdi.worker_id GROUP BY worker_id) AND DATE_FORMAT( Date_Of_Joining,'%m-%Y') = DATE_FORMAT( CURRENT_DATE,'%m-%Y') ";
			notificationQuery += searchQuery+"  GROUP BY v.worker_id ";
			
			List notificationTempList = sessionFactory.getCurrentSession().createSQLQuery(notificationQuery).list();
			List notificationList = new ArrayList<>();
			
			for(Object Obj : notificationTempList){
				Object [] objarray = (Object[]) Obj;
				notificationList.add(new SimpleObject( new BigInteger(objarray[1]+"").intValue(), objarray[0]+""));
			}	
			masterInfoMap.put("notificationList", notificationList);
			
			
			String weeklyAttendanceQuery = " SELECT DATE_FORMAT(date_field,'%d/%m/%Y'),COUNT(business_date) AS workers FROM ( "
				   +" SELECT date_field FROM ( SELECT MAKEDATE(YEAR(SUBDATE(CURRENT_DATE, WEEKDAY(CURRENT_DATE))),1) + INTERVAL (MONTH(SUBDATE(CURRENT_DATE, WEEKDAY(CURRENT_DATE)))-1) MONTH + INTERVAL daynum DAY date_field "  
				   +" FROM ( SELECT t*10+u daynum FROM "
				   +" (SELECT 0 t UNION SELECT 1 UNION SELECT 2 UNION SELECT 3) A, " 
				   +" (SELECT 0 u UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 " 
				   +" UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 " 
				   +" UNION SELECT 8 UNION SELECT 9) B  ORDER BY daynum  "
				   +" ) AA  ) dates "
				   +" WHERE date_field BETWEEN SUBDATE(CURRENT_DATE, WEEKDAY(CURRENT_DATE)) AND DATE_ADD(SUBDATE(CURRENT_DATE, WEEKDAY(CURRENT_DATE)),INTERVAL 6 DAY) ) AS t1 " 
				   +" LEFT JOIN labor_time_report ltr ON (date_field = business_date) "
				   +" LEFT JOIN worker_details v ON (ltr.emp  = v.worker_code AND date_field = business_date ";
			weeklyAttendanceQuery += searchQuery+" ) GROUP BY date_field ORDER BY date_field ";
			List weeklyAttendanceTempList = sessionFactory.getCurrentSession().createSQLQuery(weeklyAttendanceQuery).list();
			List weeklyAttendanceList = new ArrayList<>();
			
			for(Object Obj : weeklyAttendanceTempList){
				Object [] objarray = (Object[]) Obj;
				weeklyAttendanceList.add(new SimpleObject( new BigInteger(objarray[1]+"").intValue(), objarray[0]+""));
			}
			masterInfoMap.put("weeklyAttendanceList", weeklyAttendanceList);
			
			String weeklyOvertimeQuery = " SELECT DATE_FORMAT(date_field,'%d/%m/%Y'),CONVERT(COALESCE(SUM(othours),0),UNSIGNED INTEGER) AS othours FROM ( "
			   +" SELECT date_field FROM ( SELECT MAKEDATE(YEAR(SUBDATE(CURRENT_DATE, WEEKDAY(CURRENT_DATE))),1) + INTERVAL (MONTH(SUBDATE(CURRENT_DATE, WEEKDAY(CURRENT_DATE)))-1) MONTH + INTERVAL daynum DAY date_field "  
			   +" FROM ( SELECT t*10+u daynum FROM "
			   +" (SELECT 0 t UNION SELECT 1 UNION SELECT 2 UNION SELECT 3) A, " 
			   +" (SELECT 0 u UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 " 
			   +" UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 " 
			   +" UNION SELECT 8 UNION SELECT 9) B  ORDER BY daynum  "
			   +" ) AA  ) dates "
			   +" WHERE date_field BETWEEN SUBDATE(CURRENT_DATE, WEEKDAY(CURRENT_DATE)) AND DATE_ADD(SUBDATE(CURRENT_DATE, WEEKDAY(CURRENT_DATE)),INTERVAL 6 DAY) ) AS t1 " 
			   +" LEFT JOIN labor_time_report ltr ON (date_field = business_date) "
			   +" LEFT JOIN worker_details v ON (ltr.emp  = v.worker_code AND date_field = business_date ";
			weeklyOvertimeQuery += searchQuery+" ) GROUP BY date_field ORDER BY date_field ";
			List weeklyOvertimeTempList = sessionFactory.getCurrentSession().createSQLQuery(weeklyOvertimeQuery).list();
			List weeklyOvertimeList = new ArrayList<>();
			
			for(Object Obj : weeklyOvertimeTempList){
				Object [] objarray = (Object[]) Obj;
				weeklyOvertimeList.add(new SimpleObject( new BigInteger(objarray[1]+"").intValue(), objarray[0]+""));
			}
			masterInfoMap.put("weeklyOvertimeList", weeklyOvertimeList);
			
			
			
		}catch(Exception e){
			log.info(e.getMessage());
			e.printStackTrace();
		}
		return masterInfoMap;
	}
	
	
	
	/*
	 * This method will be used to  get the Workers count for new joinees and seperated workers for today, current week and current month
	 */
	@Override
	public List getWorkersHeadCount(Integer customerId, Integer companyId, Integer vendorId){
		log.info("Entered in getWorkersHeadCount ::  customerId = "+customerId+", cmpanyId = "+companyId+", vendorId = "+vendorId);
		Session session = sessionFactory.getCurrentSession();
		
		String qry = null;
		if(customerId != null && customerId > 0){
			
		}else{
			customerId = -1;
		}
		
		if(companyId != null && companyId > 0){
			
		}else{
			companyId = -1;
		}

		if(vendorId != null && vendorId > 0){
			
		}else{
			vendorId = -1;
		}
		
		String monthQuery = "  SELECT COUNT(wdi.workerId) AS count, 'New Joiners' AS type, "
							+ "	CONCAT(substring(DATE_FORMAT(current_date(),'%M'),1,3),'-',DATE_FORMAT(current_date(),'%y')) As data "
							+ " FROM v_worker_details wdi "
							+ " WHERE (wdi.Is_Active = 'Y'  OR wdi.Is_Active = 'I') "
							+ " AND (wdi.Customer_Id = "+customerId+" OR "+customerId+" = -1 ) "	
							+ " AND (wdi.Company_Id = "+companyId+" OR "+companyId+" = -1 ) "	
							+ " AND (wdi.vendor_Id = "+vendorId+" OR "+vendorId+" = -1 ) "	
							+ " AND  MONTH(wdi.Date_Of_Joining) = MONTH(current_date()) "
							+ " AND  YEAR(wdi.Date_Of_Joining) = YEAR(current_date()) "
							//+ " AND wdi.Date_Of_Joining like concat(YEAR(current_date()),'-',MONTH(current_date()),'%') " 
							+ " UNION "
							+ " ( " 
							+ " SELECT count(wdi.workerId) AS count, 'Separated Workers' AS type, "
							+ "	CONCAT(substring(DATE_FORMAT(current_date(),'%M'),1,3),'-',DATE_FORMAT(current_date(),'%y')) As data "
							+ " FROM v_worker_details wdi "
							+ " WHERE 1 = 1 "
							//+ " AND wdi.Is_Active = 'N' "
							+ " AND (wdi.Customer_Id = "+customerId+" OR "+customerId+" = -1 )"	
							+ " AND (wdi.Company_Id = "+companyId+" OR "+companyId+" = -1 )"	
							+ " AND (wdi.vendor_Id = "+vendorId+" OR "+vendorId+" = -1 )"	
							//+ " AND wdi.Date_Of_Leaving like concat(YEAR(current_date()),'-',MONTH(current_date()),'%') )" ;
							+ " AND  MONTH(wdi.Date_Of_Leaving) = MONTH(current_date())"
							+ " AND  YEAR(wdi.Date_Of_Leaving) = YEAR(current_date()) )";
		
		String dayQuery  = "  SELECT COUNT(wdi.workerId) AS count, 'New Joiners' AS type, "
				 			+ " CONCAT(DATE_FORMAT(current_date(),'%d'),'-',substring(DATE_FORMAT(current_date(),'%M'),1,3),'-',DATE_FORMAT(current_date(),'%y')) As data "
				 			+ " FROM v_worker_details wdi "
							+ " WHERE ( wdi.Is_Active = 'Y'  OR wdi.Is_Active = 'I' ) "
							+ " AND (wdi.Customer_Id = "+customerId+" OR "+customerId+" = -1 )"	
							+ " AND (wdi.Company_Id = "+companyId+" OR "+companyId+" = -1 )"	
							+ " AND (wdi.vendor_Id = "+vendorId+" OR "+vendorId+" = -1 )"	
							+ " AND wdi.Date_Of_Joining =  current_date()"
							+ " UNION "
							+ " ( "
							+ " SELECT count(wdi.workerId) AS count, 'Separated Workers' AS type, "
							//+ " DATE_FORMAT(current_date(),'%d/%m/%Y') As data "
				 			+ " CONCAT(DATE_FORMAT(current_date(),'%d'),'-',substring(DATE_FORMAT(current_date(),'%M'),1,3),'-',DATE_FORMAT(current_date(),'%y')) As data "
							+ " FROM v_worker_details wdi "
							+ " WHERE 1 = 1  "
						//	+ " AND wdi.Is_Active = 'N' "
							+ " AND (wdi.Customer_Id = "+customerId+" OR "+customerId+" = -1 )"	
							+ " AND (wdi.Company_Id = "+companyId+" OR "+companyId+" = -1 )"	
							+ " AND (wdi.vendor_Id = "+vendorId+" OR "+vendorId+" = -1 )"	
							+ " AND wdi.Date_Of_Leaving =  current_date())";
		
		String weekQuery  = "  SELECT COUNT(wdi.workerId) AS count, 'New Joiners' AS type,"
								+ " CONCAT(DATE_FORMAT(DATE_ADD((SELECT CURDATE() - INTERVAL (WEEKDAY(CURDATE())+1)DAY),INTERVAL 1 DAY) ,'%d'),'-',"
								+ " substring(DATE_FORMAT(DATE_ADD((SELECT CURDATE() - INTERVAL (WEEKDAY(CURDATE())+1)DAY),INTERVAL 1 DAY) ,'%M'),1,3),'-',"
								+ " DATE_FORMAT(DATE_ADD((SELECT CURDATE() - INTERVAL (WEEKDAY(CURDATE())+1)DAY),INTERVAL 1 DAY) ,'%y'),' to ',"
								+ " DATE_FORMAT(DATE_ADD((SELECT CURDATE() - INTERVAL (WEEKDAY(CURDATE())+1)DAY),INTERVAL 7 DAY) ,'%d'),'-',"
								+ " substring(DATE_FORMAT(DATE_ADD((SELECT CURDATE() - INTERVAL (WEEKDAY(CURDATE())+1)DAY),INTERVAL 7 DAY) ,'%M'),1,3),'-',"
								+ " DATE_FORMAT(DATE_ADD((SELECT CURDATE() - INTERVAL (WEEKDAY(CURDATE())+1)DAY),INTERVAL 7 DAY) ,'%y')"
								+ " ) As data"
				 			+ " FROM v_worker_details wdi "
							+ " WHERE ( wdi.Is_Active = 'Y'  OR wdi.Is_Active = 'I' ) "
							+ " AND (wdi.Customer_Id = "+customerId+" OR "+customerId+" = -1 )"	
							+ " AND (wdi.Company_Id = "+companyId+" OR "+companyId+" = -1 )"	
							+ " AND (wdi.vendor_Id = "+vendorId+" OR "+vendorId+" = -1 )"	
							+ " AND WEEKOFYEAR(wdi.Date_Of_Joining) =  WEEKOFYEAR(current_date())"
							+ " AND  YEAR(wdi.Date_Of_Joining) = YEAR(current_date())"
							+ " UNION "
							+ " ( "
							+ " SELECT count(wdi.workerId) AS count, 'Separated Workers' AS type, "
								+ " CONCAT(DATE_FORMAT(DATE_ADD((SELECT CURDATE() - INTERVAL (WEEKDAY(CURDATE())+1)DAY),INTERVAL 1 DAY) ,'%d'),'-',"
								+ " substring(DATE_FORMAT(DATE_ADD((SELECT CURDATE() - INTERVAL (WEEKDAY(CURDATE())+1)DAY),INTERVAL 1 DAY) ,'%M'),1,3),'-',"
								+ " DATE_FORMAT(DATE_ADD((SELECT CURDATE() - INTERVAL (WEEKDAY(CURDATE())+1)DAY),INTERVAL 1 DAY) ,'%y'),' to ',"
								+ " DATE_FORMAT(DATE_ADD((SELECT CURDATE() - INTERVAL (WEEKDAY(CURDATE())+1)DAY),INTERVAL 7 DAY) ,'%d'),'-',"
								+ " substring(DATE_FORMAT(DATE_ADD((SELECT CURDATE() - INTERVAL (WEEKDAY(CURDATE())+1)DAY),INTERVAL 7 DAY) ,'%M'),1,3),'-',"
								+ " DATE_FORMAT(DATE_ADD((SELECT CURDATE() - INTERVAL (WEEKDAY(CURDATE())+1)DAY),INTERVAL 7 DAY) ,'%y')"
								+ " ) As data"
							+ " FROM v_worker_details wdi "
							+ " WHERE 1 = 1  "
							//+ " AND wdi.Is_Active = 'N' "
							+ " AND (wdi.Customer_Id = "+customerId+" OR "+customerId+" = -1 )"	
							+ " AND (wdi.Company_Id = "+companyId+" OR "+companyId+" = -1 )"	
							+ " AND (wdi.vendor_Id = "+vendorId+" OR "+vendorId+" = -1 )"	
							+ " AND WEEKOFYEAR(wdi.Date_Of_Leaving) =  WEEKOFYEAR(current_date()) "
							+ " AND  YEAR(wdi.Date_Of_Leaving) = YEAR(current_date()) )";
		
		
		String newStatusQuery  = "  SELECT COUNT(wdi.workerId) AS count, 'New Registrations' AS type, 'Workers Approval Pending' AS newCount"
					 			+ " FROM v_worker_details wdi "
								+ " WHERE wdi.Is_Active = 'I' "
								+ " AND (wdi.Customer_Id = "+customerId+" OR "+customerId+" = -1 )"	
								+ " AND (wdi.Company_Id = "+companyId+" OR "+companyId+" = -1 )"	
								+ " AND (wdi.vendor_Id = "+vendorId+" OR "+vendorId+" = -1 )"	;
							
								
								
		String activeHeadCountQuery  = "  SELECT COUNT(wdi.workerId) AS count, 'Total Workers' AS type, 'Worker Head Count' AS newCount"
	 			+ " FROM v_worker_details wdi "
				+ " WHERE wdi.Is_Active = 'Y' "
				+ " AND (wdi.Customer_Id = "+customerId+" OR "+customerId+" = -1 )"	
				+ " AND (wdi.Company_Id = "+companyId+" OR "+companyId+" = -1 )"	
				+ " AND (wdi.vendor_Id = "+vendorId+" OR "+vendorId+" = -1 )"	;
		
		
		//String returnString = "{ ";

		//List<ArrayList> returnDataList = new ArrayList<ArrayList>();
		List<DashboardWorkersInfoVo>  returnList = new ArrayList();
		try{
			List<DashboardWorkersTypeVo> monthList = new ArrayList();
			List<DashboardWorkersTypeVo> weekList = new ArrayList();
			List<DashboardWorkersTypeVo> dayList = new ArrayList();
			List<DashboardWorkersTypeVo> newStatusList = new ArrayList();
			List<DashboardWorkersTypeVo> activeHeadCountList = new ArrayList();
			
			List month = session.createSQLQuery(monthQuery).list();
			List week = session.createSQLQuery(weekQuery).list();
			List day = session.createSQLQuery(dayQuery).list();
			List newStatus = session.createSQLQuery(newStatusQuery).list();
			List activeHeadCount = session.createSQLQuery(activeHeadCountQuery).list();
			
			/*		[230, New Joiners, Dec-16]
					[0, Seperated, Dec-16]

					[16, New Joiners, 12/12-18/12]
					[0, Seperated, 12/12-18/12]

					[2, New Joiners, 14/12/2016]
					[0, Seperated, 14/12/2016]*/


			
			
			DashboardWorkersInfoVo info = new DashboardWorkersInfoVo();
		
			String str = "";
			
			for(Object obj : activeHeadCount){
				Object[] o = (Object[])obj;
				DashboardWorkersTypeVo object = new DashboardWorkersTypeVo();
				object.setWorkerType((String)o[1]);
				object.setTotalValue(((BigInteger)o[0]).intValue());
				str= (String)o[2];
				System.out.println();
				activeHeadCountList.add(object);
			}
			info.setDateRange(str);
			info.setTypes(activeHeadCountList);
			returnList.add(info);
			
			info = new DashboardWorkersInfoVo();
			str = "";
			for(Object obj : month){
				Object[] o = (Object[])obj;
				DashboardWorkersTypeVo object = new DashboardWorkersTypeVo();
				object.setWorkerType((String)o[1]);
				object.setTotalValue(((BigInteger)o[0]).intValue());

				
				str= (String)o[2];
				System.out.println();
				
				monthList.add(object);
			}
			info.setDateRange(str);
			info.setTypes(monthList);
			returnList.add(info);
			//returnString += "\" "+str+"\" : "+monthList;

			info = new DashboardWorkersInfoVo();
			str = "";
			for(Object obj : week){
				Object[] o = (Object[])obj;
				DashboardWorkersTypeVo object = new DashboardWorkersTypeVo();
				object.setWorkerType((String)o[1]);
				object.setTotalValue(((BigInteger)o[0]).intValue());
				str= (String)o[2];
				weekList.add(object);
				System.out.println("Week  : "+object);
			}
			info.setDateRange(str);
			info.setTypes(weekList);
			//returnString += ",[\" "+str+"\" : "+weekList+"] ";
			returnList.add(info);
			
			info = new DashboardWorkersInfoVo();
			str = "";
			for(Object obj : day){
				
				Object[] o = (Object[])obj;
				DashboardWorkersTypeVo object = new DashboardWorkersTypeVo();
				object.setWorkerType((String)o[1]);
				object.setTotalValue(((BigInteger)o[0]).intValue());
				str= (String)o[2];

				dayList.add(object);
			}
			
			info.setDateRange(str);
			info.setTypes(dayList);
			returnList.add(info);
			
			info = new DashboardWorkersInfoVo();
			//str = "";
			for(Object obj : newStatus){
				
				Object[] o = (Object[])obj;
				DashboardWorkersTypeVo object = new DashboardWorkersTypeVo();
				object.setWorkerType((String)o[1]);
				object.setTotalValue(((BigInteger)o[0]).intValue());
				str= (String)o[2];

				newStatusList.add(object);
			}
			
			info.setDateRange(str);
			info.setTypes(newStatusList);
			
			
			returnList.add(info);
			//returnString += ",[\" "+str+"\" : "+dayList+"] ";
			
			//returnString += "}";
			System.out.println(returnList);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("Exception Occurred in getWorkersHeadCount()... ", e);
		}
		log.info("Exiting from getWorkersHeadCount()....   "+returnList);
		return returnList;
	}
	
	/*
	 * This method will be used to get active  workers count by age group 
	 */
	@Override
	public  <V> V  getWorkersCountByAgeGroup(DashboardVo paramDashboard){
		log.info("Entered in  getWorkersCountByAgeGroup()  ::  "+paramDashboard);
		Integer customerId = -1;
		Integer companyId = -1;
		Integer vendorId = -1;
		java.sql.Date fromDate= null;
		java.sql.Date toDate= null;
		if(paramDashboard != null && paramDashboard.getCustomerId() != null && paramDashboard.getCustomerId() > 0){
			customerId = paramDashboard.getCustomerId();
		}
		if(paramDashboard != null && paramDashboard.getCompanyId() != null && paramDashboard.getCompanyId() > 0){
			companyId = paramDashboard.getCompanyId();
		}
		if(paramDashboard != null && paramDashboard.getVendorId() != null && paramDashboard.getVendorId() > 0){
			vendorId = paramDashboard.getVendorId();
		}
		if(paramDashboard != null && paramDashboard.getFromDate() != null ){
			fromDate = DateHelper.convertDateToSQLDate(paramDashboard.getFromDate());
		}
		if(paramDashboard != null && paramDashboard.getToDate() != null  ){
			toDate = DateHelper.convertDateToSQLDate(paramDashboard.getToDate());
		}else if(paramDashboard.getFromDate() != null){
			toDate = DateHelper.convertDateToSQLDate(paramDashboard.getFromDate());
		}
		String query = "CALL WorkerAgeStatusProcedure( "+customerId+" , "+companyId+" , "+vendorId+", '"+fromDate+"' , '"+toDate+"' )";

		List<Object> ageGroupList = new ArrayList<Object>();
		try{
			ageGroupList = sessionFactory.getCurrentSession().createSQLQuery(query).list();
		}catch(Exception e){
			e.printStackTrace();
			log.error("Exception Occurred in getWorkersCountByAgeGroup()...  ", e);
		}
		log.info("Exiting from  getWorkersCountByAgeGroup()  ::  "+ageGroupList);
		return (V) ageGroupList;
	}
		
	/*
	 * This method will be used to get the workers overtime based on given data
	 */
	@Override
	public DashboardChartVo  getOvertimeDetails(DashboardVo paramDashboard){
		log.info("Entered in getOvertimeDetails()  ::  "+paramDashboard);
		String weeklyOvertimeQuery = " SELECT DATE_FORMAT(Business_Date,'%d/%m/%Y'), CONVERT(COALESCE(SUM(ltr.othours),0),UNSIGNED INTEGER) AS othours FROM "+paramDashboard.getSchemaName()+".labor_time_report ltr  "
				       + "  JOIN worker_details v ON (ltr.emp  = v.worker_code) "
                    //   + " LEFT JOIN Customer_View cv ON v.Customer_Id = cv.Customer_Id AND cv.Is_Active = 'Y' "
                    // + " LEFT JOIN company_view cmpv ON v.Customer_Id = cmpv.Customer_Id AND v.Company_Id = cmpv.Company_Id AND cmpv.Is_Active = 'Y' "
					// + " LEFT JOIN vendor_view vv ON vv.Vendor_Id = v.Vendor_Id AND vv.Vendor_Status = 'Validated' "
					 + " WHERE 1 = 1 " ;
				
		DashboardChartVo chart = new DashboardChartVo();
		String returnString = "{ " ;
		
		System.out.println(paramDashboard);
		
		if(paramDashboard != null && paramDashboard.getFromDate() != null && paramDashboard.getToDate() != null){
			weeklyOvertimeQuery += " AND ltr.Business_Date BETWEEN '"+DateHelper.convertDateToSQLDate(paramDashboard.getFromDate())+"' AND '"+DateHelper.convertDateToSQLDate(paramDashboard.getToDate())+"' ";
		}
		
		if(paramDashboard != null && paramDashboard.getCustomerId() != null && paramDashboard.getCustomerId() > 0){
			weeklyOvertimeQuery += " AND v.Customer_Id =  "+paramDashboard.getCustomerId();
		}
		
		if(paramDashboard != null && paramDashboard.getCompanyId() != null && paramDashboard.getCompanyId() > 0){
			weeklyOvertimeQuery += " AND v.Company_Id =  "+paramDashboard.getCompanyId();
		}
		
		/*if(paramDashboard != null && paramDashboard.getVendorId() != null && paramDashboard.getVendorId() > 0){
			weeklyOvertimeQuery += " AND v.Vendor_Id =  "+paramDashboard.getVendorId();
		}*/
		
		weeklyOvertimeQuery += "  GROUP BY ltr.Business_Date ORDER BY ltr.Business_Date ";
		
		List<SimpleObject> weeklyOvertimeList = new ArrayList();
		try{
			System.out.println("executing query");
			List weeklyOvertimeTempList = sessionFactory.getCurrentSession().createSQLQuery(weeklyOvertimeQuery).list();
			System.out.println("executing query completed");
			List<List<Integer>> data = new ArrayList();

			List<Integer> present = new ArrayList();
			List<String> ticks = new ArrayList();
			for(Object Obj : weeklyOvertimeTempList){
				System.out.println("in loop");
				Object [] objarray = (Object[]) Obj;
				present.add(((BigInteger)objarray[1]).intValue());
				ticks.add((String)objarray[0]);
				System.out.println("Present : "+present+", Ticks : "+ticks);
			}
			if(present.size() > 0){
				data.add(present);
			}
			chart.setChartData(data);
			chart.setTicks(ticks);
		}catch(Exception e){
			log.error("Exception Occurred in getOvertimeDetails()",e);
			e.printStackTrace();
		}
		log.info("Exiting from getOvertimeDetails() ::  "+chart);
		return chart;
	}
	
	/*
	 * This method will be used to get Workers Shift Wise count  present and scheduled
	 */
	@Override
	public <V> V getShiftWiseAttendanceDetails(DashboardVo paramDashboard){
		log.info("Entered in getShiftWiseAttendanceDetails()  ::  "+paramDashboard);
		Session session = sessionFactory.getCurrentSession();
		String queryParams = "";
		
		if(paramDashboard.getFromDate() != null && paramDashboard.getToDate() == null){
			paramDashboard.setToDate(paramDashboard.getFromDate());
		}
		
		if(paramDashboard != null && paramDashboard.getCustomerId() != null && paramDashboard.getCustomerId() > 0)	{
			queryParams += "AND wd.Customer_Id = "+paramDashboard.getCustomerId();
		}
		
		if(paramDashboard != null && paramDashboard.getCompanyId() != null && paramDashboard.getCompanyId() > 0)	{
			queryParams += " AND wd.Company_Id = "+paramDashboard.getCompanyId();
		}
		
		/*if(paramDashboard != null && paramDashboard.getVendorId() != null && paramDashboard.getVendorId() > 0)	{
			query1 += " AND  w.Vendor_Id = "+paramDashboard.getVendorId();
		}*/
		
		String presentQuery = " SELECT  ltr.Shift, COUNT(ltr.Emp) AS Count FROM "+paramDashboard.getSchemaName()+".labor_time_report  ltr "
						+ "	JOIN worker_Details wd ON   wd.Worker_Code = ltr.Emp "
						+ " WHERE  1 = 1 ";
					//	+ " AND ltr.Attendance NOT IN ('Absent', 'PH') ";
		if(paramDashboard.getFromDate() != null && DateHelper.compareDateWithCurrentDate(paramDashboard.getFromDate()) == 0){
			presentQuery  += " AND( ltr.Intime IS NOT NULL )";
		}else{
			presentQuery  += " AND ltr.Attendance NOT IN ('Absent', 'PH', 'WO') ";
		}
		presentQuery  += " AND ltr.Business_Date BETWEEN '"+DateHelper.convertDateToSQLDate(paramDashboard.getFromDate())+"' AND '"+DateHelper.convertDateToSQLDate(paramDashboard.getToDate())+"' ";
					/*+ " AND ltr.ContractorCode IN ( SELECT v.Vendor_Code FROM Vendor_view v  WHERE 1 = 1 "
						+ query1
						+" ) "*/
		presentQuery  += queryParams+" GROUP BY ltr.Shift ORDER BY ltr.Shift";
		//System.out.println("SHIFT   "+DateHelper.compareDateWithCurrentDate(paramDashboard.getFromDate()));
		//System.out.println(paramDashboard.getFromDate().compareTo(new Date()) == 0);
		
		/*String scheduledQuery = " SELECT  ltr.Shift, COUNT(ltr.Emp) AS Count FROM labor_time_report  ltr "
						+ " WHERE  1 = 1 "
						+ " AND LTR.Shift NOT IN ('AB', 'PH') "
						//+ " AND LTR.Attendance NOT IN ('PH')"
						+ " AND ltr.Business_Date BETWEEN '"+DateHelper.convertDateToSQLDate(paramDashboard.getFromDate())+"' AND '"+DateHelper.convertDateToSQLDate(paramDashboard.getToDate())+"' "
						+ " AND ltr.ContractorCode IN ( SELECT vv.Vendor_Code FROM Vendor_view vv  WHERE 1 = 1 "+query1+" )  GROUP BY ltr.Shift ORDER BY ltr.Shift";
		*/
		/* Schedule by Shift wise from labor_scheduled_shifts */    
		String scheduledQuery = " SELECT lss.Shift, COUNT(lss.Emp_Code)    FROM labor_scheduled_shifts AS lss "
									+ "	JOIN worker_Details wd ON   wd.Worker_Code = lss.Emp_Code "
		    						+ " WHERE 1 = 1 "
									+ " AND lss.Business_Date BETWEEN '"+DateHelper.convertDateToSQLDate(paramDashboard.getFromDate())+"' AND '"+DateHelper.convertDateToSQLDate(paramDashboard.getToDate())+"' "
									+queryParams
									// commented below line due to current customer WELSPUN is having only one customer and one company
		        					/*+ " AND lss.Code IN ( SELECT v.Vendor_Code FROM Vendor_view v WHERE 1 = 1 "
									+query1+" )  "*/
									+ " GROUP BY  lss.Shift ORDER BY lss.Shift "
									;			 
		
		List<Object> shiftWiseAttendanceData = new ArrayList();
		try{
			List shiftWisePresentList = session.createSQLQuery(presentQuery).list();
			List shiftWiseScheduledList = session.createSQLQuery(scheduledQuery).list();
			
			if(shiftWiseScheduledList.size() > 0){
				shiftWiseAttendanceData.add(shiftWiseScheduledList);
			}else if(shiftWisePresentList.size() > 0 && shiftWiseScheduledList.size() <= 0 ){
				Object[] o = new Object[2];
				o[0]= ((Object[])shiftWisePresentList.get(0))[0];
				o[1] = 0;
				shiftWiseScheduledList.add(o);
				shiftWiseAttendanceData.add(shiftWiseScheduledList);
			}
			
			if(shiftWisePresentList.size() > 0){
				shiftWiseAttendanceData.add(shiftWisePresentList);
			}else if(shiftWisePresentList.size() <= 0 && shiftWiseScheduledList.size() > 0 ){
				Object[] o = new Object[2];
				o[0]= ((Object[])shiftWiseScheduledList.get(0))[0];
				o[1] = 0;
				shiftWisePresentList.add(o);
				shiftWiseAttendanceData.add(shiftWisePresentList);
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("Exception occurred in getShiftWiseAttendanceDetails()...  ",e);
		}
		log.info("Exiting from getShiftWiseAttendanceDetails() ::  "+shiftWiseAttendanceData);
		return (V) shiftWiseAttendanceData;
			
	}
	
	/*
	 * This method will be used to get Workers Skill Wise count  present and scheduled
	 */
	@Override
	public <V> V  getSkillWiseAttendanceDetails(DashboardVo paramDashboard){
		log.info("Entered in getSkillWiseAttendanceDetails ::  "+paramDashboard);
		Session session = sessionFactory.getCurrentSession();
		String queryParams = "";
		
		if(paramDashboard.getFromDate() != null && paramDashboard.getToDate() == null){
			paramDashboard.setToDate(paramDashboard.getFromDate());
		}
		
		if(paramDashboard != null && paramDashboard.getCustomerId() != null && paramDashboard.getCustomerId() > 0)	{
			queryParams += " AND  wsv.Customer_Id = "+paramDashboard.getCustomerId();
		}
		
		if(paramDashboard != null && paramDashboard.getCompanyId() != null && paramDashboard.getCompanyId() > 0)	{
			queryParams += " AND wsv.Company_Id = "+paramDashboard.getCompanyId();
		}
		
		/*if(paramDashboard != null && paramDashboard.getVendorId() != null && paramDashboard.getVendorId() > 0)	{
			query1 += " AND  w.Vendor_Id = "+paramDashboard.getVendorId();
		}*/
		
		String presentQuery = " SELECT  wsv.work_skill, count(ltr.emp) AS Worker_Count   FROM "+paramDashboard.getSchemaName()+".labor_time_report ltr "
							+ "  JOIN worker_skill_view wsv ON ltr.emp = wsv.worker_code "
							+ " WHERE 1 = 1 ";
						
		if(paramDashboard.getFromDate() != null &&  DateHelper.compareDateWithCurrentDate(paramDashboard.getFromDate()) == 0){
			presentQuery  += " AND ltr.Intime IS NOT NULL ";
		}else{
			presentQuery  += " AND ltr.attendance IN ('Present') " ;
		}
							
		presentQuery  += " AND ltr.Business_Date  BETWEEN '"+DateHelper.convertDateToSQLDate(paramDashboard.getFromDate())+"' AND '"+DateHelper.convertDateToSQLDate(paramDashboard.getToDate())+"' ";
							
		//presentQuery  += query1;
		presentQuery += queryParams+" GROUP BY wsv.Work_Skill ORDER BY wsv.Work_Skill ";
		
		/*String scheduledQuery = " SELECT   wsv.work_skill, count(ltr.emp) AS Worker_Count  FROM labor_time_report ltr "
							+ " LEFT JOIN worker_skill_view wsv ON ltr.emp = wsv.worker_code "
							+ " WHERE 1 = 1 "
							+ " AND ltr.Business_Date  BETWEEN '"+DateHelper.convertDateToSQLDate(paramDashboard.getFromDate())+"' AND '"+DateHelper.convertDateToSQLDate(paramDashboard.getToDate())+"' "
							+ " AND ltr.attendance IN ('Present','Absent')  " +query1;*/
		
		String scheduledQuery = " SELECT wsv.work_skill, count(lss.Emp_Code) FROM labor_scheduled_shifts AS lss "
								+ " JOIN worker_skill_view wsv ON lss.Emp_Code = wsv.worker_code "
								+ " WHERE 1 = 1 "
								+ " AND lss.Business_Date BETWEEN '"+DateHelper.convertDateToSQLDate(paramDashboard.getFromDate())+"' AND '"+DateHelper.convertDateToSQLDate(paramDashboard.getToDate())+"' "
								
								// commented below line due to current customer WELSPUN is having only one customer and one company
								+queryParams
								+ " GROUP BY wsv.Work_Skill ORDER BY wsv.Work_Skill ";
									
		List<Object> skillWiseAttendanceData = new ArrayList();
		
		try{
			List skillWisePresentList = session.createSQLQuery(presentQuery).list();
			List skillWiseScheduledList = session.createSQLQuery(scheduledQuery).list();
			
			if(skillWiseScheduledList.size() > 0){
				skillWiseAttendanceData.add(skillWiseScheduledList);
			}else if(skillWisePresentList.size() > 0 && skillWiseScheduledList.size() <= 0 ){
				System.out.println("Empty Scheduled: "+((Object[])skillWisePresentList.get(0))[0]);
				Object[] o = new Object[2];
				o[0]= ((Object[])skillWisePresentList.get(0))[0];
				o[1] = 0;
				skillWiseScheduledList.add(o);
				skillWiseAttendanceData.add(skillWiseScheduledList);
			}
			
			if(skillWisePresentList.size() > 0){
				skillWiseAttendanceData.add(skillWisePresentList);
			}else if(skillWisePresentList.size() <= 0 && skillWiseScheduledList.size() > 0 ){
				//Object obj = ["0:"+skillWiseScheduledList.get(0)[1]];
				System.out.println("Empty Present: "+((Object[])skillWiseScheduledList.get(0))[0]);
				Object[] o = new Object[2];
				o[0]= ((Object[])skillWiseScheduledList.get(0))[0];
				o[1] = 0;
				skillWisePresentList.add(o);
				skillWiseAttendanceData.add(skillWisePresentList);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("Exception occurred in getSkillWiseAttendanceDetails()...  ",e);
		}
		log.info("Exiting from getSkillWiseAttendanceDetails() ::  "+skillWiseAttendanceData);
		return (V) skillWiseAttendanceData;
	}
	
	/*
	 * This method will be used to get Workers Shift Wise count  present and scheduled
	 */
	@Override
	public <V> V  getVendorWiseAttendanceDetails(DashboardVo paramDashboard){
		log.info("Entered in getVendorWiseAttendanceDetails()  ::  "+paramDashboard);
		Session session = sessionFactory.getCurrentSession();
		String queryParams = "";
		
		if(paramDashboard.getFromDate() != null && paramDashboard.getToDate() == null){
			paramDashboard.setToDate(paramDashboard.getFromDate());
		}
		if(paramDashboard != null && paramDashboard.getCustomerId() != null && paramDashboard.getCustomerId() > 0)	{
			queryParams += " AND v.Customer_Id = "+paramDashboard.getCustomerId();
		}
		if(paramDashboard != null && paramDashboard.getCompanyId() != null && paramDashboard.getCompanyId() > 0)	{
			queryParams += " AND  v.Company_Id = "+paramDashboard.getCompanyId();
		}
		/*if(paramDashboard != null && paramDashboard.getVendorId() != null && paramDashboard.getVendorId() > 0)	{
			queryParams += " AND w.Vendor_Id = "+paramDashboard.getVendorId();
		}*/
		
		List<Object> vendorWiseAttendanceData = new ArrayList<Object>();
		try{
			
			String queryPresent = " SELECT COUNT(ltr.Emp), ContractorName FROM "+paramDashboard.getSchemaName()+".labor_time_report  ltr "
									+" JOIN vendor_details v ON ltr.ContractorCode = v.Vendor_Code "
									+ " WHERE 1 = 1 ";
									
			if(paramDashboard.getFromDate() != null &&  DateHelper.compareDateWithCurrentDate(paramDashboard.getFromDate()) == 0){
				queryPresent  += " AND ltr.Intime IS NOT NULL ";
			}else{
				queryPresent  += " AND ltr.Attendance  IN ('Present') ";
			}
			queryPresent  += " AND ltr.Business_Date BETWEEN '"+DateHelper.convertDateToSQLDate(paramDashboard.getFromDate())+"' AND '"+DateHelper.convertDateToSQLDate(paramDashboard.getToDate())+"' ";
									//+ " AND ltr.ContractorCode IN (SELECT v.Vendor_Code FROM VENDOR_VIEW v WHERE 1 = 1 "+queryParams+" ) "
			queryPresent += queryParams+" GROUP BY ltr.ContractorName ORDER BY  ltr.ContractorName DESC ";
			
			String queryScheduled = " SELECT COUNT(lss.Emp_Code), ContractorName FROM labor_scheduled_shifts  lss "
									+" JOIN vendor_details v ON lss.Code = v.Vendor_Code "
									+ " WHERE 1= 1 "
								//	+ " AND lss.Shift NOT IN ('WO') "
									+ " AND lss.Business_Date BETWEEN '"+DateHelper.convertDateToSQLDate(paramDashboard.getFromDate())+"' AND '"+DateHelper.convertDateToSQLDate(paramDashboard.getToDate())+"' "
									//+ " AND lss.Code IN (SELECT v.Vendor_Code FROM VENDOR_VIEW v WHERE 1 = 1 "+queryParams+" ) "
									+queryParams+ " GROUP BY lss.ContractorName ORDER BY  lss.ContractorName DESC ";
	

			List vendorWisePresentList = session.createSQLQuery(queryPresent).list();
			List vendorWiseScheduledList = session.createSQLQuery(queryScheduled).list();
			
			if(vendorWiseScheduledList.size() > 0){
				vendorWiseAttendanceData.add(vendorWiseScheduledList);
			}else if(vendorWiseScheduledList.size() <= 0 && vendorWisePresentList.size() > 0 ){
				Object[] o = new Object[2];
				o[0] = 0;
				o[1]= ((Object[])vendorWisePresentList.get(0))[1];
				System.out.println();
				vendorWiseScheduledList.add(o);
				vendorWiseAttendanceData.add(vendorWiseScheduledList);
			}
			
			if(vendorWisePresentList.size() > 0){
				vendorWiseAttendanceData.add(vendorWisePresentList);
			}else if(vendorWiseScheduledList.size() <= 0 && vendorWiseScheduledList.size() > 0 ){
				Object[] o = new Object[2];
				o[0] = 0;
				o[1]= ((Object[])vendorWiseScheduledList.get(0))[1];
				System.out.println();
				vendorWisePresentList.add(o);
				vendorWiseAttendanceData.add(vendorWisePresentList);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("Exception occurred in getVendorWiseAttendanceDetails()...  ",e);
		}
		log.info("Exiting from getVendorWiseAttendanceDetails() ::  "+vendorWiseAttendanceData);
		return (V) vendorWiseAttendanceData;
	}
	
	/*
	 * This method will be used to get  expired/ expiring vendor compliance documents based on given parameters
	 */
	@Override
	public List<DashboardComplianceStatusVo>  getVendorComplianceStatus(DashboardVo paramDashboard){
		log.info("Entered in  getVendorComplianceStatus()  ::  "+paramDashboard);
		Session session = sessionFactory.getCurrentSession();
		
		List<DashboardComplianceStatusVo> returnList = new ArrayList();
		
		if(paramDashboard.getFromDate() != null && paramDashboard.getToDate() == null){
			paramDashboard.setToDate(paramDashboard.getFromDate());
		}
		
		String query = "SELECT  vv.Vendor_Name, compliance.Doccument_Name, venComp.License_Policy_Number, "
						+ "	(CASE WHEN venComp.Expiry_Date < current_date() THEN 'Expired on' ELSE 'Expires on' END) AS Status, "
						+ " DATE_FORMAT(venComp.Expiry_Date, '%d/%m/%Y'), vv.Vendor_Code, venComp.vendor_Compliance_Id "
						+ " FROM vendor_compliance venComp "
						+ " JOIN define_compliance_types compliance ON compliance.Compliance_Unique_Id = venComp.Compliance_Unique_Id "
						//+ " JOIN  customer_view cv ON venComp.Customer_Id = cv.Customer_Id  "
						//+ " JOIN  company_view cmpv ON venComp.Company_Id = cmpv.Company_Id  "
						+ " JOIN  vendor_view vv ON venComp.Vendor_Id = vv.Vendor_Id  "					
						+ " WHERE CONCAT(DATE_FORMAT(`venComp`.`Transaction_Date`, '%Y%m%d'), LPAD(`venComp`.`Vendor_Compliance_Sequence_Id`, 2, '0')) = (SELECT  "
									+" MAX(CONCAT(DATE_FORMAT(`venComp1`.`Transaction_Date`, '%Y%m%d'),   LPAD(`venComp1`.`Vendor_Compliance_Sequence_Id`, 2, '0'))) "
									+"	FROM  vendor_compliance venComp1 "
									+" WHERE venComp.`Customer_Id` = `venComp1`.`Customer_Id` "
									+" AND `venComp`.`Company_Id` = `venComp1`.`Company_Id` "
									+" AND `venComp`.`Vendor_Id` = `venComp1`.`Vendor_Id` "
									+" AND `venComp`.`Compliance_Unique_Id` = `venComp1`.`Compliance_Unique_Id` "
									//+" AND  venComp.vendor_Compliance_unique_id = venComp1.vendor_Compliance_unique_id "
									+" AND `venComp1`.`Transaction_Date` <= CURDATE() ) "
						  + " AND  venComp.Status = 'Verified' "
						//  + " AND cmpv.Is_Active = 'Y' "
						//  + " AND cv.Is_Active = 'Y' "
						  + " AND vv.Vendor_Status = 'Validated' "
						  + "  AND  venComp.Expiry_Date between  DATE_ADD(Current_DATE(),INTERVAL -15 DAY) AND DATE_ADD(current_date(),INTERVAL 15 DAY) "
;
		
		if(paramDashboard != null && paramDashboard.getCustomerId() != null && paramDashboard.getCustomerId() > 0)	{
			query += " AND venComp.Customer_Id = "+paramDashboard.getCustomerId();
		}
		
		if(paramDashboard != null && paramDashboard.getCompanyId() != null && paramDashboard.getCompanyId() > 0)	{
			query += " AND  venComp.Company_Id = "+paramDashboard.getCompanyId();
		}
		
		if(paramDashboard != null && paramDashboard.getVendorId() != null && paramDashboard.getVendorId() > 0)	{
			query += " AND venComp.Vendor_Id = "+paramDashboard.getVendorId();
		}
		
		/*if(paramDashboard != null && paramDashboard.getFromDate() != null && paramDashboard.getToDate() != null )	{
			query += " AND venComp.Expiry_Date  BETWEEN '"+DateHelper.convertDateToSQLDate(paramDashboard.getFromDate())+"' AND '"+DateHelper.convertDateToSQLDate(paramDashboard.getToDate())+"' ";
		}*/
		
		query  += "  GROUP BY venComp.Vendor_Compliance_Unique_Id ORDER BY venComp.Expiry_Date asc";

		
		try{
			List complianceList = session.createSQLQuery(query).list();
			
			for(Object object : complianceList){
				Object[] obj = (Object[])object ;
				
				DashboardComplianceStatusVo compliance = new DashboardComplianceStatusVo();
				
				compliance.setVendorName((String)obj[0]);
				compliance.setComplianceName((String)obj[1]);
				compliance.setLicenseNumber((String)obj[2]);
				compliance.setStatus((String)obj[3]);
				compliance.setExpiryDate((String)obj[4]);
				compliance.setVendorCode((String)obj[5]);
				compliance.setVendorComplianceId((Integer)obj[6]);
				returnList.add(compliance);
				
			}
	
		}catch(Exception e){
			e.printStackTrace();
			log.error("Exception occurred in getVendorComplianceStatus()...  ",e);
		}
		log.info("Exiting from  getVendorComplianceStatus()  ::  "+returnList);
		return returnList;
	}




	/*
	 * This method will be used to get department wise present and scheduled data
	 */
	@Override
	public <V> V getDepartmentWiseAttendanceDetails(DashboardVo paramDashboard) {
		log.info("Entered in getDepartmentWiseAttendanceDetails ::  "+paramDashboard);
		Session session = sessionFactory.getCurrentSession();
		String queryParams = "";
		
		if(paramDashboard.getFromDate() != null && paramDashboard.getToDate() == null){
			paramDashboard.setToDate(paramDashboard.getFromDate());
		}
		
		if(paramDashboard != null && paramDashboard.getCustomerId() != null && paramDashboard.getCustomerId() > 0)	{
			queryParams += " AND  wsv.Customer_Id = "+paramDashboard.getCustomerId();
		}
		
		if(paramDashboard != null && paramDashboard.getCompanyId() != null && paramDashboard.getCompanyId() > 0)	{
			queryParams += " AND wsv.Company_Id = "+paramDashboard.getCompanyId();
		}
		
		if(paramDashboard != null && paramDashboard.getVendorId() != null && paramDashboard.getVendorId() > 0)	{
			queryParams += " AND  wsv.Vendor_Id = "+paramDashboard.getVendorId();
		}
		
		String presentQuery = " SELECT    wsv.Department_Name,count(ltr.Emp)   FROM "+paramDashboard.getSchemaName()+".labor_time_report ltr "
							+ " JOIN worker_skill_view wsv ON ltr.emp = wsv.worker_code "
							+ " WHERE 1 = 1 ";
						
		if(paramDashboard.getFromDate() != null &&  DateHelper.compareDateWithCurrentDate(paramDashboard.getFromDate()) == 0){
			presentQuery  += " AND ltr.Intime IS NOT NULL ";
		}else{
			presentQuery  += " AND ltr.attendance IN ('Present') " ;
		}
							
		presentQuery  += " AND ltr.Business_Date  BETWEEN '"+DateHelper.convertDateToSQLDate(paramDashboard.getFromDate())+"' AND '"+DateHelper.convertDateToSQLDate(paramDashboard.getToDate())+"' "
							+queryParams
							+" GROUP BY wsv.Department_Name ORDER BY wsv.Department_Name ";
		
		String scheduledQuery = " SELECT   wsv.Department_Name, count(lss.Emp_Code) FROM labor_scheduled_shifts AS lss "
								+ " JOIN worker_skill_view wsv ON lss.Emp_Code = wsv.worker_code "
								+ " WHERE 1 = 1 "
								+ " AND lss.Business_Date BETWEEN '"+DateHelper.convertDateToSQLDate(paramDashboard.getFromDate())+"' AND '"+DateHelper.convertDateToSQLDate(paramDashboard.getToDate())+"' "
								//+ " AND ltr.attendance NOT IN ('WO')  " 
								// commented below line due to current customer WELSPUN is having only one customer and one company
								+queryParams
								+ " GROUP BY wsv.Department_Name ORDER BY wsv.Department_Name ";
									
		List<Object> departmentWiseAttendanceData = new ArrayList();
		
		try{
			List departmentWisePresentList = session.createSQLQuery(presentQuery).list();
			List departmentWiseScheduledList = session.createSQLQuery(scheduledQuery).list();
			

			
			if(departmentWiseScheduledList.size() > 0){
				departmentWiseAttendanceData.add(departmentWiseScheduledList);
			}else if(departmentWisePresentList.size() > 0 && departmentWiseScheduledList.size() <= 0 ){
				System.out.println("Empty Scheduled: "+((Object[])departmentWisePresentList.get(0))[0]);
				Object[] o = new Object[2];
				o[0]= ((Object[])departmentWisePresentList.get(0))[0];
				o[1] = 0;
				departmentWiseScheduledList.add(o);
				departmentWiseAttendanceData.add(departmentWiseScheduledList);
			}
			
			if(departmentWisePresentList.size() > 0){
				departmentWiseAttendanceData.add(departmentWisePresentList);
			}else if(departmentWisePresentList.size() <= 0 && departmentWiseScheduledList.size() > 0 ){
				//Object obj = ["0:"+skillWiseScheduledList.get(0)[1]];
				System.out.println("Empty Present: "+((Object[])departmentWiseScheduledList.get(0))[0]);
				Object[] o = new Object[2];
				o[0]= ((Object[])departmentWiseScheduledList.get(0))[0];
				o[1] = 0;
				departmentWisePresentList.add(o);
				departmentWiseAttendanceData.add(departmentWisePresentList);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("Exception occurred in getDepartmentWiseAttendanceDetails()...  ",e);
		}
		log.info("Exiting from getDepartmentWiseAttendanceDetails() ::  "+departmentWiseAttendanceData);
		return (V) departmentWiseAttendanceData;
	}
	
	
}