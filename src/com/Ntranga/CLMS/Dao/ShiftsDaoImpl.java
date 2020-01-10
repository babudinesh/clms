package com.Ntranga.CLMS.Dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.ShiftsVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.Shifts;

import common.Logger;


@Transactional
@Service("shiftsDao")
public class ShiftsDaoImpl implements ShiftsDao {

	private static Logger log = Logger.getLogger(ShiftsDaoImpl.class);
	
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Integer saveShiftData(ShiftsVo shiftsVo) {
		Session session = null;
		Integer shiftId = 0;
		try{
			
			session = sessionFactory.getCurrentSession();			
			Shifts shifts = new Shifts();
			if(shiftsVo.getShiftId() != null && shiftsVo.getShiftId() > 0){
				shifts = (Shifts) session.get(Shifts.class, shiftsVo.getShiftId());
			}
			shifts.setCompanyDetails(new CompanyDetails(shiftsVo.getCompanyDetailsId()));
			shifts.setCustomerDetails(new CustomerDetails(shiftsVo.getCustomerDetailsId()));
			shifts.setCountryId(shiftsVo.getCountryId());
			shifts.setTransactionDate(shiftsVo.getTransactionDate());
			shifts.setIsActive(shiftsVo.getIsActive());
			shifts.setWeekStartDay(shiftsVo.getWeekStartDay());
			shifts.setWeekEndDay(shiftsVo.getWeekEndDay());
			shifts.setTimeZone(shiftsVo.getTimeZone());
			shifts.setTimeSetFormat(shiftsVo.getTimeSetFormat());			
			shifts.setCurrencyId(shiftsVo.getCurrencyId());	
			shifts.setDefaultPatternType(shiftsVo.getDefaultPatternType());
			shifts.setModifiedBy(shiftsVo.getModifiedBy());
			shifts.setModifiedDate(new Date());			
			if(shiftsVo.getShiftId() != null && shiftsVo.getShiftId() > 0){
				session.update(shifts);
			}else{
				BigInteger sequenceId = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Sequence_Id),0) FROM  `shifts` WHERE `Customer_Id`="+shiftsVo.getCustomerDetailsId()+" and `Company_id`="+shiftsVo.getCompanyDetailsId()+" and  `Country_Id`="+shiftsVo.getCountryId()+" and  `Transaction_Date`='"+DateHelper.convertDateToSQLString(shiftsVo.getTransactionDate())+"'").list().get(0);
				shifts.setSequnceId(sequenceId.intValue()+1);	
				shifts.setCreatedBy(shiftsVo.getCreatedBy());
				shifts.setCretatedDate(new Date());			
				session.save(shifts);
			}
					
			session.flush();
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return shiftId;
	}

	@Override
	public ShiftsVo getShiftRecord(ShiftsVo shiftsVo) {
		ShiftsVo vo = new ShiftsVo();
		try{			
			List tempList = sessionFactory.getCurrentSession().createSQLQuery("SELECT `Shift_id`, Customer_Id, `Company_id`, `Country_Id`, `Transaction_Date`, `Is_Active`, `Week_Start_Day`, `Week_End_Day`, "+
																			"	Time_Zone, `Time_Set_Format`, `Currency_Id`,Default_Pattern_Type  FROM `shifts` parent  "+
																			"	WHERE  "+
																			"	CONCAT(DATE_FORMAT(parent.transaction_date, '%Y%m%d'), LPAD(parent.Sequence_Id, 2, '0')) =   "+
																			"	(  "+
																			"	SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.Sequence_Id, 2, '0')))  "+
																			"	FROM shifts vdi1  	WHERE  parent.customer_Id = vdi1.customer_Id AND    "+
																			"	parent.company_id = vdi1.company_id AND  parent.country_id = vdi1.country_id    "+
																			"	AND vdi1.transaction_date <= CURRENT_DATE()    "+
																			"	) AND `Customer_Id`="+shiftsVo.getCustomerDetailsId()+" AND `Company_id`="+shiftsVo.getCompanyDetailsId()+" "+
																			"	and `Country_Id`="+shiftsVo.getCountryId()+"").list();
			for(Object object : tempList){
				Object[] obj = (Object[]) object;
				 vo.setShiftId((Integer)obj[0]);
				 vo.setCustomerDetailsId((Integer)obj[1]);
				 vo.setCompanyDetailsId((Integer)obj[2]);	        	
	        	 vo.setCountryId((Integer)obj[3]);
	        	 vo.setTransactionDate((Date)obj[4]);
	        	 vo.setIsActive(obj[5]+"");
	        	 vo.setWeekStartDay((Integer)obj[6]);
	        	 vo.setWeekEndDay((Integer)obj[7]);
	        	 vo.setTimeZone(obj[8]+"");
	        	 vo.setTimeSetFormat(obj[9]+"");			
	        	 vo.setCurrencyId((Integer)obj[10]);
	        	 vo.setDefaultPatternType(obj[11]+"");
			}
			
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return vo;
	}

	@Override
	public List<SimpleObject> getTransactionDatesForShiftsHistory(ShiftsVo shiftsVo) {
		// TODO Auto-generated method stub		
		List<SimpleObject> objects = new ArrayList<SimpleObject>();
		try{
			
			List tempList = sessionFactory.getCurrentSession().createSQLQuery("SELECT `Shift_id` , CONCAT(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),' - ', Sequence_Id) AS tname FROM `shifts`  WHERE `Customer_Id`="+shiftsVo.getCustomerDetailsId()+" and `Company_id`="+shiftsVo.getCompanyDetailsId()+" and  `Country_Id`="+shiftsVo.getCountryId()).list();
			for(Object object : tempList){
				Object[] obj = (Object[]) object;
				objects.add(new SimpleObject((Integer)obj[0],obj[1]+""));
			}
			
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return objects;
	}

	@Override
	public ShiftsVo getShiftRecordByShiftId(Integer shiftId) {
		// TODO Auto-generated method stub
		ShiftsVo vo = new ShiftsVo();
		try{			
			List tempList = sessionFactory.getCurrentSession().createSQLQuery("SELECT `Shift_id`, Customer_Id, `Company_id`, `Country_Id`, `Transaction_Date`, `Is_Active`, `Week_Start_Day`, `Week_End_Day`, Time_Zone, `Time_Set_Format`, `Currency_Id`,Default_Pattern_Type  FROM `shifts`   WHERE  Shift_id ="+shiftId).list();
			for(Object object : tempList){
				Object[] obj = (Object[]) object;
				 vo.setShiftId((Integer)obj[0]);
				 vo.setCustomerDetailsId((Integer)obj[1]);
				 vo.setCompanyDetailsId((Integer)obj[2]);	        	
	        	 vo.setCountryId((Integer)obj[3]);
	        	 vo.setTransactionDate((Date)obj[4]);
	        	 vo.setIsActive(obj[5]+"");
	        	 vo.setWeekStartDay((Integer)obj[6]);
	        	 vo.setWeekEndDay((Integer)obj[7]);
	        	 vo.setTimeZone(obj[8]+"");
	        	 vo.setTimeSetFormat(obj[9]+"");			
	        	 vo.setCurrencyId((Integer)obj[10]);
	        	 vo.setDefaultPatternType(obj[11]+"");
			}
			
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return vo;
	}
	
}
