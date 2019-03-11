@extends('layouts.app')

@section('content')

<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<!------ Include the above in your HEAD tag ---------->

<!DOCTYPE html>
<html>
<head>
	<!-- image open kari effect aapva mate -->
	<link rel="stylesheet" type="text/css" media="screen" href="http://cdnjs.cloudflare.com/ajax/libs/fancybox/1.3.4/jquery.fancybox-1.3.4.css" />
<style type="text/css">
    a.fancybox img {
        border: none;
        box-shadow: 0 1px 7px rgba(0,0,0,0.6);
        -o-transform: scale(1,1); -ms-transform: scale(1,1); -moz-transform: scale(1,1); -webkit-transform: scale(1,1); transform: scale(1,1); -o-transition: all 0.2s ease-in-out; -ms-transition: all 0.2s ease-in-out; -moz-transition: all 0.2s ease-in-out; -webkit-transition: all 0.2s ease-in-out; transition: all 0.2s ease-in-out;
    } 
    a.fancybox:hover img {
        position: relative; z-index: 999; -o-transform: scale(1.03,1.03); -ms-transform: scale(1.03,1.03); -moz-transform: scale(1.03,1.03); -webkit-transform: scale(1.03,1.03); transform: scale(1.03,1.03);
    }
</style>
</head>
    <body>
        <div class="container">
        	<div class="row">
               <div class="col-xs-4 item-photo">
                    @if(count($pertId->profile_pic) > 0)
			<!-- multi image show karva mate controller ma json_encode karel 6 aetle -->
			@foreach(json_decode($pertId->profile_pic, true) as $images) 
			<!-- folder ma image hase to j browser ma show karva mate -->
				@if((file_exists("uploads/$images")) && $images )
				<img src="{{url('/uploads/'.$images)}}"  alt="--"  width="100px" height="100px" class="fancybox"/>
				@endif
			 @endforeach
			 @endif
                </div>


                <div class="col-xs-5" style="border:0px solid gray">
                    <!-- Datos del vendedor y titulo del producto -->
                    <h3>VIEW</h3>    
                  

                    <!-- Precios -->
                    <h4 class="title-price">ID</h4>
                    <h6 style="margin-top:0px;">{{$pertId->id}}</h6>
        
                    <!-- Detalles especificos del producto -->


                    <div class="section">
                        <h5 class="title-attr" style="margin-top:15px;" >FIRST NAME</h5> <h6 style="margin-top:0px;">{{$pertId->fname}}</h6>                  
                        <!-- <div>
                            <h5 class="title-attr" style="margin-top:15px;" >LAST NAME</h5> <h6 style="margin-top:0px;">{{$pertId->lname}}</h6></div>
                        </div> -->
                    </div>



                    <div class="section">
                        <h5 class="title-attr" style="margin-top:15px;" >EMAIL</h5> <h6 style="margin-top:0px;">{{$pertId->email}}</h6>                  
                        <!-- <div>
                            <h5 class="title-attr" style="margin-top:15px;" >MOBILE</h5> <h6 style="margin-top:0px;">{{$pertId->mobile}}</h6></div>
 -->
                              <div class="section">
                        <h5 class="title-attr" style="margin-top:15px;" >GENDER</h5> <h6 style="margin-top:0px;">{{$pertId->gender}}</h6>                  
                        <div>
                            <h5 class="title-attr" style="margin-top:15px;" >ADDRESS</h5> <h6 style="margin-top:0px;">{{$pertId->address}}</h6></div>
                        </div>
                    </div> 
                        </div>
                    </div>                  
                    <!-- Botones de compra -->                                          
                </div>                              
            </div>
        </div>        
    
<!-- image open karva mate -->
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/fancybox/1.3.4/jquery.fancybox-1.3.4.pack.min.js"></script>
<script type="text/javascript">
    $(function($){
        var addToAll = false;
        var gallery = true;
        var titlePosition = 'inside';
        $(addToAll ? 'img' : 'img.fancybox').each(function(){
            var $this = $(this);
            var title = $this.attr('title');
            var src = $this.attr('data-big') || $this.attr('src');
            var a = $('<a href="#" class="fancybox"></a>').attr('href', src).attr('title', title);
            $this.wrap(a);
        });
        if (gallery)
            $('a.fancybox').attr('rel', 'fancyboxgallery');
        $('a.fancybox').fancybox({
            titlePosition: titlePosition
        });
    });
    $.noConflict();
</script>

</body>
</html>
@endsection