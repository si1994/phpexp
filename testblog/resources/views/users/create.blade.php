@extends('layouts.app')

@section('content')
 <!DOCTYPE html>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<html>
<head>
	<title>Registration</title>
  

</head>
<body>
<!-- display all errors -->
<!-- messege from success -->



<!------ Include the above in your HEAD tag ---------->

 <div class="container">
       <table class="table table-striped">
          <tbody>
             <tr>
             	<h1 align="center">Users Form</h1>
</tr>
<tr>		@if(count($errors))
			<div class="alert alert-danger">
				<strong>Whoops!</strong> There were some problems with your input.
				<br/>
				<ul>
					@foreach($errors->all() as $error)
					<li>{{ $error }}</li>
					@endforeach
				</ul>
			</div>
		@endif
                <td colspan="1">
                	{{Form::open(array('url'=>'userManagement','method'=>'post', 'enctype' => 'multipart/form-data', 'class' => 'well form-horizontal'))}}

                
                  <!--  <form class="well form-horizontal"> -->
                      <fieldset>
                      	<input name="_token" type="hidden"  value="{{ csrf_token() }}">


                         <div class="form-group">
                            <label class="col-md-4 control-label">Fist Name</label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span><input id="fullName" name="fname" placeholder="Fisrt Name" class="form-control" value="{{ old('fname') }}" type="text">
                                </div>
               </div>
                           
                         </div>
                         <div class="form-group">
                            <label class="col-md-4 control-label">Last Name</label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span><input id="fullName" name="lname" placeholder="Last Name" class="form-control"   value="{{ old('lname') }}" type="text"></div>
                            </div>
                         </div>
                         <div class="form-group">
                            <label class="col-md-4 control-label">Email</label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span><input id="email" name="email" placeholder="Email" class="form-control"  value="{{ old('email') }}" type="text"></div>
                            </div>
                         </div>
                          <div class="form-group">
                            <label class="col-md-4 control-label">Mobile Number</label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-earphone"></i></span><input id="phoneNumber" name="mobile" placeholder="Mobile Number" class="form-control"  value="{{ old('mobile') }}" type="text"></div>
                            </div>
                         </div>
                         <div class="form-group">
                        <label class="col-md-4 control-label">Gender</label>
                        <div class="col-md-4">
                            <div class="radio">
                                <label>
                                    <input type="radio" name="gender" value="male" /> Male
                                </label>
                                <label>
                                    <input type="radio" name="gender" value="female" /> Female
                                </label>
                            </div>
                        </div>
                    </div>
                         <div class="form-group">
                            <label class="col-md-4 control-label">Address</label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-home"></i></span><input id="addressLine1" name="address" placeholder="Address" class="form-control"  value="{{ old('address') }}" type="text"></div>
                            </div>
                         </div>
                         <div class="form-group">
                            <label class="col-md-4 control-label">Password</label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i></span><input id="addressLine2" name="password" placeholder="Password" class="form-control"  value="{{ old('password') }}" type="password"></div>
                            </div>
                         </div>
                         <div class="form-group">
                            <label class="col-md-4 control-label">Confirom Password</label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i></span><input id="city" name="c_password" placeholder="Confirom Password" class="form-control"  value="{{ old('c_password') }}" type="password"></div>
                            </div>
                         </div>
			              <div class="form-group files">
			                <label class="col-md-4 control-label">Upload Your File </label>
			                <div class="col-md-8 inputGroupContainer">
			                  <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-file"></i></span>
			                <input type="file" class="form-control" name="profile_pic[]" multiple="">
			              </div>
			             </div>
			           </div>
                        <div class="form-group">
						  <label class="col-md-4 control-label"></label>
						  <div class="col-md-4">
						    <button type="submit" class="btn btn-warning" >Submit <span class="glyphicon glyphicon-send"></span></button>
						  </div>
						</div>
                      </fieldset>
                   </form>
                   {!! Form::close() !!}
    </div>
 </body>
</html>
@endsection