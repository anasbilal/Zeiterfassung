<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<style>
.hide {
    display: none;
    position:relative;
    clear: both;
}
.show {
    display: inline;
    position:relative;
    clear: both;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Mitarbeiter</title>
<!--  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>-->
	<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>

</head>
<body>
<div align="center">
        <br> <br>
 
        <%
            String userName = null;
        	String rollen = null;
        	String showhide = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                	if(cookie.getName().equals("username"))
                		userName = cookie.getValue();
                    if (cookie.getName().equals("userrollen"))
	                    rollen = cookie.getValue().toString();
                }
            }
           
            if (userName == null){
                response.sendRedirect("registration.html");
                }
            
            if(rollen.contains("admin")){
            	 //response.sendRedirect("Kontakt.html");
            	showhide = "show";
            	}
            /*if(rollen.contains("partner")){
            	//response.sendRedirect("Firma.html");
            	showhide = "hide";
           	}*/
             	
        %>
        <h3>
            Hi <%=userName%>, mit der <%=rollen%> role has  Login successful.
        </h3>
        <br>
       
    </div>

	<ul id="mitarbeiterList"></ul>

	<label>Vorname:</label>
	<input type="text" id="vorname" name="vorname" />
	<br>
	<label class=<%=showhide%> >Nachname:</label>
	<input type="text" id="nachname" name="nachname" class= <%=showhide%> >
	<br class=<%=showhide%>>
	<label>Strasse:</label>
	<input type="text" id="strasse" name="strasse" />
	<br>
	<label>PLZ:</label>
	<input type="text" id="plz" name="plz" />
	<br>
	<label>Stadt:</label>
	<input type="text" id="stadt" name="stadt" />
	<br>
	<button id="btnSave">Save</button><br>
	<select name="groupid" style="width: 25%;" class="select-group">
		<option value="0" selected>(Please select an option)</option>
	</select>
	
	<form action="logout" method="post">
      <input id="logout" type="submit" value="abmelden">
    </form>
	
	<script type="text/javascript" src="js/mitarbeiter.js">
		
	</script>

</body>
</html>