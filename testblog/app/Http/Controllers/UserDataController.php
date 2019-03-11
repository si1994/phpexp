<?php

namespace App\Http\Controllers;
use Mail;
use Illuminate\Http\Request;
use App\userData;
use App\contect;
use App\reg;
use App\hobby;
use App\Plan;
use DB;
use Illuminate\Foundation\Validation\ValidatesRequests;
use Maatwebsite\Excel\Facades\Excel;
use PDF;
use Carbon\Carbon;
use App\Mail\mailme;
use App\Invite;
use App\Mail\InviteCreated;
use Validator;
use Stripe;
use App\User;



class UserDataController extends Controller
{
	// public function __construct()
 //    {
 //        parent::__construct();
 //        $this->user = new User;
 //    }
	
    public function index()
    {	
    	//pagination $posts = Post::orderby('id', 'desc')->paginate(5);
    	$abc = userData::latest()->paginate(4);
       	return view('users.showUsers',compact('abc'));
           // ->with('i', (request()->input('page', 1) - 1) * 4);

       	//first time simple data show karva mate
    	// $abc = userData::all();
        // return view('users.showUsers',compact('abc'));
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


	public function destroy($id)
	{
	    $model = userData::findOrFail($id);

	    $model->delete();

	    \Session::flash('flash_message_delete','Model successfully deleted.');

	    return redirect('userManagement');
	}

	// protected static function boot()
	// {
	//     userData::boot();
	    
	//     // static::deleting...
	    
	//     userData::restoring(function ($group) 
	//     {
	//         $groups->questions()
	//             ->onlyTrashed()
	//             ->whereBetween('deleted_at', $group->getDeletedAtRange())
	//             ->restore();
	//     });
	// }

	public function trash($id)
	{
		//$id= userData::find($id);
		//$abc = userData::withTrashed()->get();
		$abc = userData::onlyTrashed()->find($id)->restore();
		return $abc;
	}

	public function restore($id)
	{
		//dd("hello");
	    //userData::onlyTrashed()->where('id',$id)->restore();
	    userData::withTrashed()->find($id)->restore();
	    return redirect('userManagement');
	}




 //    public function destroy(Request $request,$id)
	// {

	//     $employee = userData::find($id);

	//     foreach(json_decode($employee->profile_pic, true) as $images) 
	//     {

	//     	//image public folder mathi delete karva mate
	// 	    $image_path =  public_path().'/uploads/'.$images;
	// 	    //dd($image_path);
	// 	    if(\File::exists($image_path))
	// 	    {
	// 	        \File::delete($image_path);
	// 	        //unlink($image_path);
	// 	    }
	// 	}
	// 	$employee->delete();

	// 	return redirect('userManagement')->with('success', 'Delete successfully.....'); 
	//     // simple delete karva mate

	//     // $employee = userData::find($id); 
	//     // $employee->delete();
	//     // return redirect('userManagement');	

	// }


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
    	
	    $abc = userData::where('fname','LIKE','%'.$search.'%')->paginate
	    	(4);
	    if(count($abc))
	    {
	   		// return view('users.search',compact('users'));
	   		 return view('users.showUsers',compact('abc'))
            ->with('i', (request()->input('page', 1) - 1) * 4);
	   	}
	   	else
	   	{
			return redirect('userManagement')->with('success', 'User is not available.');
		}
	    return redirect('userManagement');
    }


    public function getdate(Request $request)
    {
    	$fromDate=$request->input('created_at');
    	$toDate=$request->input('updated_at');
    	$abc=userData::whereBetween('created_at',[$fromDate,$toDate])->paginate
	    	(4);
		 //return $posts;
		 return view('users.showUsers',compact('abc'));
            //->with('i', (request()->input('page', 1) - 1) * 4);
	}

	// public function view(Request $request)
 //    {
 //    	return view('users.userview');
 //    }
	


	public function users() //  show templet userside
	{
		$abc = userData::all();
	    return view('users.userview',compact('abc'));
	}

