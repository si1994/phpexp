<!DOCTYPE html>
<html>
<head>
	<title></title>
</head>
<body>


<form method="post" action="{{ route('shares.store') }}">
          <div class="form-group">
              @csrf
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
              <input type="text" class="form-control" name="birthdate"/>
          </div>
          <button type="submit" class="btn btn-primary">Submit</button>
      </form>


</body>
</html>