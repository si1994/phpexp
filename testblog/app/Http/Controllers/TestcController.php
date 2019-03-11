<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\testc;

class TestcController extends Controller
{
	public function index() 
    {	
    	// $abc = userData::latest()->paginate(4);
     //   	return view('users.showUsers',compact('abc'))
     //        ->with('i', (request()->input('page', 1) - 1) * 4);
       	//first time simple data show karva mate
    	$abc = testc::all();
        return view('testc.showUsers',compact('abc'));
    }

    public function create(Request $request)
    {
    	//registration form view karva mate

    	 return view('testc.create');
    }

     public function store(Request $request)
    {
    	
    	$user = new testc(); 
    	$this->validate($request, 
    	[
		    'fname' => 'required|max:50|min:3|alpha',
	         'email'=> 'required|email',
	         'gender'=> 'required',
	         'address'=> 'required',
	         'password'=> 'required|min:6', 
	         'c_password'=> 'required|min:6|same:password',
	         'profile_pic'=> 'required',
		]);
    	 if($request->hasfile('profile_pic'))
         {
            foreach($request->file('profile_pic') as $image)
            {
                $name=time().'-'.$image->getClientOriginalName();
                $image->move(public_path().'/uploads/',$name);  
                $data[] =$name;
            } 
         }

		$user->fname = $request->fname;
		$user->email = $request->email;	
		$user->gender = $request->gender;
		$user->address = $request->address;
		$user->password = bcrypt($request['password']);
		$user->c_password = bcrypt($request['c_password']);
		$user->profile_pic=json_encode($data);

		$user->save();

		return redirect('test')->with('success', 'Insert successfully.....');
	}

	public function show($id)
   	{
   		
   		$pertId= testc::find($id);
	   
	 //   	if ($pertId === null)	
		// {
		// 	return redirect('test')->with('success', 'Id is not available.....pleace enter correct id');
		// }		
		return view('testc.one',compact('pertId'));
   	}

   	public function destroy(Request $request,$id)
	{
	    $employee = testc::find($id);
	    foreach(json_decode($employee->profile_pic, true) as $images) 
	    {
	    	//image public folder mathi delete karva mate
		    $image_path =  public_path().'/uploads/'.$images;
		    //dd($image_path);
		    if(\File::exists($image_path))
		    {
		        \File::delete($image_path);
		        //unlink($image_path);
		    }
		}
		$employee->delete();
		return redirect('test')->with('success', 'Delete successfully.....'); 
	    // simple delete karva mate
	    // $employee = userData::find($id); 
	    // $employee->delete();
	    // return redirect('userManagement');	
	}

	 public function edit($id) 
    {
		$customer = testc::find($id);
		//show the edit form
		return view('testc.edit',compact('customer'));
	}


	public function update(Request $request, $id)
    {
    	 
    	$new = testc::find($id);
		if ($request->hasFile('profile_pic')) //update other fild without image update karya vagr 
  		{
	    	foreach($request->file('profile_pic') as $image)
            {
				$name=time().'-'.$image->getClientOriginalName();
                $image->move(public_path().'/uploads/',$name);  
                $data[] = $name;
			}
		}
     	$new->fname = $request->fname;
		$new->email = $request->email;	
		$new->gender = $request->gender;
		$new->address = $request->address;
		
		if ($request->hasFile('profile_pic')) // image update karva mate
		{
			$new->profile_pic = json_encode($data);
			
		}
		$new->save();  
		// $request->session()->flash('success', 'Update successfully.....');
		return redirect('test')->with('success', 'Update successfully.....'); 
		//return redirect()->route('userManagement.index')
                     //  ->with('success','insert successfully.....'); 
	}



}
