package com.Ntranga.CLMS.Controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Ntranga.CLMS.Service.WageCalculatorService;
import com.Ntranga.CLMS.vo.WageCalculatorSaveVo;
import com.Ntranga.CLMS.vo.WageCalculatorVo;

@RestController
@RequestMapping(value = "wageCalculatorController")
public class WageCalculatorController {
	
	private static Logger log = Logger.getLogger(WageCalculatorController.class);
	
	@Autowired
	private WageCalculatorService wageCalculatorService;
	
	
	
	@RequestMapping(value = "/getWageCalculatorList.json", method = RequestMethod.POST)
	public  ResponseEntity<List<WageCalculatorVo>> getWageCalculatorList(@RequestBody String jsonSearchContent) {
		List<WageCalculatorVo> wageCalculatorList =  new ArrayList();
		try{
		wageCalculatorList = wageCalculatorService.getWageCalculatorList(jsonSearchContent);	
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<WageCalculatorVo>>(wageCalculatorList,HttpStatus.OK);
	}
		
	@RequestMapping(value = "/saveWageCalculator.json", method = RequestMethod.POST)
	public  ResponseEntity<Integer> saveWageCalculator(@RequestBody WageCalculatorSaveVo wageCalculatorSaveVo) {
		Integer wageCalculatorId = 0;
		try{
		 wageCalculatorId = wageCalculatorService.saveWageCalculator(wageCalculatorSaveVo);		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Integer>(wageCalculatorId,HttpStatus.OK);
	}
}
