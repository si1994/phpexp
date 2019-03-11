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
        <h1 class="text-center"> Category Information </h1>
    </div>

    

<main class="page-content">
    <div class="container-fluid">
    <div class="row">
    <div class="form-group col-md-12">
    <!-- <a href="{{ route('userManagement.create')}} " class="btn btn-sm btn-success">Add User</a> -->
            <div class="col-md-12">
                
                <table class="table">
                    <tr>
                        
                        <th>ID</th>
                        <th>TITLE</th>
                        <th>DESCRIPTION</th>
                        <TH>CREATED BY</TH>
                        <TH>CREATE AT</TH>
                        <th colspan="3">OPRATION</th>   
                    </tr>
                    <tr>

                         @foreach($categories as $category)
                        <td>{{$category->id}}</td>
                        <td>{{ $category->title }}</td>
                        <td>{{ $category->description }}</td>
                        <td>Created By: <b>{{ $category->user->name }}</b></td>
                        <td>Created At: {{ $category->created_at->diffForHumans() }} </td>
                        <td>
                            <a href="{{ url('one',$category->id) }}" class="btn btn-sm btn-success">Account No.<span class="glyphicon glyphicon-eye-open"></span></a>
                        </td>
                        <td>
                            <a href="{{ route('userManagement.show',$category->id) }}" class="btn btn-sm btn-success">View<span class="glyphicon glyphicon-eye-open"></span></a>
                        </td>
                        <td>
                            <a href="{{ route('userManagement.show',$category->id) }}" class="btn btn-sm btn-success">View<span class="glyphicon glyphicon-eye-open"></span></a>
                        </td>

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

