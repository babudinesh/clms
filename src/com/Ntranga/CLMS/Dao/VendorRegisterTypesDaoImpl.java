package com.Ntranga.CLMS.Dao;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.VendorDamageOrLossDetailsVo;
import com.Ntranga.CLMS.vo.VendorDamageRecoveryVo;
import com.Ntranga.CLMS.vo.VendorFineRegisterVo;
import com.Ntranga.CLMS.vo.WorkerAdvanceAmountTakenVo;
import com.Ntranga.CLMS.vo.WorkerAdvanceInstallmentDetailsVo;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.MCurrency;
import com.Ntranga.core.CLMS.entities.VendorDamageOrLossDetails;
import com.Ntranga.core.CLMS.entities.VendorDamageRecoveryDetails;
import com.Ntranga.core.CLMS.entities.VendorDetails;
import com.Ntranga.core.CLMS.entities.VendorFinesRegister;
import com.Ntranga.core.CLMS.entities.WorkerAdvanceAmountTaken;
import com.Ntranga.core.CLMS.entities.WorkerAdvanceInstallmentDetails;
import com.Ntranga.core.CLMS.entities.WorkerDetails;
import common.Logger;


@Transactional
@Repository("vendorRegisterTypesDao")
@SuppressWarnings({"rawtypes","unused"})
public class VendorRegisterTypesDaoImpl implements VendorRegisterTypesDao  {

