<%@ page import="com.iadvise.resource.UserResource"%>
<%@ page import="com.iadvise.resource.MitarbeiterResource"%>
<%@ page import="com.iadvise.entities.User"%>
<%@ page import="com.iadvise.entities.Mitarbeiter"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<title>Neue Buchung</title>
<script	src="http://ajax.googleapis.com/ajax/libs/jquery/1.12.1/jquery.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="css/buchung.css">
<link rel="stylesheet" href="css/jquery.ui.timepicker.css">
<script src="js/jquery.ui.timepicker.js"></script>
</head>
<body>
<%
	UserResource uresource = new UserResource();
	User user        = new User();
	Mitarbeiter mit = new Mitarbeiter();
	MitarbeiterResource mresource = new MitarbeiterResource();
	String vorname = null;
	String nachname = null;
	String userName = null;
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("username"))
				userName = cookie.getValue();
		}
	}
	user = uresource.findByName(userName);
	mit = user.getMitID();
	vorname = mit.getVorname();
	nachname = mit.getNachname();
	
%>
<div>
<form>
		<h2>Neue Buchung</h2>
		<fieldset>
			<legend>Buchungsdaten</legend>
			<ul>
				<li> 
					<label>Vorname:</label>
					<input type="text" id="vorname" name="vorname" value=<%=vorname %> >
				</li>
				<li> 
					<label>Nachname:</label>
					<input type="text" id="nachname" name="nachname" value=<%=nachname %> >
				</li>
				<li>
					<label>Projekt:</label>
					<select name="groupid" style="width: 25%;" class="select-group">
					<option value="0" selected>(Please select an option)</option>
				</select>
				</li>
			</ul>
		</fieldset>
		
		<fieldset>
			<legend>Zeitpunkt</legend>
			<ul>
			<li> 
				<label>Stichtag:</label>
				<input type="text" id="stichtag" name="stichtag" />
			</li>
			
			<li> 
				<!--  <label>Uhrzeit(von)/Uhrzeit(bis):</label>
				<input type="text" id="timepicker1" name="timepicker1" class="time1" />&nbsp;
				<input type="text" id="timepicker2" name="timepicker2" class="time2" />
				</li>-->
				<li> 
				<label>Budget Stunden:</label>
				<input type="text" id="budgetstunden" name="budgetstunden" />
			</li>
			</ul>
		</fieldset>
		
		<p>
			<input type="button" id="btnSave" value="Speichern">
		</p>
</form>	
</div>	
	<script src="js/buchung.js"></script>	

</body>
</html>