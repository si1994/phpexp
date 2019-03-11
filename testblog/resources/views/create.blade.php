@extends('layouts.app')

@section('content')
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header">Create Category</div>
                <form action="{{ route('category.store') }}" method="post">
                 {{csrf_field()}}
                    <div class="card-body">                      
                        <label>Title</label>
                        <input type="text" class="form-control" name="title" />
                        <label>description </label>
                        <input type="text" class="form-control" name="description" />
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