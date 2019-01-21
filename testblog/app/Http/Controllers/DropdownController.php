<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use DB;

class DropdownController extends Controller
{
      public function index()
        {
            $countries = DB::table("countrie")->pluck("name","id");
            return view('index',compact('countries'));
        }

        public function getStateList(Request $request)
        {
            $states = DB::table("states")
            ->where("countrie_id",$request->country_id)
            ->pluck("name","id");
            return response()->json($states);
        }

        public function getCityList(Request $request)
        {
            $cities = DB::table("city")
            ->where("states_id",$request->state_id)
            ->pluck("name","id");
            return response()->json($cities);
        }
}
