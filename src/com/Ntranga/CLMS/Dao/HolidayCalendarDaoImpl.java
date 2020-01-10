package com.Ntranga.CLMS.Dao;

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

import com.Ntranga.CLMS.vo.HolidayCalendarHolidaysInfoVo;
import com.Ntranga.CLMS.vo.HolidayCalendarVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.HolidayCalendarDetails;
import com.Ntranga.core.CLMS.entities.HolidayCalendarDetailsInfo;
import com.Ntranga.core.CLMS.entities.HolidayCalendarHolidaysInfo;
import com.Ntranga.core.CLMS.entities.LocationDetails;
import com.Ntranga.core.CLMS.entities.MCountry;
import com.Ntranga.core.CLMS.entities.MHolidayType;

import common.Logger;

@Transactional
@Repository("holidayCalendarDao")
@SuppressWarnings("rawtypes")
public class HolidayCalendarDaoImpl implements HolidayCalendarDao {

	private static Logger log = Logger.getLogger(HolidayCalendarDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/*
	 * This method will be used to save holiday_calendar_details, holiday_calendar_details_info and save/updateholiday_calendar_holiays_info
	 */
	@Override
	public Integer saveHolidayCalendar(HolidayCalendarVo paramHoliday) {
		log.info("Entered in saveHolidayCalendar()  ::   "+paramHoliday);

		Session session = sessionFactory.getCurrentSession();
		Integer holidayCalendarDetailsId = 0;
		Integer holidayCalendarId = 0;
		//BigInteger plantSequenceId = new BigInteger("0");	
		
		
		HolidayCalendarDetails holidayDetails = new HolidayCalendarDetails();
		HolidayCalendarDetailsInfo holidayDetailsInfo = new HolidayCalendarDetailsInfo();
		HolidayCalendarHolidaysInfo holidaysInfo = new HolidayCalendarHolidaysInfo();
		
		try{									
			if(paramHoliday != null && paramHoliday.getHolidayCalendarId() > 0){
				
				holidayDetails = (HolidayCalendarDetails) session.load(HolidayCalendarDetails.class, paramHoliday.getHolidayCalendarId());
				holidayDetails.setCustomerDetails(new CustomerDetails(paramHoliday.getCustomerId()));
				holidayDetails.setCompanyDetails(new CompanyDetails(paramHoliday.getCompanyId()));
				holidayDetails.setHolidayCalendarCode(paramHoliday.getHolidayCalendarCode().toUpperCase());
				holidayDetails.setModifiedBy(paramHoliday.getModifiedBy());
				holidayDetails.setModifiedDate(new Date());
				session.update(holidayDetails);
				holidayCalendarId  = paramHoliday.getHolidayCalendarId();
			}else{
				
				holidayDetails = new HolidayCalendarDetails();
				holidayDetails.setCustomerDetails(new CustomerDetails(paramHoliday.getCustomerId()));
				holidayDetails.setCompanyDetails(new CompanyDetails(paramHoliday.getCompanyId()));
				holidayDetails.setHolidayCalendarCode(paramHoliday.getHolidayCalendarCode().toUpperCase());
				holidayDetails.setLocationDetails(new LocationDetails(paramHoliday.getLocationId()));
				holidayDetails.setCreatedBy(paramHoliday.getCreatedBy());
				holidayDetails.setModifiedBy(paramHoliday.getModifiedBy());
				holidayDetails.setCreatedDate(new Date());
				holidayDetails.setModifiedDate(new Date());
				holidayCalendarId = (Integer) session.save(holidayDetails);
			}
			
			session.clear();
			//Holiday_Calendar_details_info table
			if(paramHoliday != null &&  paramHoliday.getHolidayCalendarDetailsId() > 0){
				holidayDetailsInfo = (HolidayCalendarDetailsInfo) sessionFactory.getCurrentSession().load(HolidayCalendarDetailsInfo.class, paramHoliday.getHolidayCalendarDetailsId());
				holidayDetailsInfo.setHolidayCalendarDetails(new HolidayCalendarDetails(holidayCalendarId));
				holidayDetailsInfo.setCustomerDetails(new CustomerDetails(paramHoliday.getCustomerId()));
				holidayDetailsInfo.setCompanyDetails(new CompanyDetails(paramHoliday.getCompanyId()));
				holidayDetailsInfo.setLocationDetails(new LocationDetails(paramHoliday.getLocationId()));
				holidayDetailsInfo.setMCountry(new MCountry(paramHoliday.getCountryId()));
				holidayDetailsInfo.setCalendarName(paramHoliday.getCalendarName());
				holidayDetailsInfo.setStatus(paramHoliday.getStatus());
				holidayDetailsInfo.setYear(paramHoliday.getYear());
				holidayDetailsInfo.setModifiedBy(paramHoliday.getModifiedBy());
				holidayDetailsInfo.setModifiedDate(new Date());
				session.update(holidayDetailsInfo);
				holidayCalendarDetailsId = holidayDetailsInfo.getHolidayCalendarDetailsId();
			}else{	
				holidayDetailsInfo = new HolidayCalendarDetailsInfo();
				holidayDetailsInfo.setHolidayCalendarDetails(new HolidayCalendarDetails(holidayCalendarId));
				holidayDetailsInfo.setCustomerDetails(new CustomerDetails(paramHoliday.getCustomerId()));
				holidayDetailsInfo.setCompanyDetails(new CompanyDetails(paramHoliday.getCompanyId()));
				holidayDetailsInfo.setLocationDetails(new LocationDetails(paramHoliday.getLocationId()));
				holidayDetailsInfo.setMCountry(new MCountry(paramHoliday.getCountryId()));
				holidayDetailsInfo.setCalendarName(paramHoliday.getCalendarName());
				holidayDetailsInfo.setStatus(paramHoliday.getStatus());
				holidayDetailsInfo.setYear(paramHoliday.getYear());;
				holidayDetailsInfo.setCreatedBy(paramHoliday.getCreatedBy());
				holidayDetailsInfo.setModifiedBy(paramHoliday.getModifiedBy());
				holidayDetailsInfo.setCreatedDate(new Date());
				holidayDetailsInfo.setModifiedDate(new Date());
				
				holidayCalendarDetailsId = (Integer) session.save(holidayDetailsInfo);
			}
			
			
			session.clear();
			
			if(holidayCalendarDetailsId > 0 && paramHoliday.getHolidayList() != null && paramHoliday.getHolidayList().size() > 0){
				Query q = session.createQuery("DELETE FROM  HolidayCalendarHolidaysInfo WHERE Holiday_Calendar_Details_Id= "+holidayDetailsInfo.getHolidayCalendarDetailsId());
				q.executeUpdate();
				if(paramHoliday.getHolidayList() != null ){
					for(HolidayCalendarHolidaysInfoVo object : paramHoliday.getHolidayList()){
						holidaysInfo = new HolidayCalendarHolidaysInfo();
						holidaysInfo.setHolidayCalendarDetailsInfo(new HolidayCalendarDetailsInfo(holidayDetailsInfo.getHolidayCalendarDetailsId()));
						holidaysInfo.setCustomerDetails(new CustomerDetails(paramHoliday.getCustomerId()));
						holidaysInfo.setCompanyDetails(new CompanyDetails(paramHoliday.getCompanyId()));
						holidaysInfo.setHolidayName(object.getHolidayName());
						holidaysInfo.setMHoliday(new MHolidayType(object.getHolidayTypeId()));
						holidaysInfo.setFromDate(object.getFromDate());
						holidaysInfo.setToDate(object.getToDate());
						holidaysInfo.setCreatedBy(paramHoliday.getCreatedBy());
						holidaysInfo.setModifiedBy(paramHoliday.getCreatedBy());
						holidaysInfo.setCreatedDate(new Date());
						holidaysInfo.setModifiedDate(new Date());
						
						session.save(holidaysInfo);	
					}
				}
			}
			session.flush();
		}catch (Exception e) {
			 if(!(session.getTransaction().wasRolledBack())){
				 session.getTransaction().rollback();
			 }
			 log.error("Error Occurred...   ", e);
			 log.info("Exiting from saveHolidayCalendar()  ::  holidayCalendarDetailsId =  "+holidayCalendarDetailsId);
		}				
		log.info("Exiting from saveHolidayCalendar()  ::  holidayCalendarDetailsId =  "+holidayCalendarDetailsId);
		return holidayCalendarDetailsId;
	}

	/*
	 * This method will be used to get holiday Calendar record for given holidayCalendarDetailsId
	 */
	@Override
	public List<HolidayCalendarVo> getHolidayCalendarById(Integer holidayCalendarDetailsId) {
		log.info("Entered in  getHolidayCalendarById()  ::   holidayCalendarDetailsId = "+holidayCalendarDetailsId);
		Session session = sessionFactory.getCurrentSession();
		HolidayCalendarVo holiday = new HolidayCalendarVo();
		List<HolidayCalendarVo> returnList = new ArrayList<HolidayCalendarVo>();
		String  hqlQuery = " SELECT info.Customer_Id, info.Company_Id, info.Holiday_Calendar_Details_Id, info.Holiday_Calendar_Id, "
							+ "	holiday.Holiday_Calendar_Code, info.Calendar_Name, holiday.Location_Id, info.Year, info.Status, info.country_Id"
							+ " FROM holiday_calendar_details_info AS info "
							+ " LEFT JOIN holiday_calendar_details holiday ON holiday.Holiday_Calendar_Id = info.Holiday_Calendar_Id"
							+ " LEFT JOIN company_details AS cmp  ON info.Customer_Id = cmp.Customer_Id  "
																			+ "	AND info.Company_Id = cmp.Company_Id "
							+ " LEFT JOIN company_details_info AS company  ON company.Customer_Id = info.Customer_Id  "
																			+ "	AND company.Company_Id = info.Company_Id "
							+ " LEFT JOIN customer_details AS cus  ON info.Customer_Id = cus.Customer_Id "
							+ " LEFT JOIN customer_details_info AS customer  ON customer.Customer_Id = info.Customer_Id "
							+ " WHERE info.Holiday_Calendar_Details_Id = "+holidayCalendarDetailsId;
		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			List queryList = query.list();

			for (Object object : queryList) {
				Object[] obj = (Object[]) object;
				holiday = new HolidayCalendarVo();
				holiday.setCustomerId((Integer)obj[0]);
				holiday.setCompanyId((Integer)obj[1]);
				holiday.setHolidayCalendarDetailsId((Integer)obj[2]);
				holiday.setHolidayCalendarId((Integer)obj[3]);
				holiday.setHolidayCalendarCode((String)obj[4]);
				holiday.setCalendarName((String)obj[5]);
				holiday.setLocationId((Integer)obj[6]);
				holiday.setYear((Integer)obj[7]);
				holiday.setStatus(obj[8]+"");
				holiday.setCountryId((Integer)obj[9]);
	
				returnList.add(holiday);
			}	
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from  getHolidayCalendarById()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from  getHolidayCalendarById()  ::   "+returnList);
		return returnList;
	}

	/*
	 * This method will be used to search the holiday calendar list based on customer Id, companyId, countryId, location Id, 
	 * 																	calendar code and status
	 */
	@Override
	public List<HolidayCalendarVo> getHolidayCalendarListBySearch(HolidayCalendarVo paramHoliday) {
		log.info("Entered in  getHolidayCalendarListBySearch()  ::   HolidayCalendarVo  = "+paramHoliday);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<HolidayCalendarVo> returnList = new ArrayList<HolidayCalendarVo>();
		HolidayCalendarVo holiday = new HolidayCalendarVo();
		
		String hqlQuery = "SELECT  company.Company_Name, customer.Customer_Name, country.Country_Name, "
							+ "	info.Holiday_Calendar_Id,  info.Calendar_Name, location.Location_Name, "
							+ "	info.Year, info.Status, info.Holiday_Calendar_Details_Id , holiday.Holiday_Calendar_Code"
							+ " FROM holiday_calendar_details_info AS info "
							+ " LEFT JOIN holiday_calendar_details holiday ON holiday.Holiday_Calendar_Id = info.Holiday_Calendar_Id "
							
							+ " LEFT JOIN company_details_info AS company  ON company.Customer_Id = info.Customer_Id  AND company.Company_Id = info.Company_Id "
																	+ "	AND  CONCAT(DATE_FORMAT(company.Transaction_Date, '%Y%m%d'), company.Company_Sequence_Id) = ( "
																	+"	SELECT MAX(CONCAT(DATE_FORMAT(company1.Transaction_Date, '%Y%m%d'), company1.Company_Sequence_Id)) "
																	+"	FROM company_details_info company1 "
																	+"	WHERE company.Company_Id = company1.Company_Id AND company1.Transaction_Date <= CURRENT_DATE() "
																	+ " ) "	
							+ " LEFT JOIN customer_details_info AS customer  ON customer.Customer_Id = info.Customer_Id "
																	+ " AND  CONCAT(DATE_FORMAT(customer.Transaction_Date, '%Y%m%d'), customer.Customer_Sequence_Id) =  ( "
																	+"	SELECT MAX(CONCAT(DATE_FORMAT(customer1.Transaction_Date, '%Y%m%d'), customer1.Customer_Sequence_Id)) "
																	+"	FROM customer_details_info customer1 "
																	+"	WHERE customer.Customer_Id = customer1.Customer_Id AND customer1.Transaction_Date <= CURRENT_DATE() "
																	+"	) "
							+ " LEFT JOIN location_details_info location ON  location.Location_Id = holiday.Location_Id "
																	+ " AND  CONCAT(DATE_FORMAT(location.Transaction_Date, '%Y%m%d'), location.Location_Sequence_Id) =  ( "
																	+"	SELECT MAX(CONCAT(DATE_FORMAT(location1.Transaction_Date, '%Y%m%d'), location1.Location_Sequence_Id)) "
																	+"	FROM location_details_info location1 "
																	+"	WHERE location.Location_Id = location1.Location_Id AND location1.Transaction_Date <= CURRENT_DATE() "
																	+"	) "
							+ " LEFT JOIN m_country country ON  country.Country_Id = info.Country_Id "

							+ " WHERE 1= 1  ";
		
		
		if(paramHoliday.getCustomerId() > 0){
			hqlQuery += "  AND  info.Customer_Id = "+paramHoliday.getCustomerId();
		}
		
		if(paramHoliday.getCompanyId() > 0){
			hqlQuery += "  AND  info.Company_Id = "+paramHoliday.getCompanyId();
		}
		if(paramHoliday.getLocationId() > 0){
			hqlQuery += "  AND  info.Location_Id = "+paramHoliday.getLocationId();
		}
		
		if(paramHoliday.getCountryId() > 0){
			hqlQuery += " AND info.Country_Id = "+paramHoliday.getCountryId();
		}
		
		if(paramHoliday.getHolidayCalendarCode() != null && !paramHoliday.getHolidayCalendarCode().isEmpty()){
			hqlQuery += " AND holiday.Holiday_Calendar_Code LIKE '"+paramHoliday.getHolidayCalendarCode()+"%' ";
		}
		
		if(paramHoliday.getStatus() != null && !paramHoliday.getStatus().isEmpty()){
			hqlQuery += " AND info.Status LIKE '"+paramHoliday.getStatus()+"%' ";
		}
		
		if(paramHoliday.getYear() != null && paramHoliday.getYear() > 0){
			hqlQuery += " AND info.Year = '"+paramHoliday.getYear()+"' ";
		}else{
			hqlQuery += " AND customer.Is_Active = 'Y' AND company.Is_Active = 'Y' ";
		}
		
		hqlQuery += " GROUP BY info.Holiday_Calendar_Id Order By holiday.Holiday_Calendar_Code asc";
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){
				for (Object customer : queryList) {
					Object[] obj = (Object[]) customer;
					holiday = new HolidayCalendarVo();

					holiday.setCompanyName((String)obj[0]);
					holiday.setCustomerName((String)obj[1]);
					holiday.setCountryName((String)obj[2]);
					holiday.setHolidayCalendarId((Integer)obj[3]);
					holiday.setCalendarName((String)obj[4]);
					holiday.setLocationName((String)obj[5]);
					holiday.setYear((Integer)obj[6]);
					holiday.setStatus((obj[7]+"").equalsIgnoreCase("Y") ? "Active" : "Inactive");
					holiday.setHolidayCalendarDetailsId((Integer)obj[8]);
					holiday.setHolidayCalendarCode((String)obj[9]);
					
					returnList.add(holiday);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getPlantsListBySearch()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getPlantsListBySearch()  ::   "+returnList);
		return returnList;
	}

	/*
	 * This method will be used to get holidays list for a given holidayCalendarDetailsId
	 */
	@Override
	public List<HolidayCalendarHolidaysInfoVo> getHolidaysList(Integer holidayCalendarDetailsId) {
		log.info("Entered in  getHoildaysList()  ::   holidayCalendarDetailsId = "+holidayCalendarDetailsId);
		
		Session session = sessionFactory.getCurrentSession();
		HolidayCalendarHolidaysInfoVo holiday = new HolidayCalendarHolidaysInfoVo();
		List<HolidayCalendarHolidaysInfoVo> returnList = new ArrayList<HolidayCalendarHolidaysInfoVo>();
		
		String  hqlQuery = " SELECT info.Customer_Id, info.Company_Id, info.Holiday_Calendar_Details_Id, holiday.Holiday_Id, "
							+ "	holiday.Holiday_Name, holiday.From_Date, holiday.To_Date, holidayType.holiday_Type_Name, holiday.Holiday_Type_Id"
							+ " FROM holiday_calendar_details_info AS info "
							+ " LEFT JOIN holiday_calendar_details_info holi ON holi.Holiday_Calendar_Id = info.Holiday_Calendar_Id "
							+ " LEFT JOIN holiday_calendar_holidays_info holiday ON holiday.Holiday_Calendar_Details_Id = holi.Holiday_Calendar_Details_Id "
							+ " LEFT JOIN m_holiday_type holidayType ON holidayType.Holiday_Type_Id = holiday.Holiday_Type_Id "
							+ " WHERE holiday.Holiday_Calendar_Details_Id = "+holidayCalendarDetailsId
							+"  ORDER BY holiday.From_Date, holiday.To_Date, holiday.Holiday_Name asc";
		
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			List queryList = query.list();

			for (Object object : queryList) {
				Object[] obj = (Object[]) object;
				holiday = new HolidayCalendarHolidaysInfoVo();
				holiday.setCustomerId((Integer)obj[0]);
				holiday.setCompanyId((Integer)obj[1]);
				holiday.setHolidayCalendarDetailsId((Integer)obj[2]);
				holiday.setHolidayId((Integer)obj[3]);
				holiday.setHolidayName((String)obj[4]);
				holiday.setFromDate((DateHelper.convertDate((Date)obj[5])));
				holiday.setToDate((DateHelper.convertDate((Date)obj[6])));
				holiday.setHolidayTypeName((String)obj[7]);
				holiday.setHolidayTypeId((Integer)obj[8]);
	
	
				returnList.add(holiday);
			}	
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from  getHoildaysList()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from  getHoildaysList()  ::   "+returnList);
		return returnList;
	}

	@Override
	public List<SimpleObject> getHolidayCalendarDropdown(Integer customerId, Integer companyId) {
		log.info("Entered in  getHolidayCalendarDropdown()  ::   customerId  = "+customerId+", companyId  = "+companyId);
		
		Session session = sessionFactory.getCurrentSession();
		List queryList = null;
		List<SimpleObject> returnList = new ArrayList<SimpleObject>();
		
		String hqlQuery = "SELECT  info.Holiday_Calendar_Id,  info.Calendar_Name, holiday.Holiday_Calendar_Code"
							+ " FROM holiday_calendar_details_info AS info "
									+ " LEFT JOIN holiday_calendar_details holiday ON holiday.Holiday_Calendar_Id = info.Holiday_Calendar_Id "
									+ " LEFT JOIN company_view AS company  ON company.Customer_Id = info.Customer_Id  AND company.Company_Id = info.Company_Id "
									+ " LEFT JOIN customer_view AS customer  ON customer.Customer_Id = info.Customer_Id "
									+ " LEFT JOIN location_details_info location ON  location.Location_Id = holiday.Location_Id "
												+ " AND  CONCAT(DATE_FORMAT(location.Transaction_Date, '%Y%m%d'), location.Location_Sequence_Id) =  ( "
												+"	SELECT MAX(CONCAT(DATE_FORMAT(location1.Transaction_Date, '%Y%m%d'), location1.Location_Sequence_Id)) "
												+"	FROM location_details_info location1 "
												+"	WHERE location.Location_Id = location1.Location_Id AND location1.Transaction_Date <= CURRENT_DATE() "
												+"	) "
							+ " WHERE 1= 1  "
							+ " AND  info.Customer_Id = "+customerId
							+ " AND  info.Company_Id = "+companyId
							//+ " AND info.Country_Id = "+countryId
							+ " AND customer.Is_Active = 'Y' "
							+ " AND company.Is_Active = 'Y' "
							+ " AND location.Status = 'Y' "
							+ " GROUP BY info.Holiday_Calendar_Id Order By holiday.Holiday_Calendar_Code asc";
		try {				
			SQLQuery query = session.createSQLQuery(hqlQuery);
			queryList = query.list();
			if(queryList.size() > 0){
				for (Object holiday : queryList) {
					Object[] obj = (Object[]) holiday;
					SimpleObject object = new SimpleObject();
						object.setId((Integer) obj[0]);
						object.setName((String)obj[1]);
						object.setDesc((String)obj[2]);
					
					returnList.add(object);
				}	
			}
				
		} catch (Exception e) {
			log.error("Error occured ... ",e);
			log.info("Exiting from getHolidayCalendarDropdown()  ::   "+returnList);
		}
		session.flush();
		log.info("Exiting from getHolidayCalendarDropdown()  ::   "+returnList);
		return returnList;
	}

	
}
