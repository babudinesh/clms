<form id="workerForm">
	<div class="container-fluid">
	
	<div id="wizard" class="swMain">
        <ul class="anchor">
            <li>
            <a ng-href="#/workerDetails/{{workerInfoId >0 ? workerInfoId : 'create'}}" class="disabled" isdone="1" rel="1">
                <label class="stepNumber">1</label>
                <span class="stepDesc">Worker<br>
                   <!--  <small>Create a layout</small> -->
                </span>
            </a> <i class="fa fa-play pull-right disabled"  aria-hidden="true"></i>
            </li>
              <li>
            <a ng-click="workerFamilyDetails($event) || enable" class="disabled" isdone="0" rel="2">
                <label class="stepNumber">2</label>
                <span class="stepDesc">Family Details<br>                   
                </span>
            </a> 
            <i class="fa fa-play pull-right disabled" aria-hidden="true" ></i>
            </li>
            <li>
            <a ng-click="workerMedicalExamination($event) || enable"
					class="disabled" isdone="0" rel="3"> <label class="stepNumber">3</label>
						<span class="stepDesc">Medical Safety<br>
					</span>
				</a> <i class="fa fa-play pull-right disabled" aria-hidden="true"></i>
			</li>
			
			
			<li>
			<a ng-click="policeVerification($event) || enable"
					class="disabled" isdone="0" rel="4"> <label class="stepNumber">4</label>
						<span class="stepDesc">Police Verification<br>
					</span>
				</a> <i class="fa fa-play pull-right disabled" aria-hidden="true"></i>
				</li>
				
				
             <li>
             <a ng-click="workerVerification($event)" class="selected" isdone="0" rel="5">
                <label class="stepNumber">5</label>
                <span class="stepDesc">Verification<br>
                   
                </span>
            </a><i class="fa fa-play pull-right selected"  aria-hidden="true"></i></li>
           <li>
           <a ng-click="workerJobDetails($event,false)" class="disabled" isdone="0" rel="6">
                <label class="stepNumber">6</label>
                <span class="stepDesc"> Job Details                    
                </span>
            </a><i class="fa fa-play pull-right disabled"  aria-hidden="true"></i></li>
            <li>
            <a ng-click="workerbadgeGeneration($event)" class="disabled" isdone="0" rel="7">
                <label class="stepNumber">7</label>
                <span class="stepDesc">Badge Generation<br>                   
                </span>
            </a></li>
        </ul>
		
		</div>
	
		<div class="col-md-12 well">
			<h3 class="cls_plainheader">Worker Verification</h3>				
   			<div class="col-md-12">
   				<br/>	
   				<br/>	
            	<div class="panel panel-primary">
           			<div  class="panel-heading">
        	 			<h5>Worker Verification Status</h5>
        			</div>
     
       				<div class="sepr">          
           	 			<div class="row">
                            <div class="col-md-6">
                                <div class="form-group is-empty">
                                    <label for="custName" class="col-md-4 control-label required">Customer Name</label>
                                    <div class="col-md-8">
                                         <input class="form-control" ng-model="verification.customerName" placeholder="Customer Name" type="text"  name="workerCode" ng-disabled="readOnly" required/>
                                    </div>
                                </div>
								<div class="form-group is-empty">
                                    <label for="countryId" class="col-md-4 control-label required">Country Name</label>
                                    <div class="col-md-8">
                                         <input class="form-control" ng-model="verification.countryName" placeholder="Country Name" type="text"  name="workerCode" ng-disabled="readOnly" required/> 
                                    </div>
                                </div>
                                
                                 <div class="form-group is-empty">
                                    <label for="workerCode" class="col-md-4 control-label required">Worker Code</label>
                                    <div class="col-md-8">
                                        <input class="form-control" ng-model="verification.workerCode" placeholder="Worker Code" type="text"  name="workerCode" ng-disabled="readOnly" required/>
                                    </div>
                                </div>
                                
                                 <div class="form-group is-empty">
                                    <label for="verificationStatus" class="col-md-4 control-label required">Verification Status</label>
                                    <div class="col-md-8 select-box">
                                    	<select id="status" class="form-control" ng-model="verification.verificationStatus" name="status"  ng-options =" item.id as item.name for item in list_VerificationStatus"  ng-disabled="true" required>
                                        </select>
                                        <span class="input-group-btn">
                                     		<label for="Select1" class="fa fa-caret-down"></label>
                                        </span>
                                    </div>
                                </div> 
                            </div>

                            <div class="col-md-6">
                                <div class="form-group is-empty">
                                    <label for="cmpName" class="col-md-4 control-label required">Company Name</label>
                                    <div class="col-md-8">
                                       <input class="form-control" ng-model="verification.companyName" placeholder="Company Name" type="text"  name="workerCode" ng-disabled="readOnly" required/>  
                                    </div>
                                </div>
								
                               <div class="form-group is-empty">
                                    <label for="venName" class="col-md-4 control-label required">Vendor Name</label>
                                    <div class="col-md-8">
                                         <input class="form-control" ng-model="verification.vendorName" placeholder="Vendor Name" type="text"  name="workerCode" ng-disabled="readOnly" required />
                                    </div>
                               </div>
                                
                               <div class="form-group is-empty">
                                    <label for="WorkerName" class="col-md-4 control-label required">Worker Name</label>
                                    <div class="col-md-8">
                                        <input class="form-control" ng-model="verification.firstName" placeholder="Worker Name" type="text"  name="WorkerName" ng-disabled="readOnly" required/>
                                    </div>
                               </div>
                            </div>
						</div>
						
						<div class="row">
							<div class="panel panel-primary" style="margin: 15px ">	                      
								<table class="table table-striped">
									<thead>
										<tr>
											<th>S.No</th>
											<th>Verification Type</th>
											<th>Verified</th>
											<th>Comments</th>
											<th>Attachment</th>												
											<th><i class="fa fa-plus" data-toggle="modal"
												data-target="#workerVerification" ng-click="plusIconAction()"> </i></th>
										</tr>
									</thead>
									<tbody>
										<tr ng:repeat="i in (result )" on-last-repeat>
											<td>{{$index+1}}</td>
											<td>{{i.verificationType}}</td>
											<td>{{i.status}}</td>
											<td>{{i.comments}}</td>
											<!-- <td><a ng-click="fileDownload($event)">{{i.attachment.name}}</a></td>	 -->	
										 	<!-- <td><a ng-href="{{i.fileUrl}}" download="abc.jpg">download</a></td>  -->
										 	<td><a href ng-click="fun_download(i.location,i.fileName,i.attachment)" >{{i.fileName}}</a></td>
											<td><i ng-click="Delete($event)" class="fa fa-trash" ></i></td>
											<td><i ng-click="Edit($event,i.location, $index+1)" class="fa fa-edit" data-toggle="modal" data-target="#workerVerification"></i></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>				
					</div>
            		<div class="btn-group btn-group-right pull-right">            			
            			<a ng-click="workerJobDetails($event,true) || enable" ng-show="showProceedButton && buttonArray.includes('APPROVE')"  class="btn btn-default">Proceed To Activate Worker</a>
            			<a ng-click="callingUrl($event,false) || enable" ng-hide="showProceedButton && buttonArray.includes('APPROVE')"  class="btn btn-default">Proceed</a>
		            	<a href="javascript:void(0)" class="btn btn-danger" ng-show="updateBtn && buttonArray.includes('UPDATE')" ng-click="uploadFiles($event)"> Save & Proceed </a>		            	
						<a href="#/workerVerificationSearch"  class="btn btn-default">Return To Search</a>
					</div>	
        		</div>
        	</div>
        </div>
	</div>

    <div class="container-fluid">
        <div class="navbar navbar-default navbar-fixed-bottom text-center" style="min-height: 15px; padding: 0px;">
            <div class="container text-right">
                <label class="control-label text-center" style="color: white; margin: 0px; font-size: 14px;">@Copyright</label>
            </div>
        </div>
    </div>
