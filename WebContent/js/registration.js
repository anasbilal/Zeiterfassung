var userURL = "http://localhost:8080/i_advise_zeiterfassung/rest/user";
var mitarbeiterURL = "http://localhost:8080/i_advise_zeiterfassung/rest/mitarbeiter";
var logout = "http://localhost:8080/i_advise_zeiterfassung/rest/logout";

function register() {
	var vorname = $('#vorname').val();
	console.log(vorname);
	$.ajax({
		type : 'GET',
		contentType : 'application/json',
		url : mitarbeiterURL + '/search/' + vorname,
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			var mitID;
			var list = data == null ? [] : (data instanceof Array ? data : [data]);
			$.each(list, function(index, m) {
				console.log("mitID: "+m.mitID);
				mitID = m.mitID;
			});
			adduser(mitID);
		},
		error : function(data, textStatus, jqXHR) {
			alert('Mitarbeiter nicht gefunden');
		}
	});
}

function checklogin(){
	var passwort = $("#loginpassword").val();
	var username = $("#username").val();
	console.log("Username: "+username);
	$.ajax({
		type : 'GET',
		contentType : 'application/json',
		url : userURL + '/search/' + username,
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			var uname, pssw;
			var list = data == null ? [] : (data instanceof Array ? data : [data]);
			$.each(list, function(index, user) {
				console.log("Username: "+user.username+" " +"Passwort: "+user.password);
				uname = user.username;
				pssw = user.password;
				if(passwort == pssw){
					document.location="http://localhost:8080/i_advise_zeiterfassung/mitarbeiter.jsp";
				}
			});
		},
		error : function(data, textStatus, jqXHR) {
			alert('User nicht gültig');
		}
	});
}

function adduser(mitID){
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : userURL ,
		dataType : "json",
		data : formToJSON(mitID),
		success : function(data, textStatus, jqXHR) {
			alert('User created successfully');
		},
		error : function(data, textStatus, jqXHR) {
			alert('adduser error: ' + textStatus);
		}
	});
}

function formToJSON(mitID) {
	return JSON.stringify({
		"username" : $('#username').val(),
		"password" : $('#registerpassword').val(),
		"mitID" : mitID
	});
}

$(document)
		.ready(
				function() {
					// On Click SignIn Button Checks For Valid E-mail And All
					// Field Should Be Filled
					$("#login")
							.click(
									function() {
										if ($("#loginpassword").val() == '' || $("#username").val() == '') {
											alert("Bitte alle Felder ausfüllen...!!!!!!");
											return false;
										} else {
											//checklogin();
											//$("#anmeldeform")[0].reset();
										}
									});
					$("#register")
							.click(
									function() {
										var email = new RegExp(
												/^[+a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i);
										if ($("#name").val() == ''
												|| $("#registeremail").val() == ''
												|| $("#registerpassword").val() == ''
												|| $("#cpassword").val() == '') {
											alert("Please fill all fields...!!!!!!");
										} else if (!($("#registeremail").val())
												.match(email)) {
											alert("Bitte eine gültige Email eingeben...!!!!!!");
										} else if (!($("#registerpassword").val()).match($("#cpassword").val())) {
											alert("Passwoerter stimmen nicht ueberein, bitte versuchen Sie noch mal.");
										} else if (($("#registerpassword").val().length) < 8) {
											alert("Passwort muss 8 Zeichen haben...!!!!!!");
										} else {
											register();
											alert("Sie haben sich erfolgreich registriert, Sie koennen sich jetzt anmelden...!!!!!!");
											$("#registerform")[0].reset();
											$("#second").slideUp(
													"slow",
													function() {
														$("#first").slideDown(
																"slow");
													});
										}
									});
					// On Click SignUp It Will Hide Login Form and Display
					// Registration Form
					$("#signup").click(function() {
						$("#first").slideUp("slow", function() {
							$("#second").slideDown("slow");
						});
					});
					// On Click SignIn It Will Hide Registration Form and
					// Display Login Form
					$("#signin").click(function() {
						$("#second").slideUp("slow", function() {
							$("#first").slideDown("slow");
						});
					});
				});