<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<h1>Send a push to all connected devices</h1>

	<form method="post" action="pushpanel">
		<input type="hidden" name="function" value="broadcast">
		<input type="text" name="pushText" placeholder='{"title":"mytitle","year":"myyear"}' />
		<input type="submit" value="send!" />
	</form>
	
	<br/><br/>
	
	<h1>Add a new device token</h1>

	<form method="post" action="pushpanel">
		<input type="hidden" name="function" value="add_device">
		Platform (ios/android): <input type="text" name="platform" /><br/>
		Token: <input type="text" name="token" />
		<input type="submit" value="add!" />
	</form>
	
	<br/><br/>
	
	<h1>Remove a device by token</h1>

	<form method="post" action="pushpanel">
		<input type="hidden" name="function" value="remove_device">
		Platform (ios/android): <input type="text" name="platform" /><br/>
		Token: <input type="text" name="token" />
		<input type="submit" value="remove!" />
	</form>
	
	<hr>
	
	

</body>
</html>