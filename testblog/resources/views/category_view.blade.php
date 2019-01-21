<!DOCTYPE html>
<html>
<head>
	<title></title>
</head>
<body>
<table>
	<tr>
		<td>
	<ul>
		@foreach($abc as $cat)
		<li><a href="{{url('category/'.$cat->id)}}">{{$cat->title}}</a>
		</ul><a href = 'delete/{{ $cat->id }}'>Delete</a></li>
		</td>
			@endforeach
</tr>



</table>
</body>
</html>