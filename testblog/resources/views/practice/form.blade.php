@extends('layouts.app')

@section('content')
<!DOCTYPE html>
<html>
<head>
	<title></title>
</head>
<body>


<form method="post" action="{{ url('prectice') }}" enctype="mutipart/form-data">
          <div class="form-group">
             {{csrf_field()}}
              <label for="name">Name:</label>
              <input type="text" class="form-control" name="name"/>
          </div>
          <div class="form-group">
              <label for="price">Email:</label>
              <input type="text" class="form-control" name="email"/>
          </div>
          <div class="form-group">
              <label for="quantity">Password:</label>
              <input type="text" class="form-control" name="password"/>
          </div>
          <div class="form-group">
              <label for="quantity">birthdate:</label>
              <input type="date" class="form-control" name="birthdate"/>
          </div>
          <div class="form-group">
              <label for="quantity">Image:</label>
              <input type="file" class="form-control" name="profile_pic[]" multiple="">
          </div>
          <button type="submit" class="btn btn-primary">Submit</button>
      </form>


</body>
</html>
@endsection