package com.Ntranga.CLMS.Controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Ntranga.CLMS.Service.AssociatingDepartmentToLocationPlantService;
import com.Ntranga.CLMS.Service.CommonService;
import com.Ntranga.CLMS.Service.JobAllocationByVendorService;
import com.Ntranga.CLMS.Service.ReportService;
import com.Ntranga.CLMS.Service.ShiftsDefineService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.Service.WorkerService;
import com.Ntranga.CLMS.loginService.LoginService;
import com.Ntranga.CLMS.vo.AttendanceReportVo;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.DepartmentVo;
import com.Ntranga.CLMS.vo.ShiftsDefineVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

@SuppressWarnings({"rawtypes","deprecation","unused","unchecked"})
@RestController
@RequestMapping(value = "reportController")
public class ReportController {
	
	private static Logger log = Logger.getLogger(ReportController.class);
	
	@Autowired
	private CommonService commonService;
	@Autowired
	private VendorService vendorService;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private ReportService reportService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private ShiftsDefineService shiftsDefineService;
	@Autowired
	private JobAllocationByVendorService jobAllocationByVendorService;
	@Autowired
	AssociatingDepartmentToLocationPlantService associatingDepartmentToLocationPlantService;
	
	private @Value("#{system['JASPERREPORTLOCATION']}")
	String jasperlocation;
	
