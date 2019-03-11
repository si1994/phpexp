<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class hobby extends Model
{
	protected $table = 'hobbys';
    protected $fillable = ['hobby_name','user_id'];

    public function reg()
    {
        return $this->belongsTo(reg::class);
    }
}
