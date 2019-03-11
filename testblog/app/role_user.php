<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class role_user extends Model
{
   public function user()
    {
        return $this->belongsto(user::class);
    }
}
