<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!doctype html>
<html>
<head>
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="utf-8">
<title> CLMS </title>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet"  />  
</head>
<body>

<form class="form-horizontal" name='forgotpwd' id="loginForm" action="<c:url value='j_spring_security_check' />" method="post">
<header>Login</header>


<br/>
  <input type="text" name="j_username" placeholder="Username" value="" id="userName" class="form-control" >
<div class="help">At least 6 character</div> <br/>

  <input type="password" name="j_password" placeholder="Password" value="" id="password"  class="form-control" >
<div class="help">Use upper and lower case letters as well</div> <br/>
<center> <strong style="color: red;font-weight: bold;font-size: 16px;"> ${msg} </strong> </center> 

            <button type="submit" class="btn btn-default pull-left">Sign in</button> 
              
 <br/>
   
        </form>
        
</body>
</html>