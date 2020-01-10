package com.Ntranga.CLMS.Dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.DefineComplianceTypesVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.DefineComplianceTypes;
import com.Ntranga.core.CLMS.entities.LocationDetails;

import common.Logger;

@SuppressWarnings({"rawtypes","unused"})
@Transactional
@Repository("defineComplianceTypesDao")
public class DefineComplianceTypesDaoImpl implements DefineComplianceTypesDao  {

	private static Logger log = Logger.getLogger(DefineComplianceTypesDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	

	@Override
	public List<DefineComplianceTypesVo> getComplianceList(Integer customerId, Integer companyId,Integer locationId) {
		List<DefineComplianceTypesVo> complianceTypesVos = new ArrayList<DefineComplianceTypesVo>();
		try{
			List tempList = sessionFactory.getCurrentSession().createSQLQuery("SELECT 	`Define_Compliance_Type_Id`, `Company_Id`, `Customer_Id`, `Transaction_Date`, `Sequence_Id`, `Applicable_To`,`Compliance_Act`, `Compliance_Code`, `Country`, `Doccument_Description`, `Doccument_Name`, `Frequency`, `Is_Active`, Compliance_Unique_Id, Is_Mandatory,location_id FROM `define_compliance_types` parent WHERE `Transaction_Date` = (SELECT MAX(`Transaction_Date`) FROM `define_compliance_types` child WHERE child.`Transaction_Date` <= CURRENT_DATE() AND parent.`Customer_Id`= child.`Customer_Id` AND parent.`Company_Id`=child.`Company_Id`) AND `Sequence_Id` = (SELECT MAX(`Sequence_Id`) FROM `define_compliance_types` child WHERE child.`Transaction_Date` =  parent.`Transaction_Date` AND parent.`Customer_Id`= child.`Customer_Id` AND parent.`Company_Id`=child.`Company_Id` ) AND `Company_Id`="+companyId+" AND `Customer_Id`="+customerId+" and location_Id= "+locationId+" ORDER BY Compliance_Act asc ").list();
			for(Object o : tempList){
				Object[] obj = (Object[]) o;
				DefineComplianceTypesVo complianceTypesVo = new DefineComplianceTypesVo();
				complianceTypesVo.setDefineComplianceTypeId((Integer)obj[0]);
				complianceTypesVo.setComplianceCode(obj[7]+"");
				complianceTypesVo.setCountry((Integer)obj[8]);
				complianceTypesVo.setCustomerDetailsId((Integer)obj[2]);
				complianceTypesVo.setCompanyDetailsId((Integer)obj[1]);
				complianceTypesVo.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[3]));
				complianceTypesVo.setSequenceId((Integer)obj[4]);
				complianceTypesVo.setComplianceAct(obj[6]+"");
				complianceTypesVo.setDoccumentName(obj[10]+"");
				complianceTypesVo.setDoccumentDescription(obj[9]+"");
				complianceTypesVo.setIsActive(((String)obj[12]).equalsIgnoreCase("Y")? "Active" : "Inactive");
				complianceTypesVo.setApplicableTo(obj[5]+"");
				complianceTypesVo.setComplianceUniqueId((Integer)obj[13]);
				complianceTypesVo.setIsMandatory(obj[14] != null && ((String)obj[14]).equalsIgnoreCase("Y") ? true : false);
				complianceTypesVo.setFrequency(obj[11]+"");
				complianceTypesVo.setLocationId((Integer)obj[15]);
				complianceTypesVos.add(complianceTypesVo);
			}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}		
		return complianceTypesVos;
	}

	@Override
	public Integer saveCompliance(DefineComplianceTypesVo defineComplianceTypesVo) {
		Integer id = 0;
		BigInteger uniqueId = new BigInteger("0") ;
		try{		
			/*if(defineComplianceTypesVo != null && defineComplianceTypesVo.getDefineComplianceTypeId() != null && defineComplianceTypesVo.getDefineComplianceTypeId() > 0){				
				DefineComplianceTypes newComplianceTypes = (DefineComplianceTypes) sessionFactory.getCurrentSession().load(DefineComplianceTypes.class, defineComplianceTypesVo.getDefineComplianceTypeId());
				sessionFactory.getCurrentSession().createSQLQuery(" DELETE FROM `define_compliance_types`  WHERE Sequence_Id="+newComplianceTypes.getSequenceId()+"  AND`Transaction_Date` ='"+DateHelper.convertDateToSQLString(defineComplianceTypesVo.getTransactionDate())+"' AND  `Customer_Id`="+defineComplianceTypesVo.getCustomerDetailsId()+" AND `Company_Id` ="+defineComplianceTypesVo.getCompanyDetailsId()).executeUpdate();
				for(DefineComplianceTypesVo complianceTypesVo : defineComplianceTypesVo.getComplianceList()){
					DefineComplianceTypes complianceTypes = new DefineComplianceTypes();
					log.info(complianceTypesVo.getComplianceUniqueId());
					if(complianceTypesVo.getComplianceUniqueId() != null && complianceTypesVo.getComplianceUniqueId() > 0){
						complianceTypesVo.setComplianceUniqueId(complianceTypesVo.getComplianceUniqueId());
					}else{
						uniqueId = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Compliance_Unique_Id),0) FROM define_compliance_types define "
								+ "  WHERE  define.`Transaction_Date` = '"+DateHelper.convertDateToSQLString(defineComplianceTypesVo.getTransactionDate())+"' and define.Customer_Id='"+defineComplianceTypesVo.getCustomerDetailsId()+"' And define.Company_Id = '"+defineComplianceTypesVo.getCompanyDetailsId()+"'").list().get(0);
						log.info("Define Compliance Unique Id : "+uniqueId);
						if(uniqueId.intValue() > 0){
							complianceTypes.setComplianceUniqueId(uniqueId.intValue()+1);
						}else{
							complianceTypes.setComplianceUniqueId(1);
						}
					}
					
					complianceTypes.setComplianceCode(defineComplianceTypesVo.getComplianceCode());
					complianceTypes.setCountry(defineComplianceTypesVo.getCountry());
					complianceTypes.setCustomerDetails(new CustomerDetails(defineComplianceTypesVo.getCustomerDetailsId()) );
					complianceTypes.setCompanyDetails(new CompanyDetails(defineComplianceTypesVo.getCompanyDetailsId()));
					complianceTypes.setTransactionDate(defineComplianceTypesVo.getTransactionDate());
					complianceTypes.setSequenceId(newComplianceTypes.getSequenceId());
					complianceTypes.setComplianceAct(complianceTypesVo.getComplianceAct());
					complianceTypes.setDoccumentName(complianceTypesVo.getDoccumentName());
					complianceTypes.setDoccumentDescription(complianceTypesVo.getDoccumentDescription());
					complianceTypes.setIsActive(complianceTypesVo.getIsActive());
					complianceTypes.setApplicableTo(complianceTypesVo.getApplicableTo());
					complianceTypes.setFrequency(complianceTypesVo.getFrequency());
					complianceTypes.setCreatedDate(newComplianceTypes.getCreatedDate());
					complianceTypes.setModifiedDate(new Date());
					complianceTypes.setCreatedBy(newComplianceTypes.getCreatedBy());
					complianceTypes.setModifiedBy(defineComplianceTypesVo.getModifiedBy());
					id = (Integer)sessionFactory.getCurrentSession().save(complianceTypes);
				}
				
			}*/
			BigInteger seq = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(sequence_id),0) FROM `define_compliance_types` WHERE `Transaction_Date` ='"+DateHelper.convertDateToSQLString(defineComplianceTypesVo.getTransactionDate())+"' AND `Customer_Id`="+defineComplianceTypesVo.getCustomerDetailsId()+" AND `Company_Id` ="+defineComplianceTypesVo.getCompanyDetailsId()).list().get(0);			
			for(DefineComplianceTypesVo complianceTypesVo : defineComplianceTypesVo.getComplianceList()){
				DefineComplianceTypes complianceTypes = new DefineComplianceTypes();
				
				/*if(complianceTypesVo.getComplianceUniqueId() != null && complianceTypesVo.getComplianceUniqueId() > 0){
					complianceTypes.setComplianceUniqueId(complianceTypesVo.getComplianceUniqueId());
					log.info("Define Compliance Unique Id : "+complianceTypesVo.getComplianceUniqueId());
				}else{
					uniqueId = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Compliance_Unique_Id),0) FROM define_compliance_types define "
							+ "  WHERE  define.`Transaction_Date` = '"+DateHelper.convertDateToSQLString(defineComplianceTypesVo.getTransactionDate())+"' and define.Customer_Id='"+defineComplianceTypesVo.getCustomerDetailsId()+"' And define.Company_Id = '"+defineComplianceTypesVo.getCompanyDetailsId()+"'").list().get(0);
					log.info("Define Compliance Unique Id : "+uniqueId);
					if(uniqueId.intValue() > 0){
						complianceTypes.setComplianceUniqueId(uniqueId.intValue()+1);
					}else{
						complianceTypes.setComplianceUniqueId(1);
					}
				}*/
				complianceTypes.setComplianceUniqueId(complianceTypesVo.getComplianceUniqueId());
				complianceTypes.setComplianceCode(defineComplianceTypesVo.getComplianceCode());
				complianceTypes.setCountry(defineComplianceTypesVo.getCountry());
				complianceTypes.setCustomerDetails(new CustomerDetails(defineComplianceTypesVo.getCustomerDetailsId()) );
				complianceTypes.setCompanyDetails(new CompanyDetails(defineComplianceTypesVo.getCompanyDetailsId()));
				complianceTypes.setLocationDetails(new LocationDetails(defineComplianceTypesVo.getLocationId()));
				complianceTypes.setTransactionDate(defineComplianceTypesVo.getTransactionDate());
				complianceTypes.setSequenceId(seq.intValue()+1);
				complianceTypes.setComplianceAct(complianceTypesVo.getComplianceAct());
				complianceTypes.setDoccumentName(complianceTypesVo.getDoccumentName());
				complianceTypes.setDoccumentDescription(complianceTypesVo.getDoccumentDescription());
				complianceTypes.setIsActive(complianceTypesVo.getIsActive().equalsIgnoreCase("Active") ? "Y" : "N");
				complianceTypes.setApplicableTo(complianceTypesVo.getApplicableTo());
				complianceTypes.setFrequency(complianceTypesVo.getFrequency());
				complianceTypes.setIsMandatory((complianceTypesVo.getIsMandatory() == true) ? "Y":"N");
				complianceTypes.setCreatedDate(new Date());
				complianceTypes.setModifiedDate(new Date());
				complianceTypes.setCreatedBy(defineComplianceTypesVo.getCreatedBy());
				complianceTypes.setModifiedBy(defineComplianceTypesVo.getModifiedBy());
				
				id = (Integer)sessionFactory.getCurrentSession().save(complianceTypes);
			}
			
			sessionFactory.getCurrentSession().flush();
		}catch(Exception e){
			log.error("Error Occured ",e);
			id = 0;
		}
		
		return id;
	}

	@Override
	public List<SimpleObject> getTransactionDatesofHistory(Integer customerId, Integer companyId) {
		List<SimpleObject> simpleobjects = new ArrayList<SimpleObject>();
  		List<String> uniqueList = new ArrayList<String>();
		try{
			List tempList = sessionFactory.getCurrentSession().createSQLQuery("  SELECT `Define_Compliance_Type_Id` AS id ,CONCAT(DATE_FORMAT(Transaction_Date,'%d/%m/%Y'),' - ', `Sequence_Id`) AS cname  FROM `define_compliance_types` compliance WHERE compliance.Customer_Id ="+customerId+" AND compliance.Company_Id="+companyId+"  ORDER BY compliance.Transaction_Date, compliance.`Define_Compliance_Type_Id`").list();
			for(Object o : tempList){
				Object[] obj = (Object[]) o;
				if( !uniqueList.contains(obj[1]+"")){
					SimpleObject object = new SimpleObject();
					object.setId((Integer)obj[0]);
					object.setName(obj[1]+"");
					uniqueList.add(obj[1]+"");
					simpleobjects.add(object);
				}
			}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}		
		return simpleobjects;
	}

	@Override
	public List<DefineComplianceTypesVo> getComplianceRecordById(Integer defineComplianceTypeId) {
		
		List<DefineComplianceTypesVo> complianceTypesVos = new ArrayList<DefineComplianceTypesVo>();
		try{
			DefineComplianceTypes types = (DefineComplianceTypes)sessionFactory.getCurrentSession().load(DefineComplianceTypes.class, defineComplianceTypeId);
			List tempList = sessionFactory.getCurrentSession().createSQLQuery("SELECT 	`Define_Compliance_Type_Id`, `Company_Id`, `Customer_Id`, `Transaction_Date`, `Sequence_Id`, `Applicable_To`,`Compliance_Act`, `Compliance_Code`, `Country`, `Doccument_Description`, `Doccument_Name`, `Frequency`, `Is_Active`, Compliance_Unique_Id FROM `define_compliance_types` WHERE  `Company_Id`="+types.getCompanyDetails().getCompanyId()+" AND `Customer_Id`="+types.getCustomerDetails().getCustomerId()+" AND Transaction_Date='"+DateHelper.convertDateToSQLString(types.getTransactionDate())+"'  AND Sequence_Id="+types.getSequenceId()).list();
			for(Object o : tempList){
				Object[] obj = (Object[]) o;
				DefineComplianceTypesVo complianceTypesVo = new DefineComplianceTypesVo();
				complianceTypesVo.setDefineComplianceTypeId((Integer)obj[0]);
				complianceTypesVo.setComplianceCode(obj[7]+"");
				complianceTypesVo.setCountry((Integer)obj[8]);
				complianceTypesVo.setCustomerDetailsId((Integer)obj[2]);
				complianceTypesVo.setCompanyDetailsId((Integer)obj[1]);
				complianceTypesVo.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[3]));
				complianceTypesVo.setSequenceId((Integer)obj[4]);
				complianceTypesVo.setComplianceAct(obj[6]+"");
				complianceTypesVo.setDoccumentName(obj[10]+"");
				complianceTypesVo.setDoccumentDescription(obj[9]+"");
				complianceTypesVo.setIsActive(obj[12]+"");
				complianceTypesVo.setApplicableTo(obj[5]+"");
				complianceTypesVo.setFrequency(obj[11]+"");
				complianceTypesVo.setComplianceUniqueId((Integer)obj[13]);
				complianceTypesVos.add(complianceTypesVo);
			}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}		
		return complianceTypesVos;
	}
	
	@Override
	public List<DefineComplianceTypesVo> getComplianceListByApplicable(Integer customerId, Integer companyId, String applicableTo) {
		List<DefineComplianceTypesVo> complianceTypesVos = new ArrayList<DefineComplianceTypesVo>();
		try{
			String query="SELECT  `Company_Id`, `Customer_Id`, `Transaction_Date`, `Sequence_Id`, `Applicable_To`,`Compliance_Act`, `Compliance_Code`, `Country`, `Doccument_Description`, `Doccument_Name`, `Frequency`, `Is_Active`, Compliance_Unique_Id FROM `define_compliance_types` parent WHERE `Transaction_Date` = (SELECT MAX(`Transaction_Date`) FROM `define_compliance_types` child WHERE child.`Transaction_Date` <= CURRENT_DATE() AND parent.`Customer_Id`= child.`Customer_Id` AND parent.`Company_Id`=child.`Company_Id`) AND `Sequence_Id` = (SELECT MAX(`Sequence_Id`) FROM `define_compliance_types` child WHERE child.`Transaction_Date` =  parent.`Transaction_Date` AND parent.`Customer_Id`= child.`Customer_Id` AND parent.`Company_Id`=child.`Company_Id` ) AND `Company_Id`="+companyId+" AND `Customer_Id`="+customerId;
			if(applicableTo != null){
				query += " AND Applicable_To in ( '"+applicableTo+"' , 'Both' )";
			}
			
			query += " AND Is_Active = 'Y'  ORDER BY Doccument_Name asc";
			
			List tempList = sessionFactory.getCurrentSession().createSQLQuery(query).list();
			System.out.println(tempList);
			if(tempList != null && tempList.size() > 0){
				for(Object o : tempList){
					Object[] obj = (Object[]) o;
					DefineComplianceTypesVo complianceTypesVo = new DefineComplianceTypesVo();
		
					complianceTypesVo.setCompanyDetailsId((Integer)obj[0]);
					complianceTypesVo.setCustomerDetailsId((Integer)obj[1]);
					complianceTypesVo.setTransactionDate(DateHelper.convertSQLDateToDate((java.sql.Date)obj[2]));
					complianceTypesVo.setSequenceId((Integer)obj[3]);
					complianceTypesVo.setApplicableTo(obj[4]+"");
					complianceTypesVo.setComplianceAct(obj[5]+"");
					complianceTypesVo.setComplianceCode(obj[6]+"");
					complianceTypesVo.setCountry((Integer)obj[7]);
					complianceTypesVo.setDoccumentDescription(obj[8]+"");
					complianceTypesVo.setDoccumentName(obj[9]+"");
					complianceTypesVo.setFrequency(obj[10]+"");
					complianceTypesVo.setIsActive(obj[11]+"");
					complianceTypesVo.setComplianceUniqueId((Integer)obj[12]);

					complianceTypesVos.add(complianceTypesVo);
				}
			}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}		
		return complianceTypesVos;
	}
	
}
