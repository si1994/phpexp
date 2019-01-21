<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class userData extends Model
{
     protected $fillable = ['fname','lname', 'email', 'mobile','gender','address','password','profile_pic'];
}
