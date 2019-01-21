<!DOCTYPE html>
<html>
<head>
	<title></title>
</head>
<body>
<ul>
	<h1>{{ $page}}</h1>
	@foreach($posts as $post)
	
	<li><a href="{{url('post/'.$post->id)}}">{{$post->title}}</a></li>
	@endforeach


</ul>
</body>
</html>