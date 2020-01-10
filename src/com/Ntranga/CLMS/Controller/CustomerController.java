package com.Ntranga.CLMS.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Ntranga.CLMS.Service.CommonService;
import com.Ntranga.CLMS.Service.CustomerService;
import com.Ntranga.CLMS.vo.AddressVo;
import com.Ntranga.CLMS.vo.ContactVo;
import com.Ntranga.CLMS.vo.CountryVo;
import com.Ntranga.CLMS.vo.CustomerCountriesVo;
import com.Ntranga.CLMS.vo.CustomerDetailsVo;
import com.Ntranga.CLMS.vo.SectorVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.core.CLMS.entities.CustomerAddress;
import com.Ntranga.core.CLMS.entities.CustomerContact;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.MAddressType;
import com.Ntranga.core.CLMS.entities.MContactType;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@SuppressWarnings({"rawtypes","unchecked"})
@RestController
@RequestMapping(value = "CustomerController")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CommonService commonService;
	
	private static Logger log = Logger.getLogger(CustomerController.class);

	
	// To get all countries in JSON Format
	@RequestMapping(value = "/countriesList.json", method = RequestMethod.GET)
	public ResponseEntity<List<SimpleObject>> getCountriesList() {		
		log.info("Entered in Customer Controller getCountriesList()");
		
		List<SimpleObject> countryList = commonService.getCountriesList();
		log.debug("List of countries : "+countryList);    
		
		log.info("Exiting from Customer Controller getCountriesList() ::  "+countryList);
		return  new ResponseEntity<List<SimpleObject>>(countryList,HttpStatus.OK);
	}
	
	// For getting Country List based on country selected at Customer Details Page -- Return JSON
	@RequestMapping(value = "/countriesListByCustomerId.json", method = RequestMethod.POST)
	public ResponseEntity<List<CountryVo>> getCountriesListByCustomerId(@RequestBody String customeretails) {
		log.info("Entered in Customer Controller getCountriesListByCustomerId()");
		List<CountryVo> countryList = new ArrayList();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(customeretails);
		log.debug("JSON Object : "+jo);
		String data = jo.get("customerId").getAsString();
		log.debug("Data : "+data);	
		 countryList = customerService.getCountriesListByCustomerId(data);
		log.info("Exiting from Customer Controller countriesListByCustomerId()  ::  "+countryList);
		
		}catch(Exception e){
			log.error("Error in Customer Controller countriesListByCustomerId()  ::  "+e);
		}
		return new ResponseEntity<List<CountryVo>>(countryList,HttpStatus.OK);
	}

	
	/* StatesList by Country ID */
	@RequestMapping(value = "/statesListByCountryId.json", method = RequestMethod.POST)
	public String getStatesListByCountryId(@RequestBody String countryId) {		
		log.info("Entered in Customer Controller getStatesListByCountryId() ::  "+countryId);
		
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(countryId);
		log.debug("JSON Object : "+jo);
		
		String data = jo.get("countryId").getAsString();
		log.debug("Data : "+data);		
		
		List<SimpleObject> statesList = commonService.getStatesList(Integer.parseInt(data));
		log.debug("List of states: "+statesList);
		
		String statesLitJson = null;
		try {
			statesLitJson = JSONObject.valueToString(statesList).toString();
		} catch (JSONException e) {
			log.error("Error Occured. ",e);
			log.info("Exiting from Customer Controller getStatesListByCountryId()   ::  "+statesLitJson);
		}
		log.info("Exiting from Customer Controller getStatesListByCountryId()  ::  "+statesLitJson);
		return statesLitJson;
	}
	
	/* CitiesList by State ID */	
	@RequestMapping(value = "/cityLisyByStateId.json", method = RequestMethod.POST)
	public String getCityListByStateId(@RequestBody String stateId) {
		log.info("Entered in Customer Controller getCityListByStateId() ::  "+ stateId);
		
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(stateId);
		log.debug("JSON Object : "+jo);
		
		String data = jo.get("stateId").getAsString();
		log.debug("Data : "+data);	
		
		List<SimpleObject> cityList = commonService.getCitiesList(Integer.valueOf(data));
		log.debug("List of cities: "+cityList);
		
		String cityListJson = null;
		try {
			cityListJson = JSONObject.valueToString(cityList).toString();
		} catch (JSONException e) {
			log.error("Error Occured. ",e);
			log.info("Exiting from Customer Controller getCityListByStateId() ::  "+cityListJson);
		}
		log.info("Exiting from Customer Controller getCityListByStateId() ::  "+cityListJson);
		return cityListJson;
	}
	
	
	/********************** Customer Address start **************************/

							// For Customer Address Grid View -- return JSON Format
			@RequestMapping(value = "/getCustomerAddressGridList.json", method = RequestMethod.POST)
			public ResponseEntity<Map<String,List>> getCustomerAddressJsonList(@RequestBody String customerIdJson) throws JSONException {
				log.info("Entered in Customer Controller getCustomerAddressJsonList() ::  "+customerIdJson);
				Map<String, List> masterInfoMap = new HashMap<String,List>(); 
				try{
					
					JsonParser jsonParser = new JsonParser();
					JsonObject jo = (JsonObject) jsonParser.parse(customerIdJson);
					log.debug("JSON Object "+jo);		
					String customerId = null;
					if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null){
						customerId = jo.get("customerId").getAsString(); 
						log.debug("Customer Id : "+customerId);
					}		
					List<AddressVo> CustomerAddressList = new ArrayList();
					List<ContactVo> customerContactList = new ArrayList();
					if(customerId != null){
					 CustomerAddressList = customerService.getCustomerAddressList(Integer.valueOf(customerId));
					 customerContactList = customerService.getCustomerContactsList(Integer.valueOf(customerId.trim()));
					}
					log.debug("List of Customer Addresses: "+CustomerAddressList);
					masterInfoMap.putIfAbsent("CustomerAddressList", CustomerAddressList);	
					masterInfoMap.putIfAbsent("customerContactList", customerContactList);
				}catch(Exception e){
					log.error("Error Occured. ",e);
				}
				
				return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
			}
			

		// Customer Address details Save or Update Logic
			@RequestMapping(value = "/saveCustomerAddress.json", method = RequestMethod.POST)
			public ResponseEntity<SimpleObject> saveCustomerAddress(@RequestBody AddressVo customerAddressVo) {
				log.info("Entered in Customer Controller saveCustomer()  ::  ");
				SimpleObject object = new SimpleObject();
				//AddressVo customerAddressVo = new AddressVo();
				//System.out.println(customerAddressVo.getAddressTypeId() +" :: "+ customerAddressVo.getCustomerDetailsId());
				CustomerAddress customerAddress = new CustomerAddress();
				customerAddress.setAddressId(customerAddressVo.getAddressId());
				customerAddress.setAddress1(customerAddressVo.getAddress1());
				customerAddress.setAddress2(customerAddressVo.getAddress2());
				customerAddress.setAddress3(customerAddressVo.getAddress3());
				customerAddress.setIsActive(customerAddressVo.getIsActive()+"");
				customerAddress.setCountry(customerAddressVo.getCountryId());
				customerAddress.setState(customerAddressVo.getStateId());
				customerAddress.setCity(customerAddressVo.getCity());
				customerAddress.setPincode(Integer.valueOf(customerAddressVo.getPincode()));
				customerAddress.setMAddressType(new MAddressType(customerAddressVo.getAddressTypeId()));
				customerAddress.setCustomerDetails(new CustomerDetails(customerAddressVo.getCustomerDetailsId()));
				customerAddress.setCreatedBy(customerAddressVo.getCreatedBy());
				customerAddress.setModifiedBy(customerAddressVo.getModifiedBy());
				customerAddress.setCreatedDate(new Date());
				customerAddress.setModifiedDate(new Date());
				customerAddress.setAddressUniqueId(customerAddressVo.getAddressUniqueId());
				log.debug("Transaction Date : "+customerAddressVo.getTransactionDate());
				if(customerAddressVo.getTransactionDate() != null){
					customerAddress.setTransactionDate(customerAddressVo.getTransactionDate());
				}else{
					customerAddress.setTransactionDate(new Date());
				}
				Integer customerAddressId = null;
				if (customerAddress != null){
					customerAddressId = customerService.saveCustomerAddress(customerAddress);
					log.debug("Customer Address Id: "+customerAddressId);
				}
				try {
					if (customerAddressId > 0) {
						object.setId(customerAddressId);
						object.setName("success");
					} else {
						object.setId(0);
						object.setName("Fail");
					}
				} catch (Exception e) {
					log.error("Error Occured. ",e);
					log.info("Exiting from Customer Controller saveCustomer() ::  "+object);
	
				}
				log.info("Exiting from Customer Controller saveCustomer() ::  "+object);
				return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
			}
			
			
	    
	// For CustomerAddress Details Retrieve Logic by customerAddressId
	@RequestMapping(value = "/CustomerAddressMasterDetailsList.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> CustomerAddressMasterDetailsList(@RequestBody String customerAddressIdJson) {
		log.info("Entered in Customer Controller CustomerAddressMasterDetailsList() ");
		Map<String, List> masterInfoMap = new HashMap<String,List>(); 
				
		try {			
			List<SimpleObject> contactTypes = commonService.getContactTypeList();
			log.debug("Type of Contacts : "+contactTypes);
			masterInfoMap.putIfAbsent("contactTypes", contactTypes);
			List<SimpleObject> addressTypes = commonService.getAddressTypeList();
			log.debug("List of Address Types:  "+addressTypes);
			 
			   
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(customerAddressIdJson);
			log.debug("JSON Object "+jo);
			List<AddressVo> addressVo = new ArrayList<AddressVo>();
			String customerAddressId = null;
			if(jo.get("addressId") != null && jo.get("addressId").getAsString() != null ){
				customerAddressId = jo.get("addressId").getAsString(); 
			 addressVo= customerService.getCustomerAddressRecordById(Integer.valueOf(customerAddressId.trim()));
			}	
			if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null ){
				List<CountryVo> countryList = customerService.getCountriesListByCustomerId(jo.get("customerId").getAsString());
			    masterInfoMap.put("countriesList", countryList);
			}
			masterInfoMap.putIfAbsent("addressTypes", addressTypes);
			masterInfoMap.putIfAbsent("addressVo", addressVo);
			} catch (Exception e) {
				log.error("Error Occured. ",e);			
			}
			
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	} 
	
	
	
	// For getting Customer Address Transaction History List --  return JSON Object
	@RequestMapping(value = "/getTransactionListForCustomerAddress.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getTransactionListForCustomerAddress(@RequestBody String customerAddress) throws JSONException {
		log.info("Entered in Customer Controller getTransactionListForCustomerAddress() ::  "+customerAddress);

		JsonParser jsonParser = new JsonParser();
		List<SimpleObject> simpleObjects = new ArrayList();
		try{
		JsonObject jo = (JsonObject) jsonParser.parse(customerAddress);
		log.debug("JSON Object "+jo);
		
		String customerId = null , addressUniqueId = null;
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null && jo.get("addressUniqueId") != null && jo.get("addressUniqueId").getAsString() != null ){
			customerId = jo.get("customerId").getAsString(); 
			log.debug("Customer Id : "+customerId);
			addressUniqueId = jo.get("addressUniqueId").getAsString(); 
			log.debug("address Unique Id : "+addressUniqueId);
		}
		 simpleObjects = customerService.getTransactionListForCustomerAddress(Integer.valueOf(customerId), Integer.valueOf(addressUniqueId));	
		log.debug("JSON Object "+simpleObjects.size());
		}catch(Exception e){
			log.error("Error Occured. ",e);		
		}
		return new ResponseEntity<List<SimpleObject>>(simpleObjects, HttpStatus.OK);
	}
	
	/********************** Customer Address End **************************/
	
	
	
	
	
	
	
	
	// To get Customer Adress record by address Id return JSON
	@RequestMapping(value = "/getCustomerCurrentAddressRecordsByCustomerId.json", method = RequestMethod.POST)
	public String getCustomerCurrentAddressRecordsByCustomerId(@RequestBody String customerDetailsId) throws JSONException {
		log.info("Entered in Customer Controller getCustomerCurrentAddressRecordsByCustomerId() ::  "+customerDetailsId);
		
		List<SimpleObject> object = new ArrayList<SimpleObject>();
		
		JsonParser jsonParser = new JsonParser();
		try{
		JsonObject jo = (JsonObject) jsonParser.parse(customerDetailsId);
		log.debug("JSON Object : "+jo);
		
		String customerId = null;
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null){
			customerId = jo.get("customerId").getAsString();  
			log.debug("Customer Id : "+customerId);
		}
		// String CustomerAddressVoJson = null;
		object = customerService.getCustomerCurrentAddressRecordsByCustomerId(Integer.valueOf(customerId.trim()));
		// CustomerAddressVoJson = JSONObject.valueToString(object).toString();
		log.info("Exiting from Customer Controller getCustomerCurrentAddressRecordsByCustomerId() ::  "+JSONObject.valueToString(object).toString());
		// CustomerAddressVoJson = JSONObject.valueToString(object).toString();
		}catch(Exception e){
			log.error("Error Occured. ",e);		
		}
		return  JSONObject.valueToString(object).toString(); // new ResponseEntity<List<SimpleObject>>(object, HttpStatus.OK);
	}
	
	

	





