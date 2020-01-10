

<div class="body">	
   
         
    <div class="container-fluid ">
   <!--  <form id="WorkerUpload" name="WorkerUpload" novalidate ng-submit="submit" method="post"  action="uploadWorker.view" enctype="multipart/form-data"> -->
   <form id="WorkerUpload" name="WorkerUpload" novalidate ng-submit="submit">
        <div class="col-md-12 well">
         <h3 class="cls_plainheader">Worker Upload</h3>
            <br />
       <div class="panel panel-primary">
		
        
			
			<div>
				<div class="row">
				 <div class="panel-heading">
				<h5 >Guidelines to be followed</h5>
				       </div>
                          <br/> <br/>
                        <div class="col-md-10">                        
                         <label for="note" style="font-size: 12px;font-weight: bold !important;    color: grey !important;"> i) &#160;&#160; Please select CSV (Comma delimiter) as the file type. Please refer shift code from configuration page. <br/>
                         																									ii) &#160; Worker code should start with the first 3 characters mentioned in vendor name parentheses .<br/>
                         																									iii)  While uploading the CSV file make sure that the data should be in below Grid format.</label><br/>
                        	<br/>
                        </div>
                        <div class="sepr">		
                        <div class="col-md-12">
							<table class="table table-striped" id="workerDetailsTable" border="1">
								<thead>
									<tr>									
										<th>Worker Code</th>
										<th>Effective From</th>
										<!-- <th>Is_Active</th> -->
										<th>First_Name</th>
                                        <th>Last_Name</th>
                                        <th>Date_Of_Birth</th>
                                        <th>Date_Of_Joining</th>
                                        <th>Gender</th>
                                        <th>Marital_Status</th>
                                        <th>Shift_Code</th>
                                        <th>Weekly_Off</th>
									</tr>
								</thead>
								<tbody>
								<tr>
								<td>605200</td>
								<td>01-01-2016</td>
								<!-- <td>Y</td> -->
								<td>Amar</td>
								<td>Kumar</td>
								<td>10-01-1985</td>
								<td>10-01-2016</td>
								<td>M</td>
								<td>Married</td>
								<td>A</td>
								<td>MON</td>
								<tr>
								</tbody>								
							</table>
						</div>
						</div>
                        </div>	
                         <div class="row">
                          <br/> 
                           <div class="col-md-3">
                         <label style="font-weight: bold !important;    color: grey !important;    font-size: 12px;">  Gender Codes:  </label>
                          <br/> M - Male,
                           F - Female,
                           U - Unknown.
                           </div>
                           <div class="col-md-4">
                          <label style="font-weight: bold !important;    color: grey !important;    font-size: 12px;"> Marital Status Codes:  </label>
                          <br/> 
                           Married,
                           Single,
                           Unknown,
                           Divorce,
                           Widowed.
                           </div>
                            <div class="col-md-5">
                           <label style="font-weight: bold !important;    color: grey !important;    font-size: 12px;"> Weekly Off Codes:   </label>
                          <br/>
                            MON, TUE, WED, THU, FRI, SAT, SUN.
                           </div>
                          </div>
                          
                   </div>
                   <br/>
                   <div class="row">
             <div class="panel-heading">
						<h5>Worker Upload</h5>
       				</div>
                            <div class="col-md-6">
                                <div class="form-group is-empty">
                                    <label for="custName" class="col-md-4 control-label required">Customer Name</label>
                                    <div class="col-md-8">
                                        <select ng-change="customerChange()" ng-model="workerVo.customerId" name="customerId" ng-options="item.customerId as item.customerName for item in customerList" class="form-control"  id="customerId" ng-disabled="readOnly" required>
                                            <option value="">Select</option>
                                            </select>
                                    </div>
                                </div>

                                <div class="form-group is-empty">
                                    <label for="venName" class="col-md-4 control-label required">Vendor Name</label>
                                    <div class="col-md-8">
                                         <select   ng-model="workerVo.vendorId"   name="vendorId" ng-options="item.id as item.name for item in vendorList" class="form-control"  id="vendorId" name="vendorId" ng-disabled="readOnly"  required>
                                            <option value="">Select</option>
                                            </select>
                                    </div>
                                </div>

                            </div>

                            <div class="col-md-6">
                                <div class="form-group is-empty">
                                    <label for="cmpName" class="col-md-4 control-label required">Company Name</label>
                                    <div class="col-md-8">
                                         <select  ng-change="companyChange()"  ng-model="workerVo.companyId"  name="companyId" ng-options="item.id as item.name for item in companyList" class="form-control"  id="companyId" ng-disabled="readOnly"  required>
                                            <option value="">Select</option>
                                            </select>
                                    </div>
                                </div>
								<div class="form-group is-empty">
                                    <label for="countryId" class="col-md-4 control-label required">Country Name</label>
                                    <div class="col-md-8">
                                         <select  ng-model="workerVo.countryId"   ng-options="item.id as item.name for item in list_Country" class="form-control"  id="countryId"  name="countryId" required ng-disabled="true">
                                            <option value="">Select</option>
                                            </select>
                                    </div>
                                </div>
                               

                            </div>
                           
                        </div>
                         <div class="row">
                          <br/> <br/>
                        <div class="col-md-6">
                        
                              <label for="Upload" class="col-md-4 control-label required">Upload CSV File</label>				
                              <div class="col-md-8">		  
                           		<input type="file" file-model="myFile" accept=".csv"  id="fileCsv"/>
        						<button class="btn  btn-primary " ng-click="Upload($event)">Upload </button>
							  </div>
							
                       </div>
                       <div class="col-md-6">
                       <div class="form-group is-empty">
							 <label for="Upload" class="col-md-4 control-label required"> Download CSV File Format</label>				
                              <div class="col-md-8">		  
                           		<button class="btn  btn-primary " ng-click="downloadUploadFormat('Upload Worker')"> Download </button>
							 </div>
							 </div>
                       </div>
                   </div> 
                   
                  
       				 	
           	 			
			</div>   
			
			<div class="panel panel-primary">				
				
				<div  id="uploadWorkerDiv" ng-show="errorsList.length > 0"> 
				<div class="panel-heading">
				<h5>Errors in Uploaded File</h5>
				</div>
					<div class="sepr">						
						<div>
							<table class="table table-striped" id="uploadWorkerDtaTable">
								<thead>
									<tr >									
										
										<th>Worker Code</th>
										<th>First Name</th>
										<th>Last Name</th>										
                                        <th>Date Of Joining</th>
                                        <th>Date Of Birth</th>                                       
                                        <th style="color:red">Error</th>
                                         <th>Line Number</th> 
									</tr>
								</thead>								
							</table>
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
        </div>
   
  </div>
  
  





