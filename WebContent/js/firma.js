var firmaURL = "http://localhost:8080/i_advise_zeiterfassung/rest/firma";
var brancheURL = "http://localhost:8080/i_advise_zeiterfassung/rest/branche";
var aktivitaetURL = "http://localhost:8080/i_advise_zeiterfassung/rest/aktivitaet";
var kampagneURL = "http://localhost:8080/i_advise_zeiterfassung/rest/kampagne";
var mitarbeiterURL = "http://localhost:8080/i_advise_zeiterfassung/rest/mitarbeiter";
var brancheliste;
var aktivitaetliste;
var kampagneliste;
var mitarbeiterliste;
var firmenliste;
var brid;
var aktid;

$(function() {
	var exist;
	var name;
		$("#datum").datepicker();
		$("#letzterkontakt").datepicker();
		name = $("#firmaname").val();
		findAlleBranchen();
		findAlleKampagne();
		findAlleAktivitaeten();
		findAlleMitarbeiter();
		findAlleFirmen();
		$("#firmaname").blur(function(){
			//findAlleFirmen();
			$.each(firmenliste, function(index, fr){
				console.log(firmenliste[index]);
				if(fr.firmaname == $("#firmaname").val().toUpperCase()){
					alert($("#firmaname").val().toUpperCase()+' existiert bereits');
				}
			});
			
		});
		
	});

function getaktid(aktname){
	var id;
	$.each(aktivitaetliste,function(index,akt){
		if(akt.aktname == aktname){
			id = akt.aktid;
		}
	});
	return id;
}

function getbranchenid(beschreibung){
	var id;
	$.each(brancheliste, function(index, br){
		if(br.beschreibung == beschreibung){
			id = br.brID;
		}
		
	});
	console.log('ID TEST: '+id);
	return id;
}

function findAlleFirmen(){
	$.ajax({
		type : 'GET',
		url : firmaURL,
		dataType : "json", // data type of response
		success : success
	});
}

function success(data){
	firmenliste = data
	console.log(firmenliste);
	
}

function findAlleMitarbeiter(){
	$.ajax({
		type : 'GET',
		url : mitarbeiterURL,
		dataType : "json", // data type of response
		success : rendermitSelect
	});
}
function rendermitSelect(data){
	mitarbeiterliste = data == null ? [] : (data instanceof Array ? data
			: [ data ]);
		$.each(mitarbeiterliste, function(index, mit){
		$("<option></option>", {value: mit.vorname+' '+mit.nachname,text: mit.vorname+' '+mit.nachname}).appendTo('.select-mit');
		$("<option></option>", {value: mit.vorname+' '+mit.nachname,text: mit.vorname+' '+mit.nachname}).appendTo('.kennt-auch');
	});
}
function findAlleKampagne(){
	$.ajax({
		type : 'GET',
		url : kampagneURL,
		dataType : "json", // data type of response
		success : renderkampSelect
	});
}
function renderkampSelect(data){
	kampagneliste = data == null ? [] : (data instanceof Array ? data
			: [ data ]);
		$.each(kampagneliste, function(index, kamp){
		$("<option></option>", {value: kamp.kampname,text: kamp.kampname }).appendTo('.select-kamp');
	});
}
function findAlleAktivitaeten(){
	$.ajax({
		type : 'GET',
		url : aktivitaetURL,
		dataType : "json", // data type of response
		success : renderakSelect
	});
}
function renderakSelect(data){
	aktivitaetliste = data == null ? [] : (data instanceof Array ? data
			: [ data ]);
		$.each(aktivitaetliste, function(index, akt){
		$("<option></option>", {value: akt.aktname,text: akt.aktname }).appendTo('.select-akt');
	});
}
	
function findAlleBranchen(){
	$.ajax({
		type : 'GET',
		url : brancheURL,
		dataType : "json", // data type of response
		success : renderbSelect
	});
}
function renderbSelect(data){
	brancheliste = data == null ? [] : (data instanceof Array ? data
			: [ data ]);
		$.each(brancheliste, function(index, branche){
		$("<option></option>", {value: branche.beschreibung,text: branche.beschreibung }).appendTo('.select-branche');
	});
}

function IsEmail(email) {
    var regex = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    if(!regex.test(email)) {
       return false;
    }else{
       return true;
    }
  }

function addFirma() {
	console.log('addFirma');
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : firmaURL,
		dataType : "json",
		data : formToJSON(),
		success : function(data, textStatus, jqXHR) {
			alert('firma created successfully');
			document.location="http://localhost:8080/i_advise_zeiterfassung/Kontakt.html";
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('addFirma error: ' + textStatus);
		}
	});
}

$('#btnSave').click(function() {
	addFirma();
	return false;
});

function formToJSON() {
	var beschreibung = $('.select-branche').val();
	var aktname = $('.select-akt').val();
	console.log('Beschreibung: '+beschreibung);
	aktid = getaktid(aktname);
	brid = getbranchenid(beschreibung);
	console.log('BranchenID: '+brid);
	return JSON.stringify({
		//"kuID" : $('#kuID').val(),
		"firmaid" : null,
		//"anrede" : $('#anrede').val(),
		//"vorname" : $('#vorname').val(),
		//"name" : $('#name').val(),
		//"titel" : $('#titel').val(),
		//"abschluss" : $('#abschluss').val(),
		//"position" : $('#position').val(),
		"letzterkontakt" : $('#letzterKontakt').val(),
		"eMail" : $('#eMail').val(),
		"telefongeschaeftl" : $('#telefongesch').val(),
		"telefonzent" :$('#telefonZentrale').val(),
		"fax" : $('#fax').val(),
		"zusatzangabeAdresse" : $('#zusatzangabeAdresse').val(),
		"strasse" : $('#strasse').val(),
		"hausnummer" : $('#hausnummer').val(),
		"plz" : $('#plz').val(),
		"ort" : $('#ort').val(),
		"land" : $('#land').val(),
		"plz" : $('#plz').val(),
		//"persönlichebemerkung" : $('#persönlichebemerkung').val(),
		//"freitext" : $('#freitext').val(),
		"aktid"	: aktid == 0 ? null : aktid,
		"brid" : brid,
		"kontaktqualitaet": $('#kontaktqualitaet').val(),
		"rechtsform" : $('#rechtsform').val(),
		//"ansprechpartner" : $('#ansprechpartner').val(),
		"boersennotierung" : $('#boersennotierung').val(),
		"firmaname" : $('#firmaname').val().toUpperCase()
	});
}
