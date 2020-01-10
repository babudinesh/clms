package com.Ntranga.CLMS.Controller;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.Ntranga.CLMS.Service.JobAllocationByVendorService;
import com.Ntranga.CLMS.Service.RolesService;
import com.Ntranga.CLMS.Service.UsersService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.EmployeeInformationVo;
import com.Ntranga.CLMS.vo.RolesVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.UsersVo;

@RestController
@RequestMapping(value = "usersController")
public class UsersController {
	
	private static Logger log = Logger.getLogger(UsersController.class);
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private JobAllocationByVendorService jobAllocationByVendorService;

	
	@RequestMapping(value = "/getMaterDataForUser.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,Object>> getMaterDataForRole(@RequestBody UsersVo usersVo ) {
		Map<String,Object> returnList = new HashMap<String,Object>();	
		try{
			List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(usersVo.getCustomerId()+"");
			returnList.put("customerList", customerList);
			List<SimpleObject> companyList = vendorService.getComapanyNamesAsDropDown(usersVo.getCustomerId()+"",usersVo.getCompanyId()+"");
			returnList.put("companyList", companyList);
			
			List<SimpleObject> locationsList = new ArrayList();
			locationsList = jobAllocationByVendorService.getlocationsList(usersVo.getCustomerId()+"",usersVo.getCompanyId()+"",Arrays.toString(usersVo.getLocationArrayId()).replace("[", "").replace("]", ""));
			returnList.put("locationList", locationsList);
			RolesVo r = new RolesVo();
			r.setCustomerId(usersVo.getCustomerId());
			r.setCompanyId(usersVo.getCompanyId());			
			List<RolesVo> roles =  rolesService.getRoles(r);
			returnList.put("roles", roles);
			if(usersVo != null && usersVo.getUserId()!= null && usersVo.getUserId() > 0){
				UsersVo users = usersService.getUsersByUserId(usersVo.getUserId());
				returnList.put("users", users);
			}else{
				List<UsersVo> usersList = usersService.getUsers(usersVo);
				returnList.put("usersList",usersList);
			}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,Object>>(returnList,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getUsers.json", method = RequestMethod.POST)
	public  ResponseEntity<List<UsersVo>> getUsers(@RequestBody UsersVo  usersVo) {
		List<UsersVo> returnList = new ArrayList<UsersVo>();		
		try{
			returnList = usersService.getUsers(usersVo);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<UsersVo>>(returnList,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveOrUpdateUserDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<Integer> saveOrUpdateUserDetails(@RequestBody UsersVo usersVo) {				
		Integer id = usersService.saveOrUpdateUserDetails(usersVo);
		return new ResponseEntity<Integer>(id,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getEmployeeDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<List<EmployeeInformationVo>> getEmployeeDetails(@RequestBody EmployeeInformationVo  employeeInformationVo) {
		List<EmployeeInformationVo> returnList = new ArrayList<EmployeeInformationVo>();		
		try{
			returnList = usersService.getEmployeeDetails(employeeInformationVo);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<EmployeeInformationVo>>(returnList,HttpStatus.OK);
	}
	
}

	