package com.Ntranga.CLMS.Dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.AttendanceReportVo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import common.Logger;

@Transactional
@Repository("reportDao")
public class ReportDaoImpl implements ReportDao  {

	private static Logger log = Logger.getLogger(ReportDaoImpl.class);
	
	
	@Autowired
	private SessionFactory sessionFactory;

	private @Value("#{db['db.schemaName']}")
	String dbSchemaName;

	public List<AttendanceReportVo> getAttandanceReport(String attandanceReportJsonString ) {		
		Session session = sessionFactory.getCurrentSession();		
		List<AttendanceReportVo> attendanceReportVos = new ArrayList<AttendanceReportVo>();
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject)jsonParser.parse(attandanceReportJsonString);  
		try{
			String schemaName = null;
			
			if(jo.get("schemaName") != null && !jo.get("schemaName").isJsonNull() && !(jo.get("schemaName").getAsString().equalsIgnoreCase("null")) && !(jo.get("schemaName").toString().equalsIgnoreCase("null"))  && !(jo.get("schemaName").toString().isEmpty())  ){
				schemaName =jo.get("schemaName").getAsString();
			}else if(schemaName == null || schemaName.isEmpty()){
				schemaName = dbSchemaName;
			}
			log.info(schemaName);
			log.info(attandanceReportJsonString);
			String query = "SELECT DISTINCT vendorDtls.vendor_name AS Contractor,REPLACE((CONCAT(RTRIM(vw.first_name), ' ',CASE WHEN (vw.middle_name IS NULL) THEN '' ELSE CONCAT(RTRIM(vw.middle_name),' ') END, vw.last_name)),'  ',' ') AS WorkMen,a.Emp AS IDNo,Shift, "

						+" DATE_FORMAT(Business_Date,'%d/%m/%Y') AS `Date`,(CASE WHEN (Shift ='WO' AND InTime IS  NULL ) THEN 'WO' WHEN  Attendance='Absent' THEN NULL ELSE  DATE_FORMAT(InTime,'%r')END) AS  InTime, "
						+" (CASE WHEN (Shift ='WO' AND InTime IS  NULL ) THEN 'WO'  WHEN  Attendance='Absent' THEN NULL ELSE  DATE_FORMAT(OutTime, '%r') END) AS OutTime, "
						+" (CASE WHEN  Attendance IN ('Absent', 'WO') THEN NULL ELSE CAST(ManHours  AS CHAR)  END)AS ManHrs, IFNULL(OTHours,0) AS OT,"
						+"  Attendance AS  `Status` FROM  "+schemaName+".Labor_Time_report a INNER JOIN vendor_view vendorDtls ON (vendorDtls.vendor_code=a.contractorCode) INNER JOIN v_worker_details vw ON vw.Worker_code = a.Emp  " 
						+" WHERE 1 = 1 ";
			
			if(!(jo.get("customerId").toString().equalsIgnoreCase("null"))  && jo.get("customerId").getAsInt() > 0 ){
				query+="   AND  vendorDtls.customer_id='"+jo.get("customerId").getAsString()+"'";
			}
			if(!(jo.get("companyId").toString().equalsIgnoreCase("null"))  && jo.get("companyId").getAsInt() > 0){
				query+="  AND  vendorDtls.company_id= '"+jo.get("companyId").getAsString()+"'";
			}
			if(!(jo.get("vendorId").toString().equalsIgnoreCase("null"))  && jo.get("vendorId").getAsInt() > 0){
				query+=" AND  vendorDtls.vendor_id='"+jo.get("vendorId").getAsString()+"'";
			}
			if(!(jo.get("employeeCode").getAsString().equalsIgnoreCase("null"))){
				query+="  AND  a.Emp Like '%"+jo.get("employeeCode").getAsString()+"%'";
			}
			if(!(jo.get("employeeName").toString().equalsIgnoreCase("null")) && !(jo.get("employeeName").getAsString().equalsIgnoreCase("null"))){
				query+="   AND  REPLACE((CONCAT(RTRIM(vw.first_name), ' ',CASE WHEN (vw.middle_name IS NULL) THEN '' ELSE CONCAT(RTRIM(vw.middle_name),' ') END, vw.last_name)),'  ',' ')  Like '%"+jo.get("employeeName").getAsString()+"%'";
			}
			if(!(jo.get("year").getAsString().equalsIgnoreCase("null")) && jo.get("year").getAsInt() > 0){
				query+="   AND  YEAR ( Business_Date ) =  '"+jo.get("year").getAsString()+"'";
			}
			if(!(jo.get("year").getAsString().equalsIgnoreCase("null")) && !(jo.get("month").getAsString().equalsIgnoreCase("null")) && jo.get("year").getAsInt() > 0  && jo.get("month").getAsInt() > 0  ){
				query+="   AND  MONTH ( Business_Date ) =  '"+jo.get("month").getAsString()+"'";
			}
			if(!(jo.get("todate").toString().equalsIgnoreCase("null")) && !(jo.get("todate").getAsString().equalsIgnoreCase("null")) && !(jo.get("todate").toString().trim().isEmpty()) && !(jo.get("fromdate").toString().equalsIgnoreCase("null")) && !(jo.get("fromdate").getAsString().equalsIgnoreCase("null")) && !(jo.get("fromdate").toString().trim().isEmpty()) ){
				query+="   AND  Business_Date BETWEEN STR_TO_DATE('"+jo.get("fromdate").getAsString()+"','%d/%m/%Y') AND STR_TO_DATE('"+jo.get("todate").getAsString()+"','%d/%m/%Y') ";
			}
			if(!jo.get("locationId").toString().equalsIgnoreCase("null") && !jo.get("locationId").getAsString().equalsIgnoreCase("null")  && jo.get("locationId").getAsInt() > 0){
				query+=" AND  a.Location_Id='"+jo.get("locationId").getAsString()+"'";
			}
			if(!jo.get("plantId").toString().equalsIgnoreCase("null") && !jo.get("plantId").getAsString().equalsIgnoreCase("null")  && jo.get("plantId").getAsInt() > 0){
				query+=" AND  a.Plant_Id='"+jo.get("plantId").getAsString()+"'";
			}
			if(!jo.get("departmentId").toString().equalsIgnoreCase("null") && !jo.get("departmentId").getAsString().equalsIgnoreCase("null")  && jo.get("departmentId").getAsInt() > 0){
				query+=" AND  a.Department_Id='"+jo.get("departmentId").getAsString()+"'";
			}
			if(!jo.get("sectionId").toString().equalsIgnoreCase("null") && !jo.get("sectionId").getAsString().equalsIgnoreCase("null")  && jo.get("sectionId").getAsInt() > 0){
				query+=" AND  a.Section_Id='"+jo.get("sectionId").getAsString()+"'";
			}
			if(!jo.get("workAreaId").toString().equalsIgnoreCase("null") && !jo.get("workAreaId").getAsString().equalsIgnoreCase("null")  && jo.get("workAreaId").getAsInt() > 0){
				query+=" AND  a.Work_Area_Id='"+jo.get("workAreaId").getAsString()+"'";
			}
			if(!(jo.get("workSkill").toString().equalsIgnoreCase("null")) && !(jo.get("workSkill").getAsString().equalsIgnoreCase("null"))){
				query+=" AND  a.Work_Skill='"+jo.get("workSkill").getAsString()+"'";
			}
			if(!(jo.get("shift").toString().equalsIgnoreCase("null")) && !(jo.get("shift").getAsString().equalsIgnoreCase("null"))){
				query+=" AND  a.Shift='"+jo.get("shift").getAsString()+"'";
			}
			
			/*if(!(jo.get("fromdate").toString().equalsIgnoreCase("null")) && !(jo.get("fromdate").getAsString().equalsIgnoreCase("null")) && !(jo.get("fromdate").toString().trim().isEmpty())){
				query+="   AND  DATE_FORMAT(Business_Date,'%d/%m/%Y') >= '"+jo.get("fromdate").getAsString()+"'";
			}*/
			
			
			/*if(!jo.get("status").toString().equalsIgnoreCase("null")){
				query+="   AND  '"+jo.get("status").getAsString()+"'";
			}*/
			
			
			query+="  ORDER BY  1,5 ,4,2 ";
			
			log.info(query+" :: query");
			List attendanceTempList = session.createSQLQuery(query).list();
			AttendanceReportVo reportVo = null;
			for (Object reportObject : attendanceTempList) {
				Object[] reportArray = (Object[]) reportObject;
				reportVo = new AttendanceReportVo();
				reportVo.setContractorName((reportArray[0]+"").equalsIgnoreCase("null") ? "" : reportArray[0]+"");
				reportVo.setWorkMenName((reportArray[1]+"").equalsIgnoreCase("null") ? "" : reportArray[1]+"");
				reportVo.setIdNo((reportArray[2]+"").equalsIgnoreCase("null") ? "" : reportArray[2]+"");
				reportVo.setShift((reportArray[3]+"").equalsIgnoreCase("null") ? "" : reportArray[3]+"");
				reportVo.setDate((reportArray[4]+"").equalsIgnoreCase("null") ? "" : reportArray[4]+"");
				reportVo.setInTime((reportArray[5]+"").equalsIgnoreCase("null") ? "" : reportArray[5]+"");
				reportVo.setOutTime((reportArray[6]+"").equalsIgnoreCase("null") ? "" : reportArray[6]+"");
				reportVo.setManHours((reportArray[7]+"").equalsIgnoreCase("null") ? "" : reportArray[7]+"");
				reportVo.setOverTime((reportArray[8]+"").equalsIgnoreCase("null") ? "" : reportArray[8]+"");
				reportVo.setStatus((reportArray[9]+"").equalsIgnoreCase("null") ? "" : reportArray[9]+"");
				attendanceReportVos.add(reportVo);
			}
			
			
			
		}catch(Exception e){			
			log.error("getAttandanceReport ",e);
		}
		return attendanceReportVos;
	}


	@Override
	public List<AttendanceReportVo> getActualAttandanceReport(String actualAttandanceReportJsonString) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();		
		List<AttendanceReportVo> attendanceReportVos = new ArrayList<AttendanceReportVo>();
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject)jsonParser.parse(actualAttandanceReportJsonString);  
		try{
			String query = "SELECT DISTINCT a.ContractorName AS Contractor, REPLACE((CONCAT(RTRIM(vw.first_name), ' ',CASE WHEN (vw.middle_name IS NULL) THEN '' ELSE CONCAT(RTRIM(vw.middle_name),' ') END, vw.last_name)),'  ',' ')  AS WorkMen, a.Emp AS IDNo, "
						+" Shift, Business_Date AS `Date`, "
						+" (CASE WHEN Shift ='WO' THEN 'WO'  WHEN  Attendance='Absent' THEN NULL ELSE  DATE_FORMAT(InTime,'%r') END) AS  InTime, "
						+" (CASE WHEN Shift ='WO' THEN 'WO'  WHEN  Attendance='Absent' THEN NULL ELSE  DATE_FORMAT(OutTime,'%r') END) AS OutTime, "
						+" (CASE WHEN  Attendance IN ('Absent','WO') THEN NULL ELSE MANHOURS END)AS ManHrs, "
						+" IFNULL(OTHours,0) AS OT, "
						+" (CASE WHEN Shift = 'WO' THEN 'WO' ELSE  Attendance END) AS  `Status` FROM Labor_Time_report a INNER JOIN `labor_time_june` b ON( a.emp = b.emp) LEFT JOIN vendor_details vendorDtls ON (vendorDtls.vendor_code=a.contractorCode)  LEFT JOIN worker_details workerDetails ON (workerDetails.`Worker_code`  = a.emp)  " 
						+" WHERE emp_name IS NOT NULL ";
			
			if(!(jo.get("customerId").toString().equalsIgnoreCase("null"))  && jo.get("customerId").getAsInt() > 0 ){
				query+="   AND  workerDetails.customer_id='"+jo.get("customerId").getAsString()+"'";
			}
			if(!(jo.get("companyId").toString().equalsIgnoreCase("null"))  && jo.get("companyId").getAsInt() > 0){
				query+="  AND  workerDetails.company_id= '"+jo.get("companyId").getAsString()+"'";
			}
			if(!(jo.get("vendorId").toString().equalsIgnoreCase("null"))  && jo.get("vendorId").getAsInt() > 0){
				query+=" AND  vendorDtls.vendor_id='"+jo.get("vendorId").getAsString()+"'";
			}
			if(!(jo.get("employeeCode").getAsString().equalsIgnoreCase("null"))){
				query+="  AND  a.Emp Like '%"+jo.get("employeeCode").getAsString()+"%'";
			}
			if(!(jo.get("employeeName").toString().equalsIgnoreCase("null")) && !(jo.get("employeeName").getAsString().equalsIgnoreCase("null"))){
				query+="   AND  REPLACE((CONCAT(RTRIM(vw.first_name), ' ',CASE WHEN (vw.middle_name IS NULL) THEN '' ELSE CONCAT(RTRIM(vw.middle_name),' ') END, vw.last_name)),'  ',' ')  Like '%"+jo.get("employeeName").getAsString()+"%'";
			}
			if(!(jo.get("year").getAsString().equalsIgnoreCase("null")) && jo.get("year").getAsInt() > 0){
				query+="   AND  YEAR ( Business_Date ) =  '"+jo.get("year").getAsString()+"'";
			}
			if(!(jo.get("year").getAsString().equalsIgnoreCase("null")) && !(jo.get("month").getAsString().equalsIgnoreCase("null")) && jo.get("year").getAsInt() > 0  && jo.get("month").getAsInt() > 0  ){
				query+="   AND  MONTH ( Business_Date ) =  '"+jo.get("month").getAsString()+"'";
			}
			if(!(jo.get("todate").toString().equalsIgnoreCase("null")) && !(jo.get("todate").getAsString().equalsIgnoreCase("null")) && !(jo.get("todate").toString().trim().isEmpty())){
				query+="   AND  DATE_FORMAT(Business_Date,'%d/%m/%Y') = '"+jo.get("todate").getAsString()+"'";
			}
			/*if(!jo.get("status").toString().equalsIgnoreCase("null")){
				query+="   AND  '"+jo.get("status").getAsString()+"'";
			}*/
			
			
			query+="  ORDER BY 3,Business_Date ";
			
			log.debug(query+" :: query");
			List<Object> attendanceTempList = session.createSQLQuery(query).list();
			AttendanceReportVo reportVo = null;		
			for (Object reportObject : attendanceTempList) {
				Object[] reportArray = (Object[]) reportObject;
				reportVo = new AttendanceReportVo();
				reportVo.setContractorName((reportArray[0]+"").equalsIgnoreCase("null") ? "" : reportArray[0]+"");
				reportVo.setWorkMenName((reportArray[1]+"").equalsIgnoreCase("null") ? "" : reportArray[1]+"");
				reportVo.setIdNo((reportArray[2]+"").equalsIgnoreCase("null") ? "" : reportArray[2]+"");
				reportVo.setShift((reportArray[3]+"").equalsIgnoreCase("null") ? "" : reportArray[3]+"");
				reportVo.setDate((reportArray[4]+"").equalsIgnoreCase("null") ? "" : reportArray[4]+"");
				reportVo.setInTime((reportArray[5]+"").equalsIgnoreCase("null") ? "" : reportArray[5]+"");
				reportVo.setOutTime((reportArray[6]+"").equalsIgnoreCase("null") ? "" : reportArray[6]+"");
				reportVo.setManHours((reportArray[7]+"").equalsIgnoreCase("null") ? "" : reportArray[7]+"");
				reportVo.setOverTime((reportArray[8]+"").equalsIgnoreCase("null") ? "" : reportArray[8]+"");
				reportVo.setStatus((reportArray[9]+"").equalsIgnoreCase("null") ? "" : reportArray[9]+"");
				attendanceReportVos.add(reportVo);
			}
			
			
			
		}catch(Exception e){
			log.error("Error Occured ",e);
			log.error("getActualAttandanceReport ",e);
		}
		return attendanceReportVos;
	}
	
}
