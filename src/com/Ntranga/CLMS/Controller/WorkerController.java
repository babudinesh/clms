package com.Ntranga.CLMS.Controller;


import java.io.File;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Ntranga.CLMS.Service.CommonService;
import com.Ntranga.CLMS.Service.CompanyService;
import com.Ntranga.CLMS.Service.ShiftsDefineService;
import com.Ntranga.CLMS.Service.VendorComplianceService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.Service.WorkerService;
import com.Ntranga.CLMS.vo.CompanyProfileVo;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.LabourTimeVo;
import com.Ntranga.CLMS.vo.SearchCriteriaVo;
import com.Ntranga.CLMS.vo.ShiftsDefineVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.UploadStatusVo;
import com.Ntranga.CLMS.vo.WorkerCertificationDetailsVo;
import com.Ntranga.CLMS.vo.WorkerDetailsVo;
import com.Ntranga.CLMS.vo.WorkerEducationAndEmploymentVo;
import com.Ntranga.CLMS.vo.WorkerEducationDetailsVo;
import com.Ntranga.CLMS.vo.WorkerEmploymentDetailsVo;
import com.Ntranga.CLMS.vo.WorkerIdentificationProofVo;
import com.Ntranga.CLMS.vo.WorkerNomineeDetailsVo;
import com.Ntranga.CLMS.vo.WorkerReferenceVo;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.WorkerDetails;
import com.Ntranga.core.CLMS.entities.WorkerDetailsInfo;
import com.csvreader.CsvReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@SuppressWarnings({"unchecked","rawtypes","unused"})
@Transactional
@RestController
@RequestMapping(value = "workerController")

public class WorkerController {
	
	private static Logger log = Logger.getLogger(WorkerController.class);
	
	private @Value("#{db['db.schemaName']}")
	String dbSchemaName;
	
	@Autowired
	private VendorService vendorService;

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private CompanyService companyService;

	@Autowired
	private WorkerService workerService;
	
	@Autowired
	private SessionFactory sessionFactory;

	
	@Autowired
	private ShiftsDefineService shiftsDefineService;

	@Autowired
	private VendorComplianceService vendorComplianceService;
	
	@Autowired
    private HttpServletRequest request;
	
	private @Value("#{system['WORKER_ID_DOCUMENTS']}")
	String identificationDocuments;
	
	private @Value("#{system['WORKER_EDUCATION_DOCUMENTS']}")
	String educationDocuments;
	
	private @Value("#{system['WORKER_PHOTOS']}")
	String workerPhotos;
	
	

	/*=============================== Worker Details ====================================*/
	
	
	
