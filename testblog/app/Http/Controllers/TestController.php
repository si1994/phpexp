<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class TestController extends Controller
{
     public function index() 
    {	
    	//pagination 
    	
       	return view('users.showUsers');
           

       	//first time simple data show karva mate
    	// $abc = userData::all();
        // return view('users.showUsers',compact('abc'));
    }
}
