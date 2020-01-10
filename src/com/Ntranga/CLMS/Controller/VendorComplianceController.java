package com.Ntranga.CLMS.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
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

import com.Ntranga.CLMS.Service.CommonService;
import com.Ntranga.CLMS.Service.CompanyService;
import com.Ntranga.CLMS.Service.CustomerService;
import com.Ntranga.CLMS.Service.DefineComplianceTypesService;
import com.Ntranga.CLMS.Service.EmployeeService;
import com.Ntranga.CLMS.Service.LocationService;
import com.Ntranga.CLMS.Service.VendorComplianceService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.vo.CompanyProfileVo;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.DefineComplianceTypesVo;
import com.Ntranga.CLMS.vo.LocationVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.VendorComplianceVo;
import com.Ntranga.CLMS.vo.VendorDetailsVo;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.MLocation;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping(value="vendorComplianceController")
public class VendorComplianceController {

	private static Logger log = Logger.getLogger(VendorComplianceController.class);
	
	@Autowired
	private VendorComplianceService vendorComplianceService;
	
	@Autowired
	private VendorService vendorService;

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private DefineComplianceTypesService defineService;
	
	@Autowired
    private HttpServletRequest request;
	
	@Autowired
    private EmployeeService employeeService;
	
	private @Value("#{system['VENDOR_COMPLIANCE_DOCUMENTS']}")
	String vendorDocuments;
	
	/*
	 * This method will be used to save  the vendor compliance details including files
	*/ 
	@RequestMapping(value = "/saveVendorCompliance.json", method = RequestMethod.POST)
	public ResponseEntity<SimpleObject> saveVendorCompliance(@RequestParam("name") String paramComplian, @RequestParam("file") MultipartFile file) {
		log.info("Entered in Vendor Compliance Controller saveVendorCompliance() ::  "+paramComplian);
		System.out.println("Entered in Vendor Compliance Controller saveVendorCompliance() ::  "+paramComplian);
		
		VendorComplianceVo paramCompliance = new VendorComplianceVo();
		SimpleObject object = new SimpleObject();
		
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramComplian);
		log.debug("JSON Object:  "+jo);
		
