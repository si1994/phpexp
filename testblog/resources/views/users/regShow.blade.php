
@extends('layouts.app')

@section('content')


<!DOCTYPE html>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<html>
<head>
    <title></title>
</head>

<body>
    <div class="form-group col-md-12">                
        <h1 class="text-center"> Users Register Information </h1>
    </div>

    <!-- SEARCH BAR -->
    <!-- <div class="form-group col-md-12">
        <div class="form-group col-md-4">
            <form action="{{ url('userManagement/search') }}" method="post">
            {{csrf_field()}}
                <div class="input-group custom-search-form">
                    <input type="text" class="form-control" name="search" placeholder="Search..."><span class="input-group-btn">
                    <button class="btn btn-default-sm" type="submit"><i class="fas fa-search">search</i></button></span>
                </div>
            </form>
        </div> -->
    <!--END SEARCH BAR -->
    <div class="form-group col-md-6">
    <form action="{{ url('userManagement/getdob') }}" method="post">
    {{csrf_field()}}
    Birth Date:
    <input type="date" name="dob">
    To:
    <input type="date" name="todob">
    <input type="submit" name="submit" value="submit">
    </form>
    </div>

    <div class="form-group col-md-6">
    <form action="{{ url('userManagement/getage') }}" method="post">
    {{csrf_field()}}
    Age:
    <input type="text" name="age">
    To:
    <input type="text" name="toage">
    <input type="submit" name="submit" value="submit">
    </form>
    </div>

 <div class="form-group col-md-6">
    <form action="{{ url('userManagement/gethobby') }}" method="post">
    {{csrf_field()}}
    <input type="checkbox" name="hobby" value="cooking">cooking
    <input type="checkbox" name="hobby" value="reading">reading
    <input type="checkbox" name="hobby" value="sports">sports
    <input type="checkbox" name="hobby" value="travelling">travelling
    <input type="submit" name="submit" value="submit">
    </form>
    </div>
   
    </div>
    
<main class="page-content">
    <div class="container-fluid">
    <div class="row">
    <div class="form-group col-md-12">
    <!-- <a href="{{ route('userManagement.create')}} " class="btn btn-sm btn-success">Add User</a> -->
            <div class="col-md-12">
                 <!-- <form action="{{ url('userManagement/deleteAll') }}" method="post" name="delete_item" onsubmit="return confirm('Are you sure Delete Records?');">
                {{ csrf_field() }}
                <input type="submit" class="btn btn-danger" value="Delete Selected">
                </form> -->
                 <table id="bootstrap-data-table-export" class="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <!-- <th><input type="checkbox" class="checkbox_all"></th> -->
                            <th>ID</th>
                            <th>NAME</th>
                            <th>DOB</th>
                            <th>AGE</th>                                            
                            <th>GENDER</th>
                            <th>HOBBIES</th>
                            <th>CITY</th>
                            <th>STATUS</th>
                          <!--   <th colspan="3">ACTION</th> -->
                        </tr>
                    </thead>
                    <tbody>         
                        @foreach($abc as $data)
                        <tr>
                           <!--  <td><input type="checkbox" name="checkbox[]" value="{{ $data->id }}" /></td> -->
                            <td>{{$data->id}}</td>
                            <td>{{$data->name}}</td>
                            <td>{{$data->dob}}</td>
                            <td>{{$data->age}}</td>
                            <td>{{$data->gender}}</td>
                            <td>{{$data->hobby}}</td>
                            <td>{{$data->city_name}}</td>
                      <!-- <td>{{$data->status}}</td> -->
                             <td>
             @if($data->status == 1)
                  <a href="{{ url('userManagement/activePro',$data->id)}}" class="btn btn-sm btn-warning" >InActive</a>
                 @else
                 <a href="{{ url('userManagement/activePro',$data->id)}}" class="btn btn-sm btn-info">Active</a>
             @endif 
       </td>

                           <!--  <td>
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
                            </td>   -->
                        </tr>
                    @endforeach 
                </table>  
                 <!-- <div class="form-group col-md-6">
    
    </div> -->
                {!! $abc->links() !!} 
            </div>
        </div>
    </div>
    </div>
    
<!-- Date picker -->
 <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="https://unpkg.com/gijgo@1.9.11/js/gijgo.min.js" type="text/javascript"></script>
    <link href="https://unpkg.com/gijgo@1.9.11/css/gijgo.min.css" rel="stylesheet" type="text/css" />
  <script>
       $(document).ready(function () {
    $('#datepicker').datepicker({
      uiLibrary: 'bootstrap'
    });
});
    </script>


<!-- End Date picker -->

</body>   
</html>
@endsection
