<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class userData extends Model
{
	use SoftDeletes;

	protected $dates = ['deleted_at'];

    protected $fillable = ['fname','lname', 'email', 'mobile','gender','address','password','profile_pic'];

    // public function hobby()
    // {
    //     return $this->hasmany(hobby::class);
    // }
    
}

