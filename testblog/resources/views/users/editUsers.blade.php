 <!DOCTYPE html>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<html>
<head>
	<title>Registration</title>
  </script>

</head>
<body>
<!-- display all errors -->
<!-- messege from success -->
        @if(session()->has('message'))
    <div class="alert alert-success">
        {{ session()->get('message') }}
    </div>
@endif



<!------ Include the above in your HEAD tag ---------->

 <div class="container">
      
             	<h1 align="center">Users Form</h1>


 {{ Form::model( $customer, ['route' => ['userManagement.update',$customer->id], 'method' => 'put', 'role' => 'form','enctype'=>'multipart/form-data','class' => 'well form-horizontal'] ) }}

                  <!--  <form class="well form-horizontal"> -->
                      <fieldset>
                      	<input name="_token" type="hidden"  value="{{ csrf_token() }}">


                         <div class="form-group">
                            <label class="col-md-4 control-label">Fist Name</label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span><input id="fullName" name="fname" placeholder="Fisrt Name" class="form-control" value="{{$customer->fname}}" type="text">
                                </div>
               </div>
                           
                         </div>
                         <div class="form-group">
                            <label class="col-md-4 control-label">Last Name</label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span><input id="fullName" name="lname" placeholder="Last Name" class="form-control"   value="{{$customer->lname}}" type="text"></div>
                            </div>
                         </div>
                         <div class="form-group">
                            <label class="col-md-4 control-label">Email</label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span><input id="email" name="email" placeholder="Email" class="form-control"  value="{{$customer->email}}" type="text"></div>
                            </div>
                         </div>
                          <div class="form-group">
                            <label class="col-md-4 control-label">Mobile Number</label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-earphone"></i></span><input id="phoneNumber" name="mobile" placeholder="Mobile Number" class="form-control" value="{{$customer->mobile}}" type="text"></div>
                            </div>
                         </div>
                         <div class="form-group">
                        <label class="col-md-4 control-label">Gender</label>
                        <div class="col-md-8 inputGroupContainer">
                            <div class="radio">
                                <label>
                                    <input type="radio" name="gender" value="male" @if($customer->gender == 'male') checked @endif/> Male</label>
                                <label>
                                    <input type="radio" name="gender" value="female" @if($customer->gender == 'female') checked @endif/> Female</label>
                            </div>
                        </div>
                    </div>
                         <div class="form-group">
                            <label class="col-md-4 control-label">Address</label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-home"></i></span><input id="addressLine1" name="address" placeholder="Address" class="form-control"  value="{{$customer->address}}" type="text"></div>
                            </div>
                         </div>
                         <!-- <div class="form-group">
                            <label class="col-md-4 control-label">Password</label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i></span><input id="addressLine2" name="password" placeholder="Password" class="form-control" value="{{$customer->password}}" type="password"></div>
                            </div>
                         </div>
                         <div class="form-group">
                            <label class="col-md-4 control-label">Confirom Password</label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i></span><input id="city" name="c_password" placeholder="Confirom Password" class="form-control"  value="{{$customer->c_password}}" type="password"></div>
                            </div>
                         </div> -->
			              <div class="form-group files">
			                <label class="col-md-4 control-label">Upload Your File </label>
			                <div class="col-md-8 inputGroupContainer">
			                  <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-file"></i></span>
			                <input type="file" class="form-control" name="profile_pic[]" multiple="multiple" onchange="loadFile(event)">
			               @if(count($customer->profile_pic) > 0)
			@foreach(json_decode($customer->profile_pic, true) as $images) 
			<!-- folder ma image hase to j browser ma show karva mate -->
				@if((file_exists("uploads/$images")) && $images )
				<img src="{{url('/uploads/'.$images)}}"  alt="--"  width="60px" height="60px" />
				@endif
			
			 @endforeach
			 @endif
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
                   
                   {!! Form::close() !!}
    </div>
<!-- </form> -->
	 <script>

var loadFile = function(event)
{
   var image_preview = document.getElementById('image_preview');
   image_preview.src = URL.createObjectURL(event.target.files[0]);
    
 };

</script>
</body>
</html>


