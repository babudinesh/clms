<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body style=" text-align:  center;">
 Hi success Login
 
 <form class="form-horizontal" name='customerList' id="customerListForm"  method="get">


    <br>
<a href="${pageContext.request.contextPath}/CustomerList.view"> Customer Details </a>
 <br/>             
<a href="${pageContext.request.contextPath}/CustomerAddressList.view"> Customer Address </a>
   
<br/>             
<a href="${pageContext.request.contextPath}/customerContactList.view"> Customer Contact </a>
   
        </form>
        
</body>
</html>