<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title></title>
  </head>
  <body>
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
    
    
    
  </tr>
  <tr>
    
    <td>{{$user->id}}</td>
    <td>{{$user->fname}}</td>
    <td>{{$user->lname}}</td>
    <td>{{$user->email}}</td>
    <td>{{$user->mobile}}</td>
    <td>{{$user->gender}}</td>  
    <td>{{$user->address}}</td>
    <td>
    @foreach(json_decode($user->profile_pic,true) as $version)

      <img src="{{ public_path('/uploads/'.$version) }}" width="40px" height="40px"/>

    @endforeach
</td>
    
   </tr>
   
</table>  
  </body>
</html>