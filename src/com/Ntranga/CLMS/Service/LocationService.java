package com.Ntranga.CLMS.Service;

import java.util.List;

import com.Ntranga.CLMS.vo.LocationCharacteristicsVo;
import com.Ntranga.CLMS.vo.LocationVo;
import com.Ntranga.CLMS.vo.SimpleObject;


public interface LocationService {

	public String saveLocation(LocationVo paramLocation);	
	
	public List<LocationVo> getLocationsList();

	public List<LocationVo>  getLocationById(Integer locationId);
	
	public List<LocationVo> getLocationsListBySearch(LocationVo paramLocation);
	
	public List<SimpleObject> getTransactionListForLocation(Integer customerId, Integer companyId, Integer locationId);
	
	public Integer saveLocationCharacteristics(LocationCharacteristicsVo paramLocation);
	
	public List<LocationCharacteristicsVo>  getLocationCharacteristicsByLocationId(Integer locationId);

	public List<LocationVo> getDepartmentList(Integer locationId);
	
	public Integer validateLocationCode(LocationVo paramLocation);
}
