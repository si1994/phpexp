
@extends('layouts.app')

@section('content')


<!DOCTYPE html>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
   
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<html>
<head>
    <title></title>

    <!-- END SUCCESS MSG DISPLAY SHOW AND HIDE -->
</head>

<body>
    <div class="form-group col-md-12">                
        <h1 class="text-center">Contect Information </h1>
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
        </div>
    <!--END SEARCH BAR -->
    <div class="form-group col-md-5">
    
<main class="page-content">
    <div class="container-fluid">
    <div class="row">
    <div class="form-group col-md-12">
            <div class="col-md-12">          
                <table class="table">
                    <tr>  
                        <th>ID</th>
                        <th>NAME</th>
                        <th>EMAIL</th>
                        <th>SUBJECT</th> 
                        <th>MESSAGE</th> 
                    </tr>
                    <tr>
                        @foreach($abc as $data)
                        <td>{{$data->id}}</td>
                        <td>{{$data->name}}</td>
                        <td>{{$data->email}}</td>
                        <td>{{$data->subject}}</td>
                        <td>{{$data->message}}</td>   
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


