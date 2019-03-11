<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class stripe extends Model
{
    protected $fillable = ['card_no','ccExpiryMonth', 'ccExpiryYear', 'cvvNumber','amount'];
}
