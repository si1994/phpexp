<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class contect extends Model
{
    
     protected $fillable = ['name', 'email', 'subject','message','token'];

}
