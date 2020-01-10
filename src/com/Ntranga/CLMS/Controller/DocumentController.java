package com.Ntranga.CLMS.Controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
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

import com.Ntranga.CLMS.Service.CompanyService;
import com.Ntranga.CLMS.Service.DefineComplianceTypesService;
import com.Ntranga.CLMS.Service.DocumentService;
import com.Ntranga.CLMS.Service.LocationService;
import com.Ntranga.CLMS.Service.RulesForPaymentService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.Service.WorkerService;
import com.Ntranga.CLMS.vo.CompanyDocumentsVo;
import com.Ntranga.CLMS.vo.CompanyProfileVo;
import com.Ntranga.CLMS.vo.DefineComplianceTypesVo;
import com.Ntranga.CLMS.vo.LocationVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.VendorDetailsVo;
import com.Ntranga.CLMS.vo.VendorDocumentsVo;
import com.Ntranga.core.DateHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import common.Logger;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping(value="documentController")
public class DocumentController {

	
	private static final Logger log = Logger.getLogger(DocumentController.class);
	
	@Autowired
	DocumentService documentService;
	
	@Autowired
	VendorService vendorService;
	
	@Autowired 
	CompanyService companyService;
	
	@Autowired
	WorkerService workerService;
	
	@Autowired
	LocationService locationService;
	
	@Autowired
	RulesForPaymentService rulesForPaymentService;
	
	@Autowired
	DefineComplianceTypesService defineComplianceTypesService;
	
	
	private @Value("#{system['VENDOR_DOCUMENTS']}")
	String vendorDocuments;
	
	private @Value("#{system['COMPANY_DOCUMENTS']}")
	String companyDocuments;
	
	

