<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\category;

class CategoryController extends Controller
{

	public function index()
	{
		$abc=category::all();
		return view('category_view',compact('abc'));
	}

	public function show($id)
   {
	   	$cat= category::find($id);
	   	return view('show_category',compact('cat'));
   }

    public function destroy($id)
	{
	     $employee = category::find($id);
	     $employee->delete();
	     echo "Record deleted successfully";
	    // return Redirect::route('noorsi.employee.index');
	}   
}
