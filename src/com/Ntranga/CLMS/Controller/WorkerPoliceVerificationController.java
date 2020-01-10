package com.Ntranga.CLMS.Controller;

import java.io.ByteArrayOutputStream;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Ntranga.CLMS.Service.EmployeeService;
import com.Ntranga.CLMS.Service.SectionService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.Service.WorkAreaService;
import com.Ntranga.CLMS.Service.WorkerJobDetailsService;
import com.Ntranga.CLMS.Service.WorkerMedicalExaminationService;
import com.Ntranga.CLMS.Service.WorkerPoliceVerificationService;
import com.Ntranga.CLMS.Service.WorkerService;
import com.Ntranga.CLMS.vo.AssignShiftsVo;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.PlantVo;
import com.Ntranga.CLMS.vo.SectionDetailsInfoVo;
import com.Ntranga.CLMS.vo.ShiftsDefineVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WorkAreaVo;
import com.Ntranga.CLMS.vo.WorkJobDetailsVo;
import com.Ntranga.CLMS.vo.WorkerDetailsVo;
import com.Ntranga.CLMS.vo.WorkerMedicalExaminationVo;
import com.Ntranga.CLMS.vo.WorkerPoliceVerificationVo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import common.Logger;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@SuppressWarnings({"rawtypes","unchecked"})
@RestController
@RequestMapping("workerPoliceVerificationController")
public class WorkerPoliceVerificationController {

	private static Logger log = Logger.getLogger(WorkerPoliceVerificationController.class);
	
	
	@Autowired
	private WorkerPoliceVerificationService workerPoliceVerificationService;
	
