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

import com.Ntranga.CLMS.Service.AssociateWorkOrderService;
import com.Ntranga.CLMS.vo.AssociateWorkOrderVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
@RequestMapping(value = "associateWorkOrderController")
public class AssociateWorkOrderController {
	
	private static Logger log = Logger.getLogger(AssociateWorkOrderController.class);
	
	@Autowired
	private AssociateWorkOrderService associateWorkOrderService;


	
	@RequestMapping(value = "/getVendorAssociatedWorkOrder.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getVendorAssociatedWorkOrder(@RequestBody AssociateWorkOrderVo associateWorkOrderVo) {			
		List<SimpleObject> associateWorkOrderVos = associateWorkOrderService.getVendorAssociatedWorkOrder(associateWorkOrderVo);
		return new ResponseEntity<List<SimpleObject>>(associateWorkOrderVos,HttpStatus.OK);
	}
		
	@RequestMapping(value = "/getSearchGridData.json", method = RequestMethod.POST)
	public  ResponseEntity<List<AssociateWorkOrderVo>> getAssociateWorkOrderSearch(@RequestBody AssociateWorkOrderVo associateWorkOrderVo) {			
		List<AssociateWorkOrderVo> associateWorkOrderVos = associateWorkOrderService.getAssociateWorkOrderSearch(associateWorkOrderVo);
		return new ResponseEntity<List<AssociateWorkOrderVo>>(associateWorkOrderVos,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/workorderChangeListener.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> workorderChangeListener(@RequestBody AssociateWorkOrderVo associateWorkOrderVo) {			
		Map<String, String> map = associateWorkOrderService.workorderChangeListener(associateWorkOrderVo.getWorkrorderDetailId());
		return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveAssociateWorkOrder.json", method = RequestMethod.POST)
	public ResponseEntity<List<AssociateWorkOrderVo>> saveAssociateWorkOrder(@RequestBody AssociateWorkOrderVo associateWorkOrderVo) {			
		Integer orderId = associateWorkOrderService.saveAssociateWorkOrder(associateWorkOrderVo);
		List<AssociateWorkOrderVo> orderList = associateWorkOrderService.getWorkOrderList(associateWorkOrderVo);
		return new ResponseEntity<List<AssociateWorkOrderVo>>(orderList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAssociateMasterData.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String, List<AssociateWorkOrderVo>>> getWorkOrderList(@RequestBody AssociateWorkOrderVo associateWorkOrderVo) {	
		Map<String, List<AssociateWorkOrderVo>> map = new HashMap<String, List<AssociateWorkOrderVo>>();
		try{
		List<AssociateWorkOrderVo> orderList = associateWorkOrderService.getWorkOrderList(associateWorkOrderVo);
		map.put("workOrderList", orderList);
		AssociateWorkOrderVo associateWorkOrderVos = associateWorkOrderService.getWorkOrderAssociatedList(associateWorkOrderVo);
		map.put("associateWorkOrder", associateWorkOrderVos.getAssociateWorkOrderVo());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String, List<AssociateWorkOrderVo>>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAssociateWorkOrderList.json", method = RequestMethod.POST)
	public ResponseEntity<List<AssociateWorkOrderVo>> getAssociateWorkOrderList(@RequestBody String jSonString) {
		List<AssociateWorkOrderVo> orderList =  new ArrayList();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject)jsonParser.parse(jSonString);
		AssociateWorkOrderVo associateWorkOrderVo = new AssociateWorkOrderVo();
		if(!(jo.get("customerId").toString().equalsIgnoreCase("null")) && !(jo.get("customerId").getAsString().equalsIgnoreCase("null")) && !(jo.get("customerId").toString().trim().equalsIgnoreCase("select"))){
			associateWorkOrderVo.setCustomerDetailsId(jo.get("customerId").getAsInt());
		}
		if(!(jo.get("companyId").toString().equalsIgnoreCase("null")) && !(jo.get("companyId").getAsString().equalsIgnoreCase("null")) && !(jo.get("companyId").toString().trim().equalsIgnoreCase("select"))){
			associateWorkOrderVo.setCompanyDetailsId(jo.get("companyId").getAsInt());
		}
		if(!(jo.get("vendorId").toString().equalsIgnoreCase("null")) && !(jo.get("vendorId").getAsString().equalsIgnoreCase("null")) && !(jo.get("vendorId").toString().trim().equalsIgnoreCase("select"))){
			associateWorkOrderVo.setVendorDetailsId(jo.get("vendorId").getAsInt());
		}
		 orderList = associateWorkOrderService.getWorkOrderList(associateWorkOrderVo);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<AssociateWorkOrderVo>>(orderList, HttpStatus.OK);
	}
	
}
