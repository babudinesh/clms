package com.Ntranga.CLMS.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Ntranga.CLMS.Service.CommonService;
import com.Ntranga.CLMS.Service.CompanyService;
import com.Ntranga.CLMS.Service.VendorRegisterTypesService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.vo.CompanyProfileVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.VendorDamageOrLossDetailsVo;
import com.Ntranga.CLMS.vo.VendorFineRegisterVo;
import com.Ntranga.CLMS.vo.WorkerAdvanceAmountTakenVo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
@RequestMapping(value = "vendorRegisterTypesController")
@SuppressWarnings({"rawtypes","unchecked"})
public class VendorRegisterTypesController {
	
	private static Logger log = Logger.getLogger(VendorRegisterTypesController.class);
	

	
	@Autowired
   private VendorRegisterTypesService vendorRegisterTypesService;

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private CommonService commonService;
	
	
	
	@RequestMapping(value = "/getGridData.json", method = RequestMethod.POST)
	public  ResponseEntity<List<WorkerAdvanceAmountTakenVo>> getGridData(@RequestBody String vendorJsonString) throws Exception {
		log.info("Entered in VendorRegisterTypes Controller saveVendorDetails() ::  "+vendorJsonString);
		System.out.println(vendorJsonString);

		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject)jsonParser.parse(vendorJsonString);   
		List<WorkerAdvanceAmountTakenVo> workerAdvaneAmountList =  new ArrayList();

