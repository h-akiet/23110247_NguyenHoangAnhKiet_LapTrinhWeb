<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Login</title></head>
<body>
<h2>Login Page</h2>
<form method="post" action="<c:url value='/login' />">
    <label>Username:</label> <input type="text" name="username"/><br/>
    <label>Password:</label> <input type="password" name="password"/><br/>
    <input type="submit" value="Login"/>
</form>
<c:if test="${param.error != null}">
    <p style="color:red">Invalid username or password</p>
</c:if>
<c:if test="${param.logout != null}">
    <p style="color:green">You have been logged out.</p>
</c:if>
</body>
</html>
