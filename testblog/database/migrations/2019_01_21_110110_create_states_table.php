<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateStatesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('countrie', function (Blueprint $table) {
            $table->increments('id');
            $table->string('name');
            $table->timestamps();
        });

        Schema::create('states', function (Blueprint $table) {
            $table->increments('id');
            $table->string('name');
             $table->integer('countrie_id'); 
            $table->timestamps();
        });

        Schema::create('city', function (Blueprint $table) {
            $table->increments('id');
            $table->string('name');
            $table->integer('states_id'); 
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
        // Schema::dropIfExists('states');
        Schema::drop('countries');
        Schema::drop('states');
        Schema::drop('citie');
    }   
}