		try{
			if(vendorJsonString != null) {	   
				workerAdvaneAmountList = vendorRegisterTypesService.getVendorRegisterTypes(jo.get("customerId").getAsString(),jo.get("companyId").getAsString() , jo.get("vendorId").getAsString(), jo.get("employeeCode").getAsInt(), jo.get("employeeName").getAsString(), jo.get("registerType").getAsString(), jo.get("year").getAsInt(), jo.get("month").getAsInt());
			}
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("Error Occured ",e);
		}
		log.info("Exiting from VendorRegisterTypes Controller saveVendorDetails() ::  "+workerAdvaneAmountList);
		return new ResponseEntity<List<WorkerAdvanceAmountTakenVo>>(workerAdvaneAmountList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAdvanceAmountById.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String, List>> getAdvanceAmountById(@RequestBody WorkerAdvanceAmountTakenVo  paramAdvance) throws Exception {
		log.info("Entered in VendorRegisterTypes Controller  getAdvanceAmountById() ::  "+paramAdvance);
		Map<String, List> returnMap = new HashMap<String, List>();
		
		List<CompanyProfileVo> profileList = new ArrayList<CompanyProfileVo>();
		List<SimpleObject> currencyList = commonService.getCurrencyList();
		
		SimpleObject object = new SimpleObject();
		try{
			
			List<WorkerAdvanceAmountTakenVo> advanceList = new ArrayList<WorkerAdvanceAmountTakenVo>();
		if (paramAdvance.getWorkerId() != null){
			advanceList = vendorRegisterTypesService.getVendorDetailsList(paramAdvance.getWorkerId());
			
			if(advanceList != null && advanceList.size() > 0 ){
				CompanyProfileVo profileVo = companyService.getCompanyProfileByCompanyId(advanceList.get(0).getCompanyId()+"",advanceList.get(0).getCustomerId()+"");  
				profileList.add(profileVo);
			}
		}
		
		returnMap.put("advanceList",advanceList);
		returnMap.put("currencyList",currencyList);
		returnMap.put("profileList",profileList);
		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from VendorRegisterTypes Controller getAdvanceAmountById() ::  "+object);
		return new ResponseEntity<Map<String,List>>(returnMap, HttpStatus.OK);
	}
	
	
	
	
	@RequestMapping(value = "/saveOrUpdateVendorRegisterTypes.json", method = RequestMethod.POST)
	public  ResponseEntity<SimpleObject> saveOrUpdateVendorRegisterTypes(@RequestBody WorkerAdvanceAmountTakenVo  advanceAmountTakenVo) throws Exception {
		log.info("Entered in Vendor Controller saveVendorDetails() ::  ");
		
		SimpleObject object = new SimpleObject();
		try{
		Integer amountTakenId = null;
		if (advanceAmountTakenVo != null)
			amountTakenId = vendorRegisterTypesService.saveOrUpdateVendorRegisterTypes(advanceAmountTakenVo);
	
		if (amountTakenId != null) {				
			object.setId(amountTakenId);		
			object.setName("success");
		} else {
			object.setId(0);		
			object.setName("Error in saving Vendor Register Types");
		}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from saveOrUpdateVendorRegisterTypes() ::  "+object);
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/saveVendorDamage.json", method = RequestMethod.POST)
	public  ResponseEntity<SimpleObject> saveVendorDamage(@RequestBody VendorDamageOrLossDetailsVo  paramDamage) throws Exception {
		log.info("Entered in VendorRegisterTypes Controller saveVendorDamage() ::  "+paramDamage);
		
		SimpleObject object = new SimpleObject();
		try{
		Integer vendorDamageId = null;
		if (paramDamage != null)
			vendorDamageId = vendorRegisterTypesService.saveVendorDamageDetails(paramDamage);
	
		if (vendorDamageId != null) {				
			object.setId(vendorDamageId);		
			object.setName("success");
		} else {
			object.setId(0);		
			object.setName("Error in VendorRegisterTypes Controller saveVendorDamage");
		}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from VendorRegisterTypes Controller saveVendorDamage() ::  "+object);
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getVendorDamageById.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String, List>> getVendorDamageById(@RequestBody VendorDamageOrLossDetailsVo  paramDamage) throws Exception {
		log.info("Entered in VendorRegisterTypes Controller  getVendorDamageById() ::  "+paramDamage);
		Map<String, List> returnMap = new HashMap<String, List>();
		
		List<CompanyProfileVo> profileList = new ArrayList<CompanyProfileVo>();
		List<SimpleObject> currencyList = commonService.getCurrencyList();
		
		SimpleObject object = new SimpleObject();
		try{
			
			List<VendorDamageOrLossDetailsVo> damageList = new ArrayList<VendorDamageOrLossDetailsVo>();
		if (paramDamage.getWorkerId() != null){
			damageList = vendorRegisterTypesService.getDamageDetailsById(paramDamage.getWorkerId());
			
			if(damageList != null && damageList.size() > 0 ){
				CompanyProfileVo profileVo = companyService.getCompanyProfileByCompanyId(damageList.get(0).getCompanyId()+"",damageList.get(0).getCustomerId()+"");  
				profileList.add(profileVo);
			}
		}
		
		returnMap.put("damageList",damageList);
		returnMap.put("currencyList",currencyList);
		returnMap.put("profileList",profileList);
		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from VendorRegisterTypes Controller getVendorDamageById() ::  "+object);
		return new ResponseEntity<Map<String,List>>(returnMap, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveVendorFines.json", method = RequestMethod.POST)
	public  ResponseEntity<SimpleObject> saveVendorFines(@RequestBody VendorFineRegisterVo paramFine) throws Exception {
		log.info("Entered in VendorRegisterTypes Controller saveVendorFines() ::  "+paramFine);
		
		SimpleObject object = new SimpleObject();
		try{
		Integer fineRegisterId = null;
		if (paramFine != null)
			fineRegisterId = vendorRegisterTypesService.saveVendorFines(paramFine);
	
		if (fineRegisterId != null) {				
			object.setId(fineRegisterId);		
			object.setName("success");
		} else {
			object.setId(0);		
			object.setName("Error in saving Vendor Register Types");
		}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from VendorRegisterTypes Controller saveVendorFines() ::  "+object);
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getVendorFinesById.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String, List>> getVendorFinesById(@RequestBody VendorFineRegisterVo paramFine) throws Exception {
		log.info("Entered in VendorRegisterTypes Controller  getVendorFinesById() ::  "+paramFine);
		Map<String, List> returnMap = new HashMap<String, List>();
		
		List<CompanyProfileVo> profileList = new ArrayList<CompanyProfileVo>();
		List<SimpleObject> currencyList = commonService.getCurrencyList();
		
		SimpleObject object = new SimpleObject();
		try{
			
			List<VendorFineRegisterVo> finesList = new ArrayList<VendorFineRegisterVo>();
		if (paramFine.getWorkerId() != null){
			finesList = vendorRegisterTypesService.getVendorFinesById(paramFine.getWorkerId());
			
			if(finesList != null && finesList.size() > 0 ){
				CompanyProfileVo profileVo = companyService.getCompanyProfileByCompanyId(finesList.get(0).getCompanyId()+"",finesList.get(0).getCustomerId()+"");  
				profileList.add(profileVo);
			}
		}
		
		returnMap.put("finesList",finesList);
		returnMap.put("currencyList",currencyList);
		returnMap.put("profileList",profileList);
		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from VendorRegisterTypes Controller getVendorFinesById() ::  "+object);
		return new ResponseEntity<Map<String,List>>(returnMap, HttpStatus.OK);
	}
	
	
	
}
