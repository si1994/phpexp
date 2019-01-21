<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('welcome');
});
	

Route::Resource('test', 'TestController');
//---------------------------------------------------------
Route::group(['middleware' => ['Admin']], function () {   
	
	Route::post('userManagement/search', 'UserDataController@search');
	//Route::post('userManagement','UserDataController@getPdf');
	Route::get('/downloadPDF/{id}','UserDataController@downloadPDF');
	Route::get('userManagement/getImport', 'UserDataController@getImport');
	Route::post('userManagement/importExcel', 'UserDataController@importExcel');
	Route::get('userManagement/export', 'UserDataController@export');	
	Route::get('userManagement', 'UserDataController@formValidation');
	Route::post('userManagement', 'UserDataController@formValidationPost');
	// Route::get('userManagement', 'UserDataController@getAll');
	// Route::delete('userManagement/deleteAll/{id}', 'UserDataController@deleteAll');
	Route::delete('userManagement/deleteAll', 'UserDataController@deleteAll')->name('userManagement.deleteAll');
	//Route::get('userManagement/hello', 'UserDataController@hello');
	//Route::get('userManagement/del/{id}', 'UserDataController@destroy');
	Route::Resource('userManagement', 'UserDataController');

});

Route::group(['middleware' => 'User'], function () { 

Route::get('client', 'UserDataController@view');

});
//--------------------------------------------------------
Route::get('prectice','precticeController@index');



Route::get('dropdownlist','DropdownController@index');
Route::get('get-state-list','DropdownController@getStateList');
Route::get('get-city-list','DropdownController@getCityList');
	

Route::get('category', 'CategoryController@index');
Route::get('category/{id}', 'CategoryController@show');
Route::get('delete/{id}', 'CategoryController@destroy');


Route::get('posts', 'PostsController@index');
Route::get('post/{id}', 'PostsController@show');


Route::get('/product/custom', 'ProductController@custom');
Route::Resource('product', 'ProductController');

Auth::routes();

Route::get('/home', 'HomeController@index')->name('home');
