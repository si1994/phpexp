@extends('layouts.app')

@section('content')


<!DOCTYPE html>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<html>
<head>
	<title></title>
</head>

<body>	  
	<h1 class="text-center"> Search Data </h1>
	 <div class="form-group col-md-12">
   <main class="page-content">
            <div class="container-fluid">
                <div class="row">
                    <div class="form-group col-md-12">
	<!-- <a href="{{ route('userManagement.create')}} " class="btn btn-sm btn-success">Add User</a> -->
	<div class="col-md-1"> 
</div>

	<div class="col-md-12">

<table class="table">
	<tr>
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
		 @foreach($users as $data)
		 <td>{{$data->id}}</td>
         <td>{{$data->fname}}</td>
         <td>{{$data->lname}}</td>
         <td>{{$data->email}}</td>
         <td>{{$data->mobile}}</td>
         .<td>{{$data->gender}}</td>	
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
			 </td>
			<!-- folder ma image hase to j browser ma show karva mate -->
			<!-- @if((file_exists("uploads/$data->profile_pic")) && $data->profile_pic )
    			<img src="{{url('/uploads/'.$data->profile_pic)}}"   width="60px" height="60px"/>
			@endif
 -->
			<!-- <img src="{{url('/uploads/'.$data->profile_pic)}}"  width="60px" height="60px"/> -->
		

		<td><a href="{{ route('userManagement.show',$data->id) }}" class="btn btn-sm btn-success">View<span class="glyphicon glyphicon-eye-open"></span></a></td>


		<td><a href="{{ route('userManagement.edit',$data->id) }} " class="btn btn-sm btn-info">Edit<span class="glyphicon glyphicon-pencil"></span></a></td>


		<td><form action="{{action('UserDataController@destroy', $data->id)}}" method="post">
           {{csrf_field()}}
           <input name="_method" type="hidden" value="DELETE">
           <button class="btn btn-danger" type="submit">Delete<span class="glyphicon glyphicon-trash"></span></button>
         </form></td>


         <td><a href="{{action('UserDataController@downloadPDF', $data->id)}}" class="btn btn-default"><span class="glyphicon glyphicon-download-alt"></span>PDF</a></td>


		</tr>
		@endforeach
</table>
</div>
	</div>
</div>
</div>
</body>
</html>
@endsection





	


<!--  @foreach($users as $data)
                     <tr>
                     <td>{{$data->id}}</td>
                      <td>{{$data->fname}}</td>
                    <td>{{$data->lname}}</td>
                    <td>{{$data->email}}</td>
                    <td>{{$data->mobile}}</td>
                    <td>{{$data->address}}</td>

              
     @endforeach	

  </tr> -->
    