	public function process(Request $request)
	{

		//===============SUCCESS======================
		// $to_name = 'Brinda';
		// $to_email = 'brinda.bhuva@ifuturz.com';
		// $data = array('name'=>"Sam Jose", "body" => "Test mail");
		    
		// Mail::send('users.email', $data, function($message) use ($to_name, $to_email) {
		//     $message->to($to_email, $to_name)
		//             ->subject('Artisans Web Testing Mail');
		//     $message->from('krishna.motivarsh@ifuturz.com','Artisans Web');
		// });
		//==================================================

	   $this->validate($request, 
	   	[ 	'name' => 'required', 
	   		'email' => 'required|email', 
	   		'subject' => 'required',
	   		'message' => 'required' ]);
	    contect::create($request->all());

	    Mail::send('users.email',
	       array(
	           'name' => $request->get('name'),
	           'email' => $request->get('email'),
	           'subject' => $request->get('subject'),
	           'msg' => $request->get('message')
	       ), function($message)
	   {
	       $message->from('brinda.bhuva@ifuturz.com');
	       $message->to('brinda.bhuva@ifuturz.com', 'Admin')->subject('contect');
	   });
	    return back()->with('success', 'successfully Message Send.');
	}


 
    public function contectShow()
	{
		$abc = contect::latest()->paginate(4);
       	return view('users.contectUsers',compact('abc'));
	}


	public function RegisterUser(Request $request)
	{
		$reg = new reg();
		$reg->name = $request->name;
		$reg->dob = $request->dob;
		$dob=$request->input('dob');
		$reg->age = Carbon::parse($dob)->age;
		$reg->gender = $request->gender;
		$reg->hobby =implode(',',$request->hobby);
		$reg->city_id=$request->city;
		$reg->status = $request->status;
		$reg->save();
		return back();			
	}


	public function registerGet()
	{
		//$id=7;
		
		//$id = reg::all();
		//dd($id);
		
       	$abc= DB::table('regs')
                   ->join('hobbys', 'regs.city_id', '=', 'hobbys.city_id')
                   ->select('regs.*','hobbys.*')
                   // ->where(['hobbys.user_id' => $id])
                   ->paginate(4);
            // dd('paginate');  
		 //$abc = reg::latest()->paginate(4);
       	return view('users.regShow',compact('abc'));	

	}
	

	public function getdob(Request $request)
    {
    	$dob=$request->get('dob');
    	$todob=$request->get('todob');
       	$abc= DB::table('regs')
                   ->join('hobbys', 'regs.city_id', '=', 'hobbys.city_id')
                   ->select('regs.*','hobbys.*')
                   ->whereBetween('dob',[$dob,$todob])->paginate(4);
         //$abc=reg::
		return view('users.regShow',compact('abc'));
	}
	

	public function getage(Request $request)
    {
    	$age=$request->get('age');
    	$toage=$request->get('toage');
       	$abc= DB::table('regs')
                   ->join('hobbys', 'regs.city_id', '=', 'hobbys.city_id')
                   ->select('regs.*','hobbys.*')
                   ->whereBetween('age',[$age,$toage])->paginate(4);
         //$abc=reg::
		return view('users.regShow',compact('abc'));


  //   	$age=$request->get('age');
  //   	$toage=$request->get('toage');
  //   	$abc=reg::whereBetween('age',[$age,$toage])->paginate(4);
		// return view('users.regShow',compact('abc'));
	}

	public function gethobby(Request $request)
    {

    	$hobby = $request->input('hobby');
    	
       	$abc= DB::table('regs')
                   ->join('hobbys', 'regs.city_id', '=', 'hobbys.city_id')
                   ->select('regs.*','hobbys.*')
                   ->where('hobby','LIKE','%'.$hobby.'%')->paginate(4);
	    	//dd($abc);
		if(count($abc))
		    {
		   		// return view('users.search',compact('users'));
		   		 return view('users.regShow',compact('abc'))
	            ->with('i', (request()->input('page', 1) - 1) * 4);
		   	}
		   	else
		   	{
				return view('users.regShow',compact('abc'));
			}
			return view('users.regShow',compact('abc'));
	}



