<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Category;
use App\Product;
use App\User;
use App\Account;

class ProductController extends Controller
{
    
    public function index()
    {
        $product = Product::get();

        return view('show', compact('product'));
    }

     public function create()
    {
        return view('product_create');
    }


    public function store(Request $request)
    {
        $product = new Product;
        $product->name = $request->get('name');
        $product->price = $request->get('price');
        //$product->user()->associate($request->user());

        $product->save();
        $category = Category::find([3, 4]);
        $product->categories()->attach($category);

       return redirect('product');
    }

   


    // public function removeCategory(Product $product)
    // {
    //         $category = Category::find(3);

    //         $product->categories()->detach($category);
            
    //         return 'Success';
    // }


    //one to one relastionship
    // public function view()
    // {
    //     $abc = user::find(5)->account;
    //     return $abc;
    // }
    //End one to one relastionship

}
