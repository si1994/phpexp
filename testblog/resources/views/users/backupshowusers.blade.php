
@extends('layouts.app')

@section('content')


<!DOCTYPE html>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
   
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<html>
<head>
	<title></title>

	<script src="https://code.jquery.com/jquery-3.1.1.js"></script>
	<!-- SUCCESS MSG DISPLAY SHOW AND HIDE -->
	<script>
        $( document ).ready(function(){
            $('#message').fadeIn('slow', function(){
               $('#message').delay(1500).fadeOut(); 
            });
        });
        </script>


    <!-- END SUCCESS MSG DISPLAY SHOW AND HIDE -->
</head>

<body>
	<div class="form-group col-md-12">	 			  
		<h1 class="text-center"> Users Information </h1>
	</div>

	<!-- SEARCH BAR -->
	<div class="form-group col-md-12">
		<div class="form-group col-md-4">
			<form action="{{ url('userManagement/search') }}" method="post">
			{{csrf_field()}}
            	<div class="input-group custom-search-form">
	                <input type="text" class="form-control" name="search" placeholder="Search..."><span class="input-group-btn">
	    			<button class="btn btn-default-sm" type="submit"><i class="fas fa-search">search</i></button></span>
				</div>
			</form>
		</div>
	<!--END SEARCH BAR -->
	<div class="form-group col-md-5">
	<form action="{{ url('userManagement/getdate') }}" method="post">
	{{csrf_field()}}
	created_at:
	<input type="date" name="created_at">
	updated_at:
	<input type="date" name="updated_at">
	<input type="submit" name="submit" value="submit">
	</form>
	</div>
		<div class="form-group col-md-3">
			<a class="btn btn-success" style="float: right;" href="{{ route('userManagement.create') }}"> Add User </a>

			<a class="btn btn-primary" style="float: right;" href="{{ url('userManagement/getImport') }}"> Import </a>

			<a class="btn btn-primary" style="float: right;" href="{{ url('userManagement/export') }}"> Export	 </a>
		
		</div>
	</div>
	
	<div class="form-group col-md-12">
		@if ($message = Session::get('success'))
		       <div class="alert alert-success" id="message">
		           <p>{{ $message }}</p>
		       </div>
		       
		   @endif
	</div>
<main class="page-content">
    <div class="container-fluid">
    <div class="row">
    <div class="form-group col-md-12">
	<!-- <a href="{{ route('userManagement.create')}} " class="btn btn-sm btn-success">Add User</a> -->
			<div class="col-md-12">
			 	 <form action="{{ url('userManagement/deleteAll') }}" method="post" name="delete_item" onsubmit="return confirm('Are you sure Delete Records?');">
        		{{ csrf_field() }}
        		<input type="submit" class="btn btn-danger" value="Delete Selected">
				</form>
				<table class="table">
					<tr>
						<th><!-- <input type="checkbox" class="checkbox_all"> --></th>
						<th>ID</th>
						<th>FIRST NAME</th>
						<th>LAST NAME</th>
						<th>EMAIL</th>
						<th>MOBILE</th>	
						<th>GENDER</th>
						<th>ADDRESS</th>	
						<!-- <th>PASSWORD</th>
						<th>CONFIRM PASSWORD</th> -->
						<th>IMAGE</th>
						<th colspan="3">OPRATION</th>	
					</tr>
					<tr>

						@foreach($abc as $data)
						 <td><input type="checkbox" name="checkbox[]" value="{{ $data->id }}" /></td>
						<td>{{$data->id}}</td>
						<td>{{$data->fname}}</td>
						<td>{{$data->lname}}</td>
						<td>{{$data->email}}</td>
						<td>{{$data->mobile}}</td>
						<td>{{$data->gender}}</td>	
						<td>{{$data->address}}</td>
						<td>
							@if(count($data->profile_pic) > 0)
							@foreach(json_decode($data->profile_pic, true) as $images) 
							<!-- folder ma image hase to j browser ma show karva mate -->
								@if((file_exists("uploads/$images")) && $images )
								<img src="{{url('/uploads/'.$images)}}"  alt="--"  width="60px" height="60px" class="img-rounded"/>
								@endif
							 @endforeach
							 @endif
							<!-- folder ma image hase to j browser ma show karva mate -->
							<!-- @if((file_exists("uploads/$data->profile_pic")) && $data->profile_pic )
				    			<img src="{{url('/uploads/'.$data->profile_pic)}}"   width="60px" height="60px"/>
							@endif
			 						-->
							<!-- <img src="{{url('/uploads/'.$data->profile_pic)}}"  width="60px" height="60px"/> -->
						</td>

						<td>
							<a href="{{ route('userManagement.show',$data->id) }}" class="btn btn-sm btn-success">View<span class="glyphicon glyphicon-eye-open"></span></a>
						</td>

						<td>
							<a href="{{ route('userManagement.edit',$data->id) }} " class="btn btn-sm btn-info">Edit<span class="glyphicon glyphicon-pencil"></span></a>
						</td>

						<td>
							<form action="{{action('UserDataController@destroy', $data->id)}}" method="post" onsubmit="return confirm('Are you sure?');"  style="display: inline">
				           	{{csrf_field()}}
				           		<input name="_method" type="hidden" value="DELETE">
				           		<button class="btn btn-danger" type="submit">Delete<span class="glyphicon glyphicon-trash"></span></button>
				         	</form>
				        </td>

				        <td>
				         	<a href="{{action('UserDataController@downloadPDF', $data->id)}}" class="btn btn-default"><span class="glyphicon glyphicon-download-alt"></span>PDF</a>
				        </td>  
					</tr>
					@endforeach	
				</table>	
				
 				{!! $abc->links() !!}
			</div>
		</div>
	</div>
	</div>
	
</body>   
</html>
@endsection


