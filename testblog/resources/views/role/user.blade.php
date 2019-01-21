@extends('layout.default')
@section('nav')
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" target="_blank"
                   href="{{env('website_url')}}/easy-crud-with-laravel-55-step-by-step-tutorial.html">View Post</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" target="_blank"
                   href="javascript:document.getElementById('logout-form').submit();">Logout</a>
            </li>
        </ul>
        <form id="logout-form" action="{{ route('logout') }}" method="POST"
              style="display: none;">
            {{ csrf_field() }}
        </form>
    </div>
@endsection
@section('content')
    <div class="container" style="background: white">
        <br/>
        <h1>User</h1>
        <hr/>
        <br/>
    </div>
@endsection