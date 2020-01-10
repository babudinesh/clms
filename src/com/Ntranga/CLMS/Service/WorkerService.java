package com.Ntranga.CLMS.Service;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.Ntranga.CLMS.vo.LabourTimeVo;
import com.Ntranga.CLMS.vo.SearchCriteriaVo;
import com.Ntranga.CLMS.vo.ShiftsDefineVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WorkerDetailsVo;
import com.Ntranga.CLMS.vo.WorkerEducationAndEmploymentVo;
import com.Ntranga.CLMS.vo.WorkerNomineeDetailsVo;

public interface WorkerService {
	

	
/*=============================== Worker Details Start ====================================*/
	
	public List<WorkerDetailsVo> workerGridDetails(SearchCriteriaVo searchCriteriaVo);
	
	public Integer saveOrUpdateWorkerDetails(WorkerDetailsVo vo);
	
	public List<WorkerDetailsVo> getWorkerDetailsbyId(String workerInfoId);
	
	public List<SimpleObject> getTransactionDatesListForEditingWorkerDetails(Integer workerId);

	public Integer saveNomineeDetails(WorkerNomineeDetailsVo workerNomineeVo);
	
	public Integer deleteNomineeDetails(Integer workerNomineeId);
	
	public List<WorkerNomineeDetailsVo> getNomineeGridData(Integer customerId,Integer companyId,Integer workerId);

	public Integer saveEducationAndEmploymentDetails(WorkerEducationAndEmploymentVo educationAndEmployment);
	
	public WorkerEducationAndEmploymentVo getEducationAndEmploymentList(Integer customerId,Integer companyId,Integer workerId);
	
	public SimpleObject validateWorkerCode(String workerCode,Integer customerID,Integer companyId,Integer vendorId,Integer workerId);
	
	public Integer getWorkerInfoId(Integer workerId,Date transactionDate);

	
	/*=============================== Worker Details End ====================================*/
	
	
	
	/*=============================== Worker Verification Start ====================================*/
	public List<WorkerDetailsVo> workerVerificationDetailsByWorkerInfoId(String workerInfoId);

	public Integer saveVerificationStatus(String name, MultipartFile[] files, String fileNames);

	
	/*=============================== Worker Verification End ====================================*/
	public List<LabourTimeVo> viewWorkerTimeDetailsForEditing(WorkerDetailsVo detailsVo);

	public Integer saveModifiedLaborTimeDetails(WorkerDetailsVo detailsVo);
	
	public List<ShiftsDefineVo>  getAvailableShifts(ShiftsDefineVo shiftsDefineVo);
	
	public List<SimpleObject> getVendorNamesAsDropDown(String customerId,String companyId,String vendorId, String vendorStatus, String locationArrayId);
	
	public List<WorkerDetailsVo>  getWorkerChildScreensData(Integer workerId);
	
	public String getWorkerAddressByWorkerId(Integer workerId);

	public Integer getWorkerId(int asInt);
	
	public boolean validateWorkerAndSchedulingShifts(WorkerDetailsVo workervo);
	
	public boolean checkWorkerStatus(WorkerDetailsVo workervo);

	public SimpleObject getMaximumWorkerCode(WorkerDetailsVo detailsVo);

	public SimpleObject validateDuplicateWorker(WorkerDetailsVo workerVo);

	public Integer changeWorkerCode(WorkerDetailsVo workerVo);
	
}
