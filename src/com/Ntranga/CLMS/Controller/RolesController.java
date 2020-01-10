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

import com.Ntranga.CLMS.Service.PermissionsGroupService;
import com.Ntranga.CLMS.Service.RolesService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.PermissionsGroupVo;
import com.Ntranga.CLMS.vo.RolesVo;
import com.Ntranga.CLMS.vo.SimpleObject;

@RestController
@RequestMapping(value = "rolesController")
public class RolesController {
	
	private static Logger log = Logger.getLogger(RolesController.class);
	
	@Autowired
	private PermissionsGroupService permissionsGroupService;
	
	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private VendorService vendorService;
	
	
	@RequestMapping(value = "/getMaterDataForRole.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,Object>> getMaterDataForRole(@RequestBody RolesVo  rolesVo) {
		Map<String,Object> returnList = new HashMap<String,Object>();	
		try{
			List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(rolesVo.getCustomerId()+"");
			returnList.put("customerList", customerList);
			List<SimpleObject> companyList = vendorService.getComapanyNamesAsDropDown(rolesVo.getCustomerId()+"",rolesVo.getCompanyId()+"");
			returnList.put("companyList", companyList);
			List<PermissionsGroupVo> groupNames = permissionsGroupService.getPermissionsGroupName(null);
			returnList.put("groupNames", groupNames);
			if(rolesVo != null && rolesVo.getRoleId() != null && rolesVo.getRoleId() > 0){
				RolesVo roles = rolesService.getRolesByrolesId(rolesVo.getRoleId());
				returnList.put("roles", roles);
			}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,Object>>(returnList,HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/getRoles.json", method = RequestMethod.POST)
	public  ResponseEntity<List<RolesVo>> getRoles(@RequestBody RolesVo  rolesVo) {
		List<RolesVo> returnList = new ArrayList<RolesVo>();		
		try{
			returnList = rolesService.getRoles(rolesVo);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<RolesVo>>(returnList,HttpStatus.OK);
	}
	
/*	@RequestMapping(value = "/getRolesMasterData.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String, Object>> getPermissionsGroupMasterData(@RequestBody PermissionsGroupVo groupVo) {
		Map<String, Object> returnList = new HashMap<String, Object>();	
		try{
			String[] actionList = permissionsGroupService.getScreenActionsList();
			returnList.put("actionsList", actionList);
			
			
			if(groupVo != null && groupVo.getPermissionsGroupId() != null && groupVo.getPermissionsGroupId() > 0){
				PermissionsGroupVo groupVo2 = permissionsGroupService.getPermissionGroupByPermissionGroupId(groupVo.getPermissionsGroupId());
				returnList.put("groupPermission", groupVo2);
				Map<String, List> screenList = new HashMap<String, List>();	
				screenList = permissionsGroupService.getScreenActionsMapList(groupVo.getPermissionsGroupId());
				returnList.put("screenList", screenList);
			}else{
				Map<String, List> screenList = new HashMap<String, List>();	
				screenList = permissionsGroupService.getScreenActionsMapList(null);
				returnList.put("screenList", screenList);
			}
			
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String, Object>>(returnList,HttpStatus.OK);
	}*/
	
	@RequestMapping(value = "/saveOrUpdateRoleDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<Integer> saveOrUpdateRoleDetails(@RequestBody RolesVo rolesVo) {				
		Integer id = rolesService.saveOrUpdateRoleDetails(rolesVo);
		return new ResponseEntity<Integer>(id,HttpStatus.OK);
	}
}

	