<!-- <link rel="stylesheet" href="/company/create/all.css"/> -->

<form id="workerFamilyDetails">
<div class="body">
    <div class="container-fluid ">  
    <div id="wizard" class="swMain">
        <ul class="anchor">
           <li >
            <a ng-href="#/workerDetails/{{workerInfoId >0 ? workerInfoId : 'create'}}" class="disabled" isdone="1" rel="1">
               <!--  <label class="stepNumber">1</label> -->
                <span class="stepDesc">Worker                
                </span>
               
            </a>
             <i class="fa fa-play pull-right selected"  aria-hidden="true"></i>
            </li>
              <li >
            <a ng-click="workerFamilyDetails($event) || enable" class="selected" isdone="0" rel="2">
               <!--  <label class="stepNumber">2</label> -->
                <span class="stepDesc">Family Details<br>                   
                </span>
            </a> 
            <i class="fa fa-play pull-right disabled" aria-hidden="true" ></i>
            </li>
              <li >
            <a ng-click="workerMedicalExamination($event) || enable" class="disabled" isdone="0" rel="3">
              <!--   <label class="stepNumber">3</label> -->
                <span class="stepDesc">Medical Safety<br>                   
                </span>
            </a> 
            <i class="fa fa-play pull-right disabled" aria-hidden="true" ></i>
            </li>
           <li>
            <a ng-click="workerPoliceVerification($event) || enable" class="disabled" isdone="0" rel="4">
              <!--   <label class="stepNumber">4</label> -->
                <span class="stepDesc">Police Verification<br>                   
                </span>
            </a> 
            <i class="fa fa-play pull-right disabled" aria-hidden="true" ></i>
            </li>
            <li>
            <a ng-click="workerVerification($event) || enable" class="disabled" isdone="0" rel="5">
               <!--  <label class="stepNumber">5</label> -->
                <span class="stepDesc">Verification<br>                   
                </span>
            </a> 
            <i class="fa fa-play pull-right disabled" aria-hidden="true" ></i>
            </li>
            <li>
            <a ng-click="workerJobDetails($event,false) || enable" class="disabled" isdone="0" rel="6">
               <!--  <label class="stepNumber">6</label> -->
                <span class="stepDesc"> Job Details                    
                </span>
            </a><i class="fa fa-play pull-right" aria-hidden="true"></i></li>
           <li>
            <a ng-click="workerbadgeGeneration($event) || enable" class="disabled" isdone="0" rel="7">
              <!--   <label class="stepNumber">7</label> -->
                <span class="stepDesc">Badge Generation<br>                   
                </span>
            </a> </li>
        </ul>
		
		</div>
    	  
    	<div class="col-md-12 well">
        	<h3 class="cls_plainheader">Worker Family and Nominee Details</h3>
            <br />
           <!-- 	<ul class="nav nav-tabs">
				<li><a href="#/workerDetails/{{workerInfoId >0 ? workerInfoId : 'create'}}">Worker</a></li>								
				<li class="active"><a href="#/workerFamilyDetails">Family Details</a></li>
				<li><a ng-href="#/workerEducationEmploymentDetails" >Education And Employment Details</a></li>
			</ul> -->
			<br/>
           	<div class="panel panel-primary">
	 			<div class="sepr">
          			<div class="row">
                    
		            	<div class="col-md-6">
	                       	<div class="form-group is-empty">
	                            <label for="custName" class="col-md-4 control-label required">Customer Name</label>
	                            <div class="col-md-8">
	                                 <input class="form-control" ng-model="customerName" placeholder="Customer Name" type="text"  name="custName" ng-disabled="true" required/>
	                            </div>
	                        </div>
	                              
							<div class="form-group is-empty">
	                            <label for="countryId" class="col-md-4 control-label required">Country Name</label>
	                            <div class="col-md-8">
	                                 <input class="form-control" ng-model="countryName" placeholder="Country Name" type="text"  name="countryName" ng-disabled="true" required/> 
	                            </div>
	                        </div>
	                               
	                        <div class="form-group is-empty">
	                            <label for="workerCode" class="col-md-4 control-label required">Worker Code</label>
	                            <div class="col-md-8">
	                                <input class="form-control" ng-model="workerCode" placeholder="Worker Code" type="text"  name="workerCode" ng-disabled="true" required/>
	                            </div>
	                        </div>
	                              
	                    </div>  
                          
			            <div class="col-md-6">
		                    <div class="form-group is-empty">
		                        <label for="cmpName" class="col-md-4 control-label required">Company Name</label>
		                        <div class="col-md-8">
		                           <input class="form-control" ng-model="companyName" placeholder="Company Name" type="text"  name="companyName" ng-disabled="true" required/>  
		                        </div>
	                        </div>
				
	                      	<div class="form-group is-empty">
	                           <label for="venName" class="col-md-4 control-label required">Vendor Name</label>
	                           <div class="col-md-8">
	                                <input class="form-control" ng-model="vendorName" placeholder="Vendor Name" type="text"  name="vendorName" ng-disabled="true" required />
	                           </div>
	                      	</div>
	                           
	                       	<div class="form-group is-empty">
	                            <label for="WorkerName" class="col-md-4 control-label required">Worker Name</label>
	                            <div class="col-md-8">
	                                <input class="form-control" ng-model="workerName" placeholder="Worker Name" type="text"  name="WorkerName" ng-disabled="true" required/>
	                            </div>
	                		</div>
	            		</div>
            		
            		</div>
            		<br/>
            	
	            	<div class="panel panel-primary">
						<div class="panel-heading">
							<h5 >Family Member Details</h5>
						</div>
		 				<div class="sepr">
	          				<div class="row">	
								<div class="col-md-12">
		                            <table class="table table-striped">
		                                <thead>
		                                    <tr>                                            	
		                                        <th>Member Name</th>
		                                        <th>Relationship</th>
		                                       <!--  <th>Dob</th> -->
		                                        <th>Phone</th>
		                                         <th>Amount of share in Pf%</th>
		                                        <th><a class="fa fa-plus addrow fa fa-plus" data-toggle="modal" data-target="#identificationType" ng-click="clearPopupValues()" ></a></th>
		                                    </tr>
		                                </thead>
	                                    <tbody>
		                             		<tr  ng:repeat="i in nomineeList" on-last-repeat>                                                                 
		                                  		<td>{{i.memberName}}</td>
		                                  		<td>{{i.relationship}}</td>
		                                 		<!--  <td>{{i.dateOfBirth}}</td> -->
		                                  		<td>{{i.phoneNumber}}</td>
		                                  		<td>{{i.percentageShareInPF}}</td>
		                                  		<td> <i ng-click="Delete($event)"  class="fa fa-trash"></i></td>
		                                  		<td>
		                                     		<i ng-click="Edit($event)" class="fa fa-edit" data-toggle="modal" data-target="#identificationType"></i>
		                                  		</td>
		                                  		<td></td>
		                             		</tr>
	                        			</tbody>
	                               	</table>
						
	                          	</div>
	                          	
	                      	</div>
	                      	
	                    </div>
	                    
	                    
	          		</div>
	          		<br/>
          			<div class="btn-group btn-group-right pull-right">
          				<a ng-click="workerJobDetails($event,true) || enable" ng-show="showProceedButton  && buttonArray.includes('APPROVE')"  class="btn btn-default">Proceed To Activate Worker</a>
          				<a ng-click="callingUrl($event) || enable" ng-hide="showProceedButton  && buttonArray.includes('APPROVE')"  class="btn btn-default">Proceed</a>
   						<!-- <a ng-click="workerVerification($event) || enable"  class="btn btn-default">Proceed To Medical</a> -->
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
    	</div>
	</div>
