@extends('layouts.app')

@section('content')


<!DOCTYPE html>
<html>
<head>
	<title></title>
</head>
<body>
@if (count($errors) > 0)
   <div class = "alert alert-danger">
      <ul>
         @foreach ($errors->all() as $error)
            <li>{{ $error }}</li>
         @endforeach
      </ul>
   </div>
@endif
<td><a href = 'prectice/create' class="btn btn-danger">Add</a></td>
		</tr>
<form>

	<table class="table">
	<tr>
		@foreach($abc as $ab)
		<td>{{$ab->id}}</td>
		<td>{{$ab->name}}</td>
		<td>{{$ab->email}}</td>
		<td>{{$ab->password}}</td>
		<td>{{$ab->birthdate}}</td>
		<td>{{$ab->image}}</td>
		<td><a href="{{action('precticeController@edit', $ab['id'])}}" class="btn btn-warning">Edit</a></td>
        <td><a href = 'delet/{{ $ab->id }}' class="btn btn-danger">Delete</a></td>
		</tr>
		@endforeach
	</table>
</form>


</body>
</html>


@endsection