/***************************Customer Contact Start ***************/
	
	
	
	
	
	
	@RequestMapping(value = "/CustomerContactMasterDetailsList.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> CustomerContactMasterDetailsList(@RequestBody String customerIdJson) {
		log.info("Entered in Customer Controller CustomerAddressMasterDetailsList() ");
		Map<String, List> masterInfoMap = new HashMap<String,List>(); 
				
		try {			
			List<SimpleObject> contactTypes = commonService.getContactTypeList();
			log.debug("Type of Contacts : "+contactTypes);
			
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(customerIdJson);
			log.debug("JSON Object:  "+customerIdJson);
			
			String customerId = null;
			if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null){
				customerId = jo.get("customerId").getAsString();  
				log.debug("customerId Id : "+customerId);
			}
			
			
			List<SimpleObject> customerAddressList = customerService.getCustomerCurrentAddressRecordsByCustomerId(Integer.valueOf(customerId.trim()));
				   
			
			List<ContactVo> contactVo = new ArrayList<ContactVo>();
			masterInfoMap.putIfAbsent("customerAddressList", customerAddressList);				
			masterInfoMap.putIfAbsent("contactTypes", contactTypes);
			masterInfoMap.putIfAbsent("contactVo", contactVo);
			} catch (Exception e) {
				log.error("Error Occured. ",e);			
			}
			
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	
	
	
	/*
	 * To save the customer contact details
	 */
	@RequestMapping(value = "/saveCustomerContact.json", method = RequestMethod.POST)
	public ResponseEntity<SimpleObject> saveCustomerContact(@RequestBody ContactVo contact) {
		log.info("Entered in Customer Controller saveCustomerContact() ::  "+contact);
		
		SimpleObject object = new SimpleObject();
		CustomerContact customer = new CustomerContact();
		customer.setCustomerDetails(new CustomerDetails(contact.getCustomerId()));
		customer.setAddressUniqueId(contact.getAddressId());
    	customer.setContactId(contact.getContactId());
    	if(contact.getContactUniqueId() != null && (contact.getContactUniqueId()).intValue() >0){
    		customer.setContactUniqueId((contact.getContactUniqueId()).intValue());
  		}else{
  			customer.setContactUniqueId(0);  			
  		}
    	
    	customer.setMContactType(new MContactType(contact.getContactTypeId()));
    	customer.setContactName(contact.getContactName());
    	customer.setContactNameOtherLanguage(contact.getContactNameOtherLanguage());
    	customer.setTransactionDate(contact.getTransactionDate());
    	customer.setEmailId(contact.getEmailId());
    	customer.setBusinessPhoneNumber(contact.getBusinessPhoneNumber());
    	customer.setExtensionNumber(contact.getExtensionNumber());
    	customer.setIsActive(contact.getIsActive()+"");
    	customer.setMobileNumber(contact.getMobileNumber());
    	customer.setCreatedBy(contact.getCreatedBy());
    	customer.setModifiedBy(contact.getModifiedBy());
    	customer.setCreatedDate(new Date());
    	customer.setModifiedDate(new Date());
		log.debug("Transaction Date : "+contact.getTransactionDate());

		if(contact.getTransactionDate() != null){
			customer.setTransactionDate(contact.getTransactionDate());
		}else{
			customer.setTransactionDate(new Date());
		}
		Integer contactId = null;
		if (customer != null)
			contactId = customerService.saveCustomerContact(customer);
		System.out.println(contactId);
		try {
			if (contactId > 0) {
				object.setId(contactId);
				object.setName("Success");
			} else {
				object.setId(0);
				object.setName("Fail");
			}
		} catch (Exception e) {
			log.error("Error Occured. ",e);
			log.info("Exiting from Customer Controller saveCustomerContact() ::  "+object);
		}
		log.info("Exiting from Customer Controller saveCustomerContact() ::  "+object);
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}

	/*
	 * To get the customer contacts list on page load
	 */
	
  
	
	/*
	 * To get the customer contacts by customer Id
	 */
	
  
  
	/*
	 * To get the customer contacts list based on contact Id
	 */	
	@RequestMapping(value = "/getCustomerContactRecordById.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> getCustomerContactById(@RequestBody String customerIdContactIdJson) throws JSONException {
		log.info("Entered in Customer Controller getCustomerContactById()  ::   "+customerIdContactIdJson);
		Map<String, Object> masterInfoMap = new HashMap<String,Object>(); 
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(customerIdContactIdJson);
		log.debug("JSON Object:  "+customerIdContactIdJson);
		List<SimpleObject> customerAddressList =  new ArrayList();
		String contactId = null;
		String customerId = null;
		try{
		if(jo.get("contactId") != null && jo.get("contactId").getAsString() != null){
			contactId = jo.get("contactId").getAsString();  
			log.debug("Contact Id : "+contactId);
		}
		
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null){
			customerId = jo.get("customerId").getAsString();  
			 customerAddressList = customerService.getCustomerCurrentAddressRecordsByCustomerId(Integer.valueOf(customerId.trim()));
			log.debug("Contact Id : "+contactId);
		}
		
	    ContactVo contactVo= customerService.getCustomerContactRecordById(Integer.valueOf(contactId.trim()));
	    List<SimpleObject> contactTypes = commonService.getContactTypeList();
		log.debug("Type of Contacts : "+contactTypes);
		
		
		masterInfoMap.put("customerAddressList", customerAddressList);				
		masterInfoMap.put("contactTypes", contactTypes);
		masterInfoMap.put("contactVo", contactVo);
		
		log.info("Exiting from Customer Controller getCustomerContactsBycustomerId() ::  "+contactVo);
		}catch(Exception e){
			log.error("Error Occured in CustomerController method getCustomerContactById. ",e);
		}
		return new ResponseEntity<Map<String,Object>>(masterInfoMap, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/viewCustomerContactHistory.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getTransactionDatesList(@RequestBody String customerContact) throws JSONException {
		log.info("Entered in Customer Controller getTransactionDatesList() ::  "+customerContact);

		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(customerContact);
		log.debug("JSON Object "+jo);
		List<SimpleObject> simpleObjects = new ArrayList();
		try{
		String customerId = null , contactUniqueId = null;
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null && jo.get("contactUniqueId") != null && jo.get("contactUniqueId").getAsString() != null ){
			customerId = jo.get("customerId").getAsString(); 
			log.debug("Customer Id : "+customerId);
			contactUniqueId = jo.get("contactUniqueId").getAsString(); 
			log.debug("Contact Unique Id : "+contactUniqueId);
		}
		 simpleObjects = customerService.getTransactionListForContact(Integer.valueOf(customerId), Integer.valueOf(contactUniqueId));	
		log.info("Exiting from Customer Controller getTransactionDatesList() ::  "+simpleObjects);
		}catch(Exception e){
			log.error("Error Occured in CustomerController method viewCustomerContactHistory. ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(simpleObjects, HttpStatus.OK);
	}
	
	
	
	
	
  	/***************************Customer Contact End ***************/
	
 // Sai end 
	
	
	
	// Shiva start
	
	/*
	 * To get the customer Details list 
	 */	
	 @RequestMapping(value="/CustomerGridList.json", method = RequestMethod.POST)
		public ResponseEntity<List<CustomerDetailsVo>>  CustomerGridView(@RequestBody String customerIdJson) throws Exception {
		 String customerId = "";
		 JsonParser jsonParser = new JsonParser();
		 List<CustomerDetailsVo> customerDetailsVo  = new ArrayList();
		 try{
				if(customerIdJson != null) {
			    JsonObject jo = (JsonObject )jsonParser.parse(customerIdJson);    
			     customerId =  jo.get("customerId").getAsString();
				}		 
				 customerDetailsVo  = customerService.getCustomerDetailsList(customerId);		
		 	}catch(Exception e){
				log.error("Error Occured in CustomerController method CustomerGridList. ",e);
			}
			return  new ResponseEntity<List<CustomerDetailsVo>> (customerDetailsVo,HttpStatus.OK);
		}
	 
	 
	 
	
	  @RequestMapping(value="/CustomerDtails.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>>   CustomerView(@RequestBody String customerInfoIdJson) throws Exception {
		log.info("Entered in Customer Controller CustomerView() "+customerInfoIdJson);
		Map<String,List> masterInfoMap = new HashMap<String,List>();	
		List<SimpleObject> industyList = commonService.getIndustriesList();		
		log.debug("List of Industries: " +industyList);
		List<SimpleObject> countryList = commonService.getCountriesList();
		log.debug("List of Countries: " +countryList);
		List<SimpleObject> languageList = commonService.getLanguageList();
		log.debug("List of languageList: " +languageList);
		List<SimpleObject> currencyList = commonService.getCurrencyList();
		log.debug("List of currencyList: " +currencyList);		
		
		JsonParser jsonParser = new JsonParser();			
	    JsonObject jo = (JsonObject )jsonParser.parse(customerInfoIdJson);
	    log.debug("JSON Object :  "+jo);
	    String customerInfoId = "";
	    
	    List<CustomerDetailsVo> customerList = new ArrayList<CustomerDetailsVo>();
	    List<CustomerCountriesVo> customerCountriesList = new ArrayList<CustomerCountriesVo>();
	    try{
	    if(jo.get("customerInfoId") != null && jo.get("customerInfoId").getAsString() != null && jo.get("customerInfoId").getAsInt()>0){
	    	   customerInfoId =  jo.get("customerInfoId").getAsString();
	    	  customerList = customerService.customerDetailsByCustomerInfoId(Integer.parseInt(customerInfoId));
	    	  customerCountriesList  = customerService.customerCountryList(Integer.parseInt(customerInfoId));
	    	  log.debug("customer Id : "+customerInfoId); 
	    }
	    masterInfoMap.put("industyList", industyList);	
		masterInfoMap.put("countriesList", countryList);
		masterInfoMap.put("languageList", languageList);	
		masterInfoMap.put("currencyList", currencyList);	
		masterInfoMap.put("customerList", customerList);
		masterInfoMap.put("customerList", customerList);
		masterInfoMap.put("customerCountriesList", customerCountriesList);
	   
	    }catch(Exception e){
	    	log.error("Error Occured in CustomerController method CustomerView. ",e);
	    }
		
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	  
	  

	

			/*
			 *  Saving customer Details list 
			 */	
	@RequestMapping(value = "/saveCustomer.json", method = RequestMethod.POST)
	public  ResponseEntity<SimpleObject> saveCustomer(@RequestBody String customerJsonString) throws Exception {
		log.info("Entered in Customer Controller saveCustomer() ::  "+customerJsonString);
		System.out.println("customerJsonString::"+customerJsonString);
		SimpleObject object = new SimpleObject();
		Integer customerId = null;
		if (customerJsonString != null)
			customerId = customerService.saveCustomer(customerJsonString);
		if (customerId != null) {				
			object.setId(customerId);				
		} else {
	
		}
		log.info("Exiting from Customer Controller saveCustomer() ::  "+object);
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}
	 
		
		
	  /* Fetching Industry Master Data*/
	  
	  @RequestMapping(value="/industriesList.json", method = RequestMethod.GET)
	public  String getIndustriesList() {
	    log.info("Entered in Customer Controller getIndustriesList() ");
	    List<SimpleObject> customerList = commonService.getIndustriesList();	
	    log.debug("List of Customers:  " +customerList);
	    String cutomerLists  = null;
	    try {
			 cutomerLists =  JSONObject.valueToString(customerList).toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//log.error("Error Occured ",e);
			log.error("Error Occured...",e);
			log.info("Exiting from Customer Controller getIndustriesList() ::  "+cutomerLists); 
		}
	   log.info("Exiting from Customer Controller getIndustriesList() ::  "+cutomerLists); 
	   return cutomerLists;
	}
	  
	 
	  /* Fetch Sector LIst By Industry ID */
	  
	 @RequestMapping(value="/sectorList.json", method = RequestMethod.POST)
	    public  String getSectorList(@RequestBody String IndustryData) {		  	
		   JsonParser jsonParser = new JsonParser();			
       JsonObject jo = (JsonObject )jsonParser.parse(IndustryData);     
       String data =  jo.get("industryId").getAsString();		
	        List<SectorVo> sectorList = commonService.getSectorList(Integer.parseInt(data));	    
	        String sectorLists  = null;
	        try {
	        	sectorLists =  JSONObject.valueToString(sectorList).toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				log.error("Error Occured ",e);
			}
	       
	      return sectorLists;
	    }
	 
	 
	 
	 /* Fetch Sectors List by Inustry Ids */
	  @RequestMapping(value="/sectorListByIndustryIds.json", method = RequestMethod.POST)
	  public  String getSectorsListByIndustryIds( @RequestBody String industryIds){
		  log.info("Entered in Customer Controller getSectorsListByIndustryIds()  ::  "+industryIds);
		  JsonParser jsonParser = new JsonParser();			
		  JsonObject jo = (JsonObject )jsonParser.parse(industryIds);
		  log.debug("JSON Object:  "+jo);
		  String data =  jo.get("industryIds").getAsString();
		  log.debug("industryIds :  "+data);
		  log.info("Exiting from Customer Controller getSectorsListByIndustryIds()  ");
		  return customerService.getInustrySectorsList(data.replace("[", "").replace("]", ""));		
	  }
	
	  
		/*
		 * Validating Custoemr Name
		 */	
	  @RequestMapping(value="/validateCustomerName.json", method = RequestMethod.POST)
	  public  String validateCustomerName(@RequestBody String customeName) {
		  	log.info("Entered in Customer Controller validateCustomerName() ::  "+customeName);
	        String customerList =null;	 
	        String customerID = null;
	        JsonParser jsonParser = new JsonParser();			
	        JsonObject jo = (JsonObject )jsonParser.parse(customeName);
	        log.debug("JSON Object:  "+jo);
	        String CustomerNmae =  jo.get("customerName").getAsString();
	        log.debug("Customer name :  "+customeName);
	        if(jo.get("customerId") != null){
	         customerID =  jo.get("customerId").getAsString();
	         log.debug("customer Id: "+customerID);
	        }
	        
	        try {
	        	customerList =  JSONObject.valueToString(customerService.validateCustomerName(CustomerNmae,customerID)).toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("Error Occured ",e);
			}
	        log.info("Exiting from Customer Controller validateCustomerName() ::  "+customerList);
	        return customerList;
	  }
	  
	  
	  @RequestMapping(value="/validateCustomerCode.json", method = RequestMethod.POST)
	  public  String validateCustomerCode(@RequestBody String customeName) {
		  	log.info("Entered in Customer Controller validateCustomerName() ::  "+customeName);
	        String customerList =null;	 
	        String customerID = null;
	        JsonParser jsonParser = new JsonParser();			
	        JsonObject jo = (JsonObject )jsonParser.parse(customeName);
	        log.debug("JSON Object:  "+jo);
	        String CustomerCode =  jo.get("customerCode").getAsString();
	        log.debug("CustomerCode :  "+CustomerCode);
	        if(jo.get("customerId") != null){
	         customerID =  jo.get("customerId").getAsString();
	         log.debug("customer Id: "+customerID);
	        }
	        
	        try {
	        	customerList =  JSONObject.valueToString(customerService.validateCustomerCode(CustomerCode,customerID)).toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("Error Occured ",e);
			}
	        log.info("Exiting from Customer Controller validateCustomerName() ::  "+customerList);
	        return customerList;
	  }
	  
	  
	  /*
		 * To get the customer Details list 
		 */	
		 @RequestMapping(value="/CustomerCountryList.json", method = RequestMethod.POST)
			public String  CustomerCountryList(@RequestBody String customerIdJson) throws Exception {
				JsonParser jsonParser = new JsonParser();			
			    JsonObject jo = (JsonObject )jsonParser.parse(customerIdJson);
			    log.debug("JSON Object :  "+jo);
			    String customerCountryData = "";
			    List<CustomerCountriesVo> customerDetailsVo = new ArrayList<CustomerCountriesVo>();
			    
			    String customerId =  jo.get("customerId").getAsString();
			    log.debug("customer Id : "+customerId);    
			    if(customerId!= null && !customerId.equalsIgnoreCase("undefined") && !customerId.trim().isEmpty() && Integer.parseInt(customerId) >0){
			    	 customerDetailsVo  = customerService.customerCountryList(Integer.parseInt(customerId));
			    	
			    }
				
			    customerCountryData = JSONObject.valueToString(customerDetailsVo).toString();
			
				
				
				return customerCountryData;
			}
		 
		 
		 @RequestMapping(value="/CustomerCountryListbyCompanyInfoId.json", method = RequestMethod.POST)
			public String  CustomerCountryListbyCompanyInfoId(@RequestBody String customerInfoIdIdJson) throws Exception {
				JsonParser jsonParser = new JsonParser();			
			    JsonObject jo = (JsonObject )jsonParser.parse(customerInfoIdIdJson);
			    log.debug("JSON Object :  "+jo);
			    String customerCountryData = "";
			    List<CustomerCountriesVo> customerDetailsVo = new ArrayList<CustomerCountriesVo>();
			    
			    String customerInfoId =  jo.get("customerInfoId").getAsString();
			    log.debug("customerInfoId Id : "+customerInfoId);    
			    if(customerInfoId!= null && !customerInfoId.equalsIgnoreCase("undefined") && !customerInfoId.trim().isEmpty() && Integer.parseInt(customerInfoId) >0){
			    	 customerDetailsVo  = customerService.customerCountryListByCustomerInfoId(Integer.parseInt(customerInfoId));
			    	
			    }
				
			    customerCountryData = JSONObject.valueToString(customerDetailsVo).toString();
			
				
				
				return customerCountryData;
			}
		 
		 
		 @RequestMapping(value = "/getTransactionListForEditingCustomerDetails.json", method = RequestMethod.POST)
			public  ResponseEntity<List<SimpleObject>> getTransactionListForEditingCustomerDetails(@RequestBody String customerIdJson) throws JSONException {
				log.info("Entered in Customer Controller getTransactionListForEditingCustomerDetails() ::  "+customerIdJson);

				JsonParser jsonParser = new JsonParser();
				JsonObject jo = (JsonObject) jsonParser.parse(customerIdJson);
				log.debug("JSON Object "+jo);
				
				String customerId = null;
				if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null){
					customerId = jo.get("customerId").getAsString(); 
					log.debug("Customer Id : "+customerId);					
				}
				List<SimpleObject> simpleObjects = customerService.getTransactionListForEditingCustomerDetails(Integer.valueOf(customerId));	
				log.debug("JSON Object "+simpleObjects.size());
				return new ResponseEntity<List<SimpleObject>>(simpleObjects, HttpStatus.OK);
			}
		 
		
	 
	  
	// Shiva end
	
		 
		 @RequestMapping(value = "/validateCustomerAddress.json", method = RequestMethod.POST)
			public ResponseEntity<Boolean> validateCustomerAddress(@RequestBody String customerIdJson) throws JSONException {
				log.info("Entered in Customer Controller validateCustomerAddress() ::  "+customerIdJson);
				boolean returnFlag = false;
				try{
					
					JsonParser jsonParser = new JsonParser();
					JsonObject jo = (JsonObject) jsonParser.parse(customerIdJson);
					log.debug("JSON Object "+jo);		
					String customerId = null, addressTypeId = null;
					if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null){
						customerId = jo.get("customerId").getAsString(); 
						log.debug("Customer Id : "+customerId);
					}
					
					if(jo.get("addressTypeId") != null && jo.get("addressTypeId").getAsString() != null){
						addressTypeId = jo.get("addressTypeId").getAsString(); 
						log.debug("Address Type Id : "+addressTypeId);
					}
					
					//Integer validAddress = -1;
					if(customerId != null && customerId != "" && addressTypeId != null && addressTypeId != ""){
						returnFlag = customerService.validateCustomerAddress(Integer.parseInt(customerId), Integer.parseInt(addressTypeId));
					}
					log.debug("valid Address: "+returnFlag);
					
				}catch(Exception e){
					returnFlag = false;
					log.error("Error Occured. ",e);
				}
				
				return new ResponseEntity<Boolean>(returnFlag,HttpStatus.OK);
			}

}