	public function activePro(Request $request, $id)
   {

   		
	   	 // $abc = reg::find($name);
	   	$abc= reg::select('*')->where('id', $id)->get();	   
	    
	  	// dd($abc);
	   	 if($abc[0]['attributes']['status'] == '0')
	   	 {


	   	 	$abcd = [
	                      "status" => 1,
	                   
	                ];
	   	 }
	   	 else
	   	 {
	   	 	$abcd = [
	                      "status" => 0,
	                   
	                  ];


	   	 }
	    if(reg::where("id",$id)->update($abcd))
	    {$abc= DB::table('regs')
                   ->join('hobbys', 'regs.city_id', '=', 'hobbys.city_id')
                   ->select('regs.*','hobbys.*')
                   // ->where(['hobbys.user_id' => $id])
                   ->paginate(4);

	    	//$abc= reg::select('*')->paginate(4);
	    	//dd($abc);
               }
               // else{
               // 	dd("error");
               // }	   
 

	    return view('users.regShow',compact('abc'));
	     //return Redirect('regShow')->with("info","Product Activated Successfully");
  	}

  	// How can you get users IP address in Laravel ?
	public function getUserIp(Request $request)
	{
		// Getting ip address of remote user
		return $user_ip_address=$request->ip();
	}

	 public function payWithStripe()
    {
        return view('users.paywithstripe');
    }

	 public function postPaymentWithStripe(Request $request)
    {
    	//$this->user = new User;
        $validator = Validator::make($request->all(), [
            'card_no' => 'required',
            'ccExpiryMonth' => 'required',
            'ccExpiryYear' => 'required',
            'cvvNumber' => 'required',
            'amount' => 'required',
        ]);
       
        $input = $request->all();
        if ($validator->passes()) 
        {           
            $input = array_except($input,array('_token'));   
            //dd($input);         
           	$stripe = \Stripe\Stripe::setApiKey("sk_test_QD9TTwjUU77tI9BYMjwNSnBi");
             
           // dd($stripe);
            try 
            {

                $token = \Stripe\Token::create([
                    'card' => [
                        'number'    => trim($request->card_no),
                        'exp_month' => $request->ccExpiryMonth,
                        'exp_year'  => $request->ccExpiryYear,
                        'cvc'       => $request->cvvNumber,
                    ],
                ]);
                //dd($token);
                if (!isset($token['id']))
                {
                    \Session::put('error','The Stripe Token was not generated correctly');
                     return back();
                }

                $charge = \Stripe\Charge::create([
                    'card' => $token['id'],
                    'currency' => 'USD',
                    'amount'   => $request->get('amount'),// * 0.0141, convert rupee to doller 
                    'description' => 'Add in wallet',
                ]);
                if($charge['status'] == 'succeeded') 
                {
                	
                    /**
                    * Write Here Your Database insert logic.
                    */
                    \Session::put('success','Money add successfully in wallet');
                    return redirect('userManagement/stripe');
                } 
                else 
                {
                    \Session::put('error','Money not add in wallet!!');
                    return back();
                }
            }
            catch (Exception $e) 
            {
                \Session::put('error',$e->getMessage());
                return back();
            } 
            catch(\Cartalyst\Stripe\Exception\CardErrorException $e) 
            {
                \Session::put('error',$e->getMessage());
                return back();
            } 
            catch(\Cartalyst\Stripe\Exception\MissingParameterException $e) 
            {
                \Session::put('error',$e->getMessage());
                return back();
            }
        }
        \Session::put('error','All fields are required!!');
       	return back();
    } 





    public function pay(Request $request)
    {
        Stripe::setApiKey('sk_test_QD9TTwjUU77tI9BYMjwNSnBi');
 
        $token = request('stripeToken');
 
        $charge = Charge::create([
            'amount' => 1000,
            'currency' => 'usd',
            'description' => 'Test Book',
            'source' => $token,
        ]);
 
        return 'Payment Success!';
    }

	public function payment()
    {
    	 return view('users.payment'); 
    }
	// public function pay()
 //    {
 //    	  echo "hello";
 //    }
	  
	public function hello()
	{
	   echo "hello";
	}
}


		