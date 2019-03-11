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
        <h1 class="text-center"> Account Information </h1>
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
                        <th>USER ID</th>
                        <th>ACCOUNT NO</th>
                    </tr>
                    <tr>
                     
                        <td>{{ $account->id }}</td>
                        <td>{{ $account->user_id }}</td>
                        <td>{{ $account->account_number }}</td>
                    </tr>
                   
                </table>    
            </div>
        </div>
    </div>
    </div>  
</body>   
</html>
@endsection

