<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\category;
use App\User;
use App\Account;
use App\mobile;


class CategoryController extends Controller
{

	// public function index()
	// {
	// 	$abc=category::all();
	// 	return view('category_view',compact('abc'));
	// }
	public function index()
	{
	    $categories = Category::get();

	    return view('category_view', compact('categories'));
	}



	
    public function one($id)
    {   
    	//dd("hello");

    	//$abc = user::all();
    	$account = account::find(5)->user;	
        //$account = user::find(5)->account;

        return $account;
       // return view('account', compact('account'));
    }
   

    public function onetomany()
    {
    	$mobile = user::find(5)->mobile;
    	return $mobile;
    }

    public function manytomany()
    {
    	$role = user::find(5)->role;
    	return $role;
    }

}