	private static Logger log = Logger.getLogger(VendorRegisterTypesDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;


	/*
	 * This method will be used to list all the active workers for given data for given register type
	 */
	@Override
	public List<WorkerAdvanceAmountTakenVo> getVendorRegisterTypes(String customerId,String companyId,String vendorId,Integer workerCode, String workerName,String registerType,Integer year,Integer month) {
		
		Session session = sessionFactory.getCurrentSession();
		List<WorkerAdvanceAmountTakenVo> workerAmountTakenVoList = new ArrayList<WorkerAdvanceAmountTakenVo>();
		System.out.println("customerId::"+customerId+"::companyId::"+companyId.isEmpty()+"::vendorId::"+vendorId);
		try {
			String q = " SELECT vw.customer_id,vw.company_id,vw.vendor_id,vw.workerId,vw.worker_code, "
					+ " CONCAT(RTRIM(vw.first_name), ' ',CASE WHEN (vw.middle_name IS NULL) THEN '' ELSE CONCAT(RTRIM(vw.middle_name),' ') END,  "
					+ "  vw.last_name) AS workerName,vv.vendor_name,vv.vendor_code ";
			
			
			if(registerType != null){
				if(registerType.equalsIgnoreCase("Advance")){
					q += " , wa.Worker_Advance_Amount_TakenId ";
				}else if(registerType.equalsIgnoreCase("Fines")){
					q += " , vf.Fines_Register_Id ";
				}else if(registerType.equalsIgnoreCase("DamageOrLoss")){
					q += " , vdd.Vendor_Damage_Id ";
				}
					
			}
			q += " FROM v_worker_details vw "
				+ " LEFT JOIN vendor_view vv ON(vv.vendor_id = vw.vendor_id) "
				+ " LEFT JOIN company_view cp ON(cp.company_id = vw.company_id) "
				+ " LEFT JOIN customer_View cv ON cv.Customer_Id = vw.Customer_Id ";
			
			if(registerType.equalsIgnoreCase("Advance")){
				q += " LEFT JOIN worker_advance_amount_taken wa ON (vw.workerId = wa.worker_id)  ";
			}else if(registerType.equalsIgnoreCase("Fines")){
				q += " LEFT JOIN Vendor_Fines_Register vf ON (vw.workerId = vf.worker_id)  ";
			}else if(registerType.equalsIgnoreCase("DamageOrLoss")){
				q += " LEFT JOIN vendor_damage_or_loss_details vdd ON vw.WorkerId = vdd.Worker_Id  ";
			}
			q += " WHERE  vw.customer_id = '" + customerId + "' "
					+ " AND  vw.company_id = '"+ companyId + "' " ;
			if(Integer.parseInt(vendorId) > 0){
					q += " AND  vw.vendor_id = '" + vendorId + "'   ";
			}
			
			q += " AND cv.Is_Active = 'Y' AND cp.Is_Active = 'Y' AND vv.Vendor_Status = 'Validated' AND vw.Is_Active = 'Y' ";
			
			if(workerCode != null && workerCode > 0  ){
					q += " AND (vw.worker_code = '" + workerCode + "' OR -1 = '" + workerCode + "')  ";
			}
			
			if(workerName != null && workerName != ""){
					q += " AND (CONCAT(RTRIM(vw.first_name), ' ',CASE WHEN (vw.middle_name IS NULL) THEN '' ELSE CONCAT(RTRIM(vw.middle_name),' ') END,  "
					+ "  vw.last_name) LIKE '%" + workerName + "%' OR  -1 = '" + workerName + "') " ;
			}
			
			if(year > 0 && month > 0) {
				q += " AND ";
				if(registerType.equalsIgnoreCase("Advance")){
					q += "  ( YEAR(wa.Amount_Taken_Date) ='" + year + "' AND MONTH(wa.Amount_Taken_Date) = '" + month + "') ";
				}else if(registerType.equalsIgnoreCase("Fines")){
					q += "  ( YEAR(vf.Date_Of_Offence) ='" + year + "' AND MONTH(vf.Date_Of_Offence) = '" + month + "') ";
				}else if(registerType.equalsIgnoreCase("DamageOrLoss")){
					q += "  ( Month(vdd.Date_Of_Damage) = '"+month+"' AND Year(vdd.Date_Of_Damage) = '"+year+"') ";
				}
			}

			q = q + "GROUP BY vw.workerId ORDER BY vv.Vendor_Name ";		
			
			System.out.println(q);
				
			 List tempList = session.createSQLQuery(q).list();			
			for(Object o : tempList){
				Object[] obj = (Object[]) o;
				WorkerAdvanceAmountTakenVo advanceAmountTakenVo = new WorkerAdvanceAmountTakenVo();
				
				advanceAmountTakenVo.setCustomerId((Integer)obj[0]);				
				advanceAmountTakenVo.setCompanyId((Integer)obj[1]);
				advanceAmountTakenVo.setVendorId((Integer)obj[2]);
				advanceAmountTakenVo.setWorkerId((Integer)obj[3]);
				advanceAmountTakenVo.setWorkerCode(obj[4]+"");
				advanceAmountTakenVo.setWorkerName(obj[5]+"");
				advanceAmountTakenVo.setVendorName(obj[6]+"");
				advanceAmountTakenVo.setVendorCode(obj[7]+"");
				advanceAmountTakenVo.setRegisterType(registerType);
				if(registerType.equalsIgnoreCase("Advance")){
					advanceAmountTakenVo.setWorkerAdvanceAmountTakenId((Integer)obj[8]);
				}else if(registerType.equalsIgnoreCase("Fines")){
					advanceAmountTakenVo.setVendorDamageId((Integer)obj[8]);
				}else if(registerType.equalsIgnoreCase("DamageOrLoss")){
					advanceAmountTakenVo.setVendorDamageId((Integer)obj[8]);
				}
				
				workerAmountTakenVoList.add(advanceAmountTakenVo);
			}						
		} catch (Exception e) {
			log.error("Error Occured ",e);

		}		
		
		return workerAmountTakenVoList;
	}
	
	
	/* vendor register types(Register of advance)*/
	
	@Override
	public List<WorkerAdvanceAmountTakenVo> getVendorDetailsList(Integer workerId) {
		Session session = sessionFactory.getCurrentSession();
		
		List<WorkerAdvanceAmountTakenVo> workerAmountTakenVoList = new ArrayList<WorkerAdvanceAmountTakenVo>();
		
		try {
			String q = " SELECT vw.customer_id,vw.company_id,vw.vendor_id,vw.workerId,vw.worker_code, "
					+ " CONCAT(RTRIM(vw.first_name), ' ',CASE WHEN (vw.middle_name IS NULL) THEN '' ELSE CONCAT(RTRIM(vw.middle_name),' ') END,  "
					+ "  vw.last_name) AS workerName,vv.vendor_name,vv.vendor_code, "
					+ " wa.Worker_Advance_Amount_TakenId,  wa.Advance_Amount_Taken,DATE_FORMAT(wa.Amount_Taken_Date,'%d/%m/%Y'), "
					+ " wa.No_Of_Installments_Allowed,wa.Purpose,wa.Remarks,wa.Created_By,  "
					+ "  wa.Created_date,wa.Modified_By,wa.Modified_Date, mc.currency, mc.currency_id "
					+ " FROM v_worker_details vw "
					+ " LEFT JOIN vendor_view vv ON(vv.vendor_id = vw.vendor_id) "
					+ " LEFT JOIN company_view cp ON(cp.company_id = vw.company_id) "
					+ " LEFT JOIN customer_View cv ON cv.Customer_Id = vw.Customer_Id "
					+ " LEFT JOIN company_View cpv ON cpv.Customer_Id = vw.Customer_Id AND cpv.Company_Id = vw.Company_Id "
					+ " LEFT JOIN worker_advance_amount_taken wa ON vw.workerId = wa.worker_id "
					+ " LEFT JOIN m_currency mc ON (wa.Currency = mc.Currency_Id) "
					+ " WHERE (vw.WorkerId = '" + workerId + "') "
						+ " order by wa.Amount_Taken_Date ";								 
				
			 List tempList = session.createSQLQuery(q).list();	
			 
			 if(tempList != null && tempList.size() > 0 ){
				 for(Object o : tempList){
					 Object[] obj = (Object[]) o;
					 WorkerAdvanceAmountTakenVo advanceAmountTakenVo = new WorkerAdvanceAmountTakenVo();
				
				
					advanceAmountTakenVo.setCustomerId((Integer)obj[0]);				
					advanceAmountTakenVo.setCompanyId((Integer)obj[1]);
					advanceAmountTakenVo.setVendorId((Integer)obj[2]);
					advanceAmountTakenVo.setWorkerId((Integer)obj[3]);
					advanceAmountTakenVo.setWorkerCode(obj[4]+"");
					advanceAmountTakenVo.setWorkerName(obj[5]+"");
					advanceAmountTakenVo.setVendorName(obj[6]+"");
					advanceAmountTakenVo.setVendorCode(obj[7]+"");
					advanceAmountTakenVo.setWorkerAdvanceAmountTakenId((Integer)obj[8]);
					if(obj[9] != null){
						advanceAmountTakenVo.setAdvanceAmountTaken(obj[9]+"");
					}if(obj[10] != null){
						advanceAmountTakenVo.setAmountTakenDate((String)obj[10]);
					}
					if(obj[11] != null && (Integer)obj[11] > 0){
						advanceAmountTakenVo.setNoOfInstallmentsAllowed((Integer)obj[11]);
					}if(obj[12] != null){
						advanceAmountTakenVo.setPurpose(obj[12]+"");
					}
					/*if(obj[13] != null){
							advanceAmountTakenVo.setCurrency(obj[13]+"");	
					}*/
					/*if(obj[14] != null){
						advanceAmountTakenVo.setRegisterType(obj[14]+"");
					}*/if(obj[13] != null){
						advanceAmountTakenVo.setRemarks(obj[13]+"");
					}if(obj[14] != null){
						advanceAmountTakenVo.setCreatedBy((Integer)obj[14]);
					}if(obj[15] != null){
						advanceAmountTakenVo.setCreatedDate((Date)obj[15]);
					}if(obj[16] != null){
						advanceAmountTakenVo.setModifiedBy((Integer)obj[16]);
					}if(obj[17] != null){
						advanceAmountTakenVo.setModifiedDate((Date)obj[17]);
					}if(obj[18] != null){
						advanceAmountTakenVo.setCurrency((String)obj[18]);
					}if(obj[19] != null){
						advanceAmountTakenVo.setCurrencyId((Integer)obj[19]);
					}
					
					if(advanceAmountTakenVo.getWorkerAdvanceAmountTakenId() != null && advanceAmountTakenVo.getWorkerAdvanceAmountTakenId() > 0 ){
						String installmentsQuery = "SELECT Worker_Advance_Amount_TakenId, worker_Advance_Installment_Details_Id,  DATE_FORMAT(Repayment_Amount_Date,'%d/%m/%Y'), Repayment_Amount , Currency_Id "
								+ " FROM worker_advance_installment_details wid "
								+ " WHERE wid.Worker_Advance_Amount_TakenId = "+advanceAmountTakenVo.getWorkerAdvanceAmountTakenId();
						
						List<WorkerAdvanceInstallmentDetailsVo> installmentsList = new ArrayList<WorkerAdvanceInstallmentDetailsVo>();

						List list = session.createSQLQuery(installmentsQuery).list();
						if(list != null && list.size() > 0 ){
							 for(Object ob : list){
								 Object[] obj1 = (Object[]) ob;
								 WorkerAdvanceInstallmentDetailsVo installmentsVo = new WorkerAdvanceInstallmentDetailsVo();
								 
								 installmentsVo.setWorkerAdvanceAmountTakenId((Integer)obj1[0]);
								 installmentsVo.setWorkerAdvanceInstallmentDetailsId((Integer)obj1[1]);
								 installmentsVo.setRepaymentAmountDate((String)obj1[2]);
								 installmentsVo.setRepaymentAmount((String)obj1[3]);
								 installmentsVo.setCurrencyId((Integer)obj1[4]);
								 
								 installmentsList.add(installmentsVo);
							 }
						}else{
							// do nothing
						}
						advanceAmountTakenVo.setAdvanceInstallments(installmentsList);
					}else{
						// do nothing
					}
					System.out.println("::::asdfsadf");
					workerAmountTakenVoList.add(advanceAmountTakenVo);
				 }	
			 }else{
					// do nothing
				}
		} catch (Exception e) {
			log.error("Error Occured ",e);
		}		
		
		return workerAmountTakenVoList;
	}
	
	
	
	public Integer saveOrUpdateVendorRegisterTypes(WorkerAdvanceAmountTakenVo advanceAmountTakenVo) {
		Session session = sessionFactory.getCurrentSession();
		WorkerAdvanceAmountTaken advanceAmountTaken = new WorkerAdvanceAmountTaken(); 
		Integer advanceAmountTakenId = 0;
		Integer workerInfoId = 0;
		try {
			
			advanceAmountTaken.setCustomerDetails(new CustomerDetails(advanceAmountTakenVo.getCustomerId()));
			advanceAmountTaken.setCompanyDetails(new CompanyDetails(advanceAmountTakenVo.getCompanyId()));
			advanceAmountTaken.setVendorDetails(new VendorDetails(advanceAmountTakenVo.getVendorId()));			
			advanceAmountTaken.setWorkerDetails(new WorkerDetails(advanceAmountTakenVo.getWorkerId()));			
			advanceAmountTaken.setAdvanceAmountTaken(advanceAmountTakenVo.getAdvanceAmountTaken());
			advanceAmountTaken.setAmountTakenDate(DateHelper.convertStringToSQLDate(advanceAmountTakenVo.getAmountTakenDate(), "dd/mm/yyyy"));
			advanceAmountTaken.setCurrency(advanceAmountTakenVo.getCurrencyId());
			advanceAmountTaken.setPurpose(advanceAmountTakenVo.getPurpose());
			advanceAmountTaken.setRemarks(advanceAmountTakenVo.getRemarks());
			advanceAmountTaken.setNoOfInstallmentsAllowed(advanceAmountTakenVo.getNoOfInstallmentsAllowed());
			//advanceAmountTaken.setRegisterType(advanceAmountTakenVo.getRegisterType());
			advanceAmountTaken.setModifiedBy(advanceAmountTakenVo.getModifiedBy());
			advanceAmountTaken.setModifiedDate(new Date());
			//advanceAmountTaken.setIsActive("Y");
			if(advanceAmountTakenVo != null && (advanceAmountTakenVo.getWorkerAdvanceAmountTakenId() == null || advanceAmountTakenVo.getWorkerAdvanceAmountTakenId() == 0)){
					advanceAmountTaken.setCreatedBy(advanceAmountTakenVo.getCreatedBy());
					advanceAmountTaken.setCreatedDate(new Date());			
					advanceAmountTakenId = (Integer) session.save(advanceAmountTaken);
			}else if(advanceAmountTakenVo.getWorkerAdvanceAmountTakenId() > 0){
				advanceAmountTaken.setWorkerAdvanceAmountTakenId(advanceAmountTakenVo.getWorkerAdvanceAmountTakenId());
				advanceAmountTakenId = advanceAmountTakenVo.getWorkerAdvanceAmountTakenId();
				advanceAmountTaken.setCreatedBy(advanceAmountTakenVo.getCreatedBy());
				advanceAmountTaken.setCreatedDate(advanceAmountTakenVo.getCreatedDate());			
				session.update(advanceAmountTaken);
			}
			
			
			
			
			if(advanceAmountTakenVo.getAdvanceInstallments()!= null && advanceAmountTakenVo.getAdvanceInstallments().size() > 0 ){						
				Query q = session.createSQLQuery("delete from worker_advance_installment_details where Worker_Advance_Amount_TakenId = '"+advanceAmountTakenVo.getWorkerAdvanceAmountTakenId()+"'");
				q.executeUpdate();
			 
			
				for(WorkerAdvanceInstallmentDetailsVo  advanceInstallmentsVo :advanceAmountTakenVo.getAdvanceInstallments() ){
					
					log.info("********  "+advanceAmountTakenVo.getAdvanceInstallments());
					WorkerAdvanceInstallmentDetails  advanceInstallments = new WorkerAdvanceInstallmentDetails();
					
					
					advanceInstallments.setCustomerDetails(new CustomerDetails(advanceAmountTakenVo.getCustomerId()));
					advanceInstallments.setCompanyDetails(new CompanyDetails(advanceAmountTakenVo.getCompanyId()));
					advanceInstallments.setRepaymentAmount(advanceInstallmentsVo.getRepaymentAmount());
					advanceInstallments.setRepaymentAmountDate(DateHelper.convertStringToSQLDate(advanceInstallmentsVo.getRepaymentAmountDate(), "dd/mm/yyyy"));	
					advanceInstallments.setWorkerAdvanceAmountTaken(new WorkerAdvanceAmountTaken(advanceAmountTakenId));
					advanceInstallments.setCurrencyDetails(new MCurrency(advanceInstallmentsVo.getCurrencyId()));
					advanceInstallments.setCreatedBy(advanceAmountTakenVo.getCreatedBy());
					advanceInstallments.setCreatedDate(new Date());
					advanceInstallments.setModifiedBy(advanceAmountTakenVo.getModifiedBy());
					advanceInstallments.setModifiedDate(new Date());
					session.save(advanceInstallments);
					
				}
			}
			
			workerInfoId = advanceAmountTakenVo.getWorkerId();
		} catch (Exception e) {
			workerInfoId = 0;
			log.error("Error Occured ",e);
		}
		
		return workerInfoId;
	}



	@Override
	public Integer saveVendorDamageDetails(VendorDamageOrLossDetailsVo paramDamage) {
		log.info("Entered in saveVendorDamageDetails  :: "+paramDamage);
	
		Session session = sessionFactory.getCurrentSession();
		VendorDamageOrLossDetails damage = new VendorDamageOrLossDetails(); 
		Integer vendorDamageId = 0;
		Integer workerId = 0;
		try {
			
			damage.setCustomerDetails(new CustomerDetails(paramDamage.getCustomerId()));
			damage.setCompanyDetails(new CompanyDetails(paramDamage.getCompanyId()));
			damage.setVendorDetails(new VendorDetails(paramDamage.getVendorId()));			
			damage.setWorkerDetails(new WorkerDetails(paramDamage.getWorkerId()));			
			damage.setDateOfDamage(DateHelper.convertStringToSQLDate(paramDamage.getDateOfDamage(),"dd/mm/yyyy"));
			damage.setAmountOfDeductionImposed(paramDamage.getAmountOfDeduction());
			damage.setParticularsOfDamage(paramDamage.getParticularsOfDamage());
			damage.setNameOfthePerson(paramDamage.getNameOfThePerson());
			damage.setNumberOfInstallmentsAllowed(paramDamage.getNumberOfInstallments());
			damage.setWhetherWorkmanShowedCause(paramDamage.getWhetherWorkmanShowedCause());
			damage.setRemarks(paramDamage.getRemarks());
			damage.setDescription(paramDamage.getDescription());
			damage.setCurrency(new MCurrency(paramDamage.getCurrencyId()));
			damage.setModifiedBy(paramDamage.getModifiedBy());
			damage.setModifiedDate(new Date());

			if(paramDamage != null && (paramDamage.getVendorDamageId() == null || paramDamage.getVendorDamageId() == 0)){
					damage.setCreatedBy(paramDamage.getCreatedBy());
					damage.setCreatedDate(new Date());			
					vendorDamageId = (Integer) session.save(damage);
			}else if(paramDamage.getVendorDamageId() > 0){
				damage = (VendorDamageOrLossDetails) sessionFactory.getCurrentSession().load(VendorDamageOrLossDetails.class, paramDamage.getVendorDamageId());
				vendorDamageId = paramDamage.getVendorDamageId();
				damage.setCreatedBy(paramDamage.getCreatedBy());
				damage.setCreatedDate(new Date());			
				session.update(damage);
			}
			
			
			session.flush();
			
			if(paramDamage.getRecoveryList()!= null && paramDamage.getRecoveryList().size() > 0 ){						
				Query q = session.createSQLQuery("DELETE FROM vendor_damage_recovery_details WHERE Vendor_Damage_Id = "+paramDamage.getVendorDamageId());
				q.executeUpdate();
			 
			
				for(VendorDamageRecoveryVo  recoveryVo :paramDamage.getRecoveryList() ){
					
					log.info("Recovery Details ::  "+recoveryVo);
					VendorDamageRecoveryDetails  recoveryDetails = new VendorDamageRecoveryDetails();
					
					recoveryDetails.setCustomerDetails(new CustomerDetails(paramDamage.getCustomerId()));
					recoveryDetails.setCompanyDetails(new CompanyDetails(paramDamage.getCompanyId()));
					recoveryDetails.setVendorDetails(new VendorDetails(paramDamage.getVendorId()));			
					recoveryDetails.setWorkerDetails(new WorkerDetails(paramDamage.getWorkerId()));	
					recoveryDetails.setRecoveredAmount(recoveryVo.getRecoveredAmount());
					recoveryDetails.setRecoveredDate(DateHelper.convertStringToSQLDate(recoveryVo.getRecoveredDate(), "dd/mm/yyyy"));	
					recoveryDetails.setVendorDamageDetails(new VendorDamageOrLossDetails(vendorDamageId));
					recoveryDetails.setCurrency(new MCurrency(recoveryVo.getCurrencyId()));
					recoveryDetails.setCreatedBy(paramDamage.getCreatedBy());
					recoveryDetails.setCreatedDate(new Date());
					recoveryDetails.setModifiedBy(paramDamage.getModifiedBy());
					recoveryDetails.setModifiedDate(new Date());
					session.save(recoveryDetails);
					
				}
			}
		}catch (Exception e) {
				workerId = 0;
				log.error("Exception  Occured ",e);
		}
		
		workerId = paramDamage.getWorkerId();
		log.info("Exiting from saveVendorDamageDetails  ::  Worker_Id = "+workerId);

		return workerId;
			
	}

	public  List<VendorDamageOrLossDetailsVo> getDamageDetailsById(Integer workerId){
		log.info("Entered in getDamageDetailsById  :::  worker_Id = "+workerId);
		Session session = null;
		
		List<VendorDamageOrLossDetailsVo> returnList = new ArrayList<VendorDamageOrLossDetailsVo>();
		VendorDamageOrLossDetailsVo damageVo = new VendorDamageOrLossDetailsVo();
		VendorDamageRecoveryVo recoveryVo= new VendorDamageRecoveryVo();
		
		
		try{
			 session = sessionFactory.getCurrentSession();
			String damageQuery = "SELECT wdi.Customer_Id, Customer_Name, wdi.Company_Id, Company_Name, wdi.Vendor_Id, Vendor_Name, wdi.WorkerId, "
					+ " replace((CONCAT(RTRIM(first_name), ' ',CASE WHEN (middle_name IS NULL) THEN '' ELSE CONCAT(RTRIM(middle_name),' ') END, last_name)),'  ',' ') AS WorkerName, "
					+ " DATE_FORMAT(Date_Of_Damage,'%d/%m/%Y'), Particulars_Of_Damage, Whether_Workman_Showed_Cause_Against_Deduction, Description_For_Cause, "
					+ " Name_Of_The_Person, Amount_Of_Deduction_Imposed, No_Of_Installments_Allowed, vendor_Damage_Id, "
					+ " Worker_Code, Vendor_Code, remarks, Currency_Id "
					+ " FROM v_worker_details wdi"
					+ " LEFT JOIN customer_View cv ON cv.Customer_Id = wdi.Customer_Id "
					+ " LEFT JOIN company_View cpv ON cpv.Customer_Id = wdi.Customer_Id AND cpv.Company_Id = wdi.Company_Id "
					+ " LEFT JOIN vendor_View vv   ON vv.Customer_Id = wdi.Customer_Id AND vv.Company_Id = wdi.Company_Id AND vv.Vendor_Id = wdi.Vendor_Id "
					+ " LEFT JOIN vendor_damage_or_loss_details vdd ON wdi.WorkerId = vdd.Worker_Id "
					+ " WHERE cv.Is_Active = 'Y' "
					+ " AND cpv.Is_Active = 'Y' "
					+ " AND vv.Vendor_Status = 'Validated' "
					+ " AND wdi.Is_Active = 'Y' " 
					+ " AND wdi .WorkerId= "+workerId
					+ " ORDER BY vdd.Date_Of_Damage";
			
			List damageList = session.createSQLQuery(damageQuery).list();
			
			if(damageList != null && damageList.size() > 0 ) {
				for(Object damage : damageList){
					Object[] obj = (Object[])damage;
					damageVo = new VendorDamageOrLossDetailsVo();
					
					damageVo.setCustomerId((Integer)obj[0]);
					//damageVo.setCustomerName((String)obj[1]);
					damageVo.setCompanyId((Integer)obj[2]);
					//damageVo.setCompanyName((String)obj[3]);
					damageVo.setVendorId((Integer)obj[4]);
					damageVo.setVendorName((String)obj[5]);
					damageVo.setWorkerId((Integer)obj[6]);
					damageVo.setWorkerName((String)obj[7]);
					damageVo.setDateOfDamage((String)obj[8]);
					damageVo.setParticularsOfDamage((String)obj[9]);
					damageVo.setWhetherWorkmanShowedCause((String)obj[10]);
					damageVo.setDescription((String)obj[11]);
					damageVo.setNameOfThePerson((String)obj[12]);
					damageVo.setAmountOfDeduction((String)obj[13]);
					damageVo.setNumberOfInstallments((Integer)obj[14]);
					damageVo.setVendorDamageId((Integer)obj[15]);
					damageVo.setVendorCode((String)obj[16]);
					damageVo.setWorkerCode((String)obj[17]);
					damageVo.setRemarks((String)obj[18]);
					damageVo.setCurrencyId((Integer)obj[19]);
					
					List<VendorDamageRecoveryVo> recoveryList = new ArrayList<VendorDamageRecoveryVo>();
					
					//recoveryList
					if(damageVo.getVendorDamageId() != null && damageVo.getVendorDamageId() > 0 ){
						String recoveryQuery = "SELECT vendor_Damage_Id, Damage_Recovery_Id,  DATE_FORMAT(Recovered_Date,'%d/%m/%Y'), Recovered_Amount, Currency_Id "
								+ " FROM vendor_damage_recovery_details vdr "
								+ " WHERE vdr.vendor_Damage_Id = "+damageVo.getVendorDamageId()
								+ " ORDER BY vdr.Recovered_Date";
						
						List list = session.createSQLQuery(recoveryQuery).list();

						for(Object recovery : list){
							Object[] obj1 = (Object[])recovery;
							recoveryVo = new VendorDamageRecoveryVo();	
						
							recoveryVo.setVendorDamageId((Integer)obj1[0]);
							recoveryVo.setDamageRecoveryId((Integer)obj1[1]);
							recoveryVo.setRecoveredDate((String)obj1[2]);
							recoveryVo.setRecoveredAmount((String)obj1[3]);
							recoveryVo.setCurrencyId((Integer)obj1[4]);
							
							recoveryList.add(recoveryVo);
							
						}//end for-loop for recovery
						damageVo.setRecoveryList(recoveryList);
					}
					
					returnList.add(damageVo);
				}//end for-loop for damage
			}
			
		}catch(Exception e){
			log.error("Exception Occured ::  ",e);
		}
		log.info("Exiting from getDamageDetailsById  :::  returnList = "+returnList.toString());
		return returnList;
	}


	@Override
	public Integer saveVendorFines(VendorFineRegisterVo paramFine) {
		log.info("Entered in saveVendorFines  :: "+paramFine);
		
		Session session = sessionFactory.getCurrentSession();
		VendorFinesRegister fine = new VendorFinesRegister(); 
		Integer finesRgisterId = 0;
		Integer workerId = 0;
		try {
			
			fine.setCustomerDetails(new CustomerDetails(paramFine.getCustomerId()));
			fine.setCompanyDetails(new CompanyDetails(paramFine.getCompanyId()));
			fine.setVendorDetails(new VendorDetails(paramFine.getVendorId()));			
			fine.setWorkerDetails(new WorkerDetails(paramFine.getWorkerId()));			
			fine.setDateOfOffence(DateHelper.convertStringToSQLDate(paramFine.getDateOfOffence(),"dd/mm/yyyy"));
			fine.setAmountOfFineImposed(paramFine.getAmountOfFineImposed());
			fine.setActForWhichFineIsImposed(paramFine.getActForWhichFineIsImposed());
			fine.setNameOfthePerson(paramFine.getNameOfThePerson());
			fine.setWhetherWorkmanShowedCause(paramFine.getWhetherWorkmanShowedCause());
			fine.setRemarks(paramFine.getRemarks());
			fine.setDateOnWhichFineRealised(DateHelper.convertStringToSQLDate(paramFine.getDateOnWhichFineRealised(),"dd/mm/yyyy"));
			fine.setCurrency(new MCurrency(paramFine.getCurrencyId()));
			fine.setDescription(paramFine.getDescription());
			
			fine.setModifiedBy(paramFine.getModifiedBy());
			fine.setModifiedDate(new Date());

			if(paramFine != null && (paramFine.getFinesRegisterId() == null || paramFine.getFinesRegisterId() == 0)){
					fine.setCreatedBy(paramFine.getCreatedBy());
					fine.setCreatedDate(new Date());			
					finesRgisterId = (Integer) session.save(fine);
			}else if(paramFine.getFinesRegisterId() > 0){
				fine = (VendorFinesRegister) sessionFactory.getCurrentSession().load(VendorFinesRegister.class, paramFine.getFinesRegisterId());
				finesRgisterId = paramFine.getFinesRegisterId();
				fine.setCreatedBy(paramFine.getCreatedBy());
				fine.setCreatedDate(new Date());			
				session.update(fine);
			}
			
		}catch (Exception e) {
				workerId = 0;
				log.error("Exception  Occured ",e);
		}
		
		workerId = paramFine.getWorkerId();
		log.info("Exiting from saveVendorFines  ::  Worker_Id = "+workerId);

		return workerId;
	}


	@Override
	public List<VendorFineRegisterVo> getVendorFinesById(Integer workerId) {
		log.info("Entered in getVendorFinesById  :::  worker_Id = "+workerId);
		Session session = null;
		
		List<VendorFineRegisterVo> returnList = new ArrayList<VendorFineRegisterVo>();
		VendorFineRegisterVo fineVo = new VendorFineRegisterVo();
		
		
		try{
			 session = sessionFactory.getCurrentSession();
			String fineQuery = "SELECT wdi.Customer_Id, Customer_Name, wdi.Company_Id, Company_Name, wdi.Vendor_Id, Vendor_Name, wdi.WorkerId, "
					+ " replace((CONCAT(RTRIM(first_name), ' ',CASE WHEN (middle_name IS NULL) THEN '' ELSE CONCAT(RTRIM(middle_name),' ') END, last_name)),'  ',' ') AS WorkerName, "
					+ " DATE_FORMAT(Date_Of_Offence,'%d/%m/%Y'), Act_For_Which_Fine_Is_Imposed, Whether_Workman_Showed_Cause_Against_Fine,"
					+ " DATE_FORMAT(Date_On_Which_Fine_Realised,'%d/%m/%Y'), Name_Of_The_Person, Amount_Of_Fine_Imposed, Fines_Register_Id, "
					+ " Worker_Code, Vendor_Code, remarks, Currency_Id , Description "
					+ " FROM v_worker_details wdi"
					+ " LEFT JOIN customer_View cv ON cv.Customer_Id = wdi.Customer_Id "
					+ " LEFT JOIN company_View cpv ON cpv.Customer_Id = wdi.Customer_Id AND cpv.Company_Id = wdi.Company_Id "
					+ " LEFT JOIN vendor_View vv   ON vv.Customer_Id = wdi.Customer_Id AND vv.Company_Id = wdi.Company_Id AND vv.Vendor_Id = wdi.Vendor_Id "
					+ " LEFT JOIN vendor_fines_register vdd ON wdi.WorkerId = vdd.Worker_Id "
					+ " WHERE cv.Is_Active = 'Y' "
					+ " AND cpv.Is_Active = 'Y' "
					+ " AND vv.Vendor_Status = 'Validated' "
					+ " AND wdi.Is_Active = 'Y' " 
					+ " AND wdi .WorkerId= "+workerId
					+ " ORDER BY vdd.Date_Of_Offence";
			
			List finesList = session.createSQLQuery(fineQuery).list();
			
			if(finesList != null && finesList.size() > 0 ) {
				for(Object fines : finesList){
					Object[] obj = (Object[])fines;
					fineVo = new VendorFineRegisterVo();
					
					fineVo.setCustomerId((Integer)obj[0]);
					//damageVo.setCustomerName((String)obj[1]);
					fineVo.setCompanyId((Integer)obj[2]);
					//damageVo.setCompanyName((String)obj[3]);
					fineVo.setVendorId((Integer)obj[4]);
					fineVo.setVendorName((String)obj[5]);
					fineVo.setWorkerId((Integer)obj[6]);
					fineVo.setWorkerName((String)obj[7]);
					fineVo.setDateOfOffence((String)obj[8]);
					fineVo.setActForWhichFineIsImposed((String)obj[9]);
					fineVo.setWhetherWorkmanShowedCause((String)obj[10]);
					fineVo.setDateOnWhichFineRealised((String)obj[11]);
					fineVo.setNameOfThePerson((String)obj[12]);
					fineVo.setAmountOfFineImposed((String)obj[13]);
					fineVo.setFinesRegisterId((Integer)obj[14]);
					fineVo.setVendorCode((String)obj[15]);
					fineVo.setWorkerCode((String)obj[16]);
					fineVo.setRemarks((String)obj[17]);
					fineVo.setCurrencyId((Integer)obj[18]);
					fineVo.setDescription((String)obj[19]);
					
					returnList.add(fineVo);
				}//end for-loop for damage
			}
			
		}catch(Exception e){
			log.error("Exception Occured ::  ",e);
		}
		log.info("Exiting from getVendorFinesById  :::  returnList = "+returnList.toString());
		return returnList;
	}
}
