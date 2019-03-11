<?php

namespace App;

use Illuminate\Notifications\Notifiable;
use Illuminate\Foundation\Auth\User as Authenticatable;

class User extends Authenticatable
{
    use Notifiable;
    
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'name', 'email', 'password', 'type',
    ];

    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    protected $hidden = [
        'password', 'remember_token',
    ];

    public function categories()
    {
        return $this->hasMany(Category::class);
    }

    
    public function account()
    {
       return $this->hasOne(Account::class); 
    }

    public function mobile()
    {
        return $this->hasmany(mobile::class);
    }

    public function role()
    {
        return $this->belongstomany(role::class);
    }
    public function role_user()
    {
        return $this->belongstomany(role_user::class);
    }

}
