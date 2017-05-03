$(function() {
		$("#datum").datepicker();
		$("#letzterkontakt").datepicker();
		
	});
var rootURL = "http://localhost:8080/i_advise_zeiterfassung/rest/kunde/";

function addKunde() {
	console.log('addKunde');
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : rootURL,
		dataType : "json",
		data : formToJSON(),
		success : function(data, textStatus, jqXHR) {
			alert('Kunde created successfully');
			document.location="http://localhost:8080/i_advise_zeiterfassung/mitarbeiter.html";
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('addKunde error: ' + textStatus);
		}
	});
}

$('#btnSave').click(function() {
	addKunde();
	return false;
});

function formToJSON() {
	return JSON.stringify({
		//"kuID" : $('#kuID').val(),
		"anrede" : $('#anrede').val(),
		"vorname" : $('#vorname').val(),
		"name" : $('#name').val(),
		"titel" : $('#titel').val(),
		"abschluss" : $('#abschluss').val(),
		"position" : $('#position').val(),
		"firma" : $('#firma').val(),
		"letzterKontakt" : $('#letzterKontakt').val(),
		"eMail" : $('#eMail').val(),
		"telefongeschaeftl" : $('#telefongeschaeftl').val(),
		"telefonZentrale" :$('#telefonZentrale').val(),
		"fax" : $('#fax').val(),
		"zusatzangabeAdresse" : $('#zusatzangabeAdresse').val(),
		"straße" : $('#straße').val(),
		"hausnummer" : $('#hausnummer').val(),
		"plz" : $('#plz').val(),
		"ort" : $('#ort').val(),
		"land" : $('#land').val(),
		"plz" : $('#plz').val(),
		"persönlicheBemerkung" : $('#persönlicheBemerkung').val(),
		//"aktID" : $('#aktID').val(),
		//"kqID" : $('#kqID').val(),
		//"mitID" : $('#mitID').val(),
		
		
	});
}
