package com.Ntranga.CLMS.Dao;

import java.math.BigInteger;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.LocationCharacteristicsVo;
import com.Ntranga.CLMS.vo.LocationVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.DepartmentDetails;
import com.Ntranga.core.CLMS.entities.LocationCharacteristics;
import com.Ntranga.core.CLMS.entities.LocationDepartment;
import com.Ntranga.core.CLMS.entities.LocationDetails;
import com.Ntranga.core.CLMS.entities.LocationDetailsInfo;
import com.Ntranga.core.CLMS.entities.MCountry;
import com.Ntranga.core.CLMS.entities.MLocation;
import com.Ntranga.core.CLMS.entities.MState;
import common.Logger;

@SuppressWarnings("rawtypes")
@Transactional
@Repository("locationDao")
public class LocationDaoImpl implements LocationDao {

private static Logger log = Logger.getLogger(LocationDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/*
	 * This method will be used to save/update location details, location_details_info, location_department
	 */
	@Override
	public String saveLocation(LocationVo paramLocation) {
		log.info("Entered in saveLocation()  ::   "+paramLocation);

		Session session = sessionFactory.getCurrentSession();
		Integer locationDetailsId = 0, locationId = 0;
		BigInteger locationSequenceId = new BigInteger("0");	
		
		LocationDetails locationDetails = new LocationDetails();
		LocationDetailsInfo locationDetailsInfo = new LocationDetailsInfo();
		LocationDepartment locDept = new LocationDepartment();
		
		Time businessStart =  (paramLocation.getBusinessStartTime() != null  && !paramLocation.getBusinessStartTime().isEmpty()) ? DateHelper.getConvertedTime(paramLocation.getBusinessStartTime()+":00") : null;
		Time businessEnd   =  (paramLocation.getBusinessEndTime()  != null   && !paramLocation.getBusinessEndTime().isEmpty())   ? DateHelper.getConvertedTime(paramLocation.getBusinessEndTime()+":00") : null;
		
		try{									
			if(paramLocation != null && paramLocation.getLocationId() > 0){
				locationDetails = (LocationDetails) session.load(LocationDetails.class, paramLocation.getLocationId());
				locationDetails.setLocationCode(paramLocation.getLocationCode().toUpperCase());
				locationDetails.setModifiedBy(paramLocation.getModifiedBy());
				locationDetails.setModifiedDate(new Date());
				session.update(locationDetails);
				locationId  = paramLocation.getLocationId();
			}else{
				locationDetails = new LocationDetails();
				locationDetails.setCustomerDetails(new CustomerDetails(paramLocation.getCustomerId()));
				locationDetails.setCompanyDetails(new CompanyDetails(paramLocation.getCompanyId()));
				locationDetails.setLocationCode(paramLocation.getLocationCode().toUpperCase());
				locationDetails.setCreatedBy(139);
				locationDetails.setModifiedBy(paramLocation.getModifiedBy());
				locationDetails.setCreatedDate(new Date());
				locationDetails.setModifiedDate(new Date());
				locationId = (Integer) session.save(locationDetails);
			}
			session.clear();
			if(paramLocation != null &&  paramLocation.getLocationDetailsId() > 0){
				locationDetailsInfo = (LocationDetailsInfo) sessionFactory.getCurrentSession().load(LocationDetailsInfo.class, paramLocation.getLocationDetailsId());
				locationDetailsInfo.setLocationDetails(new LocationDetails(paramLocation.getLocationId()));
				locationDetailsInfo.setCustomerDetails(new CustomerDetails(paramLocation.getCustomerId()));
				locationDetailsInfo.setCompanyDetails(new CompanyDetails(paramLocation.getCompanyId()));
				locationDetailsInfo.setLocationName(paramLocation.getLocationName());
				locationDetailsInfo.setAddress1(paramLocation.getAddress1());
				locationDetailsInfo.setAddress2(paramLocation.getAddress2());
				locationDetailsInfo.setAddress3(paramLocation.getAddress3());
				locationDetailsInfo.setmCity(paramLocation.getCityId());
				locationDetailsInfo.setmState(new MState((paramLocation.getStateId()!= null && paramLocation.getStateId() > 0) ? paramLocation.getStateId() : null));
				locationDetailsInfo.setmCountry(new MCountry(paramLocation.getCountryId()));
				locationDetailsInfo.setPincode((paramLocation.getPincode() != null && paramLocation.getPincode() > 0) ? paramLocation.getPincode() : null);
				locationDetailsInfo.setMLocationType(new MLocation(paramLocation.getLocationTypeId()));
				locationDetailsInfo.setContactName(paramLocation.getContactName());
				locationDetailsInfo.setPhoneNUmber(paramLocation.getPhoneNumber());
				locationDetailsInfo.setEmailId(paramLocation.getEmailId());
				locationDetailsInfo.setFaxNumber(paramLocation.getFaxNumber());
				locationDetailsInfo.setBusinessHoursPerDay(paramLocation.getBusinessHoursPerDay());
				locationDetailsInfo.setBusinessStartTime(businessStart);
				locationDetailsInfo.setBusinessEndTime(businessEnd);
				locationDetailsInfo.setWorkWeekEndDay(paramLocation.getWorkWeekEndDay());
				locationDetailsInfo.setWorkWeekStartDay(paramLocation.getWorkWeekStartDay());
				locationDetailsInfo.setHolidayaCalendarId(paramLocation.getHolidayCalendarId());
				locationDetailsInfo.setShiftId(paramLocation.getShiftId());
				locationDetailsInfo.setStandardHoursPerWeek(paramLocation.getStandardHoursPerWeek());
				locationDetailsInfo.setIsHeadQuarter(paramLocation.getIsHeadquarter() == true ? "Y":"N");
				locationDetailsInfo.setIsShiftSystem(paramLocation.getIsShiftSystem() == true ? "Y":"N");
				locationDetailsInfo.setNumberOfWorkingDays(paramLocation.getNumberOfWorkingDays());
				locationDetailsInfo.setRiskIndicator(paramLocation.getRiskIndicator());
				locationDetailsInfo.setHazardousIndicator(paramLocation.getHazardousIndicator());
				locationDetailsInfo.setRiskDetails(paramLocation.getRiskDetails());
				locationDetailsInfo.setStatus(paramLocation.getStatus());
				locationDetailsInfo.setTransactionDate(paramLocation.getTransactionDate());
				locationDetailsInfo.setModifiedBy(paramLocation.getModifiedBy());
				locationDetailsInfo.setModifiedDate(new Date());
				session.update(locationDetailsInfo);
				locationDetailsId = locationDetailsInfo.getLocationDetailsId();
			}else{					
				locationDetailsInfo.setLocationDetails(locationDetails);
				locationDetailsInfo.setCustomerDetails(new CustomerDetails(paramLocation.getCustomerId()));
				locationDetailsInfo.setCompanyDetails(new CompanyDetails(paramLocation.getCompanyId()));
				locationDetailsInfo.setLocationName(paramLocation.getLocationName());
				locationDetailsInfo.setAddress1(paramLocation.getAddress1());
				locationDetailsInfo.setAddress2(paramLocation.getAddress2());
				locationDetailsInfo.setAddress3(paramLocation.getAddress3());
				locationDetailsInfo.setmCity(paramLocation.getCityId());
				locationDetailsInfo.setmState(new MState((paramLocation.getStateId()!= null && paramLocation.getStateId() > 0) ? paramLocation.getStateId() : null));
				locationDetailsInfo.setmCountry(new MCountry(paramLocation.getCountryId()));
				locationDetailsInfo.setPincode((paramLocation.getPincode() != null && paramLocation.getPincode() > 0) ? paramLocation.getPincode() : null);
				locationDetailsInfo.setMLocationType(new MLocation(paramLocation.getLocationTypeId()));
				locationDetailsInfo.setContactName(paramLocation.getContactName());
				locationDetailsInfo.setPhoneNUmber(paramLocation.getPhoneNumber());
				locationDetailsInfo.setEmailId(paramLocation.getEmailId());
				locationDetailsInfo.setFaxNumber(paramLocation.getFaxNumber());
				locationDetailsInfo.setBusinessHoursPerDay(paramLocation.getBusinessHoursPerDay());
				locationDetailsInfo.setBusinessStartTime(businessStart);
				locationDetailsInfo.setBusinessEndTime(businessEnd);
				locationDetailsInfo.setWorkWeekEndDay(paramLocation.getWorkWeekEndDay());
				locationDetailsInfo.setWorkWeekStartDay(paramLocation.getWorkWeekStartDay());
				locationDetailsInfo.setHolidayaCalendarId(paramLocation.getHolidayCalendarId());
				locationDetailsInfo.setShiftId(paramLocation.getShiftId());
				locationDetailsInfo.setStandardHoursPerWeek(paramLocation.getStandardHoursPerWeek());
				locationDetailsInfo.setIsHeadQuarter(paramLocation.getIsHeadquarter() == true ? "Y":"N");
				locationDetailsInfo.setIsShiftSystem(paramLocation.getIsShiftSystem() == true ? "Y":"N");
				locationDetailsInfo.setNumberOfWorkingDays(paramLocation.getNumberOfWorkingDays());
				locationDetailsInfo.setRiskIndicator(paramLocation.getRiskIndicator());
				locationDetailsInfo.setHazardousIndicator(paramLocation.getHazardousIndicator());
				locationDetailsInfo.setRiskDetails(paramLocation.getRiskDetails());
				locationDetailsInfo.setStatus(paramLocation.getStatus());
				locationDetailsInfo.setTransactionDate(paramLocation.getTransactionDate());
				locationDetailsInfo.setCreatedBy(paramLocation.getCreatedBy());
				locationDetailsInfo.setModifiedBy(paramLocation.getModifiedBy());
				locationDetailsInfo.setCreatedDate(new Date());
				locationDetailsInfo.setModifiedDate(new Date());
				if(locationId != null && locationId > 0){//and Customer_Id='"+paramLocation.getCustomerId()+"' And Company_Id = '"+paramLocation.getCompanyId()+"'"
					locationSequenceId  = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Location_Sequence_Id),0) FROM location_details_info location WHERE  location.Location_Id = "+paramLocation.getLocationId() +" AND location.`Transaction_Date` = '"+DateHelper.convertDateToSQLString(paramLocation.getTransactionDate())+"' ").list().get(0);
				}	
				locationDetailsInfo.setLocationSequenceId(locationSequenceId.intValue()+1);			
				locationDetailsId = (Integer) session.save(locationDetailsInfo);
			}
			
			session.clear();
			/*if(locationId > 0 && paramLocation.getDepartmentList() != null ){
				Query q = session.createQuery("DELETE FROM LocationDepartment WHERE Location_Id= "+locationId);
				q.executeUpdate();
			}
			
			if(paramLocation.getDepartmentList() != null){
				for(LocationVo locationVo : paramLocation.getDepartmentList()){
					locDept = new LocationDepartment();
					
					locDept.setLocation(new LocationDetails(locationId));
					locDept.setDepartment(new DepartmentDetails(locationVo.getDepartmentId()));
					locDept.setCustomerDetails(new CustomerDetails(paramLocation.getCustomerId()));
					locDept.setCompanyDetails(new CompanyDetails(paramLocation.getCompanyId()));
					locDept.setCreatedBy(paramLocation.getCreatedBy());
					locDept.setModifiedBy(paramLocation.getModifiedBy());
					locDept.setCreatedDate(new Date());
					locDept.setModifiedDate(new Date());
					session.save(locDept);
				}
			}*/
			
			session.flush();
		}catch (Exception e) {
			 if(!(session.getTransaction().wasRolledBack())){
				 session.getTransaction().rollback();
			 }
			 log.error("Error Occurred...   ", e);
			 log.info("Exiting from saveLocation()  ::  locationId =  "+locationDetailsId);
		}				
		log.info("Exiting from saveLocation()  ::  locationId =  "+locationDetailsId);
		return locationDetailsId+"-"+locationId;
	}

	/*
	 * This method will be used to location list for all customers and companies
	 */
	@Override
	public List<LocationVo> getLocationsList() {
		log.info("Entered in  getLocationsList()");
		Session session = sessionFactory.getCurrentSession();
		List locationList = null;
		List<LocationVo> returnList = new ArrayList<LocationVo>();
		LocationVo location = new LocationVo();
		
		String hqlQuery = "SELECT locDetails.Customer_Id,  locDetails.Company_Id, location.Location_Details_Id, "
							+ " location.Location_Type_Id, location.Location_Name, location.Contact_Name, location.Email_Id,"
							+ " location.Phone_Number, location.Fax_Number, location.Is_Headquarter, location.Shift_Id, location.Holiday_Calendar_Id, "
							+ " location.Transaction_Date, location.Status,locDetails.Location_Code,  location.Location_Id,"
							+ " location.Address_1, location.Address_2, location.Address_3, location.City, location.State_Id, location.Country_Id, location.Pincode, "
							+ " location.Is_Shift_System, location.Risk_Indicator, location.Hazardous_Indicator, location.Risk_Details,"
							+ " location.Business_Hours_Per_Day, location.Standard_Hours_Per_Week,"
							+ " location.Work_Week_Start_Day, location.Work_Week_End_Day, location.Number_Of_Working_Days, "
							+ " location.Business_Start_Time, location.Business_End_Time,"
							+ " custDetails.Customer_Name, cmpDetails.Company_Name"
							+ " FROM location_details_info AS location "
							+ " LEFT JOIN location_details locDetails ON location.Location_Id = locDetails.Location_Id"
							+ " LEFT JOIN m_location mlocation ON location.Location_Type_Id = mlocation.Location_Type_Id "
							+ " LEFT JOIN company_details AS company  ON locDetails.Customer_Id = company.Customer_Id  AND locDetails.Company_Id = company.Company_Id " 								
							+ " LEFT JOIN customer_details AS customer  ON locDetails.Customer_Id = customer.Customer_Id "
							+ " LEFT JOIN customer_details_info As custDetails ON locDetails.Customer_Id = custDetails.Customer_Id"
							+ " LEFT JOIN company_details_info As cmpDetails ON locDetails.Customer_Id = cmpDetails.Customer_Id AND locDetails.Company_Id = cmpDetails.Company_Id"
								+ " WHERE location.`Transaction_Date` <= CURRENT_DATE()  "
									+ " AND CONCAT(DATE_FORMAT(ldi.transaction_date, '%Y%m%d'), LPAD(ldi.location_Sequence_Id, 2, '0')) =  "
											+"	 ( "
											+"	 SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.location_Sequence_Id, 2, '0'))) "
											+" FROM location_details_info vdi1 "
											+"	 WHERE  ldi.`location_id` = vdi1.`location_id`     "
											+"  AND vdi1.transaction_date <= CURRENT_DATE()   "
											+" )   "
							+ " GROUP BY location.Location_Id Order By location.Location_Name asc";
		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			locationList = query.list();

			for (Object customer : locationList) {
				Object[] obj = (Object[]) customer;
				location = new LocationVo();
				
				location.setLocationDetailsId((Integer)obj[2]);
				//location.setLocationTypeId((Integer)obj[3]);
				location.setLocationName((String)obj[4]);
				//location.setContactName((String)obj[5]);
				//location.setEmailId((String)obj[6]);
				//location.setPhoneNumber((String)obj[7]);
				//location.setFaxNumber((String)obj[8]);
				//location.setIsHeadquarter(obj[9]+"");
				//location.setShiftId((String)obj[10]);	
				//location.setHolidayCalendarId((String)obj[11]);
				location.setTransactionDate((Date)obj[12]);
				location.setStatus(obj[13]+"");
				location.setLocationCode((String)obj[14]);
				location.setLocationId((Integer)obj[15]);
				/*location.setAddress1((String)obj[16]);
				location.setAddress2((String)obj[17]);
				location.setAddress3((String)obj[18]);
				location.setCityId((Integer)obj[19]);
				location.setStateId((Integer)obj[20]);
				location.setCountryId((Integer)obj[21]);
				location.setPincode((Integer)obj[22]);
				location.setIsShiftSystem(obj[23]+"");
				location.setRiskIndicator((String)obj[24]);
				location.setHazardousIndicator((String)obj[25]);
				location.setRiskDetails((String)obj[26]);
				location.setBusinessHoursperDay(obj[27]+"");
				location.setStandardHoursPerWeek((String)obj[28]);
				location.setWorkWeekStartDay((String)obj[29]);
				location.setWorkWeekEndDay((String)obj[30]);
				location.setNumberOfWorkingDays((Integer)obj[31]);
				location.setBusinessStartTime(obj[32]+"");
				location.setBusinessEndTime(obj[33]+"");*/
				location.setCustomerName((String)obj[34]);
				location.setCompanyName((String)obj[35]);
				
				returnList.add(location);
			}	
			
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from  getLocationsList()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from  getLocationsList()  ::   "+returnList);
		return returnList;
	}
	
	/*
	 *This method will be used to fetch all location records based on given  customer name, company name, location code,
	 *																			 location name and status or combination of any
	 */
	
	@Override
	public List<LocationVo> getLocationsListBySearch(LocationVo paramLocation) {
		log.info("Entered in  getLocationsListBySearch()  ::   paramLocation = "+paramLocation );
		Session session = sessionFactory.getCurrentSession();
		List locationList = null;
		List<LocationVo> returnList = new ArrayList<LocationVo>();
		LocationVo location = new LocationVo();
		
		
		String hqlQuery = "SELECT ldi.Customer_Id,  ldi.Company_Id, ldi.Location_Details_Id, "
							 + " ldi.Location_Type_Id, ldi.Location_Name, ldi.Contact_Name, ldi.Email_Id, "
							 + " ldi.Phone_Number, ldi.Fax_Number, ldi.Is_Headquarter, ldi.Shift_Id, ldi.Holiday_Calendar_Id, "
							 + " ldi.Transaction_Date, ldi.Status,ld.Location_Code,  ldi.Location_Id, "
							 + " ldi.Address_1, ldi.Address_2, ldi.Address_3, ldi.City, ldi.State_Id, ldi.Country_Id, ldi.Pincode, "
							 + " ldi.Is_Shift_System, ldi.Risk_Indicator, ldi.Hazardous_Indicator, ldi.Risk_Details, "
							 + " ldi.Business_Hours_Per_Day, ldi.Standard_Hours_Per_Week, "
							 + " ldi.Work_Week_Start_Day, ldi.Work_Week_End_Day, ldi.Number_Of_Working_Days, "
							 + " ldi.Business_Start_Time, ldi.Business_End_Time, com.Company_Name, cdi.Customer_Name "
							 + " from location_details  ld "
							 + " INNER join location_details_info ldi on(ldi.location_id = ld.location_id) "
							 + " INNER JOIN `customer_details_info` cdi ON(ldi.customer_id =cdi.customer_id) "
							 + " INNER JOIN `company_details_info` com ON (com.company_id = ldi.company_id) "
							 + " INNER JOIN m_location mlocation ON mlocation.Location_Type_Id = ldi.Location_Type_Id "
							 +" WHERE "
							 + " CONCAT(DATE_FORMAT(ldi.transaction_date, '%Y%m%d'), LPAD(ldi.location_Sequence_Id, 2, '0')) =  "
							 +"	 ( "
							 +"	 SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.location_Sequence_Id, 2, '0'))) "
							 +" FROM location_details_info vdi1 "
							 +"	 WHERE  ldi.`location_id` = vdi1.`location_id`     "
							  +"  AND vdi1.transaction_date <= CURRENT_DATE()   "
								+" )   "
								+" and  CONCAT(DATE_FORMAT(cdi.transaction_date, '%Y%m%d'), LPAD(cdi.customer_Sequence_Id, 2, '0')) = "
								+"  ( "
								+"  SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.customer_Sequence_Id, 2, '0'))) "
								+"  FROM customer_details_info vdi1  "
								+"  WHERE  cdi.`customer_id` = vdi1.`customer_id`     "
								+"   AND vdi1.transaction_date <= CURRENT_DATE()   "
								+"  ) "
                                 
								+" and "
								
								+" CONCAT(DATE_FORMAT(com.transaction_date, '%Y%m%d'), LPAD(com.company_Sequence_Id, 2, '0')) = "
																+"  ( "
								+"  SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.company_Sequence_Id, 2, '0'))) "
								+"  FROM company_details_info vdi1 "
								+"  WHERE  com.`company_id` = vdi1.`company_id`     "
							+" 	  AND vdi1.transaction_date <= CURRENT_DATE()   "
										  +"  ) "
                                 
								
								+ " AND cdi.Is_Active = 'Y'"
								+ " AND com.Is_Active = 'Y' ";
		//search based on customer name				
		if((paramLocation.getCustomerName() != null && !paramLocation.getCustomerName().isEmpty()) || (paramLocation.getCustomerId() > 0 )){
			hqlQuery += "  AND  ldi.Customer_Id = "+paramLocation.getCustomerId();
		}
		// search based on company name
		if((paramLocation.getCompanyName() != null && !paramLocation.getCompanyName().isEmpty())|| paramLocation.getCompanyId() >0 ){
			hqlQuery += "  AND  ldi.Company_Id = "+paramLocation.getCompanyId();
		}
		//search based on location code
		if(paramLocation.getLocationCode() != null && !paramLocation.getLocationCode().isEmpty()){
			hqlQuery += " AND ld.Location_Code LIKE '"+paramLocation.getLocationCode()+"%' ";
		}
		// search based on location name
		if(paramLocation.getLocationName() != null && !paramLocation.getLocationName().isEmpty()){
			hqlQuery += " AND ldi.Location_Name LIKE '"+paramLocation.getLocationName()+"%' ";
		}
		// search based on status
		if(paramLocation.getStatus() != null && !paramLocation.getStatus().isEmpty()){
			hqlQuery += " AND ldi.Status = '"+paramLocation.getStatus()+"'";
		}
		// search based on country id
		if(paramLocation.getCountryId() != null && paramLocation.getCountryId() > 0 ){
			hqlQuery += " AND ldi.Country_Id ="+paramLocation.getCountryId();
		}
		
		hqlQuery += " GROUP BY ldi.Location_Id Order By ldi.Location_Name asc";
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			locationList = query.list();
			if(locationList.size() > 0){
				for (Object customer : locationList) {
					Object[] obj = (Object[]) customer;
					location = new LocationVo();
					
					location.setCustomerId((Integer)obj[0]);
					location.setCompanyId((Integer)obj[1]);
					location.setLocationDetailsId((Integer)obj[2]);
					//location.setLocationTypeId((Integer)obj[3]);
					location.setLocationName((String)obj[4]);
					//location.setContactName((String)obj[5]);
					//location.setEmailId((String)obj[6]);
					//location.setPhoneNumber((String)obj[7]);
					//location.setFaxNumber((String)obj[8]);
					//location.setIsHeadquarter(obj[9]+"");
					//location.setShiftId((String)obj[10]);	
					//location.setHolidayCalendarId((String)obj[11]);
					location.setTransDate(DateHelper.convertSQLDateToString((java.sql.Date)obj[12], "dd/MM/yyyy"));
					location.setStatus((obj[13]+"").equalsIgnoreCase("Y") ? "Active" : "Inactive");
					location.setLocationCode((String)obj[14]);
					location.setLocationId((Integer)obj[15]);
					location.setCountryId((Integer)obj[21]);
					/*location.setAddress1((String)obj[16]);
					location.setAddress2((String)obj[17]);
					location.setAddress3((String)obj[18]);
					location.setCityId((Integer)obj[19]);
					location.setStateId((Integer)obj[20]);
					location.setCountryId((Integer)obj[21]);
					location.setPincode((Integer)obj[22]);
					location.setIsShiftSystem(obj[23]+"");
					location.setRiskIndicator((String)obj[24]);
					location.setHazardousIndicator((String)obj[25]);
					location.setRiskDetails((String)obj[26]);
					location.setBusinessHoursperDay(obj[27]+"");
					location.setStandardHoursPerWeek((String)obj[28]);
					location.setWorkWeekStartDay((String)obj[29]);
					location.setWorkWeekEndDay((String)obj[30]);
					location.setNumberOfWorkingDays((Integer)obj[31]);
					location.setBusinessStartTime(obj[32]+"");
					location.setBusinessEndTime(obj[33]+"");*/
					location.setCustomerName((String)obj[35]);
					location.setCompanyName((String)obj[34]);
					
					returnList.add(location);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from  getLocationsListBySearch()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from  getLocationsListBySearch()  ::   "+returnList);
		return returnList;
	}

	/*
	 * This method will be used to get the location details by given location Id
	 */
	@Override
	public List<LocationVo> getLocationById(Integer locationDetailsId) {
		log.info("Entered in  getLocationById()  ::   LocationId = "+locationDetailsId);
		Session session = sessionFactory.getCurrentSession();
		LocationVo location = new LocationVo();
		List<LocationVo> returnList = new ArrayList<LocationVo>();
		
		
		String hqlQuery = "SELECT locDetails.Customer_Id,  locDetails.Company_Id, location.Location_Details_Id, "
							+ " location.Location_Type_Id, location.Location_Name, location.Contact_Name, location.Email_Id,"
							+ " location.Phone_Number, location.Fax_Number, location.Is_Headquarter, location.Shift_Id, location.Holiday_Calendar_Id, "
							+ " location.Transaction_Date, location.Status,locDetails.Location_Code,  location.Location_Id,"
							+ " location.Address_1, location.Address_2, location.Address_3, location.City, location.State_Id, location.Country_Id, location.Pincode, "
							+ " location.Is_Shift_System, location.Risk_Indicator, location.Hazardous_Indicator, location.Risk_Details,"
							+ " location.Business_Hours_Per_Day, location.Standard_Hours_Per_Week,"
							+ " (CASE WHEN location.Work_Week_Start_Day = 'MONDAY' THEN '1' ELSE "
								+ "	(CASE WHEN location.Work_Week_Start_Day = 'TUESDAY' THEN '2' ELSE "
									+ " (CASE WHEN location.Work_Week_Start_Day = 'WEDNESDAY' THEN '3' ELSE  "
										+ " (CASE WHEN location.Work_Week_Start_Day = 'THURSDAY' THEN '4' ELSE "
											+ " (CASE WHEN location.Work_Week_Start_Day = 'FRIDAY' THEN '5' ELSE  "
												+ " (CASE WHEN location.Work_Week_Start_Day = 'SATURDAY' THEN '6' ELSE  "
													+ " (CASE WHEN location.Work_Week_Start_Day = 'SUNDAY' THEN '7' ELSE	"
													+ "'0' END)"
												+ "END) "
											+ "	END) "
										+ "END) "
									+ " END) "
								+ " END) "
							+ " END)Work_Week_Start_Day,"
							+ " (CASE WHEN location.Work_Week_End_Day = 'MONDAY' THEN '1' ELSE "
								+ "	(CASE WHEN location.Work_Week_End_Day = 'TUESDAY' THEN '2' ELSE "
									+ " (CASE WHEN location.Work_Week_End_Day = 'WEDNESDAY' THEN '3' ELSE  "
										+ " (CASE WHEN location.Work_Week_End_Day = 'THURSDAY' THEN '4' ELSE "
											+ " (CASE WHEN location.Work_Week_End_Day = 'FRIDAY' THEN '5' ELSE  "
													+ " (CASE WHEN location.Work_Week_End_Day = 'SATURDAY' THEN '6' ELSE  "
														+ " (CASE WHEN location.Work_Week_End_Day = 'SUNDAY' THEN '7' ELSE	"
														+ "'0' END)"
													+ "END) "
												+ "	END) "
											+ "END) "
										+ " END) "
									+ " END) "
							+ " END) Work_Week_End_Day, "
							+ " location.Number_Of_Working_Days, "
							+ " location.Business_Start_Time, location.Business_End_Time"
							+ " FROM location_details_info AS location "
							+ " LEFT JOIN location_details AS locDetails ON location.Location_Id = locDetails.Location_Id"
							+ " LEFT JOIN m_location AS mlocation ON location.Location_Type_Id = mlocation.Location_Type_Id "
							+ " LEFT JOIN company_details AS company  ON locDetails.Customer_Id = company.Customer_Id  AND locDetails.Company_Id = company.Company_Id " 								
							+ " LEFT JOIN customer_details AS customer  ON locDetails.Customer_Id = customer.Customer_Id "
							+ " LEFT JOIN customer_details_info As custDetails ON locDetails.Customer_Id = custDetails.Customer_Id"
							+ " LEFT JOIN company_details_info As cmpDetails ON locDetails.Customer_Id = cmpDetails.Customer_Id AND locDetails.Company_Id = cmpDetails.Company_Id"
							+ " LEFT JOIN location_department AS dept ON locDetails.Location_Id = dept.Location_Id"
								+ " WHERE location.Location_Details_Id = "+locationDetailsId
								+ " AND custDetails.Is_Active = 'Y'"
								+ " AND cmpDetails.Is_Active = 'Y' "
								+" GROUP BY location.Location_Id Order By location.Location_Id asc";

		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			List querylist = query.list();

			for (Object dept : querylist) {
				Object[] obj = (Object[]) dept;
				location = new LocationVo();
				location.setCustomerId((Integer)obj[0]);
				location.setCompanyId((Integer)obj[1]);
				location.setLocationDetailsId((Integer)obj[2]);
				location.setLocationTypeId((Integer)obj[3]);
				location.setLocationName((String)obj[4]);
				location.setContactName((String)obj[5]);
				location.setEmailId((String)obj[6]);
				location.setPhoneNumber((String)obj[7]);
				location.setFaxNumber((String)obj[8]);
				location.setIsHeadquarter((obj[9]+"").equalsIgnoreCase("Y")  ? true : false);
				location.setShiftId((String)obj[10]);	
				location.setHolidayCalendarId((String)obj[11]);
				location.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[12]));
				location.setStatus(obj[13]+"");
				location.setLocationCode((String)obj[14]);
				location.setLocationId((Integer)obj[15]);
				location.setAddress1((String)obj[16]);
				location.setAddress2((String)obj[17]);
				location.setAddress3((String)obj[18]);
				location.setCityId((String)obj[19]);
				location.setStateId((obj[20] != null && obj[20] != "") ? (Integer)obj[20] : null);
				location.setCountryId((obj[21] != null && obj[21] != "") ? (Integer)obj[21] : null);
				location.setPincode((obj[22] != null && obj[22] != "") ? (Integer)obj[22] : null);
				location.setIsShiftSystem((obj[23]+"").equalsIgnoreCase("Y") ? true : false);
				location.setRiskIndicator((String)obj[24]);
				location.setHazardousIndicator((String)obj[25]);
				location.setRiskDetails((String)obj[26]);
				location.setBusinessHoursPerDay((Integer)obj[27]);
				location.setStandardHoursPerWeek((Integer)obj[28]);
				location.setWorkWeekStartDay((String)obj[29]);
				location.setWorkWeekEndDay((String)obj[30]);
				location.setNumberOfWorkingDays((Integer)obj[31]);
				location.setBusinessStartTime((obj[32] != null && obj[32] != "") ? (obj[32]+"").substring(0,5) : null);
				location.setBusinessEndTime((obj[33] != null && obj[33] != "") ?(obj[33]+"").substring(0,5) : null);
				//location.setDepartmentId((Integer)obj[34] == null ? '0' : (Integer)obj[34] );
				
				returnList.add(location);
			}	
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from  getLocationById()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from  getLocationById()  ::   "+returnList);
		return returnList;
	}

	/*
	 * This method will be used to get transaction dates list for location records based on given customer Id, company Id and location Id
	 */

	@Override
	public List<SimpleObject> getTransactionListForLocation(Integer customerId, Integer companyId, Integer locationId) {
		log.info("Entered in getTransactionListForLocation()  ::   customerId = "+customerId+" , companyId = "+companyId+" , locationId = "+locationId);
		Session session = sessionFactory.getCurrentSession();
		List<SimpleObject> transactionList = new ArrayList<>();
		try {
			List locationList = session.createSQLQuery("SELECT Location_Details_Id AS id ,CONCAT(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),' - ',Location_Sequence_Id) AS name FROM  location_details_info location LEFT JOIN m_location mlocation ON (location.Location_Type_Id = mlocation.Location_Type_Id) LEFT JOIN location_details details ON location.Location_Id = details.Location_Id WHERE details.Customer_Id = "+customerId+" AND details.Company_Id = "+companyId+" AND location.Location_Id = "+locationId+" ORDER BY location.Transaction_Date, location.Location_Details_Id").list();
			System.out.println(locationList.size());
			for (Object transDates  : locationList) {
				Object[] transaction = (Object[]) transDates;
				SimpleObject object = new SimpleObject();
				object.setId((Integer)transaction[0]);
				object.setName(transaction[1]+"");
				transactionList.add(object);
			}			
		} catch (Exception e) {
			log.error("Error Occurred...   ", e);
			log.info("Exiting from getTransactionListForLocation()  ::   "+transactionList);
		}
		log.info("Exiting from getTransactionListForLocation()  ::   "+transactionList);
		return transactionList;
	}
	
	
	/*
	 * This method will be used to save/update location_characterstics
	 */
	@Override
	public Integer saveLocationCharacteristics(LocationCharacteristicsVo paramLocation) {
		log.info("Entered in saveLocationCharacteristics()  ::   "+paramLocation);

		Session session = sessionFactory.getCurrentSession();
		Integer characteristicId = 0;
		
		LocationCharacteristics characteristics = new LocationCharacteristics();
		
		System.out.println("pTax :: "+(paramLocation.getIsProfessionalTax() == true));
		System.out.println("union  ::  "+(paramLocation.getIsUnion() == true));
		try{									
			if(paramLocation != null &&  paramLocation.getCharacteristicId() > 0){
				characteristics = (LocationCharacteristics) sessionFactory.getCurrentSession().load(LocationCharacteristics.class, paramLocation.getCharacteristicId());
				characteristics.setLocationDetails(new LocationDetails(paramLocation.getLocationId()));
				characteristics.setCustomerDetails(new CustomerDetails(paramLocation.getCustomerId()));
				characteristics.setCompanyDetails(new CompanyDetails(paramLocation.getCompanyId()));
				characteristics.setCSTNumber(paramLocation.getCstNumber());
				characteristics.setLSTNumber(paramLocation.getLstNumber());
				characteristics.setTANNumber(paramLocation.getTanNumber());
				characteristics.setShopActLicenseNumber(paramLocation.getShopActLicenseNumber());
				characteristics.setIsProfessionalTaxApplicable(paramLocation.getIsProfessionalTax() == true ? "Y":"N");
				characteristics.setIsUnion(paramLocation.getIsUnion() == true ? "Y":"N");
				characteristics.setUnionName(paramLocation.getUnionName());
				characteristics.setPfAccountNumber(paramLocation.getPfAccountNumber());
				characteristics.setPfRegistrationDate(paramLocation.getPfRegistrationDate());
				characteristics.setPfStartDate(paramLocation.getPfStartDate());
				characteristics.setPfType(paramLocation.getPfTypeId() == 0 ? null : paramLocation.getPfTypeId());
				characteristics.setPfCircle(paramLocation.getPfCircle());
				characteristics.setESIAccountNumber(paramLocation.getEsiAccountNumber());
				characteristics.setESIRegistrationDate(paramLocation.getEsiRegistrationDate());
				characteristics.setESIStartDate(paramLocation.getEsiStartDate());
				characteristics.setCountryDetails(new MCountry(paramLocation.getCountryId()));
				characteristics.setModifiedBy(paramLocation.getModifiedBy());
				characteristics.setModifiedDate(new Date());
				session.update(characteristics);
				characteristicId = characteristics.getLocationCharacteristicId();
			}else{					
				characteristics.setLocationDetails(new LocationDetails(paramLocation.getLocationId()));
				characteristics.setCustomerDetails(new CustomerDetails(paramLocation.getCustomerId()));
				characteristics.setCompanyDetails(new CompanyDetails(paramLocation.getCompanyId()));
				characteristics.setCSTNumber(paramLocation.getCstNumber());
				characteristics.setLSTNumber(paramLocation.getLstNumber());
				characteristics.setTANNumber(paramLocation.getTanNumber());
				characteristics.setShopActLicenseNumber(paramLocation.getShopActLicenseNumber());
				characteristics.setIsProfessionalTaxApplicable(paramLocation.getIsProfessionalTax() == true ? "Y":"N");
				characteristics.setIsUnion(paramLocation.getIsUnion() == true ? "Y":"N");
				characteristics.setUnionName(paramLocation.getUnionName());
				characteristics.setPfAccountNumber(paramLocation.getPfAccountNumber());
				characteristics.setPfRegistrationDate(paramLocation.getPfRegistrationDate());
				characteristics.setPfStartDate(paramLocation.getPfStartDate());
				characteristics.setPfType(paramLocation.getPfTypeId() == 0 ? null : paramLocation.getPfTypeId());
				characteristics.setPfCircle(paramLocation.getPfCircle());
				characteristics.setESIAccountNumber(paramLocation.getEsiAccountNumber());
				characteristics.setESIRegistrationDate(paramLocation.getEsiRegistrationDate());
				characteristics.setESIStartDate(paramLocation.getEsiStartDate());
				characteristics.setCountryDetails(new MCountry(paramLocation.getCountryId()));
				characteristics.setCreatedBy(paramLocation.getCreatedBy());
				characteristics.setModifiedBy(paramLocation.getModifiedBy());
				characteristics.setCreatedDate(new Date());
				characteristics.setModifiedDate(new Date());
						
				characteristicId = (Integer) session.save(characteristics);
			}
			
			session.flush();
		}catch (Exception e) {
			 if(!(session.getTransaction().wasRolledBack())){
				 session.getTransaction().rollback();
			 }
			 log.error("Error Occurred...   ", e);
			 log.info("Exiting from saveLocationCharacterstics()  ::  characteristicId =  "+characteristicId);
		}				
		log.info("Exiting from saveLocationCharacterstics()  ::  characteristicId =  "+characteristicId);
		return characteristicId;
	}
	
	/*
	 * This method will be used to get the location details by given location Id
	 */
	@Override
	public List<LocationCharacteristicsVo> getLocationCharacteristicByLocationId(Integer locationId) {
		log.info("Entered in  getLocationCharacteristicByLocationId()  ::   LocationId = "+locationId);
		Session session = sessionFactory.getCurrentSession();
		LocationCharacteristicsVo returnLocation = new LocationCharacteristicsVo();
		List<LocationCharacteristicsVo> returnList = new ArrayList<LocationCharacteristicsVo>();
		
		String hqlQuery = "SELECT characterstics.Location_Characteristic_Id, locDetails.Customer_Id, locDetails.Company_Id, "
							+ " locDetails.Location_Code, characterstics.CST_Number, characterstics.LST_Number, characterstics.TAN_Number, "
							+ " characterstics.Shop_Act_License_Number, characterstics.Is_Professional_Tax_Applicable, characterstics.Is_Union, "
							+"  characterstics.Union_Name, characterstics.PF_Account_Number, characterstics.PF_Registration_Date, "
							+"  characterstics.PF_Start_Date, characterstics.PF_Type_Id, characterstics.PF_Circle, "
							+ " characterstics.ESI_Account_Number, characterstics.ESI_Registration_Date, characterstics.ESI_Start_Date,info.Location_Name, info.Country_Id"
							+ " FROM location_details locDetails "
							+ " LEFT JOIN location_details_info info ON locDetails.Location_Id = info.Location_Id"
							+ " LEFT JOIN location_characteristics AS characterstics  ON characterstics.Location_Id = locDetails.Location_Id"
							+ " LEFT JOIN company_details AS company  ON locDetails.Customer_Id = company.Customer_Id  AND locDetails.Company_Id = company.Company_Id " 								
							+ " LEFT JOIN customer_details AS customer  ON locDetails.Customer_Id = customer.Customer_Id "
								+ " WHERE info.Location_Id = "+locationId
								+" GROUP BY characterstics.Location_Id Order By characterstics.Location_Id asc";
		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			List queryList = query.list();

			for (Object dept : queryList) {
				Object[] obj = (Object[]) dept;
				returnLocation = new LocationCharacteristicsVo();

				returnLocation.setCharacteristicId((Integer)(obj[0] == null ? 0 :obj[0]));
				returnLocation.setCustomerId((Integer)(obj[1] == null ? 0 :obj[1]));
				returnLocation.setCompanyId((Integer)(obj[2] == null ? 0 :obj[2]));
				returnLocation.setLocationCode((String)obj[3]);
				returnLocation.setCstNumber((String)obj[4]);
				returnLocation.setLstNumber((String)obj[5]);
				returnLocation.setTanNumber((String)obj[6]);
				returnLocation.setShopActLicenseNumber((String)obj[7]);
				returnLocation.setIsProfessionalTax((obj[8]+"").equalsIgnoreCase("Y") ? true : false);
				returnLocation.setIsUnion((obj[9]+"").equalsIgnoreCase("Y") ? true : false);
				returnLocation.setUnionName((String)obj[10]);
				returnLocation.setPfAccountNumber((String)obj[11]);
				returnLocation.setPfRegistrationDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[12]));
				returnLocation.setPfStartDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[13]));
				returnLocation.setPfTypeId((Integer)(obj[14] == null ? 0 :obj[14]));
				returnLocation.setPfCircle((String)obj[15]);
				returnLocation.setEsiAccountNumber((String)obj[16]);
				returnLocation.setEsiRegistrationDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[17]));
				returnLocation.setEsiStartDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[18]));
				returnLocation.setLocationName((String)obj[19]);
				returnLocation.setCountryId((Integer)(obj[20] == null ? 0 :obj[20]));
				returnList.add(returnLocation);
			}	
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from  getLocationCharacteristicByLocationId()  ::   "+returnLocation);
		}
		session.flush();
		log.info("Exiting from  getLocationCharacteristicByLocationId()  ::   "+returnLocation);
		return returnList;
	}
	

	@Override
	public List<LocationVo> getDepartmentList(Integer locationDetailsId) {
		
		log.info("Entered in  getDepartmentList()  ::   LocationDetailsId = "+locationDetailsId);
		Session session = sessionFactory.getCurrentSession();
		LocationVo location = new LocationVo();
		List<LocationVo> returnList = new ArrayList<LocationVo>();
		
		
		String hqlQuery = "SELECT locDetails.Customer_Id,  locDetails.Company_Id, "
							+ " dept.Department_Id, department.Department_Name, deptDetails.Department_Code, department.Department_Info_Id"
							+ " FROM location_details_info AS location "
							+ " LEFT JOIN location_details locDetails ON location.Location_Id = locDetails.Location_Id"
							+ " LEFT JOIN m_location mlocation ON location.Location_Type_Id = mlocation.Location_Type_Id "
							+ " LEFT JOIN company_details AS company  ON locDetails.Customer_Id = company.Customer_Id  AND locDetails.Company_Id = company.Company_Id " 								
							+ " LEFT JOIN customer_details AS customer  ON locDetails.Customer_Id = customer.Customer_Id "
							+ " LEFT JOIN location_department AS dept ON locDetails.Location_Id = dept.Location_Id "
							+ " LEFT JOIN department_details deptDetails ON dept.Department_Id =deptDetails.Department_Id"
							+ " LEFT JOIN department_details_info department ON department.Department_Id = deptDetails.Department_Id"
								+ " WHERE location.Location_Id = "+locationDetailsId
								+ " AND department.Transaction_Date = ("
		  						+ "	SELECT MAX(info1.Transaction_Date) FROM department_details_info info1 "
    							 		+ "WHERE department.Department_Id = info1.Department_Id "
    							 		+ " AND info1.Transaction_Date <= department.Transaction_Date) "				  
    							+ " AND Department_Sequence_Id IN ( "
								  + " SELECT Max(info2.Department_Sequence_Id) FROM department_details_info info2 "
										 + " WHERE info2.Department_Id = department.Department_Id "
										 + " AND info2.Is_Active = department.Is_Active "
										 + " AND info2.Department_Type_Id = department.Department_Type_Id "
										 + " AND info2.Transaction_Date <= department.Transaction_Date ) and department.is_active= 'Y' "
								+" GROUP BY location.Location_Id,dept.Department_Id ORDER BY deptDetails.Department_Code asc";

		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			List querylist = query.list();

			for (Object dept : querylist) {
				Object[] obj = (Object[]) dept;
				location = new LocationVo();
				location.setCustomerId((Integer)obj[0]);
				location.setCompanyId((Integer)obj[1]);
				location.setDepartmentId((Integer)obj[2]);
				location.setDepartmentName((String)obj[3]);
				location.setDepartmentCode((String)obj[4]);
				location.setDepartmentInfoId((Integer)obj[5]);
				
				returnList.add(location);
			}	
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from  getDepartmentList()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from  getDepartmentList()  ::   "+returnList);
		return returnList;
	}
	
	
	/*
	 *This method will be used to check location code already exists or not and headquarter exists or not for that customer and location
	 */
	
	@Override
	public Integer validateLocationCode(LocationVo paramLocation) {
		log.info("Entered in  validateLocationCode()  ::   ParamLocation = "+paramLocation);

		//log.info("Entered in  validateLocationCode()  ::   customerId = "+customerId+", companyId = "+companyId+", countryId = "+countryId+", locationCode = "+locationCode+", isHeadQuarter = "+isHeadQuarter);
		Session session = sessionFactory.getCurrentSession();
		Integer returnSize = 0;
		
		
		String hqlQuery = "SELECT  ldi.Is_Headquarter, ld.Location_Code "
							 + " from location_details  ld "
							 + " INNER join location_details_info ldi on(ldi.location_id = ld.location_id) "
									 + " AND CONCAT(DATE_FORMAT(ldi.transaction_date, '%Y%m%d'), LPAD(ldi.location_Sequence_Id, 2, '0')) =  "
										 +"	 ( SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.location_Sequence_Id, 2, '0'))) "
										 +" FROM location_details_info vdi1 "
										 +"	 WHERE  ldi.`location_id` = vdi1.`location_id`     "
										 +"  AND vdi1.transaction_date <= CURRENT_DATE())   "
							 + " WHERE  ldi.Customer_Id = "+paramLocation.getCustomerId()
							 + " AND  ldi.Company_Id = "+paramLocation.getCompanyId()
							 + " AND ldi.Country_Id ="+paramLocation.getCountryId();
		

		//search based on location code
		if(paramLocation.getLocationCode() != null && !paramLocation.getLocationCode().isEmpty() && paramLocation.getLocationId() == 0){
			hqlQuery += " AND ld.Location_Code = '"+paramLocation.getLocationCode()+"' ";
		}else if( paramLocation.getLocationId() > 0){
			hqlQuery += " AND ldi.Location_Id  NOT IN("+paramLocation.getLocationId()+") ";
		}
		
		if(paramLocation.getIsHeadquarter() == true){
			hqlQuery += " AND ldi.Is_Headquarter = 'Y' ";
		}
		
		hqlQuery += " GROUP BY ldi.Location_Id Order By ldi.Location_Name asc";
		
		try {				
			List locationList = session.createSQLQuery(hqlQuery).list();
			returnSize = locationList.size();
				
		} catch (Exception e) {
			log.error("Exiting from  validateLocationCode()  with Exception:  "+returnSize,e);
		}
		session.flush();
		log.info("Exiting from  validateLocationCode()  ::   "+returnSize);
		return returnSize;
	}
}
