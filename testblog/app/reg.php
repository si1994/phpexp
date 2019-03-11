<?php

namespace App;



use Illuminate\Database\Eloquent\Model;

class reg extends Model
{
     protected $fillable = ['name','dob','age','gender','status'];


	public function hobby()
    {
        return $this->hasMany('App\hobby');
    }
     
}
