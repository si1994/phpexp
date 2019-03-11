
@extends('layouts.app')

@section('content')
<div class="container">
    <div class="row justify-content-center">
  @foreach($product as $products)
    <div class="card" style="width: 18rem;">
        <div class="card-body">
            <div class="card-title"><h3>{{ $products->name  }}</h3></div>
            <div class="card-title"><h3>{{ $products->price }}</h3></div>
            
        </div>
    </div>
    @endforeach
    </div>
</div>
@endsection