</form>

<form id="workerVerificationForm">
	
	<div class="modal fade" id="workerVerification" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Worker Verification</h4>
				</div>
				<div class="modal-body">
					<div class="panel panel-default">
						<div class="sepr">
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label for="verificationType" id ="verificationTypeLabel" class="col-md-4 control-label">Verification Type</label>
										<div class="col-md-8 select-box">
											<select ng-model="verificationList.verificationType" ng-change="verificationTypeChange()" ng-options="item.name as item.name for item in list_VerificationType " class="form-control" id="verificationType"  name = "verificationType" ng-disabled="readOnlyVar" required>
												<option value="">Select</option>
											</select> 
											<span class="input-group-btn"> 
												<label for="verificationType"class="fa fa-caret-down"></label>
											</span>
										</div>
									</div>
									
									<div class="form-group">
										<label for="Comments" class="col-md-4 control-label required">Comments</label>
										<div class="col-md-8">
											<textarea  ng-model="verificationList.comments" name="comments" class="form-control" id="comments" placeholder="Comments" minlength="1" maxlength="200"  required></textarea>
										</div>
									</div>
									
									
								</div>
								<div class="col-md-6">	
									<div class="form-group">
										 
	                               			<!--  <label>
	                                    		<input type="checkbox" id="isCleared" ng-click="isClearChange()"  ng-model="verificationList.isCleared" ng-checked="verificationList.isCleared == 'Yes'" name="isCleared" />
	                                   			 Verified
	                               		 	</label> -->
	                               		 	<label for="status" class="col-md-4 control-label  required">Status</label>
											<div class="col-md-8 select-box">
												<select ng-model="verificationList.status" ng-options="item.id as item.name for item in list_Status " class="form-control" id="status"  required>
													<option value="">Select</option>
												</select> 
												<span class="input-group-btn"> 
													<label for="status"class="fa fa-caret-down"></label>
												</span>
											</div>
	                            		
									</div>
									
									<div>
										<label for="Currency" class="col-md-4 control-label">Attachment</label>
										<div class="col-md-8">											
			                           	<!-- <input type="file"  file-verify="getTheFiles($files)" id="file1ssss" name="file" value="Choose File" ng-click="uploadFiles()" >Choose File</input>
			        						 <input type="button" class="btn  btn-primary "  vaue="Upload">Upload </input> -->
                                          	<input type ="file" file-verify ="myFile"  name="fileName" id ="fileName" ng-model="verificationList.fileName" onchange="angular.element(this).scope().getFileDetails(this)" />
                                          	<div ng-show="verificationList.fileName != null && myFile == null" ng-disabled="readOnly">
		                                		<a href ng-click="fun_download(verificationList.location, verificationList.fileName, '')" >{{verificationList.fileName}}</a>
		                                	</div>
										</div>
									</div>
									
								</div>
							</div>
							<br/>
						</div>
					</div>
					<div class="modal-footer">
						<button class="btn btn-primary" ng-click="saveDetails($event)" onclick="return false;" ng-show="popUpSave">Save</button>
						<button class="btn btn-primary" ng-click="updateDetails($event)" onclick="return false;" ng-show="popUpUpdate">Update</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>


