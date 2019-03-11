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
	

//Route::post('user/register', 'APIRegisterController@register');
Route::get('user', 'APIloginController@create');
//Route::post('user/login', 'APIloginController@store');
Route::post('user/login', 'APIloginController@register');

Route::Resource('test', 'TestController');

//---------------------------------------------------------

Route::group(['middleware' => ['Admin']], function () {


Route::get('/make-payment', 'UserDataController@pay');
	
	Route::get('userManagement/stripe', 'UserDataController@payWithStripe');
	Route::post('userManagement/stripepost', 'UserDataController@postPaymentWithStripe');
	Route::get('userManagement/payment', 'UserDataController@payment');
	Route::post('userManagement/pay', 'UserDataController@pay');
	Route::get('userManagement/restore/{id}','UserDataController@restore');
	Route::get('userManagement/trash/{id}','UserDataController@trash');
	Route::post('userManagement/gethobby', 'UserDataController@gethobby');
	Route::get('userManagement/activePro/{id}','UserDataController@activePro');
	Route::get('userManagement/register', 'UserDataController@registerGet');
	Route::get('userManagement/contect', 'UserDataController@contectShow');
	Route::post('userManagement/getage', 'UserDataController@getage')->name('userManagement.getage');
	Route::post('userManagement/getdob', 'UserDataController@getdob')->name('userManagement.getdob');
	Route::post('userManagement/getdate', 'UserDataController@getdate')->name('userManagement.getdate');
	//Route::post('userManagement/getdate', 'UserDataController@getdate');
	Route::post('userManagement/search', 'UserDataController@search')->name('userManagement.search');
	Route::get('/downloadPDF/{id}','UserDataController@downloadPDF');
	Route::get('userManagement/getImport', 'UserDataController@getImport');
	Route::post('userManagement/importExcel', 'UserDataController@importExcel');
	Route::get('userManagement/export', 'UserDataController@export');	
	Route::get('userManagement', 'UserDataController@formValidation');
	Route::post('userManagement', 'UserDataController@formValidationPost');
	// Route::get('userManagement', 'UserDataController@getAll');
	Route::delete('userManagement/deleteAll', 'UserDataController@deleteAll')->name('userManagement.deleteAll');
	Route::post('userManagement/hello', 'UserDataController@hello');
	//Route::get('userManagement/del/{id}', 'UserDataController@destroy');
	Route::get('userManagement/getUserIp','UserDataController@getUserIp');
	Route::get('create','UserDataController@create');
	Route::Resource('userManagement', 'UserDataController');
});



Route::group(['middleware' => 'User'], function () {
Route::get('users', 'UserDataController@users')->name('users');
Route::post('users/clientstore', 'UserDataController@process')->name('process');
Route::post('users/RegisterUser','UserDataController@RegisterUser')->name('RegisterUser');


});
//--------------------------------------------------------

Route::get('test/create', 'TestcController@create');
Route::post('test/store', 'TestcController@store');
Route::get('test', 'TestcController@index');
Route::get('test/show/{id}','TestcController@show');
Route::delete('test/delete/{id}','TestcController@destroy');
Route::get('test/edit/{id}','TestcController@edit');
Route::put('test/update/{id}','TestcController@update');



Route::get('prectice/create','precticeController@create');
Route::post('prectice','precticeController@store');
Route::get('prectice','precticeController@index');
Route::get('prectice/edit/{id}','precticeController@edit');
Route::post('prectice/update/{id}','precticeController@update');
Route::get('delet/{id}','precticeController@destroy');
//Route::resource('prectice', 'precticeController');


Route::get('dropdownlist','DropdownController@index');
Route::get('get-state-list','DropdownController@getStateList');
Route::get('get-city-list','DropdownController@getCityList');
	


// Route::get('category/{id}', 'CategoryController@show');
// Route::get('delete/{id}', 'CategoryController@destroy');
Route::get('/category/create', 'CategoryController@create')->name('category.create');
Route::post('category/store', 'CategoryController@store')->name('category.store');
Route::get('categories', 'CategoryController@index')->name('category.index');



Route::get('posts', 'PostsController@index');
Route::get('post/{id}', 'PostsController@show');


//Route::get('/product/custom', 'ProductController@custom');
//Route::Resource('product', 'ProductController');
Route::get('product/create', 'ProductController@create')->name('create');
Route::post('product/store', 'ProductController@store')->name('product.store');
Route::get('product', 'ProductController@index')->name('index');
Route::get('category/product/{product}', 'ProductController@removeCategory')->name('category.product.delete');

Auth::routes();

Route::get('/home', 'HomeController@index')->name('home');


Route::get('one/{id}', 'CategoryController@one');
Route::get('onetomany', 'CategoryController@onetomany');
Route::get('manytomany', 'CategoryController@manytomany');
