package com.Ntranga.CLMS.Dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.vo.DepartmentwiseWorkerAllocationDetailsVo;
import com.Ntranga.CLMS.vo.DepartmentwiseWorkerAllocationRequirementDetailsVo;
import com.Ntranga.CLMS.vo.WorkerDetailsVo;
import com.Ntranga.CLMS.vo.WorkerIdentificationProofVo;
import com.Ntranga.core.DateHelper;
import com.Ntranga.core.CLMS.entities.CompanyDetails;
import com.Ntranga.core.CLMS.entities.CustomerDetails;
import com.Ntranga.core.CLMS.entities.DepartmentDetails;
import com.Ntranga.core.CLMS.entities.DepartmentwiseWorkerAllocationDetails;
import com.Ntranga.core.CLMS.entities.DepartmentwiseWorkerAllocationDetailsInfo;
import com.Ntranga.core.CLMS.entities.DepartmentwiseWorkerAllocationRequirementDetails;
import com.Ntranga.core.CLMS.entities.LocationDetails;
import com.Ntranga.core.CLMS.entities.PlantDetails;
import com.Ntranga.core.CLMS.entities.SectionDetails;
import com.Ntranga.core.CLMS.entities.WorkAreaDetails;
import com.Ntranga.core.CLMS.entities.WorkerAddress;
import com.Ntranga.core.CLMS.entities.WorkerDetails;
import com.Ntranga.core.CLMS.entities.WorkerIdentificationProof;

import common.Logger;

@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
@Transactional
@Repository("departmentWiseWorkerAllocationDao")
public class DepartmentWiseWorkerAllocationDaoImpl implements DepartmentWiseWorkerAllocationDao {

