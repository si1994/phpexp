@extends('layouts.app')

@section('content')
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header">Create product</div>
                <form action="{{ route('product.store') }}" method="post">
                 {{csrf_field()}}
                    <div class="card-body">                      
                        <label>name</label>
                        <input type="text" class="form-control" name="name" />
                        <label>price </label>
                        <input type="text" class="form-control" name="price" />
                    </div>
                    <div class="card-footer">
                        <button type="submit" class="btn btn-primary">Add</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
@endsection