	@RequestMapping(value = "/countriesList.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getCountriesList() {		
		log.info("Entered in Customer Controller getCountriesList()");
		
		List<SimpleObject> countryList = commonService.getCountriesList();
		log.debug("List of countries : "+countryList);    
		
		log.info("Exiting from Customer Controller getCountriesList() ::  "+countryList);
		return  new ResponseEntity<List<SimpleObject>>(countryList,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/countriesListAndShiftsList.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> countriesListAndShiftsList(@RequestBody ShiftsDefineVo value) {		
		log.info("Entered in Customer Controller getCountriesList()");
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		
		List<SimpleObject> countryList = commonService.getCountriesList();
		 List<ShiftsDefineVo> availableShifts = workerService.getAvailableShifts(value);
		log.debug("List of countries : "+countryList);    
		masterInfoMap.put("countryList", countryList);
		masterInfoMap.put("availableShifts", availableShifts);
		
		log.info("Exiting from Customer Controller getCountriesList() ::  "+countryList);
		return  new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	

	@RequestMapping(value="/workerGridDetails.json", method = RequestMethod.POST)
	public ResponseEntity<List<WorkerDetailsVo>>  workerGridDetails(@RequestBody SearchCriteriaVo searchCriteriaVo) throws Exception {		
		List<WorkerDetailsVo> vendorBankGridList = new ArrayList();
		try{
		 vendorBankGridList = workerService.workerGridDetails(searchCriteriaVo);	
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<WorkerDetailsVo>>(vendorBankGridList,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/saveOrUpdateWorkerDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<SimpleObject> saveOrUpdateWorkerDetails(@RequestParam("name") String worker, @RequestParam("file") MultipartFile[] files ) {				
		System.out.println(files.length+",  "+worker);
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(worker);
		JsonArray jsonArray = jo.get("identityList").getAsJsonArray();
		log.info("JSON Object:  "+jo);
		
		List<WorkerIdentificationProofVo> identityList = new ArrayList<WorkerIdentificationProofVo>();
		WorkerIdentificationProofVo identity = new WorkerIdentificationProofVo();
		
		SimpleObject object = new SimpleObject();
		WorkerDetailsVo workerVo = new WorkerDetailsVo();
	try{
		workerVo.setCustomerId(jo.get("customerId"). isJsonNull() ? null : Integer.valueOf(jo.get("customerId").getAsString().trim()));
		workerVo.setCompanyId(jo.get("companyId").isJsonNull() ? null : Integer.valueOf(jo.get("companyId").getAsString().trim()));
		workerVo.setCountryId(jo.get("countryId").isJsonNull() ? null : Integer.valueOf(jo.get("countryId").getAsString().trim()));
		workerVo.setVendorId(jo.get("vendorId").isJsonNull() ? null :Integer.valueOf(jo.get("vendorId").getAsString().trim()));
		workerVo.setWorkerCode(jo.get("workerCode").isJsonNull() ? null : jo.get("workerCode").getAsString());
		workerVo.setWorkerId(jo.get("workerId").isJsonNull() ? null : jo.get("workerId").getAsInt());
		workerVo.setWorkerInfoId(jo.get("workerInfoId").isJsonNull() ? null : jo.get("workerInfoId").getAsInt());
		
		workerVo.setTransactionDate(jo.get("transactionDate").isJsonNull() ? null : DateHelper.convertStringToSQLDate(jo.get("transactionDate").getAsString()));
		workerVo.setIsActive(jo.get("isActive").isJsonNull() ? null : jo.get("isActive").getAsString());
		workerVo.setDateOfLeaving(!jo.has("dateOfLeaving") ? null : (jo.get("dateOfLeaving").isJsonNull() ? null : DateHelper.convertStringToSQLDate(jo.get("dateOfLeaving").getAsString())));
		workerVo.setReasonForStatus(!jo.has("reasonForStatus") ? null : (jo.get("reasonForStatus").isJsonNull() ? null : jo.get("reasonForStatus").getAsString()));
		workerVo.setFirstName(jo.get("firstName").isJsonNull() ? null : jo.get("firstName").getAsString());
		workerVo.setMiddleName(jo.has("middleName")? (jo.get("middleName").isJsonNull() ? null : jo.get("middleName").getAsString()): null);
		workerVo.setLastName(!jo.has("lastName") ? null : jo.get("lastName").getAsString());
		workerVo.setFatherOrSpouse(jo.has("fatherOrSpouse") ? ( jo.get("fatherOrSpouse").isJsonNull() ? null : jo.get("fatherOrSpouse").getAsString()):  null);
		workerVo.setFatherSpouseName(jo.has("fatherSpouseName") ? ( jo.get("fatherSpouseName").isJsonNull() ? null : jo.get("fatherSpouseName").getAsString()) : null);
		//workerVo.setPanNumber(!jo.has("panNumber") ? null :jo.get("panNumber").getAsString());
		
		workerVo.setPanNumber(jo.has("panNumber") ? (jo.get("panNumber").isJsonNull() ? null : jo.get("panNumber").getAsString()): null);
		
		workerVo.setDateOfBirth(!jo.has("dateOfBirth") ? null :DateHelper.convertStringToSQLDate(jo.get("dateOfBirth").getAsString()));
		workerVo.setAge(!jo.has("age") ? null :jo.get("age").getAsString());
		workerVo.setGender(!jo.has("gender") ? null :jo.get("gender").getAsCharacter());
		workerVo.setMaritalStatus(!jo.has("maritalStatus") ? null :jo.get("maritalStatus").getAsString());
		workerVo.setBloodGroup(!jo.has("bloodGroup") ? null :(jo.get("bloodGroup").isJsonNull() ? null : jo.get("bloodGroup").getAsString()));
		workerVo.setReligion(!jo.has("religion") ? null :( jo.get("religion").isJsonNull() ? null : jo.get("religion").getAsString()));
		//workerVo.setPhoneNumber(jo.get("phoneNumber").isJsonNull() ? null :jo.get("phoneNumber").getAsString());
		workerVo.setPhoneNumber(jo.has("phoneNumber") ? (jo.get("phoneNumber").isJsonNull() ? null : jo.get("phoneNumber").getAsString()): null);
		
		workerVo.setEmail(!jo.has("email") ? null : (jo.get("email").isJsonNull() ? null : jo.get("email").getAsString()));
		//workerVo.setEmail(jo.has("email") ? (jo.get("email").isJsonNull() ? null : jo.get("email").getAsString()): null);
		workerVo.setEmergencyContactNumber(!jo.has("emergencyContactNumber") ? null : ( jo.get("emergencyContactNumber").isJsonNull() ? null : jo.get("emergencyContactNumber").getAsString()));		
		workerVo.setEmergencyContactPerson(!jo.has("emergencyContactPerson") ? null :( jo.get("emergencyContactPerson").isJsonNull() ? null : jo.get("emergencyContactPerson").getAsString()));
		
		workerVo.setPermanentAddressLine1(!jo.has("permanentAddressLine1") ? null :( jo.get("permanentAddressLine1").isJsonNull() ? null : jo.get("permanentAddressLine1").getAsString()));
		workerVo.setPermanentAddressLine2(!jo.has("permanentAddressLine2") ? null :(jo.get("permanentAddressLine2").isJsonNull()  ? null : jo.get("permanentAddressLine2").getAsString()));
		workerVo.setPermanentAddressLine3(!jo.has("permanentAddressLine3") ? null :( jo.get("permanentAddressLine3").isJsonNull() ? null :jo.get("permanentAddressLine3").getAsString()));
		workerVo.setPermanentCountryId(!jo.has("permanentCountryId") ? null :( jo.get("permanentCountryId").isJsonNull() ? null : jo.get("permanentCountryId").getAsInt()));
		workerVo.setPermanentCity(!jo.has("permanentCity") ? null :( jo.get("permanentCity").isJsonNull() ? null :jo.get("permanentCity").getAsString()));
		workerVo.setPermanentStateId(!jo.has("permanentStateId") ? null :(( jo.get("permanentStateId").isJsonNull() || jo.get("presentStateId").getAsString().isEmpty()) ? null : jo.get("permanentStateId").getAsInt()));
		workerVo.setPermanentPinCode(!jo.has("permanentPinCode") ? null :( jo.get("permanentPinCode").isJsonNull() ? null :jo.get("permanentPinCode").getAsInt()));
		
		workerVo.setPresentAddressLine1(!jo.has("presentAddressLine1") ? null :( jo.get("presentAddressLine1").isJsonNull() ? null : jo.get("presentAddressLine1").getAsString()));
		workerVo.setPresentAddressLine2(!jo.has("presentAddressLine2") ? null :( jo.get("presentAddressLine2").isJsonNull() ? null :jo.get("presentAddressLine2").getAsString()));
		workerVo.setPresentAddressLine3(!jo.has("presentAddressLine3") ? null :( jo.get("presentAddressLine3").isJsonNull() ? null :jo.get("presentAddressLine3").getAsString()));
		workerVo.setPresentcountryId(!jo.has("presentcountryId") ? null :( jo.get("presentcountryId").isJsonNull() ? null :jo.get("presentcountryId").getAsInt()));
		workerVo.setPresentCity(!jo.has("presentCity")? null :( jo.get("presentCity").isJsonNull() ? null : jo.get("presentCity").getAsString()));
		workerVo.setPresentStateId(!jo.has("presentStateId") ? null :(( jo.get("presentStateId").isJsonNull() || jo.get("presentStateId").getAsString().isEmpty())? null : jo.get("presentStateId").getAsInt()));
		workerVo.setPresentPinCode(!jo.has("presentPinCode") ? null :( jo.get("presentPinCode").isJsonNull() ? null :  jo.get("presentPinCode").getAsInt()));
		workerVo.setCreatedBy(!jo.has("createdBy") ? null :jo.get("createdBy").getAsInt());
		workerVo.setModifiedBy(!jo.has("modifiedBy") ? null :jo.get("modifiedBy").getAsInt());
		
		workerVo.setShiftName(!jo.has("shiftName") ? null :( jo.get("shiftName").isJsonNull() ? null :jo.get("shiftName").getAsString()));
		
		workerVo.setWeeklyOff(!jo.has("weeklyOff") ? null :( jo.get("weeklyOff").isJsonNull() ? null :jo.get("weeklyOff").getAsString()));
		workerVo.setDateOfJoining(!jo.has("dateOfJoining") ? null :DateHelper.convertStringToSQLDate(jo.get("dateOfJoining").getAsString()));
		workerVo.setIsSameAddress(!jo.has("isSameAddress") ? false : (jo.get("isSameAddress").isJsonNull() ? false : jo.get("isSameAddress").getAsBoolean()));
		
		workerVo.setAcountHolderName(!jo.has("acountHolderName") ? null :( jo.get("acountHolderName").isJsonNull() ? null :jo.get("acountHolderName").getAsString()));	
		workerVo.setAcountNumber(!jo.has("acountNumber") ? null :( jo.get("acountNumber").isJsonNull() ? null :jo.get("acountNumber").getAsString()));
		workerVo.setBankName(!jo.has("bankName") ? null :( jo.get("bankName").isJsonNull() ? null :jo.get("bankName").getAsString()));
		workerVo.setBranchName(!jo.has("branchName") ? null :( jo.get("branchName").isJsonNull() ? null :jo.get("branchName").getAsString()));
		workerVo.setIfscCode(!jo.has("ifscCode") ? null :( jo.get("ifscCode").isJsonNull() ? null :jo.get("ifscCode").getAsString()));
		
		workerVo.setMotherName(!jo.has("motherName") ? null :( jo.get("motherName").isJsonNull() ? null :jo.get("motherName").getAsString()));
		workerVo.setNationality(!jo.has("nationality") ? null :( jo.get("nationality").isJsonNull() ? null :jo.get("nationality").getAsString()));
		workerVo.setLanguage(!jo.has("language") ? null :( jo.get("language").isJsonNull() ? null :jo.get("language").getAsString()));
		workerVo.setEducation(!jo.has("education") ? null :( jo.get("education").isJsonNull() ? null :jo.get("education").getAsString()));
		workerVo.setDomicile(!jo.has("domicile") ? null :( jo.get("domicile").isJsonNull() ? null :jo.get("domicile").getAsString()));
		
		//workerVo.setCreatedDate(!jo.has("createdDate") ? null :DateHelper.convertStringToSQLDate(jo.get("createdDate").getAsString()));
		
		//String fileUploadPath = (request.getServletContext().getRealPath("").concat(File.separator).concat(fileLocation));
		String filePath = null;
			 
        System.out.println(new File(identificationDocuments).exists());
        if(! new File(identificationDocuments).exists()){
            new File(identificationDocuments).mkdirs();
        }
        log.info("Identification = {}"+ identificationDocuments);
        
        if(! new File(workerPhotos).exists()){
            new File(workerPhotos).mkdirs();
        }
       
        
        log.info("photos = {}"+ workerPhotos);
        
        if(files.length == 0){
        	if(jo.has("imagePath") && !jo.get("imagePath").toString().equals("null") && !jo.get("imagePath").isJsonNull() && !jo.get("imagePath").toString().isEmpty()){
            	workerVo.setImagePath(jo.get("imagePath").getAsString());
            	System.out.println(jo.get("imagePath").getAsString());
            	workerVo.setImageName(jo.get("imageName").getAsString());
            }
        	if(jsonArray.size() > 0)
	        	System.out.println("%%%%%%% "+jsonArray);
	            JsonObject jObject  = null;	
	            for(int i = 0; i< jsonArray.size(); i++){
	                jObject = jsonArray.get(i).getAsJsonObject();
	                //jObject.get("countryId");
	                
	                System.out.println( jObject);
	              
                	if(jObject.has("filePath") && !jObject.get("filePath").isJsonNull()){
                		identity = new WorkerIdentificationProofVo();
                		identity.setFilePath(jObject.get("filePath").getAsString());
                		identity.setIdentificationType(jObject.get("identificationType").isJsonNull() ? null : jObject.get("identificationType").getAsString().trim());
                		identity.setIdentificationNumber(jObject.get("identificationNumber").isJsonNull() ? null : jObject.get("identificationNumber").getAsString().trim());
		                identity.setCountryId(jObject.get("countryId").isJsonNull() ? null : Integer.valueOf(jObject.get("countryId").getAsString().trim()));
		                System.out.println("file1231---------------"+identity.getIdentificationType()+identity.getCountryId()+", "+identity.getFilePath());
                	}
                	identityList.add(identity);
	            }
            workerVo.setIdentityList(identityList); 
        }
        
		for(MultipartFile f : files){
			// System.out.println(f.isEmpty());
			if(!f.isEmpty()){
				try{
	                String orgName = f.getOriginalFilename();
	                String[] extension = orgName.split("\\.(?=[^\\.]+$)");
	                DateFormat df = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
	                String fileName = orgName.split("\\.")[0]+"_"+df.format(new java.util.Date())+"."+extension[extension.length-1];
	                filePath = identificationDocuments +  fileName;
	                //String filePath1 = workerPhotos+fileName;
	                //System.out.println("file---------------"+filePath);
	                File dest = null;
	                //f.transferTo(dest);
	                System.out.println(fileName);
                	//dest = new File(filePath);
	               // f.transferTo(dest);
                
	                if(jo.has("imageName") && !jo.get("imageName").toString().equals("null") && !jo.get("imageName").isJsonNull() && !jo.get("imageName").toString().isEmpty() && jo.get("imageName").getAsString().equalsIgnoreCase(orgName)){
	                	System.out.println(workerPhotos+fileName);
	                	dest = new File(workerPhotos+fileName);
	                	f.transferTo(dest);
	                	workerVo.setImagePath(workerPhotos+fileName);
	                	workerVo.setImageName(jo.get("imageName").getAsString());
	                	//System.out.println(workerVo.getImagePath());
	                }
                
	                JsonObject jObject  = null;
	                for(int i = 0; i< jsonArray.size(); i++){
		                jObject = jsonArray.get(i).getAsJsonObject();
		                identity = new WorkerIdentificationProofVo();
		                System.out.println( jObject);
		                if(jsonArray != null && jsonArray.size() > 0 ){
		                	if(jObject.get("fileName").getAsString().equalsIgnoreCase(orgName)){
		                		System.out.println("file111---------------"+filePath);
		                		dest = new File( identificationDocuments +  fileName);
		                		f.transferTo(dest);
				                identity.setIdentificationType(jObject.get("identificationType").isJsonNull() ? null : jObject.get("identificationType").getAsString().trim());
				                identity.setIdentificationNumber(jObject.get("identificationNumber").isJsonNull() ? null : jObject.get("identificationNumber").getAsString().trim());
				                identity.setCountryId(jObject.get("countryId").isJsonNull() ? null : Integer.valueOf(jObject.get("countryId").getAsString().trim()));
				                identity.setFilePath(filePath);
		                	}else if(jObject.has("filePath") && !jObject.get("filePath").isJsonNull()){
		                		//identity = new WorkerIdentificationProofVo();
		                		identity.setFilePath(jObject.get("filePath").getAsString());
		                		identity.setIdentificationNumber(jObject.get("identificationNumber").isJsonNull() ? null : jObject.get("identificationNumber").getAsString().trim());
		                		identity.setIdentificationType(jObject.get("identificationType").isJsonNull() ? null : jObject.get("identificationType").getAsString().trim());
				                identity.setCountryId(jObject.get("countryId").isJsonNull() ? null : Integer.valueOf(jObject.get("countryId").getAsString().trim()));
				                System.out.println("file1231---------------"+filePath);
		                	}
		                }
		                identityList.add(identity);
			            
	                }
				}catch(Exception e){
					 object.setId(-1);
	     			 object.setName("Technical issue occurred while Saving file " );
	            	 log.error("Error Occured ",e);
				}
			}else{
				object.setId(-1);
     			object.setName("File is Empty");
			}
			 workerVo.setIdentityList(identityList); 
		}
		
		Integer branchId = workerService.saveOrUpdateWorkerDetails(workerVo);	
		if(branchId != null && branchId > 0){
			object.setId(branchId);
			object.setName("Success");
		}else{
			object.setId(0);
 			object.setName("Failed ");
		}
	}catch(Exception e){
		log.error("Error Occured ",e);
		e.printStackTrace();
		object.setId(-1);
		object.setName("Technical issue occurred while Saving file " );    	
	}
		return new ResponseEntity<SimpleObject>(object,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveWorkerWithoutFiles.json", method = RequestMethod.POST)
	public  ResponseEntity<SimpleObject> saveWorkerWithoutFiles(@RequestBody WorkerDetailsVo workerVo ) {	
		SimpleObject object = new SimpleObject();
		try{
		Integer branchId = workerService.saveOrUpdateWorkerDetails(workerVo);
		if(branchId != null && branchId > 0){
			object.setId(branchId);
			object.setName("Success");
		}else{
			object.setId(0);
 			object.setName("Failed ");
		}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<SimpleObject>(object,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/changeWorkerCode.json", method = RequestMethod.POST)
	public  ResponseEntity<Integer> changeWorkerCode(@RequestBody WorkerDetailsVo workerVo ) {	
		SimpleObject object = new SimpleObject();
		Integer branchId = null;
		try{
		 branchId = workerService.changeWorkerCode(workerVo);
		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Integer>(branchId,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getTransactionDatesListForEditingWorkerDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getTransactionDatesListForEditingWorkerDetails(@RequestBody String workerId) {
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(workerId);	
		List<SimpleObject> transactionDatesList = new ArrayList();
		try{
			transactionDatesList  = workerService.getTransactionDatesListForEditingWorkerDetails(jo.get("workerId").getAsInt());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(transactionDatesList,HttpStatus.OK);
	}
	
	
	
	
	@RequestMapping(value = "/getWorkerDetailsbyId.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getWorkerDetailsbyId(@RequestBody String workerInfoId) {					
		Map<String,List> masterInfoMap = new HashMap<String,List>();		
		List<WorkerDetailsVo> workerDetails = new ArrayList<>();
		List<CustomerVo> customerList = new ArrayList();
		JsonParser jsonParser = new JsonParser();
		System.out.println("workerInfoId::"+workerInfoId);
		JsonObject jo = (JsonObject) jsonParser.parse(workerInfoId);
		try{
		if( jo.get("workerInfoId")!= null && jo.get("workerInfoId").toString() != null && !jo.get("workerInfoId").getAsString().isEmpty() ){
			workerDetails  = workerService.getWorkerDetailsbyId(jo.get("workerInfoId").getAsString());			
		}						
		if(jo.get("customerId")!= null){
		 customerList = vendorService.getCustomerNamesAsDropDown(jo.get("customerId").getAsString());
		}
		List<SimpleObject> countryList =   commonService.getCountriesList();
		List<ShiftsDefineVo> availableShifts = workerService.getAvailableShifts(new ShiftsDefineVo());	
		masterInfoMap.put("availableShifts", availableShifts);
			
		masterInfoMap.put("countryList", countryList);
		masterInfoMap.put("customerList", customerList);
		masterInfoMap.put("workerDetails",  workerDetails);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
				
		//return new ResponseEntity<VendorDetailsVo>(vendorDetailsVo,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getVendorAndWorkerNamesAsDropDowns.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getVendorAndWorkerNamesAsDropDowns(@RequestBody String customerCompanyJsonString) {		
		Map<String,List> masterInfoMap = new HashMap<String,List>();	
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(customerCompanyJsonString);
		String  locationId = null;
		try{
			if(jo.has("locationId") && jo.get("locationId") != null &&  !jo.get("locationId").isJsonNull() && jo.get("locationId").getAsJsonArray().size() > 0 ){
				JsonArray locId = jo.get("locationId").getAsJsonArray();
				locationId = locId.toString().replace("]", "").replace("[", "");
			}
				List<SimpleObject> vendorNames  = workerService.getVendorNamesAsDropDown(jo.get("customerId").getAsString(), jo.get("companyId").getAsString(),jo.get("vendorId").getAsString(), "Validated",locationId);
				
				List<SimpleObject> countriesList = commonService.getCompanyCountries(jo.get("companyId").getAsInt());
				CompanyProfileVo profileVo = companyService.getCompanyProfileByCompanyId(jo.get("companyId").getAsString(),jo.get("customerId").getAsString());  
				List<CompanyProfileVo> profileList = new ArrayList<CompanyProfileVo>();
				profileList.add(profileVo);
				masterInfoMap.put("countriesList", countriesList);
				masterInfoMap.put("vendorList", vendorNames);	
				masterInfoMap.put("companyProfile", profileList);
				masterInfoMap.put("maxWorkerCode", profileList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveNomineeDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<Integer> saveNomineeDetails(@RequestBody WorkerNomineeDetailsVo workerVo ) {	
		Integer branchId = 0;
		try{
		 branchId = workerService.saveNomineeDetails(workerVo);	
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Integer>(branchId,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/deleteNomineeDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<Integer> deleteNomineeDetails(@RequestBody String nomineeId ) {
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(nomineeId);	
		Integer id = 0;
		try{
		Integer workerNomneeId = jo.get("workerNomneeId").getAsInt();
		 id = workerService.deleteNomineeDetails(workerNomneeId);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Integer>(id,HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/getNomineeGridData.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getNomineeGridData(@RequestBody String customerCompanyWorkerIdJsonString) {		
		Map<String,List> masterInfoMap = new HashMap<String,List>();	
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(customerCompanyWorkerIdJsonString);
		//System.out.println(customerCompanyWorkerIdJsonString);	
		List<WorkerNomineeDetailsVo> NomineeGridData = new ArrayList<>();
		try{
		if(jo.get("customerId").toString() != "" && jo.get("companyId").toString() != "" && jo.get("workerId").toString() != ""){
		 NomineeGridData  = workerService.getNomineeGridData(jo.get("customerId").getAsInt(), jo.get("companyId").getAsInt(),jo.get("workerId").getAsInt());
		}
		masterInfoMap.put("NomineeGridData", NomineeGridData);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	
	
	
	@RequestMapping(value="/validateWorkerCode.json", method = RequestMethod.POST)
	  public  ResponseEntity<SimpleObject> validateCustomerCode(@RequestBody WorkerDetailsVo workerVo) {
		  	log.info("Entered in Worker Controller validateWorkerCode() ::  ");
		  	SimpleObject so = new SimpleObject();     
	        try {
	        	 so = workerService.validateWorkerCode(workerVo.getWorkerCode(), workerVo.getCustomerId(), workerVo.getCompanyId(), workerVo.getVendorId(),workerVo.getWorkerId());
			} catch (Exception e) {
				log.error("Error Occured ",e);
				so.setId(-1);
				so.setName("Exception Occured :: "+e);
			}
	        log.info("Exiting from Customer Controller validateCustomerName() ::  "+so);
	        return new ResponseEntity<SimpleObject>(so,HttpStatus.OK);
	  }
	
	@RequestMapping(value="/validateDuplicateWorker.json", method = RequestMethod.POST)
	  public  ResponseEntity<SimpleObject> validateDuplicateWorker(@RequestBody WorkerDetailsVo workerVo) {
		  	log.info("Entered in Worker Controller validateWorkerCode() ::  ");
		  	SimpleObject so = new SimpleObject();     
	        try {
	        	 so = workerService.validateDuplicateWorker(workerVo);
			} catch (Exception e) {
				log.error("Error Occured ",e);
				so.setId(-1);
				so.setName("Exception Occured :: "+e);
			}
	        log.info("Exiting from Customer Controller validateCustomerName() ::  "+so);
	        return new ResponseEntity<SimpleObject>(so,HttpStatus.OK);
	  }
	
	
	/*=============================== Worker Details END ====================================*/
	
	
	
	
	/*=============================== Worker Education and Employment Start ====================================*/
	
	
	
	@RequestMapping(value = "/saveEducationAndEmploymentDetailsWithFiles.json", method = RequestMethod.POST)
	public  ResponseEntity<SimpleObject> saveEducationAndEmploymentDetailsWithFiles(@RequestParam("name") String educationAndEmploymentVo, @RequestParam("file") MultipartFile[] files) {				
		System.out.println(files.length+",  "+educationAndEmploymentVo);
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(educationAndEmploymentVo);
		JsonArray educationArray = jo.get("educationList").getAsJsonArray();
		JsonArray certificateArray = jo.get("certificationList").getAsJsonArray();
		JsonArray employmentArray = jo.get("employmentList").getAsJsonArray();
		//JsonArray referenceArray = jo.get("referenceList").getAsJsonArray();
		log.info("JSON Object:  "+jo);
		log.info(" Education Array:  "+educationArray);
		System.out.println(" Education Array:  "+educationArray);
		log.info(" Certificate Array :   "+certificateArray);
		log.info(" Employment Array  "+employmentArray);
		//log.info(" Reference Array  "+referenceArray);
		
		WorkerEducationDetailsVo education = new WorkerEducationDetailsVo();
		WorkerCertificationDetailsVo certification = new WorkerCertificationDetailsVo();
		WorkerEmploymentDetailsVo employment = new WorkerEmploymentDetailsVo();
		WorkerReferenceVo reference = new WorkerReferenceVo();
		
		List<WorkerEducationDetailsVo> educationList = new ArrayList<WorkerEducationDetailsVo>();
		List<WorkerCertificationDetailsVo> certificationList = new ArrayList<WorkerCertificationDetailsVo>();
		List<WorkerEmploymentDetailsVo> employmentList = new ArrayList<WorkerEmploymentDetailsVo>();
		List<WorkerReferenceVo> referenceList = new ArrayList<WorkerReferenceVo>();
		
		SimpleObject object = new SimpleObject();
		WorkerEducationAndEmploymentVo educationAndEmployment = new WorkerEducationAndEmploymentVo();
		try{
		educationAndEmployment.setCustomerId(jo.get("customerId"). isJsonNull() ? null : Integer.valueOf(jo.get("customerId").getAsString().trim()));
		educationAndEmployment.setCompanyId(jo.get("companyId").isJsonNull() ? null : Integer.valueOf(jo.get("companyId").getAsString().trim()));
		//educationAndEmployment.setCountryId(jo.get("countryId").isJsonNull() ? null : Integer.valueOf(jo.get("countryId").getAsString().trim()));
		educationAndEmployment.setVendorId(jo.get("vendorId").isJsonNull() ? null :Integer.valueOf(jo.get("vendorId").getAsString().trim()));
		educationAndEmployment.setWorkerCode(jo.get("workerCode").isJsonNull() ? null : jo.get("workerCode").getAsString());
		educationAndEmployment.setWorkerId(jo.get("workerId").isJsonNull() ? null : jo.get("workerId").getAsInt());
		//educationAndEmployment.setWorkerInfoId(jo.get("workerInfoId").isJsonNull() ? null : jo.get("workerInfoId").getAsInt());
		educationAndEmployment.setWorkSkill(jo.get("workerSkill").isJsonNull() ? null : jo.get("workerSkill").getAsString());
		educationAndEmployment.setCreatedBy(jo.get("createdBy").isJsonNull() ? null : Integer.valueOf(jo.get("createdBy").getAsString().trim()));
		educationAndEmployment.setModifiedBy(jo.get("modifiedBy").isJsonNull() ? null : Integer.valueOf(jo.get("modifiedBy").getAsString().trim()));

		//String fileUploadPath = (request.getServletContext().getRealPath("").concat(File.separator).concat(fileLocation));
		String filePath = null;
			 
        //System.out.println(new File(fileLocation).exists());
        if(! new File(educationDocuments).exists()){
            new File(educationDocuments).mkdirs();
        }
        System.out.println("fileLocation = {}"+ educationDocuments);
        JsonObject jObject  = null;

        for(MultipartFile f : files){
			if(!f.isEmpty()){
				try{
	                String orgName = f.getOriginalFilename();
	                String[] extension = orgName.split("\\.(?=[^\\.]+$)");
	                DateFormat df = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
	                String fileName = orgName.split("\\.")[0]+"_"+df.format(new java.util.Date())+"."+extension[extension.length-1];
	                filePath = educationDocuments +  fileName;
	                System.out.println("file---------------"+filePath);
	                File dest = new File(filePath);
	                f.transferTo(dest); 
	               
	                for(int i = 0; i< educationArray.size(); i++){
		                jObject = educationArray.get(i).getAsJsonObject();
		                System.out.println( jObject);
		                if(educationArray != null && educationArray.size() > 0 ){
		                	if(jObject.get("fileName").getAsString().equalsIgnoreCase(orgName)){
				                education.setEducationLevel(jObject.get("educationLevel").isJsonNull() ? null : jObject.get("educationLevel").getAsString().trim());
				                education.setDegreeName(jObject.get("degreeName").isJsonNull() ? null : jObject.get("degreeName").getAsString().trim());
				                education.setInstitutionName(jObject.get("institutionName").isJsonNull() ? null : jObject.get("institutionName").getAsString().trim());
				                education.setModeOfEducation(jObject.get("modeOfEducation").isJsonNull() ? null : jObject.get("modeOfEducation").getAsString().trim());
				                education.setYearOfPassing(jObject.get("yearOfPassing").isJsonNull() ? null : Integer.valueOf(jObject.get("yearOfPassing").getAsString().trim()));
				                education.setPercentageOrGrade(jObject.get("percentageOrGrade").isJsonNull() ? null : jObject.get("percentageOrGrade").getAsString().trim());
				                education.setFilePath(filePath);
		                	}
		                }
		                educationList.add(education);
		            }
		            
	                for(int i = 0; i< certificateArray.size(); i++){
		                jObject = certificateArray.get(i).getAsJsonObject();
		                System.out.println( jObject);
		                if(certificateArray != null && certificateArray.size() > 0 ){
		                	if(jObject.get("fileName").getAsString().equalsIgnoreCase(orgName)){
		                		certification.setCertificateName(jObject.get("certificateName").isJsonNull() ? null : jObject.get("certificateName").getAsString().trim());
		                		certification.setIssuingAuthority(jObject.get("issuingAuthority").isJsonNull() ? null : jObject.get("issuingAuthority").getAsString().trim());
		                		certification.setValidity(jObject.get("validity").isJsonNull() ? null : jObject.get("validity").getAsString().trim());
		                		certification.setValidFrom(jObject.get("validFrom").isJsonNull() ? null : (Date)DateHelper.convertStringToSQLDate(jObject.get("validFrom").getAsString().trim()));
				                certification.setValidTo(jObject.get("validTo").isJsonNull() ? null : (Date)DateHelper.convertStringToSQLDate(jObject.get("validTo").getAsString().trim()));
				                certification.setFilePath(filePath);
		                	}
		                }
		                certificationList.add(certification);
		            }
	                
	                for(int i = 0; i< employmentArray.size(); i++){
		                jObject = employmentArray.get(i).getAsJsonObject();
		                System.out.println( jObject);
		                if(employmentArray != null && employmentArray.size() > 0 ){
		                	if(jObject.get("fileName").getAsString().equalsIgnoreCase(orgName)){
		                		employment.setOrganizationName(jObject.get("organizationName").isJsonNull() ? null : jObject.get("certificateName").getAsString().trim());
		                		employment.setDesignation(jObject.get("designation").isJsonNull() ? null : jObject.get("designation").getAsString().trim());
		                		employment.setContactNumber(jObject.get("contactNumber").isJsonNull() ? null : jObject.get("contactNumber").getAsString().trim());
		                		employment.setStartDate(jObject.get("startDate").isJsonNull() ? null : (Date)DateHelper.convertStringToSQLDate(jObject.get("startDate").getAsString().trim()));
		                		employment.setEndDate(jObject.get("endDate").isJsonNull() ? null : (Date)DateHelper.convertStringToSQLDate(jObject.get("endDate").getAsString().trim()));
		                		employment.setCurrent(jObject.get("current").isJsonNull() ? null : jObject.get("current").getAsBoolean());
		                		employment.setTotalYears(jObject.get("totalYears").isJsonNull() ? null : Integer.valueOf(jObject.get("totalYears").getAsString().trim()));
		                		employment.setFilePath(filePath);
		                	}
		                }
		                employmentList.add(employment);
		            }
	                
				}catch(Exception e){
					 object.setId(0);
	     			 object.setName("Exception Occurred");
	            	 log.error("Error Occured ",e);
				}
				/*for(int i = 0; i< referenceArray.size(); i++){
	                jObject = referenceArray.get(i).getAsJsonObject();
	                System.out.println( jObject);
	                if(referenceArray != null && referenceArray.size() > 0 ){
                		reference.setRelationship(jObject.get("relationShip").isJsonNull() ? null : jObject.get("relationShip").getAsString().trim());
                		reference.setDesignation(jObject.get("designation").isJsonNull() ? null : jObject.get("designation").getAsString().trim());
                		reference.setContactNumber(jObject.get("contactNumber").isJsonNull() ? null : jObject.get("contactNumber").getAsString().trim());
                		reference.setName(jObject.get("name").isJsonNull() ? null : jObject.get("name").getAsString().trim());
                		reference.setEmailId(jObject.get("emailId").isJsonNull() ? null : jObject.get("emailId").getAsString().trim());
	                }
	                referenceList.add(reference);
	            }*/
				
				educationAndEmployment.setEducationalList(educationList);
				educationAndEmployment.setReferenceList(referenceList);
				educationAndEmployment.setCertificationList(certificationList);
				educationAndEmployment.setEmploymentList(employmentList);
			}else{
				object.setId(0);
     			object.setName("File is Empty");
			}

		}
		
		Integer flag = workerService.saveEducationAndEmploymentDetails(educationAndEmployment);	
		if(flag == 1){
			object.setId(0);
			object.setName("Failed");
		}else{
			object.setId(flag);
			object.setName("Success");
		}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<SimpleObject>(object,HttpStatus.OK);
	}	
	
	@RequestMapping(value = "/saveEducationAndEmploymentDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<SimpleObject> saveEducationAndEmploymentDetails(@RequestBody WorkerEducationAndEmploymentVo educationAndEmployment) {				
		SimpleObject object = new SimpleObject();
		System.out.println(educationAndEmployment);
		try{
		Integer flag = workerService.saveEducationAndEmploymentDetails(educationAndEmployment);		
		
		if(flag == 1){
			object.setId(0);
			object.setName("Failed");
		}else{
			object.setId(flag);
			object.setName("Success");
		}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<SimpleObject>(object,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getEducationAndEmployementList.json", method = RequestMethod.POST)
	public  ResponseEntity<WorkerEducationAndEmploymentVo> getEducationAndEmployementList(@RequestBody String education) {		
		Map<String,List> masterInfoMap = new HashMap<String,List>();	
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(education);
		log.info("JSON Object : " +jo);
		
		WorkerEducationAndEmploymentVo educationAndEmployment = new WorkerEducationAndEmploymentVo();
		try{
		if(jo.get("customerId").toString() != "" && jo.get("companyId").toString() != "" && jo.get("workerId").toString() != ""){
			educationAndEmployment  = workerService.getEducationAndEmploymentList(jo.get("customerId").getAsInt(), jo.get("companyId").getAsInt(),jo.get("workerId").getAsInt());
		}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<WorkerEducationAndEmploymentVo>(educationAndEmployment,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/uploadWorker.json", method = RequestMethod.POST)
	public @ResponseBody  ResponseEntity<List<WorkerDetailsVo>> uploadFile(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file) {
		//UploadStatusVo statusVo = new UploadStatusVo();
		//System.out.println(":::fileasdfasdf::::" + file.getOriginalFilename() );

		Session session = sessionFactory.getCurrentSession();
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(name);

		List<WorkerDetailsVo> errorsList = new ArrayList();		
		int rowCount =1; 
		int customerId = -1;
		int companyId = 0;
		int vendorId = 0;
		int countryId = 0;
		
		if (jo.get("customerId") != null && jo.get("customerId").getAsInt() > 0) {
			customerId = jo.get("customerId").getAsInt();
		}

		if (jo.get("companyId") != null && jo.get("companyId").getAsInt() > 0) {
			companyId = jo.get("companyId").getAsInt();
		}
		if (jo.get("vendorId") != null && jo.get("vendorId").getAsInt() > 0) {
			vendorId = jo.get("vendorId").getAsInt();
		}
		if (jo.get("countryId") != null && jo.get("countryId").getAsInt() > 0) {
			countryId = jo.get("countryId").getAsInt();
		}

		try {

			Integer workerId = 0;
			
						
			if (file != null && !file.isEmpty()) {

				CsvReader products = new CsvReader(file.getInputStream(), Charset.forName("UTF8"));
				products.readHeaders();
			
				while (products.readRecord()) {
					rowCount++;
					boolean flag = false;
					//errors="Line Number: ";
					//System.out.println(products+"::products");
					DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
					String workerCode = products.get("Worker_Code");
					if (workerCode != null && !workerCode.isEmpty()) {
						SimpleObject so = workerService.validateWorkerCode(workerCode, customerId, companyId, vendorId,0);
						if (so.getId() == 0) {	
						WorkerDetails work = new WorkerDetails();
						
							work.setCustomerId(customerId);
							work.setCompanyId(companyId);
							work.setVendorId(vendorId);
							work.setCountryId(countryId);
							work.setWorkerCode(workerCode);
							work.setCreatedBy(180);
							work.setCreatedDate(new Date());
							work.setModifiedBy(180);
							work.setModifiedDate(new Date());
							

						WorkerDetailsInfo detailsInfo = new WorkerDetailsInfo();
						

						detailsInfo.setCustomerId(customerId);
						detailsInfo.setCompanyId(companyId);
						
						if(products.get("Effective_From") != null && !products.get("Effective_From").isEmpty())
						{
							detailsInfo.setTransactionDate(formatter.parse(products.get("Effective_From")));
						}
						
						//if(products.get("Is_Active") != null && !products.get("Is_Active").isEmpty())
						//{
							detailsInfo.setIsActive("I");
						//}
						
						if(products.get("First_Name") != null && !products.get("First_Name").isEmpty())
						{
							detailsInfo.setFirstName(products.get("First_Name"));
						}
											
						if(products.get("Last_Name") != null && !products.get("Last_Name").isEmpty()){
							detailsInfo.setLastName(products.get("Last_Name"));
						}
																	
						if(products.get("Date_Of_Birth") != null && !products.get("Date_Of_Birth").isEmpty()){						
							detailsInfo.setDateOfBirth(formatter.parse(products.get("Date_Of_Birth")));	
						}
						if(products.get("Date_Of_Joining") != null && !products.get("Date_Of_Joining").isEmpty()){
							detailsInfo.setDateOfJoining(formatter.parse(products.get("Date_Of_Joining")));
						}						
						if(products.get("Gender") != null && !products.get("Gender").isEmpty()){
							detailsInfo.setGender(products.get("Gender").charAt(0));
						}						
						if (products.get("Marital_Status") != null && !products.get("Marital_Status").isEmpty()) {
							detailsInfo.setMaritalStatus(products.get("Marital_Status"));
						} 	
						if (products.get("Shift_Code") != null && !products.get("Shift_Code").isEmpty()) {
							detailsInfo.setShiftName(products.get("Shift_Code"));
						}
						if (products.get("Weekly_Off") != null && !products.get("Weekly_Off").isEmpty()) {
							detailsInfo.setWeeklyOff(products.get("Weekly_Off"));		
						}
						
						detailsInfo.setSequenceId(1);
						detailsInfo.setCreatedBy(jo.get("createdBy").getAsInt());
						detailsInfo.setCreatedDate(new Date());
						detailsInfo.setModifiedBy(jo.get("modifiedBy").getAsInt());
						detailsInfo.setModifiedDate(new Date());
												
							workerId = (Integer) session.save(work);
							session.flush();
							session.clear();
							//totaRecordsInserted++;
							detailsInfo.setWorkerDetails(new WorkerDetails(workerId));
							Integer workerInfoId = workerService.getWorkerInfoId(workerId,formatter.parse(products.get("Effective_From")));
							if (workerInfoId > 0 ) {
								detailsInfo.setWorkerInfoId(workerInfoId);
								session.update(detailsInfo);
								
							} else{
								session.save(detailsInfo);
							}
							
							
							/*List labourScheduledList = session.createSQLQuery(
									" SELECT date_field AS Business_date, vd.vendor_code,worker_CODE,replace((CONCAT(RTRIM(first_name), ' ',CASE WHEN (middle_name IS NULL) THEN '' ELSE CONCAT(RTRIM(middle_name),' ') END, last_name)),'  ',' ') , "
											+ " (CASE WHEN TRIM(UPPER(Weekly_off)) = UPPER(DATE_FORMAT(date_field,'%a')) THEN 'WO' ELSE shift_name END) AS Shift, "
											+ " Weekly_Off,vdi.vendor_name  "
											+ " FROM worker_details wd  "
											+ " JOIN worker_details_info wdi ON wd.worker_id = wdi.worker_id  "
											+ " JOIN vendor_details vd ON  wd.vendor_id = vd.vendor_id  "
											+ " JOIN vendor_details_info vdi ON ( vd.vendor_id =  vdi.vendor_id AND (CONCAT(DATE_FORMAT(`vdi`.`Transaction_Date`, '%Y%m%d'), `vdi`.`Sequence_Id`) = (SELECT MAX(CONCAT(DATE_FORMAT(`vdi1`.`Transaction_Date`, '%Y%m%d'), `vdi1`.`Sequence_Id`)) FROM `vendor_details_info` `vdi1` WHERE ((`vdi`.`vendor_id` = `vdi1`.`vendor_id`) AND (`vdi1`.`Transaction_Date` <= CURDATE())))) ) "
											+ " JOIN "
											+ " ( SELECT date_field FROM( SELECT MAKEDATE(YEAR('"+DateHelper.convertDateToSQLString(detailsInfo.getDateOfJoining())+"'),1) + INTERVAL (MONTH('"+DateHelper.convertDateToSQLString(detailsInfo.getDateOfJoining())+"')-1) MONTH + INTERVAL daynum DAY date_field "
											+ " FROM ( SELECT t*10+u daynum FROM "
											+ "             (SELECT 0 t UNION SELECT 1 UNION SELECT 2 UNION SELECT 3) A, "
											+ "            (SELECT 0 u UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 "
											+ "             UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 "
											+ "             UNION SELECT 8 UNION SELECT 9) B " + "        ORDER BY daynum "
											+ "     ) AA ) AAA WHERE date_field BETWEEN '"+DateHelper.convertDateToSQLString(detailsInfo.getDateOfJoining())+"' AND LAST_DAY('"+DateHelper.convertDateToSQLString(detailsInfo.getDateOfJoining())+"')   "
											+ " ) AS Month_date WHERE  " + " shift_name IS NOT NULL AND weekly_off IS NOT NULL "
											+ " AND date_field >= wdi.date_of_joining AND CASE WHEN wdi.`Date_Of_Leaving` IS NOT NULL THEN  date_field <= +" +  "  wdi.`Date_Of_Leaving` ELSE -1='-1' END "
											+ " AND (CONCAT(DATE_FORMAT(`wdi`.`Transaction_Date`, '%Y%m%d'), `wdi`.`Sequence_Id`) = (SELECT  "
											+ "                MAX(CONCAT(DATE_FORMAT(`wdi1`.`Transaction_Date`, '%Y%m%d'), `wdi1`.`Sequence_Id`)) "
											+ "             FROM " + "                `worker_details_info` `wdi1` "
											+ "             WHERE " + "                 ((`wdi`.`worker_id` = `wdi1`.`worker_id`) "
											+ "                     AND (`wdi`.`Transaction_Date` <= CURDATE())))) "
											+ " AND wd.worker_code = '"+workerCode+"'").list();
							
							
							 boolean flag2 = true;
							for(Object a :labourScheduledList){
								Object[] c = (Object[])a;									
								    if(flag2){
									 Query x = session.createSQLQuery("delete from labor_scheduled_shifts where MONTH(business_date) = MONTH('"+c[0]+"')  and CODE = '"+c[1]+"' and emp_code = '"+c[2]+"'");
									 x.executeUpdate();
									 flag2 = false;
								  }
								  
								String values = "'" + c[0] + "'" + ",'" + c[1] + "'" + ",'" + c[2] + "'" + ",'" + c[3] + "'" + ",'"
										+ c[4] + "','" + c[5] + "','" + c[6] + "'";
								Query q = session.createSQLQuery(
										"insert into labor_scheduled_shifts (business_date,CODE,emp_code,emp_name,shift,wo,ContractorName) values ("
												+ values + ")");
								
								q.executeUpdate();
							}	*/	
							
							 //session.flush();
							session.clear();
							
							
						} else {
							workerId = so.getId();							
							WorkerDetailsVo	workerDetailsVo =	new WorkerDetailsVo();
							workerDetailsVo.setWorkerCode(workerCode);		
							workerDetailsVo.setFirstName(products.get("First_Name"));
							workerDetailsVo.setLastName(products.get("Last_Name"));
							workerDetailsVo.setDateOfBirth(formatter.parse(products.get("Date_Of_Birth")));
							workerDetailsVo.setDateOfJoining(formatter.parse(products.get("Date_Of_Joining")));									
							workerDetailsVo.setReasonForStatus("<span style=\"color:red\">Worker Code Already Exists</span>");
							workerDetailsVo.setCustomerId(rowCount);
							errorsList.add(workerDetailsVo);							
						}
						
						//System.out.println(products.get("First_Name")+"::");
					} // Worker Code Checking If end
					//totalRecords++;

				} 
				//System.out.println("errorList:::"+errorsList.size());
				products.close();
				
			}else{
				WorkerDetailsVo	workerDetailsVo =	new WorkerDetailsVo();
				workerDetailsVo.setReasonForStatus("Upload CSV File only");
				workerDetailsVo.setCustomerId(-1);
				errorsList.add(workerDetailsVo);
			}

			
		} catch (Exception e) {
			WorkerDetailsVo	workerDetailsVo =	new WorkerDetailsVo();
			workerDetailsVo.setReasonForStatus("File Upload Failed");
			workerDetailsVo.setCustomerId(-1);
			errorsList.add(workerDetailsVo);
			log.error("Error Occured ",e);
		}
		
		return new ResponseEntity<List<WorkerDetailsVo>>((List<WorkerDetailsVo>) errorsList,HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/*=============================== Worker Education and Employment END ====================================*/
	
	
	
	
	/*=============================== Worker Verification Start ====================================*/
	
	
	
	@RequestMapping(value="/workerVerificationDetailsByWorkerInfoId.json", method = RequestMethod.POST)
	public ResponseEntity<List<WorkerDetailsVo>>  workerVerificationDetailsByWorkerInfoId(@RequestBody String workerInfoIdJson) throws Exception {		
		System.out.println("CustomerIdCompanyIdVendorIdJson::"+workerInfoIdJson);
		String workerInfoId = "";	   
		JsonParser jsonParser = new JsonParser();
		List<WorkerDetailsVo> workerVerificationDetails = new ArrayList();
		try{
		  JsonObject jo = (JsonObject)jsonParser.parse(workerInfoIdJson); 
		  
		if(workerInfoIdJson != null) {	   
			if(jo.get("workerInfoId") != null){
				workerInfoId =  jo.get("workerInfoId").getAsString();
			}			
			
		}			
		 workerVerificationDetails = workerService.workerVerificationDetailsByWorkerInfoId(workerInfoId);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<WorkerDetailsVo>>(workerVerificationDetails,HttpStatus.OK);
	}
	
	
	

	@RequestMapping(value = "/saveVerificationStatus.json", method = RequestMethod.POST)
	public Integer saveVerificationList(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile[] files) {
		Integer flag = -1;
		try{	
			flag = workerService.saveVerificationStatus(name,files,"");	
			
		}catch(Exception e){
			log.error("Error Occured ",e);
			flag = -1;
		}
		return flag;	
	}
	
	@RequestMapping(value="/validateWorkerAndSchedulingShifts.json", method = RequestMethod.POST)
	public ResponseEntity<Boolean>  validateWorkerAndSchedulingShifts(@RequestBody WorkerDetailsVo workerVo) throws Exception {		
		
		String workerInfoId = "";	   
		JsonParser jsonParser = new JsonParser();
		Boolean flag = false;
		try{
			flag = workerService.validateWorkerAndSchedulingShifts(workerVo);  
		}catch(Exception e){
			flag = false;
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Boolean>(flag,HttpStatus.OK);
	}
	
	
	
	
	@RequestMapping(value="/checkWorkerStatus.json", method = RequestMethod.POST)
	public ResponseEntity<Boolean>  checkWorkerStatus(@RequestBody WorkerDetailsVo workerVo) throws Exception {		
		
		String workerInfoId = "";	   
		JsonParser jsonParser = new JsonParser();
		Boolean flag = false;
		try{
			flag = workerService.checkWorkerStatus(workerVo);  
		}catch(Exception e){
			flag = false;
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Boolean>(flag,HttpStatus.OK);
	}
	
	
	
	/*=============================== Worker Verification End ====================================*/
	
	
	
	/*=============================== Worker Time Modification Start ====================================*/
	
	
	@RequestMapping(value = "/viewWorkerTimeDetailsForEditing.json", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Map<String,List>> viewWorkerTimeDetailsForEditing(@RequestBody WorkerDetailsVo detailsVo ) {
		
		 List<ShiftsDefineVo> shiftsDefineVos = new ArrayList<ShiftsDefineVo>();		 
		 Map<String,List> returnList = new HashMap<String,List>();
		 
		 shiftsDefineVos = shiftsDefineService.getShiftDefineGridData(detailsVo.getCustomerId(),detailsVo.getCompanyId(),"Y","",""); 
		 List<LabourTimeVo> laborTimeList = 	workerService.viewWorkerTimeDetailsForEditing(detailsVo);
		 List<ShiftsDefineVo> availableShifts = workerService.getAvailableShifts(new ShiftsDefineVo());
		 returnList.put("availableShifts", availableShifts);
		 returnList.put("laborTimeList", laborTimeList);
		 returnList.put("shiftsDefineVos", shiftsDefineVos);
		 return new ResponseEntity<Map<String,List>>(returnList,HttpStatus.OK);
	}
	
	
	
	
	@RequestMapping(value = "/saveModifiedLaborTimeDetails.json", method = RequestMethod.POST)
	public @ResponseBody  ResponseEntity<Integer> saveModifiedLaborTimeDetails(@RequestBody WorkerDetailsVo detailsVo) {
			System.out.println(detailsVo.getLabourTimeDetails().size()+"::modifiedTimeDetailsJson");
		Integer flag = workerService.saveModifiedLaborTimeDetails(detailsVo);			
		return new  ResponseEntity<Integer>(flag,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/manageTimeAndShiftFileUpload.json", method = RequestMethod.POST)
	public @ResponseBody  ResponseEntity<Map<String, List>> ManageTimeAndShiftFileUpload(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file) {
		UploadStatusVo statusMessageVo = new UploadStatusVo();		
		Session session = sessionFactory.getCurrentSession();
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(name);
		int customerId = 0;
		int companyId = 0;
		int createdBy = 0;
		int modifiedBy = 0;
		String dbSchema = "";
		List<UploadStatusVo> flag = new ArrayList();
		 Map<String,List> returnList = new HashMap<String,List>();
		
		if (jo.get("customerId") != null && jo.get("customerId").getAsInt() > 0) {
			customerId = jo.get("customerId").getAsInt();
		}

		if (jo.get("customerId") != null && jo.get("customerId").getAsInt() > 0) {
			companyId = jo.get("companyId").getAsInt();
		}
		
		if (jo.get("createdBy") != null && jo.get("createdBy").getAsInt() > 0) {
			createdBy = jo.get("createdBy").getAsInt();
		}
		if (jo.get("modifiedBy") != null && jo.get("modifiedBy").getAsInt() > 0) {
			modifiedBy = jo.get("modifiedBy").getAsInt();
		}
		if (jo.get("schemaName") != null && !jo.get("schemaName").isJsonNull() &&  !jo.get("schemaName").getAsString().equalsIgnoreCase("null") && jo.get("schemaName").getAsString() != "") {
			dbSchema =jo.get("schemaName").getAsString();
		}else{
			dbSchema = dbSchemaName;
		}

		
		
		try {

			//Integer workerId = 0;		
			String workerCode = null;
			String vendorCode = null;
			String workerName = null;
			String vendorName = null;
			String shift = null;
			Date businessDate = null;
			String attendanceStatus = null;
			
			
			
			List<UploadStatusVo> errorsList = new ArrayList();
			
			
			int rowCount = 0; 
			int totalNoOfRecordsInsered = 0;
			int noOfRecordsHavingErrors = 0;
			boolean saveRecorsFlag = true;
			
			if (file != null && !file.isEmpty() ) {
				CsvReader products = new CsvReader(file.getInputStream(), Charset.forName("UTF8"));
				CsvReader tempProducts = new CsvReader(file.getInputStream(), Charset.forName("UTF8"));
				products.readHeaders();
				
				CsvReader forWorkCodeChecking = new CsvReader(file.getInputStream(), Charset.forName("UTF8"));
				
				System.out.println(products.getHeader(1));
				
			for(String s : products.getHeaders()){
				System.out.println(s);
			}
				
				while (products.readRecord() && noOfRecordsHavingErrors <=10) {
					rowCount++;
					
					boolean errorflag = true;
					DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");	
					DateFormat formatterForTime = new SimpleDateFormat("dd-MM-yyyy HH:mm");	
					
						if(products.get("Vendor_Code") != null && !products.get("Vendor_Code").isEmpty()){
							vendorCode = products.get("Vendor_Code");
						}else{
							errorflag = false;	
						UploadStatusVo	statusVo =	this.retunUploadStatusVo(products.get("Vendor_Code"),products.get("Vendor_Name"),products.get("Worker_Code"),products.get("Worker_Name"),products.get("Business_Date"),products.get("Shift"),products.get("Attendance_Status"), null, null);
						statusVo.setErrorDescription("<span style=\"color:red\">Missing Vendor Code</span>");
						statusVo.setLineNumber(rowCount);
						errorsList.add(statusVo);
							noOfRecordsHavingErrors++;
							saveRecorsFlag = false;
							
						}
						
						if(errorflag && products.get("Vendor_Name") != null && !products.get("Vendor_Name").isEmpty()){
							vendorName = products.get("Vendor_Name");
						}else if(errorflag){
							errorflag = false;	
							UploadStatusVo statusVo =	this.retunUploadStatusVo(products.get("Vendor_Code"),products.get("Vendor_Name"),products.get("Worker_Code"),products.get("Worker_Name"),products.get("Business_Date"),products.get("Shift"),products.get("Attendance_Status"), null, null);
							statusVo.setErrorDescription("<span style=\"color:red\">Missing Vendor Name</span>");
							statusVo.setLineNumber(rowCount);
							errorsList.add(statusVo);
							noOfRecordsHavingErrors++;
							saveRecorsFlag = false;
							
						}
						
						if(errorflag && products.get("Worker_Code") != null && !products.get("Worker_Code").isEmpty()){
							workerCode = products.get("Worker_Code");	
							forWorkCodeChecking.readHeaders();
							int workerCodeCount = 0;
							while (forWorkCodeChecking.readRecord()){
								System.out.println(forWorkCodeChecking.get("Worker_Code"));
								if(forWorkCodeChecking.get("Worker_Code") != null && workerCode.equalsIgnoreCase(forWorkCodeChecking.get("Worker_Code")) && products.get("Business_Date").equalsIgnoreCase(forWorkCodeChecking.get("Business_Date"))  && workerCodeCount <= 2  ){
									workerCodeCount++;
								}
								
								if(workerCodeCount >= 2){
									errorflag = false;	
									UploadStatusVo statusVo =	this.retunUploadStatusVo(products.get("Vendor_Code"),products.get("Vendor_Name"),products.get("Worker_Code"),products.get("Worker_Name"),products.get("Business_Date"),products.get("Shift"),products.get("Attendance_Status"), null, null);
									statusVo.setErrorDescription("<span style=\"color:red\">Duplicate Worker Code</span>");
									statusVo.setLineNumber(rowCount);
									errorsList.add(statusVo);
									noOfRecordsHavingErrors++;
									saveRecorsFlag = false;
								}
							}
							
							
							
						}else  if(errorflag){
							errorflag = false;	
							UploadStatusVo statusVo =	this.retunUploadStatusVo(products.get("Vendor_Code"),products.get("Vendor_Name"),products.get("Worker_Code"),products.get("Worker_Name"),products.get("Business_Date"),products.get("Shift"),products.get("Attendance_Status"), null, null);
							statusVo.setErrorDescription("<span style=\"color:red\">Missing Worker Code</span>");
							statusVo.setLineNumber(rowCount);
							errorsList.add(statusVo);
							noOfRecordsHavingErrors++;
							saveRecorsFlag = false;
							
						}
						
						
						if(errorflag && products.get("Worker_Name") != null && !products.get("Worker_Name").isEmpty()){
							workerName = products.get("Worker_Name");
						}else if(errorflag){
							errorflag = false;	
							UploadStatusVo statusVo =	this.retunUploadStatusVo(products.get("Vendor_Code"),products.get("Vendor_Name"),products.get("Worker_Code"),products.get("Worker_Name"),products.get("Business_Date"),products.get("Shift"),products.get("Attendance_Status"), null, null);
							statusVo.setErrorDescription("<span style=\"color:red\">Missing Worker Name</span>");
							statusVo.setLineNumber(rowCount);
							errorsList.add(statusVo);
							noOfRecordsHavingErrors++;
							saveRecorsFlag = false;
							
						}
											
						if(errorflag && products.get("Shift") != null && !products.get("Shift").isEmpty()){
							shift = products.get("Shift");
						}else if(errorflag){
							errorflag = false;
							UploadStatusVo statusVo =	this.retunUploadStatusVo(products.get("Vendor_Code"),products.get("Vendor_Name"),products.get("Worker_Code"),products.get("Worker_Name"),products.get("Business_Date"),products.get("Shift"),products.get("Attendance_Status"), null, null);
							statusVo.setErrorDescription("<span style=\"color:red\">Missing Shift</span>");
							statusVo.setLineNumber(rowCount);
							errorsList.add(statusVo);
							noOfRecordsHavingErrors++;
							saveRecorsFlag = false;
							
						}
																	
						if(errorflag && products.get("Business_Date") != null && !products.get("Business_Date").isEmpty()){
							try{
							businessDate = formatter.parse(products.get("Business_Date"));
							}catch(Exception e){
								errorflag = false;
								UploadStatusVo statusVo =	this.retunUploadStatusVo(products.get("Vendor_Code"),products.get("Vendor_Name"),products.get("Worker_Code"),products.get("Worker_Name"),products.get("Business_Date"),products.get("Shift"),products.get("Attendance_Status"),null, null);
								statusVo.setErrorDescription("<span style=\"color:red\">Business Date Format Should be dd-MM-yyyy</span>");
								statusVo.setLineNumber(rowCount);
								errorsList.add(statusVo);
								noOfRecordsHavingErrors++;
								saveRecorsFlag = false;
							}
							
						}else if(errorflag){
							errorflag = false;
							UploadStatusVo statusVo =	this.retunUploadStatusVo(products.get("Vendor_Code"),products.get("Vendor_Name"),products.get("Worker_Code"),products.get("Worker_Name"),products.get("Business_Date"),products.get("Shift"),products.get("Attendance_Status"),null, null);
							statusVo.setErrorDescription("<span style=\"color:red\">Missing Business Date</span>");
							statusVo.setLineNumber(rowCount);
							errorsList.add(statusVo);
							noOfRecordsHavingErrors++;
							saveRecorsFlag = false;
							
						}
						
						/*if(errorflag && products.get("Attendance_Status") != null && !products.get("Attendance_Status").isEmpty()){						
							attendanceStatus = products.get("Attendance_Status");	
						}else if(errorflag){
							errorflag = false;
							UploadStatusVo statusVo =	this.retunUploadStatusVo(products.get("Vendor_Code"),products.get("Vendor_Name"),products.get("Worker_Code"),products.get("Worker_Name"),products.get("Business_Date"),products.get("Shift"),products.get("Attendance_Status"),null, null);
							statusVo.setErrorDescription("<span style=\"color:red\">Missing Attendance Status</span>");
							statusVo.setLineNumber(rowCount);
							errorsList.add(statusVo);
							noOfRecordsHavingErrors++;
							saveRecorsFlag = false;
							
						}*/
						
						
						if(errorflag && products.get("In_Time") != null && !products.get("In_Time").isEmpty()){		
							try{
							formatterForTime.parse(products.get("In_Time"));
							//String x = products.get("In_Time").split("-")[0];
							// String y = products.get("In_Time").split("-")[1];
							String z = products.get("In_Time").split("-")[2];
							if(z.length() > 10){
								errorflag = false;
								UploadStatusVo statusVo =	this.retunUploadStatusVo(products.get("Vendor_Code"),products.get("Vendor_Name"),products.get("Worker_Code"),products.get("Worker_Name"),products.get("Business_Date"),products.get("Shift"),products.get("Attendance_Status"),null, null);
								statusVo.setErrorDescription("<span style=\"color:red\">In Time Format Should be dd-MM-yyyy HH:mm</span>");
								statusVo.setLineNumber(rowCount);
								errorsList.add(statusVo);
								noOfRecordsHavingErrors++;
								saveRecorsFlag = false;
							}
							}catch(Exception e){
							errorflag = false;
							UploadStatusVo statusVo =	this.retunUploadStatusVo(products.get("Vendor_Code"),products.get("Vendor_Name"),products.get("Worker_Code"),products.get("Worker_Name"),products.get("Business_Date"),products.get("Shift"),products.get("Attendance_Status"),null, null);
							statusVo.setErrorDescription("<span style=\"color:red\">In Time Format Should be dd-MM-yyyy HH:mm</span>");
							statusVo.setLineNumber(rowCount);
							errorsList.add(statusVo);
							noOfRecordsHavingErrors++;
							saveRecorsFlag = false;
							}
						}
						
						if(errorflag && products.get("Out_Time") != null && !products.get("Out_Time").isEmpty()){		
							try{
							formatterForTime.parse(products.get("Out_Time"));
							}catch(Exception e){
							errorflag = false;
							UploadStatusVo statusVo =	this.retunUploadStatusVo(products.get("Vendor_Code"),products.get("Vendor_Name"),products.get("Worker_Code"),products.get("Worker_Name"),products.get("Business_Date"),products.get("Shift"),products.get("Attendance_Status"),null, null);
							statusVo.setErrorDescription("<span style=\"color:red\">Out Time Format Should be dd-MM-yyyy HH:mm</span>");
							statusVo.setLineNumber(rowCount);
							errorsList.add(statusVo);
							noOfRecordsHavingErrors++;
							saveRecorsFlag = false;
							}
						}
						
						
										
					}// CSV File Reader Ending
				
					
					returnList.put("errorsList", errorsList);
				
								
				
						
							// Saving or Updating the Shift or Time Details
							tempProducts.readHeaders();
						while (tempProducts.readRecord() && saveRecorsFlag) {
							rowCount++;
							boolean errorflag = true;
							DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");							
							vendorCode = tempProducts.get("Vendor_Code");
							vendorName = tempProducts.get("Vendor_Name");
							workerCode = tempProducts.get("Worker_Code");
					     	workerName = tempProducts.get("Worker_Name");							
							shift = tempProducts.get("Shift");	
							System.out.println(tempProducts.get("Business_Date")+"::::asdf");
																					
							attendanceStatus = tempProducts.get("Attendance_Status");
							String inTime = tempProducts.get("In_Time");
							String outTime = tempProducts.get("Out_Time");	
							String manHours = tempProducts.get("Man_Hours");
							if(manHours.length() == 1 || manHours.length() == 2){
								manHours = manHours+":00:00";
							}
							
							String otHours = tempProducts.get("OT_Hours");	
							
							Date newBusinessdate = null;
							Date actualBusinessDate = (Date) formatter.parse(tempProducts.get("Business_Date"));
							
							/*List vendorDetails = session.createSQLQuery(" SELECT Vv.vendor_name,replace((CONCAT(RTRIM(vw.first_name), ' ',CASE WHEN (vw.middle_name IS NULL) THEN '' ELSE CONCAT(vw.RTRIM(middle_name),' ') END, vw.last_name)),'  ',' ')  AS workerName  FROM `vendor_view` vv "+
																		" INNER JOIN `v_worker_details` vw ON(vv.vendor_id = vw.vendor_id) WHERE vw.worker_code  = '"+workerCode+"' and  vv.vendor_code = '"+vendorCode+"' " ).list();
							String newVenodorName = null;
							String newWorkerName = null;
							for(Object o :vendorDetails){
								Object[]  a = (Object[]) o;
								newVenodorName = a[0]+"";
								newWorkerName = a[1]+"";
								break;
							}
							*/
							
							//if(newVenodorName != null && newWorkerName != null){

								/*List laborTimeList = session.createSQLQuery("select * from "+dbSchema+".labor_time_report where emp = '"+workerCode+"' and ContractorCode = '"+vendorCode+"' and Business_Date = DATE_FORMAT( STR_TO_DATE('"+tempProducts.get("Business_Date")+"','%d-%m-%Y'),'%Y-%m-%d')").list();
								
								if(laborTimeList.size() > 0){
									Query q1 = session.createSQLQuery("DELETE FROM "+dbSchema+".labor_time_report where emp = '"+workerCode+"' and ContractorCode = '"+vendorCode+"' and Business_Date = DATE_FORMAT( STR_TO_DATE('"+tempProducts.get("Business_Date")+"','%d-%m-%Y'),'%Y-%m-%d')");
									q1.executeUpdate();	
								}	*/
							
							String sq = " SELECT wd.worker_id,wd.vendor_id,wjd.location_id,wjd.plant_id,wjd.department_id,wjd.section_id,wjd.work_area_id,wjd.work_skill, "
										+ " jcdi.job_name "
										+ " FROM worker_details wd  "
										+ " INNER JOIN work_job_details wjd ON (wjd.worker_id = wd.worker_id) "
										+ " INNER JOIN job_code_details_info jcdi ON (jcdi.job_code_id = wjd.job_name) "
										+ " WHERE "
										+ " CONCAT(DATE_FORMAT(wjd.transaction_date, '%Y%m%d'), wjd.Sequence_Id) =  "
										+ " (  "
										+ " SELECT MAX(CONCAT(DATE_FORMAT(wjd1.transaction_date, '%Y%m%d'), wjd1.Sequence_Id))  "
										+ " FROM work_job_details wjd1  "
										+ " WHERE wjd.`Worker_Id` = wjd1.`Worker_Id`  AND wjd1.`Company_Id` = wjd.`Company_Id`  AND wjd1.`Customer_Id` = wjd.customer_id  "
										+ "  AND wjd1.transaction_date <= CURRENT_DATE()  "
										+ " ) AND  "										
										+ " CONCAT(DATE_FORMAT(jcdi.transaction_date, '%Y%m%d'), jcdi.`Job_Code_Sequence_Id`) =  "
										+ " (  "
										+ " SELECT MAX(CONCAT(DATE_FORMAT(wjd1.transaction_date, '%Y%m%d'), wjd1.Job_Code_Sequence_Id))  "
										+ " FROM job_code_details_info wjd1  "
										+ " WHERE jcdi.`job_code_id` = wjd1.`job_code_id`  "
										+ "  AND jcdi.transaction_date <= CURRENT_DATE()  "
										+ " ) AND wd.worker_code ='"+workerCode+"'";
							
							
							List tempList =  session.createSQLQuery(sq).list();
							Integer workerId = 0;
							Integer vendorId = 0;
							Integer locationId = 0;
							Integer plantId = 0;
							//Integer departmentId = 0;
							//Integer sectionId = 0;
							//Integer workAreaId = 0;
							String workSkill = null;
							String designation = null;
							for(Object obj:tempList){
								Object[] ob = (Object[])obj;
								workerId = (Integer) ob[0];
								vendorId = (Integer) ob[1];	
								locationId = (Integer) ob[1];
								plantId = (Integer) ob[1];	
								///departmentId = (Integer) ob[1];	
							//	sectionId = (Integer) ob[1];	
							//	workAreaId = (Integer) ob[1];	
								workSkill = (String) ob[7];	
								designation = (String) ob[8];									
							}	
								
							
						Query q11 = session.createSQLQuery("replace into "+dbSchema+".labor_time_report (ContractorCode,ContractorName,Emp,Emp_Name,Designation,"
								+ " Shift,Intime,OutTime,Attendance,ManHours,OTHours,Business_Date,Created_By,Created_Date,Modified_By,Modified_Date,Customer_Id, "
								+ " Company_Id,Vendor_Id,Worker_Id,work_skill,location_id,plant_id) "
								+ " values('"+vendorCode+"','"+vendorName+"','"+workerCode+"','"+workerName+"' ,'"+designation+"','"+shift+"',"
								+ ((inTime != null && inTime != "") ? "DATE_FORMAT( STR_TO_DATE('"+inTime+"','%d-%m-%Y %H:%i:%s'),'%Y-%m-%d %H:%i:%s')":null)+","
								+ (outTime != null && outTime != "" ? "DATE_FORMAT( STR_TO_DATE('"+outTime+"','%d-%m-%Y %H:%i:%s'),'%Y-%m-%d %H:%i:%s')" :null)+",'"
								+ attendanceStatus+"',"+((manHours != null && manHours != "")  ? "'"+manHours+"'":null)+","+((otHours != null && otHours != "")  ? "'"
								+ otHours+"'":0)+",DATE_FORMAT( STR_TO_DATE('"+tempProducts.get("Business_Date")+"','%d-%m-%Y'),'%Y-%m-%d'),'"+createdBy+"','"
								+ DateHelper.convertDateTimeToSQLString(new Date())+"','"+modifiedBy+"','"+DateHelper.convertDateTimeToSQLString(new Date())+ "','"
								+ customerId + "','" + companyId + "','" + vendorId + "','" + workerId +"','" + workSkill + "','" + locationId + "','" + plantId + "') ");
							q11.executeUpdate();
														
							
							
								
						}
						if(!saveRecorsFlag){
							statusMessageVo.setFlag(0);
							statusMessageVo.setDescription("File upload Failed, Errors in File");
							flag.add(statusMessageVo);
							returnList.put("flag", flag);
						}	else if(saveRecorsFlag){
							statusMessageVo.setFlag(1);
							statusMessageVo.setDescription("File Uploaded Successfully");
							flag.add(statusMessageVo);
							returnList.put("flag", flag);
						}
				
				} else{
			
				statusMessageVo.setFlag(0);
				statusMessageVo.setDescription("Upload CSV File Only");
				flag.add(statusMessageVo);
				returnList.put("falg", flag);
			}

			
		} catch (Exception e) {
			e.printStackTrace();
			statusMessageVo.setFlag(0);
			statusMessageVo.setDescription("File Upload Failed");
			flag.add(statusMessageVo);
			returnList.put("flag", flag);
			log.error("Error Occured ",e);
		}
		
		return new ResponseEntity<Map<String,List>>(returnList,HttpStatus.OK);
	}
	
	
		public UploadStatusVo retunUploadStatusVo (String vendorCode,String vendorName,String workerCode,String workerName,String businessDate,String shift,String attendanceStatus, String weeklyOff, String monthYear){
			UploadStatusVo statusVo =  new UploadStatusVo();
			statusVo.setVendorCode(vendorCode);
			statusVo.setVendorName(vendorName);
			statusVo.setWorkerCode(workerCode);
			statusVo.setWorkerName(workerName);
			statusVo.setBusinessDate(businessDate);
			statusVo.setAttendanceStatus(attendanceStatus);
			statusVo.setWeeklyOff(weeklyOff);
			statusVo.setMonthYear(monthYear);
			statusVo.setShift(shift);
			return statusVo;
		}
	
	
	/*=============================== Worker Time Modification End ====================================*/
	
	
	
	@RequestMapping(value = "/validateWorkerLimit.json", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Map<String,SimpleObject>> validateWorkerLimit(@RequestBody WorkerDetailsVo detailsVo ) {
		
		SimpleObject object = new SimpleObject();
		 
		 List<SimpleObject> workerCount = 	vendorComplianceService.getNumberOfWorkersCovered(detailsVo.getCustomerId(), detailsVo.getCompanyId(), detailsVo.getVendorId());
		 SearchCriteriaVo searchCriteriaVo = new SearchCriteriaVo();
		 searchCriteriaVo.setCustomerId(detailsVo.getCustomerId());
		 searchCriteriaVo.setCompanyId(detailsVo.getCompanyId());
		 searchCriteriaVo.setVendorId(detailsVo.getVendorId());
		 searchCriteriaVo.setIsActive(detailsVo.getIsActive());
		 searchCriteriaVo.setWorkerName("");
		 searchCriteriaVo.setWorkerId(0);
		 searchCriteriaVo.setWorkerCode("");
		 searchCriteriaVo.setStatus(detailsVo.getIsActive()==null?"Y":detailsVo.getIsActive());
		 List<WorkerDetailsVo> numOfWorkers = workerService.workerGridDetails(searchCriteriaVo);

		Map returnMap = new HashMap();
		
		//Need to check whether the worker code is automatic/manual, if automatic then 
		SimpleObject maxWorkerCode = workerService.getMaximumWorkerCode(detailsVo);
		returnMap.put("MaxWorkerCode",maxWorkerCode );
		
		
		 if( workerCount != null && workerCount.size() > 0 && workerCount.get(0).getId() > 0 ){
			 object.setId(workerCount.get(0).getId());
			 object.setName("Limit Not Exceed");
		 }else{
			 object.setId(0);
			 object.setName("Worker cannot be registered to the system as you have reached maximum worker limit ("+workerCount.get(0).getId()+") ");
		 }
		/* List workerLimit = new ArrayList();
		 workerLimit.add(object);*/
		 returnMap.put("workersLimit", object);
		 return new ResponseEntity<Map<String,SimpleObject>>(returnMap, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/uploadWorkerShift.json", method = RequestMethod.POST)
	public @ResponseBody  ResponseEntity<Map<String, List>> uploadWorkerShift(@RequestParam("name") String name,@RequestParam("file") MultipartFile file) {
		
		Date start = new Date();
		Session session = sessionFactory.getCurrentSession();
		
		UploadStatusVo statusMessageVo = new UploadStatusVo();	
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(name);
		
		int customerId = 0;
		int companyId = 0;
		int countryId = 0;
		int createdBy = 0;
		int modifiedBy = 0;
	//	String dbSchema = "";
		String mon = null;
		
		int errCount = 0;
		String year = null;
		int month = 0;
		
		
		List<UploadStatusVo> flag = new ArrayList();
		Map<String, List> errorMessages = new HashMap();
		
		if (jo.get("customerId") != null && jo.get("customerId").getAsInt() > 0) {
			customerId = jo.get("customerId").getAsInt();
		}

		if (jo.get("companyId") != null && jo.get("companyId").getAsInt() > 0) {
			companyId = jo.get("companyId").getAsInt();
		}
		
		if (jo.get("countryId") != null && jo.get("countryId").getAsInt() > 0) {
			countryId = jo.get("countryId").getAsInt();
		}
		
		if (jo.get("createdBy") != null && jo.get("createdBy").getAsInt() > 0) {
			createdBy = jo.get("createdBy").getAsInt();
		}
		
		if (jo.get("modifiedBy") != null && jo.get("modifiedBy").getAsInt() > 0) {
			modifiedBy = jo.get("modifiedBy").getAsInt();
		}
		
		if (jo.get("month") != null && !jo.get("month").getAsString().equalsIgnoreCase("null")) {
			mon = jo.get("month").getAsString();
		}
		
		if (jo.get("year") != null && !jo.get("year").getAsString().equalsIgnoreCase("null") && jo.get("year").getAsString().length() == 4 ) {
			year = jo.get("year").getAsString();
		}
		
		System.out.println(mon+"-"+year.substring(2,4));
		
		if(mon != null && mon.length() == 3){
			month = DateHelper.getMonth(mon);
		}
		
		
		/*if ( !jo.get("schemaName").toString().equalsIgnoreCase("null") &&  jo.get("schemaName") != null && jo.get("schemaName").getAsString() != "" ) {
			dbSchema =jo.get("schemaName").getAsString();
		}else{
			dbSchema = dbSchemaName;
		}*/
		
		
		try {
			String workerCode =  null;
			String vendorCode = null;
			String vendorName = null;
			String workerName = null;
			String weeklyOff = null;
			String shift = null;
			String monthYear = null;
			
			Integer vendorId = 0;
			Integer workerId = 0;
			int errorCount = 0;
			boolean saveFlag = true;
			
			List<UploadStatusVo> errorsList = new ArrayList();
			
			if (file != null && !file.isEmpty()) {

				CsvReader validateReader = new CsvReader(file.getInputStream(), Charset.forName("UTF8"));
				validateReader.readHeaders();
				
				List shiftRawExists = new ArrayList();
				List<UploadStatusVo> fileList = new ArrayList();
				boolean errFlag;
				
				//To check whether there are any errors in the file
				while (validateReader.readRecord() && errorCount <= 10) {
					UploadStatusVo status = new UploadStatusVo();
					errFlag = true;
					log.info("validateReader :::  "+validateReader);
								
					if(validateReader.get("Month/Year") != null && !validateReader.get("Month/Year").isEmpty() && validateReader.get("Month/Year").equalsIgnoreCase(mon+"-"+year.substring(2,4))){
						monthYear = validateReader.get("Month/Year");
						status.setMonthYear(monthYear);
					}else{
						errFlag = false;	
						UploadStatusVo	statusVo =	this.retunUploadStatusVo(validateReader.get("Vendor Code"), validateReader.get("Vendor Name"), validateReader.get("Worker Code"), validateReader.get("Worker Name"), null, validateReader.get("Shift"), null, validateReader.get("WO"), validateReader.get("Month/Year"));						
						statusVo.setErrorDescription("<span style=\"color:red\">Month/Year must be selected Month and Year in MMM-YY Format</span>");
						errorsList.add(statusVo);
						errorCount++;
						saveFlag = false;
					}
					
					if(validateReader.get("Vendor Code") != null && !validateReader.get("Vendor Code").isEmpty()){
						vendorCode = validateReader.get("Vendor Code");
						status.setVendorCode(vendorCode);
					}else{
						errFlag = false;	
						UploadStatusVo	statusVo =	this.retunUploadStatusVo(validateReader.get("Vendor Code"), validateReader.get("Vendor Name"), validateReader.get("Worker Code"), validateReader.get("Worker Name"), null, validateReader.get("Shift"), null, validateReader.get("WO"), validateReader.get("Month/Year"));						
						statusVo.setErrorDescription("<span style=\"color:red\">Missing Vendor Code</span>");
						errorsList.add(statusVo);
						errorCount++;
						saveFlag = false;
					}
					
					if(validateReader.get("Vendor Name") != null && !validateReader.get("Vendor Name").isEmpty()){
						vendorName = validateReader.get("Vendor Name");
						status.setVendorName(vendorName);
					}else{
						errFlag = false;	
						UploadStatusVo	statusVo =	this.retunUploadStatusVo(validateReader.get("Vendor Code"), validateReader.get("Vendor Name"), validateReader.get("Worker Code"), validateReader.get("Worker Name"), null, validateReader.get("Shift"), null, validateReader.get("WO"), validateReader.get("Month/Year"));						
						statusVo.setErrorDescription("<span style=\"color:red\">Missing Vendor name</span>");
						errorsList.add(statusVo);
						errorCount++;
						saveFlag = false;
					}
					
					if(validateReader.get("Worker Code") != null && !validateReader.get("Worker Code").isEmpty()){
						workerCode =  validateReader.get("Worker Code");
						status.setWorkerCode(workerCode);
					}else{
						errFlag = false;	
						UploadStatusVo	statusVo =	this.retunUploadStatusVo(validateReader.get("Vendor Code"), validateReader.get("Vendor Name"), validateReader.get("Worker Code"), validateReader.get("Worker Name"), null, validateReader.get("Shift"), null, validateReader.get("WO"), validateReader.get("Month/Year"));						
						statusVo.setErrorDescription("<span style=\"color:red\">Missing Worker Code</span>");
						errorsList.add(statusVo);
						errorCount++;
						saveFlag = false;
					}
					
					if(validateReader.get("Worker Name") != null && !validateReader.get("Worker Name").isEmpty()){
						workerName = validateReader.get("Worker Name");
						status.setWorkerName(workerName);
					}else{
						errFlag = false;	
						UploadStatusVo	statusVo =	this.retunUploadStatusVo(validateReader.get("Vendor Code"), validateReader.get("Vendor Name"), validateReader.get("Worker Code"), validateReader.get("Worker Name"), null, validateReader.get("Shift"), null, validateReader.get("WO"), validateReader.get("Month/Year"));						
						statusVo.setErrorDescription("<span style=\"color:red\">Missing Worker Name</span>");
						errorsList.add(statusVo);
						errorCount++;
						saveFlag = false;
					}	
					
					if(validateReader.get("Shift") != null && !validateReader.get("Shift").isEmpty()){
						shift= validateReader.get("Shift");
						status.setShift(shift);
					}else{
						errFlag = false;	
						UploadStatusVo	statusVo =	this.retunUploadStatusVo(validateReader.get("Vendor Code"), validateReader.get("Vendor Name"), validateReader.get("Worker Code"), validateReader.get("Worker Name"), null, validateReader.get("Shift"), null, validateReader.get("WO"), validateReader.get("Month/Year"));						
						statusVo.setErrorDescription("<span style=\"color:red\">Missing Shift</span>");
						errorsList.add(statusVo);
						errorCount++;
						saveFlag = false;
					}
					
					if(validateReader.get("WO") != null && !validateReader.get("WO").isEmpty() && validateReader.get("WO").trim().length() == 3 ){
						weeklyOff = validateReader.get("WO");
						status.setWeeklyOff(weeklyOff);
						if(!validateWeeklyOff(weeklyOff)){
							errFlag = false;	
							UploadStatusVo	statusVo =	this.retunUploadStatusVo(validateReader.get("Vendor Code"), validateReader.get("Vendor Name"), validateReader.get("Worker Code"), validateReader.get("Worker Name"), null, validateReader.get("Shift"), null, validateReader.get("WO"), validateReader.get("Month/Year"));						
							statusVo.setErrorDescription("<span style=\"color:red\">Invalid WO Format</span>");
							errorsList.add(statusVo);
							errorCount++;
							saveFlag = false;
						}
					}else{
						errFlag = false;	
						UploadStatusVo	statusVo =	this.retunUploadStatusVo(validateReader.get("Vendor Code"), validateReader.get("Vendor Name"), validateReader.get("Worker Code"), validateReader.get("Worker Name"), null, validateReader.get("Shift"), null, validateReader.get("WO"), validateReader.get("Month/Year"));						
						statusVo.setErrorDescription("<span style=\"color:red\">Missing WO</span>");
						errorsList.add(statusVo);
						errorCount++;
						saveFlag = false;
					}
					
					
					if(monthYear != null && vendorCode != null && workerCode != null && fileList != null && fileList.size() > 0 && weeklyOff != null && shift != null ){
						for(UploadStatusVo upload : fileList){
							if(monthYear.equalsIgnoreCase(upload.getMonthYear()) && vendorCode.equalsIgnoreCase(upload.getVendorCode()) && workerCode.equalsIgnoreCase(upload.getWorkerCode())){
								System.out.println(monthYear.equalsIgnoreCase(upload.getMonthYear()));
								errFlag = false;	
								UploadStatusVo	statusVo =	this.retunUploadStatusVo(vendorCode, vendorName, workerCode, workerName, null, shift, null, weeklyOff, monthYear);						
								System.out.println(monthYear+":::"+upload.getMonthYear());
								System.out.println(vendorCode+"::::"+upload.getVendorCode());
								System.out.println(workerCode+":::::"+upload.getWorkerCode());
								statusVo.setErrorDescription("<span style=\"color:red\">Duplicate data found in file</span>");
								errorsList.add(statusVo);
								errorCount++;
								saveFlag = false;
							}
						}
							
					}
					fileList.add(status);
				}
				errorMessages.put("errorsList", errorsList);
				
				CsvReader reader = new CsvReader(file.getInputStream(), Charset.forName("UTF8"));
				reader.readHeaders();
				
				BigInteger sequenceId = (BigInteger)session.createSQLQuery("SELECT COALESCE(MAX(Sequence_Id),0) FROM labor_shift_raw ").list().get(0);
				
				int sequence = 1;
				
				if(sequenceId != null){
					sequence = sequenceId.intValue() +1;
				}
				
				if(errorsList == null || (errorsList != null && errorsList.isEmpty()) &&  fileList != null && fileList.isEmpty()){
					saveFlag = false;
				}
		
				while (reader.readRecord() && saveFlag) {
					errFlag = true;
					monthYear = reader.get("Month/Year");
					vendorCode = reader.get("Vendor Code");
					vendorName = reader.get("Vendor Name");
					workerCode =  reader.get("Worker Code");
					workerName = reader.get("Worker Name");
					shift= reader.get("Shift");
					weeklyOff = reader.get("WO");
					
					WorkerDetailsInfo worker = new WorkerDetailsInfo();
					String venName = null;
					String workrName = null;
					
					// To get latest vendor name
					List venNameList = session.createSQLQuery("SELECT Vendor_Name, vd.Vendor_Id FROM vendor_details_info vdi LEFT JOIN Vendor_details vd ON vdi.Customer_Id = vd.Customer_Id AND vdi.Company_Id = vd.Company_Id AND vdi.Vendor_Id = vd.Vendor_Id WHERE CONCAT(DATE_FORMAT(vdi.Transaction_Date,'%Y%m%d'), LPAD(vdi.Sequence_Id,2,'0')) = (SELECT MAX(CONCAT(DATE_FORMAT(vdi1.Transaction_Date,'%Y%m%d'),LPAD(vdi1.Sequence_Id,2,'0'))) FROM vendor_details_info vdi1 WHERE vdi.Vendor_Id = vdi1.Vendor_Id AND vdi1.Transaction_Date <= CURRENT_DATE() ) AND Vendor_Code = '"+vendorCode+"' ").list();
					
					//To get Latest worker name and worker id
					List workerNameList = session.createSQLQuery("SELECT replace((CONCAT(RTRIM(first_name), ' ',CASE WHEN (middle_name IS NULL) THEN '' ELSE CONCAT(RTRIM(middle_name),' ') END, last_name)),'  ',' ') , wd.Worker_Id FROM worker_details_info wdi LEFT JOIN Worker_details wd ON wdi.Customer_Id = wd.Customer_Id AND wdi.Company_Id = wd.Company_Id  AND wdi.Worker_Id = wd.Worker_Id WHERE CONCAT(DATE_FORMAT(wdi.Transaction_Date,'%Y%m%d'), LPAD(wdi.Sequence_Id,2,'0')) = (SELECT MAX(CONCAT(DATE_FORMAT(wdi1.Transaction_Date,'%Y%m%d'),LPAD(wdi1.Sequence_Id,2,'0'))) FROM worker_details_info wdi1 WHERE wdi.Worker_Id = wdi1.Worker_Id AND wdi1.Transaction_Date <= CURRENT_DATE() ) AND Worker_Code = '"+workerCode+"' ").list();
					
					shiftRawExists = session.createSQLQuery("SELECT MONTH_Shift,ContractorName,EMP_NAME,EMP_CODE FROM labor_shift_raw WHERE MONTH_Shift ='"+monthYear+"' AND Code = '"+vendorCode+"' AND Emp_Code = '"+workerCode+"'  ").list();
					
					if(venNameList != null && venNameList.size() == 1){
						Object[] v = (Object[]) venNameList.get(0);
						venName = (String)v[0];
						vendorId = (Integer)v[1];
						
					}else{
						venName = null;
					}
					
					//Integer workerId = 0;
					if(workerNameList != null && workerNameList.size() == 1){
						for (Object wrker : workerNameList) {
							Object[] w = (Object[]) wrker;
							workrName = (String)w[0];
							workerId = (Integer)w[1];
						}
						
					}else{
						workrName = null;
					}
					
					// To get the latest worker record
					List latestWorker = session.createQuery(" FROM WorkerDetailsInfo AS wdi  WHERE CONCAT(DATE_FORMAT(wdi.transactionDate,'%Y%m%d'), LPAD(wdi.sequenceId,2,'0')) = (SELECT MAX(CONCAT(DATE_FORMAT(wdi1.transactionDate,'%Y%m%d'),LPAD(wdi1.sequenceId,2,'0'))) FROM WorkerDetailsInfo AS wdi1 WHERE wdi.workerDetails = wdi1.workerDetails AND wdi1.transactionDate <= CURRENT_DATE() ) AND wdi.workerDetails = '"+workerId+"' ").list();
					log.info("Latest worker Size :: "+latestWorker.size());
					
					System.out.println("Vendor Name : "+venName+" Worker Name :"+ workrName);
					
					if(venName != null && workrName != null){
						
						if(shiftRawExists != null && shiftRawExists.size() > 0){
							Query delete = session.createSQLQuery("DELETE FROM labor_shift_raw WHERE MONTH_Shift ='"+monthYear+"' AND Code = '"+vendorCode+"' AND Emp_Code = '"+workerCode+"'  ");
							delete.executeUpdate();
						}
						
						log.info(monthYear+", "+vendorCode+", "+venName+", "+workerCode+", "+workrName+", "+shift+", "+weeklyOff);
							
						Query insertRaw = session.createSQLQuery(
								"INSERT INTO labor_shift_raw (MONTH_Shift,CODE,ContractorName,EMP_CODE,EMP_NAME,SHIFT,Weekly_Off, Sequence_Id, Created_By, Created_Date, Modified_By, Modified_Date) VALUES ("
										+"'"+monthYear+"', '"+vendorCode+"', '"+venName+"', '"+workerCode+"', '"+workrName+"', '"+shift+"', '"+weeklyOff+"', "+(sequence)+", '"+createdBy+"', '"+DateHelper.convertDateTimeToSQLString(new Date())+"', '"+modifiedBy+"', '"+DateHelper.convertDateTimeToSQLString(new Date())+"' )");
						
						insertRaw.executeUpdate();
						
						if(latestWorker != null && latestWorker.size() == 1){
							worker = (WorkerDetailsInfo)(Object)latestWorker.get(0);
							
							worker.setCreatedBy(createdBy);
							worker.setModifiedBy(modifiedBy);
							worker.setCreatedDate(new Date());
							worker.setSequenceId(worker.getSequenceId()+1);
							worker.setTransactionDate(DateHelper.convertDateToSQLDate(new Date()));
							worker.setShiftName(shift);
							worker.setWeeklyOff(weeklyOff);
							Integer id = (Integer)session.save(worker);
							System.out.println(id);
						}
					}else if(venName == null && workrName != null){
						errFlag = false;	
						UploadStatusVo	statusVo =	this.retunUploadStatusVo(vendorCode, vendorName, workerCode, workerName, null, shift, null, weeklyOff, monthYear);						
						statusVo.setErrorDescription("<span style=\"color:red\">Vendor code does not exist</span>");
						errorsList.add(statusVo);
						errorCount++;
					}else if(venName != null && workrName == null){
						errFlag = false;	
						UploadStatusVo	statusVo =	this.retunUploadStatusVo(vendorCode, vendorName, workerCode, workerName, null, shift, null, weeklyOff, monthYear);						
						statusVo.setErrorDescription("<span style=\"color:red\">Worker code does not exist</span>");
						errorsList.add(statusVo);
						errorCount++;
					}else {
						errFlag = false;
						saveFlag = false;
						UploadStatusVo	statusVo =	this.retunUploadStatusVo(vendorCode, vendorName, workerCode, workerName, null, shift, null, weeklyOff, monthYear);						
						statusVo.setErrorDescription("<span style=\"color:red\">Both Worker Code and Vendor Code does not exist</span>");
						errorsList.add(statusVo);
						errorCount++;
					}
					
				}
				reader.close();
				if(!saveFlag){
					statusMessageVo.setFlag(0);
					if(errorsList == null || errorsList != null && errorsList.isEmpty()){
						statusMessageVo.setDescription("File is Empty.");
					}else{
						statusMessageVo.setDescription("File upload Failed, Errors in File.");
					}
					
					flag.add(statusMessageVo);
					errorMessages.put("flag", flag);
				}else if(saveFlag){
					System.out.println("Procedure Values :  '"+DateHelper.convertStringToSQLDate(year+"-"+month+"-"+"01") +"', "+createdBy+", "+modifiedBy+", "+sequence+", "+customerId+", "+companyId+", "+vendorId+", "+workerId);
					Query procedure = session.createSQLQuery("CALL labour_scheduled_shifts_procedure('"+DateHelper.convertStringToSQLDate(year+"-"+month+"-"+"01") +"', "+createdBy+", "+modifiedBy+", "+sequence+", "+customerId+", "+companyId+", "+vendorId+", "+workerId+" )");
					int res = procedure.executeUpdate();
					System.out.println(res);
					if(res > 0 ){
						statusMessageVo.setFlag(1);
						if(errorsList != null && errorsList.size() > 0){
							statusMessageVo.setDescription("File Uploaded Successfully with some errors.");
						}else{
							statusMessageVo.setDescription("File Uploaded Successfully.");
						}
					}
					flag.add(statusMessageVo);
					errorMessages.put("flag", flag);
				}
			} else{
				statusMessageVo.setFlag(0);
				statusMessageVo.setDescription("File Upload failed. File is Empty");
				flag.add(statusMessageVo);
				errorMessages.put("flag", flag);
			}
			Date end = new Date();
			System.out.println("Start : "+start+":::::::::   End : "+end);
		} catch (Exception e) {
			System.out.println("Exception ::  "+e.getMessage());
		    e.printStackTrace();
		    statusMessageVo.setFlag(0);
			statusMessageVo.setDescription("Technical problem...");
			log.error("Error Occured ",e);
			flag.add(statusMessageVo);
			errorMessages.put("flag", flag);
			return new ResponseEntity<Map<String, List>>( errorMessages,HttpStatus.OK);
		}finally{
			//session.close();
		}
		
		return new ResponseEntity<Map<String, List>>( errorMessages,HttpStatus.OK);
	}

	private boolean validateWeeklyOff(String string) {
		if(string != null && (string.equalsIgnoreCase("SUN") 
					|| string.equalsIgnoreCase("MON") || string.equalsIgnoreCase("TUE") 
					|| string.equalsIgnoreCase("WED") || string.equalsIgnoreCase("THU") 
					|| string.equalsIgnoreCase("FRI") || string.equalsIgnoreCase("SAT"))){
			return true;
		}else{
			return false;
		}
	}
	
	
	@RequestMapping(value = "/getWorkerChildScreensData.json", method = RequestMethod.POST)
	public  ResponseEntity<List<WorkerDetailsVo>> getWorkerChildScreensData(@RequestBody String workerIdJson ) {	
		List<WorkerDetailsVo> workerChildIdsList  =  new ArrayList();
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(workerIdJson);
		try{
			int wokerId = (jo.get("workerId") != null && !jo.get("workerId").isJsonNull() && jo.get("workerId").getAsInt() > 0) ? jo.get("workerId").getAsInt() :0;
			workerChildIdsList = workerService.getWorkerChildScreensData(wokerId);		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<WorkerDetailsVo>>(workerChildIdsList,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getWorkerAddressByWorkerId.json", method = RequestMethod.POST)
	public  ResponseEntity<SimpleObject> getWorkerAddressByWorkerId(@RequestBody String workerIdJson ) {	
		String address = null;
		JsonParser jsonParser = new JsonParser();
		SimpleObject s = new SimpleObject();
		JsonObject jo = (JsonObject) jsonParser.parse(workerIdJson);
		try{
			if(jo.get("workerId") != null && !jo.get("workerId").isJsonNull() && jo.get("workerId").getAsInt() > 0){
				address = workerService.getWorkerAddressByWorkerId(jo.get("workerId").getAsInt());		
			}
			
			if(address != null && address != ""){
				s.setId(0);
				s.setName(address);
			}else{
				s.setId(1);
				s.setName("Worker address is not available");
			}
						
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<SimpleObject>(s,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getWorkerId.json", method = RequestMethod.POST)
	public  ResponseEntity<SimpleObject> getWorkerId(@RequestBody String workerCodeJson ) {	
		Integer address = null;
		JsonParser jsonParser = new JsonParser();
		SimpleObject s = new SimpleObject();
		JsonObject jo = (JsonObject) jsonParser.parse(workerCodeJson);
		try{
			if(jo.get("workerCode") != null && !jo.get("workerCode").isJsonNull()){
				address = workerService.getWorkerId(jo.get("workerCode").getAsInt());		
			}
			
			if(address != null){
				s.setId(address);				
			}else{
				s.setId(0);
				s.setName("Worker address is not available");
			}
						
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<SimpleObject>(s,HttpStatus.OK);
	}
	
	
}
