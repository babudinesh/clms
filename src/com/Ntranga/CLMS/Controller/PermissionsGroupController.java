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
import com.Ntranga.CLMS.vo.PermissionsGroupVo;

@RestController
@RequestMapping(value = "permissionsGroupController")
public class PermissionsGroupController {
	
	private static Logger log = Logger.getLogger(PermissionsGroupController.class);
	
	@Autowired
	private PermissionsGroupService permissionsGroupService;
	
	@RequestMapping(value = "/getPermissionsGroupName.json", method = RequestMethod.POST)
	public  ResponseEntity<List<PermissionsGroupVo>> getPermissionsGroupName(@RequestBody PermissionsGroupVo  groupVo) {
		List<PermissionsGroupVo> returnList = new ArrayList<PermissionsGroupVo>();		
		try{
			returnList = permissionsGroupService.getPermissionsGroupName(groupVo);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<PermissionsGroupVo>>(returnList,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getPermissionsGroupMasterData.json", method = RequestMethod.POST)
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
	}
	
	@RequestMapping(value = "/saveOrUpdatePermissionsGroupDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<Integer> saveOrUpdatePermissionsGroupDetails(@RequestBody PermissionsGroupVo groupVo) {				
		Integer id = permissionsGroupService.saveOrUpdatePermissionsGroupDetails(groupVo);
		return new ResponseEntity<Integer>(id,HttpStatus.OK);
	}
}

	