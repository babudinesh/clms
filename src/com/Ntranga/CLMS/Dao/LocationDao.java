package com.Ntranga.CLMS.Dao;

import java.util.List;

import com.Ntranga.CLMS.vo.LocationCharacteristicsVo;
import com.Ntranga.CLMS.vo.LocationVo;
import com.Ntranga.CLMS.vo.SimpleObject;

public interface LocationDao {

	public String saveLocation(LocationVo dept);	
	
	public List<LocationVo>getLocationsList();
	
	//public LocationVo  getLocationById(Integer locationId);
	
	public List<LocationVo>  getLocationById(Integer locationId);
	
	public List<LocationVo> getDepartmentList(Integer locationId);
	
	public List<LocationVo> getLocationsListBySearch(LocationVo location);
	
	public List<SimpleObject> getTransactionListForLocation(Integer customerId, Integer companyId, Integer locationId);
	
	public Integer saveLocationCharacteristics(LocationCharacteristicsVo dept);
	
	public List<LocationCharacteristicsVo> getLocationCharacteristicByLocationId(Integer locationId);
	
	public Integer validateLocationCode(LocationVo paramLocation);
}
