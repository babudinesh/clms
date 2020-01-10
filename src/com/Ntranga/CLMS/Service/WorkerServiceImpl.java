package com.Ntranga.CLMS.Service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Ntranga.CLMS.Dao.WorkerDao;
import com.Ntranga.CLMS.vo.LabourTimeVo;
import com.Ntranga.CLMS.vo.SearchCriteriaVo;
import com.Ntranga.CLMS.vo.ShiftsDefineVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WorkerDetailsVo;
import com.Ntranga.CLMS.vo.WorkerEducationAndEmploymentVo;
import com.Ntranga.CLMS.vo.WorkerNomineeDetailsVo;




@Service("workerService")
public class WorkerServiceImpl implements WorkerService {

	@Autowired
	private  WorkerDao workerDao;
	
	/*=============================== Worker Details START ====================================*/
	@Override
	public List<WorkerDetailsVo> workerGridDetails( SearchCriteriaVo searchCriteriaVo){
		return workerDao.workerGridDetails(searchCriteriaVo.getCustomerId().toString(),searchCriteriaVo.getCompanyId().toString(),searchCriteriaVo.getVendorId().toString(),
		        String.valueOf(searchCriteriaVo.getStatus()),searchCriteriaVo.getWorkerName().toString(),searchCriteriaVo.getWorkerCode().toString(),searchCriteriaVo.getWorkerId().toString());
		//return null;
	}
	
	
	@Override
	public Integer saveOrUpdateWorkerDetails(WorkerDetailsVo vo){		
		return workerDao.saveOrUpdateWorkerDetails(vo);
	}
	
	@Override
	public List<WorkerDetailsVo> getWorkerDetailsbyId(String workerInfoId){
		return workerDao.getWorkerDetailsbyId(workerInfoId);
		
	}
	@Override
	public List<SimpleObject> getTransactionDatesListForEditingWorkerDetails(Integer workerId){		
		return workerDao.getTransactionDatesListForEditingWorkerDetails(workerId);
	}


	@Override
	public Integer saveNomineeDetails(WorkerNomineeDetailsVo workerNomineeVo) {
		return workerDao.saveNomineeDetails(workerNomineeVo);
		
	}


	@Override
	public Integer deleteNomineeDetails(Integer workerNomineeId) {
		// TODO Auto-generated method stub
		return workerDao.deleteNomineeDetails(workerNomineeId);
	}


	@Override
	public List<WorkerNomineeDetailsVo> getNomineeGridData(Integer customerId, Integer companyId, Integer workerId) {
		// TODO Auto-generated method stub
		return workerDao.getNomineeGridData(customerId, companyId, workerId);
	}


	@Override
	public Integer saveEducationAndEmploymentDetails(WorkerEducationAndEmploymentVo educationAndEmployment) {
		
		return workerDao.saveEducationAndEmploymentDetails(educationAndEmployment);
	}


	@Override
	public SimpleObject validateWorkerCode(String workerCode, Integer customerID, Integer companyId, Integer vendorId,Integer workerId) {
		// TODO Auto-generated method stub
		return workerDao.validateWorkerCode(workerCode, customerID, companyId, vendorId, workerId);
	}
	
	@Override
	public Integer getWorkerInfoId(Integer workerId,Date transactionDate){		
		return workerDao.getWorkerInfoId(workerId,transactionDate);
	}
	/*=============================== Worker Details End ====================================*/


	@Override
	public WorkerEducationAndEmploymentVo getEducationAndEmploymentList(Integer customerId,Integer companyId,Integer workerId) {
		return workerDao.getEducationAndEmploymentList(customerId, companyId, workerId);
	}
	
	
	
	/*=============================== Worker Verification Start ====================================*/
	public List<WorkerDetailsVo> workerVerificationDetailsByWorkerInfoId(String workerInfoId){
		return workerDao.workerVerificationDetailsByWorkerInfoId(workerInfoId);
		
	}
	
	public Integer saveVerificationStatus(String name, MultipartFile[] files, String fileNames){
		return workerDao.saveVerificationStatus(name,files,fileNames);
	}
	
	/*=============================== Worker Verification End ====================================*/
	
	
	public List<LabourTimeVo> viewWorkerTimeDetailsForEditing(WorkerDetailsVo detailsVo){
		return  workerDao.viewWorkerTimeDetailsForEditing(detailsVo);
	}
	
	
	public Integer saveModifiedLaborTimeDetails(WorkerDetailsVo detailsVo){
		
		return  workerDao.saveModifiedLaborTimeDetails(detailsVo);
	}
	
	public List<ShiftsDefineVo>  getAvailableShifts(ShiftsDefineVo shiftsDefineVo){
		return  workerDao.getAvailableShifts(shiftsDefineVo);
	}
	
	
	@Override
	public List<SimpleObject> getVendorNamesAsDropDown(String customerId,String companyId,String vendorId, String vendorStatus, String locationArrayId) { 
		return workerDao.getVendorNamesAsDropDown(customerId, companyId, vendorId, vendorStatus);
		//return null;
	}


	@Override
	public List<WorkerDetailsVo> getWorkerChildScreensData(Integer workerId) {
		return workerDao.getWorkerChildScreensData(workerId);
	}


	@Override
	public String getWorkerAddressByWorkerId(Integer workerId) {
		// TODO Auto-generated method stub
		return workerDao.getWorkerAddressByWorkerId(workerId);
	}

	public Integer getWorkerId(int asInt){
		// TODO Auto-generated method stub
				return workerDao.getWorkerId(asInt);
	}
	
	public boolean validateWorkerAndSchedulingShifts(WorkerDetailsVo workervo){
		// TODO Auto-generated method stub
		return workerDao.validateWorkerAndSchedulingShifts(workervo);
		
	}


	@Override
	public boolean checkWorkerStatus(WorkerDetailsVo workervo) {
		// TODO Auto-generated method stub
		return workerDao.checkWorkerStatus(workervo);
	}


	@Override
	public SimpleObject getMaximumWorkerCode(WorkerDetailsVo paramDetails) {
		return workerDao.getMaximumWorkerCode(paramDetails);
	}
	
	@Override
	public SimpleObject validateDuplicateWorker(WorkerDetailsVo workerVo){
		return workerDao.validateDuplicateWorker(workerVo);
	}
	
	@Override
	public Integer changeWorkerCode(WorkerDetailsVo workerVo){
		return workerDao.changeWorkerCode(workerVo);
	}
	
}