	/*
	 * This method will be used to get the vendor document record based on given document name, from date and to date
	 * 																					
	 */
	@RequestMapping(value = "/getVendorDocumentsListBySearch.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getVendorDocumentsListBySearch(@RequestBody VendorDocumentsVo paramDocuments) throws JSONException {
		log.info("Entered in Document Controller getDocumentsListBySearch()   ::   "+paramDocuments);
		Map<String,List> returnData = new HashMap<String,List>();
		try{
		List<VendorDocumentsVo> vendorDocumentsList = documentService.getDocumentsListBySearch(paramDocuments);
		log.debug("List of Vendor Documents : "+vendorDocumentsList);
		
		returnData.put("vendorDocumentsList",vendorDocumentsList);
		
		log.info("Exiting from Document Controller getDocumentsListBySearch() ::  "+returnData.toString());
		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(returnData,HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get the Rules for payment  details based on given customerId and companyId or rulesForPaymentId and also to get the compliance types list and 
	 */	
	@RequestMapping(value = "/getVendorAndPaymentRulesDocumentNames.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getVendorAndPaymentRulesDocumentNames(@RequestBody String paramCompliance) throws JSONException {
		log.info("Entered in Document Controller getVendorAndPaymentRulesDocumentNames()  ::   "+paramCompliance);
		Map<String,List> returnList = new HashMap<>();
		String locationId = null;
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramCompliance);
		log.debug("JSON Object:  "+jo);
		
		String companyId = null, customerId = null, vendorId = null;
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null ){
			customerId = jo.get("customerId").getAsString(); 
			log.debug("Customer Id : "+customerId);
		}
		
		if(jo.get("companyId") != null && jo.get("companyId").getAsString() != null ){
			companyId = jo.get("companyId").getAsString(); 
			log.debug("Company Id : "+companyId);
		}
		
		if(jo.get("vendorId") != null && jo.get("vendorId").getAsString() != null ){
			vendorId = jo.get("vendorId").getAsString(); 
			log.debug("Vendor Id : "+vendorId);
		}
		if(jo.has("locationId") && jo.get("locationId") != null &&  !jo.get("locationId").isJsonNull() && jo.get("locationId").getAsJsonArray().size() > 0 ){
			JsonArray locId = jo.get("locationId").getAsJsonArray();
			locationId = locId.toString().replace("]", "").replace("[", "");
		}
	    List<SimpleObject> 	documentList = rulesForPaymentService.getPaymentRulesDocumentNames(Integer.valueOf(customerId.trim()), Integer.valueOf(companyId.trim()));
	    List<SimpleObject> vendorList  = workerService.getVendorNamesAsDropDown(customerId, companyId,vendorId, "Validated",locationId);
	    
	    returnList.put("documentsList", documentList);
	    returnList.put("vendorList", vendorList);
		log.info("Exiting from Document Controller getVendorAndPaymentRulesDocumentNames() ::  "+returnList);
		return new ResponseEntity<Map<String,List>>(returnList, HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get the vendor document record based on vendorDocumentId
	 */	
	@RequestMapping(value = "/getVendorDocumentById.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getVendorDocumentById(@RequestBody String paramDocument) throws JSONException {
		log.info("Entered in Document Controller getVendorDocumentById()  ::   "+paramDocument);
		Map<String,List> returnList = new HashMap<String,List>();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(paramDocument);
			log.debug("JSON Object:  "+jo);
			String locationId = null;
			String vendorDocumentId = null, customerId = null, vendorId= null, companyId = null;
			if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null){
				customerId = jo.get("customerId").getAsString(); 
				log.debug("Customer Id : "+customerId);
			}
				
				
			if(jo.get("vendorDocumentId") != null && jo.get("vendorDocumentId").getAsString() != null){
				vendorDocumentId = jo.get("vendorDocumentId").getAsString();  
				log.debug("vendor Document Id : "+vendorDocumentId);
			}
							
			if(jo.get("vendorId") != null && jo.get("vendorId").getAsString() != null){
				vendorId = jo.get("vendorId").getAsString(); 
				log.debug("Vendor Id : "+vendorId);
			}
				
			if(jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
				companyId = jo.get("companyId").getAsString(); 
				log.debug("Company Id : "+companyId);
			}	 
			if(jo.has("locationId") && jo.get("locationId") != null &&  !jo.get("locationId").isJsonNull() && jo.get("locationId").getAsJsonArray().size() > 0 ){
				JsonArray locId = jo.get("locationId").getAsJsonArray();
				locationId = locId.toString().replace("]", "").replace("[", "");
			}
		    List<VendorDocumentsVo> documentVo= documentService.getVendorDocumentById(Integer.valueOf(vendorDocumentId.trim()));	
		   // List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);
		   // List<SimpleObject> companyList  = vendorService.getComapanyNamesAsDropDown(customerId, companyId);		
		    List<SimpleObject> vendorList  = workerService.getVendorNamesAsDropDown(customerId,companyId, vendorId, "Validated",locationId);
		    List<VendorDetailsVo> vendorLocation =  vendorService.getVendorDetailsList(customerId, companyId, null, null, vendorId, null, "Validated");
		    CompanyProfileVo profileVo = companyService.getCompanyProfileByCompanyId(companyId, customerId);
		    List<CompanyProfileVo> profileList = new ArrayList<>();
		    profileList.add(profileVo);
		    
		    List<SimpleObject> 	documentList = rulesForPaymentService.getPaymentRulesDocumentNames(Integer.valueOf(customerId.trim()), Integer.valueOf(companyId.trim()));
 
		    returnList.put("vendorLocation",vendorLocation);
			returnList.put("documentVo",documentVo);
			returnList.put("vendorList", vendorList);
			returnList.put("documentList", documentList);
			returnList.put("defaultCurrency", profileList);
			
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		
		log.info("Exiting from Document Controller getVendorDocumentById() ::  "+returnList);
		return new ResponseEntity<Map<String,List>>(returnList, HttpStatus.OK);
	}
	
	/*
	 * This method will be used to save or update the vendor document
	*/ 
	@RequestMapping(value = "/saveVendorDocument.json", method = RequestMethod.POST)
	public ResponseEntity<SimpleObject> saveVendorDocument(@RequestParam("name") String paramString, @RequestParam("file") MultipartFile file) {
		log.info("Entered in Document Controller saveVendorDocument() ::  "+paramString);
		
		System.out.println(file.getName());
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramString);
		log.info("JSON Object:  "+jo);
		
		VendorDocumentsVo paramDocument = new VendorDocumentsVo();
		Integer vendorDocumentId = null;
		SimpleObject object = new SimpleObject();
		
		try{
			paramDocument.setVendorDocumentId(!jo.has("vendorDocumentId") ? null :( jo.get("vendorDocumentId").isJsonNull() ? null : jo.get("vendorDocumentId").getAsInt()));
			paramDocument.setCustomerId(jo.get("customerId"). isJsonNull() ? null : Integer.valueOf(jo.get("customerId").getAsString().trim()));
			paramDocument.setCompanyId(jo.get("companyId").isJsonNull() ? null : Integer.valueOf(jo.get("companyId").getAsString().trim()));
			paramDocument.setVendorId(jo.get("vendorId").isJsonNull() ? null :Integer.valueOf(jo.get("vendorId").getAsString().trim()));
    		paramDocument.setDocumentName(jo.get("documentName").isJsonNull() ? null : jo.get("documentName").getAsString().trim());
			paramDocument.setDocumentDate(jo.get("documentDate").isJsonNull() ? null : DateHelper.convertStringToSQLDate(jo.get("documentDate").getAsString()));
    		paramDocument.setAmount(!jo.has("amount") ? null :jo.get("amount").isJsonNull() ? null : jo.get("amount").getAsString().trim());
    		paramDocument.setCurrencyId(!jo.has("currencyId") ? null :jo.get("currencyId").isJsonNull() ? null : Integer.valueOf(jo.get("currencyId").getAsString().trim()));
            paramDocument.setChallanNumber(jo.get("challanNumber").isJsonNull() ? null : Integer.valueOf(jo.get("challanNumber").getAsString().trim()));
            paramDocument.setHeadCount(!jo.has("headCount") ? null :( jo.get("headCount").isJsonNull() ? null : jo.get("headCount").getAsInt()));
            paramDocument.setDocumentFrequency(jo.get("documentFrequency").isJsonNull() ? null : jo.get("documentFrequency").getAsString());
            paramDocument.setStatus(jo.get("status").isJsonNull() ? null : jo.get("status").getAsString());
            paramDocument.setCreatedBy(!jo.has("createdBy") ? null :jo.get("createdBy").getAsInt());
            paramDocument.setModifiedBy(!jo.has("modifiedBy") ? null :jo.get("modifiedBy").getAsInt());
    		paramDocument.setComments(!jo.has("comments") ? null :jo.get("comments").isJsonNull() ? null : jo.get("comments").getAsString().trim());
    		paramDocument.setFilePath(!jo.has("filePath") ? null :jo.get("filePath").isJsonNull() ? null : jo.get("filePath").getAsString().trim());
    		paramDocument.setFileName(!jo.has("fileName") ? null :jo.get("fileName").isJsonNull() ? null : jo.get("fileName").getAsString().trim());

            
            String filePath = null;
				 
	        System.out.println(file.isEmpty());
	        
	        if(! new File(vendorDocuments).exists()){
	            new File(vendorDocuments).mkdirs();
	        }
	        log.info("Documents path : "+ vendorDocuments);
 
	        
        	if(!file.isEmpty()){
				try{
	                String orgName = file.getOriginalFilename();
	                String[] extension = orgName.split("\\.(?=[^\\.]+$)");
	                DateFormat df = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
	                String fileName = orgName.split("\\.")[0]+"_"+df.format(new java.util.Date())+"."+extension[extension.length-1];
	                filePath = vendorDocuments +  fileName;
	                File dest = new File( vendorDocuments +  fileName);
            		file.transferTo(dest);
            		paramDocument.setFilePath(filePath);
            		paramDocument.setFileName(orgName);
				}catch(Exception e){
					 object.setId(-1);
	     			 object.setName("Technical issue occurred while Saving file " );
	            	 log.error("Error Occured ",e);
				}
			}else{
				object.setId(-1);
     			object.setName("File is Empty");
			}
	        
	
			if(paramDocument != null){
				vendorDocumentId = documentService.saveVendorDocument(paramDocument);
				log.debug("Invoice ID: "+vendorDocumentId);
			}
			
			if (vendorDocumentId != null) {
				object.setId(vendorDocumentId);
				object.setName("Success");
			} else {
				object.setId(0);
				object.setName("Fail");
			}
		
		}catch(Exception e){
			object.setId(-1);
			log.error("Error Occured ",e);
		}
		log.info("Exiting from Document Controller saveVendorDocument() ::  "+object.toString());
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get the company document record based on given document name, from date and to date
	 * 																					
	 */
	@RequestMapping(value = "/getCompanyDocumentsListBySearch.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getCompanyDocumentsListBySearch(@RequestBody CompanyDocumentsVo paramDocuments) throws JSONException {
		log.info("Entered in Document Controller getCompanyDocumentsListBySearch()   ::   "+paramDocuments);
		Map<String,List> returnData = new HashMap<String,List>();
		try{
		List<CompanyDocumentsVo> companyDocumentsList = documentService.getCompanyDocumentsListBySearch(paramDocuments);
		log.debug("List of Company Documents : "+companyDocumentsList);
		
		returnData.put("vendorDocumentsList",companyDocumentsList);
		
		log.info("Exiting from Document Controller getCompanyDocumentsListBySearch() ::  "+returnData.toString());
		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(returnData,HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get the Rules for payment  details based on given customerId and companyId or rulesForPaymentId and also to get the compliance types list and 
	 */	
	@RequestMapping(value = "/getLocationAndDocumentNames.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getLocationAndDocumentNames(@RequestBody String paramCompliance) throws JSONException {
		log.info("Entered in Document Controller getLocationAndDocumentNames()  ::   "+paramCompliance);
		Map<String,List> returnList = new HashMap<>();
		
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramCompliance);
		log.debug("JSON Object:  "+jo);
		
		String companyId = null, customerId = null;
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null ){
			customerId = jo.get("customerId").getAsString(); 
			log.debug("Customer Id : "+customerId);
		}
		
		if(jo.get("companyId") != null && jo.get("companyId").getAsString() != null ){
			companyId = jo.get("companyId").getAsString(); 
			log.debug("Company Id : "+companyId);
		}
		
	    List<DefineComplianceTypesVo> 	documentList = defineComplianceTypesService.getComplianceListByApplicable(Integer.valueOf(customerId.trim()), Integer.valueOf(companyId.trim()),"Company");
	   
	    LocationVo paramLocation = new LocationVo();
    	paramLocation.setCustomerId(Integer.parseInt(customerId.trim()));
    	paramLocation.setCompanyId(Integer.parseInt(companyId));
    	
    	List<LocationVo> locationsList = locationService.getLocationsListBySearch(paramLocation);
	   
    	CompanyProfileVo profileVo = companyService.getCompanyProfileByCompanyId(companyId, customerId);
	    List<CompanyProfileVo> profileList = new ArrayList<>();
	    profileList.add(profileVo);
	    
	    returnList.put("documentsList", documentList);
	    returnList.put("locationsList", locationsList);
	    returnList.put("defaultCurrency", profileList);
		log.info("Exiting from Document Controller getLocationAndDocumentNames() ::  "+returnList);
		return new ResponseEntity<Map<String,List>>(returnList, HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get the company document record based on companyDocumentId
	 */	
	@RequestMapping(value = "/getCompanyDocumentById.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getCompanyDocumentById(@RequestBody String paramDocument) throws JSONException {
		log.info("Entered in Document Controller getCompanyDocumentById()  ::   "+paramDocument);
		Map<String,List> returnList = new HashMap<String,List>();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(paramDocument);
			log.debug("JSON Object:  "+jo);
			
			String companyDocumentId = null, customerId = null,  companyId = null;
			if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null){
				customerId = jo.get("customerId").getAsString(); 
				log.debug("Customer Id : "+customerId);
			}
				
				
			if(jo.get("companyDocumentId") != null && jo.get("companyDocumentId").getAsString() != null){
				companyDocumentId = jo.get("companyDocumentId").getAsString();  
				log.debug("Company Document Id : "+companyDocumentId);
			}
							
			if(jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
				companyId = jo.get("companyId").getAsString(); 
				log.debug("Company Id : "+companyId);
			}	 
	
		    List<CompanyDocumentsVo> documentVo= documentService.getCompanyDocumentById(Integer.valueOf(companyDocumentId.trim()));	

		    LocationVo paramLocation = new LocationVo();
	    	paramLocation.setCustomerId(Integer.parseInt(customerId.trim()));
	    	paramLocation.setCompanyId(Integer.parseInt(companyId));
	    	List<LocationVo> locationsList = locationService.getLocationsListBySearch(paramLocation);
	    	
	    	List<SimpleObject> companyList  = vendorService.getComapanyNamesAsDropDown(customerId, companyId);
		    CompanyProfileVo profileVo = companyService.getCompanyProfileByCompanyId(companyId, customerId);
		    List<CompanyProfileVo> profileList = new ArrayList<>();
		    profileList.add(profileVo);
		    
		    List<DefineComplianceTypesVo> 	documentList = defineComplianceTypesService.getComplianceListByApplicable(Integer.valueOf(customerId.trim()), Integer.valueOf(companyId.trim()),"Company");
 
			returnList.put("documentVo",documentVo);
			returnList.put("locationList", locationsList);
			returnList.put("documentList", documentList);
			returnList.put("defaultCurrency", profileList);
			returnList.put("companyList", companyList);
			
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		
		log.info("Exiting from Document Controller getCompanyDocumentById() ::  "+returnList);
		return new ResponseEntity<Map<String,List>>(returnList, HttpStatus.OK);
	}
	
	/*
	 * This method will be used to save or update the company document
	*/ 
	@RequestMapping(value = "/saveCompanyDocument.json", method = RequestMethod.POST)
	public ResponseEntity<SimpleObject> saveCompanyDocument(@RequestParam("name") String paramString, @RequestParam("file") MultipartFile file) {
		log.info("Entered in Document Controller saveCompanyDocument() ::  "+paramString);
		
		System.out.println(file.getName());
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramString);
		log.info("JSON Object:  "+jo);
		
		CompanyDocumentsVo paramDocument = new CompanyDocumentsVo();
		Integer companyDocumentId = null;
		SimpleObject object = new SimpleObject();
		
		try{
			paramDocument.setCompanyDocumentId(!jo.has("vendorDocumentId") ? null :( jo.get("vendorDocumentId").isJsonNull() ? null : jo.get("vendorDocumentId").getAsInt()));
			paramDocument.setCustomerId(jo.get("customerId"). isJsonNull() ? null : Integer.valueOf(jo.get("customerId").getAsString().trim()));
			paramDocument.setCompanyId(jo.get("companyId").isJsonNull() ? null : Integer.valueOf(jo.get("companyId").getAsString().trim()));
			paramDocument.setLocationId(jo.get("locationId").isJsonNull() ? null :Integer.valueOf(jo.get("locationId").getAsString().trim()));
    		paramDocument.setDocumentName(jo.get("documentName").isJsonNull() ? null : jo.get("documentName").getAsString().trim());
			paramDocument.setDocumentDate(jo.get("documentDate").isJsonNull() ? null : DateHelper.convertStringToSQLDate(jo.get("documentDate").getAsString()));
    		paramDocument.setAmount(!jo.has("amount") ? null :jo.get("amount").isJsonNull() ? null : jo.get("amount").getAsString().trim());
    		paramDocument.setCurrencyId(!jo.has("currencyId") ? null :jo.get("currencyId").isJsonNull() ? null : Integer.valueOf(jo.get("currencyId").getAsString().trim()));
            paramDocument.setChallanNumber(jo.get("challanNumber").isJsonNull() ? null : Integer.valueOf(jo.get("challanNumber").getAsString().trim()));
            paramDocument.setHeadCount(!jo.has("headCount") ? null :( jo.get("headCount").isJsonNull() ? null : jo.get("headCount").getAsInt()));
            paramDocument.setDocumentFrequency(jo.get("documentFrequency").isJsonNull() ? null : jo.get("documentFrequency").getAsString());
            paramDocument.setStatus(jo.get("status").isJsonNull() ? null : jo.get("status").getAsString());
            paramDocument.setCreatedBy(!jo.has("createdBy") ? null :jo.get("createdBy").getAsInt());
            paramDocument.setModifiedBy(!jo.has("modifiedBy") ? null :jo.get("modifiedBy").getAsInt());
    		paramDocument.setComments(!jo.has("comments") ? null :jo.get("comments").isJsonNull() ? null : jo.get("comments").getAsString().trim());
    		paramDocument.setFilePath(!jo.has("filePath") ? null :jo.get("filePath").isJsonNull() ? null : jo.get("filePath").getAsString().trim());
    		paramDocument.setFileName(!jo.has("fileName") ? null :jo.get("fileName").isJsonNull() ? null : jo.get("fileName").getAsString().trim());

            
            String filePath = null;
				 
	        System.out.println(file.isEmpty());
	        
	        if(! new File(companyDocuments).exists()){
	            new File(companyDocuments).mkdirs();
	        }
	        log.info("Documents path : "+ companyDocuments);
 
	        
        	if(!file.isEmpty()){
				try{
	                String orgName = file.getOriginalFilename();
	                String[] extension = orgName.split("\\.(?=[^\\.]+$)");
	                DateFormat df = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
	                String fileName = orgName.split("\\.")[0]+"_"+df.format(new java.util.Date())+"."+extension[extension.length-1];
	                filePath = vendorDocuments +  fileName;
	                File dest = new File( vendorDocuments +  fileName);
            		file.transferTo(dest);
            		paramDocument.setFilePath(filePath);
            		paramDocument.setFileName(orgName);
				}catch(Exception e){
					 object.setId(-1);
	     			 object.setName("Technical issue occurred while Saving file " );
	            	 log.error("Error Occured ",e);
				}
			}else{
				object.setId(-1);
     			object.setName("File is Empty");
			}
	        
	
			if(paramDocument != null){
				companyDocumentId = documentService.saveCompanyDocument(paramDocument);
				log.debug("Invoice ID: "+companyDocumentId);
			}
			
			if (companyDocumentId != null) {
				object.setId(companyDocumentId);
				object.setName("Success");
			} else {
				object.setId(0);
				object.setName("Fail");
			}
		
		}catch(Exception e){
			object.setId(-1);
			log.error("Error Occured ",e);
		}
		log.info("Exiting from Document Controller saveCompanyDocument() ::  "+object.toString());
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}
	

}
