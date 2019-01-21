<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Post;

class PostsController extends Controller
{
    

public function index()
   {
   	$page = "index page";
   	$posts = Post::all();

    return view('posts.index',compact('posts','page'));
   }

   public function show($id)
   {
   	$post= Post::find($id);
   	return view('posts.show',compact('post'));
   }
    
}
