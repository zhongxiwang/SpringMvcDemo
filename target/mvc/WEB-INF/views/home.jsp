<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
        <script type="application/javascript" src="../resources/js/jquery-1.11.3.js"></script>
    </head>
    <body>
        <h1>Hello World!</h1>
        <p>This is the homepage!</p>
    <p>${errorMessage}</p>
        <button onclick="cli()" >get</button>
    <script >
        function cli(){
            $.ajax({
                url:"../convert",
                data:"ruiw,yas",
                type:"POST",
                contentType:"application/x-wisely",
                success:function(data){
                    console.log(data);
                    alert(data);
                }
            });
        }

    </script>
    </body>
</html>