	private static Logger log = Logger.getLogger(DepartmentWiseWorkerAllocationDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	

	public List<DepartmentwiseWorkerAllocationDetailsVo> DepartmentWiseWorkerAllocationGridDetails(String customerId,
			String companyId, String countryId, String departmentId, String locationId, String plannedOrAdhoc,
			String fromDate, String toDate) {
		System.out.println(fromDate+"::::"+toDate);
		Session session = sessionFactory.getCurrentSession();
		List<DepartmentwiseWorkerAllocationDetailsVo> workerAllocationDetailsList = new ArrayList<DepartmentwiseWorkerAllocationDetailsVo>();
		try {
			String q = " SELECT dwd.`Transaction_Id`,dwdi.`Transaction_Date`,dwdi.`From_Date`,dwdi.`To_Date`,ddi.department_name,ldi.location_name,dwdi.Worker_Allocation_Id,dwdi.Worker_Allocation_Info_Id,dwdi.Planned_Or_Adhoc,dwdi.request_status  "
					+ " FROM departmentwise_worker_allocation_details dwd "
					+ " INNER JOIN `departmentwise_worker_allocation_details_info` dwdi  "
					+ " ON(dwdi.`Worker_Allocation_Id` = dwd.`Worker_Allocation_Id`) "
					+ " INNER JOIN department_details_info ddi ON (ddi.department_id = dwdi.department_id) "
					+ " INNER JOIN location_details_info ldi ON(ldi.location_id = dwdi.location_id) "

					+ " WHERE  "
					+ " CONCAT(DATE_FORMAT(ddi.transaction_date, '%Y%m%d'), LPAD(ddi.department_Sequence_Id, 2, '0')) =   "
					+ "  (  "
					+ "  SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.department_Sequence_Id, 2, '0')))  "
					+ " FROM department_details_info vdi1  " + " WHERE  ddi.department_id = vdi1.department_id "
					+ "   AND vdi1.transaction_date <= CURRENT_DATE()    " + "  )  " + "  AND "
					+ "  CONCAT(DATE_FORMAT(ldi.transaction_date, '%Y%m%d'), LPAD(ldi.location_Sequence_Id, 2, '0')) =   "
					+ "  (  "
					+ "  SELECT MAX(CONCAT(DATE_FORMAT(vdi1.transaction_date, '%Y%m%d'), LPAD(vdi1.location_Sequence_Id, 2, '0'))) "
					+ "  FROM location_details_info vdi1  " + "  WHERE  ldi.location_id = vdi1.location_id "
					+ "   AND vdi1.transaction_date <= CURRENT_DATE()    " + "  ) ";

			if (customerId != null && !customerId.isEmpty()) {
				q += " and dwdi.Customer_Id = '" + customerId + "'";
			}
			if (companyId != null && !companyId.isEmpty()) {
				q += " and dwdi.Company_Id = '" + companyId + "' ";
			}
			if (countryId != null && !countryId.isEmpty()) {
				q += " and dwdi.country_id = '" + countryId + "'";
			}

			if (departmentId != null && !departmentId.isEmpty()) {
				q += " and dwdi.department_Id = '" + departmentId + "'";
			}
			if (locationId != null && !locationId.isEmpty()) {
				q += " and dwdi.location_id = '" + locationId + "'";
			}
			if (plannedOrAdhoc != null && !plannedOrAdhoc.isEmpty()) {
				q += " and dwdi.Planned_Or_Adhoc ='" + plannedOrAdhoc + "'";
			}

			if (fromDate != null && fromDate != "" ) {
				q += " and dwdi.from_date = '" + fromDate + "' ";
			}

			if (toDate != null && toDate != "") {
				q += " and dwdi.to_date = '" + toDate + "' ";
			}

			

			List tempList = session.createSQLQuery(q).list();

			for (Object o : tempList) {
				Object[] obj = (Object[]) o;
				DepartmentwiseWorkerAllocationDetailsVo workerVo = new DepartmentwiseWorkerAllocationDetailsVo();
				workerVo.setTransactionId((Integer) obj[0]);
				workerVo.setTransactionDate((Date) obj[1]);
				workerVo.setFromDate((Date) obj[2]);
				workerVo.setToDate((Date) obj[3]);
				workerVo.setDepartmentName(obj[4] + "");
				workerVo.setLocationName(obj[5] + "");
				workerVo.setWorkerAllocationId((Integer) obj[6]);
				workerVo.setWorkerAllocationInfoId((Integer) obj[7]);
				if(obj[8] != null)
				workerVo.setPlannedOrAdhoc(obj[8] + "");
				workerVo.setRequestStatus(obj[9] + "");
				workerAllocationDetailsList.add(workerVo);
			}
		} catch (Exception e) {
			log.error("Error Occured ", e);

		}

		return workerAllocationDetailsList;
	}

	public Integer saveOrUpdateWorkerAllocationDetails(DepartmentwiseWorkerAllocationDetailsVo workerVo) {
		Session session = sessionFactory.getCurrentSession();
		DepartmentwiseWorkerAllocationDetails workerDetails = new DepartmentwiseWorkerAllocationDetails();
		Integer workerAlloationId = 0;
		Integer workerAlloationInfoId = 0;
		String schema = null;
		BigInteger transactionId = null;
		try {

			if (workerVo != null
					&& (workerVo.getWorkerAllocationId() == null || workerVo.getWorkerAllocationId() == 0)) {
				transactionId  = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery("SELECT COALESCE(MAX(Transaction_Id),0) FROM departmentwise_worker_allocation_details dept WHERE  dept.customer_Id = "+workerVo.getCustomerId() +" AND dept.company_Id = '"+workerVo.getCompanyId()+"' ").list().get(0);
				if(transactionId != null && transactionId.intValue() >0 ){
					workerDetails.setTransactionId(transactionId.intValue()+1);
				}else{
					workerDetails.setTransactionId(1);
				}
				workerDetails.setCustomerDetails(new CustomerDetails(workerVo.getCustomerId()));
				workerDetails.setCompanyDetails(new CompanyDetails(workerVo.getCompanyId()));
				
				workerDetails.setCreatedBy(workerVo.getCreatedBy());
				workerDetails.setCreatedDate(new Date());
				workerDetails.setModifiedBy(workerVo.getModifiedBy());
				workerDetails.setModifiedDate(new Date());
				workerAlloationId = (Integer) session.save(workerDetails);

			} else if (workerVo.getWorkerAllocationInfoId() > 0) {
				workerDetails = (DepartmentwiseWorkerAllocationDetails) session
						.load(DepartmentwiseWorkerAllocationDetails.class, workerVo.getWorkerAllocationId());
				workerDetails.setCustomerDetails(new CustomerDetails(workerVo.getCustomerId()));
				workerDetails.setCompanyDetails(new CompanyDetails(workerVo.getCompanyId()));
				workerDetails.setTransactionId(workerVo.getTransactionId());
				workerDetails.setModifiedBy(workerVo.getModifiedBy());
				workerDetails.setModifiedDate(new Date());
				session.update(workerDetails);
				workerAlloationId = workerDetails.getWorkerAllocationId();
			}

			DepartmentwiseWorkerAllocationDetailsInfo detailsInfo = new DepartmentwiseWorkerAllocationDetailsInfo();

			detailsInfo.setCustomerDetails(new CustomerDetails(workerVo.getCustomerId()));
			detailsInfo.setCompanyDetails(new CompanyDetails(workerVo.getCompanyId()));
			detailsInfo.setDepartmentwiseWorkerAllocationDetails(workerDetails);
			detailsInfo.setCountryId(workerVo.getCountryId());
			detailsInfo.setDepartmentDetails(new DepartmentDetails(workerVo.getDepartmentId()));
			detailsInfo.setLocationDetails(new LocationDetails(workerVo.getLocationId()));
			detailsInfo.setPlannedOrAdhoc(workerVo.getPlannedOrAdhoc());
			detailsInfo.setRequestStatus(workerVo.getRequestStatus());
			detailsInfo.setFromDate(workerVo.getFromDate());
			detailsInfo.setToDate(workerVo.getToDate());
			detailsInfo.setTransactionDate(workerVo.getTransactionDate());
			detailsInfo.setComments(workerVo.getComments());
			if(workerVo.getPlantId() != null && workerVo.getPlantId().toString() != "" && workerVo.getPlantId() > 0){
			detailsInfo.setPlantDetails(new PlantDetails(workerVo.getPlantId()));
			}
			if(workerVo.getSectionId() != null && workerVo.getSectionId().toString() != "" && workerVo.getSectionId() > 0){
			detailsInfo.setSectionDetails(new SectionDetails(workerVo.getSectionId()));
			}
			if(workerVo.getWorkAreaId() != null && workerVo.getWorkAreaId().toString() != "" && workerVo.getWorkAreaId() > 0){
			detailsInfo.setWorkAreaDetails(new WorkAreaDetails(workerVo.getWorkAreaId()));
			}

			if (workerVo != null &&  workerVo.getWorkerAllocationInfoId()!= null && workerVo.getWorkerAllocationInfoId() > 0) {
				detailsInfo.setWorkerAllocationInfoId(workerVo.getWorkerAllocationInfoId());
				detailsInfo.setCreatedBy(workerVo.getCreatedBy());
				detailsInfo.setCreatedDate(workerVo.getCreatedDate());
				detailsInfo.setModifiedBy(workerVo.getModifiedBy());
				detailsInfo.setModifiedDate(new Date());
				session.update(detailsInfo);
				workerAlloationInfoId = workerVo.getWorkerAllocationInfoId();
			} else {
				detailsInfo.setCreatedBy(workerVo.getCreatedBy());
				detailsInfo.setCreatedDate(new Date());
				detailsInfo.setModifiedBy(workerVo.getModifiedBy());
				detailsInfo.setModifiedDate(new Date());
				workerAlloationInfoId = (Integer) session.save(detailsInfo);

			}

			DepartmentwiseWorkerAllocationRequirementDetails requirementDetails = new DepartmentwiseWorkerAllocationRequirementDetails();
			if (workerVo.getWorkerAllocationRequirementList() != null
					&& workerVo.getWorkerAllocationRequirementList().size() > 0) {

				Query q = session.createSQLQuery(
						"delete from departmentwise_worker_allocation_requirement_details where Worker_Allocation_Id = '"
								+ workerAlloationId + "'");
				q.executeUpdate();

				for (DepartmentwiseWorkerAllocationRequirementDetailsVo requirement : workerVo
						.getWorkerAllocationRequirementList()) {
					requirementDetails = new DepartmentwiseWorkerAllocationRequirementDetails();
					requirementDetails.setCustomerDetails(new CustomerDetails(workerVo.getCustomerId()));
					requirementDetails.setCompanyDetails(new CompanyDetails(workerVo.getCompanyId()));
					requirementDetails.setDepartmentwiseWorkerAllocationDetails(workerDetails);
					requirementDetails.setWorkSkill(requirement.getWorkSkill());
					requirementDetails.setFromDate(DateHelper.convertStringToDate(requirement.getFromDate(), "dd/MM/yyyy"));
					requirementDetails.setToDate(DateHelper.convertStringToDate(requirement.getToDate(), "dd/MM/yyyy"));
					requirementDetails.setNoOfWorkers(requirement.getNoOfWorkers());
					requirementDetails.setRateOrDay(requirement.getRateOrDay());
					requirementDetails.setCreatedBy(workerVo.getCreatedBy());
					requirementDetails.setCreatedDate(new Date());
					requirementDetails.setModifiedBy(workerVo.getModifiedBy());
					requirementDetails.setModifiedDate(new Date());
					session.save(requirementDetails);
				}
			}

		} catch (Exception e) {
			log.error("Error Occured ", e);
		}

		return workerAlloationId;
	}

	public List<DepartmentwiseWorkerAllocationDetailsVo> WorkerAllocationDetailsId(Integer workerAllocationId) {

		List<DepartmentwiseWorkerAllocationDetailsVo> workerAllocation = new ArrayList<DepartmentwiseWorkerAllocationDetailsVo>();
		Session session = sessionFactory.getCurrentSession();
		try {

			String q = " SELECT dwd.Transaction_Id,dwd.customer_id,dwd.company_id,dwdi.country_Id, "
					+ " dwdi.Transaction_Date,dwdi.From_Date,dwdi.To_Date, "
					+ " dwdi.Worker_Allocation_Id,dwdi.Worker_Allocation_Info_Id, "
					+ " dwdi.department_id,dwdi.location_id,dwdi.Planned_Or_Adhoc,dwdi.Plant_Id,dwdi.Section_Id,"
					+ " dwdi.Work_Area_Id, " + " dwdi.Created_By,dwdi.Created_Date,dwdi.Modified_By,dwdi.Modified_Date,dwdi.Request_Status "
					+ " FROM departmentwise_worker_allocation_details dwd "
					+ " INNER JOIN departmentwise_worker_allocation_details_info dwdi  "
					+ " ON(dwdi.Worker_Allocation_Id = dwd.Worker_Allocation_Id) "
					+ " WHERE dwdi.Worker_Allocation_Id = " + workerAllocationId;

			List tempList = session.createSQLQuery(q).list();
			for (Object o : tempList) {
				Object[] obj = (Object[]) o;
				DepartmentwiseWorkerAllocationDetailsVo detailsVo = new DepartmentwiseWorkerAllocationDetailsVo();
				detailsVo.setTransactionId((Integer) obj[0]);
				detailsVo.setCustomerId((Integer) obj[1]);
				detailsVo.setCompanyId((Integer) obj[2]);
				detailsVo.setCountryId((Integer) obj[3]);
				detailsVo.setTransactionDate((Date) obj[4]);
				detailsVo.setFromDate((Date) obj[5]);
				detailsVo.setToDate((Date) obj[6]);
				detailsVo.setWorkerAllocationId((Integer) obj[7]);
				detailsVo.setWorkerAllocationInfoId((Integer) obj[8]);
				detailsVo.setDepartmentId((Integer) obj[9]);
				detailsVo.setLocationId((Integer) obj[10]);
				detailsVo.setPlannedOrAdhoc(obj[11] + "");
				detailsVo.setPlantId((Integer) obj[12]);
				detailsVo.setSectionId((Integer) obj[13]);
				detailsVo.setWorkAreaId((Integer) obj[14]);
				detailsVo.setCreatedBy((Integer) obj[15]);
				detailsVo.setCreatedDate((Date) obj[16]);
				detailsVo.setModifiedBy((Integer) obj[17]);
				detailsVo.setModifiedDate((Date) obj[18]);
				detailsVo.setRequestStatus(obj[19] + "");
				String q1 = " SELECT Work_Skill, From_Date, To_Date, No_Of_Workers, Rate_Or_Day FROM departmentwise_worker_allocation_requirement_details where Worker_Allocation_Id = "
						+ workerAllocationId;
				List tempList1 = session.createSQLQuery(q1).list();
				List<DepartmentwiseWorkerAllocationRequirementDetailsVo> requirementList = new ArrayList();
				for (Object o1 : tempList1) {
					Object[] obj1 = (Object[]) o1;
					DepartmentwiseWorkerAllocationRequirementDetailsVo reqVo = new DepartmentwiseWorkerAllocationRequirementDetailsVo();
					reqVo.setWorkSkill(obj1[0] + "");
					reqVo.setFromDate(DateHelper.convertDateToString((Date)obj1[1], "dd/MM/yyyy"));
					reqVo.setToDate(DateHelper.convertDateToString((Date)obj1[2], "dd/MM/yyyy"));
					reqVo.setNoOfWorkers((Integer) obj1[3]);
					reqVo.setRateOrDay((Integer) obj1[4]);
					requirementList.add(reqVo);
				}
				detailsVo.setWorkerAllocationRequirementList(requirementList);
				workerAllocation.add(detailsVo);
			}

		} catch (Exception e) {
			log.error("Error Occured ", e);

		}

		return workerAllocation;
	}

}
