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
import com.Ntranga.CLMS.Service.ShiftsService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.ShiftsVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
@RequestMapping(value = "shiftsController")
public class ShiftsController {

	private static Logger log = Logger.getLogger(ShiftsController.class);
	
	@Autowired
	private ShiftsService shiftsService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private CommonService commonService;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getShiftsGridData.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getMasterInfoForCompanyGrid(@RequestBody String customerIdJson) {
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		//System.out.println(customerIdJson+"::customerIdJson");
		try{
		Integer customerId = 0;
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(customerIdJson);	
		
		if(jo.get("customerId") != null && !jo.get("customerId").toString().equalsIgnoreCase("null")){
			customerId= jo.get("customerId").getAsInt();
		}
		
		List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId+"");						
		masterInfoMap.put("customerList", customerList);
		List<SimpleObject> currencyList = commonService.getCurrencyList();					
		masterInfoMap.put("currencyList", currencyList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getCountryNamesAsDropDown.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getCountryNamesAsDropDown(@RequestBody String companyIdJson) {
		System.out.println("in getCountryNamesAsDropDown Method json "+companyIdJson);
		ShiftsVo shiftsVo = new ShiftsVo();
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(companyIdJson);			
		Map<String,List> masterInfoMap = new HashMap<String,List>();  // customerId
		try{
		List<SimpleObject> countriesList = commonService.getCompanyCountries(jo.get("companyId").getAsInt());
		masterInfoMap.put("countriesList", countriesList);
		
		if(countriesList != null && countriesList.size() == 1){
			SimpleObject object  = countriesList.get(0);
			shiftsVo.setCountryId(object.getId());
			shiftsVo.setCustomerDetailsId(jo.get("customerId").getAsInt());
			shiftsVo.setCompanyDetailsId(jo.get("companyId").getAsInt());
			ShiftsVo vo = shiftsService.getShiftRecord(shiftsVo);
			List<ShiftsVo> shiftsVos = new ArrayList<ShiftsVo>();
			shiftsVos.add(vo);
			masterInfoMap.put("shiftsVo", shiftsVos);
		}	
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveShiftRecord.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> saveShiftData(@RequestBody ShiftsVo shiftsVo) {
		Integer shiftId = shiftsService.saveShiftData(shiftsVo);
		return new ResponseEntity<Map<String,List>>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getShiftRecord.json", method = RequestMethod.POST)
	public  ResponseEntity<ShiftsVo> getShiftRecord(@RequestBody String shiftsVoString) {		
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(shiftsVoString);
		ShiftsVo vo = new ShiftsVo();
		try{
		ShiftsVo shiftsVo = new ShiftsVo();
		shiftsVo.setCustomerDetailsId(jo.get("customerId").getAsInt());
		shiftsVo.setCompanyDetailsId(jo.get("companyId").getAsInt());
		shiftsVo.setCountryId(jo.get("countryId").getAsInt());		
		vo = shiftsService.getShiftRecord(shiftsVo);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<ShiftsVo>(vo,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getTransactionDatesForShiftsHistory.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List> > getTransactionDatesForShiftsHistory(@RequestBody String shiftsVoString) {
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(shiftsVoString);
		ShiftsVo shiftsVo = new ShiftsVo();
		try{
		shiftsVo.setCustomerDetailsId(jo.get("customerId").getAsInt());
		shiftsVo.setCompanyDetailsId(jo.get("companyId").getAsInt());
		shiftsVo.setCountryId(jo.get("countryId").getAsInt());
		List<SimpleObject>  list = shiftsService.getTransactionDatesForShiftsHistory(shiftsVo);
		masterInfoMap.put("transactionDatesList", list);
		ShiftsVo vo = shiftsService.getShiftRecord(shiftsVo);
		List<ShiftsVo> shiftsVos = new ArrayList<ShiftsVo>();
		shiftsVos.add(vo);
		masterInfoMap.put("shiftsVo", shiftsVos);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getShiftRecordByShiftId.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List> > getShiftRecordByShiftId(@RequestBody String shiftId) {
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(shiftId);	
		ShiftsVo  vo = (ShiftsVo) shiftsService.getShiftRecordByShiftId(jo.get("shiftId").getAsInt());
		List<ShiftsVo> shiftsVos = new ArrayList<ShiftsVo>();
		shiftsVos.add(vo);
		masterInfoMap.put("shiftsVo", shiftsVos);
		List<SimpleObject> list = vendorService.getComapanyNamesAsDropDown(vo.getCustomerDetailsId()+"",vo.getCompanyDetailsId()+"");
		masterInfoMap.put("companyList", list);
		List<SimpleObject> countriesList = commonService.getCompanyCountries(vo.getCompanyDetailsId());
		masterInfoMap.put("countriesList", countriesList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap, HttpStatus.OK);
	}
	
	
}