	@Autowired
	private VendorService vendorService;
	
	
	private @Value("#{db['db.driver']}")
	String driverClass;
	private @Value("#{db['db.url']}")
	String url;
	private @Value("#{db['db.username']}")
	String username;
	private @Value("#{db['db.password']}")
	String password;
	
	
	@RequestMapping(value = "/saveOrUpdateWorkerPoliceVerificationDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,Integer>> saveOrUpdateWorkerPoliceVerificationDetails(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile[] files) {
		Map<String,Integer> masterInfoMap = new HashMap<String,Integer>();
		try{
			//System.out.println(verifyVo.toString()+"::::=========");
			Integer PoliceVerificationId = workerPoliceVerificationService.saveOrUpdateWorkerPoliceVerificationDetails(name,files);
			masterInfoMap.put("PoliceVerificationId", PoliceVerificationId); 
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,Integer>>(masterInfoMap,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getWorkerPoliceVerificationDetailsByWorkerId.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getWorkerPoliceVerificationDetailsByWorkerId(@RequestBody String workerId) {					
		Map<String,List> masterInfoMap = new HashMap<String,List>();		
		List<WorkerPoliceVerificationVo> workerPoliceVerificationDetails = new ArrayList<>();
		List<WorkerPoliceVerificationVo> workerAddressDetails = new ArrayList<>();	
		JsonParser jsonParser = new JsonParser();
		System.out.println("workerId=="+workerId);
		JsonObject jo = (JsonObject) jsonParser.parse(workerId);
		try{
		if( jo.get("workerId")!= null && jo.get("workerId").toString() != null && !jo.get("workerId").getAsString().isEmpty() && jo.get("workerId").getAsInt() >0){
			workerPoliceVerificationDetails  = workerPoliceVerificationService.getWorkerPoliceVerificationDetailsByWorkerId(jo.get("workerId").getAsInt());			
		}						
		
		if( jo.get("workerInfoId")!= null && jo.get("workerInfoId").toString() != null && !jo.get("workerInfoId").getAsString().isEmpty() && jo.get("workerInfoId").getAsInt() >0){
			workerAddressDetails  = workerPoliceVerificationService.getWorkerAddressByWorkerInfoId(jo.get("workerInfoId").getAsInt());			
		}	
		
		masterInfoMap.put("workerAddressDetails",  workerAddressDetails);
		masterInfoMap.put("workerPoliceVerificationDetails",  workerPoliceVerificationDetails);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
				
		//return new ResponseEntity<VendorDetailsVo>(vendorDetailsVo,HttpStatus.OK);
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
	
	
	@RequestMapping(value ="/downloadLetter", method = RequestMethod.POST)
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
                
            if( !jo.get("datedOn").isJsonNull() && !(jo.get("datedOn").getAsString().equalsIgnoreCase("null")) && !(jo.get("datedOn").getAsString().equalsIgnoreCase("null"))){
            	params.put("datedOn", jo.get("datedOn").getAsString());
            }else{
            	params.put("datedOn", "");
            }
            
          
			if( !jo.get("dateOne").isJsonNull() && !(jo.get("dateOne").toString().equalsIgnoreCase("null")) && !(jo.get("dateOne").getAsString().equalsIgnoreCase("null"))){
				params.put("dateOne", jo.get("dateOne").getAsString());
			}else{
				params.put("dateOne", "");
			}
			

			if( !jo.get("dateTwo").isJsonNull() && !(jo.get("dateTwo").toString().equalsIgnoreCase("null")) && !(jo.get("dateTwo").getAsString().equalsIgnoreCase("null"))){
				params.put("dateTwo", jo.get("dateTwo").getAsString());
			}else{
				params.put("dateTwo", "");
			}
			

			if( !jo.get("letterNo").isJsonNull() && !(jo.get("letterNo").toString().equalsIgnoreCase("null")) && !(jo.get("letterNo").getAsString().equalsIgnoreCase("null"))){
				params.put("letterNo", jo.get("letterNo").getAsString());
			}else{
				params.put("letterNo", "");
			}
			

			if( !jo.get("policeOne").isJsonNull() && !(jo.get("policeOne").toString().equalsIgnoreCase("null")) && !(jo.get("policeOne").getAsString().equalsIgnoreCase("null"))){
				params.put("policeOne", jo.get("policeOne").getAsString());
			}else{
				params.put("policeOne", "");
			}
			
			if( !jo.get("policeTwo").isJsonNull() && !(jo.get("policeTwo").toString().equalsIgnoreCase("null")) && !(jo.get("policeTwo").getAsString().equalsIgnoreCase("null"))){
				params.put("policeTwo", jo.get("policeTwo").getAsString());
			}else{
				params.put("policeTwo", "");
			}
			
			if( !jo.get("signatureOfTheProprietor").isJsonNull() && !(jo.get("signatureOfTheProprietor").toString().equalsIgnoreCase("null")) && !(jo.get("signatureOfTheProprietor").getAsString().equalsIgnoreCase("null"))){
				params.put("signatureOfTheProprietor", jo.get("signatureOfTheProprietor").getAsString());
			}else{
				params.put("signatureOfTheProprietor", "");
			}
			
			
			if( !jo.get("nameOfContract").isJsonNull() && !(jo.get("nameOfContract").toString().equalsIgnoreCase("null")) && !(jo.get("nameOfContract").getAsString().equalsIgnoreCase("null"))){
				params.put("nameOfContract", jo.get("nameOfContract").getAsString());
			}else{
				params.put("nameOfContract", "");
			}
			
			
			if( !jo.get("contractEngagedWith").isJsonNull() && !(jo.get("contractEngagedWith").toString().equalsIgnoreCase("null")) && !(jo.get("contractEngagedWith").getAsString().equalsIgnoreCase("null"))){
				params.put("contractEngagedWith", jo.get("contractEngagedWith").getAsString());
			}else{
				params.put("contractEngagedWith", "");
			}
			
			
			if( !jo.get("place").isJsonNull() && !(jo.get("place").toString().equalsIgnoreCase("null")) && !(jo.get("place").getAsString().equalsIgnoreCase("null"))){
				params.put("place", jo.get("place").getAsString());
			}else{
				params.put("place", "");
			}
			
			
			
			 params.forEach((key,value) -> System.out.println(key+" :: "+value+" :: "));			
			// File file = new File(request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/reports/"+jo.get("reportName").getAsString()+".jrxml");
			// System.out.println(file.exists());
			 System.out.println(params.toString());
            JasperReport jasperReport = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/reports/WorkerPoliceVerificationLetter.jrxml");
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
	
	
}