</div>
</form>


<form id="identificationTypeForm">
<!-- Modal -->
	<div class="modal fade" id="identificationType" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
 		<div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
               <div class="col-md-12">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h5>Family Members And Nominee Details</h5>
                        </div>
                        
                        <div class="sepr">
                            <div class="row">
                                <div class="col-md-6">
                                
                                <div class="form-group">
                                        <label for="memberName" class="col-md-4 control-label required">Member Name</label>
                                        <div class="col-md-8 ">
                                            <input type="text" class="form-control" id="Text1" placeholder="Member Name" ng-model="workerNomine.memberName" name="memberName"  minlength="2" maxlength="45" required/>

                                        </div>
                                    </div>
										
								<div class="form-group">
                                        <label for="relationship" class="col-md-4 control-label required">Relationship</label>
                                        <div class="col-md-6 select-box">
                                            <select id="relationship" class="form-control"  ng-model="workerNomine.relationship"  name="relationship" ng-disabled="readOnly" required>
                                                 <option value="">Select </option>
                                                <option value="Father">Father </option>
                                                <option value="Mother">Mother </option>
                                                <option value="Spouse">Spouse </option>
                                                <option value="Son">Son </option>
                                                <option value="Daughter">Daughter </option>
                                                <option value="Brother">Brother </option>
                                                <option value="Sister">Sister </option>
                                            </select>
                                            <span class="input-group-btn">
                                                <label for="Select1" class="fa fa-caret-down"></label>
                                            </span>
                                        </div>
                              </div>
                               <div class="form-group">
                                        <label for="gender" class="col-md-4 control-label required">Gender</label>
                                        <div class="col-md-6 select-box">
                                            <select id="Select1" class="form-control"  ng-model="workerNomine.gender"  name="gender" required>
                                             	<option value="">Select </option>
                                                <option value="M">Male </option>
                                                <option value="F">Female </option>
                                               
                                                 
                                            </select>
                                            <span class="input-group-btn">
                                                <label for="Select1" class="fa fa-caret-down"></label>
                                            </span>
                                        </div>
                                       
                                    </div>
                                    
                                    <div class="checkbox col-md-10">                                
                                        <label>
                                            <input type="checkbox" ng-model="workerNomine.isMinor" id="isMinor" name="isMinor" ng-checked="workerNomine.isMinor == 'Y'" ng-change="minirChnage()" ng-disabled="readOnly || minorDisabled">
                                            Is Minor
                                        </label>
                                    </div>
                                      <div class="form-group" ng-show="guardianShow">
                                        <label for="guardianDetails" class="col-md-4 control-label ">Guardian Details </label>
                                        <div class="col-md-8">
                                            <input type="text" class="form-control" id="guardianDetails" placeholder="Guardian Details "  ng-model="workerNomine.guardianDetails"  name="guardianDetails" minlength="1" maxlength="99" />
                                        </div>
                                    </div>
                                    
                                    
                                     
                                
                                
                           
                        </div>
                        <div class="col-md-6">                        
                       				 <div class="form-group">
                                        <label for="dateOfBirth" class="col-md-4 control-label ">Date of Birth </label>
                                        <div class="col-md-8">
                                            <input type="text" class="form-control" id="dateOfBirth"  placeholder="Date Of Birth &#xf073;" ng-model="dateOfBirth" name="dateOfBirth"  ng-disabled="readOnly" onchange="angular.element(this).scope().checkMinor()" />(OR)
                                        </div>
                                    </div>
                                    
                                      <div class="form-group">                                     
			                                    <label for="age" class="col-md-2 control-label ">Age</label>
			                                     <div class="col-md-2">
			                                        <input type="text" class="form-control onlyNumbers" id="yearsasdfasdf" placeholder="Years" ng-model="workerNomine.ageYears"  name="yearsasdfsdf" ng-disabled="readOnly" onchange="angular.element(this).scope().checkMinor()" /> 
			                                    </div>
			                                    <div class="col-md-2" >
			                                    	<label for="years" class="col-md-2 control-label ">Years</label>
			                                    </div>
			                                      <div class="col-md-2">
			                                        <input type="text" class=" col-md-2 form-control onlyNumbers" id="monthsasdfasdf" placeholder="Months" ng-model="workerNomine.ageMonths"  name="monthsasdfasdf" ng-disabled="readOnly"/> 
			                                    </div>
			                                   
			                                    <div class="col-md-2" >
			                                      	<label for="Months" class="col-md-2 control-label ">Months</label>
			                                    </div>   
			                           </div> 
                                    

                                     <div class="form-group">
                                        <label for="phoneNumber" class="col-md-5 control-label ">Phone Number </label>
                                        <div class="col-md-7">
                                            <input type="text" class="form-control" id="phoneNumber" placeholder="Phone Number"  ng-model="workerNomine.phoneNumber"  name="phoneNumber" minlength="10" maxlength="12" />
                                        </div>
                                    </div> 
                                    
                                    <div class="checkbox col-md-10">
                                        <label>
                                            <input type="checkbox" ng-model="workerNomine.selectAsNominee" ng-checked="workerNomine.selectAsNominee == 'Y'" ng-change="nomineeChange()">
                                            Select As Nominee
                                        </label>
                                    </div>
                                    <div class="form-group" ng-show="showPercentage">
                                        <label for="percentageShareInPF" class="col-md-4 control-label ">Amount of Share in PF(%)</label>
                                        <div class="col-md-8 ">
                                            <input type="text" class="form-control onlyNumbers" id="percentageShareInPF" placeholder="Amount of Share in PF (%)"  ng-model="workerNomine.percentageShareInPF"  name="percentageShareInPF" minlength="1" maxlength="3"  />

                                        </div>
                                    </div>
                        
                        
                        </div>
                        <br/>
                        <br/>
                         
                                 
                       
                        
                        
                        </div>
                        <div class="row col-md-6 ">
                        <div>                              
		                                					<div>
		                                
																<label class="control-label" > <input
																	type="checkbox" id="localOrPermAddress"
																	ng-model="isSameAddress"
																	name="localOrPermAddress"													
																	ng-disabled="readOnly"  ng-click="ifSameAddress()" />
																	Select Address Same As Worker Address or Enter Address
																</label>
															</div>
										</div>
														
                                    <div class="form-group">
                                       <!--  <label for="address" class="col-md-4 control-label ">Address</label> -->
                                        <div class="col-md-12 ">
                                            <textarea class="form-control z-index:1000px" placeholder="Address"  ng-model="workerNomine.address" name="address" minlength="2" maxlength="499" required>                                            
                                          </textarea>
                                        </div>
                                    </div>
                        
                        </div>
                          <div class="row">
                           <div class="modal-footer btn-group btn-group-right pull-right">
									<button class="btn btn-primary" ng-click="saveNomineeDetails($event)" onclick="return false;"  ng-show="popUpSave">Save</button>
									<button class="btn btn-primary" ng-click="updateNomineeDetails($event)" onclick="return false;"  ng-show="popUpUpdate">Update</button>
									<button type="button" class="btn btn-warning" data-dismiss="modal" >Close</button>
								</div>    
                          </div>
                        
                        </div>
                        
                      
                      </div>
                                		
                      </div> 
                        </div>  
                       </div>
                        
                        </div> 

</form>

