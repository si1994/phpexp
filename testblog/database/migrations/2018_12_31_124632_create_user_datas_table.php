<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateUserDatasTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('user_datas', function (Blueprint $table) {
             $table->increments('id');
            $table->string('fname');
            $table->string('lname');
            $table->string('email');
            $table->string('mobile');
            $table->string('gender');
            $table->string('address');
            $table->string('password');
            $table->string('c_password');
            $table->string('profile_pic');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('user_datas');
    }
}
