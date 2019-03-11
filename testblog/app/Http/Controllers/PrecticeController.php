<?php

namespace App\Http\Controllers;
use App\prectice;
use Illuminate\Http\Request;

class PrecticeController extends Controller
{

	 public function index()
    {
       	$abc = prectice::all();   
        return view('practice.show',compact('abc'));
    }

   public function create()
	{
	   return view('practice.form');
	}

	public function store(Request $request)
    {
        	$crud = new prectice();

        	$file = $request->file('image');
			$destinationPath = public_path('uploads');
	 	 	$file->move($destinationPath,time().'-'.$file->getClientOriginalName());
	 	 	$crud->name = $request->name;
	 	 	$crud->email = $request->email;
	 	 	$crud->password = $request->password;
	 	 	$crud->birthdate = $request->birthdate;       	
          	$crud->image = time().'-'.$file->getClientOriginalName();
	        $crud->save();
	        return redirect('/prectice')->with('success', 'New support ticket has been created! Wait sometime to get resolved');
    }


     public function edit($id)
    {
        $crud = prectice::find($id);
        
        return view('practice.edit', compact('crud','id'));

    }


    public function update(Request $request, $id)
    {
    		//return($id);die();
        	$crud = prectice::find($id);  
        	$crud->name = $request->name;
          	$crud->email  = $request->email;
          	$crud->password  = $request->password;
          	$crud->birthdate  = $request->birthdate;
	        $crud->save();
	        return redirect('/prectice');
    }


    public function destroy(Request $request,$id)
    {

        $ticket = prectice::find($id);
        $ticket->delete();

        return redirect('/prectice')->with('success', 'Data has been deleted!!');
    }

}
