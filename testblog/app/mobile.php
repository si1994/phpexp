<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class mobile extends Model
{
   	public function mobile()
   	{
   		return $this->belongsTo(User::class);
   	}
}
