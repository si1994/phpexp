<?php

namespace App\Http\Controllers;
use Illuminate\Http\Request;
use App\userData;
use Illuminate\Foundation\Validation\ValidatesRequests;
use Maatwebsite\Excel\Facades\Excel;
use PDF;

class UserDataController extends Controller
{

    public function index() 
    {	
    	//pagination $posts = Post::orderby('id', 'desc')->paginate(5);
    	$abc = userData::latest()->paginate(4);
       	return view('users.showUsers',compact('abc'))
            ->with('i', (request()->input('page', 1) - 1) * 4);

       	//first time simple data show karva mate
    	// $abc = userData::all();
        // return view('users.showUsers',compact('abc'));
    }

    public function view(Request $request)
    {
    	return view('users.userview');
    }


    public function create(Request $request)
    {
    	//registration form view karva mate
    	 return view('users.create');
    }



    public function store(Request $request)
    {
    	//insert
    	$user = new userData(); //object create for model

    	//validation 
    	$this->validate($request, 
    	[
		    'fname' => 'required|max:50|min:3|alpha',
	         'lname'=> 'required|max:50|min:3|alpha',
	         'email'=> 'required|email|unique:users',
	         'mobile'=> 'required|min:10|max:10',
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
       
    	//single image upload
  // 		$file = $request->file('profile_pic');
		// $destinationPath = public_path('uploads');
 	// 	$file->move($destinationPath,time().'-'.$file->getClientOriginalName());

     	//data insert
		$user->fname = $request->fname;
		$user->lname = $request->lname;
		$user->email = $request->email;	
		$user->mobile = $request->mobile;
		$user->gender = $request->gender;
		$user->address = $request->address;
		$user->password = bcrypt($request['password']);
		//$user->profile_pic = time().'-'.$file->getClientOriginalName();//unique image name store karva mate time().'-'.

		$user->profile_pic=json_encode($data);
         
		$user->save();	
		return redirect('userManagement')->with('success', 'Insert successfully.....');


	}



	public function show($id)
   	{
   		//perticular id show karva mate
   		$pertId= userData::find($id);
	   
	   	if ($pertId === null)	
		{
  			//echo "not availeble id";
  			// $model = userData::all();
			// dd($model);
			return redirect('userManagement')->with('success', 'Id is not available.....pleace enter correct id');
		}
		
		return view('users.perticulaeId',compact('pertId'));


   	}




    public function edit($id) 
    {
		$customer = userData::find($id);
		//show the edit form
		return view('users.editUsers',compact('customer'));
	}




	public function update(Request $request, $id)
    {
    	 
    	$new = userData::find($id);

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
		$new->lname = $request->lname;
		$new->email = $request->email;	
		$new->mobile = $request->mobile;
		$new->gender = $request->gender;
		$new->address = $request->address;
		
		if ($request->hasFile('profile_pic')) // image update karva mate
		{
			$new->profile_pic = json_encode($data);
			 // $image_path =  public_path().'/uploads/'.$new->profile_pic;
	   		
		  //   if(\File::exists($image_path))//public folder mathi image update karva mate
		  //   {
		  //   	$new->profile_pic=json_encode($data);
		       
		  //      unlink($image_path);//aagd ni image folder mathi unlink(delete)karva mate
		  //   }
		}
		$new->save();  
		// $request->session()->flash('success', 'Update successfully.....');
		return redirect('userManagement')->with('success', 'Update successfully.....'); 

		//return redirect()->route('userManagement.index')
                     //  ->with('success','insert successfully.....'); 
	}




    public function destroy(Request $request,$id)
	{

	    $employee = userData::find($id);

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

		return redirect('userManagement')->with('success', 'Delete successfully.....'); 
	    // simple delete karva mate

	    // $employee = userData::find($id); 
	    // $employee->delete();
	    // return redirect('userManagement');	

	}


	public function deleteAll(Request $request)
    {
        $user = $request->input('checkbox');
        //dd($user);
        foreach ($user as $users) 
        {
            $user = userData::findOrFail($users);
            $user->delete();
        }
        return redirect('userManagement')->with('success', 'Delete successfully.....'); 
    }


	public function getImport()
    {
        return view('users.import');
    }
	


	public function importExcel(Request $request)
    {
	    if($request->file('imported-file'))
	    {
 
	        $path = $request->file('imported-file')->getRealPath();
            $data = Excel::load($path, function($reader){})->get();
            

            if(!empty($data) && $data->count())
      		{
		        $data = $data->toArray();
		        for($i=0;$i<count($data);$i++)
		        {
		          $dataImported[] = $data[$i];
		        }
            }
      	userData::insert($dataImported);
        }
	    return redirect('userManagement')->with('success', 'Import successfully.....'); 
    }




    public function export()
    {
	    $items = userData::all();
	    Excel::create('UserData', function($excel) use($items) 
	    {
	    	$excel->sheet('ExportFile', function($sheet) use($items) 
	    	{
	              $sheet->fromArray($items);
	        });
	    })->export('xls');
    }




    public function downloadPDF($id)
    {
	    $user = userData::find($id);
	    //public_path().'/uploads/'.$user->$profile_pic;
		$pdf = PDF::loadView('users.pdf', compact('user'));	
		return $pdf->download('UserData.pdf');
		
    }	



public function search(Request $request)
    {
    	   
    	$search =  $request->input('search');
    	
	    $users = userData::where('fname','LIKE','%'.$search.'%')->paginate
	    	(5);
	    if(count($users))
	    {
	   		 return view('users.search',compact('users'));
	   	}
	   	else
	   	{
			return redirect('userManagement')->with('success', 'User is not available.');
		}
	    return redirect('userManagement');
    }



	public function hello()
	{
	     dd('dkjdjc');
	   
	} 

	
}


              