		String company = null, customer = null,  vendorCompliance = null, vendor = null;Integer locationId = null;
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
						|| jo.get("vendorComplianceId") != null && jo.get("vendorComplianceId").getAsString() != null){
			customer = jo.get("customerId").getAsString(); 
			company = jo.get("companyId").getAsString(); 
			vendor = jo.get("vendorId").getAsString(); 
			locationId = jo.get("locationId").getAsInt(); 
			vendorCompliance = jo.get("vendorComplianceId").getAsString();  
			log.debug("customer : "+customer+" \t Vendor Compliance Details Id : "+vendorCompliance);
		}
		
		paramCompliance.setCustomerId(Integer.valueOf(customer.trim()));
		paramCompliance.setCompanyId(Integer.valueOf(company.trim()));
		paramCompliance.setVendorId(Integer.valueOf(vendor.trim()));
		paramCompliance.setLocationId(locationId);
		paramCompliance.setVendorComplianceId(Integer.valueOf(vendorCompliance.trim()));
		paramCompliance.setTransactionDate(jo.has("transactionDate") ? (Date)DateHelper.checkAndConvertStringToSQLDate((jo.get("transactionDate").getAsString())): null);
		
		Integer vendorComplianceId = null;
		int companyDetailsId = 0;
		int companyId = paramCompliance.getCompanyId();
		int customerId = paramCompliance.getCustomerId();
		int vendorId = paramCompliance.getVendorId();
		System.out.println("1: "+file.isEmpty());
		if (!file.isEmpty()) {
            try {
               
                 //realPathtoUploads =  (request.getServletContext().getRealPath("").concat(File.separator).concat(fileLocation));
                System.out.println(new File(vendorDocuments).exists());
                if(! new File(vendorDocuments).exists())
                {
                    new File(vendorDocuments).mkdirs();
                }
                
                System.out.println("realPathtoUploads = {}"+ vendorDocuments);


                String orgName = file.getOriginalFilename();
                String[] extension = orgName.split("\\.(?=[^\\.]+$)");
                System.out.println(orgName);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
                String fileName = orgName.split("\\.")[0]+"_"+df.format(new java.util.Date())+"."+extension[extension.length-1];
                
                System.out.println("2: "+fileName);
                String filePath = vendorDocuments +  fileName;
                System.out.println("file---------------"+filePath);
                File dest = new File(filePath);
                file.transferTo(dest);
                paramCompliance.setDocumentPath(filePath);
                paramCompliance.setPath(filePath);
         		paramCompliance.setCustomerId(Integer.valueOf(customer.trim()));
         		paramCompliance.setCompanyId(Integer.valueOf(company.trim()));
         		paramCompliance.setVendorId(Integer.valueOf(vendor.trim()));
         		paramCompliance.setVendorComplianceId(Integer.valueOf(vendorCompliance.trim()));
         		paramCompliance.setTransactionDate(DateHelper.convertStringToSQLDate(jo.get("transactionDate").getAsString()));
         		//System.out.println(DateHelper.convertStringToSQLDate(jo.get("transactionDate").getAsString()));
         		paramCompliance.setIssueDate((Date)DateHelper.convertStringToSQLDate(jo.get("issueDate").getAsString()));
         		paramCompliance.setExpiryDate((Date)DateHelper.convertStringToSQLDate(jo.get("expiryDate").getAsString()));
         		paramCompliance.setRenewalDate(jo.has("renewalDate") ? (jo.get("renewalDate").isJsonNull() ? null : (Date)DateHelper.convertStringToSQLDate(jo.get("renewalDate").getAsString())): null);
         		paramCompliance.setStatus(jo.get("status").getAsString());
         		paramCompliance.setCountryId(jo.get("countryId").getAsInt());
         		paramCompliance.setLicensePolicyNumber(jo.get("licensePolicyNumber").getAsString());
         		//System.out.println(jo.get("reason").isJsonNull());
         		paramCompliance.setReason(!jo.has("reason") ? null : (jo.get("reason").isJsonNull() ? null : jo.get("reason").getAsString()));
         		paramCompliance.setRemarks(!jo.has("remarks") ? null : (jo.get("remarks").isJsonNull() ? null : jo.get("remarks").getAsString()));
         		paramCompliance.setDefineComplianceTypeId(jo.get("defineComplianceTypeId").getAsInt());
         		paramCompliance.setRegisteredState(jo.get("registeredState").getAsInt());
         		//paramCompliance.setMandatory(jo.get("mandatory").getAsBoolean());
         		if(jo.get("transactionAmount") != null && !jo.get("transactionAmount").toString().equalsIgnoreCase("") && !jo.get("transactionAmount").toString().equalsIgnoreCase("null") && !jo.get("transactionAmount").toString().isEmpty()){
             		paramCompliance.setTransactionAmount(jo.get("transactionAmount").getAsDouble());
         		}
         		//System.out.println(jo.get("amountType").getAsString().isEmpty()+":::amount" );
         		if(jo.get("amountType") != null && !jo.get("amountType").getAsString().isEmpty() && jo.get("amountType").getAsInt() >0 ){
         			paramCompliance.setAmountType(jo.get("amountType").getAsInt());
         		}
         		paramCompliance.setNumberOfWorkersCovered(jo.get("numberOfWorkersCovered").getAsInt());
         		paramCompliance.setVendorComplianceId(jo.has("vendorComplianceId") ? jo.get("vendorComplianceId").getAsInt() : null);
         		paramCompliance.setVendorComplianceUniqueId(jo.has("vendorComplianceUniqueId") ? jo.get("vendorComplianceUniqueId").getAsInt() : 0);
         		paramCompliance.setValidated(jo.get("validated").getAsBoolean());
         		paramCompliance.setCreatedBy(jo.get("createdBy").getAsInt());
         		paramCompliance.setModifiedBy(jo.get("modifiedBy").getAsInt());
         		
         		List<SimpleObject> obj = companyService.getCompanyTransactionDates(customerId+"", companyId+"");
         		
         		if(obj!=null && obj.size() > 0) {
         			companyDetailsId= (obj.get(0).getId());
         		}
         		
         		log.info("Date "+companyDetailsId);
         		java.util.Date registrationDate = null;
         		List<VendorDetailsVo> details = vendorService.getVendorDetailsList(customerId+"", companyId+"", null, null,vendorId+"", null, null);
         		if(details != null && details.size() > 0 ){
         			registrationDate = details.get(0).getRegistrationDate();
         		}
         		java.util.Date deptDate = (paramCompliance.getTransactionDate());
         		//log.info("Diff "+registrationDate.compareTo(deptDate));
         		
         		if(registrationDate == null || (registrationDate != null &&registrationDate.compareTo(deptDate) <= 0)){
         			if(paramCompliance != null ){
         				vendorComplianceId = vendorComplianceService.saveVendorCompliance(paramCompliance);
         				log.debug("VendorComplianceID: "+vendorComplianceId);
         			}
         			
         			if (vendorComplianceId != null && vendorComplianceId > 0) {
         				object.setId(vendorComplianceId);
         				object.setName("Success");
         			} else {
         				object.setId(0);
         				object.setName("Fail");
         			}
         		}else{
         			object.setId(-1);
         			object.setName("Transaction date should not be less than Vendor Registration Date");
         		}
             }catch(Exception e){
            	 object.setId(0);
     			 object.setName("Exception Occurred");
            	 log.error("Error Occured ",e);
            }
		}else{
			object.setId(-1);
			object.setName("Failed because file is empty");
		}
		
		
		
		log.info("Exiting from Vendor Compliance Controller saveVendorCompliance() ::  "+object.toString());
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}
	
	
	
	/*
	 * This method will be used to save or update the vendor Compliance details without files
	*/ 
	@RequestMapping(value = "/updateVendorCompliance.json", method = RequestMethod.POST)
	public ResponseEntity<SimpleObject> updateVendorCompliance(@RequestParam("name") String paramComplian) {
		log.info("Entered in Vendor Compliance Controller updateVendorCompliance() ::  "+paramComplian);
		System.out.println("Entered in Vendor Compliance Controller updateVendorCompliance() ::  "+paramComplian);
		SimpleObject object = new SimpleObject();
		try{
		VendorComplianceVo paramCompliance = new VendorComplianceVo();
		
		
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramComplian);
		log.debug("JSON Object:  "+jo);
		
		String company = null, customer = null,  vendorCompliance = null, vendor = null;Integer locationId= null;
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
						|| jo.get("vendorComplianceId") != null && jo.get("vendorComplianceId").getAsString() != null){
			customer = jo.get("customerId").getAsString(); 
			company = jo.get("companyId").getAsString(); 
			vendor = jo.get("vendorId").getAsString(); 
			locationId = jo.get("locationId").getAsInt(); 
			vendorCompliance = jo.get("vendorComplianceId").getAsString();  
			System.out.println("========"+jo.get("locationId").getAsInt());
			log.debug("customer : "+customer+" \t Vendor Compliance Details Id : "+vendorCompliance);
		}
		
		paramCompliance.setCustomerId(Integer.valueOf(customer.trim()));
		paramCompliance.setCompanyId(Integer.valueOf(company.trim()));
		paramCompliance.setVendorId(Integer.valueOf(vendor.trim()));
		paramCompliance.setLocationId(locationId);
		paramCompliance.setVendorComplianceId(Integer.valueOf(vendorCompliance.trim()));
		paramCompliance.setTransactionDate((Date)DateHelper.checkAndConvertStringToSQLDate((jo.get("transactionDate").getAsString())));
		
		Integer vendorComplianceId = null;
		int companyDetailsId = 0;
		int companyId = paramCompliance.getCompanyId();
		int customerId = paramCompliance.getCustomerId();
		int vendorId = paramCompliance.getVendorId();
	
	
		
        paramCompliance.setDocumentPath((jo.has("documentPath") ? (jo.get("documentPath").isJsonNull() ? "" : jo.get("documentPath").getAsString()) : ""));
        paramCompliance.setPath((jo.has("path") ? (jo.get("path").isJsonNull() ? "" : jo.get("path").getAsString()) : ""));
 		paramCompliance.setCustomerId(Integer.valueOf(customer.trim()));
 		paramCompliance.setCompanyId(Integer.valueOf(company.trim()));
 		paramCompliance.setVendorId(Integer.valueOf(vendor.trim()));
 		paramCompliance.setVendorComplianceId(Integer.valueOf(vendorCompliance.trim()));
 		paramCompliance.setTransactionDate(DateHelper.convertStringToSQLDate(jo.get("transactionDate").getAsString()));
 		paramCompliance.setIssueDate((Date)DateHelper.convertStringToSQLDate(jo.get("issueDate").getAsString()));
 		paramCompliance.setExpiryDate((Date)DateHelper.convertStringToSQLDate(jo.get("expiryDate").getAsString()));
 		if(!jo.get("renewalDate").isJsonNull() && jo.get("renewalDate").getAsString() != null && jo.get("renewalDate").getAsString() != "")
 			paramCompliance.setRenewalDate((Date)DateHelper.convertStringToSQLDate(jo.get("renewalDate").getAsString()));
 		paramCompliance.setStatus(jo.get("status").getAsString());
 		paramCompliance.setCountryId(jo.get("countryId").getAsInt());
 		paramCompliance.setLicensePolicyNumber(jo.get("licensePolicyNumber").getAsString());
 		paramCompliance.setReason(jo.get("reason").isJsonNull() ? null : jo.get("reason").getAsString());
 		paramCompliance.setRemarks(jo.get("remarks").isJsonNull() ? null : jo.get("remarks").getAsString());
 		paramCompliance.setDefineComplianceTypeId(jo.get("defineComplianceTypeId").getAsInt());
 		paramCompliance.setRegisteredState(jo.get("registeredState").getAsInt());
 		if(jo.get("transactionAmount") != null && !jo.get("transactionAmount").toString().equalsIgnoreCase("") && !jo.get("transactionAmount").toString().equalsIgnoreCase("null") && !jo.get("transactionAmount").toString().isEmpty()){
 		paramCompliance.setTransactionAmount(jo.get("transactionAmount").getAsDouble());
 		}
 		if(jo.get("amountType") != null && !jo.get("amountType").getAsString().isEmpty() && jo.get("amountType").getAsInt() >0 ){
 		paramCompliance.setAmountType(jo.get("amountType").getAsInt());
 		}
 		paramCompliance.setNumberOfWorkersCovered(jo.get("numberOfWorkersCovered").getAsInt());
 		paramCompliance.setVendorComplianceId(jo.get("vendorComplianceId").getAsInt());
 		paramCompliance.setVendorComplianceUniqueId(jo.get("vendorComplianceUniqueId").getAsInt());
 		paramCompliance.setCreatedBy(jo.get("createdBy").getAsInt());
 		paramCompliance.setModifiedBy(jo.get("modifiedBy").getAsInt());
 		paramCompliance.setValidated(jo.get("validated").getAsBoolean());
 		
 		List<SimpleObject> obj = companyService.getCompanyTransactionDates(customerId+"", companyId+"");
 		
 		if(obj!=null && obj.size() > 0) {
 			companyDetailsId= (obj.get(0).getId());
 		}
 		
 		log.info("Date "+companyDetailsId);
 		java.util.Date registrationDate = null;
 		List<VendorDetailsVo> details = vendorService.getVendorDetailsList(customerId+"", companyId+"",null, null, vendorId+"", null, null);
 		if(details != null && details.size() > 0 ){
 			registrationDate = details.get(0).getRegistrationDate();
 		}
 		java.util.Date deptDate = (paramCompliance.getTransactionDate());
 		//log.info("Diff "+registrationDate.compareTo(deptDate));
 		
 		if(registrationDate == null || (registrationDate != null &&registrationDate.compareTo(deptDate) <= 0)){
 			if(paramCompliance != null ){
 				vendorComplianceId = vendorComplianceService.saveVendorCompliance(paramCompliance);
 				log.debug("VendorComplianceID: "+vendorComplianceId);
 			}
 			
 			if (vendorComplianceId != null && vendorComplianceId > 0) {
 				object.setId(vendorComplianceId);
 				object.setName("Success");
 			} else {
 				object.setId(0);
 				object.setName("Fail");
 			}
 		}else{
 			object.setId(-1);
 			object.setName("Transaction date should not be less than Vendor Registration Date");
 		}
        
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		
		log.info("Exiting from Vendor Compliance Controller updateVendorCompliance() ::  "+object.toString());
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}

	
	/*
	 * This method will be used to get the vendor compliance records based on given customerId, company Id and Vendor Compliance Name
	 * 																					
	 */
 	@RequestMapping(value = "/getVendorComplianceListBySearch.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getVendorComplianceListBySearch(@RequestBody VendorComplianceVo paramCompliance) throws JSONException {
		log.info("Entered in Vendor Compliance Controller getVendorComplianceListBySearch()   ::   "+paramCompliance);
		
		//List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(String.valueOf(paramCompliance.getCustomerId()));
		List<VendorComplianceVo> vendorComplianceList =vendorComplianceService.getVendorComplianceListBySearch(paramCompliance);
		log.debug("List of vendor Compliances : "+vendorComplianceList);
		
		Map<String,List> returnData = new HashMap<String,List>();
		returnData.put("vendorComplianceList",vendorComplianceList);
		
		List<CompanyProfileVo> profileList = new ArrayList<CompanyProfileVo>();
		CompanyProfileVo profileVo = companyService.getCompanyProfileByCompanyId(paramCompliance.getCompanyId()+"",paramCompliance.getCustomerId()+"");  
		log.debug("profileVo : "+profileVo.getBussinessStartTime());
		profileList.add(profileVo);	
		
		returnData.put("profileList",profileList);
		
        List<SimpleObject>  locationList =   employeeService.getLocationListByCustomerIdAndCompanyId(paramCompliance.getCustomerId(), paramCompliance.getCompanyId(),  null );
        returnData.put("locationList",locationList);
		
		log.info("Exiting from Vendor Compliance Controller getVendorComplianceListBySearch() ::  "+returnData);
		//return  JSONObject.valueToString(s).toString();
		return new ResponseEntity<Map<String,List>>(returnData,HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get the Vendor Compliance record  based on Vendor Compliance details Id
	 */	
	@RequestMapping(value = "/getVendorComplianceById.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getVendorComplianceById(@RequestBody String paramCompliance) throws JSONException {
		log.info("Entered in Vendor Compliance Controller getVendorComplianceById()  ::   "+paramCompliance);
		Map<String,List> returnList = new HashMap<String,List>();
		
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramCompliance);
		log.debug("JSON Object:  "+jo);
		
		String vendorComplianceId = null, customerId = null;
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
						|| jo.get("vendorComplianceId") != null && jo.get("vendorComplianceId").getAsString() != null){
			customerId = jo.get("customerId").getAsString(); 
			vendorComplianceId = jo.get("vendorComplianceId").getAsString();  
			log.debug("customer d : "+customerId+" \t Vendor Compliance Details Id : "+vendorComplianceId);
		}

	    List<VendorComplianceVo> vendorComplianceVo= vendorComplianceService.getVendorComplianceById(Integer.valueOf(vendorComplianceId.trim()));	
	    List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);
	    List<SimpleObject> countryList = new ArrayList<SimpleObject>();
	    List<SimpleObject> companyList = new ArrayList<SimpleObject>();
	    List<SimpleObject> vendorList = new ArrayList<SimpleObject>();
	    List<DefineComplianceTypesVo> complianceTypesList = new ArrayList<DefineComplianceTypesVo>(); 
	    List<SimpleObject> statesList = new ArrayList<SimpleObject>();
	    List<SimpleObject> currencyList = commonService.getCurrencyList();
	    List<CompanyProfileVo> profileList = new ArrayList<CompanyProfileVo>();
		
		//List<VendorComplianceVo> vendorComplianceList = new ArrayList<VendorComplianceVo>();
		
	    //Add vendor List
	    //Add Define Compliance List
	    if(vendorComplianceVo != null && vendorComplianceVo.size() > 0){
	    	
	    	companyList = vendorService.getComapanyNamesAsDropDown((vendorComplianceVo.get(0).getCustomerId())+"",(vendorComplianceVo.get(0).getCompanyId())+"");
	    	countryList = commonService.getCompanyCountries(vendorComplianceVo.get(0).getCompanyId());
	    	vendorList = vendorService.getVendorNamesAsDropDown((vendorComplianceVo.get(0).getCustomerId())+"", (vendorComplianceVo.get(0).getCompanyId())+"",(vendorComplianceVo.get(0).getVendorId())+"");
	    	complianceTypesList = defineService.getComplianceListByApplicable(vendorComplianceVo.get(0).getCustomerId(), vendorComplianceVo.get(0).getCompanyId(),"Vendor");
	    	statesList = commonService.getStatesList(vendorComplianceVo.get(0).getCountryId());
	    	//vendorComplianceList =vendorComplianceService.getVendorComplianceListBySearch(vendorComplianceVo.get(0));
	    	CompanyProfileVo profileVo = companyService.getCompanyProfileByCompanyId(vendorComplianceVo.get(0).getCompanyId()+"",vendorComplianceVo.get(0).getCustomerId()+"");
	    	LocationVo locaVO = new LocationVo();
	    	locaVO.setCustomerId(vendorComplianceVo.get(0).getCustomerId());
	    	locaVO.setCompanyId(vendorComplianceVo.get(0).getCompanyId());
	    	locaVO.setCountryId(vendorComplianceVo.get(0).getCountryId());	    	
	    	List<SimpleObject> locationList = vendorComplianceService.getLocationListByVendor(vendorComplianceVo.get(0).getCustomerId(), vendorComplianceVo.get(0).getCompanyId(), vendorComplianceVo.get(0).getVendorId());
	    	
	    	returnList.put("locationList",locationList);
			log.debug("profileVo : "+profileVo.getBussinessStartTime());
			profileList.add(profileVo);	
			
			returnList.put("profileList",profileList);
	    	
	    }
	    
		returnList.put("vendorComplianceVo",vendorComplianceVo);
		returnList.put("customerList", customerList);
		returnList.put("countryList",countryList);
		returnList.put("companyList",companyList);
		returnList.put("vendorList",vendorList);
		returnList.put("complianceTypesList",complianceTypesList);
		returnList.put("statesList", statesList);
		returnList.put("currencyList",currencyList);
		//returnList.put("vendorComplianceList",vendorComplianceList);
		
		log.info("Exiting from Vendor Compliance Controller getVendorComplianceById() ::  "+returnList);
		return new ResponseEntity<Map<String,List>>(returnList, HttpStatus.OK);
	}

	/*
	 * This method will be used to get transaction dates list for Vendor Compliance based on given customerId, companyId and VendorComplianceUniqueId
	 */
	@RequestMapping(value = "/getVendorComplianceTransactionDatesList.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getTransactionListForVendorCompliance(@RequestBody String paramCompliance) throws JSONException {
		log.info("Entered in Vendor Compliance Controller getTransactionListForVendorCompliance() ::  "+paramCompliance);

		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramCompliance);
		log.debug("JSON Object "+jo);
		
		String customerId = null ,companyId = null, vendorComplianceUniqueId = null;
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
								&& jo.get("vendorComplianceUniqueId") != null && jo.get("vendorComplianceUniqueId").getAsString() != null
								&& jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
			customerId = jo.get("customerId").getAsString(); 
			companyId = jo.get("companyId").getAsString();			
			vendorComplianceUniqueId = jo.get("vendorComplianceUniqueId").getAsString(); 
			log.debug("Customer Id:  "+customerId+"\t Company Id :   "+companyId+"\t Vendor Compliance Id : "+vendorComplianceUniqueId);
		}
		List<SimpleObject> simpleObjects = vendorComplianceService.getTransactionListForVendorCompliance(Integer.valueOf(customerId), Integer.valueOf(companyId),  Integer.valueOf(vendorComplianceUniqueId));	
		log.info("Exiting from Vendor Compliance Controller getTransactionListForVendorCompliance() ::  "+simpleObjects);
		return new ResponseEntity<List<SimpleObject>>(simpleObjects, HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get the Vendor Compliance record  based on Vendor Compliance details Id
	 */	
	@RequestMapping(value = "/getDropdowns.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getDropdowns(@RequestBody String paramCompliance) throws JSONException {
		log.info("Entered in Vendor Compliance Controller getDropdowns()  ::   "+paramCompliance);
		Map<String,List> returnList = new HashMap<String,List>();
		
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramCompliance);
		log.debug("JSON Object:  "+jo);
		
		String companyId = null, customerId = null, vendorId=null;
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
						|| jo.get("companyId") != null && jo.get("companyId").getAsString() != null || jo.get("vendorId") != null && jo.get("vendorId").getAsString() != null){
			customerId = jo.get("customerId").getAsString(); 
			companyId = jo.get("companyId").getAsString(); 
			vendorId = jo.get("vendorId").getAsString(); 
			log.debug("customer d : "+customerId+" \t CompanyId : "+companyId+" \t vendorId : "+vendorId);
		}
	   
	    List<SimpleObject> 	countryList = commonService.getCompanyCountries(Integer.valueOf(companyId.trim()));
	    List<SimpleObject> vendorList = vendorService.getVendorNamesAsDropDown(customerId, companyId,vendorId);
	    //	    List<VendorDetailsVo> vendorList = vendorService.getVendorDetailsList(customerId, companyId, vendorId, null);
	    List<DefineComplianceTypesVo> complianceTypesList = defineService.getComplianceListByApplicable(Integer.valueOf(customerId.trim()), Integer.valueOf(companyId.trim()), "Vendor");
	    
	    
		returnList.put("countryList",countryList);
		returnList.put("vendorList",vendorList);
		returnList.put("complianceTypesList",complianceTypesList);
		
		log.info("Exiting from Vendor Compliance Controller getDropdowns() ::  "+returnList);
		return new ResponseEntity<Map<String,List>>(returnList, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getLocationListByVendor.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getLocationListByVendor(@RequestBody String paramCompliance) throws JSONException {
		log.info("Entered in Vendor Compliance Controller getDropdowns()  ::   "+paramCompliance);
		Map<String,List> returnList = new HashMap<String,List>();
		
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramCompliance);		
		
		Integer companyId = null, customerId = null, vendorId=null;
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
						&& jo.get("companyId") != null && jo.get("companyId").getAsString() != null && jo.get("vendorId") != null && jo.get("vendorId").getAsString() != null && jo.get("vendorId").getAsInt() > 0 ){
			customerId = jo.get("customerId").getAsInt(); 
			companyId = jo.get("companyId").getAsInt(); 
			vendorId = jo.get("vendorId").getAsInt(); 
			log.debug("customer d : "+customerId+" \t CompanyId : "+companyId+" \t vendorId : "+vendorId);
		}
		List<SimpleObject> locationList = vendorComplianceService.getLocationListByVendor(customerId, companyId, vendorId);
    	
    	returnList.put("locationList",locationList);
		
		log.info("Exiting from Vendor Compliance Controller getDropdowns() ::  "+returnList);
		return new ResponseEntity<Map<String,List>>(returnList, HttpStatus.OK);
	}
	
	@RequestMapping(value ="/downloadfile", method = RequestMethod.POST)
	public  void downloadFile(HttpServletRequest request, HttpServletResponse response, @RequestBody String filePath) {
		System.out.println(filePath);
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(filePath);
		log.debug("JSON Object:  "+jo);
		
		String path = null;
		if(jo.get("path") != null && jo.get("path").getAsString() != null){
			path = jo.get("path").getAsString(); 
			log.debug("Path  : "+path);
		}
		

        File file = new File (path);
        log.trace("Write response...");
        try (InputStream fileInputStream = new FileInputStream(file);
                OutputStream output = response.getOutputStream();) {

            response.reset();

            response.setContentType("application/octet-stream");
            response.setContentLength((int) (file.length()));

            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

            IOUtils.copyLarge(fileInputStream, output);
            output.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
         System.out.println(path);
	}
}