	private @Value("#{db['db.driver']}")
	String driverClass;
	private @Value("#{db['db.url']}")
	String url;
	private @Value("#{db['db.username']}")
	String username;
	private @Value("#{db['db.password']}")
	String password;
	private @Value("#{db['db.schemaName']}")
	String dbSchemaName;
		
	
	@RequestMapping(value = "/getSchemaNameByUserId.json", method = RequestMethod.POST)
	public  ResponseEntity<SimpleObject> getSchemaNameByUserId(@RequestBody String jSonString) {	
		SimpleObject object = new SimpleObject();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject)jsonParser.parse(jSonString);  
		String schemaName = loginService.getSchemaNameByUserId(jo.get("userId").getAsString());			
		if(schemaName != null )
			object.setName(schemaName);
		System.out.println("schemaName :: "+schemaName);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<SimpleObject>(object,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getCustomerListForReportView.json", method = RequestMethod.POST)
	public  ResponseEntity<List<CustomerVo>> getCustomerListForReportView(@RequestBody String jSonString) {	
		List<CustomerVo> customerList = new ArrayList();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject)jsonParser.parse(jSonString);  
		 customerList = vendorService.getCustomerNamesAsDropDown(jo.get("customerId").getAsString());	
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<CustomerVo>>(customerList,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getVendorNamesAndDepartmentsAsDropDown.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String, List>> getVendorNamesAsDropDown(@RequestBody String customerCompanyJsonString) {
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(customerCompanyJsonString);
		String locationId = null;
		try{
			if(jo.has("locationId") && jo.get("locationId") != null &&  !jo.get("locationId").isJsonNull() && jo.get("locationId").getAsJsonArray().size() > 0 ){
				JsonArray locId = jo.get("locationId").getAsJsonArray();
				locationId = locId.toString().replace("]", "").replace("[", "");
			}
			List<SimpleObject> vendorNames  = workerService.getVendorNamesAsDropDown(jo.get("customerId").getAsString(), jo.get("companyId").getAsString(),jo.get("vendorId").getAsString(), "Validated",locationId);
			
			ShiftsDefineVo shift = new ShiftsDefineVo();
			shift.setCustomerDetailsId(Integer.parseInt(jo.get("customerId").getAsString()) );
			shift.setCompanyDetailsId(Integer.parseInt(jo.get("companyId").getAsString()));
			
			List<ShiftsDefineVo> shiftsDefineVos = workerService.getAvailableShifts(shift);
			masterInfoMap.put("shiftCodes", shiftsDefineVos);
			
			List<SimpleObject> vendorNamesList = new ArrayList<SimpleObject>();
			if(jo.get("vendorId").getAsInt() == 0){
				vendorNamesList.add(new SimpleObject(0, "All"));
			}
			vendorNamesList.addAll(vendorNames);
			//System.out.println("vendorNamesList:::"+vendorNamesList.size());
			masterInfoMap.put("vendorList", vendorNamesList);
			
			List<SimpleObject> locationsList = jobAllocationByVendorService.getlocationsList(jo.get("customerId").getAsString(),jo.get("companyId").getAsString(),locationId);
			masterInfoMap.put("locationsList", locationsList);
		}catch(Exception e){
			log.error("Error Occured ",e);
			//e.printStackTrace();
		}
		/*List<SimpleObject>  departmentList = employeeService.getDepartmentListByCompanyId(jo.get("customerId").getAsInt(), jo.get("companyId").getAsInt());
		masterInfoMap.put("departmentList", departmentList);
		List<SimpleObject> countriesList = commonService.getCompanyCountries(jo.get("companyId").getAsInt());
		masterInfoMap.put("countriesList", countriesList);	*/	
		return new ResponseEntity<Map<String, List>>(masterInfoMap,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getLocationListByDept.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getLocationListByDept(@RequestBody String jSonString) {
		List<SimpleObject> locationList = new ArrayList();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject)jsonParser.parse(jSonString);  
		 locationList = commonService.getLocationListByDept(jo.get("customerId").getAsInt(), jo.get("companyId").getAsInt(), jo.get("departmentId").getAsInt());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(locationList,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getSectionAndWorkOrderListByLocation.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List<SimpleObject>>> getSectionListByLocation(@RequestBody String jSonString) {	
		Map<String,List<SimpleObject>> locationList = new HashMap<String, List<SimpleObject>>();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject)jsonParser.parse(jSonString);  
		 locationList = commonService.getSectionAndWorkOrderListByLocation(jo.get("customerId").getAsInt(), jo.get("companyId").getAsInt(), jo.get("departmentId").getAsInt(), jo.get("locationId").getAsInt());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List<SimpleObject>>>(locationList,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAttandanceReport.json", method = RequestMethod.POST)
	public  ResponseEntity<List<AttendanceReportVo>> getAttandanceReport(@RequestBody String jSonString) {			
		List<AttendanceReportVo> customerList = reportService.getAttandanceReport(jSonString);									
		return new ResponseEntity<List<AttendanceReportVo>>(customerList,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getActualAttandanceReport.json", method = RequestMethod.POST)
	public  ResponseEntity<List<AttendanceReportVo>> getActualAttandanceReport(@RequestBody String jSonString) {			
		List<AttendanceReportVo> customerList = reportService.getActualAttandanceReport(jSonString);									
		return new ResponseEntity<List<AttendanceReportVo>>(customerList,HttpStatus.OK);
	}
	
	
	private Connection getConnection() {
		Connection conn = null;
        try {        	 
        	// System.out.println(driverClass+" :: driverClass"+url+" :: "+username+" :: "+password);
        	Class.forName(driverClass);        
           // conn = DriverManager.getConnection("jdbc:mysql://10.10.10.110/clms_testing","root","root@123");
        	conn = DriverManager.getConnection(url,username,password);
            return conn;
        } catch (Exception e) {
            return null;
        }
    }
	
	
	@RequestMapping(value ="/downloadExcelAttendanceReport", method = RequestMethod.POST)
	public  void downloadExcelAttendanceReport(HttpServletRequest request, HttpServletResponse response,@RequestBody String jSonString) {
		 Connection con = null;              
	        try {
	        	JsonParser jsonParser = new JsonParser();
	    		JsonObject jo = (JsonObject)jsonParser.parse(jSonString); 
	    		System.out.println(jSonString+"::jSonString");	    		
	        	byte[] outputByte = new byte[4096]; 
	        	response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        	response.setHeader("Content-disposition", "attachment; filename=AttendanceReport.xls");
	            con = getConnection();             
	            Map<String, Object> params = new HashMap<String, Object>();
	            params.put("customerId", jo.get("customerId").getAsString());
	            params.put("companyId", jo.get("companyId").getAsString());
	          
	            if(jo.get("schemaName") != null && !jo.get("schemaName").isJsonNull() && !(jo.get("schemaName").getAsString().equalsIgnoreCase("null")) && !(jo.get("schemaName").toString().equalsIgnoreCase("null")) && !(jo.get("schemaName").toString().isEmpty()) ){
	            	params.put("schemaName",jo.get("schemaName").getAsString());
				}else{
					params.put("schemaName",dbSchemaName);
				}
	            
	            if( !jo.get("vendorId").isJsonNull() && !(jo.get("vendorId").getAsString().equalsIgnoreCase("null")) && !(jo.get("vendorId").getAsString().equalsIgnoreCase("null")) && jo.get("vendorId").getAsInt() > 0){
	            	params.put("vendorId", jo.get("vendorId").getAsString());
	            }else{
	            	params.put("vendorId", "-1");
	            }
	            if( !jo.get("employeeCode").isJsonNull() && !(jo.get("employeeCode").getAsString().equalsIgnoreCase("null"))){
	            	params.put("employeeCode", jo.get("employeeCode").getAsString());
				}else{
					params.put("employeeCode", "");
				}
				if( !jo.get("employeeName").isJsonNull() && !(jo.get("employeeName").toString().equalsIgnoreCase("null")) && !(jo.get("employeeName").getAsString().equalsIgnoreCase("null"))){
					params.put("employeeName", jo.get("employeeName").getAsString());
				}else{
					params.put("employeeName", "");
				}
				
				if(jo.get("companyName") != null && !jo.get("companyName").isJsonNull() && !(jo.get("companyName").getAsString().equalsIgnoreCase("null")) && !(jo.get("companyName").toString().equalsIgnoreCase("null"))  && !(jo.get("companyName").toString().isEmpty())  ){
	            	params.put("companyName",jo.get("companyName").getAsString());
				}else{
					params.put("companyName","");
				}
				
				if( !jo.get("fromdate").isJsonNull() && !jo.get("todate").isJsonNull() && !(jo.get("year").getAsString().equalsIgnoreCase("null")) && !(jo.get("month").getAsString().equalsIgnoreCase("null")) && jo.get("year").getAsInt() > 0  && jo.get("month").getAsInt() > 0  ){
					params.put("year", jo.get("year").getAsString());
		            params.put("month", jo.get("month").getAsString());
		            params.put("fromdate", "01/"+jo.get("month").getAsString()+"/"+jo.get("year").getAsString());
		            params.put("todate", "31/"+jo.get("month").getAsString()+"/"+jo.get("year").getAsString());
				}
				if( !jo.get("fromdate").isJsonNull() &&  !jo.get("todate").isJsonNull() &&  !(jo.get("todate").toString().equalsIgnoreCase("null")) && !(jo.get("todate").getAsString().equalsIgnoreCase("null")) && !(jo.get("todate").toString().trim().isEmpty()) && !(jo.get("fromdate").toString().equalsIgnoreCase("null")) && !(jo.get("fromdate").getAsString().equalsIgnoreCase("null")) && !(jo.get("fromdate").toString().trim().isEmpty()) ){
					params.put("fromdate", jo.get("fromdate").getAsString());
		            params.put("todate", jo.get("todate").getAsString());
		            params.put("year", "-1");
		            params.put("month", "-1");
				}
				
				if(jo.get("locationId") != null &&  !jo.get("locationId").isJsonNull() && !(jo.get("locationId").getAsString().equalsIgnoreCase("null")) && !(jo.get("locationId").toString().equalsIgnoreCase("null")) && jo.get("locationId").getAsInt() > 0){
	            	params.put("locationId", jo.get("locationId").getAsString());
	            }else{
	            	params.put("locationId", "-1");
	            } 
	            
				if(jo.get("locationName") != null && !jo.get("locationName").isJsonNull() && !(jo.get("locationName").getAsString().equalsIgnoreCase("null")) && !(jo.get("locationName").toString().equalsIgnoreCase("null"))  && !(jo.get("locationName").toString().isEmpty())  ){
	            	params.put("locationName",jo.get("locationName").getAsString());
				}else{
					params.put("locationName","");
				}
	           
	            if(jo.get("plantId") != null &&  !jo.get("plantId").isJsonNull() && !(jo.get("plantId").getAsString().equalsIgnoreCase("null")) && !(jo.get("plantId").toString().equalsIgnoreCase("null")) && jo.get("plantId").getAsInt() > 0){
	            	params.put("plantId", jo.get("plantId").getAsString());
	            }else{
	            	params.put("plantId", "-1");
	            } 
	            
	            if(jo.get("plantName") != null && !jo.get("plantName").isJsonNull() && !(jo.get("plantName").getAsString().equalsIgnoreCase("null")) && !(jo.get("plantName").toString().equalsIgnoreCase("null"))  && !(jo.get("plantName").toString().isEmpty())  ){
	            	params.put("plantName",jo.get("plantName").getAsString());
				}else{
					params.put("plantName","");
				}
	            
	            if(jo.get("departmentId") != null &&  !jo.get("departmentId").isJsonNull() && !(jo.get("departmentId").getAsString().equalsIgnoreCase("null")) && !(jo.get("departmentId").toString().equalsIgnoreCase("null")) && jo.get("departmentId").getAsInt() > 0){
	            	params.put("departmentId", jo.get("departmentId").getAsString());
	            }else{
	            	params.put("departmentId", "-1");
	            } 
	         
	            if(jo.get("sectionId") != null &&  !jo.get("sectionId").isJsonNull() && !(jo.get("sectionId").getAsString().equalsIgnoreCase("null")) && !(jo.get("sectionId").toString().equalsIgnoreCase("null")) && jo.get("sectionId").getAsInt() > 0){
	            	params.put("sectionId", jo.get("sectionId").getAsString());
	            }else{
	            	params.put("sectionId", "-1");
	            } 
	            
	            if(jo.get("workAreaId") != null &&  !jo.get("workAreaId").isJsonNull() && !(jo.get("workAreaId").getAsString().equalsIgnoreCase("null")) && !(jo.get("workAreaId").toString().equalsIgnoreCase("null")) && jo.get("workAreaId").getAsInt() > 0){
	            	params.put("workAreaId", jo.get("workAreaId").getAsString());
	            }else{
	            	params.put("workAreaId", "-1");
	            } 
	            
	            if(jo.get("workSkill") != null &&  !jo.get("workSkill").isJsonNull() && !(jo.get("workSkill").getAsString().equalsIgnoreCase("null")) && !(jo.get("workSkill").toString().equalsIgnoreCase("null"))){
	            	params.put("workSkill", jo.get("workSkill").getAsString());
	            }else{
	            	params.put("workSkill", "-1");
	            } 
	            
	            if(jo.get("shift") != null &&  !jo.get("shift").isJsonNull() && !(jo.get("shift").getAsString().equalsIgnoreCase("null")) && !(jo.get("shift").toString().equalsIgnoreCase("null"))){
	            	params.put("shift", jo.get("shift").getAsString());
	            }else{
	            	params.put("shift", "-1");
	            } 
				
	            params.forEach((key,value) -> System.out.println(key+" :: "+value+" :: "+jo.get("reportName").getAsString()));	
				JasperReport jasperReport = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/reports/"+jo.get("reportName").getAsString()+".jrxml");
	            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, con);
	            ByteArrayOutputStream  outputStream = new ByteArrayOutputStream();
	            
	            JRXlsxExporter xlsExporter = new JRXlsxExporter();
	            xlsExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
	            xlsExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputStream);
	            xlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
	            xlsExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
	            xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
	            xlsExporter.exportReport();
	            
	          /*  JExcelApiExporter exporterXLS = new JExcelApiExporter();
				exporterXLS.setParameter(JExcelApiExporterParameter.JASPER_PRINT, jasperPrint);
				exporterXLS.setParameter(JExcelApiExporterParameter.OUTPUT_STREAM, outputStream);
				exporterXLS.setParameter(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
				exporterXLS.setParameter(JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
				exporterXLS.setParameter(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
				exporterXLS.exportReport();
				ByteArrayInputStream mediais = new ByteArrayInputStream(outputStream.toByteArray());
				//AMedia amedia = new AMedia("FileFormatExcel", "xls", "application/vnd.ms-excel", mediais);

				//callReportWindow(amedia, "XLS");
				
				*/
				
	            	          
	           // JasperExportManager.exportReportToXmlStream(jasperPrint, outputStream);                  
	            OutputStream  ouputStream = response.getOutputStream();
	            outputByte = outputStream.toByteArray();              
	            ouputStream.write(outputByte);          
	            ouputStream.flush();
	            ouputStream.close();
	            
	            
	        } catch (Exception e) {
	        	e.printStackTrace();
	            //System.out.println("IN CATCH PDF UTIL");
	            log.error("Error Occured ",e);
	        } finally {
	            if (con != null) {
	                try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						log.error("Error Occured ",e);
					}
	            }
	        //System.out.println("Connection closed in pdfutil");
	        }
	    }	
	
	
	
	@RequestMapping(value ="/downloadExcelAttendanceReportForCSV", method = RequestMethod.POST)
	public  void downloadExcelAttendanceReportForCSV(HttpServletRequest request, HttpServletResponse response,@RequestBody String jSonString) {
		 Connection con = null;              
	        try {
	        	JsonParser jsonParser = new JsonParser();
	    		JsonObject jo = (JsonObject)jsonParser.parse(jSonString); 
	    		System.out.println(jSonString+"::jSonString");	    		
	        	byte[] outputByte = new byte[4096]; 
	        	response.setContentType("text/csv");
	        	response.setHeader("Content-disposition", "attachment; filename=BulkTimeUploadCSV.csv");
	            con = getConnection();             
	            Map<String, Object> params = new HashMap<String, Object>();
	            params.put("customerId", jo.get("customerId").getAsString());
	            params.put("companyId", jo.get("companyId").getAsString());
	          
	            if(jo.get("schemaName") != null && !jo.get("schemaName").isJsonNull() && !(jo.get("schemaName").getAsString().equalsIgnoreCase("null")) && !(jo.get("schemaName").toString().equalsIgnoreCase("null")) && !(jo.get("schemaName").toString().isEmpty()) ){
	            	params.put("schemaName",jo.get("schemaName").getAsString());
				}else{
					params.put("schemaName",dbSchemaName);
				}
	            
	            if( !jo.get("vendorId").isJsonNull() && !(jo.get("vendorId").getAsString().equalsIgnoreCase("null")) && !(jo.get("vendorId").getAsString().equalsIgnoreCase("null")) && jo.get("vendorId").getAsInt() > 0){
	            	params.put("vendorId", jo.get("vendorId").getAsString());
	            }else{
	            	params.put("vendorId", "-1");
	            }
	            if( !jo.get("employeeCode").isJsonNull() && !(jo.get("employeeCode").getAsString().equalsIgnoreCase("null"))){
	            	params.put("employeeCode", jo.get("employeeCode").getAsString());
				}else{
					params.put("employeeCode", "");
				}
				if( !jo.get("employeeName").isJsonNull() && !(jo.get("employeeName").toString().equalsIgnoreCase("null")) && !(jo.get("employeeName").getAsString().equalsIgnoreCase("null"))){
					params.put("employeeName", jo.get("employeeName").getAsString());
				}else{
					params.put("employeeName", "");
				}
				
				if( !jo.get("fromdate").isJsonNull() && !jo.get("todate").isJsonNull() && !(jo.get("year").getAsString().equalsIgnoreCase("null")) && !(jo.get("month").getAsString().equalsIgnoreCase("null")) && jo.get("year").getAsInt() > 0  && jo.get("month").getAsInt() > 0  ){
					params.put("year", jo.get("year").getAsString());
		            params.put("month", jo.get("month").getAsString());
		            params.put("fromdate", "01/"+jo.get("month").getAsString()+"/"+jo.get("year").getAsString());
		            params.put("todate", "31/"+jo.get("month").getAsString()+"/"+jo.get("year").getAsString());
				}
				if( !jo.get("fromdate").isJsonNull() &&  !jo.get("todate").isJsonNull() &&  !(jo.get("todate").toString().equalsIgnoreCase("null")) && !(jo.get("todate").getAsString().equalsIgnoreCase("null")) && !(jo.get("todate").toString().trim().isEmpty()) && !(jo.get("fromdate").toString().equalsIgnoreCase("null")) && !(jo.get("fromdate").getAsString().equalsIgnoreCase("null")) && !(jo.get("fromdate").toString().trim().isEmpty()) ){
					params.put("fromdate", jo.get("fromdate").getAsString());
		            params.put("todate", jo.get("todate").getAsString());
		            params.put("year", "-1");
		            params.put("month", "-1");
				}
				
            
	            JRCsvExporter  csvExporter = new JRCsvExporter();
	            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	           JasperReport jasperReport = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/reports/"+jo.get("reportName").getAsString()+".jrxml");
	            
	            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, con);
	            csvExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	            csvExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
	            csvExporter.exportReport();
	            byte[] archivo = byteArrayOutputStream.toByteArray();
	                        
	            OutputStream  ouputStream = response.getOutputStream();
	           
	            outputByte = byteArrayOutputStream.toByteArray();              
	            ouputStream.write(outputByte); 
	            ouputStream.flush();
	            ouputStream.close();
	           
	        } catch (Exception e) {
	        	e.printStackTrace();
	            //System.out.println("IN CATCH PDF UTIL");
	            log.error("Error Occured ",e);
	        } finally {
	            if (con != null) {
	                try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						log.error("Error Occured ",e);
					}
	            }
	        //System.out.println("Connection closed in pdfutil");
	        }
	    }	
	
	
	
	@RequestMapping(value ="/downloadPDFAttendanceReport", method = RequestMethod.POST)
	public  void downloadPDFAttendanceReport(HttpServletRequest request, HttpServletResponse response,@RequestBody String jSonString) {
        Connection con = null;              
        try {
        	JsonParser jsonParser = new JsonParser();
    		JsonObject jo = (JsonObject)jsonParser.parse(jSonString); 
    		System.out.println(jSonString+"::jSonString");    		
        	byte[] outputByte = new byte[4096]; 
        	//response.setContentType("application/pdf");
        	//response.setHeader("Content-disposition", "attachment; filename=AttendanceReport.pdf");
            con = getConnection();             
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("customerId", jo.get("customerId").getAsString());
            params.put("companyId", jo.get("companyId").getAsString());
            params.put("PlantName", jo.get("plantName").getAsString());
            params.put("LocationName", jo.get("locationName").getAsString());
            params.put("CompanyName", jo.get("companyName").getAsString());
            
            
            if(jo.get("schemaName") != null && !jo.get("schemaName").isJsonNull() && !(jo.get("schemaName").getAsString().equalsIgnoreCase("null")) && !(jo.get("schemaName").toString().equalsIgnoreCase("null"))  && !(jo.get("schemaName").toString().isEmpty()) ){
            	params.put("schemaName",jo.get("schemaName").getAsString());
			}else{
				params.put("schemaName",dbSchemaName);
			}
                     
            if( !jo.get("vendorId").isJsonNull() && !(jo.get("vendorId").getAsString().equalsIgnoreCase("null")) && !(jo.get("vendorId").getAsString().equalsIgnoreCase("null")) && jo.get("vendorId").getAsInt() > 0){
            	params.put("vendorId", jo.get("vendorId").getAsString());
            }else{
            	params.put("vendorId", "-1");
            }                
            if( !jo.get("employeeCode").isJsonNull() && !(jo.get("employeeCode").getAsString().equalsIgnoreCase("null"))){
            	params.put("employeeCode", jo.get("employeeCode").getAsString());
			}else{
				params.put("employeeCode", "");
			}
			if( !jo.get("employeeName").isJsonNull() && !(jo.get("employeeName").toString().equalsIgnoreCase("null")) && !(jo.get("employeeName").getAsString().equalsIgnoreCase("null"))){
				params.put("employeeName", jo.get("employeeName").getAsString());
			}else{
				params.put("employeeName", "");
			}
			
			if(jo.get("locationId") != null &&  !jo.get("locationId").isJsonNull() && !(jo.get("locationId").getAsString().equalsIgnoreCase("null")) && !(jo.get("locationId").toString().equalsIgnoreCase("null")) && jo.get("locationId").getAsInt() > 0){
            	params.put("LocationId", jo.get("locationId").getAsString());
            }else{
            	params.put("LocationId", "-1");
            } 
            
           
            if(jo.get("plantId") != null &&  !jo.get("plantId").isJsonNull() && !(jo.get("plantId").getAsString().equalsIgnoreCase("null")) && !(jo.get("plantId").toString().equalsIgnoreCase("null")) && jo.get("plantId").getAsInt() > 0){
            	params.put("PlantId", jo.get("plantId").getAsString());
            }else{
            	params.put("PlantId", "-1");
            } 
            
            if(jo.get("departmentId") != null &&  !jo.get("departmentId").isJsonNull() && !(jo.get("departmentId").getAsString().equalsIgnoreCase("null")) && !(jo.get("departmentId").toString().equalsIgnoreCase("null")) && jo.get("departmentId").getAsInt() > 0){
            	params.put("DepartmentId", jo.get("departmentId").getAsString());
            }else{
            	params.put("DepartmentId", "-1");
            } 
         
            if(jo.get("sectionId") != null &&  !jo.get("sectionId").isJsonNull() && !(jo.get("sectionId").getAsString().equalsIgnoreCase("null")) && !(jo.get("sectionId").toString().equalsIgnoreCase("null")) && jo.get("sectionId").getAsInt() > 0){
            	params.put("SectionId", jo.get("sectionId").getAsString());
            }else{
            	params.put("SectionId", "-1");
            } 
            
            if(jo.get("workAreaId") != null &&  !jo.get("workAreaId").isJsonNull() && !(jo.get("workAreaId").getAsString().equalsIgnoreCase("null")) && !(jo.get("workAreaId").toString().equalsIgnoreCase("null")) && jo.get("workAreaId").getAsInt() > 0){
            	params.put("WorkAreaId", jo.get("workAreaId").getAsString());
            }else{
            	params.put("WorkAreaId", "-1");
            } 
            
            if(jo.get("workSkill") != null &&  !jo.get("workSkill").isJsonNull() && !(jo.get("workSkill").getAsString().equalsIgnoreCase("null")) && !(jo.get("workSkill").toString().equalsIgnoreCase("null"))){
            	params.put("WorkSkill", jo.get("workSkill").getAsString());
            }else{
            	params.put("WorkSkill", "-1");
            } 
            
            if(jo.get("shift") != null &&  !jo.get("shift").isJsonNull() && !(jo.get("shift").getAsString().equalsIgnoreCase("null")) && !(jo.get("shift").toString().equalsIgnoreCase("null"))){
            	params.put("Shift", jo.get("shift").getAsString());
            }else{
            	params.put("Shift", "-1");
            } 
			
			if( !jo.get("fromdate").isJsonNull() && !jo.get("todate").isJsonNull() && !(jo.get("year").getAsString().equalsIgnoreCase("null")) && !(jo.get("month").getAsString().equalsIgnoreCase("null")) && jo.get("year").getAsInt() > 0  && jo.get("month").getAsInt() > 0  ){
				params.put("year", jo.get("year").getAsString());
	            params.put("month", jo.get("month").getAsString());
	            params.put("fromdate", "01/"+jo.get("month").getAsString()+"/"+jo.get("year").getAsString());
	            Calendar calendar = Calendar.getInstance();
	            calendar.set(Calendar.YEAR,jo.get("year").getAsInt());
	            calendar.set(Calendar.MONTH,jo.get("month").getAsInt()-1);
	            int daysQty =calendar.getActualMaximum(Calendar.DATE);
	            params.put("todate", daysQty+"/"+jo.get("month").getAsString()+"/"+jo.get("year").getAsString());
	            System.out.println(calendar.getTime());
			}
			if( !jo.get("fromdate").isJsonNull() &&  !jo.get("todate").isJsonNull() &&  !(jo.get("todate").toString().equalsIgnoreCase("null")) && !(jo.get("todate").getAsString().equalsIgnoreCase("null")) && !(jo.get("todate").toString().trim().isEmpty()) && !(jo.get("fromdate").toString().equalsIgnoreCase("null")) && !(jo.get("fromdate").getAsString().equalsIgnoreCase("null")) && !(jo.get("fromdate").toString().trim().isEmpty()) ){
				params.put("fromdate", jo.get("fromdate").getAsString());
	            params.put("todate", jo.get("todate").getAsString());
	            params.put("year", "-1");
	            params.put("month", "-1");
			} 
			
			 params.forEach((key,value) -> System.out.println(key+" :: "+value+" :: "+jo.get("reportName").getAsString()));			
			// File file = new File(request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/reports/"+jo.get("reportName").getAsString()+".jrxml");
			// System.out.println(file.exists());
			 System.out.println(params.toString());
            JasperReport jasperReport = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/reports/"+jo.get("reportName").getAsString()+".jrxml");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, con);
            ByteArrayOutputStream  outputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);                  
            OutputStream  ouputStream = response.getOutputStream();
            outputByte = outputStream.toByteArray();              
            ouputStream.write(outputByte);          
            ouputStream.flush();
            ouputStream.close();         
            
            //System.out.println("completed");   
        } catch (Exception e) {
            //System.out.println("IN CATCH PDF UTIL");
            log.error("Error Occured ",e);
        } finally {
            if (con != null) {
                try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					log.error("Error Occured ",e);
				}
            }
        //System.out.println("Connection closed in pdfutil");
        }
    }	
	
	
	@RequestMapping(value ="/downloadPDFMustorRollReport", method = RequestMethod.POST)
	public  void downloadPDFMustorRollReport(HttpServletRequest request, HttpServletResponse response,@RequestBody String jSonString) {
        Connection con = null;              
        try {
        	JsonParser jsonParser = new JsonParser();
    		JsonObject jo = (JsonObject)jsonParser.parse(jSonString); 
    		System.out.println(jSonString+"::jSonString");
    		System.out.println(jo+" :: jo");
        	byte[] outputByte = new byte[4096]; 
        	response.setContentType("application/pdf");
        	response.setHeader("Content-disposition", "attachment; filename=Muster_Roll_FormXVI.pdf");
            con = getConnection();             
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("Vendor_Code", jo.get("Vendor_Code").getAsString());
           
            if(jo.get("schemaName") != null && !jo.get("schemaName").isJsonNull() && !(jo.get("schemaName").getAsString().equalsIgnoreCase("null")) && !(jo.get("schemaName").toString().equalsIgnoreCase("null"))  && !(jo.get("schemaName").toString().isEmpty()) ){
            	params.put("schemaName",jo.get("schemaName").getAsString());
			}else{
				params.put("schemaName",dbSchemaName);
			}
            
			if(!(jo.get("Year").getAsString().equalsIgnoreCase("null")) && !(jo.get("Month").getAsString().equalsIgnoreCase("null")) && jo.get("Year").getAsInt() > 0  && jo.get("Month").getAsInt() > 0  ){
				params.put("Year", jo.get("Year").getAsString());
	            params.put("Month", jo.get("Month").getAsString());
	           
			}
			
			
			
			
            JasperReport jasperReport = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/reports/"+jo.get("reportName").getAsString()+".jrxml");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, con);
            ByteArrayOutputStream  outputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);                  
            OutputStream  ouputStream = response.getOutputStream();
            outputByte = outputStream.toByteArray();              
            ouputStream.write(outputByte);          
            ouputStream.flush();
            ouputStream.close();
            
            
        } catch (Exception e) {
            //System.out.println("IN CATCH PDF UTIL");
            log.error("Error Occured ",e);
        } finally {
            if (con != null) {
                try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					log.error("Error Occured ",e);
				}
            }
        //System.out.println("Connection closed in pdfutil");
        }
    }
	
	@RequestMapping(value ="/vendorComplianceReport", method = RequestMethod.POST)
	public  void vendorComplianceReport(HttpServletRequest request, HttpServletResponse response,@RequestBody String jSonString) {
		byte[] outputByte = new byte[4096]; 
		Connection con = null; 		
        try {
        	JsonParser jsonParser = new JsonParser();
    		JsonObject jo = (JsonObject)jsonParser.parse(jSonString);
			con = getConnection();             
	        Map<String, Object> params = new HashMap<String, Object>();
	        params.put("customerId", jo.get("customerId").getAsString());
	        params.put("companyId", jo.get("companyId").getAsString());
	        
	        if(jo.get("schemaName") != null && !jo.get("schemaName").isJsonNull() && !(jo.get("schemaName").getAsString().equalsIgnoreCase("null")) && !(jo.get("schemaName").toString().equalsIgnoreCase("null"))  && !(jo.get("schemaName").toString().isEmpty()) ){
            	params.put("schemaName",jo.get("schemaName").getAsString());
			}else{
				params.put("schemaName",dbSchemaName);
			}
	        
	        if(jo.get("status") != null && !jo.get("status").isJsonNull() && !jo.get("status").getAsString().equalsIgnoreCase("null") && !jo.get("status").toString().isEmpty() && !jo.get("status").getAsString().isEmpty())
        	{
        	params.put("status", jo.get("status").getAsString());
        	} else{
        	params.put("status", "-1");
        	}
	        
	        if(!(jo.get("toDate").toString().equalsIgnoreCase("null")) && !(jo.get("toDate").getAsString().equalsIgnoreCase("null")) && !(jo.get("toDate").toString().trim().isEmpty()) && !(jo.get("fromDate").toString().equalsIgnoreCase("null")) && !(jo.get("fromDate").getAsString().equalsIgnoreCase("null")) && !(jo.get("fromDate").toString().trim().isEmpty()) ){
				params.put("From Date", jo.get("fromDate").getAsString());
	            params.put("To Date", jo.get("toDate").getAsString());	            
			}
	        if(!(jo.get("vendorId").toString().equalsIgnoreCase("null")) && !(jo.get("vendorId").getAsString().equalsIgnoreCase("null")) && !(jo.get("vendorId").getAsString().trim().equalsIgnoreCase("select")) && !(jo.get("vendorId").getAsString().trim().isEmpty() )){
				params.put("Vendor Name", jo.get("vendorId").getAsString());
			}else{
				params.put("Vendor Name", "-1");
			}
	        if(!(jo.get("docName").toString().equalsIgnoreCase("null")) && !(jo.get("docName").getAsString().equalsIgnoreCase("null")) && !(jo.get("docName").getAsString().trim().equalsIgnoreCase("select")) && !(jo.get("docName").getAsString().trim().isEmpty() )){
	        	params.put("Document Type", jo.get("docName").getAsString() );
			}else{
				params.put("Document Type", "-1");
			}
	        if(!(jo.get("workOrderName").toString().equalsIgnoreCase("null")) && !(jo.get("workOrderName").getAsString().equalsIgnoreCase("null")) && !(jo.get("workOrderName").getAsString().trim().equalsIgnoreCase("select")) && !(jo.get("workOrderName").getAsString().trim().isEmpty() )){
	        	params.put("Work Order", jo.get("workOrderName").getAsString() );
			}else{
				params.put("Work Order", "-1");
			}
	        
	        if(jo.get("validated").getAsBoolean())
	        	params.put("validated","Validated");
	        else
	        	params.put("validated","-1");
	        
	        if(jo.get("saved").getAsBoolean())
	        	params.put("saved","Saved");
	        else
	        	params.put("saved","-1");
	        
	        params.forEach((k,v)->System.out.println("Item : " + k + " Count : " + v));
	        
			JasperReport jasperReport = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/reports/VendorComplianceReport.jrxml");
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, con);
	        ByteArrayOutputStream  outputStream = new ByteArrayOutputStream();
	        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);                  
	        OutputStream  ouputStream = response.getOutputStream();
	        outputByte = outputStream.toByteArray();              
	        ouputStream.write(outputByte);          
	        ouputStream.flush();
	        ouputStream.close();
        }catch(Exception e){
        	log.error("Error Occured ",e);
        }
	}
	
	@RequestMapping(value ="/workerBankDetailsView", method = RequestMethod.POST)
	public  void workerBankDetailsView(HttpServletRequest request, HttpServletResponse response,@RequestBody String jSonString) {
		byte[] outputByte = new byte[4096]; 
		Connection con = null; 		
        try {
        	JsonParser jsonParser = new JsonParser();
    		JsonObject jo = (JsonObject)jsonParser.parse(jSonString);
			con = getConnection();             
	        Map<String, Object> params = new HashMap<String, Object>();
	        params.put("customerId", jo.get("customerId").getAsString());
	        params.put("companyId", jo.get("companyId").getAsString());
	        
	      
	        if(!(jo.get("vendorId").toString().equalsIgnoreCase("null")) && !(jo.get("vendorId").getAsString().equalsIgnoreCase("null")) && !(jo.get("vendorId").getAsString().trim().equalsIgnoreCase("select")) && !(jo.get("vendorId").getAsString().trim().isEmpty() )){
				params.put("vendorId", jo.get("vendorId").getAsString());
			}else{
				params.put("vendorId", "-1");
			}
	        
	        if(!(jo.get("bankName").toString().equalsIgnoreCase("null")) && !(jo.get("bankName").getAsString().equalsIgnoreCase("null")) && !(jo.get("bankName").getAsString().trim().equalsIgnoreCase("select")) && !(jo.get("bankName").getAsString().trim().isEmpty() )){
	        	params.put("bankName", jo.get("bankName").getAsString() );
			}else{
				params.put("bankName", "");
			}
	        
	        if(!(jo.get("searchBy").toString().equalsIgnoreCase("null")) && !(jo.get("searchBy").getAsString().equalsIgnoreCase("null")) && !(jo.get("searchBy").getAsString().trim().equalsIgnoreCase("select")) && !(jo.get("searchBy").getAsString().trim().isEmpty() )){
	        	params.put("searchBy", jo.get("searchBy").getAsString() );
			}else{
				params.put("searchBy", "");
			}
	       /* if(!(jo.get("ifscCode").toString().equalsIgnoreCase("null")) && !(jo.get("ifscCode").getAsString().equalsIgnoreCase("null")) && !(jo.get("ifscCode").getAsString().trim().equalsIgnoreCase("select")) && !(jo.get("ifscCode").getAsString().trim().isEmpty() )){
	        	params.put("ifscCode", jo.get("ifscCode").getAsString() );
			}else{
				params.put("ifscCode", "-1");
			}*/
	        
	        
	        if( !jo.get("employeeCode").isJsonNull() && !(jo.get("employeeCode").getAsString().equalsIgnoreCase("null")) && !(jo.get("employeeCode").getAsString().isEmpty())){
            	params.put("employeeCode", jo.get("employeeCode").getAsString());
			}else{
				params.put("employeeCode", "-1");
			}
			if( !jo.get("employeeName").isJsonNull() && !(jo.get("employeeName").toString().equalsIgnoreCase("null")) && !(jo.get("employeeName").getAsString().equalsIgnoreCase("null")) && !(jo.get("employeeCode").getAsString().isEmpty())){
				params.put("employeeName", jo.get("employeeName").getAsString());
			}else{
				params.put("employeeName", "");
			}
	        
	        params.forEach((k,v)->System.out.println("Item : " + k + " Count : ::" + v+"::"));
	        
			JasperReport jasperReport = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/reports/WorkerBankDetails.jrxml");
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, con);
	        ByteArrayOutputStream  outputStream = new ByteArrayOutputStream();
	        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);                  
	        OutputStream  ouputStream = response.getOutputStream();
	        outputByte = outputStream.toByteArray();              
	        ouputStream.write(outputByte);          
	        ouputStream.flush();
	        ouputStream.close();
        }catch(Exception e){
        	log.error("Error Occured ",e);
        }
	}
	
	
	

	@RequestMapping(value ="/certificateByPrincipalEmployerFormVReport", method = RequestMethod.POST)
	public  void certificateByPrincipalEmployerFormVReport(HttpServletRequest request, HttpServletResponse response,@RequestBody String jSonString) {
		byte[] outputByte = new byte[4096]; 
		Connection con = null; 		
        try {
        	JsonParser jsonParser = new JsonParser();
    		JsonObject jo = (JsonObject)jsonParser.parse(jSonString);
			con = getConnection();             
	        Map<String, Object> params = new HashMap<String, Object>();
	        params.put("Customer_Name", jo.get("customerId").getAsString());
            params.put("Company_Name", jo.get("companyId").getAsString());
            
            if(jo.get("schemaName") != null && !jo.get("schemaName").isJsonNull() && !(jo.get("schemaName").getAsString().equalsIgnoreCase("null")) && !(jo.get("schemaName").toString().equalsIgnoreCase("null"))  && !(jo.get("schemaName").toString().isEmpty()) ){
            	params.put("schemaName",jo.get("schemaName").getAsString());
			}else{
				params.put("schemaName",dbSchemaName);
			}
            
            if(!(jo.get("vendorId").toString().equalsIgnoreCase("null")) && !(jo.get("vendorId").getAsString().equalsIgnoreCase("null")) && !(jo.get("vendorId").getAsString().trim().equalsIgnoreCase("select")) && !(jo.get("vendorId").getAsString().trim().isEmpty() )){
				params.put("Vendor_Name", jo.get("vendorId").getAsString());
			}else{
				params.put("Vendor_Name", "");
			}            
	       // params.forEach((k,v)->System.out.println("Item : " + k + " Count : " + v));
        	JasperReport jasperReport = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/reports/certificateByPrincipalEmployerFormVReport.jrxml");
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, con);
	        ByteArrayOutputStream  outputStream = new ByteArrayOutputStream();
	        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);                  
	        OutputStream  ouputStream = response.getOutputStream();
	        outputByte = outputStream.toByteArray();              
	        ouputStream.write(outputByte);          
	        ouputStream.flush();
	        ouputStream.close();
        }catch(Exception e){
        	log.error("Error Occured ",e);
        }
	}
	
	
	@RequestMapping(value ="/downloadSampleFile", method = RequestMethod.POST)
	public  void downloadSampleFile(HttpServletRequest request, HttpServletResponse response,@RequestBody String jSonString) {		
	   // System.out.println();
	    try {    	
	    	
	    	JsonParser jsonParser = new JsonParser();
    		JsonObject jo = (JsonObject)jsonParser.parse(jSonString);
    		//System.out.println(jSonString+":::"+jo.get("name").getAsString());
	        File downloadFile = new File(request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/samples/"+jo.get("name").getAsString()+".csv");
	        FileInputStream inStream = new FileInputStream(downloadFile);
	         
	        
	         
	        // obtains ServletContext
	        /*ServletContext context = getServletContext();
	         
	        // gets MIME type of the file
	        String mimeType = context.getMimeType(request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/samples/Upload Worker.csv");
	        if (mimeType == null) {        
	            // set to binary type if MIME mapping not found
	            mimeType = "application/octet-stream";
	        }
	        System.out.println("MIME type: " + mimeType);*/
	         
	        // modifies response
	        response.setContentType("application/octet-stream");
	        response.setContentLength((int) downloadFile.length());
	         
	        // forces download
	        String headerKey = "Content-Disposition";
	        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
	        response.setHeader(headerKey, headerValue);
	         
	        // obtains response's output stream
	        OutputStream outStream = response.getOutputStream();
	         
	        byte[] buffer = new byte[4096];
	        int bytesRead = -1;
	         
	        while ((bytesRead = inStream.read(buffer)) != -1) {
	            outStream.write(buffer, 0, bytesRead);
	        }
	        outStream.flush();
	        inStream.close();
	        outStream.close();     
	    }catch(Exception e){
	    	log.error("Error Occured ",e);
	    } 
    }
	
	
	@RequestMapping(value ="/workmenEmployedByContractorFormXIIIReport", method = RequestMethod.POST)
	public void workmenEmployedByContractorFormXIIIReport(HttpServletRequest request, HttpServletResponse response,@RequestBody String jSonString) {
        Connection con = null;              
        try {
        	JsonParser jsonParser = new JsonParser();
    		JsonObject jo = (JsonObject)jsonParser.parse(jSonString); 
    		System.out.println(jSonString+"::jSonString");    		
        	byte[] outputByte = new byte[4096];         	
            con = getConnection();             
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("customerId", jo.get("customerId").getAsString());
            params.put("companyId", jo.get("companyId").getAsString());
            
            if(jo.get("vendorId") != null &&  !jo.get("vendorId").isJsonNull() && !(jo.get("vendorId").getAsString().equalsIgnoreCase("null")) && !(jo.get("vendorId").getAsString().equalsIgnoreCase("null")) && jo.get("vendorId").getAsInt() > 0){
            	params.put("vendorId", jo.get("vendorId").getAsString());
            }else{
            	params.put("vendorId", "-1");
            }
            
            if(jo.get("departmentId") != null &&  !jo.get("departmentId").isJsonNull() && !(jo.get("departmentId").getAsString().equalsIgnoreCase("null")) && !(jo.get("departmentId").getAsString().equalsIgnoreCase("null")) && jo.get("departmentId").getAsInt() > 0){
            	params.put("departmentId", jo.get("departmentId").getAsString());
            }else{
            	params.put("departmentId", "-1");
            }  
            
            if(jo.get("workerId") != null &&  !jo.get("workerId").isJsonNull() && !(jo.get("workerId").getAsString().equalsIgnoreCase("null")) && !(jo.get("workerId").getAsString().equalsIgnoreCase("null")) && jo.get("workerId").getAsInt() > 0){
            	params.put("workerId", jo.get("workerId").getAsString());
            }else{
            	params.put("workerId", "-1");
            }                       
  
            if(jo.get("workerCode") != null &&  !jo.get("workerCode").isJsonNull() && !(jo.get("workerCode").getAsString().equalsIgnoreCase("null")) && !(jo.get("workerCode").toString().equalsIgnoreCase("null")) ){
            	params.put("workerCode", jo.get("workerCode").getAsString());
            }else{
            	params.put("workerCode", "-1");
            }
            if(jo.get("workerName") != null &&  !jo.get("workerName").isJsonNull() && !(jo.get("workerName").getAsString().equalsIgnoreCase("null")) && !(jo.get("workerName").toString().equalsIgnoreCase("null"))){
            	params.put("workerName", jo.get("workerName").getAsString());
            }else{
            	params.put("workerName", "");
            }
            
			if(jo.get("JoiningDateFrom") != null && jo.get("JoiningDateTo") != null &&  !jo.get("JoiningDateFrom").isJsonNull() && !jo.get("JoiningDateTo").isJsonNull() ){				
				params.put("JoiningDateFrom", jo.get("JoiningDateFrom").getAsString());
	            params.put("JoiningDateTo", jo.get("JoiningDateTo").getAsString());
			}
			
			if(jo.get("status") != null && jo.get("status") != null &&  !jo.get("status").isJsonNull() && !jo.get("status").getAsString().isEmpty()){				
				params.put("status", jo.get("status").getAsString());	          
			}else{
				params.put("status", "-1");
			}
			
			if(jo.get("locationId") != null &&  !jo.get("locationId").isJsonNull() && !(jo.get("locationId").getAsString().equalsIgnoreCase("null")) && !(jo.get("locationId").toString().equalsIgnoreCase("null")) && jo.get("locationId").getAsInt() > 0){
            	params.put("locationId", jo.get("locationId").getAsString());
            }else{
            	params.put("locationId", "-1");
            } 
            
			if(jo.get("locationName") != null && !jo.get("locationName").isJsonNull() && !(jo.get("locationName").getAsString().equalsIgnoreCase("null")) && !(jo.get("locationName").toString().equalsIgnoreCase("null"))  && !(jo.get("locationName").toString().isEmpty())  ){
            	params.put("locationName",jo.get("locationName").getAsString());
			}else{
				params.put("locationName","-1");
			}
           
            if(jo.get("plantId") != null &&  !jo.get("plantId").isJsonNull() && !(jo.get("plantId").getAsString().equalsIgnoreCase("null")) && !(jo.get("plantId").toString().equalsIgnoreCase("null")) && jo.get("plantId").getAsInt() > 0){
            	params.put("plantId", jo.get("plantId").getAsString());
            }else{
            	params.put("plantId", "-1");
            }
            
            if(jo.get("plantName") != null && !jo.get("plantName").isJsonNull() && !(jo.get("plantName").getAsString().equalsIgnoreCase("null")) && !(jo.get("plantName").toString().equalsIgnoreCase("null"))  && !(jo.get("plantName").toString().isEmpty())  ){
            	params.put("plantName",jo.get("plantName").getAsString());
			}else{
				params.put("plantName","");
			}
         
            if(jo.get("sectionId") != null &&  !jo.get("sectionId").isJsonNull() && !(jo.get("sectionId").getAsString().equalsIgnoreCase("null")) && !(jo.get("sectionId").toString().equalsIgnoreCase("null")) && jo.get("sectionId").getAsInt() > 0){
            	params.put("sectionId", jo.get("sectionId").getAsString());
            }else{
            	params.put("sectionId", "-1");
            } 
            
            if(jo.get("workAreaId") != null &&  !jo.get("workAreaId").isJsonNull() && !(jo.get("workAreaId").getAsString().equalsIgnoreCase("null")) && !(jo.get("workAreaId").toString().equalsIgnoreCase("null")) && jo.get("workAreaId").getAsInt() > 0){
            	params.put("workAreaId", jo.get("workAreaId").getAsString());
            }else{
            	params.put("workAreaId", "-1");
            } 
            
            if(jo.get("workSkill") != null &&  !jo.get("workSkill").isJsonNull() && !(jo.get("workSkill").getAsString().equalsIgnoreCase("null")) && !(jo.get("workSkill").toString().equalsIgnoreCase("null"))){
            	params.put("workSkill", jo.get("workSkill").getAsString());
            }else{
            	params.put("workSkill", "-1");
            } 
            
            if(jo.get("shift") != null &&  !jo.get("shift").isJsonNull() && !(jo.get("shift").getAsString().equalsIgnoreCase("null")) && !(jo.get("shift").toString().equalsIgnoreCase("null"))){
            	params.put("shift", jo.get("shift").getAsString());
            }else{
            	params.put("shift", "-1");
            } 
			
			params.forEach((key,value) -> System.out.println(key+" :: "+value+" :: "+jo.get("reportName").getAsString()));
			/*File file = new File(request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/reports/"+jo.get("reportName").getAsString()+".jrxml");
			System.out.println(file.exists());*/
            JasperReport jasperReport = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/reports/"+jo.get("reportName").getAsString()+".jrxml");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, con);
            ByteArrayOutputStream  outputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);                  
            OutputStream  ouputStream = response.getOutputStream();
            outputByte = outputStream.toByteArray();              
            ouputStream.write(outputByte);          
            ouputStream.flush();
            ouputStream.close();
            
            //System.out.println("completed");   
        } catch (Exception e) {
            //System.out.println("IN CATCH PDF UTIL");
            log.error("Error Occured ",e);
        } finally {
            if (con != null) {
                try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					log.error("Error Occured ",e);
				}
            }
        //System.out.println("Connection closed in pdfutil");
        }
    }	
	
	
	@RequestMapping(value ="/workmenEmployedByContractorFormXIIIExcelReport", method = RequestMethod.POST)
	public void workmenEmployedByContractorFormXIIIExcelReport(HttpServletRequest request, HttpServletResponse response,@RequestBody String jSonString) {
        Connection con = null;              
        try {
        	JsonParser jsonParser = new JsonParser();
    		JsonObject jo = (JsonObject)jsonParser.parse(jSonString); 
    		System.out.println(jSonString+"::jSonString");    		
        	byte[] outputByte = new byte[4096];         	
            con = getConnection();             
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("customerId", jo.get("customerId").getAsString());
            params.put("companyId", jo.get("companyId").getAsString());
            
            if(jo.get("vendorId") != null &&  !jo.get("vendorId").isJsonNull() && !(jo.get("vendorId").getAsString().equalsIgnoreCase("null")) && !(jo.get("vendorId").getAsString().equalsIgnoreCase("null")) && jo.get("vendorId").getAsInt() > 0){
            	params.put("vendorId", jo.get("vendorId").getAsString());
            }else{
            	params.put("vendorId", "-1");
            }
            
            if(jo.get("departmentId") != null &&  !jo.get("departmentId").isJsonNull() && !(jo.get("departmentId").getAsString().equalsIgnoreCase("null")) && !(jo.get("departmentId").getAsString().equalsIgnoreCase("null")) && jo.get("departmentId").getAsInt() > 0){
            	params.put("departmentId", jo.get("departmentId").getAsString());
            }else{
            	params.put("departmentId", "-1");
            }  
            
            if(jo.get("workerId") != null &&  !jo.get("workerId").isJsonNull() && !(jo.get("workerId").getAsString().equalsIgnoreCase("null")) && !(jo.get("workerId").getAsString().equalsIgnoreCase("null")) && jo.get("workerId").getAsInt() > 0){
            	params.put("workerId", jo.get("workerId").getAsString());
            }else{
            	params.put("workerId", "-1");
            }                       
  
            if(jo.get("workerCode") != null &&  !jo.get("workerCode").isJsonNull() && !(jo.get("workerCode").getAsString().equalsIgnoreCase("null")) && !(jo.get("workerCode").toString().equalsIgnoreCase("null")) ){
            	params.put("workerCode", jo.get("workerCode").getAsString());
            }else{
            	params.put("workerCode", "-1");
            }
            if(jo.get("workerName") != null &&  !jo.get("workerName").isJsonNull() && !(jo.get("workerName").getAsString().equalsIgnoreCase("null")) && !(jo.get("workerName").toString().equalsIgnoreCase("null"))){
            	params.put("workerName", jo.get("workerName").getAsString());
            }else{
            	params.put("workerName", "");
            }
            
			if(jo.get("fromdate") != null && jo.get("todate") != null &&  !jo.get("fromdate").isJsonNull() && !jo.get("todate").isJsonNull() ){				
				params.put("JoiningDateFrom", jo.get("fromdate").getAsString());
	            params.put("JoiningDateTo", jo.get("todate").getAsString());
			}
			
			if(jo.get("status") != null && jo.get("status") != null &&  !jo.get("status").isJsonNull() && !jo.get("status").getAsString().isEmpty()){				
				params.put("status", jo.get("status").getAsString());	          
			}else{
				params.put("status", "-1");
			}
			
			if(jo.get("locationId") != null &&  !jo.get("locationId").isJsonNull() && !(jo.get("locationId").getAsString().equalsIgnoreCase("null")) && !(jo.get("locationId").toString().equalsIgnoreCase("null")) && jo.get("locationId").getAsInt() > 0){
            	params.put("locationId", jo.get("locationId").getAsString());
            }else{
            	params.put("locationId", "-1");
            } 
            
			if(jo.get("locationName") != null && !jo.get("locationName").isJsonNull() && !(jo.get("locationName").getAsString().equalsIgnoreCase("null")) && !(jo.get("locationName").toString().equalsIgnoreCase("null"))  && !(jo.get("locationName").toString().isEmpty())  ){
            	params.put("locationName",jo.get("locationName").getAsString());
			}else{
				params.put("locationName","");
			}
           
            if(jo.get("plantId") != null &&  !jo.get("plantId").isJsonNull() && !(jo.get("plantId").getAsString().equalsIgnoreCase("null")) && !(jo.get("plantId").toString().equalsIgnoreCase("null")) && jo.get("plantId").getAsInt() > 0){
            	params.put("plantId", jo.get("plantId").getAsString());
            }else{
            	params.put("plantId", "-1");
            }
            
            if(jo.get("plantName") != null && !jo.get("plantName").isJsonNull() && !(jo.get("plantName").getAsString().equalsIgnoreCase("null")) && !(jo.get("plantName").toString().equalsIgnoreCase("null"))  && !(jo.get("plantName").toString().isEmpty())  ){
            	params.put("plantName",jo.get("plantName").getAsString());
			}else{
				params.put("plantName","");
			}
         
            if(jo.get("sectionId") != null &&  !jo.get("sectionId").isJsonNull() && !(jo.get("sectionId").getAsString().equalsIgnoreCase("null")) && !(jo.get("sectionId").toString().equalsIgnoreCase("null")) && jo.get("sectionId").getAsInt() > 0){
            	params.put("sectionId", jo.get("sectionId").getAsString());
            }else{
            	params.put("sectionId", "-1");
            } 
            
            if(jo.get("workAreaId") != null &&  !jo.get("workAreaId").isJsonNull() && !(jo.get("workAreaId").getAsString().equalsIgnoreCase("null")) && !(jo.get("workAreaId").toString().equalsIgnoreCase("null")) && jo.get("workAreaId").getAsInt() > 0){
            	params.put("workAreaId", jo.get("workAreaId").getAsString());
            }else{
            	params.put("workAreaId", "-1");
            } 
            
            if(jo.get("workSkill") != null &&  !jo.get("workSkill").isJsonNull() && !(jo.get("workSkill").getAsString().equalsIgnoreCase("null")) && !(jo.get("workSkill").toString().equalsIgnoreCase("null"))){
            	params.put("workSkill", jo.get("workSkill").getAsString());
            }else{
            	params.put("workSkill", "-1");
            } 
            
            if(jo.get("shift") != null &&  !jo.get("shift").isJsonNull() && !(jo.get("shift").getAsString().equalsIgnoreCase("null")) && !(jo.get("shift").toString().equalsIgnoreCase("null"))){
            	params.put("shift", jo.get("shift").getAsString());
            }else{
            	params.put("shift", "-1");
            } 
			
			params.forEach((key,value) -> System.out.println(key+" :1: "+value+" :: "+jo.get("reportName").getAsString()));
			JasperReport jasperReport = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/reports/"+jo.get("reportName").getAsString()+".jrxml");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, con);
            ByteArrayOutputStream  outputStream = new ByteArrayOutputStream();
            
            JRXlsxExporter xlsExporter = new JRXlsxExporter();
            xlsExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
            xlsExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputStream);
            xlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            xlsExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
            xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            xlsExporter.exportReport();
            OutputStream  ouputStream = response.getOutputStream();
            outputByte = outputStream.toByteArray();              
            ouputStream.write(outputByte);          
            ouputStream.flush();
            ouputStream.close();
            //System.out.println("completed");   
        } catch (Exception e) {
            //System.out.println("IN CATCH PDF UTIL");
            log.error("Error Occured ",e);
        } finally {
            if (con != null) {
                try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					log.error("Error Occured ",e);
				}
            }
        //System.out.println("Connection closed in pdfutil");
        }
    }	
	
	@RequestMapping(value ="/overTimeReport", method = RequestMethod.POST)
	public void overTimeReport(HttpServletRequest request, HttpServletResponse response,@RequestBody String jSonString) {
        Connection con = null;              
        try {
        	JsonParser jsonParser = new JsonParser();
    		JsonObject jo = (JsonObject)jsonParser.parse(jSonString); 
    		System.out.println(jSonString+"::jSonString");    		
        	byte[] outputByte = new byte[4096];         	
            con = getConnection();             
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("customerId", jo.get("customerId").getAsString());
            params.put("companyId", jo.get("companyId").getAsString());
            
            if(jo.get("companyName") != null && !jo.get("companyName").isJsonNull() && !(jo.get("companyName").getAsString().equalsIgnoreCase("null")) && !(jo.get("companyName").toString().equalsIgnoreCase("null"))  && !(jo.get("companyName").toString().isEmpty())  ){
            	params.put("companyName",jo.get("companyName").getAsString());
			}else{
				params.put("companyName","");
			}
           
            
            if(jo.get("status") != null && !jo.get("status").isJsonNull() && !(jo.get("status").getAsString().equalsIgnoreCase("null")) && !(jo.get("status").toString().equalsIgnoreCase("null"))  && !(jo.get("status").toString().isEmpty())  ){
            	params.put("status",jo.get("status").getAsString());
			}else{
				params.put("status","-1");
			}
            
            if(jo.get("schemaName") != null && !jo.get("schemaName").isJsonNull() && !(jo.get("schemaName").getAsString().equalsIgnoreCase("null")) && !(jo.get("schemaName").toString().equalsIgnoreCase("null"))  && !(jo.get("schemaName").toString().isEmpty())  ){
            	params.put("schemaName",jo.get("schemaName").getAsString());
            	 System.out.println(jo.get("schemaName").getAsString()+":::"+dbSchemaName);
			}else{
				params.put("schemaName",dbSchemaName);
				 System.out.println(jo.get("schemaName").getAsString()+"::0000:"+dbSchemaName);
			}         
            if(jo.get("vendorId") != null &&  !jo.get("vendorId").isJsonNull() && !(jo.get("vendorId").getAsString().equalsIgnoreCase("null")) && !(jo.get("vendorId").getAsString().equalsIgnoreCase("null")) && jo.get("vendorId").getAsInt() > 0){
            	params.put("vendorId", jo.get("vendorId").getAsString());
            }else{
            	params.put("vendorId", "-1");
            }
            
            if(jo.get("workerId") != null &&  !jo.get("workerId").isJsonNull() && !(jo.get("workerId").getAsString().equalsIgnoreCase("null")) && !(jo.get("workerId").toString().equalsIgnoreCase("null")) && jo.get("workerId").getAsInt() > 0){
            	params.put("workerId", jo.get("workerId").getAsString());
            }else{
            	params.put("workerId", "-1");
            }                       
  
            if(jo.get("workerCode") != null &&  !jo.get("workerCode").isJsonNull() && !(jo.get("workerCode").getAsString().equalsIgnoreCase("null")) && !(jo.get("workerCode").toString().equalsIgnoreCase("null")) ){
            	params.put("workerCode", jo.get("workerCode").getAsString());
            }else{
            	params.put("workerCode", "-1");
            }
            if(jo.get("workerName") != null &&  !jo.get("workerName").isJsonNull() && !(jo.get("workerName").getAsString().equalsIgnoreCase("null")) && !(jo.get("workerName").toString().equalsIgnoreCase("null"))){
            	params.put("workerName", jo.get("workerName").getAsString());
            }else{
            	params.put("workerName", "");
            }
            
            if(jo.get("departmentId") != null &&  !jo.get("departmentId").isJsonNull() && !(jo.get("departmentId").getAsString().equalsIgnoreCase("null")) && !(jo.get("departmentId").toString().equalsIgnoreCase("null")) && jo.get("departmentId").getAsInt() > 0){
            	params.put("departmentId", jo.get("departmentId").getAsString());
            }else{
            	params.put("departmentId", "-1");
            } 
            
			if(jo.get("fromdate") != null && jo.get("todate") != null &&  !jo.get("fromdate").isJsonNull() && !jo.get("todate").isJsonNull() ){				
				params.put("fromdate", jo.get("fromdate").getAsString());
	            params.put("todate", jo.get("todate").getAsString());
			}	
			
			if(jo.get("locationId") != null &&  !jo.get("locationId").isJsonNull() && !(jo.get("locationId").getAsString().equalsIgnoreCase("null")) && !(jo.get("locationId").toString().equalsIgnoreCase("null")) && jo.get("locationId").getAsInt() > 0){
            	params.put("locationId", jo.get("locationId").getAsString());
            }else{
            	params.put("locationId", "-1");
            } 
            
			if(jo.get("locationName") != null && !jo.get("locationName").isJsonNull() && !(jo.get("locationName").getAsString().equalsIgnoreCase("null")) && !(jo.get("locationName").toString().equalsIgnoreCase("null"))  && !(jo.get("locationName").toString().isEmpty())  ){
            	params.put("locationName",jo.get("locationName").getAsString());
			}else{
				params.put("locationName","");
			}
           
            if(jo.get("plantId") != null &&  !jo.get("plantId").isJsonNull() && !(jo.get("plantId").getAsString().equalsIgnoreCase("null")) && !(jo.get("plantId").toString().equalsIgnoreCase("null")) && jo.get("plantId").getAsInt() > 0){
            	params.put("plantId", jo.get("plantId").getAsString());
            }else{
            	params.put("plantId", "-1");
            }
            
            if(jo.get("plantName") != null && !jo.get("plantName").isJsonNull() && !(jo.get("plantName").getAsString().equalsIgnoreCase("null")) && !(jo.get("plantName").toString().equalsIgnoreCase("null"))  && !(jo.get("plantName").toString().isEmpty())  ){
            	params.put("plantName",jo.get("plantName").getAsString());
			}else{
				params.put("plantName","");
			}
         
            if(jo.get("sectionId") != null &&  !jo.get("sectionId").isJsonNull() && !(jo.get("sectionId").getAsString().equalsIgnoreCase("null")) && !(jo.get("sectionId").toString().equalsIgnoreCase("null")) && jo.get("sectionId").getAsInt() > 0){
            	params.put("sectionId", jo.get("sectionId").getAsString());
            }else{
            	params.put("sectionId", "-1");
            } 
            
            if(jo.get("workAreaId") != null &&  !jo.get("workAreaId").isJsonNull() && !(jo.get("workAreaId").getAsString().equalsIgnoreCase("null")) && !(jo.get("workAreaId").toString().equalsIgnoreCase("null")) && jo.get("workAreaId").getAsInt() > 0){
            	params.put("workAreaId", jo.get("workAreaId").getAsString());
            }else{
            	params.put("workAreaId", "-1");
            } 
            
            if(jo.get("workSkill") != null &&  !jo.get("workSkill").isJsonNull() && !(jo.get("workSkill").getAsString().equalsIgnoreCase("null")) && !(jo.get("workSkill").toString().equalsIgnoreCase("null"))){
            	params.put("workSkill", jo.get("workSkill").getAsString());
            }else{
            	params.put("workSkill", "-1");
            } 
            
            if(jo.get("shift") != null &&  !jo.get("shift").isJsonNull() && !(jo.get("shift").getAsString().equalsIgnoreCase("null")) && !(jo.get("shift").toString().equalsIgnoreCase("null"))){
            	params.put("shift", jo.get("shift").getAsString());
            }else{
            	params.put("shift", "-1");
            } 
			
            if(jo.get("departmentName") != null &&  !jo.get("departmentName").isJsonNull() && !(jo.get("departmentName").getAsString().equalsIgnoreCase("null")) && !(jo.get("departmentName").toString().equalsIgnoreCase("null"))){
            	params.put("departmentName", jo.get("departmentName").getAsString());
            }else{
            	params.put("departmentName", "ALL");
            } 
			params.forEach((key,value) -> System.out.println(key+" :: "+value+" :: "+jo.get("reportName").getAsString()));
			/*File file = new File(request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/reports/"+jo.get("reportName").getAsString()+".jrxml");
			System.out.println(file.exists());*/
			
            JasperReport jasperReport = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/reports/"+jo.get("reportName").getAsString()+".jrxml");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, con);
            ByteArrayOutputStream  outputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);                  
            OutputStream  ouputStream = response.getOutputStream();
            outputByte = outputStream.toByteArray();              
            ouputStream.write(outputByte);          
            ouputStream.flush();
            ouputStream.close();
            
            //System.out.println("completed");   
        } catch (Exception e) {
            //System.out.println("IN CATCH PDF UTIL");
            log.error("Error Occured ",e);
        } finally {
            if (con != null) {
                try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					log.error("Error Occured ",e);
				}
            }
        //System.out.println("Connection closed in pdfutil");
        }
    }	
	
	
	
	@RequestMapping(value ="/downloadExcelBankDetailsReport", method = RequestMethod.POST)
	public  void downloadExcelBankDetailsReport(HttpServletRequest request, HttpServletResponse response,@RequestBody String jSonString) {
		 Connection con = null;              
	        try {
	        	JsonParser jsonParser = new JsonParser();
	    		JsonObject jo = (JsonObject)jsonParser.parse(jSonString); 
	    		System.out.println(jSonString+"::jSonString");	    		
	        	byte[] outputByte = new byte[4096]; 
	        	response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        	response.setHeader("Content-disposition", "attachment; filename=AttendanceReport.xls");
	            con = getConnection();           
	          
				con = getConnection();             
		        Map<String, Object> params = new HashMap<String, Object>();
		        params.put("customerId", jo.get("customerId").getAsString());
		        params.put("companyId", jo.get("companyId").getAsString());
		        
		      
		        if(!(jo.get("vendorId").toString().equalsIgnoreCase("null")) && !(jo.get("vendorId").getAsString().equalsIgnoreCase("null")) && !(jo.get("vendorId").getAsString().trim().equalsIgnoreCase("select")) && !(jo.get("vendorId").getAsString().trim().isEmpty() )){
					params.put("vendorId", jo.get("vendorId").getAsString());
				}else{
					params.put("vendorId", "-1");
				}
		        
		        if(!(jo.get("bankName").toString().equalsIgnoreCase("null")) && !(jo.get("bankName").getAsString().equalsIgnoreCase("null")) && !(jo.get("bankName").getAsString().trim().equalsIgnoreCase("select")) && !(jo.get("bankName").getAsString().trim().isEmpty() )){
		        	params.put("bankName", jo.get("bankName").getAsString() );
				}else{
					params.put("bankName", "");
				}
		        
		        if(!(jo.get("searchBy").toString().equalsIgnoreCase("null")) && !(jo.get("searchBy").getAsString().equalsIgnoreCase("null")) && !(jo.get("searchBy").getAsString().trim().equalsIgnoreCase("select")) && !(jo.get("searchBy").getAsString().trim().isEmpty() )){
		        	params.put("searchBy", jo.get("searchBy").getAsString() );
				}else{
					params.put("searchBy", "");
				}		     
		        
		        
		        if( !jo.get("employeeCode").isJsonNull() && !(jo.get("employeeCode").getAsString().equalsIgnoreCase("null")) && !(jo.get("employeeCode").getAsString().isEmpty())){
	            	params.put("employeeCode", jo.get("employeeCode").getAsString());
				}else{
					params.put("employeeCode", "-1");
				}
				if( !jo.get("employeeName").isJsonNull() && !(jo.get("employeeName").toString().equalsIgnoreCase("null")) && !(jo.get("employeeName").getAsString().equalsIgnoreCase("null")) && !(jo.get("employeeCode").getAsString().isEmpty())){
					params.put("employeeName", jo.get("employeeName").getAsString());
				}else{
					params.put("employeeName", "");
				}
				

				JasperReport jasperReport = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/reports/WorkerBankDetailsExcelReport.jrxml");
	            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, con);
	            ByteArrayOutputStream  outputStream = new ByteArrayOutputStream();
	            
	            JRXlsxExporter xlsExporter = new JRXlsxExporter();
	            xlsExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
	            xlsExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputStream);
	            xlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
	            xlsExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
	            xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
	            xlsExporter.exportReport();
	                         
	            OutputStream  ouputStream = response.getOutputStream();
	            outputByte = outputStream.toByteArray();              
	            ouputStream.write(outputByte);          
	            ouputStream.flush();
	            ouputStream.close();
	            
	            
	        } catch (Exception e) {
	        	e.printStackTrace();
	            //System.out.println("IN CATCH PDF UTIL");
	            log.error("Error Occured ",e);
	        } finally {
	            if (con != null) {
	                try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						log.error("Error Occured ",e);
					}
	            }
	        //System.out.println("Connection closed in pdfutil");
	        }
	    }	
	
	@RequestMapping(value ="/jobAllocationReport", method = RequestMethod.POST)
	public void jobAllocationReport(HttpServletRequest request, HttpServletResponse response,@RequestBody String jSonString) {
        System.out.println("Entered in Job Allocation report :  ");
		Connection con = null;              
        try {
        	JsonParser jsonParser = new JsonParser();
    		JsonObject jo = (JsonObject)jsonParser.parse(jSonString); 
    		System.out.println(jSonString+"::jSonString");    		
        	byte[] outputByte = new byte[4096];         	
            con = getConnection();             
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("customerId", jo.get("customerId").getAsString());
            params.put("companyId", jo.get("companyId").getAsString());
            params.put("location", jo.get("locationName").getAsString());
            params.put("plant", jo.get("plantName").getAsString());
            params.put("companyName", jo.get("companyName").getAsString());
            
            if(jo.get("status") != null && !jo.get("status").isJsonNull() && !(jo.get("status").getAsString().equalsIgnoreCase("null")) && !(jo.get("status").toString().equalsIgnoreCase("null"))  && !(jo.get("status").toString().isEmpty())  ){
            	params.put("status",jo.get("status").getAsString());
			}else{
				params.put("status","-1");
			}
            
            if(jo.get("jobType") != null && !jo.get("jobType").isJsonNull() && !(jo.get("jobType").getAsString().equalsIgnoreCase("null")) && !(jo.get("jobType").toString().equalsIgnoreCase("null"))  && !(jo.get("jobType").toString().isEmpty())  ){
            	params.put("JOBTYPE",jo.get("jobType").getAsString());
			}else{
				params.put("JOBTYPE","-1");
			}

            if(jo.get("schemaName") != null && !jo.get("schemaName").isJsonNull() && !(jo.get("schemaName").getAsString().equalsIgnoreCase("null")) && !(jo.get("schemaName").toString().equalsIgnoreCase("null"))  && !(jo.get("schemaName").toString().isEmpty())  ){
            	params.put("schemaName",jo.get("schemaName").getAsString());
            	// System.out.println(jo.get("schemaName").getAsString()+":::"+dbSchemaName);
			}else{
				params.put("schemaName",dbSchemaName);
				// System.out.println(jo.get("schemaName").getAsString()+"::0000:"+dbSchemaName);
			} 
            
            if(jo.get("vendorId") != null &&  !jo.get("vendorId").isJsonNull() && !(jo.get("vendorId").getAsString().equalsIgnoreCase("null")) && !(jo.get("vendorId").getAsString().equalsIgnoreCase("null")) && jo.get("vendorId").getAsInt() > 0){
            	params.put("vendorId", jo.get("vendorId").getAsString());
            }else{
            	params.put("vendorId", "-1");
            }
           
            if(jo.get("workerCode") != null &&  !jo.get("workerCode").isJsonNull() && !(jo.get("workerCode").getAsString().equalsIgnoreCase("null")) && !(jo.get("workerCode").toString().equalsIgnoreCase("null")) ){
            	params.put("workerCode", jo.get("workerCode").getAsString());
            }else{
            	params.put("workerCode", "");
            }
            
            if(jo.get("workerName") != null &&  !jo.get("workerName").isJsonNull() && !(jo.get("workerName").getAsString().equalsIgnoreCase("null")) && !(jo.get("workerName").toString().equalsIgnoreCase("null"))){
            	params.put("workerName", jo.get("workerName").getAsString());
            }else{
            	params.put("workerName", "");
            }
            
          
            if(jo.get("locationId") != null &&  !jo.get("locationId").isJsonNull() && !(jo.get("locationId").getAsString().equalsIgnoreCase("null")) && !(jo.get("locationId").toString().equalsIgnoreCase("null")) && jo.get("locationId").getAsInt() > 0){
            	params.put("locationId", jo.get("locationId").getAsString());
            }else{
            	params.put("locationId", "-1");
            } 
            
           
            if(jo.get("plantId") != null &&  !jo.get("plantId").isJsonNull() && !(jo.get("plantId").getAsString().equalsIgnoreCase("null")) && !(jo.get("plantId").toString().equalsIgnoreCase("null")) && jo.get("plantId").getAsInt() > 0){
            	params.put("plantId", jo.get("plantId").getAsString());
            }else{
            	params.put("plantId", "-1");
            } 
            
          
            if(jo.get("departmentId") != null &&  !jo.get("departmentId").isJsonNull() && !(jo.get("departmentId").getAsString().equalsIgnoreCase("null")) && !(jo.get("departmentId").toString().equalsIgnoreCase("null")) && jo.get("departmentId").getAsInt() > 0){
            	params.put("departmentId", jo.get("departmentId").getAsString());
            }else{
            	params.put("departmentId", "-1");
            } 
         
            if(jo.get("sectionId") != null &&  !jo.get("sectionId").isJsonNull() && !(jo.get("sectionId").getAsString().equalsIgnoreCase("null")) && !(jo.get("sectionId").toString().equalsIgnoreCase("null")) && jo.get("sectionId").getAsInt() > 0){
            	params.put("sectionId", jo.get("sectionId").getAsString());
            }else{
            	params.put("sectionId", "-1");
            } 
            
            if(jo.get("workAreaId") != null &&  !jo.get("workAreaId").isJsonNull() && !(jo.get("workAreaId").getAsString().equalsIgnoreCase("null")) && !(jo.get("workAreaId").toString().equalsIgnoreCase("null")) && jo.get("workAreaId").getAsInt() > 0){
            	params.put("workAreaId", jo.get("workAreaId").getAsString());
            }else{
            	params.put("workAreaId", "-1");
            } 
            
            if(jo.get("workSkill") != null &&  !jo.get("workSkill").isJsonNull() && !(jo.get("workSkill").getAsString().equalsIgnoreCase("null")) && !(jo.get("workSkill").toString().equalsIgnoreCase("null"))){
            	params.put("workSkill", jo.get("workSkill").getAsString());
            }else{
            	params.put("workSkill", "-1");
            } 
            
            if(jo.get("shift") != null &&  !jo.get("shift").isJsonNull() && !(jo.get("shift").getAsString().equalsIgnoreCase("null")) && !(jo.get("shift").toString().equalsIgnoreCase("null"))){
            	params.put("Shift", jo.get("shift").getAsString());
            }else{
            	params.put("Shift", "-1");
            } 

			if(jo.get("year") != null && jo.get("month") != null  && jo.get("year").getAsInt() > 0  && jo.get("month").getAsInt() > 0  ){
				//params.put("year", jo.get("year").getAsString());
	           // params.put("month", jo.get("month").getAsString());
	            params.put("fromDate", jo.get("year").getAsString()+"-"+jo.get("month").getAsString()+"-01");
	            Calendar calendar = Calendar.getInstance();
	            calendar.set(Calendar.YEAR,jo.get("year").getAsInt());
	            calendar.set(Calendar.MONTH,jo.get("month").getAsInt()-1);
	            int daysQty =calendar.getActualMaximum(Calendar.DATE);
	            params.put("toDate", jo.get("year").getAsString()+"-"+jo.get("month").getAsString()+"-"+daysQty);
	            //System.out.println(calendar.getTime());
			}else if(jo.get("fromDate") != null && jo.get("toDate") != null &&  !jo.get("fromDate").isJsonNull() && !jo.get("toDate").isJsonNull() ){				
				params.put("fromDate", jo.get("fromDate").getAsString());
	            params.put("toDate", jo.get("toDate").getAsString());
	            //System.out.println("FromDate");
			}
			
			
			System.out.println("JobAllocationReport::"+params.toString());
			
            JasperReport jasperReport = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/reports/JobAllocationReport.jrxml");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, con);
            ByteArrayOutputStream  outputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);                  
            OutputStream  ouputStream = response.getOutputStream();
            outputByte = outputStream.toByteArray();              
            ouputStream.write(outputByte);          
            ouputStream.flush();
            ouputStream.close();
            
            System.out.println("completed"+ouputStream);   
        } catch (Exception e) {
            //System.out.println("IN CATCH PDF UTIL");
            log.error("Error Occured ",e);
        } finally {
            if (con != null) {
                try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					log.error("Error Occured ",e);
				}
            }
        }
    }	
	
	@RequestMapping(value ="/downloadPDFManpowerReport", method = RequestMethod.POST)
	public void downloadPDFManpowerReport(HttpServletRequest request, HttpServletResponse response,@RequestBody String jSonString) {
        System.out.println("Entered in downloadPDFManpowerReport :  ");
		Connection con = null;              
        try {
        	JsonParser jsonParser = new JsonParser();
    		JsonObject jo = (JsonObject)jsonParser.parse(jSonString); 
    		System.out.println(jSonString+"::jSonString");    		
        	byte[] outputByte = new byte[4096];         	
            con = getConnection();             
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("customerId", jo.get("customerId").getAsString());
            params.put("companyId", jo.get("companyId").getAsString());
            params.put("companyName", jo.get("companyName").getAsString());
            
           

            if(jo.get("schemaName") != null && !jo.get("schemaName").isJsonNull() && !(jo.get("schemaName").getAsString().equalsIgnoreCase("null")) && !(jo.get("schemaName").toString().equalsIgnoreCase("null"))  && !(jo.get("schemaName").toString().isEmpty())  ){
            	 params.put("schemaName",jo.get("schemaName").getAsString());
            	 System.out.println(jo.get("schemaName").getAsString()+":::"+dbSchemaName);
			}else{
				params.put("schemaName",dbSchemaName);
				
			} 
            
            if(jo.get("vendorId") != null &&  !jo.get("vendorId").isJsonNull() && !(jo.get("vendorId").getAsString().equalsIgnoreCase("null")) && !(jo.get("vendorId").getAsString().equalsIgnoreCase("null")) && jo.get("vendorId").getAsInt() > 0){
            	params.put("vendorId", jo.get("vendorId").getAsString());
            }else{
            	params.put("vendorId", "-1");
            }
           
            
          
            if(jo.get("locationId") != null &&  !jo.get("locationId").isJsonNull() && !(jo.get("locationId").getAsString().equalsIgnoreCase("null")) && !(jo.get("locationId").toString().equalsIgnoreCase("null")) && jo.get("locationId").getAsInt() > 0){
            	params.put("locationId", jo.get("locationId").getAsString());
            }else{
            	params.put("locationId", "-1");
            } 
            
            if(jo.get("locationName") != null &&  !jo.get("locationName").isJsonNull() && !(jo.get("locationName").getAsString().equalsIgnoreCase("null")) && !(jo.get("locationName").toString().equalsIgnoreCase("null"))){
            	params.put("locationName", jo.get("locationName").getAsString());
            }else{
            	params.put("locationName", "-1");
            }
           
            if(jo.get("plantId") != null &&  !jo.get("plantId").isJsonNull() && !(jo.get("plantId").getAsString().equalsIgnoreCase("null")) && !(jo.get("plantId").toString().equalsIgnoreCase("null")) && jo.get("plantId").getAsInt() > 0){
            	params.put("plantId", jo.get("plantId").getAsString());
            }else{
            	params.put("plantId", "-1");
            } 
            
            if(jo.get("plantName") != null &&  !jo.get("plantName").isJsonNull() && !(jo.get("plantName").getAsString().equalsIgnoreCase("null")) && !(jo.get("plantName").toString().equalsIgnoreCase("null"))){
            	params.put("plantName", jo.get("plantName").getAsString());
            }else{
            	params.put("plantName", "-1");
            }
          
            if(jo.get("departmentId") != null &&  !jo.get("departmentId").isJsonNull() && !(jo.get("departmentId").getAsString().equalsIgnoreCase("null")) && !(jo.get("departmentId").toString().equalsIgnoreCase("null")) && jo.get("departmentId").getAsInt() > 0){
            	params.put("departmentId", jo.get("departmentId").getAsString());
            }else{
            	params.put("departmentId", "-1");
            } 
            
            if(jo.get("departmentName") != null &&  !jo.get("departmentName").isJsonNull() && !(jo.get("departmentName").getAsString().equalsIgnoreCase("null")) && !(jo.get("departmentName").toString().equalsIgnoreCase("null"))){
            	params.put("departmentName", jo.get("departmentName").getAsString());
            }else{
            	params.put("departmentName", "ALL");
            }
         
            
            if(jo.get("shift") != null &&  !jo.get("shift").isJsonNull() && !(jo.get("shift").getAsString().equalsIgnoreCase("null")) && !(jo.get("shift").toString().equalsIgnoreCase("null"))){
            	params.put("shift", jo.get("shift").getAsString());
            }else{
            	params.put("shift", "-1");
            } 
            
            if(jo.get("businessDate") != null &&  !jo.get("businessDate").isJsonNull() ){				
				params.put("businessDate", jo.get("businessDate").getAsString());
	            //System.out.println("FromDate");
			}

            if(jo.get("fromDate") != null && jo.get("toDate") != null &&  !jo.get("fromDate").isJsonNull() && !jo.get("toDate").isJsonNull() ){				
				params.put("fromDate", jo.get("fromDate").getAsString());
	            params.put("toDate", jo.get("toDate").getAsString());
	            //System.out.println("FromDate");
			}
			
			
			System.out.println(params.toString());
            JasperReport jasperReport = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/reports/"+jo.get("reportName").getAsString()+".jrxml");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, con);
            ByteArrayOutputStream  outputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);                  
            OutputStream  ouputStream = response.getOutputStream();
            outputByte = outputStream.toByteArray();              
            ouputStream.write(outputByte);          
            ouputStream.flush();
            ouputStream.close();
            
            System.out.println("completed"+ouputStream);   
        } catch (Exception e) {
            //System.out.println("IN CATCH PDF UTIL");
            log.error("Error Occured ",e);
        } finally {
            if (con != null) {
                try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					log.error("Error Occured ",e);
				}
            }
        }
    }	
	
	@RequestMapping(value ="/downloadExcelManpowerReport", method = RequestMethod.POST)
	public  void downloadExcelManpowerReport(HttpServletRequest request, HttpServletResponse response,@RequestBody String jSonString) {
		 Connection con = null;              
	        try {
	        	JsonParser jsonParser = new JsonParser();
	    		JsonObject jo = (JsonObject)jsonParser.parse(jSonString); 
	    		System.out.println(jSonString+"::jSonString");	    		
	        	byte[] outputByte = new byte[4096]; 
	        	
	        	response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        	response.setHeader("Content-disposition", "attachment; filename=DailyContractManpowerReport.xls");
	        	
	            con = getConnection();             
	            Map<String, Object> params = new HashMap<String, Object>();
	            params.put("customerId", jo.get("customerId").getAsString());
	            params.put("companyId", jo.get("companyId").getAsString());
	            params.put("companyName", jo.get("companyName").getAsString());
	            

	            if(jo.get("schemaName") != null && !jo.get("schemaName").isJsonNull() && !(jo.get("schemaName").getAsString().equalsIgnoreCase("null")) && !(jo.get("schemaName").toString().equalsIgnoreCase("null"))  && !(jo.get("schemaName").toString().isEmpty())  ){
	            	//params.put("schemaName",jo.get("schemaName").getAsString());
	            	 System.out.println(jo.get("schemaName").getAsString()+":::"+dbSchemaName);
				}else{
					params.put("schemaName",dbSchemaName);
					// System.out.println(jo.get("schemaName").getAsString()+"::0000:"+dbSchemaName);
				} 
	            
	            if(jo.get("vendorId") != null &&  !jo.get("vendorId").isJsonNull() && !(jo.get("vendorId").getAsString().equalsIgnoreCase("null")) && !(jo.get("vendorId").getAsString().equalsIgnoreCase("null")) && jo.get("vendorId").getAsInt() > 0){
	            	params.put("vendorId", jo.get("vendorId").getAsString());
	            }else{
	            	params.put("vendorId", "-1");
	            }
	           
	            if(jo.get("locationId") != null &&  !jo.get("locationId").isJsonNull() && !(jo.get("locationId").getAsString().equalsIgnoreCase("null")) && !(jo.get("locationId").toString().equalsIgnoreCase("null")) && jo.get("locationId").getAsInt() > 0){
	            	params.put("locationId", jo.get("locationId").getAsString());
	            }else{
	            	params.put("locationId", "-1");
	            } 
	            
	            if(jo.get("locationName") != null &&  !jo.get("locationName").isJsonNull() && !(jo.get("locationName").getAsString().equalsIgnoreCase("null")) && !(jo.get("locationName").toString().equalsIgnoreCase("null"))){
	            	params.put("locationName", jo.get("locationName").getAsString());
	            }else{
	            	params.put("locationName", "-1");
	            }
	           
	            if(jo.get("plantId") != null &&  !jo.get("plantId").isJsonNull() && !(jo.get("plantId").getAsString().equalsIgnoreCase("null")) && !(jo.get("plantId").toString().equalsIgnoreCase("null")) && jo.get("plantId").getAsInt() > 0){
	            	params.put("plantId", jo.get("plantId").getAsString());
	            }else{
	            	params.put("plantId", "-1");
	            } 
	            
	            if(jo.get("plantName") != null &&  !jo.get("plantName").isJsonNull() && !(jo.get("plantName").getAsString().equalsIgnoreCase("null")) && !(jo.get("plantName").toString().equalsIgnoreCase("null"))){
	            	params.put("plantName", jo.get("plantName").getAsString());
	            }else{
	            	params.put("plantName", "-1");
	            }
	          
	            if(jo.get("departmentId") != null &&  !jo.get("departmentId").isJsonNull() && !(jo.get("departmentId").getAsString().equalsIgnoreCase("null")) && !(jo.get("departmentId").toString().equalsIgnoreCase("null")) && jo.get("departmentId").getAsInt() > 0){
	            	params.put("departmentId", jo.get("departmentId").getAsString());
	            }else{
	            	params.put("departmentId", "-1");
	            } 
	            
	            if(jo.get("departmentName") != null &&  !jo.get("departmentName").isJsonNull() && !(jo.get("departmentName").getAsString().equalsIgnoreCase("null")) && !(jo.get("departmentName").toString().equalsIgnoreCase("null"))){
	            	params.put("departmentName", jo.get("departmentName").getAsString());
	            }else{
	            	params.put("departmentName", "ALL");
	            }
	         
	            if(jo.get("shift") != null &&  !jo.get("shift").isJsonNull() && !(jo.get("shift").getAsString().equalsIgnoreCase("null")) && !(jo.get("shift").toString().equalsIgnoreCase("null"))){
	            	params.put("shift", jo.get("shift").getAsString());
	            }else{
	            	params.put("shift", "-1");
	            } 
	            
	            if(jo.get("businessDate") != null &&  !jo.get("businessDate").isJsonNull() ){				
					params.put("businessDate", jo.get("businessDate").getAsString());
		            //System.out.println("FromDate");
				}
	            
	            
	            /*if( !jo.get("fromDate").isJsonNull() && !jo.get("fromDate").isJsonNull() && !(jo.get("year").getAsString().equalsIgnoreCase("null")) && !(jo.get("month").getAsString().equalsIgnoreCase("null")) && jo.get("year").getAsInt() > 0  && jo.get("month").getAsInt() > 0  ){
					params.put("year", jo.get("year").getAsString());
		            params.put("month", jo.get("month").getAsString());
		            params.put("fromDate", "01/"+jo.get("month").getAsString()+"/"+jo.get("year").getAsString());
		            Calendar calendar = Calendar.getInstance();
		            calendar.set(Calendar.YEAR,jo.get("year").getAsInt());
		            calendar.set(Calendar.MONTH,jo.get("month").getAsInt()-1);
		            int daysQty =calendar.getActualMaximum(Calendar.DATE);
		            params.put("toDate", daysQty+"/"+jo.get("month").getAsString()+"/"+jo.get("year").getAsString());
		            System.out.println(calendar.getTime());
				}
				if( !jo.get("fromDate").isJsonNull() &&  !jo.get("toDate").isJsonNull() &&  !(jo.get("toDate").toString().equalsIgnoreCase("null")) && !(jo.get("toDate").getAsString().equalsIgnoreCase("null")) && !(jo.get("toDate").toString().trim().isEmpty()) && !(jo.get("fromDate").toString().equalsIgnoreCase("null")) && !(jo.get("fromDate").getAsString().equalsIgnoreCase("null")) && !(jo.get("fromDate").toString().trim().isEmpty()) ){
					params.put("fromDate", jo.get("fromDate").getAsString());
		            params.put("toDate", jo.get("toDate").getAsString());
		            params.put("year", "-1");
		            params.put("month", "-1");
				} */

	            System.out.println(params.toString());
	            
	            JasperReport jasperReport = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/reports/"+jo.get("reportName").getAsString()+".jrxml");
	            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, con);
	            
	            ByteArrayOutputStream  outputStream = new ByteArrayOutputStream();
	            
	            JRXlsxExporter xlsExporter = new JRXlsxExporter();
	            xlsExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
	            xlsExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputStream);
	            xlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
	            xlsExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
	            xlsExporter.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER,Boolean.TRUE);
	            xlsExporter.exportReport();
	           
	            OutputStream  ouputStream = response.getOutputStream();
	            outputByte = outputStream.toByteArray();              
	            ouputStream.write(outputByte);          
	            ouputStream.flush();
	            ouputStream.close();
	            
	            
	        } catch (Exception e) {
	        	e.printStackTrace();
	            //System.out.println("IN CATCH PDF UTIL");
	            log.error("Error Occured ",e);
	        } finally {
	            if (con != null) {
	                try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						log.error("Error Occured ",e);
					}
	            }
	        //System.out.println("Connection closed in pdfutil");
	        }
	    }	
	
		@RequestMapping(value = "/getDepartmentsByLocationAndPlantId.json", method = RequestMethod.POST)
		public  ResponseEntity<Map<String,List>> getDepartmentsByLocationAndPlantId(@RequestBody String jsonDetails) {					
			Map<String,List> masterInfoMap = new HashMap<String,List>();		
			JsonParser jsonParser = new JsonParser();
			System.out.println("jsonDetails::"+jsonDetails);
			JsonObject jo = (JsonObject) jsonParser.parse(jsonDetails);
			List<DepartmentVo> departmentList = new ArrayList();
			try{
				/*DepartmentVo dept = new DepartmentVo();
				dept.setDepartmentId(-1);
				dept.setDepartmentName("ALL");
				departmentList.add(dept);
				System.out.println(departmentList);*/
				List<DepartmentVo> departmentList1 = associatingDepartmentToLocationPlantService.getDeparmentNamesAsDropDown(jo.get("customerId").getAsString(), jo.get("companyId").getAsString(), jo.get("locationId").getAsString(), jo.get("plantId").getAsString(),null);
				departmentList.addAll(departmentList1);
			}catch(Exception e){
				log.error("Error Occured ",e);
			}
			masterInfoMap.put("departmentList",  departmentList);
			return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
		}
}
